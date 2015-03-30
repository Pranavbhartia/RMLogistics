//Function to paint to loan progress page
var countOfTasks = 0;
var workFlowContext = {
	init : function(loanId) {
		this.countOfTasks = 0;
		this.loanId = loanId;
	},
	itemsStatesToBeFetched:[],
	customerWorkflowID : {},
	dataContainer:{},
	loanManagerWorkflowID : {},
	currentRole : {},
	loanId : {},
	initAttempted:false,
	mileStoneSteps : [],
	mileStoneStepsStructured : [],
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
								if(response.resultObject.loanManagerWorkflowID==0){
									if(ob.initAttempted){
										showToastMessage("Master Tables Not Populated")
									}else{
										ob.initAttempted=true;
										ob.createWorkflow(function(ob){
											ob.getWorkflowID(callback);
										});
									}
								}else{
									ob.customerWorkflowID = response.resultObject.customerWorkflowID;
									ob.loanManagerWorkflowID = response.resultObject.loanManagerWorkflowID;	
									if(callback){
										callback(ob);
									}
								}
								
							}
							
						});

	},
	createWorkflow : function(callback) {
		var ob = this;
		var data = {};
		
		ob.ajaxRequest(
						"rest/workflow/create/" + ob.loanId,
						"GET",
						"json",
						data,
						function(response) {
							if (response.error) {
								showToastMessage(response.error.message)
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
			ajaxURL = ajaxURL  + ob.customerWorkflowID;
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
	structureParentChild:function(){
		var ob=this;
		var tempList=ob.mileStoneSteps;
		var finalList=[];
		for(var i=0;i<tempList.length;i++){
			var currentWorkItem = tempList[i];
			if(currentWorkItem.parentWorkflowItemExec){
				continue;
			}else{
				var parent=JSON.parse(JSON.stringify(currentWorkItem))
				var childList=[];
				for(var j=i+1;j<tempList.length;j++){
					var currChild= tempList[j];
					if(currChild.parentWorkflowItemExec){
						if(currChild.parentWorkflowItemExec.id==parent.id){
							childList.push(currChild)
						}
					}else{
						continue;
					}
				}
				parent.childList=childList;
				finalList.push(parent)
			}
		}
		ob.mileStoneStepsStructured=finalList;
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
		workFlowContext.itemsStatesToBeFetched=[];
		workFlowContext.mileStoneContextList={};
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
	fetchLatestStatus:function(callback){
		var ob=this;
		var data={};
		data.items=JSON.stringify(ob.itemsStatesToBeFetched);
		data.data={};
		data.data.userID=newfiObject.user.id;
		data.data.loanID=ob.loanId;
		data.data=JSON.stringify(data.data);
		ajaxRequest("rest/workflow/getupdatedstatus", "GET", "json", data, function(response) {
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
	initialize : function(role, callback) {
		this.getWorkflowID(function(ob) {
			ob.getMileStoneSteps(role, function(ob) {
				ob.renderMileStoneSteps();
			});
		});

		if (callback) {
			callback();
		}
	},
	getCssClassForWfItem : function(wfItemType,stateNumber){
		
		var status="active";
		if(stateNumber>1)
			status="inactive";
		//TODO-we have used "ms-icn-application-fee" as a default where we still are yet to receive icons
		var cssMap={
		INITIAL_CONTACT : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		SYSTEM_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		RATES_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		APP_EDU : { active : "ms-icn-application-status"	, inactive : "m-not-started ms-icn-application-status"	},
		COMM_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		NEEDS_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		LOAN_PROGRESS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		PROFILE_INFO : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		"1003_COMPLETE" : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		CREDIT_BUREAU : { active : "ms-icn-credit-status"	, inactive : "m-not-started ms-icn-credit-status"	},
		CREDIT_SCORE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		AUS_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		QC_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		NEEDS_STATUS : { active : "ms-icn-initial-need-list"	, inactive : "m-not-started ms-icn-initial-need-list"	},
		TEAM_STATUS : { active : "ms-icn-team"	, inactive : "m-not-started ms-icn-team"	},
		DISCLOSURE_STATUS : { active : "ms-icn-disclosure"	, inactive : "m-not-started ms-icn-disclosure"	},
		APP_FEE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		APPRAISAL_STATUS : { active : "ms-icn-appraisal"	, inactive : "m-not-started ms-icn-appraisal"	},
		LOCK_RATE : { active : "ms-icn-lock-rate"	, inactive : "m-not-started ms-icn-lock-rate"	},
		UW_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		CLOSURE_STATUS : { active : "ms-icn-closing-status"	, inactive : "m-not-started ms-icn-closing-status"	},
		MANAGE_PROFILE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_ACCOUNT : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_ONLINE_APP : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_PHOTO : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_SMS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_APP_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		CONNECT_ONLINE_APP : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		CONTACT_LOAN_MANAGER : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_CREDIT_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_TEAM : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		MANAGE_APP_FEE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		LOCK_YOUR_RATE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		VIEW_APPRAISAL : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		VIEW_UW : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
		VIEW_CLOSING : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	}
		}
		if(cssMap[wfItemType] && cssMap[wfItemType][status])
			return cssMap[wfItemType][status]
		
	},getItemToAppendTo: function(newLine,inline,workflowItem){
		if(workflowItem.workflowItemType=="CREDIT_SCORE"){
			return inline;
		}else if(workflowItem.workflowItemType=="AUS_STATUS"){
			return inline;
		}else if(workflowItem.workflowItemType=="QC_STATUS"){
			return inline;
		}else if(workflowItem.workflowItemType=="LOAN_MANAGER_DECISION"){
			return inline;
		}
		return newLine;
	}
};
function getInternalEmployeeMileStoneContext(mileStoneId, workItem) {

	var internalEmployeeMileStoneContext = {
		
		mileStoneId : mileStoneId,
		workItem : workItem,
		stateInfoContainer:undefined,
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
			var floatClass="";
			if(rightLeftClass.indexOf("rc")>=0){
				floatClass="";
			}else{
				floatClass="float-right"
			}
			var txtRow1 = $('<span>').attr({
				"class" : rightLeftClass + "-text "+floatClass,
				"mileNotificationId" : ob.workItem.id,
				"data-text" : ob.workItem.workflowItemType
			});
			var ajaxURL = "";	
			//need to move this logic to a different function
			if (ob.workItem.workflowItemType=="INITIAL_CONTACT")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Schedule An Alert";
			}
			else if (ob.workItem.workflowItemType=="CREDIT_SCORE")
			{				
				ajaxURL = "";
				ob.workItem.stateInfo = "EQ-646 | TU-686 | EX-685";
			}
			else if (ob.workItem.workflowItemType=="1003_COMPLETE")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Click here to view application ";			
			} 			
			else if (ob.workItem.workflowItemType == "DISCLOSURE_STATUS"||
				ob.workItem.workflowItemType == "DISCLOSURE_DISPLAY") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID=ob.workItem.workflowItemType == "DISCLOSURE_DISPLAY"?newfiObject.user.defaultLoanId:selectedUserDetail.loanID;
			}else if (ob.workItem.workflowItemType == "LOCK_RATE"||
				ob.workItem.workflowItemType == "LOCK_YOUR_RATE") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID=ob.workItem.workflowItemType == "LOCK_YOUR_RATE"?newfiObject.user.defaultLoanId:selectedUserDetail.loanID;
			}
			else if (ob.workItem.workflowItemType=="APP_FEE")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Pending";
			}
			else if (ob.workItem.workflowItemType=="APPRAISAL_STATUS")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Click here to view Appraisal Status";
			}
			else if (ob.workItem.workflowItemType=="UW_STATUS")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Click here to view Underwriting status";
			}			
			else if (ob.workItem.workflowItemType=="CLOSURE_STATUS")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Closing Status";
			}
			else if (ob.workItem.workflowItemType=="NEEDS_STATUS")
			{
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID=selectedUserDetail.loanID;
				callback =paintNeedsInfo;			
			}
			else if (ob.workItem.workflowItemType == "TEAM_STATUS") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;				
				data.loanID=selectedUserDetail.loanID;
				callback = paintMilestoneTeamMemberTable;				
			}else if (ob.workItem.workflowItemType=="MANAGE_CREDIT_STATUS")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "EQ-?? | TU-?? | EX-??";			
			}
			
			else if (ob.workItem.workflowItemType=="MANAGE_APP_FEE")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Click Here to Pay Application Fee";		
			}
			else if (ob.workItem.workflowItemType=="LOCK_YOUR_RATE")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Click here to lock your rate";
						
			}
			else if (ob.workItem.workflowItemType=="VIEW_APPRAISAL")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Not ordered";
						
			}
			else if (ob.workItem.workflowItemType == "MANAGE_TEAM") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.OTHURL="rest/workflow/execute/"+ob.mileStoneId;				
				data.loanID = newfi.user.defaultLoanId;
				callback = paintMilestoneTeamMemberTable;		
			}
			
			else if (ob.workItem.workflowItemType=="VIEW_UW")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "N/A";
			}
			else if (ob.workItem.workflowItemType=="VIEW_CLOSING")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Closed On";
				workItem.stateInfo = "Closed on.";
						
			}else if(ob.workItem.workflowItemType=="MANAGE_APP_STATUS"){
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID = newfi.user.defaultLoanId;
			}else if(ob.workItem.workflowItemType=="MANAGE_PROFILE"){
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.userID=newfiObject.user.id;
				data.loanID = newfi.user.defaultLoanId;
			}
			txtRow1.bind("click", function(e) {
				milestoneChildEventHandler(e)
			});
			ob.stateInfoContainer=txtRow1
			itemToAppendTo.append(txtRow1);
			if(ajaxURL&&ajaxURL!=""){
				ob.ajaxRequest(ajaxURL, "POST", "json", JSON.stringify(data),
					function(response) {
						if (response.error) {
							showToastMessage(response.error.message)
						} else {
							ob.workItem.stateInfo=  response.resultObject;							
						}
						if (callback) {
							callback(itemToAppendTo,ob.workItem);
						}
						else
						{
							if(ob.workItem.workflowItemType=="MANAGE_APP_STATUS"||ob.workItem.workflowItemType=="MANAGE_PROFILE"){
								if(!ob.workItem.stateInfo)
									ob.workItem.stateInfo=0;
								if(ob.workItem.stateInfo>=0){
									var progressBarCont = $('<div>').attr({
										"class" : "clearfix"
									});
									var floatClass="float-right";
									if(rightLeftClass.indexOf("rc")>=0){
										floatClass="float-left";
									}
									var progressBarTxt = $('<div>').attr({
										"class" : "miestone-progress-bar-txt "+floatClass
									}).html(ob.workItem.stateInfo+" %");

									var progressBar = $('<div>').attr({
										"class" : "miestone-progress-bar "+floatClass+" clearfix"
									});

									var progress = ob.workItem.stateInfo/10;
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
									ob.stateInfoContainer.append(progressBarCont);
								}
							}else if(ob.workItem.workflowItemType == "DISCLOSURE_STATUS"||
									ob.workItem.workflowItemType == "DISCLOSURE_DISPLAY"){
								if(ob.workItem.stateInfo){
									var tempOb=JSON.parse(ob.workItem.stateInfo);
									if(tempOb.url){
										ob.stateInfoContainer.bind("click",{"tempOb":tempOb},function(event){
											window.open(event.data.tempOb.url,"_blank")
										})
									}
									if(tempOb.status)
										ob.stateInfoContainer.html(tempOb.status);
								}

							}else if (ob.workItem.workflowItemType == "LOCK_RATE"||
									ob.workItem.workflowItemType == "LOCK_YOUR_RATE") {
								if(ob.workItem.stateInfo!="null"){
									ob.stateInfoContainer.html(ob.workItem.stateInfo);
								}else
								{
									ob.stateInfoContainer.html("Click here to lock rate");
								}
							}else
								ob.stateInfoContainer.html(ob.workItem.stateInfo);
							
						}
					});
			}else{
				ob.stateInfoContainer.html(ob.workItem.stateInfo);
			}	


		},
		getCssClassForWfItem : function(wfItemType,stateNumber){
			
			var status="active";
			if(stateNumber>1)
				status="inactive";
			var cssMap={
					INITIAL_CONTACT : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					SYSTEM_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					RATES_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					APP_EDU : { active : "ms-icn-application-status"	, inactive : "m-not-started ms-icn-application-status"	},
					COMM_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					NEEDS_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					LOAN_PROGRESS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					PROFILE_INFO : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					"1003_COMPLETE" : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					CREDIT_BUREAU : { active : "ms-icn-credit-status"	, inactive : "m-not-started ms-icn-credit-status"	},
					CREDIT_SCORE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					AUS_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					QC_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					NEEDS_STATUS : { active : "ms-icn-initial-need-list"	, inactive : "m-not-started ms-icn-initial-need-list"	},
					TEAM_STATUS : { active : "ms-icn-team"	, inactive : "m-not-started ms-icn-team"	},
					DISCLOSURE_STATUS : { active : "ms-icn-disclosure"	, inactive : "m-not-started ms-icn-disclosure"	},
					APP_FEE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					APPRAISAL_STATUS : { active : "ms-icn-appraisal"	, inactive : "m-not-started ms-icn-appraisal"	},
					LOCK_RATE : { active : "ms-icn-lock-rate"	, inactive : "m-not-started ms-icn-lock-rate"	},
					UW_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					CLOSURE_STATUS : { active : "ms-icn-closing-status"	, inactive : "m-not-started ms-icn-closing-status"	},
					MANAGE_PROFILE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_ACCOUNT : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_ONLINE_APP : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_PHOTO : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_SMS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_APP_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					CONNECT_ONLINE_APP : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					CONTACT_LOAN_MANAGER : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_CREDIT_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_TEAM : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					MANAGE_APP_FEE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					LOCK_YOUR_RATE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					VIEW_APPRAISAL : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					VIEW_UW : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
					VIEW_CLOSING : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	}
					}
			if(cssMap[wfItemType] && cssMap[wfItemType][status])
				return cssMap[wfItemType][status]
			
		}
	};
	return internalEmployeeMileStoneContext;
}

