var active = 0;
var message = "Invalid Entry";
var buyHomeTeaserRate = new Object();
var purchaseDetails = new Object();
buyHomeTeaserRate.purchaseDetails = purchaseDetails;
//buyHomeTeaserRate.purchaseDetails=purchaseDetails;
var buyHomeitemsList = ["Loan Purpose", "Loan Amount", "Home Information",
    "Zip Code", "Programs and Rates", "Create Account"
];

function getBuyHomeLeftPanel() {
    var container = $('<div>').attr({
        "class": "ce-lp float-left"
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
        "class": "ce-lp-item clearfix",
        "data-step": stepNo,
        "id": "homeProgressBaarId_" + stepNo
    });
    var leftIcon = $('<div>').attr({
        "class": "ce-lp-item-icon float-left",
        "id": "homeStepNoId_" + stepNo
    });
    itemCont.addClass('ce-lp-not-started');
    leftIcon.html(stepNo);
    var textCont = $('<div>').attr({
        "class": "ce-lp-item-text float-left"
    }).html(itemTxt);
    return itemCont.append(leftIcon).append(textCont);
}

function paintBuyHomeContainer() {
    refinanceTeaserRate = {};
    buyHomeTeaserRate.loanType = "PUR";
    $('#ce-main-container').html('');
    var wrapper = $('<div>').attr({
        "class": "ce-refinance-wrapper clearfix"
    });
    var leftPanel = getBuyHomeLeftPanel();
    var stepContOnMobileScreen = $('<div>').attr({
    	"class" : "hide cust-eng-step-hdr"
    }).html("Step ").append("<span id='step-no'>1</span>").append(" of " + buyHomeitemsList.length);
    var centerPanel = $('<div>').attr({
        "id": "ce-refinance-cp",
        "class": "ce-cp float-left"
    });
    wrapper.append(leftPanel).append(stepContOnMobileScreen).append(centerPanel);
    $('#ce-main-container').append(wrapper);
    paintBuyHomeQuest();
}

function paintBuyHomeQuest() {
    homeProgressBaar(1);
    var quesText = "Living Situation";
    var options = [{
        "text": "I am currently renting",
        "onselect": paintRentOfYourHouse,
        "value": "renting"
    }, {
        "text": "I am a home owner",
        "onselect": saleYourCurrentHome,
        "value": "homeOwner"
    }];
    var quesCont = getBuyHomeMutipleChoiceQuestion(quesText, options, "livingSituation");
    $('#ce-refinance-cp').html(quesCont);
    /*
     * $("#progressBaarId_1").addClass('ce-lp-in-progress');
     * $('#stepNoId_1').html("1");
     */
}

