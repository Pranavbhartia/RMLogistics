//place for global variables
var neededItemListObject;
var currentUserAndLoanOnj = new Object();
var doPagination = false;
var loanTypeText = "refinance";

function changeLeftPanel(primary) {
	scrollToTop();
    var leftPanel = parseInt(primary);
    $('.lp-item').removeClass('lp-item-active');
    $('.lp-right-arrow').remove();
    if (leftPanel == 1) {
        doPagination = true;
        showMessageDashboard();
    } else if (leftPanel == 2) {
        doPagination = false;
        findUser();
    }
    var contxt = getNotificationContext(newfiObject.user.defaultLoanId, newfiObject.user.id);
    contxt.initContext(true);
    addContext("notification", contxt);
}

function findUser() {
        ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {}, appendCustPersonalInfoWrapper);
    }
    /*var logedInUser;

    function resetSelectedUserDetailObject(user) {

    	

    	user = new Object();

    	user.id = user.id;

    	logedInUser.phoneNo = user.phone_no;

    }*/
function appendCustPersonalInfoWrapper(user) {
    //alert(logedInUser.userID);
    //resetlogedInUserDetailObject(user);
    showCustomerLoanPage(user);
}

function getCustomerSecondaryLeftNav() {
    var leftTab2Wrapper = $('<div>').attr({
        "class": "lp-t2-wrapper",
        "id": "cust-sec-nav"
    });
    var step1 = getCustomerSecondaryLeftNavStep(1, "Getting to know newfi");
    var step2 = getCustomerSecondaryLeftNavStep(2, "complete your application");
    var step3 = getCustomerSecondaryLeftNavStep(3, "lock<br />your rate");
    var step4 = getCustomerSecondaryLeftNavStep(4, "upload<br />needed items");
    var step5 = getCustomerSecondaryLeftNavStep(5, "loan<br />progress");
    return leftTab2Wrapper.append(step1).append(step2).append(step3).append(step4).append(step5);
}

function getCustomerSecondaryLeftNavStep(step, text) {
    var container = $('<div>').attr({
        "id": "lp-step" + step,
        "class": "lp-t2-item",
        "onclick": "changeSecondaryLeftPanel(" + step + ");"
    });
    var img = $('<div>').attr({
        "class": "lp-t2-img lp-t2-img" + step
    });
    var txt = $('<div>').attr({
        "class": "lp-t2-txt"
    }).html(text);
    return container.append(img).append(txt);
}

function showMessageDashboard() {
    $('.lp-right-arrow').remove();
    $('#right-panel').html('');
    $('.lp-item').removeClass('lp-item-active');
    $('#lp-talk-wrapper').addClass('lp-item-active');
    var convMainContainer = $('<div>').attr({
        "id": "conv-main-container",
        "class": "right-panel-messageDashboard float-left"
    });
    $('#right-panel').append(convMainContainer);
    getLoanTemUsingloanId();
    adjustCenterPanelWidth();
}

function showCustomerLoanPage(user) {
	scrollToTop();
    $('.lp-right-arrow').remove();
    $('#right-panel').html('');
    $('.lp-item').removeClass('lp-item-active');
    $('#lp-loan-wrapper').addClass('lp-item-active');
    var loanDetailsMainContainer = $('<div>').attr({
        "class": "right-panel float-left"
    });
    var secondaryLeftNav = getCustomerSecondaryLeftNav();
    var centerPanel = $('<div>').attr({
        "id": "center-panel-cont",
        "class": "center-panel float-left"
    });
    loanDetailsMainContainer.append(secondaryLeftNav).append(centerPanel);
    $('#right-panel').append(loanDetailsMainContainer);
    changeSecondaryLeftPanel(1);
    //adjustCenterPanelWidth();
    //TODO: Invoke dynamic binder to listen to secondary navigation clicks
    bindDataToSN();
    globalSNBinder();
}

function changeSecondaryLeftPanel(secondary) {
		scrollToTop();
        secondary = parseInt(secondary);
        $('.lp-t2-item').removeClass('t2-active');
        $('.lp-t2-item .arrow-right').remove();
        $('#lp-step' + secondary).addClass('t2-active');
        var rightArrow = $('<div>').attr({
            "class": "arrow-right"
        });
        $('#lp-step' + secondary).append(rightArrow);
        $('#center-panel-cont').html('');
        if (secondary == 1) {
            // getting to know newfi page
        } else if (secondary == 2) {
            var userId=newfiObject.user.id;
            getAppDetailsForUser(userId,function(){
                paintCustomerApplicationPage();
            });
            //paintSelecedOption();
        } else if (secondary == 3) {
            // fix your rate page
            //paintFixYourRatePage();
        	showToastMessage("Please Complete Your Application first");
        } else if (secondary == 4) {
            // upload need items
            uploadNeededItemsPage();
        } else if (secondary == 5) {
            // loan progress
            paintCustomerLoanProgressPage();
        }
    }


    /*

     * Functions for complete profile module

     */
function paintProfileCompleteStep1(user) {
    var topHeader = getCompletYourApplicationHeader();
    var formContainer = getAboutMeDetailsWrapper(user);
    $('#center-panel-cont').append(topHeader).append(formContainer);
}

function paintProfileCompleteStep2(user) {
    // On click of this next button the values are getting stored in the data base 
    var fname = $('#userFnameId').val();
    var lname = $('#userlnameId').val();
    var email = $('#emailId').val();
    var secEmailId = $('#secEmailId').val();
    var priPhone = $('#priPhoneNumberId').val();
    var secPhone = $('#secPhoneNumberId').val();
    var dob = $('#dobId').val();
    var city = $('#userCityId').val();
    var state = $('#userStateId').val();
    var zipcode = $('#userZipcodeId').val();
    // create a json and call a ajax passing this json string 
    var completeUserInfo = new Object();
    completeUserInfo.id = parseInt(user.id);
    completeUserInfo.firstName = fname;
    completeUserInfo.lastName = lname;
    completeUserInfo.emailId = email;
    completeUserInfo.phoneNumber = priPhone;
    var completeCustomerDetails = new Object();
    completeCustomerDetails.id = parseInt(user.customerDetail.id);
    completeCustomerDetails.addressCity = city;
    completeCustomerDetails.addressState = state;
    completeCustomerDetails.addressZipCode = zipcode;
    completeCustomerDetails.dateOfBirth = new Date(dob).getTime();
    completeCustomerDetails.secEmailId = secEmailId;
    completeCustomerDetails.secPhoneNumber = secPhone;
    completeUserInfo.customerDetail = completeCustomerDetails;
    /*

	ajaxRequest("rest/userprofile/completeprofile?userID=" + userID, "POST",

				"json", {}, onReturnOfAddUserToLoanTeam);

	*/
    //alert(JSON.stringify(completeUserInfo));
    // make a ajex call : start 
    $.ajax({
        url: "rest/userprofile/completeprofile",
        type: "POST",
        data: {
            "completeUserInfo": JSON.stringify(completeUserInfo)
        },
        dataType: "json",
        success: function(data) {},
        error: function(error) {
            alert("error" + error);
        }
    });
    /*

	$.ajax({

		url : "updateUserInfo.do",

		type:"POST",

		data:JSON.stringify(updateUserInfo),

		dataType :"json",

		contentType:"application/json; charset=utf-8",

		success : function(data){

			

		},

		error:function(error){

			

		}

	});*/
    // Ends
    $('#center-panel-cont').html("");
    var topHeader = getCompletYourApplicationHeader();
    var formContainer = getLoanDetailsWrapper();
    $('#center-panel-cont').append(topHeader).append(formContainer);
}

