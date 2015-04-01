

var appUserDetails = new Object();

var purchase = false;
var user =  new Object();
var customerDetail = new Object();
user.customerDetail = customerDetail;
var customerEnagagement = new Object();



var propertyTypeMaster = new Object();
var governmentquestion = new Object();
var refinancedetails = new Object();
var loan = new Object();
var loanType = new Object();
loanType.id=1;

spouseGovernmentquestion = new Object();

appUserDetails.user = user;
appUserDetails.propertyTypeMaster = propertyTypeMaster;
appUserDetails.governmentquestion = governmentquestion;
appUserDetails.refinancedetails = refinancedetails;
appUserDetails.loan = loan;
appUserDetails.loanType = loanType;
appUserDetails.spouseGovernmentquestion = spouseGovernmentquestion;



if (sessionStorage.loanAppFormData) {
	appUserDetails = JSON.parse(sessionStorage.loanAppFormData);
}

//loan.id = 2;
//loanType.id=1;

var applyLoanStatus = 0; 
var formCompletionStatus = 1;

appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;




var applicationItemsList = [ 
                             {
							    "text":"My Priority",
	                            "onselect" : ""
	                        },
	                        {
							    "text":"My Money",
	                            "onselect" : ""
	                        },
	                        {
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
	appUserDetails = {};
	
	appUserDetails = JSON.parse(newfi.appUserDetails);
	

	user.id = newfi.user.id;
	customerDetail.id = newfi.user.customerDetail.id;
	
	user.customerEnagagement = customerEnagagement;
	
	loan.id = newfi.user.defaultLoanId;
	
	formCompletionStatus = newfi.formCompletionStatus;
	
	appUserDetails.id = newfi.loanAppFormid; 

	
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

   // paintCustomerApplicationPageStep1a();
    
     showLoanAppFormContainer(formCompletionStatus);
    
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
    contxt.container=container;
    contxt.parentContainer.append(contxt.container);
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
        "name": question.name,
        "value":question.value
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

//TODO-try nested yesno question

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
        value: appUserDetails.user.customerDetail.addressState
    }, {
        type: "desc",
        text: "Which City do you belong?",
        name: "city",
        value: appUserDetails.user.customerDetail.addressCity
    }, {
        type: "desc",
        text: "What is your Zip Code?",
        name: "zipCode",
        value: appUserDetails.user.customerDetail.addressZipCode
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function(event) {
    	
    	var inputState = $('input[name="state"]').val();
    	var city = $('input[name="city"]').val();
    	var zipCode = $('input[name="zipCode"]').val();
   
    	
    	if(inputState != undefined && inputState != "" && city != undefined && city != ""  && zipCode != undefined && zipCode != ""  ){
        	

    		customerDetail.addressCity = city;
    		customerDetail.addressState = inputState;
    		customerDetail.addressZipCode = zipCode;

    		
    		user.customerDetail = customerDetail;
    		
    		
    		appUserDetails.user = user;
    		appUserDetails.loanAppFormCompletionStatus=applyLoanStatus;
    		
    		//alert(JSON.stringify(appUserDetails));
    		saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep1b());
    		
        	        	
        }else{
        	showToastMessage("Please give answer of the questions");
        }
   	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

//TODO-try nested yesno question

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
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, {
        type: "desc",
        text: "Who provides homeowners insurance?",
        name: "insuranceProvider",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceProvider
    }, {
        type: "desc",
        text: "How much does homeowners insurance cost per year?",
        name: "insuranceCost",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceCost
    }, {
        type: "yearMonth",
        text: "When did you purchase this property?",
        name: "purchaseTime",
        value: appUserDetails.propertyTypeMaster.propertyPurchaseYear
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
        	
        	appUserDetails.loanAppFormCompletionStatus=applyLoanStatus;
        	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
        	//saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
        	saveAndUpdateLoanAppForm(appUserDetails ,paintSecondPropertyStep());
    		
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


//TODO-try nested yesno question

function paintSecondPropertyStep(){

quesContxts = [];
$('#app-right-panel').html("");
	
    var questions = [
                    
                     {
                         "type": "yesno",
                         "text": "Do you have a second mortgage on this property? ?",
                         "name": "isSecondaryMortgage",
                         "options": [
                             {
                                 "text": "Yes",
                                 "addQuestions": [
                                     {
                                         "type": "desc",
                                         "text": "What is the current balance for this additional mortgage??",
                                         "name": "secondaryMortgageBalance",
                                         "value": ""
                                     }
                                 ]
                             },
                             {
                                 "text": "No"
                             }
                         ]
                     }
                 ];
    
    for(var i=0;i<questions.length;i++){
		var question=questions[i];
		var contxt=getQuestionContextCEP(question,$('#app-right-panel'));
		contxt.drawQuestion();
		
		quesContxts.push(contxt);
	}
	
    var saveAndContinueButton = $('<div>').attr({
	    "class": "ce-save-btn"
	}).html("Save & continue").on('click', function() {
		
		   isSecondaryMortgage = quesContxts[0].value;
		   if(isSecondaryMortgage =='Yes')
			   appUserDetails.secondMortgage = true;
		   else{
			   appUserDetails.secondMortgage = false;
		   }
		   
		   
		   refinancedetails.secondMortageBalance = $('input[name="secondaryMortgageBalance"]').val();
		   
		   appUserDetails.refinancedetails = refinancedetails;
		  
		   saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
		
	      });
	
    $('#app-right-panel').append(saveAndContinueButton);


}



function getQuestionContext(question,parentContainer,valueSet){
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
	        valueSet:valueSet,
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
	            } else if (question.type == "yearMonth") {
	                quesCont = getMonthYearTextQuestionContext(ob);
	            }
	        	
	        	//parentContainer.append(ob.container);
	        	
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
	            	var contxt=getQuestionContext(question,childContainer,ob.valueSet);
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
	        },mapValues:function(value){
	        	if(value=="Yes"||value==true){
	        		return "Yes";
	        	}else if(value=="No"||value==false){
	        		return "No";
	        	}else
	        		return value;
	        }
	};
	
	 if(valueSet){
	     for(key in valueSet){
	     	if(key==contxt.name){
	     		contxt.value=contxt.mapValues(valueSet[key]);
	         	break;
	         }
	     }
	 }
	return contxt;
}



