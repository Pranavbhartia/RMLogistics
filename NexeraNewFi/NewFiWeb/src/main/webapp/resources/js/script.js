function paintFixYourRatePage() {
	var rateProgramWrapper = getRateProgramContainer();
	var loanSummaryWrapper = getLoanSummaryWrapper();
	var closingCostWrapper = getClosingCostSummaryContainer();
	$('#center-panel-cont').html('');
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
	mobileSliderCont.append(col1).append(col2);
}

function getLoanSummaryWrapper() {
	var parentWrapper = $('<div>').attr({
		"class" : "loan-summary-wrapper"
	});
	var header = getLoanSummaryHeader();
	var container = getLoanSummaryContainer();
	var bottomText = getHeaderText("Quoted Rates are not guarteed. You may use this tool to check current rates or request a  rate lock. APR is an estimate based on an average $200,000 loan amount with 2% in total APR related fees. Actual will beon your Good Faith Estimate after Loan Amount and Income are Verified.");
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
	var row1Con1 = getClosingCostContainerRowWithSubText(1, "Prepaid Interest", "$ 699.40","This amount is $34.9700 perday for 20 days(if your 					settlement os 2/9/2015).<br/>*Prepaid interest is an estimate and will adjust based on the confirmed final closing date");
	var row2Con1 = getClosingCostContainerLastRow(2, "Total Prepaids","$ 699.40");
	container1.append(headerCon1).append(row1Con1).append(row2Con1);
	var container2 = $('<div>').attr({
		"class" : "closing-cost-container"
	});
	var headerCon2 = getClosingCostConatinerHeader("Estimated Reserves Deposited with Lender");
	var row1Con2 = getClosingCostContainerRowWithSubText(1, "Property Taxes - Estimated 2 Month(s)","Calculate","(Varies based on calendar month of  													closing)");
	row1Con2.find('.closing-cost-detail').addClass('calculate-btn');
	var row2Con2 = getClosingCostContainerRowWithSubText(2, "Homeowner's Insurance - Estimated 2 Month(s)","Calculate","(Provided you have 6 months of 																remaining coverage)");
	row2Con2.find('.closing-cost-detail').addClass('calculate-btn');
	var row3con2 = getClosingCostContainerLastRow(3,"Total Estimated Reserves Deposited with Lender","$ 0.00");
	container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row3con2);
	var bottomSubText = $('<div>').attr({
		"class" : "closing-cost-bot-row"
	}).html("Note :-Property Taxes for both 1st and 2nd half installments must be paid or will be collected at closing");
	return wrapper.append(heading).append(container1).append(container2).append(bottomSubText);
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

function getClosingCostContainerRowWithSubText(rowNum, desc, detail,subtext) {
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