function getCompletYourApplicationHeader() {
    var parent = $('<div>').attr({
        "class": "complete-application-wrapper"
    });
    var header = $('<div>').attr({
        "class": "complete-application-header"
    }).html("Complete Your Application");
    return parent.append(header);
}

function getAboutMeDetailsWrapper(user) {
    var parent = $('<div>').attr({
        "class": "about-me-details-wrapper"
    });
    var header = getAboutMeDetailsHeader(user);
    var container = getAboutMeDetailsContainer(user);
    var editProfileInfo = editProfileInformation(user);
    var nextButton = $('<div>').attr({
        "class": "submit-btn"
    }).bind('click', {
        "user": user
    }, function(event) {
        event.stopImmediatePropagation();
        paintProfileCompleteStep2(event.data.user);
    }).html("Next");
    container.append(nextButton);
    return parent.append(header).append(container).append(editProfileInfo);
}

function getAboutMeDetailsHeader(user) {
    var header = $('<div>').attr({
        "class": "application-form-header clearfix"
    });
    var headerCol1 = $('<div>').attr({
        "class": "application-form-header-col1 float-left"
    }).html("About Me");
    var headerCol2 = $('<div>').attr({
        "class": "application-form-header-col2 float-right"
    });
    var col2Container = $('<div>').attr({
        "class": "clearfix"
    });
    var col2LeftCol = $('<div>').attr({
        "class": "form-progress float-left"
    });
    var progressSpan = $('<span>').attr({
        "class": "progress-span-text"
    }).html("4/10");
    col2LeftCol.html(progressSpan).append(" Completed");
    var col2RightCol = $('<div>').attr({
        "class": "edit-form float-right",
        "onclick": "showHideEditProfile()"
    }).html("Edit");
    col2Container.append(col2LeftCol).append(col2RightCol);
    headerCol2.append(col2Container);
    return header.append(headerCol1).append(headerCol2);
}

