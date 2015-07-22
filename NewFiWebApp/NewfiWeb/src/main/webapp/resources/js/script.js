//place for global variables
var neededItemListObject;
var currentUserAndLoanOnj = new Object();
var doPagination = false;
var loanTypeText = "refinance";
var  removedKnwoNewFi = false; 
var flagKnowNewFi=true;
var flagToShowCompletPro = true;
var rateLockRequestedFlag = false;
var closedialog;
function changeLeftPanel(primary,callback) {
	scrollToTop();
    var leftPanel = parseInt(primary);
    $('.lp-item').removeClass('lp-item-active');
    $('.lp-right-arrow').remove();
    if (leftPanel == 1) {
        doPagination = true;
        showMessageDashboard();
        if(callback)
            callback();
    } else if (leftPanel == 2) {
        doPagination = false;
        findUser(callback);
    }
    var contxt = getNotificationContext(newfiObject.user.defaultLoanId, newfiObject.user.id);
    contxt.initContext(true);
    addContext("notification", contxt);
    updateHandler.initiateRequest();
}

function findUser(callback) {
   // if(window.location.hash=="")
        ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {}, function(response){appendCustPersonalInfoWrapper(response,callback)});
   /* else{
        callback();
    }*/
}
    /*var logedInUser;

    function resetSelectedUserDetailObject(user) {

    	

    	user = new Object();

    	user.id = user.id;

    	logedInUser.phoneNo = user.phone_no;

    }*/
function appendCustPersonalInfoWrapper(user,callback) {
    //alert(logedInUser.userID);
    //resetlogedInUserDetailObject(user);
    showCustomerLoanPage(user,callback);
}

function getCustomerSecondaryLeftNav() {
    var leftTab2Wrapper = $('<div>').attr({
        "class": "lp-t2-wrapper",
        "id": "cust-sec-nav"
    });
    newfiObject.applicationKnowNewfi=undefined;
    
    if(newfi.appUserDetails){
        try{

        	var tutorialStatus = undefined;
        	if(newfiObject.appUserDetails.user == undefined){
        		tutorialStatus = JSON.parse(newfiObject.appUserDetails).user.customerDetail.tutorialStatus;
        		
        	}
        	else{
        		tutorialStatus = newfi.appUserDetails.user.customerDetail.tutorialStatus;
        	}
        	
           if(tutorialStatus==true || removedKnwoNewFi ==true){
            	flagKnowNewFi=false;
            }
        }catch(e){
          console.log("catch");
        }
    }
    var step1 = "";
    if(flagKnowNewFi){
    	step1 = getCustomerSecondaryLeftNavStep(1, "Getting to know newfi");
        newfiObject.applicationKnowNewfi=step1;
    } 


    newfiObject.applicationNavTab=undefined;
    //check if user submitted application
    var flag=true;
    if(newfi.appUserDetails){
        try{
        	var loan = undefined;
        	
        	if(newfiObject.appUserDetails.user == undefined){
        		loan = JSON.parse(newfiObject.appUserDetails).loan;
        		
        	}
        	else{
        		loan = newfiObject.appUserDetails.loan;
        	}
        	//NEXNF-647
            /*if(loan.lqbFileId && loan.lqbFileId!=""){
                flag=false;
            }*/
        }catch(e){
        	 console.log("catch");
        }
    }
    var step2 = "";
    if(flag){
    	//NEXNF-635 changed complete my application to application
    	//portal updates 7.9 changed from application to loan application
        step2 = getCustomerSecondaryLeftNavStep(2, "loan<br />application");
        newfiObject.applicationNavTab=step2;
    } 
    //NEXNF-635
    /*  var step3 = getCustomerSecondaryLeftNavStep(3, "my<br />rate options");*/
    var step3 = getCustomerSecondaryLeftNavStep(3, "programs<br />and rates");
    //jira-711
/*    var step4 = getCustomerSecondaryLeftNavStep(4, "upload<br />needed items");*/
    var step4 = getCustomerSecondaryLeftNavStep(4, "manage<br />documents");
    //NEXNF-635
    /* var step5 = getCustomerSecondaryLeftNavStep(5, "my <br /> loan progress");*/
    //NEXNF-762
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

function showCustomerLoanPage(user,callback) {
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
    if (window.location.hash== "" ||window.location.hash=="#myLoan")
    {
    	changeSecondaryLeftPanel(5);
    }
    //adjustCenterPanelWidth();
    //TODO: Invoke dynamic binder to listen to secondary navigation clicks
    bindDataToSN();
    globalSNBinder();
    if(callback)
        callback();
}


function changeSecondaryLeftPanel(secondary,doNothing) {
    clearOverlayMessage();
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
    	paintGettingToKnowPage();
    } else if (secondary == 2) {
    	var userId=newfiObject.user.id;
        getAppDetailsForUser(userId,function(appUserDetailsTemp){
        	/*if(!appUserDetailsTemp.loan.lqbFileId){*/
                paintCustomerApplicationPage();
            /*}else{
            	hideCompleteYourProfile();
            	paintApplicationAlreadySubmittedPage(appUserDetailsTemp.loanType.loanTypeCd);
            }*/
        });
        //paintSelecedOption();
    } else if (secondary == 3) {
        // fix your rate page
    	 if(!doNothing){
/*	                var userId=selectedUserDetail==undefined?newfiObject.user.id:selectedUserDetail.id;
	                getAppDetailsForUser(userId,function(appUserDetails){
	                   createLoan(appUserDetails)
	                });*/

            var userId=newfiObject.user.id;
            getAppDetailsForUser(userId,function(appUserDetailsTemp){
            	
                var LQBFileId=appUserDetailsTemp.loan.lqbFileId;
                if(LQBFileId){
                    if(appUserDetailsTemp.loan.isRateLocked){
                        fixAndLoakYourRatePage2(undefined, appUserDetailsTemp);
                       
                        
                    }else{
                        paintFixYourRatePage();
                    }
                 
                }else{
                    //code to Paint teaser rate page
                	if(JSON.parse(newfiObject.appUserDetails).loanType.description !="None"){
                    paintTeaserRatePageBasedOnLoanType(appUserDetailsTemp);}
                	else{
                		showToastMessage(fillApplicationPath);
                		window.location.href =newfiObject.baseUrl+"home.do#myLoan/my-application";
                		return false;
                	}
                }
            
            } , overlayMessage);
           
         //showToastMessage("Please Complete Your Application first");
        }else{
            
        }
    } else if (secondary == 4) {
        // upload need items
        uploadNeededItemsPage();
    } else if (secondary == 5) {
        // loan progress
        paintCustomerLoanProgressPage();
    }
}

//Function to paint the application page once the application is submitted
function paintApplicationAlreadySubmittedPage(loanType) {
	$('#center-panel-cont').html("<div class='complete-application-header'>Application already submitted.</div>");
	
	var descText = $('<div>').attr({
		"class" : "app-completed-text"
	}).html("Congratulations! Based on the information you provided, you have been pre-qualified for a newfi loan. Here's what next:");
	var container = $('#center-panel-cont').append(descText);
	if (loanType == "PUR")
	{
			var btn1 = $('<div>').attr({
				"class" : "getting-to-know-btn margin-0-auto"
			}).html("View my pre-qualification letter").on('click',function(){				
				showDialogPopup("Pre-Qual Letter sent",preQualificationLetterAlreadySentToEmail,callBackpopupFunction);
				return;
			});
			container.append(btn1);
	}
	var btn2 = $('<div>').attr({
		"class" : "getting-to-know-btn margin-0-auto"
	}).html("View my rate options").on('click',function(){
		
		window.location.href =newfiObject.baseUrl+"home.do#myLoan/lock-my-rate";
	});
	container.append(btn2);
	
	var btn3 = $('<div>').attr({
		"class" : "getting-to-know-btn margin-0-auto"
	}).html("Talk to my loan advisor").on('click',function(){
		
		window.location.href =newfiObject.baseUrl+"home.do#myTeam";
	});
	container.append(btn3);

}

/*
 * Function to paint the getting to know newfi page on customer login
 */
