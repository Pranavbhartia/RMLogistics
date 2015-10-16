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
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoadConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.User;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.LoanTypeMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.WorkItemMilestoneInfo;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.common.vo.lqb.CreditScoreResponseVO;
import com.nexera.common.vo.lqb.LQBDocumentResponseListVO;
import com.nexera.common.vo.lqb.LQBResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;
import com.nexera.common.vo.lqb.LoadResponseVO;
import com.nexera.common.vo.lqb.UnderwritingConditionResponseVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.BraintreePaymentGatewayService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.TransactionService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.impl.LoanServiceImpl;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.CreditScoreXMLHandler;
import com.nexera.core.utility.LoadXMLHandler;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.core.utility.UnderwritingXMLHandler;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;

@Component
@Scope(value = "prototype")
public class ThreadManager implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger("batchJobs");

	private Loan loan;

	private List<LoanMilestoneMaster> loanMilestoneMasterList;

	private boolean invokeLQB;

	@Autowired
	LqbInvoker lqbInvoker;

	private ExceptionMaster exceptionMaster;

	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	LoanService loanService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	NexeraUtility nexeraUtility;

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	BraintreePaymentGatewayService braintreePaymentGatewayService;

	@Autowired
	UploadedFilesListService uploadedFileListService;

	@Autowired
	NeedsListService needsListService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	TemplateService templateService;

	@Autowired
	SendGridEmailService sendGridEmailService;

	List<WorkflowItemExec> workflowItemExecList = new ArrayList<WorkflowItemExec>();

	@Value("${date.format.1}")
	private String dateFormat1;

	@Value("${date.format.2}")
	private String dateFormat2;

	@Value("${date.format.3}")
	private String dateFormat3;

	@Value("${date.format.4}")
	private String dateFormat4;

	@Value("${profile.url}")
	private String baseUrl;

	@Value("${lqb.appcode}")
	private String appCode;

	@Override
	public void run() {

		boolean success = true;
		LOGGER.debug("Inside method run ");
		LOGGER.debug("Loading all workflowitems");
		workflowItemExecList = getWorkflowItemExecByLoan(loan);
		if (invokeLQB) {

			List<Integer> statusTrackingList = new LinkedList<Integer>();
			Map<String, String> map = new HashMap<String, String>();
			int format = 0;
			LOGGER.debug("Invoking load service of lendinqb ");
			JSONObject loadOperationObject = nexeraUtility
			        .createLoadJsonObject(map,
			                WebServiceOperations.OP_NAME_LOAN_BATCH_LOAD,
			                loan.getLqbFileId(), format, exceptionMaster);
			if (loadOperationObject != null) {
				LOGGER.debug("Invoking LQB service to fetch Loan status ");
				JSONObject loadJSONResponse = lqbInvoker
				        .invokeLqbService(loadOperationObject.toString());
				if (loadJSONResponse != null) {
					if (!loadJSONResponse
					        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {

						String loadResponse = loadJSONResponse
						        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
						loadResponse = nexeraUtility
						        .removeBackSlashDelimiter(loadResponse);
						int currentLoanStatus = -1;
						String lockStatus = null;
						String lockexpirationDate = null;
						String lockedRate = null;
						String docsoutDate = null;
						String appraisalOrderedDate = null;
						String appraisalReceivedDate = null;
						String disclosureReceivedDate = null;
						String loanApprValue = null;
						String loanAmount = null;
						Milestones theMilestone = null;
						String ltv = null;
						List<LoadResponseVO> loadResponseList = parseLqbResponse(loadResponse);
						if (loadResponseList != null) {
							for (LoadResponseVO loadResponseVO : loadResponseList) {
								String fieldId = loadResponseVO.getFieldId();
								if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_LOAD_LOAN_STATUS)) {
									currentLoanStatus = Integer
									        .parseInt(loadResponseVO
									                .getFieldValue());
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_RATE_LOCK_STATUS)) {
									if (loadResponseVO.getFieldValue() != null) {
										lockStatus = loadResponseVO
										        .getFieldValue();
									}
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_RATE_LOCK_EXPIRED_DATE)) {
									if (loadResponseVO.getFieldValue() != null) {
										lockexpirationDate = loadResponseVO
										        .getFieldValue();
									}
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_LOCKED_RATE)) {
									if (loadResponseVO.getFieldValue() != null) {
										lockedRate = loadResponseVO
										        .getFieldValue();
									}
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_DOCS_OUT)) {
									if (loadResponseVO.getFieldValue() != null) {
										docsoutDate = loadResponseVO
										        .getFieldValue();
									}
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_APPRAISAL_ORDERED)) {
									if (loadResponseVO.getFieldValue() != null) {
										appraisalOrderedDate = loadResponseVO
										        .getFieldValue();
									}
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_APPRAISAL_RECEIVED)) {
									if (loadResponseVO.getFieldValue() != null) {
										appraisalReceivedDate = loadResponseVO
										        .getFieldValue();
									}
								}
								else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_DISCLOSURE_RECEIVED)) {
									if (loadResponseVO.getFieldValue() != null) {
										disclosureReceivedDate = loadResponseVO
										        .getFieldValue();
									}
								}
								else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_LOAN_APPR_VALUE)) {
									if (loadResponseVO.getFieldValue() != null) {
										loanApprValue = loadResponseVO
										        .getFieldValue();
									}
								} else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_LOAN_AMOUNT)) {
									if (loadResponseVO.getFieldValue() != null) {
										loanAmount = loadResponseVO
										        .getFieldValue();
									}
								}
								else if (fieldId
								        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_LTV)) {
									if (loadResponseVO.getFieldValue() != null) {
										ltv = loadResponseVO
										        .getFieldValue();
									}
								}

							}
							if (loanAmount != null) {
								Loan updateLoan = new Loan(loan.getId());
								updateLoan.setLqbLoanAmount(Double
								        .parseDouble(formatAmount(loanAmount)));
								updateLoan.setLqbAppraisedValue(loan
								        .getLqbAppraisedValue());
								loanService.updateLQBAmounts(updateLoan);
							}
							if (loanApprValue != null) {
								Loan updateLoan = new Loan(loan.getId());
								updateLoan
								        .setLqbAppraisedValue(Double
								                .parseDouble(formatAmount(loanApprValue)));
								updateLoan.setLqbLoanAmount(loan
								        .getLqbLoanAmount());
								loanService.updateLQBAmounts(updateLoan);
							}
							if (ltv != null && loan.getLtv() == null)
							{
								//update Loan for LTV
								LOGGER.info("Updating LTV for loan " + loan.getId());
								Loan updateLoan = new Loan(loan.getId());
								updateLoan.setLtv(Double
								        .parseDouble(formatAmount(ltv)));
								loanService.updateLtv(updateLoan);
							}
							if (lockStatus != null) {
								Date date = null;
								String lockedRateCheck = null;
								if (lockexpirationDate != null) {
									date = parseStringIntoDate(lockexpirationDate);
								}
								if (lockedRate != null) {
									lockedRateCheck = lockedRate;
								}
								loanService.updateLoanLockDetails(loan.getId(),
								        date, lockedRateCheck, lockStatus);
							}
							if (docsoutDate != null) {
								LoanMilestoneMaster lmMaster = getLoanMilestoneMasterBasedOnMilestone(Milestones.DOCS_OUT);
								LoanMilestone loanMilestone = getLoanMilestone(
								        loan, lmMaster);
								// Update Docs Out milestone here.
								if (loanMilestone == null) {
									loanMilestone = new LoanMilestone();
									LOSLoanStatus loanStatus = LOSLoanStatus.LQB_STATUS_DOCS_OUT;
									loanMilestone.setLoan(loan);
									loanMilestone
									        .setLoanMilestoneMaster(getLoanMilestoneMasterBasedOnMilestone(Milestones.DOCS_OUT));
									loanMilestone.setStatusUpdateTime(new Date(
									        System.currentTimeMillis()));
									loanMilestone.setStatus(String
									        .valueOf(loanStatus
									                .getLosStatusID()));
									loanMilestone.setComments(loanStatus
									        .getDisplayStatus());
									loanMilestone.setOrder(loanStatus
									        .getOrder());
									saveLoanMilestone(loanMilestone);
								}
							}
							if (appraisalOrderedDate != null) {
								List<String> workflowItems = WorkflowConstants.STATUS_WF_ITEM_LOOKUP
								        .get(LoadConstants.LQB_APPRAISAL_ORDER);
								List<WorkflowItemExec> workflowItemsExecList = itemToExecute(
								        workflowItems, workflowItemExecList);
								putItemsIntoExecution(workflowItemsExecList,
								        LoadConstants.LQB_APPRAISAL_ORDER);
							}
							if (appraisalReceivedDate != null) {
								List<String> workflowItems = WorkflowConstants.STATUS_WF_ITEM_LOOKUP
								        .get(LoadConstants.LQB_APPRAISAL_RECEIVED);
								List<WorkflowItemExec> workflowItemsExecList = itemToExecute(
								        workflowItems, workflowItemExecList);
								putItemsIntoExecution(workflowItemsExecList,
								        LoadConstants.LQB_APPRAISAL_RECEIVED);
							}
							if (disclosureReceivedDate != null) {
								List<String> workflowItems = WorkflowConstants.MILESTONE_WF_ITEM_LOOKUP
								        .get(Milestones.DISCLOSURE);
								List<WorkflowItemExec> workflowItemsExecList = itemToExecute(
								        workflowItems, workflowItemExecList);
								putItemsIntoExecution(workflowItemsExecList,
								        LoadConstants.LQB_DISCLOSURE_RECEIVED);
							}
							LOSLoanStatus loanStatusID = null;
							if (currentLoanStatus == -1) {
								LOGGER.error("Invalid/No status received from LQB ");
								success = false;
							} else {
								loanStatusID = LOSLoanStatus
								        .getLOSStatus(currentLoanStatus);
								if (loanStatusID == null) {
									LOGGER.error("Not a supported LQB status ");
									success = false;
								}

								if (workflowItemExecList == null) {

									workflowItemExecList = new ArrayList<WorkflowItemExec>();
								}
								// Rajeswari : Get the milestone that this
								// Status code corresponds to.
								theMilestone = WorkflowConstants.LQB_STATUS_MILESTONE_LOOKUP
								        .get(loanStatusID).getMilestone();
								// Rajeswari : Get the other look ups like :
								// what are the concrete classes to call and
								// What are the other statuses linked to this
								// milestone if missed earlier
								WorkItemMilestoneInfo wItemMSInfo = getWorkItemMilestoneInfoBasedOnLoanStatus(loanStatusID);
								if (wItemMSInfo != null) {
									statusTrackingList = wItemMSInfo
									        .getStatusTrackingList();

								}
								if (currentLoanStatus == LOSLoanStatus.LQB_STATUS_PRE_QUAL
								        .getLosStatusID()) {
									LOGGER.debug("****PRE_QUAL RECEIVED*** for Loan"
									        + loan.getId());
								}
								if (currentLoanStatus == LoadConstants.LQB_STATUS_DOCUMENT_CHECK
								        || currentLoanStatus == LoadConstants.LQB_STATUS_DOCUMENT_CHECK_FAILED
								        || currentLoanStatus == LoadConstants.LQB_STATUS_PRE_UNDERWRITING
								        || currentLoanStatus == LoadConstants.LQB_STATUS_IN_UNDERWRITING
								        || currentLoanStatus == LoadConstants.LQB_STATUS_PRE_APPROVED
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

								} else if (currentLoanStatus == LoadConstants.LQB_STATUS_APPROVED) {
									LOGGER.debug("Approved has now been received ");
								}
								LoanMilestoneMaster loanMilestoneMaster = null;
								if (theMilestone != null) {
									loanMilestoneMaster = getLoanMilestoneMasterBasedOnMilestone(theMilestone);
									LOGGER.debug("Saving/Updating LoanMileStone ");
									boolean sameStatus = false;
									boolean newStatus = true;
									LoanMilestone loanMilestone = getLoanMilestone(
									        loan, loanMilestoneMaster);
									if (loanMilestone == null) {
										LOGGER.debug("This is a new entry of this milestone "
										        + loanMilestoneMaster.getName());
										putLoanMilestoneIntoExecution(
										        loanStatusID,
										        currentLoanStatus,
										        loadResponseList,
										        loanMilestoneMaster);
										newStatus = false;
									} else {
										LOGGER.debug("Milestone exist, need to update the status ");
										List<LoanMilestone> loanMilestoneList = loan
										        .getLoanMilestones();
										for (LoanMilestone loanMiles : loanMilestoneList) {
											if (loanMiles.getStatus() == null) {
												continue;
											}
											if (Integer.valueOf(loanMiles
											        .getStatus()) == currentLoanStatus) {
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

															if (currentDate != null) {
																updateLoanMilestone(loanMilestone);
															}
														} else {
															sameStatus = true;
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
											        loanStatusID,
											        currentLoanStatus,
											        loadResponseList,
											        loanMilestoneMaster);
										}
										if (WorkflowConstants.LQB_MONITOR_LIST
										        .contains(currentLoanStatus)
										        && !WorkflowConstants.LOAN_CLOSURE_LIST
										                .contains(currentLoanStatus)) {
											checkIfAnyStatusIsMissed(
											        currentLoanStatus,
											        WorkflowConstants.LQB_MONITOR_LIST,
											        loadResponseList,
											        getLoanMilestoneMasterList());
										}
										List<String> workflowItemTypeList = getWorkflowItemTypeListBasedOnWorkflowItemMSInfo(wItemMSInfo);

										List<WorkflowItemExec> itemsToExecute = itemToExecute(
										        workflowItemTypeList,
										        workflowItemExecList);
										LOGGER.debug("Putting milestone into execution ");
										putItemsIntoExecution(itemsToExecute,
										        currentLoanStatus);

										LOGGER.debug("Updating last acted on time ");
										updateLastModifiedTimeForThisLoan(loan);
									}
								}
							}
						} else {
							LOGGER.error(" Unable to parse the LQB response ");
							success = false;
							nexeraUtility.putExceptionMasterIntoExecution(
							        exceptionMaster,
							        " Unable to parse the LQB response ");
							nexeraUtility
							        .sendExceptionEmail("Unparasable LQB Response ");
						}
					} else {
						LOGGER.error(" Valid Response Not Received From Mule/LQB ");
						success = false;
						nexeraUtility.putExceptionMasterIntoExecution(
						        exceptionMaster,
						        "Invalid response received from mule/lqb");
						nexeraUtility
						        .sendExceptionEmail("Invalid Response Received From LQB ");
					}
				} else {
					LOGGER.error("Connection Timed out at LQB ");
					success = false;
					nexeraUtility.putExceptionMasterIntoExecution(
					        exceptionMaster, " Connection to mule timed out");
					nexeraUtility
					        .sendExceptionEmail("Connection to mule timed out");
				}
			} else {
				success = false;
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        "Request Json Object Not Created For Load ");
				nexeraUtility
				        .sendExceptionEmail("Unable to create json object for load ");

			}

			if (success) {
				JSONObject ClearModifiedLoanByNameByAppCodeObject = createClearModifiedLoanObject(
				        WebServiceOperations.OP_NAME_CLEARED_MODIFIED_LOAN_BY_NAME_BY_APP_CODE,
				        loan.getLqbFileId());
				if (ClearModifiedLoanByNameByAppCodeObject != null) {
					LOGGER.debug("Invoking LQB service to clear this loan "
					        + loan.getLqbFileId());
					lqbInvoker
					        .invokeLqbService(ClearModifiedLoanByNameByAppCodeObject
					                .toString());
				}
			}
		}

		if (loan.getLqbFileId() != null) {
			LOGGER.debug("Fetching Docs for this loan ");
			fetchDocsForThisLoan(loan);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Exception while sleeping for loanId: "+loan.getId()+", Exception is: "+e.getMessage());
			}
		}

		LOGGER.debug("Fetching underwriting conditions for this loan ");
		/*
		 * if (!invokeUnderwritingCondition(loan, format)) { success = false; }
		 */

		LOGGER.debug("Fetch Credit Score For This Loan ");
		if (loan.getLqbFileId() != null) {
			CustomerDetail customerDetail = loan.getUser().getCustomerDetail();
			boolean creditScoreValid = loanService
			        .isCreditScoreValid(customerDetail);
			// Using invokeLQB field to check whether loan is modified or not
			if (customerDetail.getTransunionScore() == null || (invokeLQB && !creditScoreValid)) {
				if (!fetchCreditScore(loan)) {
					success = false;
				}
			}
		}

		LOGGER.debug("Check whether purchase document is about to expire");
		if (loan.getLoanType() != null) {
			String loanTypeMaster = loan.getLoanType().getLoanTypeCd();
			if (loanTypeMaster.equalsIgnoreCase(LoanTypeMasterEnum.PUR
			        .toString())) {
				if (checkPurchaseDocumentExpiry(loan)) {
					LOGGER.debug("Purchase Docuement Is About To Expire ");
					NotificationVO notificationVO = new NotificationVO(
					        loan.getId(),
					        MilestoneNotificationTypes.PURCHASE_DOCUMENT_EXPIRATION_NOTIFICATION_TYPE
					                .getNotificationTypeName(),
					        WorkflowConstants.PURCHASE_DOCUMENT_EXPIRY_NOTIFICATION_STATIC);
					List<NotificationVO> notificationVOList = notificationService
					        .findNotificationTypeListForLoan(
					                loan.getId(),
					                MilestoneNotificationTypes.PURCHASE_DOCUMENT_EXPIRATION_NOTIFICATION_TYPE
					                        .getNotificationTypeName(), false);
					if (notificationVOList.isEmpty()) {
						notificationVO = notificationService
						        .createNotification(notificationVO);
					}
				}
			}
		} else {
			LOGGER.error("No loan type master associated with this loan "
			        + loan.getId());
			nexeraUtility.putExceptionMasterIntoExecution(
			        exceptionMaster,
			        "No loan type master associated with this loan "
			                + loan.getId());
			nexeraUtility
			        .sendExceptionEmail("No loan type master associated with this loan "
			                + loan.getId());

		}

		LOGGER.debug("Calling Braintree Tansaction Related Classes");
		invokeBrainTree(loan);

		LOGGER.debug("Calling Milestones With Reminder ");
		invokeMilestonesWithReminder();

	}

	private Boolean checkPurchaseDocumentExpiry(Loan loan) {
		Boolean status = false;
		if (loan.getPurchaseDocumentExpiryDate() != null) {
			long expireDateInMilliseconds = loan
			        .getPurchaseDocumentExpiryDate();
			long currentDateInMilliseconds = new Date().getTime();
			long timeLeft = expireDateInMilliseconds
			        - currentDateInMilliseconds;
			if (TimeUnit.MILLISECONDS.toHours(timeLeft) <= 24) {
				status = true;
			} else {
				status = false;
			}
		}
		return status;

	}

	private List<String> getWorkflowItemTypeListBasedOnWorkflowItemMSInfo(
	        WorkItemMilestoneInfo wItemMSInfo) {
		List<String> workflowItemTypeList = null;
		if (wItemMSInfo != null) {
			workflowItemTypeList = wItemMSInfo.getWorkItems();
		}
		if (workflowItemTypeList == null) {
			workflowItemTypeList = new ArrayList<String>();
		}
		return workflowItemTypeList;
	}

	private WorkItemMilestoneInfo getWorkItemMilestoneInfoBasedOnLoanStatus(
	        LOSLoanStatus loanStatusID) {
		WorkItemMilestoneInfo wItemMSInfo = WorkflowConstants.LQB_STATUS_MILESTONE_LOOKUP
		        .get(loanStatusID);
		return wItemMSInfo;
	}

	private String formatAmount(String str) {
		String repl = str.replaceAll("(?<=\\d),(?=\\d)|\\$|\\%", "");
		return repl;
	}

	private void putItemsIntoExecution(List<WorkflowItemExec> itemsToExecute,
	        int currentLoanStatus) {
		for (WorkflowItemExec workflowItemExec : itemsToExecute) {
			if (!workflowItemExec.getStatus().equalsIgnoreCase(
			        WorkItemStatus.COMPLETED.getStatus())) {
				LOGGER.debug("Putting the item in execution ");
				String params = Utils.convertMapToJson(getParamsBasedOnStatus(
				        currentLoanStatus, workflowItemExec.getId()));
				workflowService.saveParamsInExecTable(workflowItemExec.getId(),
				        params);
				
				List<WorkflowItemExec> childWorkflowItemExecList = workflowService
				        .getWorkflowItemListByParentWorkflowExecItem(workflowItemExec);
				for(WorkflowItemExec workitem: childWorkflowItemExecList){
					String chilldParams = Utils.convertMapToJson(getParamsBasedOnStatus(
					        currentLoanStatus, workitem.getId()));
					workflowService.saveParamsInExecTable(workitem.getId(),
							chilldParams);
				}
				
				EngineTrigger engineTrigger = applicationContext
				        .getBean(EngineTrigger.class);
				engineTrigger.startWorkFlowItemExecution(workflowItemExec
				        .getId());
			}
		}
	}

	private LoanMilestoneMaster getLoanMilestoneMasterBasedOnMilestone(
	        Milestones milestones) {
		LoanMilestoneMaster loanMilestoneMaster = null;
		for (LoanMilestoneMaster loanMilestone : getLoanMilestoneMasterList()) {

			if (loanMilestone.getId() == milestones.getMilestoneID()) {
				LOGGER.debug("Found LoanMilestone Master ");
				loanMilestoneMaster = loanMilestone;
				break;

			}
		}
		return loanMilestoneMaster;
	}

	private void invokeMilestonesWithReminder() {
		for (WorkflowItemExec workflowItemExec : workflowItemExecList) {
			if (workflowItemExec.getRemind()) {
				LOGGER.debug("This milestone has a reminder associated with it ");
				if (!workflowItemExec.getStatus().equalsIgnoreCase(
				        WorkItemStatus.COMPLETED.getStatus())) {
					LOGGER.debug("Checking whether the milestone is already completed ");
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME,
					        loan.getId());
					map.put(WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME,
					        workflowItemExec.getId());
					EngineTrigger engineTrigger = applicationContext
					        .getBean(EngineTrigger.class);
					engineTrigger.invokeReminder(workflowItemExec.getId(), map);
				}
			}
		}
	}

	private void invokeBrainTree(Loan loan) {
		List<TransactionDetails> transactionDetailsList = getActiveTransactions(loan);
		for (TransactionDetails transactionDetails : transactionDetailsList) {
			LOGGER.debug("Invoking Braintree Service ");
			try {
				if(transactionDetails.getTransaction_id().equals("axisTransactionId")){
					LOGGER.debug("Axis Transaction has been successful");
					User sysAdmin = new User();
					sysAdmin.setId(CommonConstants.SYSTEM_ADMIN_ID);
					LoanApplicationFee applicationFee = new LoanApplicationFee();
					applicationFee.setLoan(transactionDetails.getLoan());
					applicationFee.setModifiedBy(sysAdmin);
					applicationFee.setModifiedDate(new Date(System.currentTimeMillis()));
					applicationFee.setTransactionDetails(transactionDetails);
					
					// Use the loan dao object to make a general save
					loanService.addLoanApplicationFee(applicationFee);

					// Update the transaction details table to soft delete the record.
					transactionDetails.setStatus(CommonConstants.TRANSACTION_STATUS_DISABLED);
					loanService.updateTransactionDetails(transactionDetails);	
					
					
					
					invokeApplicationFeeMilestone(LoanStatus.APP_PAYMENT_SUCCESS);
				}
				else if (braintreePaymentGatewayService.checkAndUpdateTransactions(
				        transactionDetails).equalsIgnoreCase(
				        LoanStatus.APP_PAYMENT_SUCCESS)) {
					LOGGER.debug("Transaction has been successful");
					invokeApplicationFeeMilestone(LoanStatus.APP_PAYMENT_SUCCESS);
				} else if (braintreePaymentGatewayService
				        .checkAndUpdateTransactions(transactionDetails)
				        .equalsIgnoreCase(LoanStatus.APP_PAYMENT_FAILURE)) {
					LOGGER.debug("Transaction has failed");
					invokeApplicationFeeMilestone(LoanStatus.APP_PAYMENT_FAILURE);
				}
			} catch (NoRecordsFetchedException | InvalidInputException e) {
				LOGGER.error("Exception received for this transaction "
				        + transactionDetails.getTransaction_id() + " "
				        + e.getMessage());
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        e.getMessage());
				nexeraUtility.sendExceptionEmail(e.getMessage());
			}
		}
	}

	private List<TransactionDetails> getActiveTransactions(Loan loan) {
		return transactionService.getActiveTransactionsByLoan(loan);
	}

	private Boolean fetchCreditScore(Loan loan) {
		LOGGER.debug("Inside method fetchCreditScore ");
		int format = 0;
		Boolean success = true;
		JSONObject creditScoreJSONObject = createCreditScoreJSONObject(
		        WebServiceOperations.OP_NAME_GET_CREDIT_SCORE,
		        loan.getLqbFileId(), format);
		LOGGER.debug("Invoking LQB service to fetch credit score  ");
		if (creditScoreJSONObject != null) {
			JSONObject creditScoreJSONResponse = lqbInvoker
			        .invokeLqbService(creditScoreJSONObject.toString());
			if (creditScoreJSONResponse != null) {
				if (!creditScoreJSONResponse
				        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {
					String creditScores = creditScoreJSONResponse
					        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
					List<CreditScoreResponseVO> creditScoreResponseList = parseCreditScoresResponse(creditScores);
					Map<String, String> creditScoreMap = nexeraUtility
					        .fillCreditScoresInMap(creditScoreResponseList);
					LOGGER.debug("Save Credit Score For This Borrower ");
					saveCreditScoresForBorrower(creditScoreMap);
					saveCreditScoresForCoBorrower(creditScoreMap);
					creditScoreMap.clear();
				}
			}
		} else {
			success = false;
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        "Request Json Object Not Created For Credit Score ");
			nexeraUtility
			        .sendExceptionEmail("Unable to create json object for credit score ");
		}
		return success;
	}

	private void invokeCreditScoreMilestone() {
		LOGGER.debug("Inside method invokeCreditScoreMilestone");
		String workflowItemType = WorkflowConstants.WORKFLOW_ITEM_CREDIT_SCORE;
		List<String> workflowItemTypeList = new ArrayList<>();
		workflowItemTypeList.add(workflowItemType);
		List<WorkflowItemExec> itemsToExecute = itemToExecute(
		        workflowItemTypeList, workflowItemExecList);
		for (WorkflowItemExec workflowItemExec : itemsToExecute) {
			if (!workflowItemExec.getStatus().equalsIgnoreCase(
			        WorkItemStatus.COMPLETED.getStatus())) {
				LOGGER.debug("Putting the item in execution ");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME, loan.getId());
				String params = Utils.convertMapToJson(map);
				workflowService.saveParamsInExecTable(workflowItemExec.getId(),
				        params);
				EngineTrigger engineTrigger = applicationContext
				        .getBean(EngineTrigger.class);
				engineTrigger.startWorkFlowItemExecution(workflowItemExec
				        .getId());
			}
		}

	}

	private void invokeApplicationFeeMilestone(String paymentStatus) {
		LOGGER.debug("Inside method invokeApplicationFeeMilestone");
		String workflowItemType = WorkflowConstants.WORKFLOW_ITEM_APP_FEE;
		List<String> workflowItemTypeList = new ArrayList<>();
		workflowItemTypeList.add(workflowItemType);
		List<WorkflowItemExec> itemsToExecute = itemToExecute(
		        workflowItemTypeList, workflowItemExecList);
		for (WorkflowItemExec workflowItemExec : itemsToExecute) {
			if (!workflowItemExec.getStatus().equalsIgnoreCase(
			        WorkItemStatus.COMPLETED.getStatus())) {
				LOGGER.debug("Putting the item in execution ");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME,
				        workflowItemExec.getId());
				map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
				        paymentStatus);
				map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME, loan.getId());
				String params = Utils.convertMapToJson(map);
				workflowService.saveParamsInExecTable(workflowItemExec.getId(),
				        params);
				EngineTrigger engineTrigger = applicationContext
				        .getBean(EngineTrigger.class);
				engineTrigger.startWorkFlowItemExecution(workflowItemExec
				        .getId());
			}
		}

	}

	private void saveCreditScoresForBorrower(Map<String, String> creditScoreMap) {
		LOGGER.debug("Inside method saveCreditScores ");
		CustomerDetail customerDetail = loan.getUser().getCustomerDetail();
		if (customerDetail != null) {
			if (creditScoreMap != null && !creditScoreMap.isEmpty()) {
				String borrowerEquifaxScore = creditScoreMap
				        .get(CoreCommonConstants.SOAP_XML_BORROWER_EQUIFAX_SCORE);
				customerDetail.setEquifaxScore(borrowerEquifaxScore);
				String borrowerExperianScore = creditScoreMap
				        .get(CoreCommonConstants.SOAP_XML_BORROWER_EXPERIAN_SCORE);
				customerDetail.setExperianScore(borrowerExperianScore);
				String borrowerTransunionScore = creditScoreMap
				        .get(CoreCommonConstants.SOAP_XML_BORROWER_TRANSUNION_SCORE);
				customerDetail.setTransunionScore(borrowerTransunionScore);

				LOGGER.debug("Updating customer details ");
				updateCustomerDetails(customerDetail);

				if ((borrowerEquifaxScore != null && !borrowerEquifaxScore
				        .equalsIgnoreCase(CommonConstants.DEFAULT_CREDIT_SCORE))
				        || (borrowerExperianScore != null && !borrowerExperianScore
				                .equalsIgnoreCase(CommonConstants.DEFAULT_CREDIT_SCORE))
				        || (borrowerTransunionScore != null && !borrowerTransunionScore
				                .equalsIgnoreCase(CommonConstants.DEFAULT_CREDIT_SCORE))) {
					invokeCreditScoreMilestone();
				}

			} else {
				LOGGER.error("Credit Scores Not Found For This Loan ");
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        "Credit Score Not Found for this loan ");
				nexeraUtility
				        .sendExceptionEmail("Credit score not found for this loan "
				                + loan.getId());
			}
		} else {
			LOGGER.error("Customer Details Not Found With this user id ");
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        "Customer details not found for this user id "
			                + loan.getUser().getId());
			nexeraUtility
			        .sendExceptionEmail("Customer Details Not Found for this user "
			                + loan.getUser().getFirstName()
			                + " "
			                + loan.getUser().getLastName());
		}
	}

	private void saveCreditScoresForCoBorrower(
	        Map<String, String> creditScoreMap) {

		LOGGER.debug("Inside method saveCreditScoresForCoBorrower ");
		CustomerSpouseDetail customerSpouseDetail = loan.getLoanAppForms()
		        .get(0).getCustomerspousedetail();
		if (customerSpouseDetail != null) {

			if (creditScoreMap != null && !creditScoreMap.isEmpty()) {
				String borrowerEquifaxScore = creditScoreMap
				        .get(CoreCommonConstants.SOAP_XML_CO_BORROWER_EQUIFAX_SCORE);
				if (borrowerEquifaxScore != null
				        && !borrowerEquifaxScore.equalsIgnoreCase("")) {
					customerSpouseDetail.setEquifaxScore(borrowerEquifaxScore);
				}
				String borrowerExperianScore = creditScoreMap
				        .get(CoreCommonConstants.SOAP_XML_CO_BORROWER_EXPERIAN_SCORE);
				if (borrowerExperianScore != null
				        && !borrowerExperianScore.equalsIgnoreCase("")) {
					customerSpouseDetail
					        .setExperianScore(borrowerExperianScore);
				}
				String borrowerTransunionScore = creditScoreMap
				        .get(CoreCommonConstants.SOAP_XML_CO_BORROWER_TRANSUNION_SCORE);
				if (borrowerTransunionScore != null
				        && !borrowerTransunionScore.equalsIgnoreCase("")) {
					customerSpouseDetail
					        .setTransunionScore(borrowerTransunionScore);
				}

				LOGGER.debug("Updating customer details ");
				updateCustomerSpouseScore(customerSpouseDetail);

			} else {
				LOGGER.error("Credit Scores Not Found For This Loan ");
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        "Credit Score Not Found for this loan ");
				nexeraUtility
				        .sendExceptionEmail("Spouse Credit Score Not Found for this loan "
				                + loan.getId());
			}
		} else {
			LOGGER.error("Customer Details Not Found With this user id ");
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        "Customer details not found for this user id "
			                + loan.getUser().getId());
			nexeraUtility
			        .sendExceptionEmail("Customer details not found for this user "
			                + loan.getUser().getFirstName()
			                + " "
			                + loan.getUser().getLastName());
		}

	}

	private void updateCustomerDetails(CustomerDetail customerDetail) {
		LOGGER.debug("Updating customer detail ");
		userProfileService.updateCustomerScore(customerDetail);
	}

	private void updateCustomerSpouseScore(
	        CustomerSpouseDetail customerSpouseDetail) {
		LOGGER.debug("Updating updateCustomerSpouseScore ");
		userProfileService.updateCustomerSpouseScore(customerSpouseDetail);
	}

	private void updateLastModifiedTimeForThisLoan(Loan loan) {
		LOGGER.debug("Inside method updateLastModifiedTimeForThisLoan ");
		loanService.updateLoanModifiedDate(loan.getId(), new Date());
	}

	private Boolean invokeUnderwritingCondition(Loan loan, int format) {
		LOGGER.debug("Invoking UnderwritingCondition service of lendinqb ");
		boolean success = true;
		JSONObject underWritingConditionJSONObject = createUnderWritingConditionJSONObject(
		        WebServiceOperations.OP_NAME_UNDERWRITING_CONDITION,
		        loan.getLqbFileId(), format);
		if (underWritingConditionJSONObject != null) {
			LOGGER.debug("Invoking LQB service to fetch underwriting condition ");
			JSONObject underWritingJSONResponse = lqbInvoker
			        .invokeLqbService(underWritingConditionJSONObject
			                .toString());
			if (underWritingJSONResponse != null) {
				if (!underWritingJSONResponse
				        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {
					String underwritingRespone = underWritingJSONResponse
					        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
					List<UnderwritingConditionResponseVO> underWritingResponseList = parseUnderwritingResponse(underwritingRespone
					        .toString());

					List<NeedsListMaster> conditionDescriptionList = new ArrayList<NeedsListMaster>();
					for (UnderwritingConditionResponseVO underwritingConditionResponseVO : underWritingResponseList) {
						if (underwritingConditionResponseVO
						        .getFieldId()
						        .equalsIgnoreCase(
						                CoreCommonConstants.SOAP_XML_UNDERWRITING_CONDITION_DESCRIPTION)) {
							NeedsListMaster needsListMaster = new NeedsListMaster();
							needsListMaster.setNeedCategory("System");
							needsListMaster
							        .setLabel(underwritingConditionResponseVO
							                .getFieldValue());
							needsListMaster
							        .setDescription(underwritingConditionResponseVO
							                .getFieldValue());
							needsListMaster.setIsCustom(false);
							conditionDescriptionList.add(needsListMaster);
						}
					}

					List<LoanNeedsList> needsList = needsListService
					        .saveMasterNeedsForLoan(loan.getId(),
					                conditionDescriptionList);
					if (!needsList.isEmpty()) {
						LOGGER.debug("Send mail because new needs list have been added ");
						try {
							loanApprovedWithConditionsEmail(loan,
							        conditionDescriptionList);
						} catch (InvalidInputException
						        | UndeliveredEmailException e) {
							nexeraUtility.putExceptionMasterIntoExecution(
							        exceptionMaster, e.getMessage());
							nexeraUtility.sendExceptionEmail(e.getMessage());
						}
					}
				} else {
					success = false;
					nexeraUtility.putExceptionMasterIntoExecution(
					        exceptionMaster, underWritingJSONResponse
					                .getString("errorDescription"));
					nexeraUtility.sendExceptionEmail(underWritingJSONResponse
					        .getString("errorDescription"));
				}
			} else {
				success = false;
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        "No resposne received from lqb/mule");
				nexeraUtility
				        .sendExceptionEmail("No resposne received from lqb/mule");
			}
		} else {
			success = false;
			nexeraUtility
			        .putExceptionMasterIntoExecution(exceptionMaster,
			                "Request Json Object Not Created For UnderwritingCondition ");
			nexeraUtility
			        .sendExceptionEmail("Unable to create request jsonobject for underwriting condition ");

		}
		return success;
	}

	private String getNeedsListNameById(
	        List<NeedsListMaster> needsListMasterList) {
		String needsName = "";
		int count = needsListMasterList.size();
		for (NeedsListMaster needsListMaster : needsListMasterList) {

			if (needsListMaster != null) {
				needsName = count + ". " + needsListMaster.getLabel() + " - "
				        + needsListMaster.getDescription() + "\n" + needsName;
				count = count - 1;
			}

		}

		return needsName;
	}

	private void loanApprovedWithConditionsEmail(Loan loan,
	        List<NeedsListMaster> needsList) throws InvalidInputException,
	        UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();

		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NEEDS_LIST_UPDATED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loan.getUser()
		        .getFirstName() });
		substitutions.put("-url-", new String[] { baseUrl });

		substitutions.put("-listofitems-",
		        new String[] { getNeedsListNameById(needsList) });
		if (loan.getUser() != null) {
			emailEntity.setSenderEmailId(loan.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("You Needs list has been updated");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();
		ccList.add(loan.getUser().getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);

		emailEntity.setCCList(ccList);
		try {
			sendEmailService.sendEmailForCustomer(emailEntity, loan.getId(),
			        template);
		} catch (InvalidInputException | UndeliveredEmailException e) {
			LOGGER.error("Exception caught while sending email "
			        + e.getMessage());
		}

	}

	private Boolean fetchDocsForThisLoan(Loan loan) {

		LOGGER.debug("Invoking ListEdocsByLoanNumber service of lendinqb ");
		Boolean success = true;
		Date timeBeforeCallMade = new Date();
		JSONObject getListOfDocs = createListEDocsByLoanNumberObject(
		        WebServiceOperations.OP_NAME_LIST_EDCOS_BY_LOAN_NUMBER,
		        nexeraUtility.asciiTrim(loan.getLqbFileId()));
		if (getListOfDocs != null) {
			JSONObject receivedResponseForDocs = lqbInvoker
			        .invokeLqbService(getListOfDocs.toString());
			if (receivedResponseForDocs != null) {
				if (!receivedResponseForDocs
				        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {
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

								checkWhetherDisclosureReceived(loan, edocsList);
							}
						}
					}
				} else {
					success = false;
					nexeraUtility.putExceptionMasterIntoExecution(
					        exceptionMaster, receivedResponseForDocs
					                .getString("errorDescription"));
					nexeraUtility.sendExceptionEmail(receivedResponseForDocs
					        .getString("errorDescription"));
				}
			} else {
				success = false;
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        "No resposne received from lqb/mule");
				nexeraUtility
				        .sendExceptionEmail("No resposne received from lqb/mule");
			}
		} else {
			success = false;
			nexeraUtility
			        .putExceptionMasterIntoExecution(exceptionMaster,
			                "Request Json Object Not Created For List EDocsByLoanNumber ");
			nexeraUtility
			        .sendExceptionEmail("Unable to create request json object for ListEDocsByLoanNumber for this loan id "
			                + loan.getId());

		}
		return success;
	}

	private void checkWhetherDisclosureReceived(Loan loan,
	        List<LQBedocVO> edocsList) {
		LOGGER.debug("Inside method checkWhetherDisclosuredReceived");
		for (LQBedocVO edoc : edocsList) {

			String folderName = edoc.getFolder_name();
			if (folderName
			        .equalsIgnoreCase(CoreCommonConstants.FOLDER_NAME_INITIAL_DISCLOSURES)) {
				LOGGER.debug("Disclosure has been received ");

				NeedsListMaster needsListMasterDisclosureAvailable = getNeedsListMasterByType(CoreCommonConstants.SYSTEM_GENERATED_NEED_MASTER_DISCLOSURES_AVAILABILE);
				if (needsListMasterDisclosureAvailable != null) {
					if (!checkIfAlreadAssigned(loan,
					        needsListMasterDisclosureAvailable)) {
						assignNeedToLoan(loan,
						        needsListMasterDisclosureAvailable);
					}
				}
				NeedsListMaster needsListMasterDisclosureSigned = getNeedsListMasterByType(CoreCommonConstants.SYSTEM_GENERATED_NEED_MASTER_DISCLOSURES_SIGNED);
				if (needsListMasterDisclosureSigned != null) {
					if (!checkIfAlreadAssigned(loan,
					        needsListMasterDisclosureSigned)) {
						assignNeedToLoan(loan, needsListMasterDisclosureSigned);
						LOGGER.debug("Invoking Disclosure MileStone Classes ");
						updateLastModifiedTimeForThisLoan(loan);
					}
				}
			}
		}
	}

	private void assignNeedToLoan(Loan loan, NeedsListMaster needsListMaster) {
		LOGGER.debug("Found a Need, Assigning it to a loan ");
		LoanNeedsList loanNeedsList = new LoanNeedsList();
		loanNeedsList.setLoan(loan);
		loanNeedsList.setNeedsListMaster(needsListMaster);

		loanNeedsList.setMandatory(false);
		loanNeedsList.setSystemAction(true);
		loanService.assignNeedsToLoan(loanNeedsList);

	}

	private Boolean checkIfAlreadAssigned(Loan loan,
	        NeedsListMaster needsListMaster) {
		LOGGER.debug("Check whether loan needs list exist for this document ");
		if (loanService.findLoanNeedsList(loan, needsListMaster) != null)
			return true;
		else
			return false;
	}

	private NeedsListMaster getNeedsListMasterByType(String needsListType) {
		LOGGER.debug("Inside method getNeedsListMasterByType ");
		return needsListService.fetchNeedListMasterByType(needsListType);
	}

	private void updateLoanMilestone(LoanMilestone loanMilestone) {
		loanService.updateLoanMilestone(loanMilestone);
	}

	private void putLoanMilestoneIntoExecution(LOSLoanStatus loanStatus,
	        int currentLoanStatus, List<LoadResponseVO> loadResponseList,
	        LoanMilestoneMaster loanMilestoneMaster) {
		if (!WorkflowConstants.LQB_MONITOR_LIST.contains(loanStatus
		        .getLosStatusID())) {
			LOGGER.debug("This is NOT a Status that newfi is tracking"
			        + loanStatus.getLosStatusID() + ":" + loanStatus);
			return;
		}

		LoanMilestone loanMilestone = new LoanMilestone();
		Date date = null;
		if (loadResponseList != null) {
			String dateTime = getDataTimeField(currentLoanStatus,
			        loadResponseList);
			if (dateTime != null && !dateTime.equals(""))
				date = parseStringIntoDate(dateTime);
		}
		loanMilestone.setLoan(loan);
		loanMilestone.setLoanMilestoneMaster(loanMilestoneMaster);
		loanMilestone.setStatusUpdateTime(date);
		loanMilestone.setStatus(String.valueOf(currentLoanStatus));
		loanMilestone.setComments(loanStatus.getDisplayStatus());
		loanMilestone.setOrder(loanStatus.getOrder());
		loanMilestone.setStatusInsertTime(new Date());
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
	        List<LoanMilestoneMaster> loanMileStoneMasterList) {
		List<LoanMilestone> loanMilestoneList = loan.getLoanMilestones();
		int currentIndex = statusTrackingList.indexOf(currentStatus);
		LOGGER.debug("Checking if previous state has an entry ");
		if (currentIndex != 0) {
			int trackStatus = 0;
			int previousStatus = statusTrackingList.get(currentIndex - 1);
			LOSLoanStatus loanStatusID = LOSLoanStatus
			        .getLOSStatus(previousStatus);
			for (LoanMilestone loanMilestone : loanMilestoneList) {
				if (loanMilestone.getStatus() == null) {
					continue;
				}
				if (Integer.valueOf(loanMilestone.getStatus()) == previousStatus) {
					LOGGER.debug("No status has been missed hence breaking out of the loop");
					trackStatus = trackStatus + 1;
					break;
				}
			}
			if (trackStatus == 0) {
				Milestones milestones = WorkflowConstants.LQB_STATUS_MILESTONE_LOOKUP
				        .get(loanStatusID).getMilestone();
				LoanMilestoneMaster loanMilestoneMaster = getLoanMilestoneMasterBasedOnMilestone(milestones);
				putLoanMilestoneIntoExecution(loanStatusID, previousStatus,
				        loadResponseVOList, loanMilestoneMaster);

				LOGGER.debug("Invoking the milestone concrete classes for the missed status "
				        + previousStatus);
				WorkItemMilestoneInfo wItemMSInfo = getWorkItemMilestoneInfoBasedOnLoanStatus(loanStatusID);
				List<String> workflowItemTypeList = getWorkflowItemTypeListBasedOnWorkflowItemMSInfo(wItemMSInfo);
				List<WorkflowItemExec> itemsToExecute = itemToExecute(
				        workflowItemTypeList, workflowItemExecList);
				putItemsIntoExecution(itemsToExecute, previousStatus);
				LOGGER.debug("Recursively calling to see if multiple status has been missed ");
				checkIfAnyStatusIsMissed(previousStatus, statusTrackingList,
				        loadResponseVOList, getLoanMilestoneMasterList());

			}

		}
		return 0;
	}

	public Date parseStringIntoDate(String dateTime) {
		Date date = null;
		for (SimpleDateFormat simpleDateFormat : fillSimpleDateFormatList()) {
			try {
				date = simpleDateFormat.parse(dateTime);
				if (date != null)
					break;
			} catch (ParseException pe) {
				LOGGER.error("This format not supported");
			}
		}
		if (date == null) {
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        "No supported date format found");
			nexeraUtility.sendExceptionEmail("No supported date format found");
		}
		return date;

	}

	private List<SimpleDateFormat> fillSimpleDateFormatList() {
		List<SimpleDateFormat> simpleDateFormatList = new ArrayList<>();

		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(dateFormat1);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(dateFormat2);
		SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(dateFormat3);
		SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(dateFormat4);
		simpleDateFormatList.add(simpleDateFormat1);
		simpleDateFormatList.add(simpleDateFormat2);
		simpleDateFormatList.add(simpleDateFormat3);
		simpleDateFormatList.add(simpleDateFormat4);
		return simpleDateFormatList;
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

	public JSONObject createUnderWritingConditionJSONObject(String opName,
	        String lqbLoanId, int format) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_FORMAT, format);

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

	public JSONObject createCreditScoreJSONObject(String opName,
	        String lqbLoanId, int format) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_FORMAT, format);

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

	private Map<String, Object> getParamsBasedOnStatus(int currentLoanStatus,
	        int workflowItemExecId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME, loan.getId());
		map.put(WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME,
		        workflowItemExecId);
		if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUBMITTED) {
			map.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        "08986e4b-8407-4b44-9000-50c104db899c");
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_IN_UNDERWRITING) {

			map.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        "08986e4b-8407-4b44-9000-50c104db899c");
		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_CLEAR_TO_CLOSE) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_FUNDED) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_SUSPENDED) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_DENIED) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_WITHDRAWN) {

		} else if (currentLoanStatus == LoadConstants.LQB_STATUS_LOAN_ARCHIVED) {

		}

		map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
		        currentLoanStatus);

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
		String dateTime = "";
		for (LoadResponseVO loadResponseVO : loadResponseVOList) {
			if (loadResponseVO.getFieldId().equalsIgnoreCase(dateField)) {
				date = loadResponseVO.getFieldValue();
			}
			if (loadResponseVO.getFieldId().equalsIgnoreCase(timeField)) {
				time = loadResponseVO.getFieldValue();
			}
		}

		if (time != null && !time.equals("")) {
			dateTime = time;
			if (date != null && !date.equals("")) {
				dateTime = time.concat("-").concat(date);
			}
		} else {
			if (date != null && !date.equals("")) {
				dateTime = date;
			} else {
				dateTime = null;
			}
		}

		return dateTime;
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
			nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
			        e.getMessage());
			nexeraUtility.sendExceptionEmail(e.getMessage());
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

	private List<UnderwritingConditionResponseVO> parseUnderwritingResponse(
	        String underWritingResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			UnderwritingXMLHandler handler = new UnderwritingXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(underWritingResponse)),
			        handler);
			return handler.getUnderWriConditionResponseList();

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

	private List<CreditScoreResponseVO> parseCreditScoresResponse(
	        String creditScoreResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			CreditScoreXMLHandler handler = new CreditScoreXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(creditScoreResponse)),
			        handler);
			return handler.getCreditScoreResponseList();

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

	public ExceptionMaster getExceptionMaster() {
		return exceptionMaster;
	}

	public void setExceptionMaster(ExceptionMaster exceptionMaster) {
		this.exceptionMaster = exceptionMaster;
	}

	public boolean isInvokeLQB() {
		return invokeLQB;
	}

	public void setInvokeLQB(boolean invokeLQB) {
		this.invokeLQB = invokeLQB;
	}

}