package com.nexera.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.google.gson.Gson;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.PropertyFileReader;
import com.nexera.common.commons.Utils;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.MasterDataService;

@Controller
public class DefaultController implements InitializingBean {

	@Autowired
	protected Utils utils;

	@Autowired
	protected LoanService loanService;

	@Autowired
	protected MasterDataService masterDataService;

	private static final Logger LOG = LoggerFactory
	        .getLogger(DefaultController.class);

	// Contains the lookup for all the key value pairs to be used in UI for
	// internationalization purpose.
	protected HashMap<String, String> propMap = null;

	protected HashMap<String, HashMap<String, String>> languageMap = new HashMap<String, HashMap<String, String>>();

	protected User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}

	protected void editUserPhoto(String photoUrl, Integer userid) {
		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			if (user.getId() == userid) {
				user.setPhotoImageUrl(photoUrl);
			}
		}
	}

	/**
	 * Loads all the default elements required for the UI. Common method to be
	 * called from all controllers
	 * 
	 * @param model
	 * @param req
	 * @param user
	 * @throws JSONException
	 * @throws IOException
	 */
	public User loadDefaultValuesForCustomer(Model model,
	        HttpServletRequest req, User user) throws IOException {

		JSONObject newfi = new JSONObject();

		try {

			Locale locale = req.getLocale();
			String suffix = locale.toString();

			Map<String, String> localeText = languageMap.get(suffix);
			if (localeText == null) {
				localeText = loadLanguageMap(suffix);
			}
			UserVO userVO = new UserVO();
			userVO.setForView(user);
			LOG.info("Avoiding status code check of loan");
			LoanVO loanVO = loanService.getActiveLoanOfUser(userVO);
			userVO.setDefaultLoanId(loanVO.getId());
			Gson gson = new Gson();
			LoanTeamListVO loanTeamListVO = loanService
			        .getLoanTeamListForLoan(loanVO);
			List<LoanTeamVO> userList = loanTeamListVO.getLoanTeamList();
			List<String> imageList = new ArrayList<String>();
			for (LoanTeamVO loanTeamVO : userList) {
				imageList.add(loanTeamVO.getUser().getPhotoImageUrl());
			}

			model.addAttribute("loanTeamImage", imageList);
			newfi.put("user", gson.toJson(userVO));

			newfi.put("i18n", new JSONObject(localeText));
			model.addAttribute("userVO", userVO);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("newfi", newfi);
		return user;
	}

	/**
	 * Loads all the default elements required for the UI. Common method to be
	 * called from all controllers
	 * 
	 * @param model
	 * @param req
	 * @throws JSONException
	 * @throws IOException
	 */
	public User loadDefaultValuesForAgent(Model model, HttpServletRequest req,
	        User user) throws IOException {

		JSONObject newfi = new JSONObject();

		try {

			Locale locale = req.getLocale();
			String suffix = locale.toString();
			Gson gson = new Gson();

			Map<String, String> localeText = languageMap.get(suffix);
			if (localeText == null) {
				localeText = loadLanguageMap(suffix);
			}
			UserVO userVO = new UserVO();
			userVO.setForView(user);

			List<InternalUserRoleMaster> internalUserRoleMasters = masterDataService
			        .getInternalUserRoleMaster();
			newfi.put("internalUserRoleMasters",
			        gson.toJson(internalUserRoleMasters));

			gson = new Gson();

			newfi.put("user", gson.toJson(userVO));

			newfi.put("i18n", new JSONObject(localeText));
			model.addAttribute("userVO", userVO);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("newfi", newfi);
		return user;
	}

	private HashMap<String, String> loadLanguageMap(String suffix)
	        throws IOException {
		// TODO Auto-generated method stub

		try {
			Properties properties = new Properties();
			properties.load(PropertyFileReader.class.getClassLoader()
			        .getResourceAsStream("messages_" + suffix + ".properties"));
			languageMap.put(suffix, new HashMap<String, String>(
			        (Map) properties));
			return languageMap.get(suffix);
		} catch (NullPointerException ioe) {
			LOG.warn("locale file not found, defaulting the user", suffix);
			return languageMap.get(CommonConstants.DEFAULT_LOCALE);

		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		loadLanguageMap(CommonConstants.DEFAULT_LOCALE);

	}

}
