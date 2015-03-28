$(document).on('click',function(e){
	if($('#alert-popup-wrapper-settings').css("display") == "block"){
		hideSettingsPopup();
	}
	if ($('#admin-create-user-popup').css("display") == "block") {
		hideCreateUserPopup();
	}
});

$(document).on('click', '#admin-create-user-popup', function(event) {
	event.stopImmediatePropagation();
});
function paintAdminDashboard(){

$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	var agentDashboardMainContainer = $('<div>').attr({
		"id" : "admin-dashboard-container",
		"class" : "rp-agent-dashboard-admin float-left-admin"
	});
	$('#right-panel').append(agentDashboardMainContainer);
    getAdminDashboardRightPanel();


}
function getAdminDashboardRightPanel() {
	var userID = newfiObject.user.id;
	ajaxRequest("rest/loan/retrieveDashboard/" + userID, "GET", "json", {},
			adminDashboardRightPanel);
}

function adminDashboardRightPanel(data){
var response=data.resultObject;
appendCustomersForUserManagement(response.customers);


}

function appendCustomersForUserManagement(customers){

	for (var i = 0; i < customers.length; i++) {

		var customer = customers[i];
		entryPointForUserViewForAdmin(customer.loanID, '2');
		}

}


function entryPointForUserViewForAdmin(loanID) {

		ajaxRequest("rest/loan/" + loanID + "/retrieveDashboard", "GET",
				"json", undefined, function(response) {

					entryPointAdminViewChangeNav(loanID);

				});



}
function entryPointAdminViewChangeNav(loanID) {

	getLoanDetailsForAdmin(loanID);
}

function getLoanDetailsForAdmin(loanID) {
	ajaxRequest("rest/loan/" + loanID, "GET", "json", {}, paintAdminUserPage);
}

function paintAdminUserPage(data) {

	var loanDetails = data.resultObject;	
	appendAddUserWrapper('admin-dashboard-container');
	appendNewfiTeamWrapperForAdmin(loanDetails);
	/* var contxt = getContext(loanDetails.id + "-notification");
	if (contxt) {
		contxt.populateLoanNotification();
	} else {
		contxt = getNotificationContext(loanDetails.id, 0);
		contxt.getNotificationForLoan(function(ob) {
			contxt.populateLoanNotification();
		});
	} */

}

