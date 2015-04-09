function paintMySpouseIncome() {

	applyLoanStatus = 3;
	//appProgressBaar(3);
     var selfEmployedData={};

     if(appUserDetails)
        selfEmployedData={"selected":appUserDetails.customerSpouseDetail.isSelfEmployed,"data":appUserDetails.customerSpouseDetail.selfEmployedIncome};
     
    var employedData={};
    if(appUserDetails&&appUserDetails.customerSpouseEmploymentIncome)
        employedData={"selected":true,"data":appUserDetails.customerSpouseEmploymentIncome};
   
   
    var ssiData={};
    if(appUserDetails)
        ssiData={"selected":appUserDetails.customerSpouseDetail.isssIncomeOrDisability,"data":appUserDetails.customerSpouseDetail.ssDisabilityIncome};
   
   
    var prData={};
    if(appUserDetails)
        prData={"selected":appUserDetails.customerSpouseDetail.ispensionOrRetirement,"data":appUserDetails.customerSpouseDetail.monthlyPension};
	

	var quesTxt = "Spouse Details :Select all that apply";
	var options = [ {
		"text" : "Employed",
		"onselect" : paintSpouseRefinanceEmployed,
		"name" : "isSpouseEmployed",
        "data" : employedData,
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintSpouseRefinanceSelfEmployed,
		"name" : "isSpouseSelfEmployed",
        "data" : selfEmployedData,
		"value" : 1
	}, {
		"text" : "Social Security Income/Disability",
		"onselect" : paintSpouseRefinanceDisability,
		"name" :"isSpouseIncomeOrDisability",
        "data" : ssiData,
		"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintSpouseRefinancePension,
		"name" : "isSpousePensionOrRetirement",
        "data" : prData,
		"value" : 3
	} ];
	var quesCont = paintSpouseCustomerApplicationPageStep3(quesTxt, options, name);

	

	$('#app-right-panel').html('');
		var questcontainer = $('#app-right-panel').append(quesCont);
		for(var i=0;i<options.length;i++){
            var option=options[i];
            if(option.onselect){
                option.onselect(option.value,option.data);
            }
        }
		console.log('purchase'+purchase);
		if(purchase == true)
	    {
	    var questionsContainer10 = paintSpouseSaleOfCurrentHome();
	    questcontainer.append(questionsContainer10);
	    }

}



function paintSpouseRefinanceSelfEmployed(divId,value) {
	  var flag=true;
	    if(value&&!value.selected)
	        flag=false;
	   

	    //appUserDetails.employed ="true";
	    if(flag){
	         var quesTxt = "How much do you make a year?";

	         var wrapper = $('<div>').attr({
	          "class" : "ce-option-ques-wrapper"
	         });
	         
	         var container = $('<div>').attr({
	          "class" : "ce-ques-wrapper"
	         });

	         var quesTextCont = $('<div>').attr({
	          "class" : "ce-option-text"
	         }).html(quesTxt);

	         var optionContainer = $('<div>').attr({
	          "class" : "ce-options-cont"
	         });
	        var inputBox = $('<input>').attr({
	          "class" : "ce-input",
	          "name" : "spouseSelfEmployed",
	          "value": appUserDetails.customerSpouseDetail.selfEmployedIncome
	         });

	         optionContainer.append(inputBox);
	         container.append(quesTextCont).append(optionContainer);
	         wrapper.append(container);
	         if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
	          
	          $('#ce-option_' + divId).prepend(wrapper); 
	         }
	         $('#ce-option_' + divId).toggle();
	         
	         putCurrencyFormat("spouseSelfEmployed");
	    }
	}



