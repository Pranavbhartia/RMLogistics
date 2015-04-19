function paintMySpouseIncome() {

	
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
	

	var quesTxt = "Co-borrower Details :Select all that apply";
	var options = [ {
		"text" : "W2 Employee",
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
	},/*, {
	"text" : "Social Security Income/Disability",
	"onselect" : paintRefinanceDisability,
	"name" :"isssIncomeOrDisability",
    "data" : ssiData,
	"value" : 2
	}, {
		"text" : "Pension/Retirement/401(k)",
		"onselect" : paintRefinancePension,
		"name" : "ispensionOrRetirement",
	    "data" : prData,
		"value" : 3
	}*/
	{
		"text" : "Child Support/Alimony",
		"onselect" : paintRefinancePension,
		"name" :"",
	    "data" : "",
		"value" : 2
	}, 
	{
		"text" : "Social Security Income",
		"onselect" : paintRefinancePension,
		"name" :"",
	    "data" : "",
		"value" : 3
	}, 
	{
		"text" : "Disability Income",
		"onselect" : paintRefinancePension,
		"name" :"",
	    "data" : "",
		"value" : 4
	}, 
	{
		"text" : "Pension Income",
		"onselect" : paintRefinancePension,
		"name" :"",
	    "data" : "",
		"value" : 5
	}, 
	{
		"text" : "Retirement Income",
		"onselect" : paintRefinancePension,
		"name" :"",
	    "data" : "",
		"value" : 6
	}];
	
	var quesCont = paintSpouseCustomerApplicationPageStep3(quesTxt, options, name);

	

		$('#app-right-panel').html('');
		var questcontainer = $('#app-right-panel').append(quesCont);
		
		
		/*for(var i=0;i<options.length;i++){
            var option=options[i];
            if(option.onselect){
                option.onselect(option.value,option.data);
            }
        }*/
		
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
	         var wrapper = $('<div>').attr({
	          "class" : "ce-option-ques-wrapper"
	         });
	         
	         var quesTxt = "Monthly Income";
	         
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
	         
	         
	         var quesTxt1 = "Number of years";
	         
	         var container1 = $('<div>').attr({
	          "class" : "ce-ques-wrapper"
	         });

	         var quesTextCont1 = $('<div>').attr({
	          "class" : "ce-option-text"
	         }).html(quesTxt1);

	         var optionContainer1 = $('<div>').attr({
	          "class" : "ce-options-cont"
	         });
	         var inputBox1 = $('<input>').attr({
	          "class" : "ce-input",
	          "name" : "",
	          "value": ""
	         });

	         optionContainer1.append(inputBox1);
	         container1.append(quesTextCont1).append(optionContainer1);
	         
	         wrapper.append(container).append(container1);
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

    putCurrencyFormat("spouseBeforeTax");
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
	  }).html("Add additional source of income").bind('click',function(){
	   
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
	   
	   /*var removeAccBtn = $('<div>').attr({
	    "class" : "add-btn remove-account-btn"
	   }).html("Remove Income")
	   .bind('click',{"mainContainerId":mainContainerId},function(event){
	    $(this).closest('.ce-option-ques-wrapper').remove();
	    var parentDiv = $('#'+event.data.mainContainerId);
	    
	    if(parentDiv.children('.ce-option-ques-wrapper').length==1){
	     parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
	    }
	   });*/
	   
	   $(this).parent().children('.ce-option-ques-wrapper');/*.append(removeAccBtn);*/
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
		}).html("Monthly Income Before Taxes");


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
		}).html("Employer");


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
		
		var quesTextCont4 = $('<div>').attr({
			"class" : "ce-rp-ques-text"
		}).html("Job Title");
	    /*if(value&&value.employedSince)
	        val=value.employedSince;*/
		var inputBox4 = $('<input>').attr({
			"class" : "ce-input",
			"name" : ""
		});
		if(val!=""){
			inputBox4.attr("value",val);
		}
		quesTextCont4.append(inputBox4);

		optionContainer/*.append(quesTextCont0)*/.append(quesTextCont4).append(quesTextCont1).append(quesTextCont2).append(
				quesTextCont3);

		
		container.append(quesTextCont).append(optionContainer);
	 
	    return wrapper.append(container);
	}



