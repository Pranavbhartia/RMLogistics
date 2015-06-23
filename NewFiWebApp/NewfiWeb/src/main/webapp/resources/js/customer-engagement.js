//JavaScript functions for customer engagement pages
var isNoProductFound=false;
$(document).on('mouseover','.app-option-choice',function(){
	$(this).parent().find('.app-option-choice').removeClass('choice-hover');
	$(this).addClass('choice-hover');
});
$(document).on('mouseleave','.app-option-choice',function(){
	$(this).removeClass('choice-hover');
});

function getQuestionContextCEP(question, parentContainer) {
    var contxt = {
        type: question.type,
        text: question.text,
        name: question.name,
        options: question.options,
        container: undefined,
        childContainers: {},
        childContexts: {},
        value: question.selected,
        parentContainer: parentContainer,
        drawQuestion: function(callback) {
            var ob = this;
            if (ob.type == "mcq") {
                ob.container = getApplicationMultipleChoiceQues(ob);
            } else if (ob.type == "desc") {
                ob.container = getContextApplicationTextQuesCEP(ob);
            } else if (ob.type == "select") {
                ob.container = getContextApplicationSelectQues(ob);
            } else if (ob.type == "yesno") {
                ob.container = getContextApplicationYesNoQuesCEP(ob);
            }else if (ob.type == "yearMonth") {
                ob.container = getContextApplicationYearMonthCEP(ob);
            }
            // parentContainer.append(ob.container);
        },
        deleteContainer: function(callback) {},
        clickHandler: function(newValue, callback) {
            var ob = this;
            if (ob.value != "" && ob.value != newValue) {
                if (ob.type == "yesno") {
                    for (var key in ob.childContainers) {
                        var container = ob.childContainers[key];
                        container.remove();
                    }
                    ob.childContainers = {};
                    ob.childContexts = {};
                }
            }
        },
        clearChildren: function(callback) {},
        drawChildQuestions: function(option, questions) {
            var ob = this;
            var childContainer = $('<div>');
            ob.childContainers[option] = childContainer;
            ob.childContexts[option] = [];
            for (var i = 0; i < questions.length; i++) {
                var question = questions[i];
                var contxt = getQuestionContextCEP(question, childContainer, ob.valueSet);
                contxt.drawQuestion();
                ob.childContexts[option].push(contxt);
            }
            ob.container.after(childContainer);
        },
        changeHandler: function(newValue, callback) {
            var ob = this;
            if (ob.value != "" && ob.value != newValue) {
                if (ob.type == "yesno") {
                    for (var key in ob.childContainers) {
                        var container = ob.childContainers[key];
                        container.remove();
                    }
                    ob.childContainers = {};
                    ob.childContexts = {};
                }
            }
        },
        mapValues: function(value) {
            if (value == "Yes" || value == true) {
                return "Yes";
            } else if (value == "No" || value == false) {
                return "No";
            } else return value;
        }
    };
    /*
     * if(valueSet){ for(key in valueSet){ if(key==contxt.name){
     * contxt.value=contxt.mapValues(valueSet[key]); break; } } }
     */
    return contxt;
}

function getContextApplicationTextQuesCEP(contxt) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    contxt.container = container;
    contxt.parentContainer.append(contxt.container);
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(contxt.text);
    var optionsContainer = $('<div>').attr({
        "class": "ce-options-cont"
    });
    

    var errFeild=appendErrorMessage();
    var optionCont = $('<input>').attr({
        "class": "ce-input",
        "name": contxt.name,
        "value": contxt.value
    }).bind("change", {
        "contxt": contxt
    }, function(event) {
        var ctx = event.data.contxt;
        ctx.value = $(this).val();
    }).on("load keydown", function(e) {
        if (name != 'zipCode' && name != 'yearLeftOnMortgage') {
            $('input[name=' + contxt.name + ']').maskMoney({
                thousands: ',',
                decimal: '.',
                allowZero: true,
                prefix: '$',
                precision: 0,
                allowNegative: false
            });
        }
        
        Math.abs($('input[name=' + contxt.name + ']').val());
    });
    if (contxt.value != undefined) {
        optionCont.val(contxt.value);
    }
    optionsContainer.append(optionCont).append(errFeild);
    return container.append(quesTextCont).append(optionsContainer);
}

function getContextApplicationYesNoQuesCEP(contxt) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    contxt.container = container;
    contxt.parentContainer.append(contxt.container);
   
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(contxt.text);

    var errFeild=appendErrorMessage();
    var optionsContainer = $('<div>').attr({
        "class": "ce-options-cont",
        "name": contxt.name
    });
    for (var i = 0; i < contxt.options.length; i++) {
        var option = contxt.options[i];
        var sel = "false";
        if (contxt.value == option.text) sel = "true";
        var optionCont = $('<div>').attr({
            "class": "cep-button-color app-option-choice",
            "isSelected": sel
        }).html(option.text);
        optionCont.bind("click", {
            "contxt": contxt,
            "value": option.text,
            "option": option
        }, function(event) {
            var ctx = event.data.contxt;
            var opt = event.data.option;
            var val = event.data.value;
            optionClicked($(this), ctx, opt, val);
        });
        optionsContainer.append(optionCont).append(errFeild);
        if (contxt.value == option.text) {
            optionClicked(optionCont, contxt, option, option.text, true);
        }
    }
    return container.append(quesTextCont).append(optionsContainer);
}


function getContextApplicationYearMonthCEP(contxt) {
   
	 var container = $('<div>').attr({
	        "class": "ce-ques-wrapper"
	    });
	    contxt.container = container;
	    contxt.parentContainer.append(contxt.container);
	    var quesTextCont = $('<div>').attr({
	        "class": "ce-rp-ques-text"
	    }).html(contxt.text);
	    var optionsContainer = $('<div>').attr({
	        "class": "ce-options-cont clearfix"
	    });
	    
	    
	    var optionCont = $('<input>').attr({
	        "class": "ce-input",
	        "name": contxt.name,
	        "value": contxt.value
	    }).bind("change", {
	        "contxt": contxt
	    }, function(event) {
	        var ctx = event.data.contxt;
	        ctx.value = $(this).val();
	    }).on("load keydown", function(e) {
	        if (name != 'zipCode' && name != 'yearLeftOnMortgage') {
	            $('input[name=' + contxt.name + ']').maskMoney({
	                thousands: ',',
	                decimal: '.',
	                allowZero: true,
	                prefix: '$',
	                precision: 0,
	                allowNegative: false
	            });
	        }
	        
	        Math.abs($('input[name=' + contxt.name + ']').val());
	    });
	    
	    
	    var requird = $('<div>').attr({
	        "class": "per",
	    }).html("pe42342r");
	      
	    
	    var selectedOption = $('<div>').attr({
	        "class": "month-cont float-left"
	    }).html("Select One").on('click', function() {
	        $(this).parent().find('.app-dropdown-cont').toggle();
	    });
	    
	 
	    
	    if (contxt.value != undefined) {
	        optionCont.val(contxt.value);
	    }
	    optionsContainer.append(optionCont).append(requird).append(selectedOption);
	    return container.append(quesTextCont).append(optionsContainer);
}


function optionClicked(element, ctx, option, value, skipCondition) {
    $(element).parent().find('.app-option-choice').attr("isSelected", "false").mouseover().mouseleave();
    $(element).attr("isSelected", "true");
    ctx.clickHandler(value);
    if (ctx.value != value || skipCondition) {
        ctx.value = value;
        var opt = option;
        if (opt.addQuestions) {
            ctx.drawChildQuestions(ctx.value, opt.addQuestions);
        }
    }
}
var teaserRate = [{
    "loanDuration": "15 YR FIXED CONFORMING",
    "rateVO": [{
        "teaserRate": "3.000",
        "closingCost": "0",
        "APR": "1"
    }, {
        "teaserRate": "2.875",
        "closingCost": "$1,782.62",
        "APR": "2"
    }, {
        "teaserRate": "2.750",
        "closingCost": "$3,512.43",
        "APR": "3"
    }]
}, {
    "loanDuration": "20 YR FIXED CONFORMING",
    "rateVO": [{
        "teaserRate": "3.625",
        "closingCost": "0",
        "APR": "1"
    }, {
        "teaserRate": "3.500",
        "closingCost": "$1,155.53",
        "APR": "2"
    }, {
        "teaserRate": "3.375",
        "closingCost": "$3,658.15",
        "APR": "3"
    }, {
        "teaserRate": "3.250",
        "closingCost": "$6,166.37",
        "APR": "4"
    }]
}, {
    "loanDuration": "30 YR FIXED CONFORMING",
    "rateVO": [{
        "teaserRate": "3.875",
        "closingCost": "0",
        "APR": "1"
    }, {
        "teaserRate": "3.750",
        "closingCost": "$493.10",
        "APR": "2"
    }, {
        "teaserRate": "3.625",
        "closingCost": "$2,872.52",
        "APR": "3"
    }, {
        "teaserRate": "3.500",
        "closingCost": "$5,660.73",
        "APR": "4"
    }]
}, {
    "loanDuration": "5/1 1 YR LIBOR CONFORMING  2/2/5 30 YR ARM",
    "rateVO": [{
        "teaserRate": "3.125",
        "closingCost": "0",
        "APR": "1"
    }, {
        "teaserRate": "3.000",
        "closingCost": "$425.20",
        "APR": "2"
    }, {
        "teaserRate": "2.875",
        "closingCost": "$1,443.82",
        "APR": "3"
    }, {
        "teaserRate": "2.750",
        "closingCost": "$2,456.83",
        "APR": "4"
    }, {
        "teaserRate": "2.625",
        "closingCost": "$3,472.65",
        "APR": "5"
    }, {
        "teaserRate": "2.500",
        "closingCost": "$4,796.47",
        "APR": "6"
    }]
}, {
    "loanDuration": "7/1 1 YR LIBOR CONFORMING  5/2/5 30 YR ARM",
    "rateVO": [{
        "teaserRate": "3.250",
        "closingCost": "0",
        "APR": "1"
    }, {
        "teaserRate": "3.125",
        "closingCost": "$347.38",
        "APR": "2"
    }, {
        "teaserRate": "3.000",
        "closingCost": "$1,643.20",
        "APR": "3"
    }, {
        "teaserRate": "2.875",
        "closingCost": "$2,950.22",
        "APR": "4"
    }, {
        "teaserRate": "2.750",
        "closingCost": "$4,262.83",
        "APR": "5"
    }, {
        "teaserRate": "2.625",
        "closingCost": "$5,569.85",
        "APR": "6"
    }]
}];


