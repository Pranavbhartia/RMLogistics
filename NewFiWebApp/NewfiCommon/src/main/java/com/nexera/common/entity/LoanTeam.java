package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loanteam database table.
 * 
 */
@Entity
@NamedQuery(name="LoanTeam.findAll", query="SELECT l FROM LoanTeam l")
public class LoanTeam implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte active;
	private Date assignedOn;
	private String permissionType;
	private Loan loanBean;
	private User user1;
	private User user2;

	public LoanTeam() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="assigned_on")
	public Date getAssignedOn() {
		return this.assignedOn;
	}

	public void setAssignedOn(Date assignedOn) {
		this.assignedOn = assignedOn;
	}


	@Column(name="permission_type")
	public String getPermissionType() {
		return this.permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}


	//bi-directional many-to-one association to Loan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan")
	public Loan getLoanBean() {
		return this.loanBean;
	}

	public void setLoanBean(Loan loanBean) {
		this.loanBean = loanBean;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assigned_by")
	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}