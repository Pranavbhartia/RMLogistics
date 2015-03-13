var active = 0;

var buyHomeTeaserRate = new Object();

var buyHomeitemsList = [ "Living Situation", "Home Information",
		"Loan Information", "My Money", "My Credit" ];

function getBuyHomeLeftPanel() {
	var container = $('<div>').attr({
		"class" : "ce-lp float-left"
	});

	for (var i = 0; i < buyHomeitemsList.length; i++) {
		var itemCompletionStage = "NOT_STARTED";

		var itemCont = getbuyHomeLeftPanelItem(buyHomeitemsList[i], i + 1,
				itemCompletionStage);
		container.append(itemCont);
	}

	return container;
}

function getbuyHomeLeftPanelItem(itemTxt, stepNo, itemCompletionStage) {
	var itemCont = $('<div>').attr({
		"class" : "ce-lp-item clearfix",
		"data-step" : stepNo,
		"id" : "homeProgressBaarId_" + stepNo
	});

	var leftIcon = $('<div>').attr({
		"class" : "ce-lp-item-icon float-left",
		"id" : "homeStepNoId_" + stepNo
	});

	itemCont.addClass('ce-lp-not-started');
	leftIcon.html(stepNo);

	var textCont = $('<div>').attr({
		"class" : "ce-lp-item-text float-left"
	}).html(itemTxt);

	return itemCont.append(leftIcon).append(textCont);
}

function paintBuyHomeContainer() {

	$('#ce-main-container').html('');
	var wrapper = $('<div>').attr({
		"class" : "ce-refinance-wrapper clearfix"
	});

	var leftPanel = getBuyHomeLeftPanel();

	var centerPanel = $('<div>').attr({
		"id" : "ce-refinance-cp",
		"class" : "ce-cp float-left"
	});

	wrapper.append(leftPanel).append(centerPanel);
	$('#ce-main-container').append(wrapper);

	paintBuyHomeQuest();
}

function paintBuyHomeQuest() {
	homeProgressBaar(1);
	var quesText = "Living Situation";

	var options = [ {
		"text" : "Renting",
		"onselect" : paintBuyHomeRenting,
		"value" : 0
	}, {
		"text" : "I am a home owner",
		"onselect" : paintBuyHomeOwner,
		"value" : 1
	} ];

	var quesCont = getBuyHomeMutipleChoiceQuestion(quesText, options,
			"livingSituation");
	$('#ce-refinance-cp').html(quesCont);
	/*
	 * $("#progressBaarId_1").addClass('ce-lp-in-progress');
	 * $('#stepNoId_1').html("1");
	 */
}

function getBuyHomeMutipleChoiceQuestion(quesText, options, name) {
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
			buyHomeTeaserRate[key] = event.data.option.value;
			event.data.option.onselect();
		});
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}

// Renting

function paintBuyHomeRenting() {
	active = 2;
	homeProgressBaar(2);
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

	var optionContainer1 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "liveNow"
	});

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt2);

	var optionContainer2 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "currentAddress"
	});

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt3);

	var optionContainer3 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "city"
	});

	var quesTextCont4 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt4);

	var optionContainer4 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox4 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "state"
	});

	var quesTextCont5 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt5);

	var optionContainer5 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox5 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "zipCode"
	});

	quesTextCont1.append(optionContainer1).append(inputBox1);
	quesTextCont2.append(optionContainer2).append(inputBox2);
	quesTextCont3.append(optionContainer3).append(inputBox3);
	quesTextCont4.append(optionContainer4).append(inputBox4);
	quesTextCont5.append(optionContainer5).append(inputBox5);

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
			.bind(
					'click',
					function(event) {

						buyHomeTeaserRate["liveNow"] = $(
								'input[name="liveNow"]').val();
						buyHomeTeaserRate["currentAddress"] = $(
								'input[name="currentAddress"]').val();
						buyHomeTeaserRate["city"] = $('input[name="city"]')
								.val();
						buyHomeTeaserRate["state"] = $('input[name="state"]')
								.val();
						buyHomeTeaserRate["zipCode"] = $(
								'input[name="zipCode"]').val();

						paintBuyHomEachMonthrent();
					});

	$('#ce-refinance-cp').html(
			container.append(quesTextCont1).append(quesTextCont2).append(
					quesTextCont3).append(quesTextCont4).append(quesTextCont5)
					.append(saveBtn));

	/*
	 * $("#progressBaarId_1").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	 * $('#stepNoId_1').html("");
	 * 
	 * $("#progressBaarId_2").addClass('ce-lp-in-progress');
	 * $('#stepNoId_2').html("2");
	 */
}

