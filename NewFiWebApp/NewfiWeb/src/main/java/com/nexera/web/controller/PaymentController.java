package com.nexera.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.nexera.core.service.BraintreePaymentGatewayService;
import com.nexera.web.constants.JspLookup;

/**
 * The controller class for all the payment related requests
 * @author karthik
 *
 */
@Controller
@RequestMapping(value = "/payment/")
public class PaymentController {

	@Autowired
	private BraintreePaymentGatewayService gatewayService;

	private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value="paymentpage.do",method=RequestMethod.GET)
	public String getThePaymentPage(Model model,HttpServletRequest request){		
		LOG.info("Payment contoller called for the payment page");
		
		//ModelAndView mav = new ModelAndView();
		//mav.getModel().put("clienttoken", gatewayService.getClientToken());
		model.addAttribute("clienttoken", gatewayService.getClientToken());	
		//mav.setViewName(JspLookup.PAYMENT_PAGE);
		LOG.info("Returning the payment page");
		return JspLookup.PAYMENT_PAGE;
	}

	
}
