var count = 0;
var buyHomeRefinanceRate = new Object();
buyHomeRefinanceRate.purchaseDetails = purchaseDetails;
var loanPurchaseDetailsUnderQuickQuote = new Object();
var lqbTeaserRateUnderQuickQuote = new Object();
var inputCustmerDetailUnderQuickQuote = new Object();
loanPurchaseDetailsUnderQuickQuote.isRate = false;
loanPurchaseDetailsUnderQuickQuote.lqbTeaserRateUnderQuickQuote=lqbTeaserRateUnderQuickQuote;
loanPurchaseDetailsUnderQuickQuote.inputCustmerDetailUnderQuickQuote=inputCustmerDetailUnderQuickQuote;
var PURCHASE = "PUR";
var REFINANACE = "REF";
var REFINANACE_LOWER_MORTGAGE_PAYMENT = "REFLMP";
var REFINANACE_CASH_OUT = "REFCO";
var firstName;
var lastName;

/*JSON structure for painting the form feild on click of loantype button*/

//JSON for purchase type
var purchaseType = [{
	loanType : PURCHASE,
	formId : "quick-quote-purchase-form"
	
},{
	question : "First Name",
	id : "firstName",
	column : 0
},{
	question : "Last Name",
	id : "lastName",
	column : 0
},{
	question : "Email",
	id : "emailID",
	column : 1
},{
	question : "Phone No",
	id : "primaryPhoneID",
	column : 1
},{
	question : "Purchase price",
	id : "homeWorthToday",
	column : 0
},{
	question : "Down payment",
	id : "currentMortgageBalance",
	type : "percentage",
	column : 0
	
},{
	question : "Property use",
	id : "residenceType",
    column : 1,
    dropDownId : "residence_type_id",
	 list: [{
            text: "Primary",
            value: "0"
        }, {
            text: "Second",
            value: "1"
        }, {
            text: "Investment",
            value: "2"
        }],
        selected: ""
},{
	 question : "Zip code",
	 "id": "zipCode",
	 column : 0
},{
	question : "Property type",
	id : "propertyType",
	dropDownId : "property_type_id",
	column : 1,
	list : [{
		text: "Single family",
        value: "0"
    }, {
        text: "Condo",
        value: "1"
    }, {
        text: "2-4 Units",
        value: "2"
	}],  
  selected: ""
},{
	question : "Impounds",
	id : "impound",
	type : "yesNo",
	column : 0,
},{
	question : "Property taxes<br />(annual)",
	id : "propertyTaxesPaid",
	column : 1,
	type : "month/year",
	monthYearId : "propTaxMonthlyOryearly",
	addClass : "hide"
},{
	question : "Homeowners<br />(annual)",
	id : "annualHomeownersInsurance",
	column : 1,
	type : "month/year",
	monthYearId : "propInsMonthlyOryearly",
	addClass : "hide"
}];

//JSON for refinanace lower monthly payment
var refinanceLowerMonthlyPayment = [{
	loanType : REFINANACE,
	refinanceOption : REFINANACE_LOWER_MORTGAGE_PAYMENT,
	formId : "quick-quote-refinance-lower-mortage-form"
},{
	question : "First Name",
	id : "firstName",
	column : 0
},{
	question : "Last Name",
	id : "lastName",
	column : 0
},{
	question : "Email",
	id : "emailID",
	column : 1
},{
	question : "Phone No",
	id : "primaryPhoneID",
	column : 1
},{
	question : "Current value",
	id : "homeWorthToday",
	column : 0
},{
	question : "Current balance",
	id : "currentMortgageBalance",
	column : 0
},
{
	question : "Property type",
	id : "propertyType",
	dropDownId : "property_type_id",
	column : 1,
	list : [{
		text: "Single family",
        value: "0"
    }, {
        text: "Condo",
        value: "1"
    }, {
        text: "2-4 Units",
        value: "2"
	}],
  selected: ""
},{
	question : "Property use",
	id : "residenceType",
	dropDownId : "residence_type_id",
	 column : 1,
	 list: [{
         text: "Primary",
         value: "0"
     }, {
         text: "Second",
         value: "1"
     }, {
         text: "Investment",
         value: "2"
     }],
        selected: ""
},{
	 question : "Zip code",
	 id : "zipCode",
	 column : 0
},{
	question : "Impounds",
	id : "impound",
	type : "yesNo",
	column : 0,
},{
	question : "Property taxes<br />(annual)",
	id : "propertyTaxesPaid",
	column : 1,
	type : "month/year",
	monthYearId : "propTaxMonthlyOryearly",
	addClass : "hide"
},{
	question : "Homeowners<br />(annual)",
	id : "annualHomeownersInsurance",
	column : 1,
	type : "month/year",
	monthYearId : "propInsMonthlyOryearly",
	addClass : "hide"
}];

//JSON for refinanace cashout
var refinanceCashOut = [{
	loanType : REFINANACE_CASH_OUT,
	refinanceOption : REFINANACE_CASH_OUT,
	formId : "quick-quote-refinanace-cash-out-form"
},{
	question : "First Name",
	id : "firstName",
	column : 0
},{
	question : "Last Name",
	id : "lastName",
	column : 0
},{
	question : "Email",
	id : "emailID",
	column : 1
},{
	question : "Phone No",
	id : "primaryPhoneID",
	column : 1
},{
	question : "Current value",
	id : "homeWorthToday",
	column : 0
},{
	question : "Current balance",
	id : "currentMortgageBalance",
	column : 0
},{
	question : "Property type",
	id : "propertyType",
	dropDownId : "property_type_id",
	column : 1,
	list : [{
		text: "Single family",
        value: "0"
    }, {
        text: "Condo",
        value: "1"
    }, {
        text: "2-4 Units",
        value: "2"
	}],
  selected: ""
},{
	question : "Cash out",
	id : "cashTakeOut",
	column : 0
},
{
	question : "Property use",
	id : "residenceType",
	dropDownId : "residence_type_id",
	 column : 1,
	 list: [{
         text: "Primary",
         value: "0"
     }, {
         text: "Second",
         value: "1"
     }, {
         text: "Investment",
         value: "2"
     }],
        selected: ""
},{
	 question : "Zip code",
	 id : "zipCode",
	 column : 1
},{
	question : "Impounds",
	id : "impound",
	type : "yesNo",
	column : 0,
},{
	question : "Property taxes<br />(annual)",
	id : "propertyTaxesPaid",
	column : 1,
	type : "month/year",
	monthYearId : "propTaxMonthlyOryearly",
	addClass : "hide"
},{
	question : "Homeowners<br />(annual)",
	id : "annualHomeownersInsurance",
	column : 1,
	type : "month/year",
	monthYearId : "propInsMonthlyOryearly",
	addClass : "hide"
}];

//END


//Function which loads the contents
function loadQuickQoutePage(){
	ga('set', 'page', '/quick-quote');
	ga('send', 'pageview');
	loanPurchaseDetailsUnderQuickQuote.userId = newfiObject.user.id;
	$('#right-panel').html('');
	$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	var agentDashboardMainContainer = $('<div>').attr({
		"id" : "quick-quote-dashboard-container",
		"class" : "rp-agent-dashboard quick-quote-agent-dashboard"
	});
	var wrapper = $('<div>').attr({
		"class" : "quick-quote-wrapper"
	});
	
	var container = $('<div>').attr({
		"class" : "quick-quote-container",
		"id" : "quick-quote-container-id"
	});
	
	var teaserRateContainer = $('<div>').attr({
		"class" : "quick-quote-teaser-rate-container float-left",
		"id" : "ce-refinance-cp"
	});
	
	var header = getCompletYourApplicationHeader(true,"Quick Quote");	
	var section1 = getSectionOneOfQuickQuote();
	container.append(header).append(section1);
	wrapper.append(container);
	wrapper.append(teaserRateContainer);
	agentDashboardMainContainer.append(wrapper);
	$('#right-panel').append(agentDashboardMainContainer);
}

/*Entry point for painting quick quote page*/
function getSectionOneOfQuickQuote(){
	var buttonList = [{
		"title" : "Purchase",
		"data" : purchaseType,
		"className" : "quick-quote-col  float-left",
		"id" : "quick-quote-one-id"
	
	},
	{
		"title" : "Rate & Term",
		"data" : refinanceLowerMonthlyPayment,
		"className" : "quick-quote-col float-left",
		"id" : "quick-quote-two-id"
	},
	{
		"title" : "Cash-out",
		"data" : refinanceCashOut,
		"className" : "quick-quote-col float-left",
		"id" : "quick-quote-three-id"
	}];
	var mainContainer = $('<div>').attr({		
		"class" : "quick-quote-button-section clearfix"
	});
	for(var i=0;i<buttonList.length;i++){
		var buttons = paintButtonSection(buttonList[i]);
		mainContainer.append(buttons);
	}
	return mainContainer;
}

