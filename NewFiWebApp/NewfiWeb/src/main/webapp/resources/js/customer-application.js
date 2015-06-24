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

var buttonText = "Save & continue";
var next = "Next";

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

$(document).on('click',function(){
	
	if($('#state-dropdown-wrapper').css("display") == "block"){
		$('#state-dropdown-wrapper').hide();
	}
	if($('#carrier-dropdown-wrapper').css("display")=="block"){
		toggleCarrierDropDown();
	}
	if($('#state-dropdown-wrapper-property').css("display") == "block"){
		$('#state-dropdown-wrapper-property').hide();
	}
	
	
	
});

$(document).on('click','.app-save-btn, .ce-save-btn',function(){
	scrollToTop();
});

if (sessionStorage.loanAppFormData) {
	appUserDetails = JSON.parse(sessionStorage.loanAppFormData);
}

//loan.id = 2;
//loanType.id=1;


var formCompletionStatus = 1;
var applicationItemsList = [ 
                             {
							    "text":"Loan Purpose",
	                            "onselect" : paintSelectLoanTypeQuestion
	                        },
	                        /*{
							    "text":"My Money",
	                            "onselect" : ""
	                        },*/
	                        {
							    "text":"Home Information",
	                            "onselect" : paintRefinanceStep2
	                        },
	                        {
								"text":"Borrower(s) on the Loan?",
		                        "onselect" : paintCustomerApplicationPageStep2
		                    },
	                        {
								"text":"Income",
		                        "onselect" : paintMyIncome
		                    },
			                {
							    "text":"Government Questions",
				                 "onselect" : paintCustomerApplicationPageStep4a
				             },
				             {
				            	 "text":"Credit",
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
	var contxt=contxtHolder.createBreadCrumbItem(itemCont,itemTxt,stepNo);
    itemCont.bind("click",{"contxt":contxt},function(event){
        var contxt=event.data.contxt;
        contxt.clickHandler();
    })
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
	
	//alert('newfi.appUserDetails'+JSON.stringify(newfi.appUserDetails));
	appUserDetails = JSON.parse(newfi.appUserDetails);
	//appUserDetails.id=newfi.appUserDetails.id;

	user.id = newfi.user.id;
	customerDetail.id = newfi.user.customerDetail.id;
	
	if(newfi.user.customerDetail != null){
		
		customerDetail = appUserDetails.user.customerDetail;
	
	}
	
	
	
	 if(appUserDetails.propertyTypeMaster != null){
		 propertyTypeMaster.id = appUserDetails.propertyTypeMaster.id;
		 propertyTypeMaster.propertyTypeCd = appUserDetails.propertyTypeMaster.propertyTypeCd;
		 propertyTypeMaster.residenceTypeCd = appUserDetails.propertyTypeMaster.residenceTypeCd;
		 propertyTypeMaster.propertyTaxesPaid = appUserDetails.propertyTypeMaster.propertyTaxesPaid;
		 propertyTypeMaster.propertyInsuranceProvider = appUserDetails.propertyTypeMaster.propertyInsuranceProvider;
		 propertyTypeMaster.propertyInsuranceCost = appUserDetails.propertyTypeMaster.propertyInsuranceCost;
		 propertyTypeMaster.propertyPurchaseYear = appUserDetails.propertyTypeMaster.propertyPurchaseYear;
		 propertyTypeMaster.homeWorthToday=appUserDetails.propertyTypeMaster.homeWorthToday;
		 propertyTypeMaster.homeZipCode = appUserDetails.propertyTypeMaster.homeZipCode;

	 }
	 
	 
	 if(appUserDetails.refinancedetails !=null){
     refinancedetails.id =appUserDetails.refinancedetails.id; 	 
	 refinancedetails.refinanceOption = appUserDetails.refinancedetails.refinanceOption;
	 refinancedetails.currentMortgageBalance = appUserDetails.refinancedetails.currentMortgageBalance;
	 //refinancedetails.currentMortgageBalance=9090;
	 refinancedetails.currentMortgagePayment = appUserDetails.refinancedetails.currentMortgagePayment;
	 refinancedetails.includeTaxes = appUserDetails.refinancedetails.includeTaxes;
	 refinancedetails.secondMortageBalance = appUserDetails.refinancedetails.secondMortageBalance;
	 
	 //refinancedetails.mortgageyearsleft = appUserDetails.refinancedetails.mortgageyearsleft;
	 refinancedetails.mortgageyearsleft=appUserDetails.refinancedetails.mortgageyearsleft;
	
	 refinancedetails.cashTakeOut = appUserDetails.refinancedetails.cashTakeOut;
	 }
	 
	 
	 if(newfi.customerSpouseDetail != null){
		 customerSpouseDetail.id = newfi.customerSpouseDetail.id;
	 }
	 /*if(newfi.customerEmploymentIncome !=null){
	 customerEmploymentIncome.id = newfi.customerEmploymentIncome.id;
	 }*/
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
	
	//appUserDetails.id = newfi.loanAppFormid; 

	
	var topHeader = getCompletYourApplicationHeader();

    var container = $('<div>').attr({
        "class": "clearfix"
    });

    var stepContOnMobileScreen = $('<div>').attr({
    	"class" : "hide cust-app-step-hdr"
    }).html("Step ").append("<span id='step-no'>1</span>").append(" of " + applicationItemsList.length);
    
    var applicationLeftPanel = $('<div>').attr({
        "class": "cust-app-lp float-left"
    });

    var leftPanel = applicationStatusLeftPanel();
    
    applicationLeftPanel.append(leftPanel);
    
    var applicationRightPanel = $('<div>').attr({
        "id": "app-right-panel",
        "class": "cust-app-rp float-left"
    });

    container.append(applicationLeftPanel).append(stepContOnMobileScreen).append(applicationRightPanel);
    
    

    $('#center-panel-cont').append(topHeader).append(container);

   // paintCustomerApplicationPageStep1a();
    
    // showLoanAppFormContainer(formCompletionStatus);
    contxtHolder.switchBreadCrumb();
    
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

function getApplicationQuestion(question,val) {
    var quesCont;
    if (question.type == "mcq") {
        quesCont = getApplicationMultipleChoiceQues(question,val);
    } else if (question.type == "desc") {
        if(val)
            question.value=val;
        quesCont = getApplicationTextQues(question);
		
    } else if (question.type == "select") {
        quesCont = getApplicationSelectQues(question,val);
    } else if (question.type == "yesno") {
        quesCont = getApplicationYesNoQues(question,val);
    }
    /*else if (question.type == "yearMonth") {
        quesCont = getMonthYearTextQuestion(question);
    }*/
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
    //NEXNF-524
    var optionsContainer="";
    if(contxt.name=="propertyType"||contxt.name=="residenceType"){
    	  optionsContainer = $('<div>').attr({
    	        "class": "app-options-cont app-cont-new",
    	        "name": contxt.name
    	    });
    }else{
    	optionsContainer = $('<div>').attr({
	        "class": "app-options-cont",
	        "name": contxt.name
	    });
    }
   /* var optionsContainer = $('<div>').attr({
        "class": "app-options-cont",
        "name": contxt.name
    });*/

    var selectedOption = $('<div>').attr({
        "class": "app-option-selected"
    }).html("Select One").on('click', function(e) {
    	e.stopPropagation();
    	$('.app-dropdown-cont').hide();
        $(this).parent().find('.app-dropdown-cont').toggle();
    });

    var dropDownContainer = $('<div>').attr({
        "class": "app-dropdown-cont hide"
    });


    for (var i = 0; i < contxt.options.length; i++) {
        var option = contxt.options[i];
       // alert('option value is   '+ option.value);
        var optionCont = $('<div>').attr({
                "class": "app-option-sel"
            }).data({
                "value": option.value
            }).html(option.text);
        
        optionCont.bind('click',{"contxt":contxt,"value":option.value,"option":option},
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
                if(option.value==contxt.value)
                    selVal=optionCont;
        dropDownContainer.append(optionCont);
    }
    var val=contxt.value;

    if(val)
    	setDropDownData(selectedOption, contxt.options,val);
    	
    optionsContainer.append(selectedOption).append(dropDownContainer);

    return container.append(quesTextCont).append(optionsContainer);
}

function getApplicationSelectQues(question,val) {

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
    }).html("Select One").on('click', function(e) {
    	e.stopPropagation();
    	$('.app-dropdown-cont').hide();
        $(this).parent().find('.app-dropdown-cont').toggle();
    });

    var dropDownContainer = $('<div>').attr({
        "class": "app-dropdown-cont hide"
    });
    var selVal;
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
            if(option.value==val)
                selVal=optionCont;
        dropDownContainer.append(optionCont);
    }
    var val=getMappedValue(question);
    if(val)
    	setDropDownData(selectedOption, question.options,getMappedValue(question));
    
    optionsContainer.append(selectedOption).append(dropDownContainer);

    if(selVal){
        $(selVal).closest('.app-options-cont').find(
                        '.app-option-selected').html($(selVal).html());
        $(selVal).closest('.app-options-cont').find(
        '.app-option-selected').data("value",$(selVal).data("value"));
    }
    return container.append(quesTextCont).append(optionsContainer);
}


function getMappedValue(question){
	if(question.text==="What type of property is this?"){
		return appUserDetails.propertyTypeMaster.propertyTypeCd;
	}else if(question.text==="How do you use this home?"){
		return appUserDetails.propertyTypeMaster.residenceTypeCd;
	}else if (question.name==="spouseEthnicity"){
		return appUserDetails.spouseGovernmentQuestions.ethnicity;
	}else if (question.name==="spouseRace"){
		return appUserDetails.spouseGovernmentQuestions.race;
	}else if (question.name==="spouseSex"){
		return appUserDetails.spouseGovernmentQuestions.sex;
	}else if (question.text==="Ethnicity"){
        return appUserDetails.governmentquestion.ethnicity;
    }else if (question.text==="Race"){
        return appUserDetails.governmentquestion.race;
    }else if (question.text==="Sex"){
        return appUserDetails.governmentquestion.sex;
    }
}


function setDropDownData(element, dataSet, value){
	
	for(var i=0 ; i <dataSet.length ; i ++ ){
		
		if(dataSet[i].value === value ){
			
			$(element).html(dataSet[i].text);
			$(element).data("value",value);
		}
	}
	
}

function getApplicationTextQues(question) {
	if(question.name=="phoneNumber"){
		
		question.value=formatPhoneNumberToUsFormat(question.value);
		
	}
    var container = $('<div>').attr({
        "class": "app-ques-wrapper"
    });
    var errFeild=appendErrorMessage();
    var quesTextCont = $('<div>').attr({
        "class": "app-ques-text"
    }).html(question.text);

    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont"
    });

    var optionCont;
  if(question.name!='streetAddress'){
	  optionCont = $('<input>').attr({
	        "class": "app-input",
	        "name": question.name,
	        "value":question.value
	    }).on("focus", function(e){
	          	
	        if (checkForUnmaskedFields(question)) {
				$('input[name='+question.name+']').maskMoney({
					thousands:',',
					decimal:'.',
					allowZero:true,
					prefix: '$',
				    precision:0,
				    allowNegative:false
				});
			}


			
			   if (question.name == 'ssn') {
				  // $('input[name="ssn"]').mask("999-99-9999");
				 // $('input[name="ssn"]').attr('type', 'password');
	        }
			
		}).keypress(function(key) {
			if($('input[name='+question.name+']').attr('name')=="phoneNumber"){
				
				  if(key.charCode < 48 || key.charCode > 57) return false;
			}
	      
	    });

	  if(question.name =="birthday"){
		  optionCont.removeClass("app-input").addClass("prof-form-input date-picker").attr("placeholder","MM/DD/YYYY");
		 // $('input[name=birthday]').mask("__/__/____");
	  }
	
  }else{
	   optionCont = $('<input>').attr({
	        "class": "app-input app-append-width",
	        "name": question.name,
	        "value":question.value
	    }).on("focus", function(e){
	          	
	        if (checkForUnmaskedFields(question)) {
				$('input[name='+question.name+']').maskMoney({
					thousands:',',
					decimal:'.',
					allowZero:true,
					prefix: '$',
				    precision:0,
				    allowNegative:false
				});
			}
			
			   if (question.name == 'ssn') {
				  // $('input[name="ssn"]').mask("999-99-9999");
				 // $('input[name="ssn"]').attr('type', 'password');
	        }
			
		}).keypress(function(key) {
			if($('input[name='+question.name+']').attr('name')=="phoneNumber"){
				
				  if(key.charCode < 48 || key.charCode > 57) return false;
			}
	      
	    });
	   
	  
  }
	

    if (question.value != undefined) {
        optionCont.val(question.value);
    }

    optionsContainer.append(optionCont).append(errFeild);

    return container.append(quesTextCont).append(optionsContainer);
}

function checkForUnmaskedFields(question){
    switch(question.name){
        case 'zipCode':
            return false;
        case 'mortgageyearsleft':
            return false;
        case 'locationZipCode':
            return false;
        case 'buyhomeZipPri':
            return false;
        case 'city':
            return false;
        case 'state':
            return false;
        case 'startLivingTime':
            return false;
        case 'spouseName':
            return false;
        case 'phoneNumber':
            return false;
        case 'ssn':
            return false;
        case 'birthday':
            return false;
        case 'streetAddress':
            return false;
        case 'purchaseTime':
            return false;
        case "propCity":
            return false;
        case "propState":
            return false;
        case "propZipCode":
            return false;
    }
    return true;
}

