var stateList = [];
var currentStateId = 0;
var currentZipcodeLookUp = [];
var internalUserStates = new Object();
var states=[];
var internalUserDetailId;
var mobileCarrierConstants=[];
var userRole="Customer";
var loggedInUser ;
var nmlsAdded = false;
//var userStates=[];
$(document).on('click',function(e){
	if($('#add-licence-popup').css("display") == "block"){
		$('#add-licence-popup').hide();
	}

	

});
function showCustomerProfilePage() {
	scrollToTop();
	synchronousAjaxRequest("rest/states/", "GET", "json", "", stateListCallBack);
	synchronousAjaxRequest("rest/userprofile/getMobileCarriers", "GET", "json", "", mobileCarrierList);
	
	
	$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	$('.lp-item').removeClass('lp-item-active');
	$('#lp-customer-profile').addClass('lp-item-active');
	var rightArrow = $('<div>').attr({
		"class" : "lp-right-arrow lp-prof-arrow"
	});
	$('#lp-customer-profile').append(rightArrow);
	var profileMainContainer = $('<div>').attr({
		"id" : "profile-main-container",
		"class" : "right-panel-messageDashboard float-left"
	});
	$('#right-panel').append(profileMainContainer);

	paintCutomerProfileContainer();
	adjustRightPanelOnResize();
	getUserProfileData();
}


/*
 * function getUserProfileData() {
 * ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {},
 * appendCustPersonalInfoWrapper); }
 */

function getUserProfileData() {
	ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {},
			customerPersonalInfoWrapper);
}

//TODO changes for laon manger profile page

function showLoanManagerProfilePage(){
	selectedUserDetail = undefined;
	scrollToTop();
	synchronousAjaxRequest("rest/states/", "GET", "json", "", stateListCallBack);
	synchronousAjaxRequest("rest/userprofile/getMobileCarriers", "GET", "json", "", mobileCarrierList);
	
	$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	$('.lp-item').removeClass('lp-item-active');
	$('#lp-loan-manager-profile').addClass('lp-item-active');
	var rightArrow = $('<div>').attr({
		"class" : "lp-right-arrow lp-prof-arrow"
	}); 
	$('#lp-loan-manager-profile').append(rightArrow);
	var profileMainContainer = $('<div>').attr({
		"id" : "loan-profile-main-container",
		"class" : "right-panel-messageDashboard float-left"
	});
	$('#right-panel').append(profileMainContainer);

	paintLMProfileContainer();
	adjustRightPanelOnResize();
	getUserProfileDataLM();
}

function paintLMProfileContainer() {
	$('#loan-profile-main-container').html('');

}

function getUserProfileDataLM() {
	ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {},
			LoanPersonalInfoWrapper);
}

function LoanPersonalInfoWrapper(user) {

/* 	 var wrapper = $('<div>').attr({
		"class" : "cust-personal-info-wrapper"
	});  */
	loggedInUser = user;
	var wrapper = $('<div>').attr({
		"class" : "loan-personal-info-wrapper"
	});
	//NEXNF-664
/*	var text="";
	
	if(newfiObject.user.userRole.id==2){
		text="My Profile";
	}else{
		text="Personal Information";
	}
	*/
	//NEXNF-711
	var text="My Profile";
	
	var header = $('<div>').attr({
		//included that of customer css
		"class" : "cust-personal-info-header"
	}).html(text);

	
	var text=$('<div>').attr({
		"class" : " cust-profile-url float-right"
	}).html("Share this URL to refer: ");	
	
	var emailInput = $('<input>').attr({
		"class" : "cust-personal-info-header-url loan-detail-link",
		"id" : "profileUrlId",
		"readonly":"readonly",
		"title":"click to copy",
		"value":user.userProfileBaseUrl+user.username
	}).on("click",function(e){
	   
		$(this).zclip({
			path: "resources/js/ZeroClipboard.swf",
			copy: function(e){

					showToastMessage(copiedToClipBoard);
				    return  $(this).val();
			    },
		    clickAfter: false
			});
	});
	if(user.internalUserDetail!=null){
		if(user.internalUserDetail.internalUserRoleMasterVO.roleDescription=="Loan Advisor"){
			  text.append(emailInput);
				header.append(text);
		}
	}	
	if(user.userRole.roleDescription=="Realtor"){
    text.append(emailInput);
	header.append(text);
	}

	var container = getLoanPersonalInfoContainer(user);

	wrapper.append(header).append(container);
	$('#loan-profile-main-container').append(wrapper);
	
	
	var lqbWrapper = $('<div>').attr({
		"class" : "loan-personal-info-wrapper"
	});

	var lqbHeader = $('<div>').attr({
		//included that of customer css
		"class" : "cust-personal-info-header"
	}).html("LQB Information");
	if(!userIsRealtor()){
		if(newfiObject.user.userRole.id!=4){
			var lqbContainer = getLoanLqbInfoContainer(user);
			lqbWrapper.append(lqbHeader).append(lqbContainer);

			$('#loan-profile-main-container').append(lqbWrapper);
		}

	}
		appendChangePasswordContainer(user);

}
function validateBirthDate(dateOfBirth){
	var dat=new Date(dateOfBirth);
    var dateNow=new Date();
    dateNow.setFullYear(dateNow.getFullYear()-18);
    var yearCount=(dateNow.getTime()-dat.getTime());
    if(yearCount<0){
    	return false;
    }else{
    	return true;
    }
}
function appendChangePasswordContainer(userDetails){

	var lqbWrapper = $('<div>').attr({
		"class" : "loan-personal-info-wrapper"
	});

	var lqbHeader = $('<div>').attr({
		//included that of customer css
		"class" : "cust-personal-info-header"
	}).html("Change Password");

	
	
	var lqbContainer = getPasswordInfoContainer(user);
	
	   if(userDetails.emailVerified)
	   {
	   lqbWrapper.append(lqbHeader).append(lqbContainer);
	   }
	   else
	   {
		   
			lqbWrapper.append(lqbHeader).append(appendMessage(newfiObject.user.emailVerfiedMessage));
			
		}

	$('#loan-profile-main-container').append(lqbWrapper);
	if(newfiObject.user.userRole.roleDescription==userRole){
		$('#profile-main-container').append(lqbWrapper);
	}
		
	
	

}
function getPasswordInfoContainer(){

	var container = $('<div>').attr({
		"class" : "loan-personal-info-container"
	});

	var passwordRow = getPasswordRow("New Password","password");
	container.append(passwordRow);


	var newpasswordRow = getPasswordRow("Confirm Password","confirmpassword");
	container.append(newpasswordRow);
	

	
	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn cep-button-color",
		"onclick" : "changePassword()"
	}).html("Update");
	container.append(saveBtn);
	return container;
}

function getLoanLqbInfoContainer(user){
	var container = $('<div>').attr({
		"class" : "loan-personal-info-container"
	});

	var uploadRow = getLqbRow("User Name",user.internalUserDetail.lqbUsername,"lqb_userName");
	container.append(uploadRow);

	internalUserDetailId=user.internalUserDetail.id;
	var password = user.internalUserDetail.lqbPassword;
	var passwordRow = getPasswordRow("Password","lqb_userPassword",password);
	container.append(passwordRow);
	
	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn cep-button-color",
		"onclick" : "updateLqbLMDetails()"
	}).html("Update");
	container.append(saveBtn);
	return container;
}


function getLoanPersonalInfoContainer(user) {

	var container = $('<div>').attr({
		"class" : "loan-personal-info-container"
	});

	var uploadRow = getCustomerUploadPhotoRow(user);
	container.append(uploadRow);
	
	var nameRow = getCustomerNameFormRow(user);
	container.append(nameRow);

//	var DOBRow = getDOBRow(user);
//	container.append(DOBRow);

	var priEmailRow = getPriEmailRow(user);
	container.append(priEmailRow);
	
	//NEXNF-664
	/*var assignManager=getLoanManager(user);
	if(user.userRole.id==2){
		container.append(assignManager);	
	}*/

	var phone1Row = getPhone1RowLM(user);
	container.append(phone1Row);
	
	
	
   // added check box 
	var checkBox=getCheckStatus(user);
	container.append(checkBox);
	var stateRow = getManagerStateRow(user);
	//NEXNF-664
	if(user.userRole.id!=2){
	container.append(stateRow);
	}
	
	if(user.internalUserStateMappingVOs!=undefined){
		userStateMappingVOs=user.internalUserStateMappingVOs;

		states.length = 0;
		internalUserStates.length=0;
		for(var i=0;i<userStateMappingVOs.length;i++) {
				states.push(userStateMappingVOs[i].stateId.toString());
				internalUserStates[userStateMappingVOs[i].stateId]=userStateMappingVOs[i];
	    }
	}
 	
	var licensesRow = getLicensesRow(user);
	//NEXNF-664
	if(user.userRole.id!=2){
		container.append(licensesRow);
	}
	
	if(user.internalUserStateMappingVOs == undefined){
		//licensesRow.addClass('hide');
	}
	
	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn cep-button-color",
		"onclick" : "updateLMDetails()"
	}).html("Save");
	container.append(saveBtn);
	return container;
}