function paintSpouseRefinanceEmployed(divId,value) {
    var flag=true;
    if(value&&!value.selected){
        flag=false;
        }
    else{
        if(value){
            var quesCont ;
            var incomes=value.data;
            for(var i=0;i<incomes.length;i++){
                var income=incomes[i].customerSpouseEmploymentIncome;
                    var quesTxt = "Spouse Income :About how much do you make a year";
                    quesCont = getMultiTextQuestionSpouse(quesTxt,income);
                    $('#ce-option_' + divId).prepend(quesCont);
            }
             if(incomes.length>1)
                addRemoveButtons(quesCont);
            $('#ce-option_' + divId).toggle();
            flag=false;
        }
    }

	//appUserDetails.employed ="true";
    if(flag){
        if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
            var quesTxt = "Spouse Income :About how much do you make a year";
            var quesCont = getMultiTextQuestionSpouse(quesTxt);
            $('#ce-option_' + divId).prepend(quesCont); 
        }
        $('#ce-option_' + divId).toggle();
    }

}


function paintSpouseRefinanceDisability(divId,value) {
	  var flag=true;
	    if(value&&!value.selected)
	        flag=false;
	   

	    //appUserDetails.employed ="true";
	    if(flag){
	         var quesTxt = "About how much do you get monthly?";

	         var wrapper = $('<div>').attr({
	          "class" : "ce-option-ques-wrapper"
	         });
	         
	         var container = $('<div>').attr({
	          "class" : "ce-ques-wrapper"
	         });

	         var quesTextCont = $('<div>').attr({
	          "class" : "ce-option-text"
	         }).html(quesTxt);

	         var optionContainer = $('<div>').attr({
	          "class" : "ce-options-cont"
	         });

	         var inputBox = $('<input>').attr({
	          "class" : "ce-input",
	          "name" : "spouseDisability",
	          "value": appUserDetails.customerSpouseDetail.ssDisabilityIncome
	         });

	         optionContainer.append(inputBox);
	         container.append(quesTextCont).append(optionContainer);
	         wrapper.append(container);
	         if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
	          
	          $('#ce-option_' + divId).prepend(wrapper); 
	         }
	         $('#ce-option_' + divId).toggle();
	         
	         putCurrencyFormat("spouseDisability");
	     }
	}


function paintSpouseRefinancePension(divId,value) {
	  var flag=true;
	    if(value&&!value.selected)
	        flag=false;
	   

	    //appUserDetails.employed ="true";
	    if(flag){
	         var quesTxt = "About how much do you get monthly?";

	         var wrapper = $('<div>').attr({
	          "class" : "ce-option-ques-wrapper"
	         });
	         
	         var container = $('<div>').attr({
	          "class" : "ce-ques-wrapper"
	         });
	         
	         var quesTextCont = $('<div>').attr({
	          "class" : "ce-option-text"
	         }).html(quesTxt);

	         var optionContainer = $('<div>').attr({
	          "class" : "ce-options-cont"
	         });

	         var inputBox = $('<input>').attr({
	          "class" : "ce-input",
	          "name" : "spousePension",
	          "value": appUserDetails.customerSpouseDetail.monthlyPension
	         });

	         optionContainer.append(inputBox);
	         container.append(quesTextCont).append(optionContainer);
	         wrapper.append(container);
	         if($('#ce-option_' + divId).children('.ce-option-ques-wrapper').size() == 0){
	          
	          $('#ce-option_' + divId).prepend(wrapper); 
	         }
	         $('#ce-option_' + divId).toggle();
	         
	         putCurrencyFormat("spousePension");
	    }
	}



