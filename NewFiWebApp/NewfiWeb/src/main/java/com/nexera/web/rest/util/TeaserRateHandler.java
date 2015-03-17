package com.nexera.web.rest.util;
import java.util.ArrayList;
import java.util.Iterator;
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
    //getter method for TeaserRateResponseVO list
    public List<TeaserRateResponseVO> getTeaserRateList() {
        return teaserRateList;
    }
 
    @Override
    public void startElement(String uri, String localName, String qName,
    		Attributes attributes) throws SAXException {
    	
    	LOG.info("StartElement  qName # "+qName);
    	
    		if(qName.equalsIgnoreCase("Program")) {
    			rateVoList = new  ArrayList<LqbTeaserRateVo>() ;
    			//create a new instance of TeaserRateResponseVO
    			teaserRateVo = new TeaserRateResponseVO();
    			teaserRateVo.setLoanDuration(attributes.getValue("Name"));
    		}
    		
    		if(qName.equalsIgnoreCase("RateOption")) {
    			LqbTeaserRateVo rateVo = new LqbTeaserRateVo();
    			
    			LOG.info("TotalClosingCost  value # "+attributes.getValue("TotalClosingCost"));
    			
    			if(attributes.getValue("TotalClosingCost").toString().contains("(")){
    				totalClosingCostVal= attributes.getValue("Rate");
    				LOG.info("TotalClosingCost contains ( "+totalClosingCostVal);
    			}
    			else{
    				LOG.info("TotalClosingCost does not contains (");
    				if(!"".equalsIgnoreCase(totalClosingCostVal))
    				{LOG.info("TotalClosingCost is not equal to space hence setting cost as 0");
    				LqbTeaserRateVo tempRateVo = new LqbTeaserRateVo();
    				tempRateVo.setClosingCost("0");
    				tempRateVo.setTeaserRate(totalClosingCostVal);
    				
    				LOG.info("Setting rate VO list tempRateVo values is "+tempRateVo.getClosingCost()+"  "+tempRateVo.getTeaserRate());
    				
    				rateVoList.add(tempRateVo);
    					
    				
    					rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
        				rateVo.setTeaserRate(attributes.getValue("Rate"));
        				LOG.info("Setting rate VO list rateVo values is "+rateVo.getClosingCost()+"  "+rateVo.getTeaserRate());
        				rateVoList.add(rateVo);
    					totalClosingCostVal="";
    					
    					
    					
    					
    				}else{
    					LOG.info("Inside else"); 
    				rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
    				rateVo.setTeaserRate(attributes.getValue("Rate"));
    				rateVoList.add(rateVo);
    				}
    			}
    			
    			
    			
    			
    		}
    	}
    
    
    
 
    @Override
    public void endElement(String uri, String localName,
    		String qName) throws SAXException {
    	LOG.info("end Element  qName # "+qName);
    		if(qName.equalsIgnoreCase("Program")) {
    			//add it to the list
    			teaserRateVo.setRateVO(rateVoList);
    			teaserRateList.add(teaserRateVo);
       		}
   		}

 
 
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
}