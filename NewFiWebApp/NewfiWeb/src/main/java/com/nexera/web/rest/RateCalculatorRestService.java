package com.nexera.web.rest;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.common.vo.lqb.TeaserRateVO;
import com.nexera.web.rest.util.TeaserRateHandler;





@RestController
@RequestMapping(value = "/calculator")
public class RateCalculatorRestService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RateCalculatorRestService.class);
	
	
	@RequestMapping(value = "/findteaseratevalue", method = RequestMethod.POST)

	public @ResponseBody String getTeaserRate(String teaseRate) {		
		Gson gson = new Gson();
		LOG.info("findteaseratevalue - inout xml is"+teaseRate);
		TeaserRateVO teaserRateVO = gson.fromJson(teaseRate, TeaserRateVO.class); 
		LOG.info("teaserRateVO"+teaserRateVO.getCity());
		LOG.info("teaserRateVO"+teaserRateVO.getCurrentAddress());
		LOG.info("teaserRateVO"+teaserRateVO.getCurrentMortgageBalance());
		LOG.info("teaserRateVO"+teaserRateVO.getCreditscore());
		
		String requestXML = CreateXmlForTeaserRate(teaserRateVO);
		LOG.info("Invoking rest service with with Json Input "+CreateTeaserRateJson(requestXML,"RunQuickPricer"));
		List<TeaserRateResponseVO> teaserRateResponseVO = invokeRest(CreateTeaserRateJson(requestXML,"RunQuickPricer").toString());
		LOG.info("Json resonse returned to JSP is"+gson.toJson(teaserRateResponseVO));
		return gson.toJson(teaserRateResponseVO);
	}
	
	
	public JSONObject  CreateTeaserRateJson(String requestXML,String opName){
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sXmlData",requestXML);		
			json.put("opName",opName );
			json.put("loanVO", jsonChild); 
		} catch (JSONException e) {
			e.printStackTrace();
		}  
			return json;
	}
	
	
	
	public String CreateXmlForTeaserRate(TeaserRateVO teaserRateVO)
	{
		String loanAmount ;
		String loanPurpose;
		if("takeCashOut".equalsIgnoreCase(teaserRateVO.getRefinanceOption())){
			
			loanAmount = teaserRateVO.getCashTakeOut()+teaserRateVO.getCurrentMortgageBalance();
			LOG.info("Inside cash takeout , total loan amount is "+loanAmount);
		}else{
			loanAmount = teaserRateVO.getCurrentMortgageBalance();
		}
		
      if("takeCashOut".equalsIgnoreCase(teaserRateVO.getRefinanceOption()) || "lowerMonthlyPayment".equalsIgnoreCase(teaserRateVO.getRefinanceOption()) || "payOffMortgage".equalsIgnoreCase(teaserRateVO.getRefinanceOption())){
			
	        loanPurpose = "1";
			LOG.info("Inside cash takeout , total loan amount is "+loanAmount);
		}else{
			loanPurpose = "0";
		}
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<loan "
				+ "sHouseValPe="+"\""+teaserRateVO.getHomeWorthToday()+"\""
				+ " "
				+ "sLAmtCalcPe="+"\""+loanAmount+"\""
				+ " "
				+ "sLPurposeTPe="+"\""+"1"+"\""
				+ " "
				+ "sProdFilterTerm30Yrs="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterTerm15Yrs="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterTerm20Yrs="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterTermOther="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterFinMeth3YrsArm="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterFinMeth5YrsArm="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterFinMeth7YrsArm="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterFinMeth10YrsArm="+"\""+"true"+"\""
				+ " "
				+ "sProdFilterFinMethFixed="+"\""+"true"+"\""
				+ " "
				+ "sProdIncludeNormalProc="+"\""+"true"+"\""
				+ " "
				+ "sCreditScoreType1="+"\""+"750"+"\""
				+ " "
				+ "sSpStatePe="+"\""+teaserRateVO.getState()+"\""
				+ " "
				+ "sSpCounty="+"\""+teaserRateVO.getCity()+"\""
				+ " "
				+ "sProdRLckdDays="+"\""+"30"+"\""
				+ " "
				+ "sSpZip="+"\""+teaserRateVO.getZipCode()+"\""
				+ " "
				+ "sOccTPe="+"\""+"0"+"\""
				+ " "
				+ "sProdSpT="+"\""+"0"+"\""
				+ " "
				+ "sProdImpound="+"\""+"false"+"\""
				+ " "
				+ "sProdIncludeMyCommunityProc="+"\""+"false"+"\""
				+ " "
				+ "sProdIncludeHomePossibleProc="+"\""+"false"+"\""
				+ " "
				+ "sProdIsDuRefiPlus="+"\""+"false"+"\""
				+ " "
				+ "sProdIncludeFHATotalProc="+"\""+"true"+"\""
				+ " "
				+ "sProdIncludeVAProc="+"\""+"true"+"\""
				+ "/>";		
		return xml;
		
	}
	
	
	
	
	
	
	
	public List<TeaserRateResponseVO> invokeRest(String appFormData){
		
		LOG.info("Invoking rest Service with Input "+appFormData);
		List<TeaserRateResponseVO> teaserRateList = null;
		
		   try { 
			    HttpHeaders headers = new HttpHeaders();
		        HttpEntity request= new HttpEntity(appFormData, headers );
		        RestTemplate restTemplate = new RestTemplate();
		        String returnedUser = restTemplate.postForObject( "http://localhost:8282/loanCall", request, String.class);
		        JSONObject jsonObject = new JSONObject(returnedUser);
		        LOG.info("Response Returned from Rest Service is"+jsonObject.get("responseMessage").toString());
		        teaserRateList = parseLqbResponse(jsonObject.get("responseMessage").toString());
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("error in post entity");
	           
	        }
		   
		   return teaserRateList;
	}
	
	
	
	private  List<TeaserRateResponseVO> parseLqbResponse(String lqbTeaserRateResponse) {

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
		     TeaserRateHandler handler = new TeaserRateHandler();
			//parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(lqbTeaserRateResponse)), handler);
			List<TeaserRateResponseVO> teaserRateList  = handler.getTeaserRateList();	
	        for(TeaserRateResponseVO teaserRateResponseVO : teaserRateList)
	        { LOG.info("Program Name "+teaserRateResponseVO.getLoanDuration());
	        
	            Iterator<LqbTeaserRateVo> itr = teaserRateResponseVO.getRateVO().iterator();
	            while(itr.hasNext()){
	            	LqbTeaserRateVo resultVo = itr.next();
	            	LOG.info("Teaser Rate " + resultVo.getTeaserRate() +"  Closing Cost is "+resultVo.getClosingCost());
	            	}
	            return teaserRateList;
	        }

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return null;
	}


	
	

}