function paintSpouseCustomerApplicationPageStep3(quesText, options, name) {
	 var container = $('<div>').attr({
	  "class" : "ce-ques-wrapper"
	 });

	 var quesTextCont = $('<div>').attr({
	  "class" : "ce-rp-ques-text"
	 }).html(quesText);

	 var optionContainer = $('<div>').attr({
	  "class" : "ce-options-cont"
	 });

	 for (var i = 0; i < options.length; i++) {

	  var optionsWrapper = $('<div>').attr({
	   "class" : "hide ce-sub-option-wrapper",
	   "id" : "ce-option_"+i
	  });
	  
	  var optionIncome = $('<div>').attr({
	   "class" : "ce-option-ques-wrapper"
	  });
	    var selectedClas="";
	    if(options[i].data&&options[i].data.selected)
	        selectedClas="app-option-checked ";
	  var option = $('<div>').attr({
	   "class" : "ce-option-checkbox "+selectedClas,
	   "value" : options[i].value
	  }).html(options[i].text).bind('click', {
	   "option" : options[i],
	   "name" : options[i].name
	  }, function(event) {
	   if($(this).hasClass('app-option-checked')){
	          $(this).removeClass('app-option-checked');
	          //appUserDetails[name] = false;
	         }else{
	          $(this).addClass('app-option-checked');
	          //appUserDetails[name] = true;
	         }
	   var key = event.data.name;
	   //appUserDetails[key] = event.data.option.value;
	   event.data.option.onselect(event.data.option.value);
	  });

	  optionContainer.append(option);
	  
	  
	  var addAccountBtn = $('<div>').attr({
	   "class" : "add-btn add-account-btn"
	  }).html("Add Income").bind('click',function(){
	   
	   var mainContainerId = $(this).closest('.ce-sub-option-wrapper').attr("id");
	   
	   if($('#'+mainContainerId).children('.ce-option-ques-wrapper').length >= 3){
	    showToastMessage("Maximum 3 income needed");
	    return false;
	   }
	    var quesTxt = "About how much do you make a year";
		var quesCont = getMultiTextQuestionSpouse(quesTxt);
	   /*var containerToAppend = $(this).parent().find('.ce-option-ques-wrapper').wrap('<p/>').parent().html();
	   $(this).parent().find('.ce-option-ques-wrapper').unwrap();*/
	   $(this).before(quesCont);
	   
	   $(this).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	   
	   var removeAccBtn = $('<div>').attr({
	    "class" : "add-btn remove-account-btn"
	   }).html("Remove Income")
	   .bind('click',{"mainContainerId":mainContainerId},function(event){
	    $(this).closest('.ce-option-ques-wrapper').remove();
	    var parentDiv = $('#'+event.data.mainContainerId);
	    
	    if(parentDiv.children('.ce-option-ques-wrapper').length==1){
	     parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	    }
	   });
	   
	   $(this).parent().children('.ce-option-ques-wrapper').append(removeAccBtn);
	  });
	  
	 
	  if(i==0){
	  optionsWrapper.append(addAccountBtn);
	  }
	  optionContainer.append(optionsWrapper);
	  
	  
	 }

	 var saveBtn = $('<div>').attr({
	  "class" : "ce-save-btn"
	 }).html("Save & Continue").bind('click',function() {
	          
	          
	         
	  var  customerSpouseEmploymentIncome = [];
	      
	      $("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
	       customerSpouseEmploymentIncomeTemp1 = {};
	       
	       id = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="custSpouseEmploymentIncomeId"]').val();
	       if(id ==""){
	     	  id = undefined;
	       }
	       
	       spouseBeforeTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseBeforeTax"]').val();
	       spouseWorkPlace = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseWorkPlace"]').val();
	       spouseStartWorking = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="spouseStartWorking"]').val();
	      
	       customerSpouseEmploymentIncomeTemp1.id = id;
	       customerSpouseEmploymentIncomeTemp1.employedIncomePreTax = spouseBeforeTax;
	       customerSpouseEmploymentIncomeTemp1.employedAt = spouseWorkPlace;
	       customerSpouseEmploymentIncomeTemp1.employedSince = spouseStartWorking;
	       var temp = {};
	       temp.customerSpouseEmploymentIncome = customerSpouseEmploymentIncomeTemp1;
	       
	       customerSpouseEmploymentIncome.push(temp);
	      });
	      
	      appUserDetails.customerSpouseEmploymentIncome=customerSpouseEmploymentIncome;
	  
	  
	    
	      spouseSelfEmployed = $('input[name="spouseSelfEmployed"]').val();
	          
	      spouseDisability = $('input[name="spouseDisability"]').val();
	    
	      spousePension = $('input[name="spousePension"]').val();

	    
	    
	    
	    if(appUserDetails.customerSpouseEmploymentIncome&&appUserDetails.customerSpouseEmploymentIncome.length>0)
	    	appUserDetails.customerSpouseDetail.isSpouseEmployed =  true;
	    
	    if(spousePension&&spousePension!=""&&spousePension!="$0")
	    	appUserDetails.customerSpouseDetail.ispensionOrRetirement= true;
	    else
	    	appUserDetails.customerSpouseDetail.ispensionOrRetirement= false;
	    appUserDetails.customerSpouseDetail.monthlyPension =spousePension;
	    
	    if(spouseSelfEmployed&&spouseSelfEmployed!=""&&spouseSelfEmployed!="$0")
		    appUserDetails.customerSpouseDetail.isSelfEmployed = true;
	    else
		    appUserDetails.customerSpouseDetail.isSelfEmployed = false;
	    appUserDetails.customerSpouseDetail.selfEmployedIncome =spouseSelfEmployed;
	    
	    if(spouseDisability&&spouseDisability!=""&&spouseDisability!="$0")
		    appUserDetails.customerSpouseDetail.isssIncomeOrDisability=true;
	    else
		    appUserDetails.customerSpouseDetail.isssIncomeOrDisability=false;
	    appUserDetails.customerSpouseDetail.ssDisabilityIncome = spouseDisability;
	    
	    //sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
	    appUserDetails.loanAppFormCompletionStatus = applyLoanStatus;
	    
	    
	    saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a());
	    //paintCustomerApplicationPageStep4a();
	   });


	if (appUserDetails.loanType.description && appUserDetails.loanType.description =="Purchase"){
	 return container.append(quesTextCont).append(optionContainer);
	}else{
	 return container.append(quesTextCont).append(optionContainer).append(saveBtn);
	 }
	}



