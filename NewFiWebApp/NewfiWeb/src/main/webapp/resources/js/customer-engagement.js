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
	}).html("Refinance");
	
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