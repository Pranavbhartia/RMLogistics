//JavaScript functions for customer engagement pages
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
                allowNegative: true
            });
        }
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
                    allowNegative: true
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
            alert('json'+JSON.stringify(refinanceTeaserRate));
            sessionStorage.refinaceData = JSON.stringify(refinanceTeaserRate);
          
            if (inputValue != undefined && inputValue != "" && inputValue != "$0") {
                event.data.clickEvent();
            } else {
                showToastMessage("Please give awnsers of the questions");
            }
        });
        return container.append(quesTextCont).append(optionContainer).append(saveBtn);
    }
    /*
     * function paintRefinanceLiveNow() { stages = 2; progressBaar(2); var quesTxt1 =
     * "Where You Live Now ?"; var quesTxt2 = "Your Current Address ?"; var quesTxt3 =
     * "City"; var quesTxt4 = "State"; var quesTxt5 = "ZIP Code";
     *
     * var container = $('<div>').attr({ "class" : "ce-ques-wrapper" });
     *
     * var quesTextCont1 = $('<div>').attr({ "class" : "ce-rp-ques-text"
     * }).html(quesTxt1);
     *
     * var optionContainer1 = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox1 = $('<input>').attr({ "class" : "ce-input", "name" :
     * "liveNow", "value" : refinanceTeaserRate["liveNow"] });
     *
     * var quesTextCont2 = $('<div>').attr({ "class" : "ce-rp-sub-ques-text"
     * }).html(quesTxt2);
     *
     * var optionContainer2 = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox2 = $('<input>').attr({ "class" : "ce-input", "name" :
     * "currentAddress", "value" : refinanceTeaserRate["currentAddress"] });
     *
     * var quesTextCont3 = $('<div>').attr({ "class" : "ce-rp-sub-ques-text"
     * }).html(quesTxt3);
     *
     * var optionContainer3 = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox3 = $('<input>').attr({ "class" : "ce-input", "name" : "city",
     * "value" : refinanceTeaserRate["city"] });
     *
     * var quesTextCont4 = $('<div>').attr({ "class" : "ce-rp-sub-ques-text"
     * }).html(quesTxt4);
     *
     * var optionContainer4 = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox4 = $('<input>').attr({ "class" : "ce-input", "name" : "state",
     * "value" : refinanceTeaserRate["state"] });
     *
     * var quesTextCont5 = $('<div>').attr({ "class" : "ce-rp-sub-ques-text"
     * }).html(quesTxt5);
     *
     * var optionContainer5 = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox5 = $('<input>').attr({ "class" : "ce-input", "name" :
     * "zipCode", "value" : refinanceTeaserRate["zipCode"] });
     *
     * //optionContainer1.append(inputBox1); optionContainer2.append(inputBox2);
     * optionContainer3.append(inputBox3); optionContainer4.append(inputBox4);
     * optionContainer5.append(inputBox5);
     *
     * //quesTextCont1.append(optionContainer1);
     * quesTextCont2.append(optionContainer2);
     * quesTextCont3.append(optionContainer3);
     * quesTextCont4.append(optionContainer4);
     * quesTextCont5.append(optionContainer5);
     *
     * var saveBtn = $('<div>').attr({ "class" : "ce-save-btn" }).html("Save &
     * Continue").bind( 'click', function(event) {
     *
     * refinanceTeaserRate["liveNow"] = $('input[name="liveNow"]') .val();
     * refinanceTeaserRate["currentAddress"] =
     * $('input[name="currentAddress"]').val(); refinanceTeaserRate["city"] =
     * $('input[name="city"]').val(); refinanceTeaserRate["state"] =
     * $('input[name="state"]').val(); refinanceTeaserRate["zipCode"] =
     * $('input[name="zipCode"]').val();
     *
     * paintRefinanceStartLiving(); sessionStorage.refinaceData =
     * JSON.stringify(refinanceTeaserRate);
     *
     * });
     *
     * $('#ce-refinance-cp').html(
     * container.append(quesTextCont1).append(quesTextCont2).append(
     * quesTextCont3).append(quesTextCont4).append(quesTextCont5) .append(saveBtn));
     *  // $('#ce-refinance-cp').html(quesCont); }
     */
    /*
     * function paintRefinanceCurrentAddress() {
     *
     * var quesTxt = "What Is Your Current Address?"; var quesCont =
     * getTextQuestion(quesTxt, paintRefinanceCity,"currentAddress");
     * $('#ce-refinance-cp').html(quesCont); }
     *
     * function paintRefinanceCity() {
     *
     * var quesTxt = "What Is Your City?"; var quesCont = getTextQuestion(quesTxt,
     * paintRefinanceState, "city"); $('#ce-refinance-cp').html(quesCont);
     *  }
     *
     * function paintRefinanceState() {
     *
     * var quesTxt = "What Is Your State?"; var quesCont = getTextQuestion(quesTxt,
     * paintRefinanceZip, "state"); $('#ce-refinance-cp').html(quesCont);
     *  }
     *
     * function paintRefinanceZip() {
     *
     * var quesTxt = "What Is Your ZipCode?"; var quesCont =
     * getTextQuestion(quesTxt, paintRefinanceStartLiving, "zipCode");
     * $('#ce-refinance-cp').html(quesCont);
     *  }
     */
    /*
     * function paintRefinanceStartLiving() { stages = 3; progressBaar(3); var
     * quesTxt = "When did you start living here?"; var quesCont =
     * getTextQuestion(quesTxt, paintRefinanceStep1a, "startLiving");
     *
     * $('#ce-refinance-cp').html(quesCont); }
     */
