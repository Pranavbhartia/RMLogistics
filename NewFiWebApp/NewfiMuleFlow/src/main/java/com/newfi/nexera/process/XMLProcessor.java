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
import com.newfi.nexera.rest.RestInterceptor;


/**
 * @author Utsav
 *
 */
public class XMLProcessor
{

    private static final Logger LOG = Logger.getLogger( XMLProcessor.class );


    public  File parseXML( InputStream inputStream, String condition ) throws SAXException, IOException,
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
            
            NodeList newApplicantList = doc.getElementsByTagName( "applicant" );
            Node newApplicant = newApplicantList.item( 0 );
                
            Element aBExperianScore = createNewElement( doc, "field", "aBExperianScore", "borrowerExperianScore" );
            Element aBEquifax = createNewElement( doc, "field", "aBEquifaxScore", "borrowerEquifaxScore" );
            Element aBTransUnionScore = createNewElement( doc, "field", "aBTransUnionScore", "borrowerTransUnionScore" );
            newApplicant.appendChild( aBExperianScore );
            newApplicant.appendChild( aBEquifax );
            newApplicant.appendChild( aBTransUnionScore );
            
            
            
            
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
            Element coBorrowerHomePhone = createNewElement( doc, "field", "aBHPhone", "applicationCoborrowerHomePhone" );
            newApplicant.appendChild( coBorrowerHomePhone );          			
			Element applicationCoborrowerDecJudgment = createNewElement( doc, "field", "aBDecJudgment", "applicationCoborrowerDecJudgment" );
            newApplicant.appendChild( applicationCoborrowerDecJudgment );
            Element applicationCoborrowerDecBankrupt = createNewElement( doc, "field", "aBDecBankrupt", "applicationCoborrowerDecBankrupt" );
            newApplicant.appendChild( applicationCoborrowerDecBankrupt );
            Element applicationCoborrowerDecForeclosure = createNewElement( doc, "field", "aBDecForeclosure", "applicationCoborrowerDecForeclosure" );
            newApplicant.appendChild( applicationCoborrowerDecForeclosure );          
            Element applicationCoborrowerDecLawsuit = createNewElement( doc, "field", "aBDecLawsuit", "applicationCoborrowerDecLawsuit" );
            newApplicant.appendChild( applicationCoborrowerDecLawsuit );           
            Element applicationCoborrowerDecObligated = createNewElement( doc, "field", "aBDecObligated", "applicationCoborrowerDecObligated" );
            newApplicant.appendChild( applicationCoborrowerDecObligated );           
            Element applicationCoborrowerDecDelinquent = createNewElement( doc, "field", "aBDecDelinquent", "applicationCoborrowerDecDelinquent" );
            newApplicant.appendChild( applicationCoborrowerDecDelinquent );          
            Element applicationCoborrowerDecAlimony = createNewElement( doc, "field", "aBDecAlimony", "applicationCoborrowerDecAlimony" );
            newApplicant.appendChild( applicationCoborrowerDecAlimony );           
            Element applicationCoborrowerDecBorrowing = createNewElement( doc, "field", "aBDecBorrowing", "applicationCoborrowerDecBorrowing" );
            newApplicant.appendChild( applicationCoborrowerDecBorrowing );          
            Element applicationCoborrowerDecEndorser = createNewElement( doc, "field", "aBDecEndorser", "applicationCoborrowerDecEndorser" );
            newApplicant.appendChild( applicationCoborrowerDecEndorser );           
            Element applicationCoborrowerDecCitizen = createNewElement( doc, "field", "aBDecCitizen", "applicationCoborrowerDecCitizen" );
            newApplicant.appendChild( applicationCoborrowerDecCitizen );           
            Element applicationCoborrowerDecResidency = createNewElement( doc, "field", "aBDecResidency", "applicationCoborrowerDecResidency" );
            newApplicant.appendChild( applicationCoborrowerDecResidency );         
            Element applicationCoborrowerDecOcc = createNewElement( doc, "field", "aBDecOcc", "applicationCoborrowerDecOcc" );
            newApplicant.appendChild( applicationCoborrowerDecOcc );
            Element applicationCoborrowerDecPastOwnedPropT = createNewElement( doc, "field", "aBDecPastOwnedPropT", "applicationCoborrowerDecPastOwnedPropT" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropT ); 
            Element applicationCoborrowerDecPastOwnedPropTitleT = createNewElement( doc, "field", "aBDecPastOwnedPropTitleT", "titleTApplicationCoborrowerDecPastOwnedProp" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropTitleT );     
            Element applicationCoborrowerNoFurnish = createNewElement( doc, "field", "aBNoFurnish", "applicationCoborrowerNoFurnish" );
            newApplicant.appendChild( applicationCoborrowerNoFurnish );          
            Element applicationCoborrowerHispanicT = createNewElement( doc, "field", "aBHispanicT", "applicationCoborrowerHispanicT" );
            newApplicant.appendChild( applicationCoborrowerHispanicT );         
            Element applicationCoborrowerIsAmericanIndian = createNewElement( doc, "field", "aBIsAmericanIndian", "applicationCoborrowerIsAmericanIndian" );
            newApplicant.appendChild( applicationCoborrowerIsAmericanIndian );          
            Element applicationCoborrowerIsAsian = createNewElement( doc, "field", "aBIsAsian", "applicationCoborrowerIsAsian" );
            newApplicant.appendChild( applicationCoborrowerIsAsian );          
            Element applicationCoborrowerIsBlack = createNewElement( doc, "field", "aBIsBlack", "applicationCoborrowerIsBlack" );
            newApplicant.appendChild( applicationCoborrowerIsBlack );          
            Element applicationCoborrowerIsPacificIslander = createNewElement( doc, "field", "aBIsPacificIslander", "applicationCoborrowerIsPacificIslander" );
            newApplicant.appendChild( applicationCoborrowerIsPacificIslander );          
            Element applicationCoborrowerIsWhite = createNewElement( doc, "field", "aBIsWhite", "applicationCoborrowerIsWhite" );
            newApplicant.appendChild( applicationCoborrowerIsWhite );          
            Element applicationCoborrowerGender = createNewElement( doc, "field", "aBGender", "applicationCoborrowerGender" );
            newApplicant.appendChild( applicationCoborrowerGender );
            
            
            
			
			
