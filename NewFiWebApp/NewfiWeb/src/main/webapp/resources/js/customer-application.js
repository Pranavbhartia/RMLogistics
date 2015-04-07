var appUserDetails = new Object();

var purchase = false;
var user =  new Object();
var customerDetail = new Object();
var customerSpouseDetail = new Object();
var customerEmploymentIncome = new Object();
var customerBankAccountDetails = new Object();
var customerRetirementAccountDetails = new Object();
var customerOtherAccountDetails = new Object();

//customerDetail.customerSpouseDetail = customerSpouseDetail;
//customerDetail.customerEmploymentIncome = customerEmploymentIncome;
//customerDetail.customerBankAccountDetails = customerBankAccountDetails;
//customerDetail.customerRetirementAccountDetails = customerRetirementAccountDetails;
//customerDetail.customerOtherAccountDetails = customerOtherAccountDetails;

user.customerDetail = customerDetail;


var customerEnagagement = new Object();

var purchaseDetails = new Object();
appUserDetails.purchaseDetails = purchaseDetails;
appUserDetails.customerSpouseDetail = customerSpouseDetail;
var propertyTypeMaster = new Object();
var governmentquestion = new Object();
var refinancedetails = new Object();
var loan = new Object();
var loanType = new Object();
//loanType.id=1;

spouseGovernmentQuestions = new Object();

appUserDetails.user = user;
appUserDetails.propertyTypeMaster = propertyTypeMaster;
appUserDetails.governmentquestion = governmentquestion;
appUserDetails.refinancedetails = refinancedetails;
appUserDetails.loan = loan;
appUserDetails.loanType = loanType;

appUserDetails.spouseGovernmentQuestions = spouseGovernmentQuestions;



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
	                        /*{
							    "text":"My Money",
	                            "onselect" : ""
	                        },*/
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
	
	
	
	
	 if(appUserDetails.propertyTypeMaster != null){
		 propertyTypeMaster.id = appUserDetails.propertyTypeMaster.id;
		 propertyTypeMaster.homeWorthToday=appUserDetails.propertyTypeMaster.homeWorthToday;
	 }
	 if(appUserDetails.refinancedetails !=null){
	 refinancedetails.id = appUserDetails.refinancedetails.id;
	 }
	 
	 
	 if(newfi.customerSpouseDetail != null){
		 customerSpouseDetail.id = newfi.customerSpouseDetail.id;
	 }
	 if(newfi.customerEmploymentIncome !=null){
	 customerEmploymentIncome.id = newfi.customerEmploymentIncome.id;
	 }
	 if(newfi.customerBankAccountDetails != null){
	 customerBankAccountDetails.id = newfi.customerBankAccountDetails.id;
	 }
	 if(newfi.customerRetirementAccountDetails != null){
	 customerRetirementAccountDetails.id = newfi.customerRetirementAccountDetails.id;
	 }
	 if(newfi.user.customerDetail.customerOtherAccountDetails !=null){
	 customerOtherAccountDetails.id = newfi.user.customerDetail.customerOtherAccountDetails.id;
	 }
	
	//user.customerEnagagement = customerEnagagement;
	/*user.customerEmploymentIncome = customerEmploymentIncome;
	user.customerBankAccountDetails = customerBankAccountDetails;*/
	
	
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
    
    // showLoanAppFormContainer(formCompletionStatus);
    
    paintSelectLoanTypeQuestion();
    
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

    var requird = $('<span>').attr({
        "style": "color:red",
    }).html("*");
    
    
    var optionCont = $('<input>').attr({
        "class": "app-input",
        "name": question.name,
        "value":question.value
    }).on("load keydown", function(e){
          
		
	
        if (question.name != 'zipCode' && question.name != 'yearLeftOnMortgage' && question.name != 'locationZipCode' && question.name != 'buyhomeZipPri' && question.name != 'city' && question.name != 'state' && question.name != 'startLivingTime' && question.name != 'spouseName' && question.name != 'phoneNumber'&& question.name != 'insuranceProvider' ) {
			$('input[name='+question.name+']').maskMoney({
				thousands:',',
				decimal:'.',
				allowZero:true,
				prefix: '$',
			    precision:0,
			    allowNegative:true
			});
		}
		
		        if (question.name == 'ssn') {
            $('input[name="ssn"]').attr('type', 'password');
        }
		
	});

    if (question.value != undefined) {
        optionCont.val(question.value);
    }

