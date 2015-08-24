var count = 0;
var buyHomeRefinanceRate = new Object();
buyHomeRefinanceRate.purchaseDetails = purchaseDetails;

/*JSON structure for painting the form feild on click of loantype button*/

//JSON for purchase type
var purchaseType = [{
	loanType : "PUR",
	
},{
	question : "Payment for rent<br />(If renting) :",
	id : "rentPerMonth",
	column : 0
},{
	question : "Desired<br />purchase price :",
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
	id : "residenceType",
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

//JSON for refinanace lower monthly payment
var refinanceLowerMonthlyPayment = [{
	loanType : "REF",
	refinanceOption : "REFLMP"//to change
},{
	question : "Current mortgage balance :",
	id : "currentMortgageBalance",
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
	id : "residenceType",
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
	type : "month/year",
	monthYearId : "propTaxMonthlyOryearly"
},{
	question : "Homeowners<br />insurance :",
	id : "annualHomeownersInsurance",
	/*column : 0,*/
	type : "month/year",
	monthYearId : "propInsMonthlyOryearly"
},];

//JSON for refinanace cashout
var refinanceCashOut = [{
	loanType : "REF",
	refinanceOption : "REFCO"//to change
},{
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
	id : "residenceType",
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
	question : "Homeowners<br />insurance :",
	id : "annualHomeownersInsurance",
	/*column : 0,*/
	type : "month/year"
},];

//END


//Function which loads the contents
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
	
	var teaserRateContainer = $('<div>').attr({
		"class" : "ce-cp float-left",
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
		"className" : "quick-quote-col col-one  float-left",
		"id" : "quick-quote-one-id"
	
	},
	{
		"title" : "Rate & Term",
		"data" : refinanceLowerMonthlyPayment,
		"className" : "quick-quote-col col-two float-left",
		"id" : "quick-quote-two-id"
	},
	{
		"title" : "Cash-out",
		"data" : refinanceCashOut,
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

function paintFixYourRatePageCEPUnderQuickQuote(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn) {
    teaserRateValHolder.teaserRate=true;
    paintRatePageUnderQuickQuote(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn)
}

function paintRatePageUnderQuickQuote(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn,isViaEngagementPath) {

	   // var quesTxt = "Programs and Rates";
	    var container = $('<div>').attr({
	        "class": "ce-rate-main-container"
	    });
	/*    var quesTextCont = $('<div>').attr({
	        "class": "ce-rp-ques-text"
	    }).html(quesTxt);*/
	    // alert(JSON.stringify(refinanceTeaserRate));
	    //container.append(quesTextCont);
	    $(parentContainer).html(container);

	    var teaserRate =  modifiedLQBJsonResponse(teaserRate);
	    var rateVO = getLQBObj(teaserRate);
	    var parentWrapper = $('<div>').attr({
	        "class": "loan-summary-wrapper"
	    });
	    var ratePageHeader=getLoanSummaryHeader(inputCustomerDetails,hideCreateAccountBtn);
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
	    var closingCostWrapper = getClosingCostSummaryContainer(getLQBObj(teaserRate));
	    
	  //  $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);

	    parentWrapper.append(ratePageHeader).append(ratePageSlider).append(loanSummaryWrapper).append(buttonWrapper);
	    
	    if(!parentContainer)
	        parentContainer=$('#ce-refinance-cp');
	    $(parentContainer).append(parentWrapper).append(closingCostWrapper).append(bottomText).append(linkForDisclosure);
	    
	    if(teaserRateValHolder.teaserRate&&typeof(newfiObject)==="undefined"){
	        $(".lock-ratebottom-summary-clas").css("width", "664px");
	    }else{
	        $(".lock-ratebottom-summary-clas").css("width", "730px");
	    }
	    
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
    if (loanTypeText == "REF") {
        loanDescription=getLoanSummaryContainerRefinanceUnderQuickQuote(teaserRate, customerInputData);
    } else if (loanTypeText == "PUR") {
        loanDescription=getLoanSummaryContainerPurchaseUnderQuickQuote(teaserRate, customerInputData);
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
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyInsuranceCost)));
    var tax;
    if(teaserRateValHolder.teaserRate)
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    else
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyTaxesPaid)));
    if(isNaN(getFloatValue(tax)))
        tax="";
    if(isNaN(getFloatValue(Insurance)))
        Insurance="";
    var isIncludeTaxes=teaserRateValHolder.teaserRate==true?customerInputData.isIncludeTaxes:customerInputData.refinancedetails.includeTaxes;
    //this logic needs modification
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
    

    var estClosingCstRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost), "closingCostId");
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
    
        var currentMortgagePayment = getLoanSummaryLastRow("Current<br/> Mortgage Payment", showValue(monthlyPayment),"monthlyPaymentId",true,true);
        rightCol.append(currentMortgagePayment);
        var monthlyDiff = getLoanSummaryLastRow('Estimated Mortgage<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId",undefined,true);
        rightCol.append(monthlyDiff);


    var toggletaxComponent=getTaxInsDropToggleBtn(showValue(investment),true);
    rightCol.append(toggletaxComponent);

    var taxRow = getLoanSummaryRowRatePage("Tax" ,showValue(tax),"calTaxID2","taxContainerId",true,true,true);
    rightCol.append(taxRow);

    //var taxRow = getInputElmentRowUnderQuickQuote("Insurance","Insurance",showValue(Insurance),"CalInsuranceID2",customerInputData,"insContainerId");
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
            loanAmount =  parseFloat(removedDoller(removedComma(customerInputData.currentMortgageBalance))) ;
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
    if(isNaN(getFloatValue(Insurance))){
        Insurance="";

        Insurance=(getFloatValue(loanAmount)*.0035)/12;
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
    

    var loanProgCol = getLoanSummaryRowRatePage("Loan Program" ,showValidData(rateVO.yearData) +" Year Fixed","loanprogramId");
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
//        var saveBtn = $('<div>').attr({
//            "class" : "cep-button-color-orange sm-save-btn"//removed float-right for NEXNF-632
//        }).html("update").on('click',{"path":undefined},function(e){
//            var flag=globalChangeContainer.flag;
//            if(flag){
//                amt = $('#firstInput').val();
//                amt1 = $('#secondInput').val();
//              
//                if(teaserRateValHolder.teaserRate)
//                  modifiyTeaserRate(amt,amt1);
//                else
//                  modifiyLockRateLoanAmt(amt,amt1);
//            }
//            
//        });
//        if(!teaserRateValHolder.teaserRate){
//        	saveBtn.addClass('hide');
//        }
        container.append(col1).append(col2);
    }else if(key=="propLoanAmt"){
//        var saveBtn = $('<div>').attr({
//            "class" : "cep-button-color-orange sm-save-btn"//removed float-right for NEXNF-632
//        }).html("update").on('click',{},function(){
//            var flag=globalChangeContainer.flag;
//            if(flag){
//                amt = $('#loanAmount').val();
//                if(teaserRateValHolder.teaserRate){
//                    modifiyTeaserRate(amt);
//                }else{
//                    modifiyLockRateLoanAmt(amt);
//                }
//            }
//        });
//        if(!teaserRateValHolder.teaserRate){
//        	saveBtn.addClass('hide');
//        }
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
               // var quesTxt = "Let us Contact You";
                var container = $('<div>').attr({
                    "class": "ce-rate-main-container"
                });
               /* var quesTextCont = $('<div>').attr({
                    "class": "ce-rp-ques-text letUsContactCenter"
                }).html(quesTxt);*/
                // alert(JSON.stringify(refinanceTeaserRate));
                //container.append(quesTextCont);
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
            // var teaserRate = data;
            // paintteaserRate(data);
            //paintFixYourRatePageCEP(JSON.parse(data), refinanceTeaserRate);
            
            var ob;
            try{
                ob=JSON.parse(data);
            }catch(exception){
                ob={};
                console.log("Invalid Data");
            }
            paintFixYourRatePageCEPUnderQuickQuote(ob, teaserRateData, parentContainer, hideCreateAccountBtn);
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
             /*   var quesTextCont = $('<div>').attr({
                    "class": "ce-rp-ques-text letUsContactCenter"
                }).html(quesTxt);*/
                // alert(JSON.stringify(refinanceTeaserRate));
               // container.append(quesTextCont);
                $(parentContainer).html(container);
                var errorText=getNoProductMessageInLockRatePage();
                if(typeof(newfiObject)==='undefined')
                {
                	teaserRateValHolder.leadCustomer=true;
                }
                var mainContainer = paintApplyNow(teaserRateData,undefined,true);
                //6.12 Portal testing and Updates
                /* var createAccBtn= $('<div>').attr({
                    "class": "rate-btn createAccButton"
                }).html("Provide your contact information").on('click', function() {
                    var mainContainer = paintApplyNow(teaserRateData);
                    $('#ce-main-container').html(mainContainer);
                });*/
                $(parentContainer).append(errorText);
                if(typeof(newfiObject)==='undefined')
                    /*$(parentContainer).append(createAccBtn);*/
                    $(parentContainer).append(mainContainer);
                return
            }


            var ob;
            try{
                ob=JSON.parse(data);
                if(ob.length>0){
                    responseTime=ob[0].responseTime;
            }
            }catch(exception){
                ob={};
                responseTime="";
                console.log("Invalid Data");
            }
            

           // alert('createLoan data is '+data)
            paintFixYourRatePageCEPUnderQuickQuote(ob, teaserRateData,parentContainer,hideCreateAccountBtn);
            clearOverlayMessage();
           /* }*/
              
             
        },
        error: function(data) {
        	validateDropDown();
            showErrorToastMessage(errorInrefinanceRates+data);
            
            hideOverlay();
        }
    });
    
}


