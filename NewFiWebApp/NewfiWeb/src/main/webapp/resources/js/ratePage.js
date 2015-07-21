
function getLoanSummaryHeader(inputCustomerDetails,hideCreateAccountBtn) {
/*    var headerCont = $('<div>').attr({
        "class": "loan-summary-header clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 page-header-loan float-left"
    }).html('Programs and Rates');
    var col2 = $('<div>').attr({
        "class": "loan-summary-header-col2 loan-summary-header-col2-adjustment float-right"
    }).html(getCurrentDate(responseTime));
    //Web portal updates 6.25 changes moving col2 to extreme right
    var rateBtn2="";
    if(!hideCreateAccountBtn && teaserRateValHolder.teaserRate){
        rateBtn2 = $('<div>').attr({
            "class": "rate-btn-alertRate rate-btn-alertRate-header float-right"
        }).html("Email This Quote").on('click', function() {
            var emailQuote = true;
            var mainContainer = paintApplyNow(inputCustomerDetails,emailQuote);
            if(teaserRateValHolder.teaserRate){
                if(refinanceTeaserRate.loanType)
                    progressBaar(7)
                else
                    homeProgressBaar(5)
            }
            $('#ce-refinance-cp').html(mainContainer);
        });
    }
    if(!teaserRateValHolder.teaserRate){
        //Code to show Rate locked Status comes here....
    }
    headerCont.append(col1).append(col2).append(rateBtn2);
    return headerCont;*/
    
    
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 page-header-loan float-left",
        "style":"line-height: 45px;"
    }).html('Programs and Rates');
    
    var columnRight=$('<div>').attr({
        "class": "loan-summary-header-column-right float-right clearfix"
    });
    var rateBtn2="";
    var timeStamp="";
    var headerCont ="";
    if(!hideCreateAccountBtn && teaserRateValHolder.teaserRate){
        rateBtn2 = $('<div>').attr({
            "class": "rate-btn-alertRate rate-btn-alertRate-header float-right"
        }).html("email this quote").on('click', function() {
            var emailQuote = true;
            var mainContainer = paintApplyNow(inputCustomerDetails,emailQuote);
            if(teaserRateValHolder.teaserRate){
                if(refinanceTeaserRate.loanType)
                    progressBaar(7);
                else
                    homeProgressBaar(5);
            }
            $('#ce-refinance-cp').html(mainContainer);
        });
        var headerCont = $('<div>').attr({
            "class": "loan-summary-header clearfix",
            "style":"line-height: normal;"
        });
        
         timeStamp = $('<div>').attr({
            "class": "loan-summary-header-col2 loan-summary-header-col2-teaser-adj float-left"
        }).html(getCurrentDate(responseTime));
         
        
    }else{
    	 var headerCont = $('<div>').attr({
    	        "class": "loan-summary-header clearfix",
    	        
    	    });
		   timeStamp = $('<div>').attr({
		        "class": "loan-summary-header-col2 loan-summary-header-col2-adjustment float-right"
		    }).html(getCurrentDate(responseTime));
    }
    var timeStamp = $('<div>').attr({
        "class": "loan-summary-header-col2 loan-summary-header-col2-teaser-adj float-right"
    }).html(getCurrentDate(responseTime));
    
    columnRight.append(rateBtn2).append(timeStamp);
    headerCont.append(col1)/*.append(columnRight)*/;
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
    if(appUserDetails.loan.lockStatus!="1"){
        var tenureSlider = getYearSliderContCEP1(teaserRate,inputCustomerDetails);
        var rateSlider = getRateSliderContCEP(teaserRate,inputCustomerDetails);
        container.append(tenureSlider).append(rateSlider);
    }
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
                        progressBaar(7)
                    else
                        homeProgressBaar(5)
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
            wrapper.append(rateBtn1);
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
            wrapper.append(rateBtn);
            //NEXNF-721
            //wrapper.append(sendPreQualification);
            return wrapper;
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
    globalChangeContainer.ratVo=rateVO;
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
   
   
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
        var loanBalCol = getInputElmentRow("loanBal","Current Loan <br/>Balance",showValue(currentMortgageBalance),"firstInput",customerInputData);        
        var cashOutCol = getInputElmentRow("cashOut","Cash Out",showValue(cashTakeOut),"secondInput",customerInputData);
        var proposedLoanAmtCol = getLoanSummaryLastRow("Loan<br/> Amount", showValue(loanAmount),"loanAmount",true);
        leftCol.append(loanBalCol);
        leftCol.append(cashOutCol);
        leftCol.append(proposedLoanAmtCol);
    }else{
        var proposedLoanAmtCol = getInputElmentRow("propLoanAmt","Loan<br/> Amount",showValue(loanAmount),"loanAmount",customerInputData);
        leftCol.append(proposedLoanAmtCol);
    }
    //NEXNF-603
    /*if(refinanceOption == "REFMF"){
        var remainingPayment=(getFloatValue(monthlyPayment)*yearLeftOnMortgage*12);
        var currentMortgagePayment = getLoanSummaryLastRow("Remaining<br/> Payments<br/>Current Mortgage", showValue(remainingPayment),"monthlyPaymentId",true);
        leftCol.append(currentMortgagePayment);
        var proposedMonthlyPayment=(getFloatValue(rateVO.yearData)*getFloatValue(rateVO.payment)*12);
        var proposedPrincipalIns = getLoanSummaryLastRow("Total Payments<br/> Proposed Mortgage", showValue(proposedMonthlyPayment),"totPayProposedMortgage",true);
        leftCol.append(proposedPrincipalIns);
        var monthlyDiff = getLoanSummaryLastRow('Estimated Mortgage<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId");
        leftCol.append(monthlyDiff);
    }else{*/
        var currentMortgagePayment = getLoanSummaryLastRow("Current<br/> Mortgage Payment", showValue(monthlyPayment),"monthlyPaymentId",true,true);
        rightCol.append(currentMortgagePayment);
        var monthlyDiff = getLoanSummaryLastRow('Estimated Mortgage<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId",undefined,true);
        rightCol.append(monthlyDiff);
    /*}*/

    var toggletaxComponent=getTaxInsDropToggleBtn(showValue(investment),true);
    rightCol.append(toggletaxComponent);

    //var taxRow = getInputElmentRow("tax","Tax",showValue(tax),"calTaxID2",customerInputData,"taxContainerId");
    var taxRow = getLoanSummaryRowRatePage("Tax" ,showValue(tax),"calTaxID2","taxContainerId",true,true,true);
    rightCol.append(taxRow);

    //var taxRow = getInputElmentRow("Insurance","Insurance",showValue(Insurance),"CalInsuranceID2",customerInputData,"insContainerId");
    var taxRow = getLoanSummaryRowRatePage("Insurance" ,showValue(Insurance),"CalInsuranceID2","insContainerId",true,true,true);
    rightCol.append(taxRow);    

    var totHousingPayment = getLoanSummaryLastRow("Estimated<br/> Housing Payment", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId",true,true);
    rightCol.append(totHousingPayment);

    

    

    


    /*var purchasePrice = getInputElmentRow("purchasePrice","Purchase Price",showValue(monthlyPayment),"firstInput",customerInputData);
    leftCol.append(purchasePrice);

    
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
    
    
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Proposed Principal & Interest",showValue(rateVO.payment),"principalIntId");
    var rcRow2 = getLoanSummaryRowCalculateBtn("Tax", showValue(tax),"calTaxID","calTaxID2",customerInputData);
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Insurance", showValue(Insurance),"CalInsuranceID","CalInsuranceID2",customerInputData);
   
    var rcRow6 = getLoanSummaryRow("Current Principal & Interest ", showValue(monthlyPayment) ,"monthlyPaymentId");
    
    //var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow6);
    
    wrapper.append(container);

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

   
    var rcRow7 = getLoanSummaryLastRow('Estimated Mortgage<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId");
    bottomRightCol.append(rcRow7);

    
    bottomRow.append(bottomLeftCol).append(bottomRightCol);*/
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
   /* var interestRateRow=getLoanTableSummary("Interest Rate", val, "teaserRateId");*/
    var interestRateCol = getRateAprRowCol("Rate / APR", val, formatPercentage(rateVO.APR), "teaserRateId", "aprid");
    grp.append(interestRateCol);


   /* var aprRow=getLoanTableSummary("APR",rateVO.APR +" %", "aprid");
    grp.append(aprRow);*/
    leftCol.append(grp);


    /*rightCol.append(interestRateCol);*/
    
    //NEXNF-621
    /*var tableContaner=$('<div>').attr({
        "class": "clearfix"
    });

    var tabLeftColumn=$('<div>').attr({
        "class": "float-left"
    });
    var tabRightColumn=$('<div>').attr({
        "class": "float-left"
    });
    tableContaner.append(tabLeftColumn).append(tabRightColumn);
    parentWrapper.append(tableContaner);
 
    var loanTypeCol=getTableRow("loanType","Loan Type","Refinance","loanType");
    tabLeftColumn.append(loanTypeCol);

    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var interestRateCol=getTableRow("interestRate","Interest Rate",val,"teaserRateId");
    tabRightColumn.append(interestRateCol);

    var loanProgCol=getTableRow("loanProg","Loan Program",rateVO.yearData +" Year Fixed","loanprogramId");
    tabLeftColumn.append(loanProgCol);

    var aprCol=getTableRow("apr","APR",rateVO.APR +" %","aprid");
    tabRightColumn.append(aprCol);*/

    return parentWrapper;
}
function getLoanSummaryRowRatePage(desc, detail, id,containerId,hideFlag,paddingLeftFlag,paddingLeftFlagLabel) {
    var clas="";
    if(hideFlag)
        clas="hide";
    var container = $('<div>').attr({
        "class": "loan-summary-row loan-summary-row-border-adj clearfix "+clas
    });
    
    if(containerId)
        container.attr({
            "id":containerId
        });
    var paddingClass="";
    if(paddingLeftFlag){
        paddingClass="tax-Ins-clas";
    }
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left "+paddingClass
    }).html(desc);
    var paddinglabelClass="";
    if(paddingLeftFlagLabel){
        paddinglabelClass="tax-Ins-clas";
    }
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail rate-page-normal-text float-left "+paddinglabelClass,
        "id": id
    }).html(detail).val(detail);
    container.append(col1).append(col2);
    return container;
}

