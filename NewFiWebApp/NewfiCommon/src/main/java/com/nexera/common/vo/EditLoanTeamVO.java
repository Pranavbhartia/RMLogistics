package com.nexera.common.vo;

public class EditLoanTeamVO {
	private Boolean operationResult;
	private Integer userID;
	private Integer loanID;
	private UserVO user;

	public Boolean getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(Boolean operationResult) {
		this.operationResult = operationResult;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getLoanID() {
		return loanID;
	}

	public void setLoanID(Integer loanID) {
		this.loanID = loanID;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

}
