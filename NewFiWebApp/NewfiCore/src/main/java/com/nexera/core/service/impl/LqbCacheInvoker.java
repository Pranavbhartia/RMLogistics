package com.nexera.core.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LqbInterface;
import com.nexera.core.utility.NexeraCacheableMethodInterface;
import com.nexera.core.utility.NexeraUtility;

@Component
public class LqbCacheInvoker implements LqbInterface {

	@Value("${muleUrlForLoan}")
	private String muleUrlForLoan;

	@Autowired
	NexeraUtility nexeraUtility;

	@Autowired
	NexeraCacheableMethodInterface cacheableMethodInterface;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(LqbCacheInvoker.class);

	// @Cacheable(cacheName = "teaserRate")
	@Override
	public String invokeRest(String appFormData) {

		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity request = new HttpEntity(appFormData, headers);
			RestTemplate restTemplate = new RestTemplate();
			Date date = new Date();
			LOGGER.debug("Time taken before invoking this rest call for teaser rate is "
			        + date);
			String returnedUser = restTemplate.postForObject(muleUrlForLoan,
			        request, String.class);
			Date date1 = new Date();
			LOGGER.debug("Time taken after invoking this rest call for teaser rate is "
			        + date1);
			JSONObject jsonObject = new JSONObject(returnedUser);

			return jsonObject.get("responseMessage").toString();

		} catch (Exception e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}

		return null;
	}

	@Override
	public String findSticket(LoanAppFormVO loaAppFormVO)
	        throws InvalidKeyException, NoSuchAlgorithmException,
	        InvalidKeySpecException, NoSuchPaddingException,
	        InvalidAlgorithmParameterException, UnsupportedEncodingException,
	        IllegalBlockSizeException, BadPaddingException, IOException {
		LOGGER.debug("findSticket is called.");
		String sTicket = null;
		String lqbUsername = null;
		String lqbPassword = null;
		UserVO internalUser = null;

		User logedInUser = getUserObject();

		/* this is the case when LM or SM will fill the user's form */
		if (logedInUser.getId() != loaAppFormVO.getUser().getId()) {

			lqbUsername = logedInUser.getInternalUserDetail().getLqbUsername();
			lqbPassword = logedInUser.getInternalUserDetail().getLqbPassword();
		} else {
			/*
			 * this is the case when user submits the loan form : at the last
			 * step of while filling the form
			 */
			boolean loanMangerFound = false;
			List<UserVO> loanTeam = loaAppFormVO.getLoan().getLoanTeam();
			if (null != loanTeam && loanTeam.size() > 0)
				for (UserVO user : loanTeam) {
					if (null != user.getInternalUserDetail()
					        && user.getInternalUserDetail()
					                .getInternalUserRoleMasterVO()
					                .getRoleName().equalsIgnoreCase("LM")) {
						/* this user would be either realtor or LM */
						internalUser = user;
						lqbUsername = internalUser.getInternalUserDetail()
						        .getLqbUsername();
						lqbPassword = internalUser.getInternalUserDetail()
						        .getLqbPassword();
						loanMangerFound = true;
						break;
					}
				}
			/* This is the case when LM is not found */
			if (!loanMangerFound) {
				for (UserVO user : loanTeam) {
					if (null != user.getInternalUserDetail()
					        && user.getInternalUserDetail()
					                .getInternalUserRoleMasterVO()
					                .getRoleName().equalsIgnoreCase("SM")) {
						internalUser = user;
						lqbUsername = internalUser.getInternalUserDetail()
						        .getLqbUsername();
						lqbPassword = internalUser.getInternalUserDetail()
						        .getLqbPassword();
						break;
					}
				}
			}

		}

		sTicket = cacheableMethodInterface
		        .findSticket(lqbUsername, lqbPassword);

		return sTicket;
	}

	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}

}