function paintSpouseCustomerApplicationPageStep4a() {
   
	
	quesDeclarationContxts = [];
	
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Declaration for co-borrower";

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    var questions = [{
        type: "yesno",
        text: "Are there any outstanding judgments against you?",
        name: "isOutstandingJudgments",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you been declared bankrupt within the past 7 years?",
        name: "isBankrupt",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you had property foreclosed upon or given title or deed in lieu of in the last 7 years?",
        name: "isPropertyForeclosed",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a party to a lawsuit?",
        name: "isLawsuit",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Have you directly or indirectly been obligated on any loan which resulted in foreclosure, transfer of title in lieu of foreclosure, or judgment?",
        name: "isObligatedLoan",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you presently delinquent or in default on any Federal debt or any other loan, mortgage, financial obligation, bond or loan guarantee?",
        name: "isFederalDebt",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you obligated to pay alimony, child support, or separate maintenance?",
        name: "isObligatedToPayAlimony",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Is any part of the down payment borrowed?",
        name: "isDownPaymentBorrowed",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a co-maker or endorser on a note?",
        name: "isEndorser",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    }, {
        type: "yesno",
        text: "Are you a US citizen?",
        name: "isUSCitizen",
        options: [{
            text: "Yes"
        }, {
            text: "No",
            addQuestions: [{
                type: "yesno",
                text: "Are you a permanent resident alien?",
                name: "isPermanentResidentAlien",
                options: [{
                    text: "Yes",
                    value: "Yes"
                }, {
                    text: "No",
                    value: "No"
                }],
                "selected": ""
            }]
        }],
        selected: ""
    },
    {
        type: "yesno",
        text: "Do you intend to occupy the property as your primary residence?",
        name: "isOccupyPrimaryResidence",
        options: [{
            text: "Yes",
            value: "Yes"
        }, {
            text: "No",
            value: "No"
        }],
        selected: ""
    },
    {
        type: "yesno",
        text: "Have you had an ownership interest in a property in the last three years?",
        name: "isOwnershipInterestInProperty",
        options: [{
            text: "Yes",
            addQuestions: [{
                type: "select",
                text: "What type of property did you own? Select the choice that fits best.",
                name: "typeOfPropertyOwned",
                options: [{
                    text: "Your Primary Residence",
                    value: "Your Primary Residence"
                }, {
                    text: "A Second Home",
                    value: "A Second Home"
                },
                {
                    text: "An Investment Property",
                    value: "An Investment Property"
                }],
                "selected": ""
            },
            {
                type: "select",
                text: "What was your status on the title of that property",
                name: "propertyTitleStatus",
                options: [{
                    text: "I was the sole title holder",
                    value: "I was the sole title holder"
                }, {
                    text: "I held the title jointly with my spouse",
                    value: "I held the title jointly with my spouse"
                },
                {
                    text: "I held title jointly with another person",
                    value: "I held title jointly with another person"
                }],
                "selected": ""
            }]
        }, {
            text: "No"
        }],
        selected: ""
    }
    ];

   // var questionsContainer = getQuestionsContainer(questions);
    
    $('#app-right-panel').append(quesHeaderTextCont);
   
   
    
    for(var i=0;i<questions.length;i++){
    	var question=questions[i];
    	var contxt=getQuestionContext(question,$('#app-right-panel'),appUserDetails.spouseGovernmentQuestions);
    	contxt.drawQuestion();
    	
    	quesDeclarationContxts.push(contxt);
    }

    var saveAndContinueButton = $('<div>').attr({
        "class": "app-save-btn"
    }).html("Save & continue").on('click', function() {
    	
    	isOutstandingJudgments =  quesDeclarationContxts[0].value;
    	isBankrupt =  quesDeclarationContxts[1].value;
    	isPropertyForeclosed =  quesDeclarationContxts[2].value;
    	isLawsuit =  quesDeclarationContxts[3].value;
    	isObligatedLoan =  quesDeclarationContxts[4].value;
    	isFederalDebt =  quesDeclarationContxts[5].value;
    	isObligatedToPayAlimony =  quesDeclarationContxts[6].value;
    	isDownPaymentBorrowed = quesDeclarationContxts[7].value;
    	isEndorser =  quesDeclarationContxts[8].value;
    	isUSCitizen =  quesDeclarationContxts[9].value;
    	isOccupyPrimaryResidence =  quesDeclarationContxts[10].value;
    	isOwnershipInterestInProperty =  quesDeclarationContxts[11].value;
    	
    	 
    	
    	typeOfPropertyOwned =  $('.app-options-cont[name="typeOfPropertyOwned"]').find('.app-option-selected').data();
	    if(typeOfPropertyOwned != undefined)	
	    	typeOfPropertyOwned =  $('.app-options-cont[name="typeOfPropertyOwned"]').find('.app-option-selected').data().value;
	    	
	    
    	propertyTitleStatus =  $('.app-options-cont[name="propertyTitleStatus"]').find('.app-option-selected').data();
	    if(propertyTitleStatus != undefined)
	    	propertyTitleStatus =  $('.app-options-cont[name="propertyTitleStatus"]').find('.app-option-selected').data().value;
    	  
    	 if(appUserDetails.spouseGovernmentQuestions){
    		 spouseGovernmentQuestions = appUserDetails.spouseGovernmentQuestions;
    	 }
    	 else{
    		 spouseGovernmentQuestions = {};
    	 }
    	 
    	  if( isDownPaymentBorrowed =="Yes"){ 
    		 spouseGovernmentQuestions.isDownPaymentBorrowed = true;
 		 }else if(isDownPaymentBorrowed =="No"){
 			spouseGovernmentQuestions.isDownPaymentBorrowed = false;
 		 }else{
 			spouseGovernmentQuestions.isDownPaymentBorrowed = null;
 		 }
    	 
    	 if( isOutstandingJudgments =="Yes"){ 
    		 spouseGovernmentQuestions.isOutstandingJudgments = true;
 		 }else if (isOutstandingJudgments =="No"){
 			spouseGovernmentQuestions.isOutstandingJudgments = false;
 		 }else{
 			spouseGovernmentQuestions.isOutstandingJudgments = null;
 		 }
    	 
    	 if( isBankrupt =="Yes"){ 
    		 spouseGovernmentQuestions.isBankrupt = true;
 		 }else if(isBankrupt =="no"){
 			spouseGovernmentQuestions.isBankrupt = false;
 		 }else{
 			spouseGovernmentQuestions.isBankrupt = null;
 		 }
    	 
    	 if( isPropertyForeclosed =="Yes"){ 
    		 spouseGovernmentQuestions.isPropertyForeclosed = true;
 		 }else if(isPropertyForeclosed =="No"){
 			spouseGovernmentQuestions.isPropertyForeclosed = false;
 		 }else{
 			spouseGovernmentQuestions.isPropertyForeclosed = null;
 		 }
    	 
    	 if( isLawsuit =="Yes"){ 
    		 spouseGovernmentQuestions.isLawsuit = true;
 		 }else if(isLawsuit =="No"){
 			spouseGovernmentQuestions.isLawsuit = false;
 		 }else{
 			spouseGovernmentQuestions.isLawsuit = null;
 		 }

    	 if( isObligatedLoan =="Yes"){ 
    		 spouseGovernmentQuestions.isObligatedLoan = true;
 		 }else if(isObligatedLoan =="No"){
 			spouseGovernmentQuestions.isObligatedLoan = false;
 		 }else{
 			spouseGovernmentQuestions.isObligatedLoan = null;
 		 }
    	 
    	 if( isFederalDebt =="Yes"){ 
    		 spouseGovernmentQuestions.isFederalDebt = true;
 		 }else if( isFederalDebt =="No"){
 			spouseGovernmentQuestions.isFederalDebt = false;
 		 }else{
 			spouseGovernmentQuestions.isFederalDebt = null;
 		 }

    	 if( isObligatedToPayAlimony =="Yes"){ 
    		 spouseGovernmentQuestions.isObligatedToPayAlimony = true;
 		 }else if(isObligatedToPayAlimony =="No"){
 			spouseGovernmentQuestions.isObligatedToPayAlimony = false;
 		 }else{
 			spouseGovernmentQuestions.isObligatedToPayAlimony = null;
 		 }
    	 
    	 if( isEndorser =="Yes"){ 
    		 spouseGovernmentQuestions.isEndorser = true;
 		 }else if(isEndorser =="No"){
 			spouseGovernmentQuestions.isEndorser = false;
 		 }else{
 			spouseGovernmentQuestions.isEndorser = null;
 		 }

    	 if( isUSCitizen =="Yes"){ 
    		 spouseGovernmentQuestions.isUSCitizen = true;
 		 }else if(isUSCitizen =="No"){
 			spouseGovernmentQuestions.isUSCitizen = false;
 		 }else{
 			spouseGovernmentQuestions.isUSCitizen = null;
 		 }

    	 if( isOccupyPrimaryResidence =="Yes"){ 
    		 spouseGovernmentQuestions.isOccupyPrimaryResidence = true;
 		 }else if(isOccupyPrimaryResidence =="No"){
 			spouseGovernmentQuestions.isOccupyPrimaryResidence = false;
 		 }else{
 			spouseGovernmentQuestions.isOccupyPrimaryResidence = null;
 		 }
    	 
    	 if( isOwnershipInterestInProperty =="Yes"){ 
    		 spouseGovernmentQuestions.isOwnershipInterestInProperty = true;
 		 }else if(isOwnershipInterestInProperty =="No"){
 			spouseGovernmentQuestions.isOwnershipInterestInProperty = false;
 		 }else{
 			spouseGovernmentQuestions.isOwnershipInterestInProperty = null;
 		 }
    	 
    	  spouseGovernmentQuestions.typeOfPropertyOwned=typeOfPropertyOwned;
	     spouseGovernmentQuestions.propertyTitleStatus=propertyTitleStatus;
    	 appUserDetails.spouseGovernmentQuestions =spouseGovernmentQuestions;
    	 

    	 saveAndUpdateLoanAppForm(appUserDetails,paintSpouseCustomerApplicationPageStep4b());
    
    	//paintCustomerApplicationPageStep4b();
    });

    $('#app-right-panel').append(saveAndContinueButton);
}