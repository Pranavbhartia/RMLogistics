package com.nexera.newfi.workflow.customer.tasks;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.workflow.enums.Milestones;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class DisclosuresDisplayManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;

	@Autowired
	private NeedsListService needsListService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> objectMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Loan loan = new Loan();
		loan.setId(Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		LoanMilestone loanMilestone = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.DISCLOSURE.getMilestoneKey());
		String status = loanMilestone.getComments().toString();
		// TODO check Status Mapping
		// String DisplayStat="";
		if (status.equals(LoanStatus.disclosureAvail)
		        || status.equals(LoanStatus.disclosureViewed)
		        || status.equals(LoanStatus.disclosureSigned)) {
			NeedsListMaster needsListMaster = new NeedsListMaster();
			needsListMaster
			        .setId(Integer
			                .parseInt(MasterNeedsEnum.System_Disclosure_Need
			                        .getIndx()));
			LoanNeedsList loanNeedsList = needsListService.findNeedForLoan(
			        loan, needsListMaster);
			map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME, status);
			// TODO check column path
			map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY, loanNeedsList
			        .getUploadFileId().getS3path());

		} else {
			map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME, status);
		}
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sw.toString();
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
