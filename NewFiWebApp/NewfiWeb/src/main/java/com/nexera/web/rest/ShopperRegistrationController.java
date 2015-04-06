package com.nexera.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;

@RestController
@RequestMapping(value = "/shopper")
public class ShopperRegistrationController {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	protected AuthenticationManager authenticationManager;

	private static final Logger LOG = LoggerFactory
	        .getLogger(ShopperRegistrationController.class);

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public @ResponseBody String shopperRegistration(String registrationDetails,
	        HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

		Gson gson = new Gson();
		LOG.info("registrationDetails - inout xml is" + registrationDetails);
		try {
			LoanAppFormVO loaAppFormVO = gson.fromJson(registrationDetails,
			        LoanAppFormVO.class);
			String emailId = loaAppFormVO.getUser().getEmailId();
			LOG.info("calling 1234 "
			        + loaAppFormVO.getRefinancedetails()
			                .getCurrentMortgageBalance());
			LOG.info("calling 1234 " + loaAppFormVO.getUser().getFirstName());

			UserVO user = userProfileService.registerCustomer(loaAppFormVO);
			// userProfileService.crateWorkflowItems(user.getDefaultLoanId());
			LOG.info("User succesfully created" + user);
			authenticateUserAndSetSession(emailId, user.getPassword(), request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "./home.do";
	}

	private void authenticateUserAndSetSession(String emailId, String password,
	        HttpServletRequest request) {

		// String username = userVO.getUsername();
		// String password = userVO.getPassword();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
		        emailId, password);

		// generate session if one doesn't exist
		// request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager
		        .authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

}