function getRateAprRowCol(desc, rateDetail, aprDetail, rateid, aprId, containerId) {
    
    var container = $('<div>').attr({
        "class": "loan-summary-row loan-summary-row-border-adj clearfix "
    });
    
    var span=$('<span>').attr({
		"class" : "rate-mandatoryClass"
	}).html("*");
    
    if(containerId)
        container.attr({
            "id":containerId
        });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    
    if(desc=="Rate / APR"){
    	col1.append(span);
    }
    var col2 = $('<div>').attr({
        "class": "loan-summary-rateapr-detail rate-page-normal-text float-left ",
        "id": rateid
    }).html(rateDetail).val(rateDetail);
    var col3 = $('<div>').attr({
        "class": "loan-summary-rateapr-detail rate-page-normal-text float-left ",
        "id": aprId
    }).html(aprDetail).val(aprDetail);
    var sepCol = $('<div>').attr({
        "class": "loan-summary-rateapr-seperator float-left "
    }).html("/");
    container.append(col1).append(col2).append(sepCol).append(col3);
    return container;
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
    globalChangeContainer.ratVo=rateVO;
    //var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
    
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
   /* var principalInterest = parseFloat(removedDoller(removedComma(rateVO[index].payment)));
    var totalEstMonthlyPayment = principalInterest;
    */
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

    var purchasePrice = getInputElmentRow("purchasePrice","Purchase Price",showValue(housePrice),"firstInput",customerInputData);
    leftCol.append(purchasePrice);

    var toggletaxComponent=getTaxInsDropToggleBtn(showValue(investment));
    rightCol.append(toggletaxComponent);

    //var taxRow = getInputElmentRow("tax","Tax",showValue(tax),"calTaxID2",customerInputData,"taxContainerId");
    var taxRow = getLoanSummaryRowRatePage("Tax" ,showValue(tax),"calTaxID2","taxContainerId",true,true);
    rightCol.append(taxRow);

    var dwnPayment = getInputElmentRow("downPayment","Down Payment",showValue(downPayment),"secondInput",customerInputData);
    leftCol.append(dwnPayment);

    //var taxRow = getInputElmentRow("Insurance","Insurance",showValue(Insurance),"CalInsuranceID2",customerInputData,"insContainerId");
    var taxRow = getLoanSummaryRowRatePage("Insurance" ,showValue(Insurance),"CalInsuranceID2","insContainerId",true,true);
    rightCol.append(taxRow);

    var proposedLoanAmt = getLoanSummaryLastRow("Loan<br/>Amount", showValue(loanAmount) ,"loanAmount",true);
    leftCol.append(proposedLoanAmt);

    var estHousngPaymnt = getLoanSummaryLastRow("Estimated<br/>Housing Payment", showValue(totEstHousingPayment) ,"totalEstMonthlyPaymentId",true);
    rightCol.append(estHousngPaymnt);

    //NEXNF-483
   // var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase -"+livingSituation);  

    /*var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase");
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVO.yearData +" Year Fixed","loanprogramId");
    var lcRow3 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Purchase Price",showValue(housePrice),"Down Payment",showValue(downPayment),false);
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var lcRow4 = getLoanSummaryRow("Interest Rate", val, "teaserRateId");

    var lcRow5 = getLoanSummaryRow("APR", rateVO.APR +" %", "aprid");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5);*/
    
    // add rows in right column
    /*var rcRow1 ="";
    if(customerInputData.livingSituation != "homeOwner"){
        var rentPerMonth;
        if(teaserRateValHolder.teaserRate)
            rentPerMonth = showValue(customerInputData.purchaseDetails.rentPerMonth);
        else
            rentPerMonth = showValue(customerInputData.monthlyRent);
        
        rcRow1 = getLoanSummaryRow("Current Rental Payment", rentPerMonth,"monthlyPaymentId");
    }
    
    var rcRow3 = getLoanSummaryRowCalculateBtn("Tax",showValue(tax),"calTaxID","calTaxID2",customerInputData);
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtn("Insurance",showValue(Insurance),"CalInsuranceID","CalInsuranceID2",customerInputData);
    //var rcRow5 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment ", showValue(rateVO[index].payment) ,"totalEstMonthlyPaymentId");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4);*/

    
    wrapper.append(container);

    /*var bottomRow = $('<div>').attr({
        "class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
        "class" : "loan-summary-lp float-left"
    });
    
    
    var bottomRightCol = $('<div>').attr({
        "class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Estimated<br/>Monthly Payment", showValue(rateVO.payment) ,"totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);
    
    bottomRow.append(bottomLeftCol).append(bottomRightCol);

    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });*/
    parentWrapper.append(wrapper)/*.append(bottomRow)*/;

    /*var tableContaner=$('<div>').attr({
        "class": "clearfix"
    });

    var tabLeftColumn=$('<div>').attr({
        "class": "float-left"
    });
    var tabRightColumn=$('<div>').attr({
        "class": "float-left"
    });
    tableContaner.append(tabLeftColumn).append(tabRightColumn);
    parentWrapper.append(tableContaner);

    var loanTypeCol=getTableRow("loanType","Loan Type","Purchase","loanType");
    tabLeftColumn.append(loanTypeCol);

    
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    var interestRateCol=getTableRow("interestRate","Interest Rate",val,"teaserRateId");
    tabRightColumn.append(interestRateCol);

    var loanProgCol=getTableRow("loanProg","Loan Program",rateVO.yearData +" Year Fixed","loanprogramId");
    tabLeftColumn.append(loanProgCol);

    var aprCol=getTableRow("apr","APR",rateVO.APR +" %","aprid");
    tabRightColumn.append(aprCol);*/

    var loanTypeRow = getLoanSummaryRowRatePage("Loan Type" ,"Purchase","loanType");
    leftCol.append(loanTypeRow);
    
    var val="";
    if(rateVO.teaserRate)
        val=parseFloat(rateVO.teaserRate).toFixed(3)+" %";
    
    var interestRateCol = getRateAprRowCol("Rate / APR", val, formatPercentage(rateVO.APR), "teaserRateId", "aprid");;
    rightCol.append(interestRateCol);
    

    var loanProgCol = getLoanSummaryRowRatePage("Loan Program" ,showValidData(rateVO.yearData) +" Year Fixed","loanprogramId");
    rightCol.append(loanProgCol);
    
    /*var interestRateCol = getLoanSummaryRowRatePage("APR" ,rateVO.APR +" %","aprid","","",true);
    rightCol.append(interestRateCol);*/

    return parentWrapper;

}
function getTableRow(key,desc,value,id){
    var row=$('<div>').attr({
        "class": "clearfix"
    });
    var column1=$('<div>').attr({
        "class": "row-one-col-one"
    }).html(desc);
    var column2=$('<div>').attr({
        "class": "row-one-col-two tab-value-column",
        "id":id
    }).html(value);
    return row.append(column1).append(column2);
}


