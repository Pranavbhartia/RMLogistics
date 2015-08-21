package com.nexera.common.enums;

public enum ServiceCodes {

	USER_PROFILE_SERVICE(4),MULE_SERVICE(5);
	
	
	private int serviceID;

	ServiceCodes(int serviceId) {
		this.serviceID = serviceId;
	}

	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	
}
