function paintMySpouseIncome() {
   
	var coborrowerName = "";
	if(appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.spouseName) 
	 coborrowerName = appUserDetails.customerSpouseDetail.spouseName;
	
	var quesTxt =coborrowerName+ " details :Select all that apply";
	
    var selfEmployedData={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.selfEmployedIncome){
        var tmpData={
            monthlyIncome : appUserDetails.customerSpouseDetail.selfEmployedIncome,
            noOfYears : appUserDetails.customerSpouseDetail.selfEmployedNoYear
        }
        selfEmployedData={"selected":true,"data":tmpData};
    }
    var employedData={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseEmploymentIncome && appUserDetails.customerSpouseEmploymentIncome.length>0)
        employedData={"selected":true,"data":appUserDetails.customerSpouseEmploymentIncome};
    var childSupportIncome={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.childSupportAlimony)
        childSupportIncome={"selected":true,"data":appUserDetails.customerSpouseDetail.childSupportAlimony};
    var socialSecIncome={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.socialSecurityIncome)
        socialSecIncome={"selected":true,"data":appUserDetails.customerSpouseDetail.socialSecurityIncome};
    var socialSecDisabilityIncome={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.disabilityIncome)
        socialSecDisabilityIncome={"selected":true,"data":appUserDetails.customerSpouseDetail.disabilityIncome};
    var pensionIncome={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.monthlyPension)
        pensionIncome={"selected":true,"data":appUserDetails.customerSpouseDetail.monthlyPension};
    var retirementIncome={};
    if(appUserDetails && appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.retirementIncome)
        retirementIncome={"selected":true,"data":appUserDetails.customerSpouseDetail.retirementIncome};

	var options = [ {
		"text" : "W2 Employee",
		"onselect" : paintSpouseRefinanceEmployed,
		"name" : "isEmployed",
        "data" : employedData,
		"value" : 0
	}, {
		"text" : "Self-employed",
		"onselect" : paintRefinanceSelfEmployed,
		"name" : "isselfEmployed",
        "data" : selfEmployedData,
		"value" : 1
	},
	{
		"text" : "Child Support/Alimony",
		"onselect" : paintRefinancePension,
		"name" :"childAlimonySupport",
        "data" : childSupportIncome,
		"value" : 2
	}, 
	{
		"text" : "Social Security Income",
		"onselect" : paintRefinancePension,
		"name" :"socialSecurityIncome",
        "data" : socialSecIncome,
		"value" : 3
	}, 
	{
		"text" : "Disability Income",
		"onselect" : paintRefinancePension,
		"name" :"disabilityIncome",
        "data" : socialSecDisabilityIncome,
		"value" : 4
	}, 
	{
		"text" : "Pension Income",
		"onselect" : paintRefinancePension,
		"name" :"pensionIncome",
        "data" : pensionIncome,
		"value" : 5
	}, 
	{
		"text" : "Retirement Income",
		"onselect" : paintRefinancePension,
		"name" :"retirementIncome",
        "data" : retirementIncome,
		"value" : 6
	}];
	
	var quesCont = paintSpouseCustomerApplicationPageStep3(quesTxt, options, name);
	$('#app-right-panel').html('');
	var questcontainer = $('#app-right-panel').append(quesCont);
		
	console.log('purchase'+purchase);
	if(purchase == true){
	    
		var questionsContainer10 = paintSpouseSaleOfCurrentHome();
		questcontainer.append(questionsContainer10);
    }

	if(appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.skipMyAssets)
	var skipMyAssets = appUserDetails.customerSpouseDetail.skipMyAssets;
	 
	
	var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
	
	var saveBtn = $('<div>').attr({
		"class" : "cep-button-color ce-save-btn"
	}).html(buttonText).bind('click',function(e) {
	  	e.stopImmediatePropagation();
	  	var isStatus=[];
		if(this.innerHTML!=next){
				var  customerSpouseEmploymentIncome = [];
				//validation
				isStatus  = validateCheckbox(isStatus);
    	        if(isStatus.length>0){	
    	      	 
    	        	for(var i=0;i<isStatus.length;i++){
    	        		var status=validateInputOfChecked(isStatus[i]);
        	        	if(status==false){
        	        		return false;
        	        	}
    	        		
    	        			
    	        	}
    	        	
    	        	
    	        }else{
    	        	showErrorToastMessage(selectAnyOne);
    	        	return false;
    	        }
    	        //validation for my assests
    	        /*var isChecked=[];
				if($('.ce-option-checkbox').hasClass('myassets')){
					if($('.ce-option-checkbox.myassets').hasClass('app-option-checked')){
				
				    }else{ 
				    	
				    	var checkboxes=$('.asset-ques-wrapper').find('.app-account-wrapper');
				    	for(var count=0;count<checkboxes.length;count++){
				    		if($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').hasClass('app-option-checked')){
				    			isChecked.push($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']'));
				    		}
				    }
				    	if(isChecked==""){
				    		$('.ce-option-checkbox.myassets').addClass('text-color');
							showErrorToastMessage(selectAssestErrorMessage);
							return false; 
				    	}else{
				    		for(count=0;count<isChecked.length;count++){
				    			var list=$('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-option-selected').html();
				    			if(list=="Select One"){
				    				showErrorToastMessage(selectAccountType);
				    				return false;
				    			}
				    		    var newCount=0;
				    			var currentBalance=$('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount].value;
				    			var newHomeAdvance=$('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount+1].value;
				    			var questionOne=validateInputsOfAssests($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount],currentBalance,message,count);
								var questionTwo=validateInputsOfAssests($('.asset-ques-wrapper').find('.app-account-wrapper').find('.ce-option-checkbox[value='+count+']').next().find('.app-input')[newCount+1],newHomeAdvance,message,count);
									if(!questionOne){
										return false;
									}
									if(!questionTwo){
										return false;
									} 
				    		}
							
				    	}
				}
				    	        	
				
				}*/
               
    	        //End
			  	 if($("#ce-option_0").prev().hasClass('app-option-checked')){
				  	
			  		 $("#ce-option_0").find('.ce-option-ques-wrapper').each(function(){
						customerSpouseEmploymentIncomeTemp1 = {};
			
						id = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="customerEmploymentIncomeId"]').val();
						if(id ==""){
							id = undefined;
						}
			
						jobTitle = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="jobTitle"]').val();
						EmployedIncomePreTax = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="beforeTax"]').val();
						EmployedAt = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="workPlace"]').val();
						EmployedSince = $(this).find('.ce-ques-wrapper').find('.ce-options-cont').find('.ce-rp-ques-text').find('input[name="startWorking"]').val();
			
						customerSpouseEmploymentIncomeTemp1.id = id;
						customerSpouseEmploymentIncomeTemp1.jobTitle=jobTitle;
						customerSpouseEmploymentIncomeTemp1.employedIncomePreTax = EmployedIncomePreTax;
						customerSpouseEmploymentIncomeTemp1.employedAt = EmployedAt;
						customerSpouseEmploymentIncomeTemp1.employedSince = EmployedSince;
						var temp = {};
						temp.customerSpouseEmploymentIncome = customerSpouseEmploymentIncomeTemp1;
			
						customerSpouseEmploymentIncome.push(temp);
					});
			  		 
			  		appUserDetails.customerSpouseEmploymentIncome=customerSpouseEmploymentIncome;
			  	 }
	      
	  
			    selfEmployedIncome = $('input[name="selfEmployedIncome"]').val();
		        selfEmployedYears = $('input[name="selfEmployedYears"]').val();
		        childAlimonySupport = $('input[name="childAlimonySupport"]').val();
		        socialSecurityIncome = $('input[name="socialSecurityIncome"]').val();
		        disabilityIncome = $('input[name="disabilityIncome"]').val();
		        pensionIncome = $('input[name="pensionIncome"]').val();
		        retirementIncome = $('input[name="retirementIncome"]').val();
		        if(selfEmployedIncome != "" && selfEmployedIncome != undefined){
		    		appUserDetails.customerSpouseDetail.selfEmployedIncome= selfEmployedIncome;
		    		appUserDetails.customerSpouseDetail.selfEmployedNoYear =selfEmployedYears;
		    	}else{
		            appUserDetails.customerSpouseDetail.selfEmployedIncome= undefined;
		            appUserDetails.customerSpouseDetail.selfEmployedNoYear =undefined;
		        }
		    	if(childAlimonySupport != "" && childAlimonySupport != undefined){
		    		appUserDetails.customerSpouseDetail.childSupportAlimony =childAlimonySupport;
		    	}else{
		            appUserDetails.customerSpouseDetail.childSupportAlimony = undefined;
		        }
		    	if(socialSecurityIncome !="" && socialSecurityIncome != undefined){
		    		appUserDetails.customerSpouseDetail.socialSecurityIncome= socialSecurityIncome;
		    	}else{
		            appUserDetails.customerSpouseDetail.socialSecurityIncome = undefined;
		        }
		    	if(disabilityIncome !="" && disabilityIncome != undefined){
		            appUserDetails.customerSpouseDetail.disabilityIncome = disabilityIncome;
		        }else{
		            appUserDetails.customerSpouseDetail.disabilityIncome = undefined;
		        }
		        if(pensionIncome !="" && pensionIncome != undefined){
		            appUserDetails.customerSpouseDetail.monthlyPension = pensionIncome;
		        }else{
		            appUserDetails.customerSpouseDetail.monthlyPension = undefined;
		        }
		        if(retirementIncome !="" && retirementIncome != undefined){
		            appUserDetails.customerSpouseDetail.retirementIncome = retirementIncome;
		        }else{
		            appUserDetails.customerSpouseDetail.retirementIncome = undefined;
		        }
	    
			    if(purchase == true){
					
			    	homelistprice = $('input[name="homelistprice"]').val();
					homemortgagebalance = $('input[name="homemortgagebalance"]').val();
					inverstInPurchase = $('input[name="inverstInPurchase"]').val();
					
					appUserDetails.customerSpouseDetail.skipMyAssets = $('.myassets').hasClass("app-option-checked");
					
					/*appUserDetails.customerSpouseBankAccountDetails = [];
					appUserDetails.customerSpouseRetirementAccountDetails = [];
					appUserDetails.customerSpouseOtherAccountDetails = [];
					*/
					var assets=$('.asset-ques-wrapper').find('.app-account-wrapper');
					
					var bankContainer=assets[0];
					var retirementContainer=assets[1];
					var otherContainer=assets[2];
					
					if($(bankContainer).find('.app-option-checked').hasClass('app-option-checked')){
						appUserDetails.customerSpouseBankAccountDetails=getAccountValues(bankContainer,"customerSpouseBankAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
					}
					if($(retirementContainer).find('.app-option-checked').hasClass('app-option-checked')){
					    appUserDetails.customerSpouseRetirementAccountDetails=getAccountValues(retirementContainer,"customerSpouseRetirementAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
					}
					if($(otherContainer).find('.app-option-checked').hasClass('app-option-checked')){
					    appUserDetails.customerSpouseOtherAccountDetails=getAccountValues(otherContainer,"customerSpouseOtherAccountDetails","accountSubType","currentAccountBalance","amountForNewHome");
					}
					
					if(!appUserDetails.customerSpouseDetail.skipMyAssets && !($(bankContainer).find('.app-option-checked').hasClass('app-option-checked')) && !($(retirementContainer).find('.app-option-checked').hasClass('app-option-checked')) && !($(otherContainer).find('.app-option-checked').hasClass('app-option-checked'))){
						showErrorToastMessage("Please select any option for my assets");
		    			return false;
					}
					
					
				}
		saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep4a);
		}else{
			// when click on next button
			paintCustomerApplicationPageStep4a();
		}
		
	});
	
	if(skipMyAssets != undefined && skipMyAssets){
		$(".myassets").click();
		
	}
	
	questcontainer.append(saveBtn);
	
	for(var i=0;i<options.length;i++){
        var option=options[i];
        if(option.onselect){
            option.onselect(option.value,option.data,option.name);
        }
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

	         optionContainer.append(inputBox).append(appendErrorMessage());
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

	         optionContainer1.append(inputBox1).append(appendErrorMessage());
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
                    //var quesTxt = "Spouse Income :About how much do you make a year";
                      var quesTxt = "";
                    quesCont = getMultiTextQuestion(quesTxt,income);
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
        	//var quesTxt = "Spouse Income :About how much do you make a year";
            var quesTxt = "";
            var quesCont = getMultiTextQuestion(quesTxt);
            $('#ce-option_' + divId).prepend(quesCont); 
        }
        $('#ce-option_' + divId).toggle();
    }

    putCurrencyFormat("beforeTax");
}