function paintRatePage(teaserRate, inputCustomerDetails,parentContainer,hideCreateAccountBtn,isViaEngagementPath) {

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
    
    
    var loanSummaryWrapper = getLoanSummaryWrapper(teaserRate, inputCustomerDetails,hideCreateAccountBtn);
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
    } else if (loanTypeText == "PUR") {
        loanDescription=getLoanSummaryContainerPurchase(teaserRate, customerInputData);
    }
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });

    parentWrapper.append(loanClosingCost).append(loanDescription);

   
    return parentWrapper;
}
function getTaxInsDropToggleBtn(investment,flag){
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix no-border-bottom "
    }).bind("click",function(e){
        var taxContainer=$("#taxContainerId");
        var insContainer=$("#insContainerId");
        if($(taxContainer).hasClass("hide")){
            taxContainer.removeClass("hide");
            insContainer.removeClass("hide");
        }else{
            taxContainer.addClass("hide");
            insContainer.addClass("hide");
        }
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left "
    }).html("Tax & Insurance");
    var paddingClass="";
    if(flag){
        paddingClass="tax-Ins-clas";
    }
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left "+paddingClass
    })
    var col2Txt = $('<div>').attr({
        "class" : "loan-row-text float-left "//7.2 portal updates
    }).html(investment);
    var dropdownarrow = $('<div>').attr({
        "class": "dropdown-arrow float-left"
    });
    container.append(col1).append(col2.append(col2Txt).append(dropdownarrow));
    return container;
}
var globalChangeContainer={};
function getInputElmentRow(key,desc, val,inputElementId,appUserDetails,containerId) {
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
    
   /* var col2Txt = $('<div>').attr({
        "class" : "calculate-btn",
        "id":id

    }).html(detail)
    .bind('click',function(event){
        var prevVal = $(this).text();
        
        if(prevVal!="Edit"){
            $(this).next('input').show().focus().val(prevVal).numericInput({
              allowFloat :false,
              allowNegative: false
            });
        }else{
            $(this).next('input').show().focus().val("");
        }
        $(this).hide();
        
    });*/
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
            /*if(e.which == 27){
                var prevVal = $(this).prev('.calculate-btn').text();
                if($(this).val() == undefined || $(this).val() == prevVal){
                    $(this).hide();
                    $(this).prev('.calculate-btn').show();
                }
            }*/
            
            
            //
            
            updateElementsOnSlide(globalChangeContainer.ratVo);
            /*var taxesTemp = 0;
            if($('#calTaxID2').val() !="" )
             taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));        
            
            var InsuranceTemp = 0;
            if($('#CalInsuranceID2').val() !="")
             InsuranceTemp = parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
            
            var  monthlyPayment = 0;
            var loanType;
            var includeTaxes;
            if(teaserRateValHolder.teaserRate){
                loanType=appUserDetails.loanType;
                includeTaxes=appUserDetails.isIncludeTaxes
            }else{
                loanType=appUserDetails.loanType.loanTypeCd;
                includeTaxes=appUserDetails.refinancedetails.includeTaxes
            }

            
            if(loanType =="REF"){
                if(teaserRateValHolder.teaserRate){
                    monthlyPayment = parseFloat(removedDoller(removedComma(appUserDetails.currentMortgagePayment)));
                }else{
                    monthlyPayment  = parseFloat(removedDoller(removedComma(appUserDetails.refinancedetails.currentMortgagePayment)));      
                }
              
            }else{
                if(teaserRateValHolder.teaserRate){
                    monthlyPayment  = 0;
                }else{
                    monthlyPayment  = parseFloat(removedDoller(removedComma(appUserDetails.monthlyRent)));
                }
            }
            
            
            var investment = (InsuranceTemp + taxesTemp);
            
            
            if(includeTaxes ==true || includeTaxes == "Yes"){
                
                monthlyPayment = monthlyPayment - investment ;      
            
            }

            
            var principalInt = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
            
            var monthlyPaymentDifferenceTemp = Math.abs(principalInt - monthlyPayment);
            var monthlyPaymentDifference = monthlyPaymentDifferenceTemp.toFixed(3);
            
            var totalEstMonthlyPaymentId =  (principalInt + investment).toFixed(3);
            
            
            $('#monthlyPaymentId').text(showValue(monthlyPayment));
            $('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
            $('#totalEstMonthlyPaymentId').text(showValue(totalEstMonthlyPaymentId));
            
            var hgLow="";
            if(totalEstMonthlyPaymentId<monthlyPayment){
                hgLow='<font color="green"><b>Lower</b></font>';
            }else{
                hgLow='<font color="red"><b>Higher</b></font>';
            }
            var itm=$("#monthlyPaymentDifferenceId").parent()[0]
            $(itm).find(".loan-summary-col-desc").html('Estimated Mortgage<br/> Payment is '+hgLow+' by');
            if(hgLow=='<font color="green"><b>Lower</b></font>'){
                if(!($("#monthlyPaymentDifferenceId").hasClass("loan-summary-green-col-detail"))){
                    $("#monthlyPaymentDifferenceId").removeClass("loan-summary-red-col-detail");
                    $("#monthlyPaymentDifferenceId").addClass("loan-summary-green-col-detail");
                }
            }else{
                if(!($("#monthlyPaymentDifferenceId").hasClass("loan-summary-red-col-detail"))){
                    $("#monthlyPaymentDifferenceId").removeClass("loan-summary-green-col-detail");
                    $("#monthlyPaymentDifferenceId").addClass("loan-summary-red-col-detail");
                }
            }*/
            //
            
        });
    }
    
    
    col2.append(inputBox);
    
    if(key=="Insurance" && !teaserRateValHolder.teaserRate){
        var saveBtn = $('<div>').attr({
            "class" : "cep-button-color sm-save-btn float-right"
        }).html("Save").on('click',function(){
            
            saveTaxAndInsurance();
        });
        container.append(col1).append(col2.append(saveBtn));
    }
    else if(key=="purchasePrice"||key=="cashOut"){
        var saveBtn = $('<div>').attr({
            "class" : "cep-button-color-orange sm-save-btn"//removed float-right for NEXNF-632
        }).html("update").on('click',{"path":undefined},function(e){
            var flag=globalChangeContainer.flag;
            if(flag){
                amt = $('#firstInput').val();
                amt1 = $('#secondInput').val();
              
                if(teaserRateValHolder.teaserRate)
                  modifiyTeaserRate(amt,amt1);
                else
                  modifiyLockRateLoanAmt(amt,amt1);
            }
            
        });
        container.append(col1).append(col2.append(saveBtn));
    }else if(key=="propLoanAmt"){
        var saveBtn = $('<div>').attr({
            "class" : "cep-button-color-orange sm-save-btn"//removed float-right for NEXNF-632
        }).html("update").on('click',{},function(){
            var flag=globalChangeContainer.flag;
            if(flag){
                amt = $('#loanAmount').val();
                if(teaserRateValHolder.teaserRate){
                    modifiyTeaserRate(amt);
                }else{
                    modifiyLockRateLoanAmt(amt);
                }
            }
        });
        container.append(col1).append(col2.append(saveBtn));
    }else
        container.append(col1).append(col2);
    
    return container;
}

