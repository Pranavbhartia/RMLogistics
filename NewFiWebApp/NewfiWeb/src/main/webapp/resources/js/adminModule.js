var userDescription="Sales Manager";
$(document).on('click',function(e){
	if($('#alert-popup-wrapper-settings').css("display") == "block"){
		hideSettingsPopup();
	}
	if ($('#admin-create-user-popup').css("display") == "block") {
		$('#admin-create-user-popup').hide();
	}
});

$(document).on('click', '#admin-create-user-popup', function(event) {
	event.stopImmediatePropagation();
});
$('#alert-settings-btn').click(function(e){
 e.stopImmediatePropagation();
	if(userDescription==newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleDescription){
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


//TODO to load user mangement page
function userManagement(){
$('#right-panel').html('');
	paintAdminDashboard();
	
}

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
//TODO to delete and change to rest which retrieves all users(only write onefunction to retreive all users)
function getAdminDashboardRightPanel() {

	ajaxRequest("rest/userprofile/getUsersList", "GET", "json", {},
			adminDashboardRightPanel);
}

function adminDashboardRightPanel(data){

paintAdminUserPage(data);




}

function paintAdminUserPage(data) {

	var userDetails = data.resultObject;
//alert(userDetails);	
	appendAdminAddUserWrapper('admin-dashboard-container');
	appendNewfiTeamWrapperForAdmin(userDetails);
}

function appendAdminAddUserWrapper(parentElement,clearParent,data) {
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
		"id" : "admin-add-memeber-user-type",
		"class" : "add-member-sel-admin float-right"
	}).on('click',function(e){
	if($('#admin-add-usertype-dropdown-cont').css("display")=="block"){
	$('#admin-add-usertype-dropdown-cont').hide();
	}else{
	appendAdminUserTypeDropDown();
	$('#admin-add-usertype-dropdown-cont').show();
	}
	
	});

	
	var spanLabel=$('<span>').attr({
	"class":"admin-span-class",
	
	
	}).html("Please Select");
	userTypeSel.append(spanLabel);
	userTypeCont.append(userTypeSel);
	
	var createUserButton=$('<div>').attr({
	"class":"prof-cust-save-btn-admin-um float-left-admin",
	"id":"create-user-id",
    
	}).html("Create User").click(function(e){
		showAddUserPopUp(e);

	});	

	var uploadCSV=$('<div>').attr({
	"class":"prof-cust-upload-btn-admin-um float-left-admin",
	"id":"upload-csv-id", 
	"type":"file",
	"name":"file"

	}).click(function(e){
	$(this).submit();
		
	});
	
	var label=$('<label>').attr({
	
	"class":"admin-label-class float-left",
	
	
	}).html("(or)");
	
	var uploadText=$('<div>').attr({
	
	"class":"admin-upload-label",
	
	
	}).html("Click to upload CSV");
	
	container.append(userTypeCont);
	container.append(createUserButton); 
	container.append(label);

	container.append(uploadText).append(uploadCSV);
	wrapper.append(header).append(container);
	if(clearParent){
		$('#'+parentElement).html("");
	}
	$('#'+parentElement).append(wrapper);

	// function to append create user popup
	appendAdminCreateUserPopup();
	appendAdminUserTypeDropDown();

}

function appendAdminUserTypeDropDown(){
var dropdownCont = $('<div>').attr({
		"id" : "admin-add-usertype-dropdown-cont",
		"class" : "admin-add-member-dropdown-cont hide",
		
	});

	var userRoles = [ {
		"id" : 2,
		"internalRoleID" : 0,
		"roleCd" : "Realtor",
		"label" : "Realtor",
		"roleDescription" : "Realtor",
		"userRoleBased" : "true"
	} ];

	if(newfiObject.user.userRole.roleCd=="INTERNAL")
	for (i in newfiObject.internalUserRoleMasters) {
		var internalRole = newfiObject.internalUserRoleMasters[i];
		userRoles.push({
			"id" : 3,
			"internalRoleID" : internalRole.id,
			"label" : internalRole.roleDescription,
			"userRoleBased" : "true"
		});
	}
	
	// If the user is a borrower, allow to add a title company as well as home
	// insurance option
	if (newfiObject.user.userRole.roleCd == "CUSTOMER") {

		var custOptions = [ {
			"label" : "Title Company",
			"roleDescription" : "TITLE COMPANY",
			"code" : "TITLE_COMPANY",
			"userRoleBased" : "false"
		}, {
			"label" : "Home Owner's Insurance",
			"roleDescription" : "Home own ins",
			"code" : "HOME_OWN_INS",
			"userRoleBased" : "false"
		} ];
			for(j in custOptions)
			userRoles.push(custOptions[j]);

	}

	for (var i = 0; i < userRoles.length; i++) {
		var userRole = userRoles[i];
		var dropDownRow = $('<div>').attr({
			"class" : "admin-add-member-dropdown-row",
			"roleID" : userRole.id,
			"internalRoleID" : userRole.internalRoleID,
			"roleCD" : userRole.roleCD,
			"code"	: userRole.code,
			"userRoleBased":userRole.userRoleBased
		}).html(userRole.label)
				.on(
						'click',
						function(event) {
							event.stopImmediatePropagation();
							var roleIDCurr = $(this).attr("roleID");
							var code = $(this).attr("code");
							var roleIDPrev = $('#admin-add-memeber-user-type').attr(
									"roleID");
							$('#admin-add-memeber-user-type').attr("code",
									code);
							$('#admin-add-memeber-user-type').attr("roleID",
									roleIDCurr);
							$('#admin-add-memeber-user-type').attr("internalRoleID",
									$(this).attr("internalRoleID"));
							$('#admin-add-memeber-user-type').attr("userRoleBased",
									$(this).attr("userRoleBased"));
							$('#admin-add-memeber-user-type').html($(this).html());
							hideUserTypeDropDown();
						});
		dropdownCont.append(dropDownRow);
	}

		

	$('#admin-add-memeber-user-type').append(dropdownCont);

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
		"id" : "admin-create-user-container",
		"class" : "pop-up-container clearfix"
	});
	popUpWrapper.append(header).append(container);
	$('#create-user-id').append(popUpWrapper);

	appendAdminCreateUserPopupFirstName();
	appendAdminCreateUserPopupLastName();
	appendAdminCreateUserPopupEmail();

	// save button
	var saveBtn = $('<div>').attr({
		"class" : "prof-cust-save-btn"
	}).html("save").on(
			'click',
			function(event) {
				event.stopImmediatePropagation();

				var user = new Object();
				user.emailId = $('#admin-create-user-emailId').val();
				user.firstName = $('#admin-create-user-first-name').val();
				user.lastName = $('#admin-create-user-last-name').val();
				console.log("Create user button clicked. User : "
						+ JSON.stringify(user));
						console.log($('#admin-create-user-emailId').val()+"  "+$('#admin-create-user-first-name').val()+""+$('#admin-create-user-last-name').val());

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
					id : $("#admin-add-memeber-user-type").attr("roleid")
				};
				if ($("#admin-add-memeber-user-type").attr("roleid") == "3") {
					user.internalUserDetail = {
						internalUserRoleMasterVO : {
							id : $("#admin-add-memeber-user-type").attr(
									"internalroleid")
						}
					}
				}
				;
				//TODO  to create function to call create user rest 		
				createUserFromAdmin(user);

			});

	$('#admin-create-user-popup').append(saveBtn);



}

