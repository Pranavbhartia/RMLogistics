//Function to paint to loan progress page
var countOfTasks = 0;
var workFlowContext = {
	init : function(loanId) {
		this.countOfTasks = 0;
		this.loanId = loanId;
	},

	customerWorkflowID : {},
	loanManagerWorkflowID : {},
	currentRole : {},
	loanId : {},

	mileStoneSteps : [],
	mileStoneContextList : {},
	ajaxRequest : function(url, type, dataType, data, successCallBack) {
		$.ajax({
			url : url,
			type : type,
			dataType : dataType,
			data : data,
			success : successCallBack,
			error : function() {
			}
		});
	},
	getWorkflowID : function(callback) {
		var ob = this;
		var data = {};
		
		ob.ajaxRequest(
						"rest/workflow/details/" + ob.loanId,
						"GET",
						"json",
						data,
						function(response) {
							if (response.error) {
								showToastMessage(response.error.message)
							} else {
								ob.customerWorkflowID = response.resultObject.customerWorkflowID;
								ob.loanManagerWorkflowID = response.resultObject.loanManagerWorkflowID;
							}
							if (callback) {
								callback(ob);
							}
						});

	},
	getMileStoneSteps : function(role, callback) {
		var ob = this;
		var data = {};
		ob.currentRole = role;
		var ajaxURL = "rest/workflow/";
		if (role == "CUSTOMER") {
			ajaxURL = ajaxURL + "customer/" + ob.customerWorkflowID;
		} else {
			ajaxURL = ajaxURL + ob.loanManagerWorkflowID;
		}
		ob.ajaxRequest(ajaxURL, "GET", "json", data, function(response) {
			if (response.error) {
				showToastMessage(response.error.message)
			} else {
				ob.mileStoneSteps = response.resultObject;
			}
			if (callback) {
				callback(ob);
			}
		});

	},
	checkIfChild : function(workflowItem) {
		var ob = this;
		if (workflowItem.parentWorkflowItemExec != null)
			return true;
		else
			return false;
	},

	renderMileStoneSteps : function(callback) {
		var ob = this;
		var workItemExecList = this.mileStoneSteps;
		var childList = [];
		for (var i = 0; i < workItemExecList.length; i++) {
			var currentWorkItem = workItemExecList[i];
			if (i == 0) {
				parentWorkItem = currentWorkItem;
			} else if (ob.checkIfChild(currentWorkItem) == false) // It is a
																	// parent
			{
				// The next item is a parent.
				// This is a parent - push all the ones so far in parent
				if (childList.length != 0) {
					appendMilestoneItem(parentWorkItem, childList);
					childList = [];
				} else {
					appendMilestoneItem(parentWorkItem);
				}
				parentWorkItem = currentWorkItem;
			} else if (ob.checkIfChild(currentWorkItem) == true
					&& currentWorkItem.parentWorkflowItemExec.id == parentWorkItem.id) {
				// Keep collecting..
				childList.push(currentWorkItem);
			}
		}
		// Last item append
		if (childList.length != 0) {
			appendMilestoneItem(parentWorkItem, childList);
		} else if (childList.length == 0) {
			appendMilestoneItem(parentWorkItem);
		}
		countOfTasks=0;
		adjustBorderMilestoneContainer();
		if (callback) {
			callback(ob);
		}
	},
	initialize : function(role, callback) {
		this.getWorkflowID(function(ob) {
			ob.getMileStoneSteps(role, function(ob) {
				ob.renderMileStoneSteps();
			});
		});

		if (callback) {
			callback();
		}
	}
};
function getInternalEmployeeMileStoneContext(mileStoneId, workItem) {

	var internalEmployeeMileStoneContext = {
		
		mileStoneId : mileStoneId,
		workItem : workItem,
		infoText : workItem.stateInfo,
		ajaxRequest : function(url, type, dataType, data, successCallBack) {
			$.ajax({
				url : url,
				type : type,
				dataType : dataType,
				contentType: "application/json",
				data : data,
				success : successCallBack,
				error : function() {
				}
			});
		},
		getStateInfo : function( rightLeftClass,itemToAppendTo,callback) {
			var ob = this;
			var data = {};
			data.milestoneId=ob.mileStoneId;
			var txtRow1 = $('<div>').attr({
				"class" : rightLeftClass + "-text",
				"mileNotificationId" : ob.workItem.id,
				"data-text" : ob.workItem.workflowItemType
			});
			var ajaxURL = "";	
			if (ob.workItem.displayContent=="Make Initial Contact")
			{
				ajaxURL = "";//rest/workflow/details/1";
				// in some cases we wont have to make a REST call - how to handle that?
				//For eg: Schefule An Alert - need not come from a REST call 
			}
			if (ob.workItem.displayContent=="Needed Items")
			{
				ajaxURL = "rest/workflow/needCount/1";
				// Just exposed a rest service to test - with hard coded loan ID
			}
			if (ob.workItem.displayContent == "Add Team") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID=selectedUserDetail.loanID;
				callback = paintMilestoneTeamMemberTable;
				// Just exposed a rest service to test - with hard coded loan ID
			}
			
					
			if(ajaxURL&&ajaxURL!=""){
				ob.ajaxRequest(ajaxURL, "POST", "json", JSON.stringify(data),
					function(response) {
						if (response.error) {
							showToastMessage(response.error.message)
						} else {
							ob.infoText =  response.resultObject;
							//txtRow1.html(ob.infoText);
							txtRow1.bind("click", function(e) {
								milestoneChildEventHandler(e)
							});
							itemToAppendTo.append(txtRow1);
						}
						if (callback) {
							callback(itemToAppendTo,ob);
						}
					});
			}else{
				txtRow1.html(workItem.stateInfo);
				txtRow1.bind("click", function(e) {
					milestoneChildEventHandler(e)
				});
				itemToAppendTo.append(txtRow1);
			}	


		}
	};
	return internalEmployeeMileStoneContext;
}
function paintCustomerLoanProgressPage() {
	var wrapper = $('<div>').attr({
		"class" : "loan-progress-wrapper"
	});

	var progressHeader = getCustomerMilestoneLoanProgressHeaderBar();

	var header = $('<div>').attr({
		"class" : "loan-progress-header"
	}).html("loan progress");
	var container = $('<div>').attr({
		"id" : "cust-loan-progress",
		"class" : "loan-progress-container"
	});
	wrapper.append(progressHeader).append(header).append(container);
	$('#center-panel-cont').append(wrapper);

	paintCustomerLoanProgressContainer();
}

