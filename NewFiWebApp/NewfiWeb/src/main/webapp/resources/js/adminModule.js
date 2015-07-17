var userDescription="";
var salesManager="";
var loanManager="";
var customer="";
var Realtor="";
var loanManagerID=1;
var statusActive="ACTIVE";
var statusInActive="INACTIVE";
var flagKnowNewFi=true;
var searchUser=0;
var userDataList=new Object();
var currentSelectedUserFlag=0;
var currentSelectedUserID;

$(document).on('click',function(e){
	
	if($('#admin-add-usertype-dropdown-cont').css("display") == "block"){
		hideAdminUserTypeDropDown();
	}

	if($('#alert-popup-wrapper-settings').css("display") == "block"){
		hideSettingsPopup();
	}
	if ($('#admin-create-user-popup').css("display") == "block") {
		hideAdminUserCreatePopUp();
	}
	

});

$('#alert-settings-btn').click(function(e){
	    e.stopImmediatePropagation();
	   
		if($('#alert-popup-cont-wrapper').css('display')=="block"){
			$('#alert-popup-cont-wrapper').hide();
		}
		if ($(window).width() <= 768) {
			if ($('.left-panel').css("display") == "block") {
				$('.left-panel').toggle('slide', 'left');
				$('.small-screen-menu-icon').removeClass(
						'small-screen-menu-icon-clicked');
			}
		}
		if(newfiObject.user.userRole.id==3){
				if(newfiObject.user.internalUserDetail.internalUserRoleMasterVO.id==2){
					salesManager=newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleDescription;
				}
				if(newfiObject.user.internalUserDetail.internalUserRoleMasterVO.id==1){
					loanManager=newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleDescription;	
				}
		 }

		if(newfiObject.user.userRole.id==1){
			customer=newfiObject.user.userRole.roleDescription;
		}
		if(newfiObject.user.userRole.id==2){
			Realtor=newfiObject.user.userRole.roleDescription;
		}
		if(newfiObject.user.userRole.id==4){
			userDescription=newfiObject.user.userRole.roleDescription;
		}
			
		if($('#alert-popup-wrapper-settings').length==1){
			
			if($('#alert-popup-wrapper-settings').css("display") == "block"){				
				hideSettingsPopup();				
			}else{				
				showSettingsPopup();				
			}
		}else{
			
			appendSettingsDropDown();
		}
 
});

function appendSettingsDropDown(){

	var currentUser=newfiObject.user;

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
	 var header="";
	 if(customer!="" || Realtor!="" || loanManager!=""){
		  header = $('<div>').attr({
	   			"class" : "admin-pop-up-header"
	   		}).html("User Settings");
	 }else{
		  header = $('<div>').attr({
	   			"class" : "admin-pop-up-header"
	   		}).html("Admin Settings");
	 }
		
   		
	var container = $('<div>').attr({
				"id" : "admin-module-container",
				"class" : "admin-pop-up-container clearfix"
			});
	

	 if(customer!="" || Realtor!="" || loanManager!="")
	 {		 
		//TODO for dropdown settings for LM,customer,realtor			 
			
			var myProfileRow =paintSettingsDropDown("my-profile","My Profile","myProfile()","#");
			container.append(myProfileRow);
			
			var changepasswordRow= paintSettingsDropDown("change-password","Change Password","myProfile()","#");
			container.append(changepasswordRow);
			
			var tutorialsRow=paintSettingsDropDown("tutorials","Tutorial","completeTutorials()","#");
			
			 if(newfiObject.user.userRole.roleDescription==customer){
				 var tutorialStatusFlag=newfiObject.user.customerDetail.tutorialStatus;
				 if(tutorialStatusFlag){
		            	flagKnowNewFi=false;
		            }
				 if(flagKnowNewFi){
					 container.append(tutorialsRow);
				 }		
			 }				
	}   
	 else{	 

	   		var UserManagementRow=paintSettingsDropDown("user-management","User Mangement","userManagement()","#");
	   		container.append(UserManagementRow);
	   		
	   		var turnAroundTimeRow=paintSettingsDropDown("loan-detail","Turn Around Details","turnAroundTime()","#turn_around_times_container");
	   		container.append(turnAroundTimeRow);
	   		
	   		var templatesRow=paintSettingsDropDown("templates","Templates","populateTemplate()","#");
	   		container.append(templatesRow);
	   		
	   		var securitySettingsRow=paintSettingsDropDown("security-settings","Security Settings","","");
	   		container.append(securitySettingsRow);			    		
		
    }	
	 header.append(icondiv);
	 alertWrapper.append(header).append(container);
	 $('#alert-settings-btn').append(alertWrapper);
}