function paintSpouseRefinanceDisability(divId,value) {
	var errFeild=appendErrorMessage();
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

	         optionContainer.append(inputBox).append(errFeild);
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
	var errFeild=appendErrorMessage();
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

	         optionContainer.append(inputBox).append(errFeild);
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
			"name" : options[i].name+"-CHK"
		}, function(event) {
			if($(this).hasClass('app-option-checked')){
			    $(this).removeClass('app-option-checked');
			}else{
			    $(this).addClass('app-option-checked');
			}
			var key = event.data.name;
			//appUserDetails[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value,undefined,event.data.option.name);
		});
		optionContainer.append(option);
		var addAccountBtn = $('<div>').attr({
			"class" : "cep-button-color add-btn add-account-btn"
		}).html("Add another source of W2 income").bind('click',function(){
			var mainContainerId = $(this).closest('.ce-sub-option-wrapper').attr("id");
			if($('#'+mainContainerId).children('.ce-option-ques-wrapper').length >= 2){
				showToastMessage(max2Needed);
				return false;
			}
			var quesTxt;// = "About how much do you make a year";
			var quesCont = getMultiTextQuestion(quesTxt);
			$(this).before(quesCont);
			
			
			var removeAccBtn = $('<div>').attr({
				"class" : "cep-button-color add-btn remove-account-btn"
			}).html("Remove Income")
			.bind('click',{"mainContainerId":mainContainerId},function(event){
				$(this).closest('.ce-option-ques-wrapper').remove();
				var parentDiv = $('#'+event.data.mainContainerId);
				
				if(parentDiv.children('.ce-option-ques-wrapper').length==1){
					parentDiv.children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
				}
			});
			
			
			$(this).parent().children('.ce-option-ques-wrapper').find('.remove-account-btn').remove();
			$(this).parent().children('.ce-option-ques-wrapper').append(removeAccBtn);
		});


		if(i==0){
			optionsWrapper.append(addAccountBtn);
		}
		optionContainer.append(optionsWrapper);
	}
	

	container.append(quesTextCont).append(optionContainer);
 
	return container;
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
		quesTextCont0.append(inputBox0).append(appendErrorMessage());
		
		quesTextCont1.append(inputBox1).append(appendErrorMessage());

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

		quesTextCont2.append(inputBox2).append(appendErrorMessage());

		var quesTextCont3 = $('<div>').attr({
			"class" : "ce-rp-ques-text"
		}).html("Start date");

		 val="";
	    if(value&&value.employedSince)
	        val=value.employedSince;
	        
		var inputBox3 = $('<input>').attr({
			"class" : "ce-input",
			"name" : "spouseStartWorking",
			"value" : val
		});

		quesTextCont3.append(inputBox3).append(appendErrorMessage());
		
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
		quesTextCont4.append(inputBox4).append(appendErrorMessage());

		optionContainer/*.append(quesTextCont0)*/.append(quesTextCont4).append(quesTextCont1).append(quesTextCont2).append(
				quesTextCont3);

		
		container.append(quesTextCont).append(optionContainer);
	 
	    return wrapper.append(container);
	}