function getDwnPayComponent(value,inputElementId){
    var optionsContainer = $('<div>').attr({
        "class": "app-options-cont"
    });
    var optionCont = $('<input>').attr({
        "class": "loan-summary-sub-col-detail dwn-val float-left",
        "value":showValue(value),
        "id":inputElementId
    }).bind("load focus",{"elementID":inputElementId}, function(e){
        var inputElementId=e.data.elementID;
        $('#'+inputElementId).maskMoney({
            thousands:',',
            decimal:'.',
            allowZero:true,
            prefix: '$',
            precision:0,
            allowNegative:false
        });
        /* this is the piece of code to retrict user put special charector*/
        restrictSpecialChar(undefined,$('#'+inputElementId));
    });
    globalChangeContainer.dwnVal=showValue(value);
    var percentageComp = $('<input>').attr({
    	"class": "loan-summary-sub-col-detail dwn-percentage"
    }).attr('maxlength','2');;
    
    optionCont.bind("keyup",{"valComp":optionCont,"percentComp":percentageComp,"val":true},
        function(e){
            var purchaseAmt=getFloatValue(getpurchaseValue());
            var dwnPmntVal=getFloatValue($(e.data.valComp).val());
            var loanAmt=showValue(purchaseAmt-dwnPmntVal);
            if(dwnPmntVal>purchaseAmt){
                $(e.data.valComp).val(globalChangeContainer.dwnVal);
            }else{
                percentageUpdateEventListener(e);
                globalChangeContainer.flag=true;
                globalChangeContainer.dwnVal=showValue(dwnPmntVal);
                $("#loanAmount").html(loanAmt);
            }
        })
    percentageComp.bind("keyup",{"valComp":optionCont,"percentComp":percentageComp,"percentage":true},
        function(e){
            this.value = this.value.replace(/[^0-9]/g,'')
            percentageUpdateEventListener(e);
            globalChangeContainer.flag=true;
            var purchaseAmt=getFloatValue(getpurchaseValue());
            var dwnPmntVal=getFloatValue($(e.data.valComp).val());
            var loanAmt=showValue(purchaseAmt-dwnPmntVal);
            $("#loanAmount").html(loanAmt);
        })

    optionsContainer.append(optionCont).append(percentageComp);
    setTimeout(function(){ $(optionCont).trigger("keyup");globalChangeContainer.flag=false; }, 500);
    
    return optionsContainer;
}
function getTeaserRateData(){
    if(typeof(newfiObject)==="undefined"){
        if(refinanceTeaserRate.loanType)
            return refinanceTeaserRate;
        else
            return buyHomeTeaserRate;
    }else{
        if(teaserRateValHolder.teaserRate){
            if(appUserDetails.loanType.description=="Purchase"){
                return createTeaserRateObjectForPurchase(appUserDetails);
            }else{
                return createTeaserRateObjectForRefinance(appUserDetails);
            }
        }else{
            return appUserDetails;
        }
    }
}
function updateElementsOnSlide(rateVO,year){
    var customerInputData=getTeaserRateData();
    $('#aprid').html(rateVO.APR +" %");
    $('#closingCostId').html(showValue(rateVO.closingCost));
    $('#teaserRateId').html(parseFloat(rateVO.teaserRate).toFixed(3) +" %");
    $('#principalIntId').html(showValue(rateVO.payment));

    if(year){
        if(year =='5' || year =="7")
            $('#loanprogramId').html(year +" Year ARM");
        else
            $('#loanprogramId').html(year +" Year Fixed");
    }   
    var refinanceOption;
    if(teaserRateValHolder.teaserRate){
        refinanceOption=customerInputData.refinanceOption;
        if (customerInputData.refinanceOption == "REFLMP") refinanceOpt = "Lower monthly payment";
        if (customerInputData.refinanceOption == "REFMF") refinanceOpt = "Pay off mortgage faster";
        if (customerInputData.refinanceOption == "REFCO"){
            refinanceOpt = "Take cash out";
        }
    }else{
        refinanceOption=customerInputData.refinancedetails.refinanceOption;
        if (refinanceOption == "REFLMP") refinanceOpt = "Lower monthly payment";
        if (refinanceOption == "REFMF") refinanceOpt = "Pay off mortgage faster";
        if (refinanceOption == "REFCO"){
            refinanceOpt = "Take cash out";
        }
    }
    
    if(refinanceOption == "REFMF"){
        var proposedMonthlyPayment=(getFloatValue(rateVO.yearData)*getFloatValue(rateVO.payment)*12);
        $('#totPayProposedMortgage').html(showValue(proposedMonthlyPayment));


    }
    teaseCalculation(customerInputData);
    updateOnSlide(rateVO);
}

