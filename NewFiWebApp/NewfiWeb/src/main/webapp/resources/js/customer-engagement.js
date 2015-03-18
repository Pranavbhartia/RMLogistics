//JavaScript functions for customer engagement pages

var refinanceTeaserRate = new Object();
if (sessionStorage.refinaceData) {
	refinanceTeaserRate = JSON.parse(sessionStorage.refinaceData);
}

var stages = 0;

function paintSelectLoanTypeQuestion() {
	stages = 0;
	$('#ce-main-container').html('');

	var rateIcon = $('<div>').attr({
		"class" : "ce-rate-icon"
	});

	var titleText = $('<div>').attr({
		"class" : "ce-title"
	}).html("A New way to Finance your home");

	$('#ce-main-container').append(rateIcon).append(titleText);

	var wrapper = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var optionsContainer = $('<div>').attr({
		"class" : "ce-ques-options-container clearfix"
	});

	var option1 = $('<div>').attr({
		"class" : "ce-ques-option float-left"
	}).html("Refinance").on('click', function() {
		paintRefinanceMainContainer();
	});

	var option2 = $('<div>').attr({
		"class" : "ce-ques-option float-left"
	}).html("Buy a home").on('click', function() {
		paintBuyHomeContainer();
	});

	optionsContainer.append(option1).append(option2);

	var question = $('<div>')
			.attr({
				"class" : "ce-ques-text"
			})
			.html(
					"Why Pay thousands more to use a loan officer ?<br/>With newfi, saving weeks of headache and thousands of dollars is easy.");

	wrapper.append(optionsContainer).append(question);

	$('#ce-main-container').append(wrapper);
}

function paintRefinanceMainContainer() {
	$('#ce-main-container').html('');
	var wrapper = $('<div>').attr({
		"class" : "ce-refinance-wrapper clearfix"
	});

	var leftPanel = getRefinanceLeftPanel();

	var centerPanel = $('<div>').attr({
		"id" : "ce-refinance-cp",
		"class" : "ce-cp float-left"
	});

	wrapper.append(leftPanel).append(centerPanel);
	$('#ce-main-container').append(wrapper);

	paintRefinanceQuest1();
}

var itemsList = [ "Your Priority", "Your Mortgage", "Monthly Payment",
		"Home Value", "Zip Code" ,"Your Rates"];

function getRefinanceLeftPanel() {
	var container = $('<div>').attr({
		"class" : "ce-lp float-left"
	});

	for (var i = 0; i < itemsList.length; i++) {
		var itemCompletionStage = "NOT_STARTED";

		var itemCont = getRefinanceLeftPanelItem(itemsList[i], i + 1,
				itemCompletionStage);
		container.append(itemCont);
	}

	return container;
}

function getRefinanceLeftPanelItem(itemTxt, stepNo, itemCompletionStage) {
	var itemCont = $('<div>').attr({
		"class" : "ce-lp-item clearfix",
		"data-step" : stepNo,
		"id" : "progressBaarId_" + stepNo
	});

	var leftIcon = $('<div>').attr({
		"class" : "ce-lp-item-icon float-left",
		"id" : "stepNoId_" + stepNo
	});

	/*
	 * if(itemCompletionStage == "COMPLETE"){
	 * itemCont.addClass('ce-lp-complete'); }else if(itemCompletionStage ==
	 * "NOT_STARTED"){
	 */
	itemCont.addClass('ce-lp-not-started');
	leftIcon.html(stepNo);
	/*
	 * }else if(itemCompletionStage == "IN_PROGRESS"){
	 * itemCont.addClass('ce-lp-in-progress'); leftIcon.html(stepNo); }
	 */

	var textCont = $('<div>').attr({
		"class" : "ce-lp-item-text float-left"
	}).html(itemTxt);

	return itemCont.append(leftIcon).append(textCont);
}

function paintRefinanceQuest1() {

	stages = 1;
	progressBaar(1);
	var quesText = "Why do you want to refinance?";

	var options = [ {
		"text" : "Lower My Monthly Payment",
		"onselect" : paintRefinanceStep2,
		"value" : "lowerMonthlyPayment"
	}, {
		"text" : "Pay Off My Mortgage Faster",
		"onselect" : paintRefinanceStep1a,
		"value" : "payOffMortgage"
	}, {
		"text" : "Take Cash Out of My Home",
		"onselect" : paintRefinanceStep1b,
		"value" : "takeCashOut"
	} ];

	var quesCont = getMutipleChoiceQuestion(quesText, options,
			"refinanceOption");

	$('#ce-refinance-cp').html(quesCont);
	/*
	 * $("#progressBaarId_1").addClass('ce-lp-in-progress');
	 * $('#stepNoId_1').html("1");
	 */

}