function getBuyHomeMutipleChoiceQuestion(quesText, options, name) {
        var container = $('<div>').attr({
            "class": "ce-ques-wrapper"
        });
        var quesTextCont = $('<div>').attr({
            "class": "ce-rp-ques-text"
        }).html(quesText);
        var optionContainer = $('<div>').attr({
            "class": "ce-options-cont"
        });
        for (var i = 0; i < options.length; i++) {
            var option = $('<div>').attr({
                "class": "cep-button-color ce-option",
                "value": options[i].value
            }).html(options[i].text).bind('click', {
                "option": options[i],
                "name": name
            }, function(event) {
                var key = event.data.name;
                buyHomeTeaserRate[key] = event.data.option.value;
                event.data.option.onselect();
            });
            optionContainer.append(option);
        }
        return container.append(quesTextCont).append(optionContainer);
    }
    /*function paintBuyHomEachMonthrent() {
    active = 2;
    homeProgressBaar(3);
    var quesTxt1 = "How much do you pay each month for rent?";
    var quesTxt2 = "When did you start living here?";
    var quesTxt3 = "Where did you live earlier";
    var container = $('<div>').attr({
    "class" : "ce-ques-wrapper"
    });
    var quesTextCont1 = $('<div>').attr({
    "class" : "ce-rp-sub-ques-text"
    }).html(quesTxt1);
    var optionContainer1 = $('<div>').attr({
    "class" : "ce-options-cont"
    });
    var errFeild=appendErrorMessage();
    var inputBox1 = $('<input>').attr({
    "class" : "ce-input",
    "name" : "rentPerMonth"
    });
    var quesTextCont2 = $('<div>').attr({
    "class" : "ce-rp-sub-ques-text"
    }).html(quesTxt2);
    var optionContainer2 = $('<div>').attr({
    "class" : "ce-options-cont"
    });
    var inputBox2 = $('<input>').attr({
    "class" : "ce-input",
    "name" : "startLiving"
    }).blur(function(){
    if($('input[name="rentPerMonth"]').val() > 2){
    //alert("grater then 2");
    }
    });
    var quesTextCont3 = $('<div>').attr({
    "class" : "ce-rp-sub-ques-text"
    }).html(quesTxt3);
    var optionContainer3 = $('<div>').attr({
    "class" : "ce-options-cont"
    });
    var inputBox3 = $('<input>').attr({
    "class" : "ce-input",
    "name" : "liveEarlier"
    });
    optionContainer1.append(inputBox1).append(errFeild);
    optionContainer2.append(inputBox2).append(errFeild);
    //optionContainer3.append(inputBox3);
    quesTextCont1.append(optionContainer1);
    quesTextCont2.append(optionContainer2);
    //quesTextCont3.append(optionContainer3);
    var saveBtn = $('<div>').attr({
    "class" : "ce-save-btn"
    }).html("Save & Continue").bind(
    'click',
    function(event) {
    buyHomeTeaserRate["rentPerMonth"] = $('input[name="rentPerMonth"]').val();
    buyHomeTeaserRate["startLiving"] = $('input[name="startLiving"]').val();
    buyHomeTeaserRate["liveEarlier"] = $('input[name="liveEarlier"]').val();
    var questionOne=validateInput($('input[name="rentPerMonth"]').val(),message);
    alert(questionOne);
    if(questionOne){
    paintPlanToBuyYourHouse();
    }else{
    return false;
    }
    // paintBuyHomeStartMilitaryloans();
    });
    if (buyHomeTeaserRate["livingSituation"] == 0) {
    $('#ce-refinance-cp').html(container.append(quesTextCont1).append(saveBtn));
    } else {
    var quesTextCont4 = paintBuyHomeSellHome();
    $('#ce-refinance-cp').html(
    container.append(
    quesTextCont4).append(saveBtn));
    }
    }*/
    /*function paintPlanToBuyYourHouse() {
    var quesTxt = "Where do you plan to buy your house.";
    var quesCont = getBuyHomeTextQuestion(quesTxt,
    paintBuyHomeStartMilitaryloans, "planToBuyYourHouseZip");
    $('#ce-refinance-cp').html(quesCont);
    }*/
function paintRentOfYourHouse() {
    active = 2;
    homeProgressBaar(2);
    buyHomeTeaserRate.purchaseDetails.livingSituation = 'renting';
    var quesTxt = "How much do you pay each month for rent?";
    var quesCont = getBuyHomeTextQuestion(quesTxt, paintloanamount, "rentPerMonth");
    $('#ce-refinance-cp').html(quesCont);
}

