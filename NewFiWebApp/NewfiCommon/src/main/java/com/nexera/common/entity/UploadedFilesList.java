package com.nexera.common.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class UploadedFilesList {

	private Integer id;
	private String s3path;
	private User user;
	private Boolean isAssigned ;
	private Date addedOn ;
	private Boolean isactivate;
	private Loan loan ;
	
	
	@Column(name = "s3path")
	public String getS3path() {
		return s3path;
	}
	public void setS3path(String s3path) {
		this.s3path = s3path;
	}
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "isassigned",columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsAssigned() {
		return isAssigned;
	}
	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
	
	@Column(name = "addedon")
	public Date getAddedOn() {
		return addedOn;
	}
	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	
	@Column(name = "isactivate",columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsactivate() {
		return isactivate;
	}
	public void setIsactivate(Boolean isactivate) {
		this.isactivate = isactivate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan")
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
