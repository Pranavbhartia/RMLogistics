
function showCustomerProfilePage() {
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

	var wrapper = $('<div>').attr({
		"class" : "loan-personal-info-wrapper"
	});

	var header = $('<div>').attr({
		//included that of customer css
		"class" : "cust-personal-info-header"
	}).html("Personal Information");

	var container = getLoanPersonalInfoContainer(user);

	wrapper.append(header).append(container);
	$('#loan-profile-main-container').append(wrapper);

}

function getLoanPersonalInfoContainer(user) {

	var container = $('<div>').attr({
		"class" : "loan-personal-info-container"
	});

	var nameRow = getCustomerNameFormRow(user);
	container.append(nameRow);

	var uploadRow = getCustomerUploadPhotoRow(user);
	container.append(uploadRow);

	var DOBRow = getDOBRow(user);
	container.append(DOBRow);

	var priEmailRow = getPriEmailRow(user);
	container.append(priEmailRow);

	var phone1Row = getPhone1RowLM(user);
	container.append(phone1Row);


	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn",
		"onclick" : "updateLMDetails()"
	}).html("Save");
	container.append(saveBtn);
	return container;
}

function updateLMDetails() {

	var userProfileJson = new Object();

	userProfileJson.id = $("#userid").val();
	userProfileJson.firstName = $("#firstNameId").val();
	userProfileJson.lastName = $("#lastNameId").val();
	userProfileJson.phoneNumber = $("#priPhoneNumberId").val();
	userProfileJson.emailId = $("#priEmailId").val();

	var customerDetails = new Object();

	customerDetails.id = $("#customerDetailsId").val();
	customerDetails.dateOfBirth = new Date($("#dateOfBirthId").val()).getTime();


	userProfileJson.customerDetail = customerDetails;
    var phoneStatus=phoneNumberValidation($("#priPhoneNumberId").val());


   if($("#firstNameId").val()!="" && $("#lastNameId").val()!="" && $("#priEmailId").val()!="" && $("#priPhoneNumberId").val()!=""){
     if(phoneStatus!=false){
	    $.ajax({
		url : "rest/userprofile/updateprofile",
		type : "POST",
		data : {
			"updateUserInfo" : JSON.stringify(userProfileJson)
		},
		dataType : "json",
		success : function(data) {

			$("#profileNameId").text($("#firstNameId").val());
			$("#profilePhoneNumId").text($("#priPhoneNumberId").val());

		},
		error : function(error) {
			showToastMessage("Mandatory Fileds should not be empty");
		}
	});

	showToastMessage("Succesfully updated");}}else{
		showToastMessage("Mandatory fields should not be empty");
	}
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

	var wrapper = $('<div>').attr({
		"class" : "loan-personal-info-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "cust-personal-info-header"
	}).html("Personal Information");

	var container = getCustPersonalInfoContainer(user);
	wrapper.append(header).append(container);
	$('#profile-main-container').append(wrapper);

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

	var cityRow = getCityRow(user);
	formWrapper.append(cityRow);

	var stateRow = getStateRow(user);
	formWrapper.append(stateRow);

	var zipRow = getZipRow(user);
	formWrapper.append(zipRow);

	var phone1Row = getPhone1Row(user);
	formWrapper.append(phone1Row);

	var phone2Row = getPhone2Row(user);
	formWrapper.append(phone2Row);

	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn",
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
		"id" : "firstNameId"
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
		"id" : "lastNameId"
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
	if (user.photoImageUrl == "" || user.photoImageUrl == null
			|| user.photoImageUrl == 'undefined') {
		uploadPicPlaceholder = $('<div>').attr({
			"id" : "profilePic",
			"class" : "prof-pic-upload-placeholder float-left"
		});

	} else {
		uploadPicPlaceholder = $('<div>').attr({
			"id" : "profilePic",
			"class" : "prof-pic-upload-placeholder float-left",
			"style" : "background-image:url(" + user.photoImageUrl + ")"
		});
	}

	
	var uploadIcn = $('<div>').attr({
		"class" : "upload-prof-pic-icn"
	}).click(uploadeImage);
	
	uploadPicPlaceholder.append(uploadIcn);
	
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

	var dob = $.datepicker.formatDate('mm/dd/yy', new Date(
			user.customerDetail.dateOfBirth));

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
		"id" : "dateOfBirthId"
	}).datepicker({
		orientation : "top auto",
		autoclose : true
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

	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : user.emailId,
		"id" : "priEmailId",
		"readonly":true,
		"onblur" : "emailValidation(this.value)"
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(emailInput).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

function emailValidation(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!regex.test(email)) {
		alert("Incorrect Email");
		validationFails = true;
	}
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
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
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
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(cityInput).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
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
		"class" : "prof-form-input prof-form-input-sm",
		"value" : user.customerDetail.addressState,
		"id" : "stateId"
	});
	rowCol2.append(stateInput);
	return row.append(rowCol1).append(rowCol2);
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
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(zipInput).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