function getAboutMeDetailsContainer(user) {
    var container = $('<div>').attr({
        "class": "application-form-container clearfix"
    });
    var emptyValuesContainer = $("<div>").attr({
        "id": "emptyValuesContainer"
    });
    var nonEmptyValuesContainer = $("<div>").attr({
        "id": "nonEmptyValuesContainer"
    });
    if (user.firstName == "" || user.firstName == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("First Name", user.firstName, "userFnameId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("First Name", user.firstName, "userFnameId"));
    }
    if (user.lastName == "" || user.lastName == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("Last Name", user.lastName, "userlnameId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("Last Name", user.lastName, "userlnameId"));
    }
    // Primery Email id
    if (user.emailId == "" || user.emailId == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("Email Id", user.emailId, "emailId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("Email Id", user.emailId, "emailId"));
    }
    // Secondry email id
    if (user.customerDetail.secEmailId == "" || user.customerDetail.secEmailId == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("Second Email Id", user.customerDetail.secEmailId, "secEmailId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("Second Email Id", user.customerDetail.secEmailId, "secEmailId"));
    }
    // Primery phone number
    if (user.phoneNumber == "" || user.phoneNumber == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("Phone 1", user.phoneNumber, "priPhoneNumberId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("Phone 1", user.phoneNumber, "priPhoneNumberId"));
    }
    // secondry phone number
    if (user.customerDetail.secPhoneNumber == "" || user.customerDetail.secPhoneNumber == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("Phone 2", user.customerDetail.secPhoneNumber, "secPhoneNumberId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("Phone 2", user.customerDetail.secPhoneNumber, "secPhoneNumberId"));
    }
    //userCity
    if (user.customerDetail.addressCity == "" || user.customerDetail.addressCity == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("City", user.customerDetail.addressCity, "userCityId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("City", user.customerDetail.addressCity, "userCityId"));
    }
    //userState
    if (user.customerDetail.addressState == "" || user.customerDetail.addressState == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("State", user.customerDetail.addressState, "userStateId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("State", user.customerDetail.addressState, "userStateId"));
    }
    //userZipcode 
    if (user.customerDetail.addressZipCode == "" || user.customerDetail.addressZipCode == null) {
        nonEmptyValuesContainer.append(getEditableFormRow("Zipcode", user.customerDetail.addressZipCode, "userZipcodeId"));
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("Zipcode", user.customerDetail.addressZipCode, "userZipcodeId"));
    }
    //dobId
    if (user.customerDetail.dateOfBirth == "" || user.customerDetail.dateOfBirth == null || user.customerDetail.dateOfBirth == '0l') {
        var comRow = getEditableFormRow("DOB", user.customerDetail.dateOfBirth, "dobId");
        comRow.find('.form-detail-edit').find('input').addClass('date-picker').attr("placeholder", "MM/DD/YYYY").datepicker({
            orientation: "top auto",
            autoclose: true
        });
        nonEmptyValuesContainer.append(comRow);
    } else {
        emptyValuesContainer.append(getNonEditableFormRow("DOB", $.datepicker.formatDate("mm/dd/yy", new Date(user.customerDetail.dateOfBirth)), "dobId"));
    }
    return container.append(nonEmptyValuesContainer).append(emptyValuesContainer);
}

function getLoanDetailsWrapper() {
    var parent = $('<div>').attr({
        "class": "about-me-details-wrapper"
    });
    var header = getLoanDetailsHeader();
    var container = getLoanDetailsContainer();
    var saveApplicationButton = $('<div>').attr({
        "class": "submit-btn save-app-btn",
        "onclick": "paintFixYourRatePage()"
    }).html("Save Application");
    return parent.append(header).append(container).append(saveApplicationButton);
}



function lockLoanRate(lockratedata){

alert('final lockratedata'+JSON.stringify(lockratedata));

 $.ajax({
        url: "rest/application/lockLoanRate",
        type: "POST",
        data: {
            "appFormData": JSON.stringify(lockratedata)
        },
        datatype: "application/json",
        success: function(data) {
            $('#overlay-loader').hide();
            //TO:DO pass the data (json)which is coming from the controller
            //paintLockRate(data,appUserDetails);
            //paintLockRate(JSON.parse(data), appUserDetails);
            alert('loan is locked');
        },
        error: function() {
            alert("error");
            $('#overlay-loader').hide(); 
        }
 
    });

}

function getLoanDetailsHeader() {
    var header = $('<div>').attr({
        "class": "application-form-header clearfix"
    });
    var headerCol1 = $('<div>').attr({
        "class": "application-form-header-col1 float-left"
    }).html("My Loan Goals");
    var headerCol2 = $('<div>').attr({
        "class": "application-form-header-col2 float-right"
    });
    var col2Container = $('<div>').attr({
        "class": "clearfix"
    });
    var col2LeftCol = $('<div>').attr({
        "class": "form-progress float-left"
    });
    var progressSpan = $('<span>').attr({
        "class": "progress-span-text"
    }).html("8/17");
    col2LeftCol.html(progressSpan).append(" Completed");
    var col2RightCol = $('<div>').attr({
        "class": "edit-form float-right"
    }).html("Edit");
    col2Container.append(col2LeftCol).append(col2RightCol);
    headerCol2.append(col2Container);
    return header.append(headerCol1).append(headerCol2);
}

function getLoanDetailsContainer() {
    var container = $('<div>').attr({
        "class": "application-form-container clearfix"
    });
    var row1 = getEditableFormRow("Prop Address", true);
    row1.find('.form-detail-edit').find('input').addClass('form-detail-input-lg');
    var row2 = getEditableFormRow("Prop City", true);
    row2.find('.form-detail-edit').find('input').addClass('form-detail-input-dropdown');
    var row3 = getEditableFormRow("Prop State", true);
    row3.find('.form-detail-edit').find('input').addClass('form-detail-input-dropdown form-detail-input-sm');
    var row4 = getEditableFormRow("Prop Zip", true);
    row4.find('.form-detail-edit').find('input').addClass('form-detail-input-sm');
    var row5 = getEditableFormRow("Bor Middle Name", true);
    var row6 = getEditableFormRow("Bor Middle Name", true);
    var row7 = getEditableFormRow("SSN", false);
    container.append(row1).append(row2).append(row3).append(row4).append(row5).append(row6).append(row7);
    var estCreditNo = $('<div>').attr({
        "class": "est-credit-row"
    }).html("Estimate the Credit No.");
    container.append(estCreditNo);
    var row8 = getNonEditableFormRow("Prop Country", "Greenwood");
    var row9 = getNonEditableFormRow("Prop Use", "Primary Residence");
    var row10 = getNonEditableFormRow("Down Payment", "$ 3.50");
    var row11 = getNonEditableFormRow("Max Monthly Payment", "$ 400.00");
    var row12 = getNonEditableFormRow("Lien Amount", "$ 0.00");
    var row13 = getNonEditableFormRow("Cashout", "$ 0.00");
    var row14 = getNonEditableFormRow("Value/Price", "$ 150,000.00");
    var row15 = getNonEditableFormRow("Loan Amount", "$ 144,750.00");
    var row16 = getNonEditableFormRow("Self-Empl", "NO");
    return container.append(row8).append(row9).append(row10).append(row11).append(row12).append(row13).append(row14).append(row15).append(row16);
}

function getEditableFormRow(desc, isCompulsory, id) {
    var row = $('<div>').attr({
        "class": "form-detail-edit-row clearfix"
    });
    var rowCol1 = $('<div>').attr({
        "class": "form-detail-row-desc float-left"
    }).html(desc);
    if (isCompulsory) {
        rowCol1.addClass("form-field-compulsory");
        rowCol1.append('<span class="compulsory-span"> *</span>');
    }
    var rowCol2 = $('<div>').attr({
        "class": "form-detail-edit float-left"
    });
    var editRow = $('<input>').attr({
        "class": "form-detail-input",
        "id": id
    });
    rowCol2.append(editRow);
    return row.append(rowCol1).append(rowCol2);
}

function getNonEditableFormRow(desc, value, id) {
        var row = $('<div>').attr({
            "class": "form-detail-row clearfix"
        });
        var rowCol1 = $('<div>').attr({
            "class": "form-detail-row-name float-left"
        }).html(desc);
        var rowCol2 = $('<div>').attr({
            "class": "form-detail-row-value float-left"
        });
        var editRow = $('<input>').attr({
            "class": "form-detail-input-disabled",
            "id": id
        }).prop("disabled", "true").val(value);
        rowCol2.append(editRow);
        return row.append(rowCol1).append(rowCol2);
    }
    /*

     * Functions for fix your rate module

     */
function paintFixYourRatePage() {
    /*var rateProgramWrapper = getLockRateProgramContainer();

	$('#center-panel-cont').append(rateProgramWrapper);*/
    var loanSummaryWrapper = getLoanSummaryWrapper(lqbData, appUserDetails);
    var closingCostWrapper = getClosingCostSummaryContainer();
    $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);
}


function fixAndLoakYourRatePage(lqbData, appUserDetails) {



var lqbFileId ={};
        $('#center-panel-cont').html("");
        var loanNumber = lqbData[0].loanNumber;
       loan.lqbFileId=loanNumber;  
     //  alert('loan Number'+loanNumber);
        appUserDetails.loan.lqbFileId = loanNumber;
    lockratedata.sLoanNumber=loanNumber;
    //alert('final appUserDetails'+JSON.stringify(appUserDetails));
        saveAndUpdateLoanAppForm(appUserDetails);
        
        var lqbData =  modifiedLQBJsonResponse(lqbData);
        //alert('script lqbdata'+JSON.stringify(lqbData));
        var loanSummaryWrapper = getLoanSummaryWrapper(lqbData, appUserDetails);
        var closingCostWrapper = getClosingCostSummaryContainer(lqbData);
        $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);
    }
    /*

    function getRateProgramContainer(refinanceTeaserRate) {

    	var parentWrapper = $('<div>').attr({

    		"class" : "rate-program-wrapper"

    	});

    	var rpHeader = $('<div>').attr({

    		"class" : "rate-program-header uppercase"

    	}).html("RATES & PROGRAM");

    	var rpContainer = $('<div>').attr({

    		"class" : "rate-program-container clearfix"

    	});

    	var rpCol1 = $('<div>').attr({

    		"class" : "rate-program-container-col1 float-left"

    	});

    	

    	var rateBtnContainer = $('<div>').attr({

    		"class" : "cp-btn-cont"

    	}); 

    	

    	var paymentlabel = $('<div>').attr({

    		"class" : "cp-est-header-text"

    	}).html("Closing Cost");

    	var paymentValue = $('<div>').attr({

    		"class" : "cp-est-cost-btn"

    	}).html("$ 8,185.75");

    	rateBtnContainer.append(paymentlabel).append(paymentValue);

    	rpCol1.append(rateBtnContainer);

    	

    	if(loanType == "refinance"){

    		rateBtnContainer.addClass('margin-top30');

    	}

    	

    	if(loanType == "purchase"){

    		var downPaymentBtnContainer = $('<div>').attr({

    			"class" : "cp-btn-cont"

    		});

    		var downPaymentlabel = $('<div>').attr({

    			"class" : "cp-est-header-text"

    		}).html("Down Payment");

    		var downPaymentValue = $('<div>').attr({

    			"class" : "cp-cost-cont"

    		}).html("$ 100,000.00");

    		downPaymentBtnContainer.append(downPaymentlabel).append(downPaymentValue);

    		

    		rpCol1.append(downPaymentBtnContainer);

    	}

    	

    	var rpCol2 = $('<div>').attr({

    		"class" : "rate-program-container-col2 float-left"

    	});

    	var rateSlider = getRateSlider();

    	var tenureSlider = getTenureSlider();

    	rpCol2.append(rateSlider).append(tenureSlider);



    	var rpCol3 = $('<div>').attr({

    		"class" : "rate-program-container-col3 float-left"

    	});

    	var paymentBtnContainer = $('<div>').attr({

    		"class" : "cp-btn-cont"

    	}); 

    	var rateLabel = $('<div>').attr({

    		"class" : "cp-rate-header-text"

    	}).html("Interest Rate");

    	var rateValue = $('<div>').attr({

    		"class" : "cp-rate-btn"

    	}).html("3.375%");

    	paymentBtnContainer.append(rateLabel).append(rateValue);

    	rpCol3.append(paymentBtnContainer);



    	if(loanType == "refinance"){

    		paymentBtnContainer.addClass('margin-top30');

    	}

    	

    	if(loanType == "purchase"){

    		var purchaseAmount = $('<div>').attr({

    			"class" : "cp-btn-cont"

    		});

    		var purchaselabel = $('<div>').attr({

    			"class" : "cp-est-header-text"

    		}).html("Purchase Amount");

    		var purchaseValue = $('<div>').attr({

    			"class" : "cp-cost-cont"

    		}).html("$ 473,000.00");

    		purchaseAmount.append(purchaselabel).append(purchaseValue);

    		

    		rpCol3.append(purchaseAmount);

    	}

    	

    	//var mobileScreenCont = getSliderContainerForMobileScreen();

    	rpContainer.append(rpCol1).append(rpCol2).append(rpCol3);

    	//rpContainer.append(rpCol1).append(rpCol2).append(rpCol3).append(mobileScreenCont);

    	

    	parentWrapper.append(rpHeader).append(rpContainer);

    	var applyNow = $('<div>').attr({

    		"class" : "cp-lock-btn"

    	}).html("Create Your Account").bind('click',function(event){

    		$('#ce-main-container').html('');

    		var applyNow = paintApplyNow(refinanceTeaserRate);

    		$('#ce-main-container').html(applyNow);

    	});

    	

    	var NotifyMe = $('<div>').attr({

    		"class" : "cp-lock-btn"

    	}).html("Email My Numbers").bind('click',function(event){

    		

    		$('#ce-main-container').html('');

    		var notifyForRatesAlerts = paintNotifyForRatesAlerts();

    		$('#ce-main-container').html(notifyForRatesAlerts);

    		

    	});

    	parentWrapper.append(applyNow).append(NotifyMe);

    	return parentWrapper;

    }*/
function getRateSlider() {
    var rateSlider = $('<div>').attr({
        "class": "rate-slider"
    });
    var rateSliderTextCon = $('<div>').attr({
        "class": "slider-text-cont clearfix"
    });
    var rsLeftText = $('<div>').attr({
        "class": "slider-text-left float-left"
    }).html("Reduce Rate");
    var rsRightText = $('<div>').attr({
        "class": "slider-text-right float-right"
    }).html("Reduce Cost");
    rateSliderTextCon.append(rsLeftText).append(rsRightText);
    var rsIcon = $('<div>').attr({
        "id": "rate-slider",
        "class": "rate-slider-icon"
    }).slider({
        orientation: "horizontal",
        range: "min",
        max: 100,
        value: 40
    });
    rateSlider.append(rateSliderTextCon).append(rsIcon);
    return rateSlider;
}

function getTenureSlider() {
    var tenureSilder = $('<div>').attr({
        "class": "tenure-slider"
    });
    var tenureSliderTextCon = $('<div>').attr({
        "class": "slider-text-cont clearfix"
    });
    var tsLeftText = $('<div>').attr({
        "class": "slider-text-left float-left"
    }).html("Length of Loan");
    var tsRightText = $('<div>').attr({
        "class": "slider-text-right float-right"
    });
    var gridArray = [0, 3, 5, 7, 10, 15, 20, 30];
    var tsYearSpan = $('<span>').attr({
        "id": "years-text"
    }).html('30');
    tsRightText.append(tsYearSpan).append(" Years");
    tenureSliderTextCon.append(tsLeftText).append(tsRightText);
    var tsIcon = $('<div>').attr({
        "id": "tenure-slider",
        "class": "tenure-slider-icon"
    }).slider({
        orientation: "horizontal",
        range: "min",
        max: gridArray.length - 1,
        value: gridArray[0],
        change: function(event, ui) {
            $('#years-text').html(gridArray[ui.value]);
        }
    });
    tenureSilder.append(tenureSliderTextCon).append(tsIcon);
    var sliderGrids = getTenureSliderGrids(gridArray);
    tenureSilder.append(sliderGrids);
    return tenureSilder;
}

function getTenureSliderGrids(gridArray) {
    var gridContainer = $('<div>').attr({
        "class": "tenure-grid-container"
    });
    for (var i = 0; i < gridArray.length; i++) {
        var leftOffset = i / (gridArray.length - 1) * 100;
        var gridItem = $('<div>').attr({
            "class": "tenure-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(gridArray[i]);
        gridContainer.append(gridItem);
    }
    return gridContainer;
}

function getSliderContainerForMobileScreen() {
    var mobileSliderCont = $('<div>').attr({
        "class": "mobile-slider-container clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "rate-program-container-rs float-left"
    });
    var rateBtnContainer = $('<div>').attr({
        "class": "cp-btn-cont"
    });
    var rateLabel = $('<div>').attr({
        "class": "cp-rate-header-text"
    }).html("Interest Rate");
    var rateValue = $('<div>').attr({
        "class": "cp-rate-btn"
    }).html("3.375%");
    rateBtnContainer.append(rateLabel).append(rateValue);
    col1.append(rateBtnContainer);
    if (loanType == "purchase") {
        var downPaymentBtnContainer = $('<div>').attr({
            "class": "cp-btn-cont"
        });
        var downPaymentlabel = $('<div>').attr({
            "class": "cp-est-header-text"
        }).html("Down Payment");
        var downPaymentValue = $('<div>').attr({
            "class": "cp-cost-cont"
        }).html("$ 100,000.00");
        downPaymentBtnContainer.append(downPaymentlabel).append(downPaymentValue);
        col1.append(downPaymentBtnContainer);
    }
    var col2 = $('<div>').attr({
        "class": "rate-program-container-ts float-left"
    });
    var paymentBtnContainer = $('<div>').attr({
        "class": "cp-btn-cont"
    });
    var paymentlabel = $('<div>').attr({
        "class": "cp-est-header-text"
    }).html("Monthly Payment");
    var paymentValue = $('<div>').attr({
        "class": "cp-est-cost-btn"
    }).html("$ 8,185.75");
    paymentBtnContainer.append(paymentlabel).append(paymentValue);
    col2.append(paymentBtnContainer);
    if (loanType == "purchase") {
        var purchaseAmount = $('<div>').attr({
            "class": "cp-btn-cont"
        });
        var purchaselabel = $('<div>').attr({
            "class": "cp-est-header-text"
        }).html("Purchase Amount");
        var purchaseValue = $('<div>').attr({
            "class": "cp-cost-cont"
        }).html("$ 473,000.00");
        purchaseAmount.append(purchaselabel).append(purchaseValue);
        col2.append(purchaseAmount);
    }
    return mobileSliderCont.append(col1).append(col2);
}

function getLoanSummaryWrapper(lqbData, appUserDetails) {
    
	
	loanTypeText = appUserDetails.loanType.loanTypeCd;
    var parentWrapper = $('<div>').attr({
        "class": "loan-summary-wrapper"
    });
    var header = getLoanSummaryHeader();
    var container;
    if (loanTypeText == "REF") {
        container = getLoanSummaryContainerRefinance(lqbData, appUserDetails);
    } else if (loanTypeText == "PUR") {
        container = getLoanSummaryContainerPurchase(lqbData, appUserDetails);
    }
    var bottomText = getHeaderText("Quoted Rates are not guaranteed. You may use this tool to check current rates or request a  rate lock. APR is an estimate based on an average $200,000 loan amount with 2% in total APR related fees. Actual ARP will be available on your Good Faith Estimate after Loan Amount and Income are Verified.");
    var rateWrapper = getLoanSliderWrapper(lqbData);
    parentWrapper.append(header).append(container).append(rateWrapper).append(bottomText);
    
    return parentWrapper;
    
}

function getLoanSummaryHeader() {
    var headerCont = $('<div>').attr({
        "class": "loan-summary-header clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 float-left uppercase"
    }).html('MY LOAN SUMMARY');
    var col2 = $('<div>').attr({
        "class": "loan-summary-header-col2 float-left"
    }).html("Rates as of 1/16/2015 8:13:52 AM");
    headerCont.append(col1).append(col2);
    return headerCont;
}

function getLoanSummaryContainerPurchase(lqbData, appUserDetails) {
    
	var yearValues = lqbData;
    var rateVO = yearValues[yearValues.length-2].rateVO;
	var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
	
	var livingSituation = capitalizeFirstLetter(appUserDetails.purchaseDetails.livingSituation);
    
	var estimatedPrice = appUserDetails.purchaseDetails.estimatedPrice; 
    var loanAmount = appUserDetails.purchaseDetails.loanAmount ;
	
	
	var container = $('<div>').attr({
        "class": "loan-summary-container clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
    var lcRow1 = getLaonSummaryApplyBtnRow();
    var lcRow2 = getLoanSummaryRow("Loan Type", "Purchase -"+livingSituation);
    var lcRow3 = getLoanSummaryRow("Loan Program", yearValues[yearValues.length-1].value +" Years Fixed","loanprogramId");
    var lcRow4 = getLoanSummaryRow("Down Payment", "$ 100,000.00");
    var lcRow5 = getLoanSummaryRow("Purchase Amount", estimatedPrice);
    var lcRow6 = getLoanSummaryRow("Interest Rate", rateVO[index].teaserRate, "teaserRateId");
    var lcRow7 = getLoanSummaryRow("Loan Amount", loanAmount);
    var lcRow8 = getLoanSummaryRow("APR", rateVO[index].APR, "aprid");
    var lcRow9 = getLoanSummaryRow("Estimated<br/>Closing Cost",  rateVO[index].closingCost, "closingCostId");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5).append(lcRow6).append(lcRow7).append(lcRow8).append(lcRow9);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    var rcRow1 = getLoanSummaryRow("Monthly Payment", appUserDetails.monthlyRent);
    var rcRow2 = getLoanSummaryRow("Principal Interest", rateVO[index].payment,"principalIntId");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Tax", "Edit");
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtn("Insurance", "Edit");
    //var rcRow5 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal & interest payment");
    var rcRow6 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", "$ 1,649.02");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4).append(rcRow6);
    container.append(leftCol).append(rightCol);
    return container;
}

function getLoanSummaryContainerRefinance(lqbData, appUserDetails) {
    
	var yearValues = lqbData;
    var rateVO = yearValues[yearValues.length-2].rateVO;
    var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
 
    
    if (appUserDetails.refinancedetails.refinanceOption == "REFLMP") refinanceOpt = "Lower My Monthly Payment";
    if (appUserDetails.refinancedetails.refinanceOption == "REFMF") refinanceOpt = "Pay Off My Mortgage Faster";
    if (appUserDetails.refinancedetails.refinanceOption == "REFCO") refinanceOpt = "Take Cash Out of My Home";
    
    var principalInterest = rateVO[index].payment; 
    var Insurance ="Edit";
    var tax = "Edit";
    var  monthlyPayment  = parseFloat(appUserDetails.refinancedetails.currentMortgagePayment.split("$")[1].replace(/,/g, "")/12); 
   
    var totalEstMonthlyPayment =  principalInterest;
    if(principalInterest != "" && principalInterest != undefined )
    	principalInterest =  principalInterest.replace(/,/g, "") ;
    
    if(appUserDetails.refinancedetails.includeTaxes ==true){
    	
    	var InsuranceTemp =  parseFloat(appUserDetails.propertyTypeMaster.propertyInsuranceCost.split("$")[1].replace(/,/g, ""));
    	var taxesTemp =  parseFloat(appUserDetails.propertyTypeMaster.propertyTaxesPaid.split("$")[1].replace(/,/g, ""));
    	var sum = InsuranceTemp + taxesTemp;
    	var monthlyPaymentTemp = parseFloat(appUserDetails.refinancedetails.currentMortgagePayment.split("$")[1].replace(/,/g, ""));  
    	var monthlyPaymenttemp1 = (monthlyPaymentTemp - sum )/12;
    	monthlyPayment = monthlyPaymenttemp1.toFixed(2);
    	
    	Insurance = appUserDetails.propertyTypeMaster.propertyInsuranceCost;
    	tax = appUserDetails.propertyTypeMaster.propertyTaxesPaid;
    	totalEstMonthlyPaymentTemp  = parseFloat(principalInterest) + InsuranceTemp + taxesTemp;
    	totalEstMonthlyPayment = totalEstMonthlyPaymentTemp.toFixed(2);
    }
    
    var monthlyPaymentDifference = Math.abs(principalInterest - monthlyPayment).toFixed(2);
    

    var container = $('<div>').attr({
        "class": "loan-summary-container clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
    var lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - " + refinanceOpt);
    var lcRow2 = getLoanSummaryRow("Loan Program", yearValues[yearValues.length-1].value +" Years Fixed","loanprogramId");
    var lcRow3 = getLoanSummaryRow("Interest Rate", rateVO[index].teaserRate, "lockInterestRate");
    var lcRow4 = getLoanAmountRow("Loan Amount", appUserDetails.currentMortgagePayment, "lockloanAmountid");
    var lcRow5 = getLoanSummaryRow("APR", rateVO[index].APR, "lockrateaprid");
    var lcRow6 = getLoanSummaryLastRow("Estimated<br/>Closing Cost", rateVO[index].closingCost, "lockClosingCost");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5).append(lcRow6);
    
    lockratedata.IlpTemplateId =rateVO[index].lLpTemplateId;
  	lockratedata.requestedRate = rateVO[index].teaserRate;
   	lockratedata.requestedFee = rateVO[index].point;
    
   	// add rows in right column
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    
    var rcRow1 = getLoanSummaryRow("Principal Interest", principalInterest ,"principalIntId");
    var rcRow2 = getLoanSummaryRowCalculateBtn("Tax", tax,"calTaxID");
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Insurance", Insurance,"CalInsuranceID");
    //var rcRow4 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal 																			& interest payment");
    //var rcRow5 = getLoanSummaryRow("Total Est. Monthly Payment", "$ 3,298.40");
    var rcRow6 = getLoanSummaryRow("Current Monthly Payment", "$ "+monthlyPayment);
    var rcRow7 = getLoanSummaryRow("Monthly Payment Difference", "$ " + monthlyPaymentDifference);
    var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", "$ "+totalEstMonthlyPayment);
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow6).append(rcRow7).append(rcRow8);
    container.append(leftCol).append(rightCol);
    return container;
}

function getLaonSummaryApplyBtnRow() {
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html("Loan details");
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail apply-btn float-left"
    }).html("Apply");
    container.append(col1).append(col2);
    return container;
}

function getLoanAmountRow(desc, detail, id) {
    var container = $('<div>').attr({
        "class": "loan-summary-row"
    });
    var loanAmountCont = $('<div>').attr({
        "class": "clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left",
        "id": id
    }).html(detail);
    var dropdownarrow = $('<div>').attr({
        "class": "dropdown-arrow"
    }).bind('click', function() {
        $('#loan-amount-details').toggle();
    });
    col2.append(dropdownarrow);
    loanAmountCont.append(col1).append(col2);
    var loanAmountDetails = $('<div>').attr({
        "id": "loan-amount-details",
        "class": "loan-amount-details hide"
    });
    var row1 = $('<div>').attr({
        "class": "loan-summary-sub-row clearfix"
    });
    var col1row1 = $('<div>').attr({
        "class": "loan-summary-sub-col-desc float-left"
    }).html("Current Loan Amount");
    var col2row1 = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left"
    }).val("$ 303,000.00");
    row1.append(col1row1).append(col2row1);
    var row2 = $('<div>').attr({
        "class": "loan-summary-sub-row clearfix"
    });
    var col1row2 = $('<div>').attr({
        "class": "loan-summary-sub-col-desc float-left"
    }).html("Cashout");
    var col2row2 = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left"
    }).val("$ 70,000.00");
    row2.append(col1row2).append(col2row2);
    loanAmountDetails.append(row1).append(row2);
    return container.append(loanAmountCont).append(loanAmountDetails);
}

function getLoanSummaryRow(desc, detail, id) {
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left",
        "id": id
    }).html(detail);
    container.append(col1).append(col2);
    return container;
}

