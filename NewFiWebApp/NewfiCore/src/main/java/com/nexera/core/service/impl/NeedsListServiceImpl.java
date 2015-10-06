package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.common.vo.NeedsListMasterVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
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
	private LoanService loanService;

	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private MessageServiceHelper messageServiceHelper;

	@Autowired
	private LoanNeedListDao loanNeedListDao;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;
	@Autowired
	private NotificationService notificationService;

	@Value("${profile.url}")
	private String baseUrl;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(NeedsListServiceImpl.class);

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
	@Transactional(readOnly = true)
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
				needsList.get(MasterNeedsEnum.EXTRA.getIndx()).setIsChecked(
				        true);

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
			LOGGER.error("Exception caught " + e.getMessage());
			return null;
		}
	}

	// @Override
	// @Transactional
	// public void createInitilaNeedsList(Integer loanId) {
	// HashMap<String, Object> result;
	// try {
	// result = getLoansNeedsList(loanId);
	// List<ManagerNeedVo> needs = (List<ManagerNeedVo>) result
	// .get("result");
	// List<Integer> needsToSave = new ArrayList<Integer>();
	// for (ManagerNeedVo needVo : needs) {
	// if (needVo.getIsChecked()) {
	// needsToSave.add(needVo.getNeedType());
	// }
	// }
	// int res = saveLoanNeeds(loanId, needsToSave);
	//
	// } catch (Exception e) {
	// LOGGER.error("Exception caught " + e.getMessage());
	// }
	// }

	private void createAlertForNeedsListSet(int loanId) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.NEEDS_LIST_SET_TYPE;
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		if (notificationList.size() == 0) {
			NotificationVO notificationVO = new NotificationVO(loanId,
			        notificationType.getNotificationTypeName(),
			        WorkflowConstants.NEEDS_LIST_SET_TYPE_CONTENT);
			notificationService.createNotification(notificationVO);
		}
	}

	private void dismissAlert(
	        MilestoneNotificationTypes mileStoneNotificationType, int loanId) {

		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                mileStoneNotificationType.getNotificationTypeName(),
		                true);
		for (NotificationVO notificationVO : notificationList) {
			notificationService.dismissNotification(notificationVO.getId());
		}
	}

	@Override
	@Transactional
	public int saveLoanNeeds(int loanId, List<Integer> needsList) {
		LOGGER.info("In Save Loan Needs : " + loanId + needsList);
		LinkedHashMap<String, LoanNeedsList> existingNeeds = new LinkedHashMap<String, LoanNeedsList>();
		boolean initialList = false;
		List<LoanNeedsList> existingNeedsList = null;
		List<Integer> addedList = new ArrayList<Integer>();
		List<Integer> removedList = new ArrayList<Integer>();
		try {
			existingNeedsList = needsDao.getLoanNeedsList(loanId);
			if (existingNeedsList == null || existingNeedsList.isEmpty()) {
				initialList = true;

			} else {
				initialList = false;
			}

		} catch (NoRecordsFetchedException e1) {
			LOGGER.error("Exception caught " + e1.getMessage());
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
				if (!need.getNeedsListMaster().getNeedCategory()
				        .equalsIgnoreCase("System")) {
					removedList.add(need.getNeedsListMaster().getId());
					needsDao.deleteLoanNeed(need);
				}
			}
			notificationService.dismissReadNotifications(loanId,
			        MilestoneNotificationTypes.NEEDED_ITEMS_NOTIFICATION_TYPE);
			// CALL TO CREATE ALERT FOR NEEDS LIST UPDATION
			createOrDismissNeedsAlert(loanId);
		} catch (Exception e) {
			return 0;
		}
		if (initialList) {
			initialNeedsListSetEmail(loanId, addedList);
		} else {
			updatedNeedsListEmail(loanId, addedList, removedList);
		}
		/*
		 * messageServiceHelper.generateNeedListModificationMessage(loanId,
		 * utils.getLoggedInUser(), addedList, removedList, Boolean.FALSE);
		 */

		return 1;
	}

	@Override
	@Transactional
	public void createOrDismissNeedsAlert(int loanId) {
		NeededItemScoreVO neededItemScoreVO = getNeededItemsScore(loanId);
		if (neededItemScoreVO.getTotalSubmittedItem() >= neededItemScoreVO
		        .getNeededItemRequired()) {
			dismissAlert(MilestoneNotificationTypes.NEEDS_LIST_SET_TYPE, loanId);
		} else {
			createAlertForNeedsListSet(loanId);
		}
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
		UploadedFilesList fileMapped = loanNeedsList.getUploadFileId();
		if (fileMapped != null) {
			loanNeedsListVO.setFileId(fileMapped.getId().toString());
		}

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
	public List<LoanNeedsList> saveMasterNeedsForLoan(int loanId,
	        List<NeedsListMaster> masterNeeds) {
		List<LoanNeedsList> needsList = new ArrayList<LoanNeedsList>();
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
				needsList.add(need);
				needsDao.save(need);
			}
		}
		return needsList;
	}

	@Override
	@Transactional
	public UploadedFilesList fetchPurchaseDocumentBasedOnPurchaseContract(
	        Integer loanId) {
		NeedsListMaster needsListMaster = needsDao
		        .findNeedsListMasterByLabel(CommonConstants.PURCHASE_CONTRACT);
		if (needsListMaster != null) {
			Loan loan = new Loan(loanId);
			LoanNeedsList loanNeedsList = loanNeedListDao.findLoanNeedsList(
			        loan, needsListMaster);
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

	@Override
	public void initialNeedsListSetEmail(Integer loanID, List<Integer> addedList) {
		EmailVO emailEntity = new EmailVO();
		LoanVO loanVO = loanService.getLoanByID(loanID);
		if (loanVO != null) {
			if (loanVO.getUser() != null) {
				Template template = templateService
				        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_INITIAL_NEEDS_LIST_SET);
				// We create the substitutions map
				Map<String, String[]> substitutions = new HashMap<String, String[]>();

				substitutions.put("-name-", new String[] { loanVO.getUser()
				        .getFirstName() });
				substitutions.put("-listofitems-",
				        new String[] { getNeedsListNameById(addedList) });
				substitutions.put("-url-", new String[] { baseUrl });
				if (loanVO.getUser() != null) {
					emailEntity.setSenderEmailId(loanVO.getUser().getUsername()
					        + CommonConstants.SENDER_EMAIL_ID);
				} else {
					emailEntity
					        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
					                + CommonConstants.SENDER_EMAIL_ID);
				}
				emailEntity.setSenderName(CommonConstants.SENDER_NAME);
				emailEntity
				        .setSubject(CommonConstants.SUBJECT_INITIAL_NEEDS_LIST_ARE_SET);
				emailEntity.setTokenMap(substitutions);
				emailEntity.setTemplateId(template.getValue());
				List<String> ccList = new ArrayList<String>();
				ccList.add(loanVO.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);
				emailEntity.setDisableHtml(true);
				emailEntity.setCCList(ccList);
				try {
					sendEmailService.sendEmailForCustomer(emailEntity,
					        loanVO.getId(), template);
				} catch (InvalidInputException | UndeliveredEmailException e) {
					LOGGER.error("Exception caught while sending email "
					        + e.getMessage());
				}
			}
		}

	}

	private String getNeedsListNameById(List<Integer> needsListId) {
		NeedsListMaster needsListMaster = null;
		String needsName = "";
		List<NeedsListMaster> needsList = new ArrayList<NeedsListMaster>();
		for (int id : needsListId) {
			needsListMaster = (NeedsListMaster) needsDao.load(
			        NeedsListMaster.class, id);
			if (needsListMaster != null) {
				if (needsListMaster.getId() != Integer
				        .parseInt(MasterNeedsEnum.EXTRA.getIndx())) {
					needsList.add(needsListMaster);
				}
			}
		}

		int count = needsList.size();
		for (NeedsListMaster needsListEntry : needsList) {

			needsName = count + ". " + needsListEntry.getLabel() + " - "
			        + needsListEntry.getDescription() + "\n" + needsName;
			count = count - 1;

		}

		return needsName;
	}

	@Override
	public void updatedNeedsListEmail(Integer loanID, List<Integer> addedList,
	        List<Integer> removedList) {

		EmailVO emailEntity = new EmailVO();
		LoanVO loanVO = loanService.getLoanByID(loanID);
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NEEDS_LIST_UPDATED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loanVO.getUser()
		        .getFirstName() });
		substitutions.put("-url-", new String[] { baseUrl });

		substitutions.put("-listofitems-",
		        new String[] { getNeedsListNameById(addedList)
		                + getNeedsListNameById(removedList) });
		if (loanVO.getUser() != null) {
			emailEntity.setSenderEmailId(loanVO.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setDisableHtml(true);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(CommonConstants.SUBJECT_UPDATE_NEEDS_LIST);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();

		ccList.add(loanVO.getUser().getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);
		try {
			sendEmailService.sendEmailForCustomer(emailEntity, loanVO.getId(),
			        template);
		} catch (InvalidInputException | UndeliveredEmailException e) {
			LOGGER.error("Exception caught while sending email "
			        + e.getMessage());
		}

	}

	@Override
	@Transactional
	public boolean checkNeedExist(String label, String category,
			String description, User user, String lqbDocumentType) {
		Boolean isexist = needsDao.checkNeedExist(label, category, description, user, lqbDocumentType);
		return isexist;
	}
}
