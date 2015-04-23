package com.nexera.web.rest.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.common.commons.Utils;

public class RestInterceptor implements HandlerInterceptor {

	@Autowired
	protected Utils utils;

	private static final Logger LOG = LoggerFactory
	        .getLogger(RestInterceptor.class);

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

		List<String> unprotectedUrls = utils.getUnprotectedUrls();
		LOG.debug("Serving getPathInfo" + request.getPathInfo());
		LOG.debug("Serving URL URI " + request.getRequestURI());
		if (unprotectedUrls.contains(request.getPathInfo())) {
			LOG.debug("..is an unprotected URL");
			return true;
		}

		/*
		 * if (utils.getLoggedInUser() == null) {
		 * LOG.error("User is not logged in");
		 * LOG.debug(request.getServletPath() + "is a protected.... URL");
		 * response.sendRedirect("/NewfiWeb"); return false; }
		 */
		return true;
	}

}
