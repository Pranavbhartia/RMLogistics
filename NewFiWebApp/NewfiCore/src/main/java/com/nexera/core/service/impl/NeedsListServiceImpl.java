package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.UploadedFilesList;
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
public class NeedsListServiceImpl implements NeedsListService {

	@Autowired
	private NeedsDao needsDao;

	@Autowired
	private Utils utils;

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private MessageServiceHelper messageServiceHelper;

	@Autowired
	private LoanNeedListDao loanNeedListDao;

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;

	@Transactional
	public LinkedHashMap<String, ManagerNeedVo> getMasterNeedsListDirectory() {
		List<NeedsListMaster> needs = needsDao.getMasterNeedsList(false);
		LinkedHashMap<String, ManagerNeedVo> needsDirectory = new LinkedHashMap<String, ManagerNeedVo>();
		for (NeedsListMaster need : needs) {
			ManagerNeedVo managerNeedVo = new ManagerNeedVo(need);
			needsDirectory.put(need.getId() + "", managerNeedVo);
		}
		return needsDirectory;
	}

	@Override
	@Transactional
	public HashMap<String, Object> getLoansNeedsList(int loanId)
	        throws Exception {
		try {
			HashMap<String, Object> loanNeedsNStatus = new HashMap<String, Object>();
			List<LoanNeedsList> loanNeeds = needsDao.getLoanNeedsList(loanId);
			List<ManagerNeedVo> result = new ArrayList<ManagerNeedVo>();
			boolean initialNeedsCreation = false;
			if (loanNeeds.size() > 0) {
				List<NeedsListMaster> needs = needsDao
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
				List<NeedsListMaster> customNeeds = needsDao
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

				if (null != loanAppForm.getMaritalStatus()
				        && loanAppForm.getMaritalStatus().equals(
				                CoreCommonConstants.MARITAL_STATUS_DIVORCED)) {
					// #1
					{
						String indx = MasterNeedsEnum.Divorce_Settlement_Agree_No1
						        .getIndx();
						ManagerNeedVo managerNeedVo = needsList.get(indx);
						managerNeedVo.setIsChecked(true);
					}
				} else if (null != loanAppForm.getMaritalStatus()
				        && loanAppForm.getMaritalStatus().equals(
				                CoreCommonConstants.MARITAL_STATUS_SETTLEMENT)) {
					// #4
					{
						ManagerNeedVo managerNeedVo = needsList
						        .get(MasterNeedsEnum.Cancelled_Check_Child_Support
						                .getIndx());
						managerNeedVo.setIsChecked(true);
					}
				}
				if (null != loanAppForm.getReceiveAlimonyChildSupport()
				        && loanAppForm.getReceiveAlimonyChildSupport()) {
					// #3,
					ManagerNeedVo managerNeedVo = needsList
					        .get(MasterNeedsEnum.Divorce_Separation_Settlement_Agreement_No3
					                .getIndx());
					managerNeedVo.setIsChecked(true);
				}

				if (null != loanAppForm.getSecondMortgage()
				        && loanAppForm.getSecondMortgage()) {
					// #7
					ManagerNeedVo managerNeedVo = needsList
					        .get(MasterNeedsEnum.Mortgage_Equity_Line_Statement
					                .getIndx());
					managerNeedVo.setIsChecked(true);
					if (null != loanAppForm.getPaySecondMortgage()
					        && !loanAppForm.getPaySecondMortgage()) {
						// #13
						ManagerNeedVo managerNeedVo1 = needsList
						        .get(MasterNeedsEnum.Loan_Agreement_2nd_Mortgage_Line
						                .getIndx());
						managerNeedVo1.setIsChecked(true);
					}
				}
				if (null != loanAppForm.getHomeToSell()
				        && loanAppForm.getHomeToSell()) {
					// #10
					ManagerNeedVo managerNeedVo = needsList
					        .get(MasterNeedsEnum.Purchase_Contract_Home_Currently_Sold
					                .getIndx());
					managerNeedVo.setIsChecked(true);
				}
				if (null != loanAppForm.getOwnsOtherProperty()
				        && loanAppForm.getOwnsOtherProperty()) {
					// #12
					ManagerNeedVo managerNeedVo = needsList
					        .get(MasterNeedsEnum.Additional_Properties
					                .getIndx());
					managerNeedVo.setIsChecked(true);
					if (null != loanAppForm.getRentedOtherProperty()
					        && loanAppForm.getRentedOtherProperty()) {
						// #32
						ManagerNeedVo managerNeedVo32 = needsList
						        .get(MasterNeedsEnum.Rental_Lease_Agreements
						                .getIndx());
						managerNeedVo32.setIsChecked(true);
					}
				}
				if (null != loanAppForm.getHomeRecentlySold()
				        && loanAppForm.getHomeRecentlySold()) {
					// #15
					ManagerNeedVo managerNeedVo = needsList
					        .get(MasterNeedsEnum.Settlement_HUD_1_Property_Recently_Sold
					                .getIndx());
					managerNeedVo.setIsChecked(true);
				}
				if (null != loanAppForm.getHoaDues()
				        && loanAppForm.getHoaDues()) {
					// #16
					ManagerNeedVo managerNeedVo = needsList
					        .get(MasterNeedsEnum.Proof_of_HOA_Dues.getIndx());
					managerNeedVo.setIsChecked(true);
				}
				if (null != loanAppForm.getLoanTypeMaster()
				        && loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_PURCHASE) {
					// Purchase type
					// #5,#11,#14
					needsList
					        .get(MasterNeedsEnum.Copy_of_Check_Transfer_Escrow_Receipt
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Homeowner_Insurance_Quote
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Purchase_Contract_Including_Addendums
					                .getIndx()).setIsChecked(true);

					if (loanAppForm.getPropertyTypeMaster().getId() == CoreCommonConstants.PROPERTY_TYPE_RENTING) {
						// Renting Purpose :2
						// #8
						needsList.get(
						        MasterNeedsEnum.Cancelled_Checks_Rent_Payments
						                .getIndx()).setIsChecked(true);
					}
				} else if (null != loanAppForm.getLoanTypeMaster()
				        && (loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_REFINANCE || loanAppForm
				                .getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_REFINANCE_CASH_OUT)) {
					// All Refinance 2,3
					// #6
					needsList.get(MasterNeedsEnum.Mortgage_Statement.getIndx())
					        .setIsChecked(true);
					if (loanAppForm.getLoanTypeMaster().getId() == CoreCommonConstants.LOAN_TYPE_REFINANCE) {
						// #9
						needsList.get(
						        MasterNeedsEnum.Homeowner_Insurance.getIndx())
						        .setIsChecked(true);
					}
				}
				if (null != loanAppForm.getIsEmployed()
				        && loanAppForm.getIsEmployed()) {
					// #17,#18,
					needsList.get(
					        MasterNeedsEnum.Paychecks_Most_Recent_30_Days
					                .getIndx()).setIsChecked(true);
					needsList.get(
					        MasterNeedsEnum.W2s_Previous_2_Years.getIndx())
					        .setIsChecked(true);

				}
				if (null != loanAppForm.getIsssIncomeOrDisability()
				        && loanAppForm.getIsssIncomeOrDisability()) {
					// #19,#20,#21
					needsList.get(
					        MasterNeedsEnum.Social_Security_Award_Letter
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Previous_2_years_1099_1099_s_1099_Rs
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Evidence_recent_receipt_1099_1099_s_1099_Rs_Income
					                .getIndx()).setIsChecked(true);
				}
				if (null != loanAppForm.getIsselfEmployed()
				        && loanAppForm.getIsselfEmployed()) {
					// #20,#21,#24,#25,#26
					needsList
					        .get(MasterNeedsEnum.Previous_2_years_1099_1099_s_1099_Rs
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Evidence_recent_receipt_1099_1099_s_1099_Rs_Income
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Federal_Corporation_Partnership_K_1s_all_partnerships
					                .getIndx()).setIsChecked(true);
					needsList.get(
					        MasterNeedsEnum.Year_to_date_Profit_Loss_Business
					                .getIndx()).setIsChecked(true);
					needsList.get(
					        MasterNeedsEnum.Year_to_date_Balance_Sheet_Business
					                .getIndx()).setIsChecked(true);
				}
				if (null != loanAppForm.getIspensionOrRetirement()
				        && loanAppForm.getIspensionOrRetirement()) {
					// #20,#21
					needsList
					        .get(MasterNeedsEnum.Previous_2_years_1099_1099_s_1099_Rs
					                .getIndx()).setIsChecked(true);
					needsList
					        .get(MasterNeedsEnum.Evidence_recent_receipt_1099_1099_s_1099_Rs_Income
					                .getIndx()).setIsChecked(true);
				}
				// #22,#28,#33
				needsList.get(
				        MasterNeedsEnum.Federal_Tax_Returns_Previous_2_Years
				                .getIndx()).setIsChecked(true);
				needsList.get(
				        MasterNeedsEnum.Months2_Statements_Bank_Accounts
				                .getIndx()).setIsChecked(true);
				needsList
				        .get(MasterNeedsEnum.Driver_License_Passport.getIndx())
				        .setIsChecked(true);

				result = new ArrayList<ManagerNeedVo>(needsList.values());
				initialNeedsCreation = true;

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
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public int saveLoanNeeds(int loanId, List<Integer> needsList) {
		LinkedHashMap<String, LoanNeedsList> existingNeeds = new LinkedHashMap<String, LoanNeedsList>();
		List<LoanNeedsList> existingNeedsList = null;
		List<Integer> addedList = new ArrayList<Integer>();
		List<Integer> removedList = new ArrayList<Integer>();
		try {
			existingNeedsList = needsDao.getLoanNeedsList(loanId);
		} catch (NoRecordsFetchedException e1) {
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
		messageServiceHelper.generateNeedListModificationMessage(loanId,
		        utils.getLoggedInUser(), addedList, removedList, Boolean.TRUE);
		return 1;
	}

	@Override
	@Transactional
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

	@Transactional
	public LoanNeedsListVO buildLoanNeedsListVO(LoanNeedsList loanNeedsList) {

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

	@Transactional
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

	@Override
	@Transactional
	public Integer getLoanNeedListIdByFileId(Integer fileId) {
		return needsDao.getLoanNeedListIdByFileId(fileId);

	}

	@Override
	@Transactional
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
	@Transactional
	public int saveCustomNeed(NeedsListMaster need) {
		try {
			return (int) needsDao.save(need);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	@Transactional
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
			if (map.get(category) == null) {
				listVOs = new ArrayList<LoanNeedsListVO>();
				map.put(category, listVOs);
			} else {
				listVOs = map.get(category);
			}
			listVOs.add(buildLoanNeedsListVO(loanNeeds));
		}
		return map;
	}

	@Override
	@Transactional
	public NeededItemScoreVO getNeededItemsScore(Integer loanId) {
		Integer neededItemRequired = loanDao.getNeededItemsRequired(loanId);
		Integer totalNeededItem = loanDao.getTotalNeededItem(loanId);
		NeededItemScoreVO neededItemScoreVO = new NeededItemScoreVO();
		neededItemScoreVO.setNeededItemRequired(neededItemRequired);
		neededItemScoreVO.setTotalSubmittedItem(totalNeededItem);
		return neededItemScoreVO;
	}

	@Override
	@Transactional
	public NeedsListMaster fetchNeedListMasterUsingID(Integer needId) {
		return (NeedsListMaster) needsDao.load(NeedsListMaster.class, needId);
	}

	@Override
	@Transactional
	public LoanNeedsList findNeedForLoan(Loan loan,
	        NeedsListMaster needsListMaster) {
		// TODO Needs To be changed based on dao IMPL
		return needsDao.findNeedForLoan(loan, needsListMaster);
	}

	@Override
	@Transactional(readOnly = true)
	public NeedsListMaster fetchNeedListMasterByType(String needsListType) {
		return needsDao.findNeedsListMasterByLabel(needsListType);
	}

	@Override
	@Transactional
	public void saveMasterNeedsForLoan(int loanId,
	        List<NeedsListMaster> masterNeeds) {
		for (NeedsListMaster masterNeed : masterNeeds) {
			NeedsListMaster existingNeed = needsDao
			        .findNeedsListMasterByLabel(masterNeed.getLabel());
			if (existingNeed == null) {
				Integer id = (Integer) needsDao.save(masterNeed);

				masterNeed.setId(id);
			} else {
				masterNeed = existingNeed;
			}

			LoanNeedsList need = new LoanNeedsList();
			Loan loan = new Loan();
			loan.setId(loanId);
			need.setLoan(loan);
			need.setNeedsListMaster(masterNeed);
			need.setDeleted(false);
			need.setSystemAction(true);
			need.setActive(true);
			need.setComments("");
			need.setMandatory(true);
			need.setSystemAction(true);
			LoanNeedsList loanNeedsList = needsDao.findLoanNeedByMaster(loan,
			        masterNeed);
			if (loanNeedsList == null) {
				needsDao.save(need);
			}
		}
	}

	@Override
	@Transactional
	public UploadedFilesList fetchPurchaseDocumentBasedOnPurchaseContract() {
		NeedsListMaster needsListMaster = needsDao
		        .findNeedsListMasterByLabel(CommonConstants.PURCHASE_CONTRACT);
		if (needsListMaster != null) {
			LoanNeedsList loanNeedsList = loanNeedListDao
			        .findLoanNeedsList(needsListMaster);
			if (loanNeedsList != null) {

				return loanNeedsList.getUploadFileId();
			}
		}
		return null;
	}

	@Override
	@Transactional
	public String checkCreditReport(Integer loanID) {
		// TODO Auto-generated method stub
		return needsDao.checkCreditReport(loanID);
	}
}
