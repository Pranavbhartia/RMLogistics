

var appUserDetails = new Object();
var user =  new Object();
var customerDetail = new Object();
var propertyTypeMaster = new Object();
var governmentquestion = new Object();
var refinancedetails = new Object();
var loan = new Object();
var loanType = new Object();

if (sessionStorage.loanAppFormData) {
	appUserDetails = JSON.parse(sessionStorage.loanAppFormData);
}

loan.id = 2;
loanType.id=1;

var applyLoanStatus = 0; 

user.customerDetail = customerDetail;
appUserDetails.user = user;
appUserDetails.propertyTypeMaster = propertyTypeMaster;
appUserDetails.governmentquestion = governmentquestion;
appUserDetails.refinancedetails = refinancedetails;
appUserDetails.loan = loan;
appUserDetails.loanType = loanType;

var applicationItemsList = [ {
							    "text":"Home Information",
	                            "onselect" : paintCustomerApplicationPageStep1a
	                        },
	                        {
								"text":"Who's on the Loan?",
		                        "onselect" : paintCustomerApplicationPageStep2
		                    },
	                        {
								"text":"My Income",
		                        "onselect" : paintMyIncome
		                    },
			                {
							    "text":"Government Questions",
				                 "onselect" : paintCustomerApplicationPageStep4a
				             },
				             {
				            	 "text":"My Credit",
					             "onselect" : paintCustomerApplicationPageStep5
					         },
];

function applicationStatusLeftPanel() {
	var container = $('<div>').attr({
		"class" : "ce-lp float-left"
	});

	for (var i = 0; i < applicationItemsList.length; i++) {
		var itemCompletionStage = "NOT_STARTED";

		var itemCont = applicationStatusPanelItem(applicationItemsList[i], i + 1,itemCompletionStage);
		container.append(itemCont);
	}

	return container;
}

function applicationStatusPanelItem(itemTxt, stepNo, itemCompletionStage) {
	var itemCont = $('<div>').attr({
		"class" : "ce-lp-item clearfix",
		"data-step" : stepNo,
		"id" : "appProgressBaarId_" + stepNo
	});
	
	var leftIcon = $('<div>').attr({
		"class" : "ce-lp-item-icon float-left",
		"id" : "appStepNoId_" + stepNo
	});

	itemCont.addClass('ce-lp-not-started');
	leftIcon.html(stepNo);

	var textCont = $('<div>').attr({
		"class" : "ce-app-lp-item-text float-left"
	}).html(itemTxt.text);

	return itemCont.append(leftIcon).append(textCont);
}


function paintCustomerApplicationPage() {
    
	user.id = newfi.user.id;
	customerDetail.id = newfi.user.customerDetail.id;
	//user["emailId"] = newfi.user.emailId;
	//user["firstName"] =newfi.user.firstName;
	//user["lastName"] = newfi.user.lastName;
	
	var topHeader = getCompletYourApplicationHeader();

    var container = $('<div>').attr({
        "class": "clearfix"
    });

    var applicationLeftPanel = $('<div>').attr({
        "class": "cust-app-lp float-left"
    });

    var leftPanel = applicationStatusLeftPanel();
    
    applicationLeftPanel.append(leftPanel);
    
    var applicationRightPanel = $('<div>').attr({
        "id": "app-right-panel",
        "class": "cust-app-rp float-left"
    });

    container.append(applicationLeftPanel).append(applicationRightPanel);
    
    

    $('#center-panel-cont').append(topHeader).append(container);

    paintCustomerApplicationPageStep1a();
}


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
    else if (question.type == "yearMonth") {
        quesCont = getMonthYearTextQuestion(question);
    }
    return quesCont;
}