function paintloanamount() {
    $('#ce-refinance-cp').html("");
    quesContxts = [];
    var questions = [{
            "type": "desc",
            "text": "Desired purchase price?",
            "name": "homeWorthToday",
            "value": ""
        }, {
            "type": "dwnPayment",
            "text": "Down payment?",
            "name": "currentMortgageBalance",
            "value": ""
        }
        /*,
        {
        "type": "yesno",
        "text": "Do you prefer to include Taxes and Insurance as part of your loan payment? ",
        "name": "isIncludeTaxes",
        "options": [
        {
        "text": "Yes",
        "addQuestions": [
        {
        "type": "desc",
        "text": "What is the estimated purchase price?",
        "name": "estimatedPurchasePrice",
        "value": ""
        }
        ]
        },
        {
        "text": "No"
        }
        ]
        }*/
    ];
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var contxt = getQuestionContext(question, $('#ce-refinance-cp'), buyHomeTeaserRate);
        contxt.drawQuestion();
        quesContxts.push(contxt);
    }
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color ce-save-btn"
    }).html("Save & continue").on('click', function() {
        buyHomeTeaserRate.homeWorthToday = $('input[name="homeWorthToday"]').val();
        buyHomeTeaserRate.currentMortgageBalance = $('input[name="currentMortgageBalance"]').val();

        buyHomeTeaserRate.purchaseDetails.housePrice = $('input[name="homeWorthToday"]').val();
        buyHomeTeaserRate.purchaseDetails.loanAmount = (getFloatValue($('input[name="homeWorthToday"]').val()) -getFloatValue($('input[name="currentMortgageBalance"]').val()));
 
        var questionOne=validateInput($('input[name="homeWorthToday"]'),$('input[name="homeWorthToday"]').val(),message);
        var questionTwo=validateInput($('input[name="currentMortgageBalance"]'),$('input[name="currentMortgageBalance"]').val(),message);
  	 

        if (questionOne && questionTwo) {
        	
        		if (validateDownPaymentOrPurchasePrice($('input[name="homeWorthToday"]'), $('input[name="currentMortgageBalance"]')))
              	{
              		paintNewResidenceTypeQues();
              	} 
        	  }else{
        		  return false;
        	  }
        	
       
       
    });
    $('#ce-refinance-cp').append(saveAndContinueButton);
    //$('#ce-refinance-cp').html(quesCont);
}
function validateDownPaymentOrPurchasePrice (purchasePriceElement, downPaymentElement)
{
	 var purchasePrice = purchasePriceElement.val();
	 var downPayment = downPaymentElement.val();
	if((getFloatValue(downPayment) < (0.03* getFloatValue(purchasePrice)))){
		console.log("Eror");
		downPaymentElement.parent().find('.err-msg').html(downpaymentThreePerLessThanPurchase).show();
		downPaymentElement.addClass('ce-err-input').show();		
		return false;
	}
	if((getFloatValue(downPayment))>(getFloatValue(purchasePrice))){
		downPaymentElement.parent().find('.err-msg').html(downpaymentGreaterThanPurchase).show();
		downPaymentElement.addClass('ce-err-input').show();		
		return false;
	}
	return true;
	
}
function paintBuyHomeAnnualPropertyTaxes() {
        var quesTxt = "Approximately what is your home worth today?";
        var quesCont = getTextQuestion(quesTxt, paintHomeZipCode, "annualPropertyTaxes");
        $('#ce-refinance-cp').html(quesCont);
    }
    /*function paintBuyHomeSellHome() {
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
    "class" : "ce-rp-sub-ques-text"
    }).html(quesText);
    var optionContainer = $('<div>').attr({
    "class" : "ce-options-cont"
    });
    for (var i = 0; i < options.length; i++) {
    var option = $('<div>').attr({
    "class" : "ce--sub-option",
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
    */
function saleYourCurrentHome() {
    buyHomeTeaserRate.purchaseDetails.livingSituation = 'homeOwner';
    active = 2;
    homeProgressBaar(2);
    paintloanamount();
}

function paintHomeZipCode() {
    active = 4;
    homeProgressBaar(4);
    var quesTxt = "Zip Code";
    var quesCont = getBuyHomeTextQuestion(quesTxt, paintBuyHomeSeeTeaserRate, "zipCode");
    $('#ce-refinance-cp').html(quesCont);
}



/*function callRestAPi(){
	
	$.ajax({
		
		url:"http://localhost:8080/NewfiWeb/rest/teaseRate/marketingTeaseRate",
		type:"GET",
		success:function(data){
			 alert(data);
		},
		error:function(){}
		
	});
	
}*/

