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
	
	
	
	
	 if(appUserDetails.propertyTypeMaster != null){
		 propertyTypeMaster = appUserDetails.propertyTypeMaster;
		
	 }
	 
	 
	 if(appUserDetails.refinancedetails !=null){
	     refinancedetails.id =appUserDetails.refinancedetails.id; 	 
		 refinancedetails.refinanceOption = appUserDetails.refinancedetails.refinanceOption;
		 refinancedetails.currentMortgageBalance = appUserDetails.refinancedetails.currentMortgageBalance;
		 refinancedetails.currentMortgagePayment = appUserDetails.refinancedetails.currentMortgagePayment;
		 refinancedetails.includeTaxes = appUserDetails.refinancedetails.includeTaxes;
		 refinancedetails.secondMortageBalance = appUserDetails.refinancedetails.secondMortageBalance;
		 refinancedetails.mortgageyearsleft=appUserDetails.refinancedetails.mortgageyearsleft;
		 refinancedetails.cashTakeOut = appUserDetails.refinancedetails.cashTakeOut;
	 }
	 
	 if(appUserDetails.purchaseDetails != null){
		 
		 purchaseDetails = appUserDetails.purchaseDetails;
	 }
	 

	 
	 if(newfi.customerSpouseDetail != null){ 
		 customerSpouseDetail.id = newfi.customerSpouseDetail.id;
	 }
	//alert('newfi1'+JSON.stringify(newfi));
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
	
	
	if(appUserDetails.purchaseDetails.livingSituation && appUserDetails.purchaseDetails.livingSituation =="renting"){
		$('.ce-options-cont').find('.ce-option').first().css("background","rgb(247, 72, 31)");
	} 
	else{
		
		$('.ce-options-cont').find('.ce-option').first().next().css("background","rgb(247, 72, 31)");
	}
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
    	var isSellYourhome = quesContxts[4].value;
    	
    	//alert(isSellYourhome);
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
    		
    		
    		if(isSellYourhome =='Yes')
    		appUserDetails.homeToSell = true;
    		else
    		appUserDetails.homeToSell = false;
    		
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
                                         "text": "Please provide zipcode of desired location",
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
		var contxt=getQuestionContext(question,$('#app-right-panel'));
		contxt.drawQuestion();
		
		quesContxts.push(contxt);
	}
	
    var addRemoveRow = getAddRemoveButtonRow("buyhomeZipPri");
    
    var btn=$(addRemoveRow).find(".add-btn");
    if(appUserDetails.purchaseDetails.buyhomeZipSec&&appUserDetails.purchaseDetails.buyhomeZipSec!=""){
        addZipField("buyhomeZipPri",$(btn),appUserDetails.purchaseDetails.buyhomeZipSec);
    }
    if(appUserDetails.purchaseDetails.buyhomeZipTri&&appUserDetails.purchaseDetails.buyhomeZipTri!=""){
        addZipField("buyhomeZipPri",$(btn),appUserDetails.purchaseDetails.buyhomeZipTri);
    }
    var saveAndContinueButton = $('<div>').attr({
	    "class": "ce-save-btn"
	}).html("Save & continue").on('click', function() {
		
		  isCityOrZipKnown =quesContxts[0].value; 
		  buyhomeZipPri = $('input[name="buyhomeZipPri"]').val();
		   buyhomeZipSec = $('input[name="buyhomeZipPri1"]').val();
		    buyhomeZipTri = $('input[name="buyhomeZipPri2"]').val();
		
		// alert('buyhomeZipPri'+buyhomeZipPri);
		 //		 alert('buyhomeZipSec'+buyhomeZipSec);
		 	//	 		 alert('buyhomeZipTri'+buyhomeZipTri);
		  
		  purchaseDetails.buyhomeZipPri = buyhomeZipPri;
		  purchaseDetails.buyhomeZipSec=buyhomeZipSec;
		  purchaseDetails.buyhomeZipTri=buyhomeZipTri;
		  appUserDetails.purchaseDetails = purchaseDetails;
		
		   saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2());
		
	      });
	
    $('#app-right-panel').append(addRemoveRow).append(saveAndContinueButton);


}