/*Function which paint the loan type buttons*/
function paintButtonSection(option){

	var button = $('<div>').attr({
		"class": "cep-button-color "+option.className,
		"id" : option.id,
		"isSelected" : false
	}).html(option.title).bind('click',function(e){
	
		if($('.quick-quote-col').hasClass('quick-quote-button')){
			
			$('.quick-quote-col').removeClass('quick-quote-button');
		}
		button.addClass('quick-quote-button');
		button.attr({
			"isSelected" : true
		});
		if($('#quick-qoute-btn-id') != undefined){
			if($('#quick-qoute-btn-id').length>0){
				$('#quick-qoute-btn-id').remove();
			}
		}
		if($('#quick-quote-monthyear-container-id') != undefined){
			if($('#quick-quote-monthyear-container-id').length>0){
				$('#quick-quote-monthyear-container-id').remove();
			}
		}
		paintDataSection(option.data);

		
	});	
	 return button;
}

function paintDataSection(option,isDefault){

	if($('.complete-application-wrapper').length > 0){
		$('.complete-application-wrapper.quick-quote-details').remove();
	}
	if( $('.quick-quote-teaser-rate-container').length > 0){
		$('.quick-quote-teaser-rate-container').hide();
	}
    var header = getCompletYourApplicationHeader(true,"Quote Details");

	var questionSection = $('<div>').attr({
		"class" : "quick-quote-question-section hide clearfix"
	});
	$(questionSection).toggle();
	
	var form = $('<form>').attr({
		"class" : "clearfix",
		"id":option[0].formId
	});
	var sectionLHS = $('<div>').attr({
		"class" : "quick-quote-question-section-LHS float-left"
	});
	
	var sectionRHS = $('<div>').attr({
		"class" : "quick-quote-question-section-RHS float-left"
	});
	
	var sectionThree = $('<div>').attr({
		"class" : ""
	});
	var quickQoute = $('<div>').attr({
		"class" : "cep-button-color quick-qoute-btn clearfix ",
		"id" : "quick-qoute-btn-id"
	}).html("Get Quote").on('click', function(){
		var loanType = $('div[id="quick-quote-loan-type-id"]').attr('loan-type');
		var refinanceType = $('div[id="quick-quote-loan-type-id"]').attr('ref-option');
		var status = validateForm(loanType,refinanceType);
		
		if(!status){
			return false;
		}
		removeToastMessage();
		$('.quick-quote-details-header').parent().find('.quick-quote-question-section').hide();
		buyHomeRefinanceRate.loanType = loanType;

		if(buyHomeRefinanceRate.loanType == "PUR"){
			processBuyHomeUnderQuickQuote();
		}
		else{
			buyHomeRefinanceRate.refinanceOption = $('div[id="quick-quote-loan-type-id"]').attr('ref-option');
			if(buyHomeRefinanceRate.refinanceOption == "REFLMP"){
				processRateAndTermUnderQuickQuote();
			}
			else{
				processCashOutUnderQuickQuote();
			}
		}		
	});
		
	for(var i=0;i<option.length;i++){

		var loanType = "";
		if(i == 0){
			if($('#quick-quote-loan-type-id').length > 0){
				$('#quick-quote-loan-type-id').remove();
			}
			if(option[i].refinanceOption == undefined){
				 option[i].refinanceOption = "";
			}
			loanType = $('<div>').attr({
				"class" : "hide",
				"id" : "quick-quote-loan-type-id",
				"loan-type" : option[i].loanType,
				"ref-option" : option[i].refinanceOption,				
			});
		}else {
		
			var divMainRow = $('<div>').attr({
				"class" : "quick-quote-row clearfix"
			});
			
			var rowLHS = $('<div>').attr({
				"class" : "quick-quote-row-LHS float-left"
			}).html(option[i].question);
			
			var rowRHS = "";
			if(option[i].list !=undefined || option[i].list !=null){
				
				rowRHS = appendDropdown(option[i]);
			}else if(option[i].type == "yesNo"){
				
				rowRHS = appendYesNoQuestion(option[i]);
				
			}else if(option[i].type == "month/year"){
				
				rowRHS = appendMonthYearQuestion(option[i]);
				
				$(divMainRow).addClass(option[i].addClass);
				$(divMainRow).attr({
					"id" : "quick-quote-monthYear"
				});
				
			}else if(option[i].type == "percentage"){
				rowRHS = appendDownPaymentFeild(option[i]);
			}else {
				
				rowRHS = $('<input>').attr({
					"class" : "quick-quote-row-RHS float-left",
					"value" : "",
					"id" : option[i].id,
					"name" : option[i].id
				}).on("load focus", function(e){
					if($(this).attr("name") != "zipCode" && $(this).attr("name") != "firstName" && $(this).attr("name") != "lastName" && $(this).attr("name") != "primaryPhoneID" && $(this).attr("name") != "emailID"){
						$(this).maskMoney({
			    			thousands:',',
			    			decimal:'.',
			    			allowZero:true,
			    			prefix: '$',
			    		    precision:0,
			    		    allowNegative:false
			    		});
					}
		    		
		    	});
			} 			
			var question = option[i].question;
			if(question.length >= 23){
				rowLHS.addClass('quick-quote-row-LHS-adj');
			}			
			divMainRow.append(rowLHS).append(rowRHS).append(appendErrorMessage("err-msg-quick-quote"));			
			if(option[i].column == 0){
				sectionLHS.append(divMainRow);
			}/*else if(option[i].type == "month/year"){
				sectionThree.append(divMainRow);
			}*/else if(option[i].column == 1){
				sectionRHS.append(divMainRow);
			}
	}
		form.append(sectionLHS).append(sectionRHS).append(sectionThree).append(loanType);
		questionSection.append(form).append(quickQoute);
		header.append(questionSection);
		$('#quick-quote-container-id').append(header);
		
	}
}


/*Function which paint the question with dropdown type*/
function appendDropdown(options){
	var div = $('<div>').attr({
		
	});
	var row = $('<input>').attr({
		"class" : "quick-quote-row-RHS  quick-quote-option-selected float-left",
		"id" : options.id,
		"name" :options.id,
		"value" : "",
		"type" : "",
		"placeholder" : "Select One"
	}).on('click', function(e) {
    	e.stopPropagation();
    	$('.quick-quote-dropdown-cont').hide();
        $(this).parent().find('.quick-quote-dropdown-cont').toggle();
    });

	
	 var dropDownContainer = $('<div>').attr({
	        "class": "quick-quote-dropdown-cont hide",
	        "id" : options.dropDownId
	    });
	 var optionCont ="";
	   for (var i = 0; i < options.list.length; i++) {
	        var option = options.list[i];
	         optionCont = $('<div>').attr({
	                "class": "quick-quote-option-sel",
	                "value" : "",
	                "text" : option.text
	            }).data({
	                "value": option.value
	            }).html(option.text);
	         
	         dropDownContainer.append(optionCont);

	         optionCont.on('click',function(e){
	        	 $(this).closest('.quick-quote-dropdown-cont').toggle();
	        	 $(row).html($(this).html());
	        	 $(row).attr("type",$(this).data("value"));   
	        	 $(row).data("value",$(this).html());     
	        	 $(row).attr("value",$(this).html());   
	         });
	         
	   }

	   return div.append(row).append(dropDownContainer);
}

