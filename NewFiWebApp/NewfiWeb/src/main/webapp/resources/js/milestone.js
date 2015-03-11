//Function to paint to loan progress page
countOfTasks=0;
function getInternalEmployeeMileStoneContext(workflowId){
	
	var internalEmployeeMileStoneContext = {
			
			workflowId:workflowId,
			workItemExecList:{},
			ajaxRequest	:function (url,type,dataType,data,successCallBack){
				$.ajax({
					url : url,
					type : type,
					dataType : dataType,
					data : data,
					success : successCallBack,
					error : function(){						
					}
				});
			},
			
	getWorkFlowItemStatuses: function(callback)
	{
		var ob=this;
		var data={};
		ob.workItemExecList={};
		ob.ajaxRequest("rest/workflow/"+ob.workflowId,"GET","json",data,function(response){
			if(response.error){
				showToastMessage(response.error.message)
			}else{
				var workItemExecList=response.resultObject;
				//alert (workItemExecList[0].status + "--" + workItemExecList[0].displayContent);
				//appendAgentMilestoneItem(workItemExecList[0].status, workItemExecList[0].displayContent, "Schedule An Alert");
				var childList =[];
				for(var i=0;i<workItemExecList.length;i++){
					var currentWorkItem = workItemExecList[i];					
					if (i==0)
					{
						parentWorkItem = currentWorkItem;
					}
					else if (ob.checkIfChild(currentWorkItem) == false)
					{	
						//The next item is a parent.
						//This is a parent - push all the ones so far in parent		
						if(childList.length !=0)
						{
							appendAgentMilestoneItemWithChildren(parentWorkItem.status, parentWorkItem.displayContent, parentWorkItem.stateInfo,childList);						
							childList =[];							
						}
						else
						{
							appendAgentMilestoneItem(parentWorkItem.status, parentWorkItem.displayContent, parentWorkItem.stateInfo);
						}
						parentWorkItem = currentWorkItem;
					}
					else if (ob.checkIfChild(currentWorkItem) == true && currentWorkItem.parentWorkflowItemExec.id == parentWorkItem.id)
					{
						//Keep collecting..
						childList.push (currentWorkItem);
						if (i == workItemExecList.length-1)
						{
							appendAgentMilestoneItemWithChildren(parentWorkItem.status, parentWorkItem.displayContent,  parentWorkItem.stateInfo,childList);
						}
					}					
				}
				//Last item append
				appendAgentMilestoneItem(parentWorkItem.status, parentWorkItem.displayContent,  parentWorkItem.stateInfo);
				
				
				
				adjustBorderMilestoneContainer();
				if(callback){
					callback(ob);
				}
			}
			
		});
	},
	
	checkIfChild : function (workflowItem)
	{
		var ob=this;		
		if (workflowItem.parentWorkflowItemExec!=null)
			return true;
		else
			return false;
	},
	
	initialize:function(callback){
		this.getWorkFlowItemStatuses(function(ob){		
		});
		
		if(callback){
			callback();
		}
	}
};
	return internalEmployeeMileStoneContext;
}
function paintCustomerLoanProgressPage(){
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

function getCustomerMilestoneLoanProgressHeaderBar(){
	var container = $('<div>').attr({
		"class" : "milestone-header-bar clearfix"
	});
	
	var step1 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE",1,"My Profile");
	var step2 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE",2,"Application Status");
	var step3 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE",3,"Credit Status");
	var step4 = getCustomerMilestoneLoanProgressHeaderBarStep("IN_PROGESS",4,"Team");
	var step5 = getCustomerMilestoneLoanProgressHeaderBarStep("NOT_STARTED",5,"Initial Needs List");
	
	return container.append(step1).append(step2).append(step3).append(step4).append(step5);
}

function getCustomerMilestoneLoanProgressHeaderBarStep(status,step,heading){
	var col = $('<div>').attr({
		"class" : "milestone-header-bar-step float-left"
	});
	
	if(status == "COMPLETE"){
		col.addClass('m-step-complete');
	}else if(status == "IN_PROGESS"){
		col.addClass('m-step-in-progress');
	}else if(status == "NOT_STARTED"){
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


function paintCustomerLoanProgressContainer(){
	
	var heading = $('<div>').attr({
		"class" : "loan-progress-heading"
	}).html("Know Where You Are");
	
	var loanProgressCont = $('<div>').attr({
		"id" : "loan-progress-milestone-wrapper",
		"class" : "loan-progress-milestone-wrapper"
	});
	
	$('#cust-loan-progress').append(heading).append(loanProgressCont);
	
	
	//Append milestones
	appendMilestoneMyProfile();
	appendMilestoneApplicationStatus();
	appendMilestoneCreditStatus();
	appendMilestoneTeam();
	appendMilestoneInitialNeedList();
	appendMilestoneDisclosures();
	appendMilestoneApplicationFee();
	appendMilestoneLockRates();
	appendMilestoneAppraisal();
	appendMilestoneUnderwriting();
	appendMilestoneClosingStatus();
	
	//Paint customer profile container
	paintMilestoneCustomerProfileDetails();
	
	adjustBorderMilestoneContainer();
}

function appendMilestoneMyProfile(){
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
	for(var i=0; i<10; i++){
		var progressGrid = $('<div>').attr({
			"class" : "miestone-progress-grid float-left"
		});
		if(progress == 0){
			progressGrid.addClass('miestone-progress-grid-incomplete');
		}else{
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
	}).html("Online Application");
	
	var txtRow3 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Photo");
	
	var txtRow4 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("SMS Texting Preferences");
	
	wrapper.append(leftBorder).append(header).append(progressBarCont).append(txtRow1).append(txtRow2).append(txtRow3).append(txtRow4);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

//Function to append custome miletone application status
function appendMilestoneApplicationStatus(){
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
	
	var headerIcn = $('<div>').attr({
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
	for(var i=0; i<10; i++){
		var progressGrid = $('<div>').attr({
			"class" : "miestone-progress-grid float-left"
		});
		if(progress == 0){
			progressGrid.addClass('miestone-progress-grid-incomplete');
		}else{
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
	
	wrapper.append(rightBorder).append(header).append(progressBarCont).append(txtRow1).append(txtRow2).append(txtRow3);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

//Function to append milestone credit status
function appendMilestoneCreditStatus(){
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


//Function to append customer team milestone
function appendMilestoneTeam(){
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
	}).html("Add members to Team")
	.on('click',function(e){
		e.stopImmediatePropagation();
		if($('#ms-add-member-popup').css("display") == "block"){
			hideMilestoneAddTeamMemberPopup();		
		}else{
			showMilestoneAddTeamMemberPopup();
		}
	});
	
	wrapper.append(rightBorder).append(header).append(teamTable).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
	
	appendMilestoneAddTeamMemberPopup();
}

//Function to append pop up to add a team member in loan
function appendMilestoneAddTeamMemberPopup(){
	var wrapper = $('<div>').attr({
		"id" : "ms-add-member-popup",
		"class" : "ms-add-member-popup hide"
	}).click(function(e){
		e.stopPropagation();
	});
	
	var header = $('<div>').attr({
		"class" : "ms-add-member-popup-header"
	}).html("Add Agents");
	
	var usertypeCont = $('<div>').attr({
		"class" : "ms-add-memeber-input-cont clearfix float-left"
	});
	
	var usertypeLabel = $('<div>').attr({
		"class" : "ms-add-memeber-input-label float-left"
	}).html("User Type");
	
	var usertypeInput = $('<input>').attr({
		"class" : "ms-add-memeber-input-input float-left"
	});
	
	usertypeCont.append(usertypeLabel).append(usertypeInput);
	
	var userNameCont = $('<div>').attr({
		"class" : "ms-add-memeber-input-cont clearfix float-left"
	});
	
	var userNameLabel = $('<div>').attr({
		"class" : "ms-add-memeber-input-label float-left"
	}).html("User Name");
	
	var userNameInput = $('<input>').attr({
		"class" : "ms-add-memeber-input-input float-left"
	});
	
	userNameCont.append(userNameLabel).append(userNameInput);
	
	var emailCont = $('<div>').attr({
		"class" : "ms-add-memeber-input-cont clearfix float-left"
	});

	var emailLabel = $('<div>').attr({
		"class" : "ms-add-memeber-input-label float-left"
	}).html("Email");
	
	var emailInput = $('<input>').attr({
		"class" : "ms-add-memeber-input-input float-left"
	});
	emailCont.append(emailLabel).append(emailInput);
	
	var saveBtnWrapper = $('<div>').attr({
		"class" : "ms-save-btn-wrapper float-left"
	});
	
	var saveBtn = $('<div>').attr({
		"class" : "ms-save-btn"
	}).html("save");
	
	saveBtnWrapper.append(saveBtn);
	
	wrapper.append(header).append(usertypeCont).append(userNameCont).append(emailCont).append(saveBtnWrapper);
	$('#ms-add-team').append(wrapper);
}

$(document).click(function(){
	if($('#ms-add-member-popup').css("display") == "block"){
		hideMilestoneAddTeamMemberPopup();
	}
});

function showMilestoneAddTeamMemberPopup() {
	$('#ms-add-member-popup').show();
}

function hideMilestoneAddTeamMemberPopup() {
	$('#ms-add-member-popup').hide();
}



//Function to get milestone team member table
function getMilestoneTeamMembeTable(){
	
	var tableContainer = $('<div>').attr({
		"class" : "ms-team-member-table"
	});
	
	var th = getMilestoneTeamMembeTableHeader();
	var tr1 = getMilestoneTeamMemberRow("Elen Adarna","Title Agent");
	var tr2 = getMilestoneTeamMemberRow("Sherry Andrew","Loan Manager");
	
	return tableContainer.append(th).append(tr1).append(tr2);
}

//Function to get milestone team member table header
function getMilestoneTeamMembeTableHeader(){
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

//Function to get milestone team member row
function getMilestoneTeamMemberRow(name,title){
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


//Function to append milestone initial need list
function appendMilestoneInitialNeedList(){
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
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-initial-need-list float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("16 of 18 Complete");
	
	wrapper.append(leftBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}


//Function to append customer milestone disclosures 
function appendMilestoneDisclosures(){
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


//Function to append customer milestone application fee
function appendMilestoneApplicationFee(){
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
		"class" : "milestone-lc-text"
	}).html("Click here to Pay Application Fee");
	
	wrapper.append(leftBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneLockRates(){
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
	}).html("Click here to Request Rate Lock");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}


function appendMilestoneAppraisal(){
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


function appendMilestoneUnderwriting(){
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

function appendMilestoneClosingStatus(){
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

function adjustBorderMilestoneContainer(){
	$('.milestone-lc:first-child').find('.milestone-lc-border').css({
		"top" : 0,
		"height" : $(this).height() + 32
	});
}


function paintMilestoneCustomerProfileDetails(){
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














/*
 * JavaScript functions for loan manager Agent page
 */






//Function to paint to loan progress page
function paintAgentLoanProgressPage(){
	
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

var lmContext;
function paintAgentLoanProgressContainer() {
	var loanProgressCont = $('<div>').attr({
		"id" : "loan-progress-milestone-wrapper",
		"class" : "loan-progress-milestone-wrapper"
	});
	$('#agent-loan-progress').append(loanProgressCont);
	
	var lmMileStoneContext=getInternalEmployeeMileStoneContext(3);//
	lmMileStoneContext.initialize(function(){		
		
		
				
		
	});
	lmContext=lmMileStoneContext;
	//Append agent page loan progress milestones
	
	
	//adjustBorderMilestoneContainer();
}
function getProgressStatusClass (status)
{
	var progressClass = "m-not-started";
	if (status == 2)
	{
		progressClass = "m-in-progress";	
	}
	else if (status == 1)
	{
		progressClass = "m-complete";
	}
	return progressClass;
}


function appendAgentMilestoneItem(status, displayContent, stateInfo){
	countOfTasks++;
	var floatClass = "float-right";
	var progressClass = getProgressStatusClass(status);
	var rightLeftClass =  "milestone-lc";
	if (countOfTasks %2 == 1)
	{
		rightLeftClass = "milestone-lc";
		floatClass ="float-right";
	}
	else
	{
		rightLeftClass = "milestone-rc";
		floatClass ="float-left";
	}
	var wrapper = $('<div>').attr({
		"class" : rightLeftClass + " " +progressClass
	});
	var rightBorder = $('<div>').attr({
		"class" : rightLeftClass+"-border"
	});
	var header = $('<div>').attr({
		"class" : rightLeftClass+ "-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : rightLeftClass+"-header-txt " +floatClass
	}).html(displayContent);
	
	header.append(headerTxt);
	wrapper.append(rightBorder).append(header);
	if (stateInfo!="")
	{
		var txtRow1 = $('<div>').attr({
			"class" : rightLeftClass+"-text"
		}).html(stateInfo);		
		wrapper.append(txtRow1);
	}
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneItemWithChildren(status, displayContent, inputText, childList){
	countOfTasks++;
	var rightLeftClass =  "milestone-lc";
	var floatClass = "float-right";
	if (countOfTasks %2 == 1)
	{
		rightLeftClass = "milestone-lc";
		floatClass ="float-right";
	}
	else
	{
		rightLeftClass = "milestone-rc";
		floatClass ="float-left";
	}
	var wrapper = $('<div>').attr({
		"class" : rightLeftClass+ " " + getProgressStatusClass(status)
	});
	var rightBorder = $('<div>').attr({
		"class" : rightLeftClass+"-border"
	});
	var header = $('<div>').attr({
		"class" : rightLeftClass+"-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : rightLeftClass+"-header-txt "+floatClass
	}).html(displayContent);
	
	header.append(headerTxt);
	var workItem = wrapper.append(rightBorder).append(header);
	
	for	(index = 0; index < childList.length; index++) {
		var txtRow1 = $('<div>').attr({
			"class" : rightLeftClass+"-text"
		}).html(childList[index].displayContent);	
		workItem.append(txtRow1);
	}	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}


function appendAgentMilestone1003Status(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-in-progress"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("1003 Complete");
	
	header.append(headerTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("LQB 654823");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneCreditBureau(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-complete"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Credit bureau");
	
	header.append(headerTxt);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Credit Score EQ - 686 ~ TU - 694 ~ Ex - 714");
	
	var txtRow2 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("AUS (Automated Underwriting)");
	
	var txtRow3 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Loan Manager Decision: Pass/Declined");
	
	var txtRow4 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("QC");
	
	wrapper.append(rightBorder).append(header).append(txtRow1).append(txtRow2).append(txtRow3).append(txtRow4);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneNeededItems(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Needed Items");
	
	header.append(headerTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Click here to add needed item");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneAddTeam(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-complete"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Add Team");
	
	header.append(headerTxt);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Click here to add team member");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneDisclosures(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Disclosures/Intent To Proceed");
	
	header.append(headerTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Click here to add disclosures");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneApplicationFee(){
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
	}).html("Appication Fee");
	
	header.append(headerTxt);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Click here to edit application fee");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneAppraisal(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Appraisal");
	
	header.append(headerTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Click here to start appraisal");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneLockRate(){
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
	}).html("Lock Your Rate");
	
	header.append(headerTxt);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Click here to lock rate");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneUnderwriting(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Underwriting Status");
	
	header.append(headerTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Pending");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendAgentMilestoneClosingStatus(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Loan Closed/Declined");
	
	header.append(headerTxt);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Closing status");
	
	wrapper.append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