function paintGettingToKnowPage() {
	var wrapper = $('<div>').attr({
        "class": "getting-to-know-wrapper"
    });
   /* var header = $('<div>').attr({
        "class": "complete-application-header"
    }).html("Getting to Know Newfi").on('click',function(){
    	
    	var inputData = {};
    	inputData.id = JSON.parse(newfiObject.appUserDetails).user.customerDetail.id;
    	inputData.loanId = JSON.parse(newfiObject.appUserDetails).loan.id;
    	
    	$.ajax({
    		
    		url:"rest/userprofile/updatetutorialstatus",
    		type: "POST",
    		data: {
                "inputData": JSON.stringify(inputData)
            },
            datatype: "application/json",
    		success:function(data){
    			if(data.error== null)
    				{
    				showToastMessage("Successfully updated");
    			}else{
    				showErrorToastMessage(data.error.message);
    			}
    		},
    		error:function(data){
    			showErrorToastMessage("Error while updating tutorial status");
    		}
    	});
    	
    });
    wrapper.append(header);
    */
    
    var container = $('<div>').attr({
    	"class" : "getting-to-know-container"
    });
    
    var slideShowCont = $('<ul>').attr({
    	"class" : "pgwSlideshow left-padding"
    });
    
    var imageBaseurl = window.location.origin + "/NewfiWeb/resources/images/";

    var imagesObj = [ {
		"src" : imageBaseurl+"imageNumber1.png",
		"alt" : "",
		"data-description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber2.png",
		"alt" : "",
		"data-description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber3.png",
		"alt" : "",
		"data-description" : ""
	}, {
		"src" :imageBaseurl+ "imageNumber4.png",
		"alt" : "",
		"data-description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber5.png",
		"alt" : "",
		"description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber6.png",
		"alt" : "",
		"description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber7.png",
		"alt" : "",
		"description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber8.png",
		"alt" : "",
		"description" : ""
	}, {
		"src" : imageBaseurl+"imageNumber9.png",
		"alt" : "",
		"description" : ""
	} ];
    
    for(var i=0; i<imagesObj.length; i++){
    	
    	var item = $('<li>');
    	
    	var image = $('<img>').attr({
        	"src" : imagesObj[i].src,
        	"alt" : imagesObj[i].alt,
        	"data-description" : imagesObj[i].description
        });    	
    	
    	item.append(image);
    	slideShowCont.append(item);
    }
    
    container.append(slideShowCont);
   
    var btnWrapper = $('<div>').attr({
    	"class" : "slider-prev-next-btn-row clearfix"
    });
    
    var prevBtn = $('<div>').attr({
    	"class" : "slider-btn slider-prev-btn cep-button-color float-left"
    }).html('Previous');
    
    var nextBtn = $('<div>').attr({
    	"class" : "slider-btn slider-next-btn cep-button-color float-right"
    }).html('Next');
    
    btnWrapper.append(prevBtn).append(nextBtn);
    
    container.append(btnWrapper);
    
    $('#center-panel-cont').html(wrapper).append(container);
    
    var pgwSlideshow = $('.pgwSlideshow').pgwSlideshow();
    
    /*pgwSlideshow.reload({
	beforeSlide : function() {
		var currentSlide = pgwSlideshow.getCurrentSlide();
		var totalSlide = pgwSlideshow.getSlideCount();
		if(currentSlide >= totalSlide){
			console.log("Last Slide");
			redirectToGettingToKnowLastPage();
		}
		console.log("currentSlide : " +currentSlide + ",totalSlide : "+totalSlide);
	}
   });*/
    
    nextBtn.click(function(){
    	
    	var currentSlide = pgwSlideshow.getCurrentSlide();
		var totalSlide = pgwSlideshow.getSlideCount();
		if(currentSlide == totalSlide){
			console.log("Last Slide");
			redirectToGettingToKnowLastPage();
		}
		pgwSlideshow.nextSlide();
    	
    });
    
    prevBtn.click(function(){
    	pgwSlideshow.previousSlide();
    });
    
    adjustCenterPanelWidth();
}

