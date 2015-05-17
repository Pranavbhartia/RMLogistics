
var userDescription="System User";
var salesManager="Sales Manager";
var loanManagerID=1;
var statusActive="ACTIVE";
var statusInActive="INACTIVE";
var customer="Customer";

$(document).on('click',function(e){
	if($('#admin-add-usertype-dropdown-cont').css("display") == "block"){
		$('#admin-add-usertype-dropdown-cont').hide();
	}
	
	if($('#alert-popup-wrapper-settings').css("display") == "block"){
		hideSettingsPopup();
	}else{
		showSettingsPopup();
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
 if(newfiObject.user.internalUserDetail==undefined || newfiObject.user.internalUserDetail.internalUserRoleMasterVO.id==loanManagerID)
 {
	 if(newfiObject.user.userRole.roleDescription!=userDescription)
	 {
		//TODO for dropdown settings for LM,customer,realtor
			if($(this).has('#alert-popup-wrapper-settings').length == 1 ){
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
			}).html("User Settings");

			var container = $('<div>').attr({
				"id" : "admin-module-container",
				"class" : "admin-pop-up-container clearfix"
			});
			
			var myProfileRow = $('<div>').attr({
				"class" : "admin-module-row clearfix",
				"id":"my-profile"
			});
			var anchortag1=$('<a>').attr({
			"id" : "my-profile-id",	
		 "href":"#",
		 "onclick":"myProfile()"	
			});
		 var myProfileRowRowContent = $('<div>').attr({
				"class" : "admin-module-row-content",
					
			}).html("My Profile");
			
			anchortag1.append(myProfileRowRowContent);
			myProfileRow.append(anchortag1);
			container.append(myProfileRow);
			
			//TODO for change password
			var changepasswordRow = $('<div>').attr({
				"class" : "admin-module-row clearfix",
				"id":"change-password"
			});
			var anchortag2=$('<a>').attr({
			"id" : "change-password-id",
			"href":"#",
			"onclick":"myProfile()"	
			});
		 var changepasswordRowContent = $('<div>').attr({
				"class" : "admin-module-row-content",
					
			}).html("Change Password");
			anchortag2.append(changepasswordRowContent);
			changepasswordRow.append(anchortag2);
			container.append(changepasswordRow);
			
			//TODO for tutorials
			var tutorialsRow = $('<div>').attr({
				"class" : "admin-module-row clearfix",
				"id":"tutorials"
			});
			var anchortag3=$('<a>').attr({
			"id" : "templates",
		     "href":"#"	,
		     "onclick":"completeTutorials()"	
			});
		 var tutorialsRowContent = $('<div>').attr({
				"class" : "admin-module-row-content",
					
			}).html("Tutorial");
		 if(newfiObject.user.userRole.roleDescription==customer){
		 	 var tutorialStatus=JSON.parse(newfi.appUserDetails).user.customerDetail.tutorialStatus;
		 	 if(!tutorialStatus){
		 		 anchortag3.append(tutorialsRowContent);
					tutorialsRow.append(anchortag3);
					container.append(tutorialsRow); 
		 	 }
		 	
				
		 }
			header.append(icondiv);
			alertWrapper.append(header).append(container);
			$('#alert-settings-btn').append(alertWrapper);
			
			}
     }else{
    	 appendDropDownForAdmin() ;
     }
	
	}else{
		appendDropDownForAdmin();
	}
	 

});

function appendDropDownForAdmin(){
	if($('#alert-settings-btn').has('#alert-popup-wrapper-settings').length == 1){
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
	"href":"#turn_around_times_container",
   	"onclick":"turnAroundTime()"	
	});
    var loanDetailRowContent = $('<div>').attr({
		"class" : "admin-module-row-content",
			
	}).html("Turn Around Details");
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
        "href":"#"	,
        "onclick":"populateTemplate()"	
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
function completeTutorials(){
	window.location.href="#myLoan/myTeam";
}
function myProfile(){
	 window.location.href = "#myProfile";
}
/* Search for user list*/
function getSearchResultForAdmin(searchValue)
{
	$('#right-panel').html('');
	var agentDashboardMainContainer = $('<div>').attr({
		"id" : "admin-dashboard-container",
		"class" : "rp-agent-dashboard-admin float-left"
	});
	$('#right-panel').append(agentDashboardMainContainer);
     getSearchResultDataForAdmin(searchValue);


}

function getSearchResultDataForAdmin(searchValue)
{
	
	ajaxRequest("rest/userprofile/searchUserName?name="+searchValue,"GET", "json", {},
			adminDashboardRightPanel);


}


