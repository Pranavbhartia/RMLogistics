package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the internaluserdetails database table.
 * 
 */
@Entity
@Table(name="internaluserdetails")
@NamedQuery(name="InternalUserDetail.findAll", query="SELECT i FROM InternalUserDetail i")
public class InternalUserDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean activeInternal;
	private User user;
	private User manager;

	public InternalUserDetail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="active_internal",columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getActiveInternal() {
		return this.activeInternal;
	}

	public void setActiveInternal(Boolean activeInternal) {
		this.activeInternal = activeInternal;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="manager")
	public User getManager() {
		return manager;
	}


	public void setManager(User manager) {
		this.manager = manager;
	}


}