function paintRefinanceStep1a() {
    var quesTxt = "How many years are left on your mortgage?";
    var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2, "yearLeftOnMortgage");
    $('#ce-refinance-cp').html(quesCont);
}
quesContxts = [];
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
        paintRefinanceStep3();
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
        // progressBaar(3);
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
                "text": "No"
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
            paintRefinanceHomeWorthToday();
        });
        $('#ce-refinance-cp').append(saveAndContinueButton);
    }
    /*
     * function paintRefinanceStep4() { var quesTxt = "Does the monthly mortgage
     * payment entered above include property taxes and/or homeowners insurance?";
     * var options = [ { "text" : "Yes", "onselect" :
     * paintRefinanceAnnualPropertyTaxes, "name" : name, "value" : 1 }, { "text" :
     * "No", "onselect" : paintRefinanceHomeWorthToday, "name" : name, "value" : 0 } ];
     *
     * var quesCont = getMutipleChoiceQuestion(quesTxt, options, "includeTaxes");
     * $('#ce-refinance-cp').html(quesCont); }
     */
function paintRefinanceHomeWorthToday() {
        stages = 4;
        progressBaar(4);
        var quesTxt = "Approximately what is your home worth today?";
        var quesCont = getTextQuestion(quesTxt, paintRefinanceHomeZipCode, "homeWorthToday");
        $('#ce-refinance-cp').html(quesCont);
    }
    /*
     * function paintRefinanceAnnualPropertyTaxes() { var quesTxt = "How much is
     * your annual property taxes?"; var quesCont = getTextQuestion(quesTxt,
     * paintRefinanceAnnualHomeownersInsurance, "annualPropertyTaxes");
     * $('#ce-refinance-cp').html(quesCont); }
     *
     * function paintRefinanceAnnualHomeownersInsurance() { var quesTxt = "How much
     * is your annual homeowners insurance?"; var quesCont =
     * getTextQuestion(quesTxt, paintRefinanceHomeWorthToday,
     * "annualHomeownersInsurance"); $('#ce-refinance-cp').html(quesCont); }
     */
