package com.nexera.common.vo;

import java.util.List;

public class ExtendedLoanTeamVO {

	private Integer loanID;
	private List<UserVO> users;
	private HomeOwnersInsuranceMasterVO homeOwnInsCompany;
	private TitleCompanyMasterVO titleCompany;

	public Integer getLoanID() {
		return loanID;
	}

	public void setLoanID(Integer loanID) {
		this.loanID = loanID;
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

	public HomeOwnersInsuranceMasterVO getHomeOwnInsCompany() {
		return homeOwnInsCompany;
	}

	public void setHomeOwnInsCompany(
	        HomeOwnersInsuranceMasterVO homeOwnInsCompany) {
		this.homeOwnInsCompany = homeOwnInsCompany;
	}

	public TitleCompanyMasterVO getTitleCompany() {
		return titleCompany;
	}

	public void setTitleCompany(TitleCompanyMasterVO titleCompany) {
		this.titleCompany = titleCompany;
	}

}