function getCustomerMilestoneLoanProgressHeaderBar() {
	var container = $('<div>').attr({
		"class" : "milestone-header-bar clearfix"
	});

	var step1 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE", 1,
			"My Profile");
	var step2 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE", 2,
			"Application Status");
	var step3 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE", 3,
			"Credit Status");
	var step4 = getCustomerMilestoneLoanProgressHeaderBarStep("IN_PROGESS", 4,
			"Team");
	var step5 = getCustomerMilestoneLoanProgressHeaderBarStep("NOT_STARTED", 5,
			"Initial Needs List");

	return container.append(step1).append(step2).append(step3).append(step4)
			.append(step5);
}

function getCustomerMilestoneLoanProgressHeaderBarStep(status, step, heading) {
	var col = $('<div>').attr({
		"class" : "milestone-header-bar-step float-left"
	});

	if (status == "COMPLETE") {
		col.addClass('m-step-complete');
	} else if (status == "IN_PROGESS") {
		col.addClass('m-step-in-progress');
	} else if (status == "NOT_STARTED") {
		col.addClass('m-step-not-started');
	}
	var stepText = $('<div>').attr({
		"class" : "milestone-header-step-text"
	}).html(step);

	var msHeaderTxt = $('<div>').attr({
		"class" : "milestone-header-text"
	}).html(heading);

	return col.append(stepText).append(msHeaderTxt);
}