function paintBuyHomeUnderQuickQuote(){
	buyHomeRefinanceRate.livingSituation = "renting";
	buyHomeRefinanceRate.purchaseDetails.housePrice = $('input[id="homeWorthToday"]').val();
	buyHomeRefinanceRate.purchaseDetails.loanAmount = (getFloatValue($('input[id="homeWorthToday"]').val()) -getFloatValue($('input[id="currentMortgageBalance"]').val()));
	buyHomeRefinanceRate.purchaseDetails.rentPerMonth = $('input[id="rentPerMonth"]').val();
	buyHomeRefinanceRate.rentPerMonth = $('input[id="rentPerMonth"]').val();
	buyHomeRefinanceRate.homeWorthToday = $('input[id="homeWorthToday"]').val();
	buyHomeRefinanceRate.currentMortgageBalance = $('input[id="currentMortgageBalance"]').val();
	buyHomeRefinanceRate.propertyType = $('div[id="propertyType"]').attr('value');
	buyHomeRefinanceRate.residenceType = $('div[id="residenceType"]').attr('value');
	buyHomeRefinanceRate.zipCode = $('input[id="zipCode"]').val();
	paintBuyHomeTeaserRateUnderQuickQuote("",buyHomeRefinanceRate);
}

function paintRateAndTermUnderQuickQuote(){
	buyHomeRefinanceRate.currentMortgageBalance = $('input[id="currentMortgageBalance"]').val();
	buyHomeRefinanceRate.currentMortgagePayment = $('input[id="currentMortgagePayment"]').val();
	buyHomeRefinanceRate.isIncludeTaxes = "Yes";
	buyHomeRefinanceRate.propertyTaxesPaid = $('input[id="propertyTaxesPaid"]').val();
	buyHomeRefinanceRate.annualHomeownersInsurance = $('input[id="annualHomeownersInsurance"]').val();
	buyHomeRefinanceRate.propTaxMonthlyOryearly = $('input[id="propTaxMonthlyOryearly"]').val();
	buyHomeRefinanceRate.propInsMonthlyOryearly = $('input[id="propInsMonthlyOryearly"]').val();
	buyHomeRefinanceRate.homeWorthToday = $('input[id="homeWorthToday"]').val();
	buyHomeRefinanceRate.propertyType = $('div[id="propertyType"]').attr('value');
	buyHomeRefinanceRate.residenceType = $('div[id="residenceType"]').attr('value');
	buyHomeRefinanceRate.zipCode = $('input[id="zipCode"]').val();
	paintRefinanceSeeRatesUnderQuickQuote("",buyHomeRefinanceRate);
}