/*TODO to load user mangement page*/
function userManagement(){
$('#right-panel').html('');
	paintAdminDashboard();	
}

function paintAdminDashboard(){

$('.lp-right-arrow').remove();
	$('#right-panel').html('');
	var agentDashboardMainContainer = $('<div>').attr({
		"id" : "admin-dashboard-container",
		"class" : "rp-agent-dashboard-admin"
	});
	
	var agentDashboardErrorWrraper = $('<div>').attr({
		"id" : "admin-error-wrapper",
		"class" : "admin-error-wrapper"
	});
	
	var agentDashboardErrorContainer = $('<div>').attr({
		"id" : "admin-error-container",
		"class" : "admin-error-container"
	});
	agentDashboardErrorWrraper.append(agentDashboardErrorContainer);
	agentDashboardMainContainer.append(agentDashboardErrorWrraper);
	$('#right-panel').append(agentDashboardMainContainer);
    getAdminDashboardRightPanel();


}

function getAdminDashboardRightPanel() {

	ajaxRequest("rest/userprofile/getUsersList", "GET", "json", {},
			adminDashboardRightPanel);
}

function adminDashboardRightPanel(data){

paintAdminUserPage(data);




}
/*paint method for userManagement page*/
function paintAdminUserPage(data) {
	var userDetails = data.resultObject;
	appendAdminAddUserWrapper('admin-dashboard-container');
	appendNewfiTeamWrapperForAdmin(userDetails);
}

