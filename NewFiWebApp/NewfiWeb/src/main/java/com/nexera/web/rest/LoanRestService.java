package com.nexera.web.rest;

import java.util.List;

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
import com.nexera.common.entity.HomeOwnersInsuranceMaster;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.User;
import com.nexera.common.exception.BaseRestException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.EditLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/loan/*")
public class LoanRestService {

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private Utils utils;

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

	@RequestMapping(value = "/{loanID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoanByID(
	        @PathVariable Integer loanID) {

		LoanVO loanVO = loanService.getLoanByID(loanID);
		if (loanVO != null) {
			loanVO.setLoanTeam(loanService.retreiveLoanTeam(loanVO));
		}

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loanVO);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/team", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO addToLoanTeam(
	        @PathVariable Integer loanID,
	        @RequestParam(value = "userID") Integer userID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);

		UserVO user = new UserVO();
		user.setId(userID);
		boolean result = loanService.addToLoanTeam(loan, user);
		if (result) {
			user = userProfileService.loadInternalUser(userID);
		}
		EditLoanTeamVO editLoanTeamVO = new EditLoanTeamVO();
		editLoanTeamVO.setOperationResult(result);
		editLoanTeamVO.setUserID(userID);
		editLoanTeamVO.setLoanID(loanID);
		editLoanTeamVO.setUser(user);
		CommonResponseVO responseVO = RestUtil
		        .wrapObjectForSuccess(editLoanTeamVO);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/team", method = RequestMethod.DELETE)
	public @ResponseBody CommonResponseVO removeFromLoanTeam(
	        @PathVariable Integer loanID,
	        @RequestParam(value = "userID") Integer userID) {

		LoanVO loan = new LoanVO();
		loan.setId(loanID);

		UserVO user = new UserVO();
		user.setId(userID);
		boolean result = loanService.removeFromLoanTeam(loan, user);
		EditLoanTeamVO editLoanTeamVO = new EditLoanTeamVO();
		editLoanTeamVO.setOperationResult(result);
		editLoanTeamVO.setUserID(userID);
		editLoanTeamVO.setLoanID(loanID);
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

        LoanDashboardVO responseVO = loanService.retrieveDashboardForWorkLoans(user);

        return RestUtil.wrapObjectForSuccess(responseVO);
    }
    
    // TODO-move this to User profile rest service
    @RequestMapping(value = "/retrieveDashboardForMyLoans/{userID}")
    public @ResponseBody CommonResponseVO retrieveDashboardForMyLoan(
            @PathVariable Integer userID) {
        UserVO user = new UserVO();
        user.setId(userID);

        LoanDashboardVO responseVO = loanService.retrieveDashboardForMyLoans(user);

        return RestUtil.wrapObjectForSuccess(responseVO);
    }
    
    // TODO-move this to User profile rest service
    @RequestMapping(value = "/retrieveDashboardForArchiveLoans/{userID}")
    public @ResponseBody CommonResponseVO retrieveDashboardForArchiveLoans(
            @PathVariable Integer userID) {
        UserVO user = new UserVO();
        user.setId(userID);

        LoanDashboardVO responseVO = loanService.retrieveDashboardForArchiveLoans(user);

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
		UserVO userVO = userProfileService.buildUserVO(user);
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);

		LoanCustomerVO responseVO = loanService.retrieveDashboard(userVO,
		        loanVO);

		return RestUtil.wrapObjectForSuccess(responseVO);
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
		if(companyName==null || companyName.trim().isEmpty())
				companyName="";
		else companyName=companyName.toLowerCase();
		
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
		return RestUtil.wrapObjectForFailure(null, "400", "Bad request");
	}
}