var refinanceTeaserRate = new Object();
if (sessionStorage.refinaceData) {
    refinanceTeaserRate = JSON.parse(sessionStorage.refinaceData);
}
var stages = 0;

function paintSelectLoanTypeQuestion() {
    stages = 0;
    $('#ce-main-container').html('');
    var rateIcon = $('<div>').attr({
        "class": "ce-rate-icon"
    });
    var titleText = $('<div>').attr({
        "class": "ce-title-firstPage"
    }).html("a New way to Finance your home");
    
    var getStartedTxt = $('<div>').attr({
    	"class" : "ce-get-started"
    }).html("Get Started");
    
    $('#ce-main-container').append(rateIcon).append(titleText).append(getStartedTxt);
   
    var wrapper = $('<div>').attr({
        "class": "ce-ques-wrapper_firstPage"
    });
    var optionsContainer = $('<div>').attr({
        "class": "ce-ques-options-container clearfix"
    });
    var option1 = $('<div>').attr({
        "class": "cep-button-color ce-ques-option float-left"
    }).html("Refinance").on('click', function() {
        paintRefinanceMainContainer();
    });
    var option2 = $('<div>').attr({
        "class": "ce-ques-option float-left cep-button-color"
    }).html("Buy a home").on('click', function() {
        paintBuyHomeContainer();
    });
    optionsContainer.append(option1).append(option2);
   /* var question = $('<div>').attr({
        "class": "ce-ques-text"
    }).html("Newfi difference #1: we empower you with advanced tools to save time and money!");*/
   // wrapper.append(optionsContainer).append(question);
    wrapper.append(optionsContainer);
    $('#ce-main-container').append(wrapper);
}
function paintRefinanceMainContainer() {
    
	buyHomeTeaserRate = {};
	refinanceTeaserRate.loanType = "REF";
    $('#ce-main-container').html('');
    var wrapper = $('<div>').attr({
        "class": "ce-refinance-wrapper clearfix"
    });
    var leftPanel = getRefinanceLeftPanel();
    var stepContOnMobileScreen = $('<div>').attr({
    	"class" : "hide cust-eng-step-hdr"
    }).html("Step ").append("<span id='step-no'>1</span>").append(" of " + itemsList.length);
    var centerPanel = $('<div>').attr({
        "id": "ce-refinance-cp",
        "class": "ce-cp float-left"
    });
    wrapper.append(leftPanel).append(stepContOnMobileScreen).append(centerPanel);
    $('#ce-main-container').append(wrapper);
    paintRefinanceQuest1();
}
var itemsList = ["Loan Purpose", "Mortgage Balance", "Monthly Payment", "Home Value","Home Information", "Zip Code", "Programs and Rates", "Create Account"];

function getRefinanceLeftPanel() {
    var container = $('<div>').attr({
        "class": "ce-lp float-left"
    });
    for (var i = 0; i < itemsList.length; i++) {
        var itemCompletionStage = "NOT_STARTED";
        var itemCont = getRefinanceLeftPanelItem(itemsList[i], i + 1, itemCompletionStage);
        container.append(itemCont);
    }
    return container;
}
function switchBasedOnStage(stage){
    var element;
    if(buyHomeTeaserRate.loanType){
        element=$("#homeProgressBaarId_"+stage);
    }else if(refinanceTeaserRate.loanType){
        element=$("#progressBaarId_"+stage)
    }
    if(element){
        $(element).trigger( "click" );
    }
}
function paintRatesPageFromCrumb(){
    if(refinanceTeaserRate&&refinanceTeaserRate.loanType){
        paintRefinanceSeeRates();
    }else{
        paintBuyHomeSeeTeaserRate();
    }
}

function getRefinanceLeftPanelItem(itemTxt, stepNo, itemCompletionStage) {
    var itemCont = $('<div>').attr({
        "class": "ce-lp-item clearfix",
        "data-step": stepNo,
        "id": "progressBaarId_" + stepNo
    });
    var leftIcon = $('<div>').attr({
        "class": "ce-lp-item-icon float-left",
        "id": "stepNoId_" + stepNo
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
        "class": "ce-lp-item-text float-left"
    }).html(itemTxt);
    return itemCont.append(leftIcon).append(textCont);
}

function paintRefinanceQuest1() {
    stages = 1;
    progressBaar(1);
    var quesText = "Why do you want to refinance?";
    var options = [{
        "text": "Lower my monthly payment",
        "onselect": paintRefinanceStep2,
        "value": "REFLMP"
    }, {
        "text": "Pay off mortgage faster",
        "onselect": paintRefinanceStep1a,
        "value": "REFMF"
    }, {
        "text": "Take cash out",
        "onselect": paintRefinanceStep1b,
        "value": "REFCO"
    }];
    var quesCont = getMutipleChoiceQuestion(quesText, options, "refinanceOption");
    $('#ce-refinance-cp').html(quesCont);
    /*
     * $("#progressBaarId_1").addClass('ce-lp-in-progress');
     * $('#stepNoId_1').html("1");
     */
}

function getMutipleChoiceQuestion(quesText, options, name) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(quesText);
    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont"
    });
    var errFeild=appendErrorMessage();
    for (var i = 0; i < options.length; i++) {
        var option = $('<div>').attr({
            "class": "cep-button-color ce-option",
            "value": options[i].value
        }).html(options[i].text).bind('click', {
            "option": options[i],
            "name": name
        }, function(event) {
            var key = event.data.name;
            refinanceTeaserRate[key] = event.data.option.value;
            event.data.option.onselect();
        });
        optionContainer.append(option).append(errFeild);
    }
    return container.append(quesTextCont).append(optionContainer);
}

