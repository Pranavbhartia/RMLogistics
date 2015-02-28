package com.nexera.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/loan/*")
public class LoanRestService {

	@Autowired
	private LoanService loanService;

	@RequestMapping(value = "/getLoansOfUser/{userID}")
	public @ResponseBody String getLoansOfUser(@PathVariable Integer userID) {

		UserVO user = new UserVO();
		user.setId(userID);
		List<LoanVO> loansList = loanService.getLoansOfUser(user);

		CommonResponseVO responseVO = RestUtil
				.wrapObjectForSuccess(loansList);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/getLoanByID/{loanID}")
	public @ResponseBody String getLoanByID(@PathVariable Integer loanID) {

		LoanVO loanVO = loanService.getLoanByID(loanID);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loanVO);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/addToLoanTeam/{loanID}/{userID}")
	public @ResponseBody String addToLoanTeam(@PathVariable Integer loanID,
			@PathVariable Integer userID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);

		UserVO user = new UserVO();
		user.setId(userID);
		boolean result = loanService.addToLoanTeam(loan, user);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(result);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/removeFromLoanTeam/{loanID}/{userID}")
	public @ResponseBody String removeFromLoanTeam(
			@PathVariable Integer loanID, @PathVariable Integer userID) {

		LoanVO loan = new LoanVO();
		loan.setId(loanID);

		UserVO user = new UserVO();
		user.setId(userID);
		boolean result = loanService.removeFromLoanTeam(loan, user);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(result);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/retreiveLoanTeam/{loanID}")
	public @ResponseBody String retreiveLoanTeam(@PathVariable Integer loanID) {
		LoanVO loan = new LoanVO();
		loan.setId(loanID);
		List<UserVO> team = loanService.retreiveLoanTeam(loan);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(team);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/retreiveLoansAsManager/{managerID}")
	public @ResponseBody String retreiveLoansAsManager(
			@PathVariable Integer managerID) {

		UserVO manager = new UserVO();
		manager.setId(managerID);

		List<LoanVO> loans = loanService.retreiveLoansAsManager(manager);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(loans);

		return new Gson().toJson(responseVO);
	}

	
	@RequestMapping(value = "/activeloan/get/{userID}")
	public @ResponseBody String geActivetLoanOfUser(@PathVariable Integer userID) {

		UserVO user = new UserVO();
		user.setId(userID);
		LoanVO loansList = loanService.getActiveLoanOfUser(user);

		CommonResponseVO responseVO = RestUtil
				.wrapObjectForSuccess(loansList);

		return new Gson().toJson(responseVO);
	}
}
