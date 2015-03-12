//JavaScript functions for customer engagement pages

var refinanceTeaserRate = new Object();


function paintSelectLoanTypeQuestion(){
	
	$('#ce-main-container').html('');
	
	var rateIcon = $('<div>').attr({
		"class" : "ce-rate-icon"
	});
	
	var titleText = $('<div>').attr({
		"class" : "ce-title"
	}).html("A New way to Finance your home");
	
	$('#ce-main-container').append(rateIcon).append(titleText);
	
	var wrapper = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var optionsContainer = $('<div>').attr({
		"class" : "ce-ques-options-container clearfix"
	});
	
	var option1 = $('<div>').attr({
		"class" : "ce-ques-option float-left"
	}).html("Refinance")
	.on('click',function(){
		paintRefinanceMainContainer();
	});
	
	var option2 = $('<div>').attr({
		"class" : "ce-ques-option float-left"
	}).html("Buy a home").on('click',function(){
		paintBuyHomeContainer();
	});
	
	optionsContainer.append(option1).append(option2);
	
	var question = $('<div>').attr({
		"class" : "ce-ques-text"
	}).html("Why Pay thousands more to use a loan officer ?<br/>With newfi, saving weeks of headache and thousands of dollars is easy.");
	
	wrapper.append(optionsContainer).append(question);
	
	$('#ce-main-container').append(wrapper);
}

function paintRefinanceMainContainer(){
	$('#ce-main-container').html('');
	var wrapper = $('<div>').attr({
		"class" : "ce-refinance-wrapper clearfix"
	});
	
	var leftPanel = getRefinanceLeftPanel();
	
	var centerPanel = $('<div>').attr({
		"id" : "ce-refinance-cp",
		"class" : "ce-cp float-left"
	});
	
	wrapper.append(leftPanel).append(centerPanel);
	$('#ce-main-container').append(wrapper);
	
	paintRefinanceQuest1();
}


var itemsList = [
                 "Living Situation",
                 "Home Information",
                 "Desired Location",
                 "Who's on the loan",
                 "My Money",
                 "Government Questions",
                 "My Credit",
                 "My Solutions"
                 ];

function getRefinanceLeftPanel(){
	var container = $('<div>').attr({
		"class" : "ce-lp float-left"
	});
	
	for(var i=0;i<itemsList.length; i++){
		var itemCompletionStage = "";
		if(i < 2){
			itemCompletionStage = "COMPLETE";
		}else if(i == 2){
			itemCompletionStage = "IN_PROGRESS";
		}else{
			itemCompletionStage = "NOT_STARTED";
		}
		var itemCont = getRefinanceLeftPanelItem(itemsList[i],i+1,itemCompletionStage);
		container.append(itemCont);
	}
	
	return container;
}

function getRefinanceLeftPanelItem(itemTxt, stepNo, itemCompletionStage){
	var itemCont = $('<div>').attr({
		"class" : "ce-lp-item clearfix",
		"data-step" : stepNo
	});
	
	var leftIcon = $('<div>').attr({
		"class" : "ce-lp-item-icon float-left"
	});
	
	if(itemCompletionStage == "COMPLETE"){
		itemCont.addClass('ce-lp-complete');
	}else if(itemCompletionStage == "NOT_STARTED"){
		itemCont.addClass('ce-lp-not-started');
		leftIcon.html(stepNo);
	}else if(itemCompletionStage == "IN_PROGRESS"){
		itemCont.addClass('ce-lp-in-progress');
		leftIcon.html(stepNo);
	}
	
	var textCont = $('<div>').attr({
		"class" : "ce-lp-item-text float-left"
	}).html(itemTxt);
	
	return itemCont.append(leftIcon).append(textCont);
}

function paintRefinanceQuest1(){
	var quesText = "Why do you want to refinance?";
	
	var options = [
	    {
	    	"text" : "Lower My Monthly Payment",
	    	"onselect" : paintRefinanceLiveNow,
	    	"value" : 0
	    },
	    {
	    	"text" : "Pay Off My Mortgage Faster",
	    	"onselect" : paintRefinanceLiveNow,
	    	"value" : 1
	    },
	    {
	    	"text" : "Take Cash Out of My Home",
	    	"onselect" : paintRefinanceLiveNow,
	    	"value" : 2
	    }
	    ];
	
	var quesCont = getMutipleChoiceQuestion(quesText,options,"refinanceOption");
	$('#ce-refinance-cp').html(quesCont);
}


function getMutipleChoiceQuestion(quesText,options,name){
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	for(var i=0; i<options.length; i++){
		var option = $('<div>').attr({
			"class" : "ce-option",
			"value":options[i].value
		}).html(options[i].text)
		.bind('click',{"option":options[i],"name" : name},function(event){
			var key = event.data.name;

			refinanceTeaserRate[key] = event.data.option.value;

			event.data.option.onselect();
		});
		optionContainer.append(option);
	}
	
	return container.append(quesTextCont).append(optionContainer);
}

