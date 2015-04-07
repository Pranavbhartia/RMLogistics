package com.nexera.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.commons.MessageUtils;
import com.nexera.common.enums.DisplayMessageType;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.core.service.BraintreePaymentGatewayService;
import com.nexera.core.service.LoanService;
import com.nexera.web.constants.JspLookup;

/**
 * The controller class for all the payment related requests
 * 
 * @author karthik
 *
 */

@Controller
@RequestMapping(value = "/payment")
public class PaymentController {

	@Autowired
	private BraintreePaymentGatewayService gatewayService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private MessageUtils messageUtils;

	private static final Logger LOG = LoggerFactory
	        .getLogger(PaymentController.class);

	@RequestMapping(value = "initialisepayment.do", method = RequestMethod.POST)
	public String getThePaymentPage(Model model, HttpServletRequest request) {
		LOG.info("Payment contoller called for the payment page");

		int loanId;
		try {
			loanId = Integer.parseInt(request.getParameter("loan_id"));
		} catch (NumberFormatException e) {
			LOG.error("Exception while parsing loan id : " + e.getMessage(), e);
			model.addAttribute("error", 1);
			model.addAttribute(
			        "message",
			        messageUtils.getDisplayMessage(
			                DisplayMessageConstants.PAYMENT_POPUP_ERROR,
			                DisplayMessageType.ERROR_MESSAGE).toString());
			return JspLookup.PAYMENT_PAGE;
		}

		model.addAttribute("clienttoken", gatewayService.getClientToken());
		try {
			model.addAttribute(
			        "message",
			        messageUtils.getDisplayMessage(
			                DisplayMessageConstants.PAYMENT_POPUP_MESSAGE,
			                DisplayMessageType.SUCCESS_MESSAGE).toString()
			                + loanService.getApplicationFee(loanId));
			model.addAttribute("error", 0);
		} catch (NoRecordsFetchedException | InvalidInputException
		        | DatabaseException e) {
			LOG.error(
			        "Exception while calculating application fee for loan id : "
			                + e.getMessage(), e);
			model.addAttribute("error", 1);
			model.addAttribute(
			        "message",
			        messageUtils.getDisplayMessage(
			                DisplayMessageConstants.PAYMENT_POPUP_ERROR,
			                DisplayMessageType.ERROR_MESSAGE).toString());
			return JspLookup.PAYMENT_PAGE;
		} catch (Exception e) {
			LOG.error(
			        "Exception while calculating application fee for loan id : "
			                + e.getMessage(), e);
			model.addAttribute("error", 1);
			model.addAttribute(
			        "message",
			        messageUtils.getDisplayMessage(
			                DisplayMessageConstants.PAYMENT_POPUP_ERROR,
			                DisplayMessageType.ERROR_MESSAGE).toString());
			return JspLookup.PAYMENT_PAGE;
		}

		LOG.info("Returning the payment page");
		return JspLookup.PAYMENT_PAGE;
	}
}