function paintNeedsInfo(itemToAppendTo,workItem)
{
	rightLeftClass = "milestone-lc";
	var txtRow1 = $('<div>').attr({
		"class" : rightLeftClass + "-text" + " milestone-plain-text",
	});
	var information=JSON.parse(workItem.stateInfo);
	txtRow1.html(information.totalSubmittedItem + " out of " + information.neededItemRequired);
	
	itemToAppendTo.append(txtRow1);
	
	txtRow1 = $('<div>').attr({
			"class" : rightLeftClass + "-text",
			"mileNotificationId" : workItem.id,
			"data-text" : workItem.workflowItemType
		});
		txtRow1.html("Click here to upload more items");
		txtRow1.bind("click", function(e) {
			milestoneChildEventHandler(e)
		});
		itemToAppendTo.append(txtRow1);	
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
	else
		showMilestoneAddTeamMemberPopup();
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

function paintMilestoneTeamMemberTable(appendTo,workItem){
	
	var userList=JSON.parse(workItem.stateInfo);
	appendTo.append(getMilestoneTeamMembeTable(userList,workItem));
}

function getMilestoneTeamMembeTable(input,workItem) {

	var tableContainer = $('<div>').attr({
		"class" : "ms-team-member-table"
	});

	userList=input.users;
	if(!userList ||  userList.length==0)
		return;
	
	var addNewMember = $('<div>').attr({
		"class" : "milestone-rc-text showAnchor",
		"data-text" : workItem.workflowItemType,
		"mileNotificationId":workItem.id
	}).html("Click here to add a Team Member").bind("click", function(e) {
		milestoneChildEventHandler(e)
	});

	
	tableContainer.append(addNewMember);
	
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
		tableContainer.append(getMilestoneTeamMembeTableRow(user));
	}
	var homwOwnInsurance=input.homeOwnInsCompany;
	var titleCompany=input.titleCompany;
	if(homwOwnInsurance!=null){
		homwOwnInsurance.homeOwnInsID=homwOwnInsurance.id;
		homwOwnInsurance.firstName=homwOwnInsurance.name;
		homwOwnInsurance.emailId=homwOwnInsurance.emailID;
		homwOwnInsurance.userRole={
				label : "Home Owners Insurance"
		};
		var tableRow = getMilestoneTeamMembeTableRow(homwOwnInsurance);
		tableContainer.append(tableRow);
	}
	
	if(titleCompany!=null){
		titleCompany.titleCompanyID=titleCompany.id;
		titleCompany.firstName=titleCompany.name;
		titleCompany.emailId=titleCompany.emailID;
		titleCompany.userRole={
				label : "Title Company"
		};
		var tableRow = getMilestoneTeamMembeTableRow(titleCompany);
		tableContainer.append(tableRow);
	}
	return tableContainer;
}