var quesContxts=[];

//TODO-try nested yesno question
function paintCustomerApplicationPageStep2() {
	
	applyLoanStatus =2;
	appProgressBaar(2); // this is to show the bubble status in the left panel
	quesContxts = []; // when ever call the above function clean the array object
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Who's on the Loan?";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);
   
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
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails);
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
    		    		
    		    		isSpouseOnLoan = quesContxts[0].childContexts.Yes[0].value;
    		    		if( isSpouseOnLoan =="Yes"){ 
    		    			appUserDetails.isSpouseOnLoan =true;
    		    		}else if(isSpouseOnLoan =="No"){
    		    			appUserDetails.isSpouseOnLoan ="false";
    		    			appUserDetails.spouseName  = false;
    		    		}else{
    		    			 showToastMessage("Please give the answers of the questions");
    	    		    	 return false;
    		    		}
    		     }else{
    		    	 showToastMessage("Please give the answers of the questions");
    		    	 return false;
    		     }
    			 
    		 }else{
    			 appUserDetails.isSpouseOnLoan =false;
	    		 appUserDetails.spouseName  = "false";
    		 }
	    	
	    	
	    	// this is the condition when spouseName is in the loan
	    	if( quesContxts[0].childContexts.Yes !=  undefined && quesContxts[0].childContexts.Yes[0].childContexts.Yes != undefined){
	    		appUserDetails.isSpouseOnLoan = true;
	    		appUserDetails.spouseName = quesContxts[0].childContexts.Yes[0].childContexts.Yes[0].value;
	    	}else{
	    		appUserDetails.spouseName  = "false";
	    	}
	    	
	    	appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
	    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	//alert(JSON.stringify(appUserDetails));
	    
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
    contxt.container=container;
    contxt.parentContainer.append(contxt.container);
    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(contxt.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": contxt.name
    });

    for (var i = 0; i < contxt.options.length; i++) {
        var option = contxt.options[i];
        var sel="false";
        if(contxt.value == option.text)
        	sel="true";
        var optionCont = $('<div>').attr({
            "class": "app-option-choice",
            "isSelected" : sel
        }).html(option.text);
         
        optionCont.bind("click",{"contxt":contxt,"value":option.text,"option":option},function(event){
        	var ctx=event.data.contxt;
        	var opt=event.data.option;
        	var val=event.data.value;
        	optionClicked($(this),ctx,opt,val);
        });
        
        optionsContainer.append(optionCont);
        
        if(contxt.value==option.text){
        	optionClicked(optionCont,contxt,option,option.text,true);
        }
    }

    return container.append(quesTextCont).append(optionsContainer);
}