function getApplicationMultipleChoiceQues(question,value) {
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
        var selctedClas="";
        if(value&&value==option.value){
            selctedClas="app-option-checked";
        }
        var optionCont = $('<div>').attr({
            "class": "app-option "+selctedClas
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
function paintHomeInfoPage(){
    if( appUserDetails.loanType.loanTypeCd =="PUR" ){
        paintCustomerApplicationPurchasePageStep1a();
    }else{
        paintCustomerApplicationPageStep1a();
    }
}

function paintCustomerApplicationPageStep1a() {
    
	
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Tell us about where you currently live";
   

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var row=paintCheckBox();
    
    var selectedProperty = appUserDetails.user.customerDetail.selectedProperty;
    
    var questions = [{
        type: "desc",
        text: "Street address",
        name: "streetAddress",
        value: appUserDetails.user.customerDetail.addressStreet
    }, {
        type: "desc",
        text: "State",
        name: "propState",
        value: appUserDetails.user.customerDetail.addressState
    }, {
        type: "desc",
        text: "City",
        name: "propCity",
        value: appUserDetails.user.customerDetail.addressCity
    }, {
        type: "desc",
        text: "Zip code",
        name: "propZipCode",
        value: appUserDetails.user.customerDetail.addressZipCode
    }];



    var questionsContainer = getQuestionsContainer(questions);

    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function(event) {
    	
    	if(this.innerText != next){
	    	var address= $('input[name="streetAddress"]').val();
	    	var inputState = $('input[name="propState"]').val();
	    	var city = $('input[name="propCity"]').val();
	    	var zipCode = $('input[name="propZipCode"]').val();	    	
	    	var addressStreet =   $('input[name="streetAddress"]').val();	    	
	        var selectedProperty = $('.ce-option-checkbox').hasClass('app-option-checked');
	    	var cityStatus=validateInput($('input[name="propCity"]'),$('input[name="propCity"]').val(),message);
	    	var zipcodeStatus=validateInput($('input[name="propZipCode"]'),$('input[name="propZipCode"]').val(),zipCodeMessage);
	    	var isSuccess=validateInput($('input[name="streetAddress"]'),$('input[name="streetAddress"]').val(),message);
			var stateValidation=validateInput($('input[name="propState"]'),$('input[name="propState"]').val(),yesyNoErrorMessage);
	   
	    	if(!stateValidation){
	    		showErrorToastMessage(yesyNoErrorMessage);
	    		return false;
	    	}
	    	if(!cityStatus){
	    		return false;
	    	}
	    	if(!zipcodeStatus){
	    		return false;
	    	} else{
	    		if($('input[name="propZipCode"]').val().length >5 ||$('input[name="propZipCode"]').val().length < 5){

	    			$('input[name="propZipCode"]').next('.err-msg').html(zipCodeMessage).show();
	    			$('input[name="propZipCode"]').addClass('ce-err-input').show();
           		    return false;
           	 }
	    	} 
	    	if(!isSuccess){
				return false;
			}
	       if($('.ce-option-checkbox').hasClass('app-option-checked')){
	    		
	    	}else{
	    		var isSuccess=validateInput($('input[name="streetAddress"]'),$('input[name="streetAddress"]').val(),message);
	    		if(!isSuccess){
	    			return false;
	    		}
	    	}
            ajaxRequest("rest/states/zipCode", "GET", "json", {"zipCode":zipCode}, function(response) {
                if (response.error) {
                    showToastMessage(response.error.message)
                } else {
                    if(response.resultObject==true){
                        appUserDetails.user.customerDetail.addressCity = city;
                        appUserDetails.user.customerDetail.addressState = inputState;
                        appUserDetails.user.customerDetail.addressZipCode = zipCode;
                        appUserDetails.user.customerDetail.addressStreet = addressStreet;
                        appUserDetails.user.customerDetail.selectedProperty = selectedProperty;
                        saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep1b);
                    }else{
                        $('input[name="propZipCode"]').next('.err-msg').html(invalidStateZipCode).show();
                        $('input[name="propZipCode"]').addClass('ce-err-input').show();
                        return false;
                    }
                }
            });
	
	           
    	}else{
    		// when click on next 
    		paintCustomerApplicationPageStep1b();
    	}
   	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
   
    
    addStateCityZipLookUp();
    addCityStateZipLookUpForProperty();
}

function paintCheckBox(){
    

	var wrapper=$('<div>').attr({
		"class":"app-ques-wrapper"
	});
	var optionContainer = $('<div>').attr({
		"class" : "app-options-cont"
	});

	var checkbox=$('<div>').attr({
		"class":"ce-option-checkbox"
	}).html("I have not yet selected a  property").bind('click',function(event){
		if($(this).hasClass('app-option-checked')){
    		$(this).removeClass('app-option-checked');
    		$('input[name=streetAddress]').val('');
    		$('input[name=streetAddress]').parent().parent().show();
    		$('input[name=addressStreet]').parent().parent().show();
    	    $('input[name="propStreetAddress"]').parent().parent().show();
        	$('input[name="propState"]').parent().parent().show();
        	$('input[name="propCity"]').parent().parent().show();
        	$('input[name="propZipCode"]').parent().parent().show();
        	
    		
    		
    	
    		
    	}else{
        	$(this).addClass('app-option-checked');
        	$('input[name=streetAddress]').parent().parent().hide();
        	$('input[name="propStreetAddress"]').parent().parent().hide();
         	$('input[name="propState"]').parent().parent().hide();
         	$('input[name="propCity"]').parent().parent().hide();
         	$('input[name="propZipCode"]').parent().parent().show();
        	
    	}
		
	});
	optionContainer.append(checkbox) ;
	return wrapper.append(optionContainer);

}
function addCityStateZipLookUpForProperty(){
synchronousAjaxRequest("rest/states/", "GET", "json", "", stateListCallBack);
    
    var stateDropDownWrapper = $('<div>').attr({
    	"id" : "state-dropdown-wrapper-property",
    	"class" : "state-dropdown-wrapper hide"
    });

    $('input[name="propState"]').after(stateDropDownWrapper);
	$('input[name="propState"]').attr("id","stateID").addClass('prof-form-input-statedropdown').bind('click',function(e){

		e.stopPropagation();
		if($('#state-dropdown-wrapper-property').css("display") == "none"){
			appendStateDropDownForProperty('state-dropdown-wrapper-property',filterAllowedStates(stateList));
			$('#state-dropdown-wrapper-property').slideToggle("slow",function(){
				$('#state-dropdown-wrapper-property').perfectScrollbar({
					suppressScrollX : true
				});
				$('#state-dropdown-wrapper-property').perfectScrollbar('update');		
			});
		}/*else{
			$('#state-dropdown-wrapper-property').slideToggle("slow",function(){
				$('#state-dropdown-wrapper-property').perfectScrollbar({
					suppressScrollX : true
				});
				$('#state-dropdown-wrapper-property').perfectScrollbar('update');		
			});
		}*/
	}).bind('keyup',function(e){
		var searchTerm = "";
		if(!$(this).val()){
			return false;
		}
		searchTerm = $(this).val().trim();
		var searchedList = searchInStateArray(searchTerm,filterAllowedStates(stateList));
		appendStateDropDownForProperty('state-dropdown-wrapper-property',searchedList);
	});
	
$('input[name="propCity"]').attr("id","cityID").bind('click keydown',function(){

		var searchData = [];
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			searchData[i] = currentZipcodeLookUp[i].cityName;
		}
		
		var uniqueSearchData = searchData.filter(function(itm,i,a){
		    return i==a.indexOf(itm);
		});
		
		initializeCityLookupForProperty(uniqueSearchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	}).width(200);

$('input[name="propZipCode"]').attr("id","zipcodeID").bind('click keydown',function(){

	var selectedCity = $('#cityID').val();
	var searchData = [];
	var count = 0;
	for(var i=0; i<currentZipcodeLookUp.length; i++){
		if(selectedCity == currentZipcodeLookUp[i].cityName){
			searchData[count++] = currentZipcodeLookUp[i].zipcode;				
		}
	}

	initializeZipcodeLookupForProperty(searchData);
}).bind('focus', function(){ 
	$(this).trigger('keydown');
	$(this).autocomplete("search"); 
});

}

function addStateCityZipLookUp(){
synchronousAjaxRequest("rest/states/", "GET", "json", "", stateListCallBack);
    
    var stateDropDownWrapper = $('<div>').attr({
    	"id" : "state-dropdown-wrapper",
    	"class" : "state-dropdown-wrapper hide"
    });
    
    $('input[name="state"]').after(stateDropDownWrapper);
    $('input[name="coBorrowerState"]').after(stateDropDownWrapper);
 
    $('input[name="state"]').attr("id","stateId").addClass('prof-form-input-statedropdown').bind(' click keypress focus',function(e){
		e.stopImmediatePropagation();
		if($('#state-dropdown-wrapper').css("display") == "none"){
			appendStateDropDown('state-dropdown-wrapper',stateList);
			toggleStateDropDown();
		}/*else{
			toggleStateDropDown();
		}
		$*/
	}).bind('keyup',function(e){
		var searchTerm = "";
		if(!$(this).val()){
			return false;
		}
		searchTerm = $(this).val().trim();
		var searchList = searchInStateArray(searchTerm);
		appendStateDropDown('state-dropdown-wrapper',searchList);
	});
    
    
    $('input[name="coBorrowerState"]').attr("id","stateId").addClass('prof-form-input-statedropdown').bind('click keypress',function(e){
		e.stopImmediatePropagation();
		if($('#state-dropdown-wrapper').css("display") == "none"){
			appendStateDropDown('state-dropdown-wrapper',stateList);
			toggleStateDropDown();
		}/*else{
			toggleStateDropDown();
		}*/
	}).bind('keyup',function(e){
		var searchTerm = "";
		if(!$(this).val()){
			return false;
		}
		searchTerm = $(this).val().trim();
		var searchList = searchInStateArray(searchTerm);
		appendStateDropDown('state-dropdown-wrapper',searchList);
	});
    

    
    
    $('input[name="city"]').attr("id","cityId").bind('click keydown',function(){
		
		var searchData = [];
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			searchData[i] = currentZipcodeLookUp[i].cityName;
		}
		
		var uniqueSearchData = searchData.filter(function(itm,i,a){
		    return i==a.indexOf(itm);
		});
		
		initializeCityLookup(uniqueSearchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	}).width(200);
    
    
    
 $('input[name="coBorrowerCity"]').attr("id","cityId").bind('click keydown',function(){
		
		var searchData = [];
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			searchData[i] = currentZipcodeLookUp[i].cityName;
		}
		
		var uniqueSearchData = searchData.filter(function(itm,i,a){
		    return i==a.indexOf(itm);
		});
		
		initializeCityLookup(uniqueSearchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	}).width(200);
 
 
    $('input[name="zipCode"]').attr("id","zipcodeId").bind('click keydown',function(){
		
		var selectedCity = $('#cityId').val();
		var searchData = [];
		var count = 0;
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			if(selectedCity == currentZipcodeLookUp[i].cityName){
				searchData[count++] = currentZipcodeLookUp[i].zipcode;				
			}
		}

		initializeZipcodeLookup(searchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	});
    
    
 $('input[name="coBorrowerZipCode"]').attr("id","zipcodeId").bind('click keydown',function(){
		
		var selectedCity = $('#cityId').val();
		var searchData = [];
		var count = 0;
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			if(selectedCity == currentZipcodeLookUp[i].cityName){
				searchData[count++] = currentZipcodeLookUp[i].zipcode;				
			}
		}

		initializeZipcodeLookup(searchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	});
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
            text: "Vacation/Second home",
            value: "1"
        }, {
            text: "Investment property",
            value: "2"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "When did you purchase this property?",
        name: "purchaseTime",
        value: appUserDetails.propertyTypeMaster.propertyPurchaseYear
    }];

    /*Commented These questions since these are being asked on previous step
    {
        type: "desc",
        text: "How much is paid in property taxes every year?",
        name: "taxesPaid",
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, 
     {
        type: "desc",
        text: "How much does homeowners insurance cost per year?",
        name: "insuranceCost",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceCost
    }*/
    var questionsContainer = getQuestionsContainer(questions);
    
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
   
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function() {
    	
    	
    	if(this.innerText!= next){
		    	propertyTypeCd = $('.app-options-cont[name="propertyType"]').find('.app-option-selected').data().value;
		    	residenceTypeCd= $('.app-options-cont[name="residenceType"]').find('.app-option-selected').data().value;
		    	//propertyTaxesPaid = $('input[name="taxesPaid"]').val();
		    	//propertyInsuranceProvider = $('input[name="insuranceProvider"]').val();
		    	//propertyInsuranceCost = $('input[name="insuranceCost"]').val();
		    	propertyPurchaseYear = $('input[name="purchaseTime"]').val();
		    	
		    	//var questionOne=validateInput($('input[name="taxesPaid"]'),propertyTaxesPaid,message);
		    	//var questionTwo=validateInput($('input[name="insuranceCost"]'),propertyInsuranceCost,message);
		    	var questionThree=validateInput($('input[name="purchaseTime"]'),propertyPurchaseYear,message);
		    	if(propertyTypeCd==undefined && residenceTypeCd==undefined){
		    		showErrorToastMessage(yesyNoErrorMessage);
		    		return false;
		    	}else if(!questionThree){
		            return false;
		        }
		
		        /*else if(!questionOne){
		    		return false;
		    	}else if(!questionTwo){
		    		return false;
		    	}*/
		    	
	    		propertyTypeMaster.propertyTypeCd = propertyTypeCd;
	        	propertyTypeMaster.residenceTypeCd = residenceTypeCd;
	        	//propertyTypeMaster.propertyTaxesPaid = propertyTaxesPaid;
	        	//propertyTypeMaster.propertyInsuranceProvider = propertyInsuranceProvider;
	        	//propertyTypeMaster.propertyInsuranceCost = propertyInsuranceCost;
	        	propertyTypeMaster.propertyPurchaseYear = propertyPurchaseYear;
	            //propertyTypeMaster.homeWorthToday = homeWorthToday ;
	        	  	
	        	appUserDetails.propertyTypeMaster = propertyTypeMaster;
	        	
	        	
	        	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	        	//saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
	        	saveAndUpdateLoanAppForm(appUserDetails ,paintSecondPropertyStep());
	    		
	    		//paintCustomerApplicationPageStep2();
    	}else{
    		// when click on next button
    		paintSecondPropertyStep();
    	}
    
        
    });

    

    
    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
   
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
                         "text": "Do you have a second mortgage on this property?",
                         "name": "secondMortgage",
                         "options": [
                             {
                                 "text": "Yes",
                                 "addQuestions": [
                                     {
                                         "type": "desc",
                                         "text": "What is the current balance for this additional mortgage?",
                                         "name": "secondaryMortgageBalance",
                                         "selected": appUserDetails.refinancedetails.secondMortageBalance
                                     }
                                 ]
                             },
                             {
                                 "text": "No"
                             }
                         ],
                        "selected": ""
                     }
                 ];
    
    for(var i=0;i<questions.length;i++){
		var question=questions[i];
		var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails);
		contxt.drawQuestion();
		
		quesContxts.push(contxt);
	}
    
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    
	
    var saveAndContinueButton = $('<div>').attr({
	    "class": "cep-button-color ce-save-btn"
	}).html(buttonText).on('click', function() {
		
		  
		if(this.innerHTML!=next){
			   isSecondaryMortgage = quesContxts[0].value;
			   if(isSecondaryMortgage=="" || isSecondaryMortgage==undefined || isSecondaryMortgage==null){
				   showErrorToastMessage(yesyNoErrorMessage);
				   return false;
			   }
	
				   if(isSecondaryMortgage =='Yes'){
					   appUserDetails.secondMortgage = true;
					   var isSuccess=validateInput( $('input[name="secondaryMortgageBalance"]'), $('input[name="secondaryMortgageBalance"]').val(),message);
					   if(!isSuccess){
						   return;
					   } 
				   }
					   
				   else{
					   appUserDetails.secondMortgage = false;
				   }
				   
				  
				   refinancedetails.secondMortageBalance = $('input[name="secondaryMortgageBalance"]').val();
				   
				   appUserDetails.refinancedetails = refinancedetails;
				  
				   saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2);
	
		}else{
			// when click on next button
			paintCustomerApplicationPageStep2();
		}
				   
				   
		      });
	
    $('#app-right-panel').append(saveAndContinueButton);


}

