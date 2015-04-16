//JavaScript functions for customer engagement pages

var message = "Please enter required fields";


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
            }/*else if (ob.type == "yearMonth") {
                ob.container = getContextApplicationYearMonthCEP(ob);
            }*/
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
    
    var requird = $('<span>').attr({
        "style": "color:red",
    }).html("*");
    
    quesTextCont.append(requird);
    
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
    optionsContainer.append(optionCont);
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
   
    var requird = $('<span>').attr({
        "style": "color:red",
    }).html("*");
    
    quesTextCont.append(requird);
    
    var optionsContainer = $('<div>').attr({
        "class": "ce-options-cont",
        "name": contxt.name
    });
    for (var i = 0; i < contxt.options.length; i++) {
        var option = contxt.options[i];
        var sel = "false";
        if (contxt.value == option.text) sel = "true";
        var optionCont = $('<div>').attr({
            "class": "app-option-choice",
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
        optionsContainer.append(optionCont);
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
	        "class": "ce-options-cont"
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
	    
	    
	    var requird = $('<span>').attr({
	        "style": "padding-left:22px,padding-right:10px",
	    }).html("per");
	      
	    
	    var selectedOption = $('<span>').attr({
	        "class": "app-option-selected"
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
    $(element).parent().find('.app-option-choice').attr("isSelected", "false");
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
        "class": "ce-title"
    }).html("A New way to Finance your home");
    $('#ce-main-container').append(rateIcon).append(titleText);
    var wrapper = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    var optionsContainer = $('<div>').attr({
        "class": "ce-ques-options-container clearfix"
    });
    var option1 = $('<div>').attr({
        "class": "ce-ques-option float-left"
    }).html("Refinance").on('click', function() {
        paintRefinanceMainContainer();
    });
    var option2 = $('<div>').attr({
        "class": "ce-ques-option float-left"
    }).html("Buy a home").on('click', function() {
        paintBuyHomeContainer();
    });
    optionsContainer.append(option1).append(option2);
    var question = $('<div>').attr({
        "class": "ce-ques-text"
    }).html("Why Pay thousands more to use a loan officer ?<br/>With newfi, saving weeks of headache and thousands of dollars is easy.");
    wrapper.append(optionsContainer).append(question);
    $('#ce-main-container').append(wrapper);
}

function paintRefinanceMainContainer() {
    refinanceTeaserRate.loanType = "REF";
    $('#ce-main-container').html('');
    var wrapper = $('<div>').attr({
        "class": "ce-refinance-wrapper clearfix"
    });
    var leftPanel = getRefinanceLeftPanel();
    var centerPanel = $('<div>').attr({
        "id": "ce-refinance-cp",
        "class": "ce-cp float-left"
    });
    wrapper.append(leftPanel).append(centerPanel);
    $('#ce-main-container').append(wrapper);
    paintRefinanceQuest1();
}
var itemsList = ["Your Priority", "Your Mortgage", "Monthly Payment", "Home Value", "Zip Code", "Your Rates"];

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
        "text": "Lower My Monthly Payment",
        "onselect": paintRefinanceStep2,
        "value": "REFLMP"
    }, {
        "text": "Pay Off My Mortgage Faster",
        "onselect": paintRefinanceStep1a,
        "value": "REFMF"
    }, {
        "text": "Take Cash Out of My Home",
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
    for (var i = 0; i < options.length; i++) {
        var option = $('<div>').attr({
            "class": "ce-option",
            "value": options[i].value
        }).html(options[i].text).bind('click', {
            "option": options[i],
            "name": name
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
            "class": "ce-ques-wrapper"
        });
        var quesTextCont = $('<div>').attr({
            "class": "ce-rp-ques-text"
        }).html(quesText);
        var optionContainer = $('<div>').attr({
            "class": "ce-options-cont"
        });
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
        optionContainer.append(inputBox);
        var saveBtn = $('<div>').attr({
            "class": "ce-save-btn"
        }).html("Save & Continue").bind('click', {
            'clickEvent': clickEvent,
            "name": name
        }, function(event) {
            var key = event.data.name;
            inputValue = $('input[name="' + key + '"]').val();
            refinanceTeaserRate[key] = inputValue;
          //  alert('json'+JSON.stringify(refinanceTeaserRate));
            sessionStorage.refinaceData = JSON.stringify(refinanceTeaserRate);
          
            if (inputValue != undefined && inputValue != "" && inputValue != "$0") {
                event.data.clickEvent();
            } else {
                showToastMessage("Please give awnsers of the questions");
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
// TO DO : current mortgage balance
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
        var contxt = getQuestionContextCEP(question, $('#ce-refinance-cp'));
        contxt.drawQuestion();
        quesContxts.push(contxt);
    }
    var saveAndContinueButton = $('<div>').attr({
        "class": "ce-save-btn"
    }).html("Save & continue").bind('click', {
        'contxt': contxt
    }, function(event) {
        
    	refinanceTeaserRate.currentMortgageBalance = $('input[name="currentMortgageBalance"]').val();
        if(refinanceTeaserRate.currentMortgageBalance && refinanceTeaserRate.currentMortgageBalance !=="$0")
        {
        	paintRefinanceStep3();
        }else{
        	showToastMessage(message);
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

function paintRefinanceStep3() {
        // stages = 3;
	   progressBaar(3);
        quesContxts = [];
        $('#ce-refinance-cp').html("");
        var questions = [{
            "type": "desc",
            "text": "What is your current mortgage payment?",
            "name": "currentMortgagePayment",
            "value": ""
        }, {
            "type": "yesno",
            "text": "Does the payment entered above include property taxes and/or homeowner insuranace ?",
            "name": "isIncludeTaxes",
            "options": [{
                "text": "Yes",
                "addQuestions": [{
                    "type": "desc",
                    "text": "How much are your annual property taxes?",
                    "name": "annualPropertyTaxes",
                    "value": ""
                }, {
                    "type": "desc",
                    "text": "How much is your annual homeowners insurance ?",
                    "name": "annualHomeownersInsurance",
                    "value": ""
                }]
            }, {
                "text": "No",
                "addQuestions": [{
                    "type": "desc",
                    "text": "How much are your annual property taxes?",
                    "name": "annualPropertyTaxes",
                    "value": ""
                }, {
                    "type": "desc",
                    "text": "How much is your annual homeowners insurance ?",
                    "name": "annualHomeownersInsurance",
                    "value": ""
                }]
            }]
        }];
        for (var i = 0; i < questions.length; i++) {
            var question = questions[i];
            var contxt = getQuestionContextCEP(question, $('#ce-refinance-cp'));
            contxt.drawQuestion();
            quesContxts.push(contxt);
        }
        var saveAndContinueButton = $('<div>').attr({
            "class": "ce-save-btn"
        }).html("Save & continue").on('click', function() {
            
        	refinanceTeaserRate.currentMortgagePayment = $('input[name="currentMortgagePayment"]').val();
            refinanceTeaserRate.isIncludeTaxes = quesContxts[1].value;
            refinanceTeaserRate.propertyTaxesPaid = $('input[name="annualPropertyTaxes"]').val();
            refinanceTeaserRate.annualHomeownersInsurance = $('input[name="annualHomeownersInsurance"]').val();
            
           /* if(refinanceTeaserRate.currentMortgagePayment == "" || refinanceTeaserRate.currentMortgagePayment == null || refinanceTeaserRate.currentMortgagePayment == "$0" ){
            	
            	showToastMessage(message);
        		return false;
            }
            else if(refinanceTeaserRate.isIncludeTaxes =="Yes" && (refinanceTeaserRate.propertyTaxesPaid == "" || refinanceTeaserRate.propertyTaxesPaid == null || refinanceTeaserRate.propertyTaxesPaid !="$0") && (refinanceTeaserRate.annualHomeownersInsurance == "" || refinanceTeaserRate.annualHomeownersInsurance == null || refinanceTeaserRate.annualHomeownersInsurance != "$0")){
        		showToastMessage(message);
        		return false;
                
        	}else{
        		paintRefinanceHomeWorthToday();
        	}
        	*/
            paintRefinanceHomeWorthToday();
        });
        $('#ce-refinance-cp').append(saveAndContinueButton);
    }
   

function paintRefinanceHomeWorthToday() {
        stages = 4;
        progressBaar(4);
        var quesTxt = "Approximately what is your home worth today?";
        var quesCont = getTextQuestion(quesTxt, paintRefinanceHomeZipCode, "homeWorthToday");
        $('#ce-refinance-cp').html(quesCont);
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
            text: "Single Family Residence",
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
            text: "Primary Residence",
            value: "0"
        }, {
            text: "Vacation Home",
            value: "1"
        }, {
            text: "Second Home",
            value: "2"
        }],
        selected: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);
    
   
    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	
    	
        
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
        stages = 6;
        progressBaar(6);
        delete sessionStorage.refinaceData;
        var quesTxt = "Analyze & Adjust Your Numbers";
        var container = $('<div>').attr({
            "class": "ce-rate-main-container"
        });
        var quesTextCont = $('<div>').attr({
            "class": "ce-rp-ques-text"
        }).html(quesTxt);
        // alert(JSON.stringify(refinanceTeaserRate));
        container.append(quesTextCont);
        $(parentContainer).html(container);
        $('#overlay-loader').show();
        $.ajax({
            url: "rest/calculator/findteaseratevalue",
            type: "POST",
            data: {
                "teaseRate": JSON.stringify(teaserRateData)
            },
            datatype: "application/json",
            success: function(data) {
                $('#overlay-loader').hide();
                // var teaserRate = data;
                // paintteaserRate(data);
                  
                  paintFixYourRatePageCEP(JSON.parse(data), teaserRateData,parentContainer,hideCreateAccountBtn);
                 // paintFixYourRatePageCEP(teaserRate, refinanceTeaserRate);
            },
            error: function() {
                alert("error");
                $('#overlay-loader').hide();
            }
        });
        
    }
 

function progressBaar(num) {
        var count = 6;
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
    }
   

function paintApplyNow(inputCustomerDetails) {
  
    
    var registration = new Object();
    var parentWrapper = $('<div>').attr({
        "class": "container-row row clearfix"
    });
    var regMainContainer = $('<div>').attr({
        "class": "reg-main-container"
    });
    var regDisplayTitle = $('<div>').attr({
        "class": "reg-display-title"
    }).html("Lorem Ipsum Lorem Ipsum");
    var regDisplaySubTitle = $('<div>').attr({
        "class": "reg-display-title-subtxt"
    }).html("Lorem Ipsum is also knownas: Greeked Text, blind text, placeholder text, dummy content,filter text, lipsum, and mock-content");
    var regInputContainerFname = $('<div>').attr({
        "class": "reg-input-cont reg-fname"
    });
    var regInputfname = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "First Name",
        "name": "fname"
    });
    regInputContainerFname.append(regInputfname);
    var regInputContainerlname = $('<div>').attr({
        "class": "reg-input-cont reg-lname"
    });
    var regInputlname = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "Last Name",
        "name": "lname"
    });
    regInputContainerlname.append(regInputlname);
    var regInputContainerEmail = $('<div>').attr({
        "class": "reg-input-cont reg-email"
    });
    var regInputEmail = $('<input>').attr({
        "class": "reg-input",
        "placeholder": "Email",
        "name": "email"
    });
    regInputContainerEmail.append(regInputEmail);
    var regContainerGetStarted = $('<div>').attr({
        "class": "reg-btn-wrapper clearfix"
    });
    var regGetStarted = $('<div>').attr({
        "class": "reg-btn float-left",
    }).html("Get Started").bind('click', {
        "userDetails": registration
    }, function(event) {
        
    	registration.firstName = $('input[name="fname"]').val();
        registration.lastName = $('input[name="lname"]').val();
        var dateVar = new Date();
        var timezone = dateVar.getTimezoneOffset();
        registration.emailId = $('input[name="email"]').val() + ":" + timezone;
        

        var appUserDetails = new Object();
        var refinancedetails = new Object();
        var propertyTypeMaster = new Object();
        var purchaseDetails = new Object();
        var user = new Object();

        user.firstName = registration.firstName;
        user.lastName = registration.lastName;
        user.emailId = registration.emailId;
       
        loanType = {};
        loanType.loanTypeCd = inputCustomerDetails.loanType;
        appUserDetails.loanType = loanType;
        
        
        	if(inputCustomerDetails.isIncludeTaxes=="Yes"||inputCustomerDetails.isIncludeTaxes==true){
	        		inputCustomerDetails.isIncludeTaxes = true;
	        	}else if(inputCustomerDetails.isIncludeTaxes=="No"||inputCustomerDetails.isIncludeTaxes==false){
	        		inputCustomerDetails.isIncludeTaxes = false;
	        	}
        
        
        if(appUserDetails.loanType.loanTypeCd === 'REF'){
        	
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
            
            
            appUserDetails.refinancedetails = refinancedetails;
            appUserDetails.propertyTypeMaster = propertyTypeMaster;
            
        }else{
        	
            //purchaseDetails
         purchaseDetails.livingSituation =inputCustomerDetails.livingSituation;
   		 purchaseDetails.housePrice =inputCustomerDetails.purchaseDetails.housePrice;
   		 purchaseDetails.loanAmount = inputCustomerDetails.purchaseDetails.loanAmount;
   		 purchaseDetails.isTaxAndInsuranceInLoanAmt =inputCustomerDetails.purchaseDetails.isTaxAndInsuranceInLoanAmt; 
   		 purchaseDetails.estimatedPrice = inputCustomerDetails.estimatedPurchasePrice;
   		 purchaseDetails.buyhomeZipPri = inputCustomerDetails.zipCode;

   		 appUserDetails.monthlyRent = inputCustomerDetails.rentPerMonth;
   		 appUserDetails.purchaseDetails =purchaseDetails;
        }
     
        appUserDetails.user = user;
       
        
        // Where livingSituation should goes 
        //appUserDetails.purchaseDetails.livingSituation = refinancedetails.livingSituation;
        
       
       
        console.log(JSON.stringify(appUserDetails));
        // saveUserAndRedirect(appUserDetails,saveAndUpdateLoanAppForm(appUserDetails));
        saveUserAndRedirect(appUserDetails);
        // saveUserAndRedirect(registration);
    });
    regContainerGetStarted.append(regGetStarted);
    regMainContainer.append(regDisplayTitle);
    regMainContainer.append(regDisplaySubTitle);
    regMainContainer.append(regInputContainerFname);
    regMainContainer.append(regInputContainerlname);
    regMainContainer.append(regInputContainerEmail);
    regMainContainer.append(regContainerGetStarted);
    return parentWrapper.append(regMainContainer);
}

function saveUserAndRedirect(registration) {
    // alert(JSON.stringify(registration));
    $('#overlay-loader').show();
    $.ajax({
        url: "rest/shopper/registration",
        type: "POST",
        data: {
            "registrationDetails": JSON.stringify(registration)
        },
        datatype: "application/json",
        success: function(data) {
            // $('#overlay-loader').hide();
            $('#overlay-loader').hide();
            // alert (data);
            window.location.href = data;
            // printMedianRate(data,container);
        },
        error: function(data) {
           // alert(data);
            $('#ce-main-container').html(data.toString());
            // $('#overlay-loader').hide();
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
            success: function(data) {
              //  alert('inside appFormData');
                window.location.href = data;
            },
            error: function(erro) {
                alert("error");
            }
        });
    }


function paintFixYourRatePageCEP(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn) {

	var teaserRate =  modifiedLQBJsonRes(teaserRate);
    var loanSummaryWrapper = getLoanSummaryWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
    
    var closingCostWrapper = getClosingCostSummaryContainer(teaserRate);
  //  $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);
    if(!parentContainer)
        parentContainer=$('#ce-refinance-cp');
    $(parentContainer).append(loanSummaryWrapper).append(closingCostWrapper);
}

function getLoanSummaryWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
    
	
	var loanSummaryWrapper = getLoanSummaryWrapperTeaserRate(teaserRate, inputCustomerDetails);
    
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    
    var rateWrapper = getLoanSliderWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
    var bottomText = getHeaderText("Quoted Rates are not guaranteed. You may use this tool to check current rates or request a  rate lock. APR is an estimate based on an average $200,000 loan amount with 2% in total APR related fees. Actual ARP will be available on your Good Faith Estimate after Loan Amount and Income are Verified.");
   
    parentWrapper.append(loanSummaryWrapper).append(rateWrapper).append(bottomText);
    return parentWrapper;
}

function getLoanSummaryHeaderCEP() {
        var headerCont = $('<div>').attr({
            "class": "loan-summary-header clearfix"
        });
        var col1 = $('<div>').attr({
            "class": "loan-summary-header-col1 float-left uppercase"
        }).html('MY LOAN SUMMARY');
        var col2 = $('<div>').attr({
            "class": "loan-summary-header-col2 float-left"
        }).html("Rates as of 1/16/2015 8:13:52 AM");
        headerCont.append(col1).append(col2);
        return headerCont;
    }


function getLoanSliderWrapperCEP(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
  
  
    var wrapper = $('<div>').attr({
        "class": "lock-rate-slider-wrapper"
    });
    var header = $('<div>').attr({
        "class": "lock-rate-slider-header"
    }).html("select loan program");
    var container = $('<div>').attr({
        "class": "lock-rate-slider-container"
    });
    
    var tenureSlider = getYearSliderContCEP1(teaserRate,inputCustomerDetails);
    var rateSlider = getRateSliderContCEP(teaserRate,inputCustomerDetails);
    
    container.append(tenureSlider).append(rateSlider);
    var rateBtn1="";
    var rateBtn2="";
    if(hideCreateAccountBtn==undefined || hideCreateAccountBtn){
        rateBtn1= $('<div>').attr({
            "class": "rate-btn"
        }).html("Create Your Account").on('click', function() {
            var mainContainer = paintApplyNow(inputCustomerDetails);
            $('#ce-main-container').html(mainContainer);
        });
        rateBtn2 = $('<div>').attr({
            "class": "rate-btn-alertRate"
        }).html("Email My Number").on('click', function() {
            var mainContainer = paintApplyNow(inputCustomerDetails);
            $('#ce-main-container').html(mainContainer);
        });
    }
    return wrapper.append(header).append(container).append(rateBtn1).append(rateBtn2);;
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
                var rateSlider = getRatSliderCEP(event.data.ratesArray,inputCustomerDetails);
                $('#rate-slider-cont').append(rateSlider);
                index = parseInt(event.data.ratesArray.length / 2);
               
                $('#aprid').html(event.data.ratesArray[index].APR + " %");
                $('#closingCostId').html(event.data.ratesArray[index].closingCost);
                $('#teaserRateId').html(parseFloat(event.data.ratesArray[index].teaserRate).toFixed(2)+" %");
               
                if(event.data.year =='5' || event.data.year =="7")
                $('#loanprogramId').html(event.data.year +" Year ARM");
                else
                $('#loanprogramId').html(event.data.year +" Year Fixed");
                
                $('#principalIntId').html(showValue(event.data.ratesArray[index].payment));
                
                
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
    index = parseInt(rateArray.length / 2);
    var silderCont = getRatSliderCEP(rateArray,inputCustomerDetails);
    
    return wrapper.append(headerTxt).append(silderCont);
}

function getRatSliderCEP(gridArray,inputCustomerDetails) {
    
	
	var rateArray = [];
    for (var i = 0; i < gridArray.length; i++) {
        rateArray[i] = parseFloat(gridArray[i].teaserRate).toFixed(2);
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
            $('#closingCostId').html(gridArray[ui.value].closingCost);
            $('#teaserRateId').html(parseFloat(gridArray[ui.value].teaserRate).toFixed(2) +" %");
            $('#principalIntId').html(showValue(gridArray[ui.value].payment));
            
            teaseCalculation(inputCustomerDetails);
            
        }
    });
    container.append(tsIcon);
    var gridItemCont = $('<div>').attr({
        "class": "rt-grid-cont"
    });
    for (var i = 0; i < gridArray.length; i++) {
        var leftOffset = i / (gridArray.length - 1) * 100;
        var gridItem = $('<div>').attr({
            "class": "rt-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(parseFloat(gridArray[i].teaserRate).toFixed(2) + "%");
        gridItemCont.append(gridItem);
    }
    return container.append(gridItemCont);
}


function teaseCalculation(inputCustomerDetails){
	
	var taxesTemp = 0;
	 var InsuranceTemp = 0;
	var principalInterest = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
	if($('#calTaxID2').val() != "Calculate") 
	taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));
    
	if($('#CalInsuranceID2').val() != "Calculate") 
	InsuranceTemp =  parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
    var  monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.currentMortgagePayment)));  	
    	
    
    var investment = (InsuranceTemp + taxesTemp)/12;
	var monthlyPaymentTemp = monthlyPayment -investment ;
	monthlyPayment = monthlyPaymentTemp.toFixed(2);
	
	var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment)).toFixed(2);
	var totalEstMonthlyPaymentId = (principalInterest + investment).toFixed(2);
	
	$('#monthlyPaymentId').text(showValue(monthlyPayment));
	$('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
	$('#totalEstMonthlyPaymentId').text((showValue(totalEstMonthlyPaymentId)));
	
}