function getLoanSummaryRowCalculateBtn(desc, detail,id) {
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left"
    });
    
    var col2Txt = $('<div>').attr({
    	"class" : "calculate-btn"

    }).html(detail)
    .bind('click',{"valueData":$(this).text()},function(event){
    	var prevVal = $(this).text();
    	$(this).next('input').show().focus().val(prevVal);
    	$(this).hide();
    	//$(this).html($(this).text());
    });
    
    var inputBox = $('<input>').attr({
    	"class" : "loan-summary-sub-col-detail hide",
    	"id":id
    }).bind('keyup',function(e){
    	if(e.which == 27){
    		var prevVal = $(this).prev('.calculate-btn').text();
    		if($(this).val() == undefined || $(this).val() == prevVal){
    			$(this).hide();
    			$(this).prev('.calculate-btn').show();
    		}
    	}
    });    
    
    col2.append(col2Txt).append(inputBox);
    container.append(col1).append(col2);
    return container;
}

function getLoanSummaryLastRow(desc, detail, id) {
    var container = $('<div>').attr({
        "class": "loan-summary-last-row clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left",
        "id": id
    }).html(detail);
    container.append(col1).append(col2);
    return container;
}

function getLoanSummaryTextRow(text) {
    var container = $('<div>').attr({
        "class": "loan-summary-row-text"
    }).html(text);
    return container;
}