function optionClicked(element,ctx,option,value,skipCondition){
	$(element).parent().find('.app-option-choice').attr("isSelected","false");
	$(element).attr("isSelected","true");
	ctx.clickHandler(value);
	if(ctx.value!=value||skipCondition){
    	ctx.value=value;
    	var opt=option;
    	if(opt.addQuestions){
    		ctx.drawChildQuestions(ctx.value,opt.addQuestions);
    	}
	}
}


function getContextApplicationTextQues(contxt) {
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });
    contxt.container=container;
    contxt.parentContainer.append(contxt.container);
    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(contxt.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont"
    });

    var optionCont = $('<input>').attr({
        "class": "app-input",
        "name": contxt.name,
        "value":contxt.value
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
    $('#app-right-panel').html('');
	var questcontainer = $('#app-right-panel').append(quesCont);
	
	console.log('purchase'+purchase);
	if(purchase == true){
		
		var questionsContainer10 = paintSaleOfCurrentHome();
		questcontainer.append(questionsContainer10);
    }
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
		"value" :appUserDetails.EmployedIncomePreTax
	});

	quesTextCont1.append(inputBox1);

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "workPlace",
		"value":appUserDetails.EmployedAt
	});

	quesTextCont2.append(inputBox2);

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Working ?");

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startWorking",
		"value" : appUserDetails.EmployedSince
	});

	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(quesTextCont3);

	putCurrencyFormat("beforeTax");
	
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
		"name" : "selfEmployed",
		"value": appUserDetails.selfEmployedIncome
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
	
	putCurrencyFormat("selfEmployed");
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
		"name" : "disability",
		"value": appUserDetails.ssDisabilityIncome
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
	
	putCurrencyFormat("disability");
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
		"name" : "pension",
		"value": appUserDetails.monthlyPension
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
	
	putCurrencyFormat("pension");
}




//////////////////This is new section for Spouse Income Details /////////









function paintMySpouseIncome() {

	applyLoanStatus = 3;
	appProgressBaar(3);
	var quesTxt = "Select all that apply";
	var options = [ {
		"text" : "Employed",
		"onselect" : paintSpouseRefinanceEmployed,
		"name" : "isSpouseEmployed",
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintSpouseRefinanceSelfEmployed,
		"name" : "isSpouseSelfEmployed",
		"value" : 1
	}, {
		"text" : "Social Security Income/Disability",
		"onselect" : paintSpouseRefinanceDisability,
		"name" :"isSpouseIncomeOrDisability",
		"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintSpouseRefinancePension,
		"name" : "isSpousePensionOrRetirement",
		"value" : 3
	} ];
	var quesCont = paintSpouseCustomerApplicationPageStep3(quesTxt, options, name);

	

$('#app-right-panel').html('');
	var questcontainer = $('#app-right-panel').append(quesCont);
	
	console.log('purchase'+purchase);
	if(purchase == true)
    {
    var questionsContainer10 = paintSpouseSaleOfCurrentHome();
   questcontainer.append(questionsContainer10);
    }



}





function paintSpouseCustomerApplicationPageStep3(quesText, options, name) {
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
		        
		        spouseBeforeTax= $('input[name="spouseBeforeTax"]').val();
		        spouseWorkPlace = $('input[name="spouseWorkPlace"]').val();
		        spouseStartWorking = $('input[name="spouseStartWorking"]').val();
				
		        spouseSelfEmployed = $('input[name="spouseSelfEmployed"]').val();
		        
		        spouseDisability = $('input[name="spouseDisability"]').val();
				
		        spousePension = $('input[name="spousePension"]').val();

				
				
				
				appUserDetails.isSpouseEmployed =  true;
				appUserDetails.spouseBeforeTax = spouseBeforeTax;
				appUserDetails.spouseWorkPlace = spouseWorkPlace;
				appUserDetails.spouseStartWorking = spouseStartWorking;
				
				appUserDetails.isSpousePensionOrRetirement= true;
				appUserDetails.spousePension =spousePension;
				
				appUserDetails.isSpouseSelfEmployed = true;
				appUserDetails.spouseSelfEmployed =spouseSelfEmployed;
				
				appUserDetails.isSpouseIncomeOrDisability=true;
				appUserDetails.spouseDisability = spouseDisability;
				
				//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
				appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
				
				
				saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a());
				//paintCustomerApplicationPageStep4a();
			});

	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}









function paintSpouseRefinanceEmployed(divId) {

	//appUserDetails.employed ="true";
	var quesTxt = "Spouse Income";
	var quesCont = getMultiTextQuestionSpouse(quesTxt);
	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(quesCont);
}

