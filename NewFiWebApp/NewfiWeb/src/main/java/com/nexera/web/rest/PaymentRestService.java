package com.nexera.web.rest;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.commons.MessageUtils;
import com.nexera.common.entity.User;
import com.nexera.common.enums.DisplayMessageType;
import com.nexera.common.exception.CreditCardException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.PaymentException;
import com.nexera.common.exception.PaymentUnsuccessfulException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.core.service.BraintreePaymentGatewayService;

/**
 * The REST controller for all the payment operations
 * @author karthik
 *
 */
@RestController
@RequestMapping(value = "/payment/")
public class PaymentRestService {

	@Autowired
	private BraintreePaymentGatewayService gatewayService;
	
	@Autowired
	private MessageUtils messageUtils;

	private static final Logger LOG = LoggerFactory.getLogger(PaymentRestService.class);

	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		else {
			return null;
		}
	}

	@RequestMapping(value = "makepayment.do", method = RequestMethod.POST)
	public @ResponseBody
	String makePayment(Model model, HttpServletRequest request) {
		LOG.info("Payment Controller called to make a payment");

		String nonce = request.getParameter("payment_nonce");
		int loanId = Integer.parseInt(request.getParameter("loan_id"));
		
		LOG.debug("Making payment with nonce : " + nonce + " for loanid : " + loanId);
		Gson jsonBuilder = new Gson();
		JsonObject result = new JsonObject();

		try {
			User user = getUserObject();

			if (user == null) {
				throw new InvalidInputException("User not found in the session!");
			}
			gatewayService.makePayment(nonce, 10.00f, loanId, user);
		}
		catch (InvalidInputException e) {
			LOG.error("Invalid Input Exception caught : " + e.getMessage(), e);
			result.addProperty(CommonConstants.SUCCESS_KEY, CommonConstants.FAILURE);
			result.addProperty(CommonConstants.MESSAGE_KEY, messageUtils.getDisplayMessage(DisplayMessageConstants.GENERAL_ERROR, DisplayMessageType.ERROR_MESSAGE).getMessage());
			return jsonBuilder.toJson(result);
		}
		catch (PaymentException e) {
			LOG.error("PaymentException caught : " + e.getMessage(), e);
			result.addProperty(CommonConstants.SUCCESS_KEY, CommonConstants.FAILURE);
			result.addProperty(CommonConstants.MESSAGE_KEY, messageUtils.getDisplayMessage(DisplayMessageConstants.GENERAL_ERROR, DisplayMessageType.ERROR_MESSAGE).getMessage());
			return jsonBuilder.toJson(result);
		}
		catch (PaymentUnsuccessfulException e) {
			LOG.error("PaymentUnsuccessfulException caught : " + e.getMessage(), e);
			result.addProperty(CommonConstants.SUCCESS_KEY, CommonConstants.FAILURE);
			result.addProperty(CommonConstants.MESSAGE_KEY, messageUtils.getDisplayMessage(e.getErrorCode(), DisplayMessageType.ERROR_MESSAGE).getMessage());
			return jsonBuilder.toJson(result);
		}
		catch (CreditCardException e) {
			LOG.error("CreditCardException caught : " + e.getMessage(), e);
			result.addProperty(CommonConstants.SUCCESS_KEY, CommonConstants.FAILURE);
			result.addProperty(CommonConstants.MESSAGE_KEY, messageUtils.getDisplayMessage(e.getErrorCode(), DisplayMessageType.ERROR_MESSAGE).getMessage());
			return jsonBuilder.toJson(result);
		}
		catch (NoRecordsFetchedException e) {
			LOG.error("NoRecordsFetchedException caught : " + e.getMessage(), e);
			result.addProperty(CommonConstants.SUCCESS_KEY, CommonConstants.FAILURE);
			result.addProperty(CommonConstants.MESSAGE_KEY, messageUtils.getDisplayMessage(DisplayMessageConstants.GENERAL_ERROR, DisplayMessageType.ERROR_MESSAGE).getMessage());
			return jsonBuilder.toJson(result);
		}
		catch (UndeliveredEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.addProperty(CommonConstants.SUCCESS_KEY, CommonConstants.SUCCESS);
		result.addProperty(CommonConstants.MESSAGE_KEY, messageUtils.getDisplayMessage(DisplayMessageConstants.PAYMENT_SUCCESSFUL, DisplayMessageType.SUCCESS_MESSAGE).getMessage());
		return jsonBuilder.toJson(result);
	}
}

