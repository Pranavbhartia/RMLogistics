
function getLoanSummaryHeader(inputCustomerDetails,hideCreateAccountBtn) {
    var headerCont = $('<div>').attr({
        "class": "loan-summary-header clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 float-left capitalize"
    }).html('My Loan Summary');
    var col2 = $('<div>').attr({
        "class": "loan-summary-header-col2 float-left"
    }).html(getCurrentDate(responseTime));
    var rateBtn2="";
    if(!hideCreateAccountBtn && teaserRateValHolder.teaserRate){
        rateBtn2 = $('<div>').attr({
            "class": "rate-btn-alertRate float-right"
        }).html("Email This Quote").on('click', function() {
            var emailQuote = true;
            var mainContainer = paintApplyNow(inputCustomerDetails,emailQuote);
            $('#ce-main-container').html(mainContainer);
        });
    }
    if(!teaserRateValHolder.teaserRate){
        //Code to show Rate locked Status comes here....
    }
    headerCont.append(col1).append(col2).append(rateBtn2);
    return headerCont;
}
var teaserRateValHolder={};
function getSliders(teaserRate, inputCustomerDetails,hideCreateAccountBtn){
    var container = $('<div>').attr({
        "class": "lock-rate-slider-container"
    });
    for(var i=0;i<teaserRate.length;i++){
    	var rateArray = teaserRate[i].rateVO;
        rateArray.sort(function(a, b){
            var val1=getFloatValue(a.teaserRate);
            var val2=getFloatValue(b.teaserRate);
            return val1-val2;
        });
    	/*rateArray=rateArray.reverse();*/
    }
    var tenureSlider = getYearSliderContCEP1(teaserRate,inputCustomerDetails);
    var rateSlider = getRateSliderContCEP(teaserRate,inputCustomerDetails);
    
    container.append(tenureSlider).append(rateSlider);
    return container
}
function getRatePageButtonContainer(hideCreateAccountBtn,inputCustomerDetails,teaserRate){
    var wrapper = $('<div>').attr({
        "class": "lock-rate-slider-wrapper"
    });
    if(teaserRateValHolder.teaserRate){
        var rateBtn1="";
        //NEXNF-434
        //var rateBtn2="";
        if(!hideCreateAccountBtn){
            var createAccBtnTxt="Get Pre-Qualified Now!";
            if(inputCustomerDetails.loanType=="REF")
                createAccBtnTxt="Start My Loan Now!";
            if (teaserRateValHolder.leadCustomer)
            {
            	createAccBtnTxt="Submit this info";
            }
            rateBtn1= $('<div>').attr({
                "class": "rate-btn"
            }).html(createAccBtnTxt).on('click', function() {
                
                
                //inputCustomerDetails.propertyTaxesPaid = $('#calTaxID2').val();
                //inputCustomerDetails.propertyInsuranceCost = $('#CalInsuranceID2').val();
                //if create option is required with progress crumb
                if(teaserRateValHolder.teaserRate){
                    if(refinanceTeaserRate.loanType)
                        progressBaar(8)
                    else
                        homeProgressBaar(6)
                }
               
                var mainContainer = paintApplyNow(inputCustomerDetails);
                $('#ce-refinance-cp').html(mainContainer);
                
                /*var mainContainer = paintApplyNow(inputCustomerDetails);
                $('#ce-main-container').html(mainContainer);*/
            });
            /*rateBtn2 = $('<div>').attr({
                "class": "rate-btn-alertRate"
            }).html("Email This Quote").on('click', function() {
                var emailQuote = true;
                var mainContainer = paintApplyNow(inputCustomerDetails,emailQuote);
                $('#ce-main-container').html(mainContainer);
            });*/
        }else{
            if(newfiObject.user.userRole.roleCd!="REALTOR"){
                rateBtn1= $('<div>').attr({
                    "class": "rate-btn"
                }).html("Complete Your Loan Profile").on('click', function() {
                    if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
                        window.location.hash="#myLoan/my-application";
                        //changeSecondaryLeftPanel(2);//Commented since change in hash value does the job
                    }else{
                        window.location.hash="#loan/"+selectedUserDetail.loanID+"/application";
                        //changeAgentSecondaryLeftPanel("lp-step1");//Commented since change in hash value does the job
                    }
                });
            }else{
                rateBtn1= $('<div>').attr({
                    "class": "rate-btn"
                }).html("Contact Your Loan Advisor").on('click', function() {
                    showToastMessage("Please contact your customer");
                });
               
            }
        }
        wrapper.append(rateBtn1);
    }else{
        var rateBtn = $('<div>');
        getRequestRateLockStatus(rateBtn);
        
        var sendPreQualification = $('<div>').attr({
            "class": "rate-btn pre-qualification "
        }).html("Send Pre-Qualification Letter").on('click',function(){
            
            sendPreQualificationLetter();
        });
        var rateVO = getLQBObj(teaserRate);
        if(appUserDetails.loanType.loanTypeCd == "PUR" && !rateVO.dummyData){
            return wrapper.append(rateBtn).append(sendPreQualification);
        }else{
            wrapper.append(rateBtn);
        }
    }
    return wrapper;
}
function getLoanSummaryContainerRefinance(teaserRate, customerInputData) {
    
    var path = "CEP";
    var yearValues = teaserRate;
   
    var rateVO = getLQBObj(yearValues);
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
   
    var wrapper = $('<div>').attr({
        "class" : "loan-summary-container"
    });
    
    var container = $('<div>').attr({
        "class": "clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    
   
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
    if(isIncludeTaxes =="Yes"||isIncludeTaxes ==true){
        
        var investment = (Insurance + tax);
        monthlyPayment = monthlyPayment -investment ;
        totalEstMonthlyPayment  = principalInterest + investment;
  
    }
    
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));

    
    var lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - " + refinanceOpt);
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVO.yearData +" Year Fixed","loanprogramId");
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var lcRow3 = getLoanSummaryRow("Interest Rate", val, "teaserRateId");
    var lcRow4;
    if(refinanceOption != "REFCO")
        lcRow4 = getLoanAmountRow("Loan Amount", showValue(loanAmount),"loanAmount");
    else
        lcRow4 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Current Loan Amout",showValue(currentMortgageBalance), "Cashout",showValue(cashTakeOut),true,path);   
    
    var lcRow5 = getLoanSummaryRow("APR", rateVO.APR +" %", "aprid");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow4).append(lcRow3).append(lcRow5);
    
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Proposed Principal & Interest",showValue(rateVO.payment),"principalIntId");
    var rcRow2 = getLoanSummaryRowCalculateBtn("Tax", showValue(tax),"calTaxID","calTaxID2",customerInputData);
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Insurance", showValue(Insurance),"CalInsuranceID","CalInsuranceID2",customerInputData);
   
    var rcRow6 = getLoanSummaryRow("Current Principal & Interest ", showValue(monthlyPayment) ,"monthlyPaymentId");
    
    //var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow6);
    container.append(leftCol).append(rightCol);
    
    return wrapper.append(container);
}
function getRefinanceClosingCostComponent(teaserRate, customerInputData){
     
    var path = "CEP";
    var yearValues = teaserRate;
   
    var rateVO = getLQBObj(yearValues);
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
   
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
    if(isIncludeTaxes =="Yes"||isIncludeTaxes ==true){
        
        var investment = (Insurance + tax);
        monthlyPayment = monthlyPayment -investment ;
        totalEstMonthlyPayment  = principalInterest + investment;
  
    }
    
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));

    
    var bottomRow = $('<div>').attr({
        "class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
        "class" : "loan-summary-lp float-left"
    });
    var bottomLcRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost), "closingCostId");
    
    bottomLeftCol.append(bottomLcRow);
    
    var bottomRightCol = $('<div>').attr({
        "class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Estimated<br/>Monthly Payment ", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);

    var hgLow="";
     if(principalInterest<monthlyPayment){
        hgLow='<font color="green"><b>Lower</b></font>';
    }else{
        hgLow='<font color="red"><b>Higher</b></font>';
    }
    var rcRow7 = getLoanSummaryLastRow('This Monthly<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId");
    bottomRightCol.append(rcRow7);

    
     return bottomRow.append(bottomLeftCol).append(bottomRightCol);
}

function getLoanSummaryContainerPurchase(teaserRate, customerInputData) {
    
    var path = "CEP";
    var livingSituation;
    if(teaserRateValHolder.teaserRate)
        livingSituation = capitalizeFirstLetter(customerInputData.livingSituation);
    else
        livingSituation = capitalizeFirstLetter(appUserDetails.purchaseDetails.livingSituation);
    
    var yearValues = teaserRate;
       
    var rateVO = getLQBObj(yearValues);
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
    
    var housePrice;
    var loanAmount;
    var downPayment;
    var Insurance;
    var tax;
    if(teaserRateValHolder.teaserRate){
        housePrice = parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.housePrice)));   
        downPayment =  parseFloat(removedDoller(removedComma(customerInputData.currentMortgageBalance))) ;    
        loanAmount = (housePrice-downPayment);
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyInsuranceCost)));
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    }else{
        housePrice = getFloatValue(customerInputData.purchaseDetails.housePrice);   
        loanAmount =  getFloatValue(customerInputData.purchaseDetails.loanAmount) ;    
        downPayment = (housePrice-loanAmount);
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyInsuranceCost)));
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyTaxesPaid)));
     
    }
    
    
     
     if(isNaN(getFloatValue(tax)))
            tax="";
     if(isNaN(getFloatValue(Insurance)))
            Insurance="";
    
   /* var principalInterest = parseFloat(removedDoller(removedComma(rateVO[index].payment)));
    var totalEstMonthlyPayment = principalInterest;
    */
    var wrapper = $('<div>').attr({
        "class": "loan-summary-container"
    });
    
    var container = $('<div>').attr({
        "class" : "clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    //NEXNF-483
   // var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase -"+livingSituation);   
    var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase");
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVO.yearData +" Year Fixed","loanprogramId");
    var lcRow3 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Purchase Price",showValue(housePrice),"Down Payment",showValue(downPayment),false);
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var lcRow4 = getLoanSummaryRow("Interest Rate", val, "teaserRateId");

    var lcRow5 = getLoanSummaryRow("APR", rateVO.APR +" %", "aprid");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 ="";
    if(customerInputData.livingSituation != "homeOwner"){
        var rentPerMonth;
        if(teaserRateValHolder.teaserRate)
            rentPerMonth = showValue(customerInputData.purchaseDetails.rentPerMonth);
        else
            rentPerMonth = showValue(customerInputData.monthlyRent);
        
        rcRow1 = getLoanSummaryRow("Current Rental Payment", rentPerMonth,"monthlyPaymentId");
    }
    var rcRow2 = getLoanSummaryRow("Proposed Principal & Interest", showValue(rateVO.payment),"principalIntId");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Tax",showValue(tax),"calTaxID","calTaxID2",customerInputData);
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtn("Insurance",showValue(Insurance),"CalInsuranceID","CalInsuranceID2",customerInputData);
    //var rcRow5 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(rateVO[index].payment) ,"totalEstMonthlyPaymentId");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4);
    container.append(leftCol).append(rightCol);
    return wrapper.append(container);
}
function getPurchaseClosingCostComponent(teaserRate, customerInputData){
    var path = "CEP";
    var livingSituation;
    if(teaserRateValHolder.teaserRate)
        livingSituation = capitalizeFirstLetter(customerInputData.livingSituation);
    else
        livingSituation = capitalizeFirstLetter(appUserDetails.purchaseDetails.livingSituation);
    
    var yearValues = teaserRate;
       
    var rateVO = getLQBObj(yearValues);
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
    
    var housePrice;
    var loanAmount;
    var downPayment;
    var Insurance;
    var tax;
    if(teaserRateValHolder.teaserRate){
        housePrice = parseFloat(removedDoller(removedComma(customerInputData.purchaseDetails.housePrice)));   
        downPayment =  parseFloat(removedDoller(removedComma(customerInputData.currentMortgageBalance))) ;    
        loanAmount = (housePrice-downPayment);
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyInsuranceCost)));
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTaxesPaid)));
    }else{
        housePrice = getFloatValue(customerInputData.purchaseDetails.housePrice);   
        loanAmount =  getFloatValue(customerInputData.purchaseDetails.loanAmount) ;    
        downPayment = (housePrice-loanAmount);
        Insurance =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyInsuranceCost)));
        tax =  parseFloat(removedDoller(removedComma(customerInputData.propertyTypeMaster.propertyTaxesPaid)));
     
    }
    
    
     
     if(isNaN(getFloatValue(tax)))
            tax="";
     if(isNaN(getFloatValue(Insurance)))
            Insurance="";
    var bottomRow = $('<div>').attr({
        "class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
        "class" : "loan-summary-lp float-left"
    });
    var bottomLcRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVO.closingCost), "closingCostId");
    
    bottomLeftCol.append(bottomLcRow);
    
    var bottomRightCol = $('<div>').attr({
        "class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Estimated<br/>Monthly Payment", showValue(rateVO.payment) ,"totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);
    
    return bottomRow.append(bottomLeftCol).append(bottomRightCol);
}


function paintRatePage(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn) {

    var teaserRate =  modifiedLQBJsonResponse(teaserRate);
    var rateVO = getLQBObj(teaserRate);
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var ratePageHeader=getLoanSummaryHeader(inputCustomerDetails,hideCreateAccountBtn);
    var ratePageSlider="";
    var bottomText="";
    if(!rateVO.dummyData){

    	teaserRateValHolder.leadCustomer = undefined;
        ratePageSlider = getSliders(teaserRate, inputCustomerDetails,hideCreateAccountBtn); 
        bottomText = getHeaderText("Rate and APR quoted are based on the information you provided, are not guaranteed, and are subject to change. Actual rate and APR will be available on your Good Faith Estimate after loan amount and income are verified.");
        ratePageSlider = getSliders(teaserRate, inputCustomerDetails,hideCreateAccountBtn); 
    }
    else if (teaserRateValHolder.teaserRate)
    {
    	teaserRateValHolder.leadCustomer=true;
    }
    
    var loanSummaryWrapper = getLoanSummaryWrapper(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
    var closingCostWrapper = getClosingCostSummaryContainer(getLQBObj(teaserRate));
    var buttonWrapper=getRatePageButtonContainer(hideCreateAccountBtn,inputCustomerDetails,teaserRate);
  //  $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);

    parentWrapper.append(ratePageHeader).append(ratePageSlider).append(loanSummaryWrapper).append(buttonWrapper).append(bottomText);
    
    if(!parentContainer)
        parentContainer=$('#ce-refinance-cp');
    $(parentContainer).append(parentWrapper).append(closingCostWrapper);
}

function getLoanSummaryWrapper(teaserRate, inputCustomerDetails,hideCreateAccountBtn) {
    
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
        loanDescription=getLoanSummaryContainerRefinance(teaserRate, customerInputData);
        loanClosingCost=getRefinanceClosingCostComponent(teaserRate, customerInputData);
    } else if (loanTypeText == "PUR") {
        loanDescription=getLoanSummaryContainerPurchase(teaserRate, customerInputData);
        loanClosingCost=getPurchaseClosingCostComponent(teaserRate, customerInputData);
    }
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });

    parentWrapper.append(loanClosingCost).append(loanDescription);

   
    return parentWrapper;
}