function redirectToGettingToKnowLastPage() {
	var parentContainer = $('.getting-to-know-container');
	parentContainer.html('');
	
/*	var cont1 = $('<div>').attr({
		"class" : "getting-to-know-row clearfix"
	});
	
	cont1.append("<div class='getting-to-know-hdr-txt'>I'm not finished with the tutorial</div>");*/
	
/*	var cont1btn1 = $('<div>').attr({
		"class" : "getting-to-know-btn float-right"
	}).html("Next Steps")
	.click(function(){
		paintGettingToKnowPage();
	});
	
	cont1.append(cont1btn1);*/

/*	
	var cont2 = $('<div>').attr({
		"class" : "getting-to-know-row clearfix"
	});
	
	cont2.append("<div class='getting-to-know-hdr-txt'>I'm finished with the tutorial, take me to</div>");*/
	var cont2 = $('<div>').attr({
		"class" : "getting-to-know-row clearfix"
	});
	
	cont2.append("<div class='getting-to-know-hdr-txt'>Next Steps</div>");
	var cont2btn1 = $('<div>').attr({
		"class" : "getting-to-know-btn float-right"
	}).html("Complete Application").on('click',function(){
		//NEXNF-577 changed the text Complete My Loan Profile to Complete My Application
		//NEXNF-635 changed the text Complete My Application to Application
		//portal updates 7.16 changed the text Application to Complete Application 
			removedKnwoNewFi = true;
			finishedTutorial(newfiObject.applicationKnowNewfi,"home.do#myLoan/my-application");
	        newfiObject.applicationKnowNewfi=undefined;

	});
	
/*	var cont2btn2 = $('<div>').attr({
		"class" : "getting-to-know-btn float-right"
	}).html("View more rate options").on('click',function(){
		
		removedKnwoNewFi = true;
		finishedTutorial(newfiObject.applicationKnowNewfi,"home.do#myLoan/lock-my-rate");
		newfiObject.applicationKnowNewfi=undefined;
	});
	
	var cont2btn3 = $('<div>').attr({
		"class" : "getting-to-know-btn float-right"
	}).html("Check out my loan progress").on('click',function(){
		
		removedKnwoNewFi = true;
		finishedTutorial(newfiObject.applicationKnowNewfi,"home.do#myLoan/my-loan-progress");
		newfiObject.applicationKnowNewfi=undefined;
	});*/
	
	var cont2btn4 = $('<div>').attr({
		"class" : "getting-to-know-btn float-right"
	}).html("Talk to My Loan Advisor").on('click',function(){
		
		removedKnwoNewFi = true;
		finishedTutorial(newfiObject.applicationKnowNewfi,"home.do#myTeam");
		newfiObject.applicationKnowNewfi=undefined;
	});
/*	
	if(flagToShowCompletPro){
	cont2.append(cont2btn1).append(cont2btn2).append(cont2btn3).append(cont2btn4);
	}else{
		cont2.append(cont2btn2).append(cont2btn3).append(cont2btn4);
	}*/
	if(flagToShowCompletPro){
		cont2.append(cont2btn1).append(cont2btn4);
		}else{
			cont2.append(cont2btn4);
		}
	//parentContainer.append(cont1).append(cont2);
	parentContainer.append(cont2);
	
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
        cache:false,
        data: {
            "completeUserInfo": JSON.stringify(completeUserInfo)
        },
        dataType: "json",
        success: function(data) {},
        error: function(error) {
            showErrorToastMessage(error);
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

    //NEXNF-637
    /*var header = $('<div>').attr({
        "class": "complete-application-header"
    }).html("Complete My Loan Profile");*/
    var header = $('<div>').attr({
        "class": "complete-application-header"
    }).html("Complete Application");//Changed from Complete My Application to Complete Application
    
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



function lockLoanRate(lockratedata,element){

//alert('final lockratedata'+JSON.stringify(lockratedata));
    lockratedata.IlpTemplateId =closingCostHolder.valueSet.lLpTemplateId;
    lockratedata.requestedRate = closingCostHolder.valueSet.teaserRate;
    lockratedata.loanId=appUserDetails.loan.id;
    lockratedata.requestedFee = closingCostHolder.valueSet.point;
    lockratedata.rateVo=JSON.stringify(closingCostHolder.valueSet);
    $('#overlay-loader').show();
    $.ajax({
        url: "rest/application/lockLoanRate",
        type: "POST",
        cache:false,
        data: {
            "appFormData": JSON.stringify(lockratedata)
        },
        datatype: "application/json",
        success: function(data) {
            $('#overlay-loader').hide();
            var status="";
            var message="Something Went Wrong";
            var ie=checkIfIE();
            var result;
            if(ie){
                //this part of CODE not tested Need to be tested in IE
                xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
                xmlDoc.async=false;
                result=xmlDoc.loadXML(data); 
            }else{
                var dom=new DOMParser(data)
                result=dom.parseFromString(data, "application/xml");
            }
            if(result){
                status=$(result.documentElement).attr("status");
                message=result.documentElement.childNodes[0].innerHTML;
            }
            if(status=="Error"){
                if(message.indexOf("HOUR_CUTOFF")>=0){
                    message="Rates can be locked between : 08:30 AM PST - 04:00 PM PST";
                }
                showToastMessage(message);
            }else{
            	rateLockRequestedFlag = true;
        	    $('input').attr("readonly","true");
                showToastMessage(RateLockRequested);
                $(element).html( "Rate Lock Requested" ).unbind( "click").addClass("rateLockRequested");
                /*alert('loan is locked');*/
            }
                
            //TO:DO pass the data (json)which is coming from the controller
            //paintLockRate(data,appUserDetails);
            //paintLockRate(JSON.parse(data), appUserDetails);
            
            //need to redirect to milestone page/reload the page
        },
        error: function() {
            showErrorToastMessage("error");
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
function paintFixYourRatePage(appUserDetails) {
	
	
	
	//alert('isnide paintFixYourRatePage');
	appUserDetails = {};
	
	appUserDetails = JSON.parse(newfi.appUserDetails);
	//alert('appUserDetails JSON'+JSON.stringify(appUserDetails));
	loanNumber = appUserDetails.loan.lqbFileId;
	//alert('loanNumber'+loanNumber);
	fetchLockRatedata( loanNumber,appUserDetails);
	
}


var responseTime;
function fetchLockRatedata(loanNumber,appUserDetailsParam)
{
//alert('inside create loan method');
 showOverlay();

var url="";
var method="";
var data=undefined;
if(appUserDetailsParam&&appUserDetailsParam.loan&&appUserDetailsParam.loan.lockStatus=="1"){
    method="GET";
    url="rest/application/fetchLockRateData/"+appUserDetailsParam.loan.id;
}else if(appUserDetails&&appUserDetails.loan&&appUserDetails.loan.lockStatus=="1"){
    method="GET"
    url="rest/application/fetchLockRateData/"+appUserDetailsParam.loan.id;
}else{
    url="rest/application/fetchLockRatedata/"+loanNumber;
    method="POST"
    data={"appFormData" : JSON.stringify(appUserDetailsParam)};
}


 $.ajax({
		url:url,
		type:method,
		cache:false,
		data:data,
		datatype : "application/json",
		success:function(data){
            var ob;
            if(appUserDetailsParam&&appUserDetailsParam.loan&&appUserDetailsParam.loan.lockStatus=="1"){
                appUserDetails=JSON.parse(data);
                ob={};
            }else if(appUserDetails&&appUserDetails.loan&&appUserDetails.loan.lockStatus=="1"){
                appUserDetails=data;
                ob={};
            }else{
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
            }
		    
           // alert('fetchLockRatedata data is '+JSON.stringify(data));
            /*if(data==""){
                $('#center-panel-cont').html("Sorry, We could not find suitable products for you! One of our Loan officers will get in touch with you");
            }else{*/
			    fixAndLoakYourRatePage(ob, appUserDetails) ;
            /*}*/
			hideOverlay();
			clearOverlayMessage();
		},
		error:function(erro){
			showErrorToastMessage(errorInCreateLoan);
			 $('#overlay-loader').hide();
		}
		
	});
}


function fixAndLoakYourRatePage2(lqbData1, appUserDetails) {
    var lqbData1;
    if(lqbData1){
        var lqbFileId ={};
        $('#center-panel-cont').html("");
        try{
            var loanNumber = lqbData1[0].loanNumber;
            loan.lqbFileId=loanNumber;  
            
            appUserDetails.loan.lqbFileId = loanNumber;
            lockratedata.sLoanNumber=loanNumber;
        }catch(exception){
            console.log("caught Exception : "+exception);
        }

        lqbData1 =  modifiedLQBJsonResponse(lqbData1);
    }
    
   // alert('modified data'+lqbData1)
        var loanSummaryWrapper = getLoanSummaryWrapper(lqbData1, appUserDetails);

        var closingCostWrapper = getClosingCostSummaryContainer(getLQBObj(lqbData1));
        $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);

}


function fixAndLoakYourRatePage(lqbData, appUserDetails) {



		var lqbFileId ={};
        $('#center-panel-cont').html("");
        try{
            var loanNumber = lqbData[0].loanNumber;
            loan.lqbFileId=loanNumber;  
            
     //  alert('loan Number'+loanNumber);
            appUserDetails.loan.lqbFileId = loanNumber;
            newfiObject.appUserDetails=JSON.stringify(appUserDetails);
            lockratedata.sLoanNumber=loanNumber;
        }catch(exception){
            console.log("caught Exception : "+exception);
            var userId;
            if(selectedUserDetail)
                userId=selectedUserDetail.userID
            else
                userId=newfiObject.user.id;
            getAppDetailsForUser(userId)
        }
    //alert('final appUserDetails'+JSON.stringify(appUserDetails));
        //saveAndUpdateLoanAppForm(appUserDetails);

        teaserRateValHolder.teaserRate=false;

        paintRatePage(lqbData, appUserDetails,$('#center-panel-cont'))
        
       /* var lqbData =  modifiedLQBJsonResponse(lqbData);
        //alert('script lqbdata'+JSON.stringify(lqbData));
        var loanSummaryWrapper = getLoanSummaryWrapper(lqbData, appUserDetails);
        var closingCostWrapper = getClosingCostSummaryContainer(getLQBObj(lqbData));
        $('#center-panel-cont').append(loanSummaryWrapper).append(closingCostWrapper);*/
       //$('#center-panel-cont').append(loanSummaryWrapper);
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
    var bottomText ="";
    var yearValues = lqbData;
    var rateVoObj=getLQBObj(yearValues);

    var rateWrapper ="";
    if(!rateVoObj.dummyData){
        rateWrapper =getLoanSliderWrapper(lqbData,appUserDetails);
        bottomText = getHeaderText("Rate and APR quoted are based on the information you provided are not guaranteed and are subject to change. Actual rate and APR will be available on your Good Faith Estimate after loan amount and income are verified");
    }
    parentWrapper.append(header).append(container).append(rateWrapper).append(bottomText);
    
    return parentWrapper;
    
}

function getLoanSummaryHeader() {
    var headerCont = $('<div>').attr({
        "class": "loan-summary-header clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-header-col1 page-header-loan float-left"
    }).html('Programs and Rates');
    var col2 = $('<div>').attr({
        "class": "loan-summary-header-col2 float-left"
    }).html("Rates as of "+getCurrentDate(responseTime));
    headerCont.append(col1);
    if(responseTime!="")
        headerCont.append(col2);
    return headerCont;
}

function getLoanSummaryContainerPurchase(lqbData, appUserDetails) {
    
	var yearValues = lqbData;
    
    var rateVoObj=getLQBObj(yearValues);

	var livingSituation = capitalizeFirstLetter(appUserDetails.purchaseDetails.livingSituation);
    
	
	 var housePrice = getFloatValue(appUserDetails.purchaseDetails.housePrice);   
	 var loanAmount =  getFloatValue(appUserDetails.purchaseDetails.loanAmount) ;    
	 var downPayment = (housePrice-loanAmount);
	
	 var Insurance =  parseFloat(removedDoller(removedComma(appUserDetails.propertyTypeMaster.propertyInsuranceCost)));
	 var tax =  parseFloat(removedDoller(removedComma(appUserDetails.propertyTypeMaster.propertyTaxesPaid)));
	 
	 if(isNaN(getFloatValue(tax)))
	        tax="";
	 if(isNaN(getFloatValue(Insurance)))
	        Insurance="";
	 
	 var principalInterest = getFloatValue(rateVoObj.payment);
	 var totalEstMonthlyPayment = principalInterest;
	 
	 
	var wrapper = $('<div>').attr({
		"class": "loan-summary-container"
	});
	 
	var container = $('<div>').attr({
        "class": "clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
    //var lcRow1 = getLaonSummaryApplyBtnRow();
    var lcRow1 = getLoanSummaryRow("Loan Type", "Purchase -"+livingSituation);
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVoObj.yearData +" Years Fixed","loanprogramId");
    //var lcRow3 = getLoanSummaryRow("Loan Amount", loanAmount);
    var lcRow3 =  getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Purchase Amount",showValue(housePrice), " Down Payment",showValue(downPayment),false);
    //var lcRow4 = getLoanSummaryRow("Down Payment", "$ 100,000.00");
    //var lcRow5 = getLoanSummaryRow("Purchase Amount", estimatedPrice);
    var val="";
    if(rateVoObj.teaserRate)
        val=parseFloat(rateVoObj.teaserRate).toFixed(3)+" %";
    var lcRow4 = getLoanSummaryRow("Interest Rate", val, "lockInterestRate");
    var lcRow5 = getLoanSummaryRow("APR", rateVoObj.APR +" %", "lockrateaprid");
    //var lcRow6 = getLoanSummaryLastRow("Estimated<br/>Closing Cost",  showValue(rateVO[index].closingCost), "closingCostId");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5);
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    // add rows in right column
    
    var rcRow1 ="";
    if(appUserDetails.purchaseDetails.livingSituation != "homeOwner")
     rcRow1 = getLoanSummaryRow("Current Rental Payment", showValue(appUserDetails.monthlyRent),"monthlyPaymentId");
    
    var rcRow2 = getLoanSummaryRow("Proposed Principal & Interest", showValue(rateVoObj.payment),"principalIntId");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Tax",showValue(tax),"calTaxID","calTaxID2",appUserDetails);
    rcRow3.addClass("no-border-bottom");
    var rcRow4 = getLoanSummaryRowCalculateBtn("Insurance",showValue(Insurance),"CalInsuranceID","CalInsuranceID2",appUserDetails);
    //var rcRow5 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal & interest payment");
    //var rcRow6 = getLoanSummaryLastRow("Estimated<br/>Monthly Payment", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow4);
    container.append(leftCol).append(rightCol);
    
    
    var bottomRow = $('<div>').attr({
    	"class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
    	"class" : "loan-summary-lp float-left"
    });
    var bottomLcRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs",  showValue(rateVoObj.closingCost), "lockClosingCost");
    
    bottomLeftCol.append(bottomLcRow);
    
    var bottomRightCol = $('<div>').attr({
    	"class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Estimated<br/>Monthly Payments", showValue(totalEstMonthlyPayment),"totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);
    
    bottomRow.append(bottomLeftCol).append(bottomRightCol);
    
    return wrapper.append(container).append(bottomRow); 
}

function getLoanSummaryContainerRefinance(lqbData, appUserDetails) {
    
	var yearValues = lqbData;
   // var rateVO = yearValues[yearValues.length-1].rateVO;
   // var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
 
    var rateVoObj=getLQBObj(yearValues);
    
    var loanAmount = appUserDetails.refinancedetails.currentMortgageBalance;
    
    if (appUserDetails.refinancedetails.refinanceOption == "REFLMP") refinanceOpt = "Lower monthly payment";
    if (appUserDetails.refinancedetails.refinanceOption == "REFMF") refinanceOpt = "Pay off mortgage faster";
    if (appUserDetails.refinancedetails.refinanceOption == "REFCO"){
    	refinanceOpt = "Take cash out";
    	
        var cashTakeOut = getFloatValue(appUserDetails.refinancedetails.cashTakeOut);
        var currentMortgageBalance = getFloatValue(appUserDetails.refinancedetails.currentMortgageBalance);
    	loanAmount = cashTakeOut + currentMortgageBalance;
    }
    
    var  monthlyPayment  = parseFloat(removedDoller(removedComma(appUserDetails.refinancedetails.currentMortgagePayment)));
    var principalInterest = parseFloat(removedDoller(removedComma(rateVoObj.payment)));
    var totalEstMonthlyPayment = principalInterest;
    var Insurance =  parseFloat(removedDoller(removedComma(appUserDetails.propertyTypeMaster.propertyInsuranceCost)));
	var tax =  parseFloat(removedDoller(removedComma(appUserDetails.propertyTypeMaster.propertyTaxesPaid)));
    if(isNaN(getFloatValue(tax)))
        tax="";
    if(isNaN(getFloatValue(Insurance)))
        Insurance="";
    
    if(appUserDetails.refinancedetails.includeTaxes == true){
    	
    	var investment = (Insurance + tax);
    	monthlyPayment= monthlyPayment - investment ;
    	
    	totalEstMonthlyPayment  = principalInterest + Insurance + tax;
   	   
    }
    
    var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));


    var wrapper = $('<div>').attr({
        "class": "loan-summary-container"
    });
    
    var container = $('<div>').attr({
    	"class" : "clearfix"
    });
    var leftCol = $('<div>').attr({
        "class": "loan-summary-lp float-left"
    });
    // add rows in left column
    var lcRow1 = getLoanSummaryRow("Loan Type", "Refinance - " + refinanceOpt);
    var lcRow2 = getLoanSummaryRow("Loan Program", rateVoObj.yearData +" Year Fixed","loanprogramId");
    var val="";
    if(rateVoObj.teaserRate)
        val=parseFloat(rateVoObj.teaserRate).toFixed(3)+" %";
    var lcRow3 = getLoanSummaryRow("Interest Rate", val , "lockInterestRate");
    
    if(appUserDetails.refinancedetails.refinanceOption != "REFCO")
    var lcRow4 = getLoanAmountRow("Loan Amount",showValue(loanAmount), "loanAmount");
    else
        var lcRow4 = getLoanAmountRowPurchase("Loan Amount", showValue(loanAmount), "loanAmount","Current Loan Amout",showValue(currentMortgageBalance), "Cashout",showValue(cashTakeOut),true);

    var lcRow5 = getLoanSummaryRow("APR", rateVoObj.APR+" %", "lockrateaprid");
    //var lcRow6 = getLoanSummaryLastRow("Estimated<br/>Closing Cost", showValue(rateVO[index].closingCost), "lockClosingCost");
    leftCol.append(lcRow1).append(lcRow2).append(lcRow3).append(lcRow4).append(lcRow5);
    
    lockratedata.IlpTemplateId =rateVoObj.lLpTemplateId;
  	lockratedata.requestedRate = rateVoObj.teaserRate;
   	lockratedata.requestedFee = rateVoObj.point;
    
   	// add rows in right column
    var rightCol = $('<div>').attr({
        "class": "loan-summary-rp float-right"
    });
    
    var rcRow1 = getLoanSummaryRow("Proposed Principal & Interest",showValue(principalInterest) ,"principalIntId");
    var rcRow2 = getLoanSummaryRowCalculateBtn("Tax", showValue(tax),"calTaxID","calTaxID2",appUserDetails);
    rcRow2.addClass("no-border-bottom");
    var rcRow3 = getLoanSummaryRowCalculateBtn("Insurance", showValue(Insurance),"CalInsuranceID","CalInsuranceID2",appUserDetails);
    //var rcRow4 = getLoanSummaryTextRow("Your tax and insurance payment above will be included with your principal 																			& interest payment");
    //var rcRow5 = getLoanSummaryRow("Total Est. Monthly Payment", "$ 3,298.40");
    var rcRow6 = getLoanSummaryRow("Current Monthly Payment", showValue(monthlyPayment) ,"monthlyPaymentId");
    //var rcRow8 = getLoanSummaryLastRow("Total Est.<br/>Monthly Payment", showValue(totalEstMonthlyPayment), "totalEstMonthlyPaymentId");
    rightCol.append(rcRow1).append(rcRow2).append(rcRow3).append(rcRow6);
    container.append(leftCol).append(rightCol);
    
    
    var bottomRow = $('<div>').attr({
    	"class" : "clearfix"
    });
    
    var bottomLeftCol = $('<div>').attr({
    	"class" : "loan-summary-lp float-left"
    });
    var bottomLcRow = getLoanSummaryLastRow("Estimated<br/>Closing Costs", showValue(rateVoObj.closingCost), "lockClosingCost");
    
    bottomLeftCol.append(bottomLcRow);
    
    var bottomRightCol = $('<div>').attr({
    	"class" : "loan-summary-rp float-left"
    });
    
    var bottomRcRow = getLoanSummaryLastRow("Total Est.<br/>Monthly Payments", showValue(totalEstMonthlyPayment), "totalEstMonthlyPaymentId");
    bottomRightCol.append(bottomRcRow);
    var hgLow="";
    if(totalEstMonthlyPayment<monthlyPayment){
        hgLow='<font color="green"><b>Lower</b></font>';
    }else{
        hgLow='<font color="red"><b>Higher</b></font>';
    }
    var rcRow7 = getLoanSummaryLastRow('This Monthly<br/> Payment is '+hgLow+' by',showValue(monthlyPaymentDifference),"monthlyPaymentDifferenceId");
    bottomRightCol.append(rcRow7)

    
    bottomRow.append(bottomLeftCol).append(bottomRightCol);
    
    return wrapper.append(container).append(bottomRow);
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

function getLoanAmountRow(desc, detail, id,row1Desc,row1Val,row2Desc,row2Val) {
	
	var flag = false;
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
        "class": "loan-summary-col-detail float-left clearfix",
    });
   
    var input = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left",
        "id": id
    }).val(detail)
    .keydown(function() {
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});	
    	
    	flag = true;
    	
    });
    
    
    
    /*.on('keyup',function(e){
    	if(e.which == 27){
    		$(this).blur();
    	}
    });*/
 
    
    var saveBtn = $('<div>').attr({
    	"class" : "cep-button-color sm-save-btn float-right"
    }).html("Save").on('click',{'flag':flag},function(){
    	
    	if(flag){
	    	amt = $('#loanAmount').val();
            if(teaserRateValHolder.teaserRate){
                modifiyTeaserRate(amt);
            }else{
                modifiyLockRateLoanAmt(amt);
            }
    	}
    });
    
    col2.append(input).append(saveBtn);
    
    loanAmountCont.append(col1).append(col2);
    return container.append(loanAmountCont);
}

function getLoanSummaryRow(desc, detail, id,containerId,hideFlag) {
    var clas="";
    if(hideFlag)
        clas="hide";
    var container = $('<div>').attr({
        "class": "loan-summary-row clearfix "+clas
    });
    if(containerId)
        container.attr({
            "id":containerId
        });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    var col2 = $('<div>').attr({
        "class": "loan-summary-col-detail float-left",
        "id": id
    }).html(detail).val(detail);
    container.append(col1).append(col2);
    return container;
}

function getLoanSummaryRowCalculateBtn(desc, detail,id,id2,appUserDetails) {
    
	var container = $('<div>').attr({
        "class": "loan-summary-row clearfix"
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
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
    
    var inputBox = $('<input>').attr({
    	"class" : "loan-summary-sub-col-detail",
    	"id":id2
    }).bind('keyup',{"appUserDetails":appUserDetails},function(e){
    	appUserDetails=e.data.appUserDetails;
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});	
    	
    	
    	/*if(e.which == 27){
    		var prevVal = $(this).prev('.calculate-btn').text();
    		if($(this).val() == undefined || $(this).val() == prevVal){
    			$(this).hide();
    			$(this).prev('.calculate-btn').show();
    		}
    	}*/
    	
    	
    	//
    	
    	var taxesTemp = 0;
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
        $(itm).find(".loan-summary-col-desc").html('This Monthly<br/> Payment is '+hgLow+' by');
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
        }
    	//
    	
    });
    $(inputBox).val(detail);
    
    var saveBtn = $('<div>').attr({
    	"class" : "cep-button-color sm-save-btn float-right"
    }).html("Save").on('click',function(){
    	
    	saveTaxAndInsurance();
    });
    
    
    
    col2.append(inputBox);
    
    if(desc =="Insurance" && !teaserRateValHolder.teaserRate)
        container.append(col1).append(col2.append(saveBtn));
    else
    	container.append(col1).append(col2);
    
    return container;
}


