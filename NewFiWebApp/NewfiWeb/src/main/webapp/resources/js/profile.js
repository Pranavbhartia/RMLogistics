
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

/*function getUserProfileData() {
	ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {},
			appendCustPersonalInfoWrapper);
}
*/

function getUserProfileData(){
	ajaxRequest("rest/userprofile/completeprofile", "GET", "json", {}, appendCustPersonalInfoWrapper);
}

function userProfileData(data){

	showCustomerProfilePageCallBack(data);
}

function paintCutomerProfileContainer() {
	$('#profile-main-container').html('');
	//getUserProfileData();
}

function appendCustPersonalInfoWrapper(user) {

	var wrapper = $('<div>').attr({
		"class" : "cust-personal-info-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "cust-personal-info-header"
	}).html("Personal Information");

	var container = getCustPersonalInfoContainer(user);

	wrapper.append(header).append(container);
	$('#profile-main-container').append(wrapper);
	$("#uploadFile")
	.change(
			function() {
				
				fileUpload(this.form, 'uploadCommonImageToS3.do','profilePic', '', '1',user.id);

				// $('#path').val($(this).val());
			});

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
	
	var uploadPicPlaceholder ;
	if(user.photoImageUrl == "" || user.photoImageUrl == null || user.photoImageUrl =='undefined'){
		uploadPicPlaceholder = $('<div>').attr({
			"id" : "profilePic",
			"class" : "prof-pic-upload-placeholder float-left"
		});
		
	}else{
		 uploadPicPlaceholder = $('<div>').attr({
			"id" : "profilePic",
			"class" :"prof-pic-upload-placeholder float-left",
			"style":"background:url("+user.photoImageUrl+")"
		});
	}
	
	
	var imageForm = $('<form>').attr({
		
	});
	
	var inputHiddenFile = $('<input>').attr({
		"type" : "file",
		"id" : "uploadFile",
		"name":"fileName"
		
	});
	
	var uploadImage = $('<div>').attr({
		"class" : "uploadImage"

	}).hide();

	imageForm.append(inputHiddenFile);
	uploadImage.append(imageForm);
	uploadPicPlaceholder.append(uploadImage);

	var uploadBottomContianer = $('<div>').attr({
		"class" : "clearfix"
	});

	var uploadBtn = $('<div>').attr({
		"class" : "prof-btn upload-btn float-left"

	}).click(uploadeImage).html("upload");
	// uploadBottomContianer.append(uploadPicCont).append(uploadBtn);
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
	
	var dob = $.datepicker.formatDate('mm/dd/yy', new Date(user.customerDetail.dateOfBirth));
	
	if(dob == null || dob =="" || dob =='NaN/NaN/NaN'){
		
		dob = "";	
	}
	var dobInput = $('<input>').attr(
			{
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
	}).html("Primery Email");
	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : user.emailId,
		"id" : "priEmailId"
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
	}).html("Phone 1");
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
	}).html("Phone 2");
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

	//ajaxRequest("rest/userprofile/updateprofile", "POST", "json", JSON.stringify(userProfileJson),function(response){});

	$.ajax({
		url : "rest/userprofile/updateprofile",
		type : "POST",
		data : {"updateUserInfo":JSON.stringify(userProfileJson)},
		dataType : "json",
		success : function(data) {
			
			$("#profileNameId").text($("#firstNameId").val());
			$("#profilePhoneNumId").text($("#priPhoneNumberId").val());
		
		},
		error : function(error) {
			alert("error"+error);
		}
	});
}

function uploadeImage() {

	$("#uploadFile").trigger('click');

}


function uploadImageFunction(obj){
		
		alert(obj);
		var urlToHit="rest/userprofile/uploadCommonImageToS3";
		fileUpload($('#fullImageFormId'), urlToHit,'profilePic', '', '1');
		/*$.ajax( {
		   url: urlToHit,
		   type: "POST",
			  headers : {
					"Accept" : "application/json; charset=utf-8"
					
				},
		   data: new FormData(obj),
			  success : function(response) {
					alert("success");
				},
				
				error : function(e) {
				alert("error..");
				},
				processData: false,
			    contentType: false
		 } );*/
	
}


function fileUpload(form, action_url, img_div_id,message_div_id,suffix,userId) {
    // Create the iframe...
	
    var iframe = document.createElement("iframe");
    iframe.setAttribute("id", "upload_"+suffix);
    iframe.setAttribute("name", "upload_"+suffix);
    iframe.setAttribute("width", "0");
    iframe.setAttribute("height", "0");
    iframe.setAttribute("border", "0");
    iframe.setAttribute("style", "width: 0; height: 0; border: none;");
 
    // Add to document...
    form.parentNode.appendChild(iframe);
    window.frames['upload_'+suffix].name = "upload_"+suffix;
 
    iframeId = document.getElementById("upload_"+suffix);
 
    // Add event...
    var eventHandler = function () {
 
            if (iframeId.detachEvent) iframeId.detachEvent("onload", eventHandler);
            else iframeId.removeEventListener("load", eventHandler, false);
 
            // Message from server...
            if (iframeId.contentDocument) {
                content = iframeId.contentDocument.body.innerHTML;
            } else if (iframeId.contentWindow) {
                content = iframeId.contentWindow.document.body.innerHTML;
            } else if (iframeId.document) {
                content = iframeId.document.body.innerHTML;
            }
            
           //here is content
          // alert("content=="+content);
		  if(message_div_id!=""){
		  document.getElementById(message_div_id).innerHTML = content;
		  }
           if(content!="error" && img_div_id!=""){
		  document.getElementById(img_div_id).style.backgroundImage="url("+content+")";
		  $("#myProfilePicture").css("background","url("+content+")");
		  $('#loaderWrapper').hide();
		  } 
            // Del the iframe...
            setTimeout('iframeId.parentNode.removeChild(iframeId)', 250);
           // iframeId.parentNode.removeChild(iframeId);
        }
 
    if (iframeId.addEventListener) iframeId.addEventListener("load", eventHandler, true);
    if (iframeId.attachEvent) iframeId.attachEvent("onload", eventHandler);
 
    // Set properties of form...
    form.setAttribute("target", "upload_"+suffix);
    form.setAttribute("action", action_url);
    form.setAttribute("method", "post");
    form.setAttribute("enctype", "multipart/form-data");
    form.setAttribute("encoding", "multipart/form-data");
 
    // Submit the form...
    form.submit();
    /*
    form.removeAttribute("target");
    form.removeAttribute("action");
    form.removeAttribute("method");
    form.removeAttribute("enctype");
    form.removeAttribute("encoding");
    */
	if(message_div_id!=""){
    document.getElementById(message_div_id).innerHTML = "Uploading...";
	}
		
}
