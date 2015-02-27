function changeLeftPanel(primary) {
	var leftPanel = parseInt(primary);
	$('.lp-item').removeClass('lp-item-active');
	$('.lp-right-arrow').remove();
	if (leftPanel == 1) {
		showMessageDashboard();
	} else if (leftPanel == 2) {
		showCustomerLoanPage();
	}
}

function adjustCenterPanelWidth() {
	if (window.innerWidth <= 1200 && window.innerWidth >= 768) {
		var leftPanelWidth = $('.left-panel').width();
		var leftPanelTab2Width = $('.lp-t2-wrapper').width();
		var centerPanelWidth = $(window).width()
				- (leftPanelWidth + leftPanelTab2Width) - 35;
		$('.center-panel').width(centerPanelWidth);
	} else if (window.innerWidth < 768) {
		var leftPanelTab2Width = $('.lp-t2-wrapper').width();
		var centerPanelWidth = $(window).width() - (leftPanelTab2Width) - 20;
		$('.center-panel').width(centerPanelWidth);
	}
}

function getCustomerSecondaryLeftNav() {
	var leftTab2Wrapper = $('<div>').attr({
		"class" : "lp-t2-wrapper"
	});

	var step1 = getCustomerSecondaryLeftNavStep(1, "Getting to know newfi");
	var step2 = getCustomerSecondaryLeftNavStep(2, "complete your application");
	var step3 = getCustomerSecondaryLeftNavStep(3, "lock<br />your rate");
	var step4 = getCustomerSecondaryLeftNavStep(4, "upload<br />needed items");
	var step5 = getCustomerSecondaryLeftNavStep(5, "loan<br />progress");

	return leftTab2Wrapper.append(step1).append(step2).append(step3).append(
			step4).append(step5);
}

function getCustomerSecondaryLeftNavStep(step, text) {
	var container = $('<div>').attr({
		"id" : "lp-step" + step,
		"class" : "lp-t2-item",
		"onclick" : "changeSecondaryLeftPanel(" + step + ");"
	});

	switch (step) {
	case 1:
		$.data(container, "enum", {
			snName : SNEnum.GETTINGTOKNOWNEWFI,

		});
		break;
	case 2:
		$.data(container, "enum", {
			snName : SNEnum.COMPLETEAPPLICATION,

		});
		break;
	case 3:
		$.data(container, "enum", {
			snName : SNEnum.LOCKRATE,

		});
		break;
	case 4:
		$.data(container, "enum", {
			snName : SNEnum.UPLOAD,

		});
		break;
	case 5:
		$.data(container, "enum", {
			snName : SNEnum.LOANPROGRESS,

		});
		break;

	default:
		break;
	}

	var img = $('<div>').attr({
		"class" : "lp-t2-img lp-t2-img" + step
	});

	var txt = $('<div>').attr({
		"class" : "lp-t2-txt"
	}).html(text);

	return container.append(img).append(txt);
}

function showMessageDashboard() {
	$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	$('.lp-item').removeClass('lp-item-active');
	$('#lp-talk-wrapper').addClass('lp-item-active');
	var convMainContainer = $('<div>').attr({
		"id" : "conv-main-container",
		"class" : "right-panel-messageDashboard float-left"
	});
	$('#right-panel').append(convMainContainer);
	paintGettingToKnowMessageDashboard();
	adjustCenterPanelWidth();
}

function showCustomerLoanPage() {
	$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	$('.lp-item').removeClass('lp-item-active');
	$('#lp-loan-wrapper').addClass('lp-item-active');
	var loanDetailsMainContainer = $('<div>').attr({
		"class" : "right-panel float-left"
	});

	var secondaryLeftNav = getCustomerSecondaryLeftNav();
	var centerPanel = $('<div>').attr({
		"id" : "center-panel-cont",
		"class" : "center-panel float-left"
	});

	loanDetailsMainContainer.append(secondaryLeftNav).append(centerPanel);

	$('#right-panel').append(loanDetailsMainContainer);
	changeSecondaryLeftPanel(2);
	adjustCenterPanelWidth();
	
	//TODO: Invoke dynamic binder to listen to secondary navigation clicks
	globalSNBinder();
}

