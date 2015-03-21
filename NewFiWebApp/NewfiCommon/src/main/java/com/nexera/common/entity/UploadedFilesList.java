package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the uploadedfileslist database table.
 * 
 */
@Entity
@Table(name = "uploadedfileslist")
@NamedQuery(name = "UploadedFilesList.findAll", query = "SELECT l FROM UploadedFilesList l")
public class UploadedFilesList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean isAssigned;
	private Boolean isActivate;
	private String s3path;
	private Loan loan;
	private User uploadedBy;
	private Date uploadedDate;
	private String fileName;
	private String s3ThumbPath;
	private User assignedBy;
	private String uuidFileId;
	private Integer totalPages;
	
	public UploadedFilesList() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "is_assigned", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	@Column(name = "is_activate", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsActivate() {
		return isActivate;
	}

	public void setIsActivate(Boolean isActivate) {
		this.isActivate = isActivate;
	}

	public String getS3path() {
		return s3path;
	}

	public void setS3path(String s3path) {
		this.s3path = s3path;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploaded_date")
	public Date getUploadedDate() {
		return this.uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
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

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploaded_by")
	public User getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(User uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
	
	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "s3thumbnail")
	public String getS3ThumbPath() {
		return s3ThumbPath;
	}

	public void setS3ThumbPath(String s3ThumbPath) {
		this.s3ThumbPath = s3ThumbPath;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_by")
	public User getAssignedBy() {
		return assignedBy;
	}

	
	public void setAssignedBy(User assignedBy) {
		this.assignedBy = assignedBy;
	}

	@Column(name = "uuidfileid")
	public String getUuidFileId() {
		return uuidFileId;
	}

	public void setUuidFileId(String uuidFileId) {
		this.uuidFileId = uuidFileId;
	}

	@Column(name = "totalpages")
	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

}