function paintRefinanceHomeZipCode() {
        stages = 5;
        progressBaar(5);
        var quesTxt = "What is the zip code of your home?";
        var quesCont = getTextQuestion(quesTxt, paintRefinanceSeeRates, "zipCode");
        $('#ce-refinance-cp').html(quesCont);
    }
    /*
     * function paintRefinanceMyIncome() {
     *
     * var quesTxt = "Select all that apply"; var options = [ { "text" : "Employed",
     * "onselect" : paintRefinanceEmployed, "name" : name, "value" : 0 }, { "text" :
     * "Self-employed", "onselect" : paintRefinanceSelfEmployed, "name" : name,
     * "value" : 1 }, { "text" : "Social Security Income/Disability", "onselect" :
     * paintRefinanceDisability, "name" : name, "value" : 2 }, { "text" :
     * "Pension/Retirement/401(k)", "onselect" : paintRefinancePension, "name" :
     * name, "value" : 3 } ]; var quesCont = paintRefinanceMyMoney(quesTxt, options,
     * name);
     *
     * $('#ce-refinance-cp').html(quesCont);
     *  }
     *
     * function paintRefinanceEmployed(divId) {
     *
     * var quesTxt = "About how much do you make a year"; var quesCont =
     * getMultiTextQuestion(quesTxt); $('#ce-option_' + divId).toggle();
     * $('#ce-option_' + divId).html(quesCont); }
     *
     * function paintRefinanceSelfEmployed(divId) {
     *
     * var quesTxt = "How much do you make a year?";
     *
     * var container = $('<div>').attr({ "class" : "ce-ques-wrapper" });
     *
     * var quesTextCont = $('<div>').attr({ "class" : "ce-option-text"
     * }).html(quesTxt);
     *
     * var optionContainer = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox = $('<input>').attr({ "class" : "ce-input", "name" :
     * "selfEmployed" });
     *
     * optionContainer.append(inputBox);
     * container.append(quesTextCont).append(optionContainer);
     *
     * $('#ce-option_' + divId).toggle(); $('#ce-option_' + divId).html(container); }
     *
     * function paintRefinanceDisability(divId) {
     *
     * var quesTxt = "About how much do you get monthly?";
     *
     * var container = $('<div>').attr({ "class" : "ce-ques-wrapper" });
     *
     * var quesTextCont = $('<div>').attr({ "class" : "ce-option-text"
     * }).html(quesTxt);
     *
     * var optionContainer = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox = $('<input>').attr({ "class" : "ce-input", "name" :
     * "disability" });
     *
     * optionContainer.append(inputBox);
     * container.append(quesTextCont).append(optionContainer);
     *
     * $('#ce-option_' + divId).toggle(); $('#ce-option_' + divId).html(container); }
     *
     * function paintRefinancePension(divId) {
     *
     * var quesTxt = "About how much do you get monthly?";
     *
     * var container = $('<div>').attr({ "class" : "ce-ques-wrapper" });
     *
     * var quesTextCont = $('<div>').attr({ "class" : "ce-option-text"
     * }).html(quesTxt);
     *
     * var optionContainer = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * var inputBox = $('<input>').attr({ "class" : "ce-input", "name" : "pension"
     * });
     *
     * optionContainer.append(inputBox);
     * container.append(quesTextCont).append(optionContainer);
     *
     * $('#ce-option_' + divId).toggle(); $('#ce-option_' + divId).html(container); }
     *
     * function paintRefinanceMyMoney(quesText, options, name) { var container = $('<div>').attr({
     * "class" : "ce-ques-wrapper" });
     *
     * var quesTextCont = $('<div>').attr({ "class" : "ce-rp-ques-text"
     * }).html(quesText);
     *
     * var optionContainer = $('<div>').attr({ "class" : "ce-options-cont" });
     *
     * for (var i = 0; i < options.length; i++) {
     *
     * var optionIncome = $('<div>').attr({ "class" : "hide
     * ce-option-ques-wrapper", "id" : "ce-option_" + i });
     *
     * var option = $('<div>').attr({ "class" : "ce-option-checkbox", "value" :
     * options[i].value }).html(options[i].text).bind('click', { "option" :
     * options[i], "name" : name }, function(event) {
     * $('.ce-option-checkbox').removeClass("ce-option-checked");
     * $(this).addClass("ce-option-checked"); var key = event.data.name;
     * refinanceTeaserRate[key] = event.data.option.value;
     * event.data.option.onselect(event.data.option.value); });
     *
     * optionContainer.append(option).append(optionIncome); }
     *
     * var saveBtn = $('<div>').attr({ "class" : "ce-save-btn" }).html("Save &
     * Continue").bind( 'click', function() { refinanceTeaserRate["beforeTax"] =
     * $('input[name="beforeTax"]') .val(); refinanceTeaserRate["workPlace"] =
     * $('input[name="workPlace"]') .val(); refinanceTeaserRate["startWorking"] = $(
     * 'input[name="startWorking"]').val(); refinanceTeaserRate["selfEmployed"] = $(
     * 'input[name="selfEmployed"]').val(); refinanceTeaserRate["disability"] = $(
     * 'input[name="disability"]').val(); refinanceTeaserRate["pension"] =
     * $('input[name="pension"]') .val();
     *
     * sessionStorage.refinaceData = JSON .stringify(refinanceTeaserRate);
     * paintRefinanceDOB(); });
     *
     * return container.append(quesTextCont).append(optionContainer).append(
     * saveBtn); }
     */
    /*
     * function getMultiTextQuestion(quesText) { var container = $('<div>').attr({
     * "class" : "ce-ques-wrapper", });
     *
     * var quesTextCont = $('<div>').attr({ "class" : "ce-option-text",
     * }).html(quesText);
     *
     * var optionContainer = $('<div>').attr({ "class" : "ce-options-cont", });
     *
     * var quesTextCont1 = $('<div>').attr({ "class" : "ce-rp-ques-text",
     * }).html("Before Tax");
     *
     * var inputBox1 = $('<input>').attr({ "class" : "ce-input", "name" :
     * "beforeTax", });
     *
     * quesTextCont1.append(inputBox1);
     *
     * var quesTextCont2 = $('<div>').attr({ "class" : "ce-rp-ques-text"
     * }).html("Where Do You Work ?");
     *
     * var inputBox2 = $('<input>').attr({ "class" : "ce-input", "name" :
     * "workPlace" });
     *
     * quesTextCont2.append(inputBox2);
     *
     * var quesTextCont3 = $('<div>').attr({ "class" : "ce-rp-ques-text"
     * }).html("When Did You Start Wokring ?");
     *
     * var inputBox3 = $('<input>').attr({ "class" : "ce-input", "name" :
     * "startWorking" });
     *
     * quesTextCont3.append(inputBox3);
     *
     * optionContainer.append(quesTextCont1).append(quesTextCont2).append(
     * quesTextCont3);
     *
     * return container.append(quesTextCont).append(optionContainer); }
     */
    /*
     * function paintRefinanceDOB() { stages = 5; progressBaar(5); var quesTxt =
     * "Please enter your birthdate."; var quesCont = getTextQuestion(quesTxt,
     * paintRefinanceSSN, "dob"); $('#ce-refinance-cp').html(quesCont); }
     *
     * function paintRefinanceSSN() {
     *
     * var quesTxt = "Please enter your social security number."; var quesCont =
     * getTextQuestion(quesTxt, paintRefinanceCreditScore, "ssn");
     * $('#ce-refinance-cp').html(quesCont);
     *  }
     *
     * function paintRefinanceCreditScore() {
     *
     * if (refinanceTeaserRate["ssn"] == "" || refinanceTeaserRate["ssn"] ==
     * undefined) {
     *
     * var quesTxt = "Please give Your Credit Score"; var quesCont =
     * getTextQuestion(quesTxt, paintRefinancePhoneNumber, "creditscore");
     * $('#ce-refinance-cp').html(quesCont); } else { paintRefinancePhoneNumber(); }
     *  }
     *
     * function paintRefinancePhoneNumber() {
     *
     * var quesTxt = "Please enter your phone number"; var quesCont =
     * getTextQuestion(quesTxt, paintRefinanceSeeRates, "phoneNumber");
     * $('#ce-refinance-cp').html(quesCont); }
     */