function paintBuyHomeSeeTeaserRate(parentContainer, teaserRateData, hideCreateAccountBtn) {
    
	//callRestAPi();
	stages = 5;
    homeProgressBaar(5);
    if (!parentContainer) {
        parentContainer = $('#ce-refinance-cp');
    }
    if (!teaserRateData) {
        teaserRateData = buyHomeTeaserRate;
    }
    delete sessionStorage.refinaceData;
    showOverleyMessage(overlayMessage);
    showOverlay();
    $.ajax({
        url: "rest/calculator/findteaseratevalue",
        type: "POST",
        data: {
            "teaseRate": JSON.stringify(teaserRateData)
        },
        datatype: "application/json",
        cache:false,
        success: function(data) {
        	hideOverlay();
            clearOverlayMessage();
            if((data.error||data==""||data=="error")&&typeof(newfiObject)==='undefined'){
               // var quesTxt = "Let us Contact You";
                var container = $('<div>').attr({
                    "class": "ce-rate-main-container"
                });
               /* var quesTextCont = $('<div>').attr({
                    "class": "ce-rp-ques-text letUsContactCenter"
                }).html(quesTxt);*/
                // alert(JSON.stringify(refinanceTeaserRate));
                //container.append(quesTextCont);
                $(parentContainer).html(container);
                var errorText=getNoProductMessageInLockRatePage();
                if(typeof(newfiObject)==='undefined')
                {
                	teaserRateValHolder.leadCustomer=true;
                }
                var mainContainer = paintApplyNow(teaserRateData,undefined,true);
                var createAccBtn= $('<div>').attr({
                    "class": "rate-btn createAccButton"
                }).html("Provide your contact information").on('click', function() {
                    var mainContainer = paintApplyNow(teaserRateData);
                    $('#ce-main-container').html(mainContainer);
                });
                $(parentContainer).append(errorText);
                if(typeof(newfiObject)==='undefined')
                    $(parentContainer).append(mainContainer);
                return
            }
            // var teaserRate = data;
            // paintteaserRate(data);
            //paintFixYourRatePageCEP(JSON.parse(data), refinanceTeaserRate);
            var quesTxt = "Programs and Rates";
            var container = $('<div>').attr({
                "class": "ce-rate-main-container"
            });
            var quesTextCont = $('<div>').attr({
                "class": "ce-rp-ques-text"
            }).html(quesTxt);
            // alert(JSON.stringify(refinanceTeaserRate));
            container.append(quesTextCont);
            $(parentContainer).html(container);
            var ob;
            try{
                ob=JSON.parse(data);
            }catch(exception){
                ob={};
                console.log("Invalid Data");
            }
            paintFixYourRatePageCEP(ob, teaserRateData, parentContainer, hideCreateAccountBtn);
            clearOverlayMessage();
        },
        error: function() {
        	showErrorToastMessage("error");
            hideOverlay();
        }
    });
}
var tenureYear = [];
var rateObjArray = [];
var sortedTenureYear = [];
var year;
var index;
var unsortTenureYear = [];
var loanDurationConform;
var rateVOArrayObj;

function paintteaserRate(teaserRate) {
    //teaserRate = JSON.parse(teaserRate);
    for (var i in teaserRate) {
        loanDurationConform = teaserRate[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
        }
        tenureYear.push(parseInt(year));
        rateObjArray.push(teaserRate[i].rateVO);
    }
    for (var i = 0; i < tenureYear.length; i++) {
        unsortTenureYear[i] = tenureYear[i];
    }
    sortedTenureYear = sortYear(tenureYear);
    tenureSlider(sortedTenureYear);
    index = unsortTenureYear.indexOf(sortedTenureYear[sortedTenureYear.length - 1]);
    rateVOArrayObj = rateObjArray[index];
    $("#teaserRateId").html(rateVOArrayObj[0].teaserRate + " %");
    $("#closingCostId").html(rateVOArrayObj[0].closingCost + " ");
    rateCostSlider(rateVOArrayObj);
}

function sortYear(tenureYear) {
    var sortedTenureYear = [];
    sortedTenureYear = tenureYear;
    sortedTenureYear.sort(function(a, b) {
        return a - b;
    });
    return sortedTenureYear;
}

function tenureSlider(sortedTenureYear) {
    $('.tenure-grid-container').remove();
    var grids = getTenureSliderGrids(sortedTenureYear);
    $('#tenure-slider').parent().append(grids);
    console.log(sortedTenureYear);
    $("#tenure-slider").slider({
        value: sortedTenureYear[sortedTenureYear.length - 1],
        min: 0,
        max: sortedTenureYear.length - 1,
        slide: function(event, ui) {
            //$("#amount").val(sortedTenureYear[ui.value] + "Year");
        },
        change: function(event, ui) {
            $("#rate-slider").slider("destroy");
            $('#years-text').html(sortedTenureYear[ui.value]);
            tenureYearDate = sortedTenureYear[ui.value];
            index = unsortTenureYear.indexOf(tenureYearDate);
            var rateVOArrayObj = rateObjArray[index];
            console.log("rateVOArrayObjLength.." + rateVOArrayObj.length);
            // Rate slider change
            rateCostSlider(rateVOArrayObj);
        },
        create: function(event, ui) {
            console.log("slider is created...");
            tenureYearDate = sortedTenureYear[ui.value];
            console.log("tenureYearDate..." + tenureYearDate);
            index = unsortTenureYear.indexOf(tenureYearDate);
            console.log("tenureYearDate..." + tenureYearDate);
            var rateVOArrayObj = rateObjArray[index];
            console.log("tenureYearDate..." + tenureYearDate);
            console.log("rateVOArrayObjLength.." + rateVOArrayObj.length);
            rateCostSlider(rateVOArrayObj);
        }
    });
}