function changeSecondaryLeftPanel(secondary) {
	secondary = parseInt(secondary);
	$('.lp-t2-item').removeClass('t2-active');
	$('.lp-t2-item .arrow-right').remove();
	$('#lp-step' + secondary).addClass('t2-active');
	var rightArrow = $('<div>').attr({
		"class" : "arrow-right"
	});
	$('#lp-step' + secondary).append(rightArrow);
	$('#center-panel-cont').html('');
	if (secondary == 1) {
		// getting to know newfi page
	} else if (secondary == 2) {
		// customer profile page
		paintProfileCompleteStep1();
	} else if (secondary == 3) {
		// fix your rate page
		paintFixYourRatePage();
	} else if (secondary == 4) {
		// upload need items
		paintUploadNeededItemsPage();
	} else if (secondary == 5) {
		// loan progress
	}
}

/*
 * Functions for complete profile module
 */

function paintProfileCompleteStep1() {
	var topHeader = getCompletYourApplicationHeader();
	var formContainer = getAboutMeDetailsWrapper();
	$('#center-panel-cont').append(topHeader).append(formContainer);
}

function paintProfileCompleteStep2() {
	$('#center-panel-cont').html("");
	var topHeader = getCompletYourApplicationHeader();
	var formContainer = getLoanDetailsWrapper();
	$('#center-panel-cont').append(topHeader).append(formContainer);
}

function getCompletYourApplicationHeader() {
	var parent = $('<div>').attr({
		"class" : "complete-application-wrapper"
	});
	var header = $('<div>').attr({
		"class" : "complete-application-header"
	}).html("Complete Your Application");
	return parent.append(header);
}

function getAboutMeDetailsWrapper() {
	var parent = $('<div>').attr({
		"class" : "about-me-details-wrapper"
	});

	var header = getAboutMeDetailsHeader();
	var container = getAboutMeDetailsContainer();

	var nextButton = $('<div>').attr({
		"class" : "submit-btn",
		"onclick" : "paintProfileCompleteStep2()"
	}).html("Next");

	return parent.append(header).append(container).append(nextButton);
}

function getAboutMeDetailsHeader() {
	var header = $('<div>').attr({
		"class" : "application-form-header clearfix"
	});
	var headerCol1 = $('<div>').attr({
		"class" : "application-form-header-col1 float-left"
	}).html("About Me");
	var headerCol2 = $('<div>').attr({
		"class" : "application-form-header-col2 float-right"
	});
	var col2Container = $('<div>').attr({
		"class" : "clearfix"
	});
	var col2LeftCol = $('<div>').attr({
		"class" : "form-progress float-left"
	});
	var progressSpan = $('<span>').attr({
		"class" : "progress-span-text"
	}).html("4/10");
	col2LeftCol.html(progressSpan).append(" Completed");
	var col2RightCol = $('<div>').attr({
		"class" : "edit-form float-right"
	}).html("Edit");
	col2Container.append(col2LeftCol).append(col2RightCol);
	headerCol2.append(col2Container);
	return header.append(headerCol1).append(headerCol2);
}

function getAboutMeDetailsContainer() {
	var container = $('<div>').attr({
		"class" : "application-form-container clearfix"
	});
	var conRow1 = getEditableFormRow("Phone 1", false);
	var conRow2 = getEditableFormRow("Phone 2", false);
	var conRow3 = getEditableFormRow("Email", true);
	conRow3.find('.form-detail-edit').find('input').addClass(
			'form-detail-input-lg');
	var conRow4 = getEditableFormRow("DOB", true);
	conRow4.find('.form-detail-edit').find('input').addClass('date-picker')
			.attr("placeholder", "MM/DD/YYYY");
	container.append(conRow1).append(conRow2).append(conRow3).append(conRow4);

	var conRow5 = getNonEditableFormRow("First Name", "Jane");
	var conRow6 = getNonEditableFormRow("Last Name", "Doe");
	var conRow7 = getNonEditableFormRow("Street Address", "795 E DRAGRAM");
	var conRow8 = getNonEditableFormRow("City", "TUCSON");
	var conRow9 = getNonEditableFormRow("State", "AZ");
	var conRow10 = getNonEditableFormRow("Zip", "85705");

	return container.append(conRow5).append(conRow6).append(conRow7).append(
			conRow8).append(conRow9).append(conRow10);
}

function getLoanDetailsWrapper() {
	var parent = $('<div>').attr({
		"class" : "about-me-details-wrapper"
	});

	var header = getLoanDetailsHeader();
	var container = getLoanDetailsContainer();
	var saveApplicationButton = $('<div>').attr({
		"class" : "submit-btn save-app-btn",
		"onclick" : "paintFixYourRatePage()"
	}).html("Save Application");
	return parent.append(header).append(container)
			.append(saveApplicationButton);
}