function getQuestionContext(question,parentContainer,valueSet){
//alert('getQuestionContext');

	var contxt={
			type: question.type,
	        text: question.text,
	        name: question.name,
	        options:question.options,
	        container :undefined,
	        childContainers:{},
	        childContexts:{},
	        value:question.selected==undefined?question.value:question.selected,
            yearMonthVal:"",
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
                    ob.container = getMonthYearTextQuestionContext(ob);
                }else if (question.type == "dwnPayment") {
                    ob.container = getContextApplicationPercentageQues(ob);
                }
	        	
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
	        drawChildQuestions:function(option,questions,callback){
	        
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
	        	if(callback){
	        		callback();
	        	}
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
	        	if(value==="Yes"||value===true){
	        		return "Yes";
	        	}else if(value==="No"||value===false){
	        		return "No";
	        	}else
	        	   { 
	        		return value;
	        		}
	        },getValuesForDB:function(){
	        	var value=this.value;
	        	if(value=="Yes"||value==true){
	        		return true;
	        	}else if(value=="No"||value==false){
	        		return false;
	        	}else{
	        	
	        		return value;
	        		}
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
    if(!contxt.value){
        var res=mapDbDataForFrontend(contxt.name);
        if(res!=undefined)
            contxt.value=contxt.mapValues(res);
    }
    if(question.type == "yearMonth"){
        var res=getMappedYearMonthValue(contxt.name);
        if(res!=undefined)
            contxt.yearMonthVal=res;
    }
	return contxt;
}

function getMappedYearMonthValue(key){
    switch(key){
        case "propertyTaxesPaid":
            var val;
            if(typeof(newfiObject)!=='undefined'){
                val=appUserDetails.propertyTypeMaster.propTaxMonthlyOryearly;
                return val
            }else{
                return refinanceTeaserRate.propTaxMonthlyOryearly==undefined?"Year":refinanceTeaserRate.propTaxMonthlyOryearly;
            }
        break;
        case "annualHomeownersInsurance":
            var val;
            if(typeof(newfiObject)!=='undefined'){
                val=appUserDetails.propertyTypeMaster.propInsMonthlyOryearly;
                return val
            }else{
                return refinanceTeaserRate.propInsMonthlyOryearly==undefined?"Year":refinanceTeaserRate.propInsMonthlyOryearly;
            }
        break;
    }
    return "Month";
}

var quesContxts=[];

//TODO-try nested yesno question
function paintCustomerApplicationPageStep2() {
	
	
	appProgressBaar(3); // this is to show the bubble status in the left panel
	quesContxts = []; // when ever call the above function clean the array object
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Co-borrower Information";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).text(quesHeaderTxt);
   
    /*var questions = [{
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
    }];*/

    var questions = [{
        type: "yesno",
        text: "Is there a co-borrower?",
        name: "isCoBorrower",
        options: [{
            text: "Yes",
            addQuestions:[{
                type: "yesno",
                text: "Is the co-borrower your spouse?",
                name: "isSpouseCoBorrower",
                options: [{
                    text: "Yes",
                    callBack:addStateCityZipLookUp,
                    addQuestions:[{
                        type: "desc",
                        text: "Co-borrower's first name",
                        name: "coBorrowerName"
                    },
                    {
                        type: "desc",
                        text: "Co-borrower's last name",
                        name: "coBorrowerLastName"
                    },{
                        type: "desc",
                        text: "Co-borrower's street address",
                        name: "coBorrowerStreetAddress",
                        //value: appUserDetails.user.customerDetail.addressStreet
                    }, {
                        type: "desc",
                        text: "Co-borrower's state",
                        name: "coBorrowerState",
                        //value: appUserDetails.user.customerDetail.addressState
                    }, {
                        type: "desc",
                        text: "Co-borrower's city",
                        name: "coBorrowerCity",
                        //value: appUserDetails.user.customerDetail.addressCity
                    }, {
                        type: "desc",
                        text: "Co-borrower's zip code",
                        name: "coBorrowerZipCode",
                        //value: appUserDetails.user.customerDetail.addressZipCode
                    }]
                }, {
                    text: "No",
                    callBack:addStateCityZipLookUp,
                    addQuestions:[{
                        type: "desc",
                        text: "Co-borrower's first name",
                        name: "coBorrowerName"
                    },
                    {
                        type: "desc",
                        text: "Co-borrower's last name",
                        name: "coBorrowerLastName"
                    },{
                        type: "desc",
                        text: "Co-borrower's street address",
                        name: "coBorrowerStreetAddress",
                        //value: appUserDetails.user.customerDetail.addressStreet
                    }, {
                        type: "desc",
                        text: "Co-borrower's state",
                        name: "coBorrowerState",
                        //value: appUserDetails.user.customerDetail.addressState
                    }, {
                        type: "desc",
                        text: "Co-borrower's city",
                        name: "coBorrowerCity",
                        //value: appUserDetails.user.customerDetail.addressCity
                    }, {
                        type: "desc",
                        text: "Co-borrower's zip code",
                        name: "coBorrowerZipCode",
                        //value: appUserDetails.user.customerDetail.addressZipCode
                    }]
                }],
                selected: ""
            }]
        }, {
            text: "No"
        }],
        selected: ""
    }];
    
    $('#app-right-panel').append(quesHeaderTextCont);
   
  // alert('questions.length'+questions.length);
    
    for(var i=0;i<questions.length;i++){
    
    	var question=questions[i];
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails);
    	contxt.drawQuestion();
    	
    	quesContxts.push(contxt);
    }

    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function() {
       // alert('quesContxts[0].value'+quesContxts[0].value);
              //  alert('quesContxts[1].value'+quesContxts[1].value);
        
    	if(this.innerHTML!=next){
	    	maritalStatus = quesContxts[0].value;
	    	appUserDetails.maritalStatus =  maritalStatus;
    	
	    	if(maritalStatus !="" && maritalStatus !=undefined ){
	    	
	    		//  maritalStatus is equivalent to is co-borrower 
	    		if(maritalStatus == "Yes"){
	    		 appUserDetails.isCoborrowerPresent = true;
	    			 
	    			 if( quesContxts[0].childContexts.Yes !=  undefined && quesContxts[0].childContexts.Yes[0].value!=""){
	    		    		
	    		    		isSpouseOnLoan = quesContxts[0].childContexts.Yes[0].value;
	    		    		coBorrowerName = quesContxts[0].childContexts.Yes[0].childContexts[isSpouseOnLoan][0].value;
	    		    		
	    		    		var response=validateCoBorowerInformation();
	    		    		if(!response){
	    		    			return false;
	    		    		}
	    		    		if( isSpouseOnLoan =="Yes" && coBorrowerName!="" && coBorrowerName){ 
	    		    			appUserDetails.isSpouseOnLoan =true;
	    		    		}else if(isSpouseOnLoan =="No" && coBorrowerName!="" && coBorrowerName){
	    		    			
	    		    			appUserDetails.isSpouseOnLoan =false;
	    		    			appUserDetails.spouseName  = "";
	    		    		}
	    		    		if(isSpouseOnLoan =="Yes" || isSpouseOnLoan =="No"){
	    		    		    	  
	    		    		}else{
	    		    		   showErrorToastMessage(yesyNoErrorMessage);
	 	    	    		   return false;
	    		    		}	
	    		     }else{
	
	    		    	 var response=validateCoBorowerInformation();
	    		    		if(!response){
	    		    			return false;
	    		    		}
	
	    		     }
	    			 
	    		 }else{
	    			
	    		  appUserDetails.isCoborrowerPresent = false;
	    			 appUserDetails.isSpouseOnLoan =false;
		    		 appUserDetails.spouseName  = "";
	    		 }
	    		
		    	// this is the condition when spouseName is in the loan
	            if(!appUserDetails.customerSpouseDetail)
	                appUserDetails.customerSpouseDetail={};
		    	if( quesContxts[0].childContexts.Yes !=  undefined && quesContxts[0].childContexts.Yes[0].childContexts.Yes != undefined){
		    		appUserDetails.isSpouseOnLoan = true;
		    		appUserDetails.spouseName = quesContxts[0].childContexts.Yes[0].childContexts.Yes[0].value;
		    		
		    		appUserDetails.customerSpouseDetail.spouseName = $('input[name="coBorrowerName"]').val();
		    		appUserDetails.customerSpouseDetail.spouseLastName = $('input[name="coBorrowerLastName"]').val();
		    		appUserDetails.customerSpouseDetail.streetAddress= $('input[name="coBorrowerStreetAddress"]').val();
		    		appUserDetails.customerSpouseDetail.state= $('input[name="coBorrowerState"]').val();
		    		appUserDetails.customerSpouseDetail.city= $('input[name="coBorrowerCity"]').val();
		    		appUserDetails.customerSpouseDetail.zip= $('input[name="coBorrowerZipCode"]').val();
		    	
		    	}else if(quesContxts[0].childContexts.Yes !=  undefined && quesContxts[0].childContexts.Yes[0].childContexts.No != undefined){
		    
		    		appUserDetails.customerSpouseDetail.spouseName = $('input[name="coBorrowerName"]').val();
		    		appUserDetails.customerSpouseDetail.spouseLastName = $('input[name="coBorrowerLastName"]').val();
		    		appUserDetails.customerSpouseDetail.streetAddress= $('input[name="coBorrowerStreetAddress"]').val();
		    		appUserDetails.customerSpouseDetail.state= $('input[name="coBorrowerState"]').val();
		    		appUserDetails.customerSpouseDetail.city= $('input[name="coBorrowerCity"]').val();
		    		appUserDetails.customerSpouseDetail.zip= $('input[name="coBorrowerZipCode"]').val();
		    	
		    	}
		    	else{
		    		appUserDetails.customerSpouseDetail.spouseName  = "";
		    		appUserDetails.customerSpouseDetail.spouseLastName = "";
		    		appUserDetails.customerSpouseDetail.streetAddress="";
		    		appUserDetails.customerSpouseDetail.state="";
		    		appUserDetails.customerSpouseDetail.city="";
		    		appUserDetails.customerSpouseDetail.zip="";
		    	}
		    	
		    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
		    	////alert(JSON.stringify(appUserDetails));
		    
		    	saveAndUpdateLoanAppForm(appUserDetails,paintMyIncome);
		    	
	    	}else{
    		showErrorToastMessage(yesyNoErrorMessage);
	    	}
		}else{
			// when click on next button
			paintMyIncome();
		}
    });
    $('#app-right-panel').append(saveAndContinueButton);
    addStateCityZipLookUp();
    
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
//alert(contxt.name)
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
            "class": "cep-button-color app-option-choice",
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
	$(element).parent().find('.app-option-choice').attr("isSelected","false").mouseover().mouseleave();
	$(element).attr("isSelected","true");
	ctx.clickHandler(value);
	if(ctx.value!=value||skipCondition){
    	ctx.value=value;
    	var opt=option;
    	if(opt.addQuestions){
    		ctx.drawChildQuestions(ctx.value,opt.addQuestions,opt.callBack);
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
    var optionCont ;
    var errFeild=appendErrorMessage();
    if(contxt.name == 'propStreetAddress' || contxt.name == 'coBorrowerStreetAddress' || contxt.name=='streetAddress' || contxt.name=='addressStreet'){
    	 optionCont = $('<input>').attr({
    	        "class": "app-input app-append-width",
    	        "name": contxt.name,
    	        "value":showValue(contxt.value)
    	    }).bind("change",{"contxt":contxt},function(event){
    	    	var ctx=event.data.contxt;
    	    	ctx.value=$(this).val();
    	    }).on("load focus", function(e){
    	          
    			if(contxt.name != 'propStreetAddress' && contxt.name != 'propState' && contxt.name != 'propCity' && contxt.name != 'propZipCode' && contxt.name != 'coBorrowerZipCode' && contxt.name != 'coBorrowerName' && contxt.name != 'coBorrowerLastName' && contxt.name != 'coBorrowerStreetAddress' && contxt.name != 'coBorrowerState' && contxt.name != 'coBorrowerCity' && contxt.name != 'zipCode' && contxt.name != 'mortgageyearsleft' && contxt.name != 'locationZipCode' && contxt.name != 'buyhomeZipPri'  && contxt.name != 'city' && contxt.name != 'state' && contxt.name != 'startLivingTime' && contxt.name != 'spouseName' && contxt.name!='streetAddress' && contxt.name!='addressStreet'){
    				$('input[name='+contxt.name+']').maskMoney({
    					thousands:',',
    					decimal:'.',
    					allowZero:true,
    					prefix: '$',
    				    precision:0,
    				    allowNegative:false
    				});
    			}
    			
    			
    		}).keypress(function(key) {
    			if($('input[name='+contxt.name+']').attr('name')=="propZipCode" ||$('input[name='+contxt.name+']').attr('name')=="zipCode" ||$('input[name='+contxt.name+']').attr('name')=="coBorrowerZipCode" ){
    				
  				  if(key.charCode < 48 || key.charCode > 57) return false;
  			}
  	      
  	    });

    	    
    }else{
    	 optionCont = $('<input>').attr({
    	        "class": "app-input",
    	        "name": contxt.name,
    	        "value":showValue(contxt.value)
    	    }).bind("change",{"contxt":contxt},function(event){
    	    	var ctx=event.data.contxt;
    	    	ctx.value=$(this).val();
    	    }).on("load focus", function(e){
    	          
    			if(contxt.name != 'propStreetAddress' && contxt.name != 'propState' && contxt.name != 'propCity' && contxt.name != 'propZipCode' && contxt.name != 'coBorrowerZipCode' && contxt.name != 'coBorrowerName' && contxt.name != 'coBorrowerLastName' && contxt.name != 'coBorrowerStreetAddress' && contxt.name != 'coBorrowerState' && contxt.name != 'coBorrowerCity' && contxt.name != 'zipCode' && contxt.name != 'mortgageyearsleft' && contxt.name != 'locationZipCode' && contxt.name != 'buyhomeZipPri'  && contxt.name != 'city' && contxt.name != 'state' && contxt.name != 'startLivingTime' && contxt.name != 'spouseName' && contxt.name!='streetAddress' && contxt.name!='addressStreet'){
    				$('input[name='+contxt.name+']').maskMoney({
    					thousands:',',
    					decimal:'.',
    					allowZero:true,
    					prefix: '$',
    				    precision:0,
    				    allowNegative:false
    				});
    			}
    			/* this is the piece of code to retrict user put special charector*/
    			restrictSpecialChar(contxt.name);
    			
    		}).keypress(function(key) {
    			if($('input[name='+contxt.name+']').attr('name')=="propZipCode" ||$('input[name='+contxt.name+']').attr('name')=="zipCode" || $('input[name='+contxt.name+']').attr('name')=="coBorrowerZipCode"){
    				
    				  if(key.charCode < 48 || key.charCode > 57) return false;
    			}
    	      
    	    });;

    	    
    }
    
    
    
    if (contxt.value != undefined) {
        optionCont.val(contxt.value);
    }

    optionsContainer.append(optionCont).append(errFeild);

    	
    return container.append(quesTextCont).append(optionsContainer);
}



function incomesSelectALLThatApply() {

	
	var quesTxt = "Select all that apply";

    var selfEmployedData={};
    if(appUserDetails && appUserDetails.isselfEmployed){
        var tmpData={
            monthlyIncome : appUserDetails.selfEmployedMonthlyIncome,
            noOfYears : appUserDetails.selfEmployedNoYear
        }
        selfEmployedData={"selected":appUserDetails.isselfEmployed,"data":tmpData};
    }
    var employedData={};
    if(appUserDetails && appUserDetails.customerEmploymentIncome)
        employedData={"selected":appUserDetails.customerEmploymentIncome.length>0?true:false,"data":appUserDetails.customerEmploymentIncome};
    var childSupportIncome={};
    if(appUserDetails && appUserDetails.receiveAlimonyChildSupport)
        childSupportIncome={"selected":appUserDetails.receiveAlimonyChildSupport,"data":appUserDetails.childSupportAlimony};
    var socialSecIncome={};
    if(appUserDetails && appUserDetails.socialSecurityIncome)
        socialSecIncome={"selected":(appUserDetails.socialSecurityIncome==undefined?false:true),"data":appUserDetails.socialSecurityIncome};
    var socialSecDisabilityIncome={};
    if(appUserDetails && appUserDetails.ssDisabilityIncome)
        socialSecDisabilityIncome={"selected":(appUserDetails.ssDisabilityIncome==undefined?false:true),"data":appUserDetails.ssDisabilityIncome};
    var pensionIncome={};
    if(appUserDetails && appUserDetails.monthlyPension)
        pensionIncome={"selected":(appUserDetails.monthlyPension==undefined?false:true),"data":appUserDetails.monthlyPension};
    var retirementIncome={};
    if(appUserDetails && appUserDetails.retirementIncome)
        retirementIncome={"selected":(appUserDetails.retirementIncome==undefined?false:true),"data":appUserDetails.retirementIncome};

	var options = [ {
		"text" : "W2 Employee",
		"onselect" : paintRefinanceEmployed,
		"name" : "isEmployed",
        "data" : employedData,
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintRefinanceSelfEmployed,
		"name" : "isselfEmployed",
        "data" : selfEmployedData,
		"value" : 1
	},/*, {
		"text" : "Social Security Income/Disability",
		"onselect" : paintRefinanceDisability,
		"name" :"isssIncomeOrDisability",
        "data" : ssiData,
		"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintRefinancePension,
		"name" : "ispensionOrRetirement",
        "data" : prData,
		"value" : 3
	}*/
	{
		"text" : "Child Support/alimony",
		"onselect" : paintRefinancePension,
		"name" :"childAlimonySupport",
        "data" : childSupportIncome,
		"value" : 2
	}, 
	{
		"text" : "Social Security Income",
		"onselect" : paintRefinancePension,
		"name" :"socialSecurityIncome",
        "data" : socialSecIncome,
		"value" : 3
	}, 
	{
		"text" : "Disability Income",
		"onselect" : paintRefinancePension,
		"name" :"disabilityIncome",
        "data" : socialSecDisabilityIncome,
		"value" : 4
	}, 
	{
		"text" : "Pension Income",
		"onselect" : paintRefinancePension,
		"name" :"pensionIncome",
        "data" : pensionIncome,
		"value" : 5
	}, 
	{
		"text" : "Retirement Income",
		"onselect" : paintRefinancePension,
		"name" :"retirementIncome",
        "data" : retirementIncome,
		"value" : 6
	}];
	
    var incomesSelectALLThatApplyDiv = paintCustomerApplicationPageStep3(quesTxt, options, name);
    $('#app-right-panel').append(incomesSelectALLThatApplyDiv);
    
    for(var i=0;i<options.length;i++){
        var option=options[i];
        if(option.onselect){
            option.onselect(option.value,option.data,option.name);
        }
    }
    
    return incomesSelectALLThatApplyDiv;
}
 
function paintMyIncome() {
	
	//appUserDetails = newfi.appUserDetails;
    appProgressBaar(4);
    $('#app-right-panel').html('');
    var incomesSelectALLThatApplyDiv = incomesSelectALLThatApply();
    var questcontainer = $('#app-right-panel');
 	
	if(appUserDetails.loanType.loanTypeCd == "PUR"){
		
		var questionsContainer10 = paintSaleOfCurrentHome();
		  questcontainer.append(questionsContainer10);
    }
   
   var skipMyAssets = appUserDetails.skipMyAssets;
   
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
   
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color ce-save-btn"
    }).html(buttonText).on('click', function(event) {
    	 var isStatus=[];
    	if(this.innerHTML!=next){
    	       
    	        
  	    	/*if($('.ce-option-checkbox[value=0]').hasClass('app-option-checked')){
    	    		isStatus.push( $('.ce-option-checkbox[value='+0+']'));
    	    		var status=validateInputOfChecked(isStatus[0]);
    	        	if(status==false){
    	        		return false;
    	        	}
    	    	}else{
    	    		showErrorToastMessage(W2EmplayeeMandatoryErrorMesssage);
    	    		return isStatus;
    	    	}*/
 	      	 isStatus  = validateCheckbox(isStatus);
    	        if(isStatus.length>0){	
    	      	 
    	        	for(var i=0;i<isStatus.length;i++){
    	        		var status=validateInputOfChecked(isStatus[i]);
        	        	if(status==false){
        	        		return false;
        	        	}
    	        		
    	        			
    	        	}
    	        	
    	        	
    	        }else{
    	        	showErrorToastMessage(selectAnyOne);
    	        	return false;
    	        }
    	       /*var isChecked=[];
				if($('.ce-option-checkbox').hasClass('myassets')){
					if($('.ce-option-checkbox.myassets').hasClass('app-option-checked')){
				
				    }else{ 
				    	
				    	var checkboxes=$('.asset-ques-wrapper').find('.app-account-wrapper');
				    	for(var count=0;count<checkboxes.length;count++){
				    		if($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').hasClass('app-option-checked')){
				    			isChecked.push($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']'));
				    		}
				    }
				    	if(isChecked==""){
				    		$('.ce-option-checkbox.myassets').addClass('text-color');
							showErrorToastMessage(selectAssestErrorMessage);
							return false; 
				    	}else{
				    		for(count=0;count<isChecked.length;count++){
				    			var list=$('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-option-selected').html();
				    			if(list=="Select One"){
				    				showErrorToastMessage(selectAccountType);
				    				return false;
				    			}
				    		    var newCount=0;
				    			var currentBalance=$('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount].value;
				    			var newHomeAdvance=$('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount+1].value;
				    			var questionOne=validateInputsOfAssests($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount],currentBalance,message,count);
								var questionTwo=validateInputsOfAssests($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount+1],newHomeAdvance,message,count);
									if(!questionOne){
										return false;
									}
									if(!questionTwo){
										return false;
									} 
				    		}
							
				    	}
				}
				    	        	
				
				}
               */
    	       
    	      //End of validation
    
    
        var  customerEmploymentIncome = [];
         
        if($("#ce-option_0").prev().hasClass('app-option-checked')){
	       
        	$("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
	            customerEmploymentIncome1 = {};
	
	            id = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="customerEmploymentIncomeId"]').val();
	            if(id ==""){
	                id = undefined;
	            }
	
	            jobTitle = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="jobTitle"]').val();
	            EmployedIncomePreTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="beforeTax"]').val();
	            EmployedAt = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="workPlace"]').val();
	            EmployedSince = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="startWorking"]').val();
	
	            customerEmploymentIncome1.id  = id;
	            customerEmploymentIncome1.employedIncomePreTax = EmployedIncomePreTax;
	            customerEmploymentIncome1.jobTitle=jobTitle;
	            customerEmploymentIncome1.employedAt = EmployedAt;
	            customerEmploymentIncome1.employedSince = EmployedSince;
	            var termp = {};
	            termp.customerEmploymentIncome = customerEmploymentIncome1;
	
	            customerEmploymentIncome.push(termp);
	        });
        	
        	appUserDetails.customerEmploymentIncome = customerEmploymentIncome;
        }
       
     
        
       
    	
        selfEmployedIncome = $('input[name="selfEmployedIncome"]').val();
        selfEmployedYears = $('input[name="selfEmployedYears"]').val();
        
        childAlimonySupport = $('input[name="childAlimonySupport"]').val();
        socialSecurityIncome = $('input[name="socialSecurityIncome"]').val();
        disabilityIncome = $('input[name="disabilityIncome"]').val();
        pensionIncome = $('input[name="pensionIncome"]').val();
        retirementIncome = $('input[name="retirementIncome"]').val();
        appUserDetails.ispensionOrRetirement= false;
        appUserDetails.isselfEmployed = false;
        appUserDetails.receiveAlimonyChildSupport=false;
        appUserDetails.isssIncomeOrDisability=false;
        
        
        
    	if(selfEmployedIncome != "" && selfEmployedIncome != undefined){
            appUserDetails.isselfEmployed = true;
    		appUserDetails.selfEmployedMonthlyIncome= selfEmployedIncome;
    		appUserDetails.selfEmployedNoYear =selfEmployedYears;
    	}else{
            appUserDetails.selfEmployedMonthlyIncome= undefined;
            appUserDetails.selfEmployedNoYear =undefined;
        }
    	
    	if(childAlimonySupport != "" && childAlimonySupport != undefined){
            appUserDetails.receiveAlimonyChildSupport=true;
    		appUserDetails.childSupportAlimony =childAlimonySupport;
    	}else{
            appUserDetails.childSupportAlimony = undefined;
        }
    	
    	if(socialSecurityIncome !="" && socialSecurityIncome != undefined){
    		appUserDetails.isssIncomeOrDisability=true;
    		appUserDetails.socialSecurityIncome = socialSecurityIncome;
    	}else{
            appUserDetails.socialSecurityIncome = undefined;
        }
    	
    	if(disabilityIncome !="" && disabilityIncome != undefined){
            appUserDetails.isssIncomeOrDisability=true;
            appUserDetails.ssDisabilityIncome = disabilityIncome;
        }else{
            appUserDetails.ssDisabilityIncome = undefined;
        }
    	
        if(pensionIncome !="" && pensionIncome != undefined){
            appUserDetails.ispensionOrRetirement=true;
            appUserDetails.monthlyPension = pensionIncome;
        }else{
            appUserDetails.monthlyPension = undefined;
        }
        if(retirementIncome !="" && retirementIncome != undefined){
            appUserDetails.ispensionOrRetirement=true;
            appUserDetails.retirementIncome = retirementIncome;
        }else{
            appUserDetails.retirementIncome = undefined;
        }
        
        
        if(appUserDetails.loanType.loanTypeCd == "PUR"){
            
            homeListPrice = $('input[name="homelistprice"]').val();
            homeMortgageBalance = $('input[name="homemortgagebalance"]').val();
            investmentInHome = $('input[name="inverstInPurchase"]').val();

            
            appUserDetails.propertyTypeMaster.currentHomePrice=homeListPrice;
        	appUserDetails.propertyTypeMaster.currentHomeMortgageBalance=homeMortgageBalance;
       	    appUserDetails.propertyTypeMaster.newHomeBudgetFromsale=investmentInHome;
            
            
           // appUserDetails.customerBankAccountDetails = [];
           // appUserDetails.customerOtherAccountDetails = [];
           // appUserDetails.customerRetirementAccountDetails = [];
          
            
            appUserDetails.skipMyAssets = $('.myassets').hasClass("app-option-checked");
            
            var assets=$('.asset-ques-wrapper').find('.app-account-wrapper');
            
            var bankContainer=assets[0];
            var retirementContainer=assets[1];
            var otherContainer=assets[2];
            
            if($(bankContainer).find('.app-option-checked').hasClass('app-option-checked')){
            	appUserDetails.customerBankAccountDetails=getAccountValues(bankContainer,"customerBankAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
            }
    		if($(retirementContainer).find('.app-option-checked').hasClass('app-option-checked')){
    			appUserDetails.customerRetirementAccountDetails=getAccountValues(retirementContainer,"customerRetirementAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
    	    }
    		if($(otherContainer).find('.app-option-checked').hasClass('app-option-checked')){
    			appUserDetails.customerOtherAccountDetails=getAccountValues(otherContainer,"customerOtherAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
    		}
    		
    		/*this is the condition when all Assestes are not selected*/
    		/*if(!appUserDetails.skipMyAssets && !($(bankContainer).find('.app-option-checked').hasClass('app-option-checked')) && !($(retirementContainer).find('.app-option-checked').hasClass('app-option-checked')) && !($(otherContainer).find('.app-option-checked').hasClass('app-option-checked'))){
    			showErrorToastMessage("Please select any option for my assets");
    			return false;
    		}*/
    		
        }
                
        if (appUserDetails.isSpouseOnLoan == true ||appUserDetails.isCoborrowerPresent == true ) {
            saveAndUpdateLoanAppForm(appUserDetails, paintMySpouseIncome);
        } else {
            saveAndUpdateLoanAppForm(appUserDetails, paintCustomerApplicationPageStep4a);
        }
        
    	}else{
    		/*isStatus  = validateCheckbox(isStatus);
	        if(isStatus!=null||isStatus!=""){	
	      	 
	        	for(var i=1;i<isStatus.length;i++){
	        		var status=validateInputOfChecked(isStatus[i]);
    	        	if(status==false){
    	        		return false;
    	        	}
	        		
	        			
	        	}
	        	
	        	
	        }else{
	        	showErrorToastMessage(selectAnyOne);
	        	return false;
	        }    		*/
    		// when click on next button
    		 if (appUserDetails.isSpouseOnLoan == true ||appUserDetails.isCoborrowerPresent == true ) {
    	            paintMySpouseIncome();
    	        } else {
    	            paintCustomerApplicationPageStep4a();
    	      }
    	}
            
    });
    
    if(skipMyAssets != undefined && skipMyAssets){
		$(".myassets").click();
		
	}
    
    $('#app-right-panel').append(saveAndContinueButton);
    
}
 