function getTextQuestion(quesText,clickEvent,name){
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : name
	}); 
	
	
	optionContainer.append(inputBox);
	
	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
	.bind('click',{'clickEvent':clickEvent,"name":name},function(event){
		var key = event.data.name;
		refinanceTeaserRate[key] = $('input[name="'+key+'"]').val();
		event.data.clickEvent();
	});
	
	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}

function paintCurrentMorgageStep(){
	
}


function paintRefinanceLiveNow(){
	
	var quesTxt = "Where You Live Now ?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceCurrentAddress,"whereLiveNow");
	$('#ce-refinance-cp').html(quesCont);
}


function paintRefinanceCurrentAddress(){
	
	var quesTxt = "What Is Your Current Address?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceCity,"currentAddress");
	$('#ce-refinance-cp').html(quesCont);
}


function paintRefinanceCity(){
	
	var quesTxt = "What Is Your City?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceState,"city");
	$('#ce-refinance-cp').html(quesCont);
	
} 


function paintRefinanceState(){
	
	var quesTxt = "What Is Your State?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceZip,"state");
	$('#ce-refinance-cp').html(quesCont);
	
}


function paintRefinanceZip(){
	
	var quesTxt = "What Is Your ZipCode?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStartLiving,"zipCode");
	$('#ce-refinance-cp').html(quesCont);
	
}

function paintRefinanceStartLiving(){
	
	var quesTxt = "When did you start living here ?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep1a,"startLiving");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1a(){
	var quesTxt = "How many years are left on your mortgage?";
	
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep2,"yearLeftOnMortgage");
	
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1b() {
	var quesTxt = "How much cash do you want to take out?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep3,"cashTakeOut");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep2() {
	var quesTxt = "What is your current mortgage balance?";
	if(refinanceTeaserRate['refinanceOption'] == 0 || refinanceTeaserRate['refinanceOption'] == 1)
	{
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep3,"currentMortgageBalance");
	}
	if(refinanceTeaserRate['refinanceOption'] == 2)
	{
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep1b,"currentMortgageBalance");
	}
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep3() {
	var quesTxt = "What is your current mortgage payment?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep4,"currentMortgagePayment");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep4() {
	var quesTxt = "Does the monthly mortgage payment entered above include property taxes and/or homeowners insurance?";
	var options = [
	       	    {
	       	    	"text" : "Yes",
	       	    	"onselect" : paintRefinanceHomeWorthToday,
	       	    	"name":name,
	       	    	"value" : 1
	       	    },
	       	    {
	       	    	"text" : "No",
	       	    	"onselect" : paintRefinanceAnnualPropertyTaxes,
	       	    	"name":name,
	       	    	"value" : 0
	       	    }
	       	    ];
	       	
   	var quesCont = getMutipleChoiceQuestion(quesTxt,options,"includeTaxes");
   	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceAnnualPropertyTaxes() {
	var quesTxt = "How much is your annual property taxes?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceAnnualHomeownersInsurance,"annualPropertyTaxes");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceAnnualHomeownersInsurance() {
	var quesTxt = "How much is your annual homeowners insurance?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceHomeWorthToday,"annualHomeownersInsurance");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceHomeWorthToday() {
	var quesTxt = "Approximately what is your home worth today?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceMilitaryLoans,"homeWorthToday");
	$('#ce-refinance-cp').html(quesCont);
}


function paintRefinanceMilitaryLoans(){
	
	var quesTxt = "Are you eligible for, and interested in, VA/military loans?";
	var options = [
	       	    {
	       	    	"text" : "Yes",
	       	    	"onselect" : paintRefinanceMyIncome,
	       	    	"name":name,
	       	    	"value" : 1
	       	    },
	       	    {
	       	    	"text" : "No",
	       	    	"onselect" : paintRefinanceMyIncome,
	       	    	"name":name,
	       	    	"value" : 0
	       	    }
	       	    ];
	       	
   	var quesCont = getMutipleChoiceQuestion(quesTxt,options,"isVeteran");
   	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceMyIncome(){
	
	var quesTxt = "Select all that apply";
	var options = [
		       	    {
		       	    	"text" : "Employed",
		       	    	"onselect" : paintRefinanceEmployed,
		       	    	"name":name,
		       	    	"value" : 0
		       	    },
		       	    {
		       	    	"text" : "Self-employed",
		       	    	"onselect" :paintRefinanceSelfEmployed,
		       	    	"name":name,
		       	    	"value" : 1
		       	    },
		       	    {
		       	    	"text" : "Social Security Income/Disability",
		       	    	"onselect" : paintRefinanceDisability,
		       	    	"name":name,
		       	    	"value" : 2
		       	    },
		       	    {
		       	    	"text" : "Pension/Retirement/401(k)",
		       	    	"onselect" : paintRefinancePension,
		       	    	"name":name,
		       	    	"value" : 3
		       	    }
		       	  ];
	var quesCont = getMutipleChoiceQuestion123(quesTxt,options,"isVeteran");
		
   	$('#ce-refinance-cp').html(quesCont);
	
}


function paintRefinanceEmployed(divId){
	
	var quesTxt = "About how much do you make a year";
	var quesCont = getMultiTextQuestion(quesTxt);
	$('#ce-option_'+divId).toggle();
	$('#ce-option_'+divId).html(quesCont);
}

function paintRefinanceSelfEmployed(divId){
	
	var quesTxt = "How much do you make a year?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name": "selfEmployed"
	}); 
	
	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_'+divId).toggle();
	$('#ce-option_'+divId).html(container);
}

