function getLockRateProgramContainer() {
	var parentWrapper = $('<div>').attr({
		"class" : "rate-program-wrapper"
	});
	var rpHeader = $('<div>').attr({
		"class" : "rate-program-header uppercase"
	}).html("RATES & PROGRAM");
	var rpContainer = $('<div>').attr({
		"class" : "rate-program-container clearfix"
	});
	var rpCol1 = $('<div>').attr({
		"class" : "rate-program-container-col1 float-left"
	});
	
	var rateBtnContainer = $('<div>').attr({
		"class" : "cp-btn-cont"
	}); 
	
	var paymentlabel = $('<div>').attr({
		"class" : "cp-est-header-text"
	}).html("Closing Cost");
	var paymentValue = $('<div>').attr({
		"class" : "cp-est-cost-btn"
	}).html("$ 8,185.75");
	rateBtnContainer.append(paymentlabel).append(paymentValue);
	rpCol1.append(rateBtnContainer);
	
	/*if(loanType == "refinance"){
		rateBtnContainer.addClass('margin-top30');
	}
	
	if(loanType == "purchase"){
		var downPaymentBtnContainer = $('<div>').attr({
			"class" : "cp-btn-cont"
		});
		var downPaymentlabel = $('<div>').attr({
			"class" : "cp-est-header-text"
		}).html("Down Payment");
		var downPaymentValue = $('<div>').attr({
			"class" : "cp-cost-cont"
		}).html("$ 100,000.00");
		downPaymentBtnContainer.append(downPaymentlabel).append(downPaymentValue);
		
		rpCol1.append(downPaymentBtnContainer);
	}*/
	
	var rpCol2 = $('<div>').attr({
		"class" : "rate-program-container-col2 float-left"
	});
	var rateSlider = getRateSlider();
	var tenureSlider = getTenureSlider();
	rpCol2.append(rateSlider).append(tenureSlider);

	var rpCol3 = $('<div>').attr({
		"class" : "rate-program-container-col3 float-left"
	});
	var paymentBtnContainer = $('<div>').attr({
		"class" : "cp-btn-cont"
	}); 
	var rateLabel = $('<div>').attr({
		"class" : "cp-rate-header-text"
	}).html("Interest Rate");
	var rateValue = $('<div>').attr({
		"class" : "cp-rate-btn"
	}).html("3.375%");
	paymentBtnContainer.append(rateLabel).append(rateValue);
	rpCol3.append(paymentBtnContainer);

	/*if(loanType == "refinance"){
		paymentBtnContainer.addClass('margin-top30');
	}
	
	if(loanType == "purchase"){
		var purchaseAmount = $('<div>').attr({
			"class" : "cp-btn-cont"
		});
		var purchaselabel = $('<div>').attr({
			"class" : "cp-est-header-text"
		}).html("Purchase Amount");
		var purchaseValue = $('<div>').attr({
			"class" : "cp-cost-cont"
		}).html("$ 473,000.00");
		purchaseAmount.append(purchaselabel).append(purchaseValue);
		
		rpCol3.append(purchaseAmount);
	}*/
	
	var mobileScreenCont = getSliderContainerForMobileScreen();
	rpContainer.append(rpCol1).append(rpCol2).append(rpCol3).append(
			mobileScreenCont);
	
	parentWrapper.append(rpHeader).append(rpContainer);
	var lockYourRate = $('<div>').attr({
		"class" : "cp-lock-btn"
	}).html("Lock Your Rate").bind('click',function(event){
		changeSecondaryLeftPanel(2);
	});
	
/*	var NotifyMe = $('<div>').attr({
		"class" : "cp-lock-btn"
	}).html("Notify Me").bind('click',function(event){
		
		$('#ce-main-container').html('');
		var notifyForRatesAlerts = paintNotifyForRatesAlerts();
		$('#ce-main-container').html(notifyForRatesAlerts);
		
	});*/
	parentWrapper.append(lockYourRate);
	return parentWrapper;
}