function teaseCalculation(inputCustomerDetails){
    
    var taxesTemp = 0;
     var InsuranceTemp = 0;
    var principalInterest = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
    if($('#calTaxID2').val() != "") 
    taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));
    
    if($('#CalInsuranceID2').val() != "") 
    InsuranceTemp =  parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
    
    var  monthlyPayment;
    var isIncludeTaxes;
    if(teaserRateValHolder.teaserRate){
        if(inputCustomerDetails.loanType=="REF")
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.currentMortgagePayment))); 
        else
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.rentPerMonth))); 
        isIncludeTaxes=inputCustomerDetails.isIncludeTaxes;
    }else{
        if(inputCustomerDetails.loanType.loanTypeCd =="REF")
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.refinancedetails.currentMortgagePayment)));    
        else
            monthlyPayment  = parseFloat(removedDoller(removedComma(inputCustomerDetails.monthlyRent)));
        isIncludeTaxes=inputCustomerDetails.refinancedetails.includeTaxes;
    }
    var investment = (InsuranceTemp + taxesTemp);
    
    
    var totalEstMonthlyPaymentId=principalInterest;
    if(isIncludeTaxes =="Yes"||isIncludeTaxes ==true){
        monthlyPayment = monthlyPayment -investment ;
        
    }
    
    totalEstMonthlyPaymentId = (principalInterest + investment);
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));
    
    
    var textDiv=$('#monthlyPaymentDifferenceId').prev();
    var hgLow="";
    var ele=$('#monthlyPaymentDifferenceId');
    ele.removeClass("loan-summary-green-col-detail");
    ele.removeClass("loan-summary-red-col-detail");
    var clas="";
    if(principalInterest < monthlyPayment){
        hgLow='<font color="green"><b>Lower</b></font>';
        clas="loan-summary-green-col-detail";
    }else{
        //hgLow='<font color="red"><b>Higher</b></font>';
        hgLow='<font ><b>Higher</b></font>';
        clas="loan-summary-red-col-detail";
    }
    textDiv.html('Estimated Mortgage<br/> Payment is '+hgLow+' by');
    ele.addClass(clas);


    
    $('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
    $('#totalEstMonthlyPaymentId').text((showValue(totalEstMonthlyPaymentId)));
    $('#totalEstMonthlyPaymentId').addClass('tax-Ins-clas');
}