function addZipField(fieldName,element,value){

        var inputField = $('input[name="'+fieldName+'"]');
        
        var inputElement = $('<input>').attr({
            "name" : fieldName+inputField.parent().children('input').size(),
            "class" : "ce-input ce-input-add"
        });
        if(value)
            inputElement.val(value);
        //alert('e.data.fieldName'+e.data.fieldName+inputField.parent().children('input').size())
        var numberOfInputs = inputField.parent().children('input').size();
        
        if(numberOfInputs<3){
            inputField.parent().append(inputElement);
            if(numberOfInputs == 2){
                $(element).hide();
            }
        }
}


function getAddRemoveButtonRow(fieldName){
	
	var container = $('<div>').attr({
		"class" : "add-remove-row clearfix"
	});
	
	var addBtn = $('<div>').attr({
		"class" : "add-btn float-left"
	}).html("Add")
	.bind('click',{"fieldName":fieldName},function(e){
        addZipField(e.data.fieldName,$(this))
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


function paintSpouseSaleOfCurrentHome() {
    
	//appProgressBaar(1);
	//$('#app-right-panel').html('');
	
	if(appUserDetails.homeToSell == true){
		
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

    var skipQuestions = $('<div>').attr({
    	"class" : "ce-option-checkbox"
    }).html("No Thanks, Let's move on")
    .bind('click',function(){
	   	 if($(this).hasClass('app-option-checked')){
			 $(this).removeClass('app-option-checked');
	    	 $(this).next('.asset-ques-wrapper').show();
		 }else{
			 $(this).addClass('app-option-checked');
	    	 $(this).next('.asset-ques-wrapper').hide();    		 
		 }
	 });

    var quesHeaderTxt31 = "Bank Accounts";

    var quesHeaderTextCont31 = $('<div>').attr({
        "class": "ce-option-checkbox"
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

    var questionsContainer1 = getPopupQuestionsContainer(questions1);

      var quesHeaderTxt3 = "Retirement Accounts";

     var quesHeaderTextCont3 = $('<div>').attr({
        "class": "ce-option-checkbox"
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

    var questionsContainer2 = getPopupQuestionsContainer(questions2);

   var quesHeaderTxt4 = "Other Accounts & Securities";

     var quesHeaderTextCont4 = $('<div>').attr({
        "class": "ce-option-checkbox"
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

    var questionsContainer3 = getPopupQuestionsContainer(questions3);

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function(event) {
    	
    	////This code added to get Customer Spouse employment Income///////
    		
    		var  customerSpouseEmploymentIncome = [];
    		
      $("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
	       customerSpouseEmploymentIncomeTemp1 = {};
	       
	       id = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="custSpouseEmploymentIncomeId"]').val();
	       if(id ==""){
	     	  id = undefined;
	       }
	       
	       spouseBeforeTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseBeforeTax"]').val();
	       spouseWorkPlace = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseWorkPlace"]').val();
	       spouseStartWorking = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseStartWorking"]').val();
	      
	       customerSpouseEmploymentIncomeTemp1.id = id;
	       customerSpouseEmploymentIncomeTemp1.employedIncomePreTax = spouseBeforeTax;
	       customerSpouseEmploymentIncomeTemp1.employedAt = spouseWorkPlace;
	       customerSpouseEmploymentIncomeTemp1.employedSince = spouseStartWorking;
	       var temp = {};
	       temp.customerSpouseEmploymentIncome = customerSpouseEmploymentIncomeTemp1;
	       
	       customerSpouseEmploymentIncome.push(temp);
	      });
    
	      if(customerSpouseEmploymentIncome&&customerSpouseEmploymentIncome.length>0)
	    	  appUserDetails.customerSpouseEmploymentIncome=customerSpouseEmploymentIncome;
   
 
	      
     // appUserDetails.customerSpouseEmploymentIncome=customerSpouseEmploymentIncome;
  
  
    
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
  
    	////////	
    	 spouseHomeListPrice = $('input[name="spouseHomeListPrice"]').val();
	     spouseHomeMortgageBalance = $('input[name="spouseHomeMortgageBalance"]').val();
		 spouseInvestmentInHome = $('input[name="spouseInvestmentInHome"]').val();			 
		
		 
		 if(purchase == true){
	            
		        homelistprice = $('input[name="homelistprice"]').val();
		        homemortgagebalance = $('input[name="homemortgagebalance"]').val();
		        inverstInPurchase = $('input[name="inverstInPurchase"]').val();
		        
		        
		        appUserDetails.customerSpouseBankAccountDetails = [];
		        appUserDetails.customerSpouseRetirementAccountDetails = [];
		        appUserDetails.customerSpouseOtherAccountDetails = [];
		        
		        var assets=$('.asset-ques-wrapper').find('.app-account-wrapper');
		        
		        var bankContainer=assets[0];
		        var retirementContainer=assets[1];
		        var otherContainer=assets[2];
		        
		        if($(bankContainer).find('.app-option-checked')){
		        	appUserDetails.customerSpouseBankAccountDetails=getAccountValues(bankContainer,"customerSpouseBankAccountDetails","spouseBankAccount","spouseBankAcCurrentBankBalance","spouseBankAcUseForNewHome");
		        }
				if($(retirementContainer).find('.app-option-checked')){
					appUserDetails.customerSpouseRetirementAccountDetails=getAccountValues(retirementContainer,"customerSpouseRetirementAccountDetails","spouseRetirementBankAccounts","spouseRetirementCurrentBankBalance","spouseRetirementAmountUseForNewHome");
			    }
				if($(otherContainer).find('.app-option-checked')){
					appUserDetails.customerSpouseOtherAccountDetails=getAccountValues(otherContainer,"customerSpouseOtherAccountDetails","spouseOtherBankAccount","spouseOtherBankCurrentBankBalance","spouseOtherAmountUseForNewHome");
				}
				
				
		    }
	      
	 
    	
    		
    		
 
    		saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep4a());
  
    });


    var questionsContainer10 = $('<div>').attr({
        "class": "app-ques-container"
    });


    var assetWrapper = $('<div>').attr({
    	"class" : "asset-ques-wrapper"
    });
    
    var assetCont1 = $('<div>').attr({
    	"class" : "app-account-wrapper"
    });
    var subQuesWrapper1 = $('<div>').attr({
    	"class" : "hide ce-sub-option-wrapper"
    }).append(questionsContainer1).append(getAddAccountBtn(questions1));
    assetCont1.append(quesHeaderTextCont31).append(subQuesWrapper1);
    var assetCont2 = $('<div>').attr({
    	"class" : "app-account-wrapper"
    });
    var subQuesWrapper2 = $('<div>').attr({
    	"class" : "hide ce-sub-option-wrapper"
    }).append(questionsContainer2).append(getAddAccountBtn(questions2));
    assetCont2.append(quesHeaderTextCont3).append(subQuesWrapper2);
    var assetCont3 = $('<div>').attr({
    	"class" : "app-account-wrapper"
    });
    var subQuesWrapper3 = $('<div>').attr({
    	"class" : "hide ce-sub-option-wrapper"
    }).append(questionsContainer3).append(getAddAccountBtn(questions3));
    assetCont3.append(quesHeaderTextCont4).append(subQuesWrapper3);
    assetWrapper.append(assetCont1).append(assetCont2).append(assetCont3);
    
    questionsContainer10.append(quesHeaderTextCont).append(questionsContainer).append(quesHeaderTextCont2).append(skipQuestions).append(assetWrapper)
        .append(saveAndContinueButton);
        
    return questionsContainer10;
}



function getPopupQuestionsContainer(questions) {
    var questionsContainer = $('<div>').attr({
        "class": "ce-option-ques-wrapper"
    });
    
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var quesCont = getApplicationQuestion(question);
        questionsContainer.append(quesCont);
    }
    return questionsContainer;
}



function saleYourCurrentHome(){
	
	var quesHeaderTxt = "Sale Of Your Current Home";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    
    
	    var questions = [
	    {
	        type: "desc",
	        text: "Whats the listing price of your current home?",
	        name: "homelistprice",
	        value: ""
	    }, {
	        type: "desc",
	        text: "Whats the mortage balance of your current home?",
	        name: "homemortgagebalance",
	        value: ""
	    }, {
	        type: "desc",
	        text: "How much from this sale do you intend to purchase towards your new home ?",
	        name: "inverstInPurchase",
	        value: ""
	    }];
	
	    var questionsContainer = getQuestionsContainer(questions);
	    quesHeaderTextCont.append(questionsContainer);
	    
       return quesHeaderTextCont;	    
}

$('body').on('click','.app-account-wrapper .ce-option-checkbox',function(e){
	e.stopPropagation();
	
	if($(this).hasClass('app-option-checked')){
		$(this).removeClass('app-option-checked');
		$(this).next('.ce-sub-option-wrapper').hide();
	}else{
		$(this).addClass('app-option-checked');
		$(this).next('.ce-sub-option-wrapper').show();
	}
});

function bankAccount(){
	
	var quesHeaderTxt = "Bank Accounts";

	var quesHeaderWrapper = $('<div>').attr({
	       "class": "app-account-wrapper"
	    });

    var quesHeaderTextCont = $('<div>').attr({
    	"class" : "ce-option-checkbox"
    }).html(quesHeaderTxt);
    quesHeaderWrapper.append(quesHeaderTextCont);

    var questions = [{
       type: "select",
       text: "Account Type",
       name: "bankAccount",
       options: [{
           text: "Savings",
           value: "savings"
       }, {
           text: "Checkings",
           value: "checkings"
       }],
       selected: ""
   }, {
       type: "desc",
       text: "Current balance",
       name: "bankAccountCurrentBankBalance",
       value: ""
   }, {
       type: "desc",
       text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.",
       name: "bankAccountUsefornewhome",
       value: ""
   }];

   var questionsContainer = getPopupQuestionsContainer(questions);

   var questionsWrapper = $('<div>').attr({
	  "class" : "hide ce-sub-option-wrapper" 
   });
   
	   var addAccountBtn = $('<div>').attr({
		  "class" : "add-btn add-account-btn" 
	   }).html("Add Account")
	   .bind('click',function(e){
		    	 
	    	 var parentWrapper = $(this).closest('.ce-sub-option-wrapper');
	    	 
	    	 if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
	    		 return false;
	    	 }
	    	 
	    	 parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	    	 
    		 var questionsCont = getPopupQuestionsContainer(questions);
    		 questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));
    		 
    		 var removeAccountBtn = $('<div>').attr({
  	    		"class" : "add-btn remove-account-btn" 
  	    	 }).html("Remove Account")
  	    	 .bind('click',function(e){
  	    		$(this).closest('.ce-option-ques-wrapper').remove();
  	    		if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
  	    			parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
  	    		}
  	    	 });
     		 parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
	     });
   
   questionsWrapper.append(questionsContainer).append(addAccountBtn);
   return quesHeaderWrapper.append(questionsWrapper);
}


function retirementAccounts(){
	
	var quesRetirementAc = "Retirement Accounts";

    var quesHeaderWrapper = $('<div>').attr({
       "class": "app-account-wrapper"
    });

    var quesHeaderText = $('<div>').attr({
    	"class" : "ce-option-checkbox"
    }).html(quesRetirementAc);
    quesHeaderWrapper.append(quesHeaderText);
    
		var questions = [{
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
		       value: ""
		   }, {
		       type: "desc",
		       text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
		       name: "accountTypeUseForNewHome",
		       value: ""
		   }];
		
		   var questionsContainer = getPopupQuestionsContainer(questions);

		   var questionsWrapper = $('<div>').attr({
			  "class" : "hide ce-sub-option-wrapper" 
		   });
		   var addAccountBtn = $('<div>').attr({
				  "class" : "add-btn add-account-btn" 
		   }).html("Add Account")
		   .bind('click',function(e){
		    	 
		    	 var parentWrapper = $(this).closest('.ce-sub-option-wrapper');
		    	 
		    	 if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
		    		 return false;
		    	 }
		    	 
		    	 parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
		    	 
	    		 var questionsCont = getPopupQuestionsContainer(questions);
	    		 questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));
	    		 
	    		 var removeAccountBtn = $('<div>').attr({
	  	    		"class" : "add-btn remove-account-btn" 
	  	    	 }).html("Remove Account")
	  	    	 .bind('click',function(e){
	  	    		$(this).closest('.ce-option-ques-wrapper').remove();
	  	    		if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
	  	    			parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	  	    		}
	  	    	 });
	     		 parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
		     });
			   
		   questionsWrapper.append(questionsContainer).append(addAccountBtn);
		 return quesHeaderWrapper.append(questionsWrapper);
}


