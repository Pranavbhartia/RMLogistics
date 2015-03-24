package com.nexera.core.manager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.exception.FatalException;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.utility.LoanStatusMaster;

@Component
@Scope(value = "prototype")
public class ThreadManager implements Runnable {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(ThreadManager.class);

	private Loan loan;

	@Autowired
	LqbInvoker lqbInvoker;

	@Override
	public void run() {
		LOGGER.debug("Inside method run ");
		Map map = new HashMap<String, String>();
		// TODO Remove this line
		loan.setLqbFileId("D2015030087");
		int format = 0;

		LOGGER.debug("Invoking load service of lendinqb ");
		JSONObject loadOperationObject = createLoadJsonObject(map,
		        WebServiceOperations.OP_NAME_LOAN_LOAD, loan.getLqbFileId(),
		        format);
		JSONObject receivedResponse = lqbInvoker
		        .invokeLqbService(loadOperationObject.toString());
		// TODO Need to parse the received response
		LOGGER.debug("Invoking listDocsByLoanNumber service of lendinqb ");
		JSONObject getListOfDocs = createListEDocsByLoanNumberObject(
		        WebServiceOperations.OP_NAME_LIST_EDCOS_BY_LOAN_NUMBER,
		        loan.getLqbFileId());

		JSONObject receivedResponseForDocs = lqbInvoker
		        .invokeLqbService(getListOfDocs.toString());
		LoanProgressStatusMaster loanProgressStatusMaster = loan
		        .getLoanProgressStatus();
		if (loanProgressStatusMaster.getLoanProgressStatus().equalsIgnoreCase(
		        LoanStatusMaster.STATUS_NEW_LOAN)) {
			// TODO Invoke a lqb service
		} else if (loanProgressStatusMaster.getLoanProgressStatus()
		        .equalsIgnoreCase(LoanStatusMaster.STATUS_NEW_PROJECT)) {
			// TODO Invoke a lqb service
		} else if (loanProgressStatusMaster.getLoanProgressStatus()
		        .equalsIgnoreCase(LoanStatusMaster.STATUS_LEAD)) {
			// TODO Invoke a lqb service
		} else if (loanProgressStatusMaster.getLoanProgressStatus()
		        .equalsIgnoreCase(LoanStatusMaster.STATUS_IN_PROGRESS)) {
			// TODO Invoke a lqb service
		}

	}

	public JSONObject createLoadJsonObject(Map<String, String> requestXMLMap,
	        String opName, String lqbLoanId, int format) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_FORMAT, format);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_XML_DATA_MAP,
			        requestXMLMap);
			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
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

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

}