function paintCustomerLoanProgressContainer() {

	var heading = $('<div>').attr({
		"class" : "loan-progress-heading"
	}).html("Know Where You Are");

	var loanProgressCont = $('<div>').attr({
		"id" : "loan-progress-milestone-wrapper",
		"class" : "loan-progress-milestone-wrapper"
	});

	$('#cust-loan-progress').append(heading).append(loanProgressCont);
	paintMilestoneCustomerProfileDetails();
	workFlowContext.init(newfi.user.defaultLoanId);

	workFlowContext.initialize("CUSTOMER", function() {
	});
	
	// Append milestones
	

	// Paint customer profile container
	

	adjustBorderMilestoneContainer();
}

function appendMilestoneMyProfile() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-in-progress"
	});

	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});

	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("My Profile");

	header.append(headerTxt);

	var progressBarCont = $('<div>').attr({
		"class" : "clearfix"
	});

	var progressBarTxt = $('<div>').attr({
		"class" : "miestone-progress-bar-txt float-right"
	}).html("50 %");

	var progressBar = $('<div>').attr({
		"class" : "miestone-progress-bar float-right clearfix"
	});

	var progress = 5;
	for (var i = 0; i < 10; i++) {
		var progressGrid = $('<div>').attr({
			"class" : "miestone-progress-grid float-left"
		});
		if (progress == 0) {
			progressGrid.addClass('miestone-progress-grid-incomplete');
		} else {
			progress--;
		}
		progressBar.append(progressGrid);
	}

	progressBarCont.append(progressBar).append(progressBarTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Account");

	var txtRow2 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).click(function() {
		$("#lp-customer-profile").click();
	}).html("Online Application");

	var txtRow3 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).click(function() {
		$("#lp-customer-profile").click();
	}).html("Photo");

	var txtRow4 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).click(function() {
		$("#lp-customer-profile").click();
	}).html("SMS Texting Preferences");

	wrapper.append(leftBorder).append(header).append(progressBarCont).append(
			txtRow1).append(txtRow2).append(txtRow3).append(txtRow4);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// Function to append custome miletone application status
function appendMilestoneApplicationStatus() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-in-progress"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Application Status");

	var headerIcn = $('<div>')
			.attr(
					{
						"class" : "milestone-rc-header-icn ms-icn-application-status float-left"
					});

	header.append(headerTxt).append(headerIcn);

	var progressBarCont = $('<div>').attr({
		"class" : "clearfix"
	});

	var progressBarTxt = $('<div>').attr({
		"class" : "miestone-progress-bar-txt float-left"
	}).html("40 %");

	var progressBar = $('<div>').attr({
		"class" : "miestone-progress-bar float-left clearfix"
	});
	var progress = 4;
	for (var i = 0; i < 10; i++) {
		var progressGrid = $('<div>').attr({
			"class" : "miestone-progress-grid float-left"
		});
		if (progress == 0) {
			progressGrid.addClass('miestone-progress-grid-incomplete');
		} else {
			progress--;
		}
		progressBar.append(progressGrid);
	}
	progressBarCont.append(progressBar).append(progressBarTxt);

	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Connect Your Online Application");

	var txtRow2 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Contact Your Loan Manager");

	var txtRow3 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Connect Secu Your Online Application");

	wrapper.append(rightBorder).append(header).append(progressBarCont).append(
			txtRow1).append(txtRow2).append(txtRow3);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// Function to append milestone credit status
function appendMilestoneCreditStatus() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-complete"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Credit Status");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-credit-status float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("EQ - 686 ~ TU - 694 ~ Ex - 714");

	wrapper.append(leftBorder).append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// Function to append customer team milestone