function paintRefinanceDisability(divId){
	
	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name": "disability"
	}); 
	
	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_'+divId).toggle();
	$('#ce-option_'+divId).html(container);
}

function paintRefinancePension(divId){
	
	var quesTxt = "About how much do you get monthly?";

	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name": "pension"
	}); 
	
	optionContainer.append(inputBox);
	container.append(quesTextCont).append(optionContainer);

	$('#ce-option_'+divId).toggle();
	$('#ce-option_'+divId).html(container);
}



function getMutipleChoiceQuestion123(quesText,options,name){
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	for(var i=0; i<options.length; i++){
		
		var optionIncome = $('<div>').attr({
			"class" : "hide",
			"id" :"ce-option_"+i
		});
		
		var option = $('<div>').attr({
			"class" : "ce-option",
			"value":options[i].value
		}).html(options[i].text)
		.bind('click',{"option":options[i],"name" : name},function(event){
			var key = event.data.name;
			refinanceTeaserRate[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);
		});
		
		optionContainer.append(option).append(optionIncome);
	}
	
	
	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
	.bind('click',function(){
		refinanceTeaserRate["beforeTax"] =  $('input[name="beforeTax"]').val();
		refinanceTeaserRate["workPlace"] =  $('input[name="workPlace"]').val();
		refinanceTeaserRate["startWorking"] =  $('input[name="startWorking"]').val();
		refinanceTeaserRate["selfEmployed"] =  $('input[name="selfEmployed"]').val();
		refinanceTeaserRate["disability"] =  $('input[name="disability"]').val();
		refinanceTeaserRate["pension"] =  $('input[name="pension"]').val();
		
		paintRefinanceDOB();
	});
	
	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}


function getMultiTextQuestion(quesText){
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper",
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text",
	}).html(quesText);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont",
	}); 
	
	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text",
	}).html("Before Tax");
	
	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name": "beforeTax",
	}); 
	
	quesTextCont1.append(inputBox1);
	
	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("Where Do You Work ?");
	
	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name": "workPlace"
	}); 
	
	quesTextCont2.append(inputBox2);
	
	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html("When Did You Start Wokring ?");
	
	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name": "startWorking"
	}); 
	
	quesTextCont3.append(inputBox3);

	optionContainer.append(quesTextCont1).append(quesTextCont2).append(quesTextCont3);
	
	return container.append(quesTextCont).append(optionContainer);
}


function paintRefinanceDOB(){
	
	var quesTxt = "Please enter your birthdate.";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceSSN,"dob");
	$('#ce-refinance-cp').html(quesCont);
}


function paintRefinanceSSN(){
	
	var quesTxt = "Please enter your social security number.";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceCreditScore,"ssn");
	$('#ce-refinance-cp').html(quesCont);
	/*var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox = $('<input>').attr({
		"class" : "ce-input",
		"name" : "ssn"
	}); 
		
	optionContainer.append(inputBox);
	
	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
	.bind('click',function(){
	
		
		var ssn = $('input[name="ssn"]').val();
		
		
		if(ssn == "" || ssn == null){
			
			paintRefinanceCreaditScore();
		}else{
			
			refinanceTeaserRate["ssn"] = $('input[name="ssn"]').val();
			paintRefinancePhoneNumber();
			container.append(quesTextCont).append(optionContainer).append(saveBtn);
				
			$('#ce-refinance-cp').html(container);
		}
	
	});
	
*/
	
}

function paintRefinanceCreditScore(){
	
	var quesTxt = "Give Your Credit Score";
	var quesCont = getTextQuestion(quesTxt,paintRefinancePhoneNumber,"creditscore");
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinancePhoneNumber(){
	
	var quesTxt = "Please enter your phone number";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceSeeRates,"phoneNumber");
	$('#ce-refinance-cp').html(quesCont);
}


function paintRefinanceSeeRates(){
	
	var quesTxt = "Analyze & Adjust Your Numbers";
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);
	
	container.append(quesTextCont);
	
	$('#ce-refinance-cp').html(container);
	
	$.ajax({
	   
	   url:"rest/calculator/findteaseratevalue",
	   type:"POST",
	   data:{"teaseRate":JSON.stringify(refinanceTeaserRate)},
	   datatype:"application/json",
	   success : function(data){
		  alert("success");
          //$('#teaserresult').html(teaserresult);
		   //alert(teaserresult);
	   },
	   error :function(){
		   alert("error");
	   }
	   
   });
}

/*function paintRefinanceStep7() {
	var quesTxt = "Analyze & Adjust Your Numbers";
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt);
	
	container.append(quesTextCont);
	
	$('#ce-refinance-cp').html(container);
	
}
*/

/*
 * This section is a set of function to ask question for buy a home
 * 
 * */


/*function paintBuyHomeContainer(){
	
	
}
*/