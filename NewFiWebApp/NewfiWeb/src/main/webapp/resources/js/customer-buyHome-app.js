///////////////This code to go in BuyHomeApplication /////////////////



buyHome = {};

appUserDetails.buyHome = buyHome;
quesContxts = [];

function paintBuyHomeContainer() {

	$('#app-right-panel').html('');
	var wrapper = $('<div>').attr({
		"class" : "ce-refinance-wrapper clearfix"
	});

//	var leftPanel = getBuyHomeLeftPanel();

	var centerPanel = $('<div>').attr({
		"id" : "ce-refinance-cp",
		"class" : "ce-cp float-right"
	});

	wrapper.append(centerPanel);
	$('#app-right-panel').append(wrapper);
	purchase = true;
	paintBuyHomeQuest();
}




function paintBuyHomeQuest() {
	//homeProgressBaar(1);
	
	
	appUserDetails = {};
	
	appUserDetails = JSON.parse(newfi.appUserDetails);
	

	user.id = newfi.user.id;
	customerDetail.id = newfi.user.customerDetail.id;
	
	loan.id = newfi.user.defaultLoanId;
	
	purchaseDetails.id = appUserDetails.purchaseDetails.id;
	
	customerSpouseDetail.id = newfi.user.customerDetail.customerSpouseDetail.id;

	
	formCompletionStatus = newfi.formCompletionStatus;
	
	appUserDetails.id = newfi.loanAppFormid; 


	
	
	var quesText = "Living Situation";

	var options = [ {
		"text" : "I am currently renting",
		"onselect" : paintCustomerApplicationPurchasePageStep1a,
		"value" : "renting"
	}, {
		"text" : "I am a home owner",
		"onselect" : paintCustomerApplicationPurchasePageStep1a,
		"value" : "homeOwner"
	} ];

	var quesCont = getBuyHomeMutipleChoiceQuestion(quesText, options,"livingSituation");
	$('#app-right-panel').html(quesCont);
	
}

function getBuyHomeMutipleChoiceQuestion(quesText, options, name) {
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
			purchaseDetails[key] = event.data.option.value;
			appUserDetails.purchaseDetails = purchaseDetails;
			event.data.option.onselect();
		});
		optionContainer.append(option);
	}

	return container.append(quesTextCont).append(optionContainer);
}






function paintCustomerApplicationPurchasePageStep1a() {
	quesContxts = [];
	
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
    },{
        type: "desc",
        text: "When did you start living here ?",
        name: "startLivingTime",
        value: appUserDetails.user.customerDetail.livingSince
    }];

    if(purchaseDetails["livingSituation"]=="renting"){
    	var rentPerMonth ={
    	        type: "desc",
    	        text: "How much do you pay each month for rent?",
    	        name: "rentPerMonth",
    	        value: ""
    	    } ;
    	
    	questions.push(rentPerMonth);
    }else{
    	var sellYourHouse ={
    	        type: "yesno",
    	        text: "Are you planing to sell your current home ?",
    	        name: "isSellYourhome",
    	        options: [
                          {
                              "text": "Yes",
                              "value":"Yes"
                          },
                          {
                              "text": "No",
                              "value":"No"
                          }
                      ]
    	    } ;
    	
    	questions.push(sellYourHouse);
    	
    }
    
    $('#app-right-panel').append(quesHeaderTextCont);
    
    for(var i=0;i<questions.length;i++){
    	var question=questions[i];
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails);
    	contxt.drawQuestion();
    	
    	quesContxts.push(contxt);
    }

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function(event) {
    	
    	var inputState = $('input[name="state"]').val();
    	var city = $('input[name="city"]').val();
    	var zipCode = $('input[name="zipCode"]').val();
    	var livingSince = $('input[name="startLivingTime"]').val();
    	var monthlyRent =  $('input[name="rentPerMonth"]').val();
    	//var isSellYourhome = quesContxts[4].value;
    	
    	if(inputState != undefined && inputState != "" && city != undefined && city != ""  && zipCode != undefined && zipCode != ""  ){
        	

    		customerDetail.addressCity = city;
    		customerDetail.addressState = inputState;
    		customerDetail.addressZipCode = zipCode;
    		
    		customerDetail.livingSince = livingSince;
    		appUserDetails.monthlyRent = monthlyRent;
    		//appUserDetails.isSellYourhome = isSellYourhome;
    		
    		user.customerDetail = customerDetail;
    		
    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    		
    		appUserDetails.user = user;
    		appUserDetails.loanAppFormCompletionStatus=applyLoanStatus;
    		
    		//appUserDetails.buyHome = buyHome;
    		//alert(JSON.stringify(appUserDetails));
    		saveAndUpdateLoanAppForm(appUserDetails ,paintWhereYouLiveStep());
    		
        	        	
        }else{
        	showToastMessage("Please give answer of the questions");
        }
   	
    });

    $('#app-right-panel').append(saveAndContinueButton);
}