function paintSettingsDropDown(elementID,label,method,href){

	var row = $('<div>').attr({
		"class" : "admin-module-row clearfix",
		"id":elementID
	});
	var anchortag=$('<a>').attr({
	"class": "admin-anchor-class",
	"id" : elementID+"-id",	
    "href":href,
    "onclick":method	
	});
    var rowContent = $('<div>').attr({
		"class" : "admin-module-row-content",
			
	}).html(label);
	
	anchortag.append(rowContent);
	return row.append(anchortag);


}
function completeTutorials(){
	
	window.location.href=newfiObject.baseUrl+"home.do#myLoan/myTeam";
}
function myProfile(){
	
	 window.location.href = newfiObject.baseUrl+"home.do#myProfile";
}

/*TODO to load user mangement page*/
function userManagement(){
	
	$('#right-panel').html('');
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
			paintAdminUserPage);
}

//Function search users from the list based on customer name and email
function getSearchResultDataForAdmin(searchValue)
{	
	ajaxRequest("rest/userprofile/searchUserName?name="+searchValue,"GET", "json", {},
			adminDashboardRightPanel);
	
}
function adminDashboardRightPanel(data){
    
	$('.admin-newfi-team-container').html('');
	searchUser=1;
	appendNewfiTeamWrapperForAdmin(data.resultObject,searchUser); 
	
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
	"class":"cep-button-color prof-cust-save-btn-admin-um float-left",
	"id":"create-user-id",
    
	}).html("Create User").click(function(e){
		if($("#admin-add-memeber-user-type").attr("roleid")==undefined || $("#admin-add-memeber-user-type").attr(
		"internalroleid")==undefined ){
			$("#admin-create-user-popup").hide();
			showErrorToastMessage(selectUserType);
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
		  
		//To check uploaded file is a csv file 
		var status=checkFileExtension($("#file").prop("files")[0].name);
		if(status){					
		var formData = new FormData();
		var file=$("#file").prop("files")[0];
		console.log("file",file);
		formData.append("file",file);
		var formURL = "rest/userprofile/addusersfromcsv";
		showOverlay();
		$.ajax({
			url :formURL,
			type : "POST",
			contentType : false,
			processData : false,
			enctype:"multipart/form-data",
			cache : false,
			data : formData,
			success:function(data){
			hideOverlay();
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
				hideOverlay();
				showErrorToastMessage(uploadCsvErrorMessage);
			}
			
		});
		}else {
			return false;
		}
	});
	var inputFile=$('<input>').attr({
	"class":"input-file-admin",
	"type":"file",
	"name":"file",
	"id":"file",
	"onchange":"$('#upload-form-admin').submit();",
	"accept":".csv"
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
	"class":"admin-download-btn",

	}).html("&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Click to download csv").on('click',function(e){
		window.open("https://s3-ap-southeast-1.amazonaws.com/newfi/static/Nexera-UserUploadTemplate.csv");
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

function checkFileExtension(filename){
	var extension = filename.replace(/^.*\./, '');
	if(extension=="csv"){
		return true;
	}else{
		showErrorToastMessage(invalidFileUploadedMessage)
		return false;
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

	appendAdminPopUpDetails("First Name","admin-create-user-first-name");
	appendAdminPopUpDetails("Last Name","admin-create-user-last-name");
	appendAdminPopUpDetails("Email","admin-create-user-emailId");

	// save button
	var saveBtn = $('<div>').attr({
		"class" : "prof-cust-save-btn admin-save-btn"
	}).html("save").on(
			'click',
			function(event) {
				event.stopImmediatePropagation();

				var user = new Object();
				user.emailId = $('#admin-create-user-emailId').val();
				user.firstName = $('#admin-create-user-first-name').val();
				user.lastName = $('#admin-create-user-last-name').val();

				var isStatus=validateAdminUserCreate(user);
				if(!isStatus){
					return false;
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
		hideAdminUserCreatePopUp();
	}
	ajaxRequest("rest/userprofile/adduser", "POST", "json", JSON.stringify(user),
				appendDataToNewfiTeamWrapperForAdmin);

}

function appendDataToNewfiTeamWrapperForAdmin(data){
	if(data.error==null||data.error==undefined||data.error==""){
		 var users = data.resultObject;
		  
		   var tableRow = getAdminTeamListTableRow(users);
		   var teamContainer = $(".admin-newfi-team-container").append(tableRow);
		   EmptyTheFormFeildsInUM();
		   showToastMessage(userCreationSuccessMessage);
	}else{
			EmptyTheFormFeildsInUM();
		    showErrorToastMessage(data.resultObject.message);
	}
	
}

//function to clear the data in popup feilds
function EmptyTheFormFeildsInUM(){
	   $('#admin-create-user-emailId').val('');
	   $('#admin-create-user-first-name').val('');
	   $('#admin-create-user-last-name').val('');
}

function appendAdminPopUpDetails(title,elementID){
	var ErrMessage = $('<div>').attr({
		"class" : "admin-err-msg hide"
	});
    var row = $('<div>').attr({
		"class" : "admin-create-user-popup-cont clearfix float-left"
	});
	var label = $('<div>').attr({
		"class" : "admin-create-user-popup-label float-left"
	}).html(title);
	
	var inputBox = $('<input>').attr({
		"class" : "admin-create-user-popup-input",
		"id" : elementID
	}).val("");
	row.append(label).append(inputBox).append(ErrMessage);
	$('#admin-create-user-container').append(row);
}

function appendNewfiTeamWrapperForAdmin(userDetails,searchUser) {
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
	 getSearchResultDataForAdmin(searchValue);
	 $(this).show();
	 }     	
	});

	var searchInputBox = $('<input>').attr({
		"class" : "admin-search-input float-right",
		"id":"search-id",
		"placeholder":"Search User",
		"name":"search User"
	}).bind('keyup',function(e){	
		if (e.which == 13) {
			if ($(this).val() == "") {
				$(this).hide();
			}
			var searchValue=$('#search-id').val();
			getSearchResultDataForAdmin(searchValue);
			$(this).parent().find('.admin-search-icn').show();
		}
	
	});
	header.append(searchDiv).append(searchInputBox);
	var container = $('<div>').attr({
		"class" : "admin-newfi-team-container",
		"id":"admin-newfi-team-container-id"
	});

	var tableHeader = getAdminTeamListTableHeader();
	container.append(tableHeader);
    
    for(var i=0;i<users.length;i++){
    	if(users[i].status!=-1){
    		var tableRow = getAdminTeamListTableRow(users[i]);
    	 	//not to append sm/super admin himself in the list of customers
            if(users[i].id!=newfiObject.user.id){
            	container.append(tableRow);
            }
    	}		    
	}
    if(searchUser){
    	wrapper.append(container);
    }else{
    	wrapper.append(header).append(container);
    }
	
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

function appendData(data){
	
	userDataList=data;
}

function getAdminTeamListTableRow(user) {

		var tableRow = $('<div>').attr({
			"class" : "admin-newfi-team-list-tr clearfix",
			"userid" : user.id
		});
		var trCol1="";
		if(user.userRole.id!=1 && user.userRole.id!=4){
		 trCol1 = $('<div>').attr({
			"class" : "admin-newfi-team-list-tr-col1 float-left"
		}).html(user.firstName + " " + user.lastName).on('click',function(e){			
			e.stopImmediatePropagation();	
			//Entry point for edit profile popup
		
			var status=appendInformationForUserProfilePage(user);	
			if(status){
				paintProfilePageOfUser(userDataList,tableRow);	
			}
												
		});
		}else{
			 trCol1 = $('<div>').attr({
				"class" : "admin-newfi-team-list-tr-col1 no-click-col float-left"
			}).html(user.firstName + " " + user.lastName);
		}
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
			trCol4.append(inputActive);

		}
		else if(user.internalUserDetail.activeInternal==statusInActive){
			trCol4.append(inputInActive);
		}
		else{
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

		userDelIcn.click(function(e) {
			var userID = $(this).attr("userid");
			e.stopImmediatePropagation();
	    	RemoveUserFromUserListAdmin(messageToDeleteUser,userID);
		});
		//TODO providing access for delete to sm apart from himself 12 06 2015
/*		if(user.userRole.id==3||user.userRole.id==1||user.userRole.id==2){
				if(loanManagerID==user.internalUserDetail.internalUserRoleMasterVO.id){
				trCol5.append(userDelIcn);
			}else{
				if(user.status==1){
					trCol5.append(userDelIcn);
				}
			}
		}*/
		if(user.userRole.id!=4){
			trCol5.append(userDelIcn);
		}
		
		return tableRow.append(trCol1).append(trCol2).append(trCol3).append(trCol4)
				.append(trCol5);

	
}

//TODO to call a method to delete users
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

  ajaxRequest("rest/userprofile/deleteUser/"+userID,"GET", "json", {},
			displayResponseData);

}

function displayResponseData(data){
	if(data.error==null){		
  // var tableRow = getAdminTeamListTableRow(data.resultObject);  
	   var teamMemberRow = $(".admin-newfi-team-list-tr[userID=" + data.resultObject.id + "]");
	   teamMemberRow.remove();

	   if(data.resultObject.userRole.id==1){
		   showToastMessage(customerDeleteSuccessMessage);
	   }
	   else if(data.resultObject.userRole.id==2){
		   showToastMessage(realtorDeleteSuccessMessage);
	   }       
       else{
    	   showToastMessage(loanMangerDeleteSuccessMessage);  
       }
  // tableRow.empty(teamMemberRow); 
	}else{
		showErrorToastMessage(data.error.message);
	}
}

//function to display error message on csv upload
function displayErrorMessage(data){
	
	if($('.rp-agent-dashboard-admin')!=undefined || $('.rp-agent-dashboard-admin')!=""){
    var tableRow = $('<div>').attr({
		"class" : "admin-error-list-list-tr clearfix",
		
	}).html("Error in line"+"-"+data.lineNumber+"  "+data.message);
	return tableRow;
	
    }
}

//Function called for getting current customer data 
function appendInformationForUserProfilePage(user){
	synchronousAjaxRequest("rest/userprofile/getProfile/"+user.id, "GET", "json", {},
			appendData);
	synchronousAjaxRequest("rest/states/", "GET", "json", "", stateListCallBack);
	
	//Check for if any user edit profile is opened
		if(currentSelectedUserFlag){					
				$('#admin-user-edit-profile-id').remove();
				$('.admin-newfi-team-list-tr').attr('userid',currentSelectedUserID).removeClass('admin-newfi-team-list-tr-sel');				
			    if(currentSelectedUserID==user.id){
			    	userDataList=undefined;
					currentSelectedUserFlag=0;
			    	currentSelectedUserID="";
			    	return false;
			    }		   		    
		}
		return true;
}

//function called to paint user edit profile
function paintProfilePageOfUser(user,element){
    	
	if ($(element).next().hasClass("admin-user-edit-profile-wrapper")) {
		$('#admin-user-edit-profile-id').remove();
		$('.admin-newfi-team-list-tr').removeClass('admin-newfi-team-list-tr-sel');
	    userDataList=undefined;
	    currentSelectedUserFlag=0;
	    currentSelectedUserID="";
		return;
	}	
	$('#admin-user-edit-profile-wrapper').remove();
	$('.admin-newfi-team-list-tr-se').removeClass('admin-newfi-team-list-tr-sel');
	$(element).addClass('admin-newfi-team-list-tr-sel');
	currentSelectedUserID=user.id;
	currentSelectedUserFlag=1;
	var wrapper = $('<div>').attr({
		"id" : "admin-user-edit-profile-id",
		"class" : "admin-user-edit-profile-wrapper clearfix"
	});
	$(element).after(wrapper);
	var content=appendFormFeild(user);
	wrapper.append(content);
	
	
}

//function called to append content to the selected user profile
function appendFormFeild(user){	
	
	var	wrapper = $('<div>').attr({
			"class" : "adminUM-user-edit-profile-wrapper"
		});
	
	
	var container = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-container"
	});

	//to append left container of popup
	var leftWrapper=paintLeftEditProfileContainer(user);
	//to append photo upload details of popup
	var rightWrapper=paintRightEditProfileContainer(user);
	var rightBottomWrapper="";
	//to append LQB credentials update only for internal users of popup
	if(user.userRole.id==3){
		rightBottomWrapper=paintRightBottomWrapper(user);
		
	}
	//TO append current user and customer details for save/update of user	
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
	
	container.append(leftWrapper).append(rightWrapper).append(rightBottomWrapper).append(id).append(customerDetailsId);
	wrapper.append(container);
    $('#admin-user-edit-profile-id').append( wrapper);
    var height=$('.adminUM-user-edit-profile-left-container').height()-$('.adminUM-user-edit-profile-right-container ').height()-10;
	$('.adminUM-user-edit-profile-container-new').css('height',height+'px');

}

function paintRightBottomWrapper(user){
		 
	var rightWrapper = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-right-wrapper float-left"
	});
	
	var rightContainer = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-container-new"
	});
	var lqbContainer = getLoanLqbInfoContainer(user);
	var header = $('<div id="alertHeader">').attr({
		"class" : "adminUM-user-edit-profile-header"
	}).html("LQB Information");
	lqbContainer.css('margin','10px auto');
	var height=$('.adminUM-user-edit-profile-left-container').height()-$('.adminUM-user-edit-profile-right-container ').height()-10;
	rightContainer.css('height',height+'px');
	rightContainer.append(header).append(lqbContainer);
	return rightWrapper.append(rightContainer);
	
}