function otherAccount(){
	

	   var quesHeader = "Other Accounts & Securities";

	   var quesHeaderWrapper = $('<div>').attr({
	       "class": "app-account-wrapper"
	    });

		var quesHeaderTextCont = $('<div>').attr({
			"class" : "ce-option-checkbox"
		}).html(quesHeader);
		quesHeaderWrapper.append(quesHeaderTextCont);

	   var questions = [{
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
	        value: ""
	    }, {
	        type: "desc",
	        text: "How much from this Account are you able to use towards the purchase for your new home? Your best guess is fine.?",
	        name: "otherAccountsUseForNewHome",
	        value: ""
	    }];

	    var questionsContainer = getPopupQuestionsContainer(questions);
	    var questionsWrapper = $('<div>').attr({
			  "class" : "hide ce-sub-option-wrapper" 
		   });
	    var addAccountBtn = $('<div>').attr({
	  	  "class" : "add-btn add-account-btn" 
	     }).html("Add Account")
	     .bind('click',function(e){
	    	 
	    	 var parentWrapper = $(this).closest('.ce-sub-option-wrapper');
	    	 
	    	 if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
	    		 return false;
	    	 }
	    	 
	    	 parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	    	 
    		 var questionsCont = getPopupQuestionsContainer(questions);
    		 questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));
    		 
    		 var removeAccountBtn = $('<div>').attr({
  	    		"class" : "add-btn remove-account-btn" 
  	    	 }).html("Remove Account")
  	    	 .bind('click',function(e){
  	    		$(this).closest('.ce-option-ques-wrapper').remove();
  	    		if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
  	    			parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
  	    		}
  	    	 });
     		 parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
	     });
	     
	   questionsWrapper.append(questionsContainer).append(addAccountBtn);
	   return quesHeaderWrapper.append(questionsWrapper);
}