function appendAdminAddUserWrapper(parentElement,clearParent,data) {
	var wrapper = $('<div>').attr({
		"class" : "admin-add-team-mem-wrapper"
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


	var spanLabel=$('<span>').attr({
		"class":"admin-span-class float-left",		
		}).html("Please Select");
	var userTypeSel = $('<div>').attr({
		"id" : "admin-add-memeber-user-type",
		"class" : "add-member-sel-admin float-right"

	}).on('click',function(e){
		e.stopImmediatePropagation();
	if($('#admin-add-usertype-dropdown-cont').css("display")=="block"){
	$('#admin-add-usertype-dropdown-cont').hide();
	}else{
    appendAdminUserTypeDropDown(); 
	$('#admin-add-usertype-dropdown-cont').show();
	}	
	});

    userTypeSel.append(spanLabel);
	userTypeCont.append(userTypeSel);
	
	var createUserButton=$('<div>').attr({
	"class":"prof-cust-save-btn-admin-um float-left-admin",
	"id":"create-user-id",
    
	}).html("Create User").click(function(e){
		if($("#admin-add-memeber-user-type").attr("roleid")==undefined || $("#admin-add-memeber-user-type").attr(
		"internalroleid")==undefined ){
			$("#admin-create-user-popup").hide();
			showErrorToastMessage("Please select a user type");
			return ;
		}else{
			showAddUserPopUp(e);
		}
		

	});	

	var form=$('<form>').attr({
	"id":"upload-form-admin",
    "action":"#",
    "method":"post",
	"enctype":"multipart/form-data"
		
	}).submit(function(event){
		  event.preventDefault();
		var formData = new FormData();
		var file=$("#file").prop("files")[0];
		console.log("file",file);
		formData.append("file",file);
		var formURL = "rest/userprofile/addusersfromcsv";
		$.ajax({
			url :formURL,
			type : "POST",
			contentType : false,
			processData : false,
			enctype:"multipart/form-data",
			cache : false,
			data : formData,
			success:function(data){
			
		    if(data!=null){
			
            var response=JSON.parse(data);
            
            var errors=response.errors;
            if(errors==undefined){
            	showToastMessage(response.success);
            }else{
            	$("#admin-error-wrapper").toggleClass('admin-display');
            	for(var i=0;i<=errors.length;i++){
					var row=displayErrorMessage(errors[i]);
					$("#admin-error-container").append(row);
				}
            }
            	
		    }	
			},
			error:function(e){
				showErrorToastMessage("Problem while uploading csv.Please try again later");
			}
			
		});
	});
	var inputFile=$('<input>').attr({
	"class":"input-file-admin",
	"type":"file",
	"name":"file",
	"id":"file",
	"onchange":"$('#upload-form-admin').submit();"
	}); 
	var uploadCSV=$('<div>').attr({
	"class":"prof-cust-upload-btn-admin-um float-left-admin",
	"id":"upload-csv-id", 

	}).html("Click to upload csv");
    
	uploadCSV.append(inputFile);
	form.append(uploadCSV);
	
	var label=$('<label>').attr({
	
	"class":"admin-label-class float-left",
	
	
	}).html("(or)");
	
	var uploadText=$('<div>').attr({
	
	"class":"admin-upload-label",
	
	
	});
	
	var downloadDiv=$('<div>').attr({
	"class":"admin-download-btn"
	}).html("&nbsp;&nbsp;&nbsp;/ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Click to download csv").on('click',function(e){
		alert("hi u have choosen to download csv template");
	});
	container.append(userTypeCont);
	container.append(createUserButton); 
	container.append(label);
    uploadText.append(form).append(downloadDiv);
	container.append(uploadText);
	wrapper.append(header).append(container);
	if(clearParent){
		$('#'+parentElement).html("");
	}
	$('#'+parentElement).append(wrapper);

	// function to append create user popup
	appendAdminCreateUserPopup();
	appendAdminUserTypeDropDown();

}

function displayErrorMessage(data){
	if($('.rp-agent-dashboard-admin')!=undefined || $('.rp-agent-dashboard-admin')!=""){
    var tableRow = $('<div>').attr({
		"class" : "admin-error-list-list-tr clearfix",
		
	}).html("Error in line"+"-"+data.lineNumber+"  "+data.message);
	return tableRow;
	
}
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

	if(newfiObject.user.userRole.roleCd=="INTERNAL"||newfiObject.user.userRole.roleCd=="SYSTEM")
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
							$('#admin-add-usertype-dropdown-cont').hide();
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
					$('#admin-create-user-first-name').addClass('err-input').focus();
					showErrorToastMessage("First name cannot be empty");
					return;
				} else if (user.lastName == "") {
					$('#admin-create-user-last-name').addClass('err-input').focus();
					showErrorToastMessage("Last name cannot be empty");
					return;
				} else if (user.emailId == "") {
					$('#admin-create-user-emailId').addClass('err-input').focus();
					showErrorToastMessage("Email ID cannot be empty");
					return;
				}
				if(user.emailId!="")
				{var validationStatus=emailValidation(user.emailId);
			      if(validationStatus){
					  $('#admin-create-user-emailId').val('');								  
				      $('#admin-create-user-emailId').addClass('err-input').focus();
				  
				  return;
				  }else{
					  $('#admin-create-user-emailId').removeClass('err-input');
				  }
			      
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
if ($('#admin-create-user-popup').css("display") == "block") {
		$('#admin-create-user-popup').hide();
	}
ajaxRequest("rest/userprofile/adduser", "POST", "json", JSON.stringify(user),
			appendDataToNewfiTeamWrapperForAdmin);



}
function appendDataToNewfiTeamWrapperForAdmin(data){
if(data.error==null||data.error==undefined||data.error==""){
	 var users = data.resultObject;
	  
	   var tableRow = getAdminTeamListTableRow(users);
	   var teamContainer = $(".admin-newfi-team-container").append(tableRow);
	   $('#admin-create-user-emailId').val('');
	   $('#admin-create-user-first-name').val('');
	   $('#admin-create-user-last-name').val('');

		showToastMessage("User created successfully");
}else{
	   $('#admin-create-user-emailId').val('');
	   $('#admin-create-user-first-name').val('');
	   $('#admin-create-user-last-name').val('');
	showErrorToastMessage(data.resultObject.message);
}
  
	
}
function appendAdminCreateUserPopupFirstName(){
var row = $('<div>').attr({
		"class" : "admin-create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "admin-create-user-popup-label float-left"
	}).html("First Name");
	
	var inputBox = $('<input>').attr({
		"class" : "admin-create-user-popup-input",
		"id" : "admin-create-user-first-name"
	}).val("");
	row.append(label).append(inputBox);
	$('#admin-create-user-container').append(row);

}

function appendAdminCreateUserPopupLastName(){
var row = $('<div>').attr({
		"class" : "admin-create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "admin-create-user-popup-label float-left"
	}).html("Last Name");
	var inputBox = $('<input>').attr({
		"class" : "admin-create-user-popup-input",
		"id" : "admin-create-user-last-name"
	}).val("");
	row.append(label).append(inputBox);
	$('#admin-create-user-container').append(row);

}

function appendAdminCreateUserPopupEmail(){
	var row = $('<div>').attr({
		"class" : "admin-create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "admin-create-user-popup-label float-left"
	}).html("Email");
	var inputBox = $('<input>').attr({
		"class" : "admin-create-user-popup-input",
		"id" : "admin-create-user-emailId"
		
	}).val("");
	var errMessage = $('<div>').attr({
		"class" : "err-msg hide" 
	});
	inputBox.append(errMessage);
	row.append(label).append(inputBox);
	$('#admin-create-user-container').append(row);

}

function appendNewfiTeamWrapperForAdmin(userDetails) {
	var users = userDetails;	
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
	 var searchValue=$('#search-id').val();
	 getSearchResultForAdmin(searchValue);
	 
	$('#search-id').val('');
	 	$(this).parent().find('.admin-search-input').hide();
	 }     	
	});
	var delay = (function(){
  var timer = 0;
  return function(callback, ms){
    clearTimeout (timer);
    timer = setTimeout(callback, ms);
  };
})();
	var searchInputBox = $('<input>').attr({
		"class" : "admin-search-input float-right",
		"id":"search-id",
		"placeholder":"Search User",
		"name":"search User"
	})
	.bind('keyup',function(e){
		 delay(function(){
			 e.preventDefault();
			 
     var searchValue=$('#search-id').val();
	 getSearchResultForAdmin(searchValue);
    }, 1000 );
		
	});
	header.append(searchDiv).append(searchInputBox);
	var container = $('<div>').attr({
		"class" : "admin-newfi-team-container",
		"id":"admin-newfi-team-container-id"
	});

	var tableHeader = getAdminTeamListTableHeader();
	container.append(tableHeader);

