package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommunicationLogConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.common.vo.NeedsListMasterVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.utility.CoreCommonConstants;

@Component
@Transactional
public class NeedsListServiceImpl implements NeedsListService {

	@Autowired
	private NeedsDao needsDao;

	@Autowired
	private Utils utils;
	
	@Autowired
	private LoanDao loanDao;

	@Autowired
	private MessageServiceHelper messageServiceHelper;
	
	public LinkedHashMap<String, ManagerNeedVo> getMasterNeedsListDirectory() {
		List<NeedsListMaster> needs = (List<NeedsListMaster>) needsDao
				.getMasterNeedsList(false);
		LinkedHashMap<String, ManagerNeedVo> needsDirectory = new LinkedHashMap<String, ManagerNeedVo>();
		for (NeedsListMaster need : needs) {
			ManagerNeedVo managerNeedVo = new ManagerNeedVo(need);
			needsDirectory.put(need.getId() + "", managerNeedVo);
		}
		return needsDirectory;
	}

	public HashMap<String, Object> getLoansNeedsList(int loanId)
			throws Exception {
		try {
			HashMap<String, Object> loanNeedsNStatus = new HashMap<String, Object>();
			List<LoanNeedsList> loanNeeds = needsDao.getLoanNeedsList(loanId);
			// TODO Need Loan Details
			// TODO Fetch List of Needs List from database for the loan if list
			// is not null and length greater then 0 then those items need to be
			// selected
			List<ManagerNeedVo> result = new ArrayList<ManagerNeedVo>();
			boolean initialNeedsCreation = false;
			if (loanNeeds.size() > 0) {
				List<NeedsListMaster> needs = (List<NeedsListMaster>) needsDao
						.getMasterNeedsList(false);
				// List<NeedsListMaster>
				// needs=needsDao.loadAll(NeedsListMaster.class);
				LinkedHashMap<String, LoanNeedsList> needsDir = new LinkedHashMap<String, LoanNeedsList>();
				for (LoanNeedsList loanNeed : loanNeeds) {
					needsDir.put(loanNeed.getNeedsListMaster().getId() + "",
							loanNeed);
				}
				for (NeedsListMaster need : needs) {
					ManagerNeedVo needVo = new ManagerNeedVo(need);
					if (needsDir.containsKey(need.getId() + "")) {
						needVo.setIsChecked(true);
					} else {
						needVo.setIsChecked(false);
					}
					result.add(needVo);
				}
				List<NeedsListMaster> customNeeds = (List<NeedsListMaster>) needsDao
						.getMasterNeedsList(true);

				for (NeedsListMaster need : customNeeds) {
					if (needsDir.containsKey(need.getId() + "")) {
						ManagerNeedVo needVo = new ManagerNeedVo(need);
						needVo.setIsChecked(true);
						result.add(needVo);
					}
				}
			} else {
				LoanAppForm loanAppForm = loanDao.getLoanAppForm(loanId);
				if (loanAppForm == null) {
					throw new Exception("Loan Details Not found");
				}
				LinkedHashMap<String, ManagerNeedVo> needsList = getMasterNeedsListDirectory();
				if (needsList.size() < 36) {
					throw new Exception("Insufficient Data");
				}

				if (loanAppForm.getMaritalStatus().equals(CoreCommonConstants.MARITAL_STATUS_DIVORCED)) {
					// #1
					{
						String indx=MasterNeedsEnum.Divorce_Settlement_Agree_No1.getIndx();
						ManagerNeedVo managerNeedVo = needsList.get(indx);
						managerNeedVo.setIsChecked(true);
					}
				} else if (loanAppForm.getMaritalStatus().equals(CoreCommonConstants.MARITAL_STATUS_SETTLEMENT)) {
					// #4
					{
						ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Cancelled_Check_Child_Support.getIndx());
						managerNeedVo.setIsChecked(true);
					}
				}
				if (loanAppForm.getReceiveAlimonyChildSupport()) {
					// #3,
					ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Divorce_Separation_Settlement_Agreement_No3.getIndx());
					managerNeedVo.setIsChecked(true);
				}

				if (loanAppForm.getSecondMortgage()) {
					// #7
					ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Mortgage_Equity_Line_Statement.getIndx());
					managerNeedVo.setIsChecked(true);
					if (!loanAppForm.getPaySecondMortgage()) {
						// #13
						ManagerNeedVo managerNeedVo1 = needsList.get(MasterNeedsEnum.Loan_Agreement_2nd_Mortgage_Line.getIndx());
						managerNeedVo.setIsChecked(true);
					}
				}
				if (loanAppForm.getHomeToSell()) {
					// #10
					ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Purchase_Contract_Home_Currently_Sold.getIndx());
					managerNeedVo.setIsChecked(true);
				}
				if (loanAppForm.getOwnsOtherProperty()) {
					// #12
					ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Additional_Properties.getIndx());
					managerNeedVo.setIsChecked(true);
					if (loanAppForm.getRentedOtherProperty()) {
						// #32
						ManagerNeedVo managerNeedVo32 = needsList.get(MasterNeedsEnum.Rental_Lease_Agreements.getIndx());
						managerNeedVo32.setIsChecked(true);
					}
				}
				if (loanAppForm.getHomeRecentlySold()) {
					// #15
					ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Settlement_HUD_1_Property_Recently_Sold.getIndx());
					managerNeedVo.setIsChecked(true);
				}
				if (loanAppForm.getHoaDues()) {
					// #16
					ManagerNeedVo managerNeedVo = needsList.get(MasterNeedsEnum.Proof_of_HOA_Dues.getIndx());
					managerNeedVo.setIsChecked(true);
				}
				if (loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_PURCHASE) {
					// Purchase type
					// #5,#11,#14
					needsList.get(MasterNeedsEnum.Copy_of_Check_Transfer_Escrow_Receipt.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Homeowner_Insurance_Quote.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Purchase_Contract_Including_Addendums.getIndx()).setIsChecked(true);
					
					if (loanAppForm.getPropertyTypeMaster().getId() == CoreCommonConstants.PROPERTY_TYPE_RENTING) {
						// Renting Purpose :2
						// #8
						needsList.get(MasterNeedsEnum.Cancelled_Checks_Rent_Payments.getIndx()).setIsChecked(true);
					}
				} else if (loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_REFINANCE
						|| loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_REFINANCE_CASH_OUT) {
					// All Refinance 2,3
					// #6
					needsList.get(MasterNeedsEnum.Mortgage_Statement.getIndx()).setIsChecked(true);
					if (loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_REFINANCE) {
						// #9
						needsList.get(MasterNeedsEnum.Homeowner_Insurance.getIndx()).setIsChecked(true);
					}
				}
				if (loanAppForm.getEmployed()) {
					// #17,#18,
					needsList.get(MasterNeedsEnum.Paychecks_Most_Recent_30_Days.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.W2s_Previous_2_Years.getIndx()).setIsChecked(true);
					
				}
				if (loanAppForm.getSsIncomeOrDisability()) {
					// #19,#20,#21
					needsList.get(MasterNeedsEnum.Social_Security_Award_Letter.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Previous_2_years_1099_1099_s_1099_Rs.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Evidence_recent_receipt_1099_1099_s_1099_Rs_Income).setIsChecked(true);
				}
				if (loanAppForm.getSelfEmployed()) {
					// #20,#21,#24,#25,#26
					needsList.get(MasterNeedsEnum.Previous_2_years_1099_1099_s_1099_Rs.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Evidence_recent_receipt_1099_1099_s_1099_Rs_Income).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Federal_Corporation_Partnership_K_1s_all_partnerships.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Year_to_date_Profit_Loss_Business.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Year_to_date_Balance_Sheet_Business.getIndx()).setIsChecked(true);					
				}
				if (loanAppForm.getPensionOrRetirement()) {
					// #20,#21
					needsList.get(MasterNeedsEnum.Previous_2_years_1099_1099_s_1099_Rs.getIndx()).setIsChecked(true);
					needsList.get(MasterNeedsEnum.Evidence_recent_receipt_1099_1099_s_1099_Rs_Income).setIsChecked(true);
				}
				// #22,#28,#33
				needsList.get(MasterNeedsEnum.Federal_Tax_Returns_Previous_2_Years.getIndx()).setIsChecked(true);
				needsList.get(MasterNeedsEnum.Months2_Statements_Bank_Accounts.getIndx()).setIsChecked(true);
				needsList.get(MasterNeedsEnum.Driver_License_Passport.getIndx()).setIsChecked(true);
				

				result = new ArrayList<ManagerNeedVo>(needsList.values());
				initialNeedsCreation = true;
				// TODO code to Apply Rules comes here

				// for(NeedsListMaster need:needs){
				// if(need.getMandatory()==1){
				// need.setSelected(1);
				// }else if(need.getMandatory()==2){
				// need.setSelected(0);
				// }else if(need.getLoanType()==loan.getLoan_type()){
				// need.setSelected(1);
				// }
				// }

			}

			// Collections.sort(result,new Comparator<ManagerNeedVo>() {
			// @Override
			// public int compare(ManagerNeedVo e1, ManagerNeedVo e2) {
			// if(e1.getId().intValue() < e2.getId().intValue()){
			// return 1;
			// } else {
			// return -1;
			// }
			// }
			// });
			loanNeedsNStatus.put("result", result);
			loanNeedsNStatus.put("initialCreation", initialNeedsCreation);
			return loanNeedsNStatus;

		} catch (NoRecordsFetchedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int saveLoanNeeds(int loanId, List<Integer> needsList) {
		LinkedHashMap<String, LoanNeedsList> existingNeeds = new LinkedHashMap<String, LoanNeedsList>();
		List<LoanNeedsList> existingNeedsList = null;
		List<Integer> addedList = new ArrayList<Integer>();
		List<Integer> removedList = new ArrayList<Integer>();
		try {
			existingNeedsList = needsDao.getLoanNeedsList(loanId);
		} catch (NoRecordsFetchedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (LoanNeedsList need : existingNeedsList) {
			existingNeeds.put(need.getNeedsListMaster().getId() + "", need);
		}
		
		try {
			for (Integer needId : needsList) {
				LoanNeedsList need = new LoanNeedsList();
				Loan loan = new Loan();
				loan.setId(loanId);
				NeedsListMaster needListMaster = new NeedsListMaster();
				needListMaster.setId(needId.intValue());
				need.setLoan(loan);
				need.setNeedsListMaster(needListMaster);
				
				need.setDeleted(false);
				need.setSystemAction(true);
				need.setActive(true);
				need.setComments("");
				need.setMandatory(true);
				need.setSystemAction(true);

				if (existingNeeds.containsKey(needId.intValue() + "")) {
					existingNeeds.remove(needId.intValue() + "");
				} else {
					try {
						addedList.add(needId.intValue());
						needsDao.save(need);
					} catch (DatabaseException e) {

					}
				}
			}
			for (LoanNeedsList need : existingNeeds.values()) {
				removedList.add(need.getNeedsListMaster().getId());
				needsDao.deleteLoanNeed(need);
			}
		} catch (Exception e) {
			return 0;
		}
		messageServiceHelper.generateCommunicationLogMessage(loanId,utils.getLoggedInUser(),addedList,removedList);
		return 1;
	}

	@Override
	public List<LoanNeedsListVO> getLoanNeedsList(Integer loanId) {
		List<LoanNeedsList> loanNeedsList;
		try {
			loanNeedsList = needsDao.getLoanNeedsList(loanId);
		} catch (NoRecordsFetchedException e) {
			return Collections.EMPTY_LIST;
		}
		List<LoanNeedsListVO> loanNeedsListVO = new ArrayList<>();
		for (LoanNeedsList loanNeeds : loanNeedsList) {
			loanNeedsListVO.add(buildLoanNeedsListVO(loanNeeds));
		}
		return loanNeedsListVO;
	}

	public LoanNeedsListVO buildLoanNeedsListVO(
			LoanNeedsList loanNeedsList) {

		if (loanNeedsList == null)
			return null;

		LoanNeedsListVO loanNeedsListVO = new LoanNeedsListVO();
		loanNeedsListVO.setId(loanNeedsList.getId());
		loanNeedsListVO.setActive(loanNeedsList.getActive());
		loanNeedsListVO
				.setNeedsListMaster(buildLoanNeedsListMasterVO(loanNeedsList
						.getNeedsListMaster()));
		return loanNeedsListVO;
	}

	public NeedsListMasterVO buildLoanNeedsListMasterVO(
			NeedsListMaster needListMaster) {
		if (needListMaster == null)
			return null;
		NeedsListMasterVO needListMasterVO = new NeedsListMasterVO();
		needListMasterVO.setDescription(needListMaster.getDescription());
		needListMasterVO.setId(needListMaster.getId());
		needListMasterVO.setLabel(needListMaster.getLabel());
		needListMasterVO.setNeedCategory(needListMaster.getNeedCategory());
		return needListMasterVO;

	}

	public Integer getLoanNeedListIdByFileId(Integer fileId) {
		return needsDao.getLoanNeedListIdByFileId(fileId);

	}

	@Override
	public List<ManagerNeedVo> getNeedsListMaster(boolean isCustom) {
		List<NeedsListMaster> customNeeds = needsDao
				.getMasterNeedsList(isCustom);
		List<ManagerNeedVo> result = new ArrayList<ManagerNeedVo>();
		for (NeedsListMaster need : customNeeds) {
			ManagerNeedVo needVo = new ManagerNeedVo(need);
			result.add(needVo);
		}
		return result;
	}

	@Override
	public int saveCustomNeed(NeedsListMaster need) {
		try {
			return (int) needsDao.save(need);
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	
	

	@Override
	public Map<String, List<LoanNeedsListVO>> getLoanNeedsMap(Integer loanId) {
		Map<String, List<LoanNeedsListVO>> map = new HashMap<String, List<LoanNeedsListVO>>();

		List<LoanNeedsList> loanNeedsList;
		try {
			loanNeedsList = needsDao.getLoanNeedsList(loanId);
		} catch (NoRecordsFetchedException e) {
			return Collections.EMPTY_MAP;
		}
		List<LoanNeedsListVO> loanNeedsListVO = new ArrayList<>();
		for (LoanNeedsList loanNeeds : loanNeedsList) {
			String category = loanNeeds.getNeedsListMaster().getNeedCategory();
			category = category.replace("/", "_");
			List<LoanNeedsListVO> listVOs = null;
			if(map.get(category)==null){
				listVOs = new ArrayList<LoanNeedsListVO>();
				map.put(category, listVOs);
			}else{
				listVOs = map.get(category);
			}
			listVOs.add(buildLoanNeedsListVO(loanNeeds));
		}
		return map;
	}

	@Override
	public NeededItemScoreVO getNeededItemsScore(Integer loanId) {
		Integer neededItemRequired  = loanDao.getNeededItemsRequired(loanId);
		Integer totalNeededItem  = loanDao.getTotalNeededItem(loanId);
		NeededItemScoreVO neededItemScoreVO = new NeededItemScoreVO();
		neededItemScoreVO.setNeededItemRequired(neededItemRequired);
		neededItemScoreVO.setTotalSubmittedItem(totalNeededItem);
		return neededItemScoreVO;
	}

}
