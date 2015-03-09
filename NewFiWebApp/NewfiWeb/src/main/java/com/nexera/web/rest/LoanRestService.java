package com.nexera.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.EditLoanTeamVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanVO;
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

	@RequestMapping(value = "/user/{userID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoansOfUser(@PathVariable Integer userID) {

		UserVO user = new UserVO();
		user.setId(userID);
		List<LoanVO> loansList = loanService.getLoansOfUser(user);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loansList);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoanByID(@PathVariable Integer loanID) {

		LoanVO loanVO = loanService.getLoanByID(loanID);
		if (loanVO != null) {
			loanVO.setLoanTeam(loanService.retreiveLoanTeam(loanVO));
		}

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loanVO);

		return responseVO;
	}

	@RequestMapping(value = "/{loanID}/team",method=RequestMethod.POST)
	public @ResponseBody CommonResponseVO addToLoanTeam(@PathVariable Integer loanID,
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

	@RequestMapping(value = "/{loanID}/team",method=RequestMethod.DELETE)
	public @ResponseBody CommonResponseVO removeFromLoanTeam(
			@PathVariable Integer loanID, @RequestParam(value="userID") Integer userID) {

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

	@RequestMapping(value = "/{loanID}/team",method=RequestMethod.GET)
	public @ResponseBody CommonResponseVO retreiveLoanTeam(@PathVariable Integer loanID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);
		List<UserVO> team = loanService.retreiveLoanTeam(loan);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(team);

		return responseVO;
	}

	//TODO-move this to User profile rest service
	@RequestMapping(value = "/retrieveDashboard/{userID}")
	public @ResponseBody CommonResponseVO retrieveDashboard(@PathVariable Integer userID) {
		UserVO user = new UserVO();
		user.setId(userID);

		LoanDashboardVO responseVO = loanService.retrieveDashboard(user);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}

	
	@RequestMapping(value = "/activeloan/get/{userID}")
	public @ResponseBody CommonResponseVO geActivetLoanOfUser(@PathVariable Integer userID) {

		UserVO user = new UserVO();
		user.setId(userID);
		LoanVO loansList = loanService.getActiveLoanOfUser(user);

		CommonResponseVO responseVO = RestUtil
				.wrapObjectForSuccess(loansList);

		return RestUtil.wrapObjectForSuccess(responseVO);
	}
}