function getLoanSummaryWrapperTeaserRate(teaserRate, inputCustomerDetails) {
    
	
	var customerInputData = inputCustomerDetails;
    loanTypeText = customerInputData.loanType;
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var header = getLoanSummaryHeaderCEP();
    var container;
    if (loanTypeText == "REF") {
        container = getLoanSummaryContainerRefinanceCEP(teaserRate, customerInputData);
    } else if (loanTypeText == "PUR") {
        container = getLoanSummaryContainerPurchaseCEP(teaserRate, customerInputData);
    }
    
    parentWrapper.append(header).append(container);
    return parentWrapper;
}

function getLoanSummaryHeaderCEP() {
    var headerCont = $('<div>').attr({
        "class": "loan-summary-header clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 float-left uppercase"
    }).html('MY LOAN SUMMARY');
    var col2 = $('<div>').attr({
        "class": "loan-summary-header-col2 float-left"
    }).html("Rates as of 1/16/2015 8:13:52 AM");
    headerCont.append(col1).append(col2);
    return headerCont;
}

function getLoanSummaryContainerRefinanceCEP(teaserRate, customerInputData) {
    
	
	var yearValues = teaserRate;
   
	var rateVO = yearValues[yearValues.length-1].rateVO;
    var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
   
    var container = $('<div>').attr({
        "class": "loan-summary-container clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    
   
    var loanAmount  = customerInputData.currentMortgageBalance;
    
    if (customerInputData.refinanceOption == "REFLMP") refinanceOpt = "Lower My Monthly Payment";
    if (customerInputData.refinanceOption == "REFMF") refinanceOpt = "Pay Off My Mortgage Faster";
    if (customerInputData.refinanceOption == "REFCO"){
    	refinanceOpt = "Take Cash Out of My Home";
    	
        var cashTakeOut = getFloatValue(customerInputData.cashTakeOut);
        var currentMortgageBalance = getFloatValue(customerInputData.currentMortgageBalance);
    	loanAmount = cashTakeOut + currentMortgageBalance;
    }
   
    var  monthlyPayment  = parseFloat(removedDoller(removedComma(customerInputData.currentMortgagePayment))); 
    var Insurance ="Calculate";
    var tax = "Calculate";
    var principalInterest = parseFloat(removedDoller(removedComma(rateVO[index].payment)));
    var totalEstMonthlyPayment = principalInterest;
    
    if(customerInputData.isIncludeTaxes =="Yes"){
    	var InsuranceTemp =  parseFloat(removedDoller(removedComma(customerInputData.annualHomeownersInsurance)));
    	var taxesTemp =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    	var investment = (InsuranceTemp + taxesTemp)/12;
    	var monthlyPaymentTemp = monthlyPayment -investment ;
    	monthlyPayment = monthlyPaymentTemp.toFixed(2);
    	
    	
    	 Insurance = customerInputData.annualHomeownersInsurance;
    	 tax = customerInputData.propertyTaxesPaid;
    	 
    	 var totalEstMonthlyPaymentTemp  = principalInterest + investment;
    	 totalEstMonthlyPayment = totalEstMonthlyPaymentTemp.toFixed(2);
    }
    
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment)).toFixed(2);

    
    var lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - " + refinanceOpt);
    var lcRow2 = getLoanSummaryRow("Loan Program", yearValues[yearValues.length-1].value +" Year Fixed","loanprogramId");
    var lcRow3 = getLoanSummaryRow("Interest Rate", parseFloat(rateVO[index].teaserRate).toFixed(3)+" %", "teaserRateId");
    
    if(customerInputData.refinanceOption != "REFCO")
    var lcRow4 = getLoanAmountRowCEP("Loan Amount", showValue(loanAmount));
    else
    var lcRow4 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "lockloanAmountid","Current Loan Amout","$ "+currentMortgageBalance, "Cashout","$ "+cashTakeOut,true);	
    
    var lcRow5 = getLoanSummaryRow("APR", rateVO[index].APR +" %", "aprid");
    var lcRow6 = getLoanSummaryLastRow("Estimated<br/>Closing Cost", showValue(rateVO[index].closingCost), "closingCostId");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5).append(lcRow6);
    
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Principal Interest",showValue(rateVO[index].payment),"principalIntId");
    var rcRow2 = getLoanSummaryRowCalculateBtnCEP("Tax", tax,"calTaxID","calTaxID2",customerInputData);
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtnCEP("Insurance", Insurance,"CalInsuranceID","CalInsuranceID2",customerInputData);
   
    var rcRow6 = getLoanSummaryRow("Current Monthly Payment ", showValue(monthlyPayment) ,"monthlyPaymentId");
    var rcRow7 = getLoanSummaryRow("Monthly Payment Difference  ", showValue(monthlyPaymentDifference) ,"monthlyPaymentDifferenceId");
    var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow6).append(rcRow7).append(rcRow8);
    container.append(leftCol).append(rightCol);
    return container;
}


