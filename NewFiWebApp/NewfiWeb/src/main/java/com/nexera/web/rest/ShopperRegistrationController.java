package com.nexera.web.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.FatalException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.RealtorDetailVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.web.rest.util.RestUtil;

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

	@Value("${profile.url}")
	private String profileUrl;

	private static final Logger LOG = LoggerFactory
	        .getLogger(ShopperRegistrationController.class);

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public @ResponseBody String shopperRegistration(String registrationDetails,
	        String teaseRateData, HttpServletRequest request,
	        HttpServletResponse response) throws IOException {

		Gson gson = new Gson();
		LOG.info("registrationDetails - inout xml is" + registrationDetails);
		try {
			LoanAppFormVO loaAppFormVO = gson.fromJson(registrationDetails,
			        LoanAppFormVO.class);

			// ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<LqbTeaserRateVo>> typeRef = new TypeReference<List<LqbTeaserRateVo>>() {
			};
			// List<HashMap<String, String>> teaseRateDatalist =
			// mapper.readValue(teaseRateData, typeRef);
			List<LqbTeaserRateVo> teaseRateDatalist = gson.fromJson(
			        teaseRateData, typeRef.getType());

			String emailId = loaAppFormVO.getUser().getEmailId();
			// LOG.info("calling 1234 "+
			// loaAppFormVO.getRefinancedetails().getCurrentMortgageBalance());
			LOG.info("calling UserName : "
			        + loaAppFormVO.getUser().getFirstName());

			UserVO user = userProfileService.registerCustomer(loaAppFormVO,
			        teaseRateDatalist);

			userProfileService.crateWorkflowItems(user.getDefaultLoanId());
			LOG.info("User succesfully created" + user);
			authenticateUserAndSetSession(emailId, user.getPassword(), request);
		} catch (FatalException e) {
			LOG.error("error while creating user");
			throw new FatalException("User could not be registered");
		} catch (Exception e) {
			LOG.error("error while creating user" + e.getStackTrace());
			throw new FatalException("User could not be registered ");
		}

		return profileUrl + "home.do#myLoan/lock-my-rate";
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO validateUser(
	        String registrationDetails) {
		Gson gson = new Gson();
		LOG.info("TO validate users before registration");
		CommonResponseVO response = new CommonResponseVO();
		ErrorVO error = new ErrorVO();
		LoanAppFormVO loanAppFormVO = gson.fromJson(registrationDetails,
		        LoanAppFormVO.class);
		String emailId = loanAppFormVO.getUser().getEmailId().split(":")[0];
		User user = userProfileService.findUserByMail(emailId);
		if (user != null) {

			error.setMessage(ErrorConstants.REGISTRATION_USER_EXSIST);
			response.setError(error);

		} else {

			response = RestUtil.wrapObjectForSuccess("success");
		}
		return response;

	}

	@RequestMapping(value = "/realtorRegistration", method = RequestMethod.POST)
	public @ResponseBody String realtorRegistration(String registrationDetails,
	        HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

		Gson gson = new Gson();
		LOG.info("registrationDetails - inout xml is" + registrationDetails);
		try {
			LoanAppFormVO loanAppFormVO = gson.fromJson(registrationDetails,
			        LoanAppFormVO.class);
			String emailId = loanAppFormVO.getUser().getEmailId();

			loanAppFormVO.getUser().getUserRole()
			        .setId(UserRolesEnum.REALTOR.getRoleId());

			loanAppFormVO.getUser().setUsername(
			        loanAppFormVO.getUser().getEmailId().split(":")[0]);
			loanAppFormVO.getUser().setEmailId(
			        loanAppFormVO.getUser().getEmailId().split(":")[0]);
			if (loanAppFormVO.getLoanMangerEmail() != null) {
				User userDetail = userProfileService
				        .findUserByMail(loanAppFormVO.getLoanMangerEmail());
				if (userDetail != null) {
					RealtorDetailVO realtor = new RealtorDetailVO();
					realtor.setUser(User.convertFromEntityToVO(userDetail));
					loanAppFormVO.getUser().setRealtorDetail(realtor);
				} else {
					RealtorDetailVO realtor = new RealtorDetailVO();
					loanAppFormVO.getUser().setRealtorDetail(realtor);
				}

			}

			UserVO userVO = userProfileService
			        .createNewUserAndSendMail(loanAppFormVO.getUser());
			authenticateUserAndSetSession(emailId, userVO.getPassword(),
			        request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return profileUrl + "home.do";
	}

	// public UserVO registerCustomer(LoanAppFormVO loaAppFormVO)
	// throws FatalException {
	//
	// try {
	// // CustomerEnagagement customerEnagagement =
	// // userVO.getCustomerEnagagement();
	// UserVO userVO = loaAppFormVO.getUser();
	//
	// userVO.setUsername(userVO.getEmailId().split(":")[0]);
	// userVO.setEmailId(userVO.getEmailId().split(":")[0]);
	// userVO.setUserRole(new UserRoleVO(UserRolesEnum.CUSTOMER));
	//
	// // String password = userVO.getPassword();
	// // UserVO userVOObj= userProfileService.saveUser(userVO);
	// UserVO userVOObj = null;
	// LoanVO loanVO = null;
	//
	// LOG.info("calling createNewUserAndSendMail" + userVO.getEmailId());
	// userVOObj = userProfileService.createNewUserAndSendMail(userVO);
	// // insert a record in the loan table also
	// loanVO = new LoanVO();
	//
	// loanVO.setUser(userVOObj);
	//
	// loanVO.setCreatedDate(new Date(System.currentTimeMillis()));
	// loanVO.setModifiedDate(new Date(System.currentTimeMillis()));
	//
	// // Currently hardcoding to refinance, this has to come from UI
	// // TODO: Add LoanTypeMaster dynamically based on option selected
	// if (loaAppFormVO.getLoanType().getLoanTypeCd()
	// .equalsIgnoreCase("REF")) {
	// loanVO.setLoanType(new LoanTypeMasterVO(LoanTypeMasterEnum.REF));
	// } else {
	// loanVO.setLoanType(new LoanTypeMasterVO(LoanTypeMasterEnum.PUR));
	// }
	//
	// loanVO = loanService.createLoan(loanVO);
	// workflowCoreService.createWorkflow(new WorkflowVO(loanVO.getId()));
	// userVOObj.setDefaultLoanId(loanVO.getId());
	// // create a record in the loanAppForm table
	//
	// LoanAppFormVO loanAppFormVO = new LoanAppFormVO();
	//
	// loanAppFormVO.setUser(userVOObj);
	// loanAppFormVO.setLoan(loanVO);
	// loanAppFormVO.setLoanAppFormCompletionStatus(0);
	// loanAppFormVO.setPropertyTypeMaster(loaAppFormVO
	// .getPropertyTypeMaster());
	//
	// loanAppFormVO.setRefinancedetails(loaAppFormVO
	// .getRefinancedetails());
	// loanAppFormVO.setPurchaseDetails(loaAppFormVO.getPurchaseDetails());
	// loanAppFormVO.setLoanType(loaAppFormVO.getLoanType());
	// loanAppFormVO.setMonthlyRent(loaAppFormVO.getMonthlyRent());
	//
	// // if(customerEnagagement.getLoanType().equalsIgnoreCase("REF")){
	// // loanAppFormVO.setLoanType(new
	// // LoanTypeMasterVO(LoanTypeMasterEnum.REF));
	// // }
	// LOG.info("loanAppFormService.create(loanAppFormVO)");
	// loanAppFormService.create(loanAppFormVO);
	//
	// return userVOObj;
	// } catch (Exception e) {
	// LOG.error("User registration failed. Generating an alert"
	// + loaAppFormVO);
	// throw new FatalException("Error in User registration", e);
	// }
	// }

	private void authenticateUserAndSetSession(String emailId, String password,
	        HttpServletRequest request) {
		// If there is any session clear it
		// if (request.getSession() != null) {
		// request.getSession().invalidate();
		// LOG.debug("Clearing old sessions");
		// }

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
		        emailId, password);
		HttpSession session = request.getSession(false);
		String sessionId = (session != null) ? session.getId() : null;
		if (sessionId == null) {
			request.getSession(Boolean.TRUE);
		}
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager
		        .authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}
}
