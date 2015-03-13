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
                 "Loan Purpose",
                 "Home Information",
                 "Mortgage Information",
                 "My Money",
                 "My Credit"
                 ];

function getRefinanceLeftPanel(){
	var container = $('<div>').attr({
		"class" : "ce-lp float-left"
	});
	
	for(var i=0;i<itemsList.length; i++){
		var itemCompletionStage = "NOT_STARTED";
		
		var itemCont = getRefinanceLeftPanelItem(itemsList[i],i+1,itemCompletionStage);
		container.append(itemCont);
	}
	
	return container;
}

function getRefinanceLeftPanelItem(itemTxt, stepNo, itemCompletionStage){
	var itemCont = $('<div>').attr({
		"class" : "ce-lp-item clearfix",
		"data-step" : stepNo,
		"id" :"progressBaarId_"+stepNo
	});
	
	var leftIcon = $('<div>').attr({
		"class" : "ce-lp-item-icon float-left",
		"id":"stepNoId_"+stepNo
	});
	
	/*if(itemCompletionStage == "COMPLETE"){
		itemCont.addClass('ce-lp-complete');
	}else if(itemCompletionStage == "NOT_STARTED"){*/
		itemCont.addClass('ce-lp-not-started');
		leftIcon.html(stepNo);
	/*}else if(itemCompletionStage == "IN_PROGRESS"){
		itemCont.addClass('ce-lp-in-progress');
		leftIcon.html(stepNo);
	}*/
	
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
	    	"value" : "lowerMonthlyPayment"
	    },
	    {
	    	"text" : "Pay Off My Mortgage Faster",
	    	"onselect" : paintRefinanceLiveNow,
	    	"value" : "payOffMortgage"
	    },
	    {
	    	"text" : "Take Cash Out of My Home",
	    	"onselect" : paintRefinanceLiveNow,
	    	"value" : "takeCashOut"
	    }
	    ];
	
	var quesCont = getMutipleChoiceQuestion(quesText,options,"refinanceOption");
	$("#progressBaarId_1").addClass('ce-lp-in-progress');
	$('#ce-refinance-cp').html(quesCont);
	$('#stepNoId_1').html("1");
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



function paintRefinanceLiveNow(){

	var quesTxt1 = "Where You Live Now ?";
	var quesTxt2 = "Your Current Address ?"; 
	var quesTxt3 = "City"; 
	var quesTxt4 = "State"; 
	var quesTxt5 = "ZIP Code"; 
	
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont1 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt1);
	
	var optionContainer1  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox1 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "liveNow"
	}); 
	
	var quesTextCont2 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt2);
	
	var optionContainer2  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox2 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "currentAddress"
	}); 
	
	var quesTextCont3 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt3);
	
	var optionContainer3  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox3 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "city"
	});
	
	
	var quesTextCont4 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt4);
	
	var optionContainer4  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox4 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "state"
	}); 
	
	var quesTextCont5 = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesTxt5);
	
	var optionContainer5  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputBox5 = $('<input>').attr({
		"class" : "ce-input",
		"name" : "zipCode"
	}); 
	
	
	quesTextCont1.append(optionContainer1).append(inputBox1);
	quesTextCont2.append(optionContainer2).append(inputBox2);
	quesTextCont3.append(optionContainer3).append(inputBox3);
	quesTextCont4.append(optionContainer4).append(inputBox4);
	quesTextCont5.append(optionContainer5).append(inputBox5);
	
	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
	.bind('click',function(event){
		
		refinanceTeaserRate["liveNow"] =  $('input[name="liveNow"]').val();
		refinanceTeaserRate["currentAddress"] =  $('input[name="currentAddress"]').val();
		refinanceTeaserRate["city"] =  $('input[name="city"]').val();
		refinanceTeaserRate["state"] =  $('input[name="state"]').val();
		refinanceTeaserRate["zipCode"] =  $('input[name="zipCode"]').val();
		
		paintRefinanceStartLiving();
	});
		
	$('#ce-refinance-cp').html(container.append(quesTextCont1).append(quesTextCont2).append(quesTextCont3).append(quesTextCont4).append(quesTextCont5).append(saveBtn));

	
	$("#progressBaarId_1").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	$('#stepNoId_1').html("");
	
	$("#progressBaarId_2").addClass('ce-lp-in-progress');

	$('#stepNoId_2').html("2");
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
	
	$("#progressBaarId_2").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	$('#stepNoId_2').html("");
	
	$("#progressBaarId_3").addClass('ce-lp-in-progress');
	$('#stepNoId_3').html("3");
	
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1a(){
	
	var quesTxt = "How many years are left on your mortgage?";
	
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep2,"yearLeftOnMortgage");
	
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep2() {
	var quesTxt = "What is your current mortgage balance?";
	var quesCont ="";
	if(refinanceTeaserRate['refinanceOption'] == 'lowerMonthlyPayment' || refinanceTeaserRate['refinanceOption'] == 'payOffMortgage')
	{
	  quesCont = getTextQuestion(quesTxt,paintRefinanceStep3,"currentMortgageBalance");
	}
	if(refinanceTeaserRate['refinanceOption'] == 'takeCashOut')
	{
	   quesCont = getTextQuestion(quesTxt,paintRefinanceStep1b,"currentMortgageBalance");
	}
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1b() {
	var quesTxt = "How much cash do you want to take out?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep3,"cashTakeOut");
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

function paintRefinanceHomeWorthToday() {
	var quesTxt = "Approximately what is your home worth today?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceMilitaryLoans,"homeWorthToday");
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
	var quesCont = paintRefinanceMyMoney(quesTxt,options,name);
	
	$("#progressBaarId_3").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	$('#stepNoId_3').html("");
	
	$("#progressBaarId_4").addClass('ce-lp-in-progress');
	$('#stepNoId_4').html("4");
	
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



function paintRefinanceMyMoney(quesText,options,name){
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
	
	$("#progressBaarId_4").removeClass('ce-lp-in-progress').removeClass('ce-lp-not-started').addClass('ce-lp-complete');
	$('#stepNoId_4').html("");	
	$("#progressBaarId_5").addClass('ce-lp-in-progress');
	$('#stepNoId_5').html("5");
	
	$('#ce-refinance-cp').html(quesCont);
}


function paintRefinanceSSN(){
	
	var quesTxt = "Please enter your social security number.";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceCreditScore,"ssn");
	$('#ce-refinance-cp').html(quesCont);
	
	
}

function paintRefinanceCreditScore(){
	
	if(refinanceTeaserRate["ssn"] == "" || refinanceTeaserRate["ssn"] == undefined ){
		
		var quesTxt = "Please give Your Credit Score";
		var quesCont = getTextQuestion(quesTxt,paintRefinancePhoneNumber,"creditscore");
		$('#ce-refinance-cp').html(quesCont);
	}else{
		paintRefinancePhoneNumber();
	}
	
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
		   var teaserresult = data;
	       obj =  JSON.parse(teaserresult);
	        alert(obj);
	   },
	   error :function(){
		   alert("error");
	   }
	   
   });
}