function getMultiTextQuestionSpouse(quesText) {
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
		"name" : "spouseBeforeTax",
		"value" :appUserDetails.spouseBeforeTax
	});

	quesTextCont1.append(inputBox1);

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "spouseWorkPlace",
		"value":appUserDetails.spouseWorkPlace
	});

	quesTextCont2.append(inputBox2);

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Working ?");

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "spouseStartWorking",
		"value" : appUserDetails.spouseStartWorking
	});

	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(
			quesTextCont3);

	return container.append(quesTextCont).append(optionContainer);
	
	putCurrencyFormat("spouseBeforeTax");
}

function paintSpouseRefinanceSelfEmployed(divId) {

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
		"name" : "spouseSelfEmployed",
		"value": appUserDetails.spouseSelfEmployed
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
	
	putCurrencyFormat("spouseSelfEmployed");
}

function paintSpouseRefinanceDisability(divId) {

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
		"name" : "spouseDisability",
		"value": appUserDetails.spouseDisability
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
	
	putCurrencyFormat("spouseDisability");
}

function paintSpouseRefinancePension(divId) {

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
		"name" : "spousePension",
		"value": appUserDetails.spousePension
	});

	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_' + divId).toggle();
	$('#ce-option_' + divId).html(container);
	
	putCurrencyFormat("spousePension");
}






//////////////////////////////////////////////////////////////////////////
















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
				appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
				
				
				if(appUserDetails.isSpouseOnLoan == true)
				{
				saveAndUpdateLoanAppForm(appUserDetails,paintMySpouseIncome());
				}else{
				saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a());
				}
				//paintCustomerApplicationPageStep4a();
			});

if(purchase ==true)
{
return container.append(quesTextCont).append(optionContainer);
}

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
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails.governmentquestion);
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
    	 //alert(isOutstandingJudgments);
    	 delete appUserDetails.governmentquestion;
    	 
    	 governmentquestion = {};
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
    	 
    	 if( isPropertyForeclosed =="Yes"){ 
    		 governmentquestion.isPropertyForeclosed = true;
 		 }else{
 			governmentquestion.isPropertyForeclosed = false;
 		 }
    	 
    	 if( isLawsuit =="Yes"){ 
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
    	 
    	 appUserDetails.governmentquestion =governmentquestion;
    	 appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
    	 //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    	// alert(JSON.stringify(appUserDetails));
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
	    	
	    	
	    	if(appUserDetails.isSpouseOnLoan == true)
				{
				saveAndUpdateLoanAppForm(appUserDetails,paintSpouseCustomerApplicationPageStep4a());
				}else{
				 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep5());
				}
	    	
	    	//paintCustomerApplicationPageStep5();
	    });

	    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
	
}

function paintCustomerApplicationPageStep5() {
	
	applyLoanStatus = 5;
	appProgressBaar(5);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "My Credit";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "birthday",
        value: $.datepicker.formatDate('mm/dd/yy', new Date(appUserDetails.user.customerDetail.dateOfBirth))
    },
    {
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: appUserDetails.user.customerDetail.ssn
    },
    {
        type: "desc",
        text: "Phone Number",
        name: "phoneNumber",
        value: appUserDetails.user.customerDetail.secPhoneNumber
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	dateOfBirth = $('input[name="birthday"]').val();
    	ssn =  $('input[name="ssn"]').val();
    	secPhoneNumber =  $('input[name="phoneNumber"]').val();
    	
    	
    	if(dateOfBirth != undefined && dateOfBirth !="" && ssn != undefined && ssn !="" && secPhoneNumber != undefined && secPhoneNumber !=""){
    		
    		//appUserDetails.customerDetail
    		
    		customerDetailTemp =  appUserDetails.user.customerDetail;
    		customerDetailTemp.dateOfBirth= new Date(dateOfBirth).getTime();
    		customerDetailTemp.ssn = ssn;
    		customerDetailTemp.secPhoneNumber = secPhoneNumber;
    		//applicationFormSumbit();
    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    		
    		appUserDetails.user.customerDetail = customerDetailTemp;
    		//alert(JSON.stringify(customerDetail));
    		appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
    		
    ///alert(JSON.stringify(appUserDetails));
    		saveAndUpdateLoanAppForm(appUserDetails,applicationFormSumbit(appUserDetails));
    		
    	}else{
    		showToastMessage("Please give the answers of the questions");
    	}
    	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}