function getMutipleChoiceQuestion(quesText, options, name) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	for (var i = 0; i < options.length; i++) {
		var option = $('<div>').attr({
			"class" : "ce-option",
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : name
		}, function(event) {
			var key = event.data.name;

			refinanceTeaserRate[key] = event.data.option.value;

			event.data.option.onselect();
		});
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}

function getTextQuestion(quesText, clickEvent, name) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : name,
		"value" : refinanceTeaserRate[name]
	});

	optionContainer.append(inputBox);

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind('click', {
		'clickEvent' : clickEvent,
		"name" : name
	}, function(event) {
		var key = event.data.name;
		refinanceTeaserRate[key] = $('input[name="' + key + '"]').val();

		sessionStorage.refinaceData = JSON.stringify(refinanceTeaserRate);

		event.data.clickEvent();
	});

	return container.append(quesTextCont).append(optionContainer).append(
			saveBtn);
}

function paintRefinanceLiveNow() {
	stages = 2;
	progressBaar(2);
	var quesTxt1 = "Where You Live Now ?";
	var quesTxt2 = "Your Current Address ?";
	var quesTxt3 = "City";
	var quesTxt4 = "State";
	var quesTxt5 = "ZIP Code";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt1);

	/*var optionContainer1 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "liveNow",
		"value" : refinanceTeaserRate["liveNow"]
	});*/

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-sub-ques-text"
	}).html(quesTxt2);

	var optionContainer2 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "currentAddress",
		"value" : refinanceTeaserRate["currentAddress"]
	});

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-sub-ques-text"
	}).html(quesTxt3);

	var optionContainer3 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "city",
		"value" : refinanceTeaserRate["city"]
	});

	var quesTextCont4 = $('<div>').attr({
		"class" : "ce-rp-sub-ques-text"
	}).html(quesTxt4);

	var optionContainer4 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox4 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "state",
		"value" : refinanceTeaserRate["state"]
	});

	var quesTextCont5 = $('<div>').attr({
		"class" : "ce-rp-sub-ques-text"
	}).html(quesTxt5);

	var optionContainer5 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox5 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "zipCode",
		"value" : refinanceTeaserRate["zipCode"]
	});

	//optionContainer1.append(inputBox1);
	optionContainer2.append(inputBox2);
	optionContainer3.append(inputBox3);
	optionContainer4.append(inputBox4);
	optionContainer5.append(inputBox5);

	//quesTextCont1.append(optionContainer1);
	quesTextCont2.append(optionContainer2);
	quesTextCont3.append(optionContainer3);
	quesTextCont4.append(optionContainer4);
	quesTextCont5.append(optionContainer5);

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind(
			'click',
			function(event) {

				refinanceTeaserRate["liveNow"] = $('input[name="liveNow"]')
						.val();
				refinanceTeaserRate["currentAddress"] = $(
						'input[name="currentAddress"]').val();
				refinanceTeaserRate["city"] = $('input[name="city"]').val();
				refinanceTeaserRate["state"] = $('input[name="state"]').val();
				refinanceTeaserRate["zipCode"] = $('input[name="zipCode"]')
						.val();

				paintRefinanceStartLiving();
				sessionStorage.refinaceData = JSON
						.stringify(refinanceTeaserRate);

			});

	$('#ce-refinance-cp').html(
			container.append(quesTextCont1).append(quesTextCont2).append(
					quesTextCont3).append(quesTextCont4).append(quesTextCont5)
					.append(saveBtn));

	// $('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceCurrentAddress() {

	var quesTxt = "What Is Your Current Address?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceCity,
			"currentAddress");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceCity() {

	var quesTxt = "What Is Your City?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceState, "city");
	$('#ce-refinance-cp').html(quesCont);

}

function paintRefinanceState() {

	var quesTxt = "What Is Your State?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceZip, "state");
	$('#ce-refinance-cp').html(quesCont);

}

function paintRefinanceZip() {

	var quesTxt = "What Is Your ZipCode?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceStartLiving,
			"zipCode");
	$('#ce-refinance-cp').html(quesCont);

}