function getLicensesRow(user) {
	
	var row = $('<div>').attr({
		"id" : "licenseRow",
		"class" : "clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	});
	
	var rowCol2 = $('<div>').attr({
		"id" : "licensedStateList",
		"class" : "prof-form-rc float-left"
	});
	
	appendLicensedStates(rowCol2,user);
		
	return row.append(rowCol1).append(rowCol2);
}

function appendLicensedStates(element, user){
	element.html('');
	if(!jQuery.isEmptyObject(internalUserStates)){
		$('#licenseRow').show();
	}
	 if (user.internalUserDetail && user.internalUserDetail.nmlsID != undefined && user.internalUserDetail.nmlsID!= "")
	{
		var nmlsRow = getNMLSDisplayRow(user.internalUserDetail.nmlsID);
		element.append(nmlsRow);
		$('#licenseRow').show();
		if(jQuery.isEmptyObject(internalUserStates)){
			return;
		}
	}
	else if(jQuery.isEmptyObject(internalUserStates)){
		$('#licenseRow').hide();
		return;
	}
	for(var key in internalUserStates){
		if(internalUserStates[key].userId == undefined){
			continue;
		}
		var licenseRow = $('<div>').attr({
			"class" : "license-row clearfix"
		});
		
		var state = $('<div>').attr({
			"class" : "state-val float-left"
		}).text(findStateNameForStateId(internalUserStates[key].stateId));
		state.append(" : ");
		
		var licenseNumber = $('<div>').attr({
			"class" : "license-val float-left"
		}).text(internalUserStates[key].license);
		
		var removeIcn = $('<div>').attr({
			"class" : "message-recipient-remove-icn float-right"
		}).bind('click',{"key":key},function(event){
			var key = event.data.key;
			$(this).closest('.license-row').remove();
			internalUserStates[key].isChecked = false;
			//
			deleteStateLicenseMapping(internalUserStates[key]);
			delete internalUserStates[key];
		});
		
		licenseRow.append(state).append(licenseNumber).append(removeIcn);
		element.append(licenseRow);
		
	}
	
}

function getNMLSDisplayRow (nmlsid)
{
	var licenseRow = $('<div>').attr({
		"class" : "license-row clearfix"
	});
	
	var state = $('<div>').attr({
		"class" : "state-val float-left"
	}).text("NMLS");
	state.append(" : ");
	
	var licenseNumber = $('<div>').attr({
		"class" : "license-val float-left"
	}).text(nmlsid);
	var key = "NMLS";
	var removeIcn = $('<div>').attr({
		"class" : "message-recipient-remove-icn float-right"
	}).bind('click',{"key":key},function(event){
		var key = event.data.key;
		$(this).closest('.license-row').remove();
		if (internalUserStates[key])
		{
			internalUserStates[key].isChecked = false;
		}
		deleteStateLicenseMapping(internalUserStates[key]);
		delete internalUserStates[key];
		saveNMLS("");
		//deleteStateLicenseMapping(internalUserStates[key]);
		//delete internalUserStates[key];
	});
	
	return licenseRow.append(state).append(licenseNumber).append(removeIcn);	
}
function deleteNMLSRow()
{
	
}
function getLoanManager(user){

		
		var row = $('<div>').attr({
			"class" : "prof-form-row clearfix"
		});
		var rowCol1 = $('<div>').attr({
			"class" : "prof-form-row-desc float-left"
		}).html("Assign manager");
		
		
		var rowCol2 = $('<div>').attr({
			"class" : "prof-form-rc float-left"
		});

		var inputCont = $('<div>').attr({
			"class" : "prof-form-input-cont clearfix",
			"style" : "position:relative;"
		});
		
		var emailInput = $('<input>').attr({
			"class" : "prof-form-input float-left add-member-input",
			"value" : user.loanManagerEmail,
			"id" : "managerID",
			"roleID" : user.userRole.id,
			"internalroleid" : "0",
			"showinEditProfile" : "1",
			"userrolebased" : (user.userRole.roleCd == "REALTOR")?"true" : "false"
		}).on(
				'input',
				function() {
					var name = $(this).val();
					console.log("Name entered : " + name);
					var code = $(this).attr("code");
					var roleID = "3";//Since we are looking for loan manager
					if (roleID == undefined
							&& (code != "TITLE_COMPANY" && code != "HOME_OWN_INS")) {
						showToastMessage(selectUserType);
						return false;
					}
					var internalRoleID = "1";//Since we are looking for loan manager
					var isSearchUserRoleBased = $(this)
							.attr("userRoleBased");
					if (isSearchUserRoleBased == "true")
						searchUsersBasedOnNameAndRole(name, roleID,
								internalRoleID,true);
					else if (isSearchUserRoleBased == "false")
						searchUsersBasedOnCode(name, code);
				});
		
		var downArrow = $('<div>')
		.attr({
			"class" : "add-member-down-arrow float-right"
		}).on(
				'click',
				function(e) {
					e.stopPropagation();
					
					if ($('#add-username-dropdown-cont').css("display") == "block") {
						hideUserNameDropDown();
					} else {
						var name = "";
						console.log("Name entered : " + name);
						var code = $("#managerID").attr("code");
						var roleID = "3";//Since we are looking for loan manager
						if (roleID == undefined) {
							showToastMessage(selectUserType);
							return false;
						}
						var internalRoleID = "1";//Since we are looking for loan manager
						var isSearchUserRoleBased = $("#managerID").attr(
								"userRoleBased");
						if (isSearchUserRoleBased == "true")
							searchUsersBasedOnNameAndRole(name, roleID,
									internalRoleID,true);
						else if (isSearchUserRoleBased == "false")
							searchUsersBasedOnCode(name, code);
						
						
						
						
					}
				});
		
		var errMessage = $('<div>').attr({
			"class" : "err-msg hide" 
		});
		
		var dropdownCont = $('<div>').attr({
			"id" : "add-username-dropdown-cont",
			"class" : "add-member-dropdown-cont hide"
		});
		
		inputCont.append(emailInput).append(downArrow).append(dropdownCont);
		
		rowCol2.append(inputCont);
		return row.append(rowCol1).append(rowCol2);
}
function updateLqbLMDetails(){
	var userProfileJson = new Object();
	userProfileJson.id = $("#userid").val();
	var internalUserDetail = new Object();
	internalUserDetail.id = internalUserDetailId;
	internalUserDetail.lqbUsername = $("#lqb_userName").val();
	internalUserDetail.lqbPassword = $("#lqb_userPassword").val();
	
	if(internalUserDetail.lqbPassword  == "" ){
		
		$('#lqb_userPassword').next('.err-msg').html(passwordEmptyMessage).show();
		$('#lqb_userPassword').addClass('ce-err-input').show();
		return false;
	}else{
		$('#lqb_userPassword').next('.err-msg').hide();
		$('#lqb_userPassword').removeClass('ce-err-input');
	}
	userProfileJson.internalUserDetail=internalUserDetail;
	console.info("userProfileJson:"+userProfileJson);
	$('#overlay-loader').show();
	 $.ajax({
			url : "rest/userprofile/updateLqbprofile",
			type : "POST",
			cache:false,
			data : {
				"updateUserInfo" : JSON.stringify(userProfileJson)
			},
			dataType : "json",
			success : function(data) {
				$('#overlay-loader').hide();
				if(data.error!=null){
					showErrorToastMessage(data.error.message);
				}else{
					showToastMessage(updateSuccessMessage);	
				}
				
			},
			error : function(error) {
				$('#overlay-loader').hide();
				showErrorToastMessage(updateErrorMessage);
			}
		});
}

function changePassword(){
	var changePasswordData = new Object();
	changePasswordData.userId = $("#userid").val();
	changePasswordData.newPassword = $("#password").val();
	console.info("userProfileJson:"+JSON.stringify(changePasswordData));
	
	    var passwordField=validateFormFeild($('#password'),$('#password'),passwordFieldEmptyErrorMessage);
	    var confirmPasswordField=validateFormFeild($('#confirmpassword'),$('#confirmpassword'),passwordFieldEmptyErrorMessage);
		if(!passwordField){
			return false;
		}
		if(!confirmPasswordField){
			return false;
		}
		var fistName=newfiObject.user.firstName;
		var lastName=newfiObject.user.lastName;
		var isSuccess=validatePassword($('#password').val(),$('#confirmpassword').val(),fistName,lastName,"password");
		if(isSuccess){
			$('#overlay-loader').show();
			 $.ajax({
					url : "rest/userprofile/password",
					type : "POST",
					
					data : {
						"changePasswordData" : JSON.stringify(changePasswordData)
					},
					dataType : "json",
					cache:false,
					success : function(data) {
						$('#overlay-loader').hide();
						showToastMessage(updateSuccessMessage);
					},
					error : function(error) {
						$('#overlay-loader').hide();
						showErrorToastMessage(updateErrorMessage);
					}
				});
		}
	
}

function validatePassword(password,confirmPassword,firstName,lastName,elementID){
	
	var regex=/(?=.*[a-z])(?=.*[A-Z])/;

	if(password!=confirmPassword){
		$('#password').next('.err-msg').html(passwordDonotMatchErrorMessage).show();
		$('#'+elementID).addClass('ce-err-input').show();
		return false;
	}else{
		if(password.length<8){
			$('#password').next('.err-msg').html(passwordlengthErrorMessage).show();
			$('#'+elementID).addClass('ce-err-input').show();
			return false;
		}
		 if(firstName.length>3){
        if(password.indexOf(firstName) > -1){
        	$('#password').next('.err-msg').html(invalidPassword).show();
        	$('#'+elementID).addClass('ce-err-input').show();
			return false;
		}
	  }
        if(password.indexOf(firstName) == -1){
			var lowercase=password.toLowerCase();
			  if(firstName.length>3){
			if(lowercase.indexOf(firstName) > -1){
				$('#password').next('.err-msg').html(invalidPassword).show();
				$('#'+elementID).addClass('ce-err-input').show();
				return false;
			}
		}
	
		}
		 if(regex.test(password)==false){
				$('#password').next('.err-msg').html(passwordRegexErrorMessage).show();
				$('#'+elementID).addClass('ce-err-input').show();
			return false;
		}
         if(lastName.length>3){
        	 if(password.indexOf(lastName) > -1){
            	 $('#password').next('.err-msg').html(invalidPassword).show();
    				$('#'+elementID).addClass('ce-err-input').show();
    			return false;
    		}
         }
        
         if(password.indexOf(lastName) == -1){		
			var lowercase=password.toLowerCase();
		    if(lastName.length>3){
			if(lowercase.indexOf(lastName) > -1){
				$('#password').next('.err-msg').html(invalidPassword).show();
				$('#'+elementID).addClass('ce-err-input').show();
				return false;
			}
		}
			
			
		}
	}
	return true;
}


function updateLMDetails() {

	var firstname=validateFirstName("firstNameId");
	var lastname=validateLastName("lastNameId");
	if(!firstname || !lastname){
		return false;
	}
	var userProfileJson = new Object();

	userProfileJson.id = $("#userid").val();
	userProfileJson.firstName = $("#firstNameId").val();
	userProfileJson.lastName = $("#lastNameId").val();
	var phoneNumber=$("#priPhoneNumberId").val();
	userProfileJson.phoneNumber = phoneNumber.replace(/[^0-9]/g, '');
	
	userProfileJson.emailId = $("#priEmailId").val();
	if($("#managerID").val()!=undefined){
		userProfileJson.loanManagerEmail=$("#managerID").val();
	 
			if($("#managerID").val()!=""){
	    	var isSuccess=emailValidation($("#managerID").val());
	    	if(isSuccess){
	    		$("#managerID").next('.err-msg').html(invalidEmailErrorMessage).show();
	    		$("#managerID").addClass('err-input').focus();
	    		return ;
	    	}
	    }
	}
	
	if($('.cust-radio-btn-yes').hasClass('radio-btn-selected')){
		userProfileJson.mobileAlertsPreference = true;	
		userProfileJson.carrierInfo=$('#carrierInfoID').val();
	    var phoneStatus=phoneNumberValidation(userProfileJson.phoneNumber,userProfileJson.mobileAlertsPreference ,"priPhoneNumberId");
	    if(phoneStatus!=false){
	    	if(userProfileJson.carrierInfo == ""){
	    		$('#carrierInfoID').next('.err-msg').html(carrierSelectmessage).show();
	    		$('#carrierInfoID').addClass('ce-err-input').show();
	    		return false;
				//showErrorToastMessage(carrierSelectmessage);
				
			}else{
				$('#carrierInfoID').next('.err-msg').hide();
				$('#carrierInfoID').removeClass('ce-err-input');
			
			}
	    	
	    }else{	    		
	    		return false;
				//showErrorToastMessage(carrierSelectmessage);
	    }	
		}else if($('.cust-radio-btn-no').hasClass('radio-btn-selected')){
		   userProfileJson.mobileAlertsPreference = false;
    }
	
	var customerDetails = new Object();

	customerDetails.id = $("#customerDetailsId").val();
	if($("#dateOfBirthId").val()!=undefined){
		customerDetails.dateOfBirth = makeDateFromDatePicker("dateOfBirthId");
	}
	//new Date($("#dateOfBirthId").val()).getTime();


	userProfileJson.customerDetail = customerDetails;
/*	var internalUserState=[];
	//for(var i=0;i<internalUserStates.length;i++){
	for(var key in internalUserStates){
		if(internalUserStates[key]!=0)
			internalUserState.push(internalUserStates[key]);
	}
	userProfileJson.internalUserStateMappingVOs = internalUserState; */
    	showOverlay();
	    $.ajax({
		url : "rest/userprofile/updateprofile",
		type : "POST",
		cache:false,
		data : {
			"updateUserInfo" : JSON.stringify(userProfileJson)
		},
		dataType : "json",
		success : function(data) {
			hideOverlay();
            if(data.error==null){
            	    if(newfiObject.user.id==userProfileJson.id){
            	    	$("#profileNameId").text($("#firstNameId").val());
            	    	//NEXNF-711
                    	/*$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($("#priPhoneNumberId").val()));*/
                    	$("#priPhoneNumberId").mask("(999) 999-9999");
            			showToastMessage(updateSuccessMessage);
            		  	$('#overlay-toast').fadeIn("fast",function(){
            				setTimeout(function(){
            					$('#overlay-toast').fadeOut("fast");
            				},100);
            			    });
            			  	if($('#overlay-toast-error-txt').html()!=""){
            			  	$('#overlay-toast-error-txt').fadeIn("fast",function(){
            					setTimeout(function(){
            						$('#overlay-toast-error-txt').fadeOut("fast");
            					},100);
            				});
            			  	}
            			  	hideOverlay();
            			//showLoanManagerProfilePage();
            			window.location.href = newfiObject.baseUrl+"home.do#loan";
                    }else{
                    	
            	    	showToastMessage(updateSuccessMessage);
            	    }
            	    }else{
            	    	
                    	$("#managerID").val('');
                    	showErrorToastMessage(data.error.message);
                    }
            	    
            					
		},
		error : function(error) {
			hideOverlay();
			showErrorToastMessage(updateErrorMessage);
		}
	});

	}


//end of changes
function userProfileData(data) {

	showCustomerProfilePageCallBack(data);
}

function paintCutomerProfileContainer() {
	$('#profile-main-container').html('');
	// getUserProfileData();
}

function customerPersonalInfoWrapper(user) {

	if($('#profile-main-container >div').length!=0){
		//If the container contains personal info already, dont repeat
		return;
	}
	var wrapper = $('<div>').attr({
		"class" : "loan-personal-info-wrapper"
	});

	//jira-711
    var text="My Profile";
    
	var header = $('<div>').attr({
		"class" : "cust-personal-info-header"
	}).html(text);

	var container = getCustPersonalInfoContainer(user);
	wrapper.append(header).append(container);
	$('#profile-main-container').append(wrapper);

	appendChangePasswordContainer(user);

			restrictSpecialChar("firstName");
			restrictSpecialChar("lastName");
			
}

function getCustPersonalInfoContainer(user) {

	var container = $('<div>').attr({
		"class" : "cust-personal-info-container"
	});
	
	var formWrapper = $('<form>').attr({
		"id" : "profile-form"
	});

	var uploadRow = getCustomerUploadPhotoRow(user);
	formWrapper.append(uploadRow);
	
	var nameRow = getCustomerNameFormRow(user);
	formWrapper.append(nameRow);

	var DOBRow = getDOBRow(user);
	formWrapper.append(DOBRow);

	var priEmailRow = getPriEmailRow(user);
	formWrapper.append(priEmailRow);

	var secEmailRow = getSecEmailRow(user);
	formWrapper.append(secEmailRow);

	/*
	 * var streetAddrRow = getStreetAddrRow(user);
	 * container.append(streetAddrRow);
	 */
	
	var stateRow = getStateRow(user);
	formWrapper.append(stateRow);
	
	var cityRow = getCityRow(user);
	formWrapper.append(cityRow);
	
	var zipRow = getZipRow(user);
	formWrapper.append(zipRow);

	var phone1Row = getPhone1Row(user);
	formWrapper.append(phone1Row);

	var phone2Row = getPhone2Row(user);
	formWrapper.append(phone2Row);
    
	var checkBox=getCheckStatus(user);
	formWrapper.append(checkBox);
				
	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn cep-button-color",
		"onclick" : "updateUserDetails()"
	}).html("Save");
	formWrapper.append(saveBtn);
	
	return container.append(formWrapper);
}