function getTextQuestion(quesText, clickEvent, name) {
        var container = $('<div>').attr({
            "class": "ce-ques-wrapper"
        });
        var quesTextCont = $('<div>').attr({
            "class": "ce-rp-ques-text"
        }).html(quesText);
        var optionContainer = $('<div>').attr({
            "class": "ce-options-cont"
        });
        var errFeild=appendErrorMessage();
        var inputBox = $('<input>').attr({
            "class": "ce-input",
            "name": name,
            "value": refinanceTeaserRate[name]
        }).on("load keydown", function(e) {
            if (name != 'zipCode' && name != 'yearLeftOnMortgage') {
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
        $(inputBox).val(refinanceTeaserRate[name]);
        
        
        optionContainer.append(inputBox).append(errFeild);
        var saveBtn = $('<div>').attr({
            "class": "cep-button-color ce-save-btn"
        }).html("Save & Continue").bind('click', {
            'clickEvent': clickEvent,
            "name": name
        }, function(event) {
        	removeToastMessage();
            var key = event.data.name;
            inputValue = $('input[name="' + key + '"]').val();
            refinanceTeaserRate[key] = inputValue;
            var classname=$('input[name="' + key + '"]');
           
            if($('input[name="zipCode"]').val()==inputValue){
            	 var isSuccess=validateInput(classname,inputValue,zipCodeMessage);
                 if(isSuccess){
                	 if(inputValue.length >5 ||inputValue.length < 5){

                		 $('input[name="' + key + '"]').next('.err-msg').html(zipCodeMessage).show();
                		 $('input[name="' + key + '"]').addClass('ce-err-input').show();
                		 return false;
                	 }else{
                        var callback=event.data.clickEvent;
                        ajaxRequest("rest/states/zipCode", "GET", "json", {"zipCode":inputValue}, function(response) {
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
                 	
                 }else{
                 	return false;
                 }
            }else{
            	 var isSuccess=validateInput(classname,inputValue,message);
                 if(isSuccess){
                 	event.data.clickEvent();
                 }else{
                 	return false;
                 }
            }
          
        });
        return container.append(quesTextCont).append(optionContainer).append(saveBtn);
    }
    


function paintRefinanceStep1a() {
    var quesTxt = "How many years are left on your mortgage?";
    var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2, "yearLeftOnMortgage");
    $('#ce-refinance-cp').html(quesCont);
}

var quesContxts = [];

function paintRefinanceStep2() {
    quesContxts = [];
    stages = 2;
    progressBaar(2);
    $('#ce-refinance-cp').html('');
    var questions = [{
        "type": "desc",
        "text": "What is your current mortgage balance?",
        "name": "currentMortgageBalance",
        "value": ""
    }];
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var contxt = getQuestionContext(question, $('#ce-refinance-cp'),refinanceTeaserRate);
        contxt.drawQuestion();
        quesContxts.push(contxt);
    }
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color ce-save-btn"
    }).html("Save & continue").bind('click', {
        'contxt': contxt
    }, function(event) {
    	removeToastMessage();
    	refinanceTeaserRate.currentMortgageBalance = $('input[name="currentMortgageBalance"]').val();
        var className=$('input[name="currentMortgageBalance"]');
    	var isSuccess=validateInput(className,$('input[name="currentMortgageBalance"]').val(),message);
    
    	if(isSuccess){
    		paintRefinanceStepCEP();
    	}else{
    		return false;
    	}
        
        
    });
    $('#ce-refinance-cp').append(saveAndContinueButton);
}

function paintRefinanceStep1b() {
    var quesTxt = "How much cash do you want to take out?";
    var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2, "cashTakeOut");
    $('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStepCEP() {
        stages = 3;
	   progressBaar(3);
        quesContxts = {};
        $('#ce-refinance-cp').html("");
            var questions = [{
            "type": "desc",
            "text": "What is your current mortgage payment?",
            "name": "currentMortgagePayment",
            "value": ""
        }, {
            "type": "yesno",
            "text": "Does the payment entered above include property taxes and/or homeowners insurance?",
            "name": "isIncludeTaxes",
            "options": [{
                "text": "Yes"
            }, {
                "text": "No"
            }]
        },{
            "type": "yearMonth",
            "text": "How much are your property taxes?",
            "name": "propertyTaxesPaid",
            "value": ""
        }, {
            "type": "yearMonth",
            "text": "How much is your homeowners insurance?",
            "name": "annualHomeownersInsurance",
            "value": ""
        }];
        for (var i = 0; i < questions.length; i++) {
            var question = questions[i];
            var contxt = getQuestionContext(question, $('#ce-refinance-cp'),refinanceTeaserRate);
            contxt.drawQuestion();
            quesContxts[question.name]=contxt;
        }
        var saveAndContinueButton = $('<div>').attr({
            "class": "cep-button-color ce-save-btn"
        }).html("Save & continue").on('click', function() {
           removeToastMessage();
        	
        	refinanceTeaserRate.currentMortgagePayment = quesContxts["currentMortgagePayment"].value;//$('input[name="currentMortgagePayment"]').val()
            refinanceTeaserRate.isIncludeTaxes = quesContxts["isIncludeTaxes"].value;//quesContxts[1].value;
            refinanceTeaserRate.propertyTaxesPaid = quesContxts["propertyTaxesPaid"].value;//$('input[name="annualPropertyTaxes"]').val();
            refinanceTeaserRate.annualHomeownersInsurance = quesContxts["annualHomeownersInsurance"].value;//$('input[name="annualHomeownersInsurance"]').val();
            refinanceTeaserRate.propTaxMonthlyOryearly = quesContxts["propertyTaxesPaid"].yearMonthVal;//$('input[name="annualHomeownersInsurance"]').val();
            refinanceTeaserRate.propInsMonthlyOryearly = quesContxts["annualHomeownersInsurance"].yearMonthVal;//$('input[name="annualHomeownersInsurance"]').val();
           
            var questionOne=validateInput($('input[name="currentMortgagePayment"]'),refinanceTeaserRate.currentMortgagePayment,message);
            var questionThree=validateInput($('input[name="propertyTaxesPaid"]'),refinanceTeaserRate.propertyTaxesPaid,message);
            var questionFour=validateInput($('input[name="annualHomeownersInsurance"]'),refinanceTeaserRate.annualHomeownersInsurance ,message);

          // alert(questionOne+""+questionThree+""+questionFour);
            
           if(!questionOne || !questionThree || !questionFour){
        	   return false;
           }
           
           if(refinanceTeaserRate.isIncludeTaxes=="Yes"|| refinanceTeaserRate.isIncludeTaxes=="No"){
      			
      		}else{
      			validateDropDown();
      			showErrorToastMessage(answerQuestionOne);
      			return false;
      		}		  
            paintRefinanceHomeWorthToday();
        			 
        });
        $('#ce-refinance-cp').append(saveAndContinueButton);
    }
   

function paintRefinanceHomeWorthToday() {
    stages = 4;
    progressBaar(4);
    var quesTxt = "Approximately what is your home worth today?";
    var quesCont = getTextQuestion(quesTxt, validateMortage, "homeWorthToday");
    $('#ce-refinance-cp').html(quesCont);
}
function validateMortage()
{
	var homeWorthToday =getFloatValue( $('input[name="homeWorthToday"]').val());
	if (homeWorthToday < getFloatValue(refinanceTeaserRate.currentMortgageBalance))
	{				
		$('.ce-input').next('.err-msg').html(propertyValueErrorMessage + refinanceTeaserRate.currentMortgageBalance).show();
		$('.ce-input').addClass('ce-err-input').show();
	}
	else
	{
		paintNewResidenceTypeQues();
	}
	
}
function paintNewResidenceTypeQues(){

	
    if(refinanceTeaserRate.loanType){
    	 stages = 5;
    	progressBaar(5);
    }
    if(buyHomeTeaserRate.loanType){
    	active = 3;
    	homeProgressBaar(3);
    }

    quesContxts = {};
    $('#ce-refinance-cp').html("");
    var questions = [{
        type: "select",
        text: "Property type?",
        name: "propertyType",
        options: [{
            text: "Single family residence",
            value: "0"
        }, {
            text: "Condo",
            value: "1"
        }, {
            text: "2-4 Units",
            value: "2"
        }],
        selected: ""
    }, {
        type: "select",
        text: "Property use?",
        name: "residenceType",
        options: [{
            text: "Primary residence",
            value: "0"
        }, {
            text: "Vacation/Second home",
            value: "1"
        }, {
            text: "Investment property",
            value: "2"
        }],
        selected: ""
    }];

    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var contxt = getQuestionContext(question, $('#ce-refinance-cp'));
        contxt.drawQuestion();
        quesContxts[question.name]=contxt;
    }
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color ce-save-btn"
    }).html("Save & continue").on('click', function() {
    	removeToastMessage();
        if(refinanceTeaserRate.loanType){
            refinanceTeaserRate.propertyType = quesContxts["propertyType"].value;//$('input[name="currentMortgagePayment"]').val()
            refinanceTeaserRate.residenceType = quesContxts["residenceType"].value;//quesContxts[1].value;
            if(quesContxts["propertyType"].value!="" && quesContxts["residenceType"].value!=""){
            	removeToastMessage();
            	paintRefinanceHomeZipCode();	
            }else{
            	validateDropDown();
            	showErrorToastMessage(yesyNoErrorMessage);
            }
            
        }
        if(buyHomeTeaserRate.loanType){
            buyHomeTeaserRate.propertyType = quesContxts["propertyType"].value;//$('input[name="currentMortgagePayment"]').val()
            buyHomeTeaserRate.residenceType = quesContxts["residenceType"].value;//quesContxts[1].value;
            if(quesContxts["propertyType"].value!="" && quesContxts["residenceType"].value!=""){
            	removeToastMessage();
            	paintHomeZipCode();	
            }else{
            	validateDropDown();
            	showErrorToastMessage(yesyNoErrorMessage);
            }
           // paintHomeZipCode();
        }
    });
    $('#ce-refinance-cp').append(saveAndContinueButton);
}
    

function paintRefinanceHomeInformation() {
    stages = 5;
    progressBaar(5);
    var quesHeaderTxt = "Home Details";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "select",
        text: "What type of property is this?",
        name: "propertyType",
        options: [{
            text: "Single family residence",
            value: "0"
        }, {
            text: "Condo",
            value: "1"
        }, {
            text: "2-4 Units",
            value: "2"
        }],
        selected: ""
    }, {
        type: "select",
        text: "How do you use this home?",
        name: "residenceType",
        options: [{
            text: "Primary residence",
            value: "0"
        }, {
            text: "Vacation home",
            value: "1"
        }, {
            text: "Second home",
            value: "2"
        }],
        selected: ""
    }];

    var questionsContainer = getQuestionContextCEP(questions);
    
   
    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {    	
    	removeToastMessage();
    });

    var container = quesHeaderTextCont.append(questionsContainer).append(saveAndContinueButton);
    $('#ce-refinance-cp').html(container);
    
}