function paintSaleOfCurrentHome() {
    
	
	
	var questionsContainer = $('<div>').attr({
        "class": "app-ques-container"
    });
   
	if(appUserDetails.homeToSell == true){
	
	    var saleYourCurrentHomeDIV = saleYourCurrentHome(); 
	    questionsContainer.append(saleYourCurrentHomeDIV);
     }

     var quesHeaderAssets = "My Assets";
     var quesMyAssetsCont = $('<div>').attr({
        "class": "app-ques-header-txt"
     }).html(quesHeaderAssets);
   
     questionsContainer.append(quesMyAssetsCont);
     
     var skipQuestions = $('<div>').attr({
    	"class" : "ce-option-checkbox" 
     }).html("No Thanks, Let's move on")
     .bind('click',function(){
    	 if($(this).hasClass('app-option-checked')){
    		 $(this).removeClass('app-option-checked');
        	 $(this).next('.asset-ques-wrapper').show();
    	 }else{
    		 $(this).addClass('app-option-checked');
        	 $(this).next('.asset-ques-wrapper').hide();    		 
    	 }
     });
     
     var assetQuestionsWrapper = $('<div>').attr({
    	"class" : "asset-ques-wrapper" 
     });
     
     /*  bank details*/
     var bankAccountDiv = bankAccount();
     assetQuestionsWrapper.append(bankAccountDiv);

     /*  Retirement bank details*/
     var retirementAccountsDiv = retirementAccounts();
     assetQuestionsWrapper.append(retirementAccountsDiv);
     
     /* other bank details*/
     var otherAccountDiv = otherAccount();
     assetQuestionsWrapper.append(otherAccountDiv);
        
     return questionsContainer.append(skipQuestions).append(assetQuestionsWrapper);
}


function getAddAccountBtn(questions) {
	var addAccountBtn = $('<div>').attr({
		  "class" : "add-btn add-account-btn" 
	   }).html("Add Account")
	   .bind('click',function(e){
		    	 
	    	 var parentWrapper = $(this).closest('.ce-sub-option-wrapper');
	    	 
	    	 if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
	    		 return false;
	    	 }
	    	 
	    	 parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	    	 
  		 var questionsCont = getPopupQuestionsContainer(questions);
  		 questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));
  		 
  		 var removeAccountBtn = $('<div>').attr({
	    		"class" : "add-btn remove-account-btn" 
	    	 }).html("Remove Account")
	    	 .bind('click',function(e){
	    		$(this).closest('.ce-option-ques-wrapper').remove();
	    		if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
	    			parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	    		}
	    	 });
   		 parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
   });
	return addAccountBtn;
}


///////////////BuyHomeApplication /////////////////


//////////////////////END OF NEW CODE /////////////////////////////

