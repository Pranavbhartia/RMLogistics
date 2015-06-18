package com.nexera.core.batchprocessor;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.entity.BatchJobExecution;
import com.nexera.common.entity.BatchJobMaster;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.vo.lqb.ModifiedLoanListResponseVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.manager.ThreadManager;
import com.nexera.core.service.BatchService;
import com.nexera.core.service.LoanService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.ModifiedLoanListXMLHandler;
import com.nexera.core.utility.NexeraUtility;

@DisallowConcurrentExecution
public class LoanBatchProcessor extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory.getLogger("batchJobs");

	@Autowired
	private LoanService loanService;

	@Autowired
	private ApplicationContext applicationContext;

	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private BatchService batchService;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Autowired
	LqbInvoker lqbInvoker;

	private ExceptionMaster exceptionMaster;

	@Value("${lqb.appcode}")
	private String appCode;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
	        throws JobExecutionException {

		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LOGGER.info("Triggering the Quartz Schedular ");
		loadExceptionMaster();
		BatchJobMaster batchJobMaster = getBatchJobMasterById(2);
		if (batchJobMaster != null) {
			if (batchJobMaster.getStatus() == CommonConstants.STATUS_ACTIVE) {
				taskExecutor = getTaskExecutor();
				BatchJobExecution batchJobExecution = putBatchIntoExecution(batchJobMaster);
				try {
					LOGGER.info("Invoking modifiedLoanListByAppCode service of lendinqb ");
					List<ModifiedLoanListResponseVO> modifiedLoanResponseList = null;
					JSONObject modifiedLoanListOperationObject = createLoanModifiedListJsonObject(WebServiceOperations.OP_NAME_LIST_MODIFIED_LOANS_BY_APP_CODE);
					if (modifiedLoanListOperationObject != null) {
						LOGGER.info("Invoking LQB service to fetch modified Loans ");
						JSONObject modifiedLoanListJSONResponse = lqbInvoker
						        .invokeLqbService(modifiedLoanListOperationObject
						                .toString());
						if (modifiedLoanListJSONResponse != null) {
							if (!modifiedLoanListJSONResponse
							        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {

								String modifiedLoanListResponse = modifiedLoanListJSONResponse
								        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
								modifiedLoanListResponse = nexeraUtility
								        .removeBackSlashDelimiter(modifiedLoanListResponse);
								modifiedLoanResponseList = parseLoanModifiedLqbResponse(modifiedLoanListResponse);
							}
						}
					}

					List<Loan> modifiedLoans = new ArrayList<Loan>();
					List<Loan> loanList = loanService.getLoansInActiveStatus();
					if (modifiedLoanResponseList != null) {
						for (ModifiedLoanListResponseVO modifiedLoanListResponseVO : modifiedLoanResponseList) {
							if (modifiedLoanListResponseVO.getValid()) {
								LOGGER.info("This loan is still valid in lqb "
								        + modifiedLoanListResponseVO
								                .getLoanName());
								for (Loan loan : loanList) {
									if (loan.getLqbFileId() != null) {
										if (loan.getLqbFileId()
										        .equalsIgnoreCase(
										                modifiedLoanListResponseVO
										                        .getLoanName())) {
											modifiedLoans.add(loan);
										}
									} else {
										LOGGER.info("This loan doesnt have an lqb id associated with it "
										        + loan.getId());
									}
								}
							} else {
								LOGGER.info("Loan has been deleted in LQB, hence removing this loan"
								        + modifiedLoanListResponseVO
								                .getLoanName());
								JSONObject ClearModifiedLoanByNameByAppCodeObject = createClearModifiedLoanObject(
								        WebServiceOperations.OP_NAME_CLEARED_MODIFIED_LOAN_BY_NAME_BY_APP_CODE,
								        modifiedLoanListResponseVO
								                .getLoanName());
								if (ClearModifiedLoanByNameByAppCodeObject != null) {
									LOGGER.info("Invoking LQB service to clear Loan "
									        + modifiedLoanListResponseVO
									                .getLoanName());
									lqbInvoker
									        .invokeLqbService(ClearModifiedLoanByNameByAppCodeObject
									                .toString());
								}
							}
						}
					}
					if (modifiedLoans != null) {
						for (Loan loan : modifiedLoans) {
							if (loan.getLqbFileId() != null) {
								ThreadManager threadManager = applicationContext
								        .getBean(ThreadManager.class);
								threadManager
								        .setLoanMilestoneMasterList(getLoanMilestoneMasterList());
								threadManager.setLoan(loan);
								threadManager.setInvokeLQB(true);
								threadManager
								        .setExceptionMaster(exceptionMaster);
								taskExecutor.execute(threadManager);
							}
						}

					}

					for (Loan loan : loanList) {
						if (!modifiedLoans.contains(loan)) {
							ThreadManager threadManager = applicationContext
							        .getBean(ThreadManager.class);
							threadManager
							        .setLoanMilestoneMasterList(getLoanMilestoneMasterList());
							threadManager.setLoan(loan);
							threadManager.setInvokeLQB(false);
							threadManager.setExceptionMaster(exceptionMaster);
							taskExecutor.execute(threadManager);

						}
					}

					taskExecutor.shutdown();

				} finally {
					LOGGER.info("Updating the end time for this batch job ");
					updateBatchJobExecution(batchJobExecution);

				}
			} else {
				LOGGER.info("Batch Jobs Not Running ");
			}
		}
	}

	public JSONObject createClearModifiedLoanObject(String opName,
	        String lqbLoanId) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);

			jsonChild.put(WebServiceMethodParameters.PARAMETER_APP_CODE,
			        appCode);
			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
		}
		return json;
	}

	private void updateBatchJobExecution(BatchJobExecution batchJobExecution) {
		batchJobExecution.setDateLastRunEndtime(new Date());
		batchService.updateBatchJobExecution(batchJobExecution);

	}

	private BatchJobExecution putBatchIntoExecution(
	        BatchJobMaster batchJobMaster) {
		BatchJobExecution batchJobExecution = new BatchJobExecution();
		batchJobExecution.setBatchJobMaster(batchJobMaster);
		batchJobExecution
		        .setComments("Loan Batch Processor Has Been Put Into Execution ");
		batchJobExecution.setDateLastRunStartTime(new Date());
		return batchService.putBatchIntoExecution(batchJobExecution);
	}

	private BatchJobMaster getBatchJobMasterById(int batchJobId) {
		LOGGER.info("Inside method getBatchJobMasterById ");
		return batchService.getBatchJobMasterById(batchJobId);
	}

	private List<LoanMilestoneMaster> getLoanMilestoneMasterList() {
		LOGGER.info("Inside method getLoanMilestoneMasterList ");

		return loanService.getLoanMilestoneMasterList();
	}

	private ExceptionMaster loadExceptionMaster() {

		if (exceptionMaster == null) {
			LOGGER.info("Loading Loan ExceptionMaster ");
			exceptionMaster = nexeraUtility
			        .getExceptionMasterByType(CoreCommonConstants.EXCEPTION_TYPE_LOAN_BATCH);

		}
		return exceptionMaster;

	}

	private ThreadPoolTaskExecutor getTaskExecutor() {
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.initialize();
		taskExecutor.setCorePoolSize(3);
		taskExecutor.setMaxPoolSize(8);
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(Integer.MAX_VALUE);
		return taskExecutor;

	}

	public JSONObject createLoanModifiedListJsonObject(String opName) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_APP_CODE,
			        appCode);
			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
		}
		return json;
	}

	private List<ModifiedLoanListResponseVO> parseLoanModifiedLqbResponse(
	        String loadResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			ModifiedLoanListXMLHandler handler = new ModifiedLoanListXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(loadResponse)), handler);
			return handler.getModifiedLoanList();

		} catch (SAXException se) {
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        se.getMessage());
			nexeraUtility.sendExceptionEmail(se.getMessage());
		} catch (ParserConfigurationException pce) {
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        pce.getMessage());
			nexeraUtility.sendExceptionEmail(pce.getMessage());
		} catch (IOException ie) {
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        ie.getMessage());
			nexeraUtility.sendExceptionEmail(ie.getMessage());
		}

		return null;
	}
}