function getLoanDetailsHeader() {
	var header = $('<div>').attr({
		"class" : "application-form-header clearfix"
	});
	var headerCol1 = $('<div>').attr({
		"class" : "application-form-header-col1 float-left"
	}).html("My Loan Goals");
	var headerCol2 = $('<div>').attr({
		"class" : "application-form-header-col2 float-right"
	});
	var col2Container = $('<div>').attr({
		"class" : "clearfix"
	});
	var col2LeftCol = $('<div>').attr({
		"class" : "form-progress float-left"
	});
	var progressSpan = $('<span>').attr({
		"class" : "progress-span-text"
	}).html("8/17");
	col2LeftCol.html(progressSpan).append(" Completed");
	var col2RightCol = $('<div>').attr({
		"class" : "edit-form float-right"
	}).html("Edit");
	col2Container.append(col2LeftCol).append(col2RightCol);
	headerCol2.append(col2Container);
	return header.append(headerCol1).append(headerCol2);
}

function getLoanDetailsContainer() {
	var container = $('<div>').attr({
		"class" : "application-form-container clearfix"
	});
	var row1 = getEditableFormRow("Prop Address", true);
	row1.find('.form-detail-edit').find('input').addClass(
			'form-detail-input-lg');
	var row2 = getEditableFormRow("Prop City", true);
	row2.find('.form-detail-edit').find('input').addClass(
			'form-detail-input-dropdown');
	var row3 = getEditableFormRow("Prop State", true);
	row3.find('.form-detail-edit').find('input').addClass(
			'form-detail-input-dropdown form-detail-input-sm');
	var row4 = getEditableFormRow("Prop Zip", true);
	row4.find('.form-detail-edit').find('input').addClass(
			'form-detail-input-sm');
	var row5 = getEditableFormRow("Bor Middle Name", true);
	var row6 = getEditableFormRow("Bor Middle Name", true);
	var row7 = getEditableFormRow("SSN", false);

	container.append(row1).append(row2).append(row3).append(row4).append(row5)
			.append(row6).append(row7);

	var estCreditNo = $('<div>').attr({
		"class" : "est-credit-row"
	}).html("Estimate the Credit No.");
	container.append(estCreditNo);

	var row8 = getNonEditableFormRow("Prop Country", "Greenwood");
	var row9 = getNonEditableFormRow("Prop Use", "Primary Residence");
	var row10 = getNonEditableFormRow("Down Payment", "$ 3.50");
	var row11 = getNonEditableFormRow("Max Monthly Payment", "$ 400.00");
	var row12 = getNonEditableFormRow("Lien Amount", "$ 0.00");
	var row13 = getNonEditableFormRow("Cashout", "$ 0.00");
	var row14 = getNonEditableFormRow("Value/Price", "$ 150,000.00");
	var row15 = getNonEditableFormRow("Loan Amount", "$ 144,750.00");
	var row16 = getNonEditableFormRow("Self-Empl", "NO");

	return container.append(row8).append(row9).append(row10).append(row11)
			.append(row12).append(row13).append(row14).append(row15).append(
					row16);
}

function getEditableFormRow(desc, isCompulsory) {
	var row = $('<div>').attr({
		"class" : "form-detail-edit-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "form-detail-row-desc float-left"
	}).html(desc);
	if (isCompulsory) {
		rowCol1.addClass("form-field-compulsory");
		rowCol1.append('<span class="compulsory-span"> *</span>');
	}
	var rowCol2 = $('<div>').attr({
		"class" : "form-detail-edit float-left"
	});
	var editRow = $('<input>').attr({
		"class" : "form-detail-input"
	});
	rowCol2.append(editRow);
	return row.append(rowCol1).append(rowCol2);
}

function getNonEditableFormRow(desc, value) {
	var row = $('<div>').attr({
		"class" : "form-detail-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "form-detail-row-name float-left"
	}).html(desc);
	var rowCol2 = $('<div>').attr({
		"class" : "form-detail-row-value float-left"
	});
	var editRow = $('<input>').attr({
		"class" : "form-detail-input-disabled"
	}).prop("disabled", "true").val(value);

	rowCol2.append(editRow);
	return row.append(rowCol1).append(rowCol2);
}

/*
 * Functions for fix your rate module
 */
