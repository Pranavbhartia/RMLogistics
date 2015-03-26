$(document).on('click',function(e){
	if($('#alert-popup-wrapper-settings').css("display") == "block"){
		hideSettingsPopup();
	}
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


function entryPointForUserViewForAdmin(loanID, viewName) {

		ajaxRequest("rest/loan/" + loanID + "/retrieveDashboard", "GET",
				"json", undefined, function(response) {

					entryPointAdminViewChangeNav(loanID,viewName);

				});



}
function entryPointAdminViewChangeNav(loanID,viewName) {

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
		"class" : "add-team-mem-header clearfix",
		"id":"admin-user-management-header"
	}).html("Add User");

	var rightHeaderIcon = $('<div>').attr({
		"class" : "header-down-icn float-right"
	});

	header.append(rightHeaderIcon);

	var container = $('<div>').attr({
		"class" : "add-team-mem-container clearfix"
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
	"class":"prof-cust-save-btn-admin-um float-right-admin",
	"id":"create-user-id",

	}).html("Create User");	
	container.append(createUserButton);
	container.append(userTypeCont);
	wrapper.append(header).append(container);
	if(clearParent){
		$('#'+parentElement).html("");
	}
	$('#'+parentElement).append(wrapper);

	// function to append create user popup
	
	appendUserTypeDropDown();

}

function appendNewfiTeamWrapperForAdmin(loanDetails) {
	var team = loanDetails.loanTeam;
	var loanID = loanDetails.id;
	var wrapper = $('<div>').attr({
		"class" : "newfi-team-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "newfi-team-header"
	}).html("Newfi Team");

	var searchLabel=$('<label>').attr({	
	"class":"search-label-class float-right-admin"
	}).html("Search");
	
	var serachInput=$('<input>').attr({
	"class":"search-input-area float-right-admin",
	"id":"search-input-area-id",
	"name":"searchBox",
	
	});
	header.append(serachInput);
	header.append(searchLabel);
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

$('#create-user-id').click(function(e){
alert("in create user function");
showAddUserPopUp(e);

});
function getAdminTeamListTableHeader() {
	var tableHeaderRow = $('<div>').attr({
		"class" : "newfi-team-list-th clearfix"
	});

	var thCol1 = $('<div>').attr({
		"class" : "newfi-team-list-th-col1 newfi-team-list-tr-col1 float-left"
	}).html("User Name");

	var thCol2 = $('<div>').attr({
		"class" : "newfi-team-list-th-col2 newfi-team-list-tr-col2 float-left"
	}).html("User Type");

	var thCol3 = $('<div>').attr({
		"class" : "newfi-team-list-th-col3 newfi-team-list-tr-col3 float-left"
	}).html("Email");
	
	var thCol4 = $('<div>').attr({
		"class" : "newfi-team-list-th-col4 newfi-team-list-tr-col4 float-left"
	}).html("Email");

	return tableHeaderRow.append(thCol1).append(thCol2).append(thCol3);
}

function getAdminTeamListTableRow(user, loanID) {
	var tableRow = $('<div>').attr({
		"class" : "newfi-team-list-tr clearfix",
		"userid" : user.id
	});

	var trCol1 = $('<div>').attr({
		"class" : "newfi-team-list-tr-col1 float-left"
	}).html(user.firstName + " " + user.lastName);

	var trCol2 = $('<div>').attr({
		"class" : "newfi-team-list-tr-col2 float-left"
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
		"class" : "newfi-team-list-tr-col3 float-left"
	}).html(user.emailId);

	var trCol4 = $('<div>').attr({
		"class" : "newfi-team-list-tr-col4 float-left"
	});
    //trCol4.append("<input type='radio' class='radio-btn-admin' id='myRadio-active'>").html("Active");
	var trCol5 = $('<div>').attr({
		"class" : "newfi-team-list-tr-col5 float-left"
	});
	//trCol5.append("<input type='radio' class='radio-btn-admin' id='myRadio-inactive'>").html("Inactive");
    var trCol6 = $('<div>').attr({
		"class" : "newfi-team-list-tr-col6 float-left"
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
	trCol5.append(userDelIcn);
	return tableRow.append(trCol1).append(trCol2).append(trCol3).append(trCol4)
			.append(trCol5);
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
	
    "href":"adminModule.do?userID="+newfi.user.id	
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