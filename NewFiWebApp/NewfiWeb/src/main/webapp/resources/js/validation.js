
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

	if($('input[name="' + name + '"]').val() == undefined || $('input[name="' + name + '"]').val() == ""
		){
	
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
function validateDropDown(){
	if($(".soft-menu-wrapper").css('display')=="block"){
			$(".soft-menu-wrapper").hide();
		}
}
function validateCheckbox(isStatus){

        for(var i=0;i<$('.ce-option-checkbox').length;i++){
	
		if($('.ce-option-checkbox[value='+i+']').hasClass('app-option-checked')){
			isStatus.push( $('.ce-option-checkbox[value='+i+']'));
		}
		
	   }
          return isStatus;
}

function validateInputOfChecked(isStatus){
	
		if(isStatus.attr('value')==0){
			//validation done for single ce-option-ques-wrapper class
			var elements=$("#ce-option_"+isStatus.attr('value')+"").find('.ce-option-ques-wrapper');
			for(var size=0;size<elements.length;size++){
				 var inputs = $(elements[size]).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input');
		     	 var offset=$(elements[size]).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input').height();
		     	 //validating the inputs inside the ce-option-ques-wrapper class
		         for(var count=0;count<inputs.length;count++){
		            	if(inputs[count].name!="customerEmploymentIncomeId"){          		
		            		 var isStatus=validateInputsOfAssests(inputs[count],inputs[count].value,message,offset);
		                	 if(isStatus==false){
		                		 return false;
		                	 }
		            	}
			}      	
           }
        }else{
        	 var offset=$("#ce-option_"+isStatus.attr('value')+"").find('.ce-option-ques-wrapper').find('.ce-ques-wrapper').find('.ce-options-cont').find('input').offset().top;
     	     var inputs = $("#ce-option_"+isStatus.attr('value')+"").find('.ce-option-ques-wrapper').find('.ce-ques-wrapper').find('.ce-options-cont').find('input');
	            for(var count=0;count<inputs.length;count++){
	        	 var isStatus=validateInputsOfAssests(inputs[count],inputs[count].value,message,offset);
	        	 if(isStatus==false){
	        		 return false;
	        	 }
        }
            
       
	}
                   
				   

}
function validateInputsOfAssests(element,inputVal,message,offset){
	console.log(inputVal+"::::::"+offset);
	var name=$(element).attr('name');
	var width=$(element).css('width');
	var height=$('.header-wrapper').height();
	//$('input[name="currentMortgageBalance"]')

	if(inputVal == undefined || inputVal == ""
		){
	
			$(element).next('.err-msg').html(message).show();
			$(element).addClass('ce-err-input').show();
			$(".err-msg").css('width',width);
			if(name=="jobTitle"||name=="beforeTax"||name=="workPlace"||name=="startWorking"){
				$(window).scrollTop(offset+50);
			}else{
				$(window).scrollTop(offset-height-50);
			}
			
			//$(element).scrollTop(offset);
			return false;
		
		

	}else{
		if(inputVal == "$0" || inputVal == 0){
			$(element).next('.err-msg').html(feildShouldNotBeZero).show();
			$(element).addClass('ce-err-input').show();
			$(".err-msg").css('width',width);
			$(window).scrollTop(offset-height-50);
			//$(element).scrollTop(offset);
			return false;
		}else{
			$(element).next('.err-msg').hide();
			$(element).removeClass('ce-err-input');
			return true;
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
