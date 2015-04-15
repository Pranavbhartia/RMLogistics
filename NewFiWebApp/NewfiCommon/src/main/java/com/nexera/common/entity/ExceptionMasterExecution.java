package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the batchjobmaster database table.
 * 
 */
@Entity
@Table(name = "exceptionmasterexecution")
@NamedQuery(name = "ExceptionMasterExecution.findAll", query = "SELECT e FROM ExceptionMasterExecution e")
public class ExceptionMasterExecution implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String exceptionMessage;
	private Date exceptionTime;
	private ExceptionMaster exceptionMaster;

	public ExceptionMasterExecution() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "exception_message")
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "exception_time")
	public Date getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	// bi-directional many-to-one association to BatchJobMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exceptionmasterid")
	public ExceptionMaster getExceptionMaster() {
		return exceptionMaster;
	}

	public void setExceptionMaster(ExceptionMaster exceptionMaster) {
		this.exceptionMaster = exceptionMaster;
	}

}