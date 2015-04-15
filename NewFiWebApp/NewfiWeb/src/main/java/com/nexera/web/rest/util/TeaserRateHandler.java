package com.nexera.web.rest.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;



 
 
public class TeaserRateHandler extends DefaultHandler {
 

	private static final Logger LOG = LoggerFactory.getLogger(TeaserRateHandler.class);

	
    //List to hold TeaserRateResponseVOs object   
	private ArrayList<TeaserRateResponseVO> teaserRateList = new  ArrayList<TeaserRateResponseVO>() ;
    private TeaserRateResponseVO teaserRateVo = new TeaserRateResponseVO();
    private ArrayList<LqbTeaserRateVo> rateVoList = new  ArrayList<LqbTeaserRateVo>() ;
    String tempVal = "";
    String totalClosingCostVal="";
    HashMap<String,String> tempMap = new HashMap<String,String>();
    //getter method for TeaserRateResponseVO list
    public List<TeaserRateResponseVO> getTeaserRateList() {
        return teaserRateList;
    }
   boolean eligibilityFlag = false;
    
    @Override
    public void startElement(String uri, String localName, String qName,
    		Attributes attributes) throws SAXException {
    	
    	
    	
    	//LOG.info("StartElement  qName # "+qName);
    	
    		if(qName.equalsIgnoreCase("Program")) {
    			eligibilityFlag = false;
    			rateVoList = new  ArrayList<LqbTeaserRateVo>() ;
    			//create a new instance of TeaserRateResponseVO
    			teaserRateVo = new TeaserRateResponseVO();
    		
    			if(attributes.getValue("Status") == null ){
    			teaserRateVo.setLoanDuration(attributes.getValue("Name"));
    			eligibilityFlag = true;
    			}
    			
    			if(attributes.getValue("Status") != null && "Eligible".equalsIgnoreCase(attributes.getValue("Status")) ){
    			
    				teaserRateVo.setLoanDuration(attributes.getValue("Name"));
    				eligibilityFlag = true;
    				
    			}
    			
    			
    		}
    		
    		if(qName.equalsIgnoreCase("RateOption") && eligibilityFlag== true) {
    			LqbTeaserRateVo rateVo = new LqbTeaserRateVo();
    				
    			
    			
    			if(attributes.getValue("TotalClosingCost").toString().contains("(")){
    				//totalClosingCostVal= attributes.getValue("Rate");
    				tempMap.put("Rate",attributes.getValue("Rate"));
    				tempMap.put("APR",attributes.getValue("APR"));
    				tempMap.put("lLpTemplateId",attributes.getValue("lLpTemplateId"));
    				tempMap.put("Payment",attributes.getValue("Payment"));
    				tempMap.put("Point",attributes.getValue("Point"));
    				
    				
    				
    				
    				LOG.info("TotalClosingCost contains ( "+totalClosingCostVal);
    			}
    			else{
    				LOG.info("TotalClosingCost does not contains (");
    				//if(!"".equalsIgnoreCase(totalClosingCostVal))
    				if(tempMap !=null && tempMap.size() !=0)
    				{
    				LOG.info("TotalClosingCost is not equal to space hence setting cost as 0");
    				LqbTeaserRateVo tempRateVo = new LqbTeaserRateVo();
    				tempRateVo.setClosingCost("0");
    				tempRateVo.setTeaserRate(tempMap.get("Rate"));
    				tempRateVo.setAPR(tempMap.get("APR"));
    				tempRateVo.setlLpTemplateId(tempMap.get("lLpTemplateId"));
    				tempRateVo.setPayment(tempMap.get("Payment"));
    				tempRateVo.setPoint(tempMap.get("Point"));
    				
    				LOG.info("Setting rate VO list tempRateVo values is "+tempRateVo.getClosingCost()+"  "+tempRateVo.getTeaserRate());
    				
    				rateVoList.add(tempRateVo);
    					
    				tempMap.clear();
    				
    					rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
    					rateVo.setTeaserRate(attributes.getValue("Rate"));
    					rateVo.setAPR(attributes.getValue("APR"));
    	    			rateVo.setlLpTemplateId(attributes.getValue("lLpTemplateId"));
    	    			rateVo.setPayment(attributes.getValue("Payment"));
    	    			rateVo.setPoint(attributes.getValue("Point"));
    					LOG.info("Setting rate VO list rateVo values is "+rateVo.getClosingCost()+"  "+rateVo.getTeaserRate());
    					rateVoList.add(rateVo);
    					totalClosingCostVal="";
    					
    					
    					
    					
    				}else{
    					LOG.info("Inside else"); 
    				rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
    				rateVo.setTeaserRate(attributes.getValue("Rate"));
    				rateVo.setAPR(attributes.getValue("APR"));
        			rateVo.setlLpTemplateId(attributes.getValue("lLpTemplateId"));
        			rateVo.setPayment(attributes.getValue("Payment"));
        			rateVo.setPoint(attributes.getValue("Point"));
    				if(rateVoList.size()<=5)
        			rateVoList.add(rateVo);
    				}
    			}
    			
    			
    			
    			
    			
    			
    		
    			
    			
    			
    			
    		}
    	}
    
    
    
    
    
    
 
    @Override
    public void endElement(String uri, String localName,
    		String qName) throws SAXException {
    	//LOG.info("end Element  qName # "+qName);
    		if(qName.equalsIgnoreCase("Program")) {
    			
    			if(rateVoList!=null && rateVoList.size()>0){
    			//add it to the list
    			teaserRateVo.setRateVO(rateVoList);
    			teaserRateList.add(teaserRateVo);
    			}
       		}
   		}

 
 
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
}