function getLoanSummaryLastRow(desc, detail, id,lighterBackFlag,paddingLeftFlag) {
    var clas="loan-summary-col-detail";
    var paddingClass="";
    if(lighterBackFlag){
        clas="loan-summary-col-detail-light";
    }else{
        paddingClass="estimated-row-padding-clas";
    }
    var container = $('<div>').attr({
        "class": "loan-summary-last-row clearfix "+paddingClass
    });
    var col1 = $('<div>').attr({
        "class": "loan-summary-col-desc float-left"
    }).html(desc);
    
    
    if(desc.indexOf('color="green"')>0){
        clas="loan-summary-green-col-detail";
    }else if(desc.indexOf('<font >')>0){
        clas="loan-summary-red-col-detail";
    }
    var paddingClass="";
    if(paddingLeftFlag){
    	paddingClass="tax-Ins-clas";
    }
    var col2 = $('<div>').attr({
        "class": clas+" float-left "+paddingClass,
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
    }).html("<span class='rate-mandatoryClass'>*</span>"+text);

    return headerText;
}

function populateClosingCostHolder (inputObject)
{	
	var dynamicList = Object.keys(inputObject);
	for (var attrCount = 0 ; attrCount <= dynamicList.length; attrCount++)
	{
		var currentAttribute = dynamicList[attrCount];
		if (typeof inputObject[currentAttribute] == "object" && Object.keys(inputObject[currentAttribute]).length >0)
		{
			//if that itself is an oBject
			populateClosingCostHolder(inputObject[currentAttribute]);
		}
		else if (inputObject[currentAttribute])
		{
			closingCostHolder[currentAttribute] = inputObject[currentAttribute];
		}
	}	
}
function setClosingCostContainerValues(){
    if(appUserDetails!=undefined){
        closingCostHolder.loanType=(appUserDetails.loanType!=undefined?appUserDetails.loanType.loanTypeCd:"PUR")
    }else{
        closingCostHolder.loanType="PUR";
    }

    if(closingCostHolder.loanType=="PUR"){
        closingCostHolder.housePrice=appUserDetails.purchaseDetails.housePrice;
    }else{
        closingCostHolder.housePrice=appUserDetails.propertyTypeMaster.homeWorthToday;
        closingCostHolder.annualHomeownersInsurance=appUserDetails.propertyTypeMaster.propertyInsuranceCost;
        closingCostHolder.propertyTaxesPaid=appUserDetails.propertyTypeMaster.propertyTaxesPaid;
    }
        
}   
function getClosingCostSummaryContainer(valueSet) {
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
        var header = getClosingCostHeader("Estimated Closing Cost Summary");
       /* var descText = getHeaderText("Based on the information you have provided, below is a summary of your estimated closing costs:");
        var closingDate = $('<span>').attr({
            "class": "semibold"
        });
        descText.append(closingDate);*/
        var topContainer = getClosingCostTopConatiner();
        var bottomContainer = getClosingCostBottomConatiner();
        //parentWrapper.append(header).append(descText).append(topContainer).append(bottomContainer);
        parentWrapper.append(header).append(topContainer).append(bottomContainer);
    }
    return parentWrapper;
}



