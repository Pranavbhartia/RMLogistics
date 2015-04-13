/**
 * 
 */
package com.nexera.common.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.WorkItemMilestoneInfo;

/**
 * @author Utsav T
 */
public class WorkflowConstants {
	public static final String PROPERTY_FILE_NAME = "application";

	public static final String POOL_SIZE = "pool.size";
	public static final String RENDER_STATE_INFO_METHOD = "renderStateInfo";

	public static final String CHECK_STATUS_METHOD = "checkStatus";

	public static final String INVOKE_ACTION_METHOD = "invokeAction";

	public static final String EXECUTE_METHOD = "execute";

	public static final String LOAN_MANAGER_WORKFLOW_TYPE = "LM_WF_ALL";

	public static final String CUSTOMER_WORKFLOW_TYPE = "CUST_WF_ALL";

	public static final String WORKFLOW_ITEM_INITIAL_CONTACT = "INITIAL_CONTACT";
	public static final String WORKFLOW_ITEM_UW_STATUS = "UW_STATUS";
	public static final String WORKFLOW_ITEM_VIEW_UW = "VIEW_UW";
	public static final String WORKFLOW_ITEM_MANAGE_TEAM = "MANAGE_TEAM";

	public static final String WORKFLOW_ITEM_TEAM_STATUS = "TEAM_STATUS";
	public static final String WORKFLOW_ITEM_SYSTEM_EDU = "SYSTEM_EDU";
	public static final String WORKFLOW_ITEM_RATES_EDU = "RATES_EDU";
	public static final String WORKFLOW_ITEM_QC_STATUS = "QC_STATUS";
	public static final String WORKFLOW_ITEM_PROFILE_INFO = "PROFILE_INFO";
	public static final String WORKFLOW_ITEM_NEEDS_STATUS = "NEEDS_STATUS";
	public static final String WORKFLOW_ITEM_NEEDS_EDU = "NEEDS_EDU";
	public static final String WORKFLOW_ITEM_LOCK_RATE = "LOCK_RATE";
	public static final String WORKFLOW_ITEM_LOAN_PROGRESS = "LOAN_PROGRESS";
	public static final String WORKFLOW_ITEM_DISCLOSURE_STATUS = "DISCLOSURE_STATUS";
	public static final String WORKFLOW_ITEM_DISCLOSURE_DISPLAY = "DISCLOSURE_DISPLAY";
	public static final String WORKFLOW_ITEM_CREDIT_SCORE = "CREDIT_SCORE";
	public static final String WORKFLOW_ITEM_CREDIT_BUREAU = "CREDIT_BUREAU";
	public static final String WORKFLOW_ITEM_COMM_EDU = "COMM_EDU";
	public static final String WORKFLOW_ITEM_CLOSURE_STATUS = "CLOSURE_STATUS";
	public static final String WORKFLOW_ITEM_CLOSURE_DISPLAY = "VIEW_CLOSING";
	public static final String WORKFLOW_ITEM_AUS_STATUS = "AUS_STATUS";

	public static final String WORKFLOW_ITEM_APPRAISAL_STATUS = "APPRAISAL_STATUS";
	public static final String WORKFLOW_ITEM_VIEW_APPRAISAL = "VIEW_APPRAISAL";

	public static final String WORKFLOW_ITEM_APP_FEE = "APP_FEE";
	public static final String WORKFLOW_ITEM_APP_EDU = "APP_EDU";

	public static final String WORKFLOW_ITEM_1003_COMPLETE = "1003_COMPLETE";
	public static final String WORKFLOW_ITEM_1003_DISPLAY = "1003_DISPLAY";

	public static final String WORKFLOW_CUST_ITEM_NEEDS_STATUS = "VIEW_NEEDS";
	public static final HashMap<LOSLoanStatus, WorkItemMilestoneInfo> LQB_STATUS_MILESTONE_LOOKUP = new HashMap<LOSLoanStatus, WorkItemMilestoneInfo>();;

	private static final HashMap<Milestones, List<String>> MILESTONE_WF_ITEM_LOOKUP = new HashMap<Milestones, List<String>>();;
	private static final HashMap<LOSLoanStatus, List<Integer>> MILESTONE_ORDER_LOOKUP = new HashMap<LOSLoanStatus, List<Integer>>();;

