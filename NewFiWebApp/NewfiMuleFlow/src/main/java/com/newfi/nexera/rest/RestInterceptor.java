/**
 * 
 */
package com.newfi.nexera.rest;

import java.io.File;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.newfi.nexera.bean.RestParameters;
import com.newfi.nexera.constants.NewFiConstants;
import com.newfi.nexera.constants.WebServiceOperations;
import com.newfi.nexera.manager.NewFiManager;
import com.newfi.nexera.process.XMLProcessor;
import com.nexera.util.Utils;

/**
 * @author Utsav
 *
 */
public class RestInterceptor implements Callable {

	private static final Logger LOG = Logger.getLogger(RestInterceptor.class);

	private XMLProcessor xmlProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		LOG.debug("Inside method onCall ");
		MuleMessage message = eventContext.getMessage();
		String payload = message.getPayloadAsString();
		Gson gson = new Gson();
		RestParameters restParameters = gson.fromJson(payload,
		        RestParameters.class);

		if (restParameters.getOpName().equalsIgnoreCase(
		        WebServiceOperations.OP_NAME_GET_CREDIT_SCORE)
		        || restParameters
		                .getOpName()
		                .equalsIgnoreCase(
		                        WebServiceOperations.OP_NAME_GET_UNDERWRITING_CONDITION)
		        || restParameters.getOpName().equalsIgnoreCase(
		                WebServiceOperations.OP_NAME_LOAN_BATCH_LOAD)) {
			message.setOutboundProperty(NewFiConstants.CONSTANT_OP_NAME,
			        WebServiceOperations.OP_NAME_LOAN_LOAD);
		} else if (restParameters.getOpName().equalsIgnoreCase(
		        WebServiceOperations.OP_NAME_SAVE_CREDIT_SCORE)) {
			message.setOutboundProperty(NewFiConstants.CONSTANT_OP_NAME,
			        WebServiceOperations.OP_NAME_LOAN_SAVE);
		} else {
			message.setOutboundProperty(NewFiConstants.CONSTANT_OP_NAME,
			        restParameters.getOpName());
		}
		if (restParameters.getLoanVO().getsTicket() == null
		        || restParameters.getLoanVO().getsTicket().equalsIgnoreCase("")) {

			if (NewFiManager.userTicket == null) {
				LOG.debug("Getting the user ticket based on the username and password ");
				NewFiManager.userTicket = Utils.getUserTicket(
				        "Nexera_RareMile", "Portal0262");
				if (NewFiManager.userTicket == null) {
					LOG.debug("Valid ticket was not generated hence trying again ");
					NewFiManager.userTicket = Utils.getUserTicket(
					        "Nexera_RareMile", "Portal0262");
				}
				NewFiManager.generationTime = System.currentTimeMillis();
			} else {
				long generationTime = NewFiManager.generationTime;
				long currentTime = System.currentTimeMillis();
				long differenceInMilliSeconds = currentTime - generationTime;
				long differenceInHours = differenceInMilliSeconds
				        / (60 * 60 * 1000);
				if (differenceInHours >= 3) {
					LOG.debug("Ticket would have expired as time difference has gone beyond 4 hours ");
					NewFiManager.userTicket = Utils.getUserTicket(
					        "Nexera_RareMile", "Portal0262");
					if (NewFiManager.userTicket == null) {
						LOG.debug("Valid ticket was not generated hence trying again ");
						NewFiManager.userTicket = Utils.getUserTicket(
						        "Nexera_RareMile", "Portal0262");
					}

				}
			}
			LOG.info("Ticket generated " + NewFiManager.userTicket);
		} else {
			LOG.info(" This ticket is specific to a loan manager "
			        + restParameters.getLoanVO().getsTicket());
		}

		LOG.info("Operation chosen by user " + restParameters.getOpName());

		LOG.info("Parameters passed by user " + restParameters.getLoanVO());