quesTextCont.append(requird)
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
	appProgressBaar(2);
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
	appProgressBaar(3); // this is to show the bubble status in the left panel
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
	    		
	    		customerSpouseDetail.spouseName = quesContxts[0].childContexts.Yes[0].childContexts.Yes[0].value;
	    	
	    	}else{
	    		appUserDetails.spouseName  = "false";
	    	}
	    	appUserDetails.customerSpouseDetail = customerSpouseDetail;
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
    }).on("load keydown", function(e){
          
		if(contxt.name != 'zipCode' && contxt.name != 'yearLeftOnMortgage' && contxt.name != 'locationZipCode' && contxt.name != 'buyhomeZipPri'  && contxt.name != 'city' && contxt.name != 'state' && contxt.name != 'startLivingTime' && contxt.name != 'spouseName'){
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



function incomesSelectALLThatApply() {

	
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
	
	    var incomesSelectALLThatApplyDiv = paintCustomerApplicationPageStep3(quesTxt, options, name);
    return incomesSelectALLThatApplyDiv;
}
 
function paintMyIncome() {
    //applyLoanStatus = 3;
    appProgressBaar(4);
    $('#app-right-panel').html('');
    var incomesSelectALLThatApplyDiv = incomesSelectALLThatApply();
    var questcontainer = $('#app-right-panel').append(incomesSelectALLThatApplyDiv);
 
  
	
	console.log('purchase'+purchase);
	if(purchase == true){
		
		var questionsContainer10 = paintSaleOfCurrentHome();
		        questcontainer.append(paintSaleOfCurrentHomeDIV);
    }
   
   
   
    var saveAndContinueButton = $('<div>').attr({
        "class": "ce-save-btn"
    }).html("Save1 & continue").on('click', function(event) {
    
    
    
    
    var  customerEmploymentIncome = [];
     
     $("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
      customerEmploymentIncome1 = {};
      
      EmployedIncomePreTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="beforeTax"]').val();
      EmployedAt = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="workPlace"]').val();
      EmployedSince = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="startWorking"]').val();
     
      customerEmploymentIncome1.employedIncomePreTax = EmployedIncomePreTax;
      customerEmploymentIncome1.employedAt = EmployedAt;
      customerEmploymentIncome1.employedSince = EmployedSince;
      var termp = {};
      termp.customerEmploymentIncome = customerEmploymentIncome1;
      
      customerEmploymentIncome.push(termp);
     });
     

appUserDetails.customerEmploymentIncome=customerEmploymentIncome;
    selfEmployedIncome = $('input[name="selfEmployed"]').val();
		        
		        ssDisabilityIncome = $('input[name="disability"]').val();
				
		        monthlyPension = $('input[name="pension"]').val();

		
				if(monthlyPension != "" && monthlyPension != undefined){
					
					appUserDetails.ispensionOrRetirement= true;
					appUserDetails.monthlyPension =monthlyPension;
				}
				
				
				if(selfEmployedIncome != "" && selfEmployedIncome != undefined){
					
					appUserDetails.isselfEmployed = true;
					appUserDetails.selfEmployedIncome =selfEmployedIncome;
				}
				
				if(ssDisabilityIncome !="" && ssDisabilityIncome != undefined){
					
					appUserDetails.isssIncomeOrDisability=true;
					appUserDetails.ssDisabilityIncome = ssDisabilityIncome;
				}
				
				
				//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
				appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
			

		
				
				
       

       
        homelistprice = $('input[name="homelistprice"]').val();
        homemortgagebalance = $('input[name="homemortgagebalance"]').val();
        inverstInPurchase = $('input[name="inverstInPurchase"]').val();
        /* Bank Account Start*/
        accountSubType = $('.app-options-cont[name="bankAccount"]').find('.app-option-selected').text();
        currentAccountBalance = $('input[name="bankAccountCurrentBankBalance"]').val();
        amountForNewHome = $('input[name="bankAccountUsefornewhome"]').val();
      //  appUserDetails.customerBankAccountDetails.accountSubType = accountSubType;
        //appUserDetails.customerBankAccountDetails.currentAccountBalance = currentAccountBalance;
        //appUserDetails.customerBankAccountDetails.amountForNewHome = amountForNewHome;
        /* Bank Account End*/
        /* Retirement Account Start*/
        //accountSubType = $('.app-options-cont[name="accountType"]').find('.app-option-selected').text();
        currentAccountBalance = $('input[name="accountTypeCurrentBankBalance"]').val();
        accountTypeUseForNewHome = $('input[name="accountTypeUseForNewHome"]').val();
       // appUserDetails.customerRetirementAccountDetails.accountSubType = accountSubType;
        //appUserDetails.customerRetirementAccountDetails.currentAccountBalance = currentAccountBalance;
        //appUserDetails.customerRetirementAccountDetails.amountForNewHome = accountTypeUseForNewHome;
        /* Retirement Account Ends*/
        /* Other Account Start*/
        otherAccountName = $('.app-options-cont[name="otherAccounts"]').find('.app-option-selected').text();
        otherAccountCurrentBankBalance = $('input[name="otherAccountCurrentBankBalance"]').val();
        otherAccountsUseForNewHome = $('input[name="otherAccountsUseForNewHome"]').val();
       // appUserDetails.customerOtherAccountDetails.accountSubType = otherAccountName;
        //appUserDetails.customerOtherAccountDetails.currentAccountBalance = otherAccountCurrentBankBalance;
        //appUserDetails.customerOtherAccountDetails.amountForNewHome = otherAccountsUseForNewHome;
        /* Other Account Ends*/
        /*appUserDetails.homelistprice = homelistprice;
             appUserDetails.homemortgagebalance = homemortgagebalance;
             appUserDetails.inverstInPurchase = inverstInPurchase;
                                     
             appUserDetails.accountTypeCurrentBankBalance = accountTypeCurrentBankBalance;                       
             appUserDetails.accountTypeUseForNewHome = accountTypeUseForNewHome;
             appUserDetails.otherAccountName = otherAccountName;
             appUserDetails.otherAccountsUseForNewHome = otherAccountsUseForNewHome;*/
        //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
        appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
        //alert(JSON.stringify(appUserDetails));
        if (appUserDetails.isSpouseOnLoan == true) {
            saveAndUpdateLoanAppForm(appUserDetails, paintMySpouseIncome());
        } else {
            saveAndUpdateLoanAppForm(appUserDetails, paintCustomerApplicationPageStep4a());
        }
        //  }else{
        //  showToastMessage("Please give answer of the questions");
        //}
    });
    $('#app-right-panel').append(saveAndContinueButton);
}
 

