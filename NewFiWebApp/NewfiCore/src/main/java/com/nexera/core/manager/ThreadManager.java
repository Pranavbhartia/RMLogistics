package com.nexera.core.manager;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nexera.common.commons.LoadConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.Milestones;
import com.nexera.common.exception.FatalException;
import com.nexera.common.vo.WorkItemMilestoneInfo;
import com.nexera.common.vo.lqb.LQBDocumentResponseListVO;
import com.nexera.common.vo.lqb.LQBResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;
import com.nexera.common.vo.lqb.LoadResponseVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.utility.LoadXMLHandler;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.service.WorkflowService;

@Component
@Scope(value = "prototype")
public class ThreadManager implements Runnable {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(ThreadManager.class);

	private Loan loan;

	private List<LoanMilestoneMaster> loanMilestoneMasterList;

	@Autowired
	LqbInvoker lqbInvoker;

	@Autowired
	WorkflowService workflowService;

	@Autowired
	LoanService loanService;

	@Autowired
	EngineTrigger engineTrigger;

	@Autowired
	UploadedFilesListService uploadedFileListService;

	@Override
	public void run() {

		LOGGER.debug("Inside method run ");
		List<Integer> statusTrackingList = new LinkedList<Integer>();
		Map<String, String> map = new HashMap<String, String>();
		int format = 0;

		LOGGER.debug("Invoking load service of lendinqb ");
		JSONObject loadOperationObject = createLoadJsonObject(map,
		        WebServiceOperations.OP_NAME_LOAN_LOAD, loan.getLqbFileId(),
		        format);
		LOGGER.debug("Invoking LQB service to fetch Loan status ");
		JSONObject loadJSONResponse = lqbInvoker
		        .invokeLqbService(loadOperationObject.toString());
		if (loadJSONResponse != null) {
			if (!loadJSONResponse.isNull("responseMessage")) {

				String loadResponse = loadJSONResponse
				        .getString("responseMessage");
				loadResponse = removeBackSlashDelimiter(loadResponse);
				int currentLoanStatus = -1;
				Milestones milestones = null;
				List<LoadResponseVO> loadResponseList = parseLqbResponse(loadResponse);
				if (loadResponseList != null) {
					for (LoadResponseVO loadResponseVO : loadResponseList) {
						String fieldId = loadResponseVO.getFieldId();
						if (fieldId.equalsIgnoreCase("sStatusT")) {
							currentLoanStatus = Integer.parseInt(loadResponseVO
							        .getFieldValue());
						}

					}
					LOSLoanStatus loanStatusID = null;
					if (currentLoanStatus == -1) {
						LOGGER.error("Invalid/No status received from LQB ");
					} else {
						loanStatusID = LOSLoanStatus
						        .getLOSStatus(currentLoanStatus);
						if (loanStatusID == null) {
							LOGGER.error("Not a supported LQB status ");
						}
						List<WorkflowItemExec> workflowItemExecList = getWorkflowItemExecByLoan(loan);
						if (workflowItemExecList == null) {

							workflowItemExecList = new ArrayList<WorkflowItemExec>();
						}
						milestones = WorkflowConstants.LQB_STATUS_MILESTONE_LOOKUP
						        .get(loanStatusID).getMilestone();
						WorkItemMilestoneInfo wItemMSInfo = WorkflowConstants.LQB_STATUS_MILESTONE_LOOKUP
						        .get(loanStatusID);
						if (wItemMSInfo != null) {
							statusTrackingList = wItemMSInfo
							        .getStatusTrackingList();
						}
						if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCUMENT_CHECK
						        || currentLoanStatus == LoadConstants.LQB_STATUS_DOCUMENT_CHECK_FAILED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_PRE_UNDERWRITING
						        || currentLoanStatus == LoadConstants.LQB_STATUS_IN_UNDERWRITING
						        || currentLoanStatus == LoadConstants.LQB_STATUS_PRE_APPROVED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_APPROVED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_CONDITION_REVIEW
						        || currentLoanStatus == LoadConstants.LQB_STATUS_FINAL_UNDER_WRITING
						        || currentLoanStatus == LoadConstants.LQB_STATUS_FINAL_DOCS) {
							LOGGER.debug("These status are related to underwriting ");

						} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_ORDERED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_DRAWN
						        || currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_OUT
						        || currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_BACK
						        || currentLoanStatus == LoadConstants.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW
						        || currentLoanStatus == LoadConstants.LQB_STATUS_IN_PURCHASE_REVIEW
						        || currentLoanStatus == LoadConstants.LQB_STATUS_PRE_PURCHASE_CONDITIONS) {
							LOGGER.debug("These status are related to appraisal ");

						} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PRE_DOC_QC
						        || currentLoanStatus == LoadConstants.LQB_STATUS_CLEAR_TO_CLOSE
						        || currentLoanStatus == LoadConstants.LQB_STATUS_CLEAR_TO_PURCHASE
						        || currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_PURCHASED) {
							LOGGER.debug("These status are related to QC ");

						} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUSPENDED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_DENIED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_WITHDRAWN
						        || currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_ARCHIVED
						        || currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_CLOSED) {
							LOGGER.debug("These status are related to Loan Closure ");

						} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUBMITTED) {
							LOGGER.debug("These status are related to 1003 ");

						}

