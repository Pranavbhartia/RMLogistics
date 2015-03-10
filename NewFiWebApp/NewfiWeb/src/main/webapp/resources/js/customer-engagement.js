//JavaScript functions for customer engagement pages

function paintSelectLoanTypeQuestion(){
	
	$('#ce-main-container').html('');
	
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
	}).html("Buy a home");
	
	optionsContainer.append(option1).append(option2);
	
	var question = $('<div>').attr({
		"class" : "ce-ques-text"
	}).html("Why Pay thousands more to use a loan officer ?");
	
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


function getRefinanceLeftPanel(){
	var container = $('<div>').attr({
		"class" : "ce-lp float-left"
	});
	return container;
}

function paintRefinanceQuest1(){
	var quesText = "Why do you want to refinance?";
	
	var options = [
	    {
	    	"text" : "Lower My Monthly Payment",
	    	"onselect" : paintRefinanceStep2
	    },
	    {
	    	"text" : "Pay Off My Mortgage Faster",
	    	"onselect" : paintRefinanceStep1a
	    },
	    {
	    	"text" : "Take Cash Out of My Home",
	    	"onselect" : paintRefinanceStep1b
	    }
	    ];
	
	var quesCont = getMutipleChoiceQuestion(quesText,options);
	$('#ce-refinance-cp').html(quesCont);
}


function getMutipleChoiceQuestion(quesText,options){
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
			"class" : "ce-option"
		}).html(options[i].text)
		.bind('click',{'clickEvent':options[i].onselect},function(event){
			event.data.clickEvent();
		});
		optionContainer.append(option);
	}
	
	return container.append(quesTextCont).append(optionContainer);
}

function getTextQuestion(quesText,clickEvent){
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
		"class" : "ce-input"
	}); 
	
	optionContainer.append(inputBox);
	
	var saveBtn = $('<div>').attr({
		"class" : "ce-save-btn"
	}).html("Save & Continue")
	.bind('click',{'clickEvent':clickEvent},function(event){
		event.data.clickEvent();
	});
	
	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}

function paintRefinanceStep1a(){
	var quesTxt = "How many years are left on your mortgage?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep2);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep1b() {
	var quesTxt = "How much cash do you want to take out?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep2);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep2() {
	var quesTxt = "What is your current mortgage balance?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep3);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep3() {
	var quesTxt = "What is your current mortgage payment?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep4);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep4() {
	var quesTxt = "Does the monthly mortgage payment entered above include property taxes and/or homeowners insurance?";
	var options = [
	       	    {
	       	    	"text" : "Yes",
	       	    	"onselect" : paintRefinanceStep4a
	       	    },
	       	    {
	       	    	"text" : "No",
	       	    	"onselect" : paintRefinanceStep5
	       	    }
	       	    ];
	       	
   	var quesCont = getMutipleChoiceQuestion(quesTxt,options);
   	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep4a() {
	var quesTxt = "How much is your annual property taxes?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep4b);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep4b() {
	var quesTxt = "How much is your annual homeowners insurance?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep5);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep5() {
	var quesTxt = "Approximately what is your home worth today?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep6);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep6() {
	var quesTxt = "What is the zip code of your home?";
	var quesCont = getTextQuestion(quesTxt,paintRefinanceStep7);
	$('#ce-refinance-cp').html(quesCont);
}

function paintRefinanceStep7() {
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