function getMilestoneTeamMembeTableRow(user){
	if(user.lastName==undefined)user.lastName="";
	var dispName = user.firstName+" "+user.lastName;
	var userRole = user.userRole;
	var roleLabel = userRole.label;
	if (userRole.id == 3) {
		if (user.internalUserDetail
				&& user.internalUserDetail.internalUserRoleMasterVO
				&& user.internalUserDetail.internalUserRoleMasterVO.roleDescription)
			roleLabel = user.internalUserDetail.internalUserRoleMasterVO.roleDescription;
	}
	return getMilestoneTeamMemberRow(dispName, roleLabel,user.id);
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
function getMilestoneTeamMemberRow(name, title,userID) {
	var row = $('<div>').attr({
		"class" : "ms-team-member-tr clearfix",
		"userID":userID
	});

	var nameCol = $('<div>').attr({
		"class" : "ms-team-member-tr-col1 float-left"
	}).html(name);

	var titleCol = $('<div>').attr({
		"class" : "ms-team-member-tr-col2 float-left"
	}).html(title);

	return row.append(nameCol).append(titleCol);
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
	if(newfiObject.user.photoImageUrl){
		custImg.attr("style" , "background-image : url("+newfiObject.user.photoImageUrl+"); background-size: cover;");
	}
	var custTxtContainer = $('<div>').attr({
		"class" : "ms-cust-prof-txt-cont float-left"
	});

	var name = $('<div>').attr({
		"class" : "ms-cust-prof-txt-name"
	}).html(newfiObject.user.firstName+" "+newfiObject.user.lastName);

	var role = $('<div>').attr({
		"class" : "ms-cust-prof-txt-role"
	}).html("Home Buyer");

	var contact = $('<div>').attr({
		"class" : "ms-cust-prof-txt-contact"
	}).html(newfiObject.user.phoneNumber);

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
	if (status == 3) {
		progressClass = "m-complete";
	} else if (status == 1||status == 2) {
		progressClass = "m-in-progress";
	}
	return progressClass;
}
function updateMileStoneElementState(url,data,callback,targetElement){
	data=JSON.stringify(data);
	ajaxRequest(url,"POST","json",data,function(response){
		if(response.error){
			showToastMessage(response.error.message);
		}else{
			if(callback){
				callback(response.resultObject,targetElement);
			}
		}
		
	});
}
function checkboxActionEvent(workflowItem,targetElement,callback){
	var wf=workflowItem;
	var wfContainer=$("#WF"+wf.id);
	var parentChk=wfContainer.attr("WFparent");
	var childChk=wfContainer.attr("WFchild");
	var targetData={};
	targetData.targetElement=targetElement;
	targetData.parentChk=parentChk;
	targetData.childChk=childChk;
	if(parentChk){
		var url="rest/workflow/changestateofworkflowitemexec/"+wf.id;
		var data={};
		data.status="1";//since we will send only completed status from frontend
		data["workflowItemExecId"]=wf.id;
		data["loanID"]=workFlowContext.loanID;
		updateMileStoneElementState(url,data,callback,targetData)
	}else{
		var url="rest/workflow/invokeaction/"+wf.id;
		var data={};
		data["EMAIL_RECIPIENT"]=selectedUserDetail.emailId;
		data["EMAIL_TEMPLATE_NAME"]="90d97262-7213-4a3a-86c6-8402a1375416";
		data["EMAIL_RECIPIENT_NAME"]=selectedUserDetail.name;
		data["loanID"]=workFlowContext.loanId;
		data["workflowItemExecId"]=wf.id;
		updateMileStoneElementState(url,data,callback,targetData)
	}
	/*if(callback){
		callback({},targetData);
	}*/
}
function updateMilestoneView(wf,data,targetData){
	targetElement=targetData.targetElement;
	if(data.match(/success/i)){
		if(targetData.parentChk){
			targetElement.attr("data-checked", "checked");
			var parentContainer=$("#WF"+wf.id);
			clearStatusClass(parentContainer);
			parentContainer.addClass("m-complete");
		}else{
			var parentid=wf.parentWorkflowItemExec.id;
			targetElement.attr("data-checked", "checked");
			var parentContainer=$("#WF"+parentid);
			var childs=parentContainer.find(".milestone-rc-text");
			var childsChecked=true;
			for(var i=0;i<childs.length;i++){
				var child=childs[i];
				var childCheckbox=$(child).find(".ms-check-box");
				if($(childCheckbox).attr("data-checked")=="unchecked"){
					childsChecked=false;
				}
			}
			if(childsChecked){
				var parentCheckBox=parentContainer.find(".ms-check-box-header");
				$(parentCheckBox).attr("data-checked","checked");
				clearStatusClass(parentContainer);
				parentContainer.addClass("m-complete");
			}else{
				clearStatusClass(parentContainer);
				parentContainer.addClass("m-in-progress");
			}
		}
		
	}
}
function clearStatusClass(container){
	container.removeClass("m-not-started");
	container.removeClass("m-in-progress");
	container.removeClass("m-complete");
}
function getStatusClass(workflowItem){
	switch(workflowItem.status){
		case "0":
			return "unchecked";
		case "1":
			return "unchecked";
		case "2":
			return "unchecked";
		case "3":
			return "checked";
	}
}
function appendMilestoneItem(workflowItem, childList) {

	countOfTasks++;
	var floatClass = "float-right";
	if(workflowItem.status!=3)
		workFlowContext.itemsStatesToBeFetched.push(workflowItem.id);
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
		"class" : rightLeftClass + " " + progressClass,
		"id":"WF"+workflowItem.id
	});
	wrapper.attr("WFparent",true);
	
	var rightBorder = $('<div>').attr({
		"class" : rightLeftClass + "-border"
	});
	var header = $('<div>').attr({
		"class" : rightLeftClass + "-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : rightLeftClass + "-header-txt " + floatClass
	}).html(workflowItem.displayContent);


	var headerIcn = $('<div>').attr({
		"class" : rightLeftClass+"-header-icn "
	}).addClass(workFlowContext.getCssClassForWfItem(workflowItem.workflowItemType,workflowItem.status)).addClass(floatClass);
	
	var headerCheckBox = $('<div>').attr({
		"class" : "ms-check-box-header box-border-box " + floatClass,
		"data-checked" : getStatusClass(workflowItem)
	});
	if(workflowItem.clickable){
		headerCheckBox.bind('click', {
			"workflowItem" : workflowItem
		}, function(event) {
			event.stopImmediatePropagation();
			if ($(this).attr("data-checked") == "unchecked") {
				var wf=event.data.workflowItem;
				var targetElement=$(this);
				checkboxActionEvent(wf,targetElement,function(data,targetData){
					updateMilestoneView(wf,data,targetData);
				})
				
			} 
			
			// getLoanDetails(loanID);
		})
	}
	headerTxt.append(headerCheckBox);
	header.append(headerTxt).append(headerIcn);

	wrapper.append(rightBorder).append(header);
	var itemToAppendTo=workFlowContext.getItemToAppendTo(wrapper,header,workflowItem);
	var WFContxt=appendInfoAction(rightLeftClass, itemToAppendTo, workflowItem);
	workFlowContext.mileStoneContextList[workflowItem.id]=WFContxt;
	if (childList != null) {
		for (index = 0; index < childList.length; index++) {
			var childRow = $('<div>').attr({
				"class" : rightLeftClass + "-text"+" clearfix",
				"mileNotificationId" : childList[index].id,
				"data-text" : childList[index].workflowItemType,
				"id":"WF"+childList[index].id
			}).html(childList[index].displayContent);
			childRow.attr("WFchild",true);
			childRow.bind("click", function(e) {
				milestoneChildEventHandler(e)
			});
			if(childList[index].status!=3)
				workFlowContext.itemsStatesToBeFetched.push(childList[index].id);

			var itemCheckBox = $('<div>').attr({
				"class" : "ms-check-box box-border-box " + floatClass,
				"data-checked" : getStatusClass(childList[index])
			})
			if(childList[index].clickable){
				itemCheckBox.bind('click', {
					"workflowItem" : childList[index]
				}, function(event) {
					event.stopImmediatePropagation();
					if ($(this).attr("data-checked") == "unchecked") {
						var wf=event.data.workflowItem;
						var targetElement=$(this);
						checkboxActionEvent(wf,targetElement,function(data,targetData){
							updateMilestoneView(wf,data,targetData);
						})
						
					} 
			
			// getLoanDetails(loanID);
				})
			}
			childRow.append(itemCheckBox);
			wrapper.append(childRow);
			var itemToAppendTo=workFlowContext.getItemToAppendTo(wrapper,childRow,childList[index]);
			var WFContxt=appendInfoAction(rightLeftClass, itemToAppendTo, childList[index]);
			workFlowContext.mileStoneContextList[childList[index].id]=WFContxt;
			
		}
	}
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