function getCustomerNameFormRow(user) {
    //TODO added for validation 
    var span=$('<span>').attr({
		"class" : "mandatoryClass"
	}).html("*");

	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Name");
	
	rowCol1.append(span);
	
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var firstName = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var firstNameInput = $('<input>').attr({
		"class" : "prof-form-input",
		"placeholder" : "First Name",
		"value" : user.firstName,
		"name":"firstName",
		"id" : "firstNameId"
	}).bind('keypress', function(e) {


	        var k = e.which;
	        var ok = k >= 65 && k <= 90 || // A-Z
	            k >= 97 && k <= 122 || // a-z
	            k >= 48 && k <= 57||// 0-9
	            k==32 ||//to allow space
	    	    k==8 ||//to allow to delte
	    	    k==46;//to allow backspace

	        if (!ok){
	            e.preventDefault();
	        }
	
	});

	var firstNameErrMessage = $('<div>').attr({
		"class" : "err-msg hide"
	});
	
	firstName.append(firstNameInput).append(firstNameErrMessage);
	
	var lastName = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	
	var lastNameInputCont = $('<input>').attr({
		"class" : "prof-form-input",
		"placeholder" : "Last Name",
		"value" : user.lastName,
		"name":"lastName",
		"id" : "lastNameId"
	}).bind('keypress', function(e) {

	  
	        var k = e.which;
	        var ok = k >= 65 && k <= 90 || // A-Z
	            k >= 97 && k <= 122 || // a-z
	            k >= 48 && k <= 57||// 0-9
	            k==32 ||//to allow space
	    	    k==8 ||//to allow to delte
	    	    k==46;//to allow backspace

	        if (!ok){
	            e.preventDefault();
	        }

	});
	
	var lastNameErrMessage = $('<div>').attr({
		"class" : "err-msg hide"
	});

	lastName.append(lastNameInputCont).append(lastNameErrMessage);
	
	var id = $('<input>').attr({
		"type" : "hidden",
		"value" : user.id,
		"id" : "userid"
	});

	var customerDetailsId = $('<input>').attr({
		"type" : "hidden",
		"value" : user.customerDetail.id,
		"id" : "customerDetailsId"
	});

	rowCol2.append(firstName).append(lastName).append(id).append(
			customerDetailsId);
	return row.append(rowCol1).append(rowCol2);
}

function getCustomerUploadPhotoRow(user) {

	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc upload-pic-desc float-left"
	}).html("Upload Photo");

	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var uploadPicPlaceholder;
	var initialText = user.firstName.charAt(0) + " " + user.lastName.charAt(0);
	if (user.photoImageUrl == "" || user.photoImageUrl == null
			|| user.photoImageUrl == 'undefined') {
		uploadPicPlaceholder = $('<div>').attr({
			"id" : "profilePic",
			"class" : "upload-prof-pic-icn float-left"			
		}).click(uploadeImage);

	} else {
		uploadPicPlaceholder = $('<div>').attr({
			"id" : "profilePic",
			"class" : "prof-pic-upload-placeholder float-left",
			"style" : "background-image:url(" + user.photoImageUrl + ")"
		}).click(uploadeImage);
	}

	
	var uploadIcn = $('<div>').attr({
		"class" : "upload-prof-pic-icn"
	}).click(uploadeImage);
	
	//uploadPicPlaceholder.append(uploadIcn);
	
	var uploadBottomContianer = $('<div>').attr({
		"class" : "clearfix"
	});

	var inputHiddenFile = $('<input>').attr({
		"type" : "file",
		"id" : "prof-image",
		"name" : user.id,
		"value" :"Upload"
	});

	var inputHiddenDiv = $('<div>').attr({
		"style" : "display:none"

	});

	inputHiddenDiv.append(inputHiddenFile);

	/*var uploadBtn = $('<div>').attr({
		"class" : "prof-btn upload-btn float-left"
	}).click(uploadeImage).html("upload");*/

	uploadBottomContianer.append(inputHiddenDiv);

	rowCol2.append(uploadPicPlaceholder).append(uploadBottomContianer);

	return row.append(rowCol1).append(rowCol2);
}

function uploadeImage() {

	$("#prof-image").trigger('click');
	$(".overlay-container").css("display","block");

}

