/**
 * 
 */
package com.newfi.nexera.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.newfi.nexera.constants.NewFiConstants;


/**
 * @author Utsav
 *
 */
public class XMLProcessor
{

    private static final Logger LOG = Logger.getLogger( XMLProcessor.class );


    public File parseXML( InputStream inputStream, String condition ) throws SAXException, IOException,
        ParserConfigurationException, TransformerException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse( inputStream );
        Node loan = doc.getElementsByTagName( "loan" ).item( 0 );
        if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_NO_CO_BORROWER_WITH_SSN ) ) {
            LOG.debug( "DO Nothing ,default case  " );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_NO_CO_BORROWER_WITHOUT_SSN ) ) {
            LOG.debug( "Need to remove ssn related info for borrower" );
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_WITH_SSN_BOTH ) ) {
            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "ApplicantCoBorrowerId" );
            Element credit = doc.createElement( "credit" );
            credit.setAttribute( "craid", "creditCoborrowerCardId" );
            credit.setAttribute( "requestType", "NEW" );
            credit.setAttribute( "loginName", "LOGIN_Coborrower_NAME" );
            credit.setAttribute( "password", "PASS_COBORROWER_WORD" );
            credit.setAttribute( "equifax", "equifaxCoborrowerStatus" );
            credit.setAttribute( "experian", "experianCoborrowerStatus" );
            credit.setAttribute( "transunion", "transunionCoborrowerStatus" );
            newApplicant.appendChild( credit );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "firstCoborrowerName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "middleCoborrowerName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "lastCoborrowerName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "dateOfCoborrowerBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "applicantCoborrowerAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "userCoborrowerSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "alimonyCoborrowerName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "alimonyCoborrowerPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "jobCoborrowerExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "jobRelatedCoborrowerPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "applicantCoborrowerCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "applicantCoborrowerState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "applicantCoborrowerZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_WITHOUT_SSN_BOTH ) ) {
            LOG.debug( "Need to remove ssn related info for borrower" );
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );

            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "ApplicantCoBorrowerId" );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "firstCoborrowerName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "middleCoborrowerName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "lastCoborrowerName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "dateOfCoborrowerBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "applicantCoborrowerAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "userCoborrowerSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "alimonyCoborrowerName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "alimonyCoborrowerPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "jobCoborrowerExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "jobRelatedCoborrowerPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "applicantCoborrowerCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "applicantCoborrowerState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "applicantCoborrowerZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_WITHOUT_SSN ) ) {
            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "ApplicantCoBorrowerId" );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "firstCoborrowerName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "middleCoborrowerName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "lastCoborrowerName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "dateOfCoborrowerBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "applicantCoborrowerAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "userCoborrowerSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "alimonyCoborrowerName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "alimonyCoborrowerPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "jobCoborrowerExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "jobRelatedCoborrowerPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "applicantCoborrowerCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "applicantCoborrowerState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "applicantCoborrowerZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_BORROWER_WITHOUT_SSN ) ) {
            LOG.debug( "Need to remove ssn related info for borrower" );
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );

            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "ApplicantCoborrowerId" );
            Element credit = doc.createElement( "credit" );
            credit.setAttribute( "craid", "creditCoborrowerCardId" );
            credit.setAttribute( "requestType", "NEW" );
            credit.setAttribute( "loginName", "LOGIN_Coborrower_NAME" );
            credit.setAttribute( "password", "PASS_COBORROWER_WORD" );
            credit.setAttribute( "equifax", "equifaxCoborrowerStatus" );
            credit.setAttribute( "experian", "experianCoborrowerStatus" );
            credit.setAttribute( "transunion", "transunionCoborrowerStatus" );
            newApplicant.appendChild( credit );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "firstCoborrowerName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "middleCoborrowerName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "lastCoborrowerName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "dateOfCoborrowerBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "applicantCoborrowerAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "userCoborrowerSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "alimonyCoborrowerName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "alimonyCoborrowerPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "jobCoborrowerExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "jobRelatedCoborrowerPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "applicantCoborrowerCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "applicantCoborrowerState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "applicantCoborrowerZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_IS_WIFE_WITH_SSN_BOTH ) ) {
            NodeList newApplicantList = doc.getElementsByTagName( "applicant" );
            Node newApplicant = newApplicantList.item( 0 );
            Element firstName = createNewElement( doc, "field", "aCFirstNm", "firstCoborrowerName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aCMidNm", "middleCoborrowerName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aCLastNm", "lastCoborrowerName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aCDob", "dateOfCoborrowerBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aCBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aCAddr", "applicantCoborrowerAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aCSsn", "userCoborrowerSSNnumber" );
            newApplicant.appendChild( userSSN );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_IS_WIFE_WITHOUT_SSN_BOTH ) ) {
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );
            NodeList newApplicantList = doc.getElementsByTagName( "applicant" );
            Node newApplicant = newApplicantList.item( 0 );
            Element firstName = createNewElement( doc, "field", "aCFirstNm", "firstCoborrowerName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aCMidNm", "middleCoborrowerName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aCLastNm", "lastCoborrowerName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aCDob", "dateOfCoborrowerBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aCBaseI", "baseCoborrowerIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aCAddr", "applicantCoborrowerAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aCSsn", "userCoborrowerSSNnumber" );
            newApplicant.appendChild( userSSN );
        }
        File file = writeContentToXMLFile( doc );
        return file;
    }


    private Element createNewElement( Document doc, String fieldName, String attributeName, String textContent )
    {
        Element element = doc.createElement( fieldName );
        element.setAttribute( "id", attributeName );
        element.setTextContent( textContent );
        return element;
    }


    private File writeContentToXMLFile( Document doc ) throws IOException, TransformerException
    {
        File file = null;
        boolean status = true;
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource( doc );

        file = new File( getRootDirectory() + File.separator + randomStringOfLength() + ".xml" );
        file.createNewFile();
        StreamResult result = new StreamResult( file );
        transformer.transform( source, result );

        return file;
    }


    private String getRootDirectory()
    {
        return System.getProperty( "java.io.tmpdir" );
    }


    public String randomStringOfLength()
    {
        Integer length = 30;
        StringBuffer buffer = new StringBuffer();
        while ( buffer.length() < length ) {
            buffer.append( uuidString() );
        }

        // this part controls the length of the returned string
        return buffer.substring( 0, length );
    }


    private String uuidString()
    {
        return UUID.randomUUID().toString();
    }


}