function paintRefinanceHomeZipCode() {
        stages = 6;
        progressBaar(6);
        var quesTxt = "What is the zip code of your home?";
        var quesCont = getTextQuestion(quesTxt, paintRefinanceSeeRates, "zipCode");
        $('#ce-refinance-cp').html(quesCont);
    }	
    


function paintRefinanceSeeRates(parentContainer,teaserRateData,hideCreateAccountBtn) {
        
	
	if(!parentContainer){
            parentContainer=$('#ce-refinance-cp');
        }
        if(!teaserRateData){
            teaserRateData=refinanceTeaserRate;
        }
        stages = 7;
        progressBaar(7);
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
	             /*   var quesTextCont = $('<div>').attr({
	                    "class": "ce-rp-ques-text letUsContactCenter"
	                }).html(quesTxt);*/
	                // alert(JSON.stringify(refinanceTeaserRate));
	               // container.append(quesTextCont);
	                $(parentContainer).html(container);
	                var errorText=getNoProductMessageInLockRatePage();
	                if(typeof(newfiObject)==='undefined')
	                {
	                	teaserRateValHolder.leadCustomer=true;
	                }
	                var mainContainer = paintApplyNow(teaserRateData,undefined,true);
	                //6.12 Portal testing and Updates
                    /* var createAccBtn= $('<div>').attr({
	                    "class": "rate-btn createAccButton"
	                }).html("Provide your contact information").on('click', function() {
	                    var mainContainer = paintApplyNow(teaserRateData);
	                    $('#ce-main-container').html(mainContainer);
	                });*/
	                $(parentContainer).append(errorText);
	                if(typeof(newfiObject)==='undefined')
                        /*$(parentContainer).append(createAccBtn);*/
	                    $(parentContainer).append(mainContainer);
	                return
	            }


                var ob;
                try{
                    ob=JSON.parse(data);
                    if(ob.length>0){
                        responseTime=ob[0].responseTime;
                }
                }catch(exception){
                    ob={};
                    responseTime="";
                    console.log("Invalid Data");
                }
                var quesTxt = "Loan Programs and Rates";
                var container = $('<div>').attr({
                    "class": "ce-rate-main-container"
                });
                var quesTextCont = $('<div>').attr({
                    "class": "ce-rp-ques-text"
                }).html(quesTxt);
                // alert(JSON.stringify(refinanceTeaserRate));
                container.append(quesTextCont);
                $(parentContainer).html(container);

               // alert('createLoan data is '+data)
                paintFixYourRatePageCEP(ob, teaserRateData,parentContainer,hideCreateAccountBtn);
                clearOverlayMessage();
               /* }*/
                  
                 
            },
            error: function(data) {
            	validateDropDown();
                showErrorToastMessage(errorInrefinanceRates+data);
                
                hideOverlay();
            }
        });
        
    }
 
var refreshSupport=true;
function progressBaar(num) {
		scrollToTop();
		adjustCustomerApplicationPageOnResize();
		adjustCustomerEngagementPageOnResize();
        var count = itemsList.length;
        $('#step-no').text(num);
        $("#progressBaarId_" + num).removeClass('ce-lp-in-progress').removeClass('ce-lp-complete').addClass('ce-lp-in-progress');
        $('#stepNoId_' + num).html(num);
        for (var i = 1; i <= num - 1; i++) {
            $("#progressBaarId_" + i).removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
            $('#stepNoId_' + i).html("");
        }
        for (var i = num + 1; i <= count; i++) {
            $("#progressBaarId_" + i).removeClass('ce-lp-in-progress').removeClass('ce-lp-complete').addClass('ce-lp-not-started');
            $('#stepNoId_' + i).html(i);
        }
        sessionStorage.refinaceData = JSON.stringify(refinanceTeaserRate);
        if(typeof(newfiObject)==='undefined'){
            //window.location.hash="#CE-"+(num-1);
            if(window.location.hash!=("#CE-"+(num-1)))
                saveState(undefined, undefined, undefined,(num-1));
        }
            
    }
   

function paintApplyNow(inputCustomerDetails,emailQuote,appendedFlag) {

    
    var registration = new Object();
    var parentWrapper = $('<div>').attr({
        "class": "container-row row clearfix"
    });
    var regMainContainer = $('<div>').attr({
        "class": "reg-main-container"
    });
    var regDisplayTitle = $('<div>').attr({
        "class": "reg-display-title"
    }).html("Get Started");
    var regDisplaySubTitle = $('<div>').attr({
        "class": "reg-display-title-subtxt cus-eng-reg"
    }).html("Create your account now to have immediate access to the powerful newfi lending tool.");
    var regInputContainerFname = $('<div>').attr({
        "class": "reg-input-cont reg-fname"
    });
    var regInputfname = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "First Name",
        "name": "fname"
    }).bind('keypress', function(e) {
 
        if($(this).val().length == 0){
            var k = e.which;
            var ok = k >= 65 && k <= 90 || // A-Z
                k >= 97 && k <= 122 || // a-z
                k >= 48 && k <= 57; // 0-9
 
            if (!ok){
                e.preventDefault();
            }
        }
    });
    regInputContainerFname.append(regInputfname).append(appendErrorMessage());
    var regInputContainerlname = $('<div>').attr({
        "class": "reg-input-cont reg-lname"
    });
    var regInputlname = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "Last Name",
        "name": "lname"
    }).bind('keypress', function(e) {
 
        if($(this).val().length == 0){
            var k = e.which;
            var ok = k >= 65 && k <= 90 || // A-Z
                k >= 97 && k <= 122 || // a-z
                k >= 48 && k <= 57; // 0-9
 
            if (!ok){
                e.preventDefault();
            }
        }
    });
    regInputContainerlname.append(regInputlname).append(appendErrorMessage());
    var regInputContainerEmail = $('<div>').attr({
        "class": "reg-input-cont reg-email"
    });
    var regInputEmail = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "Email",
        "name": "email"
    });
    regInputContainerEmail.append(regInputEmail).append(appendErrorMessage());
    //TODO added phone feild