function getRateSliderContCEP(LQBResponse,inputCustomerDetails) {
    var wrapper = $('<div>').attr({
        "id": "rate-slider-cont",
        "class": "slider-wrapper clearfix"
    });
    
    var text="Rate";//Changed from Interest Rate to Rate
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html(text);
    
    var yearValues = LQBResponse;
    
    var rateArray = yearValues[yearValues.length-1].rateVO;
//    rateArray=rateArray.reverse();
    index = parseInt(rateArray.length / 2);
    var silderCont = getRatSliderCEP(rateArray,inputCustomerDetails,yearValues[yearValues.length-1].value);
    
    return wrapper.append(headerTxt).append(silderCont);
}

/*function getRatSliderCEP(gridArray,inputCustomerDetails,yearValue) {
    
    
    var rateArray = [];
    for (var i = 0; i < gridArray.length; i++) {
        rateArray[i] = parseFloat(gridArray[i].teaserRate).toFixed(3);
    }
    index = parseInt(rateArray.length / 2);
    var container = $('<div>').attr({
        "class": "silder-cont rt-slider float-left"
    });
    var tsIcon = $('<div>').attr({
        "id": "tenure-slider",
        "class": "tenure-slider-icon"
    }).slider({
        orientation: "horizontal",
        range: "min",
        max: rateArray.length - 1,
        value: index,
        change: function(event, ui) {
         
            /*$('#aprid').html(gridArray[ui.value].APR +" %");
            $('#closingCostId').html(showValue(gridArray[ui.value].closingCost));
            $('#teaserRateId').html(parseFloat(gridArray[ui.value].teaserRate).toFixed(3) +" %");
            $('#principalIntId').html(showValue(gridArray[ui.value].payment));
            
            teaseCalculation(inputCustomerDetails);*/
            /*var ratVo=gridArray[ui.value];
            ratVo.yearData=yearValue*/
            /*updateOnSlide(ratVo);*/
            /*globalChangeContainer.ratVo=ratVo;
            updateElementsOnSlide(ratVo);
        }
    });
    container.append(tsIcon);
    var gridItemCont = $('<div>').attr({
        "class": "rt-grid-cont"
    });
    //gridArray=gridArray.reverse();
    for (var i = 0; i < gridArray.length; i++) {
        var leftOffset = i / (gridArray.length - 1) * 100;
        var gridItem = $('<div>').attr({
            "class": "rt-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(parseFloat(gridArray[i].teaserRate).toFixed(3) + "%");
        gridItemCont.append(gridItem);
    }
    return container.append(gridItemCont);
}*/

