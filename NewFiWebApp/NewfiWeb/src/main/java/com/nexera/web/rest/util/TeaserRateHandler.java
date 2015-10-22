package com.nexera.web.rest.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;




 
 
public class TeaserRateHandler extends DefaultHandler {
 

	

	
    //List to hold TeaserRateResponseVOs object   
	private ArrayList<TeaserRateResponseVO> teaserRateList = new  ArrayList<TeaserRateResponseVO>() ;
    private TeaserRateResponseVO teaserRateVo = new TeaserRateResponseVO();
    private ArrayList<LqbTeaserRateVo> rateVoList = new  ArrayList<LqbTeaserRateVo>() ;
    String tempVal = "";
    String totalClosingCostVal="";
    HashMap<String,String> tempMap = new HashMap<String,String>();
    LqbTeaserRateVo rateVo;
    boolean bypassFlag = false;
    boolean eligibilityFlag = false;
    private ArrayList<String> loanPrograms = new ArrayList<String>();
    
   public  TeaserRateHandler(){

        loanPrograms.add("15 YR FIXED CONFORMING");
    	loanPrograms.add("20 YR FIXED CONFORMING");
    	loanPrograms.add("30 YR FIXED CONFORMING");
    	loanPrograms.add("5/1 1 YR LIBOR CONFORMING  2/2/5 30 YR ARM");
    	loanPrograms.add("7/1 1 YR LIBOR CONFORMING  5/2/5 30 YR ARM");
    	 loanPrograms.add("15 YR FIXED NONCONFORMING");
    	 loanPrograms.add("20 YR FIXED NONCONFORMING");
      	loanPrograms.add("30 YR FIXED NONCONFORMING");
      	loanPrograms.add("5/1 1 YR LIBOR NONCONFORMING  2/2/5 30 YR ARM");
      	loanPrograms.add("7/1 1 YR LIBOR NONCONFORMING  5/2/5 30 YR ARM");
      	
    	
    	
    	//loanPrograms.add("7/1 1 YR LIBOR CONFORMING  5/2/5 30 YR ARM");
        
    }
    
    
    
    
    
    
   
    //getter method for TeaserRateResponseVO list
    public List<TeaserRateResponseVO> getTeaserRateList() {
    	
    	
    	
        return teaserRateList;
    }
 
   
    
