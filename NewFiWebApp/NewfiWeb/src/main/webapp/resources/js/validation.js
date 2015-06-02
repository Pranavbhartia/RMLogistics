
var message = "Invalid Entry";
function appendMessage(data){
	var errMessageDiv=$('<div>').attr({
		"class":"cust-prof-err-mess"	
		}).html(data);
	return errMessageDiv;
}


function appendErrorMessage(){
	
	var ErrMessage = $('<div>').attr({
		"class" : "err-msg hide"
	});
	
	return ErrMessage;
}

function validateInput(element,inputVal,message){
	var name=$(element).attr('name');
	var width=$(element).css('width');
	//$('input[name="currentMortgageBalance"]')

	if(inputVal == undefined || inputVal == "" ){
	
			$('input[name="' + name + '"]').next('.err-msg').html(message).show();
			$('input[name="' + name + '"]').addClass('ce-err-input').show();
			$(".err-msg").css('width',width);
			return false;
		
		

	}else{
		if(inputVal == "$0" || inputVal == 0){
			$('input[name="' + name + '"]').next('.err-msg').html(feildShouldNotBeZero).show();
			$('input[name="' + name + '"]').addClass('ce-err-input').show();
			$(".err-msg").css('width',width);
			return false;
		}else{
			$('input[name="' + name + '"]').next('.err-msg').hide();
			$('input[name="' + name + '"]').removeClass('ce-err-input');
			return true;
		}
		
	}
	
	
	
	
}

function validateCheckbox(isStatus){

        for(var i=1;i<$('.ce-option-checkbox').length;i++){
	
		if($('.ce-option-checkbox[value='+i+']').hasClass('app-option-checked')){
			isStatus.push( $('.ce-option-checkbox[value='+i+']'));
		}
		
	   }
          return isStatus;
}

function validateInputOfChecked(isStatus){

		if(isStatus.attr('value')==0){
     	   var inputs = $("#ce-option_"+isStatus.attr('value')+"").find('.ce-option-ques-wrapper').find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input');
            for(var count=0;count<inputs.length;count++){
            	if(inputs[count].name!="customerEmploymentIncomeId"){
            		 var isStatus=validateInput(inputs[count],inputs[count].value,message);
                	 if(isStatus==false){
                		 return false;
                	 }
            	}
        	
           }
        }else{
     	   var inputs = $("#ce-option_"+isStatus.attr('value')+"").find('.ce-option-ques-wrapper').find('.ce-ques-wrapper').find('.ce-options-cont').find('input');
	            for(var count=0;count<inputs.length;count++){
	        	 var isStatus=validateInput(inputs[count],inputs[count].value,message);
	        	 if(isStatus==false){
	        		 return false;
	        	 }
        }
            
       
	}
                   
				   

}

function validateFormFeild(inputElement,divErrElement,message){
	var inputVal=$(inputElement).val();
	if(inputVal == undefined || inputVal == ""){
		$(inputElement).next('.err-msg').html(message).show();
		$(divErrElement).addClass('ce-err-input').show();
		return false;

	}
	else{
		$(inputElement).next('.err-msg').hide();
		$(divErrElement).removeClass('ce-err-input');
		return true;
	}
	
}
