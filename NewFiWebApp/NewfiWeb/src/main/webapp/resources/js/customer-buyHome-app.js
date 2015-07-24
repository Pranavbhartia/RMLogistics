///////////////This code to go in BuyHomeApplication /////////////////



buyHome = {};

appUserDetails.buyHome = buyHome;
quesContxts = [];

function paintBuyHomeContainer(appUserDetails) {

	
	
	
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
	paintBuyHomeQuest(appUserDetails);
}




function paintBuyHomeQuest(appUserDetails) {
	//homeProgressBaar(1);
//	alert('JSON.stringify(appUserDetails)'+JSON.stringify(appUserDetails));
	if(JSON.stringify(appUserDetails) == "{}"){
	appUserDetails = {};
		appUserDetails = JSON.parse(newfi.appUserDetails);
	
	}

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
	
	//appUserDetails.id = newfi.loanAppFormid; 

	
	
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
	var applicationLocked=checkLqbFileId();
	if(applicationLocked&&appUserDetails.purchaseDetails.livingSituation && appUserDetails.purchaseDetails.livingSituation =="renting"){
		options[1].onselect=function(){};
	} 
	if(applicationLocked&&appUserDetails.purchaseDetails.livingSituation && appUserDetails.purchaseDetails.livingSituation =="homeOwner"){
		options[0].onselect=function(){};
	}	

	var quesCont = getBuyHomeMutipleChoiceQuestion(quesText, options,"livingSituation");
	$('#app-right-panel').html(quesCont);
	
	
	if(appUserDetails.purchaseDetails.livingSituation && appUserDetails.purchaseDetails.livingSituation =="renting"){
		$('.ce-options-cont').find('.ce-option').first().css("background","rgb(244, 117, 34)");
	} 
	if(appUserDetails.purchaseDetails.livingSituation && appUserDetails.purchaseDetails.livingSituation =="homeOwner"){
		
		$('.ce-options-cont').find('.ce-option').first().next().css("background","rgb(244, 117, 34)");
	}
}