						LoanMilestoneMaster loanMilestoneMaster = null;
						if (milestones != null) {
							for (LoanMilestoneMaster loanMilestone : getLoanMilestoneMasterList()) {

								if (loanMilestone.getId() == milestones
								        .getMilestoneID()) {
									LOGGER.debug("Found LoanMilestone Master ");
									loanMilestoneMaster = loanMilestone;
									break;

								}
							}
							LOGGER.debug("Saving/Updating LoanMileStone ");
							boolean sameStatus = false;
							boolean newStatus = false;
							LoanMilestone loanMilestone = getLoanMilestone(
							        loan, loanMilestoneMaster);
							if (loanMilestone == null) {
								LOGGER.debug("This is a new entry of this milestone "
								        + loanMilestoneMaster.getName());
								putLoanMilestoneIntoExecution(
								        currentLoanStatus, loadResponseList,
								        loanMilestoneMaster);
								newStatus = false;

							} else {
								LOGGER.debug("Milestone exist, need to update the status ");
								List<LoanMilestone> loanMilestoneList = loan
								        .getLoanMilestones();
								for (LoanMilestone loanMiles : loanMilestoneList) {
									if (Integer.valueOf(loanMiles.getStatus()) == currentLoanStatus) {
										newStatus = false;
										Date date = loanMiles
										        .getStatusUpdateTime();
										if (date != null) {
											String currentDateString = getDataTimeField(
											        currentLoanStatus,
											        loadResponseList);
											if (currentDateString != null) {
												Date currentDate = parseStringIntoDate(currentDateString);
												if (currentDate != null) {
													currentDate = formatDateIntoUTC(currentDate);
													if (date.compareTo(currentDate) == 0) {
														sameStatus = true;
													} else {
														updateLoanMilestone(loanMilestone);
													}
												}
											}

										} else {
											String currentDateString = getDataTimeField(
											        currentLoanStatus,
											        loadResponseList);
											if (currentDateString != null) {
												Date currentDate = parseStringIntoDate(currentDateString);
												if (currentDate != null) {
													currentDate = formatDateIntoUTC(currentDate);
													if (currentDate != null) {
														updateLoanMilestone(loanMilestone);
													}
												}
											} else {
												sameStatus = true;
											}
										}

									}
								}
							}

							if (!sameStatus) {

								LOGGER.debug("If status has changed then only proceed to call the class ");
								if (newStatus) {
									LOGGER.debug("The loan milestone exist but this is a new status hence adding into database ");
									putLoanMilestoneIntoExecution(
									        currentLoanStatus,
									        loadResponseList,
									        loanMilestoneMaster);
								}
								if (statusTrackingList != null)
									checkIfAnyStatusIsMissed(currentLoanStatus,
									        statusTrackingList,
									        loadResponseList,
									        loanMilestoneMaster);

								List<String> workflowItemTypeList = new ArrayList<String>();
								if (wItemMSInfo != null) {
									workflowItemTypeList = wItemMSInfo
									        .getWorkItems();
								}

								List<WorkflowItemExec> itemToExecute = itemToExecute(
								        workflowItemTypeList,
								        workflowItemExecList);
								String params = Utils
								        .convertMapToJson(getParamsBasedOnStatus(currentLoanStatus));
								for (WorkflowItemExec workflowItemExec : itemToExecute) {
									LOGGER.debug("Putting the item in execution ");
									workflowService.saveParamsInExecTable(
									        workflowItemExec.getId(), params);
									engineTrigger
									        .startWorkFlowItemExecution(workflowItemExec
									                .getId());
								}
							}
						}
					}
				} else {
					LOGGER.error(" Unable to parse the LQB response ");
				}
			} else {
				LOGGER.error(" Valid Response Not Received From Mule/LQB ");
			}
		} else {
			LOGGER.error("Connection Timed out at LQB ");
		}
		LOGGER.debug("Fetching Docs for this loan ");
		fetchDocsForThisLoan(loan);
	}

	private void fetchDocsForThisLoan(Loan loan) {

		LOGGER.debug("Invoking ListEdocsByLoanNumber service of lendinqb ");

		Date timeBeforeCallMade = new Date();
		JSONObject getListOfDocs = createListEDocsByLoanNumberObject(
		        WebServiceOperations.OP_NAME_LIST_EDCOS_BY_LOAN_NUMBER,
		        loan.getLqbFileId());

		JSONObject receivedResponseForDocs = lqbInvoker
		        .invokeLqbService(getListOfDocs.toString());
		if (receivedResponseForDocs != null) {
			if (!receivedResponseForDocs.isNull("responseMessage")) {
				LQBResponseVO lqbResponseVO = uploadedFileListService
				        .parseLQBXMLResponse(receivedResponseForDocs);
				if (lqbResponseVO != null) {
					LOGGER.debug("Response received for this loan "
					        + loan.getId());
					if (lqbResponseVO.getResult().equalsIgnoreCase("Ok")) {
						LQBDocumentResponseListVO lqbDocumentResponseListVO = lqbResponseVO
						        .getDocumentResponseListVOs();
						if (lqbDocumentResponseListVO != null) {
							List<LQBedocVO> edocsList = lqbDocumentResponseListVO
							        .getvBedocVO();
							uploadedFileListService.updateUploadedDocument(
							        edocsList, loan, timeBeforeCallMade);
						}
					}
				}
			}
		}

	}

	private void updateLoanMilestone(LoanMilestone loanMilestone) {
		loanService.updateLoanMilestone(loanMilestone);
	}

	private void putLoanMilestoneIntoExecution(int currentLoanStatus,
	        List<LoadResponseVO> loadResponseList,
	        LoanMilestoneMaster loanMilestoneMaster) {

		LoanMilestone loanMilestone = new LoanMilestone();
		String dateTime = getDataTimeField(currentLoanStatus, loadResponseList);
		Date date = null;
		if (dateTime != null && !dateTime.equals(""))
			date = parseStringIntoDate(dateTime);
		if (date != null && !dateTime.equals(""))
			date = formatDateIntoUTC(date);
		loanMilestone.setLoan(loan);
		loanMilestone.setLoanMilestoneMaster(loanMilestoneMaster);
		loanMilestone.setStatusUpdateTime(date);
		loanMilestone.setStatus(String.valueOf(currentLoanStatus));
		saveLoanMilestone(loanMilestone);

	}

	private LoanMilestone getLoanMilestone(Loan loan,
	        LoanMilestoneMaster loanMilestoneMaster) {
		return loanService.findLoanMileStoneByLoan(loan,
		        loanMilestoneMaster.getName());
	}

	private int checkIfAnyStatusIsMissed(int currentStatus,
	        List<Integer> statusTrackingList,
	        List<LoadResponseVO> loadResponseVOList,
	        LoanMilestoneMaster loanMilestoneMaster) {
		List<LoanMilestone> loanMilestoneList = loan.getLoanMilestones();
		int currentIndex = statusTrackingList.indexOf(currentStatus);
		LOGGER.debug("Checking if previous state has an entry ");
		if (currentIndex != 0) {
			int previousStatus = statusTrackingList.get(currentIndex - 1);
			for (LoanMilestone loanMilestone : loanMilestoneList) {
				if (Integer.valueOf(loanMilestone.getStatus()) == previousStatus) {
					LOGGER.debug("No status has been missed hence breaking out of the loop");
					break;
				} else {
					putLoanMilestoneIntoExecution(previousStatus,
					        loadResponseVOList, loanMilestoneMaster);

					LOGGER.debug("Recursively calling to see if multiple status has been missed ");
					checkIfAnyStatusIsMissed(previousStatus,
					        statusTrackingList, loadResponseVOList,
					        loanMilestoneMaster);
					break;
				}
			}
		}
		return 0;
	}

	public String removeBackSlashDelimiter(String string) {
		if (string != null) {
			string = string.replace("\\", "");
		}
		return string;
	}

	public Date parseStringIntoDate(String dateTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
		        "hh:mm a z-MM/dd/yyyy");
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateTime);
		} catch (ParseException pe) {
			LOGGER.error(pe.getMessage());
		}
		return date;

	}

	private Date formatDateIntoUTC(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
		        "hh:mm a z-MM/dd/yyyy");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatedDate = simpleDateFormat.format(date);
		return parseStringIntoDate(formatedDate);
	}

	private List<WorkflowItemExec> itemToExecute(
	        List<String> workflowItemtypeList,
	        List<WorkflowItemExec> workflowItemExecList) {
		List<WorkflowItemExec> itemToExecute = new ArrayList<WorkflowItemExec>();
		for (WorkflowItemExec workflowItemExec : workflowItemExecList) {
			for (String workflowItemType : workflowItemtypeList) {
				if (workflowItemExec.getWorkflowItemMaster()
				        .getWorkflowItemType()
				        .equalsIgnoreCase(workflowItemType)) {
					if (workflowItemExec != null)
						itemToExecute.add(workflowItemExec);
				}
			}
		}
		return itemToExecute;
	}

	public JSONObject createLoadJsonObject(Map<String, String> requestXMLMap,
	        String opName, String lqbLoanId, int format) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_FORMAT, format);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_XML_QUERY_MAP,
			        requestXMLMap);
			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	private List<WorkflowItemExec> getWorkflowItemExecByLoan(Loan loan) {
		List<WorkflowItemExec> workflowItemExecList = new ArrayList<WorkflowItemExec>();
		if (loan != null) {
			WorkflowExec loanManagerWorkflowExec = null;
			Integer loanWFID = loan.getLoanManagerWorkflow();
			if (loanWFID != null)
				loanManagerWorkflowExec = workflowService
				        .getWorkflowExecFromId(loanWFID);

			Integer custWFID = loan.getCustomerWorkflow();
			WorkflowExec customerWorkflowExec = null;
			if (loanWFID != null)
				customerWorkflowExec = workflowService
				        .getWorkflowExecFromId(custWFID);
			if (loanManagerWorkflowExec != null) {
				workflowItemExecList.addAll(loanManagerWorkflowExec
				        .getWorkflowItems());
			}
			if (customerWorkflowExec != null) {
				workflowItemExecList.addAll(customerWorkflowExec
				        .getWorkflowItems());
			}
		}
		return workflowItemExecList;
	}

	private Map<String, Object> getParamsBasedOnStatus(int currentLoanStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME, loan.getId());
		String wfItemStatus = null;
		if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUBMITTED) {
			map.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        "08986e4b-8407-4b44-9000-50c104db899c");
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_IN_UNDERWRITING) {
			wfItemStatus = LoanStatus.inUnderwriting;
			map.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        "08986e4b-8407-4b44-9000-50c104db899c");
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_CLEAR_TO_CLOSE) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_FUNDED) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUSPENDED) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_DENIED) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_WITHDRAWN) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_ARCHIVED) {

		}
		if (wfItemStatus != null) {
			map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
			        wfItemStatus);
		}
		return map;

	}

	private LoanMilestone saveLoanMilestone(LoanMilestone loanMilestone) {
		LOGGER.debug("Inside method saveLoanMilestone ");
		return loanService.saveLoanMilestone(loanMilestone);
	}

	private String getDataTimeField(int currentLoanStatus,
	        List<LoadResponseVO> loadResponseVOList) {
		if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_OPEN) {
			return getResponseBasedOnField(LoadConstants.LOAN_OPEN_TIME_FIELD,
			        LoadConstants.LOAN_OPEN_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PRE_PROCESSING) {
			return getResponseBasedOnField(
			        LoadConstants.PRE_PROCESSING_TIME_FIELD_FIELD,
			        LoadConstants.PRE_PROCESSING_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PROCESSING) {
			return getResponseBasedOnField(LoadConstants.PROCESSING_TIME_FIELD,
			        LoadConstants.PROCESSING_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCUMENT_CHECK) {
			return getResponseBasedOnField(
			        LoadConstants.DOCUMENT_CHECK_TIME_FIELD,
			        LoadConstants.DOCUMENT_CHECK_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCUMENT_CHECK_FAILED) {
			return getResponseBasedOnField(
			        LoadConstants.DOCUMENT_CHECK_FAILED_TIME_FIELD,
			        LoadConstants.DOCUMENT_CHECK_FAILED_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUBMITTED) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_SUBMITTED_TIME_FIELD,
			        LoadConstants.LOAN_SHIPPED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PRE_UNDERWRITING) {
			return getResponseBasedOnField(
			        LoadConstants.PRE_UNDER_WRITTING_TIME_FIELD,
			        LoadConstants.PRE_UNDER_WRITTING_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_IN_UNDERWRITING) {
			return getResponseBasedOnField(
			        LoadConstants.IN_UNDER_WRITTING_TIME_FIELD,
			        LoadConstants.IN_UNDER_WRITTING_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PRE_APPROVED) {
			return getResponseBasedOnField(
			        LoadConstants.PRE_APPROVED_TIME_FIELD,
			        LoadConstants.PRE_APPROVED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_APPROVED) {
			return getResponseBasedOnField(LoadConstants.APPROVED_TIME_FIELD,
			        LoadConstants.APPROVED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_CONDITION_REVIEW) {
			return getResponseBasedOnField(
			        LoadConstants.CONDITION_REVIEW_TIME_FIELD,
			        LoadConstants.CONDITION_REVIEW_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_FINAL_UNDER_WRITING) {
			return getResponseBasedOnField(
			        LoadConstants.FINAL_UNDER_WRITTING_TIME_FIELD,
			        LoadConstants.FINAL_UNDER_WRITTING_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PRE_DOC_QC) {
			return getResponseBasedOnField(LoadConstants.PRE_DOC_QC_TIME_FIELD,
			        LoadConstants.PRE_DOC_QC_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_CLEAR_TO_CLOSE) {
			return getResponseBasedOnField(
			        LoadConstants.CLEAR_TO_CLOSE_TIME_FIELD,
			        LoadConstants.CLEAR_TO_CLOSE_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_ORDERED) {
			return getResponseBasedOnField(
			        LoadConstants.DOCS_ORDERED_TIME_FIELD,
			        LoadConstants.DOCS_ORDERED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_DRAWN) {
			return getResponseBasedOnField(LoadConstants.DOCS_DRAWN_TIME_FIELD,
			        LoadConstants.DOCS_DRAWN_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_OUT) {
			return getResponseBasedOnField(LoadConstants.DOCS_OUT_TIME_FIELD,
			        LoadConstants.DOCS_OUT_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCS_BACK) {
			return getResponseBasedOnField(LoadConstants.DOCS_BACK_TIME_FIELD,
			        LoadConstants.DOCS_BACK_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_FUNDING_CONDITIONS) {
			return getResponseBasedOnField(
			        LoadConstants.FUNDING_CONDITIONS_TIME_FIELD,
			        LoadConstants.FUNDED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_FUNDED) {
			return getResponseBasedOnField(LoadConstants.FUNDED_TIME_FIELD,
			        LoadConstants.FUNDED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_RECORDED) {
			return getResponseBasedOnField(LoadConstants.RECORDED_TIME_FIELD,
			        LoadConstants.RECORDED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_FINAL_DOCS) {
			return getResponseBasedOnField(LoadConstants.FINAL_DOCS_TIME_FIELD,
			        LoadConstants.FINAL_DOCS_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_CLOSED) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_CLOSED_TIME_FIELD,
			        LoadConstants.LOAN_CLOSED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW) {
			return getResponseBasedOnField(
			        LoadConstants.SUBMITTED_FOR_PURCHASE_REVIEW_TIME_FIELD,
			        LoadConstants.SUBMITTED_FOR_FINAL_PURCHASE_REVIEW_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_IN_PURCHASE_REVIEW) {
			return getResponseBasedOnField(
			        LoadConstants.IN_PURCHASE_REVIEW_TIME_FIELD,
			        LoadConstants.IN_PURCHASE_REVIEW_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_PRE_PURCHASE_CONDITIONS) {
			return getResponseBasedOnField(
			        LoadConstants.PRE_PURCHASE_REVIEW_TIME_FIELD,
			        LoadConstants.PRE_PURCHASE_REVIEW_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_CLEAR_TO_PURCHASE) {
			return getResponseBasedOnField(
			        LoadConstants.CLEAR_TO_PURCHASE_REVIEW_TIME_FIELD,
			        LoadConstants.CLEAR_TO_PURCHASE_REVIEW_DATE_FIELD,
			        loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_PURCHASED) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_PURCHASED_TIME_FIELD,
			        LoadConstants.LOAN_PURCHASED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUSPENDED) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_SUSPENDED_TIME_FIELD,
			        LoadConstants.LOAN_SUSPENDED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_DENIED) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_DENIED_TIME_FIELD,
			        LoadConstants.LOAN_DENIED_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_WITHDRAWN) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_WITHDRAWN_TIME_FIELD,
			        LoadConstants.LOAN_WITHDRAWN_DATE_FIELD, loadResponseVOList);
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_ARCHIVED) {
			return getResponseBasedOnField(
			        LoadConstants.LOAN_ARCHIVED_TIME_FIELD,
			        LoadConstants.LOAN_ARCHIVED_DATE_FIELD, loadResponseVOList);
		}
		return null;

	}

	private String getResponseBasedOnField(String timeField, String dateField,
	        List<LoadResponseVO> loadResponseVOList) {
		String date = "";
		String time = "";
		for (LoadResponseVO loadResponseVO : loadResponseVOList) {
			if (loadResponseVO.getFieldId().equalsIgnoreCase(dateField)) {
				date = loadResponseVO.getFieldValue();
			}
			if (loadResponseVO.getFieldId().equalsIgnoreCase(timeField)) {
				time = loadResponseVO.getFieldValue();
			}
		}

		if (time != null && time.equals(""))
			time = date;
		else {

			if (date != null && !date.equals(""))
				time = time.concat("-").concat(date);

		}
		return time;
	}

	public JSONObject createListEDocsByLoanNumberObject(String opName,
	        String lqbLoanId) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	private List<LoadResponseVO> parseLqbResponse(String loadResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			LoadXMLHandler handler = new LoadXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(loadResponse)), handler);
			return handler.getLoadResponseVOList();

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return null;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public List<LoanMilestoneMaster> getLoanMilestoneMasterList() {
		return loanMilestoneMasterList;
	}

	public void setLoanMilestoneMasterList(
	        List<LoanMilestoneMaster> loanMilestoneMasterList) {
		this.loanMilestoneMasterList = loanMilestoneMasterList;
	}

}