function getContextApplicationSelectQues(contxt) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(contxt.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": contxt.name
    });

    var selectedOption = $('<div>').attr({
        "class": "app-option-selected"
    }).html("Select One").on('click', function() {
        $(this).parent().find('.app-dropdown-cont').toggle();
    });

    var dropDownContainer = $('<div>').attr({
        "class": "app-dropdown-cont hide"
    });

    for (var i = 0; i < contxt.options.length; i++) {
        var option = contxt.options[i];
        var optionCont = $('<div>').attr({
                "class": "app-option-sel"
            }).data({
                "value": option.value
            }).html(option.text);
        
        optionCont.bind('click',{"contxt":contxt,"value":option.text,"option":option},
                function(event) {
        	      
                    $(this).closest('.app-options-cont').find(
                        '.app-option-selected').html($(this).html());
                    $(this).closest('.app-options-cont').find(
                    '.app-option-selected').data("value",$(this).data("value"));
                    $(this).closest('.app-dropdown-cont').toggle();
                    var ctx=event.data.contxt;
                	ctx.clickHandler(event.data.value);
                	if(ctx.value!=event.data.value){
        	        	ctx.value=event.data.value;
        	        	var opt=event.data.option;
        	        	if(opt.addQuestions){
        	        		ctx.drawChildQuestions(ctx.value,opt.addQuestions);
        	        	}
                	}
                });
        dropDownContainer.append(optionCont);
    }

    optionsContainer.append(selectedOption).append(dropDownContainer);

    return container.append(quesTextCont).append(optionsContainer);
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
                        '.app-option-selected').html($(this).html());
                    $(this).closest('.app-options-cont').find(
                    '.app-option-selected').data("value",$(this).data("value"));
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
	        	$(this).addClass('app-option-checked');
        	}
        });
        optionsContainer.append(optionCont);
    }

    return container.append(quesTextCont).append(optionsContainer);
}