			Element applicationCoborrowerEmplrName = createNewElement( doc, "field", "abprimaryemplrnm", "applicationCoborrowerEmplrName" );
            newApplicant.appendChild( applicationCoborrowerEmplrName );
            Element applicationCoborrowerEmploymentStartDate = createNewElement( doc, "field", "abprimaryempltstartd", "applicationCoborrowerEmploymentStartDate" );
            newApplicant.appendChild( applicationCoborrowerEmploymentStartDate );
            Element applicationCoborrowerEmployementTitle = createNewElement( doc, "field", "abprimaryjobtitle", "applicationCoborrowerEmployementTitle" );
            newApplicant.appendChild( applicationCoborrowerEmployementTitle );
			
			
            
           
           
           
          
            
            loan.appendChild( newApplicant );
        } else if ( condition.equalsIgnoreCase( NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_WITHOUT_SSN_BOTH ) ) {
            LOG.debug( "Need to remove ssn related info for borrower" );
            NodeList nodeToRemove = doc.getElementsByTagName( "credit" );
            nodeToRemove.item( 0 ).getParentNode().removeChild( nodeToRemove.item( 0 ) );

            
            NodeList newApplicantList = doc.getElementsByTagName( "applicant" );
            Node newApplicantB = newApplicantList.item( 0 );
                
            Element aBExperianScore = createNewElement( doc, "field", "aBExperianScore", "borrowerExperianScore" );
            Element aBEquifax = createNewElement( doc, "field", "aBEquifaxScore", "borrowerEquifaxScore" );
            Element aBTransUnionScore = createNewElement( doc, "field", "aBTransUnionScore", "borrowerTransUnionScore" );
            newApplicantB.appendChild( aBExperianScore );
            newApplicantB.appendChild( aBEquifax );
            newApplicantB.appendChild( aBTransUnionScore );
            //loan.appendChild( newApplicantB );
            
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
            Element coBorrowerHomePhone = createNewElement( doc, "field", "aBHPhone", "applicationCoborrowerHomePhone" );
            newApplicant.appendChild( coBorrowerHomePhone );
            
            Element applicationCoborrowerDecJudgment = createNewElement( doc, "field", "aBDecJudgment", "applicationCoborrowerDecJudgment" );
            newApplicant.appendChild( applicationCoborrowerDecJudgment );
            Element applicationCoborrowerDecBankrupt = createNewElement( doc, "field", "aBDecBankrupt", "applicationCoborrowerDecBankrupt" );
            newApplicant.appendChild( applicationCoborrowerDecBankrupt );
            Element applicationCoborrowerDecForeclosure = createNewElement( doc, "field", "aBDecForeclosure", "applicationCoborrowerDecForeclosure" );
            newApplicant.appendChild( applicationCoborrowerDecForeclosure );          
            Element applicationCoborrowerDecLawsuit = createNewElement( doc, "field", "aBDecLawsuit", "applicationCoborrowerDecLawsuit" );
            newApplicant.appendChild( applicationCoborrowerDecLawsuit );           
            Element applicationCoborrowerDecObligated = createNewElement( doc, "field", "aBDecObligated", "applicationCoborrowerDecObligated" );
            newApplicant.appendChild( applicationCoborrowerDecObligated );           
            Element applicationCoborrowerDecDelinquent = createNewElement( doc, "field", "aBDecDelinquent", "applicationCoborrowerDecDelinquent" );
            newApplicant.appendChild( applicationCoborrowerDecDelinquent );          
            Element applicationCoborrowerDecAlimony = createNewElement( doc, "field", "aBDecAlimony", "applicationCoborrowerDecAlimony" );
            newApplicant.appendChild( applicationCoborrowerDecAlimony );           
            Element applicationCoborrowerDecBorrowing = createNewElement( doc, "field", "aBDecBorrowing", "applicationCoborrowerDecBorrowing" );
            newApplicant.appendChild( applicationCoborrowerDecBorrowing );          
            Element applicationCoborrowerDecEndorser = createNewElement( doc, "field", "aBDecEndorser", "applicationCoborrowerDecEndorser" );
            newApplicant.appendChild( applicationCoborrowerDecEndorser );           
            Element applicationCoborrowerDecCitizen = createNewElement( doc, "field", "aBDecCitizen", "applicationCoborrowerDecCitizen" );
            newApplicant.appendChild( applicationCoborrowerDecCitizen );           
            Element applicationCoborrowerDecResidency = createNewElement( doc, "field", "aBDecResidency", "applicationCoborrowerDecResidency" );
            newApplicant.appendChild( applicationCoborrowerDecResidency );         
            Element applicationCoborrowerDecOcc = createNewElement( doc, "field", "aBDecOcc", "applicationCoborrowerDecOcc" );
            newApplicant.appendChild( applicationCoborrowerDecOcc );
            Element applicationCoborrowerDecPastOwnedPropT = createNewElement( doc, "field", "aBDecPastOwnedPropT", "applicationCoborrowerDecPastOwnedPropT" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropT ); 
            Element applicationCoborrowerDecPastOwnedPropTitleT = createNewElement( doc, "field", "aBDecPastOwnedPropTitleT", "titleTApplicationCoborrowerDecPastOwnedProp" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropTitleT );


            Element applicationCoborrowerNoFurnish = createNewElement( doc, "field", "aBNoFurnish", "applicationCoborrowerNoFurnish" );
            newApplicant.appendChild( applicationCoborrowerNoFurnish );          
            Element applicationCoborrowerHispanicT = createNewElement( doc, "field", "aBHispanicT", "applicationCoborrowerHispanicT" );
            newApplicant.appendChild( applicationCoborrowerHispanicT );         
            Element applicationCoborrowerIsAmericanIndian = createNewElement( doc, "field", "aBIsAmericanIndian", "applicationCoborrowerIsAmericanIndian" );
            newApplicant.appendChild( applicationCoborrowerIsAmericanIndian );          
            Element applicationCoborrowerIsAsian = createNewElement( doc, "field", "aBIsAsian", "applicationCoborrowerIsAsian" );
            newApplicant.appendChild( applicationCoborrowerIsAsian );          
            Element applicationCoborrowerIsBlack = createNewElement( doc, "field", "aBIsBlack", "applicationCoborrowerIsBlack" );
            newApplicant.appendChild( applicationCoborrowerIsBlack );          
            Element applicationCoborrowerIsPacificIslander = createNewElement( doc, "field", "aBIsPacificIslander", "applicationCoborrowerIsPacificIslander" );
            newApplicant.appendChild( applicationCoborrowerIsPacificIslander );          
            Element applicationCoborrowerIsWhite = createNewElement( doc, "field", "aBIsWhite", "applicationCoborrowerIsWhite" );
            newApplicant.appendChild( applicationCoborrowerIsWhite );          
            Element applicationCoborrowerGender = createNewElement( doc, "field", "aBGender", "applicationCoborrowerGender" );
            newApplicant.appendChild( applicationCoborrowerGender );
            
            
            
              
         /*   Element applicationCoborrowerEmpCollection = doc.createElement( "collection" );
            applicationCoborrowerEmpCollection.setAttribute( "id", "aBEmpCollection" );
            Element coBorrowerRecord = doc.createElement( "record" );         
            Element applicationCoborrowerEmpIsCurrent = createNewElement( doc, "field", "IsCurrent", "true" );
            Element applicationCoborrowerEmplrName = createNewElement( doc, "field", "EmplrNm", "applicationCoborrowerEmplrName" );
            Element applicationCoborrowerMontlyIncome = createNewElement( doc, "field", "MonI", "applicationCoborrowerMontlyIncome" );
            Element applicationCoborrowerEmploymentStartDate = createNewElement( doc, "field", "EmplmtStartD", "applicationCoborrowerEmploymentStartDate" );
            Element applicationCoborrowerEmployementTitle = createNewElement( doc, "field", "JobTitle", "applicationCoborrowerEmployementTitle" );           
            coBorrowerRecord.appendChild(applicationCoborrowerEmpIsCurrent);
            coBorrowerRecord.appendChild(applicationCoborrowerEmplrName);
            coBorrowerRecord.appendChild(applicationCoborrowerMontlyIncome);
            coBorrowerRecord.appendChild(applicationCoborrowerEmploymentStartDate);
            coBorrowerRecord.appendChild(applicationCoborrowerEmployementTitle);           
            applicationCoborrowerEmpCollection.appendChild(coBorrowerRecord);
            newApplicant.appendChild( applicationCoborrowerEmpCollection );*/
            
            
        	Element applicationCoborrowerEmplrName = createNewElement( doc, "field", "abprimaryemplrnm", "applicationCoborrowerEmplrName" );
            newApplicant.appendChild( applicationCoborrowerEmplrName );
            Element applicationCoborrowerEmploymentStartDate = createNewElement( doc, "field", "abprimaryempltstartd", "applicationCoborrowerEmploymentStartDate" );
            newApplicant.appendChild( applicationCoborrowerEmploymentStartDate );
            Element applicationCoborrowerEmployementTitle = createNewElement( doc, "field", "abprimaryjobtitle", "applicationCoborrowerEmployementTitle" );
            newApplicant.appendChild( applicationCoborrowerEmployementTitle );
			
            
            
            
            Element aBCoBorrowerExperianScore = createNewElement( doc, "field", "aBExperianScore", "800" );
            Element aBCoBorrowerEquifax = createNewElement( doc, "field", "aBEquifaxScore", "800" );
            Element aBCoBorrowerTransUnionScore = createNewElement( doc, "field", "aBTransUnionScore", "800" );
            newApplicant.appendChild( aBCoBorrowerExperianScore );
            newApplicant.appendChild( aBCoBorrowerEquifax );
            newApplicant.appendChild( aBCoBorrowerTransUnionScore );
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
            Element coBorrowerHomePhone = createNewElement( doc, "field", "aCHPhone", "applicationCoborrowerHomePhone" );
            newApplicant.appendChild( coBorrowerHomePhone );
            
            Element applicationCoborrowerDecJudgment = createNewElement( doc, "field", "aCDecJudgment", "applicationCoborrowerDecJudgment" );
            newApplicant.appendChild( applicationCoborrowerDecJudgment );
            Element applicationCoborrowerDecBankrupt = createNewElement( doc, "field", "aCDecBankrupt", "applicationCoborrowerDecBankrupt" );
            newApplicant.appendChild( applicationCoborrowerDecBankrupt );
            Element applicationCoborrowerDecForeclosure = createNewElement( doc, "field", "aCDecForeclosure", "applicationCoborrowerDecForeclosure" );
            newApplicant.appendChild( applicationCoborrowerDecForeclosure );          
            Element applicationCoborrowerDecLawsuit = createNewElement( doc, "field", "aCDecLawsuit", "applicationCoborrowerDecLawsuit" );
            newApplicant.appendChild( applicationCoborrowerDecLawsuit );           
            Element applicationCoborrowerDecObligated = createNewElement( doc, "field", "aCDecObligated", "applicationCoborrowerDecObligated" );
            newApplicant.appendChild( applicationCoborrowerDecObligated );           
            Element applicationCoborrowerDecDelinquent = createNewElement( doc, "field", "aCDecDelinquent", "applicationCoborrowerDecDelinquent" );
            newApplicant.appendChild( applicationCoborrowerDecDelinquent );          
            Element applicationCoborrowerDecAlimony = createNewElement( doc, "field", "aCDecAlimony", "applicationCoborrowerDecAlimony" );
            newApplicant.appendChild( applicationCoborrowerDecAlimony );           
            Element applicationCoborrowerDecBorrowing = createNewElement( doc, "field", "aCDecBorrowing", "applicationCoborrowerDecBorrowing" );
            newApplicant.appendChild( applicationCoborrowerDecBorrowing );          
            Element applicationCoborrowerDecEndorser = createNewElement( doc, "field", "aCDecEndorser", "applicationCoborrowerDecEndorser" );
            newApplicant.appendChild( applicationCoborrowerDecEndorser );           
            Element applicationCoborrowerDecCitizen = createNewElement( doc, "field", "aCDecCitizen", "applicationCoborrowerDecCitizen" );
            newApplicant.appendChild( applicationCoborrowerDecCitizen );           
            Element applicationCoborrowerDecResidency = createNewElement( doc, "field", "aCDecResidency", "applicationCoborrowerDecResidency" );
            newApplicant.appendChild( applicationCoborrowerDecResidency );         
            Element applicationCoborrowerDecOcc = createNewElement( doc, "field", "aCDecOcc", "applicationCoborrowerDecOcc" );
            newApplicant.appendChild( applicationCoborrowerDecOcc );
            Element applicationCoborrowerDecPastOwnedPropT = createNewElement( doc, "field", "aCDecPastOwnedPropT", "applicationCoborrowerDecPastOwnedPropT" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropT ); 
            Element applicationCoborrowerDecPastOwnedPropTitleT = createNewElement( doc, "field", "aCDecPastOwnedPropTitleT", "titleTApplicationCoborrowerDecPastOwnedProp" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropTitleT );
            
            
            
            
            Element applicationCoborrowerNoFurnish = createNewElement( doc, "field", "aCNoFurnish", "applicationCoborrowerNoFurnish" );
            newApplicant.appendChild( applicationCoborrowerNoFurnish );          
            Element applicationCoborrowerHispanicT = createNewElement( doc, "field", "aCHispanicT", "applicationCoborrowerHispanicT" );
            newApplicant.appendChild( applicationCoborrowerHispanicT );         
            Element applicationCoborrowerIsAmericanIndian = createNewElement( doc, "field", "aCIsAmericanIndian", "applicationCoborrowerIsAmericanIndian" );
            newApplicant.appendChild( applicationCoborrowerIsAmericanIndian );          
            Element applicationCoborrowerIsAsian = createNewElement( doc, "field", "aCIsAsian", "applicationCoborrowerIsAsian" );
            newApplicant.appendChild( applicationCoborrowerIsAsian );          
            Element applicationCoborrowerIsBlack = createNewElement( doc, "field", "aCIsBlack", "applicationCoborrowerIsBlack" );
            newApplicant.appendChild( applicationCoborrowerIsBlack );          
            Element applicationCoborrowerIsPacificIslander = createNewElement( doc, "field", "aCIsPacificIslander", "applicationCoborrowerIsPacificIslander" );
            newApplicant.appendChild( applicationCoborrowerIsPacificIslander );          
            Element applicationCoborrowerIsWhite = createNewElement( doc, "field", "aCIsWhite", "applicationCoborrowerIsWhite" );
            newApplicant.appendChild( applicationCoborrowerIsWhite );          
            Element applicationCoborrowerGender = createNewElement( doc, "field", "aCGender", "applicationCoborrowerGender" );
            newApplicant.appendChild( applicationCoborrowerGender );
            
            
          
              
           
            
            
            
        	Element applicationCoborrowerEmplrName = createNewElement( doc, "field", "acprimaryemplrnm", "applicationCoborrowerEmplrName" );
            newApplicant.appendChild( applicationCoborrowerEmplrName );
            Element applicationCoborrowerEmploymentStartDate = createNewElement( doc, "field", "acprimaryempltstartd", "applicationCoborrowerEmploymentStartDate" );
            newApplicant.appendChild( applicationCoborrowerEmploymentStartDate );
            Element applicationCoborrowerEmployementTitle = createNewElement( doc, "field", "acprimaryjobtitle", "applicationCoborrowerEmployementTitle" );
            newApplicant.appendChild( applicationCoborrowerEmployementTitle );
			
            
            
            
            
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
            
            Element coBorrowerHomePhone = createNewElement( doc, "field", "aCHPhone", "applicationCoborrowerHomePhone" );
            newApplicant.appendChild( coBorrowerHomePhone );
            
            
            Element applicationCoborrowerDecJudgment = createNewElement( doc, "field", "aCDecJudgment", "applicationCoborrowerDecJudgment" );
            newApplicant.appendChild( applicationCoborrowerDecJudgment );
            Element applicationCoborrowerDecBankrupt = createNewElement( doc, "field", "aCDecBankrupt", "applicationCoborrowerDecBankrupt" );
            newApplicant.appendChild( applicationCoborrowerDecBankrupt );
            Element applicationCoborrowerDecForeclosure = createNewElement( doc, "field", "aCDecForeclosure", "applicationCoborrowerDecForeclosure" );
            newApplicant.appendChild( applicationCoborrowerDecForeclosure );          
            Element applicationCoborrowerDecLawsuit = createNewElement( doc, "field", "aCDecLawsuit", "applicationCoborrowerDecLawsuit" );
            newApplicant.appendChild( applicationCoborrowerDecLawsuit );           
            Element applicationCoborrowerDecObligated = createNewElement( doc, "field", "aCDecObligated", "applicationCoborrowerDecObligated" );
            newApplicant.appendChild( applicationCoborrowerDecObligated );           
            Element applicationCoborrowerDecDelinquent = createNewElement( doc, "field", "aCDecDelinquent", "applicationCoborrowerDecDelinquent" );
            newApplicant.appendChild( applicationCoborrowerDecDelinquent );          
            Element applicationCoborrowerDecAlimony = createNewElement( doc, "field", "aCDecAlimony", "applicationCoborrowerDecAlimony" );
            newApplicant.appendChild( applicationCoborrowerDecAlimony );           
            Element applicationCoborrowerDecBorrowing = createNewElement( doc, "field", "aCDecBorrowing", "applicationCoborrowerDecBorrowing" );
            newApplicant.appendChild( applicationCoborrowerDecBorrowing );          
            Element applicationCoborrowerDecEndorser = createNewElement( doc, "field", "aCDecEndorser", "applicationCoborrowerDecEndorser" );
            newApplicant.appendChild( applicationCoborrowerDecEndorser );           
            Element applicationCoborrowerDecCitizen = createNewElement( doc, "field", "aCDecCitizen", "applicationCoborrowerDecCitizen" );
            newApplicant.appendChild( applicationCoborrowerDecCitizen );           
            Element applicationCoborrowerDecResidency = createNewElement( doc, "field", "aCDecResidency", "applicationCoborrowerDecResidency" );
            newApplicant.appendChild( applicationCoborrowerDecResidency );         
            Element applicationCoborrowerDecOcc = createNewElement( doc, "field", "aCDecOcc", "applicationCoborrowerDecOcc" );
            newApplicant.appendChild( applicationCoborrowerDecOcc );
            Element applicationCoborrowerDecPastOwnedPropT = createNewElement( doc, "field", "aCDecPastOwnedPropT", "applicationCoborrowerDecPastOwnedPropT" );
            newApplicant.appendChild( applicationCoborrowerDecPastOwnedPropT ); 
            Element titleTApplicationCoborrowerDecPastOwnedProp = createNewElement( doc, "field", "aCDecPastOwnedPropTitleT", "titleTApplicationCoborrowerDecPastOwnedProp" );
            newApplicant.appendChild( titleTApplicationCoborrowerDecPastOwnedProp );
            Element applicationCoborrowerNoFurnish = createNewElement( doc, "field", "aCNoFurnish", "applicationCoborrowerNoFurnish" );
            newApplicant.appendChild( applicationCoborrowerNoFurnish );          
            Element applicationCoborrowerHispanicT = createNewElement( doc, "field", "aCHispanicT", "applicationCoborrowerHispanicT" );
            newApplicant.appendChild( applicationCoborrowerHispanicT );         
            Element applicationCoborrowerIsAmericanIndian = createNewElement( doc, "field", "aCIsAmericanIndian", "applicationCoborrowerIsAmericanIndian" );
            newApplicant.appendChild( applicationCoborrowerIsAmericanIndian );          
            Element applicationCoborrowerIsAsian = createNewElement( doc, "field", "aCIsAsian", "applicationCoborrowerIsAsian" );
            newApplicant.appendChild( applicationCoborrowerIsAsian );          
            Element applicationCoborrowerIsBlack = createNewElement( doc, "field", "aCIsBlack", "applicationCoborrowerIsBlack" );
            newApplicant.appendChild( applicationCoborrowerIsBlack );          
            Element applicationCoborrowerIsPacificIslander = createNewElement( doc, "field", "aCIsPacificIslander", "applicationCoborrowerIsPacificIslander" );
            newApplicant.appendChild( applicationCoborrowerIsPacificIslander );          
            Element applicationCoborrowerIsWhite = createNewElement( doc, "field", "aCIsWhite", "applicationCoborrowerIsWhite" );
            newApplicant.appendChild( applicationCoborrowerIsWhite );          
            Element applicationCoborrowerGender = createNewElement( doc, "field", "aCGender", "applicationCoborrowerGender" );
            newApplicant.appendChild( applicationCoborrowerGender );
      
           
           /* Element applicationCoborrowerEmpCollection = doc.createElement( "collection" );
            applicationCoborrowerEmpCollection.setAttribute( "id", "aCEmpCollection" );
            Element coBorrowerRecord = doc.createElement( "record" );         
            Element applicationCoborrowerEmpIsCurrent = createNewElement( doc, "field", "IsCurrent", "true" );
            Element applicationCoborrowerEmplrName = createNewElement( doc, "field", "EmplrNm", "applicationCoborrowerEmplrName" );
            Element applicationCoborrowerMontlyIncome = createNewElement( doc, "field", "MonI", "applicationCoborrowerMontlyIncome" );
            Element applicationCoborrowerEmploymentStartDate = createNewElement( doc, "field", "EmplmtStartD", "applicationCoborrowerEmploymentStartDate" );
            Element applicationCoborrowerEmployementTitle = createNewElement( doc, "field", "JobTitle", "applicationCoborrowerEmployementTitle" );           
            coBorrowerRecord.appendChild(applicationCoborrowerEmpIsCurrent);
            coBorrowerRecord.appendChild(applicationCoborrowerEmplrName);
            coBorrowerRecord.appendChild(applicationCoborrowerMontlyIncome);
            coBorrowerRecord.appendChild(applicationCoborrowerEmploymentStartDate);
            coBorrowerRecord.appendChild(applicationCoborrowerEmployementTitle);           
            applicationCoborrowerEmpCollection.appendChild(coBorrowerRecord);
            newApplicant.appendChild( applicationCoborrowerEmpCollection );*/
            

            
        	Element applicationCoborrowerEmplrName = createNewElement( doc, "field", "acprimaryemplrnm", "applicationCoborrowerEmplrName" );
            newApplicant.appendChild( applicationCoborrowerEmplrName );
            Element applicationCoborrowerEmploymentStartDate = createNewElement( doc, "field", "acprimaryempltstartd", "applicationCoborrowerEmploymentStartDate" );
            newApplicant.appendChild( applicationCoborrowerEmploymentStartDate );
            Element applicationCoborrowerEmployementTitle = createNewElement( doc, "field", "acprimaryjobtitle", "applicationCoborrowerEmployementTitle" );
            newApplicant.appendChild( applicationCoborrowerEmployementTitle );
			
        
            
            Element aBExperianScore = createNewElement( doc, "field", "aBExperianScore", "borrowerExperianScore" );
            Element aBEquifax = createNewElement( doc, "field", "aBEquifaxScore", "borrowerEquifaxScore" );
            Element aBTransUnionScore = createNewElement( doc, "field", "aBTransUnionScore", "borrowerTransUnionScore" );
            
            Element aCExperianScore = createNewElement( doc, "field", "aCExperianScore", "ExperianCoborrowerWifeScore" );
            Element aCEquifax = createNewElement( doc, "field", "aCEquifaxScore", "EquifaxCoborrowerWifeScore" );
            Element aCTransUnionScore = createNewElement( doc, "field", "aCTransUnionScore", "TransUnionCoborrowerWifeScore" );
            newApplicant.appendChild( aBExperianScore );
            newApplicant.appendChild( aBEquifax );
            newApplicant.appendChild( aBTransUnionScore );
            newApplicant.appendChild( aCExperianScore );
            newApplicant.appendChild( aCEquifax );
            newApplicant.appendChild( aCTransUnionScore );
            loan.appendChild( newApplicant );
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
       // System.out.println("file"+file);
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


    
    
   /* public static void main(String[] args){
    	try{
    	RestInterceptor  ri = new RestInterceptor();
    	InputStream inputStream = ri.getResource( "save.xml" );
    	XMLProcessor xm = new XMLProcessor();
    	xm.parseXML(inputStream, NewFiConstants.CONSTANT_CONDITION_CO_BORROWER_IS_WIFE_WITH_SSN_BOTH ) ;
    	}catch(Exception e){
    		
    	}
    }*/
    
}