// this will add a "Information Link" that is clickable to the task.
function appendInfoAction (rightLeftClass, itemToAppendTo, workflowItem)
{
	var mileStoneStepContext = getInternalEmployeeMileStoneContext(workflowItem.id,workflowItem);
	
	mileStoneStepContext.getStateInfo(rightLeftClass,itemToAppendTo);
	
	return mileStoneStepContext;
}
function milestoneChildEventHandler(event) {
	// condition need to be finalized for identifying each element
	
	if ($(event.target).attr("data-text") == "INITIAL_CONTACT") {
		event.stopPropagation();
		var data = {};
		data.milestoneId = event.target.getAttribute("milenotificationid");
		data.OTHURL = "rest/workflow/invokeaction/"+data.milestoneId;
		addNotificationPopup(selectedUserDetail.loanID, event.target, data);
	} else if  ($(event.target).attr("data-text") == "TEAM_STATUS")
		{
		event.stopPropagation();
		var teamTable = getMilestoneTeamMembeTable();
		var data = {};
		data.milestoneID=$(event.target).attr("mileNotificationId");
		data.OTHURL = "rest/workflow/invokeaction/"+data.milestoneID;
		data.loanID = selectedUserDetail.loanID;
		appendMilestoneAddTeamMemberPopup(selectedUserDetail.loanID,
				event.target, data);
		var context=getCreateTitleCompanyContext(
				newfiObject.user.defaultLoanId);
		context.createTitleCompanyPopup();

				
		context = getCreateHomeOwnInsCompanyContext(newfiObject.user.defaultLoanId)
		context.createTitleCompanyPopup();
	}
	else if  ($(event.target).attr("data-text") == "MANAGE_TEAM") {
		event.stopPropagation();
		//var teamTable = getMilestoneTeamMembeTable();
		var data = {};
		data.milestoneID=$(event.target).attr("mileNotificationId");
		data.OTHURL = "rest/workflow/invokeaction/"+data.milestoneID;
		data.loanID = newfi.user.defaultLoanId;
		appendMilestoneAddTeamMemberPopup(newfi.user.defaultLoanId,
				event.target, data);
		var context=getCreateTitleCompanyContext(
				newfiObject.user.defaultLoanId);
		context.createTitleCompanyPopup();

				
		context = getCreateHomeOwnInsCompanyContext(newfiObject.user.defaultLoanId)
		context.createTitleCompanyPopup();
	}
	 else if ($(event.target).attr("data-text") == "NEEDS_STATUS") {
	 	event.stopPropagation();
		 $("#lp-step4").click();
	}
	 else if ($(event.target).attr("data-text") == "1003_COMPLETE") {
	 	event.stopPropagation();
		 $("#lp-step1").click();
	}else if ($(event.target).attr("data-text") == "LOCK_RATE") {
	 	event.stopPropagation();
		 window.location.hash="#loan/1/lock-rate"
	}else if ($(event.target).attr("data-text") == "LOCK_YOUR_RATE") {
	 	event.stopPropagation();
		window.location.hash="#myLoan/lock-my-rate"
	}else if ($(event.target).attr("data-text") == "MANAGE_APP_FEE") {
	 	event.stopPropagation();
		console.log("Pay application fee clicked!");
		showOverlay();
		$('body').addClass('body-no-scroll');
		url = "rest/payment/paymentpage.do";
		
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
	}else if ($(event.target).attr("data-text") == "CONTACT_LOAN_MANAGER") {
	 	event.stopPropagation();
	 	if(workFlowContext.dataContainer.managerList){
	 		appendLoanManagerPopup($(event.target),workFlowContext.dataContainer.managerList)
	 	}else{
	 		var data={}
	 		ajaxRequest(
				"rest/loan/"+workFlowContext.loanId+"/manager",
				"GET",
				"json",
				data,
				function(response) {
					if (response.error) {
						showToastMessage(response.error.message)
					}else{
						workFlowContext.dataContainer.managerList=response.resultObject;
						appendLoanManagerPopup($(event.target),workFlowContext.dataContainer.managerList)
					}
			},false);
	 	}
	}
}