function getBuyHomeMutipleChoiceQuestion(quesText, options, name) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});

	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);

	if(quesText=="Living Situation"){
		quesTextCont.addClass('ce-title-adj');
	}
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
	
    var quesHeaderTxt = "Tell us about where you currently live";
    

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var row=paintCheckBox();
    
    var questions = [/*{
        type: "desc",
        text: "Street address",
        name: "addressStreet",
        value: appUserDetails.user.customerDetail.addressStreet
    },*/ {
        type: "desc",
        text: "State",
        name: "state",
        value: appUserDetails.user.customerDetail.addressState
    }, {
        type: "desc",
        text: "City",
        name: "city",
        value: appUserDetails.user.customerDetail.addressCity
    }, {
        type: "desc",
        text: "ZIP code",
        name: "zipCode",
        value: appUserDetails.user.customerDetail.addressZipCode
    },{
        type: "desc",
        text: "When did you start living here ?",
        name: "startLivingTime",
        value: appUserDetails.user.customerDetail.livingSince
    }];

    if(appUserDetails.purchaseDetails["livingSituation"]=="renting"){
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
    	        name: "homeToSell",
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
    var applicationLocked=checkLqbFileId();
    
    var propQuestions = [/*{
        type: "desc",
        text: "Property street address",
        name: "propStreetAddress",
        value: appUserDetails.propertyTypeMaster.propStreetAddress
    },*/ {
        type: "desc",
        text: "Property state",
        name: "propState",
        value: appUserDetails.propertyTypeMaster.propState
    }, {
        type: "desc",
        text: "Property city",
        name: "propCity",
        value: appUserDetails.propertyTypeMaster.propCity
    }, {
        type: "desc",
        text: "In what ZIP code will you likely purchase?",
        name: "propZipCode",
        value: appUserDetails.propertyTypeMaster.homeZipCode
    }];

    
    
    $('#app-right-panel').append(quesHeaderTextCont);
    
    for(var i=0;i<questions.length;i++){
    	var question=questions[i];
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails);
    	contxt.drawQuestion();
    	
    	quesContxts.push(contxt);
    }
    
    var propQuesHeaderTxt = "Tell us about the property you want to buy";
    
    
    var propSpacer = $('<div>').attr({
        "class": "app-ques-header-txt"
    });
    
    
    var propQuesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(propQuesHeaderTxt);
    
    $('#app-right-panel').append(propSpacer).append(propQuesHeaderTextCont);
    $('#app-right-panel').append(row);
   
    for(var i=0;i<propQuestions.length;i++){
    	var propQuest=propQuestions[i];
    	var contxt=getQuestionContext(propQuest,$('#app-right-panel'),appUserDetails);
    	contxt.drawQuestion();
    	
    	quesContxts.push(contxt);
    }
    

    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}

    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function(event) {
    	
    	if($(this).html()!=next ){
		    	/*var addressStreet = $('input[name="addressStreet"]').val();*/
		    	var inputState = $('input[name="state"]').val();
		    	var city = $('input[name="city"]').val();
		    	var zipCode = $('input[name="zipCode"]').val();
		    	var livingSince = $('input[name="startLivingTime"]').val();
		    	var monthlyRent =  $('input[name="rentPerMonth"]').val();
		    	var isSellYourhome = quesContxts[5].value;
		    			    	
		    	/*var propAddress= $('input[name="propStreetAddress"]').val();*/
		    	var propState = $('input[name="propState"]').val();
		    	var propCity = $('input[name="propCity"]').val();
		    	var propZipCode = $('input[name="propZipCode"]').val();
                /*Validation*/
		    	var isStatus=currentlyLivingValidation();
		    	if(!isStatus){
		    		return false;
		    	}
		    	
		    	/*End of validation*/

		    	ajaxRequest("rest/states/zipCode", "GET", "json", {"zipCode":propZipCode}, function(response) {
	                if (response.error) {
	                    showToastMessage(response.error.message);
	                } else {
	                    if(response.resultObject == zipcode_valid){
	                       /* customerDetail.addressStreet=addressStreet;*/
				    		customerDetail.addressCity = city;
				    		customerDetail.addressState = inputState;
				    		customerDetail.addressZipCode = zipCode;
				    		
				    		customerDetail.livingSince = livingSince;
				    		appUserDetails.monthlyRent = monthlyRent;
				    		//appUserDetails.isSellYourhome = isSellYourhome;
				    		
				    		//user.customerDetail = customerDetail;
				    		
				    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
				    		
				    		appUserDetails.user.customerDetail = customerDetail;
				    		
				    		
				    		if(isSellYourhome =='Yes')
				    		appUserDetails.homeToSell = true;
				    		else
				    		appUserDetails.homeToSell = false;
				    		
				    		//appUserDetails.buyHome = buyHome;
				    		//alert(JSON.stringify(appUserDetails));
				    		
				
				    		
				    		//if(appUserDetails.propertyTypeMaster){
				    		/*appUserDetails.propertyTypeMaster.propStreetAddress=propAddress;*/
				    		appUserDetails.propertyTypeMaster.propState=propState;
				    		appUserDetails.propertyTypeMaster.propCity=propCity;
				    		appUserDetails.propertyTypeMaster.homeZipCode=propZipCode;
				    		//}
    		
    						saveAndUpdateLoanAppForm(appUserDetails ,paintloanamountBuyApp());

	                    }else{
	                    	 if(response.resultObject == zipcode_isnot_valid){
                        		 invalidStateZipCodeMsg =response.resultObject; 
                        	 }else{
                        		 invalidStateZipCodeMsg =response.resultObject;
                        	 }
                             $('input[name="propZipCode"]').next('.err-msg').html(invalidStateZipCodeMsg).show();
                             $('input[name="propZipCode"]').addClass('ce-err-input').show();
				       		return false;

	                    }
	                }
            	});
		            
    	}else{
    		paintloanamountBuyApp();
    	}
        	        	
    });

    $('#app-right-panel').append(saveAndContinueButton);
    if(!applicationLocked){
	    addStateCityZipLookUp();
	    addCityStateZipLookUpForProperty();
   	}
}