function getLoanSummaryContainerPurchaseCEP(teaserRate, customerInputData) {
    
	var livingSituation = capitalizeFirstLetter(customerInputData.livingSituation);
	
	var yearValues = teaserRate;
	   
	var rateVO = yearValues[yearValues.length-1].rateVO;
    var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
	
   
    var housePrice = parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.housePrice)));   
    var loanAmount =  parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.loanAmount))) ;    
    var downPayment = (housePrice-loanAmount);
    
   /* var principalInterest = parseFloat(removedDoller(removedComma(rateVO[index].payment)));
    var totalEstMonthlyPayment = principalInterest;
    */
  
    
    
	var container = $('<div>').attr({
        "class": "loan-summary-container clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
    //var lcRow1 = getLaonSummaryApplyBtnRow();
    var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase -"+livingSituation);
    var lcRow2 = getLoanSummaryRow("Loan Program", yearValues[yearValues.length-1].value +" Year Fixed","loanprogramId");
    var lcRow3 =  getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "lockloanAmountid","Purchase Amount","$ "+housePrice, " Down Payment","$ "+downPayment);
    //var lcRow4 = getLoanSummaryRow("Down Payment", "");
    //var lcRow5 = getLoanSummaryRow("Purchase Amount", estimatedPrice);
    var lcRow4 = getLoanSummaryRow("Interest Rate", parseFloat(rateVO[index].teaserRate).toFixed(3) +" %", "teaserRateId");
    //var lcRow7 = getLoanSummaryRow("Loan Amount", loanAmount);
    var lcRow5 = getLoanSummaryRow("APR", rateVO[index].APR +" %", "aprid");
    var lcRow6 = getLoanSummaryLastRow("Estimated<br/>Closing Cost", showValue(rateVO[index].closingCost), "closingCostId");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5).append(lcRow6);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 ="";
    if(customerInputData.livingSituation != "homeOwner")
         rcRow1 = getLoanSummaryRow("Current Monthly Payment", showValue(customerInputData.purchaseDetails.rentPerMonth));
    var rcRow2 = getLoanSummaryRow("Principal Interest", showValue(rateVO[index].payment),"principalIntId");
    var rcRow3 = getLoanSummaryRowCalculateBtnCEP("Tax", "Calculate","calTaxID","calTaxID2",customerInputData);
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtnCEP("Insurance", "Calculate","CalInsuranceID","CalInsuranceID2",customerInputData);
    //var rcRow5 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal 																			& interest payment");
    var rcRow5 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(rateVO[index].payment) ,"totalEstMonthlyPaymentId");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4).append(rcRow5);
    container.append(leftCol).append(rightCol);
    return container;
}