function paintCustomerApplicationPageStep1a() {
    
	applyLoanStatus = 1;
	appProgressBaar(1);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Residential Address";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [/*{
        type: "desc",
        text: "Street Address",
        name: "streetAddress",
        value: ""
    }, */{
        type: "desc",
        text: "Which State do you live in?",
        name: "state",
        value: ""
    }, {
        type: "desc",
        text: "Which City do you belong?",
        name: "city",
        value: ""
    }, {
        type: "desc",
        text: "What is your Zip Code?",
        name: "zipCode",
        value: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function(event) {
    	
    	var inputState = $('input[name="state"]').val();
    	//appUserDetails["state"] = inputState;
    	
    	var city = $('input[name="city"]').val();
    	//appUserDetails["city"] = city;
    	
    	var zipCode = $('input[name="zipCode"]').val();
    	//appUserDetails["zipCode"] = zipCode;
    	
    	if(inputState != undefined && inputState != "" && city != undefined && city != ""  && zipCode != undefined && zipCode != ""  ){
        	

    		customerDetail.addressCity = city;
    		customerDetail.addressState = inputState;
    		customerDetail.addressZipCode = zipCode;

    		user.customerDetail = customerDetail;
    		
    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    		appUserDetails.loanAppFormCompletionStatus=applyLoanStatus;
    		saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep1b());
    		//paintCustomerApplicationPageStep1b();
        	        	
        }else{
        	showToastMessage("Please give answer of the questions");
        }
   	
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
            value: "0"
        }, {
            text: "Condo",
            value: "1"
        }, {
            text: "Multi-Unit",
            value: "2"
        }, {
            text: "Mobile/Manufactured",
            value: "3"
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
            text: "Investment Property",
            value: "2"
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
        type: "yearMonth",
        text: "When did you purchase this property?",
        name: "purchaseTime",
        value: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	
    	propertyTypeCd = $('.app-options-cont[name="propertyType"]').find('.app-option-selected').data().value;
    	residenceTypeCd= $('.app-options-cont[name="residenceType"]').find('.app-option-selected').data().value;
    	propertyTaxesPaid = $('input[name="taxesPaid"]').val();
    	propertyInsuranceProvider = $('input[name="insuranceProvider"]').val();
    	propertyInsuranceCost = $('input[name="insuranceCost"]').val();
    	propertyPurchaseYear = $('input[name="purchaseTime"]').val();
    	homeWorthToday = '$35,000';
    	
    	
    	
    	if(propertyTypeCd != undefined && propertyTypeCd != "" && residenceTypeCd != undefined && residenceTypeCd != ""  && propertyTaxesPaid != undefined && propertyTaxesPaid != ""  && propertyInsuranceProvider != undefined && propertyInsuranceProvider != "" && propertyInsuranceCost != undefined && propertyInsuranceCost != ""  && propertyPurchaseYear != undefined && propertyPurchaseYear != ""  ){
    		
    		propertyTypeMaster.propertyTypeCd = propertyTypeCd;
        	propertyTypeMaster.residenceTypeCd = residenceTypeCd;
        	propertyTypeMaster.propertyTaxesPaid = propertyTaxesPaid;
        	propertyTypeMaster.propertyInsuranceProvider = propertyInsuranceProvider;
        	propertyTypeMaster.propertyInsuranceCost = propertyInsuranceCost;
        	propertyTypeMaster.propertyPurchaseYear = propertyPurchaseYear;
        	propertyTypeMaster.homeWorthToday = homeWorthToday ;
        	  	
        	appUserDetails.propertyTypeMaster = propertyTypeMaster;
        	
        	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
        	saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
        	
    		
    		//paintCustomerApplicationPageStep2();
    	}else{
    		showToastMessage("Please give answer of the questions");
    	}
    	
    	
        
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
   
    putCurrencyFormat("taxesPaid");
    putCurrencyFormat("insuranceCost");
    
    
}
function getQuestionContext(question,parentContainer){
	var contxt={
			type: question.type,
	        text: question.text,
	        name: question.name,
	        options:question.options,
	        container :undefined,
	        childContainers:{},
	        childContexts:{},
	        value:question.selected,
	        parentContainer:parentContainer,
	        drawQuestion:function(callback){
	        	var ob=this;
	        	if (ob.type == "mcq") {
	        		ob.container = getApplicationMultipleChoiceQues(ob);
	            } else if (ob.type == "desc") {
	            	ob.container = getContextApplicationTextQues(ob);
	            } else if (ob.type == "select") {
	            	ob.container = getContextApplicationSelectQues(ob);
	            } else if (ob.type == "yesno") {
	            	ob.container = getContextApplicationYesNoQues(ob);
	            }
	        	
	        	parentContainer.append(ob.container);
	        	
	        },
	        deleteContainer:function(callback){
	        	
	        },
	        clickHandler:function(newValue,callback){
	        	var ob=this;
	        	if(ob.value!=""&&ob.value!=newValue){
	        		if(ob.type=="yesno"){
	        			for(var key in ob.childContainers){
	        				var container=ob.childContainers[key];
	        				container.remove();
	        			}
	        			ob.childContainers={};
	        	        ob.childContexts={};
	        		}
	        	}
	        },
	        clearChildren:function(callback){
	        	
	        },
	        drawChildQuestions:function(option,questions){
	        	var ob=this;
	        	var childContainer = $('<div>');
	        	ob.childContainers[option]=childContainer;
	        	ob.childContexts[option]=[];
	        	for(var i=0;i<questions.length;i++){
	        		var question=questions[i];
	            	var contxt=getQuestionContext(question,childContainer);
	            	contxt.drawQuestion();
	            	ob.childContexts[option].push(contxt);
	        	}
	        	ob.container.after(childContainer);
	        },
	        changeHandler:function(newValue,callback){
	        	var ob=this;
	        	if(ob.value!=""&&ob.value!=newValue){
	        		if(ob.type=="yesno"){
	        			for(var key in ob.childContainers){
	        				var container=ob.childContainers[key];
	        				container.remove();
	        			}
	        			ob.childContainers={};
	        	        ob.childContexts={};
	        		}
	        	}
	        }
	};
	return contxt;
}
var quesContxts=[];
function paintCustomerApplicationPageStep2() {
	
	applyLoanStatus =2;
	appProgressBaar(2); // this is to show the bubble status in the left panel
	quesContxts = []; // when ever call the above function clean the array object
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Who's on the Loan?";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);
    //TODO-try nested yesno question
    var questions = [{
        type: "yesno",
        text: "Are you married?",
        name: "maritalStatus",
        options: [{
            text: "Yes",
            addQuestions:[{
                type: "yesno",
                text: "Is your spouse on the loan?",
                name: "isSpouseOnLoan",
                options: [{
                    text: "Yes",
                    addQuestions:[{
                        type: "desc",
                        text: "What is your spouse name ?",
                        name: "spouseName"
                    }]
                }, {
                    text: "No"
                }],
                selected: ""
            }]
        }, {
            text: "No"
        }],
        selected: ""
    }];
    
    $('#app-right-panel').append(quesHeaderTextCont);
   
   
    
    for(var i=0;i<questions.length;i++){
    	var question=questions[i];
    	var contxt=getQuestionContext(question,$('#app-right-panel'));
    	contxt.drawQuestion();
    	
    	quesContxts.push(contxt);
    }
    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
        
    	maritalStatus = quesContxts[0].value;
    	
    	appUserDetails.maritalStatus =  maritalStatus;
    	
    	if(maritalStatus !="" && maritalStatus !=undefined ){
    	
    		 if(maritalStatus == "Yes"){
    			 
    			 if( quesContxts[0].childContexts.Yes !=  undefined){
    		    		
    		    		isSouseOnLoan = quesContxts[0].childContexts.Yes[0].value;
    		    		if( isSouseOnLoan =="Yes"){ 
    		    			appUserDetails.isSouseOnLoan =true;
    		    		}else{
    		    			appUserDetails.isSouseOnLoan =false;
    		    			appUserDetails.spouseName  = "false";
    		    		}
    		     }else{
    		    	 showToastMessage("Please give the answers of the questions");
    		    	 return false;
    		     }
    			 
    		 }else{
    			 appUserDetails.isSouseOnLoan =false;
	    		 appUserDetails.spouseName  = "false";
    		 }
	    	
	    	
	    	// this is the condition when spouseName is in the loan
	    	if( quesContxts[0].childContexts.Yes !=  undefined && quesContxts[0].childContexts.Yes[0].childContexts.Yes != undefined){
	    	 
	    		appUserDetails.spouseName = quesContxts[0].childContexts.Yes[0].childContexts.Yes[0].value;
	    	}else{
	    		appUserDetails.spouseName  = "false";
	    	}
	    	
	    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	saveAndUpdateLoanAppForm(appUserDetails,paintMyIncome());
	    	
    	}else{
    		showToastMessage("Please give the answers of the questions");
    	}
         //alert(JSON.stringify(appUserDetails));
    	//paintCustomerApplicationPageStep3();
    	
    	
    });
    $('#app-right-panel').append(saveAndContinueButton);
    
}

