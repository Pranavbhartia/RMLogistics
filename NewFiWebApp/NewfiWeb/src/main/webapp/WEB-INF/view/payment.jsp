<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="payment-details-wrapper" style="display: block; position: relative; margin: 100px auto 0px; width: 40%;">
	<div id="payment-details-form" class="payment-details-form" style="background-color: rgb(246, 248, 247); padding: 20px 0px;">
		<div class="ms-add-member-popup-header" style="font-size: 23px;">Your card details</div>
		<form id="checkout" method="POST">
			<div id="dropin" class="payment-dropin" style="width: 405px; margin-left: 67px; margin-top: 30px;"></div>
			<div class="clearfix">
				<input type="submit" class="login-submit-button" style="margin-top: 14px; width: 29%; margin-left: 29px; margin-bottom: 22px;" value="Make Payment" /> 
				<input type="button" id="cancel-payment" class="login-submit-button" style="margin-top: 14px; width: 29%; float: right; margin-right: 29px; margin-bottom: 22px;" value="Cancel" />
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	
	$(document).ready(function() {
		console.log("Loading braintree");
		console.log("Setting up the payment form");
		braintree.setup('${clienttoken}', 'dropin', {
			container : 'dropin',
			paymentMethodNonceReceived : makePayment
		});
		console.log("Braintree loaded");
	});
	
	function makePayment(event,nonce){
		console.log("Making payment");
		console.log(event);
		console.log(nonce);
		console.log(newfiObject.user.defaultLoanId);
		showOverlay();
		url="./payment/makepayment.do";
		data = "payment_nonce=" + String(nonce);
		data = data +"&loan_id=" + String(newfiObject.user.defaultLoanId);
		$.ajax({
			url : url,
			type : "POST",
			data : data,
			success : showMessage,
			error :  function(e) {
	            console.error("error : " + e);
	        }
		});
	}

	$("#cancel-payment").click(function() {
		$('body').removeClass('body-no-scroll');
		$('#popup-overlay').hide();
	});

	function showMessage(data) {
		var jsonData = JSON.parse(data);
		console.log("Data recieved : " + jsonData);
		if (jsonData["success"] == 1) {
			console.log(jsonData["message"]);
			hideOverlay();
        	$("#popup-overlay").hide();
			console.log("Showing toast now");
			showToastMessage("Payment successful!");
			console.log("Finished showing the toast");
		} else {
			console.error("Error occured while upgrading. ");
			hideOverlay();
			console.log("Showing toast now");
			showToastMessage(jsonData["message"]);
			console.log("Finished showing the toast");
		}
	}

	function displayToast(data) {
		hidePayment();
		hideOverlay();
		console.log("Message recieved. Hiding Payment popup");
		console.log("Removing no-scroll class from body");
		$('body').removeClass('body-no-scroll');
		$('#st-settings-payment-off').show();
		$('#st-settings-payment-on').hide();
		$('#overlay-toast').html(data);
		console.log("Added toast message. Showing it now");
		showToast();
		console.log("Finished showing the toast");
	}

	function displayError(err) {
		$('body').removeClass('body-no-scroll');
		$('#st-settings-payment-off').show();
		$('#st-settings-payment-on').hide();
		hidePayment();
		hideOverlay();
		console.log("Error occured. Hiding Overlay");
		console.log("Removing no-scroll class from body");
		$('body').removeClass('body-no-scroll');
		$('#overlay-toast')
				.html(
						"Oops! We seem to be having a technical fault. Please try in some time.");
		console.log("Added toast message. Showing it now");
		showToast();
		console.log("Finished showing the toast");
	}
</script>
