package com.nexera.core.manager;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.server.LoaderHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.exception.FatalException;
import com.nexera.common.vo.lqb.LoadResponseVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.utility.LoadXMLHandler;
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

		/*
		 * 1. We get the loan status from LQB. DONE
		 * 
		 * Once the status is retrieved, these are teh set of tasks to be done:
		 * 
		 * a. Loan milestone master contains all the status that is needed in
		 * the newFi System, essentially these are the 11 boxes we are showing
		 * in LM view b. loanmilestone table contains the current status and the
		 * status history of a particular loan.
		 * 
		 * We will get the LQB status, this needs to be mapped to the loan
		 * milestone master table based on Hina's sheet For every status and a
		 * value, there needs to be mapping in the loanmilestone table to be
		 * populated in the comments column.
		 * 
		 * 
		 * Once the status updates are done. We need to invoke the WFItem. Look
		 * up the WFIM, for the given status change what is the WFExecId,
		 */

		LOGGER.debug("Inside method run ");

		// TODO remove this hardcoded value
		Map<String, String> map = new HashMap<String, String>();
		map.put("aBSsn", "4084311052");
		map.put("aCSsn", "4084311052");
		int format = 0;

		LOGGER.debug("Invoking load service of lendinqb ");
		JSONObject loadOperationObject = createLoadJsonObject(map,
		        WebServiceOperations.OP_NAME_LOAN_LOAD, loan.getLqbFileId(),
		        format);
		JSONObject loadJSONResponse = lqbInvoker
		        .invokeLqbService(loadOperationObject.toString());

		String loadResponse = loadJSONResponse.getString("responseMessage");
		loadResponse = removeBackSlashDelimiter(loadResponse);

		List<LoadResponseVO> loadResponseList = parseLqbResponse(loadResponse);
		for (LoadResponseVO loadResponseVO : loadResponseList) {
			String fieldId = loadResponseVO.getFieldId();
			String fieldValue = loadResponseVO.getFieldValue();
		}
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

	public String removeBackSlashDelimiter(String string) {
		if (string != null) {
			string = string.replace("\\", "");
		}
		return string;
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

}