//Functions to view loan manager details in customer page

function removeLoanManagerPopup(){
	$('#loan-manager-popup').remove();
}

function appendLoanManagerPopup(element,loanManagerArray){
	
	removeLoanManagerPopup();
	
	var leftOffset = $(element).offset().left;
	var topOffset = $(element).offset().top;
	
	var wrapper = $('<div>').attr({
		"id" : "loan-manager-popup",
		"class" : "loan-manager-popup"
	}).css({
		"left" : leftOffset,
		"top" : topOffset
	});
	
	for(var i=0;i<loanManagerArray.length;i++){
		var loanManagerObj = loanManagerArray[i];
		
		var container = $('<div>').attr({
			"class" : "loan-manager-popup-container clearfix"
		});
		
		var profilePic = $('<div>').attr({
			"class" : "lp-pic float-left"
		});
		
		if(loanManagerObj.photoImageUrl != undefined && loanManagerObj.photoImageUrl != ""){
			profilePic.attr("style","background-image:url('"+loanManagerObj.photoImageUrl+"')");
		}
		
		var profileDetails = $('<div>').attr({
			"class" : "lm-details-txt-col float-left"
		});
		
		var name = $('<div>').attr({
			"class" : "lp-txt1"
		}).html(loanManagerObj.displayName);
		
		var emailId = $('<div>').attr({
			"class" : "lp-txt2"
		}).html(loanManagerObj.emailId);
		
		var phoneNumber = $('<div>').attr({
			"class" : "lp-txt2"
		});
		
		if(loanManagerObj.phoneNumber != undefined){
			phoneNumber.html(formatPhoneNumberToUsFormat(loanManagerObj.phoneNumber));
		}
		
		profileDetails.append(name).append(emailId).append(phoneNumber);
		
		container.append(profilePic).append(profileDetails);

		wrapper.append(container);
	}
	
		
	$('body').append(wrapper);
}

$(document).resize(function(){
	removeLoanManagerPopup();
});

$(document).on('click',function(){
	removeLoanManagerPopup();
});

$(document).on('click','#loan-manager-popup',function(e){
	e.stopPropagation();
});