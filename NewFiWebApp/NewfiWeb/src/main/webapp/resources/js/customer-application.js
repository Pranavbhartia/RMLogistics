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


var formCompletionStatus = 1;






var applicationItemsList = [ 
                             {
							    "text":"My Priority",
	                            "onselect" : paintSelectLoanTypeQuestion
	                        },
	                        /*{
							    "text":"My Money",
	                            "onselect" : ""
	                        },*/
	                        {
							    "text":"Home Information",
	                            "onselect" : paintHomeInfoPage
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
	
	appUserDetails = JSON.parse(newfi.appUserDetails);
	

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
       // alert('option value is   '+ option.value);
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
                if(option.value==val)
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
    }).html("Select One").on('click', function() {
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
    }).on("keyup", function(e){
          	
        if (question.name != 'zipCode' && question.name != 'mortgageyearsleft' && question.name != 'locationZipCode' && question.name != 'buyhomeZipPri' && question.name != 'city' && question.name != 'state' && question.name != 'startLivingTime' && question.name != 'spouseName' && question.name != 'phoneNumber'&& question.name != 'insuranceProvider' && question.name != 'ssn' && question.name != 'birthday') {
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
		
	});

    if (question.value != undefined) {
        optionCont.val(question.value);
    }

    quesTextCont.append(requird);
    optionsContainer.append(optionCont);

    return container.append(quesTextCont).append(optionsContainer);
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
    if(purchase){
        paintCustomerApplicationPurchasePageStep1a();
    }else{
        paintCustomerApplicationPageStep1a();
    }
}

function paintCustomerApplicationPageStep1a() {
    
	
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
    		
    		
    		////alert(JSON.stringify(appUserDetails));
    		saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep1b());
    		
        	        	
        }else{
        	showToastMessage("Please give answer of the questions");
        }
   	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
    addStateCityZipLookUp();
}