function paintRefinanceEmployed(divId) {

	//appUserDetails.employed ="true";
	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
		var quesTxt = "About how much do you make a year";
		var quesCont = getMultiTextQuestion(quesTxt);
		$('#ce-option_' + divId).prepend(quesCont);	
	}
	$('#ce-option_' + divId).toggle();


}

function getMultiTextQuestion(quesText) {
	
	var wrapper = $('<div>').attr({
		"class" : "ce-option-ques-wrapper"
	});
	
	
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
		"value" :customerEmploymentIncome.employedIncomePreTax
	});

	quesTextCont1.append(inputBox1);

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");

	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "workPlace",
		"value":customerEmploymentIncome.employedAt
	});

	quesTextCont2.append(inputBox2);

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Working ?");

	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startWorking",
		"value" : customerEmploymentIncome.employedSince
	});

	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(quesTextCont3);

	putCurrencyFormat("beforeTax");
	
	 container.append(quesTextCont).append(optionContainer);
     return wrapper.append(container);
}

$('body').on('focus',"input[name='startWorking'], input[name='startLivingTime']", function(){
    $(this).datepicker({
		format: "M yyyy",
	    minViewMode: "months",
	    endDate: "today",
	    autoclose : true
    }).on('changeDate',function(e){
    	e.stopImmediatePropagation();
    	var year = $(this).data('datepicker').getFormattedDate('yyyy');
    	var month = $(this).data('datepicker').getFormattedDate('mm');
    	var currentYear = new Date().getFullYear();
    	var currentMonth = new Date().getMonth();
    	
    	if( (currentYear - year < 2) || (currentYear - year == 2 && month > (currentMonth+1)) ){
    		$('#ce-option_0').find('.add-account-btn').trigger('click');
    		var text = "Previous Employement Details";
    		$('#ce-option_0').children('.ce-option-ques-wrapper').last().find('.ce-ques-wrapper').find('.ce-option-text').html(text);
    	}
    });
});



function paintRefinanceSelfEmployed(divId) {

	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
		var quesTxt = "How much do you make a year?";
		var wrapper = $('<div>').attr({
			"class" : "ce-option-ques-wrapper"
		});
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
			"value" : appUserDetails.selfEmployedIncome
		});
		optionContainer.append(inputBox);
		container.append(quesTextCont).append(optionContainer);
		wrapper.append(container);
		$('#ce-option_' + divId).prepend(wrapper);
	}
	$('#ce-option_' + divId).toggle();
	
	putCurrencyFormat("selfEmployed");
}

