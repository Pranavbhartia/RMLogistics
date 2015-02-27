/*
* Functions for customer profile page
*/

function showCustomerProfilePage(){
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
	appendCustPersonalInfoWrapper();
	
}

function appendCustPersonalInfoWrapper(){
	var wrapper = $('<div>').attr({
		"class" : "cust-personal-info-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "cust-personal-info-header"
	}).html("Personal Information");
	
	var container = getCustPersonalInfoContainer();
	
	wrapper.append(header).append(container);
	$('#profile-main-container').append(wrapper);
}

function getCustPersonalInfoContainer(){
	var container = $('<div>').attr({
		"class" : "cust-personal-info-container"
	});
	
	var nameRow = getCustomerNameFormRow();
	container.append(nameRow);
	
	var uploadRow = getCustomerUploadPhotoRow();
	container.append(uploadRow);
	
	var DOBRow = getDOBRow();
	container.append(DOBRow);
	
	var emailRow = getEmailRow();
	container.append(emailRow);
	
	var streetAddrRow = getStreetAddrRow();
	container.append(streetAddrRow);
	
	var cityRow = getCityRow();
	container.append(cityRow);
	
	var stateRow = getStateRow();
	container.append(stateRow);
	
	var zipRow = getZipRow();
	container.append(zipRow);
	
	var phone1Row = getPhone1Row();
	container.append(phone1Row);

	var phone2Row = getPhone2Row();
	container.append(phone2Row);
	
	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn"
	}).html("Save");
	container.append(saveBtn);
	return container;
}


function getCustomerNameFormRow() {
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
		"placeholder" : "First Name"
	});
	
	var lastName = $('<input>').attr({
		"class" : "prof-form-input",
		"placeholder" : "Last Name"
	});
	rowCol2.append(firstName).append(lastName);
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
	
	var uploadBottomContianer = $('<div>').attr({
		"class" : "clearfix"
	});
	
	var uploadPicCont = $('<div>').attr({
		"class" : "prof-pic-upload-cont prof-form-input float-left clearfix"
	});
	
	var uploadPicInput = $('<input>').attr({
		"class" : "hide"
	});
	
	var uploadIcn = $('<div>').attr({
		"class" : "prof-upload-icn float-right"
	});
	
	uploadPicCont.append(uploadPicInput).append(uploadIcn);
	
	var uploadBtn = $('<div>').attr({
		"class" : "prof-btn upload-btn float-left"
	}).html("upload");
	uploadBottomContianer.append(uploadPicCont).append(uploadBtn);
	rowCol2.append(uploadPicPlaceholder).append(uploadBottomContianer);
	
	return row.append(rowCol1).append(rowCol2);
}


function getDOBRow() {
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
		"placeholder" : "MM/DD/YYYY"
	}).datepicker({
		orientation: "top auto",
		autoclose: true
	});
	rowCol2.append(dobInput);
	return row.append(rowCol1).append(rowCol2);
}


function getEmailRow() {
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "prof-form-row-desc float-left"
	}).html("Email");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg"
	});
	rowCol2.append(emailInput);
	return row.append(rowCol1).append(rowCol2);
}


function getStreetAddrRow() {
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
		"class" : "prof-form-input prof-form-input-lg"
	});
	rowCol2.append(steetAddrInput);
	return row.append(rowCol1).append(rowCol2);
}

function getCityRow() {
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
		"class" : "prof-form-input"
	});
	rowCol2.append(cityInput);
	return row.append(rowCol1).append(rowCol2);
}

function getStateRow() {
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
		"class" : "prof-form-input prof-form-input-sm"
	});
	rowCol2.append(stateInput);
	return row.append(rowCol1).append(rowCol2);
}


function getZipRow() {
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
		"class" : "prof-form-input prof-form-input-sm"
	});
	rowCol2.append(zipInput);
	return row.append(rowCol1).append(rowCol2);
}


function getPhone1Row() {
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
		"class" : "prof-form-input"
	});
	rowCol2.append(phone1Input);
	return row.append(rowCol1).append(rowCol2);
}


function getPhone2Row() {
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
		"class" : "prof-form-input"
	});
	rowCol2.append(phone2Input);
	return row.append(rowCol1).append(rowCol2);
}