function getAccountValues(element,key,accType,balance,forNewHome){
	var values=[];
	$(element).find('.ce-option-ques-wrapper').each(function(){
		
		var id =$(this).find('.ce-rp-ques-text').find('.ce-input[name="id"]').attr("value");
		var accountSubType = $(this).find('.app-options-cont[name="'+accType+'"]').find('.app-option-selected').data("value");
		var currentAccountBalance = $(this).find('input[name="'+balance+'"]').val();
		var  amountForNewHome =  $(this).find('input[name="'+forNewHome+'"]').val();
		
		var accDetailTemp = {};
		var accDetail = {};
		accDetail.id = id;
		accDetail.accountSubType = accountSubType;
		accDetail.currentAccountBalance = currentAccountBalance;
		accDetail.amountForNewHome = amountForNewHome;
		accDetailTemp[key] = accDetail;
		values.push(accDetailTemp);		
	});	
	return values;
}

function paintRefinanceEmployed(divId,value) {
    var flag=true;
    if(value&&!value.selected){
        flag=false;
    }
    else{
        if(value){
            var quesCont ;
            var incomes=value.data;
            for(var i=incomes.length-1;i>=0;i--){
                var income=incomes[i].customerEmploymentIncome;
                    var quesTxt;// = "About how much do you make a year";
                    quesCont = getMultiTextQuestion(quesTxt,income);
                    $('#ce-option_' + divId).prepend(quesCont);
            }
             if(incomes.length>1)
                addRemoveButtons(quesCont);
            $('#ce-option_' + divId).toggle();
            flag=false;
        }
    }
	//appUserDetails.employed ="true";
    if(flag){
    	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
    		var quesTxt;// = "About how much do you make a year";
    		var quesCont = getMultiTextQuestion(quesTxt);
    		$('#ce-option_' + divId).prepend(quesCont);	
    	}
    	$('#ce-option_' + divId).toggle();
    }

    putCurrencyFormat("beforeTax");
}

function getMultiTextQuestion(quesText, value) {

    var wrapper = $('<div>').attr({
        "class": "ce-option-ques-wrapper"
    });


    var container = $('<div>').attr({
        "class": "ce-ques-wrapper",
    });

    var quesTextCont 

    if(quesText){
        quesTextCont= $('<div>').attr({
            "class": "ce-option-text",
        }).html(quesText);
    }

    var optionContainer = $('<div>').attr({
        "class": "ce-options-cont",
    });
    
    // id in hidden form 
    var quesTextCont0 = $('<div>').attr({
        "class": "ce-rp-ques-text"
    });

    var val = "";

    if (value && value.id)
        val = value.id;
    var inputBox0 = $('<input>').attr({
        "class": "ce-input",
        "name": "customerEmploymentIncomeId",
        "type": "hidden"
    });
    if (val != "") {
        inputBox0.attr("value", val);
    }
    quesTextCont0.append(inputBox0);
    
    
    // Job title
    var quesTextCont4 = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html("Job title");

    var val = "";
    
    if (value && value.jobTitle)
        val = value.jobTitle;
    
    var inputBox4 = $('<input>').attr({
        "class": "ce-input",
        "name": "jobTitle"
    });
    if (val != "") {
        inputBox4.attr("value", val);
    }
    quesTextCont4.append(inputBox4).append(appendErrorMessage());
    

    // Monthly income
    var quesTextCont1 = $('<div>').attr({
        "class": "ce-rp-ques-text",
    }).html("Monthly income before taxes");

    var val = "";

    if (value && value.employedIncomePreTax)
        val = showValue(value.employedIncomePreTax);

    var inputBox1 = $('<input>').attr({
        "class": "ce-input",
        "name": "beforeTax"
    });
    if (val != "") {
        inputBox1.attr("value", val);
    }
  
    quesTextCont1.append(inputBox1).append(appendErrorMessage());
    
    
    // Employer

    var quesTextCont2 = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html("Employer");
    
    val = "";
    
    if (value && value.employedAt)
        val = value.employedAt;
    var inputBox2 = $('<input>').attr({
        "class": "ce-input",
        "name": "workPlace"
    });
    if (val != "") {
        inputBox2.attr("value", val);
    }
    quesTextCont2.append(inputBox2).append(appendErrorMessage());
    
    
   // Start working 
    
    var quesTextCont3 = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html("Start date");
    
    val = "";
    
    if (value && value.employedSince)
        val = value.employedSince;
    var inputBox3 = $('<input>').attr({
        "class": "ce-input",
        "name": "startWorking"
    });
    if (val != "") {
        inputBox3.attr("value", val);
    }
    quesTextCont3.append(inputBox3).append(appendErrorMessage());

   

    optionContainer.append(quesTextCont0).append(quesTextCont4).append(quesTextCont1).append(quesTextCont2).append(quesTextCont3);

    if(quesTextCont)
        container.append(quesTextCont)

    container.append(optionContainer);

    return wrapper.append(container);
}

$('body').on('focus',"input[name='startWorking'], input[name='startLivingTime'] ,input[name='purchaseTime'],input[name='spouseStartWorking']", function(e){

	$(this).datepicker({
		format: "M yyyy",
	    minViewMode: "months",
	    autoclose : true,
		maxDate: 0,
		defaultDate:'',
	    constrainInput: false
    }).on('changeDate',function(e){
    	e.stopImmediatePropagation();
    	e.preventDefault();
    	var year = $(this).data('datepicker').getFormattedDate('yyyy');
    	var month = $(this).data('datepicker').getFormattedDate('mm');
    	var currentYear = new Date().getFullYear();
    	var currentMonth = new Date().getMonth();
    	
    	/*if( (currentYear - year < 2) || (currentYear - year == 2 && month > (currentMonth+1)) ){
    		$('#ce-option_0').find('.add-account-btn').trigger('click');
    		if($('#ce-option_0').children('.prev-employement-ques').length <= 0){
    			$('#ce-option_0').find('.add-account-btn').before(getPreviousEmployementQuestions());
    		}
    	}*/
    	
    });
});


$('body').on('keypress',"input[name='beforeTax']",function(){
	
	$("input[name='beforeTax']").maskMoney({
		thousands:',',
		decimal:'.',
		allowZero:true,
		prefix: '$',
	    precision:0,
	    allowNegative:false
	});	
	
});

function getPreviousEmployementQuestions(value) {

	var wrapper = $('<div>').attr({
		"class" : "ce-option-ques-wrapper prev-employement-ques"
	});
	
	
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper",
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-option-text",
	}).html("Previous employement details");

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont",
	});

	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text",
	}).html("Annual income before tax");
   
	var val="";
   
	if(value&&value.employedIncomePreTax)
        val=value.employedIncomePreTax;
	
	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "beforeTax"
	});
	if(val!=""){
		inputBox1.attr("value",val);
	}

	var quesTextCont0 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	});
	
	
	var val="";
	   
	if(value && value.id)
        val=value.id;
	var inputBox0 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "customerEmploymentIncomeId",
		"type":"hidden"
	});
	if(val!=""){
		inputBox0.attr("value",val);
	}
	quesTextCont0.append(inputBox0);
	
	quesTextCont1.append(inputBox1).append(appendErrorMessage());

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Name of previous employer");
    val="";
    if(value&&value.employedAt)
        val=value.employedAt;
	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "workPlace"
	});
	if(val!=""){
		inputBox2.attr("value",val);
	}
	quesTextCont2.append(inputBox2).append(appendErrorMessage());

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Years of employment");
    val="";
    if(value&&value.employedSince)
        val=value.employedSince;
	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "startWorking"
	});
	if(val!=""){
		inputBox3.attr("value",val);
	}
	quesTextCont3.append(inputBox3).append(appendErrorMessage());
	
	var quesTextCont4 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Job title");
    /*if(value&&value.employedSince)
        val=value.employedSince;*/
	var inputBox4 = $('<input>').attr({
		"class" : "ce-input",
		"name" : ""
	});
	if(val!=""){
		inputBox4.attr("value",val);
	}
	quesTextCont4.append(inputBox4).append(appendErrorMessage());

	optionContainer.append(quesTextCont0).append(quesTextCont4).append(quesTextCont1).append(quesTextCont2).append(quesTextCont3);

	 container.append(quesTextCont).append(optionContainer); 
     return wrapper.append(container);
}

$('body').on('focus',"input[name='birthday']",function(){
			
    	$(this).datepicker({
			orientation : "top auto",
			autoclose : true,
			maxDate: 0,
			defaultDate:'',
			placeholder : "MM/DD/YYYY",
			
		});
   
});
$('body').on('focus',"input[name='purchaseTime']",function(e){
	
	 var k = e.which;
     var ok = k >= 65 && k <= 90 || // A-Z
         k >= 97 && k <= 122 || // a-z
         k >= 48 && k <= 57 || k == 32;; // 0-9
     
     if (!ok){
         e.preventDefault();
     }


});

$('body').on('keypress',"input[name='birthday']",function(e){

        var k = e.which;
        var ok = k >= 65 && k <= 90 || // A-Z
            k >= 97 && k <= 122 || // a-z
            k >= 48 && k <= 57 || k == 32 ; // 0-9
        
        if (!ok){
            e.preventDefault();
        }
 
	});
$('body').on('keypress',"input[name='startLivingTime']",function(e){

        var k = e.which;
        var ok = k >= 65 && k <= 90 || // A-Z
            k >= 97 && k <= 122 || // a-z
            k >= 48 && k <= 57 || k == 32;; // 0-9
        
        if (!ok){
            e.preventDefault();
        }

	});
$('body').on('keypress',"input[name='startWorking']",function(e){

        var k = e.which;
        var ok = k >= 65 && k <= 90 || // A-Z
            k >= 97 && k <= 122 || // a-z
            k >= 48 && k <= 57 || k == 32; // 0-9
        
        if (!ok){
            e.preventDefault();
        }

	});