	private static final List<Integer> LQB_MONITOR_LIST = new ArrayList<Integer>(
	        Arrays.asList(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
	                .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING
	                        .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_FUNDED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_DENIED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN
	                        .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED.getLosStatusID()));
	private static final List<Integer> UW_LIST = new ArrayList<Integer>(
	        Arrays.asList(LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK
	                .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK_FAILED
	                        .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_APPROVED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_CONDITION_REVIEW.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_FINAL_UNDER_WRITING
	                        .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_FINAL_DOCS.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE.getLosStatusID()));
	private static final List<Integer> APPRAISAL_LIST = new ArrayList<Integer>(
	        Arrays.asList(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED
	                .getLosStatusID(), LOSLoanStatus.LQB_STATUS_DOCS_DRAWN
	                .getLosStatusID(), LOSLoanStatus.LQB_STATUS_DOCS_OUT
	                .getLosStatusID(), LOSLoanStatus.LQB_STATUS_DOCS_BACK
	                .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW
	                        .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_IN_PURCHASE_REVIEW
	                        .getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_PRE_PURCHASE_CONDITIONS
	                        .getLosStatusID()));
	private static final List<Integer> QC_LIST = new ArrayList<Integer>(
	        Arrays.asList(LOSLoanStatus.LQB_STATUS_PRE_DOC_QC.getLosStatusID(),

	        LOSLoanStatus.LQB_STATUS_CLEAR_TO_PURCHASE.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_PURCHASED.getLosStatusID()));

	private static final List<Integer> LOAN_CLOSURE_LIST = new ArrayList<Integer>(
	        Arrays.asList(LOSLoanStatus.LQB_STATUS_FUNDED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_DENIED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED.getLosStatusID(),
	                LOSLoanStatus.LQB_STATUS_LOAN_CLOSED.getLosStatusID()));
	private static final List<Integer> APP_1003_LIST = new ArrayList<Integer>(
	        Arrays.asList(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
	                .getLosStatusID()));

	public static final int SYSTEM_USER = 1;

	public static final String SYS_EDU_NOTIFICATION_CONTENT = "System Education Pending";

	// TODO DYNAMIC content
	public static final String DISCLOSURE_AVAIL_NOTIFICATION_CONTENT = "Disclosures are due in {\"72\":\"elapsed\"} hrs.";
	public static final String AGENT_ADD_NOTIFICATION_CONTENT = "Do you have an agent? Please add him to NewFi Team.";
	public static final String CREDIT_SCORE_NOTIFICATION_CONTENT = "Credit Score Pending";

	public static final String NEEDED_ITEMS_NOTIFICATION_CONTENT = "INitial needs list not created";
	public static final String LOCK_RATE__NOTIFICATION_CONTENT = "Rates not locked";
	public static final String APP_FEE__NOTIFICATION_CONTENT = "Fee pending";
	public static final String APP_FEE_OVERDUE_NOTIFICATION_CONTENT = "Fee pending";
	public static final String APPRAISAL_NOTIFICATION__NOTIFICATION_CONTENT = "Appraisals not available";
	public static final String UW_NOTIFICATION__NOTIFICATION_CONTENT = "Underwriting pending";
	public static final String LOCK_RATE_CUST_NOTIFICATION_CONTENT = "Lock your rate";
	static {

		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.App1003,
		        new ArrayList<String>(Arrays
		                .asList(WORKFLOW_ITEM_1003_COMPLETE,
		                        WORKFLOW_ITEM_1003_DISPLAY)));

