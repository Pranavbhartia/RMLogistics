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
	
	var options = ["Lower My Monthly Payment","Pay Off My Mortgage Faster","Take Cash Out of My Home"];
	
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
		}).html(options[i]);
		optionContainer.append(option);
	}
	
	return container.append(quesTextCont).append(optionContainer);
}

function getTextQuestion(quesText){
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
	});
	
	var quesTextCont = $('<div>').attr({
		"class" : "ce-rp-ques-text"
	}).html(quesText);
	
	var optionContainer  = $('<div>').attr({
		"class" : "ce-options-cont"
	}); 
	
	var inputCont = $('<input>').attr({
		"class" : "ce-input"
	}); 
	
	optionContainer.append(inputCont);
	
	return container.append(quesTextCont).append(optionContainer);
}