function paintSpouseCustomerApplicationPageStep4a() {
   
	 var quesHeaderTxt = "Declaration for co-borrower";
	 if(appUserDetails.customerSpouseDetail && appUserDetails.customerSpouseDetail.spouseName)
		  quesHeaderTxt = "Declaration for " +appUserDetails.customerSpouseDetail.spouseName;
	quesDeclarationContxts = [];
	
	$('#app-right-panel').html('');
   

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
                name: "permanentResidentAlien",
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
    
    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    

    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function() {
    	
    	if(this.innerHTML!=next){
		    	for(var i=0;i<quesDeclarationContxts.length;i++){
		    		if(quesDeclarationContxts[i].value==""||quesDeclarationContxts[i].value==undefined){
		    			showErrorToastMessage(yesyNoErrorMessage);
		    			return;
		    		}
		    	}
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
		 		 }else if(isBankrupt =="No"){
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
		 			permanentResidentAlien = quesDeclarationContxts[9].childContexts.No[0].value;
						if(permanentResidentAlien =="Yes")
							spouseGovernmentQuestions.permanentResidentAlien = true;
						else if(permanentResidentAlien =="No")
							spouseGovernmentQuestions.permanentResidentAlien = false;
						else 
							spouseGovernmentQuestions.permanentResidentAlien = null;
		 			
		 			
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
		    		 if(typeOfPropertyOwned==undefined && propertyTitleStatus==undefined){
		    			 showErrorToastMessage(yesyNoErrorMessage);
		    			 return;
		    		 }
		 		 }else if(isOwnershipInterestInProperty =="No"){
		 			spouseGovernmentQuestions.isOwnershipInterestInProperty = false;
		 		 }else{
		 			spouseGovernmentQuestions.isOwnershipInterestInProperty = null;
		 		 }
		    	 
		    	  spouseGovernmentQuestions.typeOfPropertyOwned=typeOfPropertyOwned;
			     spouseGovernmentQuestions.propertyTitleStatus=propertyTitleStatus;
		    	 appUserDetails.spouseGovernmentQuestions =spouseGovernmentQuestions;
		    	 
		
		    	 saveAndUpdateLoanAppForm(appUserDetails,paintSpouseCustomerApplicationPageStep4b());
    	}else{
    		paintSpouseCustomerApplicationPageStep4b();
    	}	 
    
    });

    $('#app-right-panel').append(saveAndContinueButton);
}