		MILESTONE_WF_ITEM_LOOKUP.put(Milestones.AUSUW, null);

		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.DISCLOSURE,
		        new ArrayList<String>(Arrays.asList(
		                WORKFLOW_ITEM_DISCLOSURE_STATUS,
		                WORKFLOW_ITEM_DISCLOSURE_DISPLAY)));

		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.APPRAISAL,
		        new ArrayList<String>(Arrays.asList(
		                WORKFLOW_ITEM_APPRAISAL_STATUS,
		                WORKFLOW_ITEM_VIEW_APPRAISAL)));
		MILESTONE_WF_ITEM_LOOKUP.put(Milestones.UW, new ArrayList<String>(
		        Arrays.asList(WORKFLOW_ITEM_UW_STATUS, WORKFLOW_ITEM_VIEW_UW)));
		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.LOAN_CLOSURE,
		        new ArrayList<String>(Arrays.asList(
		                WORKFLOW_ITEM_CLOSURE_STATUS,
		                WORKFLOW_ITEM_CLOSURE_DISPLAY)));

		// Now the order mapping being filled

		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK,
		        UW_LIST);

		MILESTONE_ORDER_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK_FAILED, UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING,
		        UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING,
		        UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_PRE_APPROVED,
		        UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_APPROVED, UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_CONDITION_REVIEW,
		        UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_FINAL_UNDER_WRITING, UW_LIST);
		MILESTONE_ORDER_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_FINAL_DOCS, UW_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE,
		        UW_LIST);
		// Apparisal
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED,
		        APPRAISAL_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_DRAWN,
		        APPRAISAL_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_OUT,
		        APPRAISAL_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_BACK,
		        APPRAISAL_LIST);
		MILESTONE_ORDER_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW,
		        APPRAISAL_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_IN_PURCHASE_REVIEW,
		        APPRAISAL_LIST);

		MILESTONE_ORDER_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_PURCHASE_CONDITIONS,
		        APPRAISAL_LIST);

		// QC
		MILESTONE_ORDER_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_PRE_DOC_QC, QC_LIST);

		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_CLEAR_TO_PURCHASE,
		        QC_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_PURCHASED,
		        QC_LIST);
		// Loan Closure
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED,
		        LOAN_CLOSURE_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_DENIED,
		        LOAN_CLOSURE_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN,
		        LOAN_CLOSURE_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED,
		        LOAN_CLOSURE_LIST);

		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_CLOSED,
		        LOAN_CLOSURE_LIST);
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_FUNDED,
		        LOAN_CLOSURE_LIST);

		// 1003
		MILESTONE_ORDER_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED,
		        APP_1003_LIST);
		// End of Order Mapping

		// Now the objects
		// THOSE that are needed now
		// THese are the ones that change NewFI WF Items

		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING,
		                new WorkItemMilestoneInfo(
		                        Milestones.UW,
		                        MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW),
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED,
		        new WorkItemMilestoneInfo(Milestones.App1003,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.App1003),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_FUNDED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_FUNDED)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_DENIED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_DENIED)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE),
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED)));
		// End of needed list

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK,
		        new WorkItemMilestoneInfo(Milestones.UW, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK_FAILED,
		                new WorkItemMilestoneInfo(
		                        Milestones.UW,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK_FAILED)));

		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING,
		                new WorkItemMilestoneInfo(
		                        Milestones.UW,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING,
		                new WorkItemMilestoneInfo(
		                        Milestones.UW,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_APPROVED,
		        new WorkItemMilestoneInfo(Milestones.UW, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_APPROVED)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_CONDITION_REVIEW,
		                new WorkItemMilestoneInfo(
		                        Milestones.UW,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_CONDITION_REVIEW)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_FINAL_UNDER_WRITING,
		                new WorkItemMilestoneInfo(
		                        Milestones.UW,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_FINAL_UNDER_WRITING)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_FINAL_DOCS,
		        new WorkItemMilestoneInfo(Milestones.UW, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_FINAL_DOCS)));

		// For Appraisal

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCS_ORDERED,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCS_DRAWN,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_DOCS_DRAWN)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCS_OUT,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_DOCS_OUT)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCS_BACK,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_DOCS_BACK)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW,
		                new WorkItemMilestoneInfo(
		                        Milestones.APPRAISAL,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_IN_PURCHASE_REVIEW,
		                new WorkItemMilestoneInfo(
		                        Milestones.APPRAISAL,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_IN_PURCHASE_REVIEW)));
		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_PRE_PURCHASE_CONDITIONS,
		                new WorkItemMilestoneInfo(
		                        Milestones.APPRAISAL,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_PRE_PURCHASE_CONDITIONS)));
		// QC

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_DOC_QC,
		        new WorkItemMilestoneInfo(Milestones.QC, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_PRE_DOC_QC)));

		LQB_STATUS_MILESTONE_LOOKUP
		        .put(LOSLoanStatus.LQB_STATUS_CLEAR_TO_PURCHASE,
		                new WorkItemMilestoneInfo(
		                        Milestones.QC,
		                        null,
		                        MILESTONE_ORDER_LOOKUP
		                                .get(LOSLoanStatus.LQB_STATUS_CLEAR_TO_PURCHASE)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_PURCHASED,
		        new WorkItemMilestoneInfo(Milestones.QC, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_PURCHASED)));

		// Loan Closure

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_CLOSED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_CLOSED)));

		// IGNORE LIST
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LEAD_NEW,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LEAD_NEW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LEAD_CANCELED,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LEAD_CANCELED)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LEAD_DECLINED,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LEAD_DECLINED)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LEAD_OTHER,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LEAD_OTHER)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_OPEN,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_LOAN_OPEN)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_QUAL,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_PRE_QUAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_REGISTERED,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_REGISTERED)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_PROCESSING,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_PRE_PROCESSING)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PROCESSING,
		        new WorkItemMilestoneInfo(Milestones.OTHER, null,
		                MILESTONE_ORDER_LOOKUP
		                        .get(LOSLoanStatus.LQB_STATUS_PROCESSING)));

	}

}