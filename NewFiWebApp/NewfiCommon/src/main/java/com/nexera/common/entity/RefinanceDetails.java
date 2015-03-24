package com.nexera.common.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "refinancedetails")
@NamedQuery(name = "RefinanceDetails.findAll", query = "SELECT r FROM RefinanceDetails r")
public class RefinanceDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String refinanceOption;
	private String currentMortgageBalance;
	private String currentMortgagePayment;
	private boolean includeTaxes;
	private List<LoanAppForm> loanAppForms;
	
	
	public RefinanceDetails(){
		
	}
	
	
	@Column(name = "refinanceOption")
	public String getRefinanceOption() {
		return refinanceOption;
	}
	public void setRefinanceOption(String refinanceOption) {
		this.refinanceOption = refinanceOption;
	}
	
	@Column(name = "currentMortgageBalance")
	public String getCurrentMortgageBalance() {
		return currentMortgageBalance;
	}
	public void setCurrentMortgageBalance(String currentMortgageBalance) {
		this.currentMortgageBalance = currentMortgageBalance;
	}
	
	@Column(name = "currentMortgagePayment")
	public String getCurrentMortgagePayment() {
		return currentMortgagePayment;
	}
	public void setCurrentMortgagePayment(String currentMortgagePayment) {
		this.currentMortgagePayment = currentMortgagePayment;
	}
	
	@Column(name = "includeTaxes")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isIncludeTaxes() {
		return includeTaxes;
	}
	public void setIncludeTaxes(boolean includeTaxes) {
		this.includeTaxes = includeTaxes;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	// bi-directional many-to-one association to LoanAppForm
			@OneToMany(mappedBy = "refinancedetails")
			public List<LoanAppForm> getLoanappforms() {
				return this.loanAppForms;
			}

			public void setLoanappforms(List<LoanAppForm> loanappforms) {
				this.loanAppForms = loanappforms;
			}

			public LoanAppForm addLoanappform(LoanAppForm loanappform) {
				getLoanappforms().add(loanappform);
				loanappform.setRefinancedetails(this);

				return loanappform;
			}

			public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
				getLoanappforms().remove(loanappform);
				loanappform.setRefinancedetails(null);

				return loanappform;
			}
		
		
	
	
}