function getMultiTextQuestionSpouse(quesText,value) {

	var wrapper = $('<div>').attr({
	  "class" : "ce-option-ques-wrapper"
	 });
	 
		var container = $('<div>').attr({
			"class" : "ce-ques-wrapper",
		});

		var quesTextCont = $('<div>').attr({
			"class" : "ce-option-text",
		}).html(quesText);

		var optionContainer = $('<div>').attr({
			"class" : "ce-options-cont",
		});

		var quesTextCont1 = $('<div>').attr({
			"class" : "ce-rp-ques-text",
		}).html("Before Tax");


	   var val="";
	      if(value&&value.employedIncomePreTax)
	        val=value.employedIncomePreTax;
	        
	        
		var inputBox1 = $('<input>').attr({
			"class" : "ce-input",
			"name" : "spouseBeforeTax",
			"value" :val
		});

		
		var quesTextCont0 = $('<div>').attr({
			"class" : "ce-rp-ques-text"
		});
		
		
		var val="";
		   
		if(value && value.id)
	        val=value.id;
		var inputBox0 = $('<input>').attr({
			"class" : "ce-input",
			"name" : "custSpouseEmploymentIncomeId",
			"type":"hidden",
			"value" :val
		});
		quesTextCont0.append(inputBox0);
		
		quesTextCont1.append(inputBox1);

		var quesTextCont2 = $('<div>').attr({
			"class" : "ce-rp-ques-text"
		}).html("Where Do You Work ?");


	    val="";
	    if(value&&value.employedAt)
	        val=value.employedAt;
	        

		var inputBox2 = $('<input>').attr({
			"class" : "ce-input",
			"name" : "spouseWorkPlace",
			"value":val
		});

		quesTextCont2.append(inputBox2);

		var quesTextCont3 = $('<div>').attr({
			"class" : "ce-rp-ques-text"
		}).html("When Did You Start Working ?");

		 val="";
	    if(value&&value.employedSince)
	        val=value.employedSince;
	        
		var inputBox3 = $('<input>').attr({
			"class" : "ce-input",
			"name" : "spouseStartWorking",
			"value" : val
		});

		quesTextCont3.append(inputBox3);

		optionContainer.append(quesTextCont0).append(quesTextCont1).append(quesTextCont2).append(
				quesTextCont3);

		
		
		putCurrencyFormat("spouseBeforeTax");
		
		container.append(quesTextCont).append(optionContainer);
	 
	    return wrapper.append(container);
	}