function getClosingCostHeader(text) {
    var header = $('<div>').attr({
        "class": "closing-cost-header  cus-eng-font capitalize ccSummary"
    }).html(text);
    return header;
}

function getClosingCostTopConatiner() {
    var wrapper = $('<div>').attr({
        //"class": "closing-cost-cont-wrapper-top"
    });
    //var heading = getClosingCostHeadingCont("Estimated Closing Costs");
    var container1 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon1 = getClosingCostConatinerHeader("Estimated Lender Costs");
    var row1Con1 = getClosingCostContainerRow(1, getClosingCostLabel("Lender Fee"), "");
    var row2Con1 = getClosingCostContainerRow(2, getClosingCostLabel("This is your cost or credit based on rate selected"), "");
    var row3Con1 = getClosingCostContainerLastRow(3, getClosingCostLabel("Estimated Lender Costs"), "");
    container1.append(headerCon1).append(row1Con1).append(row2Con1).append(row3Con1);
    var container2 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon2 = getClosingCostConatinerHeader("Estimated Third Party Costs");
    var row1Con2 = getClosingCostContainerRow(1, getClosingCostLabel("Appraisal Fee"), "");
    var row2Con2 = getClosingCostContainerRow(2, getClosingCostLabel("Credit Report"), "");
    var row3Con2 = getClosingCostContainerRow(3, getClosingCostLabel("Flood Certification"), "");
    var row4Con2 = getClosingCostContainerRow(4, getClosingCostLabel("Wire Fee"), "");
    var row4_1Con2;
    if(closingCostHolder.loanType&&closingCostHolder.loanType=="PUR")
        row4_1Con2 = getClosingCostContainerRow(5, getClosingCostLabel("Owners Title Insurance"), "");
    var row5Con2 = getClosingCostContainerRow(5, getClosingCostLabel("Lenders Title Insurance"), "");
    var row6Con2 = getClosingCostContainerRow(6, getClosingCostLabel("Closing/Escrow Fee"), "");
    var row7Con2 = getClosingCostContainerRow(7, getClosingCostLabel("Recording Fee"), "");
    var row8Con2;
    if(closingCostHolder.loanType&&closingCostHolder.loanType=="PUR")
    	//NEXNF-483
        //row8Con2= getClosingCostContainerRow(8, getClosingCostLabel("City/County Tax stamps"), "$ 107.00");
    row8Con2= getClosingCostContainerRow(8, getClosingCostLabel("City/County Tax stamps"), "");
    var row9Con2 = getClosingCostContainerLastRow(9, getClosingCostLabel("Total Estimated Third Party Costs"), "");
    container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row3Con2).append(row4Con2).append(row4_1Con2).append(row5Con2).append(row6Con2).append(row7Con2).append(row8Con2).append(row9Con2);
    
    var container3 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    //var headerCon3 = getClosingCostConatinerHeader("Estimated Prepaids");
   // var row1Con3 = getClosingCostContainerRowWithSubText(1, getClosingCostLabel("Interest"), "","");
    //var row2Con3 = getClosingCostContainerRow(2, getClosingCostLabel("Homeowners Insurance"), "");
    //var row3Con3 = getClosingCostContainerLastRow(3, getClosingCostLabel("Total Prepaids"), "");
    var row10Con3 = getClosingCostContainerLastRow(10, getClosingCostLabel("Total Estimated Closing Cost"), "");
    
    //container3.append(headerCon3).append(row1Con3).append(row2Con3).append(row3Con3).append(row10Con3);
    container3.append(row10Con3);
    
    //return wrapper.append(heading).append(container1).append(container2).append(container3);
    return wrapper.append(container1).append(container2).append(container3);
}

