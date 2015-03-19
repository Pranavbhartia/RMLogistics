function getQuestionsContainer(questions) {
    var questionsContainer = $('<div>').attr({
        "class": "app-ques-container"
    });

    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var quesCont = getApplicationQuestion(question);
        questionsContainer.append(quesCont);
    }
    return questionsContainer;
}

function getApplicationQuestion(question) {
    var quesCont;
    if (question.type == "mcq") {
        quesCont = getApplicationMultipleChoiceQues(question);
    } else if (question.type == "desc") {
        quesCont = getApplicationTextQues(question);
    } else if (question.type == "select") {
        quesCont = getApplicationSelectQues(question);
    } else if (question.type == "yesno") {
        quesCont = getApplicationYesNoQues(question);
    }
    return quesCont;
}

function getApplicationSelectQues(question) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(question.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": question.name
    });

    var selectedOption = $('<div>').attr({
        "class": "app-option-selected"
    }).html("Select One").on('click', function() {
        $(this).parent().find('.app-dropdown-cont').toggle();
    });

    var dropDownContainer = $('<div>').attr({
        "class": "app-dropdown-cont hide"
    });

    for (var i = 0; i < question.options.length; i++) {
        var option = question.options[i];
        var optionCont = $('<div>').attr({
                "class": "app-option-sel"
            }).data({
                "value": option.value
            }).html(option.text)
            .on(
                'click',
                function() {
                    $(this).closest('.app-options-cont').find(
                        '.app-option-selected').html(
                        $(this).data("value"));
                    $(this).closest('.app-dropdown-cont').toggle();
                });
        dropDownContainer.append(optionCont);
    }

    optionsContainer.append(selectedOption).append(dropDownContainer);

    return container.append(quesTextCont).append(optionsContainer);
}

function getApplicationTextQues(question) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(question.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont"
    });

    var optionCont = $('<input>').attr({
        "class": "app-input",
        "name": question.name
    });

    if (question.value != undefined) {
        optionCont.val(question.value);
    }

    optionsContainer.append(optionCont);

    return container.append(quesTextCont).append(optionsContainer);
}

function getApplicationMultipleChoiceQues(question) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(question.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": question.name
    });

    for (var i = 0; i < question.options.length; i++) {
        var option = question.options[i];
        var optionCont = $('<div>').attr({
            "class": "app-option"
        }).data({
            value: option.value
        }).html(option.text)
        .on('click',function(){
        	if($(this).hasClass('app-option-checked')){
        		$(this).removeClass('app-option-checked');
        	}else{
	        	$(this).parent().find('.app-option').removeClass('app-option-checked');
	        	$(this).addClass('app-option-checked');
        	}
        });
        optionsContainer.append(optionCont);
    }

    return container.append(quesTextCont).append(optionsContainer);
}

function getApplicationYesNoQues(question) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(question.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": question.name
    });

    for (var i = 0; i < question.options.length; i++) {
        var option = question.options[i];
        var optionCont = $('<div>').attr({
            "class": "app-option-choice",
            "isSelected" : "false"
        }).data({
            value: option.value
        }).html(option.text)
        .on('click',function(){
        	$(this).parent().find('.app-option-choice').attr("isSelected","false");
        	$(this).attr("isSelected","true");
        });

      /*  if (option.sub_questions != undefined) {
        	
        	var subQuesWrapper = $('<div>').attr({
        		"class" : "app-sub-ques-wrapper hide"
        	});
        	
        	for(var j=0;j<option.sub_questions.length;j++){
        		var subQues = getApplicationQuestion(option.sub_questions[j]);
        		subQuesWrapper.append(subQues);
        	}
        	optionCont.append(subQuesWrapper);
            //var subQues = getApplicationQuestion(option.sub_question);
        }*/

        optionsContainer.append(optionCont);
    }

    return container.append(quesTextCont).append(optionsContainer);
}

function paintCustomerApplicationPage() {
    var topHeader = getCompletYourApplicationHeader();

    var container = $('<div>').attr({
        "class": "clearfix"
    });

    var applicationLeftPanel = $('<div>').attr({
        "class": "cust-app-lp float-left"
    });

    var applicationRightPanel = $('<div>').attr({
        "id": "app-right-panel",
        "class": "cust-app-rp float-left"
    });

    container.append(applicationLeftPanel).append(applicationRightPanel);

    $('#center-panel-cont').append(topHeader).append(container);

    paintCustomerApplicationPageStep1a();
}