//TODO function to create single user 
function createUserFromAdmin(user){
ajaxRequest("rest/userprofile/adduser", "POST", "json", JSON.stringify(user),
			appendDataToNewfiTeamWrapperForAdmin);



}
function appendDataToNewfiTeamWrapperForAdmin(data){
$('#admin-add-usertype-dropdown-cont').hide();
   var users = data.resultObject;
	var wrapper = $('<div>').attr({
		"class" : "admin-newfi-team-wrapper"
	});
	var container = $('<div>').attr({
		"class" : "admin-newfi-team-container"
	});


   var tableRow = getAdminTeamListTableRow(users);
   container.append(tableRow);
 

	wrapper.append(container);
	$('#admin-newfi-team-container').append(wrapper);
	showToastMessage("User Created successfully");

}
function appendAdminCreateUserPopupFirstName(){
var row = $('<div>').attr({
		"class" : "create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "create-user-popup-label float-left"
	}).html("First Name");
	
	var inputBox = $('<input>').attr({
		"class" : "create-user-popup-input",
		"id" : "admin-create-user-first-name"
	}).val("");
	row.append(label).append(inputBox);
	$('#admin-create-user-container').append(row);

}

function appendAdminCreateUserPopupLastName(){
var row = $('<div>').attr({
		"class" : "create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "create-user-popup-label float-left"
	}).html("Last Name");
	var inputBox = $('<input>').attr({
		"class" : "create-user-popup-input",
		"id" : "admin-create-user-last-name"
	}).val("");
	row.append(label).append(inputBox);
	$('#admin-create-user-container').append(row);

}

