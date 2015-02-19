package com.nexera.newfi.core.utility;

import java.util.UUID;

public class NewfiUtility {

	//Place for all reusable static methods 
	
	public static long generateRandomString() {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		return uuid.getMostSignificantBits();
	}
	
}
