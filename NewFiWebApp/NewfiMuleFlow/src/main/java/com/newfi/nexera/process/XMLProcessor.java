/**
 * 
 */
package com.newfi.nexera.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
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
            newApplicant.setAttribute( "id", "CoBorrowerApplicantId" );
            Element credit = doc.createElement( "credit" );
            credit.setAttribute( "craid", "coborrower_creditCardId" );
            credit.setAttribute( "loginName", "COBORROWER_LOGIN_NAME" );
            credit.setAttribute( "password", "COBORROWER_PASSWORD" );
            credit.setAttribute( "equifax", "coborrower_equifaxStatus" );
            credit.setAttribute( "experian", "coborrower_experianStatus" );
            credit.setAttribute( "transunion", "coborrower_transunionStatus" );
            newApplicant.appendChild( credit );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "coborrower_firstName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "coborrower_middleName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "coborrower_lastName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "coborrower_dateOfBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "coborrower_applicantAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "coborrower_userSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "coborrower_alimonyName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "coborrower_alimonyPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "coborrower_jobExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "coborrower_jobRelatedPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "coborrower_applicantCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "coborrower_applicantState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "coborrower_applicantZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_WITHOUT_SSN_BOTH ) ) {
            LOG.debug( "Need to remove ssn related info for borrower" );
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );

            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "CoBorrowerApplicantId" );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "coborrower_firstName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "coborrower_middleName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "coborrower_lastName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "coborrower_dateOfBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "coborrower_applicantAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "coborrower_userSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "coborrower_alimonyName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "coborrower_alimonyPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "coborrower_jobExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "coborrower_jobRelatedPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "coborrower_applicantCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "coborrower_applicantState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "coborrower_applicantZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_WITHOUT_SSN ) ) {
            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "CoBorrowerApplicantId" );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "coborrower_firstName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "coborrower_middleName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "coborrower_lastName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "coborrower_dateOfBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "coborrower_applicantAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "coborrower_userSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "coborrower_alimonyName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "coborrower_alimonyPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "coborrower_jobExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "coborrower_jobRelatedPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "coborrower_applicantCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "coborrower_applicantState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "coborrower_applicantZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_BORROWER_WITHOUT_SSN ) ) {
            LOG.debug( "Need to remove ssn related info for borrower" );
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );

            Element newApplicant = doc.createElement( "applicant" );
            newApplicant.setAttribute( "id", "CoBorrowerApplicantId" );
            Element credit = doc.createElement( "credit" );
            credit.setAttribute( "craid", "coborrower_creditCardId" );
            credit.setAttribute( "loginName", "COBORROWER_LOGIN_NAME" );
            credit.setAttribute( "password", "COBORROWER_PASSWORD" );
            credit.setAttribute( "equifax", "coborrower_equifaxStatus" );
            credit.setAttribute( "experian", "coborrower_experianStatus" );
            credit.setAttribute( "transunion", "coborrower_transunionStatus" );
            newApplicant.appendChild( credit );
            Element firstName = createNewElement( doc, "field", "aBFirstNm", "coborrower_firstName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aBMidNm", "coborrower_middleName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aBLastNm", "coborrower_lastName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aBDob", "coborrower_dateOfBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aBAddr", "coborrower_applicantAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aBSsn", "coborrower_userSSNnumber" );
            newApplicant.appendChild( userSSN );
            Element alimonyName = createNewElement( doc, "field", "aAlimonyNm", "coborrower_alimonyName" );
            newApplicant.appendChild( alimonyName );
            Element alimonyPayment = createNewElement( doc, "field", "aAlimonyPmt", "coborrower_alimonyPayment" );
            newApplicant.appendChild( alimonyPayment );
            Element baseIncome1 = createNewElement( doc, "field", "aBBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome1 );
            Element jobExpenses = createNewElement( doc, "field", "aJobRelated1ExpenseDesc", "coborrower_jobExpenses" );
            newApplicant.appendChild( jobExpenses );
            Element jobRelatedPayment = createNewElement( doc, "field", "aJobRelated1Pmt", "coborrower_jobRelatedPayment" );
            newApplicant.appendChild( jobRelatedPayment );
            Element applicantCity = createNewElement( doc, "field", "aBCity", "coborrower_applicantCity" );
            newApplicant.appendChild( applicantCity );
            Element applicantState = createNewElement( doc, "field", "aBState", "coborrower_applicantState" );
            newApplicant.appendChild( applicantState );
            Element applicantZip = createNewElement( doc, "field", "aBZip", "coborrower_applicantZipCode" );
            newApplicant.appendChild( applicantZip );
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_IS_WIFE_WITH_SSN_BOTH ) ) {
            NodeList newApplicantList = doc.getElementsByTagName( "applicant" );
            Node newApplicant = newApplicantList.item( 0 );
            Element firstName = createNewElement( doc, "field", "aCFirstNm", "coborrower_firstName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aCMidNm", "coborrower_middleName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aCLastNm", "coborrower_lastName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aCDob", "coborrower_dateOfBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aCBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aCAddr", "coborrower_applicantAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aCSsn", "coborrower_userSSNnumber" );
            newApplicant.appendChild( userSSN );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_IS_WIFE_WITHOUT_SSN_BOTH ) ) {
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );
            NodeList newApplicantList = doc.getElementsByTagName( "applicant" );
            Node newApplicant = newApplicantList.item( 0 );
            Element firstName = createNewElement( doc, "field", "aCFirstNm", "coborrower_firstName" );
            newApplicant.appendChild( firstName );
            Element middleName = createNewElement( doc, "field", "aCMidNm", "coborrower_middleName" );
            newApplicant.appendChild( middleName );
            Element lastName = createNewElement( doc, "field", "aCLastNm", "coborrower_lastName" );
            newApplicant.appendChild( lastName );
            Element dateOfBirth = createNewElement( doc, "field", "aCDob", "coborrower_dateOfBirth" );
            newApplicant.appendChild( dateOfBirth );
            Element baseIncome = createNewElement( doc, "field", "aCBaseI", "coborrower_baseIncome" );
            newApplicant.appendChild( baseIncome );
            Element address = createNewElement( doc, "field", "aCAddr", "coborrower_applicantAddress" );
            newApplicant.appendChild( address );
            Element userSSN = createNewElement( doc, "field", "aCSsn", "coborrower_userSSNnumber" );
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
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource( doc );

        File file = new File( getRootDirectory() + File.separator +"tmpFolder" + File.separator+ "file.xml" );
        file.createNewFile();
        StreamResult result = new StreamResult( file );
        transformer.transform( source, result );
        return file;
    }


    private String getRootDirectory()
    {
        return File.listRoots()[0].getAbsolutePath();
    }


}