function paintBuyHomEachMonthrent() {
	active = 3;
	homeProgressBaar(3);
	var quesTxt1 = "How much do you pay each month for rent?";
	var quesTxt2 = "When did you start living here?";
	var quesTxt3 = "Where did you live earlier";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt1);

	var optionContainer1 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "rentPerMonth"
	});

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt2);

	var optionContainer2 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startLiving"
	});

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt3);

	var optionContainer3 = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "liveEarlier"
	});

	quesTextCont1.append(optionContainer1).append(inputBox1);
	quesTextCont2.append(optionContainer2).append(inputBox2);
	quesTextCont3.append(optionContainer3).append(inputBox3);

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind(
			'click',
			function(event) {

				buyHomeTeaserRate["rentPerMonth"] = $(
						'input[name="rentPerMonth"]').val();
				buyHomeTeaserRate["startLiving"] = $(
						'input[name="startLiving"]').val();
				buyHomeTeaserRate["liveEarlier"] = $(
						'input[name="liveEarlier"]').val();

				paintBuyHomeStartMilitaryloans();
			});

	if (buyHomeTeaserRate["livingSituation"] == 0) {

		$('#ce-refinance-cp').html(
				container.append(quesTextCont1).append(quesTextCont2).append(
						quesTextCont3).append(saveBtn));
	} else {
		var quesTextCont4 = paintBuyHomeSellHome();

		$('#ce-refinance-cp').html(
				container.append(quesTextCont2).append(quesTextCont3).append(
						quesTextCont4).append(saveBtn));
	}
	/*
	 * $("#progressBaarId_2").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	 * $('#stepNoId_2').html("");
	 * 
	 * $("#progressBaarId_3").addClass('ce-lp-in-progress');
	 * $('#stepNoId_3').html("3");
	 */
}

function paintBuyHomeSellHome() {

	var quesText = "Are you planning to sell your current home?";

	var options = [ {
		"text" : "Yes",
		// "onselect" : paintBuyHomeRenting,
		"value" : 0
	}, {
		"text" : "No",
		// "onselect" : paintBuyHomeOwner,
		"value" : 1
	} ];

	return paintSellYourHome(quesText, options, "sellYourHome");

}

function paintSellYourHome(quesText, options, name) {

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
			"id" : "ce-option_" + i,
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : name
		}, function(event) {
			var key = event.data.name;
			buyHomeTeaserRate[key] = event.data.option.value;

		});
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}

function paintBuyHomeStartMilitaryloans() {

	var quesTxt = "Are you eligible for, and interested in, VA/military loans?";
	var options = [ {
		"text" : "Yes",
		"onselect" : paintBuyHomeMyIncome,
		"name" : name,
		"value" : 1
	}, {
		"text" : "No",
		"onselect" : paintBuyHomeMyIncome,
		"name" : name,
		"value" : 0
	} ];

	var quesCont = getBuyHomeMutipleChoiceQuestion(quesTxt, options,
			"isVeteran");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeMyIncome() {

	active = 4;
	homeProgressBaar(4);
	var quesTxt = "Select all that apply";
	var options = [ {
		"text" : "Employed",
		"onselect" : painBuyHomeEmployed,
		"name" : name,
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintBuyHomeSelfEmployed,
		"name" : name,
		"value" : 1
	}, {
		"text" : "Social Security Income/Disability",
		"onselect" : paintBuyHomeDisability,
		"name" : name,
		"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintBuyHomePension,
		"name" : name,
		"value" : 3
	} ];
	var quesCont = paintBuyHomeMyMoney(quesTxt, options, name);

	$('#ce-refinance-cp').html(quesCont);
	/*
	 * $("#progressBaarId_3").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	 * $('#stepNoId_3').html("");
	 * $("#progressBaarId_4").addClass('ce-lp-in-progress');
	 * $('#stepNoId_4').html("4");
	 */

}

function painBuyHomeEmployed(divId) {

	var quesTxt = "About how much do you make a year";
	var quesCont = getBuyHomeMyMoney(quesTxt);
	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(quesCont);
}

function getBuyHomeMyMoney(quesText) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper",
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text",
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont",
	});

	// /----
	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text",
	}).html("Before Tax");

	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "beforeTax",
	});

	quesTextCont1.append(inputBox1);

	// /----
	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "workPlace"
	});

	quesTextCont2.append(inputBox2);

	// /----
	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Wokring ?");

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startWorking"
	});

	// /----

	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(
			quesTextCont3);

	return container.append(quesTextCont).append(optionContainer);
}

function paintBuyHomeSelfEmployed(divId) {

	var quesTxt = "How much do you make a year?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
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

function paintBuyHomeDisability(divId) {

	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
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

function paintBuyHomePension(divId) {

	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
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

function paintBuyHomeMyMoney(quesText, options, name) {
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
			"class" : "hide",
			"id" : "ce-option_" + i
		});

		var option = $('<div>').attr({
			"class" : "ce-option",
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : name
		}, function(event) {
			var key = event.data.name;
			buyHomeTeaserRate[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);
		});

		optionContainer.append(option).append(optionIncome);
	}

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
			.bind(
					'click',
					function() {
						buyHomeTeaserRate["beforeTax"] = $(
								'input[name="beforeTax"]').val();
						buyHomeTeaserRate["workPlace"] = $(
								'input[name="workPlace"]').val();
						buyHomeTeaserRate["startWorking"] = $(
								'input[name="startWorking"]').val();
						buyHomeTeaserRate["selfEmployed"] = $(
								'input[name="selfEmployed"]').val();
						buyHomeTeaserRate["disability"] = $(
								'input[name="disability"]').val();
						buyHomeTeaserRate["pension"] = $(
								'input[name="pension"]').val();

						if (buyHomeTeaserRate["livingSituation"] == 0) {
							paintBuyHomeDob();
						} else {
							if (buyHomeTeaserRate["sellYourHome"] == 0) {
								saleYourCurrentHome();
							} else {
								paintBuyHomeSSN();
							}

						}
					});

	return container.append(quesTextCont).append(optionContainer).append(
			saveBtn);
}

