/*
 * Functions for fix your rate module
 */
function getRateProgramContainer() {
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
	
	var rateLabel = $('<div>').attr({
		"class" : "cp-rate-header-text"
	}).html("Interest Rate");
	var rateValue = $('<div>').attr({
		"class" : "cp-rate-btn",
		"id":"teaserRateId"
	}).html("3.375%");
	rateBtnContainer.append(rateLabel).append(rateValue);
	rpCol1.append(rateBtnContainer);
	
	if(loanType == "refinance"){
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
	}
	
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
	var paymentlabel = $('<div>').attr({
		"class" : "cp-est-header-text"
	}).html("Closing Cost");
	var paymentValue = $('<div>').attr({
		"class" : "cp-est-cost-btn",
		"id":"closingCostId"
	}).html("$ 8,185.75");
	paymentBtnContainer.append(paymentlabel).append(paymentValue);
	rpCol3.append(paymentBtnContainer);

	if(loanType == "refinance"){
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
	}
	
	var mobileScreenCont = getSliderContainerForMobileScreen();
	rpContainer.append(rpCol3).append(rpCol2).append(rpCol1).append(
			mobileScreenCont);
	
	parentWrapper.append(rpHeader).append(rpContainer);
	
	var lockRateBtnApply = $('<div>').attr({
		"class" : "cp-lock-btn  float-left"
	}).click(function(){
		
		$.ajax({
			
			url:"register.do",
			type:"GET",
			cache:false,
			success :function(){},
			error :function(){}
		});
		
	}).html("Apply Now");
	
	var lockRateBtnRegister = $('<div>').attr({
		"class" : "cp-lock-btn float-left"
	}).html("Register for alerts");
	
	var lockRateBtn = $('<div>').attr({
		"class" : " clearfix",
    	
	}).append(lockRateBtnApply).append(lockRateBtnRegister);
	
	/*var lockRateBtn = $('<div>').attr({
		"class" : "cp-lock-btn"
	}).html("lock your rate");*/
	
	parentWrapper.append(lockRateBtn);
	//parentWrapper.append(lockRateBtn);
	return parentWrapper;
}

function getRateSlider() {
	var rateSlider = $('<div>').attr({
		"class" : "rate-slider"
	});
	var rateSliderTextCon = $('<div>').attr({
		"class" : "slider-text-cont clearfix"
	});
	var rsLeftText = $('<div>').attr({
		"class" : "slider-text-left float-left"
	}).html("Reduce Cost");
	var rsRightText = $('<div>').attr({
		"class" : "slider-text-right float-right"
	}).html("Reduce Rate");
	rateSliderTextCon.append(rsLeftText).append(rsRightText);
	var rsIcon = $('<div>').attr({
		"id" : "rate-slider",
		"class" : "rate-slider-icon"
	});
	rateSlider.append(rateSliderTextCon).append(rsIcon);
	return rateSlider;
}

function getTenureSlider() {
	var tenureSilder = $('<div>').attr({
		"class" : "tenure-slider"
	});
	var tenureSliderTextCon = $('<div>').attr({
		"class" : "slider-text-cont clearfix"
	});
	var tsLeftText = $('<div>').attr({
		"class" : "slider-text-left float-left"
	}).html("Length of Loan");
	var tsRightText = $('<div>').attr({
		"class" : "slider-text-right float-right"
	});
	
	var gridArray = [0,3,5,7,10,15,20,30];
	
	var tsYearSpan = $('<span>').attr({
		"id" : "years-text"
	}).html('30');
	tsRightText.append(tsYearSpan).append(" Years");
	tenureSliderTextCon.append(tsLeftText).append(tsRightText);
	var tsIcon = $('<div>').attr({
		"id" : "tenure-slider",
		"class" : "tenure-slider-icon"
	}).slider({
		orientation : "horizontal",
		range : "min",
		max : gridArray.length - 1,
		value : gridArray[0],
		change:function(event,ui){
			$('#years-text').html(gridArray[ui.value]);
		}
	});
	tenureSilder.append(tenureSliderTextCon).append(tsIcon);
	
	var sliderGrids = getTenureSliderGrids(gridArray);
	
	tenureSilder.append(sliderGrids);
	return tenureSilder;
}

function getTenureSliderGrids(gridArray){
	var gridContainer = $('<div>').attr({
		"class" : "tenure-grid-container"
	});
	
	for(var i=0;i<gridArray.length; i++){
		var leftOffset = i/(gridArray.length-1) * 100;
		var gridItem = $('<div>').attr({
			"class" : "tenure-grid-item"
		}).css({
			"left" : leftOffset + "%"
		}).html(gridArray[i]);
		
		gridContainer.append(gridItem);
	}
	
	return gridContainer;
}


function getRateCostSliderGrids(gridArray){
	var gridContainer = $('<div>').attr({
		"class" : "tenure-grid-container"
	});
	
	for(var i=0;i<gridArray.length; i++){
		var leftOffset = i/(gridArray.length-1) * 100;
		var gridItem = $('<div>').attr({
			"class" : "tenure-grid-item"
		}).css({
			"left" : leftOffset + "%"
		}).html(gridArray[i].teaserRate+" %");
		
		gridContainer.append(gridItem);
	}
	
	return gridContainer;
}

/*function getSliderContainerForMobileScreen() {
	var mobileSliderCont = $('<div>').attr({
		"class" : "mobile-slider-container clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "rate-program-container-rs float-left"
	});

	var rateBtnContainer = $('<div>').attr({
		"class" : "cp-btn-cont"
	}); 
	
	var rateLabel = $('<div>').attr({
		"class" : "cp-rate-header-text"
	}).html("Interest Rate");
	var rateValue = $('<div>').attr({
		"class" : "cp-rate-btn",
		"id":"teaseRate"
	}).html("3.375%");
	rateBtnContainer.append(rateLabel).append(rateValue);
	col1.append(rateBtnContainer);
	
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
		
		col1.append(downPaymentBtnContainer);
	}
	
	
	var col2 = $('<div>').attr({
		"class" : "rate-program-container-ts float-left"
	});
	
	var paymentBtnContainer = $('<div>').attr({
		"class" : "cp-btn-cont"
	}); 
	var paymentlabel = $('<div>').attr({
		"class" : "cp-est-header-text"
	}).html("Closing Cost");
	var paymentValue = $('<div>').attr({
		"class" : "cp-est-cost-btn",
		"id":"closingCostId"
	}).html("$ 8,185.75");
	paymentBtnContainer.append(paymentlabel).append(paymentValue);
	col2.append(paymentBtnContainer);
	
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
	
		col2.append(purchaseAmount);
	}
	
	return mobileSliderCont.append(col1).append(col2);
}*/