/*    var regInputContainerPhone = $('<div>').attr({
        "class": "reg-input-cont reg-phone"
    });
    var regInputPhone = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "Phone number",
        "name": "phone"
    });
    regInputPhone.mask("(999) 999-9999");
    regInputContainerPhone.append(regInputPhone).append(appendErrorMessage());*/
    //End
    var errorMsg = $('<div>').attr({
        "class": "reg-input-error hide errorMsg"
    });
    var errorMsgSpan = $('<span>').attr({
        "class": "registration-error",
        
    }).html(emailIDErrorMessageFromServer).on('click',function(){
    	
    	$('.errorMsg').hide();
    });
    
    errorMsg.append(errorMsgSpan);
    
    var regContainerGetStarted = $('<div>').attr({
        "class": "reg-btn-wrapper clearfix"
    });
    var buttonText = "Create Account";
    if (teaserRateValHolder.leadCustomer)
    	{
    	buttonText = "Submit";
    	}
    var regGetStarted = $('<div>').attr({
        "class": "cep-button-color reg-btn float-left",

    }).html(buttonText).bind('click', {



        "userDetails": registration
    }, function(event) {
    	registration.firstName = $('input[name="fname"]').val();
        registration.lastName = $('input[name="lname"]').val();
        var dateVar = new Date();
        var timezone = dateVar.getTimezoneOffset();
        registration.emailId = $('input[name="email"]').val() + ":" + timezone;
    	if (teaserRateValHolder.leadCustomer)
    	{
    		isNoProductFound=true;
    		validateUsersBeforeRegistration(registration,isNoProductFound);
    		//sendInfoToNewfi(registration);
    	}
    	else
    	{
	    	
	       /* var phoneNumber = $('input[name="phone"]').val();
	        registration.phoneNumber = phoneNumber.replace(/[^0-9]/g, '');*/
	       
	        var status=validateCustomerRegistration(registration.phoneNumber);
	        if(!status){
	        	return false;
	        }
	        var appUserInput = new Object();
	        var refinancedetails = new Object();
	        var propertyTypeMaster = new Object();
	        var purchaseDetails = new Object();
	        var user = new Object();
	
	        var selectedLqbData =undefined;
	        var initValueSet =undefined;
	        if(closingCostHolder){
	            selectedLqbData = closingCostHolder.valueSet;
	            initValueSet = closingCostHolder.initValueSet;
	        }
	
	        var teaseRateDataList=[];
	        teaseRateDataList.push(initValueSet);
	        teaseRateDataList.push(selectedLqbData);
	
	        user.firstName = registration.firstName;
	        user.lastName = registration.lastName;
	        user.emailId = registration.emailId;
	        //user.phoneNumber=registration.phoneNumber;
	        appUserInput.emailQuote = emailQuote;
	
	        loanType = {};
	        loanType.loanTypeCd = inputCustomerDetails.loanType;
	        appUserInput.loanType = loanType;
	            
	            
	    	if(inputCustomerDetails.isIncludeTaxes=="Yes"||inputCustomerDetails.isIncludeTaxes==true){
	        	inputCustomerDetails.isIncludeTaxes = true;
	        }else if(inputCustomerDetails.isIncludeTaxes=="No"||inputCustomerDetails.isIncludeTaxes==false){
	        	inputCustomerDetails.isIncludeTaxes = false;
	        }
	            
	            
	        if(appUserInput.loanType.loanTypeCd === 'REF'){
	        	
	        	refinancedetails.refinanceOption = inputCustomerDetails.refinanceOption;
	            refinancedetails.mortgageyearsleft=inputCustomerDetails.yearLeftOnMortgage;
	            refinancedetails.cashTakeOut=inputCustomerDetails.cashTakeOut;
	            refinancedetails.currentMortgageBalance = inputCustomerDetails.currentMortgageBalance;
	            refinancedetails.currentMortgagePayment = inputCustomerDetails.currentMortgagePayment;
	            refinancedetails.includeTaxes = inputCustomerDetails.isIncludeTaxes;
	            
	
	            propertyTypeMaster.propertyTaxesPaid = inputCustomerDetails.propertyTaxesPaid;
	            propertyTypeMaster.propertyInsuranceCost = inputCustomerDetails.annualHomeownersInsurance;
	            propertyTypeMaster.homeWorthToday = inputCustomerDetails.homeWorthToday;
	            propertyTypeMaster.homeZipCode = inputCustomerDetails.zipCode;
	            
	            propertyTypeMaster.propTaxMonthlyOryearly = refinanceTeaserRate.propTaxMonthlyOryearly; 
	            propertyTypeMaster.propInsMonthlyOryearly = refinanceTeaserRate.propInsMonthlyOryearly;
	            propertyTypeMaster.propertyTypeCd = refinanceTeaserRate.propertyType;
	            propertyTypeMaster.residenceTypeCd = refinanceTeaserRate.residenceType;
	
	            appUserInput.refinancedetails = refinancedetails;
	            appUserInput.propertyTypeMaster = propertyTypeMaster;
	            
	        }else{
	        	
	            //purchaseDetails
	            purchaseDetails.livingSituation =inputCustomerDetails.livingSituation;
	            purchaseDetails.housePrice =inputCustomerDetails.purchaseDetails.housePrice;
	            var loanAmount = getFloatValue(inputCustomerDetails.purchaseDetails.housePrice) -getFloatValue(inputCustomerDetails.currentMortgageBalance);
	            purchaseDetails.loanAmount = loanAmount;
	            purchaseDetails.isTaxAndInsuranceInLoanAmt =inputCustomerDetails.purchaseDetails.isTaxAndInsuranceInLoanAmt; 
	            purchaseDetails.estimatedPrice = inputCustomerDetails.estimatedPurchasePrice;
	            purchaseDetails.buyhomeZipPri = inputCustomerDetails.zipCode;
	
	            appUserInput.monthlyRent = inputCustomerDetails.rentPerMonth;
	            appUserInput.purchaseDetails =purchaseDetails;
	
	            propertyTypeMaster.propertyTypeCd=buyHomeTeaserRate.propertyType;
	            propertyTypeMaster.residenceTypeCd=buyHomeTeaserRate.residenceType;
	            propertyTypeMaster.propertyTaxesPaid = inputCustomerDetails.propertyTaxesPaid;
	            propertyTypeMaster.propertyInsuranceCost = inputCustomerDetails.annualHomeownersInsurance;
	            propertyTypeMaster.homeZipCode = inputCustomerDetails.zipCode;
	            appUserInput.propertyTypeMaster = propertyTypeMaster;
	        }
	        appUserInput.user = user; 
	        console.log(appUserInput);
	       // Where livingSituation should goes 
	        //appUserDetails.purchaseDetails.livingSituation = refinancedetails.livingSituation;
	        validateUsersBeforeRegistration(appUserInput, teaseRateDataList);
	        // saveUserAndRedirect(appUserDetails,saveAndUpdateLoanAppForm(appUserDetails));
	        //saveUserAndRedirect(appUserDetails);
	        // saveUserAndRedirect(registration);
    	}
    });
    regContainerGetStarted.append(regGetStarted);
    if(!appendedFlag){
        regMainContainer.append(regDisplayTitle);
        regMainContainer.append(regDisplaySubTitle);
    }
    regMainContainer.append(regInputContainerFname);
    regMainContainer.append(regInputContainerlname);
    regMainContainer.append(regInputContainerEmail);
    /*regMainContainer.append(regInputContainerPhone);*/
    regMainContainer.append(errorMsg);
    regMainContainer.append(regContainerGetStarted);
   
    return parentWrapper.append(regMainContainer);
}
function validateUsersBeforeRegistration(registration,teaseRateDataList){
	
	$('#overlay-loader').show();
    $.ajax({
        url: "rest/shopper/validate",
        type: "POST",
        data: {
            "registrationDetails": JSON.stringify(registration)
        },
        datatype: "application/json",
        cache:false,
        success: function(data) {

            $('#overlay-loader').hide();
            if(data.error==null){
            	if(!isNoProductFound){
            		 saveUserAndRedirect(registration,teaseRateDataList);
            	}else{
            		sendInfoToNewfi(registration);
            	}
            	
            }else{
            	//showErrorToastMessage(data.error.message);
            	$('.errorMsg').show();
            }
           
        },
        error: function(data) {
             //showErrorToastMessage(data);
        	$('.errorMsg').show();
             $('#overlay-loader').hide();
        }
    });
}

function sendInfoToNewfi(registration){
	
	$('#overlay-loader').show();
    $.ajax({
        url: "rest/shopper/record",
        type: "POST",
        data: {
            "registrationDetails": JSON.stringify(registration)
        },
        datatype: "application/json",
        cache:false,
        success: function(data) {
            $('#overlay-loader').hide();
            if(data.error==null){
            	$('.reg-main-container').hide();
            	$('.contactInfoText').html('Your details have been submitted. Our team will contact you shortly.')
            	
            }else{
            	showErrorToastMessage(data.error.message);
            	$('.errorMsg').show();
            }
           
        },
        error: function(data) {
             //showErrorToastMessage(data);
        	$('.errorMsg').show();
             $('#overlay-loader').hide();
        }
    });
}
function saveUserAndRedirect(registration,teaseRateDataList) {
	
    // alert(JSON.stringify(registration));
    $('#overlay-loader').show();
    $.ajax({
        url: "rest/shopper/registration",
        type: "POST",
        data: {
            "registrationDetails": JSON.stringify(registration),
            "teaseRateData": JSON.stringify(teaseRateDataList),
        },
        datatype: "application/json",
        cache:false,
        success: function(data) {
            // $('#overlay-loader').hide();
            $('#overlay-loader').hide();
            // alert (data);
            window.location.href = data;
            // printMedianRate(data,container);
        },
        error: function(data) {
           // alert(data);
        	validateDropDown();
            $('#ce-main-container').html(data.toString());
            // $('#overlay-loader').hide();
            showErrorToastMessage(data);
        }
    });
}

function saveAndUpdateLoanAppForm(appUserDetails) {
	
        $.ajax({
            url: "rest/application/applyloan",
            type: "POST",
            data: {
                "appFormData": JSON.stringify(appUserDetails)
            },
            datatype: "application/json",
            cache:false,
            success: function(data) {
              //  alert('inside appFormData');
                window.location.href = data;
            },
            error: function(erro) {
            	validateDropDown();
                showErrorToastMessage(erro);
            }
        });
    }


function paintFixYourRatePageCEP(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn) {
    //set teaser rate flag
    teaserRateValHolder.teaserRate=true;
    paintRatePage(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn)
	/*var teaserRate =  modifiedLQBJsonRes(teaserRate);
    var loanSummaryWrapper = getLoanSummaryWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
    
    var closingCostWrapper = getClosingCostSummaryContainer(getLQBObj(teaserRate));
  //  $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);
    if(!parentContainer)
        parentContainer=$('#ce-refinance-cp');
    $(parentContainer).append(loanSummaryWrapper).append(closingCostWrapper);*/
}

function getLoanSummaryWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
    
	
	var loanSummaryWrapper = getLoanSummaryWrapperTeaserRate(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
    
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var rateWrapper ="";
    var bottomText ="";
    var rateVO = getLQBObj(teaserRate);
    if(!rateVO.dummyData){
        rateWrapper = getLoanSliderWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
        bottomText = getHeaderText("Rate and APR quoted are based on the information you provided, are not guaranteed, and are subject to change. Actual rate and APR will be available on your Good Faith Estimate after loan amount and income are verified.");
    }
    parentWrapper.append(loanSummaryWrapper).append(rateWrapper).append(bottomText);
    return parentWrapper;
}