/*Function which paint the question with yes/no button type*/
function appendYesNoQuestion(option){
	
	var mainDiv = $('<div>').attr({
		"class":"quick-quote-yesno-container",
		"id" : option.id,
		"value" : ""
	});
	
	var yesContent = $('<div>').attr({
		"class":"quick-quote-yes-btn radio-btn float-left",
		"id":"quick-quote-yes-container-id",
		"isSelected":""
	}).html("Yes").bind('click',function(e){
		e.stopImmediatePropagation();		
		$('div[id="quick-quote-monthYear"]').show();
		//TO calculate tax insurance and homeowners insurance value
		calculateInsuranceValue();		
		if($('#quick-quote-no-container-id').hasClass('radio-btn-selected')){			
			$('#quick-quote-no-container-id').removeClass('radio-btn-selected');
		}
		$(this).addClass('radio-btn-selected');
		$(this).attr('value',$(this).html());
		
		$(this).attr({
			"isSelected" : true,
			"value" : $(this).html()
		});
		$(mainDiv).attr({
			"value" : $(this).html()
		});
		
	});

	var noContent = $('<div>').attr({
		"class":"quick-quote-no-btn radio-btn float-left",
		"id":"quick-quote-no-container-id",
		"isSelected":""
	}).html("No").bind('click',function(e){
		
		e.stopImmediatePropagation();
		$('div[id="quick-quote-monthYear"]').hide();
		
		if($('#quick-quote-yes-container-id').hasClass('radio-btn-selected')){			
			$('#quick-quote-yes-container-id').removeClass('radio-btn-selected');
		}
		$(this).addClass('radio-btn-selected');

		$(this).attr({
			"isSelected" : true,
			"value" : $(this).html()
		});
		$(mainDiv).attr({
			"value" : $(this).html()
		});
		
	});
	
	return mainDiv.append(yesContent).append(noContent);

}
function calculateInsuranceValue(){
	
	   if($('#quick-quote-loan-type-id').attr('loan-type') == REFINANACE){
		   var mainValue = parseFloat(removedDoller(removedComma($('input[id="currentMortgageBalance"]').val())));
			if($('input[id="currentMortgageBalance"]').val() != ""){
				var value = mainValue*1.25/100;
				value = Math.round(value);
				value = '$'+value;
				$('#propertyTaxesPaid').val("");
				$('#propertyTaxesPaid').val(value);
				$('#propertyTaxesPaid').attr({
					"value": showValue(value)
				});
				 
				var homeValue = mainValue*0.35/100;
				homeValue = Math.round(homeValue);
				homeValue = '$'+homeValue;
				$('#annualHomeownersInsurance').val("");
				$('#annualHomeownersInsurance').val(homeValue);
				$('#annualHomeownersInsurance').attr({
					"value": showValue(homeValue)
				});
			}
	   }else{
		   var mainValue = parseFloat(removedDoller(removedComma($('input[id="homeWorthToday"]').val())));
			if($('input[id="homeWorthToday"]').val() != ""){
				var value = mainValue*1.25/100;
				value = Math.round(value);
				value = '$'+value;
				$('#propertyTaxesPaid').val("");
				$('#propertyTaxesPaid').val(value);
				$('#propertyTaxesPaid').attr({
					"value": showValue(value)
				});
				
				var loanAmount = parseFloat(removedDoller(removedComma($('input[id="homeWorthToday"]').val()))-removedDoller(removedComma($('input[id="currentMortgageBalance"]').val())));
				var homeValue = loanAmount*0.35/100;
				homeValue = Math.round(homeValue);
				homeValue = '$'+homeValue;
				$('#annualHomeownersInsurance').val("");
				$('#annualHomeownersInsurance').val(homeValue);
				$('#annualHomeownersInsurance').attr({
					"value": showValue(homeValue)
				});
			}
	   }
}
/*Function which paint the question with year/month dropdown*/
function appendMonthYearQuestion(option){
	
	var mainDiv = $('<div>').attr({
		"class" : "quick-quote-monthyear-container ",
		"id" : "quick-quote-monthyear-container-id"
	});
	
	var rowLHS = $('<input>').attr({
		"class" : "quick-quote-row-RHS float-left",
		"id" : option.id,
		"name" :option.id,
		"value" : ""
	}).on("load focus", function(e){
		$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});
	});
	return mainDiv.append(rowLHS);	
}

/*Function to append downpayment row*/
function appendDownPaymentFeild(option){
	
	var mainDiv = $('<div>').attr({
		"class" : "float-left"
	});
	
	var rowLHS = $('<input>').attr({
		"class" : "quick-quote-row-RHS quick-quote-dwn-payment float-left",
		"id" : option.id,
		"name" : option.id
	}).on("load focus", function(e){
	
			$(this).maskMoney({
    			thousands:',',
    			decimal:'.',
    			allowZero:true,
    			prefix: '$',
    		    precision:0,
    		    allowNegative:false
    		});
	});;
	
	var rowRHS = $('<input>').attr({
		"class" : "quick-quote-dwn-percentage float-left",
	}).attr('maxlength','2');
	rowLHS.bind("keyup",{"valComp":rowLHS,"percentComp":rowRHS,"val":true,"contxt":option},percentageUpdateEventListener)
	rowRHS.bind("keyup",{"valComp":rowLHS,"percentComp":rowRHS,"percentage":true,"contxt":option},
        	function(e){
        		this.value = this.value.replace(/[^0-9]/g,'')
        		percentageUpdateEventListener(e);
        	})

    $(rowLHS).trigger("keyup");
	return mainDiv.append(rowLHS).append(rowRHS);
}

function sendPurchasePdfUnderQuickQuote(){
	$('#overlay-loader').show();  
	$.ajax({
		
		url:"rest/application/sendPurchasePdfUnderQuickQuote",
		type:"POST",
		data:{"loanPurchaseDetailsUnderQuickQuote" : JSON.stringify(loanPurchaseDetailsUnderQuickQuote)},
		dataType:"application/pdf",
		cache:false,
		success:function(data){
			$('#overlay-loader').hide();  
			showToastMessage(pdfSentSuccessMessage);
		},
		error:function(data){
			$('#overlay-loader').hide();  
			
			if(data.status != 200)
			{
				showErrorToastMessage("Error");
			}
			else{
				showToastMessage(pdfSentSuccessMessage);
			}
		}
		
	});
}

function paintFixYourRatePageCEPUnderQuickQuote(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn) {
    teaserRateValHolder.teaserRate=true;
    paintRatePageUnderQuickQuote(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn)
}

