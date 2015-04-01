package com.nexera.web.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.enums.LoanTypeMasterEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CustomerEnagagement;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.workflow.vo.WorkflowVO;

@RestController
@RequestMapping(value = "/shopper")
public class ShopperRegistrationController {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserDetailsService userDetailsSvc;

	@Autowired
	private LoanService loanService;

	@Autowired
	protected LoanAppFormService loanAppFormService;

	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	WorkflowCoreService workflowCoreService;


	private static final Logger LOG = LoggerFactory
	        .getLogger(ShopperRegistrationController.class);

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public @ResponseBody String shopperRegistration(String registrationDetails,
	        HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

		Gson gson = new Gson();
		LOG.info("registrationDetails - inout xml is" + registrationDetails);
		UserVO userVO = gson.fromJson(registrationDetails, UserVO.class);

		CustomerEnagagement customerEnagagement = userVO.getCustomerEnagagement(); 
		String emailId = userVO.getEmailId();

		userVO.setUsername(userVO.getEmailId().split(":")[0]);
		userVO.setEmailId(userVO.getEmailId().split(":")[0]);
		// String password = userVO.getPassword();
		// UserVO userVOObj= userProfileService.saveUser(userVO);
		UserVO userVOObj = null;
		LoanVO loanVO = null;
		try {
			userVOObj = userProfileService.createNewUserAndSendMail(userVO);
			// insert a record in the loan table also
			loanVO = new LoanVO();

			loanVO.setUser(userVOObj);

			loanVO.setCreatedDate(new Date(System.currentTimeMillis()));
			loanVO.setModifiedDate(new Date(System.currentTimeMillis()));

			// Currently hardcoding to refinance, this has to come from UI
			// TODO: Add LoanTypeMaster dynamically based on option selected
			loanVO.setLoanType(new LoanTypeMasterVO(LoanTypeMasterEnum.REF));

		

			loanVO = loanService.createLoan(loanVO);
			workflowCoreService.createWorkflow(new WorkflowVO(loanVO.getId()));
			// create a record in the loanAppForm table

			LoanAppFormVO loanAppFormVO = new LoanAppFormVO();
			userVOObj.setCustomerEnagagement(customerEnagagement);
			loanAppFormVO.setUser(userVOObj);
			loanAppFormVO.setLoan(loanVO);
			loanAppFormVO.setLoanAppFormCompletionStatus(0);
			
			if(customerEnagagement.getLoanType().equalsIgnoreCase("REF")){
				loanAppFormVO.setLoanType(new LoanTypeMasterVO(LoanTypeMasterEnum.REF));
			}
			
			loanAppFormService.create(loanAppFormVO);

		} catch (InvalidInputException e) {

			e.printStackTrace();
		} catch (UndeliveredEmailException e) {

			e.printStackTrace();
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		authenticateUserAndSetSession(emailId, userVOObj.getPassword(), request);

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