$('body').on('focus',"input[name='ssn']",function(){
	
	$(this).mask("999-99-9999");
		
});
$('body').on('focus',"input[name='phoneNumber']",function(){
    $(this).mask("(999) 999-9999");
});
$('body').on('focus',"input[name='birthday']",function(){
    $(this).mask("99/99/9999");
});


function paintRefinanceSelfEmployed(divId,value) {
    var flag=true;
    if(value&&!value.selected)
        flag=false;
    //appUserDetails.employed ="true";
 
    if(flag){
    	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
    		var wrapper = $('<div>').attr({
    			"class" : "ce-option-ques-wrapper"
    		});
    		var container = $('<div>').attr({
    			"class" : "ce-ques-wrapper"
    		});
    		var quesTextCont = $('<div>').attr({
    			"class" : "ce-option-text"
    		}).html("Monthly income");
    		var optionContainer = $('<div>').attr({
    			"class" : "ce-options-cont"
    		});
            var val="";
            if(value&&value.data&&value.data.monthlyIncome){
                val=showValue(value.data.monthlyIncome);
            }
    		var inputBox = $('<input>').attr({
    			"class" : "ce-input",
    			"name" : "selfEmployedIncome",
    			"value" : val
    		});
    		optionContainer.append(inputBox).append(appendErrorMessage());
    		container.append(quesTextCont).append(optionContainer);
    		
    		var container1 = $('<div>').attr({
    			"class" : "ce-ques-wrapper"
    		});
    		var quesTextCont1 = $('<div>').attr({
    			"class" : "ce-option-text"
    		}).html("Years self-employed");
    		var optionContainer1 = $('<div>').attr({
    			"class" : "ce-options-cont"
    		});
            val="";
            if(value&&value.data&&value.data.noOfYears){
                val=value.data.noOfYears;
            }
    		var inputBox1 = $('<input>').attr({
    			"class" : "ce-input",
    			"name" : "selfEmployedYears",
    			"value" : val
    		});
    		optionContainer1.append(inputBox1).append(appendErrorMessage);
    		container1.append(quesTextCont1).append(optionContainer1);
    		
    		wrapper.append(container).append(container1);
    		$('#ce-option_' + divId).prepend(wrapper);
    	}
    	$('#ce-option_' + divId).toggle();
    	
    	putCurrencyFormat("selfEmployedIncome");
    	restrictSpecialChar("selfEmployedYears");
    	restrictChar("selfEmployedYears");
    }
}

function paintRefinanceDisability(divId,value) {
	var errFeild=appendErrorMessage();
    var flag=true;
    if(value&&!value.selected)
        flag=false;
    //appUserDetails.employed ="true";
    if(flag){
    	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
    		var quesTxt = "Monthly income";
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

    		optionContainer.append(inputBox).append(errFeild);
    		container.append(quesTextCont).append(optionContainer);
    		wrapper.append(container);
    		$('#ce-option_' + divId).prepend(wrapper);
    	}
    	$('#ce-option_' + divId).toggle();
    	putCurrencyFormat("disability");
    }
}

function paintRefinancePension(divId,value,name) {
	var errFeild=appendErrorMessage();
    var flag=true;
    if(value&&!value.selected)
        flag=false;
    //appUserDetails.employed ="true";
    if(flag){
    	if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
    		var quesTxt = "Monthly income";
            var inputElementName="pension";
            if(name){
                inputElementName=name;
            }
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
    	    var val="";
            if(value&&value.data){
                val=showValue(value.data);
            }
    		var inputBox = $('<input>').attr({
    			"class" : "ce-input",
    			"name" : inputElementName,
    			"value": val
    		});
    	
    		optionContainer.append(inputBox).append(errFeild);
    		container.append(quesTextCont).append(optionContainer);
    		wrapper.append(container);
    		$('#ce-option_' + divId).prepend(wrapper);
    		putCurrencyFormat(inputElementName);
    	}
    	$('#ce-option_' + divId).toggle();
    	
    }
}




//////////////////This is new section for Spouse Income Details /////////






















//////////////////////////////////////////////////////////////////////////
















/*function getAppDetialsTextQuestion(quesText, clickEvent, name) {
	var errFeild=appendErrorMessage();
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

	optionContainer.append(inputBox).append(errFeild);

	var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
	
	var saveBtn = $('<div>').attr({
		"class" : "cep-button-color ce-save-btn"
	}).html(buttonText).bind('click', {'clickEvent' : clickEvent,"name" : name}, function(event) {
		var key = event.data.name;
		//appUserDetails[key] = $('input[name="' + key + '"]').val();
		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
		event.data.clickEvent();
	});

	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}*/


function addRemoveButtons(element){
    $(element).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
    var mainContainerId = $(element).parent().attr("id");
    /*var removeAccBtn = $('<div>').attr({
        "class" : "add-btn remove-account-btn"
    }).html("Remove Income").bind('click',{"mainContainerId":mainContainerId},function(event){
        $(this).closest('.ce-option-ques-wrapper').remove();
        var parentDiv = $('#'+event.data.mainContainerId);
    
        if(parentDiv.children('.ce-option-ques-wrapper').length==1){
            parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
        }
    });*/
    $(element).parent().children('.ce-option-ques-wrapper');/*.append(removeAccBtn)*/
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
        
		var selectedClas="";
        
        if(options[i].data&&options[i].data.selected)
            selectedClas="app-option-checked";
		
        var option = $('<div>').attr({
			"class" : "ce-option-checkbox "+selectedClas,
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : options[i].name+"-CHK"
		}, function(event) {
			if($(this).hasClass('app-option-checked')){
        		$(this).removeClass('app-option-checked');
        		//appUserDetails[name] = false;
        		var selectedCheck = $(this).attr("value");
        		$("#ce-option_"+selectedCheck+" :input").each(function() {
        			  $(this).val('');
        			  
        		});
        		
        	}else{
	        	$(this).addClass('app-option-checked');
	        	//appUserDetails[name] = true;
        	}
			var key = event.data.name;
			
			console.info("key = "+key+" ---- > value attr = "+$(this).attr("value"));
			
			//appUserDetails[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value,undefined,event.data.option.name);
		});

		optionContainer.append(option);
		
		if(i==0){
			var addAccountBtn = $('<div>').attr({
				"class" : "cep-button-color add-btn add-account-btn"
			}).html("Add another source of W2 income").bind('click',function(){
				
				var mainContainerId = $(this).closest('.ce-sub-option-wrapper').attr("id");
				
				if($('#'+mainContainerId).children('.ce-option-ques-wrapper').length >= 2){
					showErrorToastMessage(maxIncomeNeeded);
				     return false;
				}
				var quesTxt;// = "About how much do you make a year";
	    		var quesCont = getMultiTextQuestion(quesTxt);
				/*var containerToAppend = $(this).parent().find('.ce-option-ques-wrapper').wrap('<p/>').parent().html();
				$(this).parent().find('.ce-option-ques-wrapper').unwrap();*/
				$(this).before(quesCont);
				
				//$(this).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
				
				var removeAccBtn = $('<div>').attr({
					"class" : "cep-button-color add-btn remove-account-btn"
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
			optionsWrapper.append(addAccountBtn);
		}
		
		optionContainer.append(optionsWrapper);
	}

	if(appUserDetails.loanType.loanTypeCd =="PUR") {
		return container.append(quesTextCont).append(optionContainer);
	}

	return container.append(quesTextCont).append(optionContainer);
}



var quesDeclarationContxts =[];
function paintCustomerApplicationPageStep4a() {
   
	
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
        text: "Have you had property foreclosed upon or given title or deed in lieu of in the last 7 years?",
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
                name: "permanentResidentAlien",
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
                name: "typeOfPropertyOwned",
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
                name: "propertyTitleStatus",
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
    
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    

    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function() {
    	
    	
    	if(this.innerHTML!=next){
    		//Validation
	    	for(var i=0;i<quesDeclarationContxts.length;i++){
	    		if(quesDeclarationContxts[i].value==""||quesDeclarationContxts[i].value==undefined){
	    			showErrorToastMessage(yesyNoErrorMessage);
	    			return false;
	    		}
	    	}

	    	//End of validation
	    	isOutstandingJudgments =  quesDeclarationContxts[0].value;
	    	isBankrupt =  quesDeclarationContxts[1].value;
	    	isPropertyForeclosed =  quesDeclarationContxts[2].value;
	    	isLawsuit =  quesDeclarationContxts[3].value;
	    	isObligatedLoan =  quesDeclarationContxts[4].value;
	    	isFederalDebt =  quesDeclarationContxts[5].value;
	    	isObligatedToPayAlimony =  quesDeclarationContxts[6].value;
	    	isDownPaymentBorrowed = quesDeclarationContxts[7].value;
	    	isEndorser =  quesDeclarationContxts[8].value;
	    	
	    	isUSCitizen =  quesDeclarationContxts[9].value;
	    	
	    	
	    	
	    	 isOccupyPrimaryResidence =  quesDeclarationContxts[10].value;
	    	 isOwnershipInterestInProperty =  quesDeclarationContxts[11].value;
	    	
	    	 typeOfPropertyOwned =  $('.app-options-cont[name="typeOfPropertyOwned"]').find('.app-option-selected').data();
	     	if(typeOfPropertyOwned != undefined){
	     		typeOfPropertyOwned =  $('.app-options-cont[name="typeOfPropertyOwned"]').find('.app-option-selected').data().value;
	     		//validation
	     		if(typeOfPropertyOwned==""||typeOfPropertyOwned==undefined){
	     			showErrorToastMessage(yesyNoErrorMessage);
	     			return false;
	     		}
	     		//End of validation
	     	}
	     		
	     	
	     	 propertyTitleStatus =  $('.app-options-cont[name="propertyTitleStatus"]').find('.app-option-selected').data();
	     	if(propertyTitleStatus != undefined){
	     		propertyTitleStatus =  $('.app-options-cont[name="propertyTitleStatus"]').find('.app-option-selected').data().value;
	     		//End of validation
	     		if(propertyTitleStatus==""||propertyTitleStatus==undefined){
	     			showErrorToastMessage(yesyNoErrorMessage);
	     			return false;
	     		}
	     		//End of validation
	     	}
	     		
	 	    	
	 	    
	    	 governmentquestion = appUserDetails.governmentquestion;
	    	 governmentquestion.typeOfPropertyOwned=typeOfPropertyOwned;
		    governmentquestion.propertyTitleStatus=propertyTitleStatus;
	    	 if( isOutstandingJudgments =="Yes"){ 
	    		 governmentquestion.isOutstandingJudgments = true;
	 		 }else if(isOutstandingJudgments =="No"){
	 			governmentquestion.isOutstandingJudgments = false;
	 		 }else{
	 			governmentquestion.isOutstandingJudgments = null;
	 		 }
	    	 
	    	 
	    	 
	    	 if( isDownPaymentBorrowed =="Yes"){ 
	    		 governmentquestion.isDownPaymentBorrowed = true;
	 		 }else if(isDownPaymentBorrowed =="No"){
	 			governmentquestion.isDownPaymentBorrowed = false;
	 		 }else{
	 			governmentquestion.isDownPaymentBorrowed = null;
	 		 }
	    	 
	    	 
	    	 
	    	 if( isBankrupt =="Yes"){ 
	    		 governmentquestion.isBankrupt = true;
	 		 }else if(isBankrupt =="No"){
	 			governmentquestion.isBankrupt = false;
	 		 }else{
	 			governmentquestion.isBankrupt = null;
	 		 }
	    	 
	    	 if( isPropertyForeclosed =="Yes"){ 
	    		 governmentquestion.isPropertyForeclosed = true;
	 		 }else if(isPropertyForeclosed =="No"){
	 			governmentquestion.isPropertyForeclosed = false;
	 		 }else{
	 			governmentquestion.isPropertyForeclosed = null;
	 		 }
	    	 
	    	 if( isLawsuit =="Yes"){ 
	    		 governmentquestion.isLawsuit = true;
	 		 }else if(isLawsuit =="No"){
	 			governmentquestion.isLawsuit = false;
	 		 }else{
	 			governmentquestion.isLawsuit = null;
	 		 }
	
	    	 if( isObligatedLoan =="Yes"){ 
	    		 governmentquestion.isObligatedLoan = true;
	 		 }else if(isObligatedLoan =="No"){
	 			governmentquestion.isObligatedLoan = false;
	 		 }else{
	 			governmentquestion.isObligatedLoan = null;
	 		 }
	    	 
	    	 if( isFederalDebt =="Yes"){ 
	    		 governmentquestion.isFederalDebt = true;
	 		 }else if(isFederalDebt =="No"){
	 			governmentquestion.isFederalDebt = false;
	 		 }else{
	 			governmentquestion.isFederalDebt = null;
	 		 }
	
	    	 if( isObligatedToPayAlimony =="Yes"){ 
	    		 governmentquestion.isObligatedToPayAlimony = true;
	 		 }else if( isObligatedToPayAlimony =="No"){
	 			governmentquestion.isObligatedToPayAlimony = false;
	 		 }else{
	 			governmentquestion.isObligatedToPayAlimony = null;
	 		 }
	    	 
	    	 if( isEndorser =="Yes"){ 
	    		 governmentquestion.isEndorser = true;
	 		 }else if(isEndorser =="No"){
	 			governmentquestion.isEndorser = false;
	 		 }else{
	 			governmentquestion.isEndorser = null;
	 		 }
	
	    	 if( isUSCitizen =="Yes"){ 
	    		 governmentquestion.isUSCitizen = true;
	 		 }else if(isUSCitizen =="No"){
	 			governmentquestion.isUSCitizen = false;
	 			permanentResidentAlien = quesDeclarationContxts[9].childContexts.No[0].value;
	 				if(permanentResidentAlien =="Yes")
	 					governmentquestion.permanentResidentAlien = true;
	 				else if(permanentResidentAlien =="No")
	 					governmentquestion.permanentResidentAlien = false;
	 				else 
	 					governmentquestion.permanentResidentAlien = null;
	 		 }else{
	 			governmentquestion.isUSCitizen = null;
	 		 }
	
	    	 if( isOccupyPrimaryResidence =="Yes"){ 
	    		 governmentquestion.isOccupyPrimaryResidence = true;
	 		 }else if(isOccupyPrimaryResidence =="No"){
	 			governmentquestion.isOccupyPrimaryResidence = false;
	 		 }else{
	 			governmentquestion.isOccupyPrimaryResidence = null;
	 		 }
	    	 
	    	 if( isOwnershipInterestInProperty =="Yes"){ 
	    		 governmentquestion.isOwnershipInterestInProperty = true;
	    		 if(typeOfPropertyOwned==undefined && propertyTitleStatus==undefined){
	    			 showErrorToastMessage(yesyNoErrorMessage);
	    			 return;
	    		 }
	 		 }else if(isOwnershipInterestInProperty =="No"){
	 			governmentquestion.isOwnershipInterestInProperty = false;
	 		 }else{
	 			governmentquestion.isOwnershipInterestInProperty = null;
	 		 }
	    	 
	    	 appUserDetails.governmentquestion =governmentquestion;
	    	 
	    	 //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	// //alert(JSON.stringify(appUserDetails));
	    	 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4b);
    	}else{
    		// when click on next button
    		paintCustomerApplicationPageStep4b();
    	}	 
    	
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
		"text" : "I decline to provide",
		"name" : "bypassoptional",
		"value" : 0
	}];
	

	var quesCont = paintGovernmentMonitoringQuestions(quesHeaderTxt, options, name);

	$('#app-right-panel').append(quesCont);
    
	var optionalQuestion = appUserDetails.governmentquestion.skipOptionalQuestion;
	
	
    
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
            value: "nativeHawaiian"
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
	 
	 var lqbFileId = checkLqbFileId();
		if(lqbFileId){
			buttonText = next;
		}

	 var saveAndContinueButton = $('<div>').attr({
	        "class": "cep-button-color app-save-btn"
	    }).html(buttonText).on('click', function() {
	    	
	    	if(this.innerHTML!=next){
            //dateOfBirth = $('input[name="birthday"]').val();
		    	ethnicity =  $('.app-options-cont[name="ethnicity"]').find('.app-option-selected').data().value;
		    	race =  $('.app-options-cont[name="race"]').find('.app-option-selected').data().value;
		    	sex =  $('.app-options-cont[name="sex"]').find('.app-option-selected').data().value;
		    	
		    	skipOptionalQuestion = $('.ce-option-checkbox').hasClass("ce-option-checked");
	
		    	if($('.ce-option-checkbox').hasClass("ce-option-checked")){
		    		
		    	}else{
		    		if(ethnicity==undefined && race==undefined && sex==undefined){
			    		showErrorToastMessage(yesyNoErrorMessage);
			    		return false;
			    	} 
		    		
		    	}
		    	//govQues =appUserDetails.governmentquestion;
		    	
		    	appUserDetails.governmentquestion.ethnicity = ethnicity;
		    	appUserDetails.governmentquestion.race = race;
		    	appUserDetails.governmentquestion.sex =sex;
		    	appUserDetails.governmentquestion.skipOptionalQuestion=skipOptionalQuestion;
		    	
		    	//appUserDetails.governmentquestion = govQues; 
		    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
		    	
		    	
		    	if(appUserDetails.isSpouseOnLoan == true || appUserDetails.isCoborrowerPresent == true)
					{
					saveAndUpdateLoanAppForm(appUserDetails,paintSpouseCustomerApplicationPageStep4a);
					}else{
					 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep5);
					}
	    	}else{
	    		// when click on next button
	    		if(appUserDetails.isSpouseOnLoan == true || appUserDetails.isCoborrowerPresent == true)
				{
				   paintSpouseCustomerApplicationPageStep4a();
				}else{
				   paintCustomerApplicationPageStep5();
				}
	    		
	    	}
	    });

	    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
	
	    if(optionalQuestion != undefined && optionalQuestion){
			$(".ce-option-checkbox").click();
			
		}
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


 
 
/*function goverementOptionalQues() {
 
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
            value: "nativeHawaiian"
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
}*/
 

function paintCustomerApplicationPageStep5() {
	
	var userName = appUserDetails.user.firstName;
	
	appProgressBaar(6);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Credit for " +userName;

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

   
    var dob = "";
    if(appUserDetails.user.customerDetail.dateOfBirth != 0)
    dob =makeDateFromLong(appUserDetails.user.customerDetail.dateOfBirth);
    if(dob =="" || dob == undefined || dob =='NaN/NaN/NaN')
    	dob="";
    
    var socialSecurityWrapper = $('<div>').attr({
    	"class" : "ce-options-cont"
    });
    
    var isAuthorizedCheckBox = $('<div>').attr({
    	"class" : "ce-option-checkbox"
    }).text("I authorize newfi to pull my credit report to find the best rates and programs that meet my situation ")
    .bind('click',function(){
    	if($(this).hasClass('ce-option-checked')){
    		$(this).removeClass('ce-option-checked');
    		$(this).parent().find('.ss-ques-wrapper').hide();
    	}else{
    		$(this).addClass('ce-option-checked');
    		$(this).parent().find('.ss-ques-wrapper').show();
    	}
    });
    
    var socialSecurityQues = [{
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: appUserDetails.user.customerDetail.ssn
    }];
    
    var socialSecurityQuesContainer = $('<div>').attr({
    	"class" : "hide ss-ques-wrapper"
    }).append(getQuestionsContainer(socialSecurityQues));
    
    socialSecurityWrapper.append(isAuthorizedCheckBox).append(socialSecurityQuesContainer);
    
    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "birthday",
        value: dob
    }/*,
    {
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: appUserDetails.user.customerDetail.ssn
    }*/,
    {
        type: "desc",
        text: "Phone Number",
        name: "phoneNumber",
        value: appUserDetails.user.phoneNumber
    }];

    var questionsContainer = getQuestionsContainer(questions);
    
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}

    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function() {
    	
    	if(this.innerHTML!=next){
    	
		    	dateOfBirth = $('input[name="birthday"]').val();
		    	ssn =  $('input[name="ssn"]').val();
		    	phoneNumber =  $('input[name="phoneNumber"]').val();
		    	phoneNumber = phoneNumber.replace(/[^0-9]/g, '');
		    	var dat=new Date(dateOfBirth);
		        var dateNow=new Date();
		        dateNow.setFullYear(dateNow.getFullYear()-18);
		        var yearCount=(dateNow.getTime()-dat.getTime());
		    	
		    	var questionOne=validateInput($('input[name="birthday"]'),$('input[name="birthday"]').val(),message);
		    	var questionTwo=validateInput($('input[name="phoneNumber"]'),$('input[name="phoneNumber"]').val(),message);
		    	if(!questionOne){
		    		return false;
		    	}
		    	if(!questionTwo){
		    		return false;
		    	}else{
		    		if(phoneNumber.length<10){
		    			$('input[name="phoneNumber"]').next('.err-msg').html(phoneNumberLegthErrorMessage).show();
		    			$('input[name="phoneNumber"]').addClass('ce-err-input').show();
	    				return false;
	    			}
		    	}
		    	var ssnProvided = $('.ce-option-checkbox').hasClass("ce-option-checked");
		    	if( $('.ce-option-checkbox').hasClass("ce-option-checked")){
		    		
		    		var isSuccess=validateInput($('input[name="ssn"]'),$('input[name="ssn"]').val(),message);
		    		
		    		if(!isSuccess){
		    			
		    			return false;
		    		}else{
		    			if($('input[name="ssn"]').val().length<9){
		    				$('input[name="ssn"]').next('.err-msg').html(ssnLengthErrorMessage).show();
		    				$('input[name="ssn"]').addClass('ce-err-input').show();
		    				return false;
		    			}
		    		}
		    	}else{
		    		if(!questionOne){
		        		return false;
		        	}else if(!questionTwo){
		        		return false;
		        	}
		    	}
		    	//alert('ssnProvided'+ssnProvided);
		    	
		    	if(dateOfBirth != undefined && dateOfBirth !=""  && phoneNumber != undefined && phoneNumber !="" && yearCount>=0){
		    		
		    		//appUserDetails.customerDetail
		    		
		    		customerDetailTemp =  appUserDetails.user.customerDetail;
		            userTemp = appUserDetails.user;
		    		customerDetailTemp.dateOfBirth= dateOfBirth;//new Date(dateOfBirth).getTime();
		    		
		    		customerDetailTemp.ssn = ssn;
		    		//customerDetailTemp.secPhoneNumber = secPhoneNumber;
		    		userTemp.phoneNumber = phoneNumber;
		    		//applicationFormSumbit();
		    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
		    		appUserDetails.ssnProvided = ssnProvided;
		    		appUserDetails.user = userTemp;
		    		
		    		appUserDetails.user.customerDetail = customerDetailTemp;
		    		////alert(JSON.stringify(customerDetail));
		    		
		    		
		    /////alert(JSON.stringify(appUserDetails));
		    		
		    		
		    		if(appUserDetails.isSpouseOnLoan == true || appUserDetails.isCoborrowerPresent == true){
						saveAndUpdateLoanAppForm(appUserDetails,paintCustomerSpouseApplicationPageStep5);
					}else{
						 saveAndUpdateLoanAppForm(appUserDetails,applicationFormSumbit(appUserDetails));
					}
		    		
		    		
		    		
		    		
		    		
		    	}else{
		            if(yearCount<0){
		                showErrorToastMessage(ageErrorMessage);
		            }else
		    		  showErrorToastMessage(yesyNoErrorMessage);
		    	}
    	}else{
    		// when click on next button
    		if(appUserDetails.isSpouseOnLoan == true || appUserDetails.isCoborrowerPresent == true){
				paintCustomerSpouseApplicationPageStep5();
			}else{
                paintLockRatePage();
				// applicationFormSumbit(appUserDetails);
			}
    	}
    	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(socialSecurityWrapper).append(saveAndContinueButton);
        
        
        var ssnGiven = appUserDetails.ssnProvided;
if(ssnGiven!= undefined && ssnGiven){
$(".ce-option-checkbox").click();
}
}