function paintRatePageUnderQuickQuote(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn,isViaEngagementPath) {
	    var container = $('<div>').attr({
	        "class": "ce-rate-main-container"
	    });
	    $(parentContainer).html(container);

	    var teaserRate =  modifiedLQBJsonResponse(teaserRate);
	    var rateVO = getLQBObj(teaserRate);
	    var parentWrapper = $('<div>').attr({
	        "class": "loan-summary-wrapper"
	    });
	   /* var ratePageHeader=getLoanSummaryHeader(inputCustomerDetails,hideCreateAccountBtn);*/
	    var ratePageHeader = $('<div>').attr({
            "class": "loan-summary-header clearfix",
            "style":"line-height: normal;"
        });
	    var header = "";
	    
	    if(firstName && lastName){
	    	header = firstName+" "+lastName;
	    }else {
	    	header = 'Programs and Rates';
	    }
	    var ratePageHeaderCol1 = $('<div>').attr({
	        "class": "loan-summary-header-col1 page-header-loan float-left",
	        "style":"line-height: 45px;"
	    }).html(header);
	    var ratePageHeaderCol2 = $('<div>').attr({
	        "class": "cep-button-color quick-quote-header-pdf float-right",
	        "id" : "quick-quote-generate-pdf"
	    }).html('Save as PDF');
	   	if(loanPurchaseDetailsUnderQuickQuote.isRate){
	    	 ratePageHeader.append(ratePageHeaderCol1).append(ratePageHeaderCol2);
	    }else {
	    	 ratePageHeader.append(ratePageHeaderCol1);
	    }
	   
	    var ratePageSlider="";
	    var bottomText="";
	    var buttonWrapper="";
	    if(!rateVO.dummyData){

	    	teaserRateValHolder.leadCustomer = undefined;
	        bottomText = getHeaderText("Rate and APR quoted are based on the information you provided, are not guaranteed, and are subject to change. </br>Actual rate and APR will be available on your Good Faith Estimate after loan amount and income are verified.");
	        ratePageSlider = getSliders(teaserRate, inputCustomerDetails,hideCreateAccountBtn); 
	        if(typeof(newfiObject)!=="undefined"&&teaserRateValHolder.teaserRate)
	            buttonWrapper="";
	        else{
	            if(appUserDetails.loan.lockStatus!="1")
	                buttonWrapper=getRatePageButtonContainer(hideCreateAccountBtn,inputCustomerDetails,teaserRate);
	        }
	    }
	    else if (teaserRateValHolder.teaserRate)
	    {
	    	teaserRateValHolder.leadCustomer=true;
	    }
	    
	    var linkForDisclosure=$('<div>').attr({
	    	"class":"cp-sub-txt-link"
	    		
	    }).on('click',function(e){
	    	window.open("https://www.newfi.com/rates-disclosures/");
	    	
	    }).html('view additional disclosures');
	    
	    
	    var loanSummaryWrapper = getLoanSummaryWrapperUnderQuickQuote(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
	    var closingCostWrapper = getClosingCostSummaryContainerUnderQuickQuote(getLQBObj(teaserRate));
	 
	    parentWrapper.append(ratePageHeader).append(ratePageSlider).append(loanSummaryWrapper).append(buttonWrapper);
	    
	    if(!parentContainer)
	        parentContainer=$('#ce-refinance-cp');
	    $(parentContainer).append(parentWrapper).append(closingCostWrapper).append(bottomText).append(linkForDisclosure);
	    $(parentContainer).show();
	    if(teaserRateValHolder.teaserRate&&typeof(newfiObject)==="undefined"){
	        $(".lock-ratebottom-summary-clas").css("width", "664px");
	    }else{
	        $(".lock-ratebottom-summary-clas").css("width", "730px");
	    }
	    
	}

function getClosingCostSummaryContainerUnderQuickQuote(valueSet) {
    closingCostHolder=getObContainer();
    if(typeof(newfiObject)!=='undefined'){
        //Code TO get loan type for loggedin user Hardcoded For Now
    	//Rajeswari    	
        //closingCostHolder.loanType=appUserDetails!=undefined?(appUserDetails.loanType!=undefined?appUserDetails.loanType.loanTypeCd:"PUR"):"PUR";
        setClosingCostContainerValues();
    }else{
    	
        if(buyHomeTeaserRate.loanType && buyHomeTeaserRate.loanType=="PUR"){
        	populateClosingCostHolder(buyHomeTeaserRate);
        }else if(refinanceTeaserRate){
        	populateClosingCostHolder(refinanceTeaserRate);
        }
    }
    
    if(typeof(buyHomeRefinanceRate)!=='undefined'){
        if(buyHomeRefinanceRate.loanType){
        	populateClosingCostHolder(buyHomeRefinanceRate);
        }
    }
    
    if(valueSet){
        closingCostHolder.valueSet=valueSet;
        closingCostHolder.initValueSet = valueSet;
    }else{
        closingCostHolder.valueSet={};
        closingCostHolder.initValueSet = valueSet;
    }
    var parentWrapper = $('<div>').attr({
        "class": "closing-cost-wrapper"
    });
    if(valueSet.dummyData){
        parentWrapper.html(getHeaderText(getNoProductMessageInLockRatePage()));
    }else{
        var header = getClosingCostHeaderUnderQuickQuote("Estimated Closing Cost Summary");
       /* var descText = getHeaderText("Based on the information you have provided, below is a summary of your estimated closing costs:");
        var closingDate = $('<span>').attr({
            "class": "semibold"
        });
        descText.append(closingDate);*/
        var topContainer = getClosingCostTopConatinerUnderQuickQuote();
        var bottomContainer = getClosingCostBottomConatinerUnderQuickQuote();
        //parentWrapper.append(header).append(descText).append(topContainer).append(bottomContainer);
        parentWrapper.append(header).append(topContainer).append(bottomContainer);
    }
    return parentWrapper;
}


function getClosingCostContainerRowWithSubTextUnderQuickQuote(rowNum, desc, detail, subtext,isHomeOwnersInsurance) {
    var key=objectKeyMakerFunctionUnderQuickQuote(desc);
    if(closingCostHolder.valueSet[key]){
        detail=closingCostHolder.valueSet[key];
        lqbTeaserRateUnderQuickQuote[key]=detail;
    }
    var row = $('<div>').attr({
        "class": "closing-cost-cont-desc-row clearfix"
    });
    //NEXNF-622
   /* if (rowNum % 2 == 0) {
        row.addClass("closing-cost-cont-desc-row-even");
    }*/

    //NEXNF-483
    //NEXNF-578
    if(desc=="Interest"||desc=="Tax Reserve - Estimated 2 Months"||desc=="Homeowners Insurance Reserve - Estimated 2 Months"){
    	   var rowDesc = $('<div>').attr({
    	        "class": "closing-cost-desc eng-indent float-left"
    	    });
    }else{
    	   var rowDesc = $('<div>').attr({
    	        "class": "closing-cost-desc float-left"
    	    });
    	 //NEXNF-622
    	   row.addClass("closing-cost-cont-desc-row-even");
    }
/*    var rowDesc = $('<div>').attr({
        "class": "closing-cost-desc eng-indentfloat-left"
    });*/
    var descText = $('<div>').attr({
        "class": "semi-bold"
    }).html(desc);
   var subTextDiv = $('<div>').attr({
        "class": "subtext"
    }).html(subtext);
   
 //NEXNF-655
   if(isHomeOwnersInsurance){
   	 var bottomSubText = $('<div>').attr({
   	        "class": "closing-cost-bot-row eng-closing-cost-note"
   	    }).html("Note: Taxes for 1st and 2nd installments must be paid or will be collected at closing.");
   	   rowDesc.append(descText).append(subTextDiv).append(bottomSubText);
   }else{
	   rowDesc.append(descText).append(subTextDiv);
   }
 
    //rowDesc.append(descText).append(subTextDiv);
    
    
    var cssClass = "closing-cost-detail float-left";
    
    if(desc=="Homeowners Insurance Reserve - Estimated 2 Months" || desc=="Tax Reserve - Estimated 2 Months"){
    	cssClass = "closing-cost-detail float-left homeowners-insurance-reserve";
    }
    
    var rowDetail = $('<div>').attr({
        "class":cssClass
    }).html(detail);
    
    var rwObj=getRowHolderObject(rowDetail,detail,key);
    closingCostHolder[key]=rwObj;
    rwObj.updateView();
    rwObj.updateDataForPDF();
    rwObj.updateTaxesAndInsurances();

    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerLastRowUnderQuickQuote(rowNum, desc, detail) {
    var key=objectKeyMakerFunctionUnderQuickQuote(desc);
    if(closingCostHolder.valueSet[key]){
        detail=closingCostHolder.valueSet[key];
        lqbTeaserRateUnderQuickQuote[key]=detail;
    }
    
    var cssclass ="closing-cost-cont-desc-row clearfix";
    if(desc != "Total Estimated Prepaids"){
    	cssclass ="closing-cost-cont-desc-row clearfix light-solid-line";
    }
    
    if(desc == "Total Estimated Closing Costs"){
    	cssclass ="closing-cost-cont-desc-row clearfix light-solid-line total-es-clo-cost";
    }
    
    var row = $('<div>').attr({
        "class": cssclass
    });

    //NEXNF-622
/*    if (rowNum % 2 == 0) {
        row.addClass("closing-cost-cont-desc-row-even");
    }*/
    

        row.addClass("closing-cost-cont-desc-row-even");

    
    var rowDesc = $('<div>').attr({
        "class": "closing-cost-desc float-left"
    }).html(desc);
    var rowDetail = $('<div>').attr({
        "class": "closing-cost-detail float-left semi-bold"
    }).html(detail);
    var rwObj=getRowHolderObject(rowDetail,detail,key);
    closingCostHolder[key]=rwObj;
    rwObj.updateView();
    rwObj.updateDataForPDF();
    rwObj.updateTaxesAndInsurances();
    if(key == "totEstimatedClosingCost"){
    	rwObj.updateTotalEstimatedClosingCosts();
    }
    	
    return row.append(rowDesc).append(rowDetail);
}


function getClosingCostBottomConatinerUnderQuickQuote() {
    // removed class to fixed : NEXNF-578
	var wrapper = $('<div>').attr({
        "class": ""
    });
    //var heading = getClosingCostHeadingCont("Total Estimated Closing Cost");
    var container2 = $('<div>').attr({
        "class": "closing-cost-container"
    });
  
    //NEXNF-569
    
    var row1Con3 = getClosingCostContainerRowUnderQuickQuote(1, getClosingCostLabel("Interest"), "","");
    var row2Con3 = getClosingCostContainerRowUnderQuickQuote(2, getClosingCostLabel("Homeowners Insurance"), "");
    
/*    var row1Con2 = getClosingCostContainerRowUnderQuickQuoteWithSubTextUnderQuickQuoteUnderQuickQuote(1, getClosingCostLabel("Tax Reserve - Estimated 2 Month(s)"), "$ 1,072.00", "(Varies based on calendar month of closing)");*///NEXNF-655
    var row1Con2;
    var row2Con2;
    var headerCon2;
    var row4Con2;
	if(loanPurchaseDetailsUnderQuickQuote.impounds == "No"){
		headerCon2 = getClosingCostConatinerHeader("Estimated Prepaid and Escrows");
		row1Con2 = getClosingCostContainerRowUnderQuickQuote(1, "Tax Reserve", "$ 1,072.00", "Varies based on calendar month of closing");
		row2Con2 = getClosingCostContainerRowUnderQuickQuote(2, "Homeowners Insurance Reserve", "$ 1,072.00", "Provided you have 6 months of remaining coverage",true);
		row4Con2 = getClosingCostContainerLastRowUnderQuickQuote(4, "Total Estimated Prepaids and Escrows", "");
	}
	else{
		headerCon2 = getClosingCostConatinerHeader("Estimated Prepaids and Escrow Reserves");
		row1Con2 = getClosingCostContainerRowUnderQuickQuote(1, "Tax Reserve - Estimated 6 Months", "$ 1,072.00", "Varies based on calendar month of closing");
		row2Con2 = getClosingCostContainerRowUnderQuickQuote(2, "Homeowners Insurance Reserve - Estimated 6 Months", "$ 1,072.00", "Provided you have 6 months of remaining coverage",true);
		row4Con2 = getClosingCostContainerLastRowUnderQuickQuote(4, "Total Estimated Prepaids and Escrow Reserves", "");
	}
   /* var row2Con2 = getClosingCostContainerRowUnderQuickQuoteWithSubTextUnderQuickQuoteUnderQuickQuote(2, getClosingCostLabel("Homeowners Insurance Reserve - Estimated 2 Month(s)"), "$ 1,072.00", "(Provided you have 6 months of remaining coverage)");*/
   
    //var row1Con2 = getClosingCostContainerRowUnderQuickQuoteWithSubTextUnderQuickQuoteUnderQuickQuote(1, getClosingCostLabel("Tax Reserve - Estimated 2 Month"), "$ 1,072.00", "(Varies based on calendar month of closing)");
    //var row2Con2 = getClosingCostContainerRowUnderQuickQuoteWithSubTextUnderQuickQuoteUnderQuickQuote(2, getClosingCostLabel("Homeowners Insurance Reserve - Estimated 2 Month"), "$ 1,072.00", "(Provided you have 6 months of remaining coverage)");//NEXNF-655
     
    
    
    //container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row4Con2);
    container2.append(headerCon2).append(row1Con3).append(row1Con2).append(row2Con2).append(row4Con2);
    //NEXNF-655
    /* 
    var bottomSubText = $('<div>').attr({
        "class": "closing-cost-bot-row eng-closing-cost-note"
    }).html("Note: Taxes for 1st and 2nd installments must be paid or will be collected at closing.");
    return wrapper.append(container2).append(bottomSubText);*/
    return wrapper.append(container2);
}

function objectKeyMakerFunctionUnderQuickQuote(item) {
	switch (item) {
	case getClosingCostLabel("Lender Fee"):
		return "lenderFee813";
	case getClosingCostLabel("This is your cost or credit based on rate selected"):
		return "creditOrCharge802";
	case getClosingCostLabel("Estimated Lender Costs"):
		return "TotEstLenCost";
	case getClosingCostLabel("Appraisal Fee"):
		return "appraisalFee804";
	case getClosingCostLabel("Credit Report"):
		return "creditReport805";
	case getClosingCostLabel("Flood Certification"):
		return "floodCertification807";
	case getClosingCostLabel("Wire Fee"):
		return "wireFee812";
	case getClosingCostLabel("Owners Title Insurance"):
		return "ownersTitleInsurance1103";
	case getClosingCostLabel("Lenders Title Insurance"):
		return "lendersTitleInsurance1104";
	case getClosingCostLabel("Closing/Escrow Fee"):
		return "closingEscrowFee1102";
	case getClosingCostLabel("Recording Fee"):
		return "recordingFees1201";
	case getClosingCostLabel("City/County Tax stamps"):
		return "cityCountyTaxStamps1204";
	case getClosingCostLabel("Total Estimated Third Party Costs"):
		return "totEstThdPtyCst";
	case getClosingCostLabel("Interest"):
		return "interest901";
	case getClosingCostLabel("Homeowners Insurance"):
		return "hazIns903";
	case getClosingCostLabel("Total Prepaids"):
		return "totPrepaids";
	case "Tax Reserve - Estimated 6 Months":
		return "taxResrv1004";
	case "Tax Reserve":
		return "taxResrv1004";
	case "Homeowners Insurance Reserve - Estimated 6 Months":
		return "hazInsReserve1002";
	case "Homeowners Insurance Reserve":
		return "hazInsReserve1002";
	case "Total Estimated Prepaids and Escrows":
		return "totEstResDepWthLen";
	case "Total Estimated Prepaids and Escrow Reserves":
		return "totEstResDepWthLen";
	case getClosingCostLabel("Total Estimated Closing Cost"):
		return "totEstimatedClosingCost";
	}
	return undefined;
}

function getClosingCostContainerRowUnderQuickQuote(rowNum, desc, detail) {
    var key=objectKeyMakerFunctionUnderQuickQuote(desc);
    var indentTextFlag=false;
    if(closingCostHolder.valueSet[key]){
        detail=closingCostHolder.valueSet[key];
        lqbTeaserRateUnderQuickQuote[key]=detail;
    }
    var row = $('<div>').attr({
        "class": "closing-cost-cont-desc-row clearfix"
    });
    //NEXNF-622
   /* if (rowNum % 2 == 0) {
    	 row.addClass("closing-cost-cont-desc-row-even");
    }*/
    //NEXNF-483 and updated for 6.17 updates
    // NEXNF-537
    if(desc=="Lender Fee"||desc=="Appraisal Fee"||desc=="Credit Report"||desc=="Flood Certification"||
    		desc=="Wire Fee"||desc=="Owners Title Insurance"||desc=="Lenders Title Insurance"||desc=="Closing/Escrow Fee"||
    			desc=="Recording Fee"||desc=="Interest"||desc=="City/County Transfer Taxes"||desc=="Homeowners Insurance" || 
    				desc =="Your cost or credit based on rate selected" || desc=="Tax Reserve - Estimated 6 Months" || desc == "Tax Reserve" ||
    				desc =="Homeowners Insurance Reserve - Estimated 6 Months" || desc =="Homeowners Insurance Reserve"){
    	indentTextFlag=true;
    }else{
    	//NEXNF-622
    	 row.addClass("closing-cost-cont-desc-row-even");
    }
    var rowDesc="";
    if(indentTextFlag){
    	 rowDesc = $('<div>').attr({
            "class": "closing-cost-desc eng-indent float-left"
        }).html(desc);
    }else{
    	 rowDesc = $('<div>').attr({
            "class": "closing-cost-desc float-left"
        }).html(desc);
    }
    //end
   /* var rowDesc = $('<div>').attr({
        "class": "closing-cost-desc float-left"
    }).html(desc);*/
    var rowDetail = $('<div>').attr({
        "class": "closing-cost-detail float-left"
    }).html(detail);
    var rwObj=getRowHolderObject(rowDetail,detail,key);
    closingCostHolder[key]=rwObj;
    rwObj.updateView();
    rwObj.updateDataForPDF();
    rwObj.updateTaxesAndInsurances();
    
    return row.append(rowDesc).append(rowDetail);
}


function getClosingCostTopConatinerUnderQuickQuote() {
    var wrapper = $('<div>').attr({
        //"class": "closing-cost-cont-wrapper-top"
    });
    //var heading = getClosingCostHeadingCont("Estimated Closing Costs");
    var container1 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon1 = getClosingCostConatinerHeader("Estimated Lender Costs");
    var row1Con1 = getClosingCostContainerRowUnderQuickQuote(1, getClosingCostLabel("Lender Fee"), "");
    var row2Con1 = getClosingCostContainerRowUnderQuickQuote(2, getClosingCostLabel("This is your cost or credit based on rate selected"), "");
    var row3Con1 = getClosingCostContainerLastRowUnderQuickQuote(3, getClosingCostLabel("Estimated Lender Costs"), "");
    container1.append(headerCon1).append(row1Con1).append(row2Con1).append(row3Con1);
    var container2 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon2 = getClosingCostConatinerHeader("Estimated Third Party Costs");
    var row1Con2 = getClosingCostContainerRowUnderQuickQuote(1, getClosingCostLabel("Appraisal Fee"), "");
    var row2Con2 = getClosingCostContainerRowUnderQuickQuote(2, getClosingCostLabel("Credit Report"), "");
    var row3Con2 = getClosingCostContainerRowUnderQuickQuote(3, getClosingCostLabel("Flood Certification"), "");
    var row4Con2 = getClosingCostContainerRowUnderQuickQuote(4, getClosingCostLabel("Wire Fee"), "");
    var row4_1Con2;
    if(closingCostHolder.loanType&&closingCostHolder.loanType=="PUR")
        row4_1Con2 = getClosingCostContainerRowUnderQuickQuote(5, getClosingCostLabel("Owners Title Insurance"), "");
    var row5Con2 = getClosingCostContainerRowUnderQuickQuote(5, getClosingCostLabel("Lenders Title Insurance"), "");
    var row6Con2 = getClosingCostContainerRowUnderQuickQuote(6, getClosingCostLabel("Closing/Escrow Fee"), "");
    var row7Con2 = getClosingCostContainerRowUnderQuickQuote(7, getClosingCostLabel("Recording Fee"), "");
    var row8Con2;
    if(closingCostHolder.loanType&&closingCostHolder.loanType=="PUR")
    	//NEXNF-483
        //row8Con2= getClosingCostContainerRowUnderQuickQuote(8, getClosingCostLabel("City/County Tax stamps"), "$ 107.00");
    row8Con2= getClosingCostContainerRowUnderQuickQuote(8, getClosingCostLabel("City/County Tax stamps"), "");
    var row9Con2 = getClosingCostContainerLastRowUnderQuickQuote(9, getClosingCostLabel("Total Estimated Third Party Costs"), "");
    container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row3Con2).append(row4Con2).append(row4_1Con2).append(row5Con2).append(row6Con2).append(row7Con2).append(row8Con2).append(row9Con2);
    
    var container3 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    //var headerCon3 = getClosingCostConatinerHeader("Estimated Prepaids");
   // var row1Con3 = getClosingCostContainerRowUnderQuickQuoteWithSubTextUnderQuickQuote(1, getClosingCostLabel("Interest"), "","");
    //var row2Con3 = getClosingCostContainerRowUnderQuickQuote(2, getClosingCostLabel("Homeowners Insurance"), "");
    //var row3Con3 = getClosingCostContainerLastRow(3, getClosingCostLabel("Total Prepaids"), "");
    var row10Con3 = getClosingCostContainerLastRowUnderQuickQuote(10, getClosingCostLabel("Total Estimated Closing Cost"), "");
    
    //container3.append(headerCon3).append(row1Con3).append(row2Con3).append(row3Con3).append(row10Con3);
    container3.append(row10Con3);
    
    //return wrapper.append(heading).append(container1).append(container2).append(container3);
    return wrapper.append(container1).append(container2).append(container3);
}
function getClosingCostHeaderUnderQuickQuote(text) {
    var header = $('<div>').attr({
        "class": "closing-cost-header  cus-eng-font capitalize ccSummary"
    }).html(text);
    return header;
}

function getLoanSummaryWrapperUnderQuickQuote(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
    
    var customerInputData = inputCustomerDetails;
    var loanTypeText;
    if(teaserRateValHolder.teaserRate){
        loanTypeText = customerInputData.loanType;
    }else{
        loanTypeText =  customerInputData.loanType.loanTypeCd;
    } 
    var loanDescription;
    var loanClosingCost;
    if (loanTypeText == "PUR") {
        loanDescription=getLoanSummaryContainerPurchaseUnderQuickQuote(teaserRate, customerInputData);
    } else {
        loanDescription=getLoanSummaryContainerRefinanceUnderQuickQuote(teaserRate, customerInputData);
    }
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });

    parentWrapper.append(loanClosingCost).append(loanDescription);

   
    return parentWrapper;
}

