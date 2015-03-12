var buyHomeTeaserRate = new Object();

function paintBuyHomeContainer(){
	
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
	
	paintBuyHomeQuest();
}


function paintBuyHomeQuest(){
	var quesText = "Living Situation";
	
	var options = [
	    {
	    	"text" : "Renting",
	    	"onselect" : paintBuyHomeRenting,
	    	"value" : 0
	    },
	    {
	    	"text" : "I am a home owner",
	    	"onselect" : paintBuyHomeOwner,
	    	"value" : 1
	    }
	    ];
	
	var quesCont = getBuyHomeMutipleChoiceQuestion(quesText,options,"livingSituation");
	$('#ce-refinance-cp').html(quesCont);
}


function getBuyHomeMutipleChoiceQuestion(quesText,options,name){
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
			buyHomeTeaserRate[key] = event.data.option.value;
			event.data.option.onselect();
		});
		optionContainer.append(option);
	}
	
	return container.append(quesTextCont).append(optionContainer);
}

// Renting

function paintBuyHomeRenting(){
		
	var quesTxt = "Where You Live Now ?";
	var quesCont = getBuyHomeTextQuestion(quesTxt, paintBuyHomeCurrentAddress,"whereLiveNow");
	$('#ce-refinance-cp').html(quesCont);

}

function paintBuyHomeCurrentAddress(){
	
	var quesTxt = "What Is Your Current Address?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeCity,"currentAddress");
	$('#ce-refinance-cp').html(quesCont);
}


function paintBuyHomeCity(){
	
	var quesTxt = "What Is Your City?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeState,"city");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeState(){
	
	var quesTxt = "What Is Your State?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeZip,"state");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeZip(){
	
	var quesTxt = "What Is Your ZipCode?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeRent,"zipCode");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeRent(){
	
	var quesTxt = "How much do you pay each month for rent?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeStartLiving,"rent");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeStartLiving(){
	
	var quesTxt = "When did you start living here?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeStartMilitaryloans,"startLiving");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeStartMilitaryloans(){
	
	var quesTxt = "Are you eligible for, and interested in, VA/military loans?";
	var options = [
	       	    {
	       	    	"text" : "Yes",
	       	    	"onselect" : paintBuyHomeMyIncome,
	       	    	"name":name,
	       	    	"value" : 1
	       	    },
	       	    {
	       	    	"text" : "No",
	       	    	"onselect" : paintBuyHomeMyIncome,
	       	    	"name":name,
	       	    	"value" : 0
	       	    }
	       	   ];
	       	
   	var quesCont = getBuyHomeMutipleChoiceQuestion(quesTxt,options,"isVeteran");
   	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeMyIncome(){
	
	var quesTxt = "Select all that apply";
	var options = [
		       	    {
		       	    	"text" : "Employed",
		       	    	"onselect" : paintBuyHomeEmployed,
		       	    	"name":name,
		       	    	"value" : 0
		       	    },
		       	    {
		       	    	"text" : "Self-employed",
		       	    	"onselect" :paintBuyHomeSelfEmployed,
		       	    	"name":name,
		       	    	"value" : 1
		       	    },
		       	    {
		       	    	"text" : "Social Security Income/Disability",
		       	    	"onselect" : paintBuyHomeDisability,
		       	    	"name":name,
		       	    	"value" : 2
		       	    },
		       	    {
		       	    	"text" : "Pension/Retirement/401(k)",
		       	    	"onselect" : paintBuyHomePension,
		       	    	"name":name,
		       	    	"value" : 3
		       	    }
		       	  ];
	var quesCont = getMutipleChoiceQuestion123(quesTxt,options,"");
		
   	$('#ce-refinance-cp').html(quesCont);
	
}


function painBuyHomeEmployed(divId){
	
	var quesTxt = "About how much do you make a year";
	var quesCont = getMultiTextQuestion(quesTxt);
	$('#ce-option_'+divId).toggle();
	$('#ce-option_'+divId).html(quesCont);
}

function paintBuyHomeSelfEmployed(divId){
	
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

function paintBuyHomeDisability(divId){
	
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

function paintBuyHomePension(divId){
	
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
			buyHomeTeaserRate[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);
		});
		
		optionContainer.append(option).append(optionIncome);
	}
	
	
	var saveBtn = $('<div>').attr({
		"class" : "ce-buyhome-save-btn"
	}).html("Save & Continue")
	.bind('click',function(){
		buyHomeTeaserRate["beforeTax"] =  $('input[name="beforeTax"]').val();
		buyHomeTeaserRate["workPlace"] =  $('input[name="workPlace"]').val();
		buyHomeTeaserRate["startWorking"] =  $('input[name="startWorking"]').val();
		buyHomeTeaserRate["selfEmployed"] =  $('input[name="selfEmployed"]').val();
		buyHomeTeaserRate["disability"] =  $('input[name="disability"]').val();
		buyHomeTeaserRate["pension"] =  $('input[name="pension"]').val();
		
		saleYourCurrentHome();
	});
	
	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}

function saleYourCurrentHome(){
	
	var quesTxt = "What is the listing price of your current home?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeMortgagebalance,"priceOfHome");
	$('#ce-refinance-cp').html(quesCont);
	
}

function paintBuyHomeMortgagebalance(){
	
	var quesTxt = "What is the mortgage balance of your current home?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomePurchaseforNewHome,"mortgagebalance");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomePurchaseforNewHome(){
	
	var quesTxt = "How much from this sale do you intend to use toward the purchase for your new home?";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeDob,"purchaseforNewHome");
	$('#ce-refinance-cp').html(quesCont);
	
}

function paintBuyHomeDob(){
	
	var quesTxt = "Please enter your birthdate.";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeSSN,"dob");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomeSSN(){
	
	var quesTxt = "Please enter your social security number.";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomePhone,"ssn");
	$('#ce-refinance-cp').html(quesCont);
}

function paintBuyHomePhone(){
	
	var quesTxt = "Please enter you phone number.";
	var quesCont = getBuyHomeTextQuestion(quesTxt,paintBuyHomeSeeRates,"ssn");
	$('#ce-refinance-cp').html(quesCont);
	
}

function paintBuyHomeSeeRates(){
	
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
	   data:{"teaseRate":JSON.stringify(buyHomeTeaserRate)},
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

function getBuyHomeMultiTextQuestion(quesText){
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


function getBuyHomeTextQuestion(quesText,clickEvent,name){
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
		"class" : "ce-buyhome-save-btn"
	}).html("Save & Continue")
	.bind('click',{'clickEvent':clickEvent,"name":name},function(event){
		var key = event.data.name;
		buyHomeTeaserRate[key] = $('input[name="'+key+'"]').val();
		event.data.clickEvent();
	});
	
	return container.append(quesTextCont).append(optionContainer).append(saveBtn);
}




function paintBuyHomeEmployed(divId){
	
	var quesTxt = "About how much do you make a year";
	var quesCont = getMultiTextQuestion(quesTxt);
	$('#ce-option_'+divId).toggle();
	$('#ce-option_'+divId).html(quesCont);
}

function paintBuyHomeSelfEmployed(divId){
	
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

function paintbuyHomeDisability(divId){
	
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

function paintBuyHomePension(divId){
	
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


function paintBuyHomeOwner(){
	
	
}