function paintCustomerApplicationPageStep1a() {
    $('#app-right-panel').html('');
    var quesHeaderTxt = "Address";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "desc",
        text: "Street Address",
        name: "streetAddress",
        value: ""
    }, {
        type: "desc",
        text: "State",
        name: "state",
        value: ""
    }, {
        type: "desc",
        text: "City",
        name: "city",
        value: ""
    }, {
        type: "desc",
        text: "Zip Code",
        name: "zipCode",
        value: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
        paintCustomerApplicationPageStep1b();
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function paintCustomerApplicationPageStep1b() {
    $('#app-right-panel').html('');
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
            value: "Single Family Residence"
        }, {
            text: "Condo",
            value: "Condo"
        }, {
            text: "Multi-Unit",
            value: "Multi-Unit"
        }, {
            text: "Mobile/Manufactured",
            value: "Mobile/Manufactured"
        }],
        selected: ""
    }, {
        type: "select",
        text: "How do you use this home?",
        name: "residenceType",
        options: [{
            text: "Primary Residence",
            value: "Primary Residence"
        }, {
            text: "Vacation Home",
            value: "Vacation Home"
        }, {
            text: "Investment Property",
            value: "Investment Property"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "How much is paid in property taxes every year?",
        name: "taxesPaid",
        value: ""
    }, {
        type: "desc",
        text: "Who provides homeowners insurance?",
        name: "insuranceProvider",
        value: ""
    }, {
        type: "desc",
        text: "How much does homeowners insurance cost per year?",
        name: "insuranceCost",
        value: ""
    }, {
        type: "desc",
        text: "When did you purchase this property?",
        name: "purchaseTime",
        value: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
        paintCustomerApplicationPageStep2();
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function paintCustomerApplicationPageStep2() {
    $('#app-right-panel').html('');
    var quesHeaderTxt = "Who's on the Loan?";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "yesno",
        text: "Are you married?",
        name: "isMarried",
        options: [{
            text: "Yes",
            sub_questions: [{
                type: "yesno",
                text: "Is your spouse goint to be on the loan",
                name: "isSpouseOnLoan",
                options: [{
                    "text": "yes",
                    sub_questions: [{
                        type: "desc",
                        text: "What's your spouses name?",
                        name: "spouseName"
                    }]
                }, {
                    text: "No"
                }],
                "selected": ""
            }]
        }, {
            text: "No"
        }],
        selected: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
        paintCustomerApplicationPageStep3();
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function paintCustomerApplicationPageStep3() {
    $('#app-right-panel').html('');
    var quesHeaderTxt = "My Income";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "mcq",
        text: "Select all that apply",
        name: "purchaseTime",
        options: [{
            text: "Employee",
            value: "Employee",
            sub_questions: [{
                type: "desc",
                text: "About home much do you make a year before taxes?",
                name: "",
                value: ""
            }, {
                type: "desc",
                text: "Where do you work?",
                name: "",
                value: ""
            }, {
                type: "desc",
                text: "When did your start working there?",
                name: "",
                value: ""
            }]
        }, {
            text: "Self-employed",
            value: "Self-employed",
            sub_questions: [{
                type: "desc",
                text: "How much do you make a year?",
                name: "",
                value: ""
            }]
        }, {
            text: "Social Security/ Income Disability",
            value: "Social Security/ Income Disability",
            sub_questions: [{
                type: "desc",
                text: "About how much do you get monthly?",
                name: "",
                value: ""
            }]
        }, {
            text: "Pension/Re/rement/401(k)",
            value: "Pension/Re/rement/401(k)",
            sub_questions: [{
                type: "desc",
                text: "About how much do you get monthly?",
                name: "",
                value: ""
            }]
        }],
        selected: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
        paintCustomerApplicationPageStep4a();
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function paintCustomerApplicationPageStep4a() {
    $('#app-right-panel').html('');
    var quesHeaderTxt = "Declaration Questions";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "yesno",
        text: "Are there any outstanding judgments against you?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you been declared bankrupt within the past 7 years?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you had property foreclosed upon or given title or deed in lieu thereof in the last 7 years?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a party to a lawsuit?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you directly or indirectly been obligated on any loan which resulted in foreclosure, transfer of title in lieu of foreclosure, or judgment?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you presently delinquent or in default on any Federal debt or any other loan, mortgage, financial obligation, bond or loan guarantee?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you obligated to pay alimony, child support, or separate maintenance?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Is any part of the down payment borrowed?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a co-maker or endorser on a note?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a US citizen?",
        name: "",
        options: [{
            text: "Yes"
        }, {
            text: "No",
            sub_questions: [{
                type: "select",
                text: "Are you a permanent resident alien?",
                name: "",
                options: [{
                    text: "Yes",
                    value: "yes"
                }, {
                    text: "No",
                    value: "no"
                }],
                "selected": ""
            }]
        }],
        selected: ""
    },
    {
        type: "yesno",
        text: "Do you intend to occupy the property as your primary residence?",
        name: "",
        options: [{
            text: "Yes",
            value: "yes"
        }, {
            text: "No",
            value: "no"
        }],
        selected: ""
    },
    {
        type: "yesno",
        text: "Have you had an ownership interest in a property in the last three years?",
        name: "",
        options: [{
            text: "Yes",
            sub_questions: [{
                type: "select",
                text: "What type of property did you own? Select the choice that fits best.",
                name: "",
                options: [{
                    text: "Your Primary Residence",
                    value: "Your Primary Residence"
                }, {
                    text: "A Second Home",
                    value: "A Second Home"
                },
                {
                    text: "An Investment Property",
                    value: "An Investment Property"
                }],
                "selected": ""
            },
            {
                type: "select",
                text: "What was your status on the title of that property",
                name: "",
                options: [{
                    text: "I was the sole title holder",
                    value: "I was the sole title holder"
                }, {
                    text: "I held the title jointly with my spouse",
                    value: "I held the title jointly with my spouse"
                },
                {
                    text: "I held title jointly with another person",
                    value: "I held title jointly with another person"
                }],
                "selected": ""
            }]
        }, {
            text: "No"
        }],
        selected: ""
    }
    ];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	paintCustomerApplicationPageStep5();
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function paintCustomerApplicationPageStep5() {
    $('#app-right-panel').html('');
    var quesHeaderTxt = "My Income";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "",
        value: ""
    },
    {
        type: "desc",
        text: "Social Security Number",
        name: "",
        value: ""
    },
    {
        type: "desc",
        text: "Phone Number",
        name: "",
        value: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
        
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}