    @Override
    public void startElement(String uri, String localName, String qName,
    		Attributes attributes) throws SAXException {
    	
    	//System.out.println("StartElement  qName # "+qName);
    	
    	
    		if(qName.equalsIgnoreCase("Program") &&  loanPrograms.contains(attributes.getValue("Name"))) {
    			eligibilityFlag = false;
    			rateVoList = new  ArrayList<LqbTeaserRateVo>() ;
    			//create a new instance of TeaserRateResponseVO
    			teaserRateVo = new TeaserRateResponseVO();
    			//teaserRateVo.setLoanDuration(attributes.getValue("Name"));
    			
    			
    			if(attributes.getValue("Status") == null ){
        			teaserRateVo.setLoanDuration(attributes.getValue("Name"));
        			eligibilityFlag = true;
        			}
        			
        			if(attributes.getValue("Status") != null && "Eligible".equalsIgnoreCase(attributes.getValue("Status")) ){
        			
        				teaserRateVo.setLoanDuration(attributes.getValue("Name"));
        				eligibilityFlag = true;
        				
        			}
    		}
    		
    		if((qName.equalsIgnoreCase("RateOption") || qName.equalsIgnoreCase("ClosingCost")) && eligibilityFlag== true ) {
    	 			
    			
    			if(qName.equalsIgnoreCase("RateOption")){
    				
    			if(attributes.getValue("TotalClosingCost").toString().contains("(")){
    				//totalClosingCostVal= attributes.getValue("Rate");
    				tempMap.put("Rate",attributes.getValue("Rate"));
    				tempMap.put("APR",attributes.getValue("APR"));
    				tempMap.put("lLpTemplateId",attributes.getValue("lLpTemplateId"));
    				tempMap.put("Payment",attributes.getValue("Payment"));
    				tempMap.put("Point",attributes.getValue("Point"));
    				
    				bypassFlag = true;
    				
    				
    			//	System.out.println("TotalClosingCost contains ( "+totalClosingCostVal);
    			}
    			else{
    				rateVo = new LqbTeaserRateVo();
    				bypassFlag = false;
    			//	System.out.println("TotalClosingCost does not contains (");
    				//if(!"".equalsIgnoreCase(totalClosingCostVal))
    				if(tempMap !=null && tempMap.size() !=0)
    				{
    			//	System.out.println("TotalClosingCost is not equal to space hence setting cost as 0");
    				LqbTeaserRateVo tempRateVo = new LqbTeaserRateVo();
    				tempRateVo.setClosingCost("$0.00");
    				tempRateVo.setTeaserRate(tempMap.get("Rate"));
    				tempRateVo.setAPR(tempMap.get("APR"));
    				tempRateVo.setlLpTemplateId(tempMap.get("lLpTemplateId"));
    				tempRateVo.setPayment(tempMap.get("Payment"));
    				tempRateVo.setPoint(tempMap.get("Point"));
    				tempRateVo.setLenderFee813(tempMap.get("lenderFee813"));
    				tempRateVo.setCreditOrCharge802(tempMap.get("creditOrCharge802"));
    				tempRateVo.setAppraisalFee804(tempMap.get("appraisalFee804"));
    				tempRateVo.setCreditReport805(tempMap.get("creditReport805"));
    				tempRateVo.setFloodCertification807(tempMap.get("floodCertification807"));
    				tempRateVo.setWireFee812(tempMap.get("wireFee812"));
    				tempRateVo.setLendersTitleInsurance1104(tempMap.get("lendersTitleInsurance1104"));
    				tempRateVo.setClosingEscrowFee1102(tempMap.get("closingEscrowFee1102"));
    				tempRateVo.setRecordingFees1201(tempMap.get("recordingFees1201"));
    				tempRateVo.setRecordingFees1202(tempMap.get("recordingFees1202"));
    				tempRateVo.setCityCountyTaxStamps1204(tempMap.get("cityCountyTaxStamps1204"));
    				tempRateVo.setInterest901(tempMap.get("interest901"));
    				tempRateVo.setHazIns903(tempMap.get("hazIns903"));
    				tempRateVo.setTaxResrv1004(tempMap.get("taxResrv1004"));
    				tempRateVo.setHazInsReserve1002(tempMap.get("hazInsReserve1002"));
    				tempRateVo.setOwnersTitleInsurance1103(tempMap.get("ownersTitleInsurance1103"));
    								
    			//	System.out.println("Setting rate VO list tempRateVo values is "+tempRateVo.getClosingCost()+"  "+tempRateVo.getTeaserRate());
    				tempRateVo= emptyClosingCost(tempRateVo);
    				rateVoList.add(tempRateVo);
    					
    				tempMap.clear();
    				
    					rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
    					rateVo.setTeaserRate(attributes.getValue("Rate"));
    					rateVo.setAPR(attributes.getValue("APR"));
    	    			rateVo.setlLpTemplateId(attributes.getValue("lLpTemplateId"));
    	    			rateVo.setPayment(attributes.getValue("Payment"));
    	    			rateVo.setPoint(attributes.getValue("Point"));
    					//System.out.println("Setting rate VO list rateVo values is "+rateVo.getClosingCost()+"  "+rateVo.getTeaserRate());
    					//rateVoList.add(rateVo);
    					totalClosingCostVal="";
    					
    					
    					
    					
    				}else{
    					//System.out.println("Inside else"); 
    				rateVo.setClosingCost(attributes.getValue("TotalClosingCost"));
    				rateVo.setTeaserRate(attributes.getValue("Rate"));
    				rateVo.setAPR(attributes.getValue("APR"));
        			rateVo.setlLpTemplateId(attributes.getValue("lLpTemplateId"));
        			rateVo.setPayment(attributes.getValue("Payment"));
        			rateVo.setPoint(attributes.getValue("Point"));
        				}
    					}
    			
    			}
    					if(qName.equalsIgnoreCase("ClosingCost") && bypassFlag == false){
        			
    				rateVo = setClosingCost(attributes,rateVo);
    								
    			}else{
    				
    				tempMap = setClosingCostTempMapValue(attributes,tempMap);
    			} 			
    	   		}
    		 	}
    
   
	
	
private LqbTeaserRateVo setClosingCost(Attributes attributes, LqbTeaserRateVo rateVO){
	

    if("813".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setLenderFee813(attributes.getValue("Amount"));
	
	
	if("802".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setCreditOrCharge802(attributes.getValue("Amount"));
	
	
	if("804".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setAppraisalFee804(attributes.getValue("Amount"));
	
	
	if("805".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setCreditReport805(attributes.getValue("Amount"));
	
	
	if("807".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setFloodCertification807(attributes.getValue("Amount"));
	
	
	if("812".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setWireFee812(attributes.getValue("Amount"));
	
	
	if("1104".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setLendersTitleInsurance1104(attributes.getValue("Amount"));
	
	
	if("1102".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setClosingEscrowFee1102(attributes.getValue("Amount"));
	
	
	if("1201".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setRecordingFees1201(attributes.getValue("Amount"));
	
	if("1202".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setRecordingFees1202(attributes.getValue("Amount"));
	
	if("1204".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setCityCountyTaxStamps1204(attributes.getValue("Amount"));
	
	
	if("901".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setInterest901(attributes.getValue("Amount"));
	
	
	if("903".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setHazIns903(attributes.getValue("Amount"));
	
	
	if("1004".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setTaxResrv1004(attributes.getValue("Amount"));
	
	
	if("1002".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setHazInsReserve1002(attributes.getValue("Amount"));
	
	if("1103".equalsIgnoreCase(attributes.getValue("Line")))
		rateVo.setOwnersTitleInsurance1103(attributes.getValue("Amount"));

return rateVO;

}
    
    
    
    
    
private HashMap<String,String> setClosingCostTempMapValue(Attributes attributes, HashMap<String,String> tempMap){
	
	if("813".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("lenderFee813",attributes.getValue("Amount"));
			
	
	if("801".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("loanOriginationFee801",attributes.getValue("Amount"));
	
	
	
	if("802".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("creditOrCharge802",attributes.getValue("Amount"));
		
	
	
	if("804".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("appraisalFee804",attributes.getValue("Amount"));
		
	
	
	if("805".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("creditReport805",attributes.getValue("Amount"));
		
	
	
	if("807".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("floodCertification807",attributes.getValue("Amount"));
	
	
	if("812".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("wireFee812",attributes.getValue("Amount"));
		
	
	
	if("1104".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("lendersTitleInsurance1104",attributes.getValue("Amount"));
		
	
	
	if("1102".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("closingEscrowFee1102",attributes.getValue("Amount"));
		
	
	
	if("1201".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("recordingFees1201",attributes.getValue("Amount"));
		
	if("1202".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("recordingFees1202",attributes.getValue("Amount"));
		
	
	
	if("1204".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("cityCountyTaxStamps1204",attributes.getValue("Amount"));
		
	
	
	if("901".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("interest901",attributes.getValue("Amount"));
		
	
	
	if("903".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("hazIns903",attributes.getValue("Amount"));
		
	
	
	if("1004".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("taxResrv1004",attributes.getValue("Amount"));
		
	
	
	if("1002".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("hazInsReserve1002",attributes.getValue("Amount"));
		
	
	if("1103".equalsIgnoreCase(attributes.getValue("Line")))
		tempMap.put("ownersTitleInsurance1103",attributes.getValue("Amount"));
	
	
	return tempMap;
	
} 
    
    
    
    
    
 
    @Override
    public void endElement(String uri, String localName,
    		String qName) throws SAXException {
    	//System.out.println("end Element  qName # "+qName);
    	
    	if(qName.equalsIgnoreCase("RateOption") && rateVo != null && rateVoList!=null && rateVoList.size()<=5) {
    		rateVo= emptyClosingCost(rateVo);
    		rateVoList.add(rateVo);
    	}
    	
    	
    	if(qName.equalsIgnoreCase("Program")) {
    			//add it to the list
    			rateVo = null;
    			eligibilityFlag= false;
    			if(rateVoList!=null && rateVoList.size()>0){
    			teaserRateVo.setRateVO(rateVoList);
    			teaserRateList.add(teaserRateVo);
    			rateVoList=null;
    			}
       		}
   		}

 
    
    
   private LqbTeaserRateVo emptyClosingCost(LqbTeaserRateVo rateVo){
	   
	   
	   
	    if(rateVo.getLenderFee813() == null)
	    rateVo.setLenderFee813("$0.00");
	    if(rateVo.getCreditOrCharge802() == null)
	    rateVo.setCreditOrCharge802("$0.00");
	    if(rateVo.getAppraisalFee804() == null)
	    rateVo.setAppraisalFee804("$0.00");
	    if(rateVo.getCreditReport805() == null)
	    rateVo.setCreditReport805("$0.00");
	    if(rateVo.getFloodCertification807() == null)
	    rateVo.setFloodCertification807("$10.00");
	    if(rateVo.getWireFee812() == null)
	    rateVo.setWireFee812("$0.00");
	    if(rateVo.getLendersTitleInsurance1104() == null)
	    rateVo.setLendersTitleInsurance1104("$0.00");
	    if(rateVo.getClosingEscrowFee1102() == null)
	    rateVo.setClosingEscrowFee1102("$0.00");
	    if(rateVo.getRecordingFees1201() == null)
	    rateVo.setRecordingFees1201("125");
	    if(rateVo.getCityCountyTaxStamps1204() == null)
	    rateVo.setCityCountyTaxStamps1204("$0.00");
	    if(rateVo.getInterest901() == null)
	    rateVo.setInterest901("$0.00");
	    if(rateVo.getHazIns903() == null)
	    rateVo.setHazIns903("$0.00");
	    if(rateVo.getTaxResrv1004() == null)
	    rateVo.setTaxResrv1004("$0.00");
	    if(rateVo.getHazInsReserve1002() == null)
	    rateVo.setHazInsReserve1002("$0.00");
		if(rateVo.getOwnersTitleInsurance1103() == null)
	    rateVo.setOwnersTitleInsurance1103("$0.00");
	   
	   
	   
	   return rateVo;
   }
 
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
}