function getLoanSummaryHeaderCEP() {
	currentDateTime= getCurrentDate(responseTime);
	  
	   var headerCont = $('<div>').attr({
            "class": "loan-summary-header clearfix"
        });
        var col1 = $('<div>').attr({
            "class": "loan-summary-header-col1 float-left capitalize"
        }).html('Programs and Rates');
        var col2 = $('<div>').attr({
            "class": "loan-summary-header-col2 float-left"
        }).html(currentDateTime);
        headerCont.append(col1);
        if(responseTime!="")
            headerCont.append(col2);
        return headerCont;
    }




function getLoanSliderWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
  
  
    var wrapper = $('<div>').attr({
        "class": "lock-rate-slider-wrapper"
    });
    var header = $('<div>').attr({
        "class": "lock-rate-slider-header"
    }).html("Select Loan Program and Rate");
    var container = $('<div>').attr({
        "class": "lock-rate-slider-container"
    });
    
    var tenureSlider = getYearSliderContCEP1(teaserRate,inputCustomerDetails);
    var rateSlider = getRateSliderContCEP(teaserRate,inputCustomerDetails);
    
    container.append(header).append(tenureSlider).append(rateSlider);
    var rateBtn1="";
    //NEXNF-434
    //var rateBtn2="";
    if(!hideCreateAccountBtn){
        rateBtn1= $('<div>').attr({
            "class": "rate-btn"
        }).html("Get Pre-Qualified Now!").on('click', function() {
            
        	
        	//inputCustomerDetails.propertyTaxesPaid = $('#calTaxID2').val();
        	//inputCustomerDetails.propertyInsuranceCost = $('#CalInsuranceID2').val();
        	
            
        	var mainContainer = paintApplyNow(inputCustomerDetails);
            $('#ce-main-container').html(mainContainer);
        });
        /*rateBtn2 = $('<div>').attr({
            "class": "rate-btn-alertRate"
        }).html("Email This Quote").on('click', function() {
        	var emailQuote = true;
            var mainContainer = paintApplyNow(inputCustomerDetails,emailQuote);
            $('#ce-main-container').html(mainContainer);
        });*/
    }else{
        rateBtn1= $('<div>').attr({
            "class": "rate-btn"
        }).html("Complete Your Loan Profile").on('click', function() {
            if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
                window.location.hash="#myLoan/my-application";
                //changeSecondaryLeftPanel(2);//Commented since change in hash value does the job
            }else{
                window.location.hash="#loan/"+selectedUserDetail.loanID+"/application";
                //changeAgentSecondaryLeftPanel("lp-step1");//Commented since change in hash value does the job
            }
        });
    }
    return wrapper.append(container).append(rateBtn1)/*.append(rateBtn2);*/;
}



function getYearSliderContCEP1(teaserRate,inputCustomerDetails) {
    var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Length of Loan");
    
    var silderCont = getYearSliderCEP(teaserRate,inputCustomerDetails);
    
    return wrapper.append(headerTxt).append(silderCont);
}


function getYearSliderCEP(LQBResponse,inputCustomerDetails) {
    var container = $('<div>').attr({
        "class": "silder-cont yr-slider float-left"
    });
    var yearValues = LQBResponse;

    for (var i = 0; i < yearValues.length; i++) {
        var leftOffset = i / (yearValues.length - 1) * 100;
        var gridItemCont = $('<div>').attr({
            "class": "yr-grid-cont"
        });
        var selectIcon = $('<div>').attr({
            "class": "yr-slider-icon"
        }).css({
            "left": leftOffset + "%"
        }).bind('click', {
            "ratesArray": yearValues[i].rateVO,
            "year":yearValues[i].value
        }, function(event) {
            if (!$(this).hasClass('yr-slider-icon-selected')) {
                $('.yr-grid-cont .yr-slider-icon').removeClass('yr-slider-icon-selected');
                $(this).addClass('yr-slider-icon-selected');
                $('.yr-grid-cont .yr-grid-item-selected').hide();
                $('.yr-grid-cont .yr-grid-item').show();
                $(this).parent().find('.yr-grid-item').hide();
                $(this).parent().find('.yr-grid-item-selected').show();
                $('#rate-slider-cont').find('.rt-slider').remove();
                var rateSlider = getRatSliderCEP(event.data.ratesArray,inputCustomerDetails,event.data.year);
                $('#rate-slider-cont').append(rateSlider);
                index = parseInt(event.data.ratesArray.length / 2);
               
                $('#aprid').html(event.data.ratesArray[index].APR + " %");
                $('#closingCostId').html(showValue(event.data.ratesArray[index].closingCost));
                $('#teaserRateId').html(parseFloat(event.data.ratesArray[index].teaserRate).toFixed(3)+" %");
               
                if(event.data.year =='5' || event.data.year =="7")
                $('#loanprogramId').html(event.data.year +" Year ARM");
                else
                $('#loanprogramId').html(event.data.year +" Year Fixed");
                
                $('#principalIntId').html(showValue(event.data.ratesArray[index].payment));
                var ratVo=event.data.ratesArray[index];
                ratVo.yearData=event.data.year
                updateOnSlide(ratVo);
                
                teaseCalculation(inputCustomerDetails);
            }
        });
        var gridItem = $('<div>').attr({
            "class": "yr-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(yearValues[i].value + " Yr");
        var gridItemSelected = $('<div>').attr({
            "class": "yr-grid-item-selected hide"
        }).css({
            "left": leftOffset + "%"
        }).html(yearValues[i].text);
        gridItemCont.append(selectIcon).append(gridItem).append(gridItemSelected);
        // Static code to select year by default
        if (i == yearValues.length-1) {
            selectIcon.addClass('yr-slider-icon-selected');
            gridItem.hide();
            gridItemSelected.show();
        }
        container.append(gridItemCont);
    }
    return container;
}

function getRateSliderContCEP(LQBResponse,inputCustomerDetails) {
    var wrapper = $('<div>').attr({
        "id": "rate-slider-cont",
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Interest Rate");
    
    var yearValues = LQBResponse;
    
    var rateArray = yearValues[yearValues.length-1].rateVO;
//    rateArray=rateArray.reverse();
    index = parseInt(rateArray.length / 2);
    var silderCont = getRatSliderCEP(rateArray,inputCustomerDetails,yearValues[yearValues.length-1].value);
    
    return wrapper.append(headerTxt).append(silderCont);
}

function getRatSliderCEP(gridArray,inputCustomerDetails,yearValue) {
    
	
	var rateArray = [];
    for (var i = 0; i < gridArray.length; i++) {
        rateArray[i] = parseFloat(gridArray[i].teaserRate).toFixed(3);
    }
    index = parseInt(rateArray.length / 2);
    var container = $('<div>').attr({
        "class": "silder-cont rt-slider float-left"
    });
    var tsIcon = $('<div>').attr({
        "id": "tenure-slider",
        "class": "tenure-slider-icon"
    }).slider({
        orientation: "horizontal",
        range: "min",
        max: rateArray.length - 1,
        value: index,
        change: function(event, ui) {
         
            $('#aprid').html(gridArray[ui.value].APR +" %");
            $('#closingCostId').html(showValue(gridArray[ui.value].closingCost));
            $('#teaserRateId').html(parseFloat(gridArray[ui.value].teaserRate).toFixed(3) +" %");
            $('#principalIntId').html(showValue(gridArray[ui.value].payment));
            
            teaseCalculation(inputCustomerDetails);
            var ratVo=gridArray[ui.value];
            ratVo.yearData=yearValue
            updateOnSlide(ratVo);
        }
    });
    container.append(tsIcon);
    var gridItemCont = $('<div>').attr({
        "class": "rt-grid-cont"
    });
    //gridArray=gridArray.reverse();
    for (var i = 0; i < gridArray.length; i++) {
        var leftOffset = i / (gridArray.length - 1) * 100;
        var gridItem = $('<div>').attr({
            "class": "rt-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(parseFloat(gridArray[i].teaserRate).toFixed(3) + "%");
        gridItemCont.append(gridItem);
    }
    return container.append(gridItemCont);
}


function teaseCalculation(inputCustomerDetails){
	
	var taxesTemp = 0;
	 var InsuranceTemp = 0;
	var principalInterest = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
	if($('#calTaxID2').val() != "") 
	taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));
    
	if($('#CalInsuranceID2').val() != "") 
	InsuranceTemp =  parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
    
    var  monthlyPayment;
    var isIncludeTaxes;
    if(teaserRateValHolder.teaserRate){
        if(inputCustomerDetails.loanType=="REF")
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.currentMortgagePayment))); 
        else
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.rentPerMonth))); 
        isIncludeTaxes=inputCustomerDetails.isIncludeTaxes;
    }else{
        if(inputCustomerDetails.loanType.loanTypeCd =="REF")
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.refinancedetails.currentMortgagePayment)));    
        else
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.monthlyRent)));
        isIncludeTaxes=inputCustomerDetails.refinancedetails.includeTaxes;
    }
    var investment = (InsuranceTemp + taxesTemp);
	
    
    var totalEstMonthlyPaymentId=principalInterest;
    if(isIncludeTaxes =="Yes"||isIncludeTaxes ==true){
        monthlyPayment = monthlyPayment -investment ;
        totalEstMonthlyPaymentId = (principalInterest + investment);
    }
	
	
	var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));
	
    
    var textDiv=$('#monthlyPaymentDifferenceId').prev();
    var hgLow="";
    var ele=$('#monthlyPaymentDifferenceId');
    ele.removeClass("loan-summary-green-col-detail");
    ele.removeClass("loan-summary-red-col-detail");
    var clas="";
    if(principalInterest < monthlyPayment){
        hgLow='<font color="green"><b>Lower</b></font>';
        clas="loan-summary-green-col-detail";
    }else{
        hgLow='<font color="red"><b>Higher</b></font>';
        clas="loan-summary-red-col-detail";
    }
    textDiv.html('This Monthly<br/> Payment is '+hgLow+' by');
    ele.addClass(clas);


	$('#monthlyPaymentId').text(showValue(monthlyPayment));
	$('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
	$('#totalEstMonthlyPaymentId').text((showValue(totalEstMonthlyPaymentId)));
	
}