function paintRefinanceDisability(divId) {
	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
		var quesTxt = "About how much do you get monthly?";
		var wrapper = $('<div>').attr({
			"class" : "ce-option-ques-wrapper"
		});
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
			"value" : appUserDetails.ssDisabilityIncome
		});

		optionContainer.append(inputBox);
		container.append(quesTextCont).append(optionContainer);
		wrapper.append(container);
		$('#ce-option_' + divId).prepend(wrapper);
	}
	$('#ce-option_' + divId).toggle();
	putCurrencyFormat("disability");
}

function paintRefinancePension(divId) {
	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
		var quesTxt = "About how much do you get monthly?";
	
		var wrapper = $('<div>').attr({
			"class" : "ce-option-ques-wrapper"
		});
		
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
		wrapper.append(container);
		$('#ce-option_' + divId).prepend(wrapper);
	}
	$('#ce-option_' + divId).toggle();
	putCurrencyFormat("pension");
}




//////////////////This is new section for Spouse Income Details /////////









function paintMySpouseIncome() {

	applyLoanStatus = 3;
	//appProgressBaar(3);
	var quesTxt = "Spouse Details :Select all that apply";
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

  var optionsWrapper = $('<div>').attr({
   "class" : "hide ce-sub-option-wrapper",
   "id" : "ce-option_"+i
  });
  
  var optionIncome = $('<div>').attr({
   "class" : "ce-option-ques-wrapper"
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

  optionContainer.append(option);
  
  
  var addAccountBtn = $('<div>').attr({
   "class" : "add-btn add-account-btn"
  }).html("Add Income").bind('click',function(){
   
   var mainContainerId = $(this).closest('.ce-sub-option-wrapper').attr("id");
   
   if($('#'+mainContainerId).children('.ce-option-ques-wrapper').length >= 3){
    showToastMessage("Maximum 3 income needed");
    return false;
   }
   
   var containerToAppend = $(this).parent().find('.ce-option-ques-wrapper').wrap('<p/>').parent().html();
   $(this).parent().find('.ce-option-ques-wrapper').unwrap();
   $(this).before(containerToAppend);
   
   $(this).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
   
   var removeAccBtn = $('<div>').attr({
    "class" : "add-btn remove-account-btn"
   }).html("Remove Income")
   .bind('click',{"mainContainerId":mainContainerId},function(event){
    $(this).closest('.ce-option-ques-wrapper').remove();
    var parentDiv = $('#'+event.data.mainContainerId);
    
    if(parentDiv.children('.ce-option-ques-wrapper').length==1){
     parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
    }
   });
   
   $(this).parent().children('.ce-option-ques-wrapper').append(removeAccBtn);
  });
  
 
  if(i==0){
  optionsWrapper.append(addAccountBtn);
  }
  optionContainer.append(optionsWrapper);
  
  
 }

 var saveBtn = $('<div>').attr({
  "class" : "ce-save-btn"
 }).html("Save & Continue").bind('click',function() {
          
          
         
  var  customerSpouseEmploymentIncome = [];
      
      $("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
       customerSpouseEmploymentIncomeTemp1 = {};
       
       spouseBeforeTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseBeforeTax"]').val();
       spouseWorkPlace = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseWorkPlace"]').val();
       spouseStartWorking = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseStartWorking"]').val();
      
       customerSpouseEmploymentIncomeTemp1.employedIncomePreTax = spouseBeforeTax;
       customerSpouseEmploymentIncomeTemp1.employedAt = spouseWorkPlace;
       customerSpouseEmploymentIncomeTemp1.employedSince = spouseStartWorking;
       var temp = {};
       temp.customerSpouseEmploymentIncome = customerSpouseEmploymentIncomeTemp1;
       
       customerSpouseEmploymentIncome.push(temp);
      });
      
      appUserDetails.customerSpouseEmploymentIncome=customerSpouseEmploymentIncome;
  
  
    
          spouseSelfEmployed = $('input[name="spouseSelfEmployed"]').val();
          
          spouseDisability = $('input[name="spouseDisability"]').val();
    
          spousePension = $('input[name="spousePension"]').val();

    
    
    
    
    appUserDetails.customerSpouseDetail.isSpouseEmployed =  true;
    
    
    appUserDetails.customerSpouseDetail.ispensionOrRetirement= true;
    appUserDetails.customerSpouseDetail.monthlyPension =spousePension;
    
    appUserDetails.customerSpouseDetail.isSelfEmployed = true;
    appUserDetails.customerSpouseDetail.selfEmployedIncome =spouseSelfEmployed;
    
    appUserDetails.customerSpouseDetail.isssIncomeOrDisability=true;
    appUserDetails.customerSpouseDetail.ssDisabilityIncome = spouseDisability;
    
    //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
    
    
    saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a());
    //paintCustomerApplicationPageStep4a();
   });

 return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}