function paintRightEditProfileContainer(user){
	
	var rightWrapper = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-right-wrapper float-left"
	});
	
	var rightContainer = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-right-container "
	});
	
	var header = $('<div id="alertHeader">').attr({
		"class" : "adminUM-user-edit-profile-header"
	}).html("Upload Photo");
			
	var divImage=$('<div>').attr({
		"class" : "adminUM-user-upload-profile-image"
	});
	var imgCont = $('<div>').attr({
		"id":"userProfilePic"
	}).click(uploadeImage);
	
	if(user.photoImageUrl==""||user.photoImageUrl==null){
		imgCont.addClass("adminUM-agent-default-img");		
		imgCont.text(getInitialsFromFullName(user.displayName));	
	}else{
		imgCont.addClass("adminUM-agent-assigned-img");
		imgCont.css("background-image", "url('" + user.photoImageUrl + "')");
	}
	
	divImage.append(imgCont);
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
	uploadBottomContianer.append(inputHiddenDiv);
	
	var mainRow=$('<div>').attr({
		"class":"adminUM-user-upload-profile-image-row"
	});
	
	var rowContentName=$('<div>').attr({
		"class":"adminUM-user-upload-profile-image-row-text",
		"id":"fullName"
	}).html(user.firstName+""+user.lastName);
	
	var userRoleStr="";
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
	
	var rowContent=$('<div>').attr({
		"class":"adminUM-user-upload-profile-image-row-text",
		"id":"userType"
	}).html(userRoleStr);
	
	
	mainRow.append(rowContentName).append(rowContent);
	
	rightContainer.append(header).append(divImage).append(uploadBottomContianer).append(mainRow);
	return rightWrapper.append(rightContainer);
}

