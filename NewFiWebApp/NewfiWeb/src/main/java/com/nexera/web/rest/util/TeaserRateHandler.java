package com.nexera.web.rest.util;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.LqbTeaserRateVo;
import com.nexera.common.vo.TeaserRateResponseVO;

 
 
public class TeaserRateHandler extends DefaultHandler {
 
	private static final Logger LOG = LoggerFactory.getLogger(TeaserRateHandler.class);
	
    //List to hold TeaserRateResponseVOs object   
	private ArrayList<TeaserRateResponseVO> teaserRateList = new  ArrayList<TeaserRateResponseVO>() ;
    private TeaserRateResponseVO teaserRateVo = new TeaserRateResponseVO();
    private ArrayList<LqbTeaserRateVo> rateVoList = new  ArrayList<LqbTeaserRateVo>() ;
    String tempVal = "";
    
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
    			rateVo.setTeaserRate(attributes.getValue("Rate"));
    			rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
    			rateVoList.add(rateVo);
    			
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