function applicationFormSumbit(){
	
	//createLoan();
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
	  "value" : appUserDetails.propertyTypeMaster.propertyPurchaseYear,
	  "placeholder" : "YYYY"
	 });

	 optionContainer.append(monthDropDown).append(yearInput);


	 return container.append(quesTextCont).append(optionContainer);
	}

function getMonthYearTextQuestionContext(contxt) {
	 var container = $('<div>').attr({
	  "class" : "ce-ques-wrapper"
	 });

	 contxt.container=container;
	 contxt.parentContainer.append(contxt.container);
	 
	 var quesTextCont = $('<div>').attr({
	  "class" : "app-ques-text"
	 }).html(contxt.text);

	 var optionContainer = $('<div>').attr({
	  "class" : "ce-options-cont"
	 });

	 var monthDropDown = $('<select>').attr({
	  "class" : "ce-input width-75",
	  "name" : contxt.name
	 });
	 
	 for(var i=1;i<=12;i++){
	  
	  var option = $("<option>").html(i);
	  monthDropDown.append(option);
	 }
	 
	 var yearInput = $('<input>').attr({
	  "class" : "ce-input width-150",
	  "name" : contxt.name,
	  "value" : appUserDetails.propertyTypeMaster.propertyPurchaseYear,
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



function showLoanAppFormContainer(formCompletionStatus){
	
	switch (formCompletionStatus) {
    case 0:
    	appProgressBaar(1);
    	//paintCustomerApplicationPageStep1a();
    	paintSelectLoanTypeQuestion();
        break;
    case 1:
    	appProgressBaar(2);
		//paintCustomerApplicationPageStep2();
		paintSelectLoanTypeQuestion();
        break;
    case 2:
    	appProgressBaar(3);
		//paintMyIncome();
		paintSelectLoanTypeQuestion();
        break;
    case 3:
    	appProgressBaar(4);
		//paintCustomerApplicationPageStep4a();
		paintSelectLoanTypeQuestion();
        break;
    case 4:
    	appProgressBaar(5);
    	//paintCustomerApplicationPageStep5();
    	paintSelectLoanTypeQuestion();
        break;
    case 5:
    	appProgressBaar(6);
    	paintSelectLoanTypeQuestion();
    	//paintCustomerApplicationPageStep5();
    default:
    	

	}
}


///////////////////////NEW CODE ADDED ////////////////////////////


function paintSelectLoanTypeQuestion() {
    

	console.log("Inside paintSelectLoanTypeQuestion ");
	stages = 0;
	//$('#ce-main-container').html('');
    $('#app-right-panel').html('');
	
   /* var rateIcon = $('<div>').attr({
		"class" : "ce-rate-icon"
	});*/

	var titleText = $('<div>').attr({
		"class" : "ce-title",
		"style":"text-align: left"
	}).html("Choose your loan type");

	//$('#app-right-panel').append(rateIcon).append(titleText);
	$('#app-right-panel').append(titleText);

	var wrapper = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var optionsContainer = $('<div>').attr({
		"class" : "ce-ques-options-container clearfix"
	});

	var option1 = $('<div>').attr({
		"class" : "ce-option"
	}).html("Refinance").on('click', function() {
		
		customerEnagagement.loanType = "REF";
				
		user.customerEnagagement = customerEnagagement;
				
		appUserDetails.user = user;
		
		paintRefinanceMainContainer();
	});

	var option2 = $('<div>').attr({
		"class" : "ce-option"
	}).html("Buy a home").on('click', function() {
		
		appUserDetails.chooseLoanType = "buyHome";
		paintBuyHomeContainer();
	});

	optionsContainer.append(option1).append(option2);

	/*var question = $('<div>').attr({
				"class" : "ce-ques-text"
			})
			.html("Why Pay thousands more to use a loan officer ?<br/>With newfi, saving weeks of headache and thousands of dollars is easy.");*/

	//wrapper.append(optionsContainer).append(question);
	wrapper.append(optionsContainer);

	$('#app-right-panel').append(wrapper);
}

function paintRefinanceMainContainer() {
purchase = false;
	$('#app-right-panel').html('');
	var wrapper = $('<div>').attr({
		"class" : "ce-refinance-wrapper clearfix"
	});

	//var leftPanel = getRefinanceLeftPanel();

	var centerPanel = $('<div>').attr({
		"id" : "ce-refinance-cp",
		"class" : "ce-cp float-left"
	});

	wrapper.append(centerPanel);
	$('#app-right-panel').append(wrapper);

	paintRefinanceQuest1();
}


function paintRefinanceQuest1() {

	stages = 1;
	//progressBaar(1);
	var quesText = "Why do you want to refinance?";

	var options = [ {
		"text" : "Lower My Monthly Payment",
		"onselect" : paintRefinanceStep2,
		"value" : "REFLMP"
	}, {
		"text" : "Pay Off My Mortgage Faster",
		"onselect" : paintRefinanceStep1a,
		"value" : "payOffMortgage"
	}, {
		"text" : "Take Cash Out of My Home",
		"onselect" : paintRefinanceStep1b,
		"value" : "takeCashOut"
	} ];

	var quesCont = getMutipleChoiceQuestion(quesText, options,"refinanceOption");

	$('#app-right-panel').html(quesCont);
	/*
	 * $("#progressBaarId_1").addClass('ce-lp-in-progress');
	 * $('#stepNoId_1').html("1");
	 */

}





function paintRefinanceStep2() {

quesContxts = [];
stages = 2;
//progressBaar(2);

$('#app-right-panel').html('');
var questions = [
                {
                    "type": "desc",
                    "text": "What is your current mortgage balance?",
                    "name": "currentMortgageBalance",
                    "value": ""
                }];

		for(var i=0;i<questions.length;i++){
			var question=questions[i];
			var contxt=getQuestionContextCEP(question,$('#app-right-panel'));
			contxt.drawQuestion();
			
			quesContxts.push(contxt);
		}
		
		var saveAndContinueButton = $('<div>').attr({
		    "class": "ce-save-btn"
		}).html("Save & continue").bind('click',{'contxt':contxt}, function(event) {
			
			appUserDetails.currentMortgageBalance = $('input[name="currentMortgageBalance"]').val();
			paintRefinanceStep3();
			
			//paintCustomerApplicationPageStep1a();
		   });
		
	$('#app-right-panel').append(saveAndContinueButton);
}



function paintRefinanceStep1b() {
	var quesTxt = "How much cash do you want to take out?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2, "cashTakeOut");
	$('#app-right-panel').html(quesCont);
}

function paintRefinanceStep3() {
//stages = 3;
//progressBaar(3);
	quesContxts = [];
	
	$('#app-right-panel').html("");
	
    var questions = [
                     {
                         "type": "desc",
                         "text": "What is your current mortgage payment?",
                         "name": "currentMortgagePayment",
                         "value": ""
                     },
                     {
                         "type": "yesno",
                         "text": "Does the payment entered above include property taxes and/or homeowner insuranace ?",
                         "name": "isIncludeTaxes",
                         "options": [
                             {
                                 "text": "Yes",
                                 "addQuestions": [
                                     {
                                         "type": "desc",
                                         "text": "How much are your annual property taxes?",
                                         "name": "annualPropertyTaxes",
                                         "value": ""
                                     },
                                     {
                                         "type": "desc",
                                         "text": "How much is your annual homeowners insurance ?",
                                         "name": "annualHomeownersInsurance",
                                         "value": ""
                                     }
                                 ]
                             },
                             {
                                 "text": "No"
                             }
                         ]
                     }
                 ];
    
    for(var i=0;i<questions.length;i++){
		var question=questions[i];
		var contxt=getQuestionContextCEP(question,$('#app-right-panel'));
		contxt.drawQuestion();
		
		quesContxts.push(contxt);
	}
	
    var saveAndContinueButton = $('<div>').attr({
	    "class": "ce-save-btn"
	}).html("Save & continue").on('click', function() {
		
			appUserDetails.currentMortgagePayment = $('input[name="currentMortgagePayment"]').val();		  
			appUserDetails.isIncludeTaxes = quesContxts[1].value;
			appUserDetails.annualPropertyTaxes = $('input[name="annualPropertyTaxes"]').val();
			appUserDetails.annualHomeownersInsurance = $('input[name="annualHomeownersInsurance"]').val();
		  
		    paintCustomerApplicationPageStep1a();
		
	      });
	
    $('#app-right-panel').append(saveAndContinueButton);
}


function paintRefinanceStep1a() {

	var quesTxt = "How many years are left on your mortgage?";

	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2,"yearLeftOnMortgage");

	$('#app-right-panel').html(quesCont);
}





function getQuestionContextCEP(question,parentContainer){
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
	            	ob.container = getContextApplicationTextQuesCEP(ob);
	            } else if (ob.type == "select") {
	            	ob.container = getContextApplicationSelectQues(ob);
	            } else if (ob.type == "yesno") {
	            	ob.container = getContextApplicationYesNoQuesCEP(ob);
	            }
	        	
	        	//parentContainer.append(ob.container);
	        	
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
	            	var contxt=getQuestionContextCEP(question,childContainer,ob.valueSet);
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
	        },mapValues:function(value){
	        	if(value=="Yes"||value==true){
	        		return "Yes";
	        	}else if(value=="No"||value==false){
	        		return "No";
	        	}else
	        		return value;
	        }
	};
	
	/* if(valueSet){
	     for(key in valueSet){
	     	if(key==contxt.name){
	     		contxt.value=contxt.mapValues(valueSet[key]);
	         	break;
	         }
	     }
	 }*/
	return contxt;
}