/*
 * function getCustomerUploadPhotoRow(user) {
 * 
 * //$('#loading').html('<img
 * src="http://preloaders.net/preloaders/287/Filling%20broken%20ring.gif">
 * loading...'); var row = $('<div>').attr({ "class" : "prof-form-row clearfix"
 * }); var rowCol1 = $('<div>').attr({ "class" : "prof-form-row-desc
 * float-left" }).html("Upload Photo");
 * 
 * var rowCol2 = $('<div>').attr({ "class" : "prof-form-rc float-left" });
 * 
 * var uploadPicPlaceholder ; if(user.photoImageUrl == "" || user.photoImageUrl ==
 * null || user.photoImageUrl =='undefined'){ uploadPicPlaceholder = $('<div>').attr({
 * "id" : "profilePic", "class" : "prof-pic-upload-placeholder float-left" });
 * 
 * }else{ uploadPicPlaceholder = $('<div>').attr({ "id" : "profilePic", "class"
 * :"prof-pic-upload-placeholder float-left",
 * "style":"background:url("+user.photoImageUrl+")" }); }
 * 
 * var imageForm = $('<form>').attr({ "id" : "fullImageFormId" });
 * 
 * var imageTage = $('<img>').attr({ "id" : "target", "style": "display:none",
 * });
 * 
 * var canvasTage = $('<canvas>').attr({ "id" : "canvasid", "style":
 * "display:none", });
 * 
 * 
 * var inputHiddenFile = $('<input>').attr({ "type" : "file", "id" :
 * "prof-image", "name":"fileName"
 * 
 * });
 * 
 * var uploadcontinue = $('<input>').attr({ "type" : "button", "id" :
 * "uploadcontinue", "value":"upload"
 * 
 * });
 * 
 * imageForm.append(inputHiddenFile).append(imageTage).append(uploadcontinue);
 * 
 * var outerDiv = $('<div>').attr({ "id" : "outer", });
 * 
 * var jcExampleDiv = $('<div>').attr({ "class" : "jcExample", });
 * 
 * var articleDiv = $('<div>').attr({ "id" : "article", });
 * 
 * 
 * articleDiv.append(imageTage); jcExampleDiv.append(articleDiv);
 * outerDiv.append(jcExampleDiv);
 * 
 * 
 * 
 * var uploadBottomContianer = $('<div>').attr({ "class" : "clearfix" });
 * 
 * var uploadBtn = $('<div>').attr({ "class" : "prof-btn upload-btn float-left"
 * 
 * }).click(uploadeImage).html("upload"); //
 * uploadBottomContianer.append(uploadPicCont).append(uploadBtn);
 * 
 * uploadBottomContianer.append(uploadBtn).append(imageForm).append(canvasTage).append(outerDiv);
 * rowCol2.append(uploadPicPlaceholder).append(uploadBottomContianer);
 * 
 * 
 * 
 * return row.append(rowCol1).append(rowCol2); }
 */

function getDOBRow(user) {

	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Date of Birth");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var dob =null;
	if(user.customerDetail.dateOfBirth != 0)
		{
		dob = makeDateFromLong(user.customerDetail.dateOfBirth);
		}
	if (dob == null || dob == "" || dob == 'NaN/NaN/NaN') {

		dob = "";
	}
	
	var dobCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var dobInput = $('<input>').attr({
		"class" : "prof-form-input date-picker",
		"placeholder" : "MM/DD/YYYY",
		"value" : dob,
		"id" : "dateOfBirthId",
		
	}).datepicker({
				orientation : "top auto",
				autoclose : true,
				maxDate: new Date() ,
				changeMonth: true,
				changeYear: true

		
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	dobCont.append(dobInput).append(errMessage);
	
	rowCol2.append(dobCont);
	return row.append(rowCol1).append(rowCol2);
}

function getPriEmailRow(user) {
    //TODO added for validation
    var span=$('<span>').attr({	
		"class" : "mandatoryClass"
	}).html("*");
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Primary Email");
	
	rowCol1.append(span);
	
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var primaryEmailCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input",
		"value" : user.emailId,
		"id" : "priEmailId",
		"readonly":true,
		"onblur" : "emailValidation(this.value)"
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	primaryEmailCont.append(emailInput).append(errMessage);
	
	//TODO for email registration validation link
	var primaryEmailLinkCont = $('<div>').attr({
		"class" : "float-left"
	});
	var emailInput="";

	if(!user.emailVerified){
		 emailInput = $('<a>').attr({
			"class" : "prof-form-input-lg prof-form-verfication-link",
			"id" : "priEmailVerificationLinkId",
			"readonly":true,
			"href":"#",
			"onclick" : "forgetPassword()"
		}).html("Resend your verification email");
		
	}else{
		 emailInput = $('<a>').attr({
			"class" : "prof-form-input-lg prof-form-verfied-mess",
			"id" : "priEmailVerificationLinkId",
			"readonly":true,
		}).html("Verified");
		
	}
	primaryEmailLinkCont.append(emailInput);
	rowCol2.append(primaryEmailCont).append(primaryEmailLinkCont);
	return row.append(rowCol1).append(rowCol2);
}
function forgetPassword(){
	var user=new Object();
	user.emailId=$('#priEmailId').val();
	ajaxRequest("rest/userprofile/forgetPassword"+"?resend=true", "POST", "json", JSON.stringify(user),
			  showSuccessMessage);
}
function showSuccessMessage(data){
	if(data.error==null){
		showToastMessage(data.resultObject);
	}else{
		showErrorToastMessage(data.error.message);
	}
}
function emailValidation(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!regex.test(email)) {
		//showErrorToastMessage("Incorrect Email");
		validationFails = true;
		return true;
	}
	return false;
}


function getSecEmailRow(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Secondary Email");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	//removal of  prof-form-input-lg as per jira-711
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input",
		"value" : user.customerDetail.secEmailId,
		"id" : "secEmailId"
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(emailInput).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

/*
 * function getStreetAddrRow() { var row = $('<div>').attr({ "class" :
 * "prof-form-row clearfix" }); var rowCol1 = $('<div>').attr({ "class" :
 * "prof-form-row-desc float-left" }).html("Street Address"); var rowCol2 = $('<div>').attr({
 * "class" : "prof-form-rc float-left" }); var steetAddrInput = $('<input>').attr({
 * "class" : "prof-form-input prof-form-input-lg",
 * "value":user.customerDetail.secEmailId }); rowCol2.append(steetAddrInput);
 * return row.append(rowCol1).append(rowCol2); }
 */

var a=["hello","hi","hello"];

var unique=a.filter(function(itm,i,a){
    return i==a.indexOf(itm);
});

function getCityRow(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("City");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var cityInput = $('<input>').attr({
		"class" : "prof-form-input",
		"value" : user.customerDetail.addressCity,
		"id" : "cityId"
	}).bind('keydown',function(){
		
		var searchData = [];
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			searchData[i] = currentZipcodeLookUp[i].cityName;
		}
		
		var uniqueSearchData = searchData.filter(function(itm,i,a){
		    return i==a.indexOf(itm);
		});
		
		initializeCityLookup(uniqueSearchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(cityInput).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

function initializeCityLookup(searchData){
	$('#cityId').autocomplete({
		minLength : 0,
		source : searchData,
		focus : function(event, ui) {
			/*$("#cityId").val(ui.item.label);
			return false;*/
			event.stopPropagation();
		},
		select : function(event, ui) {
			/*$("#cityId").val(ui.item.label);
			return false;*/
			event.stopPropagation();
		},
		open : function() {
			$('.ui-autocomplete').perfectScrollbar({
				suppressScrollX : true
			});
			$('.ui-autocomplete').perfectScrollbar('update');
		}
	});/*.autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li>").append(item.label).appendTo(ul);
	}*/
	
}

function initializeCityLookupForProperty(searchData){

		$('#cityID').autocomplete({
			minLength : 0,
			source : searchData,
			focus : function(event, ui) {
				/*$("#cityId").val(ui.item.label);
				return false;*/
				event.stopPropagation();
			},
			select : function(event, ui) {
				/*$("#cityId").val(ui.item.label);
				return false;*/
				event.stopPropagation();
			},
			open : function() {
				$('.ui-autocomplete').perfectScrollbar({
					suppressScrollX : true
				});
				$('.ui-autocomplete').perfectScrollbar('update');
			}
		});

}

function getStateRow(user) {
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("State");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var stateInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-sm prof-form-input-select uppercase",
		"id" : "stateId"
	}).bind('click',function(e){
		e.stopPropagation();
		if($('#state-dropdown-wrapper').css("display") == "none"){
			appendStateDropDown('state-dropdown-wrapper',stateList);
			toggleStateDropDown();
		}else{
			toggleStateDropDown();
		}
	}).bind('keyup',function(e){
		var searchTerm = "";
		if(!$(this).val()){
			return false;
		}
		searchTerm = $(this).val().trim();
		var searchList = searchInStateArray(searchTerm);
		appendStateDropDown('state-dropdown-wrapper',searchList);
	});
	
	if(user.customerDetail.addressState){
		stateInput.val(user.customerDetail.addressState);
		var stateCode = user.customerDetail.addressState.toUpperCase();
		
		var stateId = findStateIdForStateCode(stateCode);
		ajaxRequest("rest/states/"+stateId+"/zipCode", "GET", "json", "", zipCodeLookUpListCallBack);
	}
	
	var dropDownWrapper = $('<div>').attr({
		"id" : "state-dropdown-wrapper",
		"class" : "state-dropdown-wrapper hide"
	});
	
	rowCol2.append(stateInput).append(dropDownWrapper);
	return row.append(rowCol1).append(rowCol2);
}


function getManagerStateRow() {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Licensed States");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left clearfix"
	});
	
	var addLicence = $('<span>').attr({
		"class" : "link-pointer"
	}).text("Add License")
	.bind('click',function(e){
		if(currentSelectedUserID!=newfiObject){
			//$('.adminUM-user-edit-profile-container').css("height","762px");
			$('.ms-add-member-popup.add-licence-popup ').css({
				"right":"-497px",
				"left":"auto"
			});
		}
		e.stopImmediatePropagation();
		if($('#add-licence-popup').length==1){
			
			if($('#add-licence-popup').css("display")=="block"){
				$('#add-licence-popup').hide();
			}else{
				$('#add-licence-popup').show();
			}
			}else{
				appendAddLicencePopup(this);
				if(currentSelectedUserID!=newfiObject){
				//	$('.adminUM-user-edit-profile-container').css("height","762px");
					$('.ms-add-member-popup.add-licence-popup ').css({
						"right":"-497px",
						"left":"auto"
					});
				}
			}
		
	});
	
	rowCol2.append(addLicence);
	return row.append(rowCol1).append(rowCol2);
}

function removeAddLicencePopup() {
	$('#add-licence-popup').remove();	
}

function appendAddLicencePopup(element) {
	
	removeAddLicencePopup();
	
	var wrapper = $('<div>').attr({
		"id" : "add-licence-popup",
		"class" : "ms-add-member-popup add-licence-popup"
	}).click(function(e) {
		e.stopPropagation();
	});
	var header = $('<div>').attr({
		"class" : "popup-header"
	}).html("Add a License");
	
	var container = $('<div>').attr({
		"class" : "popup-container"
	});
	
	var row = $('<div>').attr({
		"class" : "clearfix"
	});
	
	var col1 = $('<div>').attr({
		"class" : "add-member-input-cont float-left clearfix"
	});
	
	var col1Label = $('<div>').attr({
		"class" : "add-member-label float-left"
	}).text("State");
	
	var stateWrapper = $('<div>').attr({
		"class" : "state-wrapper float-left"
	});
	
	var stateInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-statedropdown prof-form-input-select uppercase",
		"id" : "stateId"
	}).bind('click',function(e){
		e.stopPropagation();
			if($('#state-dropdown-wrapper').css("display") == "none"){
				appendManagerStateDropDown('state-dropdown-wrapper',stateList);
				toggleStateDropDown();
			}else{
				toggleStateDropDown();
			}
	}).bind('keyup',function(e){
		var searchTerm = "";
		if(!$(this).val()){
			return false;
		}
		searchTerm = $(this).val().trim();
		var searchList = searchInStateArray(searchTerm);
		appendManagerStateDropDown('state-dropdown-wrapper',searchList);
	});
	
	
	
	var dropDownWrapper = $('<div>').attr({
		"id" : "state-dropdown-wrapper",
		"class" : "state-dropdown-wrapper hide"
	});
	
	stateWrapper.append(stateInput).append(dropDownWrapper);
	
	col1.append(col1Label).append(stateWrapper);
	
	var col2 = $('<div>').attr({
		"class" : "add-member-input-cont float-left clearfix"
	});
	
	var col2Label = $('<div>').attr({
		"class" : "add-member-label float-left"
	}).text("License No");
	
	var col2Input = $('<input>').attr({
		"id" : "licenseId",
		"class" : "create-user-popup-input licence-input float-left"
	});
	
	col2.append(col2Label).append(col2Input);
	
	row.append(col1).append(col2);
	
	var updateBtn = $('<div>').attr({
		"id" : "save-license-btn",
		"class" : "prof-btn prof-save-btn"
	}).text("Save")
	.bind('click',function(e){
		e.stopPropagation();
		showOverlay();
		if ($(this).attr("state-id") == "NMLS")
		{
			saveNMLS($('#licenseId').val());
			return;
		}
		var licenseVal = $('#licenseId').val();
		
		if(licenseVal == undefined || licenseVal == ""){
			return false;
		}
		
		var internalUserStateMappingVO = {};
		var isNew = true;
		if(internalUserStates[$(this).attr("state-id")]){
			isNew = true;
			internalUserStateMappingVO = internalUserStates[$(this).attr("state-id")];
		}
		
		internalUserStateMappingVO.userId=$("#userid").val();
		internalUserStateMappingVO.stateId=$(this).attr("state-id");
		internalUserStateMappingVO.license=licenseVal;
		
		//if(isNew)
			//internalUserStates[$(this).attr("state-id")] = internalUserStateMappingVO;
		
		removeAddLicencePopup();
		saveInternalUserStatesAndLicense(internalUserStateMappingVO);
		
		appendLicensedStates($('#licensedStateList'),loggedInUser);
		hideOverlay();
	});
	
	
	container.append(row).append(updateBtn);
	
	wrapper.append(header).append(container).append(updateBtn);
	
	$(element).append(wrapper);
}

