var count = 0;

function loadQuickQoutePage(){
	$('#right-panel').html('');
	$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	var agentDashboardMainContainer = $('<div>').attr({
		"id" : "quick-quote-dashboard-container",
		"class" : "rp-agent-dashboard"
	});
	var wrapper = $('<div>').attr({
		"class" : "quick-quote-wrapper"
	});
	
	var container = $('<div>').attr({
		"class" : "quick-quote-container",
		"id" : "quick-quote-container-id"
	});
	var header = getCompletYourApplicationHeader(true,"Quick Quote");	
	var section1 = getSectionOneOfQuickQuote();
	container.append(header).append(section1);
	wrapper.append(container);
	agentDashboardMainContainer.append(wrapper);
	$('#right-panel').append(agentDashboardMainContainer);
	
	

}

	var questionlistOne = [{
		question : "Payment for rent :",
		id : "rentPerMonth",
		column : 0
	},{
		question : "Desired purchase price :",
		id : "homeWorthToday",
		column : 1
	},{
		question : "Down payment :",
		id : "currentMortgageBalance",
		column : 0
	},{
		question : "Property type :",
		id : "propertyType",
		column : 1,
		list : [{
			text: "Single family residence",
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
		question : "Property use :",
		id : "propertyUse",
		 column : 0,
		 list: [{
	            text: "Primary residence",
	            value: "0"
	        }, {
	            text: "Vacation/Second home",
	            value: "1"
	        }, {
	            text: "Investment property",
	            value: "2"
	        }],
	        selected: ""
	},{
		 question : "Zip Code :",
		 "id": "zipCode",
		 column : 1
	}];
	
	
	var questionlistTwo = [{
		question : "Current mortgage balance :",
		id : "cashTakeOut",
		column : 0
	},{
		question : "Current mortgage payment :",
		id : "currentMortgagePayment",
		column : 1
	},/*{
		question : "Payment entered above includes property taxes and/or homeowners insurance?",
		id : "isIncludeTaxes",
		column : 1,
		type : "yesNo"
	},*/{
		question : "Home worth today :",
		id : "homeWorthToday",
		column : 0
	},{
		question : "Property type :",
		id : "propertyType",
		column : 1,
		list : [{
			text: "Single family residence",
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
		question : "Property use :",
		id : "propertyUse",
		 column : 0,
		 list: [{
	            text: "Primary residence",
	            value: "0"
	        }, {
	            text: "Vacation/Second home",
	            value: "1"
	        }, {
	            text: "Investment property",
	            value: "2"
	        }],
	        selected: ""
	},{
		 question : "Zip Code",
		 id : "zipCode",
		 column : 1
	},{
		question : "Property taxes :",
		id : "propertyTaxesPaid",
		/*column : 0,*/
		type : "month/year"
	},{
		question : "Homeowners insurance :",
		id : "annualHomeownersInsurance",
		/*column : 0,*/
		type : "month/year"
	},];
	

	var questionlistThree = [{
		question : "Cash to be taken out :",
		id : "cashTakeOut",
		column : 0
	},{
		question : "Current mortgage balance :",
		id : "currentMortgageBalance",
		column : 1
	},{
		question : "Current mortgage payment :",
		id : "currentMortgagePayment",
		column : 0
	},/*{
		question : "Payment entered above includes property taxes and/or homeowners insurance?",
		id : "isIncludeTaxes",
		column : 1,
		type : "yesNo"
	},*/{
		question : "Home worth today :",
		id : "homeWorthToday",
		column : 1
	},{
		question : "Property type :",
		id : "propertyType",
		column : 0,
		list : [{
			text: "Single family residence",
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
		question : "Property use :",
		id : "propertyUse",
		 column : 1,
		 list: [{
	            text: "Primary residence",
	            value: "0"
	        }, {
	            text: "Vacation/Second home",
	            value: "1"
	        }, {
	            text: "Investment property",
	            value: "2"
	        }],
	        selected: ""
	},{
		 question : "Zip Code :",
		 id : "zipCode",
		 column : 0
	},{
		question : "Property taxes :",
		id : "propertyTaxesPaid",
/*		column : 0,*/
		type : "month/year"
	},{
		question : "Homeowners insurance :",
		id : "annualHomeownersInsurance",
		/*column : 0,*/
		type : "month/year"
	},];
	
function getSectionOneOfQuickQuote(){
	var buttonList = [{
		"title" : "Purchase",
		"data" : questionlistOne,
		"className" : "quick-quote-col col-one  float-left",
		"id" : "quick-quote-one-id"
	
	},
	{
		"title" : "Rate & Term",
		"data" : questionlistTwo,
		"className" : "quick-quote-col col-two float-left",
		"id" : "quick-quote-two-id"
	},
	{
		"title" : "Cash-out",
		"data" : questionlistThree,
		"className" : "quick-quote-col col-three float-left",
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
		count++;
		
	});	
	 return button;
}

function paintDataSection(option,isDefault){

	if($('.quick-quote-question-section').length > 0){
		$('.quick-quote-question-section').remove();
	}
	var questionSection = $('<div>').attr({
		"class" : "quick-quote-question-section clearfix"
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
	}).html("Get Quote");
	
	
	for(var i=0;i<option.length;i++){

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
			
		}else {
			
			rowRHS = $('<input>').attr({
				"class" : "quick-quote-row-RHS float-left",
				"value" : "",
				"id" : option[i].id
			});
		} 
		
		var question = option[i].question;
		if(question.length >= 23){
			rowLHS.addClass('quick-quote-row-LHS-adj');
		}
		divMainRow.append(rowLHS).append(rowRHS);
		
		if(option[i].column == 0){
			sectionLHS.append(divMainRow);
		}else if(option[i].type == "month/year"){
			sectionThree.append(divMainRow);
		}else if(option[i].column == 1 && option[i].type == undefined){
			sectionRHS.append(divMainRow);
		}
		questionSection.append(sectionLHS).append(sectionRHS).append(sectionThree);
		 $('#quick-quote-container-id').append(questionSection);
		
	}
	 $('#quick-quote-container-id').append(quickQoute);
}


function appendDropdown(options){
	var div = $('<div>').attr({
		
	});
	var row = $('<div>').attr({
		"class" : "quick-quote-row-RHS  quick-quote-option-selected float-left",
		"id" : options.id,
		"value" : ""
	}).html("Select One").on('click', function(e) {
    	e.stopPropagation();
    	$('.quick-quote-dropdown-cont').hide();
        $(this).parent().find('.quick-quote-dropdown-cont').toggle();
    });

	
	 var dropDownContainer = $('<div>').attr({
	        "class": "quick-quote-dropdown-cont hide"
	    });
	 var optionCont ="";
	   for (var i = 0; i < options.list.length; i++) {
	        var option = options.list[i];
	       // alert('option value is   '+ option.value);
	         optionCont = $('<div>').attr({
	                "class": "quick-quote-option-sel",
	                "value" : option.value,
	                "text" : option.text
	            }).data({
	                "value": option.value
	            }).html(option.text);
	         
	         dropDownContainer.append(optionCont);

	         optionCont.on('click',function(e){
	        	 $(this).closest('.quick-quote-dropdown-cont').toggle();
	        	 $(row).html($(this).html());
	        	 $(row).data("value",$(this).data("value"));       	
	         });
	         
	   }

	   return div.append(row).append(dropDownContainer);
}

function appendYesNoQuestion(option){
	
	var mainDiv = $('<div>').attr({
		"class":"quick-quote-yesno-container",
		"id" : option.id
	});
	
	var yesContent = $('<div>').attr({
		"class":"cep-button-color quick-quote-yes-no-container float-left",
		"id":"quick-quote-yes-container-id",
		"isSelected":""
	}).html("Yes").bind('click',function(e){
		if($('#quick-quote-no-container-id').hasClass('quick-quote-button')){			
			$('#quick-quote-no-container-id').removeClass('quick-quote-button');
		}
		$(this).addClass('quick-quote-button');
		$(this).attr('value',$(this).html());
		
		$(this).attr({
			"isSelected" : true,
			"value" : $(this).html()
		});
	});

	var noContent = $('<div>').attr({
		"class":"cep-button-color quick-quote-yes-no-container float-left",
		"id":"quick-quote-no-container-id",
		"isSelected":""
	}).html("No").bind('click',function(e){
		if($('#quick-quote-yes-container-id').hasClass('quick-quote-button')){			
			$('#quick-quote-yes-container-id').removeClass('quick-quote-button');
		}
		$(this).addClass('quick-quote-button');

		$(this).attr({
			"isSelected" : true,
			"value" : $(this).html()
		});
	});
	
	return mainDiv.append(yesContent).append(noContent);

}

function appendMonthYearQuestion(option){
	
	var mainDiv = $('<div>').attr({
		"class" : "quick-quote-monthyear-container",
		"id" : "quick-quote-monthyear-container-id"
	});
	
	var input = $('<input>').attr({
		"class" : "quick-quote-row-RHS month-year-row-adj float-left",
		"id" : option.id,
		"value" : ""
	});
	
	var text = $('<div>').attr({
		"class" : "quick-quote-text float-left"
	}).html("per");
	
	var monthYearInput = $('<input>').attr({
		"class" : "quick-quote-row-RHS month-year-drop-down-row float-left",
		"id" : option.id,
		"value" : ""
	}).html("Select").on('click',function(e){
		  e.stopPropagation();
		  $('#quick-quote-drop-down-monthyear').hide();
		  $(this).parent().find('#quick-quote-drop-down-monthyear').toggle();
	});
	 var dropDownContainer = $('<div>').attr({
	    "class": "quick-quote-dropdown-cont quick-quote-dropdown-cont-adj hide",
	    "id" : "quick-quote-drop-down-monthyear"
	 });
	 
	 var year = $('<div>').attr({
		 "class" : "quick-quote-option-sel",
		 "id" : "quick-quote-option-sel-year"
	 }).html("Year").bind('click',function(e){
		 $(this).closest('#quick-quote-drop-down-monthyear').toggle();
		 $(this).parent().prev().html($(this).html());
		 $(this).parent().prev().attr("value",$(this).html());   
		 $('#quick-quote-drop-down-monthyear').hide();
	 });
	 
	 var month = $('<div>').attr({
		 "class" : "quick-quote-option-sel",
		 "id" : "quick-quote-option-sel-month"
	 }).html("Month").click(function(e){
		 $(this).closest('#quick-quote-drop-down-monthyear').toggle();
		 $(this).parent().prev().html($(this).html());
		 $(this).parent().prev().attr("value",$(this).html());  
		 $('#quick-quote-drop-down-monthyear').hide();
		
	 });

	 dropDownContainer.append(year).append(month);
	 return mainDiv.append(input).append(input).append(text).append(monthYearInput).append(dropDownContainer);
}