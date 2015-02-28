package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.core.service.NeedsListService;

@Component
@Transactional
public class NeedsListServiceImpl implements NeedsListService{

	@Autowired
	NeedsDao needsDao;
	@Autowired
	LoanDao loanDao;
	
	public LinkedHashMap<String, ManagerNeedVo> getMasterNeedsListDirectory() {
		List<NeedsListMaster> needs=(List<NeedsListMaster>)needsDao.getMasterNeedsList(false);
		LinkedHashMap<String, ManagerNeedVo> needsDirectory=new LinkedHashMap<String,ManagerNeedVo>();
		for(NeedsListMaster need:needs){
			ManagerNeedVo managerNeedVo=new ManagerNeedVo(need);
			needsDirectory.put(need.getId()+"",managerNeedVo);
		}
		return needsDirectory;
	}
	
	public List<ManagerNeedVo> getLoansNeedsList(int loanId) throws Exception{
		try {
			List<LoanNeedsList> loanNeeds=needsDao.getLoanNeedsList(loanId);
			//TODO Need Loan Details
			// TODO Fetch List of Needs List from database for the loan if list is not null and length greater then 0 then those items need to be selected
			List<ManagerNeedVo> result=new ArrayList<ManagerNeedVo>();
			if(loanNeeds.size()>0){
				List<NeedsListMaster> needs=(List<NeedsListMaster>)needsDao.getMasterNeedsList(false);
				//List<NeedsListMaster> needs=needsDao.loadAll(NeedsListMaster.class);
				LinkedHashMap<String, LoanNeedsList> needsDir=new LinkedHashMap<String,LoanNeedsList>();
				for(LoanNeedsList loanNeed:loanNeeds){
					needsDir.put(loanNeed.getNeedsListMaster().getId()+"", loanNeed);
				}
				for(NeedsListMaster need:needs){
					ManagerNeedVo needVo=new ManagerNeedVo(need);
					if(needsDir.containsKey(need.getId()+"")){
						needVo.setIsChecked(true);
					}else{
						needVo.setIsChecked(false);
					}
					result.add(needVo);
				}
				List<NeedsListMaster> customNeeds=(List<NeedsListMaster>)needsDao.getMasterNeedsList(true);
				
				for(NeedsListMaster need:customNeeds){
					if(needsDir.containsKey(need.getId()+"")){
						ManagerNeedVo needVo=new ManagerNeedVo(need);
						needVo.setIsChecked(true);
						result.add(needVo);
					}
				}
			}else{
				LoanAppForm loanAppForm=loanDao.getLoanAppForm(loanId);
				if(loanAppForm==null){
					throw new Exception("Loan Details Not found");
				}
				LinkedHashMap<String, ManagerNeedVo> needsList=getMasterNeedsListDirectory();
				if(needsList.size()<36){
					throw new Exception("Insufficient Data");
				}
				
				if(loanAppForm.getMaritalStatus().equals("Divorced")){
					//#1
					{
						ManagerNeedVo managerNeedVo=needsList.get("1");
						managerNeedVo.setIsChecked(true);
					}
				}else if(loanAppForm.getMaritalStatus().equals("Settlement")){
					//#4
					{
						ManagerNeedVo managerNeedVo=needsList.get("4");
						managerNeedVo.setIsChecked(true);
					}
				}
				if(loanAppForm.getReceiveAlimonyChildSupport()){
					//#3,
					ManagerNeedVo managerNeedVo=needsList.get("3");
					managerNeedVo.setIsChecked(true);
				}
		
				if(loanAppForm.getSecondMortgage()){
					//#7
					ManagerNeedVo managerNeedVo=needsList.get("7");
					managerNeedVo.setIsChecked(true);
					if(!loanAppForm.getPaySecondMortgage()){
						//#13
						ManagerNeedVo managerNeedVo1=needsList.get("13");
						managerNeedVo.setIsChecked(true);
					}
				} 
				if(loanAppForm.getHomeToSell()){
					//#10
					ManagerNeedVo managerNeedVo=needsList.get("10");
					managerNeedVo.setIsChecked(true);
				}
				if(loanAppForm.getOwnsOtherProperty()){
					//#12
					ManagerNeedVo managerNeedVo=needsList.get("12");
					managerNeedVo.setIsChecked(true);
					if(loanAppForm.getRentedOtherProperty()){
						//#31
						ManagerNeedVo managerNeedVo32=needsList.get("32");
						managerNeedVo.setIsChecked(true);
					}
				}
				if(loanAppForm.getHomeRecentlySold()){
					//#15
					ManagerNeedVo managerNeedVo=needsList.get("15");
					managerNeedVo.setIsChecked(true);
				}
				if(loanAppForm.getHoaDues()){
					//#16
					ManagerNeedVo managerNeedVo=needsList.get("16");
					managerNeedVo.setIsChecked(true);
				}
				if(loanAppForm.getLoanTypeMaster().getId()==1){
					//Purchase type
					//#5,#11,#14
					needsList.get("5").setIsChecked(true);
					needsList.get("11").setIsChecked(true);
					needsList.get("14").setIsChecked(true);
					if(loanAppForm.getPropertyTypeMaster().getId()==2){
						//Renting Purpose
						//#8
						needsList.get("8").setIsChecked(true);
					}
				}else if(loanAppForm.getLoanTypeMaster().getId()==2||loanAppForm.getLoanTypeMaster().getId()==3){
					//All Refinance
					//#6
					needsList.get("6").setIsChecked(true);
					if(loanAppForm.getLoanTypeMaster().getId()==2){
						//#9
						needsList.get("9").setIsChecked(true);
					}
				}
				if(loanAppForm.getEmployed()){
					//#17,#18,
					needsList.get("17").setIsChecked(true);
					needsList.get("18").setIsChecked(true);
				}
				if(loanAppForm.getSsIncomeOrDisability()){
					//#19,#20,#21
					needsList.get("19").setIsChecked(true);
					needsList.get("20").setIsChecked(true);
					needsList.get("21").setIsChecked(true);
				}
				if(loanAppForm.getSelfEmployed()){
					//#20,#21,#24,#25,#26
					needsList.get("20").setIsChecked(true);
					needsList.get("21").setIsChecked(true);
					needsList.get("24").setIsChecked(true);
					needsList.get("25").setIsChecked(true);
					needsList.get("26").setIsChecked(true);
				}
				if(loanAppForm.getPensionOrRetirement()){
					//#20,#21
					needsList.get("20").setIsChecked(true);
					needsList.get("21").setIsChecked(true);
				}
				//#22,#28,#33
				needsList.get("22").setIsChecked(true);
				needsList.get("28").setIsChecked(true);
				needsList.get("33").setIsChecked(true);
				
				result=new ArrayList<ManagerNeedVo>(needsList.values());
				// TODO code to Apply Rules comes here
				
				
				
				
		//		for(NeedsListMaster need:needs){
		//			if(need.getMandatory()==1){
		//				need.setSelected(1);
		//			}else if(need.getMandatory()==2){
		//				need.setSelected(0);
		//			}else if(need.getLoanType()==loan.getLoan_type()){
		//				need.setSelected(1);
		//			}
		//		}
				
			}
			
			
//			Collections.sort(result,new Comparator<ManagerNeedVo>() {
//				 @Override
//				    public int compare(ManagerNeedVo e1, ManagerNeedVo e2) {
//				        if(e1.getId().intValue() < e2.getId().intValue()){
//				            return 1;
//				        } else {
//				            return -1;
//				        }
//				    }
//			});
			
			return result;
		
		} catch (NoRecordsFetchedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int saveLoanNeeds(int loanId,List<Integer> needsList){
		LinkedHashMap<String, LoanNeedsList> existingNeeds=new LinkedHashMap<String,LoanNeedsList>();
		List<LoanNeedsList> ExistingNeedsList = null;
		try {
			ExistingNeedsList = needsDao.getLoanNeedsList(loanId);
		} catch (NoRecordsFetchedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(LoanNeedsList need:ExistingNeedsList){
			existingNeeds.put(need.getNeedsListMaster().getId()+"", need);
		}
		List<LoanNeedsList> needs=new ArrayList<LoanNeedsList>();
		try{
		for(Integer needId:needsList){
			LoanNeedsList need=new LoanNeedsList();
			Loan loan=new Loan();
			loan.setId(loanId);
			NeedsListMaster needListMaster=new NeedsListMaster();
			needListMaster.setId(needId.intValue());
			need.setLoan(loan);
			need.setNeedsListMaster(needListMaster);
			need.setDeleted(false);
			need.setSystemAction(true);
			need.setActive(true);
			need.setComments("");
			need.setFileId("");
			need.setFileUrl("");
			need.setMandatory(true);
			need.setSystemAction(true);
			
			if(existingNeeds.containsKey(needId.intValue()+"")){
				existingNeeds.remove(needId.intValue()+"");
			}else{
				try{
				needsDao.save(need);
				}catch(DatabaseException e){
					
				}
			}
		}
		System.out.println("4");
		for(LoanNeedsList need:existingNeeds.values()){
			needsDao.deleteLoanNeed(need);
		}
		System.out.println("5");
		}catch(Exception e){
			return 0;
		}
		return 1;
	}
	@Override
	public List<ManagerNeedVo> getNeedsListMaster(boolean isCustom){
		List<NeedsListMaster> customNeeds=needsDao.getMasterNeedsList(isCustom);
		List<ManagerNeedVo> result=new ArrayList<ManagerNeedVo>();
		for(NeedsListMaster need:customNeeds){
			ManagerNeedVo needVo=new ManagerNeedVo(need);
			result.add(needVo);
		}
		return result;
	}

	@Override
	public int saveCustomNeed(NeedsListMaster need) {
		try{
			return (int) needsDao.save(need);
		}catch(Exception e){
			return 0;
		}
	}
}

