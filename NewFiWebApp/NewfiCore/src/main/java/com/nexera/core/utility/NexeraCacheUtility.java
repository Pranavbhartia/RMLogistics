package com.nexera.core.utility;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WebServiceOperations;
import com.nexera.core.lqb.broker.LqbInvoker;

@Component
public class NexeraCacheUtility implements NexeraCacheableMethodInterface {

	@Autowired
	private LqbInvoker lqbInvoker;

	@Value("${cryptic.key}")
	private String crypticKey;

	@Autowired
	NexeraUtility nexeraUtility;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(NexeraUtility.class);

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	@Override
	@Cacheable(value = "sTicket")
	public String findSticket(String lqbUsername, String lqbPassword) {
		LOGGER.debug("findSticket of cacheMethod called");
		String sTicket = null;
		if (null != lqbUsername && null != lqbPassword) {
			lqbUsername = lqbUsername.replaceAll("[^\\x00-\\x7F]", "");
			try {
				lqbUsername = nexeraUtility.decrypt(salt, crypticKey,
				        lqbUsername);
			} catch (InvalidKeyException | NoSuchAlgorithmException
			        | InvalidKeySpecException | NoSuchPaddingException
			        | InvalidAlgorithmParameterException
			        | IllegalBlockSizeException | BadPaddingException
			        | IOException e) {

				e.printStackTrace();
			}

			lqbPassword = lqbPassword.replaceAll("[^\\x00-\\x7F]", "");
			try {
				lqbPassword = nexeraUtility.decrypt(salt, crypticKey,
				        lqbPassword);
			} catch (InvalidKeyException | NoSuchAlgorithmException
			        | InvalidKeySpecException | NoSuchPaddingException
			        | InvalidAlgorithmParameterException
			        | IllegalBlockSizeException | BadPaddingException
			        | IOException e) {

				e.printStackTrace();
			}

			org.json.JSONObject authOperationObject = NexeraUtility
			        .createAuthObject(
			                WebServiceOperations.OP_NAME_AUTH_GET_USER_AUTH_TICET,
			                lqbUsername, lqbPassword);
			LOGGER.debug("Invoking LQB service to fetch user authentication ticket ");
			String authTicketJson = lqbInvoker
			        .invokeRestSpringParseObjForAuth(authOperationObject
			                .toString());
			if (!authTicketJson.contains("Access Denied")) {
				sTicket = authTicketJson;

			} else {
				LOGGER.error("Ticket Not Generated For This User ");
			}

		} else {
			LOGGER.error("LQBUsername or Password are not valid ");
		}
		return sTicket;
	}
}
