package com.nexera.core.utility;

import java.util.HashMap;

public interface NexeraCacheableMethodInterface {

	String findSticket(String lqbUsername, String lqbPassword);

	HashMap<String, String> cacheInvokeRest(String loanNumber,
	        String appFormData);

}