function paintloanamountBuyApp() {
    $('#app-right-panel').html("");
    quesContxts = [];
    var dwnPaymnt=showValue(getFloatValue(appUserDetails.purchaseDetails.housePrice)-getFloatValue(appUserDetails.purchaseDetails.loanAmount));
    if(getFloatValue(appUserDetails.purchaseDetails.housePrice) == undefined)
    	dwnPaymnt="";
    
    var questions = [{
            "type": "desc",
            "text": "Desired purchase price?",
            "name": "housePrice",
            "value": ""
        }, {
            "type": "dwnPayment",
            "text": "Down payment?",
            "name": "dwnPayment",
            "value": dwnPaymnt
        }
    ];
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var contxt = getQuestionContext(question, $('#app-right-panel'), appUserDetails.purchaseDetails);
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
       
    	if($(this).html()!=next){
    	
	    	appUserDetails.purchaseDetails.housePrice = $('input[name="housePrice"]').val();
	        appUserDetails.purchaseDetails.loanAmount = getFloatValue(appUserDetails.purchaseDetails.housePrice)-getFloatValue($('input[name="dwnPayment"]').val());
	        
	        var questionOne=validateInput($('input[name="housePrice"]'),$('input[name="housePrice"]').val(),message);
	        var questionTwo=validateInput($('input[name="dwnPayment"]'),$('input[name="dwnPayment"]').val(),message);
		     if(!questionOne){
		    	 return false;
		     }else if(!questionTwo){
		    	 return false;
		     }
             var result=validateDownPaymentOrPurchasePrice($('input[name="housePrice"]'),$('input[name="dwnPayment"]'));
             if(!result){
            	 return false;
             }
             appUserDetails.propertyTypeMaster.propStreetAddress=appUserDetails.propertyTypeMaster.propStreetAddress;
		     appUserDetails.propertyTypeMaster.propState=appUserDetails.propertyTypeMaster.propState;
		     appUserDetails.propertyTypeMaster.propCity=appUserDetails.propertyTypeMaster.propCity;
     
            saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2);
    	}else{
    		// when click on next button
    		paintCustomerApplicationPageStep2();
    	}
    	

    });
    $('#app-right-panel').append(saveAndContinueButton);
    //$('#ce-refinance-cp').html(quesCont);
}


function paintWhereYouLiveStep(){

	quesContxts = [];
	$('#app-right-panel').html("");
	
    var questions = [
                     {
							"type" : "desc",
							"text" : "Please provide the zipcode where you want buy a home.",
							"name" : "buyhomeZipPri",
							"value" : ""
						}
                 ];
    
    for(var i=0;i<questions.length;i++){
		var question=questions[i];
		var contxt=getQuestionContext(question,$('#app-right-panel'));
		contxt.drawQuestion();
		
		quesContxts.push(contxt);
	}
	
    var addRemoveRow = getAddRemoveButtonRow("buyhomeZipPri");
    
    $('#app-right-panel').find('.app-ques-wrapper').append(addRemoveRow);
    
    var btn=$(addRemoveRow).find(".add-btn");
    if(appUserDetails.purchaseDetails.buyhomeZipSec&&appUserDetails.purchaseDetails.buyhomeZipSec!=""){
        addZipField("buyhomeZipPri",$(btn),appUserDetails.purchaseDetails.buyhomeZipSec);
    }
    if(appUserDetails.purchaseDetails.buyhomeZipTri&&appUserDetails.purchaseDetails.buyhomeZipTri!=""){
        addZipField("buyhomeZipPri",$(btn),appUserDetails.purchaseDetails.buyhomeZipTri);
    }
    
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    
    var saveAndContinueButton = $('<div>').attr({
	    "class": "cep-button-color ce-save-btn"
	}).html(buttonText).on('click', function() {
		 
		if($(this).html()!=next){
			var isSuccess=validateInput( $('input[name="buyhomeZipPri"]'), $('input[name="buyhomeZipPri"]').val(),"Please enter a valid 5-digit zipcode");
		    if(!isSuccess){
		    	return false;
		    }else{
		    	 if($('input[name="buyhomeZipPri"]').val().length >5 ||$('input[name="buyhomeZipPri"]').val().length < 5){

		    		 $('input[name="buyhomeZipPri"]').next('.err-msg').html("Please enter a valid 5-digit zipcode").show();
		    		 $('input[name="buyhomeZipPri"]').addClass('ce-err-input').show();
            		 return false;
            	 }
		    }
		    isCityOrZipKnown =quesContxts[0].value; 
		    buyhomeZipPri = $('input[name="buyhomeZipPri"]').val();
		    buyhomeZipSec = $('input[name="buyhomeZipPri1"]').val();
		    buyhomeZipTri = $('input[name="buyhomeZipPri2"]').val();
		
		// alert('buyhomeZipPri'+buyhomeZipPri);
		 //		 alert('buyhomeZipSec'+buyhomeZipSec);
		 	//	 		 alert('buyhomeZipTri'+buyhomeZipTri);
		  
		    if(appUserDetails.purchaseDetails != null){
				 
				 purchaseDetails = appUserDetails.purchaseDetails;
			 }
			  purchaseDetails.buyhomeZipPri = buyhomeZipPri;
			  purchaseDetails.buyhomeZipSec=buyhomeZipSec;
			  purchaseDetails.buyhomeZipTri=buyhomeZipTri;
			  appUserDetails.purchaseDetails = purchaseDetails;
		
		   saveAndUpdateLoanAppForm(appUserDetails ,paintCustomerApplicationPageStep2);
		}else{
			// when click on next button
			paintCustomerApplicationPageStep2();
		}
	 });
	
    
    $('#app-right-panel').append(saveAndContinueButton);


}

