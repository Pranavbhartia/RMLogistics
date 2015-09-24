package com.nexera.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.BaseRestException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.EditLoanTeamVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LeadsDashBoardVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanUserSearchVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.DashboardCriteriaVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.web.rest.util.RestUtil;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;

@RestController
@RequestMapping(value = "/loan/*")
public class LoanRestService {

	private static final String ACCESS_DENIED = "401";

	private static final String YOU_DO_NOT_HAVE_ACCESS = "You do not have access";

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private Utils utils;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private NeedsListService needsListService;
	


	@Autowired
	private UploadedFilesListService uploadedFilesListService;

	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanRestService.class);

	
	// TODO-move this to User profile rest service
	 @RequestMapping(value = "/retrieveDashboardForMyLeads/{userID}")
	 public @ResponseBody CommonResponseVO retrieveDashboardForMyLeads(
	         @PathVariable Integer userID,
	         @RequestParam(required = false) String startlimit,
	         @RequestParam(required = false) String count) {
	  UserVO user = new UserVO();
	  user.setId(userID);
	  CommonResponseVO commonResponseVO = null;
	  if (startlimit != null) {
	   LeadsDashBoardVO responseVO = loanService
	           .retrieveDashboardForMyLeads(user, startlimit, count);
	   commonResponseVO=RestUtil.wrapObjectForSuccess(responseVO);
	  } else {
	   LeadsDashBoardVO responseVO = loanService
	           .retrieveDashboardForMyLeads(user);
	   commonResponseVO=RestUtil.wrapObjectForSuccess(responseVO);
	  }

	  return commonResponseVO;
	 }
	
	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public @ResponseBody String createUser(@RequestBody String loanVOStr) {

		LoanVO loanVO = new Gson().fromJson(loanVOStr, LoanVO.class);

		loanVO = loanService.createLoan(loanVO);

		return new Gson().toJson(RestUtil.wrapObjectForSuccess(loanVO));

	}

	@RequestMapping(value = "/user/{userID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoansOfUser(
	        @PathVariable Integer userID) {

		UserVO user = new UserVO();
		user.setId(userID);
		List<LoanVO> loansList = loanService.getLoansOfUser(user);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loansList);

		return responseVO;
	}

	@RequestMapping(value = "/checkCreditReport/{loanID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO checkCreditReport(
	        @PathVariable Integer loanID) {
		UserRolesEnum rolesEnum = utils.getLoggedInUserRole();

		switch (rolesEnum) {
		case CUSTOMER:
			return RestUtil.wrapObjectForFailure(null, ACCESS_DENIED,
			        YOU_DO_NOT_HAVE_ACCESS);
		case REALTOR:
			return RestUtil.wrapObjectForFailure(null, ACCESS_DENIED,
			        YOU_DO_NOT_HAVE_ACCESS);

		default:
			break;
		}
		String fileId = needsListService.checkCreditReport(loanID);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(fileId);
		return responseVO;
	}

	@RequestMapping(value = "/{loanID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoanByID(
	        @PathVariable Integer loanID) {

		LoanVO loanVO = loanService.wrapperCallForDashboard(loanID);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loanVO);
		return responseVO;
	}

	@RequestMapping(value = "/creditReport/{loanID}", method = RequestMethod.GET)
	public @ResponseBody String creditReport(
	        @PathVariable Integer loanID, HttpServletRequest arg0,
	        HttpServletResponse response) {

		LoanVO loanVO = loanService.wrapperCallForDashboard(loanID);

		response.setHeader("Location", loanVO.getCreditReportUrl());
		response.setStatus(302);
		if (loanVO.getCreditReportUrl().equals("")
		        || null == loanVO.getCreditReportUrl())
			return "Credit Report Not Available";
		return "";
	}

	@RequestMapping(value = "/{loanID}/team", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO addToLoanTeam(
	        @PathVariable Integer loanID,
	        @RequestParam(value = "userID", required = false) Integer userID,
	        @RequestParam(value = "titleCompanyID", required = false) Integer titleCompanyID,
	        @RequestParam(value = "homeOwnInsCompanyID", required = false) Integer homeOwnInsCompanyID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);

		EditLoanTeamVO editLoanTeamVO = new EditLoanTeamVO();
		editLoanTeamVO.setUserID(userID);
		editLoanTeamVO.setLoanID(loanID);
		editLoanTeamVO.setHomeOwnInsCompanyID(homeOwnInsCompanyID);
		editLoanTeamVO.setTitleCompanyID(titleCompanyID);
		if (userID != null && userID > 0) {
			UserVO user = new UserVO();
			user.setId(userID);
			boolean result = loanService.addToLoanTeam(loan, user);
			if (result) {
				user = userProfileService.loadInternalUser(userID);
			}
			editLoanTeamVO.setOperationResult(result);
			editLoanTeamVO.setUser(user);

		} else if (titleCompanyID != null && titleCompanyID > 0) {
			TitleCompanyMasterVO company = new TitleCompanyMasterVO();
			company.setId(titleCompanyID);
			company = loanService.addToLoanTeam(loan, company,
			        User.convertFromEntityToVO(utils.getLoggedInUser()));
			editLoanTeamVO.setTitleCompany(company);
			editLoanTeamVO.setOperationResult(true);
		} else if (homeOwnInsCompanyID != null && homeOwnInsCompanyID > 0) {
			HomeOwnersInsuranceMasterVO company = new HomeOwnersInsuranceMasterVO();
			company.setId(homeOwnInsCompanyID);
			company = loanService.addToLoanTeam(loan, company,
			        User.convertFromEntityToVO(utils.getLoggedInUser()));
			editLoanTeamVO.setHomeOwnInsCompany(company);
			editLoanTeamVO.setOperationResult(true);
		} else
			return RestUtil.wrapObjectForFailure(null, "400", "User could not be added to team");

		CommonResponseVO responseVO = RestUtil
		        .wrapObjectForSuccess(editLoanTeamVO);

		return responseVO;

	}

	@RequestMapping(value = "/{loanID}/team", method = RequestMethod.DELETE)
	public @ResponseBody CommonResponseVO removeFromLoanTeam(
	        @PathVariable Integer loanID,
	        @RequestParam(value = "userID", required = false) Integer userID,
	        @RequestParam(value = "titleCompanyID", required = false) Integer titleCompanyID,
	        @RequestParam(value = "homeOwnInsCompanyID", required = false) Integer homeOwnInsCompanyID) {

		LoanVO loan = new LoanVO();
		loan.setId(loanID);

		EditLoanTeamVO editLoanTeamVO = new EditLoanTeamVO();
		editLoanTeamVO.setUserID(userID);
		editLoanTeamVO.setLoanID(loanID);
		editLoanTeamVO.setHomeOwnInsCompanyID(homeOwnInsCompanyID);
		editLoanTeamVO.setTitleCompanyID(titleCompanyID);
		if (userID != null && userID > 0) {
			UserVO user = new UserVO();
			user.setId(userID);
			boolean result = loanService.removeFromLoanTeam(loan, user);
			editLoanTeamVO.setOperationResult(result);

		} else if (titleCompanyID != null && titleCompanyID > 0) {
			TitleCompanyMasterVO companyMaster = new TitleCompanyMasterVO();
			companyMaster.setId(titleCompanyID);
			boolean result = loanService
			        .removeFromLoanTeam(loan, companyMaster);
			editLoanTeamVO.setOperationResult(result);
		} else if (homeOwnInsCompanyID != null && homeOwnInsCompanyID > 0) {
			HomeOwnersInsuranceMasterVO companyMaster = new HomeOwnersInsuranceMasterVO();
			companyMaster.setId(homeOwnInsCompanyID);
			boolean result = loanService
			        .removeFromLoanTeam(loan, companyMaster);
			editLoanTeamVO.setOperationResult(result);
		}

		CommonResponseVO responseVO = RestUtil
		        .wrapObjectForSuccess(editLoanTeamVO);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/team", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO retreiveLoanTeam(
	        @PathVariable Integer loanID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);
		List<UserVO> team = loanService.retreiveLoanTeam(loan);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(team);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/rateCheck", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO canRateBeLocked(
	        @PathVariable Integer loanID) {
		boolean status = false;
		LoanVO loanVO = loanService.getLoanByID(loanID);
		if (loanVO != null) {
			Integer loanManagerWorkflowExecId = loanVO
			        .getLoanManagerWorkflowID();
			if (loanManagerWorkflowExecId != null) {
				WorkflowExec workflowExec = workflowService
				        .getWorkflowExecFromId(loanManagerWorkflowExecId);
				if (workflowExec != null) {

					WorkflowItemMaster initialContactWorkflow = workflowService
					        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_INITIAL_CONTACT);
					if (initialContactWorkflow != null) {
						WorkflowItemExec workflowItemExec = workflowService
						        .getWorkflowItemExecByType(workflowExec,
						                initialContactWorkflow);
						if (workflowItemExec != null) {
							if (workflowItemExec.getStatus().equalsIgnoreCase(
							        WorkItemStatus.COMPLETED.getStatus())) {
								WorkflowItemMaster WorkflowItem = workflowService
								        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_1003_COMPLETE);
								if (WorkflowItem != null) {
									WorkflowItemExec workitemExec = workflowService
									        .getWorkflowItemExecByType(
									                workflowExec, WorkflowItem);
									if (workitemExec != null) {
										if (workitemExec
										        .getStatus()
										        .equalsIgnoreCase(
										                WorkItemStatus.COMPLETED
										                        .getStatus())) {
											status = true;
										} else {
											status = false;
										}
									} else {
										status = false;
									}
								} else {
									status = false;
								}
							} else {
								status = false;
							}
						} else {
							status = false;
						}
					} else {
						status = false;
					}
				} else {
					status = false;
				}
			} else {
				status = false;
			}
		} else {
			status = false;
		}
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(status);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/extendedTeam", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO retreiveExtendedLoanTeam(
	        @PathVariable Integer loanID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);
		ExtendedLoanTeamVO extendedLoanTeamVO = loanService
		        .findExtendedLoanTeam(loan);
		CommonResponseVO responseVO = RestUtil
		        .wrapObjectForSuccess(extendedLoanTeamVO);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/manager", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO retreiveLoanManagers(
	        @PathVariable Integer loanID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);
		List<UserVO> team = loanService.retreiveLoanManagers(loan);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(team);

		return responseVO;
	}

	// TODO-move this to User profile rest service
	@RequestMapping(value = "/retrieveDashboard/{userID}")
	public @ResponseBody CommonResponseVO retrieveDashboard(
	        @PathVariable Integer userID) {
		UserVO user = new UserVO();
		user.setId(userID);

		LoanDashboardVO responseVO = loanService.retrieveDashboard(user);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}

	// TODO-move this to User profile rest service
	@RequestMapping(value = "/retrieveDashboardForWorkLoans/{userID}")
	public @ResponseBody CommonResponseVO retrieveDashboardForWorkingLoan(
	        @PathVariable Integer userID) {
		UserVO user = new UserVO();
		user.setId(userID);
		//TODO added for to get mobilepreference and carrier info 
		user=userProfileService.findUser(userID);
		LoanDashboardVO responseVO = loanService
		        .retrieveDashboardForWorkLoans(user);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}

	// TODO-move this to User profile rest service
	@RequestMapping(value = "/retrieveDashboardForMyLoans/{userID}")
	public @ResponseBody CommonResponseVO retrieveDashboardForMyLoan(
	        @PathVariable Integer userID,
	        @RequestParam(required = false) String startlimit,
	        @RequestParam(required = false) String count) {
		UserVO user = new UserVO();
		user.setId(userID);
		CommonResponseVO commonResponseVO = null;
		if (startlimit != null) {
			LoanDashboardVO responseVO = loanService
			        .retrieveDashboardForWorkLoans(user, startlimit, count);
			commonResponseVO=RestUtil.wrapObjectForSuccess(responseVO);
		} else {
			LoanDashboardVO responseVO = loanService
			        .retrieveDashboardForWorkLoans(user);
			commonResponseVO=RestUtil.wrapObjectForSuccess(responseVO);
		}

		return commonResponseVO;
	}

	// TODO-move this to User profile rest service
	@RequestMapping(value = "/retrieveDashboardForArchiveLoans/{userID}")
	public @ResponseBody CommonResponseVO retrieveDashboardForArchiveLoans(
	        @PathVariable Integer userID) {
		UserVO user = new UserVO();
		user.setId(userID);

		LoanDashboardVO responseVO = loanService
		        .retrieveDashboardForArchiveLoans(user);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}

	// TODO-move this to User profile rest service
	@RequestMapping(value = "/{loanID}/retrieveDashboard")
	public @ResponseBody CommonResponseVO retrieveLoanForDashboard(
	        @PathVariable Integer loanID) {

		User user = utils.getLoggedInUser();
		if (user == null) {
			throw new BaseRestException();
			// return RestUtil.wrapObjectForFailure(null, "403",
			// "User Not Logged in.");
		}

		// UserVO userVO = User.convertFromEntityToVO(user);

		UserVO userVO = userProfileService.findUser(user.getId());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		try {
			LoanCustomerVO responseVO = loanService.retrieveDashboard(userVO,
			        loanVO);
			return RestUtil.wrapObjectForSuccess(responseVO);
		} catch (Exception e) {
			LOG.error("Exception in retrieve dashboard based on loan", e);
			return RestUtil.wrapObjectForFailure(null, "500",
			        "Internal Server Error");
		}

	}

	@RequestMapping(value = "/activeloan/get/{userID}")
	public @ResponseBody CommonResponseVO geActivetLoanOfUser(
	        @PathVariable Integer userID) {

		UserVO user = new UserVO();
		user.setId(userID);
		LoanVO loansList = loanService.getActiveLoanOfUser(user);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loansList);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}

	@RequestMapping(value = "/searchTitleCompanyOrHomeOwnIns")
	public @ResponseBody CommonResponseVO searchTitleCompany(
	        @RequestParam(required = false) String companyName,
	        @RequestParam(required = true) String code) {

		String homeInsCode = "HOME_OWN_INS";
		String titleCompanyCode = "TITLE_COMPANY";
		if (companyName == null || companyName.trim().isEmpty())
			companyName = "";
		else
			companyName = companyName.toLowerCase();

		if (homeInsCode.equals(code)) {
			HomeOwnersInsuranceMasterVO ins = new HomeOwnersInsuranceMasterVO();
			ins.setName(companyName);
			List<HomeOwnersInsuranceMasterVO> companyList = loanService
			        .findHomeOwnInsByName(ins);
			return RestUtil.wrapObjectForSuccess(companyList);
		} else if (titleCompanyCode.equals(code)) {
			TitleCompanyMasterVO titleCompany = new TitleCompanyMasterVO();
			titleCompany.setName(companyName);
			List<TitleCompanyMasterVO> companyList = loanService
			        .findTitleCompanyByName(titleCompany);
			return RestUtil.wrapObjectForSuccess(companyList);
		}
		return RestUtil.wrapObjectForFailure(null, "400", "User could not be added to team");
	}

	@RequestMapping(value = "/titleCompany", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO addTitleCompany(
	        @RequestBody String titleCompanyStr) {

		TitleCompanyMasterVO vo = new Gson().fromJson(titleCompanyStr,
		        TitleCompanyMasterVO.class);

		vo = loanService.addTitleCompany(vo);

		return RestUtil.wrapObjectForSuccess(vo);
	}

	@RequestMapping(value = "/homeOwnersInsurance", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO addhomeOwnersInsurance(
	        @RequestBody String homeOwnInsStr) {
		HomeOwnersInsuranceMasterVO vo = new Gson().fromJson(homeOwnInsStr,
		        HomeOwnersInsuranceMasterVO.class);

		vo = loanService.addHomeOwnInsCompany(vo);

		return RestUtil.wrapObjectForSuccess(vo);
	}

	@RequestMapping(value = "/LoanDashBoardOnSearch", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO LoanDashboardOnSearch(
	        @RequestBody String searchData)

	{

		LoanUserSearchVO searchVO = new Gson().fromJson(searchData,
		        LoanUserSearchVO.class);

		LoanDashboardVO responseVO = loanService.searchUsers(searchVO);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}

	@RequestMapping(value = "/loanTurnAroundTime/{loanId}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO addLoanTurnArounTime(
	        @PathVariable Integer loanId) {
		loanService.saveAllLoanTurnAroundTimeForLoan(loanId);
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		return RestUtil.wrapObjectForSuccess(commonResponseVO);
	}

	@RequestMapping(value = "/TurnAroundTime/{loanId}/{workFlowItemId}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO retrieveLoanTurnArounTime(
	        @PathVariable Integer loanId, @PathVariable Integer workFlowItemId) {
		LoanTurnAroundTimeVO aroundTimeVO = loanService
		        .retrieveTurnAroundTimeByLoan(loanId, workFlowItemId);
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		commonResponseVO.setResultObject(aroundTimeVO);
		return RestUtil.wrapObjectForSuccess(commonResponseVO);
	}

	@RequestMapping(value = "/appform/{userId}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO retrieveLoanAppForm(
	        @PathVariable Integer userId) {
		UserVO userVO = new UserVO(userId);
		LoanAppFormVO loanAppFormVO = loanService.retrieveLoanAppForm(userVO);
		Gson gson = new Gson();

		/*
		 * ObjectMapper mapper = new ObjectMapper(); StringWriter sw = new
		 * StringWriter(); try { mapper.writeValue(sw, loanAppFormVO); } catch
		 * (JsonGenerationException e) { e.printStackTrace(); } catch
		 * (JsonMappingException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
		return RestUtil.wrapObjectForSuccess(gson.toJson(loanAppFormVO));
	}

	@RequestMapping(value = "/purchaseDocument/expiryDate", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO setExpiryDate(
	        @RequestParam(value = "loanId", required = false) String loanId,
	        @RequestParam(value = "date", required = false) String date) {
		CommonResponseVO commonResponseVO = null;
		try {
			LOG.info("loanId : " + loanId + " & " + date);
			loanService.setExpiryDateToPurchaseDocument(
			        Integer.valueOf(loanId), Long.valueOf(date));
			commonResponseVO = RestUtil.wrapObjectForSuccess(true);
		} catch (Exception e) {
			commonResponseVO = RestUtil.wrapObjectForFailure(null, "500",
			        "Exception");
			LOG.error(e.getMessage(), e);
		}

		return commonResponseVO;

	}

	@RequestMapping(value = "/{loanID}", method = RequestMethod.DELETE)
	public @ResponseBody CommonResponseVO markLoanDeleted(
	        @PathVariable Integer loanID) {

		loanService.markLoanDeleted(loanID);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess("Success");
		return responseVO;
	}
	
	

}