function appendAddUserWrapper(parentElement,clearParent,data) {
	var wrapper = $('<div>').attr({
		"class" : "add-team-mem-wrapper"
	}).data("additionalData",data);

	var header = $('<div>').attr({
		"class" : "admin-add-team-mem-header clearfix",
		"id":"admin-user-management-header"
	}).html("Add User");

	var rightHeaderIcon = $('<div>').attr({
		"class" : "header-down-icn float-right"
	});

	header.append(rightHeaderIcon);

	var container = $('<div>').attr({
		"class" : "admin-add-team-mem-container clearfix"
	});

	var userTypeCont = $('<div>').attr({
		"class" : "add-member-input-admin-cont float-left clearfix"
	}).html("User Type");

	var userTypeSel = $('<div>').attr({
		"id" : "add-memeber-user-type",
		"class" : "add-member-sel-admin float-right"
	}).on('click',userTypeClicked);

	userTypeCont.append(userTypeSel);
	
	var createUserButton=$('<div>').attr({
	"class":"prof-cust-save-btn-admin-um float-left-admin",
	"id":"create-user-id",
    
	}).html("Create User").click(function(e){
		showAddUserPopUp(e);

	});	

	var userNameSel = $('<div>').attr({
		"id" : "add-member-sel"
	});
	createUserButton.append(userNameSel); 
	

	container.append(userTypeCont);
	container.append(createUserButton); 
	wrapper.append(header).append(container);
	if(clearParent){
		$('#'+parentElement).html("");
	}
	$('#'+parentElement).append(wrapper);

	// function to append create user popup
	appendAdminCreateUserPopup();
	appendUserTypeDropDown();

}
function appendAdminCreateUserPopup(){
var popUpWrapper = $('<div>').attr({
		"id" : "admin-create-user-popup",
		"class" : "pop-up-wrapper create-user-popup hide"
	});

	var header = $('<div>').attr({
		"class" : "pop-up-header"
	}).html("Create User");

	var container = $('<div>').attr({
		"id" : "create-user-container",
		"class" : "pop-up-container clearfix"
	});
	popUpWrapper.append(header).append(container);
	$('#add-member-sel').append(popUpWrapper);

	appendCreateUserPopupFirstName();
	appendCreateUserPopupLastName();
	// TODO-decide what needs to be saved for internal users and realtors
	// appendCreateUserPopupStreetAddr();
	// appendCreateUserPopupCity();
	// appendCreateUserPopupState();
	// appendCreateUserPopupZip();
	// appendCreateUserPopupDOB();
	appendCreateUserPopupEmail();

	// save button
	var saveBtn = $('<div>').attr({
		"class" : "prof-cust-save-btn"
	}).html("save").on(
			'click',
			function(event) {
				event.stopImmediatePropagation();

				var user = new Object();
				user.emailId = $('#create-user-emailId').val();
				user.firstName = $('#create-user-first-name').val();
				user.lastName = $('#create-user-last-name').val();
				console.log("Create user button clicked. User : "
						+ JSON.stringify(user));

				if (user.firstName == "") {
					showToastMessage("First name cannot be empty");
					return;
				} else if (user.lastName == "") {
					showToastMessage("Last name cannot be empty");
					return;
				} else if (user.emailId == "") {
					showToastMessage("Email ID cannot be empty");
					return;
				}
				user.userRole = {
					id : $("#add-memeber-user-type").attr("roleid")
				};
				if ($("#add-memeber-user-type").attr("roleid") == "3") {
					user.internalUserDetail = {
						internalUserRoleMasterVO : {
							id : $("#add-memeber-user-type").attr(
									"internalroleid")
						}
					}
				}
				;
				createUserAndAddToLoanTeam(user);

			});

	$('#admin-create-user-popup').append(saveBtn);



}
function appendNewfiTeamWrapperForAdmin(loanDetails) {
	var team = loanDetails.loanTeam;
	var loanID = loanDetails.id;
	var wrapper = $('<div>').attr({
		"class" : "admin-newfi-team-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "agent-wrapper-header agent-wrapper-header-active"
	}).html("Newfi Team");

	var searchDiv=$('<div>').attr({
    "class":"admin-search-icn float-right-admin",
    "id":"searchText"
    }).on('click', function() {

	$(this).parent().find('.admin-search-input').show().focus();
     if($('#search-id').val()!="" && $('#search-id').val()!=undefined){
	 var val=$('#search-id').val();
		alert(val);
	$('#search-id').val('');
	 	$(this).parent().find('.admin-search-input').hide();
	 }     
	

	});
	
	var searchInputBox = $('<input>').attr({
		"class" : "admin-search-input float-left hide",
		"id":"search-id",
		"placeholder":"search",
		"name":"search"
	}).on('keyup', function(e) {
		if (e.which == 13) {
			$(this).hide();
			
		}
	
	}).on('blur', function() {
		$(this).hide();
		
	});
	searchDiv.append(searchInputBox);
	header.append(searchDiv);
	var container = $('<div>').attr({
		"class" : "newfi-team-container"
	});

	var tableHeader = getAdminTeamListTableHeader();
	container.append(tableHeader);

	for (var i = 0; i < team.length; i++) {
		var tableRow = getAdminTeamListTableRow(team[i], loanID);
		container.append(tableRow);
	}

	wrapper.append(header).append(container);
	$('#admin-dashboard-container').append(wrapper);

}