function paintLeftEditProfileContainer(user){
	
	var leftWrapper = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-left-wrapper float-left"
	});
	
	var leftContainer = $('<div>').attr({
		"class" : "adminUM-user-edit-profile-left-container"
	});
		
	var header = $('<div id="alertHeader">').attr({
		"class" : "adminUM-user-edit-profile-header"
	}).html("Edit Profile");
	
	leftContainer.append(header);
	
	var formfeilds="";		 
		 formfeilds=[
		                {
			              "type":"text",
			              "id":"firstNameId",
			              "placeholder":"First Name",
			              "value":user.firstName,
			              "html":"First Name"
		                },
		                {
		                	"type":"text",
				            "id":"lastNameId",
				            "placeholder":"Last Name",
				            "value":user.lastName,
				            "html":"Last Name"	
		                },
		                {
		                	"type":"text",
				            "id":"priEmailId",
				            "value":user.emailId,
				            "html":"Primary Email"
		                },
		                {
		                	"type":"lookup",
		                	"title":"Assign Manager",
				            "user":user	
		                },
		                {
		                	"type":"text",
				            "id":"priPhoneNumberId",
				            "placeholder":"Primary Phone",
				            "value":user.phoneNumber,
				            "html":"Primary Phone",
				            "user":user
		                },
		                {
		                	"type":"radio",
		                	"user":user
		                },
		                {
		                	"type":"popup",
		                	"user":user
				            
		                },
		                {
		                	"type":"function",
				            "user":user
				            
		                },		               		                
		                ];

    
	for(var i=0;i<formfeilds.length;i++){
		var question=formfeilds[i];
		var row=paintFormPage(question);
		if(question.id=="priPhoneNumberId"){
			synchronousAjaxRequest("rest/userprofile/getMobileCarriers", "GET", "json", "", mobileCarrierList);			
			var carrierInfo=getCarrierDropdown(question.user);
			carrierInfo.addClass('carrier-info-display');
			leftContainer.append(row).append(carrierInfo);			
		}else{
			leftContainer.append(row);
		}		
	}
	
	var submitButton=$('<div>').attr({
		"class":"adminUM-sumit-button cep-button-color",
		"id":"submitID"
	}).html("Save").on('click',function(e){				
			updateLMDetails();		
	});
	leftContainer.append(submitButton);
	return leftWrapper.append(leftContainer);
}