function paintFixYourRatePage() {
	var rateProgramWrapper = getRateProgramContainer();
	var loanSummaryWrapper = getLoanSummaryWrapper();
	var closingCostWrapper = getClosingCostSummaryContainer();
	$('#center-panel-cont').append(rateProgramWrapper).append(
			loanSummaryWrapper).append(closingCostWrapper);
}

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
	var col1Txt = $('<div>').attr({
		"class" : "cp-rate-header-text"
	}).html("Interest Rate");
	var col1btn = $('<div>').attr({
		"class" : "cp-rate-btn"
	}).html("3.375%");
	rpCol1.append(col1Txt).append(col1btn);

	var rpCol2 = $('<div>').attr({
		"class" : "rate-program-container-col2 float-left"
	});
	var rateSlider = getRateSlider();
	var tenureSlider = getTenureSlider();
	rpCol2.append(rateSlider).append(tenureSlider);

	var rpCol3 = $('<div>').attr({
		"class" : "rate-program-container-col3 float-left"
	});
	var col3Txt = $('<div>').attr({
		"class" : "cp-est-header-text"
	}).html("Estimated Closing Cost");
	var col3btn = $('<div>').attr({
		"class" : "cp-est-cost-btn"
	}).html("$ 8,185.75");
	rpCol3.append(col3Txt).append(col3btn);
	var mobileScreenCont = getSliderContainerForMobileScreen();
	rpContainer.append(rpCol1).append(rpCol2).append(rpCol3).append(
			mobileScreenCont);
	parentWrapper.append(rpHeader).append(rpContainer);
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
	}).html("Reduce Rate");
	var rsRightText = $('<div>').attr({
		"class" : "slider-text-right float-right"
	}).html("Reduce Cost");
	rateSliderTextCon.append(rsLeftText).append(rsRightText);
	var rsIcon = $('<div>').attr({
		"id" : "rate-slider",
		"class" : "rate-slider-icon"
	}).slider({
		orientation : "horizontal",
		range : "min",
		max : 100,
		value : 40
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
		max : 30,
		value : 10
	});
	tenureSilder.append(tenureSliderTextCon).append(tsIcon);
	return tenureSilder;
}

function getSliderContainerForMobileScreen() {
	var mobileSliderCont = $('<div>').attr({
		"class" : "mobile-slider-container clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "rate-program-container-rs float-left"
	});
	var col1Txt = $('<div>').attr({
		"class" : "cp-rate-header-text"
	}).html("Interest Rate");
	var col1btn = $('<div>').attr({
		"class" : "cp-rate-btn"
	}).html("3.375%");
	col1.append(col1Txt).append(col1btn);
	var col2 = $('<div>').attr({
		"class" : "rate-program-container-ts float-left"
	});
	var col2Txt = $('<div>').attr({
		"class" : "cp-est-header-text"
	}).html("Estimated Closing Cost");
	var col2btn = $('<div>').attr({
		"class" : "cp-est-cost-btn"
	}).html("$ 8,185.75");
	col2.append(col2Txt).append(col2btn);
	return mobileSliderCont.append(col1).append(col2);
}

function getLoanSummaryWrapper() {
	var parentWrapper = $('<div>').attr({
		"class" : "loan-summary-wrapper"
	});
	var header = getLoanSummaryHeader();
	var container = getLoanSummaryContainer();
	var bottomText = getHeaderText("Quoted Rates are not guaranteed. You may use this tool to check current rates or request a  rate lock. APR is an estimate based on an average $200,000 loan amount with 2% in total APR related fees. Actual ARP will be available on your Good Faith Estimate after Loan Amount and Income are Verified.");
	parentWrapper.append(header).append(container).append(bottomText);
	return parentWrapper;
}

function getLoanSummaryHeader() {
	var headerCont = $('<div>').attr({
		"class" : "loan-summary-header clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "loan-summary-header-col1 float-left uppercase"
	}).html('MY LOAN SUMMARY');
	var col2 = $('<div>').attr({
		"class" : "loan-summary-header-col2 float-left"
	}).html("Rates as of 1/16/2015 8:13:52 AM");
	headerCont.append(col1).append(col2);
	return headerCont;
}