function getLoanSummaryWrapperTeaserRate(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
    
	
	var customerInputData = inputCustomerDetails;
    loanTypeText = customerInputData.loanType;
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var header = getLoanSummaryHeaderCEP(inputCustomerDetails,hideCreateAccountBtn);
    var container;
    if (loanTypeText == "REF") {
        container = getLoanSummaryContainerRefinanceCEP(teaserRate, customerInputData);
    } else if (loanTypeText == "PUR") {
        container = getLoanSummaryContainerPurchaseCEP(teaserRate, customerInputData);
    }
    
    parentWrapper.append(header).append(container);
    return parentWrapper;
}

function getLoanSummaryHeaderCEP(inputCustomerDetails,hideCreateAccountBtn) {
    var headerCont = $('<div>').attr({
        "class": "loan-summary-header clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 float-left capitalize"
    }).html('Programs and Rates');
    var col2 = $('<div>').attr({
        "class": "loan-summary-header-col2 float-left"
    }).html(getCurrentDate(responseTime));
    
    var rateBtn2="";
    if(!hideCreateAccountBtn){
        rateBtn2 = $('<div>').attr({
            "class": "rate-btn-alertRate float-right"
        }).html("Email This Quote").on('click', function() {
            var emailQuote = true;
            var mainContainer = paintApplyNow(inputCustomerDetails,emailQuote);
            $('#ce-main-container').html(mainContainer);
        });
    }
    headerCont.append(col1).append(col2).append(rateBtn2);
    return headerCont;
}

function getLoanSummaryContainerRefinanceCEP(teaserRate, customerInputData) {
    
	var path = "CEP";
	var yearValues = teaserRate;
   
	var rateVO = getLQBObj(yearValues);
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
   
    var wrapper = $('<div>').attr({
    	"class" : "loan-summary-container"
    });
    
    var container = $('<div>').attr({
        "class": "clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    
   
    var loanAmount  = customerInputData.currentMortgageBalance;
    
    if (customerInputData.refinanceOption == "REFLMP") refinanceOpt = "Lower monthly payment";
    if (customerInputData.refinanceOption == "REFMF") refinanceOpt = "Pay off mortgage faster";
    if (customerInputData.refinanceOption == "REFCO"){
    	refinanceOpt = "Take cash out";
    	
        var cashTakeOut = getFloatValue(customerInputData.cashTakeOut);
        var currentMortgageBalance = getFloatValue(customerInputData.currentMortgageBalance);
    	loanAmount = cashTakeOut + currentMortgageBalance;
    }
   
    var  monthlyPayment  = parseFloat(removedDoller(removedComma(customerInputData.currentMortgagePayment))); 
    var principalInterest = parseFloat(removedDoller(removedComma(rateVO.payment)));
    var totalEstMonthlyPayment = principalInterest;
    var Insurance =  parseFloat(removedDoller(removedComma(customerInputData.annualHomeownersInsurance)));
	var tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    if(isNaN(getFloatValue(tax)))
        tax="";
    if(isNaN(getFloatValue(Insurance)))
        Insurance="";
    
    if(customerInputData.isIncludeTaxes =="Yes"||customerInputData.isIncludeTaxes ==true){
    	
    	var investment = (Insurance + tax);
    	monthlyPayment = monthlyPayment -investment ;
    	totalEstMonthlyPayment  = principalInterest + investment;
  
    }
    
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));

    
    var lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - " + refinanceOpt);
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVO.yearData +" Year Fixed","loanprogramId");
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var lcRow3 = getLoanSummaryRow("Interest Rate", val, "teaserRateId");
    
    if(customerInputData.refinanceOption != "REFCO")
    var lcRow4 = getLoanAmountRowCEP("Loan Amount", showValue(loanAmount),"loanAmount");
    else
    var lcRow4 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Current Loan Amout",showValue(currentMortgageBalance), "Cashout",showValue(cashTakeOut),true,path);	
    
    var lcRow5 = getLoanSummaryRow("APR", rateVO.APR +" %", "aprid");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5);
    
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Proposed Principal & Interest",showValue(rateVO.payment),"principalIntId");
    var rcRow2 = getLoanSummaryRowCalculateBtnCEP("Tax", showValue(tax),"calTaxID","calTaxID2",customerInputData);
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtnCEP("Insurance", showValue(Insurance),"CalInsuranceID","CalInsuranceID2",customerInputData);
   
    var rcRow6 = getLoanSummaryRow("Current Principal & Interest ", showValue(monthlyPayment) ,"monthlyPaymentId");
    
    //var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow6);
    container.append(leftCol).append(rightCol);
    
    var bottomRow = $('<div>').attr({
    	"class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
    	"class" : "loan-summary-lp float-left"
    });
    var bottomLcRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost), "closingCostId");
    
    bottomLeftCol.append(bottomLcRow);
    
    var bottomRightCol = $('<div>').attr({
    	"class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Estimated<br/>Monthly Payments ", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);

    var hgLow="";
     if(principalInterest<monthlyPayment){
        hgLow='<font color="green"><b>Lower</b></font>';
    }else{
        hgLow='<font color="red"><b>Higher</b></font>';
    }
    var rcRow7 = getLoanSummaryLastRow('This Monthly<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId");
    bottomRightCol.append(rcRow7);

    
    bottomRow.append(bottomLeftCol).append(bottomRightCol);
    
    return wrapper.append(container).append(bottomRow);
}


function getLoanSummaryContainerPurchaseCEP(teaserRate, customerInputData) {
    
	var path = "CEP";
	var livingSituation = capitalizeFirstLetter(customerInputData.livingSituation);
	
	var yearValues = teaserRate;
	   
	var rateVO = getLQBObj(yearValues);
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
	
   
    var housePrice = parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.housePrice)));   
    var downPayment =  parseFloat(removedDoller(removedComma(customerInputData.currentMortgageBalance))) ;    
    var loanAmount = (housePrice-downPayment);
    
    var Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyInsuranceCost)));
	var tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
	 
	 if(isNaN(getFloatValue(tax)))
	        tax="";
	 if(isNaN(getFloatValue(Insurance)))
	        Insurance="";
    
   /* var principalInterest = parseFloat(removedDoller(removedComma(rateVO[index].payment)));
    var totalEstMonthlyPayment = principalInterest;
    */
  
    
    
    var wrapper = $('<div>').attr({
        "class": "loan-summary-container"
    });
    
    var container = $('<div>').attr({
    	"class" : "clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });

    var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase -"+livingSituation);
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVO.yearData +" Year Fixed","loanprogramId");
    var lcRow3 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Purchase Amount",showValue(housePrice), " Down Payment",showValue(downPayment),false,path);
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var lcRow4 = getLoanSummaryRow("Interest Rate", val, "teaserRateId");

    var lcRow5 = getLoanSummaryRow("APR", rateVO.APR +" %", "aprid");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 ="";
    if(customerInputData.livingSituation != "homeOwner")
         rcRow1 = getLoanSummaryRow("Current Rental Payment", showValue(customerInputData.purchaseDetails.rentPerMonth));
    var rcRow2 = getLoanSummaryRow("Proposed Principal & Interest", showValue(rateVO.payment),"principalIntId");
    var rcRow3 = getLoanSummaryRowCalculateBtnCEP("Tax",showValue(tax),"calTaxID","calTaxID2",customerInputData);
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtnCEP("Insurance",showValue(Insurance),"CalInsuranceID","CalInsuranceID2",customerInputData);
    //var rcRow5 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(rateVO[index].payment) ,"totalEstMonthlyPaymentId");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4);
    container.append(leftCol).append(rightCol);
    
    var bottomRow = $('<div>').attr({
    	"class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
    	"class" : "loan-summary-lp float-left"
    });
    var bottomLcRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost), "closingCostId");
    
    bottomLeftCol.append(bottomLcRow);
    
    var bottomRightCol = $('<div>').attr({
    	"class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Total Est.<br/>Monthly Payments ", showValue(rateVO.payment) ,"totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);
    
    bottomRow.append(bottomLeftCol).append(bottomRightCol);
    
    return wrapper.append(container).append(bottomRow);
}

function getLoanAmountRowCEP(desc, detail, id) {
   
	var flag = false;
	var container = $('<div>').attr({
        "class": "loan-summary-row"
    });
    var loanAmountCont = $('<div>').attr({
        "class": "clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left clearfix",
    });
   
    var input = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left",
        "id": id
    }).val(detail)
    .keydown(function() {
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});	
    	
    	flag = true;
    });/*.on('keyup',function(e){
    	if(e.which == 27){
    		$(this).blur();
    	}
    });*/
 
    
    var saveBtn = $('<div>').attr({
    	"class" : "cep-button-color sm-save-btn float-right"
    }).html("Save").on('click',{"flag":flag},function(){
    
    	if(flag){
    		amt = $('#loanAmount').val();
            if(teaserRateValHolder.teaserRate){
                modifiyTeaserRate(amt);
            }else{
                modifiyLockRateLoanAmt(amt);
            }
    	}
    });
    
    col2.append(input).append(saveBtn);
    
    loanAmountCont.append(col1).append(col2);
    return container.append(loanAmountCont);
}