function getLoanSummaryContainerRefinanceUnderQuickQuote(teaserRate, customerInputData) {
    
    var path = "CEP";
    var yearValues = teaserRate;
   
    var rateVO = getLQBObj(yearValues);
    globalChangeContainer.ratVo=rateVO;
     
   var yearLeftOnMortgage;
    var loanAmount  = teaserRateValHolder.teaserRate==true?customerInputData.currentMortgageBalance:customerInputData.refinancedetails.currentMortgageBalance;
    var refinanceOption;
    if(teaserRateValHolder.teaserRate){
        refinanceOption=customerInputData.refinanceOption;
        if (customerInputData.refinanceOption == "REFLMP") refinanceOpt = "Lower monthly payment";
        if (customerInputData.refinanceOption == "REFMF") refinanceOpt = "Pay off mortgage faster";
        if (customerInputData.refinanceOption == "REFCO"){
            refinanceOpt = "Take cash out";
            
            var cashTakeOut = getFloatValue(customerInputData.cashTakeOut);
            var currentMortgageBalance = getFloatValue(customerInputData.currentMortgageBalance);
            loanAmount = cashTakeOut + currentMortgageBalance;
        }
        yearLeftOnMortgage=getFloatValue(customerInputData.yearLeftOnMortgage);
    }else{
        refinanceOption=customerInputData.refinancedetails.refinanceOption;
        if (refinanceOption == "REFLMP") refinanceOpt = "Lower monthly payment";
        if (refinanceOption == "REFMF") refinanceOpt = "Pay off mortgage faster";
        if (refinanceOption == "REFCO"){
            refinanceOpt = "Take cash out";
            
            var cashTakeOut = getFloatValue(customerInputData.refinancedetails.cashTakeOut);
            var currentMortgageBalance = getFloatValue(customerInputData.refinancedetails.currentMortgageBalance);
            loanAmount = cashTakeOut + currentMortgageBalance;
        }
        yearLeftOnMortgage=getFloatValue(customerInputData.refinancedetails.mortgageyearsleft);
    }
   
    var  monthlyPayment; 
    if(teaserRateValHolder.teaserRate)
        monthlyPayment= parseFloat(removedDoller(removedComma(customerInputData.currentMortgagePayment))); 
    else
        monthlyPayment  = parseFloat(removedDoller(removedComma(customerInputData.refinancedetails.currentMortgagePayment)));

    var principalInterest = parseFloat(removedDoller(removedComma(rateVO.payment)));
    var totalEstMonthlyPayment = principalInterest;
    var Insurance;
    if(teaserRateValHolder.teaserRate)
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.annualHomeownersInsurance)));
    else
    	Insurance =  parseFloat(removedDoller(removedComma(customerInputData.annualHomeownersInsurance)));
    //    Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyInsuranceCost)));
    var tax;
    if(teaserRateValHolder.teaserRate)
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    else
    	tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
      //  tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyTaxesPaid)));
    if(isNaN(getFloatValue(tax)))
        tax="";
    else{
    	tax = getFloatValue(tax) / 12;
    }
    if(isNaN(getFloatValue(Insurance)))
        Insurance="";
    else{
    	Insurance = getFloatValue(Insurance) / 12;
    }
    var isIncludeTaxes=teaserRateValHolder.teaserRate==true?customerInputData.isIncludeTaxes:customerInputData.refinancedetails.includeTaxes;
    var investment = (Insurance + tax);
    if(isIncludeTaxes =="Yes"||isIncludeTaxes ==true){
        
        monthlyPayment = monthlyPayment -investment ;
    }
    totalEstMonthlyPayment  = principalInterest + investment;
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));

    var hgLow="";
        if(principalInterest<monthlyPayment){
            hgLow='<font color="green"><b>Lower</b></font>';
    }else{
        //hgLow='<font color="red"><b>Higher</b></font>';
        hgLow='<font ><b>Higher</b></font>';
    }


    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var wrapper = $('<div>').attr({
        "class" : "loan-summary-container"
    });
    
    var container = $('<div>').attr({
        "class": "lock-rate-header-val-cont-clas clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    container.append(leftCol).append(rightCol);
    
    wrapper.append(container);

    parentWrapper.append(wrapper);

    var estClosingCstRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost) , "closingCostId");
    leftCol.append(estClosingCstRow);

    var bottomRcRow = getLoanSummaryLastRow("Estimated<br/>Mortgage Payment ", showValue(principalInterest),"principalIntId");
    rightCol.append(bottomRcRow);

    container = $('<div>').attr({
        "class": "lock-ratebottom-summary-clas clearfix"
    });
    leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    container.append(leftCol).append(rightCol);
    
    wrapper.append(container);

    if(refinanceOption == "REFCO"){
        var loanBalCol = getInputElmentRowUnderQuickQuote("loanBal","Current Loan <br/>Balance",showValue(currentMortgageBalance),"firstInput",customerInputData);        
        var cashOutCol = getInputElmentRowUnderQuickQuote("cashOut","Cash Out",showValue(cashTakeOut),"secondInput",customerInputData);
        var proposedLoanAmtCol = getLoanSummaryLastRow("Loan<br/> Amount", showValue(loanAmount),"loanAmount",true);
        leftCol.append(loanBalCol);
        leftCol.append(cashOutCol);
        leftCol.append(proposedLoanAmtCol);
    }else{
        var proposedLoanAmtCol = getInputElmentRowUnderQuickQuote("propLoanAmt","Loan<br/> Amount",showValue(loanAmount),"loanAmount",customerInputData);
        leftCol.append(proposedLoanAmtCol);
    }
    
      //  var currentMortgagePayment = getLoanSummaryLastRow("Current<br/> Mortgage Payment", showValue(monthlyPayment),"monthlyPaymentId",true,true);
     //   rightCol.append(currentMortgagePayment);
    //   var monthlyDiff = getLoanSummaryLastRow('Estimated Mortgage<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId",undefined,true);
     //   rightCol.append(monthlyDiff);


    var toggletaxComponent=getTaxInsDropToggleBtn(showValue(investment),true);
    if($('div[id="impound"]').attr('value') == 'Yes'){
    rightCol.append(toggletaxComponent);
    }
    var taxRow = getLoanSummaryRowRatePage("Tax" ,showValue(tax),"calTaxID2","taxContainerId",true,true,true);
    rightCol.append(taxRow);

    var taxRow = getLoanSummaryRowRatePage("Insurance" ,showValue(Insurance),"CalInsuranceID2","insContainerId",true,true,true);
    rightCol.append(taxRow);    

    var totHousingPayment = getLoanSummaryLastRow("Estimated<br/> Housing Payment", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId",true,true);
    rightCol.append(totHousingPayment);

    var grp=$('<div>').attr({
        "class": "loan-rate-tableRow-container"
    });
    var loanTypeRow=getLoanTableSummary("Loan Type", "Refinance", "loanType");
    grp.append(loanTypeRow);

    var loanProgRow=getLoanTableSummary("Loan Program", showValidData(rateVO.yearData) +" Year Fixed", "loanprogramId");
    loanPurchaseDetailsUnderQuickQuote.loanProgram = showValidData(rateVO.yearData) +"-Year Fixed";
    grp.append(loanProgRow);
    leftCol.append(grp);
    grp=$('<div>').attr({
        "class": "loan-rate-tableRow-container"
    });
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";

    var interestRateCol = getRateAprRowCol("Rate / APR", val, formatPercentage(rateVO.APR), "teaserRateId", "aprid");
    grp.append(interestRateCol);

    loanPurchaseDetailsUnderQuickQuote.RateAndApr = val + " / "+  formatPercentage(rateVO.APR);
    leftCol.append(grp);

    return parentWrapper;
}