function getRatSliderCEP(gridArray,inputCustomerDetails,yearValue) {
    
    
    var rateArray = [];    
    var container = $('<div>').attr({
        "class": "silder-cont yr-slider rate-slider float-left"
    });
    
    for (var i = 0; i < gridArray.length; i++) {
        rateArray[i] = parseFloat(gridArray[i].teaserRate).toFixed(3);
    }
    index = parseInt(rateArray.length / 2);
    for (var i = 0; i < gridArray.length; i++) { 
        var leftOffset = i / (gridArray.length - 1) * 100;
        var gridItemCont = $('<div>').attr({
            "class": "yr-grid-cont"
        });
        var selectIcon = $('<div>').attr({
            "class": "yr-slider-icon rate",
        }).css({
            "left": leftOffset + "%"
        }).bind('click', {  
            "value":  i,
        }, function(event) {
            if (!$(this).hasClass('yr-slider-icon-selected')) {
                $('.yr-grid-cont .yr-slider-icon.rate').removeClass('yr-slider-icon-selected');
                $(this).addClass('yr-slider-icon-selected');
                $('.yr-grid-cont.rate .yr-grid-item').show();
                /*$('#aprid').html(gridArray[ui.value].APR +" %");
                $('#closingCostId').html(showValue(gridArray[ui.value].closingCost));
                $('#teaserRateId').html(parseFloat(gridArray[ui.value].teaserRate).toFixed(3) +" %");
                $('#principalIntId').html(showValue(gridArray[ui.value].payment));
                
                teaseCalculation(inputCustomerDetails);*/
                var ratVo=gridArray[event.data.value];
                ratVo.yearData=yearValue
                /*updateOnSlide(ratVo);*/
                globalChangeContainer.ratVo=ratVo;
                updateElementsOnSlide(ratVo);
                    
                               
            }
        });
        var gridItem = $('<div>').attr({
            "class": "yr-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(parseFloat(gridArray[i].teaserRate).toFixed(3) + "%");
       
        gridItemCont.append(selectIcon).append(gridItem);

        container.append(gridItemCont);
        if (i == index) {
            selectIcon.addClass('yr-slider-icon-selected');
            
        }
    }
    return container;
}

function getYearSliderContCEP1(teaserRate,inputCustomerDetails) {
    var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Loan Term");
    
    var silderCont = getYearSliderCEP(teaserRate,inputCustomerDetails);
    
    return wrapper.append(headerTxt).append(silderCont);
}


function getYearSliderCEP(LQBResponse,inputCustomerDetails) {
    var container = $('<div>').attr({
        "class": "silder-cont yr-slider float-left"
    });
    var yearValues = LQBResponse;

    for (var i = 0; i < yearValues.length; i++) {
        var leftOffset = i / (yearValues.length - 1) * 100;
        var gridItemCont = $('<div>').attr({
            "class": "yr-grid-cont"
        });
        var selectIcon = $('<div>').attr({
            "class": "yr-slider-icon"
        }).css({
            "left": leftOffset + "%"
        }).bind('click', {
            "ratesArray": yearValues[i].rateVO,
            "year":yearValues[i].value
        }, function(event) {
            if (!$(this).hasClass('yr-slider-icon-selected')) {
                $('.yr-grid-cont .yr-slider-icon').removeClass('yr-slider-icon-selected');
                $(this).addClass('yr-slider-icon-selected');
                $('.yr-grid-cont .yr-grid-item-selected').hide();
                $('.yr-grid-cont .yr-grid-item').show();
                $(this).parent().find('.yr-grid-item').hide();
                $(this).parent().find('.yr-grid-item-selected').show();
                $('#rate-slider-cont').find('.yr-slider').remove();
                var rateSlider = getRatSliderCEP(event.data.ratesArray,inputCustomerDetails,event.data.year);
                $('#rate-slider-cont').append(rateSlider);
                index = parseInt(event.data.ratesArray.length / 2);
               
                if(event.data.year =='5' || event.data.year =="7")
                    $('#loanprogramId').html(event.data.year +" Year ARM");
                else
                    $('#loanprogramId').html(event.data.year +" Year Fixed");
                
                var ratVo=event.data.ratesArray[index];
                ratVo.yearData=event.data.year;

                globalChangeContainer.ratVo=ratVo;
                updateElementsOnSlide(ratVo,event.data.year)
                /*updateOnSlide(ratVo);
                
                teaseCalculation(inputCustomerDetails);*/
            }
        });
        var gridItem = $('<div>').attr({
            "class": "yr-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(yearValues[i].value + " Yr");
        var gridItemSelected = $('<div>').attr({
            "class": "yr-grid-item-selected hide"
        }).css({
            "left": leftOffset + "%"
        }).html(yearValues[i].text);
        gridItemCont.append(selectIcon).append(gridItem).append(gridItemSelected);
        // Static code to select year by default
        if (i == yearValues.length-1) {
            selectIcon.addClass('yr-slider-icon-selected');
            gridItem.hide();
            gridItemSelected.show();
        }
        container.append(gridItemCont);
    }
    return container;
}


function getLoanTableSummary(desc, detail, id) {
    var container = $('<div>').attr({
        "class": "loan-rate-tableRow-clas clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left",
        "id": id
    }).html(detail);
    
    if(desc=="Loan Type" || desc=="Loan Program" ){
     col2.removeClass('loan-summary-col-detail');
     col2.addClass('loan-summary-col-detail-new');
    }
    container.append(col1).append(col2);
    return container;
}