function addZipField(fieldName,element,value){

        var inputField = $('input[name="'+fieldName+'"]');
        
        var inputCont = $('<div>').attr({
        	"class" : "app-options-cont"
        });
        
        var inputElement = $('<input>').attr({
            "name" : fieldName+inputField.parent().parent().find(".app-options-cont").size(),
            "class" : "app-input"
        });
        
        inputCont.append(inputElement);
        if(value)
            inputElement.val(value);
        //alert('e.data.fieldName'+e.data.fieldName+inputField.parent().children('input').size())
        var numberOfInputs = inputField.parent().parent().children('.app-options-cont').size();
        
        if(numberOfInputs<3){
            inputField.parent().parent().find('.add-remove-row').before(inputCont);
            if(numberOfInputs >= 2){
                $(element).hide();
            }
            
            inputField.parent().parent().children('.app-options-cont').find('.remove-btn').remove();
            
            if(numberOfInputs > 0){
            	var removeBtn = $('<div>').attr({
            		"class" : "remove-btn"
            	}).text("Remove")
            	.bind('click',{"fieldName":fieldName},function(e){
            		var inputField = $('input[name="'+e.data.fieldName+'"]');
            		
            		$(element).show();
            		if(inputField.parent().parent().children('.app-options-cont').size()>1){
            			$(this).parent().remove();
            		}
            		if(inputField.parent().parent().children('.app-options-cont').size()==1){
            			inputField.parent().parent().children('.app-options-cont').find('.remove-btn').remove();
            		}
            	});
            	inputField.parent().parent().children('.app-options-cont').append(removeBtn);
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
        addZipField(e.data.fieldName,$(this));
	});
	
	return container.append(addBtn);
}


function paintSpouseSaleOfCurrentHome() {
    
	//appProgressBaar(1);
	//$('#app-right-panel').html('');
	var questionsContainer = $('<div>').attr({
        "class": "app-ques-container"
    });
	if(appUserDetails.homeToSell == true){
		
		var quesHeaderTxt = "Sale of your current home";
	
	    var quesHeaderTextCont = $('<div>').attr({
	        "class": "app-ques-header-txt"
	    }).html(quesHeaderTxt);
	
	    var questions = [
	    {
	        type: "desc",
	        text: "What's the listing price of your current home?",
	        name: "spouseHomeListPrice",
	        value: appUserDetails.customerSpouseDetail.currentHomePrice
	    }, {
	        type: "desc",
	        text: "What's the mortage balance of your current home?",
	        name: "spouseHomeMortgageBalance",
	        value: appUserDetails.customerSpouseDetail.currentHomeMortgageBalance
	    }, {
	        type: "desc",
	        //NEXNF-538
	       // text: "How much from this sale do you intend to purchase towards your new home ?",
	        text: "How much of the sale proceeds do you plan to use towards your new home?",
	        name: "spouseInvestmentInHome",
	        value: appUserDetails.customerSpouseDetail.newHomeBudgetFromsale
	    }];
	
	    var hmSellContainer = getQuestionsContainer(questions);
        questionsContainer.append(quesHeaderTextCont).append(hmSellContainer);

	}
	
    
	/*var quesHeaderTxt2 = "My Assets";

    var quesHeaderTextCont2 = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt2);
    questionsContainer.append(quesHeaderTextCont2);

    var skipQuestions = $('<div>').attr({
    	"class" : "ce-option-checkbox myassets"
    }).html("No thanks, let's move on")
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
    questionsContainer.append(skipQuestions).append(assetQuestionsWrapper);

    var bankAccountDiv = bankAccount(assetQuestionsWrapper,true,0);

    var retirementAccountsDiv = retirementAccounts(assetQuestionsWrapper,true,1);
     
    var otherAccountDiv = otherAccount(assetQuestionsWrapper,true,2);*/
     
    return questionsContainer;
}