function getPhone1Row(user) {
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Primary Phone");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var phone1Input = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-m",
		"value" : user.phoneNumber,
		"id" : "priPhoneNumberId",
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(phone1Input).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}
//TODO added for validation LM

function getPhone1RowLM(user) {
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
		"value" : user.phoneNumber,
		"id" : "priPhoneNumberId",
		
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(phone1Input).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}

function phoneNumberValidation(phoneNo){

var regex = /^\d{10}$/;   
	if (!regex.test(phoneNo)) {
		showToastMessage("Invalid phone number");
		validationFails = true;
		return false
	}
return true;
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
		"id" : "secPhoneNumberId"
	});
	
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	
	inputCont.append(phone2Input).append(errMessage);
	
	rowCol2.append(inputCont);
	return row.append(rowCol1).append(rowCol2);
}


/**
 * Functions related to form validations
 */

function validateProfileForm(){
	
	var isFormValid = true;
	if(!validateFirstName('firstNameId')){
		isFormValid = false;
	}
	if(!validateLastName('lastNameId')){
		isFormValid = false;
	}
	
	return isFormValid;
}

function validateFirstName(elementId){
	
	var inputVal = $('#'+elementId).val();
	
	if(inputVal == undefined || inputVal == ""){
		$('#'+elementId).next('.err-msg').html("First name can not be empty").show();
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
		$('#'+elementId).next('.err-msg').html("Last name can not be empty").show();
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
		$('#'+elementId).next('.err-msg').html("Email can not be empty").show();
		$('#'+elementId).addClass('err-input').focus();
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
		$('#'+elementId).next('.err-msg').html("Phone can not be empty").show();
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
		$('#'+elementId).next('.err-msg').html("Zipcode can not be empty").show();
		$('#'+elementId).addClass('err-input').focus();
		return false;
	}else{
		$('#'+elementId).next('.err-msg').hide();
		$('#'+elementId).removeClass('err-input');
		return true;
	}
}


function updateUserDetails() {

	//Validate User form
	
	if(!validateProfileForm()){
		return false;
	}
	
	
	var userProfileJson = new Object();

	userProfileJson.id = $("#userid").val();
	userProfileJson.firstName = $("#firstNameId").val();
	userProfileJson.lastName = $("#lastNameId").val();
	userProfileJson.phoneNumber = $("#priPhoneNumberId").val();
	userProfileJson.emailId = $("#priEmailId").val();

	var customerDetails = new Object();

	customerDetails.id = $("#customerDetailsId").val();
	customerDetails.addressCity = $("#cityId").val();
	customerDetails.addressState = $("#stateId").val();
	customerDetails.addressZipCode = $("#zipcodeId").val();
	customerDetails.dateOfBirth = new Date($("#dateOfBirthId").val()).getTime();
	customerDetails.secEmailId = $("#secEmailId").val();
	customerDetails.secPhoneNumber = $("#secPhoneNumberId").val();

	userProfileJson.customerDetail = customerDetails;

    var phoneStatus=phoneNumberValidation($("#priPhoneNumberId").val());
	
  

    if($("#firstNameId").val()!="" && $("#lastNameId").val()!="" && $("#priEmailId").val()!="" && $("#priPhoneNumberId").val()!=""){
    if(phoneStatus!=false){
	$.ajax({
		url : "rest/userprofile/updateprofile",
		type : "POST",
		data : {
			"updateUserInfo" : JSON.stringify(userProfileJson)
		},
		dataType : "json",
		success : function(data) {

			$("#profileNameId").text($("#firstNameId").val());
			$("#profilePhoneNumId").text($("#priPhoneNumberId").val());

		},
		error : function(error) {
			alert("error" + error);
		}
	});

	showToastMessage("Succesfully updated");}
	}else{
		showToastMessage("Mandatory Fileds should not be empty");
	}
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
	var secPhone =  $('#editsecPhoneNumberId').val();
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
	completeCustomerDetails.dateOfBirth =new Date(dob).getTime();
	completeCustomerDetails.secEmailId = secEmailId;
	completeCustomerDetails.secPhoneNumber = secPhone;
	
	completeUserInfo.customerDetail = completeCustomerDetails;

	$.ajax({
		url : "rest/userprofile/completeprofile",
		type : "POST",
		data : {"completeUserInfo":JSON.stringify(completeUserInfo)},
		dataType : "json",
		success : function(data) {

		},
		error : function(error) {
			alert("error"+error);
		}
	});
	
	$('#profileNameId').text(fname);
	$('#profilePhoneNumId').text(priPhone);
	
	
	$('#center-panel-cont').html("");
	var topHeader = getCompletYourApplicationHeader();
	var formContainer = getLoanDetailsWrapper();
	$('#center-panel-cont').append(topHeader).append(formContainer);
	
}


