
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
	var height=$(element).offset().top-100;

	if($('input[name="' + name + '"]').val() == undefined || $('input[name="' + name + '"]').val() == ""){
	
			$('input[name="' + name + '"]').parent().find('.err-msg').html(message).show();
			$('input[name="' + name + '"]').addClass('ce-err-input').show();
			$(window).scrollTop(height);
			if(name=="propertyTaxesPaid"||name=="annualHomeownersInsurance"){
				$('input[name="' + name + '"]').parent().find('.err-msg').removeClass('float-left');
			}
			return false;
	}else{
		if(inputVal == "$0" || inputVal == 0){
			if(name!="zipCode"){
				$('input[name="' + name + '"]').parent().find('.err-msg').html(feildShouldNotBeZero).show();
				$('input[name="' + name + '"]').addClass('ce-err-input').show();
				$(window).scrollTop(height);
				if(name=="propertyTaxesPaid"||name=="annualHomeownersInsurance"){
					$('input[name="' + name + '"]').parent().find('.err-msg').addClass('float-left');
					
				}
				return false;
			}			
		}else{
			$('input[name="' + name + '"]').parent().find('.err-msg').hide();
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
		            		 var isStatus=validateInputsOfMyIncomePage(inputs[count],inputs[count].value,message,offset);
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
	        	 var isStatus=validateInputsOfMyIncomePage(inputs[count],inputs[count].value,message,offset);
	        	 if(isStatus==false){
	        		 return false;
	        	 }
        }
            
       
	}
                   
				   

}