function paintCashOutUnderQuickQuote(){
	buyHomeRefinanceRate.cashTakeOut = $('input[id="cashTakeOut"]').val();
	buyHomeRefinanceRate.currentMortgageBalance = $('input[id="currentMortgageBalance"]').val();
	buyHomeRefinanceRate.currentMortgagePayment = $('input[id="currentMortgagePayment"]').val();
	buyHomeRefinanceRate.isIncludeTaxes = "Yes"
	buyHomeRefinanceRate.propertyTaxesPaid = $('input[id="propertyTaxesPaid"]').val();
	buyHomeRefinanceRate.annualHomeownersInsurance = $('input[id="annualHomeownersInsurance"]').val();
	buyHomeRefinanceRate.propTaxMonthlyOryearly = $('input[id="propTaxMonthlyOryearly"]').val();
	buyHomeRefinanceRate.propInsMonthlyOryearly = $('input[id="propInsMonthlyOryearly"]').val();
	buyHomeRefinanceRate.homeWorthToday = $('input[id="homeWorthToday"]').val();
	buyHomeRefinanceRate.propertyType = $('div[id="propertyType"]').attr('value');
	buyHomeRefinanceRate.residenceType = $('div[id="residenceType"]').attr('value');
	buyHomeRefinanceRate.zipCode = $('input[id="zipCode"]').val();
	paintRefinanceSeeRatesUnderQuickQuote("",buyHomeRefinanceRate);
}