function getHeaderText(text) {
    var headerText = $('<div>').attr({
        "class": "cp-header-text"
    }).html(text);
    return headerText;
}

function getClosingCostSummaryContainer() {
    var parentWrapper = $('<div>').attr({
        "class": "closing-cost-wrapper"
    });
    var header = getClosingCostHeader("CLOSING COST SUMMARY");
    var descText = getHeaderText("Based on the loan you selected your application, credit report and the estimated closing date of ");
    var closingDate = $('<span>').attr({
        "class": "semibold"
    }).html("02/09/2015");
    descText.append(closingDate).append(" ,your estimated lender and third party costs are:");
    var topContainer = getClosingCostTopConatiner();
    var bottomContainer = getClosingCostBottomConatiner();
    return parentWrapper.append(header).append(descText).append(topContainer).append(bottomContainer);
}

function getClosingCostHeader(text) {
    var header = $('<div>').attr({
        "class": "closing-cost-header uppercase"
    }).html(text);
    return header;
}

function getClosingCostTopConatiner() {
    var wrapper = $('<div>').attr({
        "class": "closing-cost-cont-wrapper-top"
    });
    var heading = getClosingCostHeadingCont("Total Estimated Closing Cost");
    var container1 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon1 = getClosingCostConatinerHeader("Estimated Lender Cost");
    var row1Con1 = getClosingCostContainerRow(1, "Lender Fee", "$ 1,495.00");
    var row2Con1 = getClosingCostContainerRow(2, "Credit/Charge", "$ 5,128.75");
    var row3Con1 = getClosingCostContainerLastRow(3, "Total Estimated Lender Costs", "$ 6,623.75");
    container1.append(headerCon1).append(row1Con1).append(row2Con1).append(row3Con1);
    var container2 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon2 = getClosingCostConatinerHeader("Estimated Third Party Cost");
    var row1Con2 = getClosingCostContainerRow(1, "Appraisal Fee", "$ 455.00");
    var row2Con2 = getClosingCostContainerRow(2, "Credit Report", "$ 455.00");
    var row3Con2 = getClosingCostContainerRow(3, "Flood Certification", "$ 455.00");
    var row4Con2 = getClosingCostContainerRow(4, "Wire Fee", "$ 455.00");
    var row5Con2 = getClosingCostContainerRow(5, "Lenders Title Insurance", "$ 450.00");
    var row6Con2 = getClosingCostContainerRow(6, "Closing/Escrow Fee", "$ 500.00");
    var row7Con2 = getClosingCostContainerRow(7, "Recording Fee", "$ 107.00");
    var row8Con2 = getClosingCostContainerRow(8, "City/County Tax stamps", "$ 107.00");
    var row9Con2 = getClosingCostContainerLastRow(9, "Total Estimated Third Party Costs", "$ 1,562.00");
    container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row3Con2).append(row4Con2).append(row5Con2).append(row6Con2).append(row7Con2).append(row8Con2).append(row9Con2);
    return wrapper.append(heading).append(container1).append(container2);
}

