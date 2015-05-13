package com.nexera.web.rest.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.nexera.common.commons.Utils;

public class RestInterceptor implements HandlerInterceptor {

	@Autowired
	protected Utils utils;

	private static final Logger LOG = LoggerFactory
	        .getLogger(RestInterceptor.class);

	@Value("${redirect.path}")
	private String redirectPath;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
	        HttpServletResponse arg1, Object arg2, Exception arg3)
	        throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
	        Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
	        HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServerName() + ":" + request.getServerPort()
		        + "/" + request.getContextPath();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", "Session Expired");
		map.put("message",
		        "For your protection, we have logged you out due to "
		                + "inactivity.Simply click this <a href='http://"
		                + path + "'>LOGIN</a> link to start a new session.");
		System.out.println(request.getServerName() + ":"
		        + request.getServerPort() + "/" + request.getContextPath());

		System.out.println(map.toString());
		List<String> unprotectedUrls = utils.getUnprotectedUrls();
		LOG.debug("Serving getPathInfo" + request.getPathInfo());
		LOG.debug("Serving URL URI " + request.getRequestURI());
		if (unprotectedUrls.contains(request.getPathInfo())) {
			LOG.debug("..is an unprotected URL");
			return true;
		}

		if (utils.getLoggedInUser() == null) {
			LOG.error("User is not logged in");
			LOG.debug(request.getServletPath() + "is a protected.... URL");
			// response.sendRedirect(redirectPath);
			//
			response.setContentType("application/json");
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(map));
			return false;
		}

		return true;
	}
}