		Object[] inputParameters = getAllParameters(restParameters);
		message.setPayload(inputParameters);
		return message;
	}

	public Object[] getAllParameters(RestParameters restParameters)
	        throws Exception {
		File file = null;
		try {
			LOG.debug("Inside method getAllParameters");
			Object[] inputParams = null;
			if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_CREATE)) {
				LOG.debug("Operation Chosen Was Create ");
				inputParams = new String[2];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsTemplateName();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_CREATE_LEAD)) {
				LOG.debug("Operation Chosen Was CreateLead ");
				inputParams = new String[2];

				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsTemplateName();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_LOAD)) {
				LOG.debug("Operation Chosen Was Load ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				String sXmlQueryDefault = Utils.readFileAsString("load.xml");
				if (restParameters.getLoanVO().getsXmlQueryMap() != null) {
					sXmlQueryDefault = Utils.applyMapOnString(restParameters
					        .getLoanVO().getsXmlQueryMap(), sXmlQueryDefault);
				}
				inputParams[2] = sXmlQueryDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_BATCH_LOAD)) {
				LOG.debug("Operation Chosen Was Load ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				String sXmlQueryDefault = Utils
				        .readFileAsString("batchload.xml");
				if (restParameters.getLoanVO().getsXmlQueryMap() != null) {
					sXmlQueryDefault = Utils.applyMapOnString(restParameters
					        .getLoanVO().getsXmlQueryMap(), sXmlQueryDefault);
				}
				inputParams[2] = sXmlQueryDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_GET_CREDIT_SCORE)) {
				LOG.debug("Operation Chosen Was CreditScore ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				String sDataContentQueryDefault = Utils
				        .readFileAsString("loadCreditinfo.xml");
				inputParams[2] = sDataContentQueryDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();

			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_SAVE_CREDIT_SCORE)) {
				LOG.debug("Operation Chosen Was SaveCreditScore ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				String sDataContentQueryDefault = Utils
				        .readFileAsString("saveCreditScore.xml");
				if (restParameters.getLoanVO().getsDataContentMap() != null) {
					sDataContentQueryDefault = Utils.applyMapOnString(
					        restParameters.getLoanVO().getsDataContentMap(),
					        sDataContentQueryDefault);
				}
				inputParams[2] = sDataContentQueryDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();

			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_GET_UNDERWRITING_CONDITION)) {
				LOG.debug("Operation Chosen Was UnderwritingCondition ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				String sDataContentQueryDefault = Utils
				        .readFileAsString("underwritingCondition.xml");
				inputParams[2] = sDataContentQueryDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();

			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_LOCK_LOAN_PROGRAM)) {
				LOG.debug("Operation Chosen Was LockLoanProgram ");
				inputParams = new Object[5];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				inputParams[2] = restParameters.getLoanVO().getIlpTemplateId();
				inputParams[3] = restParameters.getLoanVO().getRequestedRate();
				inputParams[4] = restParameters.getLoanVO().getRequestedFee();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_RUN_QUICK_PRICER)) {
				LOG.debug("Operation Chosen Was RunQuickPricer ");
				inputParams = new String[2];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				String teaserRateDefault = Utils
				        .readFileAsString("teaserRate.xml");

				if (restParameters.getLoanVO().getsXmlDataMap() != null)
					teaserRateDefault = Utils.applyMapOnString(restParameters
					        .getLoanVO().getsXmlDataMap(), teaserRateDefault);
				inputParams[1] = teaserRateDefault;
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_SAVE)) {
				LOG.debug("Operation Chosen Was Save ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();

				InputStream inputStream = getResource("save.xml");
				LOG.debug("InputStream of save.xml received "
				        + inputParams.toString());
				String condition = restParameters.getLoanVO().getCondition();
				if (condition == null || condition.equals("")) {
				} else {
					try {
						file = xmlProcessor.parseXML(inputStream, condition);
						if (file != null) {
							LOG.info("File got created succesfully "
							        + file.getName());
							LOG.info("File got created succesfully "
							        + file.getAbsolutePath());
						} else {
							LOG.error("Unable to create file");
							throw new Exception("Unable to create file ");
						}

					} catch (SAXException e) {
						LOG.error("Exception Caught " + e.getMessage());
					} catch (ParserConfigurationException e) {
						LOG.error("Exception Caught " + e.getMessage());
					} catch (TransformerException e) {
						LOG.error("Exception Caught " + e.getMessage());
					}
				}
				String saveDefault = Utils.readFileAsStringFromPath(file
				        .getAbsolutePath());
				if (restParameters.getLoanVO().getsDataContentMap() != null) {
					saveDefault = Utils.applyMapOnString(restParameters
					        .getLoanVO().getsDataContentMap(), saveDefault);
				}
				inputParams[2] = saveDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();

				LOG.info("saveDefault xmls is" + saveDefault);
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LIST_EDCOS_BY_LOAN_NUMBER)) {
				LOG.debug("Operation Chosen Was ListEDocsByLoanNumber ");
				inputParams = new Object[2];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
			} else if (restParameters
			        .getOpName()
			        .equals(WebServiceOperations.OP_NAME_LIST_MODIFIED_LOANS_BY_APP_CODE)) {
				LOG.debug("Operation Chosen Was ListModifiedLoansByAppCode ");
				inputParams = new Object[2];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getAppCode();
			} else if (restParameters
			        .getOpName()
			        .equals(WebServiceOperations.OP_NAME_CLEARED_MODIFIED_LOAN_BY_NAME_BY_APP_CODE)) {
				LOG.debug("Operation Chosen Was ListModifiedLoansByAppCode ");
				inputParams = new Object[3];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				inputParams[2] = restParameters.getLoanVO().getAppCode();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT)) {
				LOG.debug("Operation Chosen Was UploadPDFDocument ");

				inputParams = new String[5];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
				inputParams[2] = restParameters.getLoanVO().getDocumentType();
				inputParams[3] = restParameters.getLoanVO().getNotes();
				inputParams[4] = restParameters.getLoanVO().getsDataContent();
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_DOWNLOAD_EDCOS_PDF_BY_ID)) {
				LOG.debug("Operation Chosen Was OP_NAME_DOWNLOAD_EDCOS_PDF_BY_ID ");

				inputParams = new String[2];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getDocId();

			}
			return inputParams;
		} finally {
			if (file != null) {
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}

	public InputStream getResource(String fileName) throws NoSuchFileException {
		ClassLoader classLoader = this.getClass().getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		if (inputStream == null) {
			throw new NoSuchFileException(
			        "Resource file not found. Note that the current directory is the source folder!");
		}

		return inputStream;
	}

	/**
	 * @return the xmlProcessor
	 */
	public XMLProcessor getXmlProcessor() {
		return xmlProcessor;
	}

	/**
	 * @param xmlProcessor
	 *            the xmlProcessor to set
	 */
	public void setXmlProcessor(XMLProcessor xmlProcessor) {
		this.xmlProcessor = xmlProcessor;
	}
}
