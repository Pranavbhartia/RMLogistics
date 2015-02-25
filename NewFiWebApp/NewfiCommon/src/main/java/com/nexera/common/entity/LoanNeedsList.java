package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loanneedslist database table.
 * 
 */
@Entity
@NamedQuery(name="LoanNeedsList.findAll", query="SELECT l FROM LoanNeedsList l")
public class LoanNeedsList implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte active;
	private String comments;
	private byte deleted;
	private String fileId;
	private String fileUrl;
	private byte mandatory;
	private byte systemAction;
	private Date uploadedDate;
	private Loan loanBean;
	private NeedsListMaster needslistmaster;
	private User user;

	public LoanNeedsList() {
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


	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}


	@Column(name="file_id")
	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	@Column(name="file_url")
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	public byte getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(byte mandatory) {
		this.mandatory = mandatory;
	}


	@Column(name="system_action")
	public byte getSystemAction() {
		return this.systemAction;
	}

	public void setSystemAction(byte systemAction) {
		this.systemAction = systemAction;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="uploaded_date")
	public Date getUploadedDate() {
		return this.uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
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


	//bi-directional many-to-one association to NeedsListMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="need_type")
	public NeedsListMaster getNeedslistmaster() {
		return this.needslistmaster;
	}

	public void setNeedslistmaster(NeedsListMaster needslistmaster) {
		this.needslistmaster = needslistmaster;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="uploaded_by")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}