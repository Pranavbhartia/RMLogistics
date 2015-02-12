package com.nexera.newfi.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.nexeracommon.newfi.configuration.HibernateConfiguration;
import com.nexeracore.newfi.configuration.CoreConfiguration;

public class WebInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		
		AnnotationConfigWebApplicationContext context  = new AnnotationConfigWebApplicationContext();
		context.register(Config.class);
		context.register(WebAppConfig.class);
		context.register(RestConfig.class);
		context.register(CoreConfiguration.class);
		context.register(HibernateConfiguration.class);
		
		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.setInitParameter("defaultHtmlEscape", "true");
		
		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		
		
	}

}