/*Function which paint form feild elements on click of button*/
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
	}).html("Get Quote").on('click', function(){
		
		buyHomeRefinanceRate.loanType = $('div[id="quick-quote-loan-type-id"]').attr('loan-type');
		
		if(buyHomeRefinanceRate.loanType == "PUR"){
			paintBuyHomeUnderQuickQuote();
		}
		else{
			buyHomeRefinanceRate.refinanceOption = $('div[id="quick-quote-loan-type-id"]').attr('ref-option');
			if(buyHomeRefinanceRate.refinanceOption == "REFLMP"){
				paintRateAndTermUnderQuickQuote();
			}
			else{
				paintCashOutUnderQuickQuote();
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
	}
		questionSection.append(sectionLHS).append(sectionRHS).append(sectionThree).append(loanType);
		 $('#quick-quote-container-id').append(questionSection);
		
	}
	 $('#quick-quote-container-id').after(quickQoute);
}


/*Function which paint the question with dropdown type*/
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
	                "value" : "",
	                "text" : option.text
	            }).data({
	                "value": option.value
	            }).html(option.text);
	         
	         dropDownContainer.append(optionCont);

	         optionCont.on('click',function(e){
	        	 $(this).closest('.quick-quote-dropdown-cont').toggle();
	        	 $(row).html($(this).html());
	        	 $(row).data("value",$(this).data("value"));     
	        	 $(row).attr("value",$(this).data("value"));   
	         });
	         
	   }

	   return div.append(row).append(dropDownContainer);
}

/*Function which paint the question with yes/no button type*/
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

/*Function which paint the question with year/month dropdown*/
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
		"id" : option.monthYearId,
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