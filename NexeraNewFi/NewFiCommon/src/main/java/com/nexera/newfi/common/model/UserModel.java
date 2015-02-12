package com.nexera.newfi.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserModel {

	@javax.persistence.Id
	@Column(name="userid")
	private Integer Id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="fname")
	private String firstName;
	
	@Column(name="lname")
	private String lastName;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UserModel [Id=" + Id + ", email=" + email + ", password="
				+ password + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

	public UserModel(String email, String password, String firstName,
			String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserModel() {
		super();
	}
	
	
	
	
	
}