function paintSpouseRefinanceEmployed(divId) {

	//appUserDetails.employed ="true";
 if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
  var quesTxt = "Spouse Income :About how much do you make a year";
  var quesCont = getMultiTextQuestionSpouse(quesTxt);
  $('#ce-option_' + divId).prepend(quesCont); 
 }
 $('#ce-option_' + divId).toggle();

}

function getMultiTextQuestionSpouse(quesText) {

var wrapper = $('<div>').attr({
  "class" : "ce-option-ques-wrapper"
 });
 
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

	
	
	putCurrencyFormat("spouseBeforeTax");
	
	container.append(quesTextCont).append(optionContainer);
 
    return wrapper.append(container);
}




function paintSpouseRefinanceSelfEmployed(divId) {

 var quesTxt = "How much do you make a year?";

 var wrapper = $('<div>').attr({
  "class" : "ce-option-ques-wrapper"
 });
 
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
 wrapper.append(container);
 if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
  
  $('#ce-option_' + divId).prepend(wrapper); 
 }
 $('#ce-option_' + divId).toggle();
 
 putCurrencyFormat("spouseSelfEmployed");
}



function paintSpouseRefinanceDisability(divId) {

 var quesTxt = "About how much do you get monthly?";

 var wrapper = $('<div>').attr({
  "class" : "ce-option-ques-wrapper"
 });
 
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
 wrapper.append(container);
 if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
  
  $('#ce-option_' + divId).prepend(wrapper); 
 }
 $('#ce-option_' + divId).toggle();
 
 putCurrencyFormat("spouseDisability");
}




function paintSpouseRefinancePension(divId) {

 var quesTxt = "About how much do you get monthly?";

 var wrapper = $('<div>').attr({
  "class" : "ce-option-ques-wrapper"
 });
 
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
 wrapper.append(container);
 if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
  
  $('#ce-option_' + divId).prepend(wrapper); 
 }
 $('#ce-option_' + divId).toggle();
 
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

		var optionsWrapper = $('<div>').attr({
			"class" : "hide ce-sub-option-wrapper",
			"id" : "ce-option_"+i
		});

		var optionIncome = $('<div>').attr({
			"class" : "ce-option-ques-wrapper"
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

	optionContainer.append(option);
		
		var addAccountBtn = $('<div>').attr({
			"class" : "add-btn add-account-btn"
		}).html("Add Income").bind('click',function(){
			
			var mainContainerId = $(this).closest('.ce-sub-option-wrapper').attr("id");
			
			if($('#'+mainContainerId).children('.ce-option-ques-wrapper').length >= 3){
				showToastMessage("Maximum 3 income needed");
			     return false;
			}
			
			var containerToAppend = $(this).parent().find('.ce-option-ques-wrapper').wrap('<p/>').parent().html();
			$(this).parent().find('.ce-option-ques-wrapper').unwrap();
			$(this).before(containerToAppend);
			
			$(this).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
			
			var removeAccBtn = $('<div>').attr({
				"class" : "add-btn remove-account-btn"
			}).html("Remove Income")
			.bind('click',{"mainContainerId":mainContainerId},function(event){
				$(this).closest('.ce-option-ques-wrapper').remove();
				var parentDiv = $('#'+event.data.mainContainerId);
				
				if(parentDiv.children('.ce-option-ques-wrapper').length==1){
					parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
				}
			});
			
			$(this).parent().children('.ce-option-ques-wrapper').append(removeAccBtn);
		});
	
		if(i==0){
		optionsWrapper.append(addAccountBtn);
		}
		optionContainer.append(optionsWrapper);
	}

if(purchase ==true)
{
return container.append(quesTextCont).append(optionContainer);
}

	return container.append(quesTextCont).append(optionContainer);
}