function saveNMLS(nmlsID)
{
		var userProfileJson = new Object();
		userProfileJson.id = $("#userid").val();
		var internalUserDetail = new Object();
		internalUserDetail.id = internalUserDetailId;
		internalUserDetail.nmlsID =nmlsID;
		userProfileJson.internalUserDetail=internalUserDetail;
		console.info("userProfileJson:"+userProfileJson);
		$('#overlay-loader').show();
		 $.ajax({
				url : "rest/userprofile/nmls",
				type : "POST",
				cache:false,
				data : {
					"updateUserInfo" : JSON.stringify(userProfileJson)
				},
				dataType : "json",
				success : function(data) {
					$('#overlay-loader').hide();					
					removeAddLicencePopup();
					var internalUserStateMappingVO = {};
					var isNew = true;
					if(internalUserStates[$(this).attr("state-id")]){
						isNew = true;
						internalUserStateMappingVO = internalUserStates[$(this).attr("state-id")];
					}
					
					internalUserStateMappingVO.userId=$("#userid").val();
					internalUserStateMappingVO.stateId=$(this).attr("state-id");
					internalUserStateMappingVO.license=nmlsID;
					loggedInUser.internalUserDetail.nmlsID = nmlsID;
					appendLicensedStates($('#licensedStateList'), loggedInUser);					
					showToastMessage(updateSuccessMessage);
				},
				error : function(error) {
					$('#overlay-loader').hide();
					showErrorToastMessage(updateErrorMessage);
				}
			});	
}
function zipCodeLookUpListCallBack(response) {
	if(response.error == null){
		currentZipcodeLookUp = response.resultObject;
	}
}

function appendStateDropDown(elementToApeendTo,states) {

	var parentToAppendTo = $('#'+elementToApeendTo);
	parentToAppendTo.html('');
	
	for(var i=0; i<states.length; i++){
		var stateRow = $('<div>').attr({
			"class" : "state-dropdown-row"
		}).html(states[i].stateCode)
		.bind('click',function(e){
			e.stopPropagation();
			$('#stateId').val($(this).html());
			currentZipcodeLookUp = [];
			$('#cityId').val('');
			$('#zipcodeId').val('');
			var stateCode = $(this).html();
			
			var stateId = findStateIdForStateCode(stateCode);
			toggleStateDropDown();
			synchronousAjaxRequest("rest/states/"+stateId+"/zipCode", "GET", "json", "", zipCodeLookUpListCallBack);
		});
		
		parentToAppendTo.append(stateRow);
	}
}
function appendStateDropDownForProperty(elementToApeendTo,states){
	var parentToAppendTo = $('#'+elementToApeendTo);
	parentToAppendTo.html('');

	for(var i=0; i<states.length; i++){
		var stateRow = $('<div>').attr({
			"class" : "state-dropdown-row"
		}).html(states[i].stateCode)
		.bind('click',function(e){
			e.stopPropagation();
			$('#stateID').val($(this).html());
			currentZipcodeLookUp = [];
			$('#cityID').val('');
			$('#zipcodeID').val('');
			var stateCode = $(this).html();
			
			var stateId = findStateIdForStateCode(stateCode);
			$('#state-dropdown-wrapper-property').slideToggle("slow",function(){
				$('#state-dropdown-wrapper-property').perfectScrollbar({
					suppressScrollX : true
				});
				$('#state-dropdown-wrapper-property').perfectScrollbar('update');		
			});
			synchronousAjaxRequest("rest/states/"+stateId+"/zipCode", "GET", "json", "", zipCodeLookUpListCallBack);
		});
		
		parentToAppendTo.append(stateRow);
}
}


function appendManagerStateDropDown(elementToApeendTo,stateList) {

	var parentToAppendTo = $('#'+elementToApeendTo);
	parentToAppendTo.html('');
	var $containerToAppend=$("<div>");
	
	if (!userIsRealtor()
			&& newfiObject.user.userRole.id!=4 && !nmlsAdded)
	{			
		var nmlsElement = new Object();
		nmlsElement.id="NMLS";
		nmlsElement.stateCode="NMLS";
		stateList.splice(0, 0, nmlsElement);
		stateList.join();
		nmlsAdded=true;
	}
	for(var i=0; i<stateList.length; i++){
		console.log(i);
		var stateRow = $('<div>').attr({
			"class" : "state-dropdown-row agent-state-dropdown-row clearfix",
			"id" : stateList[i].id,
			"name":  stateList[i].stateCode
		});
		var checkBox = $('<div>').attr({
				"class" : "float-left doc-checkbox hide",
				"id": "checkBox_"+stateList[i].id,
		});
		if(states.indexOf((stateList[i].id).toString())>-1){
			//$(checkBox).attr('checked',true);
			$(checkBox).addClass('doc-checked');
		}else{
			$(checkBox).addClass('doc-unchecked');
		}
		var textRow = $('<div>').attr({
			"class" : "float-left state-row",
			"id" : "stateName_"+stateList[i].id
		}).html(stateList[i].stateCode);
		stateRow.append(checkBox).append(textRow);
		stateRow.bind('click',function(e){
			e.stopPropagation();
			$('#stateId').val($(this).text());
			$('#save-license-btn').attr("state-id",this.id);			
			appendUserStatesInLMProfile($('#inputTextarea'));				
				//$('#stateId').val(this.name);
				toggleStateDropDown();
			});
		if(states.indexOf((stateList[i].id).toString())>-1){
			stateRow.prependTo($containerToAppend);
		}
		else{ 
			$containerToAppend.append(stateRow);
		}
		
	}
	parentToAppendTo.append($containerToAppend);
	
}


function findStateIdForStateCode(stateCode) {

	for(var i=0; i<stateList.length; i++){
		if(stateList[i].stateCode == stateCode){
			return stateList[i].id;
		}
	}
	
	return 0;
}

function findStateNameForStateId(stateId) {
	
	for(var i=0; i<stateList.length; i++){
		if(stateList[i].id == stateId){
			return stateList[i].stateCode;
		}
	}
	return 0;
}