function paintRefinanceStartLiving() {
	stages = 3;
	progressBaar(3);
	var quesTxt = "When did you start living here?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep1a, "startLiving");

	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1a() {

	var quesTxt = "How many years are left on your mortgage?";

	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2,
			"yearLeftOnMortgage");

	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep2() {
stages = 2;
progressBaar(2);

	var quesTxt = "What is your current mortgage balance?";
	var quesCont = "";
	//if (refinanceTeaserRate['refinanceOption'] == 'lowerMonthlyPayment'
		//	|| refinanceTeaserRate['refinanceOption'] == 'payOffMortgage') {
		quesCont = getTextQuestion(quesTxt, paintRefinanceStep3,
				"currentMortgageBalance");
	//}
//	if (refinanceTeaserRate['refinanceOption'] == 'takeCashOut') {
//		quesCont = getTextQuestion(quesTxt, paintRefinanceStep1b,
//				"currentMortgageBalance");
//	} 
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1b() {
	var quesTxt = "How much cash do you want to take out?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2, "cashTakeOut");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep3() {
stages = 3;
progressBaar(3);

	var quesTxt = "What is your current mortgage payment?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep4,
			"currentMortgagePayment");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep4() {
	var quesTxt = "Does the monthly mortgage payment entered above include property taxes and/or homeowners insurance?";
	var options = [ {
		"text" : "Yes",
		"onselect" : paintRefinanceAnnualPropertyTaxes,
		"name" : name,
		"value" : 1
	}, {
		"text" : "No",
		"onselect" : paintRefinanceHomeWorthToday,
		"name" : name,
		"value" : 0
	} ];

	var quesCont = getMutipleChoiceQuestion(quesTxt, options, "includeTaxes");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceHomeWorthToday() {
stages = 4;
progressBaar(4);
	var quesTxt = "Approximately what is your home worth today?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceHomeZipCode,
			"homeWorthToday");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceAnnualPropertyTaxes() {
	var quesTxt = "How much is your annual property taxes?";
	var quesCont = getTextQuestion(quesTxt,
			paintRefinanceAnnualHomeownersInsurance, "annualPropertyTaxes");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceAnnualHomeownersInsurance() {
	var quesTxt = "How much is your annual homeowners insurance?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceHomeWorthToday,
			"annualHomeownersInsurance");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceHomeZipCode() {
stages = 5;
progressBaar(5);
var quesTxt = "What is the zip code of your home?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceSeeRates,
			"zipCode");
	$('#ce-refinance-cp').html(quesCont);
	
}

function paintRefinanceMyIncome() {

	var quesTxt = "Select all that apply";
	var options = [ {
		"text" : "Employed",
		"onselect" : paintRefinanceEmployed,
		"name" : name,
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintRefinanceSelfEmployed,
		"name" : name,
		"value" : 1
	}, {
		"text" : "Social Security Income/Disability",
		"onselect" : paintRefinanceDisability,
		"name" : name,
		"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintRefinancePension,
		"name" : name,
		"value" : 3
	} ];
	var quesCont = paintRefinanceMyMoney(quesTxt, options, name);

	$('#ce-refinance-cp').html(quesCont);

}

function paintRefinanceEmployed(divId) {

	var quesTxt = "About how much do you make a year";
	var quesCont = getMultiTextQuestion(quesTxt);
	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(quesCont);
}

function paintRefinanceSelfEmployed(divId) {

	var quesTxt = "How much do you make a year?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-option-text"
	}).html(quesTxt);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : "selfEmployed"
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
}

function paintRefinanceDisability(divId) {

	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-option-text"
	}).html(quesTxt);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : "disability"
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
}

function paintRefinancePension(divId) {

	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-option-text"
	}).html(quesTxt);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : "pension"
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
}

function paintRefinanceMyMoney(quesText, options, name) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	for (var i = 0; i < options.length; i++) {

		var optionIncome = $('<div>').attr({
			"class" : "hide ce-option-ques-wrapper",
			"id" : "ce-option_" + i
		});

		var option = $('<div>').attr({
			"class" : "ce-option-checkbox",
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : name
		}, function(event) {
			$('.ce-option-checkbox').removeClass("ce-option-checked");
			$(this).addClass("ce-option-checked");
			var key = event.data.name;
			refinanceTeaserRate[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);
		});

		optionContainer.append(option).append(optionIncome);
	}

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind(
			'click',
			function() {
				refinanceTeaserRate["beforeTax"] = $('input[name="beforeTax"]')
						.val();
				refinanceTeaserRate["workPlace"] = $('input[name="workPlace"]')
						.val();
				refinanceTeaserRate["startWorking"] = $(
						'input[name="startWorking"]').val();
				refinanceTeaserRate["selfEmployed"] = $(
						'input[name="selfEmployed"]').val();
				refinanceTeaserRate["disability"] = $(
						'input[name="disability"]').val();
				refinanceTeaserRate["pension"] = $('input[name="pension"]')
						.val();

				sessionStorage.refinaceData = JSON
						.stringify(refinanceTeaserRate);
				paintRefinanceDOB();
			});

	return container.append(quesTextCont).append(optionContainer).append(
			saveBtn);
}

function getMultiTextQuestion(quesText) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper",
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-option-text",
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont",
	});

	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text",
	}).html("Before Tax");

	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "beforeTax",
	});

	quesTextCont1.append(inputBox1);

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "workPlace"
	});

	quesTextCont2.append(inputBox2);

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Wokring ?");

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startWorking"
	});

	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(
			quesTextCont3);

	return container.append(quesTextCont).append(optionContainer);
}