function getContextApplicationYesNoQues(contxt) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(contxt.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": contxt.name
    });

    for (var i = 0; i < contxt.options.length; i++) {
        var option = contxt.options[i];
        var optionCont = $('<div>').attr({
            "class": "app-option-choice",
            "isSelected" : "false"
        }).html(option.text);
        
        optionCont.bind("click",{"contxt":contxt,"value":option.text,"option":option},function(event){
        	$(this).parent().find('.app-option-choice').attr("isSelected","false");
        	$(this).attr("isSelected","true");
        	var ctx=event.data.contxt;
        	ctx.clickHandler(event.data.value);
        	if(ctx.value!=event.data.value){
	        	ctx.value=event.data.value;
	        	var opt=event.data.option;
	        	if(opt.addQuestions){
	        		ctx.drawChildQuestions(ctx.value,opt.addQuestions);
	        	}
        	}
        });
       

        optionsContainer.append(optionCont);
    }

    return container.append(quesTextCont).append(optionsContainer);
}
function getContextApplicationTextQues(contxt) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });

    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(contxt.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont"
    });

    var optionCont = $('<input>').attr({
        "class": "app-input",
        "name": contxt.name
    }).bind("change",{"contxt":contxt},function(event){
    	var ctx=event.data.contxt;
    	ctx.value=$(this).val();
    });

    if (contxt.value != undefined) {
        optionCont.val(contxt.value);
    }

    optionsContainer.append(optionCont);

    return container.append(quesTextCont).append(optionsContainer);
}