var quesDeclarationContxts =[];
function paintCustomerApplicationPageStep4a() {
   
	applyLoanStatus = 4;
	quesDeclarationContxts = [];
	appProgressBaar(5);
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


 
 
function goverementOptionalQues() {
 
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
	
	
	    return questionsContainer;
}
 

function paintCustomerApplicationPageStep5() {
	
	applyLoanStatus = 5;
	appProgressBaar(6);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "My Credit";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var dob = $.datepicker.formatDate('mm/dd/yy', new Date(appUserDetails.user.customerDetail.dateOfBirth));
    if(dob =="" || dob == undefined || dob =='NaN/NaN/NaN')
    	dob="";
    
    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "birthday",
        value: dob
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
    		
    		
    		if(appUserDetails.isSpouseOnLoan == true)
				{
				saveAndUpdateLoanAppForm(appUserDetails,paintCustomerSpouseApplicationPageStep5());
				}else{
				 saveAndUpdateLoanAppForm(appUserDetails,applicationFormSumbit(appUserDetails));
				}
    		
    		
    		
    		
    		
    	}else{
    		showToastMessage("Please give the answers of the questions");
    	}
    	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}





function paintCustomerSpouseApplicationPageStep5() {
	
	applyLoanStatus = 5;
	appProgressBaar(6);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "My Spouse Credit";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var dob = $.datepicker.formatDate('mm/dd/yy', new Date(appUserDetails.customerSpouseDetail.spouseDateOfBirth));
    if(dob =="" || dob == undefined || dob =='NaN/NaN/NaN')
    	dob="";
    
    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "birthday",
        value: dob
    },
    {
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: appUserDetails.customerSpouseDetail.ssn
    },
    {
        type: "desc",
        text: "Phone Number",
        name: "phoneNumber",
        value: appUserDetails.customerSpouseDetail.secPhoneNumber
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
    		
    		customerDetailTemp =  appUserDetails.customerSpouseDetail;
    		customerDetailTemp.spouseDateOfBirth= new Date(dateOfBirth).getTime();
    		customerDetailTemp.spouseSsn = ssn;
    		customerDetailTemp.spouseSecPhoneNumber = secPhoneNumber;
    		//applicationFormSumbit();
    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    		
    		appUserDetails.customerSpouseDetail = customerDetailTemp;
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
	
	createLoan();
	saveUserAndLockRate(appUserDetails) ;
	//changeSecondaryLeftPanel(3);
}





var tempdata = [{
    "loanDuration": "15 YR FIXED CONFORMING",
    "rateVO": [{
        "teaserRate": "3.000",
        "closingCost": "0",
        "APR": "1",
    }, {
        "teaserRate": "2.875",
        "closingCost": "$1,782.62",
        "APR": "2",
    }, {
        "teaserRate": "2.750",
        "closingCost": "$3,512.43",
        "APR": "3",
    }]
}, {
    "loanDuration": "20 YR FIXED CONFORMING",
    "rateVO": [{
        "teaserRate": "3.625",
        "closingCost": "0",
        "APR": "1",
    }, {
        "teaserRate": "3.500",
        "closingCost": "$1,155.53",
        "APR": "2",
    }, {
        "teaserRate": "3.375",
        "closingCost": "$3,658.15",
        "APR": "3",
    }, {
        "teaserRate": "3.250",
        "closingCost": "$6,166.37",
        "APR": "4",
    }]
}, {
    "loanDuration": "30 YR FIXED CONFORMING",
    "rateVO": [{
        "teaserRate": "3.875",
        "closingCost": "0",
        "APR": "1",
    }, {
        "teaserRate": "3.750",
        "closingCost": "$493.10",
        "APR": "2",
    }, {
        "teaserRate": "3.625",
        "closingCost": "$2,872.52",
        "APR": "3",
    }, {
        "teaserRate": "3.500",
        "closingCost": "$5,660.73",
        "APR": "4",
    }]
}, {
    "loanDuration": "5/1 1 YR LIBOR CONFORMING  2/2/5 30 YR ARM",
    "rateVO": [{
        "teaserRate": "3.125",
        "closingCost": "0",
        "APR": "1",
    }, {
        "teaserRate": "3.000",
        "closingCost": "$425.20",
        "APR": "2",
    }, {
        "teaserRate": "2.875",
        "closingCost": "$1,443.82",
        "APR": "3",
    }, {
        "teaserRate": "2.750",
        "closingCost": "$2,456.83",
        "APR": "4",
    }, {
        "teaserRate": "2.625",
        "closingCost": "$3,472.65",
        "APR": "5",
    }, {
        "teaserRate": "2.500",
        "closingCost": "$4,796.47",
        "APR": "6",
    }]
}, {
    "loanDuration": "7/1 1 YR LIBOR CONFORMING  5/2/5 30 YR ARM",
    "rateVO": [{
        "teaserRate": "3.250",
        "closingCost": "0",
        "APR": "1",
    }, {
        "teaserRate": "3.125",
        "closingCost": "$347.38",
        "APR": "2",
    }, {
        "teaserRate": "3.000",
        "closingCost": "$1,643.20",
        "APR": "3",
    }, {
        "teaserRate": "2.875",
        "closingCost": "$2,950.22",
        "APR": "4",
    }, {
        "teaserRate": "2.750",
        "closingCost": "$4,262.83",
        "APR": "5",
    }, {
        "teaserRate": "2.625",
        "closingCost": "$5,569.85",
        "APR": "6",
    }]
}];
 