function getClosingCostBottomConatiner() {
    var wrapper = $('<div>').attr({
        "class": "closing-cost-cont-wrapper-bottom no-border-bottom"
    });
    var heading = getClosingCostHeadingCont("Total Estimated Closing Cost");
    var container1 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon1 = getClosingCostConatinerHeader("Prepaids");
    var row1Con1 = getClosingCostContainerRowWithSubText(1, "Interest", "$ 699.40","");
    var row2Con1 = getClosingCostContainerRow(2, "Haz Ins", "$ 455.00");
    var row3Con1 = getClosingCostContainerLastRow(3, "Total Prepaids", "$ 699.40");
    container1.append(headerCon1).append(row1Con1).append(row2Con1).append(row3Con1);
    var container2 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon2 = getClosingCostConatinerHeader("Estimated Reserves Deposited with Lender");
    var row1Con2 = getClosingCostContainerRowWithSubText(1, "Tax Reserve - Estimated 2 Month(s)", "$ 1,072.00", "(Varies based on calendar month of closing)");
    var row2Con2 = getClosingCostContainerRowWithSubText(2, "Haz ins. Reserve - Estimated 2 Month(s)", "$ 1,072.00", "(Provided you have 6 months of remaining coverage)");
    var row3Con2 = getClosingCostContainerRowWithSubText(3, "Mtg ins. Reserve - Estimated 2 Month(s)", "$ 1,072.00", "(Provided you have 6 months of remaining coverage)");
    var row4Con2 = getClosingCostContainerLastRow(4, "Total Estimated Reserves Deposited with Lender", "$ 3,216.00");
    container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row3Con2).append(row4Con2);
    var bottomSubText = $('<div>').attr({
        "class": "closing-cost-bot-row"
    }).html("Note :-Taxes for both 1st and 2nd half installments must be paid or will be collected at closing");
    return wrapper.append(heading).append(container1).append(container2).append(bottomSubText);
}

function getClosingCostConatinerHeader(text) {
    var header = $('<div>').attr({
        "class": "closing-cost-cont-desc-header"
    }).html(text);
    return header;
}