function rateCostSlider(rateVOArrayObj) {
    index = parseInt(rateVOArrayObj.length / 2);
    medienRateCostObj = rateVOArrayObj[index];
    console.log("index... " + index);
    console.log("medienRateCostObj... " + medienRateCostObj);
    $('.rate-slider').find('.tenure-grid-container').remove();
    var grids = getRateCostSliderGrids(rateVOArrayObj);
    $('#rate-slider').parent().append(grids);
    $("#rate-slider").slider({
        value: index,
        min: 0,
        max: rateVOArrayObj.length - 1,
        change: function(event, ui) {
            $(".cp-rate-btn").html(rateVOArrayObj[ui.value].teaserRate + " %");
            $(".cp-est-cost-btn").html(rateVOArrayObj[ui.value].closingCost + " ");
        },
        create: function(event, ui) {
            console.log("--------yo yo--------");
            $(".cp-rate-btn").html(rateVOArrayObj[index].teaserRate + " %");
            $(".cp-est-cost-btn").html(rateVOArrayObj[index].closingCost + " ");
        }
    });
    $("#amount").val(sortedTenureYear[$("#tenure-slider").slider("value")] + "Years");
}

function getBuyHomeMultiTextQuestion(quesText) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper",
    });
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text",
    }).html(quesText);
    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont",
    });
    var quesTextCont1 = $('<div>').attr({
        "class": "ce-rp-ques-text",
    }).html("Monthly Income Before Taxes");
    var errFeild = appendErrorMessage();
    var inputBox1 = $('<input>').attr({
        "class": "ce-input",
        "name": "beforeTax",
    });
    quesTextCont1.append(inputBox1).append(errFeild);
    var quesTextCont2 = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html("Employer");
    var inputBox2 = $('<input>').attr({
        "class": "ce-input",
        "name": "workPlace"
    });
    quesTextCont2.append(inputBox2).append(errFeild);
    var quesTextCont3 = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html("When Did You Start Wokring ?");
    var inputBox3 = $('<input>').attr({
        "class": "ce-input",
        "name": "startWorking"
    });
    quesTextCont3.append(inputBox3).append(errFeild);
    optionContainer.append(quesTextCont1).append(quesTextCont2).append(
        quesTextCont3);
    return container.append(quesTextCont).append(optionContainer);
}