for(var i=0;i<users.length;i++){
	if(users[i].userRole.roleDescription!=userDescription){
     var tableRow = getAdminTeamListTableRow(users[i]);
      container.append(tableRow);
      }
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

	return tableHeaderRow.append(thCol1).append(thCol2).append(thCol3).append(thCol4).append(thCol5);
}

function getAdminTeamListTableRow(user) {

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
		}else if(user.userRole.id == 2){
		userRoleStr="Realtor";
		
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
			"userID":user.id
			
		});
		if(user.userRole.id==3){
		if(user.internalUserDetail.internalUserRoleMasterVO.id==1){
		if(user.internalUserDetail.activeInternal==statusActive){
		console.log("status of user",user.status);
		trCol4.append(inputActive);

		
		}else if(user.internalUserDetail.activeInternal==statusInActive){
		console.log("status of user in",user.status);
		trCol4.append(inputInActive);
		}else{
			trCol4.append(inputActive);
		}
		}
		}
	    inputActive.click(function(){
	     ajaxRequest("rest/userprofile/disable/"+user.id,"GET", "json", {},"");
		var teamMemberRow = $(".admin-newfi-team-list-tr-col4[userID=" + user.id + "]");
		teamMemberRow.empty();
		teamMemberRow.append(inputInActive);
		});	
		inputInActive.click(function(){
	    ajaxRequest("rest/userprofile/enable/"+user.id,"GET", "json", {},
				"");
		var teamMemberRow = $(".admin-newfi-team-list-tr-col4[userID=" + user.id + "]");
		teamMemberRow.empty();
		teamMemberRow.append(inputActive);	
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

	    RemoveUserFromUserListAdmin("Are you sure you want to delete the user?",userID);
		});
		if(user.userRole.id==3){
			if(loanManagerID==user.internalUserDetail.internalUserRoleMasterVO.id){
			trCol5.append(userDelIcn);}
		}
		if(user.userRole.id==1){
			if(user.status==1){
				trCol5.append(userDelIcn);
			}
			
		}
		
		return tableRow.append(trCol1).append(trCol2).append(trCol3).append(trCol4)
				.append(trCol5);

	
}

function RemoveUserFromUserListAdmin(textMessage,userID){
$('#overlay-confirm').off();
	$('#overlay-cancel').off();
	
	$('#overlay-popup-txt').html(textMessage);
	$('#overlay-confirm').on('click',function(){
			removeUserFromList(userID);
			$('#overlay-popup').hide();
	});
	
	$('#overlay-cancel').on('click',function(){
		$('#overlay-popup').hide();
	});
	
	$('#overlay-popup').show();
}
function removeUserFromList(userID){
 //TODO to call a method to delete users
  ajaxRequest("rest/userprofile/deleteUser/"+userID,"GET", "json", {},
			displayResponseData);

 
}
function displayResponseData(data){
	if(data.error==null){		
  // var tableRow = getAdminTeamListTableRow(data.resultObject);  
   var teamMemberRow = $(".admin-newfi-team-list-tr[userID=" + data.resultObject.id + "]");
   teamMemberRow.remove();
   if(data.resultObject.userRole.id==1){
       showToastMessage("Customer deleted successfully");
   }else{
	   showToastMessage("Loan manger deleted successfully");  
   }

  // tableRow.empty(teamMemberRow); 
	}else{
		showErrorToastMessage(data.error.message);
	}
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

