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
		try {
		LoanAppFormVO loaAppFormVO = gson.fromJson(registrationDetails,LoanAppFormVO.class);
		LOG.info("calling 1234 "+loaAppFormVO.getRefinancedetails().getCurrentMortgageBalance());
		LOG.info("calling 1234 "+loaAppFormVO.getUser().getFirstName());
		UserVO userVO = loaAppFormVO.getUser();
		LOG.info("calling 1234  "+userVO.getEmailId());
	//	CustomerEnagagement customerEnagagement = userVO.getCustomerEnagagement(); 
		String emailId = userVO.getEmailId();

		userVO.setUsername(userVO.getEmailId().split(":")[0]);
		userVO.setEmailId(userVO.getEmailId().split(":")[0]);
		// String password = userVO.getPassword();
		// UserVO userVOObj= userProfileService.saveUser(userVO);
		UserVO userVOObj = null;
		LoanVO loanVO = null;
		
			LOG.info("calling createNewUserAndSendMail"+userVO.getEmailId());
			
			LOG.info("userVO in Shopper"+userVO.getCustomerDetail());
				userVOObj = userProfileService.createNewUserAndSendMail(userVO);
			
			LOG.info("userVOObj in Shopper"+userVOObj.getCustomerDetail());
				
			
			
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
		//	userVOObj.setCustomerEnagagement(customerEnagagement);
			//LOG.info("userVOObj.getCustomerDetail().getCustomerSpouseDetail()"+userVOObj.getCustomerDetail().getCustomerSpouseDetail());
			
			loanAppFormVO.setUser(userVOObj);
			loanAppFormVO.setLoan(loanVO);
			loanAppFormVO.setLoanAppFormCompletionStatus(0);
			loanAppFormVO.setPropertyTypeMaster(loaAppFormVO.getPropertyTypeMaster());
			loanAppFormVO.setRefinancedetails(loaAppFormVO.getRefinancedetails());
			
		//	if(customerEnagagement.getLoanType().equalsIgnoreCase("REF")){
			//	loanAppFormVO.setLoanType(new LoanTypeMasterVO(LoanTypeMasterEnum.REF));
		//	}
LOG.info("loanAppFormService.create(loanAppFormVO)");
			loanAppFormService.create(loanAppFormVO);
			authenticateUserAndSetSession(emailId, userVOObj.getPassword(), request);

		} catch (InvalidInputException e) {

			e.printStackTrace();
		} catch (UndeliveredEmailException e) {

			e.printStackTrace();
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