function appendMilestoneTeam() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Team");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-team float-left"
	});

	header.append(headerTxt).append(headerIcn);

	var teamTable = getMilestoneTeamMembeTable();

	var txtRow1 = $('<div>').attr({
		"id" : "ms-add-team",
		"class" : "milestone-rc-text ms-add-team"
	}).html("Add members to Team").on('click', function(e) {
		e.stopImmediatePropagation();
		if ($('#ms-add-member-popup').css("display") == "block") {
			hideMilestoneAddTeamMemberPopup();
		} else {
			showMilestoneAddTeamMemberPopup();
		}
	});

	wrapper.append(rightBorder).append(header).append(teamTable)
			.append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);

	appendMilestoneAddTeamMemberPopup(txtRow1);
}

// Function to append pop up to add a team member in loan
function appendMilestoneAddTeamMemberPopup(loanID, itemToAppendTo, data) {
	var wrapper = $('<div>').attr({
		"id" : "ms-add-member-popup",
		"class" : "ms-add-member-popup"
	}).click(function(e) {
		e.stopPropagation();
	});
	// $(itemToAppendTo).html("");
	if ($('#ms-add-member-popup').length == 0)
		$(itemToAppendTo).append(wrapper);
	appendAddTeamMemberWrapper('ms-add-member-popup', true, data);
}

$(document).click(function() {
	if ($('#ms-add-member-popup').css("display") == "block") {
		removeMilestoneAddTeamMemberPopup();
	}
});

function showMilestoneAddTeamMemberPopup() {
	$('#ms-add-member-popup').show();
}

function hideMilestoneAddTeamMemberPopup() {
	$('#ms-add-member-popup').hide();
}

function removeMilestoneAddTeamMemberPopup() {
	$('#ms-add-member-popup').remove();
}

// Function to get milestone team member table

function paintMilestoneTeamMemberTable(appendTo,object){
	
	var userList=JSON.parse(object.infoText);
	appendTo.append(getMilestoneTeamMembeTable(userList,object.mileStoneId));
}

function getMilestoneTeamMembeTable(userList,milestoneID) {

	var tableContainer = $('<div>').attr({
		"class" : "ms-team-member-table"
	});

	
	var addNewMember = $('<div>').attr({
		"class" : "milestone-rc-text",
		"data-text" : "Click here to add a Team Member",
		"mileNotificationId":milestoneID
	}).html("Click here to add a Team Member").bind("click", function(e) {
		milestoneChildEventHandler(e)
	});

	tableContainer.append(addNewMember);
	
	if(!userList ||  userList.length==0)
		return;
	
	//team table header
	var th = getMilestoneTeamMembeTableHeader();
	tableContainer.append(th);
	
	for (i in userList) {

		var user = userList[i];
		var dispName = user.firstName+" "+user.lastName;
		var userRole = userList[i].userRole;
		var roleLabel = userRole.label;
		if (userRole.id == 3) {
			if (user.internalUserDetail
					&& user.internalUserDetail.internalUserRoleMasterVO
					&& user.internalUserDetail.internalUserRoleMasterVO.roleDescription)
				roleLabel = user.internalUserDetail.internalUserRoleMasterVO.roleDescription;
		}
		tableContainer.append(getMilestoneTeamMemberRow(dispName, roleLabel));
	}
	
	

	return tableContainer;
}

// Function to get milestone team member table header
function getMilestoneTeamMembeTableHeader() {
	var row = $('<div>').attr({
		"class" : "ms-team-member-th clearfix"
	});

	var nameCol = $('<div>').attr({
		"class" : "ms-team-member-th-col1 float-left"
	}).html("Name");

	var titleCol = $('<div>').attr({
		"class" : "ms-team-member-th-col2 float-left"
	}).html("Title");

	return row.append(nameCol).append(titleCol);
}

// Function to get milestone team member row
function getMilestoneTeamMemberRow(name, title) {
	var row = $('<div>').attr({
		"class" : "ms-team-member-tr clearfix"
	});

	var nameCol = $('<div>').attr({
		"class" : "ms-team-member-tr-col1 float-left"
	}).html(name);

	var titleCol = $('<div>').attr({
		"class" : "ms-team-member-tr-col2 float-left"
	}).html(title);

	return row.append(nameCol).append(titleCol);
}