function paintFormPage(question){
	
	var row="";
	if(question.type=="text"){
		 row=appendInputsOfFormFeild(question);
		
	}else if(question.type=="lookup"){
		/*if(question.title=="Assign Manager"){	
			if(question.user.userRole.id==2){
				var row=getLoanManager(question.user);		
			}									
		}	*/		
	} else if(question.type=="popup"){
		if(question.user.userRole.id!=2){
		 row=getManagerStateRow();	
		}
		
	} else if(question.type=="radio"){
		row=getCheckStatus(question.user);
		row.find('.prof-form-row-desc').addClass('admin-row-adj');
		
	}
	else if(question.type=="function"){
		if(question.user.userRole.id!=2){
			internalUserStateMappingVO = undefined; 
			internalUserStates = new Object();
			if(question.user.internalUserStateMappingVOs!=undefined){
				userStateMappingVOs=question.user.internalUserStateMappingVOs;
	
				states.length = 0;
				//internalUserStates.length=0;
				for(var i=0;i<userStateMappingVOs.length;i++) {
						states.push(userStateMappingVOs[i].stateId.toString());
						internalUserStates[userStateMappingVOs[i].stateId]=userStateMappingVOs[i];
			    }
			}
			row=getLicensesRow(question.user);
			loggedInUser = question.user;
		}
	}

	return row;
}