function getAdminTeamListTableHeader() {
	var tableHeaderRow = $('<div>').attr({
		"class" : "admin-newfi-team-list-th clearfix"
	});

	var thCol1 = $('<div>').attr({
		"class" : "admin-newfi-team-list-th-col1  float-left"
	}).html("User Name");

	var thCol2 = $('<div>').attr({
		"class" : "admin-newfi-team-list-th-col2  float-left"
	}).html("User Type");

	var thCol3 = $('<div>').attr({
		"class" : "admin-newfi-team-list-th-col3  float-left"
	}).html("Email");	

	var thCol4=$('<div>').attr({
	
	"class" : "admin-newfi-team-list-th-col4  float-left"
	
	}).html("Status");
	
	var thCol5=$('<div>').attr({
	
	"class" : "admin-newfi-team-list-th-col5  float-left"
	
	});
	var thCol6=$('<div>').attr({
	
	"class" : "admin-newfi-team-list-th-col6  float-left"
	
	});
	return tableHeaderRow.append(thCol1).append(thCol2).append(thCol3).append(thCol4).append(thCol5).append(thCol6);
}

function getAdminTeamListTableRow(user, loanID) {
	var tableRow = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr clearfix",
		"userid" : user.id
	});

	var trCol1 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col1 float-left"
	}).html(user.firstName + " " + user.lastName);

	var trCol2 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col2 float-left"
	});

	var userRoleStr = user.userRole.label;
	// TODO -- remove hard coding for internal user
	if (user.userRole.id == 3) {
		// userRoleStr =
		// user.internalUserDetail.internalUserRoleMasterVO.roleDescription;
		var intRoleID = user.internalUserDetail.internalUserRoleMasterVO.id;
		for (j in newfiObject.internalUserRoleMasters) {
			var intMaster = newfiObject.internalUserRoleMasters[j];
			if (intMaster.id == intRoleID)
				userRoleStr = intMaster.roleDescription;
		}
	}

	trCol2.html(userRoleStr);

	var trCol3 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col3 float-left"
	}).html(user.emailId);

	var labelActive=$('<label>').attr({			
	"class":"admin-label-radio-active"
	}).text("Active");
	
	var labelInActive=$('<label>').attr({			
	"class":"admin-label-radio-in-active"
	}).text("InActive");
	
	var trCol4 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col4 float-left"
	});
	var inputActive=$('<input>').attr({
	"class":"radio-btn-admin-active",
	"id":"myRadio-active",
	"type":"radio"
	});
    trCol4.append(inputActive).append(labelActive);
	var trCol5 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col5 float-left"
	});
    var inputInActive=$('<input>').attr({
	"class":"radio-btn-admin-in-active",
	"id":"myRadio-inactive",
	"type":"radio"
	});
	trCol5.append(inputInActive).append(labelInActive);
	
    var trCol6 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col6 float-left"
	});
	
	
	var userDelIcn = $('<div>').attr({
		"class" : "user-del-icn",
		"userid" : user.id,
		"loanID" : loanID
	});
	userDelIcn.click(function() {
		var userID = $(this).attr("userid");
		var loanID = $(this).attr("loanid");
		confirmRemoveUser("Are you sure you want to delete the user?",userID, loanID);
	});
	trCol6.append(userDelIcn);
	return tableRow.append(trCol1).append(trCol2).append(trCol3).append(trCol4)
			.append(trCol5).append(trCol6);
}
 $('#alert-settings-btn').click(function(e){
 e.stopImmediatePropagation();
	if($(this).has('#alert-popup-wrapper-settings').length == 1){
		if($('#alert-popup-wrapper-settings').css("display") == "block"){
			hideSettingsPopup();
		}else{
			showSettingsPopup();
		}
	}else{		
	if(newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleDescription=="Sales Manager"){
	var alertWrapper = $('<div>').attr({
		"id" : "alert-popup-wrapper-settings",
		"class" : "alert-popup-wrapper-settings"
	});
	var icondiv=$('<div>').attr({
	"class":"float-left-icon"
	
	});
	 var icon=$('<i>').attr({
	 "class":"settings-icon"	 	 
	 });
	 icondiv.append(icon);
	var header = $('<div>').attr({
		"class" : "admin-pop-up-header"
	}).html("Admin Settings");

	var container = $('<div>').attr({
		"id" : "admin-module-container",
		"class" : "admin-pop-up-container clearfix"
	});
	
	var userMangementRow = $('<div>').attr({
		"class" : "admin-module-row clearfix",
		"id":"user-management"
	});
	var anchortag1=$('<a>').attr({
	"id" : "user-mangement-id",	
    "href":"#",
    "onclick":"userManagement()"	
	});
    var userMangementRowContent = $('<div>').attr({
		"class" : "admin-module-row-content",
			
	}).html("User Mangement");
	
	anchortag1.append(userMangementRowContent);
	userMangementRow.append(anchortag1);
	container.append(userMangementRow);
	
	//TODO for loan details
	var loanDetailRow = $('<div>').attr({
		"class" : "admin-module-row clearfix",
		"id":"loan-detail"
	});
	var anchortag2=$('<a>').attr({
	"id" : "loan-detail-id",
    "href":""	
	});
    var loanDetailRowContent = $('<div>').attr({
		"class" : "admin-module-row-content",
			
	}).html("Loan Details");
	anchortag2.append(loanDetailRowContent);
	loanDetailRow.append(anchortag2);
	container.append(loanDetailRow);
	
	//TODO for templates
	var templatesRow = $('<div>').attr({
		"class" : "admin-module-row clearfix",
		"id":"loan-detail"
	});
	var anchortag3=$('<a>').attr({
	"id" : "templates",
    "href":""	
	});
    var templatesRowContent = $('<div>').attr({
		"class" : "admin-module-row-content",
			
	}).html("Templates");
	anchortag3.append(templatesRowContent);
	templatesRow.append(anchortag3);
	container.append(templatesRow);
	
	//TODO for security settings
	var securitySettingsRow = $('<div>').attr({
		"class" : "admin-module-row clearfix",
		"id":"loan-detail"
	});
	var anchortag4=$('<a>').attr({
	"id" : "security-settings",
    "href":""	
	});
    var securitySettingsRowContent = $('<div>').attr({
		"class" : "admin-module-row-content",
			
	}).html("Security Settings");
	anchortag4.append(securitySettingsRowContent);
	securitySettingsRow.append(anchortag4);
	container.append(securitySettingsRow);
	header.append(icondiv);
	alertWrapper.append(header).append(container);
	$('#alert-settings-btn').append(alertWrapper);
	
	}
	
	}
});

function appendAdminModuleDetails(){

var row = $('<div>').attr({
		"class" : "admin-module-row clearfix"
	});
var divContent = $('<div>').attr({
		"class" : "admin-module-row-content",
		"id" : id,
		
	}).html(value);

}
function hideSettingsPopup(){

$('#alert-popup-wrapper-settings').hide();
}

function showSettingsPopup(){

$('#alert-popup-wrapper-settings').show();
}
//TODO to load user mangement page
function userManagement(){
$('#right-panel').html('');
	paintAdminDashboard();
	
}
//TODO to search/filter users in the list
$('#search-input-area-id').click(function(e){



});

//TODO to create a user
$('#create-user-id').click(function(e){

	showAddUserPopUp(e);

});

//TODO to change the status of user active/inactive
$("#myRadio-active").click(function(e){

$("#myRadio-active").attr("checked",true);
$("#myRadio-inactive").attr("checked",false);
alert("staus of user is active");

});

$("#myRadio-inactive").click(function(e){

$("#myRadio-inactive").attr("checked",true);
$("#myRadio-active").attr("checked",false);
alert("status of user is inactive");

});
