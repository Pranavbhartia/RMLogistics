package com.nexera.core.utility;

import java.util.HashMap;

public interface NexeraCacheableMethodInterface {

	String findSticket(String lqbUsername, String lqbPassword);

	public void invalidateApplicationRateCache(String loanNumber,
	        String appFormData);

	HashMap<String, String> cacheInvokeRest(String loanNumber,
	        String appFormData);

}