function getBuyHomeTextQuestion(quesText, clickEvent, name) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(quesText);
    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont"
    });
    var errFeild = appendErrorMessage();
    var inputBox = $('<input>').attr({
        "class": "ce-input",
        "name": name,
        "value": buyHomeTeaserRate[name]
    }).on("load keydown", function(e) {
        if (name != 'zipCode') {
            $('input[name=' + name + ']').maskMoney({
                thousands: ',',
                decimal: '.',
                allowZero: true,
                prefix: '$',
                precision: 0,
                allowNegative: false
            });
        }
    });
    optionContainer.append(inputBox).append(errFeild);
    var saveBtn = $('<div>').attr({
        "class": "cep-button-color ce-save-btn"
    }).html("Save & Continue").bind('click', {
        'clickEvent': clickEvent,
        "name": name
    }, function(event) {
        var key = event.data.name;
        buyHomeTeaserRate[key] = $('input[name="' + key + '"]').val();
        buyHomeTeaserRate.purchaseDetails[key] = $('input[name="' + key + '"]').val();
        var className=$('input[name="' + key + '"]');
       
        if ("zipCode"== key) {
            var isSuccess = validateInput(className,$('input[name="zipCode"]').val(), zipCodeMessage);
       
            if (isSuccess) {
                if ($('input[name="zipCode"]').val().length > 5 || $('input[name="zipCode"]').val().length < 5) {
                	 $('input[name="' + key + '"]').next('.err-msg').html(zipCodeMessage).show();
            		 $('input[name="' + key + '"]').addClass('ce-err-input').show();
                    return false;
                } else {
                	var callback=event.data.clickEvent;
                    ajaxRequest("rest/states/zipCode", "GET", "json", {"zipCode":$('input[name="zipCode"]').val()}, function(response) {
                        if (response.error) {
                            showToastMessage(response.error.message)
                        } else {
                            if(response.resultObject==true){
                                callback();    
                            }else{
                                 $('input[name="' + key + '"]').next('.err-msg').html(invalidStateZipCode).show();
                                 $('input[name="' + key + '"]').addClass('ce-err-input').show();
                            }
                        }
                    });
                }
            } else {
                return false;
            }
        } else {
        	
            var isSuccess = validateInput(className,$('input[name="' + key + '"]').val(), message);
            if (isSuccess) {
                event.data.clickEvent();
            } else {
                return false;
            }
        }
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
	var quesTxt = "Monthly income";

	var wrapper = $('<div>');
	
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});
	var errFeild=appendErrorMessage();
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : "selfEmployed"
	});

	optionContainer.append(inputBox).append(errFeild);
	container.append(quesTextCont).append(optionContainer);
	
	var quesTxt1 = "Number of years";

	var container1 = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt1);

	var optionContainer1 = $('<div>').attr({
		"class" : "ce-options-cont"
	});
	var errFeild1=appendErrorMessage();
	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : ""
	});

	optionContainer1.append(inputBox1).append(errFeild1);
	container1.append(quesTextCont1).append(optionContainer1);

	wrapper.append(container).append(container1);
	
	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(wrapper);
}

function paintbuyHomeDisability(divId) {
    var quesTxt = "About how much do you get monthly?";
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(quesTxt);
    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont"
    });
    var errFeild = appendErrorMessage();
    var inputBox = $('<input>').attr({
        "class": "ce-input",
        "name": "disability"
    });
    optionContainer.append(inputBox).append(errFeild);
    container.append(quesTextCont).append(optionContainer);
    $('#ce-option_' + divId).toggle();
    $('#ce-option_' + divId).html(container);
}

function paintBuyHomePension(divId) {
    var quesTxt = "About how much do you get monthly?";
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(quesTxt);
    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont"
    });
    var errFeild = appendErrorMessage();
    var inputBox = $('<input>').attr({
        "class": "ce-input",
        "name": "pension"
    });
    optionContainer.append(inputBox).append(errFeild);
    container.append(quesTextCont).append(optionContainer);
    $('#ce-option_' + divId).toggle();
    $('#ce-option_' + divId).html(container);
}

function paintBuyHomeOwner() {
    paintBuyHomeRenting();
}

function homeProgressBaar(num) {
	scrollToTop();
	adjustCustomerApplicationPageOnResize();
	adjustCustomerEngagementPageOnResize();
    var count = buyHomeitemsList.length;
    $('#step-no').text(num);
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
    if(typeof(newfiObject)==='undefined')
        saveState(undefined, undefined, undefined,(num-1));
    active=num;
    sessionStorage.refinaceData = JSON.stringify(buyHomeTeaserRate);
}

function getMonthYearTextQuestion(quesText, clickEvent, name) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(quesText);
    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont"
    });
    var monthDropDown = $('<select>').attr({
        "class": "ce-input width-75",
        "name": name,
        "id": "monthId"
    });
    for (var i = 1; i <= 12; i++) {
        var option = $("<option>").attr({}).html(i);
        monthDropDown.append(option);
    }
    var yearInput = $('<input>').attr({
        "class": "ce-input width-150",
        "name": name,
        "value": buyHomeTeaserRate[name],
        "placeholder": "YYYY"
    });
    optionContainer.append(monthDropDown).append(yearInput);
    var saveBtn = $('<div>').attr({
        "class": "cep-button-color ce-save-btn"
    }).html("Save & Continue").bind('click', {
        'clickEvent': clickEvent,
        "name": name
    }, function(event) {
        var key = event.data.name;
        buyHomeTeaserRate[key] = $('input[name="' + key + '"]').val();
        sessionStorage.refinaceData = JSON.stringify(buyHomeTeaserRate);
        event.data.clickEvent();
    });
    return container.append(quesTextCont).append(optionContainer).append(
        saveBtn);
}