function paintSpouseCustomerApplicationPageStep4b(){
	
	var coborrower ="co-borrower";
	if(appUserDetails.customerSpouseDetail.spouseName)
	coborrower = appUserDetails.customerSpouseDetail.spouseName;
	
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Government Monitoring Questions for "+coborrower;

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    });

	var options = [ {
		"text" : "I decline to Provide",
		"name" : "bypassoptional",
		"value" : 0
	}];
	var quesCont = paintSpouseGovernmentMonitoringQuestions(quesHeaderTxt, options, name);

	$('#app-right-panel').append(quesCont);
	
	var optionalQuestion = appUserDetails.spouseGovernmentQuestions.skipOptionalQuestion;

    
    ///
    var questions = [{
        type: "select",
        text: "Ethnicity",
        name: "spouseEthnicity",
        options: [{
            text: "Hispanic",
            value: "hispanic"
        }, {
            text: "Non Hispanic or Latino",
            value: "latino"
        }],
        selected: ""
    }, {
        type: "select",
        text: "Race",
        name: "spouseRace",
        options: [{
            text: "American Indian or Alaska Native",
            value: "americanIndian"
        }, {
            text: "Native Hawaiian or Pacific Islander",
            value: "nativeHawaiian "
        }, {
            text: "Black or African American",
            value: "black"
        },
        {
            text: "White",
            value: "white"
        },
        {
            text: "Asian",
            value: "asian"
        }],
        selected: ""
    }, {
        type: "select",
        text: "Sex",
        name: "spouseSex",
        options: [{
            text: "Male",
            value: "male"
        }, {
            text: "Female",
            value: "female "
        }],
      selected: ""
    }];

	
	 var questionsContainer = getQuestionsContainer(questions);
	 
	 var lqbFileId = checkLqbFileId();
		if(lqbFileId){
			buttonText = next;
		}
	 
	
	 var saveAndContinueButton = $('<div>').attr({
	        "class": "cep-button-color app-save-btn"
	    }).html(buttonText).on('click', function() {
	    	
	    	if(this.innerHTML!=next){
			    	ethnicity =  $('.app-options-cont[name="spouseEthnicity"]').find('.app-option-selected').data().value;
			    	race =  $('.app-options-cont[name="spouseRace"]').find('.app-option-selected').data().value;
			    	sex =  $('.app-options-cont[name="spouseSex"]').find('.app-option-selected').data().value;
			    	skipOptionalQuestion = $('.ce-option-checkbox').hasClass("ce-option-checked");
		
			    	if($('.ce-option-checkbox').hasClass("ce-option-checked")){
			    		
			    	}else{
			    		if(ethnicity==undefined && race==undefined && sex==undefined){
				    		showErrorToastMessage(yesyNoErrorMessage);
				    		return false;
				    	} 
			    	}
			    	appUserDetails.spouseGovernmentQuestions.ethnicity = ethnicity;
			    	appUserDetails.spouseGovernmentQuestions.race = race;
			    	appUserDetails.spouseGovernmentQuestions.sex =sex;
			    	appUserDetails.spouseGovernmentQuestions.skipOptionalQuestion=skipOptionalQuestion;	
			    	
			    	//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
			    	 saveAndUpdateLoanAppForm(appUserDetails,paintCustomerApplicationPageStep5);
	    	}else{
	    		paintCustomerApplicationPageStep5();
	    	} 	 
	    });

	    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(saveAndContinueButton);
	    
	    if(optionalQuestion != undefined && optionalQuestion){
			$(".ce-option-checkbox").click();
			
		}
}
 