function getContextApplicationTextQuesCEP(contxt) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    contxt.container=container;
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
        "value":contxt.value
    }).bind("change",{"contxt":contxt},function(event){
    	var ctx=event.data.contxt;
    	ctx.value=$(this).val();
    }).on("load keydown", function(e){
          
		if(contxt.name != 'zipCode' && contxt.name != 'yearLeftOnMortgage' && contxt.name != 'locationZipCode'){
			$('input[name='+contxt.name+']').maskMoney({
				thousands:',',
				decimal:'.',
				allowZero:true,
				prefix: '$',
			    precision:0,
			    allowNegative:true
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
    contxt.container=container;
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
        var sel="false";
        if(contxt.value == option.text)
        	sel="true";
        var optionCont = $('<div>').attr({
            "class": "app-option-choice",
            "isSelected" : sel
        }).html(option.text);
         
        optionCont.bind("click",{"contxt":contxt,"value":option.text,"option":option},function(event){
        	var ctx=event.data.contxt;
        	var opt=event.data.option;
        	var val=event.data.value;
        	optionClicked($(this),ctx,opt,val);
        });
        
        optionsContainer.append(optionCont);
        
        if(contxt.value==option.text){
        	optionClicked(optionCont,contxt,option,option.text,true);
        }
    }

    return container.append(quesTextCont).append(optionsContainer);
}


function optionClicked(element,ctx,option,value,skipCondition){
	$(element).parent().find('.app-option-choice').attr("isSelected","false");
	$(element).attr("isSelected","true");
	ctx.clickHandler(value);
	if(ctx.value!=value||skipCondition){
    	ctx.value=value;
    	var opt=option;
    	if(opt.addQuestions){
    		ctx.drawChildQuestions(ctx.value,opt.addQuestions);
    	}
	}
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
	//	"value" : refinanceTeaserRate[name]
	}).on("load keydown", function(e){
          
		if(name != 'zipCode' && name != 'yearLeftOnMortgage'){
			$('input[name='+name+']').maskMoney({
				thousands:',',
				decimal:'.',
				allowZero:true,
				prefix: '$',
			    precision:0,
			    allowNegative:true
			});
		}
		
	});
	
	optionContainer.append(inputBox);

	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue").bind('click', {
		'clickEvent' : clickEvent,
		"name" : name
	}, function(event) {
		var key = event.data.name;
		console.log('key'+key);
		inputValue= $('input[name="' + key + '"]').val();

		appUserDetails[key]  = inputValue;
		//sessionStorage.refinaceData = JSON.stringify(refinanceTeaserRate);
        if(inputValue != undefined && inputValue != "" && inputValue != "$0"){
        	console.log("event.data.clickEvent");
        	event.data.clickEvent();
        }else{
        	showToastMessage("Please give awnsers of the questions");
        }
	});

	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
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

		//	refinanceTeaserRate[key] = event.data.option.value;
			
			customerEnagagement[key] = event.data.option.value;			
			user.customerEnagagement = customerEnagagement;					
			appUserDetails.user = user;	
			
			event.data.option.onselect();
		});
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}