function getClosingCostBottomConatiner() {
    // removed class to fixed : NEXNF-578
	var wrapper = $('<div>').attr({
        "class": ""
    });
    //var heading = getClosingCostHeadingCont("Total Estimated Closing Cost");
    var container2 = $('<div>').attr({
        "class": "closing-cost-container"
    });
    var headerCon2 = getClosingCostConatinerHeader("Estimated Prepaids and Escrows");
    //NEXNF-569
    
    var row1Con3 = getClosingCostContainerRowWithSubText(1, getClosingCostLabel("Interest"), "","");
    var row2Con3 = getClosingCostContainerRow(2, getClosingCostLabel("Homeowners Insurance"), "");
    
/*    var row1Con2 = getClosingCostContainerRowWithSubText(1, getClosingCostLabel("Tax Reserve - Estimated 2 Month(s)"), "$ 1,072.00", "(Varies based on calendar month of closing)");*///NEXNF-655
    var row1Con2 = getClosingCostContainerRowWithSubText(1, getClosingCostLabel("Tax Reserve - Estimated 2 Month(s)"), "$ 1,072.00", "Varies based on calendar month of closing");
   /* var row2Con2 = getClosingCostContainerRowWithSubText(2, getClosingCostLabel("Homeowners Insurance Reserve - Estimated 2 Month(s)"), "$ 1,072.00", "(Provided you have 6 months of remaining coverage)");*/
    var row2Con2 = getClosingCostContainerRowWithSubText(2, getClosingCostLabel("Homeowners Insurance Reserve - Estimated 2 Month(s)"), "$ 1,072.00", "Provided you have 6 months of remaining coverage",true);
    //var row1Con2 = getClosingCostContainerRowWithSubText(1, getClosingCostLabel("Tax Reserve - Estimated 2 Month"), "$ 1,072.00", "(Varies based on calendar month of closing)");
    //var row2Con2 = getClosingCostContainerRowWithSubText(2, getClosingCostLabel("Homeowners Insurance Reserve - Estimated 2 Month"), "$ 1,072.00", "(Provided you have 6 months of remaining coverage)");//NEXNF-655
    var row4Con2 = getClosingCostContainerLastRow(4, getClosingCostLabel("Total Estimated Reserves Deposited in Escrow Account"), "");
    
    
    //container2.append(headerCon2).append(row1Con2).append(row2Con2).append(row4Con2);
    container2.append(headerCon2).append(row1Con3).append(row2Con3).append(row1Con2).append(row2Con2).append(row4Con2);
    //NEXNF-655
    /* 
    var bottomSubText = $('<div>').attr({
        "class": "closing-cost-bot-row eng-closing-cost-note"
    }).html("Note: Taxes for 1st and 2nd installments must be paid or will be collected at closing.");
    return wrapper.append(container2).append(bottomSubText);*/
    return wrapper.append(container2);
}

function getClosingCostConatinerHeader(text) {
	//NEXNF-483
	//NEXNF-537
	/*var indentHeaderFeildFlag=false;
	var header="";
	if(text=="Estimated Third Party Costs"){
		indentHeaderFeildFlag=true;
	}
	if(indentHeaderFeildFlag){
		  header = $('<div>').attr({
		        "class": "closing-cost-cont-desc-header eng-indent"
		    }).html(text);
	}else{
		  header = $('<div>').attr({
		        "class": "closing-cost-cont-desc-header"
		    }).html(text);
	}*/
	
	
    var header = $('<div>').attr({
        "class": "closing-cost-cont-desc-header"
    }).html(text);
    
    //NEXNF-622
    header.addClass('closing-cost-cont-desc-header-adj');
    
    return header;
}