function saleYourCurrentHome() {

	var quesTxt = "What is the listing price of your current home?";
	var quesCont = getBuyHomeTextQuestion(quesTxt, paintBuyHomeMortgagebalance,
			"homeWorthToday");
	$('#ce-refinance-cp').html(quesCont);

}

function paintBuyHomeMortgagebalance() {

	var quesTxt = "What is the mortgage balance of your current home?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,
			paintBuyHomePurchaseforNewHome, "currentMortgageBalance");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomePurchaseforNewHome() {

	var quesTxt = "How much from this sale do you intend to use toward the purchase for your new home?";
	var quesCont = getBuyHomeTextQuestion(quesTxt, paintBuyHomeDob,
			"purchaseforNewHome");
	$('#ce-refinance-cp').html(quesCont);

}

function paintBuyHomeDob() {

	var quesTxt = "Please enter your birthdate.";
	var quesCont = getBuyHomeTextQuestion(quesTxt, paintBuyHomeSSN, "dob");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeSSN() {
	active = 5;
	homeProgressBaar(5);
	var quesTxt = "Please enter your social security number.";
	var quesCont = getBuyHomeTextQuestion(quesTxt, paintBuyHomeSeeRates, "ssn");
	$('#ce-refinance-cp').html(quesCont);

	/*
	 * $("#progressBaarId_4").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	 * $('#stepNoId_4').html("");
	 * $("#progressBaarId_5").addClass('ce-lp-in-progress');
	 * $('#stepNoId_5').html("5");
	 */
}

function paintBuyHomeSeeRates() {

	if (buyHomeTeaserRate["ssn"] == "" || buyHomeTeaserRate["ssn"] == undefined) {
		var quesTxt = "Please give Your Credit Score";
		var quesCont = getTextQuestion(quesTxt, paintBuyHomeSeeTeaserRate,
				"creditscore");
		$('#ce-refinance-cp').html(quesCont);
	} else {
		paintBuyHomeSeeTeaserRate();
	}

}

function paintBuyHomeSeeTeaserRate() {

	$(".ce-lp").html(" ");
	var quesTxt = "Analyze & Adjust Your Numbers";
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);

	var teaserRate = getRateProgramContainer();

	container.append(quesTextCont).append(teaserRate);
	//container.append(quesTextCont);

	$('#ce-refinance-cp').html(container);

	$.ajax({

		url : "rest/calculator/findteaseratevalue",
		type : "POST",
		data : {
			"teaseRate" : JSON.stringify(buyHomeTeaserRate)
		},
		datatype : "application/json",
		success : function(data) {
			//alert("success");
			// $('#teaserresult').html(teaserresult);
			// alert(teaserresult);
		},
		error : function() {
			alert("error");
		}

	});
}

function getBuyHomeMultiTextQuestion(quesText) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper",
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text",
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

function getBuyHomeTextQuestion(quesText, clickEvent, name) {
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
		"name" : name
	});

	optionContainer.append(inputBox);

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind('click', {
		'clickEvent' : clickEvent,
		"name" : name
	}, function(event) {
		var key = event.data.name;
		buyHomeTeaserRate[key] = $('input[name="' + key + '"]').val();
		event.data.clickEvent();
	});

	return container.append(quesTextCont).append(optionContainer).append(
			saveBtn);
}

function paintBuyHomeEmployed(divId) {

	var quesTxt = "About how much do you make a year";
	var quesCont = getMultiTextQuestion(quesTxt);
	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(quesCont);
}

function paintBuyHomeSelfEmployed(divId) {

	var quesTxt = "How much do you make a year?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
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

function paintbuyHomeDisability(divId) {

	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
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

function paintBuyHomePension(divId) {

	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
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

function paintBuyHomeOwner() {

	paintBuyHomeRenting();
}

function homeProgressBaar(num) {

	var count = 5;
	$("#homeProgressBaarId_" + num).removeClass('ce-lp-in-progress')
			.removeClass('ce-lp-complete').addClass('ce-lp-in-progress');
	$('#homeStepNoId_' + num).html(num);

	for (var i = 1; i <= num - 1; i++) {
		$("#homeProgressBaarId_" + i).removeClass('ce-lp-in-progress')
				.removeClass('ce-lp-not-started').addClass('ce-lp-complete');
		$('#homeStepNoId_' + i).html("");
	}
	for (var i = num + 1; i <= count; i++) {
		$("#homeProgressBaarId_" + i).removeClass('ce-lp-in-progress')
				.removeClass('ce-lp-complete').addClass('ce-lp-not-started');
		$('#homeStepNoId_' + i).html(i);
	}
}
