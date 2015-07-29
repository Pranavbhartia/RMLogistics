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
public class RestInterceptor implements Callable
{

    private static final Logger LOG = Logger.getLogger( RestInterceptor.class );

    private XMLProcessor xmlProcessor;

    private Utils utils;


    public RestInterceptor()
    {}


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {

        LOG.info( "Inside method onCall " );
        MuleMessage message = eventContext.getMessage();


        String payload = message.getPayloadAsString();
        Gson gson = new Gson();
        RestParameters restParameters = gson.fromJson( payload, RestParameters.class );

        if ( restParameters.getOpName().equalsIgnoreCase( WebServiceOperations.OP_NAME_GET_CREDIT_SCORE )
            || restParameters.getOpName().equalsIgnoreCase( WebServiceOperations.OP_NAME_GET_UNDERWRITING_CONDITION )
            || restParameters.getOpName().equalsIgnoreCase( WebServiceOperations.OP_NAME_LOAN_BATCH_LOAD ) ) {
            message.setOutboundProperty( NewFiConstants.CONSTANT_OP_NAME, WebServiceOperations.OP_NAME_LOAN_LOAD );
		} else if (restParameters.getOpName().equalsIgnoreCase(
		        WebServiceOperations.OP_NAME_SAVE_CREDIT_SCORE)
		        || restParameters.getOpName().equalsIgnoreCase(
		                WebServiceOperations.OP_NAME_LOAN_UPDATE)) {
            message.setOutboundProperty( NewFiConstants.CONSTANT_OP_NAME, WebServiceOperations.OP_NAME_LOAN_SAVE );
        } else {
            message.setOutboundProperty( NewFiConstants.CONSTANT_OP_NAME, restParameters.getOpName() );
        }
        if ( restParameters.getLoanVO().getsTicket() == null || restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {

            utils.getUserTicket();

        } else {
            LOG.info( " This ticket is specific to a loan manager " + restParameters.getLoanVO().getsTicket() );
        }

        LOG.info( "Operation chosen by user " + restParameters.getOpName() );

        if ( NewFiManager.userTicket == null
            && ( restParameters.getLoanVO().getsTicket() == null || restParameters.getLoanVO().getsTicket()
                .equalsIgnoreCase( "" ) ) ) {
            throw new Exception( "Ticket Not Found, Hence breaking the flow " );
        }
        Object[] inputParameters = getAllParameters( restParameters );

        if ( inputParameters != null ) {
            LOG.info( "PARAMETERS PASSED TO LQB FROM MULE " );
            int count = 0;
            for ( Object param : inputParameters ) {
                LOG.info( ++count + "  " + param );
            }
        }
        message.setPayload( inputParameters );

        return message;
    }


    public Object[] getAllParameters( RestParameters restParameters ) throws Exception
    {
        File file = null;
        try {
            LOG.info( "Inside method getAllParameters" );
            Object[] inputParams = null;
            if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_CREATE ) ) {
                LOG.info( "Operation Chosen Was Create " );
                inputParams = new String[2];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsTemplateName();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_CREATE_LEAD ) ) {
                LOG.info( "Operation Chosen Was CreateLead " );
                inputParams = new String[2];

                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsTemplateName();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_LOAD ) ) {
                LOG.info( "Operation Chosen Was Load " );
                inputParams = new Object[4];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                String sXmlQueryDefault = utils.readFileAsString( "load.xml" );
                LOG.info( "Default load file " + sXmlQueryDefault );
                if ( restParameters.getLoanVO().getsXmlQueryMap() != null ) {
                    sXmlQueryDefault = utils.applyMapOnString( restParameters.getLoanVO().getsXmlQueryMap(), sXmlQueryDefault );
                    LOG.info( "Load file after applying map " + sXmlQueryDefault );
                }
                inputParams[2] = sXmlQueryDefault;
                inputParams[3] = restParameters.getLoanVO().getFormat();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_BATCH_LOAD ) ) {
                LOG.info( "Operation Chosen Was Load " );
                inputParams = new Object[4];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                String sXmlQueryDefault = utils.readFileAsString( "batchload.xml" );
                LOG.info( "Load called from Loan Batch Process " + sXmlQueryDefault );
                if ( restParameters.getLoanVO().getsXmlQueryMap() != null ) {
                    sXmlQueryDefault = utils.applyMapOnString( restParameters.getLoanVO().getsXmlQueryMap(), sXmlQueryDefault );
                }
                inputParams[2] = sXmlQueryDefault;
                inputParams[3] = restParameters.getLoanVO().getFormat();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_GET_CREDIT_SCORE ) ) {
                LOG.info( "Operation Chosen Was CreditScore " );
                inputParams = new Object[4];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                String sDataContentQueryDefault = utils.readFileAsString( "loadCreditinfo.xml" );
                LOG.info( "Credit Score request object passed " + sDataContentQueryDefault );
                inputParams[2] = sDataContentQueryDefault;
                inputParams[3] = restParameters.getLoanVO().getFormat();

            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_SAVE_CREDIT_SCORE ) ) {
                LOG.info( "Operation Chosen Was SaveCreditScore " );
                inputParams = new Object[4];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                String sDataContentQueryDefault = utils.readFileAsString( "saveCreditScore.xml" );
                LOG.info( "Saving credit score default string " + sDataContentQueryDefault );
                if ( restParameters.getLoanVO().getsDataContentMap() != null ) {
                    sDataContentQueryDefault = utils.applyMapOnString( restParameters.getLoanVO().getsDataContentMap(),
                        sDataContentQueryDefault );
                    LOG.info( "Saving Credit Score String passed to lqb after applying map " + sDataContentQueryDefault );
                }
                inputParams[2] = sDataContentQueryDefault;
                inputParams[3] = restParameters.getLoanVO().getFormat();

            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_GET_UNDERWRITING_CONDITION ) ) {
                LOG.info( "Operation Chosen Was UnderwritingCondition " );
                inputParams = new Object[4];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                String sDataContentQueryDefault = utils.readFileAsString( "underwritingCondition.xml" );
                LOG.info( "Underwriting Condition request string passed " + sDataContentQueryDefault );
                inputParams[2] = sDataContentQueryDefault;
                inputParams[3] = restParameters.getLoanVO().getFormat();

            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_LOCK_LOAN_PROGRAM ) ) {
                LOG.info( "Operation Chosen Was LockLoanProgram " );
                inputParams = new Object[5];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                inputParams[2] = restParameters.getLoanVO().getIlpTemplateId();
                inputParams[3] = restParameters.getLoanVO().getRequestedRate();
                inputParams[4] = restParameters.getLoanVO().getRequestedFee();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_RUN_QUICK_PRICER ) ) {
                LOG.info( "Operation Chosen Was RunQuickPricer " );
                inputParams = new String[2];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                String teaserRateDefault = utils.readFileAsString( "teaserRate.xml" );
                LOG.info( "Default teaser rate string " + teaserRateDefault );

                if ( restParameters.getLoanVO().getsXmlDataMap() != null ) {
                    teaserRateDefault = utils.applyMapOnString( restParameters.getLoanVO().getsXmlDataMap(), teaserRateDefault );
                    LOG.info( "Teaser Rate String after applying map " + teaserRateDefault );
                }
                inputParams[1] = teaserRateDefault;
			} else if (restParameters.getOpName().equals(
			        WebServiceOperations.OP_NAME_LOAN_UPDATE)) {
				LOG.info("Operation Chosen Was Update ");
				inputParams = new Object[4];
				if (restParameters.getLoanVO().getsTicket() != null
				        && !restParameters.getLoanVO().getsTicket()
				                .equalsIgnoreCase("")) {
					inputParams[0] = restParameters.getLoanVO().getsTicket();
				} else {
					inputParams[0] = NewFiManager.userTicket;
				}
				inputParams[1] = restParameters.getLoanVO().getsLoanNumber();

				InputStream inputStream = getResource("update.xml");
				LOG.info("InputStream of update.xml received "
				        + inputParams.toString());
				String condition = restParameters.getLoanVO().getCondition();
				LOG.info("Condition passed by user for Save call " + condition);
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
				String saveDefault = utils.readFileAsStringFromPath(file
				        .getAbsolutePath());
				LOG.info("Default save file created from condition passed by user "
				        + saveDefault);
				if (restParameters.getLoanVO().getsDataContentMap() != null) {
					saveDefault = utils.applyMapOnString(restParameters
					        .getLoanVO().getsDataContentMap(), saveDefault);
					LOG.info("Save request object passed to lqb " + saveDefault);
				}
				inputParams[2] = saveDefault;
				inputParams[3] = restParameters.getLoanVO().getFormat();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_SAVE ) ) {
                LOG.info( "Operation Chosen Was Save " );
                inputParams = new Object[4];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();

                InputStream inputStream = getResource( "save.xml" );
                LOG.info( "InputStream of save.xml received " + inputParams.toString() );
                String condition = restParameters.getLoanVO().getCondition();
                LOG.info( "Condition passed by user for Save call " + condition );
                if ( condition == null || condition.equals( "" ) ) {
                } else {
                    try {
                        file = xmlProcessor.parseXML( inputStream, condition );
                        if ( file != null ) {
                            LOG.info( "File got created succesfully " + file.getName() );
                            LOG.info( "File got created succesfully " + file.getAbsolutePath() );
                        } else {
                            LOG.error( "Unable to create file" );
                            throw new Exception( "Unable to create file " );
                        }

                    } catch ( SAXException e ) {
                        LOG.error( "Exception Caught " + e.getMessage() );
                    } catch ( ParserConfigurationException e ) {
                        LOG.error( "Exception Caught " + e.getMessage() );
                    } catch ( TransformerException e ) {
                        LOG.error( "Exception Caught " + e.getMessage() );
                    }
                }
                String saveDefault = utils.readFileAsStringFromPath( file.getAbsolutePath() );
                LOG.info( "Default save file created from condition passed by user " + saveDefault );
                if ( restParameters.getLoanVO().getsDataContentMap() != null ) {
                    saveDefault = utils.applyMapOnString( restParameters.getLoanVO().getsDataContentMap(), saveDefault );
                    LOG.info( "Save request object passed to lqb " + saveDefault );
                }
                inputParams[2] = saveDefault;
                inputParams[3] = restParameters.getLoanVO().getFormat();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LIST_EDCOS_BY_LOAN_NUMBER ) ) {
                LOG.info( "Operation Chosen Was ListEDocsByLoanNumber " );
                inputParams = new Object[2];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LIST_MODIFIED_LOANS_BY_APP_CODE ) ) {
                LOG.info( "Operation Chosen Was ListModifiedLoansByAppCode " );
                inputParams = new Object[2];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getAppCode();
            } else if ( restParameters.getOpName().equals(
                WebServiceOperations.OP_NAME_CLEARED_MODIFIED_LOAN_BY_NAME_BY_APP_CODE ) ) {
                LOG.info( "Operation Chosen Was ListModifiedLoansByAppCode " );
                inputParams = new Object[3];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                inputParams[2] = restParameters.getLoanVO().getAppCode();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT ) ) {
                LOG.info( "Operation Chosen Was UploadPDFDocument " );

                inputParams = new String[5];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
                inputParams[2] = restParameters.getLoanVO().getDocumentType();
                inputParams[3] = restParameters.getLoanVO().getNotes();
                inputParams[4] = restParameters.getLoanVO().getsDataContent();
            } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_DOWNLOAD_EDCOS_PDF_BY_ID ) ) {
                LOG.info( "Operation Chosen Was OP_NAME_DOWNLOAD_EDCOS_PDF_BY_ID " );

                inputParams = new String[2];
                if ( restParameters.getLoanVO().getsTicket() != null
                    && !restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
                    inputParams[0] = restParameters.getLoanVO().getsTicket();
                } else {
                    inputParams[0] = NewFiManager.userTicket;
                }
                inputParams[1] = restParameters.getLoanVO().getDocId();

            }
            return inputParams;
        } finally {
            if ( file != null ) {
                if ( file.exists() ) {
                    file.delete();
                }
            }
        }
    }


    public InputStream getResource( String fileName ) throws NoSuchFileException
    {
        ClassLoader classLoader = this.getClass().getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream( fileName );

        if ( inputStream == null ) {
            throw new NoSuchFileException( "Resource file not found. Note that the current directory is the source folder!" );
        }

        return inputStream;
    }


    /**
     * @return the xmlProcessor
     */
    public XMLProcessor getXmlProcessor()
    {
        return xmlProcessor;
    }


    /**
     * @param xmlProcessor
     *            the xmlProcessor to set
     */
    public void setXmlProcessor( XMLProcessor xmlProcessor )
    {
        this.xmlProcessor = xmlProcessor;
    }


    public Utils getUtils()
    {
        return utils;
    }


    public void setUtils( Utils utils )
    {
        this.utils = utils;
    }

}