function getClosingCostContainerLastRow(rowNum, desc, detail) {
    var key=objectKeyMakerFunction(desc);
    if(closingCostHolder.valueSet[key]){
        detail=closingCostHolder.valueSet[key];
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
    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerRow(rowNum, desc, detail) {
    var key=objectKeyMakerFunction(desc);
    var indentTextFlag=false;
    if(closingCostHolder.valueSet[key]){
        detail=closingCostHolder.valueSet[key];
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
    if(desc=="Lender Fee"||desc=="Appraisal Fee"||desc=="Credit Report"||desc=="Flood Certification"||desc=="Wire Fee"||desc=="Owners Title Insurance"||desc=="Lenders Title Insurance"||desc=="Closing/Escrow Fee"||desc=="Recording Fee"||desc=="Interest"||desc=="City/County Transfer Taxes"||desc=="Homeowners Insurance" || desc =="Your cost or credit based on rate selected"){
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
    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostContainerRowWithSubText(rowNum, desc, detail, subtext,isHomeOwnersInsurance) {
    var key=objectKeyMakerFunction(desc);
    if(closingCostHolder.valueSet[key]){
        detail=closingCostHolder.valueSet[key];
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
    
    
    return row.append(rowDesc).append(rowDetail);
}

function getClosingCostHeadingCont(text) {
    var heading = $('<div>').attr({
        "class": "closing-cost-cont-heading"
    }).html(text);
    return heading;
}

function getFileDragAndDropContainer(loanEmailID) {
    var container = $('<div>').attr({
        "id": "drop-zone",
        "class": "file-drag-drop-container"
    });
    var fileUploadIcn = $('<div>').attr({
        "id": "file-upload-icn",
        "class": "file-upload-icn"
    });
    //NEXNF-806
/*    var textCont1 = $('<div>').attr({
        "class": "file-upload-text-cont1"
    }).html("Select Files to Upload/Drag and Drop Files");*/
    var textCont1 = $('<div>').attr({
        "class": "file-upload-text-cont1"
    }).html("Select file(s) to <div class='upload-files' id='file-upload-icn'>upload</div> or drag and drop file(s) here.");
    //NEXNF-806
/*    var textCont1Mobile = $('<div>').attr({
        "class": "file-upload-text-mobile"
    }).html("Select Files to Upload");*/
    var textCont1Mobile = $('<div>').attr({
        "class": "file-upload-text-mobile"
    }).html("Select File(s) to Upload");
    //NEXNF-806
/*    var textCont2 = $('<div>').attr({
        "class": "file-upload-text-cont2"
    }).html("or e-mail attachment to : "+loanEmailID);*/
    var textCont2 = $('<div>').attr({
        "class": "file-upload-text-cont2"
    }).html("You can also email files to : "+loanEmailID);
    //NEXNF-806
   /* return container.append(fileUploadIcn).append(textCont1Mobile).append(textCont2);*/
    return container.append(textCont1).append(fileUploadIcn).append(textCont1Mobile).append(textCont2);
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
        if (listUploadedFiles[i].needType == undefined || listUploadedFiles[i].needType == null || listUploadedFiles[i].needType == "" ) {
        	if (listUploadedFiles[i].isMiscellaneous != undefined && !listUploadedFiles[i].isMiscellaneous && !userIsInternal())
        	{
        		//Not miscellaneous menas LQB
        		// User is not internal means : he is customer or realtor - so dont show.
        		continue;
        	}
            var col1 = getDocumentUploadColumn(listUploadedFiles[i]);
            documentContainer.append(col1);
            $('.submit-btn').removeClass('hide');
        }
        
    }
    
   
    return container.append(documentContainer);
}

function createdNeededList(categoryName, elements) {
	var incomeDocCont = $('<div>').attr({
		"class" : "needed-doc-container"
	});
	var incDocHeading = $('<div>').attr({
		"class" : "needed-doc-heading"
	}).html(categoryName);
	incomeDocCont.append(incDocHeading);
	$.each(elements, function(i, val) {
		//IF Need is 40, then it is extra. Hence do not show in UI
		if (val.needsListMaster.id != 40) {
			var needDocRow = getNeededDocRow(val.needsListMaster.label,
					val.needsListMaster.id, val.needsListMaster.description);
			incDocHeading.append(needDocRow);

		}

	});
	return incomeDocCont;
}

function getNeededDocRow(desc, needId, details) {
    var row = $('<div>').attr({
        "class": "needed-item-row clearfix"
    });
/*    var leftImage = $('<div>').attr({
        "class": "list-item-icn float-left"
    });*/
    var docUploadImage = $("<div>").attr({
        "class": "doc-uploaded-icn float-left",
        "id" : "doc-uploaded-icn_"+needId,
        "title" : "upload"
    }).data("needId", needId).on("click", uploadDocument);
    
    var rowDesc = $('<div>').attr({
        "class": "needed-item-desc float-left",
        "title" : details
    }).html(desc+"<span class='questionMark'>?</span>");
    /*var docUploadImage = $("<div>").attr({
        "class": "doc-uploaded-icn float-left",
        "id" : "doc-uploaded-icn_"+needId
    }).data("needId", needId).on("click", uploadDocument);*/
/*    row.append(leftImage).append(rowDesc);*/
   
    var docRemoveImage = $('<div>').attr({
        "class": "hide float-left",
        "id": "needDoc" + needId,
        
    });
    
    row.append(docUploadImage).append(docRemoveImage).append(rowDesc);
/*   
    row.append(docUploadImage).append(docRemoveImage);*/
   
    
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

function overlayclickclose() {

        	if(closedialog!=undefined){
        		if (closedialog) {
                    $("#dialog").dialog("close");
                    closedialog = 0;
                    return;
                }
                closedialog = 1;
        	}

        
    
    //set to one because click on dialog box sets to zero 
    
}
function showDialogPopup(title, content, okButtonEvent) {	   
        $("#dialog").empty();
        $("#dialog").html(content);
        $("#dialog").attr("title", title);
        $("#dialog").dialog({
       	    open: function() {
        	    	//alert("hiopen"+closedialog);
        	        closedialog = 0;        	 
        	      //  $(document).bind("click", overlayclickclose);
        	    },
        	    focus: function() {        	    	
        	        closedialog = 0;
        	    },
        	    close: function() {       	    	
        	    	$(this).dialog("close");
                  
        	    },
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
var isPopupScrolled = false;
$(document).on('mousemove',function(){
	if($('#alert-popup-wrapper').hasClass('ps-in-scrolling'))
		isPopupScrolled = true;
});

$(document).on('click', '#alert-popup-wrapper', function(e) {
    e.stopImmediatePropagation();
});
$(document).on('click', function(e) {
	//closedialog = 0;  
    if ($('#alert-popup-wrapper').css("display") == "block") {
        hideAlertNotificationPopup();
    }
    
    overlayclickclose();
    
   

});
$(document).on('click', '#alert-notification-btn', function(e) {
    e.stopImmediatePropagation();
    
    if($('#alert-popup-wrapper-settings').css("display") == "block"){
		hideSettingsPopup();
	}
    if ($(this).has('#alert-popup-cont-wrapper').length == 1) {
        if ($('#alert-popup-cont-wrapper').css("display") == "block") {
        	if(!isPopupScrolled)
        		hideAlertNotificationPopup();
        	isPopupScrolled = false;
        } else {
            showAlertNotificationPopup();
        }
    } else {
        	appendAlertNotificationPopup();
    }
    //NEXNF-601
    var length=$('#alert-notification-btn').find('#alert-popup-cont-wrapper').find('#alert-popup-wrapper').find('.alert-popup-row').length;
    if(length==0){
    	hideAlertNotificationPopup();
    }
});

function showAlertNotificationPopup() {
    $('#alert-popup-cont-wrapper').show();
    $('#alert-popup-wrapper').perfectScrollbar({
        suppressScrollX : true
    });
}

function hideAlertNotificationPopup() {
    $('#alert-popup-cont-wrapper').hide();
}

/*function appendAlertNotificationPopup() {
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
    $('#alert-popup-wrapper').perfectScrollbar({
        suppressScrollX : true
    });
}*/

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

function getLoanSliderWrapper(lqbData,appUserDetails) {
    var wrapper = $('<div>').attr({
        "class": "lock-rate-slider-wrapper"
    });
    var header = "";
    
   
    
    var container = "";

    if(!appUserDetails.loan.isRateLocked){
        var tenureSlider = getYearSliderCont(lqbData,appUserDetails);
        var rateSlider = getRateSliderCont(lqbData,appUserDetails);
    
        header = $('<div>').attr({
            "class": "lock-rate-slider-header"
        }).html("select loan program");
        container= $('<div>').attr({
            "class": "lock-rate-slider-container"
        });
        container.append(header).append(tenureSlider).append(rateSlider);
    }
    var rateBtn = $('<div>');
    getRequestRateLockStatus(rateBtn);
    
    var sendPreQualification = $('<div>').attr({
        "class": "rate-btn pre-qualification "
    }).html("Send Pre-Qualification Letter").on('click',function(){
    	
    	sendPreQualificationLetter();
    });
    
    if(appUserDetails.loanType.loanTypeCd == "PUR"){
    	return wrapper.append(container).append(rateBtn).append(sendPreQualification);
    }
    
 
    return wrapper.append(container).append(rateBtn);
}


function sendPreQualificationLetter(){
	
	$.ajax({
		
		url:"rest/application/sendPreQualiticationLatter",
		type:"POST",
		data:{"appFormData" : JSON.stringify(appUserDetails),"rateDataSet":JSON.stringify(closingCostHolder.valueSet)},
		dataType:"application/json",
		cache:false,
		success:function(data){
			showDialogPopup("Pre-Qual Letter sent",preQualificationLetterSent,callBackpopupFunction);
			return false;

		},
		error:function(data){
			if(data.status != 200)
			{
				showErrorToastMessage("Error");
			}
			else{
				showDialogPopup("Pre-Qual Letter sent",preQualificationLetterSent,callBackpopupFunction);
			}
		}
		
	});
}


function getRequestRateLockStatus(element){
    if(!appUserDetails.loan.isRateLocked){
        var loanId=appUserDetails.loan.id;
        ajaxRequest("rest/loan/"+loanId+"/rateCheck","GET","json",undefined,function(response){
            if(response.error){
                showToastMessage(response.error.message)
            }else{
                var status=response.resultObject;
                rateLockRequestedFlag=appUserDetails.loan.rateLockRequested;
                if(status&&newfiObject.user.userRole.roleCd!="REALTOR"){
                    element.addClass("rate-btn");
                    if(!rateLockRequestedFlag){
	                    element.html("Request Rate Lock").on('click', function(event) {
	                    	    if(!rateLockRequestedFlag)
	                               lockLoanRate(lockratedata,event.target);
	                    });
                    }else{
                    	element.html( "Rate Lock Requested").unbind( "click").addClass("rateLockRequested");
                    }
                }else{
                    $(element).remove();
            		/*element.addClass("rate-btn");
                    element.html("Contact Your Loan Advisor").on('click',function(){
                     //changeLeftPanel(1);
                    	
                    	if(newfiObject.user.userRole.roleCd!="REALTOR"){
                    	
	                        if(typeof(selectedUserDetail)!=='undefined'){
	                            window.location.hash="#loan/"+selectedUserDetail.loanID+"/progress"
	                        }else{
	                            window.location.hash="#myTeam";  
	                        }
                    	}else{
                    		showToastMessage("Please contact your customer");
                    	}
                    });*/
                }
            }
        });
    }
}

function getYearSliderCont(lqbData,appUserDetails) {
   
	var wrapper = $('<div>').attr({
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Length of Loan");
    var silderCont = getYearSlider(lqbData,appUserDetails);
    return wrapper.append(headerTxt).append(silderCont);
}

function getYearSlider(LQBResponse,appUserDetails) {
   
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
                $('#rate-slider-cont').find('.rt-slider').remove();
                var rateSlider = getRatSlider(event.data.ratesArray,appUserDetails,event.data.year);
                $('#rate-slider-cont').append(rateSlider);
                index = parseInt(event.data.ratesArray.length / 2);
                
                $('#lockrateaprid').html(event.data.ratesArray[index].APR +" %");
                $('#lockClosingCost').html(showValue(event.data.ratesArray[index].closingCost));
                $('#lockInterestRate').html(event.data.ratesArray[index].teaserRate+" %");
                $('#principalIntId').html(showValue(event.data.ratesArray[index].payment));
             
                if(event.data.year =='5' || event.data.year =="7")
                    $('#loanprogramId').html(event.data.year +" Year ARM");
                    else
                    $('#loanprogramId').html(event.data.year +" Year Fixed");
                    
                
                
                
                lockratedata.IlpTemplateId =event.data.ratesArray[index].lLpTemplateId;
  				lockratedata.requestedRate = event.data.ratesArray[index].teaserRate;
   				lockratedata.requestedFee = event.data.ratesArray[index].point;
   				var ratVo=event.data.ratesArray[index];
                ratVo.yearData=event.data.year
                updateOnSlide(ratVo);

   				lockRateCalculation(appUserDetails);
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

function getRateSliderCont(LQBResponse,appUserDetails) {
    var wrapper = $('<div>').attr({
        "id": "rate-slider-cont",
        "class": "slider-wrapper clearfix"
    });
    var headerTxt = $('<div>').attr({
        "class": "slider-hdr-txt float-left"
    }).html("Interest Rate");
    
    var yearValues =LQBResponse;
    
   
    var rateArray = yearValues[yearValues.length-1].rateVO;
    var silderCont = getRatSlider(rateArray,appUserDetails,yearValues[yearValues.length-1].value);
    return wrapper.append(headerTxt).append(silderCont);
}

function getRatSlider(gridArray,appUserDetails,yearValue) {
    
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
     
            $('#lockrateaprid').html(gridArray[ui.value].APR +" %");
            $('#lockClosingCost').html(showValue(gridArray[ui.value].closingCost));
            $('#lockInterestRate').html(parseFloat(gridArray[ui.value].teaserRate).toFixed(3)+" %");
            $('#principalIntId').html(showValue(gridArray[ui.value].payment));
            
              lockratedata.IlpTemplateId =gridArray[ui.value].lLpTemplateId;
  			  lockratedata.requestedRate = gridArray[ui.value].teaserRate;
   			  lockratedata.requestedFee = gridArray[ui.value].point;

   			var ratVo=gridArray[ui.value];
            ratVo.yearData=yearValue
            updateOnSlide(ratVo);
   			lockRateCalculation(appUserDetails);
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
}

function lockRateCalculation(appUserDetails){
	
	var taxesTemp = 0;
	var InsuranceTemp = 0;
	var principalInterest = parseFloat(removedDoller(removedComma($('#principalIntId').text())));
	
	if($('#calTaxID2').val() != "") 
	taxesTemp = parseFloat(removedDoller(removedComma($('#calTaxID2').val())));
    
	if($('#CalInsuranceID2').val() != "") 
	InsuranceTemp =  parseFloat(removedDoller(removedComma($('#CalInsuranceID2').val())));
	
	var  monthlyPayment = 0;
	if(appUserDetails.loanType.loanTypeCd =="REF")
      monthlyPayment  = parseFloat(removedDoller(removedComma(appUserDetails.refinancedetails.currentMortgagePayment)));  	
	else
      monthlyPayment  = parseFloat(removedDoller(removedComma(appUserDetails.monthlyRent)));
    
	
    var investment = (InsuranceTemp + taxesTemp);
   
    if(appUserDetails.refinancedetails.includeTaxes ==true){
    	monthlyPayment = monthlyPayment -investment ;
    }

	
	var monthlyPaymentDifference = (Math.abs(principalInterest - monthlyPayment));
	var totalEstMonthlyPaymentId = (principalInterest + investment);
	
	$('#monthlyPaymentId').text(showValue(monthlyPayment));
	$('#monthlyPaymentDifferenceId').text(showValue(monthlyPaymentDifference));
	$('#totalEstMonthlyPaymentId').text(showValue(totalEstMonthlyPaymentId));
	
}







function modifiedLQBJsonResponse(LQBResponse) {
   
	var yearValues = [];
    var loanDurationConform;
    for (var i in LQBResponse) {
   
        loanDurationConform = LQBResponse[i].loanDuration;
        year = loanDurationConform.split(" ")[0];
        temp = {};
        temp.value = year;
        temp.text = year + " - year fixed";
        if (year.indexOf("/") > 0) {
            year = year.split("/")[0];
            temp.value = year;
            temp.text = year + " - year ARM";
        }
        
        temp.rateVO = LQBResponse[i].rateVO;
        yearValues.push(temp);
    }
    yearValues.sort(function(a, b) {
        return parseFloat(a.value) - parseFloat(b.value);
    });
    yearValues=yearValues.slice(1);
    return yearValues;
}



var purchaseTRate;

function getLoanAmountRowPurchase(desc, detail, id,row1Desc,row1Val,row2Desc,row2Val,cashOutCheck,path) {
	
	var flag = false;
	
	purchaseTRate={};
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
        "class": "loan-summary-col-detail float-left clearfix"
    });
    
    var col2Txt = $('<div>').attr({
    	"class" : "float-left",
    	"id" : id
    }).html(detail);
    
    var dropdownarrow = $('<div>').attr({
        "class": "dropdown-arrow float-left"
    }).bind('click', function() {
        $('#loan-amount-details').show();
    });
    
    
    col2.append(col2Txt).append(dropdownarrow);
    
    
    var saveBtn = $('<div>').attr({
    	"class" : "cep-button-color sm-save-btn float-right"
    }).html("Update").on('click',{"path":path,"flag":flag},function(){
    	
    	if(flag){
	    	amt = $('#firstInput').val();
	    	amt1 = $('#secondInput').val();
	      
	    	if(teaserRateValHolder.teaserRate)
	    	  modifiyTeaserRate(amt,amt1);
	    	else
	    	  modifiyLockRateLoanAmt(amt,amt1);
    	}
    	
    });
    
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
    }).html(row1Desc);
    var col2row1 = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left",
        "id":"firstInput"
    }).val(showValue(row1Val))
    .keydown(function() {
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});	
    	
    	flag = true;
    });
    
    row1.append(col1row1).append(col2row1);
    var row2 = $('<div>').attr({
        "class": "loan-summary-sub-row clearfix"
    });
    var col1row2 = $('<div>').attr({
        "class": "loan-summary-sub-col-desc float-left"
    }).html(row2Desc);
    var col2row2 = $('<input>').attr({
        "class": "loan-summary-sub-col-detail float-left loan-summary-sub-col-detail",
        "id":"secondInput"
    }).val(showValue(row2Val))
    .keydown(function() {
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});	
    	
    	flag = true;
    });
    
    var col2 = $('<div>').attr({
    	"class" : "loan-summary-col-detail float-left"
    });
    
    col2.append(col2row2).append(saveBtn);
    
    row2.append(col1row2).append(col2);
    
  
    loanAmountDetails.append(row1).append(row2);
    
    purchaseTRate.purAmtElement=col2row1;
    purchaseTRate.dwnPayElement=col2row2;
    purchaseTRate.LoanAmtElement=col2;
    purchaseTRate.cashOutCheck = cashOutCheck;
    
    purchaseTRate.change=function(){
    	var ob=purchaseTRate;
    	var purAmt=  getFloatValue(ob.purAmtElement.val());
    	var dwnAmt= getFloatValue(ob.dwnPayElement.val());
    	var loanAmt ;
    	if(purchaseTRate.cashOutCheck){
    		loanAmt = (purAmt + dwnAmt);
    	}
    	else{
    		 loanAmt = (purAmt - dwnAmt);
    	}
    	
    	$('#loanAmount').text(showValue(loanAmt));
    };
    col2row1.keyup(purchaseTRate.change);
    col2row2.keyup(purchaseTRate.change);
    return container.append(loanAmountCont).append(loanAmountDetails);
   // return container.append(loanAmountCont);
}


function saveTaxAndInsurance(){
	
    $('#overlay-loader').show();
    
     appUserDetails.propertyTypeMaster.propertyTaxesPaid = $('#calTaxID2').val();
     appUserDetails.propertyTypeMaster.propertyInsuranceCost = $('#CalInsuranceID2').val();
    
    $.ajax({
        url:"rest/application/savetaxandinsurance",
        type:"POST",
        data:{"appFormData" : JSON.stringify(appUserDetails)},
        datatype : "application/json",
        cache:false,
        success:function(data){
            var ob;
            try{
                ob=JSON.parse(data);
            }catch(exception){
                ob={};
                console.log("Invalid Data");
            }         
             $('#overlay-loader').hide();
        },
        error:function(erro){
            showErrorToastMessage(errorInCreateLoan);
             $('#overlay-loader').hide();
        }
        
    });
}