function getPopupQuestionsContainer(questions,value) {
    var questionsContainer = $('<div>').attr({
        "class": "ce-option-ques-wrapper"
    });

    var idContainer = $('<div>').attr({
        "class" : "ce-rp-ques-text"
    });
    
    
    var idVal="";
       
    if(value && value.id)
        idVal=value.id;
    var idValue = $('<input>').attr({
        "class" : "ce-input",
        "name" : "id",
        "type":"hidden"
    });
    if(idVal!=""){
        idValue.attr("value",idVal);
    }
    idContainer.append(idValue);
    questionsContainer.append(idContainer);

    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var val;
        if(value&&value[question.name])
            val=value[question.name];
        var quesCont = getApplicationQuestion(question,val);
        questionsContainer.append(quesCont);
    }
    return questionsContainer;
}



function saleYourCurrentHome(){
	
    var currentHomePrice = "";
	if(appUserDetails.propertyTypeMaster.currentHomePrice)
		currentHomePrice = appUserDetails.propertyTypeMaster.currentHomePrice;
	var homemortgagebalance ="";
	homemortgagebalance = appUserDetails.propertyTypeMaster.currentHomeMortgageBalance;
	var inverstInPurchase = "";
		inverstInPurchase = appUserDetails.propertyTypeMaster.newHomeBudgetFromsale; 
	
	
	var quesHeaderTxt = "Sale of your current home";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

	    var questions = [
	    {
	        type: "desc",
	        text: "What's the listing price of your current home?",
	        name: "homelistprice",
	        value: currentHomePrice
	    }, {
	        type: "desc",
	        text: "What's the mortage balance of your current home?",
	        name: "homemortgagebalance",
	        value: homemortgagebalance
	    }, {
	        type: "desc",
	        //NEXNF-538
	       // text: "How much from this sale do you intend to purchase towards your new home ?",
	        text: "How much of the sale proceeds do you plan to use towards your new home?",
	        name: "inverstInPurchase",
	        value: inverstInPurchase
	    }];
	
	    var questionsContainer = getPopupQuestionsContainer(questions);
	    quesHeaderTextCont.append(questionsContainer);
	    
       return quesHeaderTextCont;	    
}