function getLoanSummaryContainerPurchaseUnderQuickQuote(teaserRate, customerInputData) {
    
    var path = "CEP";
    var livingSituation;
    if(teaserRateValHolder.teaserRate)
        livingSituation = capitalizeFirstLetter(customerInputData.livingSituation);
    else
        livingSituation = capitalizeFirstLetter(appUserDetails.purchaseDetails.livingSituation);
    
    var yearValues = teaserRate;
       
    var rateVO = getLQBObj(yearValues);
    globalChangeContainer.ratVo=rateVO;
   
    
    var housePrice;
    var loanAmount;
    var downPayment;
    var Insurance;
    var tax;
    if(teaserRateValHolder.teaserRate){
        housePrice = parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.housePrice))); 
        if(typeof(newfiObject)!=="undefined"){
            loanAmount =  parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.loanAmount))) ;
            downPayment = (housePrice-loanAmount);
        }else{
            downPayment =  parseFloat(removedDoller(removedComma(customerInputData.currentMortgageBalance))) ;
            loanAmount = (housePrice-downPayment);
        }
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyInsuranceCost)));
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    }else{
        housePrice = getFloatValue(customerInputData.purchaseDetails.housePrice);   
        loanAmount =  getFloatValue(customerInputData.purchaseDetails.loanAmount) ;    
        downPayment = (housePrice-loanAmount);
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyInsuranceCost)));
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyTaxesPaid)));
     
    }

    if(isNaN(getFloatValue(tax))){
        tax="";

        tax=(getFloatValue(housePrice)*.0125)/12;
    }
    else{
    	tax = getFloatValue(tax) / 12;
    }
    if(isNaN(getFloatValue(Insurance))){
        Insurance="";
        Insurance=(getFloatValue(loanAmount)*.0035)/12;
    }
    else{
    	Insurance = getFloatValue(Insurance) / 12;
    }
    var investment=tax+Insurance;
    var totEstHousingPayment=getFloatValue(rateVO.payment)+getFloatValue(tax)+getFloatValue(Insurance);
  
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var wrapper = $('<div>').attr({
        "class": "loan-summary-container"
    });
    
    var container = $('<div>').attr({
        "class" : "lock-rate-header-val-cont-clas clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    container.append(leftCol).append(rightCol);
    wrapper.append(container);
    
    var estClosingCostRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost), "closingCostId");
    leftCol.append(estClosingCostRow);

    var estMonthlyPaymnt = getLoanSummaryLastRow("Estimated<br/>Mortgage Payment", showValue(rateVO.payment) ,"principalIntId");
    rightCol.append(estMonthlyPaymnt);

    container = $('<div>').attr({
        "class": "lock-ratebottom-summary-clas clearfix"
    });
    leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    container.append(leftCol).append(rightCol);

    var purchasePrice = getInputElmentRowUnderQuickQuote("purchasePrice","Purchase Price",showValue(housePrice),"firstInput",customerInputData);
    leftCol.append(purchasePrice);

    var toggletaxComponent=getTaxInsDropToggleBtn(showValue(investment));
    rightCol.append(toggletaxComponent);

   
    var taxRow = getLoanSummaryRowRatePage("Tax" ,showValue(tax),"calTaxID2","taxContainerId",true,true);
    rightCol.append(taxRow);

    var dwnPayment = getInputElmentRowUnderQuickQuote("downPayment","Down Payment",showValue(downPayment),"secondInput",customerInputData);
    leftCol.append(dwnPayment);

 
    var taxRow = getLoanSummaryRowRatePage("Insurance" ,showValue(Insurance),"CalInsuranceID2","insContainerId",true,true);
    rightCol.append(taxRow);

    var proposedLoanAmt = getLoanSummaryLastRow("Loan<br/>Amount", showValue(loanAmount) ,"loanAmount",true);
    leftCol.append(proposedLoanAmt);

    var estHousngPaymnt = getLoanSummaryLastRow("Estimated<br/>Housing Payment", showValue(totEstHousingPayment) ,"totalEstMonthlyPaymentId",true);
    rightCol.append(estHousngPaymnt);

    wrapper.append(container);
    
    parentWrapper.append(wrapper)/*.append(bottomRow)*/;

    var loanTypeRow = getLoanSummaryRowRatePage("Loan Type" ,"Purchase","loanType");
    leftCol.append(loanTypeRow);
    
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    
    var interestRateCol = getRateAprRowCol("Rate / APR", val, formatPercentage(rateVO.APR), "teaserRateId", "aprid");;
    rightCol.append(interestRateCol);
    
    loanPurchaseDetailsUnderQuickQuote.RateAndApr = val + " / "+  formatPercentage(rateVO.APR);
    var loanProgCol = getLoanSummaryRowRatePage("Loan Program" ,showValidData(rateVO.yearData) +" Year Fixed","loanprogramId");
    loanPurchaseDetailsUnderQuickQuote.loanProgram = showValidData(rateVO.yearData) +"-Year Fixed";
    rightCol.append(loanProgCol);
    
    return parentWrapper;

}