function getClosingCostContainerLastRow(rowNum, desc, detail) {
    var row = $('<div>').attr({
        "class": "closing-cost-cont-desc-row no-border-bottom clearfix"
    });
    if (rowNum % 2 == 0) {
        row.addClass("closing-cost-cont-desc-row-even");
    }
    var rowDesc = $('<div>').attr({
        "class": "closing-cost-desc float-left"
    }).html(desc);
    var rowDetail = $('<div>').attr({
        "class": "closing-cost-detail float-left semi-bold"
    }).html(detail);
    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerRow(rowNum, desc, detail) {
    var row = $('<div>').attr({
        "class": "closing-cost-cont-desc-row clearfix"
    });
    if (rowNum % 2 == 0) {
        row.addClass("closing-cost-cont-desc-row-even");
    }
    var rowDesc = $('<div>').attr({
        "class": "closing-cost-desc float-left"
    }).html(desc);
    var rowDetail = $('<div>').attr({
        "class": "closing-cost-detail float-left"
    }).html(detail);
    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerRowWithSubText(rowNum, desc, detail, subtext) {
    var row = $('<div>').attr({
        "class": "closing-cost-cont-desc-row clearfix"
    });
    if (rowNum % 2 == 0) {
        row.addClass("closing-cost-cont-desc-row-even");
    }
    var rowDesc = $('<div>').attr({
        "class": "closing-cost-desc float-left"
    });
    var descText = $('<div>').attr({
        "class": "semi-bold"
    }).html(desc);
    var subTextDiv = $('<div>').attr({
        "class": "subtext"
    }).html(subtext);
    rowDesc.append(descText).append(subTextDiv);
    var rowDetail = $('<div>').attr({
        "class": "closing-cost-detail float-left"
    }).html(detail);
    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostHeadingCont(text) {
    var heading = $('<div>').attr({
        "class": "closing-cost-cont-heading"
    }).html(text);
    return heading;
}

function getFileDragAndDropContainer() {
    var container = $('<div>').attr({
        "id": "drop-zone",
        "class": "file-drag-drop-container"
    });
    var fileUploadIcn = $('<div>').attr({
        "id": "file-upload-icn",
        "class": "file-upload-icn"
    });
    var textCont1 = $('<div>').attr({
        "class": "file-upload-text-cont1"
    }).html("Select Files to Upload/Drag and Drop Files");
    var textCont1Mobile = $('<div>').attr({
        "class": "file-upload-text-mobile"
    }).html("Select Files to Upload");
    var textCont2 = $('<div>').attr({
        "class": "file-upload-text-cont2"
    }).html("or, E-Mail Attachment To : 654321@loan.newfi.com");
    return container.append(fileUploadIcn).append(textCont1).append(textCont1Mobile).append(textCont2);
}

function getDocumentContainer() {
    var container = $('<div>').attr({
        "class": "document-container clearfix",
        "id": "needListDocumentFileContainer"
    });
    var progressBarContainer = $("<div>").attr({
        "class": "progress progress-striped active",
        "role": "progressbar",
        "aria-valuemin": "0",
        "aria-valuemax": "100",
        "aria-valuenow": "0"
    });
    var progressBar = $("<div>").attr({
        "class": "progress-bar progress-bar-success",
        "style": "width:0%",
        "data-dz-uploadprogress": ""
    });
    progressBarContainer.append(progressBar);
    var documentContainer = $("<div>").attr({
        "id": "documentContainer clearfix"
    });
    var listUploadedFiles = neededItemListObject.resultObject.listUploadedFilesListVO;
    for (i in listUploadedFiles) {
        if (listUploadedFiles[i].needType == undefined || listUploadedFiles[i].needType == null || listUploadedFiles[i].needType == "") {
            var col1 = getDocumentUploadColumn(listUploadedFiles[i]);
            documentContainer.append(col1);
            $('.submit-btn').removeClass('hide');
        }
        
    }
    
   
    return container.append(documentContainer);
}

function createdNeededList(categoryName, elements) {
    var incomeDocCont = $('<div>').attr({
        "class": "needed-doc-container"
    });
    var incDocHeading = $('<div>').attr({
        "class": "needed-doc-heading"
    }).html(categoryName);
    incomeDocCont.append(incDocHeading);
    $.each(elements, function(i, val) {
        var needDocRow = getNeededDocRow(val.needsListMaster.label, val.needsListMaster.id);
        incDocHeading.append(needDocRow);
    });
    return incomeDocCont;
}

function getNeededDocRow(desc, needId) {
    var row = $('<div>').attr({
        "class": "needed-item-row clearfix"
    });
    var leftImage = $('<div>').attr({
        "class": "list-item-icn float-left"
    });
    var rowDesc = $('<div>').attr({
        "class": "needed-item-desc float-left"
    }).html(desc);
    var docUploadImage = $("<div>").attr({
        "class": "doc-uploaded-icn float-left",
        "id" : "doc-uploaded-icn_"+needId
    }).data("needId", needId).on("click", uploadDocument);
    row.append(leftImage).append(rowDesc);
    var docRemoveImage = $('<div>').attr({
        "class": "hide float-left",
        "id": "needDoc" + needId
    });
    
    row.append(docUploadImage).append(docRemoveImage);
    
   
    
    return row;
}

function getUploadedItemsWrapper() {
    var wrapper = $('<div>').attr({
        "class": "uploaded-items-wrapper"
    });
    var header = $('<div>').attr({
        "class": "uploaded-items-header uppercase"
    }).html("uploaded items");
    var container = $('<div>').attr({
        "class": "uploaded-items-container clearfix"
    });
    var assetContainer = getAssetContainer();
    var unassignDocContainer = getUnassignDocContiner();
    // container.append(assetContainer).append(unassignDocContainer);
    return wrapper.append(header).append(container);
}

function getAssetContainer() {
    var container = $('<div>').attr({
        "class": "asset-doc-cont float-left"
    });
    var heading = $('<div>').attr({
        "class": "uploaded-items-cont-heading"
    }).html("Asset Documents");
    container.append(heading);
    var row1 = getAssetDocRow("2 months statements for most recent retirement fund or stock portfolio");
    var row2 = getAssetDocRow("2 months bank statements (all pages)");
    var row3 = getAssetDocRow("2 months statements for most recent retirement fund or stock portfolio");
    var row4 = getAssetDocRow("2 months bank statements (all pages)");
    return container.append(row1).append(row2).append(row3).append(row4);
}

function getAssetDocRow(desc) {
    var row = $('<div>').attr({
        "class": "asset-doc-row"
    }).html(desc);
    return row;
}

function getUnassignDocContiner() {
    var container = $('<div>').attr({
        "class": "unassign-doc-cont float-left"
    });
    var heading = $('<div>').attr({
        "class": "uploaded-items-cont-heading"
    }).html("Unassigned Documents");
    container.append(heading);
    var row1 = getUnassignedDocRow("2 months bank statements (all pages)");
    var row2 = getUnassignedDocRow("2 months statements for most recent retirement fund or stock portfolio");
    var row3 = getUnassignedDocRow("2 months bank statements (all pages)");
    return container.append(row1).append(row2).append(row3);
}

function getUnassignedDocRow(desc) {
    var row = $('<div>').attr({
        "class": "unassign-doc-row"
    }).html(desc);
    return row;
}

function mapCategories(category) {
    switch (category) {
        case "Credit_Liabilities":
            return "credit/liability";
        case "Property":
            return "property";
        case "Income_Assets":
            return "income/assets";
        case "Other":
            return "other";
    }
}

function showDialogPopup(title, content, okButtonEvent) {
        $("#dialog").empty();
        $("#dialog").html(content);
        $("#dialog").attr("title", title);
        $("#dialog").dialog({
            buttons: [{
                text: "Ok",
                click: function() {
                    okButtonEvent();
                    $(this).dialog("close");
                }
            }]
        });
    }
    /*

     * Function for notification popup

     */
$(document).on('click', '#alert-popup-wrapper', function(e) {
    e.stopImmediatePropagation();
});
$(document).on('click', function(e) {
    if ($('#alert-popup-wrapper').css("display") == "block") {
        hideAlertNotificationPopup();
    }
});
$(document).on('click', '#alert-notification-btn', function(e) {
    e.stopImmediatePropagation();
    if ($(this).has('#alert-popup-wrapper').length == 1) {
        if ($('#alert-popup-wrapper').css("display") == "block") {
            hideAlertNotificationPopup();
        } else {
            showAlertNotificationPopup();
        }
    } else {
        appendAlertNotificationPopup();
    }
});

function showAlertNotificationPopup() {
    $('#alert-popup-wrapper').show();
}

function hideAlertNotificationPopup() {
    $('#alert-popup-wrapper').hide();
}

function appendAlertNotificationPopup() {
    var alertWrapper = $('<div>').attr({
        "id": "alert-popup-wrapper",
        "class": "alert-popup-wrapper"
    });
    var row1 = getAlertNotificationRow("Salaried-W2-forms", "2hr ago", false);
    var row2 = getAlertNotificationRow("Salaried-W2-forms", "2hr ago", true);
    var row3 = getAlertNotificationRow("Salaried-W2-forms", "2hr ago", false);
    var row4 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum", "2hr ago", false);
    var row5 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum", "2hr ago", false);
    var row6 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum", "2hr ago", false);
    alertWrapper.append(row1).append(row2).append(row3).append(row4).append(row5).append(row6);
    $('#alert-notification-btn').append(alertWrapper);
}

function getAlertNotificationRow(alert, time, isSystemAlert) {
    var row = $('<div>').attr({
        "class": "alert-popup-row clearfix"
    });
    var container = $('<div>').attr({
        "class": "alert-popup-container clearfix"
    });
    var alertIcn = $('<div>').attr({
        "class": "alert-popup-icn float-left"
    });
    if (isSystemAlert) {
        alertIcn.addClass('alert-system-icn');
    } else {
        alertIcn.addClass('alert-user-icn');
    }
    var alertTxtCont = $('<div>').attr({
        "class": "alert-popup-cont float-left"
    });
    var alertTxt = $('<div>').attr({
        "class": "alert-popup-txt"
    }).html(alert);
    var alertTime = $('<div>').attr({
        "class": "alert-popup-time"
    }).html(time);
    alertTxtCont.append(alertTxt).append(alertTime);
    container.append(alertIcn).append(alertTxtCont);
    if (!isSystemAlert) {
        var alertRemoveIcn = $('<div>').attr({
            "class": "alert-rm-icn float-right"
        }).on('click', function(e) {
            e.stopImmediatePropagation();
            dismissAlert(this);
        });
        container.append(alertRemoveIcn);
    }
    return row.append(container);
}

function dismissAlert(element) {
        $(element).closest('.alert-popup-row').remove();
    }
    //History support for customer
function entryPointCustomerViewChangeNav(viewName) {
    changeSecondaryLeftPanel(viewName);
}

var lockratedata= {};

function getLoanSliderWrapper(lqbData) {
    var wrapper = $('<div>').attr({
        "class": "lock-rate-slider-wrapper"
    });
    var header = $('<div>').attr({
        "class": "lock-rate-slider-header"
    }).html("select loan program");
    var container = $('<div>').attr({
        "class": "lock-rate-slider-container"
    });
   
    var tenureSlider = getYearSliderCont(lqbData);
    var rateSlider = getRateSliderCont(lqbData);
    
    container.append(tenureSlider).append(rateSlider);
    var rateBtn = $('<div>').attr({
        "class": "rate-btn"
    }).html("Request Rate Lock").on('click', function(event) {
    	
    	
    
   
   
  // lockratedata.sLoanNumber="D2015040035";
 //  lockratedata.IlpTemplateId ="1cca04b2-4f0d-4cc9-a67c-17210f95a5b2";
  // lockratedata.requestedRate = "4.750";
  // lockratedata.requestedFee = "0.075";

    		
    		//alert('lockratedata'+JSON.stringify(lockratedata));
    		lockLoanRate(lockratedata);
    		
        	        	
        
   	
    });
    return wrapper.append(header).append(container).append(rateBtn);
}

function getYearSliderCont(lqbData) {
   
	var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Length of Loan");
    var silderCont = getYearSlider(lqbData);
    return wrapper.append(headerTxt).append(silderCont);
}

function getYearSlider(LQBResponse) {
   
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
            "ratesArray": yearValues[i].rateVO
        }, function(event) {
            if (!$(this).hasClass('yr-slider-icon-selected')) {
                $('.yr-grid-cont .yr-slider-icon').removeClass('yr-slider-icon-selected');
                $(this).addClass('yr-slider-icon-selected');
                $('.yr-grid-cont .yr-grid-item-selected').hide();
                $('.yr-grid-cont .yr-grid-item').show();
                $(this).parent().find('.yr-grid-item').hide();
                $(this).parent().find('.yr-grid-item-selected').show();
                $('#rate-slider-cont').find('.rt-slider').remove();
                var rateSlider = getRatSlider(event.data.ratesArray);
                $('#rate-slider-cont').append(rateSlider);
                index = parseInt(event.data.ratesArray.length / 2);
                $('#lockrateaprid').html(event.data.ratesArray[index].APR);
                $('#lockClosingCost').html(event.data.ratesArray[index].closingCost);
                $('#lockInterestRate').html(event.data.ratesArray[index].teaserRate);
             //  alert('setting lockratedata');
                lockratedata.IlpTemplateId =event.data.ratesArray[index].lLpTemplateId;
  				lockratedata.requestedRate = event.data.ratesArray[index].teaserRate;
   				lockratedata.requestedFee = event.data.ratesArray[index].point;
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
        //Static code to select year by default
        if (i == yearValues.length-1) {
            selectIcon.addClass('yr-slider-icon-selected');
            gridItem.hide();
            gridItemSelected.show();
        }
        container.append(gridItemCont);
    }
    return container;
}

function getRateSliderCont(LQBResponse) {
    var wrapper = $('<div>').attr({
        "id": "rate-slider-cont",
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Interest Rate");
    
    var yearValues =LQBResponse;
    
   
    var rateArray = yearValues[yearValues.length-1].rateVO;
    var silderCont = getRatSlider(rateArray);
    return wrapper.append(headerTxt).append(silderCont);
}

function getRatSlider(gridArray) {
    
	var rateArray = [];
    for (var i = 0; i < gridArray.length; i++) {
        rateArray[i] = gridArray[i].teaserRate;
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
       // alert('changing 2');
            $('#lockrateaprid').html(gridArray[ui.value].APR);
            $('#lockClosingCost').html(gridArray[ui.value].closingCost);
            $('#lockInterestRate').html(gridArray[ui.value].teaserRate);
            
              lockratedata.IlpTemplateId =gridArray[ui.value].lLpTemplateId;
  				lockratedata.requestedRate = gridArray[ui.value].teaserRate;
   				lockratedata.requestedFee = gridArray[ui.value].point;
        }
    });
    container.append(tsIcon);
    var gridItemCont = $('<div>').attr({
        "class": "rt-grid-cont"
    });
    for (var i = 0; i < gridArray.length; i++) {
        var leftOffset = i / (gridArray.length - 1) * 100;
        var gridItem = $('<div>').attr({
            "class": "rt-grid-item"
        }).css({
            "left": leftOffset + "%"
        }).html(gridArray[i].teaserRate + "%");
        gridItemCont.append(gridItem);
    }
    return container.append(gridItemCont);
}









function modifiedLQBJsonResponse(LQBResponse) {
    var yearValues = [];
    for (var i in LQBResponse) {
        loanDurationConform = LQBResponse[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
        }
        if(LQBResponse[i].rateVO != null && LQBResponse[i].rateVO.length != 0){
        temp = {};
        temp.value = year;
        temp.text = year + " - year fixed arm",
            temp.rateVO = LQBResponse[i].rateVO;
            yearValues.push(temp);
            }
       
    }
    yearValues.sort(function(a, b) {
        return parseFloat(a.value) - parseFloat(b.value);
    });
    return yearValues;
}