function addStateCityZipLookUp(){
synchronousAjaxRequest("rest/states/", "GET", "json", "", stateListCallBack);
    
    var stateDropDownWrapper = $('<div>').attr({
    	"id" : "state-dropdown-wrapper",
    	"class" : "state-dropdown-wrapper hide"
    });
    
    $('input[name="state"]').after(stateDropDownWrapper);
    
    $('input[name="state"]').attr("id","stateId").addClass('prof-form-input-statedropdown').bind('click',function(e){
		e.stopPropagation();
		if($('#state-dropdown-wrapper').css("display") == "none"){
			appendStateDropDown('state-dropdown-wrapper',stateList);
			toggleStateDropDown();
		}else{
			toggleStateDropDown();
		}
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
        type: "desc",
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
    	
    	
    	
    	
    	if(propertyTypeCd != undefined && propertyTypeCd != "" && residenceTypeCd != undefined && residenceTypeCd != ""  && propertyTaxesPaid != undefined && propertyTaxesPaid != ""  && propertyInsuranceProvider != undefined && propertyInsuranceProvider != "" && propertyInsuranceCost != undefined && propertyInsuranceCost != ""  && propertyPurchaseYear != undefined && propertyPurchaseYear != ""  ){
    		
    		propertyTypeMaster.propertyTypeCd = propertyTypeCd;
        	propertyTypeMaster.residenceTypeCd = residenceTypeCd;
        	propertyTypeMaster.propertyTaxesPaid = propertyTaxesPaid;
        	propertyTypeMaster.propertyInsuranceProvider = propertyInsuranceProvider;
        	propertyTypeMaster.propertyInsuranceCost = propertyInsuranceCost;
        	propertyTypeMaster.propertyPurchaseYear = propertyPurchaseYear;
          //	propertyTypeMaster.homeWorthToday = homeWorthToday ;
        	  	
        	appUserDetails.propertyTypeMaster = propertyTypeMaster;
        	
        	
        	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
        	//saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
        	saveAndUpdateLoanAppForm(appUserDetails ,paintSecondPropertyStep());
    		
    		//paintCustomerApplicationPageStep2();
    	}else{
    		showToastMessage("Please give answer of the questions");
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
                         "text": "Do you have a second mortgage on this property? ?",
                         "name": "secondMortgage",
                         "options": [
                             {
                                 "text": "Yes",
                                 "addQuestions": [
                                     {
                                         "type": "desc",
                                         "text": "What is the current balance for this additional mortgage??",
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
//alert('getQuestionContext');

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
	            	//ob.container = getApplicationSelectQues(ob);
	            	
	            } else if (ob.type == "yesno") {
	          // alert('inside getQuestionContext yes no');
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
	return contxt;
}



var quesContxts=[];

//TODO-try nested yesno question
function paintCustomerApplicationPageStep2() {
	
	
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
    		    			appUserDetails.isSpouseOnLoan =false;
    		    			appUserDetails.spouseName  = "";
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
	    		 appUserDetails.spouseName  = "";
    		 }
	    	
	    	
	    	// this is the condition when spouseName is in the loan
            if(!appUserDetails.customerSpouseDetail)
                appUserDetails.customerSpouseDetail={};
	    	if( quesContxts[0].childContexts.Yes !=  undefined && quesContxts[0].childContexts.Yes[0].childContexts.Yes != undefined){
	    		appUserDetails.isSpouseOnLoan = true;
	    		appUserDetails.spouseName = quesContxts[0].childContexts.Yes[0].childContexts.Yes[0].value;
	    		
	    		appUserDetails.customerSpouseDetail.spouseName = quesContxts[0].childContexts.Yes[0].childContexts.Yes[0].value;
	    	
	    	}else{
	    		appUserDetails.customerSpouseDetail.spouseName  = "";
	    	}
	    	
	    	
	    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	////alert(JSON.stringify(appUserDetails));
	    
	    	saveAndUpdateLoanAppForm(appUserDetails,paintMyIncome());
	    	
	    	
	    	
    	}else{
    		showToastMessage("Please give the answers of the questions");
    	}
         ////alert(JSON.stringify(appUserDetails));
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
          
		if(contxt.name != 'zipCode' && contxt.name != 'mortgageyearsleft' && contxt.name != 'locationZipCode' && contxt.name != 'buyhomeZipPri'  && contxt.name != 'city' && contxt.name != 'state' && contxt.name != 'startLivingTime' && contxt.name != 'spouseName'){
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

    optionsContainer.append(optionCont);

    return container.append(quesTextCont).append(optionsContainer);
}



function incomesSelectALLThatApply() {

	
	var quesTxt = "Select all that apply";
    var selfEmployedData={};

   //  alert('appUserDetails'+appUserDetails);
    if(appUserDetails)
        selfEmployedData={"selected":appUserDetails.isselfEmployed,"data":appUserDetails.selfEmployedIncome};
  //alert('selfEmployedData'+selfEmployedData);

    var employedData={};
    if(appUserDetails && appUserDetails.customerEmploymentIncome)
        employedData={"selected":true,"data":appUserDetails.customerEmploymentIncome};
    var ssiData={};
    if(appUserDetails)
        ssiData={"selected":appUserDetails.isssIncomeOrDisability,"data":appUserDetails.ssDisabilityIncome};
    var prData={};
    if(appUserDetails)
        prData={"selected":appUserDetails.ispensionOrRetirement,"data":appUserDetails.monthlyPension};

	var options = [ {
		"text" : "Employed",
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
	}, {
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
	} ];
	
	    var incomesSelectALLThatApplyDiv = paintCustomerApplicationPageStep3(quesTxt, options, name);
        $('#app-right-panel').append(incomesSelectALLThatApplyDiv);
        for(var i=0;i<options.length;i++){
            var option=options[i];
            if(option.onselect){
                option.onselect(option.value,option.data);
            }
        }
    return incomesSelectALLThatApplyDiv;
}
 
function paintMyIncome() {
    
    appProgressBaar(4);
    $('#app-right-panel').html('');
    var incomesSelectALLThatApplyDiv = incomesSelectALLThatApply();
    var questcontainer = $('#app-right-panel');
 	
	console.log('purchase'+purchase);
	if(purchase == true){
		
		var questionsContainer10 = paintSaleOfCurrentHome();
		  questcontainer.append(questionsContainer10);
    }
   
   
   
    var saveAndContinueButton = $('<div>').attr({
        "class": "ce-save-btn"
    }).html("Save & continue").on('click', function(event) {
    
    
    
    var  customerEmploymentIncome = [];
     
     $("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
      customerEmploymentIncome1 = {};
     
      id = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="customerEmploymentIncomeId"]').val();
      if(id ==""){
    	  id = undefined;
      }
      EmployedIncomePreTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="beforeTax"]').val();
      EmployedAt = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="workPlace"]').val();
      EmployedSince = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="startWorking"]').val();
     
      customerEmploymentIncome1.id  = id;
      customerEmploymentIncome1.employedIncomePreTax = EmployedIncomePreTax;
      customerEmploymentIncome1.employedAt = EmployedAt;
      customerEmploymentIncome1.employedSince = EmployedSince;
      var termp = {};
      termp.customerEmploymentIncome = customerEmploymentIncome1;
      
      customerEmploymentIncome.push(termp);
     });
     
    // alert('customerEmploymentIncome.size');
   
   if(customerEmploymentIncome&&customerEmploymentIncome.length>0)
   appUserDetails.customerEmploymentIncome=customerEmploymentIncome;
   
    			selfEmployedIncome = $('input[name="selfEmployed"]').val();
		        
		        ssDisabilityIncome = $('input[name="disability"]').val();
				
		        monthlyPension = $('input[name="pension"]').val();

		
				if(monthlyPension != "" && monthlyPension != undefined){
					appUserDetails.ispensionOrRetirement= true;
					appUserDetails.monthlyPension =monthlyPension;
				}else{
                    appUserDetails.ispensionOrRetirement= false;
                    appUserDetails.monthlyPension ="";
                }
				
				
				if(selfEmployedIncome != "" && selfEmployedIncome != undefined){
					
					appUserDetails.isselfEmployed = true;
					appUserDetails.selfEmployedIncome =selfEmployedIncome;
				}else{
                    appUserDetails.isselfEmployed = false;
                    appUserDetails.selfEmployedIncome ="";
                }
				
				if(ssDisabilityIncome !="" && ssDisabilityIncome != undefined){
					
					appUserDetails.isssIncomeOrDisability=true;
					appUserDetails.ssDisabilityIncome = ssDisabilityIncome;
				}else{
                    appUserDetails.isssIncomeOrDisability=false;
                    appUserDetails.ssDisabilityIncome = "";
                }
				
				
				//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
				
			
			
       
      if(purchase == true){
            
    	  homeListPrice = $('input[name="homelistprice"]').val();
    	  homeMortgageBalance = $('input[name="homemortgagebalance"]').val();
    	  investmentInHome = $('input[name="inverstInPurchase"]').val();
	        
	        
	        appUserDetails.propertytypemaster.currentHomePrice=homeListPrice;
	    	appUserDetails.propertytypemaster.currentHomeMortgageBalance=homeMortgageBalance;
	   	    appUserDetails.propertytypemaster.newHomeBudgetFromsale=investmentInHome;
	        
	        
	        appUserDetails.customerBankAccountDetails = [];
	        appUserDetails.customerOtherAccountDetails = [];
	        appUserDetails.customerRetirementAccountDetails = [];
	        
	        var assets=$('.asset-ques-wrapper').find('.app-account-wrapper');
	        
	        var bankContainer=assets[0];
	        var retirementContainer=assets[1];
	        var otherContainer=assets[2];
	        
	        if($(bankContainer).find('.app-option-checked')){
	        	appUserDetails.customerBankAccountDetails=getAccountValues(bankContainer,"customerBankAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
	        }
			if($(retirementContainer).find('.app-option-checked')){
				appUserDetails.customerRetirementAccountDetails=getAccountValues(retirementContainer,"customerRetirementAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
		    }
			if($(otherContainer).find('.app-option-checked')){
				appUserDetails.customerOtherAccountDetails=getAccountValues(otherContainer,"customerOtherAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
			}
			
			
	    }
            
        
       
        if (appUserDetails.isSpouseOnLoan == true) {
            saveAndUpdateLoanAppForm(appUserDetails, paintMySpouseIncome());
        } else {
            saveAndUpdateLoanAppForm(appUserDetails, paintCustomerApplicationPageStep4a());
        }
        
    });
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
            for(var i=0;i<incomes.length;i++){
                var income=incomes[i].customerEmploymentIncome;
                    var quesTxt = "About how much do you make a year";
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
    		var quesTxt = "About how much do you make a year";
    		var quesCont = getMultiTextQuestion(quesTxt);
    		$('#ce-option_' + divId).prepend(quesCont);	
    	}
    	$('#ce-option_' + divId).toggle();
    }

    putCurrencyFormat("beforeTax");
}

function getMultiTextQuestion(quesText,value) {
	
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
	
	quesTextCont1.append(inputBox1);

	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");
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
	quesTextCont2.append(inputBox2);

	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Working ?");
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
	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont0).append(quesTextCont1).append(quesTextCont2).append(quesTextCont3);

	 container.append(quesTextCont).append(optionContainer); 
     return wrapper.append(container);
}

$('body').on('focus',"input[name='startWorking'], input[name='startLivingTime'] ,input[name='purchaseTime'],input[name='spouseStartWorking']", function(){
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

$('body').on('focus',"input[name='birthday']",function(){
			
    	$(this).datepicker({
			orientation : "top auto",
			autoclose : true,
			maxDate: 0
		});
  
});

$('body').on('focus',"input[name='ssn']",function(){
	
	$(this).mask("***-**-****");
		
});



function paintRefinanceSelfEmployed(divId,value) {
    var flag=true;
    if(value&&!value.selected)
        flag=false;
    //appUserDetails.employed ="true";
    if(flag){
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
}

function paintRefinanceDisability(divId,value) {
    var flag=true;
    if(value&&!value.selected)
        flag=false;
    //appUserDetails.employed ="true";
    if(flag){
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
}

function paintRefinancePension(divId,value) {
    var flag=true;
    if(value&&!value.selected)
        flag=false;
    //appUserDetails.employed ="true";
    if(flag){
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
}




//////////////////This is new section for Spouse Income Details /////////






















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
function addRemoveButtons(element){
    $(element).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
    var mainContainerId = $(element).parent().attr("id");
    var removeAccBtn = $('<div>').attr({
        "class" : "add-btn remove-account-btn"
    }).html("Remove Income").bind('click',{"mainContainerId":mainContainerId},function(event){
        $(this).closest('.ce-option-ques-wrapper').remove();
        var parentDiv = $('#'+event.data.mainContainerId);
    
        if(parentDiv.children('.ce-option-ques-wrapper').length==1){
            parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
        }
    });
    $(element).parent().children('.ce-option-ques-wrapper').append(removeAccBtn);
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
			"name" : options[i].name
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
			var quesTxt = "About how much do you make a year";
    		var quesCont = getMultiTextQuestion(quesTxt);
			/*var containerToAppend = $(this).parent().find('.ce-option-ques-wrapper').wrap('<p/>').parent().html();
			$(this).parent().find('.ce-option-ques-wrapper').unwrap();*/
			$(this).before(quesCont);
			
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
    	isDownPaymentBorrowed = quesDeclarationContxts[7].value;
    	isEndorser =  quesDeclarationContxts[8].value;
    	isUSCitizen =  quesDeclarationContxts[9].value;
    	
    	
    	
    	 isOccupyPrimaryResidence =  quesDeclarationContxts[10].value;
    	 isOwnershipInterestInProperty =  quesDeclarationContxts[11].value;
    	
    	 typeOfPropertyOwned =  $('.app-options-cont[name="typeOfPropertyOwned"]').find('.app-option-selected').data();
     	if(typeOfPropertyOwned != undefined)
     		typeOfPropertyOwned =  $('.app-options-cont[name="typeOfPropertyOwned"]').find('.app-option-selected').data().value;
     	
     	 propertyTitleStatus =  $('.app-options-cont[name="propertyTitleStatus"]').find('.app-option-selected').data();
     	if(propertyTitleStatus != undefined)
     		propertyTitleStatus =  $('.app-options-cont[name="propertyTitleStatus"]').find('.app-option-selected').data().value;
 	    	
 	    
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
 		 }else if(isOwnershipInterestInProperty =="No"){
 			governmentquestion.isOwnershipInterestInProperty = false;
 		 }else{
 			governmentquestion.isOwnershipInterestInProperty = null;
 		 }
    	 
    	 appUserDetails.governmentquestion =governmentquestion;
    	 
    	 //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    	// //alert(JSON.stringify(appUserDetails));
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
		"text" : "No thank you. Let's move on",
		"name" : "bypassoptional",
		"value" : 0
	}];
	
	//alert('name'+name)
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
	    	
           dateOfBirth = $('input[name="birthday"]').val();
	    	ethnicity =  $('.app-options-cont[name="ethnicity"]').find('.app-option-selected').data().value;
	    	race =  $('.app-options-cont[name="race"]').find('.app-option-selected').data().value;
	    	sex =  $('.app-options-cont[name="sex"]').find('.app-option-selected').data().value;
	    	
	    	skipOptionalQuestion = $('.ce-option-checkbox').hasClass("ce-option-checked");
	    	
	    	governmentquestion.ethnicity = ethnicity;
	    	governmentquestion.race = race;
	    	governmentquestion.sex =sex;
	    	governmentquestion.skipOptionalQuestion=skipOptionalQuestion;
	    	
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
    		////alert(JSON.stringify(customerDetail));
    		
    		
    /////alert(JSON.stringify(appUserDetails));
    		
    		
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
    		////alert(JSON.stringify(customerDetail));
    		
    		
    /////alert(JSON.stringify(appUserDetails));
    		
    		
    	
				 saveAndUpdateLoanAppForm(appUserDetails,applicationFormSumbit(appUserDetails));
				
    		
    		
    		
    		
    		
    	}else{
    		showToastMessage("Please give the answers of the questions");
    	}
    	
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer)
        .append(saveAndContinueButton);
}






function applicationFormSumbit(appUserDetails){
	//paintLockRate(lqbData, appUserDetails);
	createLoan(appUserDetails);
	changeSecondaryLeftPanel(3,true);
	//saveUserAndLockRate(appUserDetails) ;
	//changeSecondaryLeftPanel(3);
}






var lqbData=[
    {
        "loanDuration": "sample",
        "loanNumber": "D2015040065"
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
        success: function(data) {
            $('#overlay-loader').hide();
            //TO:DO pass the data (json)which is coming from the controller
            //paintLockRate(data,appUserDetails);
            //paintLockRate(JSON.parse(data), appUserDetails);
            paintLockRate(tempdata, appUserDetails);
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
	appUserDetails.loanAppFormCompletionStatus=contxtHolder.getPercentageForStep(num);
}

function putCurrencyFormat(name){
	
	$("input[name="+name+"]").keydown(function() {
    	$("input[name="+name+"]").maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
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
			
			appUserDetails=data;
			console.log('appUserDetails'+appUserDetails);
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
		paintPageBasedObLoanType();
	});
	
	if (appUserDetails.loanType.description && appUserDetails.loanType.description =="Refinance"){
		option1.css("background","rgb(247, 72, 31)");
	}

	var option2 = $('<div>').attr({
		"class" : "ce-option"
	}).html("Buy a home").on('click', function() {
		loanType.loanTypeCd = "PUR";
		appUserDetails.loanType= loanType;
		paintPageBasedObLoanType();
	});

	if (appUserDetails.loanType.description && appUserDetails.loanType.description =="Purchase"){
		option2.css("background","rgb(247, 72, 31)");
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
function paintPageBasedObLoanType(){
    if(appUserDetails.loanType.loanTypeCd == "PUR"){
        paintBuyHomeContainer();
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
		"text" : "Lower My Monthly Payment",
		"onselect" : paintRefinanceStep2,
		"value" : "REFLMP"
	}, {
		"text" : "Pay Off My Mortgage Faster",
		"onselect" : paintRefinanceStep1a,
		"value" : "REFMF"
	}, {
		"text" : "Take Cash Out of My Home",
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
                         "selected": appUserDetails.refinancedetails.currentMortgagePayment
                     },
                     {
                         "type": "yesno",
                         "text": "Does the payment entered above include property taxes and/or homeowner insuranace ?",
                         "name": "includeTaxes",
                         "selected":"",
                         "options": [
                             {
                                 "text": "Yes",
                                 "addQuestions": [
                                     {
                                         "type": "desc",
                                         "text": "How much your property taxes?",
                                         "name": "annualPropertyTaxes",
                                         "selected":appUserDetails.propertyTypeMaster.propertyTaxesPaid
                                     },
                                     {
                                         "type": "desc",
                                         "text": "How much your homeowners insurance ?",
                                         "name": "annualHomeownersInsurance",
                                         "selected": appUserDetails.propertyTypeMaster.propertyInsuranceCost
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
		var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails.refinancedetails);
		contxt.drawQuestion();
		
		quesContxts.push(contxt);
	}
	
    var saveAndContinueButton = $('<div>').attr({
	    "class": "ce-save-btn"
	}).html("Save & continue").on('click', function() {
		
			refinancedetails.currentMortgagePayment = $('input[name="currentMortgagePayment"]').val();		  
			refinancedetails.includeTaxes = quesContxts[1].getValuesForDB();
			
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

		appUserDetails.refinancedetails[key]  = inputValue;

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
		if(options[i].value==appUserDetails.refinancedetails.refinanceOption){
			option.css("background","rgb(247, 72, 31)")
		}
		
		
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}


////////////Declaration and Government Question for Spouse///////








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
		"text" : "No thank you. Let's move on",
		"name" : name,
		"value" : 0
	}];
	var quesCont = paintSpouseGovernmentMonitoringQuestions(quesHeaderTxt, options, name);

	$('#app-right-panel').append(quesCont);
    

    
    ///
    var questions = [{
        type: "select",
        text: "Ethnicity",
        name: "spouseEthnicity",
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
        name: "spouseRace",
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
        name: "spouseSex",
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
	    	
	    	ethnicity =  $('.app-options-cont[name="spouseEthnicity"]').find('.app-option-selected').data().value;
	    	race =  $('.app-options-cont[name="spouseRace"]').find('.app-option-selected').data().value;
	    	sex =  $('.app-options-cont[name="spouseSex"]').find('.app-option-selected').data().value;
	    	skipOptionalQuestion = $('.ce-option-checkbox').hasClass("ce-option-checked");
	    	
	    	spouseGovernmentQuestions.ethnicity = ethnicity;
	    	spouseGovernmentQuestions.race = race;
	    	spouseGovernmentQuestions.sex =sex;
	    	spouseGovernmentQuestions.skipOptionalQuestion=skipOptionalQuestion;	
	    	
	    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    	 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep5());
	    	//paintCustomerApplicationPageStep5();
	    });

	    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
}
 






function createLoan(appUserDetails)
{
////alert('inside create loan method');
 $('#overlay-loader').show();
$.ajax({
		url:"rest/application/createLoan",
		type:"POST",
		data:{"appFormData" : JSON.stringify(appUserDetails)},
		datatype : "application/json",
		success:function(data){
		
           // alert('createLoan data is '+data)
			paintLockRate(JSON.parse(data), appUserDetails);
			 $('#overlay-loader').hide();
		},
		error:function(erro){
			alert("error inside createLoan ");
			 $('#overlay-loader').hide();
		}
		
	});
}










function paintLockRate(lqbData, appUserDetails) {
  // alert('lqbData'+lqbData);

    fixAndLoakYourRatePage(lqbData, appUserDetails);
}
 

function mapDbDataForFrontend(key){
//alert('isside mapDbDataForFrontend');
    switch(key){
        case "state":
            return appUserDetails.user.customerDetail.addressState;
        case "city":
            return appUserDetails.user.customerDetail.addressCity;
        case "zipCode":
            return appUserDetails.user.customerDetail.addressZipCode;
        case "startLivingTime":
            return appUserDetails.user.customerDetail.livingSince;
        case "rentPerMonth":
            return appUserDetails.monthlyRent;
        case "isCityOrZipKnown":
            if(appUserDetails.purchaseDetails.buyhomeZipPri&&appUserDetails.purchaseDetails.buyhomeZipPri!="")
                return true;
            else
                return false;
        case "buyhomeZipPri":
            return appUserDetails.purchaseDetails.buyhomeZipPri;
        case "isOutstandingJudgments":
            return appUserDetails.governmentquestion.outstandingJudgments;
        case "isBankrupt":
            return appUserDetails.governmentquestion.bankrupt;
        case "isPropertyForeclosed":
            return appUserDetails.governmentquestion.propertyForeclosed;
        case "isLawsuit":
            return appUserDetails.governmentquestion.lawsuit;
        case "isObligatedLoan":
            return appUserDetails.governmentquestion.obligatedLoan;
        case "isFederalDebt":
            return appUserDetails.governmentquestion.federalDebt;
        case "isObligatedToPayAlimony":
            return appUserDetails.governmentquestion.obligatedToPayAlimony;
        case "isEndorser":
            return appUserDetails.governmentquestion.endorser;
        case "isUSCitizen":
            return appUserDetails.governmentquestion.uscitizen;
        case "isOccupyPrimaryResidence":
            return appUserDetails.governmentquestion.occupyPrimaryResidence;
        case "isOwnershipInterestInProperty":
            return appUserDetails.governmentquestion.ownershipInterestInProperty;
    /*    case "isDownPaymentBorrowed":
            return appUserDetails.governmentquestion.isDownPaymentBorrowed;
        case "typeOfPropertyOwned":
            return appUserDetails.governmentquestion.typeOfPropertyOwned;
          case "propertyTitleStatus":
            return appUserDetails.governmentquestion.propertyTitleStatus;*/
            
            
            
            
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
                "rentPerMonth":"$5,000",
                "isTaxAndInsuranceInLoanAmt":appUserDet.purchaseDetails.isTaxAndInsuranceInLoanAmt,
                "housePrice":appUserDet.purchaseDetails.housePrice,
                "loanAmount":appUserDet.purchaseDetails.loanAmount,
                "zipCode":appUserDet.purchaseDetails.buyhomeZipPri
            },
            "loanType":appUserDet.loanType.loanTypeCd,
            "livingSituation":appUserDet.purchaseDetails.livingSituation,
            "rentPerMonth":"$5,000",
            "homeWorthToday":appUserDet.purchaseDetails.housePrice,
            "currentMortgageBalance":"$280,000",
            "zipCode":appUserDet.purchaseDetails.buyhomeZipPri
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
            "propertyTaxesPaid":appUserDet.propertyTypeMaster!=undefined?appUserDet.refinancedetails.propertyTaxesPaid:"",
            "annualHomeownersInsurance":appUserDet.propertyTypeMaster!=undefined?appUserDet.refinancedetails.propertyInsuranceCost:"",
            "homeWorthToday":appUserDet.propertyTypeMaster!=undefined?appUserDet.refinancedetails.homeWorthToday:"",
            "zipCode":appUserDet.propertyTypeMaster!=undefined?appUserDet.refinancedetails.homeZipCode:""
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