function paintRefinanceDOB() {
	stages = 5;
	progressBaar(5);
	var quesTxt = "Please enter your birthdate.";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceSSN, "dob");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceSSN() {

	var quesTxt = "Please enter your social security number.";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceCreditScore, "ssn");
	$('#ce-refinance-cp').html(quesCont);

}

function paintRefinanceCreditScore() {

	if (refinanceTeaserRate["ssn"] == ""
			|| refinanceTeaserRate["ssn"] == undefined) {

		var quesTxt = "Please give Your Credit Score";
		var quesCont = getTextQuestion(quesTxt, paintRefinancePhoneNumber,
				"creditscore");
		$('#ce-refinance-cp').html(quesCont);
	} else {
		paintRefinancePhoneNumber();
	}

}

function paintRefinancePhoneNumber() {

	var quesTxt = "Please enter your phone number";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceSeeRates,
			"phoneNumber");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceSeeRates() {

	// teaserFixYourRatePage();
stages = 6;
progressBaar(6);
	delete sessionStorage.refinaceData;

	var quesTxt = "Analyze & Adjust Your Numbers";
	var container = $('<div>').attr({
		"class" : "ce-rate-main-container"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);

	var rateProgramWrapper = getRateProgramContainer();
	var loanSummaryWrapper = getLoanSummaryWrapper();
	var closingCostWrapper = getClosingCostSummaryContainer();

	//container.append(quesTextCont).append(rateProgramWrapper).append(
		//	loanSummaryWrapper).append(closingCostWrapper);
container.append(quesTextCont).append(rateProgramWrapper);
	$('#ce-refinance-cp').html(container);
$('#overlay-loader').show();
	$.ajax({

		url : "rest/calculator/findteaseratevalue",
		type : "POST",
		data : {
			"teaseRate" : JSON.stringify(refinanceTeaserRate)
		},
		datatype : "application/json",
		success : function(data) {
			
			$('#overlay-loader').hide();
			
			paintteaserRate(data);
			//printMedianRate(data,container);
			
		},
		error : function() {
			alert("error");
			$('#overlay-loader').hide();
		}

	});
}




function printMedianRate(data,container){
teaserresult = JSON.parse(data);
var rateVoJson;
var counter = 0;
var rateAndClosingCost =0;
//alert(teaserresult);

for (var j in teaserresult) {
   
    var loanDuration = teaserresult[j].loanDuration;
    console.log("loanDuration"+loanDuration);
    
    if ("30 YR FIXED CONFORMING".match(".*\\b" + loanDuration + "\\b.*")) {
    console.log('inside if');
  //  alert('inside if');
    
    
    
        rateVoJson = JSON.stringify(teaserresult[j].rateVO);
        rateVoJsonArrayLength = JSON.parse(rateVoJson).length;

        index = parseInt(rateVoJsonArrayLength / 2);
        rateAndClosingCost = JSON.parse(rateVoJson)[index];
        counter ++;
        console.log(counter);
console.log(rateVoJson);
console.log(rateVoJsonArrayLength);
console.log(index);
console.log(rateAndClosingCost);
        break;
    }
}



if (rateAndClosingCost === undefined) {
    alert("No rate is present");
}
//alert(rateAndClosingCost.teaserRate);
console.log(" median rate :"+rateAndClosingCost.teaserRate);

var rateresult = $('<div>').attr({
"class" : "ce-rate-result cp-est-cost-btn"
}).html(rateAndClosingCost.teaserRate);
container.append(rateresult);
$('#ce-refinance-cp').append(rateresult);



}





function progressBaar(num) {

	var count = 6;
	$("#progressBaarId_" + num).removeClass('ce-lp-in-progress').removeClass(
			'ce-lp-complete').addClass('ce-lp-in-progress');
	$('#stepNoId_' + num).html(num);

	for (var i = 1; i <= num - 1; i++) {
		$("#progressBaarId_" + i).removeClass('ce-lp-in-progress').removeClass(
				'ce-lp-not-started').addClass('ce-lp-complete');
		$('#stepNoId_' + i).html("");
	}
	for (var i = num + 1; i <= count; i++) {
		$("#progressBaarId_" + i).removeClass('ce-lp-in-progress').removeClass(
				'ce-lp-complete').addClass('ce-lp-not-started');
		$('#stepNoId_' + i).html(i);
	}
}

function teaserFixYourRatePage() {
	$('#ce-refinance-cp').html('');
	var rateProgramWrapper = getRateProgramContainer();
	//var loanSummaryWrapper = getLoanSummaryWrapper();
	//var closingCostWrapper = getClosingCostSummaryContainer();
	//$('#ce-refinance-cp').append(rateProgramWrapper).append(loanSummaryWrapper)
	//		.append(closingCostWrapper);
	$('#ce-refinance-cp').append(rateProgramWrapper);
}