function paintMyIncome() {

	applyLoanStatus = 3;
	appProgressBaar(3);
	var quesTxt = "Select all that apply";
	var options = [ {
		"text" : "Employed",
		"onselect" : paintRefinanceEmployed,
		"name" : "isEmployed",
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintRefinanceSelfEmployed,
		"name" : "isselfEmployed",
		"value" : 1
	}, {
		"text" : "Social Security Income/Disability",
		"onselect" : paintRefinanceDisability,
		"name" :"isssIncomeOrDisability",
		"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintRefinancePension,
		"name" : "ispensionOrRetirement",
		"value" : 3
	} ];
	var quesCont = paintCustomerApplicationPageStep3(quesTxt, options, name);

	$('#app-right-panel').html(quesCont);

}

function paintRefinanceEmployed(divId) {

	//appUserDetails.employed ="true";
	var quesTxt = "About how much do you make a year";
	var quesCont = getMultiTextQuestion(quesTxt);
	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(quesCont);
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
	}).html("When Did You Start Working ?");

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startWorking"
	});

	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(
			quesTextCont3);

	return container.append(quesTextCont).append(optionContainer);
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


function getAppDetialsTextQuestion(quesText, clickEvent, name) {
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
		//appUserDetails[key] = $('input[name="' + key + '"]').val();
		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
		event.data.clickEvent();
	});

	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}

function paintCustomerApplicationPageStep3(quesText, options, name) {
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
			"name" : options[i].name
		}, function(event) {
			if($(this).hasClass('app-option-checked')){
        		$(this).removeClass('app-option-checked');
        		//appUserDetails[name] = false;
        	}else{
	        	$(this).addClass('app-option-checked');
	        	//appUserDetails[name] = true;
        	}
			var key = event.data.name;
			//appUserDetails[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);
		});

		optionContainer.append(option).append(optionIncome);
	}

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind('click',function() {
		        
		        EmployedIncomePreTax= $('input[name="beforeTax"]').val();
		        EmployedAt = $('input[name="workPlace"]').val();
		        EmployedSince = $('input[name="startWorking"]').val();
				
		        selfEmployedIncome = $('input[name="selfEmployed"]').val();
		        
		        ssDisabilityIncome = $('input[name="disability"]').val();
				
				monthlyPension = $('input[name="pension"]').val();

				
				
				
				appUserDetails.isEmployed =  true;
				appUserDetails.EmployedIncomePreTax = EmployedIncomePreTax;
				appUserDetails.EmployedAt = EmployedAt;
				appUserDetails.EmployedSince = EmployedSince;
				
				appUserDetails.ispensionOrRetirement= true;
				appUserDetails.monthlyPension =monthlyPension;
				
				appUserDetails.isselfEmployed = true;
				appUserDetails.selfEmployedIncome =selfEmployedIncome;
				
				appUserDetails.isssIncomeOrDisability=true;
				appUserDetails.ssDisabilityIncome = ssDisabilityIncome;
				
				//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
				
				saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a());
				//paintCustomerApplicationPageStep4a();
			});

	return container.append(quesTextCont).append(optionContainer).append(
			saveBtn);
}



