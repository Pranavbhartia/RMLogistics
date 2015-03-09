
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

function userProfileData(data) {

	showCustomerProfilePageCallBack(data);
}

function paintCutomerProfileContainer() {
	$('#profile-main-container').html('');
	// getUserProfileData();
}

function customerPersonalInfoWrapper(user) {

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

function getCustPersonalInfoContainer(user) {

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

	/*
	 * var streetAddrRow = getStreetAddrRow(user);
	 * container.append(streetAddrRow);
	 */

	var cityRow = getCityRow(user);
	container.append(cityRow);

	var stateRow = getStateRow(user);
	container.append(stateRow);

	var zipRow = getZipRow(user);
	container.append(zipRow);

	var phone1Row = getPhone1Row(user);
	container.append(phone1Row);

	var phone2Row = getPhone2Row(user);
	container.append(phone2Row);

	var saveBtn = $('<div>').attr({
		"class" : "prof-btn prof-save-btn",
		"onclick" : "updateUserDetails()"
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
		"value" : user.firstName,
		"id" : "firstNameId"
	});

	var lastName = $('<input>').attr({
		"class" : "prof-form-input",
		"placeholder" : "Last Name",
		"value" : user.lastName,
		"id" : "lastNameId"
	});

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
		"class" : "prof-form-row-desc float-left"
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

	var uploadBottomContianer = $('<div>').attr({
		"class" : "clearfix"
	});

	var inputHiddenFile = $('<input>').attr({
		"type" : "file",
		"id" : "prof-image",
		"name" : "fileName",
		"value" : "Upload"

	});

	var inputHiddenDiv = $('<div>').attr({
		"style" : "display:none"

	});

	var canvasTage = $('<canvas>').attr({
		"id" : "canvasid",
		"style" : "display:none",
	});

	var imageTage = $('<img>').attr({
		"id" : "target",
		"style" : "display:none",
	});

	// outer.append(jcExample).append(article).append(imageTage);

	inputHiddenDiv.append(inputHiddenFile);

	var uploadBtn = $('<div>').attr({
		"class" : "prof-btn upload-btn float-left"

	}).click(uploadeImage).html("upload");

	uploadBottomContianer.append(uploadBtn).append(inputHiddenDiv);

	rowCol2.append(uploadPicPlaceholder).append(uploadBottomContianer);

	return row.append(rowCol1).append(rowCol2);// .append(outer);
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
	var dobInput = $('<input>').attr({
		"class" : "prof-form-input date-picker",
		"placeholder" : "MM/DD/YYYY",
		"value" : dob,
		"id" : "dateOfBirthId"
	}).datepicker({
		orientation : "top auto",
		autoclose : true
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
	}).html("Primary Email");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : user.emailId,
		"id" : "priEmailId",
		"onblur" : "emailValidation(this.value)"
	});
	rowCol2.append(emailInput);
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

	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : user.customerDetail.secEmailId,
		"id" : "secEmailId"
	});
	rowCol2.append(emailInput);
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
	var cityInput = $('<input>').attr({
		"class" : "prof-form-input",
		"value" : user.customerDetail.addressCity,
		"id" : "cityId"
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
	var zipInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-sm",
		"value" : user.customerDetail.addressZipCode,
		"id" : "zipcodeId"
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
	}).html("Primary Phone");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var phone1Input = $('<input>').attr({
		"class" : "prof-form-input",
		"value" : user.phoneNumber,
		"id" : "priPhoneNumberId"
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
	}).html("Secondary Phone");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});
	var phone2Input = $('<input>').attr({
		"class" : "prof-form-input",
		"value" : user.customerDetail.secPhoneNumber,
		"id" : "secPhoneNumberId"
	});
	rowCol2.append(phone2Input);
	return row.append(rowCol1).append(rowCol2);
}

function updateUserDetails() {

	/*
	 * alert(validationFails);
	 * 
	 * if(validationFails){ return false; }
	 */

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

	// ajaxRequest("rest/userprofile/updateprofile", "POST", "json",
	// JSON.stringify(userProfileJson),function(response){});

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

	showToastMessage("Succesfully updated");
}

/*
 * function uploadImageFunction(obj){
 * 
 * alert(obj); var urlToHit="rest/userprofile/uploadCommonImageToS3";
 * fileUpload($('#fullImageFormId'), urlToHit,'profilePic', '', '1'); $.ajax( {
 * url: urlToHit, type: "POST", headers : { "Accept" : "application/json;
 * charset=utf-8"
 *  }, data: new FormData(obj), success : function(response) { alert("success"); },
 * 
 * error : function(e) { alert("error.."); }, processData: false, contentType:
 * false } );
 *  }
 */

function fileUpload(form, action_url, img_div_id, message_div_id, suffix,
		userId) {
	// Create the iframe...

	var iframe = document.createElement("iframe");
	iframe.setAttribute("id", "upload_" + suffix);
	iframe.setAttribute("name", "upload_" + suffix);
	iframe.setAttribute("width", "0");
	iframe.setAttribute("height", "0");
	iframe.setAttribute("border", "0");
	iframe.setAttribute("style", "width: 0; height: 0; border: none;");

	// Add to document...
	form.parentNode.appendChild(iframe);
	window.frames['upload_' + suffix].name = "upload_" + suffix;

	iframeId = document.getElementById("upload_" + suffix);

	// Add event...
	var eventHandler = function() {

		if (iframeId.detachEvent)
			iframeId.detachEvent("onload", eventHandler);
		else
			iframeId.removeEventListener("load", eventHandler, false);

		// Message from server...
		if (iframeId.contentDocument) {
			content = iframeId.contentDocument.body.innerHTML;
		} else if (iframeId.contentWindow) {
			content = iframeId.contentWindow.document.body.innerHTML;
		} else if (iframeId.document) {
			content = iframeId.document.body.innerHTML;
		}

		// here is content
		// alert("content=="+content);

		// $("#fileUploadId").val($("#uploadFile").val());

		// $("#fileId").html($("#originalImageId").html());

		if (message_div_id != "") {
			document.getElementById(message_div_id).innerHTML = content;
		}
		if (content != "error" && img_div_id != "") {
			document.getElementById(img_div_id).style.backgroundImage = "url("
					+ content + ")";
			$("#myProfilePicture").css("background", "url(" + content + ")");
			//

			createCropDiv('imageCropContainerDiv', content);
			$('#loaderWrapper').hide();
		}
		// Del the iframe...
		setTimeout('iframeId.parentNode.removeChild(iframeId)', 250);
		// iframeId.parentNode.removeChild(iframeId);
	}

	if (iframeId.addEventListener)
		iframeId.addEventListener("load", eventHandler, true);
	if (iframeId.attachEvent)
		iframeId.attachEvent("onload", eventHandler);

	// Set properties of form...
	form.setAttribute("target", "upload_" + suffix);
	form.setAttribute("action", action_url);
	form.setAttribute("method", "post");
	form.setAttribute("enctype", "multipart/form-data");
	form.setAttribute("encoding", "multipart/form-data");

	// Submit the form...
	form.submit();
	/*
	 * form.removeAttribute("target"); form.removeAttribute("action");
	 * form.removeAttribute("method"); form.removeAttribute("enctype");
	 * form.removeAttribute("encoding");
	 */
	if (message_div_id != "") {
		document.getElementById(message_div_id).innerHTML = "Uploading...";
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


function cancelUploadPhoto(){
	
	//$("#popup-overlay").css("display", "none");
	//$('#pu-img').attr('src', "");
}
