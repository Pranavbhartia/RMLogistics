package com.nexera.common.compositekey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QuoteCompositeKey implements Serializable {

	private String userName;
	
	private Integer internalUserId;
	
	@Column(name="prospect_username")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="internal_user_id")
	public Integer getInternalUserId() {
		return internalUserId;
	}
	public void setInternalUserId(Integer internalUserId) {
		this.internalUserId = internalUserId;
	}
		
}
