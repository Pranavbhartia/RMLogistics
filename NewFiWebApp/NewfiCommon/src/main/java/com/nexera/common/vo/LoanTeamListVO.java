package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author rohit
 *
 */
public class LoanTeamListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<LoanTeamVO> loanTeamList;

	public List<LoanTeamVO> getLoanTeamList() {
		return loanTeamList;
	}

	public void setLoanTeamList(List<LoanTeamVO> loanTeamList) {
		this.loanTeamList = loanTeamList;
	}

}