var quesDeclarationContxts =[];
function paintCustomerApplicationPageStep4a() {
   
	applyLoanStatus = 4;
	quesDeclarationContxts = [];
	appProgressBaar(4);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Declaration Questions";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "yesno",
        text: "Are there any outstanding judgments against you?",
        name: "isOutstandingJudgments",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you been declared bankrupt within the past 7 years?",
        name: "isBankrupt",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you had property foreclosed upon or given title or deed in lieu thereof in the last 7 years?",
        name: "isPropertyForeclosed",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a party to a lawsuit?",
        name: "isLawsuit",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you directly or indirectly been obligated on any loan which resulted in foreclosure, transfer of title in lieu of foreclosure, or judgment?",
        name: "isObligatedLoan",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you presently delinquent or in default on any Federal debt or any other loan, mortgage, financial obligation, bond or loan guarantee?",
        name: "isFederalDebt",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you obligated to pay alimony, child support, or separate maintenance?",
        name: "isObligatedToPayAlimony",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Is any part of the down payment borrowed?",
        name: "isDownPaymentBorrowed",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a co-maker or endorser on a note?",
        name: "isEndorser",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a US citizen?",
        name: "isUSCitizen",
        options: [{
            text: "Yes"
        }, {
            text: "No",
            addQuestions: [{
                type: "yesno",
                text: "Are you a permanent resident alien?",
                name: "isPermanentResidentAlien",
                options: [{
                    text: "Yes",
                    value: "Yes"
                }, {
                    text: "No",
                    value: "No"
                }],
                "selected": ""
            }]
        }],
        selected: ""
    },
    {
        type: "yesno",
        text: "Do you intend to occupy the property as your primary residence?",
        name: "isOccupyPrimaryResidence",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    },
    {
        type: "yesno",
        text: "Have you had an ownership interest in a property in the last three years?",
        name: "isOwnershipInterestInProperty",
        options: [{
            text: "Yes",
            addQuestions: [{
                type: "select",
                text: "What type of property did you own? Select the choice that fits best.",
                name: "yourPrimaryResidence",
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
                name: "propertyStatus",
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

   // var questionsContainer = getQuestionsContainer(questions);
    
    $('#app-right-panel').append(quesHeaderTextCont);
   
   
    
    for(var i=0;i<questions.length;i++){
    	var question=questions[i];
    	var contxt=getQuestionContext(question,$('#app-right-panel'));
    	contxt.drawQuestion();
    	
    	quesDeclarationContxts.push(contxt);
    }

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	
    	isOutstandingJudgments =  quesDeclarationContxts[0].value;
    	isBankrupt =  quesDeclarationContxts[1].value;
    	isPropertyForeclosed =  quesDeclarationContxts[2].value;
    	isLawsuit =  quesDeclarationContxts[3].value;
    	isObligatedLoan =  quesDeclarationContxts[4].value;
    	isFederalDebt =  quesDeclarationContxts[5].value;
    	isObligatedToPayAlimony =  quesDeclarationContxts[6].value;
    	//appUserDetails["isDownPaymentBorrowed"] =quesDeclarationContxts[7].value;
    	isEndorser =  quesDeclarationContxts[8].value;
    	
    	isUSCitizen =  quesDeclarationContxts[9].value;
    
    	//appUserDetails["isPermanentResidentAlien"] = null;
    	//if(quesDeclarationContxts[9].childContexts.No != undefined)
    		//isOccupyPrimaryResidence = quesDeclarationContxts[9].childContexts.No[0].value;
    	
    	 isOccupyPrimaryResidence =  quesDeclarationContxts[10].value;
    	 isOwnershipInterestInProperty =  quesDeclarationContxts[11].value;
    	
    	//appUserDetails["yourPrimaryResidence"] = null;
    	//if(quesDeclarationContxts[11].childContexts.Yes != undefined)
    	//appUserDetails["yourPrimaryResidence"] = quesDeclarationContxts[11].childContexts.Yes[0].value;
    	
    	//appUserDetails["propertyStatus"] =null;
    	//if(quesDeclarationContxts[11].childContexts.Yes != undefined)
    	//appUserDetails["propertyStatus"] = quesDeclarationContxts[11].childContexts.Yes[1].value;
    	 
    	 
    	 if( isOutstandingJudgments =="Yes"){ 
    		 governmentquestion.isOutstandingJudgments = true;
 		 }else{
 			governmentquestion.isOutstandingJudgments = false;
 		 }
    	 
    	 if( isBankrupt =="Yes"){ 
    		 governmentquestion.isBankrupt = true;
 		 }else{
 			governmentquestion.isBankrupt = false;
 		 }
    	 
    	 if( isBankrupt =="Yes"){ 
    		 governmentquestion.isPropertyForeclosed = true;
 		 }else{
 			governmentquestion.isPropertyForeclosed = false;
 		 }
    	 
    	 if( isBankrupt =="Yes"){ 
    		 governmentquestion.isLawsuit = true;
 		 }else{
 			governmentquestion.isLawsuit = false;
 		 }

    	 if( isObligatedLoan =="Yes"){ 
    		 governmentquestion.isObligatedLoan = true;
 		 }else{
 			governmentquestion.isObligatedLoan = false;
 		 }
    	 
    	 if( isFederalDebt =="Yes"){ 
    		 governmentquestion.isFederalDebt = true;
 		 }else{
 			governmentquestion.isFederalDebt = false;
 		 }

    	 if( isObligatedToPayAlimony =="Yes"){ 
    		 governmentquestion.isObligatedToPayAlimony = true;
 		 }else{
 			governmentquestion.isObligatedToPayAlimony = false;
 		 }
    	 
    	 if( isEndorser =="Yes"){ 
    		 governmentquestion.isEndorser = true;
 		 }else{
 			governmentquestion.isEndorser = false;
 		 }

    	 if( isUSCitizen =="Yes"){ 
    		 governmentquestion.isUSCitizen = true;
 		 }else{
 			governmentquestion.isUSCitizen = false;
 		 }

    	 if( isOccupyPrimaryResidence =="Yes"){ 
    		 governmentquestion.isOccupyPrimaryResidence = true;
 		 }else{
 			governmentquestion.isOccupyPrimaryResidence = false;
 		 }
    	 
    	 if( isOwnershipInterestInProperty =="Yes"){ 
    		 governmentquestion.isOwnershipInterestInProperty = true;
 		 }else{
 			governmentquestion.isOwnershipInterestInProperty = false;
 		 }
    	 

    	 
    	 //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    	 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4b());
    	
    	//paintCustomerApplicationPageStep4b();
    });

    $('#app-right-panel').append(saveAndContinueButton);
}

function paintCustomerApplicationPageStep4b(){
	
	
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Government Monitoring Questions";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    });

	var options = [ {
		"text" : "No thank you. Let’s move on",
		"name" : name,
		"value" : 0
	}];
	var quesCont = paintGovernmentMonitoringQuestions(quesHeaderTxt, options, name);

	$('#app-right-panel').append(quesCont);
    

    
    ///
    var questions = [{
        type: "select",
        text: "Ethnicity",
        name: "ethnicity",
        options: [{
            text: "Hispanic",
            value: "hispanic"
        }, {
            text: "Non Hispanic or Latino",
            value: "latino"
        }],
        selected: ""
    }, {
        type: "select",
        text: "Race",
        name: "race",
        options: [{
            text: "American Indian or Alaska Native",
            value: "americanIndian"
        }, {
            text: "Native Hawaiian or Pacific Islander",
            value: "nativeHawaiian "
        }, {
            text: "Black or African American",
            value: "black"
        },
        {
            text: "White",
            value: "white"
        },
        {
            text: "Asian",
            value: "asian"
        }],
        selected: ""
    }, {
        type: "select",
        text: "Sex",
        name: "sex",
        options: [{
            text: "Male",
            value: "male"
        }, {
            text: "Female",
            value: "female "
        }],
      selected: ""
    }];

	
	 var questionsContainer = getQuestionsContainer(questions);
	
	 var saveAndContinueButton = $('<div>').attr({
	        "class": "app-save-btn"
	    }).html("Save & continue").on('click', function() {
	    	
	    	ethnicity =  $('.app-options-cont[name="ethnicity"]').find('.app-option-selected').data().value;
	    	race =  $('.app-options-cont[name="race"]').find('.app-option-selected').data().value;
	    	sex =  $('.app-options-cont[name="sex"]').find('.app-option-selected').data().value;
	    	
	    	
	    	governmentquestion.ethnicity = ethnicity;
	    	governmentquestion.race = race;
	    	governmentquestion.sex =sex;
	    	
	    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep5());
	    	//paintCustomerApplicationPageStep5();
	    });

	    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
	
}

function paintCustomerApplicationPageStep5() {
    
	appProgressBaar(5);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "My Income";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "birthday",
        value: ""
    },
    {
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: ""
    },
    {
        type: "desc",
        text: "Phone Number",
        name: "phoneNumber",
        value: ""
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	dateOfBirth = $('input[name="birthday"]').val();
    	ssn =  $('input[name="ssn"]').val();
    	secPhoneNumber =  $('input[name="phoneNumber"]').val();
    	
    	
    	if(dateOfBirth != undefined && dateOfBirth !="" && ssn != undefined && ssn !="" && secPhoneNumber != undefined && secPhoneNumber !=""){
    		
    		customerDetail.dateOfBirth= dateOfBirth;
    		customerDetail.ssn = ssn;
    		customerDetail.secPhoneNumber = secPhoneNumber;
    		//applicationFormSumbit();
    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    		saveAndUpdateLoanAppForm(appUserDetails,applicationFormSumbit());
    		
    	}else{
    		showToastMessage("Please give the answers of the questions");
    	}
    	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function applicationFormSumbit(){
	
	changeSecondaryLeftPanel(3);
}


function appProgressBaar(num){
	
	var count = 5;
	$("#appProgressBaarId_" + num).removeClass('ce-lp-in-progress')
			.removeClass('ce-lp-complete').addClass('ce-lp-in-progress');
	$('#appStepNoId_' + num).html(num);

	for (var i = 1; i <= num - 1; i++) {
		$("#appProgressBaarId_" + i).removeClass('ce-lp-in-progress')
				.removeClass('ce-lp-not-started').addClass('ce-lp-complete');
		$('#appStepNoId_' + i).html("");
	}
	for (var i = num + 1; i <= count; i++) {
		$("#appProgressBaarId_" + i).removeClass('ce-lp-in-progress')
				.removeClass('ce-lp-complete').addClass('ce-lp-not-started');
		$('#appStepNoId_' + i).html(i);
	}
	
}

function putCurrencyFormat(name){
	
	$("input[name="+name+"]").keydown(function() {
    	$("input[name="+name+"]").maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:true
		});		
    });
}


