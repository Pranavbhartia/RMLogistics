/*
* Functions for customer profile page
*/

function showCustomerProfilePage(){
	ajaxRequest("customerProfile.do", "GET", "HTML", {}, showCustomerProfilePageCallBack);
}

function getUserProfileData(){
	ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {}, appendCustPersonalInfoWrapper);
}

function userProfileData(data){

	showCustomerProfilePageCallBack(data);
}

function showCustomerProfilePageCallBack(data){
	$('#main-body-wrapper').html(data);
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
}

function paintCutomerProfileContainer() {
	$('#profile-main-container').html('');
	 getUserProfileData();
}

function appendCustPersonalInfoWrapper(user){
	
	var wrapper = $('<div>').attr({
		"class" : "cust-personal-info-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "cust-personal-info-header"
	}).html("Personal Information");
	
	var container = getCustPersonalInfoContainer(user);
	
	wrapper.append(header).append(container);
	$('#profile-main-container').append(wrapper);
}

function getCustPersonalInfoContainer(user){
	
	
	var container = $('<div>').attr({
		"class" : "cust-personal-info-container"
	});
	
	var nameRow = getCustomerNameFormRow(user);
	container.append(nameRow);
	
	var uploadRow = getCustomerUploadPhotoRow(user);
	container.append(uploadRow);
	
	var DOBRow = getDOBRow(user);
	container.append(DOBRow);
	
	var priEmailRow = getPriEmailRow(user);
	container.append(priEmailRow);
	
	var secEmailRow = getSecEmailRow(user);
	container.append(secEmailRow);
	
	/*var streetAddrRow = getStreetAddrRow(user);
	container.append(streetAddrRow);*/
	
	var cityRow = getCityRow(user);
	container.append(cityRow);
	
	var stateRow = getStateRow(user);
	container.append(stateRow);
	
	var zipRow = getZipRow(user);
	container.append(zipRow);
	
	var phone1Row = getPhone1Row(user);
	container.append(phone1Row);

<<<<<<< HEAD
	var phone2Row = getPhone2Row(user);
=======
	var phone2Row = getPhone2Row();
>>>>>>> upstream/master
	container.append(phone2Row);
	
	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn",
		"onclick" :"updateUserDetails()"
	}).html("Save");
	container.append(saveBtn);
	return container;
}


function getCustomerNameFormRow(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Name");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var firstName = $('<input>').attr({
		"class" : "prof-form-input",
		"placeholder" : "First Name",
		"value":user.firstName,
		"id":"firstNameId"
	});
	
	
	var lastName = $('<input>').attr({
		"class" : "prof-form-input",
		"placeholder" : "Last Name",
		"value":user.lastName,
		"id":"lastNameId"
	});
	
	var id = $('<input>').attr({
		"type" : "hidden",
		"value":user.id,
		"id":"userid"
	});
	
	var customerDetailsId = $('<input>').attr({
		"type" : "hidden",
		"value":user.customerDetail.id,
		"id":"customerDetailsId"
	});
	
	
	rowCol2.append(firstName).append(lastName).append(id).append(customerDetailsId);
	return row.append(rowCol1).append(rowCol2);
}


function getCustomerUploadPhotoRow() {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Upload Photo");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var uploadPicPlaceholder = $('<div>').attr({
		"class" : "prof-pic-upload-placeholder float-left"
	});
	var inputHiddenFile = $('<input>').attr({
		"type" : "file",
		"id":"uploadImageId"
		 
	});
	
	var uploadImage = $('<div>').attr({
		"class" : "uploadImage"
		 
	}).hide();
	
	uploadImage.append(inputHiddenFile);
	uploadPicPlaceholder.append(uploadImage);
	
	var uploadBottomContianer = $('<div>').attr({
		"class" : "clearfix"
	});
	
	/*var uploadPicCont = $('<div>').attr({
		"class" : "prof-pic-upload-cont prof-form-input float-left clearfix"
	});
	
	var uploadPicInput = $('<input>').attr({
		"class" : "hide"
	});
	
	var uploadIcn = $('<div>').attr({
		"class" : "prof-upload-icn float-right"
	});
	
	uploadPicCont.append(uploadPicInput).append(uploadIcn);
	*/
	var uploadBtn = $('<div>').attr({
		"class" : "prof-btn upload-btn float-left",
		"type":"file"
		
	}).click(uploadeImage).html("upload");
	//uploadBottomContianer.append(uploadPicCont).append(uploadBtn);
	uploadBottomContianer.append(uploadBtn);
	rowCol2.append(uploadPicPlaceholder).append(uploadBottomContianer);
	
	return row.append(rowCol1).append(rowCol2);
}