function appendAdminCreateUserPopupEmail(){
	var row = $('<div>').attr({
		"class" : "create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "create-user-popup-label float-left"
	}).html("Email");
	var inputBox = $('<input>').attr({
		"class" : "create-user-popup-input",
		"id" : "admin-create-user-emailId",
		
	}).val("");
	row.append(label).append(inputBox);
	$('#admin-create-user-container').append(row);

}

function appendNewfiTeamWrapperForAdmin(userDetails) {
	var users = userDetails;

	console.log("users list",users);
	
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
		"class" : "admin-newfi-team-container"
	});

	var tableHeader = getAdminTeamListTableHeader();
	container.append(tableHeader);

for(var i=0;i<users.length;i++){
var tableRow = getAdminTeamListTableRow(users[i]);
		container.append(tableRow);}
 

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

	return tableHeaderRow.append(thCol1).append(thCol2).append(thCol3).append(thCol4).append(thCol5);
}

function getAdminTeamListTableRow(user) {
console.log("user",user);
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
	

var userRoleStr;
	if (user.userRole.id == 3) {
		var intRoleID = user.internalUserDetail.internalUserRoleMasterVO.id;
		for (j in newfiObject.internalUserRoleMasters) {
			var intMaster = newfiObject.internalUserRoleMasters[j];
			if (intMaster.id == intRoleID)
				userRoleStr = intMaster.roleDescription;
		}
	}else{
	userRoleStr=user.userRole.label;
	
	}

	trCol2.html(userRoleStr);

	var trCol3 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col3 float-left"
	}).html(user.emailId);	
	
	var inputActive=$('<div>').attr({
	"class":"admin-btn-active",
	"id":"admin-status-active"
	});
	var inputInActive=$('<div>').attr({
	"class":"admin-btn-in-active",
	"id":"admin-status-inactive"
	});
	
	var trCol4 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col4 float-left",
		"value":user.status,
		
	});
	if(user.status==true){
	console.log("status of user",user.status);
	trCol4.append(inputActive);

	
	}else{
	console.log("status of user in",user.status);
	trCol4.append(inputInActive);
 
	}
	
    inputActive.click(function(){
/* 	 $('.admin-btn-active').hide();
	 $('.admin-btn-in-active').show();  	 */
	 alert("Status changed from active to inactive");
	});
	
	inputInActive.click(function(){
/* 		$('.admin-btn-in-active').hide();
	$('.admin-btn-active').show();  */
	alert("Status changed from inactive to active");
	
	});
	
	var trCol5 = $('<div>').attr({
		"class" : "admin-newfi-team-list-tr-col5 float-left"
	});

	var userDelIcn = $('<div>').attr({
		"class" : "user-del-icn",
		"userid" : user.id,
	
	});

	userDelIcn.click(function() {
		var userID = $(this).attr("userid");
		//var loanID = $(this).attr("loanid");
		//confirmRemoveUser("Are you sure you want to delete the user?",userID, loanID);
	});
	trCol5.append(userDelIcn);
	return tableRow.append(trCol1).append(trCol2).append(trCol3).append(trCol4)
			.append(trCol5);
}


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