function paintRefinanceSeeRates() {
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
        $('#ce-refinance-cp').html(container);
        $('#overlay-loader').show();
        $.ajax({
            url: "rest/calculator/findteaseratevalue",
            type: "POST",
            data: {
                "teaseRate": JSON.stringify(refinanceTeaserRate)
            },
            datatype: "application/json",
            success: function(data) {
                $('#overlay-loader').hide();
                // var teaserRate = data;
                // paintteaserRate(data);
                  
                  //paintFixYourRatePageCEP(JSON.parse(data), refinanceTeaserRate);
                  paintFixYourRatePageCEP(teaserRate, refinanceTeaserRate);
            },
            error: function() {
                alert("error");
                $('#overlay-loader').hide();
            }
        });
        // paintFixYourRatePageCEP(refinanceTeaserRate);
    }
    /*
     * function printMedianRate(data,container){ teaserresult = JSON.parse(data);
     * var rateVoJson; var counter = 0; var rateAndClosingCost =0;
     * //alert(teaserresult);
     *
     * for (var j in teaserresult) {
     *
     * var loanDuration = teaserresult[j].loanDuration;
     * console.log("loanDuration"+loanDuration);
     *
     * if ("30 YR FIXED CONFORMING".match(".*\\b" + loanDuration + "\\b.*")) {
     * console.log('inside if'); // alert('inside if');
     *
     *
     *
     * rateVoJson = JSON.stringify(teaserresult[j].rateVO); rateVoJsonArrayLength =
     * JSON.parse(rateVoJson).length;
     *
     * index = parseInt(rateVoJsonArrayLength / 2); rateAndClosingCost =
     * JSON.parse(rateVoJson)[index]; counter ++; console.log(counter);
     * console.log(rateVoJson); console.log(rateVoJsonArrayLength);
     * console.log(index); console.log(rateAndClosingCost); break; } }
     *
     *
     *
     * if (rateAndClosingCost === undefined) { alert("No rate is present"); }
     * //alert(rateAndClosingCost.teaserRate); console.log(" median rate
     * :"+rateAndClosingCost.teaserRate);
     *
     * var rateresult = $('<div>').attr({ "class" : "ce-rate-result
     * cp-est-cost-btn" }).html(rateAndClosingCost.teaserRate);
     * container.append(rateresult); $('#ce-refinance-cp').append(rateresult);
     *
     *
     *  }
     */
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
    /*
     * function teaserFixYourRatePage() { $('#ce-refinance-cp').html(''); var
     * rateProgramWrapper = getRateProgramContainer(); //var loanSummaryWrapper =
     * getLoanSummaryWrapper(); //var closingCostWrapper =
     * getClosingCostSummaryContainer();
     * //$('#ce-refinance-cp').append(rateProgramWrapper).append(loanSummaryWrapper) //
     * .append(closingCostWrapper);
     * $('#ce-refinance-cp').append(rateProgramWrapper); }
     */