function paintLockRatePage(){
    var userId=selectedUserDetail.userID;
    getAppDetailsForUser(userId,function(appUserDetailsTemp){
        $('#overlay-loader').show();
        var LQBFileId=appUserDetailsTemp.loan.lqbFileId;
        if(LQBFileId){
            paintFixYourRatePage();
        }else{
            //code to Paint teaser rate page
            paintTeaserRatePageBasedOnLoanType(appUserDetailsTemp);
        }
    });
}

function applicationFormSumbit(appUserDetails){
	//paintLockRate(lqbData, appUserDetails);
	createLoan(appUserDetails, true);
	
	//saveUserAndLockRate(appUserDetails) ;
	//changeSecondaryLeftPanel(3);
}






var lqbData=[
    {
        "loanDuration": "sample",
        "loanNumber": "D2015040294"
    },
    {
        "loanDuration": "30 FIXED CONF YOSEMITE",
        "rateVO": [
            {
                "teaserRate": "4.750",
                "closingCost": "($3,245.83)",
                "APR": "N/A",
                "payment": "1,460.61",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "-1.925"
            },
            {
                "teaserRate": "4.625",
                "closingCost": "($2,902.02)",
                "APR": "N/A",
                "payment": "1,439.59",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "-1.797"
            },
            {
                "teaserRate": "4.500",
                "closingCost": "($1,810.60)",
                "APR": "N/A",
                "payment": "1,418.72",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "-1.402"
            },
            {
                "teaserRate": "4.375",
                "closingCost": "($590.38)",
                "APR": "N/A",
                "payment": "1,398.00",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "-0.961"
            },
            {
                "teaserRate": "4.250",
                "closingCost": "$719.43",
                "APR": "N/A",
                "payment": "1,377.43",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "-0.488"
            },
            {
                "teaserRate": "4.125",
                "closingCost": "$2,709.65",
                "APR": "N/A",
                "payment": "1,357.02",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "0.228"
            },
            {
                "teaserRate": "4.000",
                "closingCost": "$4,960.27",
                "APR": "N/A",
                "payment": "1,336.76",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "1.037"
            },
            {
                "teaserRate": "3.990",
                "closingCost": "$5,099.10",
                "APR": "N/A",
                "payment": "1,335.15",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "1.087"
            },
            {
                "teaserRate": "3.875",
                "closingCost": "$6,695.68",
                "APR": "N/A",
                "payment": "1,316.66",
                "lLpTemplateId": "1cca04b2-4f0d-4cc9-a67c-17210f95a5b2",
                "point": "1.662"
            }
        ]
    },
    {
        "loanDuration": "30 FIXED CONFORMING CASCADES",
        "rateVO": [
            {
                "teaserRate": "4.375",
                "closingCost": "($8,889.58)",
                "APR": "N/A",
                "payment": "1,398.00",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-3.925"
            },
            {
                "teaserRate": "4.250",
                "closingCost": "($8,296.57)",
                "APR": "N/A",
                "payment": "1,377.43",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-3.708"
            },
            {
                "teaserRate": "4.125",
                "closingCost": "($6,605.95)",
                "APR": "N/A",
                "payment": "1,357.02",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-3.099"
            },
            {
                "teaserRate": "4.000",
                "closingCost": "($4,582.13)",
                "APR": "N/A",
                "payment": "1,336.76",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-2.371"
            },
            {
                "teaserRate": "3.875",
                "closingCost": "($2,558.32)",
                "APR": "N/A",
                "payment": "1,316.66",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-1.643"
            },
            {
                "teaserRate": "3.750",
                "closingCost": "($1,004.90)",
                "APR": "N/A",
                "payment": "1,296.72",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-1.083"
            },
            {
                "teaserRate": "3.625",
                "closingCost": "$1,220.52",
                "APR": "N/A",
                "payment": "1,276.94",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "-0.283"
            },
            {
                "teaserRate": "3.500",
                "closingCost": "$3,986.33",
                "APR": "N/A",
                "payment": "1,257.33",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "0.710"
            },
            {
                "teaserRate": "3.375",
                "closingCost": "$5,626.55",
                "APR": "N/A",
                "payment": "1,237.87",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "1.301"
            },
            {
                "teaserRate": "3.250",
                "closingCost": "$7,493.57",
                "APR": "N/A",
                "payment": "1,218.58",
                "lLpTemplateId": "5e580ed9-2af2-4fd6-ab54-ce7ceb4a3461",
                "point": "1.973"
            }
        ]
    },
    {
        "loanDuration": "30 FIXED CONFORMING HIGH BALANCE CASCADES",
        "rateVO": [
            {
                "teaserRate": "5.000",
                "closingCost": "($6,680.27)",
                "APR": "N/A",
                "payment": "1,503.10",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-3.162"
            },
            {
                "teaserRate": "4.875",
                "closingCost": "($5,810.05)",
                "APR": "N/A",
                "payment": "1,481.78",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-2.846"
            },
            {
                "teaserRate": "4.750",
                "closingCost": "($5,040.63)",
                "APR": "N/A",
                "payment": "1,460.61",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-2.566"
            },
            {
                "teaserRate": "4.500",
                "closingCost": "($4,148.60)",
                "APR": "N/A",
                "payment": "1,418.72",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-2.237"
            },
            {
                "teaserRate": "4.375",
                "closingCost": "($3,485.58)",
                "APR": "N/A",
                "payment": "1,398.00",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-1.995"
            },
            {
                "teaserRate": "4.250",
                "closingCost": "($2,377.37)",
                "APR": "N/A",
                "payment": "1,377.43",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-1.594"
            },
            {
                "teaserRate": "4.125",
                "closingCost": "($765.15)",
                "APR": "N/A",
                "payment": "1,357.02",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-1.013"
            },
            {
                "teaserRate": "4.000",
                "closingCost": "$1,101.87",
                "APR": "N/A",
                "payment": "1,336.76",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-0.341"
            },
            {
                "teaserRate": "3.875",
                "closingCost": "$1,414.88",
                "APR": "N/A",
                "payment": "1,316.66",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "-0.224"
            },
            {
                "teaserRate": "3.750",
                "closingCost": "$2,856.30",
                "APR": "N/A",
                "payment": "1,296.72",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "0.296"
            },
            {
                "teaserRate": "3.625",
                "closingCost": "$4,966.92",
                "APR": "N/A",
                "payment": "1,276.94",
                "lLpTemplateId": "13a99a03-3850-4f7f-afda-1f7a2516ec5a",
                "point": "1.055"
            }
        ]
    },
    {
        "loanDuration": "30 YR FIXED HIGH BALANCE FNMA MAMMOTH",
        "rateVO": [
            {
                "teaserRate": "4.625",
                "closingCost": "($7,480.02)",
                "APR": "N/A",
                "payment": "1,439.59",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-3.432"
            },
            {
                "teaserRate": "4.500",
                "closingCost": "($6,859.00)",
                "APR": "N/A",
                "payment": "1,418.72",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-3.205"
            },
            {
                "teaserRate": "4.375",
                "closingCost": "($5,815.18)",
                "APR": "N/A",
                "payment": "1,398.00",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-2.827"
            },
            {
                "teaserRate": "4.250",
                "closingCost": "($4,804.97)",
                "APR": "N/A",
                "payment": "1,377.43",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-2.461"
            },
            {
                "teaserRate": "4.125",
                "closingCost": "($3,416.75)",
                "APR": "N/A",
                "payment": "1,357.02",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-1.960"
            },
            {
                "teaserRate": "4.000",
                "closingCost": "($1,496.53)",
                "APR": "N/A",
                "payment": "1,336.76",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-1.269"
            },
            {
                "teaserRate": "3.875",
                "closingCost": "$177.28",
                "APR": "N/A",
                "payment": "1,316.66",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-0.666"
            },
            {
                "teaserRate": "3.750",
                "closingCost": "$1,652.30",
                "APR": "N/A",
                "payment": "1,296.72",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "-0.134"
            },
            {
                "teaserRate": "3.625",
                "closingCost": "$3,799.32",
                "APR": "N/A",
                "payment": "1,276.94",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "0.638"
            },
            {
                "teaserRate": "3.500",
                "closingCost": "$6,492.33",
                "APR": "N/A",
                "payment": "1,257.33",
                "lLpTemplateId": "799d269b-d129-4df2-9229-afd5b36420a7",
                "point": "1.605"
            }
        ]
    },
    {
        "loanDuration": "30 YR FIXED LTV PLUS NONCONFORMING CASCADES",
        "rateVO": []
    }
];





var tempdata = [{
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
 
function saveUserAndLockRate(appUserDetails) {
    ////alert(JSON.stringify(registration));
    
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
        cache:false,
        success: function(data) {
            $('#overlay-loader').hide();
            //TO:DO pass the data (json)which is coming from the controller
            //paintLockRate(data,appUserDetails);
            //paintLockRate(JSON.parse(data), appUserDetails);
            paintLockRate(tempdata, appUserDetails);
        },
        error: function() {
        	showErrorToastMessage("error");
            $('#overlay-loader').hide(); 
        }
 
    });
 
 
 
}
 
 
 
 

function appProgressBaar(num){
	scrollToTop();
	adjustCustomerApplicationPageOnResize();
	adjustCustomerEngagementPageOnResize();
	$('#step-no').text(num);
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
	appUserDetails.loanAppFormCompletionStatus=contxtHolder.getPercentageForStep(num);
}

function putCurrencyFormat(name){
	
	$("input[name="+name+"]").focus(function() {
    	$("input[name="+name+"]").maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});		
    });
	
	restrictSpecialChar(name);
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