function getInputElmentRowUnderQuickQuote(key,desc, val,inputElementId,appUserDetails,containerId) {
    var hideclass="";
    var txtAlignClas="";
    if(key=="tax"||key=="Insurance"){
        txtAlignClas="tax-clas-Rate-page"
        hideclass="hide"
    }
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix no-border-bottom "+hideclass
    });
    if(containerId){
        container .attr({
            "id": containerId
        });
    }
    var cla="";
    if(key=="cashOut"){
    	cla="loan-summary-col-desc-adj";
    }
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left "+txtAlignClas+" "+cla
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left"
    });
    
    var inputBox;
    globalChangeContainer.flag=false;
    if(key=="downPayment"){
        inputBox=getDwnPayComponent(val,inputElementId);
    }else{
        inputBox = $('<input>').attr({
            "class" : "loan-summary-sub-col-detail float-left",
            "id":inputElementId,
            "value":showValue(val),
        }).bind('keyup',{"appUserDetails":appUserDetails,"key":key},function(e){
            appUserDetails=e.data.appUserDetails;
            key=e.data.key;
            $(this).maskMoney({
                thousands:',',
                decimal:'.',
                allowZero:true,
                prefix: '$',
                precision:0,
                allowNegative:false
            }); 
            
            if(key=="propLoanAmt"||key=="cashOut"||key=="loanBal"||key=="purchasePrice")
                globalChangeContainer.flag=true;
            
            updateElementsOnSlide(globalChangeContainer.ratVo,undefined,true);
           
        
            
        });
    }
    
	if(!teaserRateValHolder.teaserRate){
	    	 if(key!="downPayment"){
	    		 inputBox = $('<div>').attr({
	    			 "id":inputElementId,
	    	         "value":showValue(val),
	    	    	}).html(showValue(val));
	    	 }
	    
	    	
	    }
    col2.append(inputBox);
    
    if(key=="Insurance" && !teaserRateValHolder.teaserRate){
        var saveBtn = $('<div>').attr({
            "class" : "cep-button-color sm-save-btn float-right"
        }).html("Save").on('click',function(){
            
            saveTaxAndInsurance();
        });
        if(!teaserRateValHolder.teaserRate){
        	saveBtn.addClass('hide');
        }
        container.append(col1).append(col2.append(saveBtn));
    }
    else if(key=="purchasePrice"||key=="cashOut"){
        container.append(col1).append(col2);
    }else if(key=="propLoanAmt"){
        container.append(col1).append(col2);
    }else
        container.append(col1).append(col2);
    
    return container;
}