function paintSpouseGovernmentMonitoringQuestions(quesText, options, name) {
	var container = $('<div>').attr({
		"class" : "ce-ques-wrapper"
		
	});

	var quesTextCont = $('<div>').attr({
		"class" : "app-ques-header-txt"
	}).html(quesText);

	var optionContainer = $('<div>').attr({
		"class" : "ce-options-cont"
	});

	for (var i = 0; i < options.length; i++) {

		var optionIncome = $('<div>').attr({
			"class" : "hide ce-option-ques-wrapper"
			//"id" : "ce-option_" + i
		});

		var option = $('<div>').attr({
			"class" : "ce-option-checkbox",
			"value" : options[i].value
		}).html(options[i].text).bind('click', {
			"option" : options[i],
			"name" : name
		}, function(event) {
			if($(this).hasClass("ce-option-checked")){
				$(this).removeClass("ce-option-checked");
			}else{
				$(this).addClass("ce-option-checked");
			}
			/*var key = event.data.name;
			appUserDetails[key] = event.data.option.value;
			event.data.option.onselect(event.data.option.value);*/
			$(".app-ques-container").toggle(function() {
				
			});
		});

		optionContainer.append(option).append(optionIncome);
	}

	return container.append(quesTextCont).append(optionContainer);
}




function paintCustomerSpouseApplicationPageStep5() {
	
	var coborrower = "co borrower";
	 coborrower = appUserDetails.customerSpouseDetail.spouseName;
	appProgressBaar(6);
	$('#app-right-panel').html('');
    var quesHeaderTxt = "Credit for "+coborrower;

    var quesHeaderTextCont = $('<div>').attr({
        "class": "app-ques-header-txt"
    }).html(quesHeaderTxt);

    
    var dob = "";
    if(appUserDetails.customerSpouseDetail.spouseDateOfBirth != 0)
    dob = makeDateFromLong(appUserDetails.customerSpouseDetail.spouseDateOfBirth);
    if(dob =="" || dob == undefined || dob =='NaN/NaN/NaN')
    	dob="";
    
    var socialSecurityWrapper = $('<div>').attr({
    	"class" : "ce-options-cont"
    });
    
    var isAuthorizedCheckBox = $('<div>').attr({
    	"class" : "ce-option-checkbox"
    }).text("I authorize Newfi To Pull My Credit Report For the Purposes of Appying for a Morgage Loan")
    .bind('click',function(){
    	if($(this).hasClass('ce-option-checked')){
    		$(this).removeClass('ce-option-checked');
    		$(this).parent().find('.ss-ques-wrapper').hide();
    	}else{
    		$(this).addClass('ce-option-checked');
    		$(this).parent().find('.ss-ques-wrapper').show();
    	}
    });
    
    var socialSecurityQues = [{
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: appUserDetails.customerSpouseDetail.spouseSsn
    }];
    
    var socialSecurityQuesContainer = $('<div>').attr({
    	"class" : "hide ss-ques-wrapper"
    }).append(getQuestionsContainer(socialSecurityQues));
    
    socialSecurityWrapper.append(isAuthorizedCheckBox).append(socialSecurityQuesContainer);
    
    var questions = [{
        type: "desc",
        text: "Birthday",
        name: "birthday",
        value: dob
    }/*,
    {
        type: "desc",
        text: "Social Security Number",
        name: "ssn",
        value: appUserDetails.customerSpouseDetail.ssn
    }*/,
    {
        type: "desc",
        text: "Phone Number",
        name: "phoneNumber",
        value: appUserDetails.customerSpouseDetail.spouseSecPhoneNumber
    }];

    var questionsContainer = getQuestionsContainer(questions);

    var lqbFileId = checkLqbFileId();
	if(lqbFileId){
		buttonText = next;
	}
    
    var saveAndContinueButton = $('<div>').attr({
        "class": "cep-button-color app-save-btn"
    }).html(buttonText).on('click', function() {
    	
    	if(this.innerHTML!=next){
		    	dateOfBirth = $('input[name="birthday"]').val();
		    	ssn =  $('input[name="ssn"]').val();
		    	secPhoneNumber =  $('input[name="phoneNumber"]').val();
		    	var dat=new Date(dateOfBirth);
		        var dateNow=new Date();
		        dateNow.setFullYear(dateNow.getFullYear()-18);
		        var yearCount=(dateNow.getTime()-dat.getTime());
		       var cbSsnProvided = $('.ce-option-checkbox').hasClass("ce-option-checked");
		    	
		       var questionOne=validateInput($('input[name="birthday"]'),$('input[name="birthday"]').val(),message);
		       var questionTwo=validateInput($('input[name="phoneNumber"]'),$('input[name="phoneNumber"]').val(),message);
		      
		       if($('.ce-option-checkbox').hasClass("ce-option-checked")){
		    	   var isSuccess=validateInput($('input[name="ssn"]'),$('input[name="ssn"]').val(),message);
		    	   if(!isSuccess){
		    		   return false;
		    	   }
		    	   
		       }else{
		    	   if(!questionOne){
		        	   return false;
		           }else if(!questionTwo){
		        	   return false;
		           }else if(yearCount<0){
		        	   showErrorToastMessage(ageErrorMessage);
		        	   return false;
		           }
		       }
		
		    		customerDetailTemp =  appUserDetails.customerSpouseDetail;
		    		customerDetailTemp.spouseDateOfBirth= dateOfBirth;//new Date(dateOfBirth).getTime();
		    		customerDetailTemp.spouseSsn = ssn;
		    		customerDetailTemp.spouseSecPhoneNumber = secPhoneNumber;
		    		//applicationFormSumbit();
		    		//sessionStorage.loanAppFormData = JSON.parse(appUserDetails);
		    		appUserDetails.cbSsnProvided = cbSsnProvided;
		    		appUserDetails.customerSpouseDetail = customerDetailTemp;
					saveAndUpdateLoanAppForm(appUserDetails,applicationFormSumbit(appUserDetails));  
    	}else{
    		applicationFormSumbit(appUserDetails);
    	}
					
    });

    $('#app-right-panel').append(quesHeaderTextCont).append(questionsContainer).append(socialSecurityWrapper)
        .append(saveAndContinueButton);
        
        
    var cbSsnGiven = appUserDetails.cbSsnProvided;
	if(cbSsnGiven!= undefined && cbSsnGiven){
	$(".ce-option-checkbox").click();
	}

}