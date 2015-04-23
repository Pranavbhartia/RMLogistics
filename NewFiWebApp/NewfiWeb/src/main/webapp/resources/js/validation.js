
var message = "Invalid Entry";



function appendErrorMessage(){
	
	var ErrMessage = $('<div>').attr({
		"class" : "err-msg hide"
	});
	
	return ErrMessage;
}

function validateInput(element,inputVal,message){
	var name=$(element).attr('name');
	//$('input[name="currentMortgageBalance"]')

	if(inputVal == undefined || inputVal == ""){
		$('input[name="' + name + '"]').next('.err-msg').html(message).show();
		$('input[name="' + name + '"]').addClass('ce-err-input').show();
		return false;

	}
	else{
		$('input[name="' + name + '"]').next('.err-msg').hide();
		$('input[name="' + name + '"]').removeClass('ce-err-input');
		return true;
	}
	
	
	
}
