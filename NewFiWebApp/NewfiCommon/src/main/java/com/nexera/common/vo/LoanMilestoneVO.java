package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;

public class LoanMilestoneVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte[] comments;
	private Date createdDate;
	private Date endDate;
	private Date startDate;
	private String status;
	private LoanMilestoneMasterVO loanMilestoneMaster;
	private LoanVO loan;
	private int order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getComments() {
		return comments;
	}

	public void setComments(byte[] comments) {
		this.comments = comments;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LoanMilestoneMasterVO getLoanMilestoneMaster() {
		return loanMilestoneMaster;
	}

	public void setLoanMilestoneMaster(LoanMilestoneMasterVO loanMilestoneMaster) {
		this.loanMilestoneMaster = loanMilestoneMaster;
	}

	public LoanVO getLoan() {
		return loan;
	}

	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public static LoanMilestone convertFromVOToEntity(
	        final LoanMilestoneVO inputVO) {
		if (inputVO == null) {
			return null;
		}
		LoanMilestone loanMilestone = new LoanMilestone();
		loanMilestone.setId(inputVO.getId());
		if (inputVO.getComments() != null) {
			loanMilestone.setComments(inputVO.getComments().toString());
		}
		loanMilestone.setStatus(inputVO.getStatus());
		loanMilestone.setLoanMilestoneMaster(LoanMilestoneMasterVO
		        .convertFromVOToEntity(inputVO.getLoanMilestoneMaster()));
		loanMilestone.setLoan(new Loan(inputVO.getLoan().getId()));
		return loanMilestone;
	}

	public static LoanMilestoneVO convertFromEntityToVO(
	        final LoanMilestone inputEntity) {
		if (inputEntity == null) {
			return null;
		}
		LoanMilestoneVO loanMilestoneVO = new LoanMilestoneVO();
		loanMilestoneVO.setId(inputEntity.getId());
		if (loanMilestoneVO.getComments() != null) {
			loanMilestoneVO.setComments(inputEntity.getComments().getBytes());
		}
		loanMilestoneVO.setStatus(inputEntity.getStatus());
		loanMilestoneVO.setLoanMilestoneMaster(LoanMilestoneMasterVO
		        .convertFromEntityToVO(inputEntity.getLoanMilestoneMaster()));
		loanMilestoneVO.setLoan(new LoanVO(inputEntity.getLoan().getId()));
		return loanMilestoneVO;
	}
}