function getLoanSummaryContainer() {
	var container = $('<div>').attr({
		"class" : "loan-summary-container clearfix"
	});
	var leftCol = $('<div>').attr({
		"class" : "loan-summary-lp float-left"
	});
	// add rows in left column
	var lcRow1 = getLaonSummaryApplyBtnRow();
	var lcRow2 = getLoanSummaryRow("Loan Type", "Refinance - No Cash Out");
	var lcRow3 = getLoanSummaryRow("Loan Program", "30 Years Fixed");
	var lcRow4 = getLoanSummaryRow("Interest Rate", "3.375%");
	var lcRow5 = getLoanSummaryRow("Loan Amount", "$ 373,000.000");
	var lcRow6 = getLoanSummaryRow("ARP", "3.547%");
	var lcRow7 = getLoanSummaryRow("Estimated<br/>Closing Cost", "$8,185.75");
	leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(
			lcRow5).append(lcRow6).append(lcRow7);

	var rightCol = $('<div>').attr({
		"class" : "loan-summary-rp float-right"
	});
	// add rows in right column
	var rcRow1 = getLoanSummaryRow("Monthly Payment", "");
	var rcRow2 = getLoanSummaryRow("Principal Interest", "$ 1,649.20");
	var rcRow3 = getLoanSummaryRowCalculateBtn("Tax", "Calculate");
	rcRow3.addClass("no-border-bottom");
	var rcRow4 = getLoanSummaryRowCalculateBtn("Insurance", "Calculate");
	var rcRow5 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal 																			& interest payment");
	var rcRow6 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment",
			"$ 1,649.02");
	rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4)
			.append(rcRow5).append(rcRow6);

	container.append(leftCol).append(rightCol);
	return container;
}

function getLaonSummaryApplyBtnRow() {
	var container = $('<div>').attr({
		"class" : "loan-summary-row clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "loan-summary-col-desc float-left"
	}).html("Loan details");
	var col2 = $('<div>').attr({
		"class" : "loan-summary-col-detail apply-btn float-left"
	}).html("Apply");
	container.append(col1).append(col2);
	return container;
}

function getLoanSummaryRow(desc, detail) {
	var container = $('<div>').attr({
		"class" : "loan-summary-row clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "loan-summary-col-desc float-left"
	}).html(desc);
	var col2 = $('<div>').attr({
		"class" : "loan-summary-col-detail float-left"
	}).html(detail);
	container.append(col1).append(col2);
	return container;
}

function getLoanSummaryRowCalculateBtn(desc, detail) {
	var container = $('<div>').attr({
		"class" : "loan-summary-row clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "loan-summary-col-desc float-left"
	}).html(desc);
	var col2 = $('<div>').attr({
		"class" : "loan-summary-col-detail calculate-btn float-left"
	}).html(detail);
	container.append(col1).append(col2);
	return container;
}

function getLoanSummaryLastRow(desc, detail) {
	var container = $('<div>').attr({
		"class" : "loan-summary-last-row clearfix"
	});
	var col1 = $('<div>').attr({
		"class" : "loan-summary-col-desc float-left"
	}).html(desc);
	var col2 = $('<div>').attr({
		"class" : "loan-summary-col-detail float-left"
	}).html(detail);
	container.append(col1).append(col2);
	return container;
}

function getLoanSummaryTextRow(text) {
	var container = $('<div>').attr({
		"class" : "loan-summary-row-text"
	}).html(text);
	return container;
}

function getHeaderText(text) {
	var headerText = $('<div>').attr({
		"class" : "cp-header-text"
	}).html(text);
	return headerText;
}

function getClosingCostSummaryContainer() {
	var parentWrapper = $('<div>').attr({
		"class" : "closing-cost-wrapper"
	});
	var header = getClosingCostHeader("CLOSING COST SUMMARY");
	var descText = getHeaderText("Based on the loan you selected your application, credit report and the estimated closing date of ");
	var closingDate = $('<span>').attr({
		"class" : "semibold"
	}).html("02/09/2015");
	descText.append(closingDate).append(
			" ,your estimated lender and third party costs are:");
	var topContainer = getClosingCostTopConatiner();
	var bottomContainer = getClosingCostBottomConatiner();
	return parentWrapper.append(header).append(descText).append(topContainer)
			.append(bottomContainer);
}

function getClosingCostHeader(text) {
	var header = $('<div>').attr({
		"class" : "closing-cost-header uppercase"
	}).html(text);
	return header;
}