function searchInStateArray(searchTerm,searchList){
	//searchTerm = searchTerm.toUpperCase();
	var regEx = new RegExp(searchTerm, 'i');
	var searchedStateList = [];
	if(!searchList)
		searchList=stateList;
	count = 0;
	for(var i=0; i<searchList.length;i++){
		var result = searchList[i].stateCode.match(regEx);
		if(result){
			searchedStateList[count++] = searchList[i];			
		}
	}
	
	return searchedStateList;
}

$(document).on('click',function(e){
	if($('#state-dropdown-wrapper').css("display") == "block"){
		toggleStateDropDown();
	};
});

function toggleStateDropDown() {
		$('#state-dropdown-wrapper').slideToggle("slow",function(e){
			$('#state-dropdown-wrapper').perfectScrollbar({
				suppressScrollX : true
			});
			$('#state-dropdown-wrapper').perfectScrollbar('update');		
		});
}

function toggleCarrierDropDown() {
	$('#carrier-dropdown-wrapper').slideToggle("slow",function(){
		$('#carrier-dropdown-wrapper').perfectScrollbar({
			suppressScrollX : true
		});
		$('#carrier-dropdown-wrapper').perfectScrollbar('update');		
	});
}
function stateListCallBack(response) {
	if(response.error == null){
		stateList = response.resultObject;
	}
}
var allowedStateArray=["CA", "OR" , "WA"];
function filterAllowedStates(){
	var filteredList=[];
	for(var i=0;i<stateList.length;i++){
		if(allowedStateArray.indexOf(stateList[i].stateCode)>=0){
			filteredList.push(stateList[i]);
		}
	}
	return filteredList;
}
function mobileCarrierList(response){
	if(response.error == null){
		mobileCarrierConstants = response.resultObject;
	}
}

function appendCarrierNames(parentElement,carrierList){
	var parentToAppendTo = $('#'+parentElement);
	parentToAppendTo.html('');
	for(var i=0; i<carrierList.length; i++){
		var carrierRow = $('<div>').attr({
			"class" : "carrier-dropdown-row"
		}).html(carrierList[i])
		.bind('click',function(e){
			e.stopPropagation();
			$('#carrierInfoID').val($(this).text());
			toggleCarrierDropDown();
			
		});
		
		parentToAppendTo.append(carrierRow);
	}
	
	
}
function getZipRow(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Zip");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var zipInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-sm",
		"value" : user.customerDetail.addressZipCode,
		"id" : "zipcodeId"
	}).bind('keydown',function(){
		
		var selectedCity = $('#cityId').val();
		var searchData = [];
		var count = 0;
		for(var i=0; i<currentZipcodeLookUp.length; i++){
			if(selectedCity == currentZipcodeLookUp[i].cityName){
				searchData[count++] = currentZipcodeLookUp[i].zipcode;				
			}
		}
		initializeZipcodeLookup(searchData);
	}).bind('focus', function(){ 
		$(this).trigger('keydown');
		$(this).autocomplete("search"); 
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(zipInput).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

function initializeZipcodeLookup(searchData){
	$('#zipcodeId').autocomplete({
		minLength : 0,
		source : searchData,
		focus : function(event, ui) {
			/*$("#zipcodeId").val(ui.item.label);
			return false;*/
			event.stopPropagation();
		},
		select : function(event, ui) {
			/*$("#zipcodeId").val(ui.item.label);
			return false;*/
			event.stopPropagation();
		},
		open : function() {
			$('.ui-autocomplete').perfectScrollbar({
				suppressScrollX : true
			});
			$('.ui-autocomplete').perfectScrollbar('update');
		}
	});/*.autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li>").append(item.label).appendTo(ul);
	}*/

	

}
function initializeZipcodeLookupForProperty(searchData){
	$('#zipcodeID').autocomplete({
		minLength : 0,
		source : searchData,
		focus : function(event, ui) {
			/*$("#zipcodeId").val(ui.item.label);
			return false;*/
			event.stopPropagation();
		},
		select : function(event, ui) {
			/*$("#zipcodeId").val(ui.item.label);
			return false;*/
			event.stopPropagation();
		},
		open : function() {
			$('.ui-autocomplete').perfectScrollbar({
				suppressScrollX : true
			});
			$('.ui-autocomplete').perfectScrollbar('update');
		}
	});
}
function getPhone1Row(user) {
	var phoneNumber=user.phoneNumber;
	var span=$('<span>').attr({
		"class" : "mandatoryClass"
	}).html("*");
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Primary Phone");
	if(user.customerDetail.mobileAlertsPreference){
	rowCol1.append(span);
	}
	
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var phone1Input = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-m",
		"value":phoneNumber,
		"id" : "priPhoneNumberId",
	    "data-mask":"(999) 999-9999"
	});
	phone1Input.mask("(999) 999-9999");
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(phone1Input).append(errMessage);
	var carrierInfo=getCarrierDropdown(user);
	
	rowCol2.append(inputCont).append(carrierInfo);
	return row.append(rowCol1).append(rowCol2);
}

//TODO added for validation LM

function getPhone1RowLM(user) {
	var phoneNumber=user.phoneNumber;
    var span=$('<span>').attr({
		"class" : "mandatoryClass"
	}).html("*");
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Primary Phone");
	rowCol1.append(span);
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var phone1Input = $('<input>').attr({
		"class" : "prof-form-input",
		"value":phoneNumber,
		"id" : "priPhoneNumberId",
		"data-mask":"(999) 999-9999"
	}).on('ready',function(){	
		
	});
	phone1Input.mask("(999) 999-9999");
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(phone1Input).append(errMessage);
	var carrierInfo=getCarrierDropdown(user);
	
	rowCol2.append(inputCont).append(carrierInfo);
	return row.append(rowCol1).append(rowCol2);
}



//END of changes
function getPhone2Row(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Secondary Phone");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var phone2Input = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-m",
		"value" : user.customerDetail.secPhoneNumber,
		"id" : "secPhoneNumberId",
		"data-mask":"(999) 999-9999"
	});
	phone2Input.mask("(999) 999-9999");
	
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(phone2Input).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

 function getCheckStatus(user){
var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});

	var text="Receive Text Alert";
	
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html(text);
	
	var rowColtext = $('<div>').attr({
		"class" : "cust-sms-ch float-left"
	}).html("Yes");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var rowColtext2 = $('<div>').attr({
		"class" : "cust-sms-ch float-left"
	}).html("No");
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var radioYesButton = $('<div>').attr({
		"class" : "cust-radio-btn-yes radio-btn float-left",		
		"id" : "alertSMSPreferenceIDYes",		
		

	}).bind('click',function(e){
			$('.cust-radio-btn-no').removeClass('radio-btn-selected');
			$(this).addClass('radio-btn-selected');			
			//validatePhone('priPhoneNumberId');
	        $('#prof-form-row-custom-email').show();
	});
	
	var radioNoButton = $('<div>').attr({
		"class" : "cust-radio-btn-no radio-btn float-left",		
		"id" : "alertSMSPreferenceIDNo"
	}).bind('click',function(e){
		
			$('.cust-radio-btn-yes').removeClass('radio-btn-selected');
			$(this).addClass('radio-btn-selected');
			$('#prof-form-row-custom-email').hide();
	});
	
	if(user.mobileAlertsPreference!=null){
		if(user.mobileAlertsPreference){
			radioYesButton.addClass('radio-btn-selected');
		}else if(!user.mobileAlertsPreference){
			radioNoButton.addClass('radio-btn-selected');
		}}

	
	inputCont.append(radioYesButton).append(rowColtext).append(radioNoButton).append(rowColtext2);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);


}
function getCarrierDropdown(user){

    var carrierName = getCarrierName(user.carrierInfo);
	var row = $('<div>').attr({
		"class" : "prof-form-row-carrier clearfix hide",
		"id":"prof-form-row-custom-email"
	});

	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
			
	var carrierinfo = $('<input>').attr({
		"class" : "prof-form-input-carrier prof-form-input-carrierDropdown prof-form-input-select",
		"value" : carrierName,
		"placeholder":"Select Carrier",
		"id" : "carrierInfoID"
	}).bind('click',function(e){
		e.stopPropagation();
		if($('#carrier-dropdown-wrapper').css("display") == "none"){
			appendCarrierNames('carrier-dropdown-wrapper',mobileCarrierConstants);
			toggleCarrierDropDown();
		}else{
			toggleCarrierDropDown();
		}
	});
	

	if(user.mobileAlertsPreference){
		row.removeClass('hide');
		
	}
	
	var dropDownWrapper = $('<div>').attr({
		"id" : "carrier-dropdown-wrapper",
		"class" : "carrier-dropdown-wrapper hide"
	});
	
	rowCol2.append(carrierinfo).append(appendErrorMessage()).append(dropDownWrapper);
	return row.append(rowCol2);	
}
/**
 * Functions related to form validations
 */

var isProfileFormValid = true;
$(document).on('blur','#firstNameId',function(){
	if(!validateFormField('firstNameId', true, firstNameEmptyMessage, "","")){
		isProfileFormValid = false;
	}
});


$(document).on('blur','#lastNameId',function(){
	if(!validateFormField('lastNameId', true, lastNameEmptyMessage, "","")){
		isProfileFormValid = false;
	}
});

$(document).on('blur','#secEmailId',function(){
	if(!validateFormField('secEmailId', false, "", invalidEmailErrorMessage, emailRegex)){
		isProfileFormValid = false;
	}
});

/*$(document).on('blur','#zipcodeId',function(){
	if(!validateFormField("zipcodeId", false, "", invalidZipCodeMessage, zipcodeRegex)){
		isProfileFormValid = false;
	}
});*/

$(document).on('blur','#priPhoneNumberId',function(){
	if(!validateFormField("priPhoneNumberId", false, "", invalidPhoneNumberMessage, phoneRegex)){
		isProfileFormValid = false;
	}
});

$(document).on('blur','#secPhoneNumberId',function(){
	if(!validateFormField("secPhoneNumberId", false, "",invalidPhoneNumberMessage, phoneRegex)){
		isProfileFormValid = false;
	}
});


	
		$(document).on('blur',"#carrierInfoID",function(){
			if($("#carrierInfoID").val()!=""||$("#carrierInfoID").val()!=null){
			$("#carrierInfoID").next('.err-msg').hide();
			$("#carrierInfoID").removeClass('ce-err-input');
			}
		});
	



