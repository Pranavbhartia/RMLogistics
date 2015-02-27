package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanDetail;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanDetailVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;

@Component
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanDao loanDao;

	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoanVO> getLoansOfUser(UserVO user) {

		List<Loan> list = loanDao.getLoansOfUser(LoanServiceImpl
				.parseUserModel(user));
		return LoanServiceImpl.buildLoanVOList(list);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO getLoanByID(Integer loanID) {
		return LoanServiceImpl.buildLoanVO((Loan) loanDao
				.getLoanWithDetails(loanID));
	}

	@Override
	@Transactional()
	public boolean addToLoanTeam(LoanVO loan, UserVO user) {

		Loan loanModel = LoanServiceImpl.parseLoanModel(loan);
		User userModel = LoanServiceImpl.parseUserModel(user);

		// TODO CHange the added by appropriately, move the get user obj in
		// correct service
		return loanDao.addToLoanTeam(loanModel, userModel, getUserObject());
	}

	@Override
	@Transactional()
	public boolean removeFromLoanTeam(LoanVO loan, UserVO user) {

		Loan loanModel = LoanServiceImpl.parseLoanModel(loan);
		User userModel = LoanServiceImpl.parseUserModel(user);
		return loanDao.removeFromLoanTeam(loanModel, userModel);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserVO> retreiveLoanTeam(LoanVO loanVO) {

		List<User> team = loanDao.retreiveLoanTeam(LoanServiceImpl
				.parseLoanModel(loanVO));

		return LoanServiceImpl.buildUserVOList(team);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoanVO> retreiveLoansAsManager(UserVO loanManager) {

		User manager = LoanServiceImpl.parseUserModel(loanManager);

		return LoanServiceImpl.buildLoanVOList(loanDao
				.retreiveLoansAsManager(manager));
	}

	public static Loan parseLoanModel(LoanVO loanVO) {

		if (loanVO == null)
			return null;

		Loan loan = new Loan();
		loan.setId(loanVO.getId());
		loan.setCreatedDate(loanVO.getCreatedDate());
		loan.setDeleted(loanVO.getDeleted());
		loan.setLoanEmailId(loanVO.getLoanEmailId());
		loan.setLqbFileId(loanVO.getLqbFileId());
		loan.setModifiedDate(loanVO.getModifiedDate());
		loan.setName(loanVO.getName());

		return loan;

	}

	public static LoanVO buildLoanVO(Loan loan) {

		if (loan == null)
			return null;

		LoanVO loanVo = new LoanVO();
		loanVo.setId(loan.getId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setDeleted(loan.getDeleted());
		loanVo.setLoanEmailId(loan.getLoanEmailId());
		loanVo.setLqbFileId(loan.getLqbFileId());
		loanVo.setModifiedDate(loan.getModifiedDate());
		loanVo.setName(loan.getName());

		return loanVo;

	}

	public static List<LoanVO> buildLoanVOList(List<Loan> loanList) {

		if (loanList == null)
			return null;

		List<LoanVO> voList = new ArrayList<LoanVO>();
		for (Loan loan : loanList) {
			voList.add(LoanServiceImpl.buildLoanVO(loan));
		}

		return voList;
	}

	public static User parseUserModel(UserVO userVO) {

		if (userVO == null)
			return null;

		User user = new User();

		user.setId(userVO.getId());

		return user;
	}

	public static UserVO buildUserVO(User user) {

		if (user == null)
			return null;

		UserVO userVO = new UserVO();

		userVO.setId(user.getId());
		userVO.setFirstName(user.getFirstName());
		userVO.setLastName(user.getLastName());
		userVO.setUserRole(LoanServiceImpl.buildUserRoleVO(user.getUserRole()));

		return userVO;
	}

	public static LoanTeamVO buildLoanTeamVO(LoanTeam loanTeam) {
		if (loanTeam == null)
			return null;

		LoanTeamVO loanTeamVO = new LoanTeamVO();
		loanTeamVO.setId(loanTeam.getId());
		loanTeamVO.setUser(LoanServiceImpl.buildUserVO(loanTeam.getUser()));
		loanTeamVO.setActive(loanTeam.getActive());
		return loanTeamVO;

	}

	public static UserRoleVO buildUserRoleVO(UserRole role) {

		if (role == null)
			return null;

		UserRoleVO roleVO = new UserRoleVO();

		roleVO.setId(role.getId());
		roleVO.setRoleCd(role.getRoleCd());
		roleVO.setRoleDescription(role.getRoleDescription());

		return roleVO;

	}

	public static UserRole parseUserRoleModel(UserRoleVO roleVO) {

		if (roleVO == null)
			return null;

		UserRole role = new UserRole();

		role.setId(roleVO.getId());
		role.setRoleCd(roleVO.getRoleCd());
		role.setRoleDescription(roleVO.getRoleDescription());

		return role;

	}

	public static LoanDetailVO buildLoanDetailVO(LoanDetail detail) {
		if (detail == null)
			return null;

		LoanDetailVO detailVO = new LoanDetailVO();
		detailVO.setDownPayment(detail.getDownPayment());
		detailVO.setLoanAmount(detail.getLoanAmount());
		detailVO.setRate(detail.getRate());

		return detailVO;

	}

	public static List<UserVO> buildUserVOList(List<User> userList) {

		if (userList == null)
			return null;

		List<UserVO> voList = new ArrayList<UserVO>();
		for (User user : userList) {
			voList.add(LoanServiceImpl.buildUserVO(user));
		}

		return voList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoanDashboardVO> retrieveDashboard(UserVO userVO) {
		/*
		 * Get all loans this user has access to.
		 */
		List<Loan> loanList = loanDao.retrieveLoanForDashboard(LoanServiceImpl
				.parseUserModel(userVO));

		/*
		 * For each loan the logged in user has access to, retrieve the loan
		 * list
		 */
		/*
		 * Create the dashboardVO object for all the loans
		 */
		return null;
	}

}