function bankAccount(parentContainer,isSpouse,value){
	
	var quesHeaderTxt = "Bank Accounts";

	var quesHeaderWrapper = $('<div>').attr({
	       "class": "app-account-wrapper"
	});
    var ary;
    if(isSpouse){
        ary=appUserDetails.customerSpouseBankAccountDetails
    }else{
        ary=appUserDetails.customerBankAccountDetails
    }
    var selectedClass="";
    if(ary&&ary.length>0){
        selectedClass="app-option-checked";
    }
    var quesHeaderTextCont = $('<div>').attr({
    	"class" : "ce-option-checkbox "+selectedClass,
    	"value":value
    }).html(quesHeaderTxt);
    quesHeaderWrapper.append(quesHeaderTextCont);

   var questionsWrapper = $('<div>').attr({
	  "class" : "hide ce-sub-option-wrapper" 
   });
   
    var addAccountBtn = $('<div>').attr({
      "class" : "add-btn add-account-btn" 
    }).html("Add Account").bind('click',function(e){
        addBankAccountComponent($(this));
    });
   
    questionsWrapper.append(addAccountBtn);
    quesHeaderWrapper.append(questionsWrapper);
    if(parentContainer){
        parentContainer.append(quesHeaderWrapper);
    }
    if(ary&&ary.length>0){
        for(var i=0;i<ary.length;i++){
            var value;
            if(isSpouse){
                value=ary[i].customerSpouseBankAccountDetails;
            }else{
                value=ary[i].customerBankAccountDetails;
            }
            addBankAccountComponent(addAccountBtn,value)
        }
    }
    
    return quesHeaderWrapper;
}
function addBankAccountComponent(element,value){ 
    var parentWrapper = $(element).closest('.ce-sub-option-wrapper');

    var questions = [{
        type: "select",
        text: "Account type",
        name: "accountSubType",
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
        name: "currentAccountBalance",
        value: ""
    }, {
        type: "desc",
        text: "How much from this account are you able to use towards the purchase for your new home? Your best guess is fine.",
        name: "amountForNewHome",
        value: ""
    }];
    if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
        return false;
    }

    parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();

    var questionsCont = getPopupQuestionsContainer(questions,value);
    questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));

    var removeAccountBtn = $('<div>').attr({
        "class" : "add-btn remove-account-btn" 
    }).html("Remove Account").bind('click',{"parentWrapper":parentWrapper},function(e){
         var parentWrapper=e.data.parentWrapper;
        $(this).closest('.ce-option-ques-wrapper').remove();
        if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
            parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
        }
    });
    parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
    $(parentWrapper).show();
}

function retirementAccounts(parentContainer,isSpouse,value){
	
	var quesRetirementAc = "Retirement Accounts";

    var quesHeaderWrapper = $('<div>').attr({
       "class": "app-account-wrapper"
    });
    var ary;
    if(isSpouse){
        ary=appUserDetails.customerSpouseRetirementAccountDetails
    }else{
        ary=appUserDetails.customerRetirementAccountDetails
    }
    var selectedClass="";
    if(ary&&ary.length>0){
        selectedClass="app-option-checked";
    }
    var quesHeaderText = $('<div>').attr({
    	"class" : "ce-option-checkbox "+selectedClass,
    	"value":value
    }).html(quesRetirementAc);
    quesHeaderWrapper.append(quesHeaderText);

    var addAccountBtn = $('<div>').attr({
        "class" : "add-btn add-account-btn" 
    }).html("Add Account").bind('click',function(e){
        addRetirementQuestion($(this));
    });
    var questionsWrapper = $('<div>').attr({
          "class" : "hide ce-sub-option-wrapper" 
    });
	questionsWrapper.append(addAccountBtn);
    quesHeaderWrapper.append(questionsWrapper);
    if(parentContainer){
        parentContainer.append(quesHeaderWrapper);
    }
    if(ary&&ary.length>0){
        for(var i=0;i<ary.length;i++){
            var value;
            if(isSpouse){
                value=ary[i].customerSpouseRetirementAccountDetails;
            }else{
                value=ary[i].customerRetirementAccountDetails;
            }
            addRetirementQuestion(addAccountBtn,value);
        }
    }
	return quesHeaderWrapper;
}
function addRetirementQuestion(element,value){
    var parentWrapper = $(element).closest('.ce-sub-option-wrapper');
    var questions = [{
       type: "select",
       text: "Account type",
       name: "accountSubType",
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
       name: "currentAccountBalance",
       value: ""
    }, {
       type: "desc",
       text: "How much from this account are you able to use towards the purchase for your new home? Your best guess is fine.?",
       name: "amountForNewHome",
       value: ""
    }];       
    if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
    return false;
    }
     
    parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();

    var questionsCont = getPopupQuestionsContainer(questions,value);
    questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));

    var removeAccountBtn = $('<div>').attr({
    "class" : "add-btn remove-account-btn" 
    }).html("Remove Account").bind('click',{"parentWrapper":parentWrapper},function(e){
        var parentWrapper=e.data.parentWrapper;
        $(this).closest('.ce-option-ques-wrapper').remove();
        if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
            parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
        }
    });
    parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
    $(parentWrapper).show();
}