////////////Declaration and Government Question for Spouse///////


function paintSpouseCustomerApplicationPageStep4a() {
   
	applyLoanStatus = 4;
	quesDeclarationContxts = [];
	appProgressBaar(4);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Spouse Declaration Questions";

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
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails.governmentquestion);
    	contxt.drawQuestion();
    	
    	quesDeclarationContxts.push(contxt);
    }

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	
    	isSpouseOutstandingJudgments =  quesDeclarationContxts[0].value;
    	isSpouseBankrupt =  quesDeclarationContxts[1].value;
    	isSpousePropertyForeclosed =  quesDeclarationContxts[2].value;
    	isSpouseLawsuit =  quesDeclarationContxts[3].value;
    	isSpouseObligatedLoan =  quesDeclarationContxts[4].value;
    	isSpouseFederalDebt =  quesDeclarationContxts[5].value;
    	isSpouseObligatedToPayAlimony =  quesDeclarationContxts[6].value;
    	//appUserDetails["isDownPaymentBorrowed"] =quesDeclarationContxts[7].value;
    	isSpouseEndorser =  quesDeclarationContxts[8].value;
    	
    	isSpouseUSCitizen =  quesDeclarationContxts[9].value;
    
    	//appUserDetails["isPermanentResidentAlien"] = null;
    	//if(quesDeclarationContxts[9].childContexts.No != undefined)
    		//isOccupyPrimaryResidence = quesDeclarationContxts[9].childContexts.No[0].value;
    	
    	 isSpouseOccupyPrimaryResidence =  quesDeclarationContxts[10].value;
    	 isSpouseOwnershipInterestInProperty =  quesDeclarationContxts[11].value;
    	
    	//appUserDetails["yourPrimaryResidence"] = null;
    	//if(quesDeclarationContxts[11].childContexts.Yes != undefined)
    	//appUserDetails["yourPrimaryResidence"] = quesDeclarationContxts[11].childContexts.Yes[0].value;
    	
    	//appUserDetails["propertyStatus"] =null;
    	//if(quesDeclarationContxts[11].childContexts.Yes != undefined)
    	//appUserDetails["propertyStatus"] = quesDeclarationContxts[11].childContexts.Yes[1].value;
    	 //alert(isOutstandingJudgments);
    	 delete appUserDetails.spouseGovernmentquestion;
    	 
    	 spouseGovernmentquestion = {};
    	 if( isOutstandingJudgments =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseOutstandingJudgments = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseOutstandingJudgments = false;
 		 }
    	 
    	 if( isSpouseBankrupt =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseBankrupt = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseBankrupt = false;
 		 }
    	 
    	 if( isSpousePropertyForeclosed =="Yes"){ 
    		 spouseGovernmentquestion.isSpousePropertyForeclosed = true;
 		 }else{
 			spouseGovernmentquestion.isSpousePropertyForeclosed = false;
 		 }
    	 
    	 if( isSpouseLawsuit =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseLawsuit = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseLawsuit = false;
 		 }

    	 if( isSpouseObligatedLoan =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseObligatedLoan = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseObligatedLoan = false;
 		 }
    	 
    	 if( isSpouseFederalDebt =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseFederalDebt = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseFederalDebt = false;
 		 }

    	 if( isSpouseObligatedToPayAlimony =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseObligatedToPayAlimony = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseObligatedToPayAlimony = false;
 		 }
    	 
    	 if( isSpouseEndorser =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseEndorser = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseEndorser = false;
 		 }

    	 if( isSpouseUSCitizen =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseUSCitizen = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseUSCitizen = false;
 		 }

    	 if( isSpouseOccupyPrimaryResidence =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseOccupyPrimaryResidence = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseOccupyPrimaryResidence = false;
 		 }
    	 
    	 if( isSpouseOwnershipInterestInProperty =="Yes"){ 
    		 spouseGovernmentquestion.isSpouseOwnershipInterestInProperty = true;
 		 }else{
 			spouseGovernmentquestion.isSpouseOwnershipInterestInProperty = false;
 		 }
    	 
    	 appUserDetails.spouseGovernmentquestion =spouseGovernmentquestion;
    	 appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
    	 //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    	// alert(JSON.stringify(appUserDetails));
    	 saveAndUpdateLoanAppForm(appUserDetails,paintSpouseCustomerApplicationPageStep4b());
    	
    	//paintCustomerApplicationPageStep4b();
    });

    $('#app-right-panel').append(saveAndContinueButton);
}

function paintSpouseCustomerApplicationPageStep4b(){
	
	
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Spouse Government Monitoring Questions";

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
	    	
	    	
	    	spouseGovernmentquestion.ethnicity = ethnicity;
	    	spouseGovernmentquestion.race = race;
	    	spouseGovernmentquestion.sex =sex;
	    	
	    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep5());
	    	//paintCustomerApplicationPageStep5();
	    });

	    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
	
}
 






function createLoan(appUserDetails,callBack)
{
//alert('inside create loan method');
$.ajax({
		url:"rest/application/createLoan",
		type:"POST",
		data:{"appFormData" : JSON.stringify(appUserDetails)},
		datatype : "application/json",
		success:function(data){

			callBack;
		},
		error:function(erro){
			alert("success");
		}
		
	});
}