function appendInputsOfFormFeild(question){

	var mainRow=$('<div>').attr({
		"class":"adminUM-user-edit-profile-row clearfix"
	});
	
	var column1=$('<div>').attr({
		"class":"adminUM-col-1 float-left"
	}).html(question.html);
	
	var column2=$('<div>').attr({
		"class":"adminUM-col-2 float-left"
	});
	var div=$('<div>').attr({
		"class":"adminUM-cont"
	});
	var input="";
	if(question.id=="dateOfBirthId"){	
			 input=$('<input>').attr({
					"class":"adminUM-user-edit-profile-input date-picker",
					"placeholder":question.placeholder,
					"value":question.value,
					"id":question.id,
					
				}).datepicker({
					orientation : "top auto",
					autoclose : true,
					maxDate: new Date() ,
					changeMonth: true,
					changeYear: true

			
		    });		
		
	}else if(question.id=="priEmailId"){
		
		input=$('<input>').attr({
			"class":"adminUM-user-edit-profile-input",
			"placeholder":question.placeholder,
			"value":question.value,
			"id":question.id,
			"readonly":true
			
		});
		
	}else{
		 input=$('<input>').attr({
			"class":"adminUM-user-edit-profile-input",
			"placeholder":question.placeholder,
			"value":question.value,
			"id":question.id,
			
		});
	}
	var carrierInfo="";
	if(question.id=="priPhoneNumberId"){		
		div.append(input).append(appendErrorMessage());
		carrierInfo=getCarrierDropdown(question.user);		
		column2.append(div);
		mainRow.after(carrierInfo);		
	}else{
		div.append(input).append(appendErrorMessage());
		column2.append(div);
	}

	if(question.id=="priPhoneNumberId"||question.id=="secPhoneNumberId"){
		input.mask("(999) 999-9999");
	}
	 mainRow.append(column1).append(column2);
	 return mainRow;
	
}