function paintBuyHomeTeaserRateUnderQuickQuote(parentContainer, teaserRateData, hideCreateAccountBtn) {
    
    if (!parentContainer) {
        parentContainer = $('#ce-refinance-cp');
    }
    if (!teaserRateData) {
        teaserRateData = buyHomeTeaserRate;
    }
    delete sessionStorage.refinaceData;
    showOverleyMessage(overlayMessage);
    showOverlay();
    $.ajax({
        url: "rest/calculator/findteaseratevalue",
        type: "POST",
        data: {
            "teaseRate": JSON.stringify(teaserRateData)
        },
        datatype: "application/json",
        cache:false,
        success: function(data) {
        	hideOverlay();
            clearOverlayMessage();
            if((data.error||data==""||data=="error")&&typeof(newfiObject)==='undefined'){
                var container = $('<div>').attr({
                    "class": "ce-rate-main-container"
                });
                $(parentContainer).html(container);
                var errorText=getNoProductMessageInLockRatePage();
                if(typeof(newfiObject)==='undefined')
                {
                	teaserRateValHolder.leadCustomer=true;
                }
                var mainContainer = paintApplyNow(teaserRateData,undefined,true);
                var createAccBtn= $('<div>').attr({
                    "class": "rate-btn createAccButton"
                }).html("Provide your contact information").on('click', function() {
                    var mainContainer = paintApplyNow(teaserRateData);
                    $('#ce-main-container').html(mainContainer);
                });
                $(parentContainer).append(errorText);
                if(typeof(newfiObject)==='undefined')
                    $(parentContainer).append(mainContainer);
                return
            }
            
            var ob;
            try{
                ob=JSON.parse(data);
                loanPurchaseDetailsUnderQuickQuote.isRate = true;
            }catch(exception){
                ob={};
                console.log("Invalid Data");
                loanPurchaseDetailsUnderQuickQuote.isRate = false;
            }
            paintFixYourRatePageCEPUnderQuickQuote(ob, teaserRateData, parentContainer, hideCreateAccountBtn);
            loanPurchaseDetailsUnderQuickQuote.inputCustmerDetailUnderQuickQuote = teaserRateData;
            clearOverlayMessage();
        },
        error: function() {
        	showErrorToastMessage("error");
            hideOverlay();
        }
    });
}

function paintRefinanceSeeRatesUnderQuickQuote(parentContainer,teaserRateData,hideCreateAccountBtn) {
    
	
    if(!parentContainer){
        parentContainer=$('#ce-refinance-cp');
    }
    if(!teaserRateData){
        teaserRateData=refinanceTeaserRate;
    }
    stages = 6;
    progressBaar(6);
    delete sessionStorage.refinaceData;
   
    
    showOverleyMessage(overlayMessage);
    showOverlay();
    $.ajax({
        url: "rest/calculator/findteaseratevalue",
        type: "POST",
        data: {
            "teaseRate": JSON.stringify(teaserRateData)
        },
        datatype: "application/json",
        cache:false,
        success: function(data) {
        	
           hideOverlay();
           clearOverlayMessage();
            if((data.error||data==""||data=="error")&&typeof(newfiObject)==='undefined'){
               // var quesTxt = "Let us Contact You";
                var container = $('<div>').attr({
                    "class": "ce-rate-main-container"
                });
          
                $(parentContainer).html(container);
                var errorText=getNoProductMessageInLockRatePage();
                if(typeof(newfiObject)==='undefined')
                {
                	teaserRateValHolder.leadCustomer=true;
                }
                var mainContainer = paintApplyNow(teaserRateData,undefined,true);
     
                $(parentContainer).append(errorText);
                if(typeof(newfiObject)==='undefined')
                    $(parentContainer).append(mainContainer);
                return
            }


            var ob;
            try{
                ob=JSON.parse(data);
                loanPurchaseDetailsUnderQuickQuote.isRate = true;
                if(ob.length>0){
                    responseTime=ob[0].responseTime;
            }
            }catch(exception){
                ob={};
                responseTime="";
                console.log("Invalid Data");
                loanPurchaseDetailsUnderQuickQuote.isRate = false;
            }
           
            paintFixYourRatePageCEPUnderQuickQuote(ob, teaserRateData,parentContainer,hideCreateAccountBtn);
            loanPurchaseDetailsUnderQuickQuote.inputCustmerDetailUnderQuickQuote = teaserRateData;
            clearOverlayMessage();
       
              
             
        },
        error: function(data) {
        	validateDropDown();
            showErrorToastMessage(errorInrefinanceRates+data);
            
            hideOverlay();
        }
    });
    
}

function processCommonParameters(){
	buyHomeRefinanceRate.propertyType = $('#propertyType').attr('type');
	buyHomeRefinanceRate.residenceType = $('#residenceType').attr('type');
	buyHomeRefinanceRate.homeWorthToday = parseFloat(removedDoller(removedComma($('input[id="homeWorthToday"]').val())));
	buyHomeRefinanceRate.currentMortgageBalance = parseFloat(removedDoller(removedComma($('input[id="currentMortgageBalance"]').val())));
	buyHomeRefinanceRate.zipCode = $('input[id="zipCode"]').val();
	loanPurchaseDetailsUnderQuickQuote.impounds  = $('div[id="impound"]').attr('value');
	loanPurchaseDetailsUnderQuickQuote.firstName = $('input[id="firstName"]').val();
	firstName = loanPurchaseDetailsUnderQuickQuote.firstName;
	loanPurchaseDetailsUnderQuickQuote.lastName = $('input[id="lastName"]').val();
	lastName = loanPurchaseDetailsUnderQuickQuote.lastName;
	loanPurchaseDetailsUnderQuickQuote.emailId = $('input[id="emailID"]').val();
	var phoneNumber = $('input[id="primaryPhoneID"]').val();
	loanPurchaseDetailsUnderQuickQuote.phoneNo = phoneNumber.replace(/[^0-9]/g, '');
	buyHomeRefinanceRate.privateincludeTaxes = "No";
	if($('div[id="impound"]').attr('value') == 'Yes'){
		if($('input[id="propertyTaxesPaid"]').val() != null && $('input[id="propertyTaxesPaid"]').val() != ""){
			buyHomeRefinanceRate.propertyTaxesPaid = parseFloat(removedDoller(removedComma($('input[id="propertyTaxesPaid"]').val()))); 	
		}
		if($('input[id="annualHomeownersInsurance"]').val() != null && $('input[id="annualHomeownersInsurance"]').val() != "" ){
			buyHomeRefinanceRate.annualHomeownersInsurance = parseFloat(removedDoller(removedComma( $('input[id="annualHomeownersInsurance"]').val())));	
		}
		buyHomeRefinanceRate.privateincludeTaxes = "Yes";
	}
	
}

function processBuyHomeUnderQuickQuote(){
	buyHomeRefinanceRate.livingSituation = "renting";
	buyHomeRefinanceRate.purchaseDetails.housePrice = parseFloat(removedDoller(removedComma($('input[id="homeWorthToday"]').val()))); 
	buyHomeRefinanceRate.purchaseDetails.loanAmount = parseFloat(removedDoller(removedComma($('input[id="homeWorthToday"]').val()))) - parseFloat(removedDoller(removedComma($('input[id="currentMortgageBalance"]').val())));
	buyHomeRefinanceRate.purchaseDetails.rentPerMonth = parseFloat(removedDoller(removedComma($('input[id="rentPerMonth"]').val())));
	buyHomeRefinanceRate.purchaseDetails.buyhomeZipPri = $('input[id="zipCode"]').val();
	
	buyHomeRefinanceRate.impounds = $('div[id="impound"]').attr('value');
	if($('input[id="rentPerMonth"]').val() != ""){
		buyHomeRefinanceRate.rentPerMonth = parseFloat(removedDoller(removedComma($('input[id="rentPerMonth"]').val()))); 
	}	
	processCommonParameters();
	paintBuyHomeTeaserRateUnderQuickQuote("",buyHomeRefinanceRate);
	


}

function processRateAndTermUnderQuickQuote(){
	buyHomeRefinanceRate.currentMortgagePayment = parseFloat(removedDoller(removedComma($('input[id="currentMortgagePayment"]').val())));
	processCommonParameters();
	paintRefinanceSeeRatesUnderQuickQuote("",buyHomeRefinanceRate);
	
}

function processCashOutUnderQuickQuote(){
	buyHomeRefinanceRate.cashTakeOut = parseFloat(removedDoller(removedComma($('input[id="cashTakeOut"]').val())));
	buyHomeRefinanceRate.currentMortgagePayment = parseFloat(removedDoller(removedComma($('input[id="currentMortgagePayment"]').val())));
	processCommonParameters();
	paintRefinanceSeeRatesUnderQuickQuote("",buyHomeRefinanceRate);
	
}

/*To expand and collapse Quote Details*/
$('body').on('click','#quick-quote-header-down-icn',function(e){
	$('.quick-quote-question-section').toggle();	
});

$(document).click(function(){
	if($('#residence_type_id').css("display") == "block"){
		$('#residence_type_id').toggle();
	}
	if($('#property_type_id').css("display") == "block"){
		$('#property_type_id').toggle();
	}
	
});


$("body").on('click',"#quick-quote-generate-pdf",function(e){
	for (var key in closingCostHolder.valueSet) {
		if(key=="taxResrv1004" || key == "hazInsReserve1002" ){
			continue;
		}
		lqbTeaserRateUnderQuickQuote[key]=closingCostHolder.valueSet[key];
	}
	sendPurchasePdfUnderQuickQuote();
});

$('body').keyup('#currentMortgageBalance',function(e) {
    console.log('keyup called');
	calculateInsuranceValue();
 });

$('body').keyup('#homeWorthToday',function(e) {
    console.log('keyup called');
    if($('#quick-quote-loan-type-id').attr('loan-type') == PURCHASE){
    	calculateInsuranceValue();
	}

 });