function paintWhereYouLiveStep(){

	quesContxts = [];
$('#app-right-panel').html("");
	
    var questions = [
                    
                     {
                         "type": "yesno",
                         "text": "Do you know city or zip code where you want to buy a home ?",
                         "name": "isCityOrZipKnown",
                         "options": [
                             {
                                 "text": "Yes",
                                 "addQuestions": [
                                     {
                                         "type": "desc",
                                         "text": "You can add upto three locations",
                                         "name": "buyhomeZipPri",
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
	
    var addRemoveRow = getAddRemoveButtonRow("buyhomeZipPri");
    
    var saveAndContinueButton = $('<div>').attr({
	    "class": "ce-save-btn"
	}).html("Save & continue").on('click', function() {
		
		  isCityOrZipKnown =quesContxts[0].value; 
		  buyhomeZipPri = $('input[name="buyhomeZipPri"]').val();
		 
		  //buyHome["isCityOrZipKnown"] = isCityOrZipKnown;
		  //buyHome["locationZipCode"] = locationZipCode;
		  
		  
		 // appUserDetails.buyHome = buyHome;
		  purchaseDetails.buyhomeZipPri = buyhomeZipPri;
		  appUserDetails.purchaseDetails = purchaseDetails;
		 alert(JSON.stringify(appUserDetails));
		   saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
		
	      });
	
    $('#app-right-panel').append(addRemoveRow).append(saveAndContinueButton);


}





function paintSpouseSaleOfCurrentHome() {
    
	applyLoanStatus = 1;
	appProgressBaar(1);
	//$('#app-right-panel').html('');
	
	if(appUserDetails.isSellYourhome == "Yes"){
		
		var quesHeaderTxt = "Sale Of Your Current Home";
	
	    var quesHeaderTextCont = $('<div>').attr({
	        "class": "app-ques-header-txt"
	    }).html(quesHeaderTxt);
	
	    var questions = [
	    {
	        type: "desc",
	        text: "Whats the listing price of your current home?",
	        name: "spouseHomeListPrice",
	        value: appUserDetails.user.customerDetail.addressState
	    }, {
	        type: "desc",
	        text: "Whats the mortage balance of your current home?",
	        name: "spouseHomeMortgageBalance",
	        value: appUserDetails.user.customerDetail.addressCity
	    }, {
	        type: "desc",
	        text: "How much from this sale do you intend to purchase towards your new home ?",
	        name: "spouseInvestmentInHome",
	        value: appUserDetails.user.customerDetail.addressZipCode
	    }];
	
	    var questionsContainer = getQuestionsContainer(questions);

	}
	
    
	var quesHeaderTxt2 = "My Assets";

    var quesHeaderTextCont2 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt2);


    var quesHeaderTxt31 = "Bank Accounts";

    var quesHeaderTextCont31 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt31);

    var questions1 = [{
        type: "select",
        text: "Account Type",
        name: "spouseBankAccount",
        options: [{
            text: "Savings",
            value: "0"
        }, {
            text: "Checkings",
            value: "1"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "Current balance",
        name: "spouseBankAcCurrentBankBalance",
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, {
        type: "desc",
        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "spouseBankAcUseForNewHome",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceProvider
    }];

    var questionsContainer1 = getQuestionsContainer(questions1);





      var quesHeaderTxt3 = "Retirement Accounts";

     var quesHeaderTextCont3 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt3);


   


 var questions2 = [{
        type: "select",
        text: "Account Type",
        name: "spouseRetirementBankAccounts",
        options: [{
            text: "IRA",
            value: "0"
        }, {
            text: "401K",
            value: "1"
        }, {
            text: "Other",
            value: "2"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "Current balance",
        name: "spouseRetirementCurrentBankBalance",
        value: appUserDetails.spouseRetirementCurrentBankBalance
    }, {
        type: "desc",
        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "spouseRetirementAmountUseForNewHome",
        value: appUserDetails.spouseRetirementAmountUseForNewHome
    }];

    var questionsContainer2 = getQuestionsContainer(questions2);




   var quesHeaderTxt4 = "Other Accounts & Securities";

     var quesHeaderTextCont4 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt4);


   


 var questions3 = [{
        type: "select",
        text: "Account Type",
        name: "spouseOtherBankAccount",
        options: [{
            text: "Money Market",
            value: "0"
        }, {
            text: "Cer8ficate of deposit",
            value: "1"
        }, {
            text: "Mutual Fund",
            value: "2"
        }, {
            text: "Stock",
            value: "3"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "Current balance",
        name: "spouseOtherBankCurrentBankBalance",
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, {
        type: "desc",
        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "spouseOtherAmountUseForNewHome",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceProvider
    }];

    var questionsContainer3 = getQuestionsContainer(questions3);






    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function(event) {
    	
    	
    		
    	spouseHomeListPrice = $('input[name="spouseHomeListPrice"]').val();
	     spouseHomeMortgageBalance = $('input[name="spouseHomeMortgageBalance"]').val();
		 spouseInvestmentInHome = $('input[name="spouseInvestmentInHome"]').val();			 
		 
		 spouseBankAccount = $('.app-options-cont[name="spouseBankAccount"]').find('.app-option-selected').text();
		 spouseBankAcCurrentBankBalance = $('input[name="spouseBankAcCurrentBankBalance"]').val();	
		 spouseBankAcUseForNewHome = $('input[name="spouseBankAcUseForNewHome"]').val();	
		
		 spouseRetirementBankAccounts =  $('.app-options-cont[name="spouseRetirementBankAccounts"]').find('.app-option-selected').text();
		 spouseRetirementCurrentBankBalance = $('input[name="spouseRetirementCurrentBankBalance"]').val();
		 spouseRetirementAmountUseForNewHome = $('input[name="spouseRetirementAmountUseForNewHome"]').val(); 
		 
		 spouseOtherBankAccount = $('.app-options-cont[name="spouseOtherBankAccount"]').find('.app-option-selected').text();
		 spouseOtherBankCurrentBankBalance = $('input[name="spouseOtherBankCurrentBankBalance"]').val();
		 spouseOtherAmountUseForNewHome  = $('input[name="spouseOtherAmountUseForNewHome"]').val();
		 
	     appUserDetails.spouseHomeListPrice = spouseHomeListPrice;
	     appUserDetails.spouseHomeMortgageBalance = spouseHomeMortgageBalance;
		 appUserDetails.spouseInvestmentInHome = spouseInvestmentInHome;
		 
		 appUserDetails.spouseBankAccount = spouseBankAccount;
		 appUserDetails.spouseBankAcCurrentBankBalance = spouseBankAcCurrentBankBalance;
		 appUserDetails.spouseBankAcUseForNewHome = spouseBankAcUseForNewHome;
		
		 appUserDetails.spouseRetirementBankAccounts = spouseRetirementBankAccounts;
		 appUserDetails.spouseRetirementCurrentBankBalance = spouseRetirementCurrentBankBalance;
		 appUserDetails.spouseRetirementAmountUseForNewHome = spouseRetirementAmountUseForNewHome;
		 
		 appUserDetails.spouseOtherBankAccount = spouseOtherBankAccount;
		 appUserDetails.spouseOtherBankCurrentBankBalance = spouseOtherBankCurrentBankBalance;
		 appUserDetails.spouseOtherAmountUseForNewHome = spouseOtherAmountUseForNewHome;
    	
    		appUserDetails.loanAppFormCompletionStatus=applyLoanStatus;
    		
    		//alert(JSON.stringify(appUserDetails));
    		saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep4a());
    		
        	        	
     //   }else{
       // 	showToastMessage("Please give answer of the questions");
        //}
   	
    });


 var questionsContainer10 = $('<div>').attr({
        "class": "app-ques-container"
    });


    questionsContainer10.append(quesHeaderTextCont).append(questionsContainer).append(quesHeaderTextCont2).append(quesHeaderTextCont31).append(questionsContainer1).append(quesHeaderTextCont3).append(questionsContainer2).append(quesHeaderTextCont4).append(questionsContainer3)
        .append(saveAndContinueButton);
        
        return questionsContainer10;
}




function paintSaleOfCurrentHome() {
    
	applyLoanStatus = 1;
	appProgressBaar(1);
	//$('#app-right-panel').html('');
   
	if(appUserDetails.isSellYourhome == "Yes"){
		
	var quesHeaderTxt = "Sale Of Your Current Home";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    
    
	    var questions = [
	    {
	        type: "desc",
	        text: "Whats the listing price of your current home?",
	        name: "homelistprice",
	        value: appUserDetails.homelistprice
	    }, {
	        type: "desc",
	        text: "Whats the mortage balance of your current home?",
	        name: "homemortgagebalance",
	        value: appUserDetails.homemortgagebalance
	    }, {
	        type: "desc",
	        text: "How much from this sale do you intend to purchase towards your new home ?",
	        name: "inverstInPurchase",
	        value: appUserDetails.inverstInPurchase
	    }];
	
	    var questionsContainer = getQuestionsContainer(questions);
   }

      var quesHeaderTxt2 = "My Assets";

     var quesHeaderTextCont2 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt2);


var quesHeaderTxt31 = "Bank Accounts";

     var quesHeaderTextCont31 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt31);

 var questions1 = [{
        type: "select",
        text: "Account Type",
        name: "bankAccount",
        options: [{
            text: "Savings",
            value: "0"
        }, {
            text: "Checkings",
            value: "1"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "Current balance",
        name: "bankAccountCurrentBankBalance",
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, {
        type: "desc",
        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "bankAccountUsefornewhome",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceProvider
    }];

    var questionsContainer1 = getQuestionsContainer(questions1);





      var quesHeaderTxt3 = "Retirement Accounts";

     var quesHeaderTextCont3 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt3);


   


 var questions2 = [{
        type: "select",
        text: "Account Type",
        name: "accountType",
        options: [{
            text: "IRA",
            value: "0"
        }, {
            text: "401K",
            value: "1"
        }, {
            text: "Other",
            value: "2"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "Current balance",
        name: "accountTypeCurrentBankBalance",
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, {
        type: "desc",
        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "accountTypeUseForNewHome",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceProvider
    }];

    var questionsContainer2 = getQuestionsContainer(questions2);




   var quesHeaderTxt4 = "Other Accounts & Securities";

     var quesHeaderTextCont4 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt4);


   


 var questions3 = [{
        type: "select",
        text: "Account Type",
        name: "otherAccounts",
        options: [{
            text: "Money Market",
            value: "0"
        }, {
            text: "Cer8ficate of deposit",
            value: "1"
        }, {
            text: "Mutual Fund",
            value: "2"
        }, {
            text: "Stock",
            value: "3"
        }],
        selected: ""
    }, {
        type: "desc",
        text: "Current balance",
        name: "otherAccountCurrentBankBalance",
        value: appUserDetails.propertyTypeMaster.propertyTaxesPaid
    }, {
        type: "desc",
        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "otherAccountsUseForNewHome",
        value: appUserDetails.propertyTypeMaster.propertyInsuranceProvider
    }];

    var questionsContainer3 = getQuestionsContainer(questions3);






    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function(event) {
    	
    	
    	
    	EmployedIncomePreTax= $('input[name="beforeTax"]').val();
        EmployedAt = $('input[name="workPlace"]').val();
        EmployedSince = $('input[name="startWorking"]').val();
		
        selfEmployedIncome = $('input[name="selfEmployed"]').val();
        
        ssDisabilityIncome = $('input[name="disability"]').val();
		
        monthlyPension = $('input[name="pension"]').val();

		
		
		
        appUserDetails.user.customerDetail.customerEmploymentIncome.employedIncomePreTax = EmployedIncomePreTax;
		appUserDetails.user.customerDetail.customerEmploymentIncome.employedIncomePreTax = EmployedIncomePreTax;
		appUserDetails.user.customerDetail.customerEmploymentIncome.employedAt = EmployedAt;
		appUserDetails.user.customerDetail.customerEmploymentIncome.employedSince = EmployedSince;
		
		if(monthlyPension != "" && monthlyPension != undefined){
			
			appUserDetails.user.customerDetail.isselfEmployed= true;
			appUserDetails.user.customerDetail.monthlyPension =monthlyPension;
		}
		
		
		if(selfEmployedIncome != "" && selfEmployedIncome != undefined){
			
			appUserDetails.user.customerDetail.ispensionOrRetirement = true;
			appUserDetails.user.customerDetail.selfEmployedIncome =selfEmployedIncome;
		}
		
		if(ssDisabilityIncome !="" && ssDisabilityIncome != undefined){
			
			appUserDetails.user.customerDetail.isssIncomeOrDisability=true;
			appUserDetails.user.customerDetail.ssDisabilityIncome = ssDisabilityIncome;
		}
    	 
    	
    	 homelistprice = $('input[name="homelistprice"]').val();
	     homemortgagebalance =  $('input[name="homemortgagebalance"]').val();
	     inverstInPurchase = $('input[name="inverstInPurchase"]').val();			 
	     
	     /* Bank Account Start*/
	     
	     accountSubType = $('.app-options-cont[name="bankAccount"]').find('.app-option-selected').text();
	     currentAccountBalance = $('input[name="bankAccountCurrentBankBalance"]').val();			 
	     amountForNewHome = $('input[name="bankAccountUsefornewhome"]').val();
	    
	     
	     appUserDetails.user.customerDetail.customerBankAccountDetails.accountSubType = accountSubType;
	     appUserDetails.user.customerDetail.customerBankAccountDetails.currentAccountBalance =currentAccountBalance;
	     appUserDetails.user.customerDetail.customerBankAccountDetails.amountForNewHome = amountForNewHome;
	     
	     /* Bank Account End*/
	     
	     
	     /* Retirement Account Start*/
	     
	     accountSubType =  $('.app-options-cont[name="accountType"]').find('.app-option-selected').text();			 
	     currentAccountBalance = $('input[name="accountTypeCurrentBankBalance"]').val();		 
	     accountTypeUseForNewHome  = $('input[name="accountTypeUseForNewHome"]').val();
	     
	     appUserDetails.user.customerDetail.customerRetirementAccountDetails.accountSubType = accountSubType;
	     appUserDetails.user.customerDetail.customerRetirementAccountDetails.currentAccountBalance = currentAccountBalance;
	     appUserDetails.user.customerDetail.customerRetirementAccountDetails.amountForNewHome= accountTypeUseForNewHome;
	     
	     /* Retirement Account Ends*/
	     
	     
	     /* Other Account Start*/
	     
	     otherAccountName = $('.app-options-cont[name="otherAccounts"]').find('.app-option-selected').text();
	     otherAccountCurrentBankBalance = $('input[name="otherAccountCurrentBankBalance"]').val();
	     otherAccountsUseForNewHome  = $('input[name="otherAccountsUseForNewHome"]').val();
	     
	     appUserDetails.user.customerDetail.customerOtherAccountDetails.accountSubType = otherAccountName;
	     appUserDetails.user.customerDetail.customerOtherAccountDetails.currentAccountBalance= otherAccountCurrentBankBalance;
	     appUserDetails.user.customerDetail.customerOtherAccountDetails.amountForNewHome = otherAccountsUseForNewHome; 
	     
		 
	     /* Other Account Ends*/
		 
	     /*appUserDetails.homelistprice = homelistprice;
	     appUserDetails.homemortgagebalance = homemortgagebalance;
	     appUserDetails.inverstInPurchase = inverstInPurchase;
	     			 			 
	     appUserDetails.accountTypeCurrentBankBalance = accountTypeCurrentBankBalance;			 			 
	     appUserDetails.accountTypeUseForNewHome = accountTypeUseForNewHome;
	     appUserDetails.otherAccountName = otherAccountName;
		 appUserDetails.otherAccountsUseForNewHome = otherAccountsUseForNewHome;*/
    		
    		
    		
    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
    		
    		appUserDetails.loanAppFormCompletionStatus=applyLoanStatus;
    		
    		//alert(JSON.stringify(appUserDetails));
    		
				if(appUserDetails.isSpouseOnLoan == true)
				{
				saveAndUpdateLoanAppForm(appUserDetails,paintMySpouseIncome());
				}else{
				saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a());
				}
    		
        	        	
      //  }else{
        //	showToastMessage("Please give answer of the questions");
        //}
   	
    });


 var questionsContainer10 = $('<div>').attr({
        "class": "app-ques-container"
    });


    questionsContainer10.append(quesHeaderTextCont).append(questionsContainer).append(quesHeaderTextCont2).append(quesHeaderTextCont31).append(questionsContainer1).append(quesHeaderTextCont3).append(questionsContainer2).append(quesHeaderTextCont4).append(questionsContainer3)
        .append(saveAndContinueButton);
        
        return questionsContainer10;
}



///////////////BuyHomeApplication /////////////////


//////////////////////END OF NEW CODE /////////////////////////////