function getLoanAmountRowCEP(desc, detail, id) {
   
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
        "class": "loan-summary-col-detail float-left",
        "id": id
    }).html(detail);
   
    /*var dropdownarrow = $('<div>').attr({
        "class": "dropdown-arrow"
    }).bind('click', function() {
        $('#loan-amount-details').toggle();
    });
    col2.append(dropdownarrow);
    loanAmountCont.append(col1).append(col2);
    var loanAmountDetails = $('<div>').attr({
        "id": "loan-amount-details",
        "class": "loan-amount-details hide"
    });
    var row1 = $('<div>').attr({
        "class": "loan-summary-sub-row clearfix"
    });
    var col1row1 = $('<div>').attr({
        "class": "loan-summary-sub-col-desc float-left"
    }).html("Current Loan Amount");
    var col2row1 = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left"
    }).val("$ 303,000.00");
    row1.append(col1row1).append(col2row1);
    var row2 = $('<div>').attr({
        "class": "loan-summary-sub-row clearfix"
    });
    var col1row2 = $('<div>').attr({
        "class": "loan-summary-sub-col-desc float-left"
    }).html("Cashout");
    var col2row2 = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left"
    }).val("$ 70,000.00");
    row2.append(col1row2).append(col2row2);
    loanAmountDetails.append(row1).append(row2);
    return container.append(loanAmountCont).append(loanAmountDetails);*/
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
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left"
    });
    
    var col2Txt = $('<div>').attr({
    	"class" : "calculate-btn",
    	"id":id

    }).html(detail)
    .bind('click',{"valueData":$(this).text()},function(event){
    	var prevVal = $(this).text();
    	if(prevVal!="Calculate")
    		$(this).next('input').show().focus().val(prevVal);
    	else{
    		$(this).next('input').show().focus().val("");
    	}
    	$(this).hide();
    	
    });
    
    var inputBox = $('<input>').attr({
    	"class" : "loan-summary-sub-col-detail hide",
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
    	
    	if(e.which == 27){
    		var prevVal = $(this).prev('.calculate-btn').text();
    		if($(this).val() == undefined || $(this).val() == prevVal){
    			$(this).hide();
    			$(this).prev('.calculate-btn').show();
    		}
    	}
    	
    	var taxesTemp = 0;
    	if($('#calTaxID2').val() !="Calculate")
    	 taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));    	
    	
    	var InsuranceTemp = 0;
    	if($('#CalInsuranceID2').val() !="Calculate")
    	 InsuranceTemp = parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
    	
    	var monthlyPayment  = 0;
    	if(customerInputData.loanType =="REF")
    		monthlyPayment = parseFloat(removedDoller(removedComma(customerInputData.currentMortgagePayment))); 
    	/*else
    		monthlyPayment =  parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.rentPerMonth)));*/
    	
    	var investment = (InsuranceTemp + taxesTemp)/12;
    	var monthlyPaymentTemp = monthlyPayment - investment ;  	
    	monthlyPayment = monthlyPaymentTemp.toFixed(2);
    	
    	var principalInt = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
    	
    	var monthlyPaymentDifferenceTemp = Math.abs(principalInt - monthlyPayment);
    	var monthlyPaymentDifference = monthlyPaymentDifferenceTemp.toFixed(2);
    	var totalEstMonthlyPaymentId =  (principalInt + investment).toFixed(2);
    	
    	
    	$('#monthlyPaymentId').text(showValue(monthlyPayment));
    	$('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
    	$('#totalEstMonthlyPaymentId').text(showValue(totalEstMonthlyPaymentId));
    	
    	
    });
    $(inputBox).val(detail);
    
    col2.append(col2Txt).append(inputBox);
    container.append(col1).append(col2);
    return container;
}





/*function getYearSliderContCEP() {
    var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Length of Loan");
    var silderCont = getYearSlider();
    return wrapper.append(headerTxt).append(silderCont);
}*/

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