function getYearMonthOptionContainer(contxt){
    var optionsContainer = $('<div>').attr({
        "class": "month-cont app-options-cont float-left",
        "name": "yearMonth"
    });

    
    var options=[
         {
             value:"Year",
             text:"Year"
         },
         {
             value:"Month",
             text:"Month"
         }
   ];
    
    var selectedOption = $('<div>').attr({
    	 "class": "app-option-selected"
    }).data("value",options[0].value)
    .text(options[0].text)
    .on('click', function(e) {
    	e.stopPropagation();
    	$('.app-dropdown-cont').hide();
        $(this).parent().find('.app-dropdown-cont').toggle();
    });

    var dropDownContainer = $('<div>').attr({
        "class": "app-dropdown-cont hide"
    });
    var selVal;
    
    for (var i = 0; i < options.length; i++) {
        var option = options[i];
        
        var optionCont = $('<div>').attr({
                "class": "app-option-sel"
            }).data({
                "value": option.value
            }).html(option.text)
            .bind(
                'click',{"contxt":contxt},
                function(e) {
                    var ctx=e.data.contxt;
                    $(this).closest('.app-options-cont').find(
                        '.app-option-selected').html($(this).html());
                    $(this).closest('.app-options-cont').find(
                    '.app-option-selected').data("value",$(this).data("value"));
                    $(this).closest('.app-dropdown-cont').toggle();
                    if(ctx.yearMonthVal!=$(this).data("value")){
                        ctx.yearMonthVal=$(this).data("value");
                        if($(this).data("value")=="Year"){
                            var val=getFloatValue(ctx.value)/12;
                            ctx.value = val;
                        }else{
                            var val=getFloatValue(ctx.value)*12;
                            ctx.value = val;
                        }
                    }
                });
            if(option.value==contxt.yearMonthVal)
                selVal=optionCont;
        dropDownContainer.append(optionCont);
    }
    optionsContainer.append(selectedOption).append(dropDownContainer);
    if(selVal){
        $(selVal).closest('.app-options-cont').find(
                        '.app-option-selected').html($(selVal).html());
        $(selVal).closest('.app-options-cont').find(
        '.app-option-selected').data("value",$(selVal).data("value"));
        contxt.yearMonthVal=$(selVal).data("value");
    }
    
    return optionsContainer;
}

function getMonthYearTextQuestionContext(contxt) {
    var container = $('<div>').attr({
        "class": "ce-ques-wrapper"
    });
    contxt.container = container;
    contxt.parentContainer.append(contxt.container);
    var quesTextCont = $('<div>').attr({
        "class": "ce-rp-ques-text"
    }).html(contxt.text);
    var newDiv=$('<div>').attr({
    	
    	"class":"month-options-cont float-left"
    });
    var optionsContainer = $('<div>').attr({
        "class": "ce-options-cont clearfix"
    });
    var errFeild=$('<div>').attr({
		"class" : "err-msg float-left hide"
	});

    var optionCont = $('<input>').attr({
        "class": "ce-input float-left",
        "name": contxt.name,
        "value": contxt.value
    }).bind("change", {
        "contxt": contxt
    }, function(event) {
        var ctx = event.data.contxt;
        if(ctx.yearMonthVal=="Month"){
            ctx.value = $(this).val();
        }else{
            var val=getFloatValue($(this).val())/12;
            ctx.value = val;
        }
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
    	"class": "per float-left",
    }).html("per");
      

    var selectedOption = getYearMonthOptionContainer(contxt);

    if (contxt.value != undefined && contxt.value != "") {
        if(contxt.yearMonthVal=="Year"){
            optionCont.val(showValue(getFloatValue(contxt.value)*12));
        }else{
            optionCont.val(showValue(contxt.value));
        }
    }
   /* newDiv.append(optionCont).append(errFeild);
    optionsContainer.append(newDiv).append(requird).append(selectedOption);*/
    newDiv.append(optionCont).append(requird).append(selectedOption).append(errFeild);
    optionsContainer.append(newDiv);
    return container.append(quesTextCont).append(optionsContainer);
}


function saveAndUpdateLoanAppForm(appUserDetailsParam,callBack){
	globalBinder();
	var LQBFileId=appUserDetails.loan.lqbFileId;
    if(!LQBFileId){
    	$.ajax({
    		url:"rest/application/applyloan",
    		type:"POST",
    		data:{"appFormData" : JSON.stringify(appUserDetailsParam)},
    		datatype : "application/json",
    		cache:false,
    		success:function(data){
    			if(data.status&&data.status==="Session Expired"){
                    var component=$("#right-panel");
                    if(component&&component.length==0){
                        component=$(document.body)
                    }
                    var content="<div class='rp-agent-dashboard float-left'><div class='center-text-session-expire'>"+data.message+"</div></div>"
                    $(component).html(content);
                }else{
        			appUserDetails=JSON.parse(data);
                    newfi.appUserDetails=data;
        			console.log('appUserDetails'+appUserDetails);
        			if(callBack)
        			callBack();
                }
    		},
    		error:function(erro){
    			showErrorToastMessage("error");
    		}
    		
    	});
    }else{
        showErrorToastMessage(applicationFormnotEditable);
    }
}



/*function showLoanAppFormContainer(formCompletionStatus){
	
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
}*/


///////////////////////NEW CODE ADDED ////////////////////////////


function paintSelectLoanTypeQuestion() {
    	//appUserDetails = {};
		//appUserDetails = JSON.parse(newfi.appUserDetails);
	
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
	}).html("Choose your loan purpose");

	//$('#app-right-panel').append(rateIcon).append(titleText);
	$('#app-right-panel').append(titleText);

	var wrapper = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var optionsContainer = $('<div>').attr({
		"class" : "ce-ques-options-container clearfix"
	});

	var option1 = $('<div>').attr({
		"class" : "cep-button-color ce-option"
	}).html("Refinance").on('click', function() {
		
		// In case when user is not coming from the customer engagement path 
		$('#loanType').text("Refinance");
		
		//
		
		//loanType.loanTypeCd = "REF";
		appUserDetails.loanType.id= "2";
		appUserDetails.loan.loanType.id = "2";
		appUserDetails.loanType.loanTypeCd= "REF";
		appUserDetails.loan.loanType.loanTypeCd = "REF";
		appUserDetails.loanType.description= "Refinance";
		appUserDetails.loan.loanType.description = "Refinance";
		
		paintPageBasedObLoanType(appUserDetails);
	});
	
	if (appUserDetails.loanType.description && appUserDetails.loanType.description =="Refinance"){
		option1.css("background","rgb(244, 117, 34)");
	}

	var option2 = $('<div>').attr({
		"class" : "cep-button-color ce-option"
	}).html("Buy a home").on('click', function() {
	
	console.log('setting value as purchase');
		
	
	// in case when user come directly not from customer engagement path 
	
	$('#loanType').text("Home Buyer");
	
	//
	
		//loanType.loanTypeCd = "PUR";
		appUserDetails.loanType.id= "1";
		appUserDetails.loan.loanType.id = "1";
		appUserDetails.loanType.loanTypeCd= "PUR";
		appUserDetails.loan.loanType.loanTypeCd = "PUR";
		appUserDetails.loanType.description= "Purchase";
		appUserDetails.loan.loanType.description = "Purchase";
		
		
		
		
		paintPageBasedObLoanType(appUserDetails);
	});

	if (appUserDetails.loanType.description && appUserDetails.loanType.description =="Purchase"){
		option2.css("background","rgb(244, 117, 34)");
	}
	
	optionsContainer.append(option1).append(option2);

	/*var question = $('<div>').attr({
				"class" : "ce-ques-text"
			})
			.html("Why Pay thousands more to use a loan officer ?<br/>With newfi, saving weeks of headache and thousands of dollars is easy.");*/

	//wrapper.append(optionsContainer).append(question);
	wrapper.append(optionsContainer);

	$('#app-right-panel').append(wrapper);
}
function paintPageBasedObLoanType(appUserDetails){

    if(appUserDetails.loanType.loanTypeCd == "PUR"){
        paintBuyHomeContainer(appUserDetails);
    }else if(appUserDetails.loanType.loanTypeCd == "REF"){
        paintRefinanceMainContainer();
    }
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
		"text" : "Lower my monthly payment",
		"onselect" : paintRefinanceStep2,
		"value" : "REFLMP"
	}, {
		"text" : "Pay off my mortgage faster",
		"onselect" : paintRefinanceStep1a,
		"value" : "REFMF"
	}, {
		"text" : "Take cash out of my home",
		"onselect" : paintRefinanceStep1b,
		"value" : "REFCO"
	} ];

	var quesCont = getMutipleChoiceQuestion(quesText, options,"refinanceOption");

	
	
	
	
	
	
	/*
	 * $("#progressBaarId_1").addClass('ce-lp-in-progress');
	 * $('#stepNoId_1').html("1");
	 */

	$('#app-right-panel').html(quesCont);
}






function paintRefinanceStep2() {

if(appUserDetails.loanType.loanTypeCd =="PUR"){
	paintCustomerApplicationPurchasePageStep1a();
	return;
}
	
appProgressBaar(2);
quesContxts = [];
stages = 2;
//progressBaar(2);

$('#app-right-panel').html('');
var questions = [
                {
                    "type": "desc",
                    "text": "What is your current mortgage balance?",
                    "name": "currentMortgageBalance",
                    "selected": appUserDetails.refinancedetails.currentMortgageBalance
                }];

		for(var i=0;i<questions.length;i++){
			var question=questions[i];
			var contxt=getQuestionContextCEP(question,$('#app-right-panel'));
			contxt.drawQuestion();
			
			quesContxts.push(contxt);
		}
		
		
		var lqbFileId = checkLqbFileId();
		if(lqbFileId){
			buttonText = "Next";
		}
		
		var saveAndContinueButton = $('<div>').attr({
		    "class": "cep-button-color ce-save-btn"
		}).html(buttonText).bind('click',{'contxt':contxt}, function(event) {
			
			
			if(this.innerText!="Next"){
				refinancedetails.currentMortgageBalance = $('input[name="currentMortgageBalance"]').val();
				 appUserDetails.refinancedetails = refinancedetails;
				var isSuccess=validateInput( $('input[name="currentMortgageBalance"]'),refinancedetails.currentMortgageBalance ,message);
					if(isSuccess){
						 saveAndUpdateLoanAppForm(appUserDetails ,paintRefinanceStep3);
						
					}else{
						return false;
					}
			  }else{
				  paintRefinanceStep3();
			}
			
			//paintCustomerApplicationPageStep1a();
		   });
		
		
	$('#app-right-panel').append(saveAndContinueButton);
}


function checkLqbFileId(){
    buttonText = "Save & continue";
	var LQBFileIdPresent = false;
	var LQBFileId=appUserDetails.loan.lqbFileId;
    if(LQBFileId){
    	LQBFileIdPresent = true;
    }
    return LQBFileIdPresent;
}


function paintRefinanceStep1b() {
	var quesTxt = "How much cash do you want to take out?";
	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2, "cashTakeOut");
	$('#app-right-panel').html(quesCont);
}

function paintRefinanceStep3() {
//stages = 3;
//progressBaar(3);
	quesContxts = {};
	
	$('#app-right-panel').html("");
	
    var questions = [
                     {
                         "type": "desc",
                         "text": "What is your current mortgage payment?",
                         "name": "currentMortgagePayment",
                         "selected": appUserDetails.refinancedetails.currentMortgagePayment
                     },
                     {
                         "type": "yesno",
                         "text": "Does the payment entered above include property taxes and/or homeowners insurance?",
                         "name": "includeTaxes",
                         "selected":"",
                         "options": [
                             {
                                 "text": "Yes"
                             },
                             {
                                 "text": "No"
                             }
                         ]
                    },
                    {
                        "type": "yearMonth",
                        "text": "How much are your property taxes?",
                        "name": "propertyTaxesPaid",
                        "value": ""
                    }, {
                        "type": "yearMonth",
                        "text": "How much is your homeowners insurance?",
                        "name": "annualHomeownersInsurance",
                        "value": ""
                    }
                 ];
    
    for(var i=0;i<questions.length;i++){
		var question=questions[i];
		var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails.refinancedetails);
		contxt.drawQuestion();
		
		quesContxts[question.name]=contxt;
	}
	
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    
    
    var saveAndContinueButton = $('<div>').attr({
	    "class": "cep-button-color ce-save-btn"
	}).html(buttonText).on('click', function() {
		
			if(this.innerText != next){
			    refinancedetails.currentMortgagePayment = quesContxts["currentMortgagePayment"].value;//$('input[name="currentMortgagePayment"]').val();		  
				refinancedetails.includeTaxes = quesContxts["includeTaxes"].value;//quesContxts[1].getValuesForDB();
				
				propertyTypeMaster.propertyTaxesPaid = quesContxts["propertyTaxesPaid"].value;//$('input[name="annualPropertyTaxes"]').val();
				propertyTypeMaster.propertyInsuranceCost = quesContxts["annualHomeownersInsurance"].value;//$('input[name="annualHomeownersInsurance"]').val();
	            propertyTypeMaster.propTaxMonthlyOryearly = quesContxts["propertyTaxesPaid"].yearMonthVal;//$('input[name="annualHomeownersInsurance"]').val();
	            propertyTypeMaster.propInsMonthlyOryearly = quesContxts["annualHomeownersInsurance"].yearMonthVal;//$('input[name="annualHomeownersInsurance"]').val();
	             var questionOne=validateInput($('input[name="currentMortgagePayment"]'),refinancedetails.currentMortgagePayment ,message);
	             var questionTwo=validateInput($('input[name="propertyTaxesPaid"]'),propertyTypeMaster.propertyTaxesPaid,message);
	             var questionThree=validateInput($('input[name="annualHomeownersInsurance"]'),propertyTypeMaster.propertyInsuranceCost,message);
	            
	             if(!questionOne){
	            	 return false;
	             } 
	             if(!questionTwo){
	            	 return false;
	             } 
	             if(!questionThree){
	            	 return false;
	             } 
	             
	             if(quesContxts["includeTaxes"].value==null||quesContxts["includeTaxes"].value==""){
	            	 showErrorToastMessage(yesyNoErrorMessage);
	            	 return false;
	             }
			    appUserDetails.refinancedetails=refinancedetails;
			    appUserDetails.propertyTypeMaster=propertyTypeMaster;
			    saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep1a());
			    //paintCustomerApplicationPageStep1a();
		    }else{
		    	paintCustomerApplicationPageStep1a();
		    }
		
	      });
	
    $('#app-right-panel').append(saveAndContinueButton);
}


function paintRefinanceStep1a() {

	var quesTxt = "How many years are left on your mortgage?";

	var quesCont = getTextQuestion(quesTxt, paintRefinanceStep2,"mortgageyearsleft");

	$('#app-right-panel').html(quesCont);
}