function getClosingCostTopConatiner() {
	var wrapper = $('<div>').attr({
		"class" : "closing-cost-cont-wrapper-top"
	});
	var heading = getClosingCostHeadingCont("Total Estimated Losing Cost");
	var container1 = $('<div>').attr({
		"class" : "closing-cost-container"
	});
	var headerCon1 = getClosingCostConatinerHeader("Estimated Lender Cost");
	var row1Con1 = getClosingCostContainerRow(1, "Administration Fee",
			"$ 1,495.00");
	var row2Con1 = getClosingCostContainerRow(2, "Loan Points", "$ 5,128.75");
	var row3Con1 = getClosingCostContainerLastRow(3,
			"Total Estimated Lender Costs", "$ 6,622.75");
	container1.append(headerCon1).append(row1Con1).append(row2Con1).append(
			row3Con1);
	var container2 = $('<div>').attr({
		"class" : "closing-cost-container"
	});
	var headerCon2 = getClosingCostConatinerHeader("Estimated Third Party Cost");
	var row1Con2 = getClosingCostContainerRow(1, "Appraisal Fee", "$ 455.00");
	var row2Con2 = getClosingCostContainerRow(2, "Lenders Title Insurance",
			"$ 450.00");
	var row3Con2 = getClosingCostContainerRow(3, "Escrow/Closing Fee",
			"$ 500.00");
	var row4Con2 = getClosingCostContainerRow(4, "Government Recording",
			"$ 107.00");
	var row5Con2 = getClosingCostContainerLastRow(5,
			"Total Estimated Third Party Costs", "$ 1,562.00");
	container2.append(headerCon2).append(row1Con2).append(row2Con2).append(
			row3Con2).append(row4Con2).append(row5Con2);
	return wrapper.append(heading).append(container1).append(container2);
}

function getClosingCostBottomConatiner() {
	var wrapper = $('<div>').attr({
		"class" : "closing-cost-cont-wrapper-bottom no-border-bottom"
	});
	var heading = getClosingCostHeadingCont("Total Estimated Closing Cost");
	var container1 = $('<div>').attr({
		"class" : "closing-cost-container"
	});
	var headerCon1 = getClosingCostConatinerHeader("Prepaids");
	var row1Con1 = getClosingCostContainerRowWithSubText(
			1,
			"Prepaid Interest",
			"$ 699.40",
			"This amount is $34.9700 perday for 20 days(if your 					settlement os 2/9/2015).<br/>*Prepaid interest is an estimate and will adjust based on the confirmed final closing date");
	var row2Con1 = getClosingCostContainerLastRow(2, "Total Prepaids",
			"$ 699.40");
	container1.append(headerCon1).append(row1Con1).append(row2Con1);
	var container2 = $('<div>').attr({
		"class" : "closing-cost-container"
	});
	var headerCon2 = getClosingCostConatinerHeader("Estimated Reserves Deposited with Lender");
	var row1Con2 = getClosingCostContainerRowWithSubText(1,
			"Property Taxes - Estimated 2 Month(s)", "Calculate",
			"(Varies based on calendar month of  													closing)");
	row1Con2.find('.closing-cost-detail').addClass('calculate-btn');
	var row2Con2 = getClosingCostContainerRowWithSubText(2,
			"Homeowner's Insurance - Estimated 2 Month(s)", "Calculate",
			"(Provided you have 6 months of 																remaining coverage)");
	row2Con2.find('.closing-cost-detail').addClass('calculate-btn');
	var row3con2 = getClosingCostContainerLastRow(3,
			"Total Estimated Reserves Deposited with Lender", "$ 0.00");
	container2.append(headerCon2).append(row1Con2).append(row2Con2).append(
			row3con2);
	var bottomSubText = $('<div>')
			.attr({
				"class" : "closing-cost-bot-row"
			})
			.html(
					"Note :-Property Taxes for both 1st and 2nd half installments must be paid or will be collected at closing");
	return wrapper.append(heading).append(container1).append(container2)
			.append(bottomSubText);
}

function getClosingCostConatinerHeader(text) {
	var header = $('<div>').attr({
		"class" : "closing-cost-cont-desc-header"
	}).html(text);
	return header;
}

