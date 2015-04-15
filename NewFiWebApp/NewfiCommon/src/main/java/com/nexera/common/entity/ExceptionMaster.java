package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the exceptionmaster database table.
 * 
 */
@Entity
@Table(name = "exceptionmaster")
@NamedQuery(name = "ExceptionMaster.findAll", query = "SELECT e FROM ExceptionMaster e")
public class ExceptionMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private String exceptionType;
	private int id;
	private String description;

	public ExceptionMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "exception_type")
	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

}