function getQuestionContextCEP(question,parentContainer,valueset){
	var contxt={
			type: question.type,
	        text: question.text,
	        name: question.name,
	        options:question.options,
	        container :undefined,
	        childContainers:{},
	        childContexts:{},
	        value:question.selected,
            yearMonthVal:"",
            valueset:valueset,
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
	        	if(value==="Yes"||value===true){
	        		return "Yes";
	        	}else if(value==="No"||value===false){
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
	var errFeild=appendErrorMessage();
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
    }).on("load focus", function(e){
          
		if(contxt.name != 'zipCode' && contxt.name != 'mortgageyearsleft' && contxt.name != 'locationZipCode' && contxt.name != 'buyhomeZipPri' ){
			$('input[name='+contxt.name+']').maskMoney({
				thousands:',',
				decimal:'.',
				allowZero:true,
				prefix: '$',
			    precision:0,
			    allowNegative:false
			});
		}
		
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








function getTextQuestion(quesText, clickEvent, name) {

	var value ="";
	if(mapDbDataForFrontend(name))
	value = mapDbDataForFrontend(name);
	
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});
	var errFeild=appendErrorMessage();
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : name,
		"value" : value
	}).on("load focus", function(e){
          
		if(name != 'zipCode' && name != 'mortgageyearsleft'){
			$('input[name='+name+']').maskMoney({
				thousands:',',
				decimal:'.',
				allowZero:true,
				prefix: '$',
			    precision:0,
			    allowNegative:false
			});
		}
		
	});
	
	optionContainer.append(inputBox).append(errFeild);

	var saveBtn = $('<div>').attr({
		"class" : "cep-button-color ce-save-btn"
	}).html("Save & Continue").bind('click', {
		'clickEvent' : clickEvent,
		"name" : name
	}, function(event) {
		var key = event.data.name;
		console.log('key'+key);
		inputValue= $('input[name="' + key + '"]').val();
        var className=$('input[name="' + key + '"]');
		appUserDetails.refinancedetails[key]  = inputValue;
        var isSuccess=validateInput(className,$('input[name="' + key + '"]').val(),message);
        if(isSuccess){
        	event.data.clickEvent();
        }else{
        	return false;
        }
		//sessionStorage.refinaceData = JSON.stringify(refinanceTeaserRate);
       /* if(inputValue != undefined && inputValue != "" && inputValue != "$0"){
        	console.log("event.data.clickEvent");
        	event.data.clickEvent();
        }else{
        	showToastMessage("Please give awnsers of the questions");
        }*/
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
			"class" : "cep-button-color ce-option",
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
		if(options[i].value==appUserDetails.refinancedetails.refinanceOption){
			option.css("background","rgb(244, 117, 34)")
		}
		
		
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}


////////////Declaration and Government Question for Spouse///////






















function createLoan(appUserDetails, flag)
{
////alert('inside create loan method');
	//fixAndLoakYourRatePage(lqbData, appUserDetails);
	
$('#overlay-loader').show();
showOverleyMessage(overlayMessage);
$.ajax({
		url:"rest/application/createLoan",
		type:"POST",
		data:{"appFormData" : JSON.stringify(appUserDetails)},
		datatype : "application/json",
		cache:false,
		success:function(data){
		
           // alert('createLoan data is '+data)
			/*if(data==""){
                $('#center-panel-cont').html(getHeaderText("Sorry, We could not find suitable products for you! One of our Loan officers will get in touch with you"));
            }else{*/
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
               // alert('createLoan data is '+data)

                if(data=="error"){
                    showErrorToastMessage(applicationNotSubmitted);
                   }else{
                	   if(flag)
                		{
                		   changeSecondaryLeftPanel(3,true); 
                           hideCompleteYourProfile();               		  
                		}
                	   paintLockRate(ob, appUserDetails); 
                   }
                

            /*}*/
            $('#overlay-loader').hide();
            clearOverlayMessage();
		},
		error:function(erro){
			showErrorToastMessage(errorInCreateLoan);
			 $('#overlay-loader').hide();
		}
		
	});
}










function paintLockRate(lqbData, appUserDetails) {
  // alert('lqbData'+lqbData);
    fixAndLoakYourRatePage(lqbData, appUserDetails);
}
 


function mapDbDataForFrontend(key){

    switch(key){
        case "state":
            if(appUserDetails.user&&appUserDetails.user.customerDetail)
                return appUserDetails.user.customerDetail.addressState;
            break;
        case "city":
            if(appUserDetails.user&&appUserDetails.user.customerDetail)
                return appUserDetails.user.customerDetail.addressCity;
            break;
        case "zipCode":
            if(appUserDetails.user&&appUserDetails.user.customerDetail)
                return appUserDetails.user.customerDetail.addressZipCode;
            break;
        case "propState":
            if(appUserDetails.propertyTypeMaster)
                return appUserDetails.propertyTypeMaster.propState;
            break;
        case "propCity":
            if(appUserDetails.propertyTypeMaster)
                return appUserDetails.propertyTypeMaster.propCity;
            break;
        case "propZipCode":
            if(appUserDetails.propertyTypeMaster)
                return appUserDetails.propertyTypeMaster.homeZipCode;
            break; 
        case "propStreetAddress":
            if(appUserDetails.propertyTypeMaster)
                return appUserDetails.propertyTypeMaster.propStreetAddress;
            break; 
        case "startLivingTime":
            if(appUserDetails.user&&appUserDetails.user.customerDetail)
                return appUserDetails.user.customerDetail.livingSince;
            break;
        case "rentPerMonth":
            return appUserDetails.monthlyRent;
        case "isCoBorrower":
            return appUserDetails.isCoborrowerPresent;
        case "isSpouseCoBorrower":
            return appUserDetails.isSpouseOnLoan;
        case "coBorrowerName":
            if(appUserDetails.customerSpouseDetail)
                return appUserDetails.customerSpouseDetail.spouseName;
            break;
        case "coBorrowerLastName":
            if(appUserDetails.customerSpouseDetail)
                return appUserDetails.customerSpouseDetail.spouseLastName;
            break;
        case "coBorrowerStreetAddress":
            if(appUserDetails.customerSpouseDetail)
                return appUserDetails.customerSpouseDetail.streetAddress;
            break;
        case "coBorrowerState":
            if(appUserDetails.customerSpouseDetail)
                return appUserDetails.customerSpouseDetail.state;
            break;
        case "coBorrowerZipCode":
            if(appUserDetails.customerSpouseDetail)
                return appUserDetails.customerSpouseDetail.zip;
            break;
        case "coBorrowerCity":
            if(appUserDetails.customerSpouseDetail)
                return appUserDetails.customerSpouseDetail.city;
            break;
        case "isCityOrZipKnown":
            if(appUserDetails.purchaseDetails&&appUserDetails.purchaseDetails.buyhomeZipPri&&appUserDetails.purchaseDetails.buyhomeZipPri!="")
                return true;
            else
                return false;
        case "buyhomeZipPri":
            if(appUserDetails.purchaseDetails)
                return appUserDetails.purchaseDetails.buyhomeZipPri;
            break;
        case "isOutstandingJudgments":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.outstandingJudgments;
            break;
        case "isBankrupt":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.bankrupt;
            break;
        case "isPropertyForeclosed":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.propertyForeclosed;
            break;
        case "isLawsuit":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.lawsuit;
            break;
        case "isObligatedLoan":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.obligatedLoan;
            break;
        case "isFederalDebt":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.federalDebt;
            break;
        case "isObligatedToPayAlimony":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.obligatedToPayAlimony;
            break;
        case "isEndorser":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.endorser;
            break;
        case "isUSCitizen":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.uscitizen;
            break;
        case "isOccupyPrimaryResidence":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.occupyPrimaryResidence;
            break;
        case "isOwnershipInterestInProperty":
            if(appUserDetails.governmentquestion)
                return appUserDetails.governmentquestion.ownershipInterestInProperty;
            break;
        case  "propertyTaxesPaid":
            if(typeof(newfiObject)!=='undefined'){
                var val=appUserDetails.propertyTypeMaster.propertyTaxesPaid;
                return val
            }else{
                return refinanceTeaserRate.propertyTaxesPaid;
            }
            break;
        case "annualHomeownersInsurance":
            var val;
            if(typeof(newfiObject)!=='undefined'){
                if(appUserDetails.propertyTypeMaster)
                    val=appUserDetails.propertyTypeMaster.propertyInsuranceCost;
                return val
            }else{
                return refinanceTeaserRate.annualHomeownersInsurance;
            }
            break;
        case "propertyType":
            var val;
            if(typeof(newfiObject)!=='undefined'){
                if(appUserDetails.propertyTypeMaster)
                    val=appUserDetails.propertyTypeMaster.propertyTypeCd;
                return val
            }else{
                if(refinanceTeaserRate.propertyType)
                    return refinanceTeaserRate.propertyType;
                else if(buyHomeTeaserRate.propertyType)
                    return buyHomeTeaserRate.propertyType;
            }
            break;
        case "residenceType":
            var val;
            if(typeof(newfiObject)!=='undefined'){
                if(appUserDetails.propertyTypeMaster)
                    val=appUserDetails.propertyTypeMaster.propertyTypeCd;
                return val;
            }else{
                if(refinanceTeaserRate.residenceType)
                    return refinanceTeaserRate.residenceType;
                else if(buyHomeTeaserRate.residenceType)
                    return buyHomeTeaserRate.residenceType;
            }
            break;
        case "cashTakeOut":
            if(appUserDetails.refinancedetails)
                return appUserDetails.refinancedetails.cashTakeOut;
            break;
        case "mortgageyearsleft":
            if(appUserDetails.refinancedetails)
                return appUserDetails.refinancedetails.mortgageyearsleft;
            break;
            
            
       /* case "isDownPaymentBorrowed":
        	if(appUserDetails.governmentquestion)
            return appUserDetails.governmentquestion.isDownPaymentBorrowed;
        	break;*/
        case "typeOfPropertyOwned":
            return appUserDetails.governmentquestion.typeOfPropertyOwned;
            break;
          case "propertyTitleStatus":
            return appUserDetails.governmentquestion.propertyTitleStatus;
            break;
            
        return undefined; 
    }
}

function showAlert(message){
    alert(message);
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
		
		var inputCont = $('<div>').attr({
        	"class" : "app-options-cont"
        });
		
		var inputElement = $('<input>').attr({
			"name" : e.data.fieldName,
			"class" : "ce-input ce-input-add"
		});
		
		inputCont.append(inputElement);
		
		var numberOfInputs = inputField.parent().parent().children('input').size();
		
		if(numberOfInputs<3){
			inputField.parent().parent().append(inputCont);
			if(numberOfInputs >= 2){
				$(this).hide();
			}
			
			inputField.parent().parent().children('.app-options-cont').find('.remove-btn').remove();
            
            if(numberOfInputs > 0){
            	var removeBtn = $('<div>').attr({
            		"class" : "remove-btn"
            	}).html("-")
            	.bind('click',{"fieldName":fieldName},function(e){
            		var inputField = $('input[name="'+e.data.fieldName+'"]');
            		if(inputField.parent().parent().children('input').size()>1){
            			$(this).parent().remove();
            		}
            	});
            	inputField.parent().parent().children('.app-options-cont').append(removeBtn);
            }
		}
	});
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

function createTeaserRateObjectForPurchase(appUserDet){
    var ob;
    if(appUserDet){
        ob= {
            "purchaseDetails":{
                "livingSituation":appUserDet.purchaseDetails.livingSituation,
                "rentPerMonth":appUserDet.monthlyRent,
                "isTaxAndInsuranceInLoanAmt":appUserDet.purchaseDetails.isTaxAndInsuranceInLoanAmt,
                "housePrice":appUserDet.purchaseDetails.housePrice,
                "loanAmount":appUserDet.purchaseDetails.loanAmount,
                "zipCode":appUserDet.purchaseDetails.buyhomeZipPri
            },
            "loanType":appUserDet.loanType.loanTypeCd,
            "livingSituation":appUserDet.purchaseDetails.livingSituation,
            "rentPerMonth":appUserDet.monthlyRent,
            "homeWorthToday":appUserDet.purchaseDetails.housePrice,
            "currentMortgageBalance":appUserDet.purchaseDetails.loanAmount,
            "zipCode":appUserDet.purchaseDetails.buyhomeZipPri,
            "propertyInsuranceCost":appUserDet.propertyTypeMaster.propertyInsuranceCost,
            "propertyTaxesPaid":appUserDet.propertyTypeMaster.propertyTaxesPaid
        };
    }
    return ob;
}
function createTeaserRateObjectForRefinance(appUserDet){
    var ob;
    if(appUserDet){
        ob={
            "loanType":"REF",
            "refinanceOption":appUserDet.refinancedetails!=undefined?appUserDet.refinancedetails.refinanceOption:"",
            "yearLeftOnMortgage":appUserDet.refinancedetails!=undefined?appUserDet.refinancedetails.mortgageyearsleft:"",
            "currentMortgageBalance":appUserDet.refinancedetails!=undefined?appUserDet.refinancedetails.currentMortgageBalance:"",
            "currentMortgagePayment":appUserDet.refinancedetails!=undefined?appUserDet.refinancedetails.currentMortgagePayment:"",
            "isIncludeTaxes":appUserDet.refinancedetails!=undefined?appUserDet.refinancedetails.includeTaxes:"",
            "propertyTaxesPaid":appUserDet.propertyTypeMaster!=undefined?appUserDet.propertyTypeMaster.propertyTaxesPaid:"",
            "annualHomeownersInsurance":appUserDet.propertyTypeMaster!=undefined?appUserDet.propertyTypeMaster.propertyInsuranceCost:"",
            "homeWorthToday":appUserDet.propertyTypeMaster!=undefined?appUserDet.propertyTypeMaster.homeWorthToday:"",
            "zipCode":appUserDet.propertyTypeMaster!=undefined?appUserDet.propertyTypeMaster.homeZipCode:""
        };
    }
    return ob;
}
function paintTeaserRatePageBasedOnLoanType(appUserDet){
    if(appUserDet.loanType.description=="Purchase"){
        var parentContainer=$('#center-panel-cont');
        paintBuyHomeSeeTeaserRate(parentContainer,createTeaserRateObjectForPurchase(appUserDet),true);
    }else{
        var parentContainer=$('#center-panel-cont');
        paintRefinanceSeeRates(parentContainer,createTeaserRateObjectForRefinance(appUserDet),true);
    }
}
function modifiyLockRateLoanAmt(loanAmount,purchaseAmount) {
    $('#overlay-loader').show();
    loanAmount = getFloatValue(loanAmount);  
    purchaseAmount = getFloatValue(purchaseAmount); 
    if (appUserDetails.loanType.description && appUserDetails.loanType.description =="Purchase"){
        appUserDetails.purchaseDetails.loanAmount=loanAmount-purchaseAmount;
        appUserDetails.purchaseDetails.housePrice=loanAmount;
        appUserDetails.propertyTypeMaster.propertyTaxesPaid = $('#calTaxID2').val();
        appUserDetails.propertyTypeMaster.propertyInsuranceCost = $('#CalInsuranceID2').val();
    }else{
        var parentContainer=$('#center-panel-cont');
        appUserDetails.refinancedetails.currentMortgageBalance=loanAmount;
        appUserDetails.refinancedetails.cashTakeOut=purchaseAmount;
        appUserDetails.propertyTypeMaster.propertyTaxesPaid = $('#calTaxID2').val();
        appUserDetails.propertyTypeMaster.propertyInsuranceCost = $('#CalInsuranceID2').val();
    }

    $.ajax({
        url:"rest/application/changeLoanAmount",
        type:"POST",
        data:{"appFormData" : JSON.stringify(appUserDetails)},
        datatype : "application/json",
        cache:false,
        success:function(data){
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
           // alert('createLoan data is '+data)
            paintLockRate(ob, appUserDetails);
             $('#overlay-loader').hide();
        },
        error:function(erro){
        	showErrorToastMessage(errorInCreateLoan);
             $('#overlay-loader').hide();
        }
        
    });
}

$(document).on('click',function(){
	$('.app-dropdown-cont').hide();
});

function validateCoBorowerInformation(){
	var question=validateInput($('input[name="coBorrowerName"]'),$('input[name="coBorrowerName"]').val(),message);
	var question1=validateInput($('input[name="coBorrowerLastName"]'),$('input[name="coBorrowerLastName"]').val(),message);
	var question2=validateInput($('input[name="coBorrowerStreetAddress"]'),$('input[name="coBorrowerStreetAddress"]').val(),message);
	var question3=validateInput($('input[name="coBorrowerState"]'),$('input[name="coBorrowerState"]').val(),message);
	var question4=validateInput($('input[name="coBorrowerCity"]'),$('input[name="coBorrowerCity"]').val(),message);
	var question5=validateInput($('input[name="coBorrowerZipCode"]'),$('input[name="coBorrowerZipCode"]').val(),message);
	if(!question){
		return false;
	}
	if(!question1){
		return false;
	
	}
    if(!question2){
		return false;
		
	}
    if(!question3){
    	showErrorToastMessage(yesyNoErrorMessage);
		return false;
		
	}
    if(!question4){
		return false;
		
	}
    if(!question5){
		return false;
		
	}
    else{
		if($('input[name="coBorrowerZipCode"]').val().length>5||$('input[name="coBorrowerZipCode"]').val().length<5){
			$('input[name="coBorrowerZipCode"]').next('.err-msg').html(zipCodeMessage).show();
			$('input[name="coBorrowerZipCode"]').addClass('ce-err-input').show();
    		 return false;
		}
	}
    return true;
}

function getContextApplicationPercentageQues(contxt) {
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
    var errFeild=appendErrorMessage();
	var optionCont = $('<input>').attr({
	    "class": "app-input dwn-val float-left",
	    "name": contxt.name,
	    "value":showValue(contxt.value)
	}).on("load focus", function(e){
		$('input[name='+contxt.name+']').maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});
		/* this is the piece of code to retrict user put special charector*/
		restrictSpecialChar(contxt.name);
	});
	var percentageComp = $('<input>').attr({
	    "class": "app-input dwn-percentage"
	}).attr('maxlength','2');;
    if (contxt.value != undefined) {
        optionCont.val(contxt.value);
    }
    optionCont.bind("keyup",{"valComp":optionCont,"percentComp":percentageComp,"val":true,"contxt":contxt},percentageUpdateEventListener)
    percentageComp.bind("keyup",{"valComp":optionCont,"percentComp":percentageComp,"percentage":true,"contxt":contxt},
    	function(e){
    		this.value = this.value.replace(/[^0-9]/g,'')
    		percentageUpdateEventListener(e);
    	})

    optionsContainer.append(optionCont).append(percentageComp).append(errFeild);
    $(optionCont).trigger("keyup")
    return container.append(quesTextCont).append(optionsContainer);
}
function getpurchaseValue(){
    if($("#secondInput").length>0){
        return $('#secondInput').val();
    }else if($('input[name="homeWorthToday"]').length>0){
        return $('input[name="homeWorthToday"]').val();
    }else if($('input[name="housePrice"]').length>0){
        return $('input[name="housePrice"]').val();
    }
}
function percentageUpdateEventListener(e){
    var valComp = e.data.valComp;
    var percentComp = e.data.percentComp;
    var purchaseVal=getpurchaseValue();
    if(purchaseVal&&getFloatValue(purchaseVal)!=0){
        purchaseVal=getFloatValue(purchaseVal);
        var val=e.data.val;
        var percentage=e.data.percentage;
        if(val){
        	var amt=getFloatValue(valComp.val());
            var perVal=(amt/purchaseVal)*100;
            if(percentComp)
                percentComp.val(parseFloat(perVal).toFixed(0));
        }else if(percentage){
        	var percent=getFloatValue(percentComp.val());
            var valu=(purchaseVal*percent)/100;
            if(valComp)
                valComp.val(showValue(valu));
        }
    }
    var ctx=e.data.contxt;
	ctx.value=valComp.val();
}