function validateProfileForm(){
	
	isProfileFormValid = true;
	
	$('#profile-form input').trigger('blur');
	
	return isProfileFormValid;
}

function validateFirstName(elementId){
	
	var inputVal = $('#'+elementId).val();
	
	if(inputVal == undefined || inputVal == ""){
		$('#'+elementId).next('.err-msg').html(firstNameEmptyMessage).show();
		$('#'+elementId).addClass('err-input').focus();
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
	
}

function validateLastName(elementId){
	
	var inputVal = $('#'+elementId).val();
	
	if(inputVal == undefined || inputVal == ""){
		$('#'+elementId).next('.err-msg').html(lastNameEmptyMessage).show();
		$('#'+elementId).addClass('err-input').focus();
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
}

function validateEmail(elementId){
	
	var inputVal = $('#'+elementId).val();
	
	if(inputVal == undefined || inputVal == ""){
		$('#'+elementId).next('.err-msg').html(emailEmptyMessage).show();
		$('#'+elementId).addClass('err-input').focus();
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
}


function validateFormField(elementId,isRequired,emptyErrorMessage,errorMessage,regEx){
	var inputVal = $('#'+elementId).val();
	
	if(regEx == undefined ||regEx == ""){
		regEx = /.*/;
	}
	
	if(inputVal == undefined || inputVal == ""){
		if(isRequired){
			$('#'+elementId).next('.err-msg').html(emptyErrorMessage).show();
			$('#'+elementId).addClass('err-input');
			return false;
		}else{
			$('#'+elementId).next('.err-msg').hide();
			$('#'+elementId).removeClass('err-input');
			return true;
		}
	}else if(!regEx.test(inputVal.trim())){
		$('#'+elementId).next('.err-msg').html(errorMessage).show();
		$('#'+elementId).addClass('err-input');
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
}


function validatePhone(elementId){
	
	var inputVal = $('#'+elementId).val();
	
	if(inputVal == undefined || inputVal == ""){
		$('#'+elementId).next('.err-msg').html(phoneEmptyMessage).show();
		$('#'+elementId).addClass('err-input').focus();
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
}

function validateZipCode(elementId){
	
	var inputVal = $('#'+elementId).val();
	
	if(inputVal == undefined || inputVal == ""){
		$('#'+elementId).next('.err-msg').html(zipCodeEmptyMessage).show();
		$('#'+elementId).addClass('err-input').focus();
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
}
$('body').on('focus',"#priPhoneNumberId",function(){
    $(this).mask("(999) 999-9999");
});
$('body').on('focus',"#secPhoneNumberId",function(){
    $(this).mask("(999) 999-9999");
});
function updateUserDetails() {

	//Validate User form
	
	if(!validateProfileForm()){
		return false;
	}
	
	
	var userProfileJson = new Object();

	userProfileJson.id = $("#userid").val();
	userProfileJson.firstName = $("#firstNameId").val();
	userProfileJson.lastName = $("#lastNameId").val();
	var phoneNumber = $("#priPhoneNumberId").val();
	userProfileJson.phoneNumber = phoneNumber.replace(/[^0-9]/g, '');
	
	userProfileJson.emailId = $("#priEmailId").val();

	var customerDetails = new Object();

	customerDetails.id = $("#customerDetailsId").val();
	customerDetails.addressCity = $("#cityId").val();
	customerDetails.addressState = $("#stateId").val();
	customerDetails.addressZipCode = $("#zipcodeId").val();
	customerDetails.dateOfBirth = makeDateFromDatePicker("dateOfBirthId");//new Date($("#dateOfBirthId").val()).getTime();
	var isStatus=validateBirthDate(customerDetails.dateOfBirth);
	if(!isStatus){
		//showErrorToastMessage(ageErrorMessage);
		$('#dateOfBirthId').next('.err-msg').html(ageErrorMessage).show();
		 $('#dateOfBirthId').addClass('ce-err-input').show();
			
		return false;
	}else{
		$('#dateOfBirthId').next('.err-msg').hide();
		$('#dateOfBirthId').removeClass('ce-err-input');
	}
	customerDetails.secEmailId = $("#secEmailId").val();
	var secPhoneNumber=$("#secPhoneNumberId").val();
	customerDetails.secPhoneNumber = secPhoneNumber.replace(/[^0-9]/g, '');
	//Condition to check whether primary and secondary email are same
		if($("#secEmailId").val()!=null||$("#secEmailId").val()!=""||$("#secEmailId").val()!=undefined){
			if($("#priEmailId").val()==$("#secEmailId").val()){
				showErrorToastMessage(priAndSecEmailValidation);
				return false;
			}
		}
	
		if($('.cust-radio-btn-yes').hasClass('radio-btn-selected')){
			userProfileJson.mobileAlertsPreference = true;	
			userProfileJson.carrierInfo=$('#carrierInfoID').val();
			}else if($('.cust-radio-btn-no').hasClass('radio-btn-selected')){
			   userProfileJson.mobileAlertsPreference = false;
			}

    
		userProfileJson.customerDetail = customerDetails;
	    
	    //var phoneStatus=validatePhone("priPhoneNumberId");
		var phoneStatus="";	
		if(userProfileJson.mobileAlertsPreference){
		 phoneStatus=validatePhone("priPhoneNumberId");	
		 if(!phoneStatus){
			 if($('#carrierInfoID').val()==null||$('#carrierInfoID').val()==""||$('#carrierInfoID').val()==undefined){
				// showErrorToastMessage(carrierSelectmessage);
				 $('#carrierInfoID').next('.err-msg').html(carrierSelectmessage).show();
				 $('#carrierInfoID').addClass('ce-err-input').show();
					return false;
	
				}
				else{
					$('#carrierInfoID').next('.err-msg').hide();
					$('#carrierInfoID').removeClass('ce-err-input');
					
				}	
		 }else{
			 if($('#carrierInfoID').val()==null||$('#carrierInfoID').val()==""||$('#carrierInfoID').val()==undefined){
				 $('#carrierInfoID').next('.err-msg').html(carrierSelectmessage).show();
				 $('#carrierInfoID').addClass('ce-err-input').show();
					return false;
	
				}
				else{
					$('#carrierInfoID').next('.err-msg').hide();
					$('#carrierInfoID').removeClass('ce-err-input');
					
				}
		 }
	
		}else if(userProfileJson.mobileAlertsPreference==false){
			phoneStatus=true;	
		}
	    showOverlay();
		$.ajax({
			url : "rest/userprofile/updateprofile",
			type : "POST",
			cache:false,
			data : {
				"updateUserInfo" : JSON.stringify(userProfileJson)
			},
			dataType : "json",
			success : function(data) {
				showToastMessage(updateSuccessMessage);
				hideOverlay();			
				$("#profileNameId").text($("#firstNameId").val());
				//NEXNF-711
			  /*	$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($("#priPhoneNumberId").val()));*/
			  	$("#priPhoneNumberId").mask("(999) 999-9999");
			  	$('#overlay-toast').fadeIn("fast",function(){
				setTimeout(function(){
					$('#overlay-toast').fadeOut("fast");
				},1000);
			    });
			  	if($('#overlay-toast-error-txt').html()!=""){
			  	$('#overlay-toast-error-txt').fadeIn("fast",function(){
					setTimeout(function(){
						$('#overlay-toast-error-txt').fadeOut("fast");
					},1000);
				});
			  	}
			  //	removeToastMessage();
			 
			  		window.location.href = "#myLoan";
			  	
	            
	 
			},
			error : function(data) {
				hideOverlay();
				if(data.error.message!=null){
					showErrorToastMessage(data.error.message);
				}else{
					showErrorToastMessage(updateErrorMessage);
				}
			
			}
		});
}

    



function createCropDiv(divToAppend, url) {
	$("#" + divToAppend).empty();
	var $containerDiv = $("<div>");
	var $divImg = $("<img src='" + url
			+ "' id='target' alt='[Jcrop Example]' />");
	var $containerDiv1 = $("<div id='preview-pane'>");
	var $containerDiv2 = $("<div class='preview-container'>");
	var $divImg1 = $("<img src='" + url
			+ "' class='jcrop-preview' alt='[Preview]' />");
	var $containerDiv3 = $("<div id='cropDoneId' class='loginPageButton'>")
			.text("Done");
	$containerDiv3.click(function() {
		$("#fullImageFormId").attr('action', 'uploadProfilePicture.do');
		$("#fullImageFormId").submit();
		selectedAvatar = null;
		// lightbox_close();
	});
	$containerDiv.append($divImg);
	$containerDiv1.append($containerDiv2);
	$containerDiv2.append($divImg1);
	$containerDiv.append($containerDiv1);
	$containerDiv.append($containerDiv3);
	$("#" + divToAppend).append($containerDiv);
	jQuery(function($) {

		// Create variables (in this scope) to hold the API and image size
		var jcrop_api, boundx, boundy,

		// Grab some information about the preview pane
		$preview = $('#preview-pane'), $pcnt = $('#preview-pane .preview-container'), $pimg = $('#preview-pane .preview-container img'),

		xsize = $pcnt.width(), ysize = $pcnt.height();

		console.log('init', [ xsize, ysize ]);
		$('#target').Jcrop({
			onChange : updatePreview,
			onSelect : updatePreview,
			aspectRatio : 1
		}, function() {
			// Use the API to get the real image size
			var bounds = this.getBounds();
			boundx = bounds[0];
			boundy = bounds[1];
			// Store the API in the jcrop_api variable
			jcrop_api = this;

			// Move the preview into the jcrop container for css positioning
			$preview.appendTo(jcrop_api.ui.holder);
		});

		function updatePreview(c) {

			if (parseInt(c.w) > 0) {

				var rx = xsize / c.w;
				var ry = ysize / c.h;
				$('#xId').val(c.x);
				$('#yId').val(c.y);
				$('#wId').val(Math.round(c.w));
				$('#hId').val(Math.round(c.h));

				$pimg.css({
					width : Math.round(rx * boundx) + 'px',
					height : Math.round(ry * boundy) + 'px',
					marginLeft : '-' + Math.round(rx * c.x) + 'px',
					marginTop : '-' + Math.round(ry * c.y) + 'px'
				});
			}
		}
		;

	});

}

function editProfileInformation(user) {

	var container = $('<div>').attr({
		"class" : "application-form-container-edit clearfix hide"
	});

	var nonEmptyValuesContainer = $("<div>").attr({
		"id" : "nonEmptyValuesContainer"
	});

	nonEmptyValuesContainer.append(editProfileInfoRow("First Name",
			user.firstName, "edituserFnameId", true));

	nonEmptyValuesContainer.append(editProfileInfoRow("Last Name",
			user.lastName, "edituserlnameId", true));

	nonEmptyValuesContainer.append(editProfileInfoRow("Email Id", user.emailId,
			"editemailId", true));

	nonEmptyValuesContainer.append(editProfileInfoRow("Second Email Id",
			user.customerDetail.secEmailId, "editsecEmailId", false));

	nonEmptyValuesContainer.append(editProfileInfoRow("Phone 1",
			user.phoneNumber, "editpriPhoneNumberId", true));

	nonEmptyValuesContainer.append(editProfileInfoRow("Phone 2",
			user.customerDetail.secPhoneNumber, "editsecPhoneNumberId", false));

	nonEmptyValuesContainer.append(editProfileInfoRow("City",
			user.customerDetail.addressCity, "edituserCityId", true));

	nonEmptyValuesContainer.append(editProfileInfoRow("State",
			user.customerDetail.addressState, "edituserStateId", true));

	nonEmptyValuesContainer.append(editProfileInfoRow("Zipcode",
			user.customerDetail.addressZipCode, "edituserZipcodeId", true));

	var comRow = editProfileInfoRow("DOB", $.datepicker.formatDate("mm/dd/yy",new Date(user.customerDetail.dateOfBirth)),
			"editdobId", true);
	comRow.find('.form-detail-edit').find('input').addClass('date-picker')
			.attr("placeholder", "MM/DD/YYYY").datepicker({
				orientation : "top auto",
				autoclose : true
			});
	nonEmptyValuesContainer.append(comRow);

	/* discuss this approch : is it posible to make a global user varibale ands save them */
	
	/*var nextButton = $('<div>').attr({
		"class" : "submit-btn"
	}).bind('click', {
		"user" : user
	}, function(event) {
		event.stopImmediatePropagation();
		paintProfileCompleteStep2(event.data.user);
	}).html("Next");

*/
	var nextButton = $('<div>').attr({
		"class" : "submit-btn"
	}).bind('click', function(event) {
		event.stopImmediatePropagation();
		saveEditUserProfile(user);
	}).html("Next");
	return container.append(nonEmptyValuesContainer).append(nextButton);
}

function editProfileInfoRow(desc, value, id, isCompulsory) {
	var row = $('<div>').attr({
		"class" : "form-detail-edit-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "form-detail-row-desc float-left"
	}).html(desc);
	if (isCompulsory) {
		rowCol1.addClass("form-field-compulsory");
		rowCol1.append('<span class="compulsory-span"> *</span>');
	}
	var rowCol2 = $('<div>').attr({
		"class" : "form-detail-edit float-left"
	});
	var editRow = $('<input>').attr({
		"class" : "form-detail-input",
		"id" : id,
		"value" : value
	});
	rowCol2.append(editRow);
	return row.append(rowCol1).append(rowCol2);
}

function showHideEditProfile() {

	$(".application-form-container").css("display", "none");
	$(".application-form-container-edit").css("display", "block");
}

function saveEditUserProfile(user){
	
	// On click of this next button the values are getting stored in the data base 
	var fname =  $('#edituserFnameId').val();	
	var lname =  $('#edituserlnameId').val();
	var email =  $('#editemailId').val();
	var secEmailId =  $('#editsecEmailId').val();
	var priPhone =  $('#editpriPhoneNumberId').val();
	priPhone=phoneNumber.replace(/[^0-9]/g, '');
	var secPhone =  $('#editsecPhoneNumberId').val();
	secPhone=phoneNumber.replace(/[^0-9]/g, '');
	var dob =  $('#editdobId').val();	
	var city =  $('#edituserCityId').val();
	var state =  $('#edituserStateId').val();
	var zipcode =  $('#edituserZipcodeId').val();

	
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
	completeCustomerDetails.dateOfBirth =makeDate(dob)//new Date(dob).getTime();
	completeCustomerDetails.secEmailId = secEmailId;
	completeCustomerDetails.secPhoneNumber = secPhone;
	
	completeUserInfo.customerDetail = completeCustomerDetails;
	$('#overlay-loader').show();
	$.ajax({
		url : "rest/userprofile/completeprofile",
		type : "POST",
		cache:false,
		data : {"completeUserInfo":JSON.stringify(completeUserInfo)},
		dataType : "json",
		success : function(data) {
			$('#overlay-loader').hide();
		},
		error : function(error) {
			$('#overlay-loader').hide();
			showErrorToastMessage(errorMessage);
		}
	});
	var loanType=JSON.parse(newfi.appUserDetails).loanType.description;
	$('#profileNameId').text(fname);
	//$('#profilePhoneNumId').text(priPhone);
	$('#loanType').text(loanType);
	//NEXNF-711
	/*$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat(priPhone));*/
	
	
	$('#center-panel-cont').html("");
	var topHeader = getCompletYourApplicationHeader();
	var formContainer = getLoanDetailsWrapper();
	$('#center-panel-cont').append(topHeader).append(formContainer);
	
}


function getStateTextRow() {
	
	var userStates = $('<div>').attr({
		"class" : "prof-form-input-textarea float-left clearfix",
		"id" : "inputTextarea"
 	});
	
	appendUserStatesInLMProfile(userStates);
	
	return userStates;
}

function appendUserStatesInLMProfile(element) {
	
	var userStates = element;
	userStates.html('');
	for(var key in internalUserStates){
		if(internalUserStates[key]!=0 && internalUserStates[key].isChecked){
			
			var emailHtml = $('<div>').attr({
				"class" : "prof-form-input-textarea-block float-left"
			});
			
			var emailHtmlTxt = $('<div>').attr({
				"class" : "float-left"
			}).html(findStateNameForStateId(internalUserStates[key].stateId));
			
			var removeIcn = $('<div>').attr({
				"class" : "message-recipient-remove-icn float-left",
				"data-id" : key
			}).bind('click',function(e){
				var id = $(this).attr("data-id");
				/*internalUserStates[id].isChecked = false;*/
				$(this).closest('.prof-form-input-textarea-block').remove();
			var	internalUserStateMappingVO=internalUserStates[id];
				if(internalUserStateMappingVO.isChecked!=typeof underdefined)
					internalUserStateMappingVO.isChecked=false;
				internalUserStates[id]=internalUserStateMappingVO;
				$("#checkBox_"+id).addClass("doc-unchecked").removeClass("doc-checked");
			});
			
			emailHtml.append(emailHtmlTxt).append(removeIcn);
			
			userStates.append(emailHtml);
		}
	}
}


function getLqbRow(displayName,value,id) {
	
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html(displayName);

	
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var input = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-m",
		"value" : value,
		"id" : id,
	});
	
	inputCont.append(input);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}


function getPasswordRow(displayName,id,password) {
	
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html(displayName);

	
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	var input;
	if(id!="lqb_userPassword"){
		 input = $('<input>').attr({
			"class" : "prof-form-input prof-form-input-m",
			"id" : id,
			"type" : "password",
			"name":"change-password"
		});
		/* inputCont.append(input).append(appendErrorMessage());*/
	}else{
		input = $('<input>').attr({
			"class" : "prof-form-input prof-form-input-m",
			"id" : id,
			"type" : "password"
		}).val(password);
		/*inputCont.append(input);*/
	}
	
	
	inputCont.append(input).append(appendErrorMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

function getCarrierName(carrierInfo){
	
	 switch(carrierInfo){
     case "@txt.att.net":
             return "AT&T";
         break;
     case "@tmomail.net":
         return "T-Mobile";
     break;
     case "@vtext.com":
         return "Verizon";
     break;
     case "@messaging.sprintpcs.com":
         return "Sprint";
     break;
     case "@vmobl.com":
         return "Virgin Mobile";
     break;
     case "@mmst5.tracfone.com":
         return "Tracfone";
     break;
     case "@mymetropcs.com":
         return "Metro Pcs";
     break;
     case "@myboostmobile.com":
         return "Boost Mobile";
     break;
     case "@sms.mycricket.com":
         return "Cricket";
     break;
     case "@messaging.nextel.com":
         return "Nextel";
     break;
     case "@message.alltel.com":
         return "Alltel";
     break;
     case "@ptel.com":
         return "Ptel";
     break;
     case "@tms.suncom.com":
         return "Suncom";
     break;
     case "@qwestmp.com":
         return "Quest";
     break;
     case "@email.uscc.net":
         return "US Cellular";
     break;
     case "@mobile.mycingular.com":
         return "Cingular";
     break;
    
     return ""; 
	}
}


function saveInternalUserStatesAndLicense(internalUserStateMappingparam){
	$('#overlay-loader').show();
	 $.ajax({
	        url: "rest/userprofile/internaluserstatemapping",
	        type: "POST",
	        data: {
	            "internaluserstatemapping": JSON.stringify(internalUserStateMappingparam)
	        },
	        datatype: "application/json",
	        async: false,
	        cache:false,
	        success: function(data) {
	        	$('#overlay-loader').hide();
	        	$('#add-licence-popup').hide();
	        	internalUserStateMappingVO = data.resultObject; 
	        	internalUserStates[internalUserStateMappingVO.stateId] = internalUserStateMappingVO;
	        },
	        error: function() {
	        	$('#overlay-loader').hide();
	        	$('#add-licence-popup').hide();
	            showErrorToastMessage(errorMessage);
	           
	        }
	 
	    });
}


function deleteStateLicenseMapping(statelicensemapping){
	$('#overlay-loader').show();
	 $.ajax({
	        url: "rest/userprofile/deleteStatelicensemapping",
	        type: "POST",
	        data: {
	            "internaluserstatemapping": JSON.stringify(statelicensemapping)
	        },
	        datatype: "application/json",
	        cache:false,
	        success: function(data) {
	        	$('#overlay-loader').hide();
	        },
	        error: function() {
	        	$('#overlay-loader').hide();
	        	showErrorToastMessage(errorMessage);
	           
	        }
	 
	    });
}