function paintApplyNow(inputCustomerDetails) {
   
    console.log(JSON.stringify(inputCustomerDetails));
   // alert('input'+JSON.stringify(inputCustomerDetails))
    // var refinanceTeaserRate = JSON.parse(refinanceTeaserRate) ;
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
        
        if(appUserDetails.loanType.loanTypeCd === 'REF'){
        	
        	refinancedetails.refinanceOption = inputCustomerDetails.refinanceOption;
            refinancedetails.mortgageyearsleft=inputCustomerDetails.yearLeftOnMortgage;
            refinancedetails.cashTakeOut=inputCustomerDetails.cashTakeOut;
            refinancedetails.currentMortgageBalance = inputCustomerDetails.currentMortgageBalance;
            refinancedetails.currentMortgagePayment = inputCustomerDetails.currentMortgagePayment;
            refinancedetails.isIncludeTaxes = inputCustomerDetails.isIncludeTaxes;
            
            propertyTypeMaster.propertyTaxesPaid = inputCustomerDetails.propertyTaxesPaid;
            propertyTypeMaster.propertyInsuranceCost = inputCustomerDetails.annualHomeownersInsurance;
            propertyTypeMaster.homeWorthToday = inputCustomerDetails.homeWorthToday;
            propertyTypeMaster.homeZipCode = inputCustomerDetails.zipCode;
            
            
            appUserDetails.refinancedetails = refinancedetails;
            appUserDetails.propertyTypeMaster = propertyTypeMaster;
            
        }else{
        	
            //purchaseDetails
         purchaseDetails.livingSituation =inputCustomerDetails.livingSituation;
   		 purchaseDetails.housePrice =inputCustomerDetails.housePrice;
   		 purchaseDetails.loanAmount = inputCustomerDetails.loanAmount;
   		 purchaseDetails.isTaxAndInsuranceInLoanAmt =inputCustomerDetails.isIncludeTaxes; 
   		 purchaseDetails.estimatedPrice = inputCustomerDetails.estimatedPurchasePrice;
   		 purchaseDetails.buyhomeZipPri = inputCustomerDetails.zipCode;

   		 appUserDetails.monthlyRent = inputCustomerDetails.rentPerMonth;
   		 appUserDetails.purchaseDetails =purchaseDetails;
        }
     
        appUserDetails.user = user;
       
        
        // Where livingSituation should goes 
        //appUserDetails.purchaseDetails.livingSituation = refinancedetails.livingSituation;
        
        // alert('hey');
        // alert(JSON.stringify(appUserDetails));
       
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
            alert(data);
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
                alert('inside appFormData');
                window.location.href = data;
            },
            error: function(erro) {
                alert("error");
            }
        });
    }
    // notify me for the rates alerts
    /*
     * function paintNotifyForRatesAlerts(){
     *
     * var registration = new Object (); var parentWrapper = $('<div>').attr({
     * "class" : "container-row row clearfix" });
     *
     * var regMainContainer = $('<div>').attr({ "class" : "reg-main-container" });
     *
     * var regDisplayTitle = $('<div>').attr({ "class": "reg-display-title"
     *
     * }).html("Lorem Ipsum Lorem Ipsum");
     *
     * var regDisplaySubTitle = $('<div>').attr({
     * "class":"reg-display-title-subtxt"
     *
     * }).html("Lorem Ipsum is also knownas: Greeked Text, blind text, placeholder
     * text, dummy content,filter text, lipsum, and mock-content");
     *
     * var regInputContainerFname = $('<div>').attr({ "class":"reg-input-cont
     * reg-fname"
     *
     * });
     *
     * var regInputfname = $('<input>').attr({ "class":"reg-input",
     * "placeholder":"First Name", "name":"fname"
     *
     * });
     *
     * regInputContainerFname.append(regInputfname);
     *
     * var regInputContainerlname = $('<div>').attr({ "class":"reg-input-cont
     * reg-lname"
     *
     * });
     *
     * var regInputlname = $('<input>').attr({ "class":"reg-input",
     * "placeholder":"Last Name", "name":"lname" });
     *
     * regInputContainerlname.append(regInputlname);
     *
     *
     * var regInputContainerEmail = $('<div>').attr({ "class":"reg-input-cont
     * reg-email"
     *
     * });
     *
     * var regInputEmail = $('<input>').attr({ "class":"reg-input",
     * "placeholder":"Email", "name":"email"
     *
     * });
     *
     * regInputContainerEmail.append(regInputEmail);
     *
     *  // radio button
     *
     * var regRatioContainer = $('<div>').attr({ "class":"reg-btn-cont" });
     *
     *
     * var regRadioNotifyCont1 = $('<div>').attr({ "class" :
     * "reg-radio-btn-wrapper" });
     *
     * var regRadioNotify1 = $('<input>').attr({ "type":"radio",
     * "class":"reg-radio", "name":"notifyme", "value":0 });
     *
     * var regRadioNotify1Txt = $('<span>').attr({ "class" : "reg-radio-txt"
     * }).html("Daily");
     *
     * regRadioNotifyCont1.append(regRadioNotify1).append(regRadioNotify1Txt);
     *
     * var regRadioNotifyCont2 = $('<div>').attr({ "class" :
     * "reg-radio-btn-wrapper" });
     *
     * var regRadioNotify2 = $('<input>').attr({ "type":"radio",
     * "class":"reg-radio", "name":"notifyme", "value":1 });
     *
     * var regRadioNotify2Txt = $('<span>').attr({ "class" : "reg-radio-txt"
     * }).html("Weekly");
     *
     * regRadioNotifyCont2.append(regRadioNotify2).append(regRadioNotify2Txt);
     *
     * regRatioContainer.append(regRadioNotifyCont1).append(regRadioNotifyCont2);
     *
     * var regContainerGetStarted = $('<div>').attr({ "class":"reg-btn-wrapper
     * clearfix"
     *
     * });
     *
     * var regGetStarted = $('<div>').attr({ "class":"reg-btn float-left",
     *
     * }).html("Notify
     * Me").bind('click',{"userDetails":registration},function(event){
     *
     * registration.firstName = $('input[name="fname"]').val();
     * registration.lastName = $('input[name="lname"]').val(); var dateVar = new
     * Date(); var timezone = dateVar.getTimezoneOffset(); registration.emailId =
     * $('input[name="email"]').val() + ":" + timezone;
     * saveUserAndNotifyRatesAlerts(registration); });
     *
     * regContainerGetStarted.append(regGetStarted);
     *
     * regMainContainer.append(regDisplayTitle);
     * regMainContainer.append(regDisplaySubTitle);
     * regMainContainer.append(regInputContainerFname);
     * regMainContainer.append(regInputContainerlname);
     * regMainContainer.append(regInputContainerEmail);
     * regMainContainer.append(regRatioContainer);
     *
     * regMainContainer.append(regContainerGetStarted);
     *
     *
     * return parentWrapper.append(regMainContainer);
     *  }
     *
     * function saveUserAndNotifyRatesAlerts(registration){
     *
     * //alert(JSON.stringify(registration)); $('#overlay-loader').show(); $.ajax({
     *
     * url : "rest/shopper/registration", type : "POST", data : {
     * "registrationDetails" : JSON.stringify(registration) }, datatype :
     * "application/json", success : function(data) {
     *
     * $('#overlay-loader').hide();
     *
     * window.location.href = data; //printMedianRate(data,container);
     *  }, error : function(data) { alert(data);
     * $('#ce-main-container').html(data.toString()); //$('#overlay-loader').hide(); }
     *
     * });
     *  }
     */
