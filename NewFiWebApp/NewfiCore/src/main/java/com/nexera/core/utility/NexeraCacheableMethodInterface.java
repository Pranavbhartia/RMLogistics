package com.nexera.core.utility;

import java.util.HashMap;

public interface NexeraCacheableMethodInterface {

	public void invalidateApplicationRateCache(String loanNumber,
	        String appFormData);

	HashMap<String, String> cacheInvokeRest(String loanNumber,
	        String appFormData);

}