function saveUserAndLockRate(appUserDetails) {
    //alert(JSON.stringify(registration));
    
    var teaserrate = {};
    
    teaserrate.loanType="REF";
    teaserrate.refinanceOption="REFLMP";
    teaserrate.currentMortgageBalance="$280,000";
    teaserrate.currentMortgagePayment="$3,000";
    teaserrate.isIncludeTaxes="Yes";
    teaserrate.propertyTaxesPaid="$350";
    teaserrate.annualHomeownersInsurance="$500";
    teaserrate.homeWorthToday="$350,000";
    teaserrate.zipCode="94087";

   
    
    $.ajax({
        url: "rest/calculator/findteaseratevalue",
        type: "POST",
        data: {
            "teaseRate": JSON.stringify(teaserrate)
        },
        datatype: "application/json",
        success: function(data) {
            $('#overlay-loader').hide();
            //TO:DO pass the data (json)which is coming from the controller
            //paintLockRate(data,appUserDetails);
            paintLockRate(JSON.parse(data), appUserDetails);
        },
        error: function() {
            alert("error");
            $('#overlay-loader').hide(); 
        }
 
    });
 
 
 
}
 
 
 
 

function appProgressBaar(num){
	
	var count = 6;
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
	  "value" : "",
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
	  "value" : "",
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
    
	appProgressBaar(1);
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
		
	
		loanType.loanTypeCd = "REF";
		appUserDetails.loanType= loanType;
		
		paintRefinanceMainContainer();
	});

	var option2 = $('<div>').attr({
		"class" : "ce-option"
	}).html("Buy a home").on('click', function() {
		
		loanType.loanTypeCd = "PUR";
		appUserDetails.loanType= loanType;
		
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
			
			
			
			
			refinancedetails.currentMortgageBalance = $('input[name="currentMortgageBalance"]').val();
			 appUserDetails.refinancedetails = refinancedetails;
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
                         "value": appUserDetails.refinancedetails.currentMortgagePayment
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
		
			refinancedetails.currentMortgagePayment = $('input[name="currentMortgagePayment"]').val();		  
			refinancedetails.includeTaxes = quesContxts[1].value;
			propertyTypeMaster.propertyTaxesPaid = $('input[name="annualPropertyTaxes"]').val();
			propertyTypeMaster.propertyInsuranceCost = $('input[name="annualHomeownersInsurance"]').val();
		  appUserDetails.refinancedetails=refinancedetails;
		  appUserDetails.propertyTypeMaster=propertyTypeMaster;
		    saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep1a());
		    //paintCustomerApplicationPageStep1a();
		
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
          
		if(contxt.name != 'zipCode' && contxt.name != 'yearLeftOnMortgage' && contxt.name != 'locationZipCode' && contxt.name != 'buyhomeZipPri' ){
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
			
			//customerEnagagement[key] = event.data.option.value;			
			//user.customerEnagagement = customerEnagagement;					
			//appUserDetails.user = user;	
			refinancedetails.refinanceOption = event.data.option.value;
						appUserDetails.refinancedetails = refinancedetails;	
			
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
    	 delete appUserDetails.spouseGovernmentQuestions;
    	 
    	 spouseGovernmentQuestions = {};
    	 if( isOutstandingJudgments =="Yes"){ 
    		 spouseGovernmentQuestions.isOutstandingJudgments = true;
 		 }else{
 			spouseGovernmentQuestions.isOutstandingJudgments = false;
 		 }
    	 
    	 if( isBankrupt =="Yes"){ 
    		 spouseGovernmentQuestions.isBankrupt = true;
 		 }else{
 			spouseGovernmentQuestions.isBankrupt = false;
 		 }
    	 
    	 if( isPropertyForeclosed =="Yes"){ 
    		 spouseGovernmentQuestions.isPropertyForeclosed = true;
 		 }else{
 			spouseGovernmentQuestions.isPropertyForeclosed = false;
 		 }
    	 
    	 if( isLawsuit =="Yes"){ 
    		 spouseGovernmentQuestions.isLawsuit = true;
 		 }else{
 			spouseGovernmentQuestions.isLawsuit = false;
 		 }

    	 if( isObligatedLoan =="Yes"){ 
    		 spouseGovernmentQuestions.isObligatedLoan = true;
 		 }else{
 			spouseGovernmentQuestions.isObligatedLoan = false;
 		 }
    	 
    	 if( isFederalDebt =="Yes"){ 
    		 spouseGovernmentQuestions.isFederalDebt = true;
 		 }else{
 			spouseGovernmentQuestions.isFederalDebt = false;
 		 }

    	 if( isObligatedToPayAlimony =="Yes"){ 
    		 spouseGovernmentQuestions.isObligatedToPayAlimony = true;
 		 }else{
 			spouseGovernmentQuestions.isObligatedToPayAlimony = false;
 		 }
    	 
    	 if( isEndorser =="Yes"){ 
    		 spouseGovernmentQuestions.isEndorser = true;
 		 }else{
 			spouseGovernmentQuestions.isEndorser = false;
 		 }

    	 if( isUSCitizen =="Yes"){ 
    		 spouseGovernmentQuestions.isUSCitizen = true;
 		 }else{
 			spouseGovernmentQuestions.isUSCitizen = false;
 		 }

    	 if( isOccupyPrimaryResidence =="Yes"){ 
    		 spouseGovernmentQuestions.isOccupyPrimaryResidence = true;
 		 }else{
 			spouseGovernmentQuestions.isOccupyPrimaryResidence = false;
 		 }
    	 
    	 if( isOwnershipInterestInProperty =="Yes"){ 
    		 spouseGovernmentQuestions.isOwnershipInterestInProperty = true;
 		 }else{
 			spouseGovernmentQuestions.isOwnershipInterestInProperty = false;
 		 }
    	 
    	 appUserDetails.spouseGovernmentQuestions =spouseGovernmentQuestions;
    	 appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
    	 //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    	// alert(JSON.stringify(appUserDetails));
    	 saveAndUpdateLoanAppForm(appUserDetails,paintSpouseCustomerApplicationPageStep4b());
    	
    	//paintCustomerApplicationPageStep4b();
    });

    $('#app-right-panel').append(saveAndContinueButton);
}