function otherAccount(parentContainer,isSpouse,value){
    var quesHeader = "Other Accounts & Securities";

    var quesHeaderWrapper = $('<div>').attr({
       "class": "app-account-wrapper"
    });
    var ary;
    if(isSpouse){
        ary=appUserDetails.customerSpouseOtherAccountDetails
    }else{
        ary=appUserDetails.customerOtherAccountDetails
    }
    var selectedClass="";
    if(ary&&ary.length>0){
        selectedClass="app-option-checked";
    }
    var quesHeaderTextCont = $('<div>').attr({
    	"class" : "ce-option-checkbox "+selectedClass,
    	"value":value
    }).html(quesHeader);
    quesHeaderWrapper.append(quesHeaderTextCont);

    var questionsWrapper = $('<div>').attr({
    	  "class" : "hide ce-sub-option-wrapper" 
    });
    var addAccountBtn = $('<div>').attr({
      "class" : "add-btn add-account-btn" 
    }).html("Add Account").bind('click',function(e){
        addOtherAccountComponent($(this));
    });
	     
    questionsWrapper.append(addAccountBtn);
    quesHeaderWrapper.append(questionsWrapper);
    if(parentContainer)
        parentContainer.append(quesHeaderWrapper);
    if(ary&&ary.length>0){
        for(var i=0;i<ary.length;i++){
            var value;
            if(isSpouse){
                value=ary[i].customerSpouseOtherAccountDetails;
            }else{
                value=ary[i].customerOtherAccountDetails;
            }
            addOtherAccountComponent(addAccountBtn,value);
        }
    }
    return quesHeaderWrapper;
}
function addOtherAccountComponent(element,value){
    var parentWrapper = $(element).closest('.ce-sub-option-wrapper');
    var questions = [{
        type: "select",
        text: "Account type",
        name: "accountSubType",
        options: [{
            text: "Money Market",
            value: "0"
        }, {
            text: "Certificate of deposit",
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
        name: "currentAccountBalance",
        value: ""
    }, {
        type: "desc",
        text: "How much from this account are you able to use towards the purchase for your new home? Your best guess is fine.?",
        name: "amountForNewHome",
        value: ""
    }];
    if(parentWrapper.children('.ce-option-ques-wrapper').length >= 3){
        return false;
    }

    parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();

    var questionsCont = getPopupQuestionsContainer(questions,value);
    questionsCont.insertBefore(parentWrapper.find('.add-account-btn'));

    var removeAccountBtn = $('<div>').attr({
    "class" : "add-btn remove-account-btn" 
    }).html("Remove Account").bind('click',{"parentWrapper":parentWrapper},function(e){
        var parentWrapper=e.data.parentWrapper;
        $(this).closest('.ce-option-ques-wrapper').remove();
        if(parentWrapper.children('.ce-option-ques-wrapper').length == 1){
            parentWrapper.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
        }
    });
    parentWrapper.children('.ce-option-ques-wrapper').append(removeAccountBtn);
    $(parentWrapper).show();
}


function paintSaleOfCurrentHome() {
    
	
	
	var questionsContainer = $('<div>').attr({
        "class": "app-ques-container"
    });
   
	if(appUserDetails.homeToSell == true){
	
	    var saleYourCurrentHomeDIV = saleYourCurrentHome(); 
	    questionsContainer.append(saleYourCurrentHomeDIV);
     }

     /*var quesHeaderAssets = "My Assets";
     var quesMyAssetsCont = $('<div>').attr({
        "class": "app-ques-header-txt"
     }).html(quesHeaderAssets);
     
   
   
     questionsContainer.append(quesMyAssetsCont);
     
     var skipQuestions = $('<div>').attr({
    	"class" : "ce-option-checkbox myassets" 
     }).html("No thanks, let's move on")
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
     
       bank details
     var bankAccountDiv = bankAccount(assetQuestionsWrapper,"",0);
     //assetQuestionsWrapper.append(bankAccountDiv);

       Retirement bank details
     var retirementAccountsDiv = retirementAccounts(assetQuestionsWrapper,"",1);
     //assetQuestionsWrapper.append(retirementAccountsDiv);
     
      other bank details
     var otherAccountDiv = otherAccount(assetQuestionsWrapper,"",2);
     //assetQuestionsWrapper.append(otherAccountDiv);
       
     return questionsContainer.append(skipQuestions).append(assetQuestionsWrapper);*/ 
     return questionsContainer;
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

///////////////BuyHomeApplication /////////////////


//////////////////////END OF NEW CODE /////////////////////////////