function validateInputsOfMyIncomePage(element,inputVal,message,offset){

	var name=$(element).attr('name');
	var width=$(element).css('width');
	var height=$('.header-wrapper').height();
	//$('input[name="currentMortgageBalance"]')

	if(inputVal == undefined || inputVal == ""){
	
			$(element).next('.err-msg').html(message).show();
			$(element).addClass('ce-err-input').show();
			$(".err-msg").css('width',width);
			if(name=="jobTitle"||name=="beforeTax"||name=="workPlace"||name=="startWorking"){
				$(window).scrollTop(offset+50);
			}else{
				$(window).scrollTop(offset-height-50);
			}
			return false;
	}else{
		if(inputVal == "$0" || inputVal == 0){
			$(element).next('.err-msg').html(feildShouldNotBeZero).show();
			$(element).addClass('ce-err-input').show();
			$(".err-msg").css('width',width);
			if(name=="jobTitle"||name=="beforeTax"||name=="workPlace"||name=="startWorking"){
				$(window).scrollTop(offset+50);
			}else{
				$(window).scrollTop(offset-height-50);
			}
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
		$(inputElement).parent().find('.err-msg').html(message).show();
		$(divErrElement).addClass('ce-err-input').show();
		return false;

	}
	else{
			$(inputElement).parent().find('.err-msg').hide();
			$(divErrElement).removeClass('ce-err-input');			
    }
	return true;
}

function validateCustomerRegistration(){
	
    if($('input[name="fname"]').val()==""){
	    	$('input[name="fname"]').next('.err-msg').html(firstNameEmptyMessage).show();
			$(".reg-input-cont.reg-fname").addClass('err-input').focus();
			return false;
	}else{
			$('input[name="fname"]').next('.err-msg').hide();
			$(".reg-input-cont.reg-fname").removeClass('err-input');
	}
    if($('input[name="lname"]').val()==""){
	    	$('input[name="lname"]').next('.err-msg').html(lastNameEmptyMessage).show();
	    	$(".reg-input-cont.reg-lname").addClass('err-input').focus();
	    	return false;
    }else{
	    	$('input[name="lname"]').next('.err-msg').hide();
	    	$(".reg-input-cont.reg-lname").removeClass('err-input');
    }
    if($('input[name="email"]').val()==""){
	    	$('input[name="email"]').next('.err-msg').html(emailEmptyMessage).show();
	    	$(".reg-input-cont.reg-email").addClass('err-input').focus();
	    	return false;
    }else{
	    	$('input[name="email"]').next('.err-msg').hide();
	    	$(".reg-input-cont.reg-email").removeClass('err-input');
    }
    if($('input[name="email"]').val()!=null||$('input[name="email"]').val()!=""){
	    	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	        if (!regex.test($('input[name="email"]').val())) {
	        	$('input[name="email"]').next('.err-msg').html(incorrectEmailID).show();
	    		$(".reg-input-cont.reg-email").addClass('err-input').focus();
	    	return false;
        }else{
        	$('input[name="email"]').next('.err-msg').hide();
    		$(".reg-input-cont.reg-email").removeClass('err-input');
        }
    }
   /* if(phoneNumber==""){
    	$('input[name="phone"]').next('.err-msg').html(phoneEmptyMessage).show();
		$(".reg-input-cont.reg-phone").addClass('err-input').focus();
		//showErrorToastMessage("Firstname cannot be empty");
		return false;
    }else{
    	if(phoneNumber.length<10){
    		$('input[name="phone"]').next('.err-msg').html(phoneNumberLegthErrorMessage).show();
    		$(".reg-input-cont.reg-phone").addClass('err-input').focus();
			//showErrorToastMessage("Firstname cannot be empty");
			return false;
    	}else{
    		$('input[name="phone"]').next('.err-msg').hide();
    		$(".reg-input-cont.reg-phone").removeClass('err-input');
    	}
    }*/
    
    return true;
}

function validateAdminUserCreate(user){
	
	if (user.firstName == "") {
		$('#admin-create-user-first-name').next('.admin-err-msg').html(firstNameEmptyMessage).show();
		$('#admin-create-user-first-name').addClass('ce-err-input').show();
		return false;
	}else{
		$('#admin-create-user-first-name').next('.admin-err-msg').hide();
		$('#admin-create-user-first-name').removeClass('ce-err-input');
	} 
	if (user.lastName == "") {
		$('#admin-create-user-last-name').next('.admin-err-msg').html(lastNameEmptyMessage).show();
		$('#admin-create-user-last-name').addClass('ce-err-input').show();
		return false;
	} else {
		$('#admin-create-user-last-name').next('.admin-err-msg').hide();
		$('#admin-create-user-last-name').removeClass('ce-err-input');
	}
   if (user.emailId == "") {
	   $('#admin-create-user-emailId').next('.admin-err-msg').html(emailEmptyMessage).show();
	   $('#admin-create-user-emailId').addClass('ce-err-input').show();
		return false;
	}else{
		$('#admin-create-user-emailId').next('.admin-err-msg').hide();
		$('#admin-create-user-emailId').removeClass('ce-err-input');
	}
	if(user.emailId!="")
	{var validationStatus=emailValidation(user.emailId);
      if(validationStatus){
		  $('#admin-create-user-emailId').val('');	
		  $('#admin-create-user-emailId').next('.admin-err-msg').html(invalidEmailErrorMessage).show();
	      $('#admin-create-user-emailId').addClass('ce-err-input').focus();
	  
	  return false;
	  }else{
		  $('#admin-create-user-emailId').next('.admin-err-msg').hide();
		  $('#admin-create-user-emailId').removeClass('ce-err-input');
	  }
      
     }
	return true;
}

function phoneNumberValidation(phoneNo,customerStatus,elementId){

	var regex = /^\d{10}$/;   
	if(customerStatus!=false){
		if(phoneNo==null || phoneNo==""){
			$('#'+elementId).next('.err-msg').html(phoneFieldEmptyMessage).show();
			$('#'+elementId).addClass('err-input');
		//showErrorToastMessage("Phone field cannot be empty");
		return false;
		}else{
			if(!regex.test(phoneNo)) {
				$('#'+elementId).next('.err-msg').html(invalidPhoneNumberMessage).show();
				$('#'+elementId).addClass('err-input');
				//showErrorToastMessage("Invalid phone number");
				
				return false;
			}
				$('#'+elementId).next('.err-msg').html('');
				$('#'+elementId).removeClass('err-input');
				return true;
		
		}
		
	}else{
		$('#'+elementId).next('.err-msg').html('');
		$('#'+elementId).removeClass('err-input');
		return true;
	}

	}

function currentlyLivingValidation(){
	
	var stateValidation=validateInput($('input[name="state"]'),$('input[name="state"]').val(),yesyNoErrorMessage);
	var questionOne=validateInput($('input[name="city"]'),$('input[name="city"]').val(),message);
	var questionTwo=validateInput($('input[name="zipCode"]'),$('input[name="zipCode"]').val(),message);
	var questionThree=validateInput($('input[name="startLivingTime"]'),$('input[name="startLivingTime"]').val(),message);		    			    	
	var addressValidation=validateInput($('input[name="addressStreet"]'),$('input[name="addressStreet"]').val(),message);		    	
	var propertQuestionTwo=validateInput($('input[name="propZipCode"]'),$('input[name="propZipCode"]').val(),zipCodeMessage);
	if($('input[name="rentPerMonth"]').length>0){
		var questionfour=validateInput($('input[name="rentPerMonth"]'),$('input[name="rentPerMonth"]').val(),message);
		if(!questionfour){
    		return false;
    	}
	}
	if(!stateValidation){
		//showErrorToastMessage(yesyNoErrorMessage);
		return false;
	}
	if(!questionOne){
		return false;
	}		    	
	if(!addressValidation){
		return false;
	}
	
	if(!questionTwo){
		return false;
	}else{
		
     if($('input[name="zipCode"]').val().length >5 ||$('input[name="zipCode"]').val().length < 5){			
    	$('input[name="zipCode"]').next('.err-msg').html(zipCodeMessage).show();
    	$('input[name="zipCode"]').addClass('ce-err-input').show();
    	var height=$('input[name="zipCode"]').offset().top-100;
    	$(window).scrollTop(height);
       	return false;
       
	  }  
	}
	if(!questionThree){
		return false;
	}

	if(!propertQuestionTwo){
		return false;
	}else{
		if($('input[name="propZipCode"]').val().length >5 ||$('input[name="propZipCode"]').val().length < 5){		
			$('input[name="propZipCode"]').next('.err-msg').html(zipCodeMessage).show();
			$('input[name="propZipCode"]').addClass('ce-err-input').show();
			var height=$('input[name="propZipCode"]').offset().top-100;
	    	$(window).scrollTop(height);
   		 return false;
   	 }
		
	}
	if($('.ce-option-checkbox').hasClass('app-option-checked')){
		
	}else{
		var propertQuestionOne=validateInput($('input[name="propCity"]'),$('input[name="propCity"]').val(),message);
		var propertQuestionThree=validateInput($('input[name="propState"]'),$('input[name="propState"]').val(),yesyNoErrorMessage);
    	var propertQuestionfour=validateInput($('input[name="propStreetAddress"]'),$('input[name="propStreetAddress"]').val(),message);
    	
    	if(!propertQuestionOne){
    		return false;
    	}

    	if(!propertQuestionThree){
    		return false;
    	}

    	if(!propertQuestionfour){
    		return false;
    	}
	}
	return true;
}