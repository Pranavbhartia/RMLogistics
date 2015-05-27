package com.nexera.core.utility;

public interface NexeraCacheableMethodInterface {

	String findSticket(String lqbUsername, String lqbPassword);

	String cacheInvokeRest(String loanNumber, String appFormData);

}