function getLoanSummaryRowCalculateBtnCEP(desc, detail,id,id2,customerInputData) {
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    
    col1.append(" ").append("<span>(Monthly)</span>");
    
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left"
    });
    
   /* var col2Txt = $('<input>').attr({
    	"class" : "calculate-btn",
    	"id":id

    }).html(detail)
    .bind('click',{"valueData":$(this).html()},function(event){
    	var prevVal = $(this).text();
    	if(prevVal!="Calculate")
    		$(this).next('input').show().focus().val(prevVal);
    	else{
    		$(this).next('input').show().focus().val("");
    	}
    	$(this).hide();
    	
    });*/
    
    var inputBox = $('<input>').attr({
    	"class" : "loan-summary-sub-col-detail",
    	"id":id2
    }).bind('keyup',{"customerInputData":customerInputData},function(e){
    	
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});	
    	
    	/*if(e.which == 27){
    		var prevVal = $(this).prev('.calculate-btn').text();
    		if($(this).val() == undefined || $(this).val() == prevVal){
    			$(this).hide();
    			$(this).prev('.calculate-btn').show();
    		}
    	}*/
    	
    	var taxesTemp = 0;
    	if($('#calTaxID2').val() !="")
    	 taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));    	
    	customerInputData.propertyTaxesPaid = taxesTemp;
    	
    	var InsuranceTemp = 0;
    	if($('#CalInsuranceID2').val() !="")
    	 InsuranceTemp = parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
    	
    	customerInputData.annualHomeownersInsurance = InsuranceTemp; 
    	
    	var monthlyPayment  = 0;
    	if(customerInputData.loanType =="REF")
    		monthlyPayment = parseFloat(removedDoller(removedComma(customerInputData.currentMortgagePayment))); 
    	/*else
    		monthlyPayment =  parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.rentPerMonth)));*/
    	
    	var investment = (InsuranceTemp + taxesTemp);
    	
    	if(customerInputData.isIncludeTaxes =="Yes"){
    		
    		monthlyPayment = monthlyPayment - investment ;  	
        	
        	
    	}
    	
    	
    	
    	var principalInt = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
    	
    	var monthlyPaymentDifferenceTemp = Math.abs(principalInt - monthlyPayment);
    	var monthlyPaymentDifference = monthlyPaymentDifferenceTemp.toFixed(3);
    	var totalEstMonthlyPaymentId =  (principalInt + investment).toFixed(3);
    	
    	
    	$('#monthlyPaymentId').text(showValue(monthlyPayment));
    	$('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
    	$('#totalEstMonthlyPaymentId').text(showValue(totalEstMonthlyPaymentId));
    	
        var hgLow="";
        if(totalEstMonthlyPaymentId<monthlyPayment){
            hgLow='<font color="green"><b>Lower</b></font>';
        }else{
            hgLow='<font color="red"><b>Higher</b></font>';
        }
        var itm=$("#monthlyPaymentDifferenceId").parent()[0];
        $(itm).find(".loan-summary-col-desc").html('This Monthly<br/> Payment is '+hgLow+' by');
        if(hgLow=='<font color="green"><b>Lower</b></font>'){
            if(!($("#monthlyPaymentDifferenceId").hasClass("loan-summary-green-col-detail"))){
                $("#monthlyPaymentDifferenceId").removeClass("loan-summary-red-col-detail");
                $("#monthlyPaymentDifferenceId").addClass("loan-summary-green-col-detail");
            }
        }else{
            if(!($("#monthlyPaymentDifferenceId").hasClass("loan-summary-red-col-detail"))){
                $("#monthlyPaymentDifferenceId").removeClass("loan-summary-green-col-detail");
                $("#monthlyPaymentDifferenceId").addClass("loan-summary-red-col-detail");
            }
        }
    	
    });
    $(inputBox).val(detail);
    
    col2.append(inputBox);
    container.append(col1).append(col2);
    return container;
}


function modifiedLQBJsonRes(LQBResponse) {
    var yearValues = [];
    for (var i in LQBResponse) {
        loanDurationConform = LQBResponse[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
       
        temp = {};
        temp.value = year;
        temp.text = year + " - year fixed";
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
            temp.value = year;
            temp.text = year + " - year ARM";
        }
        
        temp.rateVO = LQBResponse[i].rateVO;
        yearValues.push(temp);
    }
    yearValues.sort(function(a, b) {
        return parseFloat(a.value) - parseFloat(b.value);
    });
    return yearValues;
}

function modifiyTeaserRate(amt,amt1) {
    if(amt){
        amt=getFloatValue(amt);
        amt1=getFloatValue(amt1);
        if(typeof(newfiObject)!=='undefined'){
            if(appUserDetails.loanType.description=="Purchase"){
                var parentContainer=$('#center-panel-cont');
                appUserDetails.purchaseDetails.housePrice=amt;
                appUserDetails.purchaseDetails.loanAmount=(amt-amt1);
                paintBuyHomeSeeTeaserRate(parentContainer,createTeaserRateObjectForPurchase(appUserDetails),true);
            }else{
                var parentContainer=$('#center-panel-cont');
                appUserDetails.refinancedetails.currentMortgageBalance=amt;
                appUserDetails.refinancedetails.cashTakeOut=amt1;
                paintRefinanceSeeRates(parentContainer,createTeaserRateObjectForRefinance(appUserDetails),true);
            }
        }else{
            if (buyHomeTeaserRate.loanType){
            	buyHomeTeaserRate.purchaseDetails.housePrice=amt;
                buyHomeTeaserRate.purchaseDetails.loanAmount=(amt-amt1);
                buyHomeTeaserRate.currentMortgageBalance = amt1;
                paintBuyHomeSeeTeaserRate();
            }else if(refinanceTeaserRate.loanType){
                refinanceTeaserRate.currentMortgageBalance=amt;
                refinanceTeaserRate.cashTakeOut=amt1;
                paintRefinanceSeeRates();
            }
            }
        }
 }
function changeToState(num){
    switch(num){
        case 1:
            if(refinanceTeaserRate.loanType == "REF")
                paintRefinanceMainContainer()   
            else
                paintBuyHomeContainer();
            break;
        case 2:
            if(refinanceTeaserRate.loanType == "REF")
                paintRefinanceStep2();  
            else{
                if(buyHomeTeaserRate["livingSituation"] = "renting")
                    paintRentOfYourHouse();
                else
                    saleYourCurrentHome();
            }
            break;
        case 3:
            if(refinanceTeaserRate.loanType == "REF")
                paintRefinanceStepCEP();  
            else
                paintNewResidenceTypeQues();
            break;
        case 4:
            if(refinanceTeaserRate.loanType == "REF")
                paintRefinanceHomeWorthToday(); 
            else
                paintHomeZipCode();
            break;
        case 5:
            if(refinanceTeaserRate.loanType == "REF")
                paintNewResidenceTypeQues();    
            else
                paintBuyHomeSeeTeaserRate();
            break;
        case 6:
            if(refinanceTeaserRate.loanType == "REF")
                paintRefinanceHomeZipCode();    
            break;
        case 7:
            if(refinanceTeaserRate.loanType == "REF")
                paintRefinanceSeeRates();  
            break; 
        default:
            paintSelectLoanTypeQuestion();
    }
}