function paintSpouseGovernmentMonitoringQuestions(quesText, options, name) {
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
	var quesCont = paintSpouseGovernmentMonitoringQuestions(quesHeaderTxt, options, name);

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
	    	
	    	
	    	spouseGovernmentQuestions.ethnicity = ethnicity;
	    	spouseGovernmentQuestions.race = race;
	    	spouseGovernmentQuestions.sex =sex;
	    	
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


function paintLockRate(lqbData, appUserDetails) {
    //console.log("lqbData..."+JSON.stringify(lqbData));
    //console.log("appUserDetails..."+JSON.stringify(appUserDetails));
    paintFixYourRatePage1(lqbData, appUserDetails);
}
 



function getAddRemoveButtonRow(fieldName){
	
	var container = $('<div>').attr({
		"class" : "add-remove-row clearfix"
	});
	
	var addBtn = $('<div>').attr({
		"class" : "add-btn float-left"
	}).html("Add")
	.bind('click',{"fieldName":fieldName},function(e){
		var inputField = $('input[name="'+e.data.fieldName+'"]');
		
		var inputElement = $('<input>').attr({
			"name" : e.data.fieldName,
			"class" : "ce-input ce-input-add"
		});
		
		var numberOfInputs = inputField.parent().children('input').size();
		
		if(numberOfInputs<3){
			inputField.parent().append(inputElement);
			if(numberOfInputs == 2){
				$(this).hide();
			}
		}
	});
	
	/*var removeBtn = $('<div>').attr({
		"class" : "remove-btn float-left"
	}).html("-")
	.bind('click',{"fieldName":fieldName},function(e){
		var inputField = $('input[name="'+e.data.fieldName+'"]');
		if(inputField.parent().children('input').size()>1){
			inputField.parent().find('input:last-child').remove();
		}
	});*/
	return container.append(addBtn);
}

function getAddMoreEmployementDetails() {
	var container = $('<div>').attr({
		"class" : "add-remove-row clearfix"
	});
	var addBtn = $('<div>').attr({
		"class" : "add-btn add-account-btn float-left"
	}).html("Add Account");
	return container.append(addBtn);
}