function getDOBRow(user) {
	
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("DOB");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var dobInput = $('<input>').attr({
		"class" : "prof-form-input date-picker",
		"placeholder" : "MM/DD/YYYY",
		"value":$.datepicker.formatDate('mm/dd/yy',new Date(user.customerDetail.dateOfBirth)),
		"id":"dateOfBirthId"
	}).datepicker({
		orientation: "top auto",
		autoclose: true
	});
	rowCol2.append(dobInput);
	return row.append(rowCol1).append(rowCol2);
}


function getPriEmailRow(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Primery Email");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value":user.emailId,
		"id":"priEmailId"
	});
	rowCol2.append(emailInput);
	return row.append(rowCol1).append(rowCol2);
}


function getSecEmailRow(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Secondry Email");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value":user.customerDetail.secEmailId,
		"id":"secEmailId"
	});
	rowCol2.append(emailInput);
	return row.append(rowCol1).append(rowCol2);
}



/*function getStreetAddrRow() {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Street Address");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var steetAddrInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value":user.customerDetail.secEmailId
	});
	rowCol2.append(steetAddrInput);
	return row.append(rowCol1).append(rowCol2);
}*/

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
	var cityInput = $('<input>').attr({
		"class" : "prof-form-input",
		"value":user.customerDetail.city,
		"id":"cityId"
	});
	rowCol2.append(cityInput);
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
		"value":user.customerDetail.addressState,
		"id":"stateId"
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
	var zipInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-sm",
		"value":user.customerDetail.addressZipCode,
		"id":"zipcodeId"
	});
	rowCol2.append(zipInput);
	return row.append(rowCol1).append(rowCol2);
}


function getPhone1Row(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Phone 1");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var phone1Input = $('<input>').attr({
		"class" : "prof-form-input",
		"value":user.phoneNumber,
		"id":"priPhoneNumberId"
	});
	rowCol2.append(phone1Input);
	return row.append(rowCol1).append(rowCol2);
}


function getPhone2Row(user) {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Phone 2");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var phone2Input = $('<input>').attr({
		"class" : "prof-form-input",
		"value":user.customerDetail.secPhoneNumber,
		"id":"secPhoneNumberId"
	});
	rowCol2.append(phone2Input);
	return row.append(rowCol1).append(rowCol2);
}

function updateUserDetails(){
	
	var userProfileJson = new Object();
	
	/*
	var customerDetails = {
			
			'addressCity' :  $("#cityId").val(),
			'addressState' : $("#stateId").val(),
			'addressZipCode' : $("#zipcodeId").val(),
			'dateOfBirth': $("#dateOfBirthId").val(),
			'secEmailId' : $("#secEmailId").val(),
			'secPhoneNumber': $("#secPhoneNumberId").val()
	};
	
	var userProfileJson = {
			'firstName' :  $("#firstNameId").val(),
			'lastName' : $("#lastNameId").val(),
			'phoneNumber' : $("#priPhoneNumberId").val(),
			'customerDetail':customerDetails
			};
*/
	
	userProfileJson.id = $("#userid").val();
	userProfileJson.firstName = $("#firstNameId").val();
	userProfileJson.lastName = $("#lastNameId").val();
	userProfileJson.phoneNumber = $("#priPhoneNumberId").val();
	userProfileJson.emailId  = $("#priEmailId").val(); 
	
	var customerDetails = new Object();
	
	customerDetails.id   =  $("#customerDetailsId").val();
	customerDetails.addressCity   =  $("#cityId").val();
	customerDetails.addressState  =  $("#stateId").val();
	customerDetails.addressZipCode =  $("#zipcodeId").val();
	customerDetails.dateOfBirth = new Date($("#dateOfBirthId").val()).getTime();
	customerDetails.secEmailId = $("#secEmailId").val();
	customerDetails.secPhoneNumber = $("#secPhoneNumberId").val();
	
	userProfileJson.customerDetail = customerDetails;
	
	
	$.ajax({
		url : "rest/userprofile/updateprofile",
		type:"POST",
		data:JSON.stringify(userProfileJson),
		dataType :"json",
		contentType:"application/json; charset=utf-8",
		success : function(data){
			
		},
		error:function(error){
			
		}
	});
	
	
	//ajaxRequest("rest/userprofile/updateprofile", "POST", "json",userProfileJson, {});
	
	
}

function uploadeImage(){
	
	$("#uploadImageId").click();
}