// Function to append milestone initial need list
function appendMilestoneInitialNeedList() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-in-progress"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Initial Needs List");

	var headerIcn = $('<div>')
			.attr(
					{
						"class" : "milestone-lc-header-icn ms-icn-initial-need-list float-right"
					});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).click(function() {
		$("#lp-step4").click();
	}).html("16 of 18 Complete");

	wrapper.append(leftBorder).append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// Function to append customer milestone disclosures
function appendMilestoneDisclosures() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Disclosures");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-disclosure float-left"
	});

	header.append(rightBorder).append(headerTxt).append(headerIcn);

	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Click here to Sign");

	wrapper.append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// Function to append customer milestone application fee
function appendMilestoneApplicationFee() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Application Fee");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-application-fee float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text pay-application-fee"
	}).html("Click here to Pay Application Fee");

	wrapper.append(leftBorder).append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneLockRates() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Lock Rate");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-lock-rate float-left"
	});

	header.append(headerTxt).append(headerIcn);

	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).click(function() {
		$("#lp-step3").click();
	}).html("Click here to Request Rate Lock");

	wrapper.append(rightBorder).append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneAppraisal() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Appraisal");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-appraisal float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Not Ordered");

	wrapper.append(leftBorder).append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneUnderwriting() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Underwriting");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-underwriting float-left"
	});

	header.append(headerTxt).append(headerIcn);

	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Pending");

	wrapper.append(rightBorder).append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneClosingStatus() {
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Closing Status");

	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-closing-status float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Download the First Payment Coupon");

	wrapper.append(header).append(txtRow1);

	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function adjustBorderMilestoneContainer() {
	$('.milestone-lc:first-child').find('.milestone-lc-border').css({
		"top" : 0,
		"height" : $(this).height() + 32
	});
}

function paintMilestoneCustomerProfileDetails() {
	var container = $('<div>').attr({
		"class" : "ms-cust-prof-container clearfix"
	});

	var custImg = $('<div>').attr({
		"class" : "ms-cust-prof-img float-left"
	});

	var custTxtContainer = $('<div>').attr({
		"class" : "ms-cust-prof-txt-cont float-left"
	});

	var name = $('<div>').attr({
		"class" : "ms-cust-prof-txt-name"
	}).html("jane doe");

	var role = $('<div>').attr({
		"class" : "ms-cust-prof-txt-role"
	}).html("Home Buyer");

	var contact = $('<div>').attr({
		"class" : "ms-cust-prof-txt-contact"
	}).html("+1 (888) 555-1875");

	custTxtContainer.append(name).append(role).append(contact);
	container.append(custImg).append(custTxtContainer);
	$('#loan-progress-milestone-wrapper').append(container);
}

// Function to paint to loan progress page
function paintAgentLoanProgressPage() {

	appendCustomerDetailHeader(selectedUserDetail);

	var wrapper = $('<div>').attr({
		"class" : "loan-progress-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "loan-progress-header"
	}).html("loan progress");
	var container = $('<div>').attr({
		"id" : "agent-loan-progress",
		"class" : "loan-progress-container"
	});
	wrapper.append(header).append(container);
	$('#center-panel-cont').append(wrapper);

	paintAgentLoanProgressContainer();
}


function paintAgentLoanProgressContainer() {
	var loanProgressCont = $('<div>').attr({
		"id" : "loan-progress-milestone-wrapper",
		"class" : "loan-progress-milestone-wrapper"
	});
	$('#agent-loan-progress').append(loanProgressCont);

	workFlowContext.init(selectedUserDetail.loanID);

	workFlowContext.initialize("AGENT", function() {
	});
	
	// Append agent page loan progress milestones

	// adjustBorderMilestoneContainer();
}
function getProgressStatusClass(status) {
	var progressClass = "m-not-started";
	if (status == 2) {
		progressClass = "m-in-progress";
	} else if (status == 1) {
		progressClass = "m-complete";
	}
	return progressClass;
}

function appendMilestoneItem(workflowItem, childList) {

	countOfTasks++;
	var floatClass = "float-right";
	var progressClass = getProgressStatusClass(workflowItem.status);
	var rightLeftClass = "milestone-lc";
	if (countOfTasks % 2 == 1) {
		rightLeftClass = "milestone-lc";
		floatClass = "float-right";
	} else {
		rightLeftClass = "milestone-rc";
		floatClass = "float-left";
	}
	var wrapper = $('<div>').attr({
		"class" : rightLeftClass + " " + progressClass
	});
	var rightBorder = $('<div>').attr({
		"class" : rightLeftClass + "-border"
	});
	var header = $('<div>').attr({
		"class" : rightLeftClass + "-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : rightLeftClass + "-header-txt " + floatClass
	}).html(workflowItem.displayContent);

	var headerCheckBox = $('<div>').attr({
		"class" : "ms-check-box-header box-border-box " + floatClass,
		"data-checked" : "checked"
	});
	headerTxt.append(headerCheckBox);
	header.append(headerTxt);

	wrapper.append(rightBorder).append(header);
	if (workflowItem.stateInfo != "") {
		appendInfoAction(rightLeftClass, wrapper, workflowItem);
	}
	if (childList != null) {
		for (index = 0; index < childList.length; index++) {
			var childRow = $('<div>').attr({
				"class" : rightLeftClass + "-text",
				"mileNotificationId" : childList[index].id,
				"data-text" : childList[index].workflowItemType
			}).html(childList[index].displayContent);
			childRow.bind("click", function(e) {
				milestoneChildEventHandler(e)
			});
			var itemCheckBox = $('<div>').attr({
				"class" : "ms-check-box box-border-box " + floatClass,
				"data-checked" : "unchecked"
			}).on('click', function() {
				if ($(this).attr("data-checked") == "checked") {
					$(this).attr("data-checked", "unchecked");
				} else {
					$(this).attr("data-checked", "checked");
				}
			});

			childRow.append(itemCheckBox);
			wrapper.append(childRow);

			if (childList[index].stateInfo != null && childList[index].stateInfo!="") {
				appendInfoAction(rightLeftClass, wrapper, childList[index]);				
			}
		}
	}
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// this will add a "Information Link" that is clickable to the task.
function appendInfoAction (rightLeftClass, itemToAppendTo, workflowItem)
{
	var mileStoneStepContext = getInternalEmployeeMileStoneContext(workflowItem.id,workflowItem);
	
	mileStoneStepContext.getStateInfo(rightLeftClass,itemToAppendTo,function(){});
	
	return mileStoneStepContext;
}
function milestoneChildEventHandler(event) {
	// condition need to be finalized for identifying each element
	event.stopPropagation();
	if ($(event.target).attr("data-text") == "INITIAL_CONTACT") {
		var data = {};
		data.milestoneId = event.target.getAttribute("milenotificationid");
		data.OTHURL = "rest/workflow/execute/"+data.milestoneId;
		addNotificationPopup(selectedUserDetail.loanID, event.target, data);
	} else if ($(event.target).attr("data-text") == "TEAM_STATUS") {
		var teamTable = getMilestoneTeamMembeTable();
		var data = {};
		data.milestoneID=$(event.target).attr("mileNotificationId");
		data.OTHURL = "rest/workflow/execute/"+data.milestoneID;
		data.loanID = selectedUserDetail.loanID;
		appendMilestoneAddTeamMemberPopup(selectedUserDetail.loanID,
				event.target, data);
	} else if ($(event.target).attr("data-text") == "APP_FEE") {
		console.log("Pay application fee clicked!");
		showOverlay();
		$('body').addClass('body-no-scroll');
		url = "./payment/paymentpage.do";
		
		 $.ajax({
		        url : url,
		        type : "GET",
		        success : function(data) {
		        	console.log("Show payment called with data : " + data);
		        	$("#popup-overlay").html(data);
		        	hideOverlay();
		        	$("#popup-overlay").show();
		        },
		        error : function(e) {
		        	hideOverlay();
		            console.error("error : " + e);
		        }
		    });
	}
}
