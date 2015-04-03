package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the loanneedslist database table.
 * 
 */
@Entity
@Table(name = "loanneedslist")
@NamedQuery(name = "LoanNeedsList.findAll", query = "SELECT l FROM LoanNeedsList l")
public class LoanNeedsList implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean active;
	private String comments;
	private Boolean deleted;
	private UploadedFilesList uploadFileId;
	private Boolean mandatory;
	private Boolean systemAction;
	private Loan loan;
	private NeedsListMaster needsListMaster;

	public LoanNeedsList() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	

	
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
	

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Column(name = "system_action",columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getSystemAction() {
		return this.systemAction;
	}

	public void setSystemAction(Boolean systemAction) {
		this.systemAction = systemAction;
	}

	// bi-directional many-to-one association to Loan
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan")
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	// bi-directional many-to-one association to NeedsListMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "need_type")
	public NeedsListMaster getNeedsListMaster() {
		return needsListMaster;
	}

	public void setNeedsListMaster(NeedsListMaster needsListMaster) {
		this.needsListMaster = needsListMaster;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="file_id")
	public UploadedFilesList getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(UploadedFilesList uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
}