function paintFixYourRatePageCEP(teaserRate, inputCustomerDetails) {
    /*
     * var rateProgramWrapper = getLockRateProgramContainer();
     * $('#center-panel-cont').append(rateProgramWrapper);
     */
    // var refinanceTeaserRate = JSON.parse(refinanceTeaserRate);
    // var loanTypeText = refinanceTeaserRate.loanType;
    var loanSummaryWrapper = getLoanSummaryWrapperCEP(teaserRate, inputCustomerDetails);
    $('#ce-refinance-cp').append(loanSummaryWrapper);
}

function getLoanSummaryWrapperCEP(teaserRate, inputCustomerDetails) {
    var loanSummaryWrapper = getLoanSummaryWrapperTeaserRate(teaserRate, inputCustomerDetails);
    // var closingCostWrapper = getClosingCostSummaryContainer();
    // $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);
    // var refinanceTeaserRate = JSON.parse(refinanceTeaserRate);
    // loanTypeText = refinanceTeaserRate.loanType;
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    /*
     * var container; if(loanTypeText == "refinance"){ container =
     * getLoanSummaryContainerRefinanceCEP(); }else if(loanTypeText ==
     * "purchase"){ container = getLoanSummaryContainerPurchaseCEP(); }
     */
    var rateWrapper = getLoanSliderWrapperCEP(teaserRate, inputCustomerDetails);
    var bottomText = getHeaderText("Quoted Rates are not guaranteed. You may use this tool to check current rates or request a  rate lock. APR is an estimate based on an average $200,000 loan amount with 2% in total APR related fees. Actual ARP will be available on your Good Faith Estimate after Loan Amount and Income are Verified.");
    // parentWrapper.append(container).append(loanSummaryWrapper).append(closingCostWrapper).append(rateWrapper).append(bottomText);
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
    /*
     * function getLoanSummaryContainerRefinanceCEP() { var container = $('<div>').attr({
     * "class" : "loan-summary-container clearfix" }); var leftCol = $('<div>').attr({
     * "class" : "loan-summary-lp float-left" }); // add rows in left column var
     * lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - No Cash Out"); var
     * lcRow2 = getLoanSummaryRow("Loan Program", "30 Years Fixed"); var lcRow3 =
     * getLoanSummaryRow("Interest Rate", "3.375%"); var lcRow4 =
     * getLoanAmountRow("Loan Amount", "$ 373,000.000");
     *
     * var lcRow5 = getLoanSummaryRow("APR", "3.547%"); var lcRow6 =
     * getLoanSummaryLastRow("Estimated<br/>Closing Cost", "$8,185.75");
     * leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(
     * lcRow5).append(lcRow6);
     *
     * var rightCol = $('<div>').attr({ "class" : "loan-summary-rp float-right" }); //
     * add rows in right column var rcRow1 = getLoanSummaryRow("Principal Interest", "$
     * 1,649.20"); var rcRow2 = getLoanSummaryRowCalculateBtn("Tax", "Calculate");
     * rcRow2.addClass("no-border-bottom"); var rcRow3 =
     * getLoanSummaryRowCalculateBtn("Insurance", "Calculate"); var rcRow4 =
     * getLoanSummaryTextRow("Your tax and insurance payment above will be included
     * with your principal & interest payment");
     *
     * var rcRow5 = getLoanSummaryRow("Total Est. Monthly Payment", "$ 3,298.40");
     * var rcRow6 = getLoanSummaryRow("Current Monthly Payment", "$ 1,649.20"); var
     * rcRow7 = getLoanSummaryRow("Monthly Payment Difference", "$ 1,649.20");
     *
     * var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", "$
     * 1,649.02");
     * rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4)
     * .append(rcRow5).append(rcRow6).append(rcRow7).append(rcRow8);
     *
     * container.append(leftCol).append(rightCol); return container; }
     */
    /*
     * function getLoanSummaryContainerPurchaseCEP() { var container = $('<div>').attr({
     * "class" : "loan-summary-container clearfix" }); var leftCol = $('<div>').attr({
     * "class" : "loan-summary-lp float-left" }); // add rows in left column var
     * lcRow1 = getLaonSummaryApplyBtnRow(); var lcRow2 = getLoanSummaryRow("Loan
     * Type", "Purchase"); var lcRow3 = getLoanSummaryRow("Loan Program", "5 Years
     * Fixed ARM"); var lcRow4 = getLoanSummaryRow("Down Payment", "$ 100,000.00");
     * var lcRow5 = getLoanSummaryRow("Purchase Amount", "$ 473,000.000"); var
     * lcRow6 = getLoanSummaryRow("Interest Rate", "3.375%"); var lcRow7 =
     * getLoanSummaryRow("Loan Amount", "$ 373,000.000"); var lcRow8 =
     * getLoanSummaryRow("ARP", "3.547%"); var lcRow9 = getLoanSummaryRow("Estimated<br/>Closing
     * Cost", "$8,185.75");
     * leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(
     * lcRow5).append(lcRow6).append(lcRow7).append(lcRow8).append(lcRow9);
     *
     * var rightCol = $('<div>').attr({ "class" : "loan-summary-rp float-right" }); //
     * add rows in right column var rcRow1 = getLoanSummaryRow("Monthly Payment",
     * ""); var rcRow2 = getLoanSummaryRow("Principal Interest", "$ 1,649.20"); var
     * rcRow3 = getLoanSummaryRowCalculateBtn("Tax", "Calculate");
     * rcRow3.addClass("no-border-bottom"); var rcRow4 =
     * getLoanSummaryRowCalculateBtn("Insurance", "Calculate"); var rcRow5 =
     * getLoanSummaryTextRow("Your tax and insurance payment above will be included
     * with your principal & interest payment"); var rcRow6 =
     * getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", "$ 1,649.02");
     * rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4)
     * .append(rcRow5).append(rcRow6);
     *
     * container.append(leftCol).append(rightCol); return container; }
     */
function getLoanSliderWrapperCEP(teaserRate, inputCustomerDetails) {
    // alert(JSON.stringify(refinanceTeaserRate));
    var wrapper = $('<div>').attr({
        "class": "lock-rate-slider-wrapper"
    });
    var header = $('<div>').attr({
        "class": "lock-rate-slider-header"
    }).html("select loan program");
    var container = $('<div>').attr({
        "class": "lock-rate-slider-container"
    });
    var tenureSlider = getYearSliderContCEP1(teaserRate);
    var rateSlider = getRateSliderContCEP(teaserRate);
    container.append(tenureSlider).append(rateSlider);
    var rateBtn1 = $('<div>').attr({
        "class": "rate-btn"
    }).html("Create Your Account").on('click', function() {
        var mainContainer = paintApplyNow(inputCustomerDetails);
        $('#ce-main-container').html(mainContainer);
    });
    var rateBtn2 = $('<div>').attr({
        "class": "rate-btn-alertRate"
    }).html("Email My Number").on('click', function() {
        var mainContainer = paintApplyNow(inputCustomerDetails);
        $('#ce-main-container').html(mainContainer);
    });
    return wrapper.append(header).append(container).append(rateBtn1).append(rateBtn2);;
}

function getYearSliderContCEP1(teaserRate) {
    var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Length of Loan");
    var silderCont = getYearSliderCEP(teaserRate);
    return wrapper.append(headerTxt).append(silderCont);
}

function getYearSliderCEP(LQBResponse) {
    var container = $('<div>').attr({
        "class": "silder-cont yr-slider float-left"
    });
    var yearValues = [];
    for (var i in LQBResponse) {
        loanDurationConform = LQBResponse[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
        }
        temp = {};
        temp.value = year;
        temp.text = year + " - year fixed arm",
            temp.rateVO = LQBResponse[i].rateVO;
        yearValues.push(temp);
    }
    yearValues.sort(function(a, b) {
        return parseFloat(a.value) - parseFloat(b.value);
    });
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
            "ratesArray": yearValues[i].rateVO
        }, function(event) {
            if (!$(this).hasClass('yr-slider-icon-selected')) {
                $('.yr-grid-cont .yr-slider-icon').removeClass('yr-slider-icon-selected');
                $(this).addClass('yr-slider-icon-selected');
                $('.yr-grid-cont .yr-grid-item-selected').hide();
                $('.yr-grid-cont .yr-grid-item').show();
                $(this).parent().find('.yr-grid-item').hide();
                $(this).parent().find('.yr-grid-item-selected').show();
                $('#rate-slider-cont').find('.rt-slider').remove();
                var rateSlider = getRatSliderCEP(event.data.ratesArray);
                $('#rate-slider-cont').append(rateSlider);
                index = parseInt(event.data.ratesArray.length / 2);
                $('#aprid').html(event.data.ratesArray[index].APR);
                $('#closingCostId').html(event.data.ratesArray[index].closingCost);
                $('#teaserRateId').html(event.data.ratesArray[index].teaserRate);
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
        if (i == 0) {
            selectIcon.addClass('yr-slider-icon-selected');
            gridItem.hide();
            gridItemSelected.show();
        }
        container.append(gridItemCont);
    }
    return container;
}

function getRateSliderContCEP(LQBResponse) {
    var wrapper = $('<div>').attr({
        "id": "rate-slider-cont",
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Interest Rate");
    var yearValues = [];
    for (var i in LQBResponse) {
        loanDurationConform = LQBResponse[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
        }
        temp = {};
        temp.value = year;
        temp.text = year + " - year fixed arm",
            temp.rateVO = LQBResponse[i].rateVO;
        yearValues.push(temp);
    }
    yearValues.sort(function(a, b) {
        return parseFloat(a.value) - parseFloat(b.value);
    });
    var rateArray = yearValues[0].rateVO;
    index = parseInt(rateArray.length / 2);
    var silderCont = getRatSliderCEP(rateArray);
    /*
     * $('#aprid').html(rateArray[index].APR);
     * $('#closingCostId').html(rateArray[index].closingCost);
     */
    return wrapper.append(headerTxt).append(silderCont);
}

function getRatSliderCEP(gridArray) {
    var rateArray = [];
    for (var i = 0; i < gridArray.length; i++) {
        rateArray[i] = gridArray[i].teaserRate;
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
         //    alert(gridArray[ui.value].APR);
            $('#aprid').html(gridArray[ui.value].APR);
            $('#closingCostId').html(gridArray[ui.value].closingCost);
            $('#teaserRateId').html(gridArray[ui.value].teaserRate);
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
        }).html(gridArray[i].teaserRate + "%");
        gridItemCont.append(gridItem);
    }
    return container.append(gridItemCont);
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
        container = getLoanSummaryContainerPurchaseCEP();
    }
    // var bottomText = getHeaderText("Quoted Rates are not guaranteed. You may
    // use this tool to check current rates or request a rate lock. APR is an
    // estimate based on an average $200,000 loan amount with 2% in total APR
    // related fees. Actual ARP will be available on your Good Faith Estimate
    // after Loan Amount and Income are Verified.");
    // var rateWrapper = getLoanSliderWrapper();
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
    var yearValues = modifiedLQBJsonRes(teaserRate);
    var rateVO = yearValues[0].rateVO;
    var index = parseInt(yearValues[0].rateVO.length / 2);
    var container = $('<div>').attr({
        "class": "loan-summary-container clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
   
   alert(customerInputData.refinanceOption);
    if (customerInputData.refinanceOption == "REFLMP") refinanceOpt = "Lower My Monthly Payment";
    if (customerInputData.refinanceOption == "REFMF") refinanceOpt = "Pay Off My Mortgage Faster";
    if (customerInputData.refinanceOption == "REFCO") refinanceOpt = "Take Cash Out of My Home";
   
    var lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - " + refinanceOpt);
    var lcRow2 = getLoanSummaryRow("Loan Program", "30 Years Fixed");
    var lcRow3 = getLoanSummaryRow("Interest Rate", rateVO[index].teaserRate, "teaserRateId");
    var lcRow4 = getLoanAmountRow("Loan Amount", customerInputData.currentMortgageBalance);
    var lcRow5 = getLoanSummaryRow("APR", rateVO[index].APR, "aprid");
    var lcRow6 = getLoanSummaryLastRow("Estimated<br/>Closing Cost", rateVO[index].closingCost, "closingCostId");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5).append(lcRow6);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Principal Interest", "$ 1,649.20");
    var rcRow2 = getLoanSummaryRowCalculateBtn("Tax", "Calculate");
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Insurance", "Calculate");
    var rcRow4 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal 																			& interest payment");
    var rcRow5 = getLoanSummaryRow("Total Est. Monthly Payment", "$ 3,298.40");
    var rcRow6 = getLoanSummaryRow("Current Monthly Payment", "$ 1,649.20");
    var rcRow7 = getLoanSummaryRow("Monthly Payment Difference", "$ 1,649.20");
    var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", "$ 1,649.02");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4).append(rcRow5).append(rcRow6).append(rcRow7).append(rcRow8);
    container.append(leftCol).append(rightCol);
    return container;
}

function getLoanSummaryContainerPurchaseCEP() {
    var container = $('<div>').attr({
        "class": "loan-summary-container clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
    var lcRow1 = getLaonSummaryApplyBtnRow();
    var lcRow2 = getLoanSummaryRow("Loan Type", "Purchase");
    var lcRow3 = getLoanSummaryRow("Loan Program", "5 Years Fixed ARM");
    var lcRow4 = getLoanSummaryRow("Down Payment", "$ 100,000.00");
    var lcRow5 = getLoanSummaryRow("Purchase Amount", "$ 473,000.000");
    var lcRow6 = getLoanSummaryRow("Interest Rate", "3.375%");
    var lcRow7 = getLoanSummaryRow("Loan Amount", "$ 373,000.000");
    var lcRow8 = getLoanSummaryRow("ARP", "3.547%");
    var lcRow9 = getLoanSummaryRow("Estimated<br/>Closing Cost", "$8,185.75");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5).append(lcRow6).append(lcRow7).append(lcRow8).append(lcRow9);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Monthly Payment", "");
    var rcRow2 = getLoanSummaryRow("Principal Interest", "$ 1,649.20");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Tax", "Calculate");
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtn("Insurance", "Calculate");
    var rcRow5 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal 																			& interest payment");
    var rcRow6 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", "$ 1,649.02");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4).append(rcRow5).append(rcRow6);
    container.append(leftCol).append(rightCol);
    return container;
}

function getYearSliderContCEP() {
    var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Length of Loan");
    var silderCont = getYearSlider();
    return wrapper.append(headerTxt).append(silderCont);
}

function modifiedLQBJsonRes(LQBResponse) {
    var yearValues = [];
    for (var i in LQBResponse) {
        loanDurationConform = LQBResponse[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
        }
        temp = {};
        temp.value = year;
        temp.text = year + " - year fixed arm",
            temp.rateVO = LQBResponse[i].rateVO;
        yearValues.push(temp);
    }
    yearValues.sort(function(a, b) {
        return parseFloat(a.value) - parseFloat(b.value);
    });
    return yearValues;
}