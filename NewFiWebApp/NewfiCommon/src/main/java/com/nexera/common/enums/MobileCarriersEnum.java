package com.nexera.common.enums;

public enum MobileCarriersEnum {
	CARRIER_AT_T("@txt.att.net", "AT&T"), CARRIER_T_MOBILE("@tmomail.net",
	        "T-Mobile"), CARRIER_VERIZON("@vtext.com", "Verizon"), CARRIER_SPRINT(
	        "@messaging.sprintpcs.com", "Sprint"), CARRIER_SPRINT_ALTERNATIVE(
	        "@pm.sprint.com", "Sprint"), CARRIER_VIRGIN("@vmobl.com",
	        "Virgin Mobile"), CARRIER_TRACFONE("@mmst5.tracfone.com",
	        "Tracfone"), CARRIER_METRO_PCS("@mymetropcs.com", "Metro Pcs"), CARRIER_BOOST_MOBILE(
	        "@myboostmobile.com", "Boost Mobile"), CARRIER_CRICKET(
	        "@sms.mycricket.com", "Cricket"), CARRIER_NEXTEL(
	        "@messaging.nextel.com", "Nextel"), CARRIER_ALLTEL(
	        "@message.alltel.com", "Alltel"), CARRIER_PTEL("@ptel.com", "Ptel"), CARRIER_SUNCOM(
	        "@tms.suncom.com", "Suncom"), CARRIER_QUEST("@qwestmp.com", "Quest"), CARRIER_US_CELLULAR(
	        "@email.uscc.net", "US Cellular"), CARRIER_CINGULAR(
	        "@mobile.mycingular.com", "Cingular");

	private MobileCarriersEnum(String carrierEmail, String carrierName) {
		this.carrierEmail = carrierEmail;
		this.carrierName = carrierName;
	}

	public String getCarrierEmail() {
		return carrierEmail;
	}

	public String getCarrierName() {
		return carrierName;
	}

	private String carrierEmail;
	private String carrierName;

	public static MobileCarriersEnum getCarrierEmailForName(String carrierName) {
		MobileCarriersEnum mobileCarriers = null;
		for (MobileCarriersEnum value : MobileCarriersEnum.values()) {
			if (value.carrierName.equalsIgnoreCase(carrierName)) {
				mobileCarriers = value;
				break;
			}
		}
		return mobileCarriers;

	}
	
	public static MobileCarriersEnum getCarrierNameForEmail(String carrierEmail) {
		MobileCarriersEnum mobileCarriers = null;
		for (MobileCarriersEnum value : MobileCarriersEnum.values()) {
			if (value.carrierEmail.equalsIgnoreCase(carrierEmail)) {
				mobileCarriers = value;
				break;
			}
		}
		return mobileCarriers;

	}


}