function showSettingsPopup(){

	$('#alert-popup-wrapper-settings').show();
	
}

function hideSettingsPopup(){

	$('#alert-popup-wrapper-settings').hide();
	
}

function hideAdminUserCreatePopUp(){
	
	$('#admin-create-user-popup').hide();
	
	//To remove validation
	$('#admin-create-user-first-name').next('.admin-err-msg').hide();
	$('#admin-create-user-first-name').removeClass('ce-err-input');


	$('#admin-create-user-last-name').next('.admin-err-msg').hide();
	$('#admin-create-user-last-name').removeClass('ce-err-input');


	$('#admin-create-user-emailId').next('.admin-err-msg').hide();
	$('#admin-create-user-emailId').removeClass('ce-err-input');


	$('#admin-create-user-emailId').next('.admin-err-msg').hide();
	$('#admin-create-user-emailId').removeClass('ce-err-input');
	
	//TO Empty feild values if any
	EmptyTheFormFeildsInUM();

}

function showAdminUserCreatePopUp(){
	
	$('#admin-create-user-popup').show();
	
}

function hideAdminUserTypeDropDown(){
	
	$('#admin-add-usertype-dropdown-cont').hide();
	
}

function showAdminUserTypeDropDown(){
	
	$('#admin-add-usertype-dropdown-cont').show();
	
}