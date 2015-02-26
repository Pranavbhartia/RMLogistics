package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;

@Component
@Transactional
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanDao loanDao;

	@Override
	public List<LoanVO> getLoansOfUser(UserVO user) {

		List<Loan> list = loanDao.getLoansOfUser(LoanServiceImpl
				.parseUserModel(user));
		return LoanServiceImpl.buildLoanVOList(list);
	}

	@Override
	public LoanVO getLoanByID(Integer loanID) {
		return LoanServiceImpl.buildLoanVO((Loan) loanDao.load(Loan.class,
				loanID));
	}

	@Override
	public boolean addToLoanTeam(LoanVO loan, UserVO user) {

		Loan loanModel = LoanServiceImpl.parseLoanModel(loan);
		User userModel = LoanServiceImpl.parseUserModel(user);

		return loanDao.addToLoanTeam(loanModel, userModel);
	}

	@Override
	public boolean removeFromLoanTeam(LoanVO loan, UserVO user) {

		Loan loanModel = LoanServiceImpl.parseLoanModel(loan);
		User userModel = LoanServiceImpl.parseUserModel(user);
		return loanDao.removeFromLoanTeam(loanModel, userModel);
	}

	@Override
	public List<UserVO> retreiveLoanTeam(LoanVO loan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanVO> retreiveLoansAsManager(UserVO loanManager) {
		// TODO Auto-generated method stub
		return null;
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

}
