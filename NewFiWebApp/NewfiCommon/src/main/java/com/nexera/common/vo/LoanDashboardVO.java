package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

public class LoanDashboardVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int num_found;
	private List<LoanCustomerVO> customers;
	private List<LeadsDashBoardVO> leads;

	public List<LeadsDashBoardVO> getLeads() {
		return leads;
	}

	public void setLeads(List<LeadsDashBoardVO> leads) {
		this.leads = leads;
	}

	public List<LoanCustomerVO> getCustomers() {
		return customers;
	}

	public int getNum_found() {
		return num_found;
	}

	public void setCustomers(List<LoanCustomerVO> customers) {
		this.customers = customers;
	}

	public void setNum_found(int num_found) {
		this.num_found = num_found;
	}

}