function paintGovernmentMonitoringQuestions(quesText, options, name) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
		
	});

	var quesTextCont = $('<div>').attr({
		"class" : "app-ques-header-txt"
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	for (var i = 0; i < options.length; i++) {

		var optionIncome = $('<div>').attr({
			"class" : "hide ce-option-ques-wrapper"
			//"id" : "ce-option_" + i
		});

		var option = $('<div>').attr({
			"class" : "ce-option-checkbox",
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : name
		}, function(event) {
			if($(this).hasClass("ce-option-checked")){
				$(this).removeClass("ce-option-checked");
			}else{
				$(this).addClass("ce-option-checked");
			}
			/*var key = event.data.name;
			appUserDetails[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);*/
			$(".app-ques-container").toggle(function() {
				
			});
		});

		optionContainer.append(option).append(optionIncome);
	}

	return container.append(quesTextCont).append(optionContainer);
}



function getMonthYearTextQuestion(question) {
	 var container = $('<div>').attr({
	  "class" : "ce-ques-wrapper"
	 });

	 var quesTextCont = $('<div>').attr({
	  "class" : "app-ques-text"
	 }).html(question.text);

	 var optionContainer = $('<div>').attr({
	  "class" : "ce-options-cont"
	 });

	 var monthDropDown = $('<select>').attr({
	  "class" : "ce-input width-75",
	  "name" : question.name
	 });
	 
	 for(var i=1;i<=12;i++){
	  
	  var option = $("<option>").html(i);
	  monthDropDown.append(option);
	 }
	 
	 var yearInput = $('<input>').attr({
	  "class" : "ce-input width-150",
	  "name" : question.name,
	  //"value" : appUserDetails[name],
	  "placeholder" : "YYYY"
	 });

	 optionContainer.append(monthDropDown).append(yearInput);


	 return container.append(quesTextCont).append(optionContainer);
	}


function saveAndUpdateLoanAppForm(appUserDetails,callBack){
	
	$.ajax({
		url:"rest/application/applyloan",
		type:"POST",
		data:{"appFormData" : JSON.stringify(appUserDetails)},
		datatype : "application/json",
		success:function(data){

			callBack;
		},
		error:function(erro){
			alert("error");
		}
		
	});
}