function getClosingCostContainerLastRow(rowNum, desc, detail) {
	var row = $('<div>').attr({
		"class" : "closing-cost-cont-desc-row no-border-bottom clearfix"
	});
	if (rowNum % 2 == 0) {
		row.addClass("closing-cost-cont-desc-row-even");
	}

	var rowDesc = $('<div>').attr({
		"class" : "closing-cost-desc float-left"
	}).html(desc);

	var rowDetail = $('<div>').attr({
		"class" : "closing-cost-detail float-left semi-bold"
	}).html(detail);

	return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerRow(rowNum, desc, detail) {
	var row = $('<div>').attr({
		"class" : "closing-cost-cont-desc-row clearfix"
	});
	if (rowNum % 2 == 0) {
		row.addClass("closing-cost-cont-desc-row-even");
	}

	var rowDesc = $('<div>').attr({
		"class" : "closing-cost-desc float-left"
	}).html(desc);

	var rowDetail = $('<div>').attr({
		"class" : "closing-cost-detail float-left"
	}).html(detail);

	return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerRowWithSubText(rowNum, desc, detail, subtext) {
	var row = $('<div>').attr({
		"class" : "closing-cost-cont-desc-row clearfix"
	});
	if (rowNum % 2 == 0) {
		row.addClass("closing-cost-cont-desc-row-even");
	}

	var rowDesc = $('<div>').attr({
		"class" : "closing-cost-desc float-left"
	});
	var descText = $('<div>').attr({
		"class" : "semi-bold"
	}).html(desc);

	var subTextDiv = $('<div>').attr({
		"class" : "subtext"
	}).html(subtext);

	rowDesc.append(descText).append(subTextDiv);

	var rowDetail = $('<div>').attr({
		"class" : "closing-cost-detail float-left"
	}).html(detail);

	return row.append(rowDesc).append(rowDetail);
}

function getClosingCostHeadingCont(text) {
	var heading = $('<div>').attr({
		"class" : "closing-cost-cont-heading"
	}).html(text);
	return heading;
}

/*
 * Functions for upload items module
 */

function paintUploadNeededItemsPage() {
	var header = $('<div>').attr({
		"class" : "upload-item-header uppercase"
	}).html("Upload needed items");
	var container = $('<div>').attr({
		"class" : "upload-item-container"
	});
	var fileDragDropCon = getFileDragAndDropContainer();
	var documentContainer = getDocumentContainer();
	var neededItemsWrapper = getNeedItemsWrapper();
	var uploadedItemsWrapper = getUploadedItemsWrapper();
	container.append(fileDragDropCon).append(documentContainer).append(
			neededItemsWrapper).append(uploadedItemsWrapper);
	$('#center-panel-cont').append(header).append(container);

	// using dropzone js for file upload
	new Dropzone("#drop-zone", {
		url : "./IndexNew.do",
		clickable : "#file-upload-icn",
		drop : function() {
			alert("dropped");
		},
		complete : function() {
			$('#file-upload-icn').removeClass('file-upload-hover-icn');
		},
		dragenter : function() {
			$('#file-upload-icn').addClass('file-upload-hover-icn');
		},
		dragleave : function() {
			$('#file-upload-icn').removeClass('file-upload-hover-icn');
		},
		dragover : function() {
			$('#file-upload-icn').addClass('file-upload-hover-icn');
		}
	});
}

/*
 * $(document).on(".file-drag-drop-container", "dropover", function( event, ui ) {
 * $('#file-upload-icn').addClass('file-upload-icn-hover'); });
 * 
 * $(document).on(".file-drag-drop-container", "drop", function( event, ui ) {
 * alert("dropped"); });
 */

function getFileDragAndDropContainer() {
	var container = $('<div>').attr({
		"id" : "drop-zone",
		"class" : "file-drag-drop-container"
	});

	var fileUploadIcn = $('<div>').attr({
		"id" : "file-upload-icn",
		"class" : "file-upload-icn"
	});
	var textCont1 = $('<div>').attr({
		"class" : "file-upload-text-cont1"
	}).html("Select Files to Upload/Drag and Drop Files");
	var textCont1Mobile = $('<div>').attr({
		"class" : "file-upload-text-mobile"
	}).html("Select Files to Upload");
	var textCont2 = $('<div>').attr({
		"class" : "file-upload-text-cont2"
	}).html("or, E-Mail Attachment To : 654321@loan.newfi.com");

	return container.append(fileUploadIcn).append(textCont1).append(
			textCont1Mobile).append(textCont2);
}

function getDocumentContainer() {
	var container = $('<div>').attr({
		"class" : "document-container clearfix"
	});
	var col1 = getDocumentUploadColumn("Salaried-W-2 forms for the most recent 2 years");
	var col2 = getDocumentUploadColumn("Payroll stubs for the past 30 days (showing YTD earings)");
	var col3 = getDocumentUploadColumn("Salaried-W-2 forms for the most recent 2 years");
	var col4 = getDocumentUploadColumn("Payroll stubs for the past 30 days (showing YTD earings)");
	return container.append(col1).append(col2).append(col3).append(col4);
}

function getDocumentUploadColumn(description) {
	var column = $('<div>').attr({
		"class" : "document-cont-col float-left"
	});
	var docImg = $('<div>').attr({
		"class" : "doc-img"
	});
	var docDesc = $('<div>').attr({
		"class" : "doc-desc"
	}).html(description);
	var docAssign = $('<div>').attr({
		"class" : "doc-assign"
	}).html("Assign Document");
	return column.append(docImg).append(docDesc).append(docAssign);
}

function getNeedItemsWrapper() {
	var wrapper = $('<div>').attr({
		"class" : "needed-items-wrapper"
	});
	var header = $('<div>').attr({
		"class" : "needed-items-header uppercase"
	}).html("needed items");
	var container = $('<div>').attr({
		"class" : "needed-items-container clearfix"
	});
	var leftContainer = $('<div>').attr({
		"class" : "needed-items-lc float-left"
	});
	var incomeDocCont = $('<div>').attr({
		"class" : "needed-doc-container"
	});
	var incDocHeading = $('<div>').attr({
		"class" : "needed-doc-heading"
	}).html("Income Documents");
	incomeDocCont.append(incDocHeading);
	var incDocRow1 = getNeededDocRow("Salaried-W-2 forms for the most recent 2 years");
	var incDocRow2 = getNeededDocRow("Payroll stubs for the past 30 days (showing YTD earnings)");
	incomeDocCont.append(incDocRow1).append(incDocRow2);
	var propDocContainer = $('<div>').attr({
		"class" : "needed-doc-container"
	});
	var propDocHeading = $('<div>').attr({
		"class" : "needed-doc-heading"
	}).html("Property Documents");
	propDocContainer.append(propDocHeading);
	var propDocRow1 = getNeededDocRow("Refinance - Copy of property tax bill");
	var propDocRow2 = getNeededDocRow("Refinance - Copy of homeowner's hazard insurance policy");
	propDocContainer.append(propDocRow1).append(propDocRow2);
	leftContainer.append(incomeDocCont).append(propDocContainer);
	var rightContainer = $('<div>').attr({
		"class" : "needed-items-rc float-right"
	});
	container.append(leftContainer).append(rightContainer);
	return wrapper.append(header).append(container);
}

function getNeededDocRow(desc) {
	var row = $('<div>').attr({
		"class" : "needed-item-row clearfix"
	});
	var leftImage = $('<div>').attr({
		"class" : "list-item-icn float-left"
	});
	var rowDesc = $('<div>').attr({
		"class" : "needed-item-desc float-left"
	}).html(desc);
	var docUploadImage = $('<div>').attr({
		"class" : "doc-uploaded-icn float-left"
	});
	row.append(leftImage).append(rowDesc).append(docUploadImage);
	var docRemoveImage = $('<div>').attr({
		"class" : "doc-remove-icn float-left"
	});
	row.append(docRemoveImage);
	return row;
}

function getUploadedItemsWrapper() {
	var wrapper = $('<div>').attr({
		"class" : "uploaded-items-wrapper"
	});
	var header = $('<div>').attr({
		"class" : "uploaded-items-header uppercase"
	}).html("uploaded items");
	var container = $('<div>').attr({
		"class" : "uploaded-items-container clearfix"
	});
	var assetContainer = getAssetContainer();
	var unassignDocContainer = getUnassignDocContiner();
	container.append(assetContainer).append(unassignDocContainer);
	return wrapper.append(header).append(container);
}

function getAssetContainer() {
	var container = $('<div>').attr({
		"class" : "asset-doc-cont float-left"
	});
	var heading = $('<div>').attr({
		"class" : "uploaded-items-cont-heading"
	}).html("Asset Documents");
	container.append(heading);
	var row1 = getAssetDocRow("2 months statements for most recent retirement fund or stock portfolio");
	var row2 = getAssetDocRow("2 months bank statements (all pages)");
	var row3 = getAssetDocRow("2 months statements for most recent retirement fund or stock portfolio");
	var row4 = getAssetDocRow("2 months bank statements (all pages)");
	return container.append(row1).append(row2).append(row3).append(row4);
}

function getAssetDocRow(desc) {
	var row = $('<div>').attr({
		"class" : "asset-doc-row"
	}).html(desc);
	return row;
}

function getUnassignDocContiner() {
	var container = $('<div>').attr({
		"class" : "unassign-doc-cont float-left"
	});
	var heading = $('<div>').attr({
		"class" : "uploaded-items-cont-heading"
	}).html("Unassigned Documents");
	container.append(heading);
	var row1 = getUnassignedDocRow("2 months bank statements (all pages)");
	var row2 = getUnassignedDocRow("2 months statements for most recent retirement fund or stock portfolio");
	var row3 = getUnassignedDocRow("2 months bank statements (all pages)");
	return container.append(row1).append(row2).append(row3);
}

function getUnassignedDocRow(desc) {
	var row = $('<div>').attr({
		"class" : "unassign-doc-row"
	}).html(desc);
	return row;
}