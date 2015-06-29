//Function to paint to loan progress page
var countOfTasks = 0;
var LOAN_MANAGER="Loan Advisor";
var SALES_MANAGER="Sales Manager";
var COMPLETED = "3";
var NOT_STARTED = "0";
var RENDER_RIGHT = "RIGHT";
var RENDER_LEFT = "LEFT";
var milestoneAction=""
var workFlowContext = {
	init : function(loanId, customer) {
		this.countOfTasks = 0;
		this.loanId = loanId;
		this.customer = customer;		
	},
	itemsStatesToBeFetched:[],
	customerWorkflowID : {},
	dataContainer:{},
	loanManagerWorkflowID : {},
	currentRole : {},
	loanId : {},
	customerId:{},
	
	initAttempted:false,
	mileStoneSteps : [],
	mileStoneStepsStructured : [],
	milestoneStepsLookup:{},
	mileStoneContextList : {},
	mileStoneStatuses:[],
	mileStoneStatusLookup:{},
	getWorkflowID : function(callback) {
		var ob = this;
		var data = {};
		
		ajaxRequest(
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
										showToastMessage(masterTable);
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
		
		ajaxRequest(
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
		ajaxRequest(ajaxURL, "GET", "json", data, function(response) {
			if (response.error) {
				showToastMessage(response.error.message)
			} else {
				ob.mileStoneSteps = response.resultObject;
				ob.createMileStoneStepsLookup();
			}
			if (callback) {
				callback(ob);
			}
		});

	},
	getMilestonesStatusForLoan : function( callback) {
		var ob = this;
		var data = {};
		
		var ajaxURL = "rest/workflow/milestones/";		
		ajaxURL = ajaxURL  + ob.loanId;		
		ajaxRequest(ajaxURL, "GET", "json", data, function(response) {
			if (response.error) {
				showToastMessage(response.error.message)
			} else {
				ob.mileStoneStatuses = response.resultObject;				
				//Do somethng here	
				ob.createMileStoneStatusLookup();
			}
			if (callback) {
				callback(ob);
			}
		});

	},
	createMileStoneStepsLookup :function ()
	{
		var ob=this;
		ob.milestoneStepsLookup = {};
		for ( stepNo = 0; stepNo < ob.mileStoneSteps.length; stepNo++)
		{
			ob.milestoneStepsLookup[ob.mileStoneSteps[stepNo].workflowItemType]=ob.mileStoneSteps[stepNo];
		}
	},
	createMileStoneStatusLookup :function ()
	{
		var ob=this;
		ob.mileStoneStatusLookup = {};
		for ( stepNo = 0; stepNo < ob.mileStoneStatuses.length; stepNo++)
		{
			var loanMilestone = ob.mileStoneStatuses[stepNo];
			var loanMilestoneName = loanMilestone.loanMilestoneMaster.name;
			var loanMilestoneStatus = loanMilestone.status;
			ob.mileStoneStatusLookup[loanMilestoneName]=loanMilestoneStatus;
		}
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
	getStructuredParent:function(parentId){
		var ob=this;
		for(var i=0;i<ob.mileStoneStepsStructured.length;i++){
			var parent=ob.mileStoneStepsStructured[i];
			if(parent.id==parentId){
				return ob.mileStoneStepsStructured[i];
			}
		}
		return null;
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

		for (var i = 0; i < ob.mileStoneStepsStructured.length; i++) {
			var parentWorkItem=ob.mileStoneStepsStructured[i];
			appendMilestoneItem(parentWorkItem, parentWorkItem.childList);
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
		data.data.userID=workFlowContext.customer.id;
		data.data.loanID=ob.loanId;
		data.data=JSON.stringify(data.data);
		ajaxRequest("rest/workflow/getupdatedstatus", "GET", "json", data, function(response) {
			if (response.error) {
				showToastMessage(response.error.message)
			} else {
				for(var key in response.resultObject){
					if(response.resultObject[key]!=""){
						var contxt=workFlowContext.mileStoneContextList[key];
						contxt.updateMilestoneView(response.resultObject[key]);
					}
				}
			}
			if (callback) {
				callback(ob);
			}
			ob.performAction();
		});
	},
	performAction:function(){
		if(hAction){
			var notificationTyp=mapNotificationToMilestone(hAction);
			var workFlowItem=workFlowContext.milestoneStepsLookup[mapNotificationToMilestone(hAction)];
			if(workFlowItem){
				var contxt=workFlowContext.mileStoneContextList[workFlowItem.id];
				if(contxt){
					var stateContainer=contxt.stateInfoContainer
					if(stateContainer){
						var topOffset= stateContainer.offset().top;
						$(window).scrollTop(topOffset-100);
					}
				}
			}
		}
	},
	initialize : function(role, callback) {
		this.getWorkflowID(function(ob) {
			
			ob.getMileStoneSteps(role, function(ob) {
				ob.structureParentChild();				
				ob.renderMileStoneSteps(function(){
					ob.fetchLatestStatus();					
				});
				if (role != "AGENT")
				{
					ob.getMilestonesStatusForLoan(function (){								
						showProgressHeaderSteps();
					}
					);
				}
			});
			
		});

		if (callback) {
			callback();
		}
	},
	getItemToAppendTo: function(newLine,inline,workflowItem){
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
	},
	getCssClassForWfItem : function(wfItemType,stateNumber){
		
		var status="active";
		if(stateNumber>1)
			status="inactive";
		var cssMap={
				INITIAL_CONTACT : { active : "ms-icn-application-fee-initial-contact"	, inactive : "m-not-started ms-icn-application-fee-initial-contact"	},
				SYSTEM_EDU : { active : "ms-icn-application-fee-2"	, inactive : "m-not-started ms-icn-application-fee-2"	},
				RATES_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				APP_EDU : { active : "ms-icn-application-status"	, inactive : "m-not-started ms-icn-application-status"	},
				COMM_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				NEEDS_EDU : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				LOAN_PROGRESS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				PROFILE_INFO : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				"1003_COMPLETE" : { active : "ms-icn-application-fee-app-status"	, inactive : "m-not-started ms-icn-application-fee-app-status"	},
				CREDIT_BUREAU : { active : "ms-icn-credit-status"	, inactive : "m-not-started ms-icn-credit-status"	},
				CREDIT_SCORE : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				AUS_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				QC_STATUS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				NEEDS_STATUS : { active : "ms-icn-initial-need-list"	, inactive : "m-not-started ms-icn-initial-need-list"	},
				VIEW_NEEDS : { active : "ms-icn-initial-need-list"	, inactive : "m-not-started ms-icn-initial-need-list"	},
				TEAM_STATUS : { active : "ms-icn-team"	, inactive : "m-not-started ms-icn-team"	},
				MANAGE_TEAM : { active : "ms-icn-team"	, inactive : "m-not-started ms-icn-team"	},
				DISCLOSURE_STATUS : { active : "ms-icn-disclosure"	, inactive : "m-not-started ms-icn-disclosure"	},
				DISCLOSURE_DISPLAY : { active : "ms-icn-disclosure"	, inactive : "m-not-started ms-icn-disclosure"	},
				APP_FEE : { active : "ms-icn-application-fee-app"	, inactive : "m-not-started ms-icn-application-fee-app"	},
				MANAGE_APP_FEE : { active : "ms-icn-application-fee-app"	, inactive : "m-not-started ms-icn-application-fee-app"	},
				APPRAISAL_STATUS : { active : "ms-icn-appraisal"	, inactive : "m-not-started ms-icn-appraisal"	},
				VIEW_APPRAISAL : { active : "ms-icn-appraisal"	, inactive : "m-not-started ms-icn-appraisal"	},
				LOCK_RATE : { active : "ms-icn-lock-rate"	, inactive : "m-not-started ms-icn-lock-rate"	},
				LOCK_YOUR_RATE : { active : "ms-icn-lock-rate"	, inactive : "m-not-started ms-icn-lock-rate"	},							
				UW_STATUS : { active : "ms-icn-application-fee-um"	, inactive : "m-not-started ms-icn-application-fee-um"	},
				VIEW_UW : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},		
				CLOSURE_STATUS : { active : "ms-icn-closing-status"	, inactive : "m-not-started ms-icn-closing-status"	},
				VIEW_CLOSING : { active : "ms-icn-closing-status"	, inactive : "m-not-started ms-icn-closing-status"	},
				MANAGE_PROFILE : { active : "ms-icn-application-fee-profile"	, inactive : "m-not-started ms-icn-application-fee-profile"	},
				MANAGE_ACCOUNT : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				MANAGE_ONLINE_APP : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				MANAGE_PHOTO : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				MANAGE_SMS : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				MANAGE_APP_STATUS : { active : "ms-icn-application-fee-app-status"	, inactive : "m-not-started ms-icn-application-fee-app-status"	},
				CONNECT_ONLINE_APP : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				CONTACT_YOUR_LA : { active : "ms-icn-application-fee"	, inactive : "m-not-started ms-icn-application-fee"	},
				MANAGE_CREDIT_STATUS : { active : "ms-icn-credit-status"	, inactive : "m-not-started ms-icn-credit-status"	}														
				}
		if(cssMap[wfItemType] && cssMap[wfItemType][status])
			return cssMap[wfItemType][status]
		
	}
};
function getInternalEmployeeMileStoneContext( workItem) {

	var internalEmployeeMileStoneContext = {
		
		mileStoneId : workItem.id,
		workItem : workItem,
		stateInfoContainer:undefined,
		updateMilestoneView:function(status,callback){
			var ob=this;
			ob.workItem.status=status;
			var container=$("#WF"+ob.workItem.id);
			var parentChk=container.attr("WFparent");
			var childChk=container.attr("WFchild");
			if(parentChk){
				changeContainerClassBasedOnStatus(container,status);
				var parentCheckBox=$(container).find(".ms-check-box-header");
				parentCheckBox.attr("data-checked", getStatusClass(ob.workItem));
				var parent=workFlowContext.getStructuredParent(ob.workItem.id);
				for(var i=0;i<parent.childList.length;i++){
					var childid=parent.childList[i].id;
					var cntxt=workFlowContext.mileStoneContextList[childid];
					if(cntxt.workItem.status!=COMPLETED){
						cntxt.updateMilestoneView(status);
					}
				}
			}else{
				var childCheckBox=$(container).find(".ms-check-box");
				childCheckBox.attr("data-checked", getStatusClass(ob.workItem));
				var parentid=ob.workItem.parentWorkflowItemExec.id;
				var parentContainer=$("#WF"+parentid);
				var childs=parentContainer.find("div."+getContainerLftRghtClass(parentContainer)+"-text");
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
			data.loanID=workFlowContext.loanId;
			//need to move this logic to a different function
			ob.stateInfoContainer=txtRow1;
			if (ob.workItem.workflowItemType=="INITIAL_CONTACT")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "Schedule An Alert";
				$(ob.stateInfoContainer).addClass("cursor-pointer");
				txtRow1.bind("click", function(e) {
					milestoneChildEventHandler(e)
				});
			}
			else if (ob.workItem.workflowItemType=="CONTACT_YOUR_LA")
			{
				ajaxURL = "";
				ob.workItem.stateInfo = "";				
				$(ob.stateInfoContainer).addClass("cursor-pointer");
				txtRow1.addClass("cursor-pointer");
				txtRow1.bind("click", function(e) {
					milestoneChildEventHandler(e)
				});
			}
			else if (ob.workItem.workflowItemType=="1003_COMPLETE")
			{
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID=workFlowContext.loanId;
				data.userID = newfiObject.user.id;		
				callback = showLQBInfo;		
				$(ob.stateInfoContainer).addClass("cursor-pointer");	
			} 			
			else if (ob.workItem.workflowItemType == "DISCLOSURE_STATUS"||
				ob.workItem.workflowItemType == "DISCLOSURE_DISPLAY") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				$(ob.stateInfoContainer).addClass("cursor-pointer");
			}else if (ob.workItem.workflowItemType == "LOCK_RATE"||
				ob.workItem.workflowItemType == "LOCK_YOUR_RATE") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
			}
			else if (ob.workItem.workflowItemType=="APP_FEE" )
			{
				data.userID=workFlowContext.customer.id;
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				callback = showAppFee;	
			}
			else if (ob.workItem.workflowItemType=="MANAGE_APP_FEE" )
			{				
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				txtRow1.bind("click", function(e) {
					milestoneChildEventHandler(e);
				});
			}
			else if (ob.workItem.workflowItemType=="UW_STATUS")
			{
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				
			}			
			else if (ob.workItem.workflowItemType=="CLOSURE_STATUS")
			{
				data.loanID = workFlowContext.loanId;
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
			}
			else if (ob.workItem.workflowItemType=="NEEDS_STATUS"||ob.workItem.workflowItemType=="VIEW_NEEDS")
			{
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				callback =paintNeedsInfo;
			}
			else if (ob.workItem.workflowItemType == "TEAM_STATUS") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				callback = paintMilestoneTeamMemberTable;				
			}else if (ob.workItem.workflowItemType=="MANAGE_CREDIT_STATUS"||ob.workItem.workflowItemType=="CREDIT_SCORE")
			{
				data.userID=workFlowContext.customer.id;
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
			}			
			else if (ob.workItem.workflowItemType=="VIEW_APPRAISAL"||ob.workItem.workflowItemType=="APPRAISAL_STATUS")
			{
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				
			}
			else if (ob.workItem.workflowItemType == "MANAGE_TEAM") {
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.OTHURL="rest/workflow/execute/"+ob.mileStoneId;
				data.loanID = workFlowContext.loanId;
				callback = paintMilestoneTeamMemberTable;		
			}
			
			else if (ob.workItem.workflowItemType=="VIEW_UW")
			{
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
			}
			else if (ob.workItem.workflowItemType=="VIEW_CLOSING")
			{
				data.loanID = workFlowContext.loanId;
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;				
				
			}else if(ob.workItem.workflowItemType=="MANAGE_APP_STATUS"){
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.loanID = workFlowContext.loanId;
			}else if(ob.workItem.workflowItemType=="MANAGE_PROFILE"){
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;
				data.userID=workFlowContext.customer.id;
				data.loanID = workFlowContext.loanId;
			}
			else if(ob.workItem.workflowItemType=="LOAN_MANAGER_DECISION"){
				ajaxURL = "rest/workflow/renderstate/"+ob.mileStoneId;				
				data.loanID = workFlowContext.loanId;
			}
			
			
			itemToAppendTo.append(txtRow1);
			if(ajaxURL&&ajaxURL!=""){
				ajaxRequest(ajaxURL, "POST", "json", JSON.stringify(data),
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
							if(ob.workItem.workflowItemType=="MANAGE_APP_STATUS" ||ob.workItem.workflowItemType=="MANAGE_PROFILE") {
								if(!ob.workItem.stateInfo)
									ob.workItem.stateInfo=0;
								if(ob.workItem.stateInfo>=0){
									ob.workItem.stateInfo=Math.round(ob.workItem.stateInfo);
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

									var progress = Math.round(ob.workItem.stateInfo/10);
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
									ob.workItem.workflowItemType == "DISCLOSURE_DISPLAY" || ob.workItem.workflowItemType == "VIEW_APPRAISAL"||
									ob.workItem.workflowItemType == "APPRAISAL_STATUS"){								
								if(ob.workItem.stateInfo){
									txtRow1.bind("click", function(e) {
										milestoneChildEventHandler(e)
									});
									var tempOb=JSON.parse(ob.workItem.stateInfo);
									if(tempOb.url){
										ob.stateInfoContainer.addClass("cursor-pointer");
										ob.stateInfoContainer.bind("click",{"tempOb":tempOb},function(event){
											window.open(generateDownloadURL(tempOb.url),"_blank")
										});
										txtRow1.addClass("cursor-pointer");
									}
									if(tempOb.status)
									{
										ob.stateInfoContainer.html(tempOb.status);
									}
								}

							}else if (ob.workItem.workflowItemType == "LOCK_RATE"||
									ob.workItem.workflowItemType == "LOCK_YOUR_RATE") {
								txtRow1.bind("click", function(e) {
									milestoneChildEventHandler(e)
								});
								txtRow1.addClass("cursor-pointer");
								if(ob.workItem.stateInfo!=null){
									ob.stateInfoContainer.html(ob.workItem.stateInfo);
								}else
								{
									if (ob.workItem.status == COMPLETED)
									{
										ob.stateInfoContainer.html("Click here to view rates");
									}
									else{																		
										ob.stateInfoContainer.html("Click here to lock rate");
									}
									$(ob.stateInfoContainer).addClass("cursor-pointer");
								}
							}else if (ob.workItem.workflowItemType=="MANAGE_CREDIT_STATUS"||
								ob.workItem.workflowItemType=="CREDIT_SCORE"){
								if(ob.workItem.stateInfo){
									txtRow1.bind("click", function(e) {
										milestoneChildEventHandler(e)
									});
									var tempOb=JSON.parse(ob.workItem.stateInfo);
									if(tempOb.url && !userIsRealtor()){
										ob.stateInfoContainer.bind("click",{"tempOb":tempOb},function(event){
											if($(event.target).hasClass("creditScoreClickableClass"))
												return;
											window.open(generateDownloadURL(tempOb.url),"_blank")
										})
										$(ob.stateInfoContainer).addClass("cursor-pointer");
									}
									if(tempOb.status && !userIsRealtor())
									{
										tempOb.status=checkCreditScore(tempOb.status);
										
										if (tempOb.status == "NA") {
											ob.stateInfoContainer.html();
										}
										else
										{
											ob.stateInfoContainer.html(" "+tempOb.status);
										}
									}
										
								}
							}
							
							else{
								ob.stateInfoContainer.html(ob.workItem.stateInfo);
								attachCursorPointerClassToElement(ob.stateInfoContainer);
							}
						}
					});
			}else{
				
				ob.stateInfoContainer.html(ob.workItem.stateInfo);
				attachCursorPointerClassToElement(ob.stateInfoContainer);
			}	
		}		
	};
	return internalEmployeeMileStoneContext;
}
function attachCursorPointerClassToElement(element){	
	if(element.html()=="Click here to pay"){
		element.addClass("cursor-pointer");
	}	
}

function showAppFee (itemToAppendTo,workItem)
{
	rightLeftClass = getContainerLftRghtClass($("#WF"+workItem.id));
	var txtRow2 = $('<div>').attr({
		"class" : rightLeftClass + "-text" ,
		"id":workItem.id+"fee",
		"data-text" : workItem.workflowItemType,
		"mileNotificationId":workItem.id
	});
	if(workItem.stateInfo){
		var tempOb=JSON.parse(workItem.stateInfo);
		if(tempOb.status){
			workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.html(tempOb.status);	
			workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.addClass("cursor-pointer");
			if (workItem.status == NOT_STARTED)
			{				
				workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.attr("data-text",workItem.workflowItemType+ "_PAY_FOR");
				workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.bind("click", function(e) {
					milestoneChildEventHandler(e);
				});						
			}
		}	
		itemToAppendTo.append(txtRow2);	
		if(tempOb.appfee)
		{
			txtRow2.html("$"+tempOb.appfee);					
		}
			
	}
	if( newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleDescription == SALES_MANAGER 
			&& workItem.status == NOT_STARTED)
	{
		itemToAppendTo.append(getAppFeeEdit(workItem));
	}
}
function paintNeedsInfo(itemToAppendTo,workItem)
{
	rightLeftClass = getContainerLftRghtClass($("#WF"+workItem.id));
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
		var spanHolder = $('<span>').attr({
			"class" : "cursor-pointer",
			"mileNotificationId" : workItem.id,
			"data-text" : workItem.workflowItemType
		}).html("Click here to upload more items");
		txtRow1.append(spanHolder);
		
		spanHolder.bind("click", function(e) {
			milestoneChildEventHandler(e)
		});
		itemToAppendTo.append(txtRow1);	
}
function paintCustomerLoanProgressPage() {
	if(!userIsRealtor()){
		workFlowContext.init(newfi.user.defaultLoanId, newfiObject.user);	
	}else{
		workFlowContext.init(selectedUserDetail.loanID, createNewfiUser());
	}
	workFlowContext.initialize("CUSTOMER", function() {
	});
	paintCustomerInfo ()
}

function paintCustomerInfo ()
{
	var wrapper = $('<div>').attr({
		"class" : "loan-progress-wrapper"
	});	
	$('#WFProgressHeaderBar').remove();
	
	var header = $('<div>').attr({
			"class" : "loan-progress-header"
		}).html("loan progress");
	 
	var progressHeader = getCustomerMilestoneLoanProgressHeaderBar();	
	var subText = $('<div>').attr({
		"class" : "loan-progress-sub-txt"
	}).html("Below is a detailed list of your loan progress to date.  Click any link below to work on that portion of the loan.  Focus on the links in orange as they are the most critical items at this time.  As always, please reach out to the newfi team with questions.");
	
	
	var container = $('<div>').attr({
		"id" : "cust-loan-progress",
		"class" : "loan-progress-container"
	});
	wrapper.append(header).append(progressHeader).append(subText).append(container);
	$('#center-panel-cont').append(wrapper);
	
	paintCustomerLoanProgressContainer();
}
function getCustomerMilestoneLoanProgressHeaderBar() {
	var container = $('<div>').attr({
		"class" : "milestone-header-bar clearfix",
		"id" : "WFProgressHeaderBar"			
	});
	
	return container;
}
function showProgressHeaderSteps(){
	var container=$("#WFProgressHeaderBar");
	
	if($('#WFProgressHeaderBar >div').length!=0){
		//Avoid duplicate eppend issue
		return;
	}
	var msStep = workFlowContext.milestoneStepsLookup["MANAGE_APP_STATUS"];	
	var stepElement  = getCustomerMilestoneLoanProgressHeaderBarStep(msStep.status, 1, "Application");
	container.append(stepElement);	
	msStep = workFlowContext.milestoneStepsLookup["LOCK_YOUR_RATE"];
	stepElement  = getCustomerMilestoneLoanProgressHeaderBarStep(msStep.status, 2, "Rate Locked");
	container.append(stepElement);	
	
	var docsOutLMStatus = workFlowContext.mileStoneStatusLookup["DOCS_OUT"];
	var docsOutLMStatusRep = "0";
	if (docsOutLMStatus && docsOutLMStatus == "5")
	{
		docsOutLMStatusRep = COMPLETED;
	}
	stepElement  = getCustomerMilestoneLoanProgressHeaderBarStep(docsOutLMStatusRep, 3, "Docs Ready");
	container.append(stepElement);	

	var loanApprovedLMStatus = workFlowContext.mileStoneStatusLookup["LOAN_APPROVED"];
	var loanApprovedLMStatusRep = "0";
	if (loanApprovedLMStatus && loanApprovedLMStatus == "4")
	{
		loanApprovedLMStatusRep = COMPLETED;
	}
	stepElement  = getCustomerMilestoneLoanProgressHeaderBarStep(loanApprovedLMStatusRep, 4, "Loan Approved");
	container.append(stepElement);	
	msStep = workFlowContext.milestoneStepsLookup["VIEW_CLOSING"];
	stepElement  = getCustomerMilestoneLoanProgressHeaderBarStep(msStep.status, 5, "Funded");
	container.append(stepElement);	
	
	return container;
}

function getCustomerMilestoneLoanProgressHeaderBarStep(status, step, heading) {
	var col = $('<div>').attr({
		"class" : "milestone-header-bar-step float-left"
	});

	if (status == COMPLETED) {
		col.addClass('m-step-complete');
	} else if (status == "2" || status == "1") {
		col.addClass('m-step-in-progress');
	} else if (status == "0") {
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

	
	var loanProgressCont = $('<div>').attr({
		"id" : "loan-progress-milestone-wrapper",
		"class" : "loan-progress-milestone-wrapper"
	});

	$('#cust-loan-progress').append(loanProgressCont);

	
	
	//paintMilestoneCustomerProfileDetails(workFlowContext.customer);
	
	
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
function getAppFeeEdit(workItem)
{
	if(workItem.status == COMPLETED)
	{
		return;
	}
	var clas="milestone-lc-text";
	var floatCls="float-right";
	var rightClassCheck=workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.hasClass("milestone-rc-text");
	if(rightClassCheck){
		clas="milestone-rc-text";
		floatCls="float-left";
	}
	var appFeeEditItem = $('<div>').attr({
		"id" : "SM_FEE_EDIT",
		"class" : clas,
		"data-text" : workItem.workflowItemType,
		"mileNotificationId":workItem.id
	});
	var spanContainer=$('<span>').attr({
		"class" : "showAnchor",
		"data-text" : workItem.workflowItemType,
		"mileNotificationId":workItem.id
	}).html("Click here to change fee").bind("click", function(e) {
		milestoneChildEventHandler(e);
	});
	appFeeEditItem.append(spanContainer);
	return appFeeEditItem;
}
function getMilestoneTeamMembeTable(input,workItem) {
	var clas="milestone-lc-text";
	var floatCls="float-right";
	var rightClassCheck=workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.hasClass("milestone-rc-text");
	if(rightClassCheck){
		clas="milestone-rc-text";
		floatCls="float-left";
	}
	var tableContainer = $('<div>').attr({
		"class" : "ms-team-member-table clearfix"
	});

	userList=input.users;
	if(!userList ||  userList.length==0)
		return;
	
	var addNewMember = $('<span>').attr({
		"class" : clas+" showAnchor",
		"data-text" : workItem.workflowItemType,
		"mileNotificationId":workItem.id
	}).html("Click here to add a Team Member").bind("click", function(e) {
		milestoneChildEventHandler(e)
	});

	
	tableContainer.append(addNewMember);
	
	//team table header
	var th = getMilestoneTeamMembeTableHeader(floatCls);
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
		tableContainer.append(getMilestoneTeamMembeTableRow(user,floatCls));
	}
	var homwOwnInsurance=input.homeOwnInsCompany;
	var titleCompany=input.titleCompany;
	if(homwOwnInsurance!=null){
		homwOwnInsurance.homeOwnInsID=homwOwnInsurance.id;
		homwOwnInsurance.firstName=homwOwnInsurance.name;
		homwOwnInsurance.emailId=homwOwnInsurance.emailID;
		homwOwnInsurance.userRole={
				label : "Insurance Agent"
		};
		var tableRow = getMilestoneTeamMembeTableRow(homwOwnInsurance,floatCls);
		tableContainer.append(tableRow);
	}
	
	if(titleCompany!=null){
		titleCompany.titleCompanyID=titleCompany.id;
		titleCompany.firstName=titleCompany.name;
		titleCompany.emailId=titleCompany.emailID;
		titleCompany.userRole={
				label : "Title Company"
		};
		var tableRow = getMilestoneTeamMembeTableRow(titleCompany,floatCls);
		tableContainer.append(tableRow);
	}
	return tableContainer;
}

function getMilestoneTeamMembeTableRow(user,floatCls){	
	if(!floatCls)
	{
		var teamWorkItem = workFlowContext.milestoneStepsLookup["TEAM_STATUS"];
		if(!teamWorkItem)
		{
			teamWorkItem = workFlowContext.milestoneStepsLookup["MANAGE_TEAM"]
		}
		floatCls = getRightLeftReference(teamWorkItem.id);
				
	}
	console.log(floatCls);
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
	var custFlag=false;
	if(user.userRole.roleCd=="CUSTOMER")
		custFlag=true;
	return getMilestoneTeamMemberRow(dispName, roleLabel,user.id,floatCls,custFlag,user);
}

function getRightLeftReference (workItemId)
{		
	var rightLeftIdenfier = RENDER_RIGHT;
	if (workFlowContext.mileStoneContextList[workItemId].workItem.rightLeftReference)
	{
		rightLeftIdenfier = workFlowContext.mileStoneContextList[workItemId].workItem.rightLeftReference;
	}
	return rightLeftIdenfier;	
}
// Function to get milestone team member table header
function getMilestoneTeamMembeTableHeader(floatCls) {
	
	var row = $('<div>').attr({
		"class" : "ms-team-member-th clearfix "+floatCls
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
function getMilestoneTeamMemberRow(name, title,userID,floatCls,custFlag,userDetails) {
	var row = $('<div>').attr({
		"class" : "ms-team-member-tr clearfix "+floatCls,
		"userID":userID
	});

	var nameCol = $('<div>').attr({
		"class" : "ms-team-member-tr-col1 float-left"
	}).html(name);

	var titleCol = $('<div>').attr({
		"class" : "ms-team-member-tr-col2 float-left"
	}).html(title);
	var userid=undefined;
	var homeOwnInsID=undefined;
	var titleCompanyID=undefined;
	if(userDetails.userRole.label=="Insurance Agent"){
		homeOwnInsID=userDetails.id;
	}else if(userDetails.userRole.label=="Title Company"){
		titleCompanyID=userDetails.id;
	}else{
		userid=userDetails.id;
	}
	var delCol = $('<span>').attr({
		"class" : "ms-team-member-tr-col3 del-icon float-left",
		"userid":userid,
		"homeOwnInsID":homeOwnInsID,
		"titleCompanyID":titleCompanyID
	}).bind("click",function(e){
		e.stopImmediatePropagation();
		var userID = $(this).attr("userid");
		var loanID = selectedUserDetail==undefined?newfiObject.user.defaultLoanId:selectedUserDetail.loanID
		var homeOwnInsID = $(this).attr("homeOwnInsID");
		var titleCompanyID = $(this).attr("titleCompanyID");
		if (userID == undefined)
			userID = 0;
		if (homeOwnInsID == undefined)
			homeOwnInsID = 0;
		if (titleCompanyID == undefined)
			titleCompanyID = 0;

		var input = {
			"userID" : userID,
			"homeOwnInsID" : homeOwnInsID,
			"titleCompanyID" : titleCompanyID
		};
		teamItm=$(this).parent();
		confirmRemoveUser(messageToDeleteUser, input,
				loanID,removeTeamItem);
	});
	row.append(nameCol).append(titleCol);
	var showDelBtnFlag=checkDeleteBtnApplicable(userDetails);
	if(!custFlag&&showDelBtnFlag)
		row.append(delCol);
		
	return row;
}
var teamItm;
function removeTeamItem(){
	$(teamItm).remove();
	teamItm=undefined;
}
$(document).on('click', '.creditScoreClickableClass', function(e) {
	e.stopImmediatePropagation();
	if(newfiObject.user.userRole.roleCd=="INTERNAL"){
		var loanid=$(this).attr("loanid");
		var textMessage="Are you sure you want to fetch Credit Score ?"
		var trimerge=true;
	     if($(e.target).parent()[0].innerHTML.indexOf("EQ-?")>=0&&
			   $(e.target).parent()[0].innerHTML.indexOf("TU-?")>=0&&
			   $(e.target).parent()[0].innerHTML.indexOf("EX-?")>=0){
			   		textMessage="Are you sure you want to fetch Transunion Score ?"
			   			trimerge=false;
		}	     		
		confirmFetchScore(textMessage, loanid,trimerge)
	}
});
function checkDeleteBtnApplicable(userDetails){
	try{
		if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
			if(userDetails.userRole.roleCd=="INTERNAL")
				return false;
			else
				return true;
		}else if(newfiObject.user.userRole.roleCd=="INTERNAL"){
			return true;
		}
	}catch(e){

	}
	return false;
}
function confirmFetchScore(textMessage, loanID,trimerge,callback) {
	$('#overlay-confirm').off();
	$('#overlay-cancel').off();
	$('#overlay-popup-txt').html(textMessage);
	$('#overlay-confirm').on('click', function() {
		if(loanID){
			fetchCreditScore(loanID,trimerge,callback);
			$('#overlay-popup').hide();
			$('#overlay-confirm').on('click', function() {});			
		}
	});

	$('#overlay-cancel').on('click', function() {
		$('#overlay-popup').hide();
		$('#overlay-confirm').on('click', function() {});
	});

	$('#overlay-popup').show();
}

function fetchCreditScore(loanid,trimerge, callback){
	var data={}
	var pullScoreURL = "rest/application/pullScore/"+loanid+"/N";
	if (trimerge && trimerge == true)
	{
		 pullScoreURL = "rest/application/pullScore/"+loanid+ "/Y";	
	}
	ajaxRequest(pullScoreURL,"GET","json",data,function(response){
		if(response.error){
			showToastMessage(response.error.message);
		}else{
			var result=response.resultObject;
			showToastMessage(result);
			if(callback){
				callback(response.resultObject,targetElement);
			}
		}
		
	});
}

function adjustBorderMilestoneContainer() {
	$('.milestone-lc:first-child').find('.milestone-lc-border').css({
		"top" : 0,
		"height" : $(this).height() + 32
	});
}



function paintMilestoneCustomerProfileDetails(userObj) {

	var loanType=JSON.parse(newfi.appUserDetails).loanType.description;
	if(loanType === "Purchase")
		loanType = "Home Buyer";
	
	var container = $('<div>').attr({
		"class" : "ms-cust-prof-container clearfix"
	});

	var custImg = $('<div>').attr({
		
	});
	if(userObj.photoImageUrl){
		custImg.addClass("ms-cust-prof-img float-left");
		custImg.attr("style" , "background-image : url("+userObj.photoImageUrl+"); background-size: cover;");
	}
	else
	{
		custImg.addClass("ms-cust-prof-img-default float-left");
		custImg.text(getInitials(userObj.firstName,userObj.lastName));
	}
	var custTxtContainer = $('<div>').attr({
		"class" : "ms-cust-prof-txt-cont float-left"
	});

	var name = $('<div>').attr({
		"class" : "ms-cust-prof-txt-name"
	}).html(userObj.firstName+" "+userObj.lastName);

	var role = $('<div>').attr({
		"class" : "ms-cust-prof-txt-role"
	}).html(loanType);

	var contact = $('<div>').attr({
		"class" : "ms-cust-prof-txt-contact"
	}).html(userObj.phoneNumber);

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
	workFlowContext.init(selectedUserDetail.loanID,createNewfiUser());
	workFlowContext.initialize("AGENT", function() {
	});
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
		var url="rest/workflow/execute/"+wf.id;
		var data={};
		data.workflowItemstatus=COMPLETED;//since we will send only completed status from frontend
		if (workflowItem.workflowItemType == "1003_COMPLETE")
		{
			data.workflowItemstatus="28"; // Tihs LOS Status for 1003 Complete
		}
		data["workflowItemExecId"]=wf.id;
		data["loanID"]=workFlowContext.loanId;
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
function getParentStatusClass(status){
	switch(status){
		case "0":
			return "m-not-started";
		case "1":
			return "m-in-progress";
		case "2":
			return "m-in-progress";
		case "3":
			return "m-complete";
	}
}
function getContainerLftRghtClass(container){
	if(container.hasClass("milestone-lc"))
		return "milestone-lc";
	else if(container.hasClass("milestone-rc"))
		return "milestone-rc";
}


function changeContainerClassBasedOnStatus(container,status){
	clearStatusClass(container);
	container.addClass(getParentStatusClass(status));
}
function addClicableClassToElement(element,workflowItem){
	var type=workflowItem.workflowItemType;
	switch(type){
		case "NEEDS_STATUS":
			$(element).addClass("cursor-pointer");
			break;
		case "VIEW_NEEDS":
			$(element).addClass("cursor-pointer");
			break;
		case "QC_STATUS":
			$(element).addClass("cursor-pointer");
			break;
		case "LOAN_MANAGER_DECISION":
			$(element).addClass("cursor-pointer");
			break;
		case "CONNECT_ONLINE_APP":
			$(element).addClass("cursor-pointer");
			break;
		case "CONTACT_YOUR_LA":
			$(element).addClass("cursor-pointer");
			break;
		case "MANAGE_ACCOUNT":
			$(element).addClass("cursor-pointer");
			break;
		case "SMS_TEXTING_PREF":
			$(element).addClass("cursor-pointer");
			break;
		case "MANAGE_PHOTO":
			$(element).addClass("cursor-pointer");
			break;
			
	}
}
function appendMilestoneItem(workflowItem, childList) {

	countOfTasks++;
	var rightLeftReference = RENDER_RIGHT;
	if (countOfTasks % 2 == 1) {
		rightLeftReference = RENDER_LEFT;
	}
	var floatClass = "float-right";
	if(workflowItem.status!=3||workflowItem.workflowItemType=="NEEDS_STATUS"||workflowItem.workflowItemType=="VIEW_NEEDS"||workflowItem.workflowItemType=="MANAGE_APP_STATUS")//force status checking for needed list
		workFlowContext.itemsStatesToBeFetched.push(workflowItem.id);
	var progressClass = getProgressStatusClass(workflowItem.status);
	var rightLeftClass = "milestone-lc";
	if (rightLeftReference == RENDER_LEFT) {
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
		$(headerCheckBox).addClass("cursor-pointer");
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
		})
	}else{
		$(headerCheckBox).addClass("cursor-auto");
	}
	headerTxt.append(headerCheckBox);
	header.append(headerTxt).append(headerIcn);
	wrapper.append(rightBorder).append(header);
	var itemToAppendTo=workFlowContext.getItemToAppendTo(wrapper,header,workflowItem);
	var WFContxt=appendInfoAction(rightLeftClass, itemToAppendTo, workflowItem);
	workFlowContext.mileStoneContextList[workflowItem.id]=WFContxt;
	if (childList != null) {
		// It has children 
		// to the workItem put the rightLeft Attribute
		workflowItem.rightLeftReference=floatClass;
		for (index = 0; index < childList.length; index++) {			
			var childRow = $('<div>').attr({
				"class" : rightLeftClass + "-text"+" clearfix",
				"mileNotificationId" : childList[index].id,
				"data-text" : childList[index].workflowItemType,
				"id":"WF"+childList[index].id
			});
			var childSpan = $('<span>').attr({
				"mileNotificationId" : childList[index].id,
				"data-text" : childList[index].workflowItemType
			}).html(childList[index].displayContent);
			childRow.append(childSpan);
			if(childList[index].workflowItemType == "QC_STATUS"  && newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleDescription == LOAN_MANAGER)
			{
				continue;
			}
			childRow.attr("WFchild",true);
			childSpan.attr("WFchild",true);
			childSpan.bind("click", function(e) {
				milestoneChildEventHandler(e)
			});
			addClicableClassToElement(childSpan,childList[index])
			if(childList[index].status!=COMPLETED)
				workFlowContext.itemsStatesToBeFetched.push(childList[index].id);

			var itemCheckBox = $('<div>').attr({
				"class" : "ms-check-box box-border-box " + floatClass,
				"data-checked" : getStatusClass(childList[index])
			})
			if(childList[index].clickable){
				$(itemCheckBox).addClass("cursor-pointer");
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
				})
			}else{
				$(itemCheckBox).addClass("cursor-auto");
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
	var mileStoneStepContext = getInternalEmployeeMileStoneContext(workflowItem);
	
	mileStoneStepContext.getStateInfo(rightLeftClass,itemToAppendTo);
	
	return mileStoneStepContext;
}
function milestoneChildEventHandler(event) {
	// condition need to be finalized for identifying each element
	if($(event.target).hasClass("creditScoreClickableClass"))
		return;
	removeAllPopups();
	if ($(event.target).attr("data-text") == "INITIAL_CONTACT") {
		event.stopPropagation();
		var data = {};
		data.milestoneId = event.target.getAttribute("milenotificationid");
		data.OTHURL = "rest/workflow/invokeaction/"+data.milestoneId;
		addNotificationPopup(selectedUserDetail.loanID, event.target, data);
	} else if  ($(event.target).attr("data-text") == "TEAM_STATUS")
		{
		event.stopPropagation();
		var data = {};
		data.milestoneID=$(event.target).attr("mileNotificationId");
		data.OTHURL = "rest/workflow/invokeaction/"+data.milestoneID;
		data.loanID = selectedUserDetail.loanID;
		appendMilestoneAddTeamMemberPopup(selectedUserDetail.loanID,
				event.target, data);
		var context=getCreateTitleCompanyContext(
				workFlowContext.loanId);
		context.createTitleCompanyPopup();

				
		context = getCreateHomeOwnInsCompanyContext(workFlowContext.loanId)
		context.createCompanyPopup();
	}
	else if  ($(event.target).attr("data-text") == "MANAGE_TEAM") {
		event.stopPropagation();
		var data = {};
		data.milestoneID=$(event.target).attr("mileNotificationId");
		data.OTHURL = "rest/workflow/invokeaction/"+data.milestoneID;
		data.loanID = newfi.user.defaultLoanId;
		appendMilestoneAddTeamMemberPopup(newfi.user.defaultLoanId,
				event.target, data);
		var context=getCreateTitleCompanyContext(
				workFlowContext.loanId);
		context.createTitleCompanyPopup();
		context = getCreateHomeOwnInsCompanyContext(workFlowContext.loanId)
		context.createTitleCompanyPopup();
	}
	 else if ($(event.target).attr("data-text") == "NEEDS_STATUS"||$(event.target).attr("data-text") == "VIEW_NEEDS") {
	 	event.stopPropagation();
		 $("#lp-step4").click();
	}
	 else if ($(event.target).attr("data-text") == "CONNECT_ONLINE_APP") {
	 	event.stopPropagation();
		 $("#lp-step2").click();
	}else if ($(event.target).attr("data-text") == "LOCK_RATE") {
	 	event.stopPropagation();
		 window.location.hash="#loan/"+workFlowContext.loanId+"/lock-rate"
	}else if ($(event.target).attr("data-text") == "LOCK_YOUR_RATE") {
	 	event.stopPropagation();
	 	if(userIsRealtor()){
	 		window.location.hash="#loan/"+workFlowContext.loanId+"/lock-rate"
	 	}else{
	 		window.location.hash="#myLoan/lock-my-rate"	
	 	}		
	}else if ($(event.target).attr("data-text") == "LOAN_MANAGER_DECISION") {
	 	event.stopPropagation();
	 	if(workFlowContext.mileStoneContextList[$(event.target).attr("mileNotificationId")].workItem.status!=COMPLETED)
			appendLoanStatusPopup($(event.target),$(event.target).attr("mileNotificationId"));
	}
	else if ($(event.target).attr("data-text") == "CONTACT_YOUR_LA") {
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
	else if ($(event.target).attr("data-text") == "QC_STATUS") {
	 	event.stopPropagation();
	 	if(workFlowContext.mileStoneContextList[$(event.target).attr("mileNotificationId")].workItem.status!=COMPLETED)
			appendQCPopup($(event.target),$(event.target).attr("mileNotificationId"));
	}
	else if ($(event.target).attr("data-text") == "APP_FEE" ) {
	 	event.stopPropagation();
	 	if(workFlowContext.mileStoneContextList[$(event.target).attr("mileNotificationId")].workItem.status!=COMPLETED)
	 	{
			appendAppFeeEditPopup($(event.target),$(event.target).attr("mileNotificationId"));
	 	}
	}
	else if ($(event.target).attr("data-text") == "MANAGE_PHOTO" || $(event.target).attr("data-text") == "MANAGE_ACCOUNT" ||
			$(event.target).attr("data-text") == "SMS_TEXTING_PREF") {
	 	event.stopPropagation();
	 	if(!userIsRealtor()){
	 		showCustomerProfilePage();	
	 	}
	 	
	}
	else if ($(event.target).attr("data-text") == "MANAGE_APP_FEE" || $(event.target).attr("data-text") == "APP_FEE_PAY_FOR" ) {
		var workItemIDAppFee = $(event.target).attr("mileNotificationId");
		if (workFlowContext.mileStoneContextList[workItemIDAppFee].workItem.status != NOT_STARTED )
		{
			return;
		}
	 	event.stopPropagation();
	 	
		console.log("Pay application fee clicked!");
		showOverlay();
		$('body').addClass('body-no-scroll');
		url = "/NewfiWeb/payment/initialisepayment.do";
		payload = "loan_id=" + String(workFlowContext.loanId);
		
		 $.ajax({
		        url : url,
		        type : "POST",
		        data : payload,
		        cache:false,
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



$(document).resize(function(){
	removeLoanManagerPopup();
	removeLoanStatusPopup();
	removeAppFeeEditPopup();
	removeNotificationPopup();
});

$(document).on('click',function(){
	removeAllPopups();
});

function removeAllPopups(){
	removeLoanManagerPopup();
	removeLoanStatusPopup();
	removeAppFeeEditPopup();
	removeQCPopup();
	removeNotificationPopup();
}

$(document).on('click','#loan-manager-popup, #loan-status-popup',function(e){
	e.stopPropagation();
});

function appendLoanStatusPopup(element,milestoneId) {
	
	var offset = $(element).offset();
	
	var wrapper = $('<div>').attr({
		"id" : "loan-status-popup",
		"class" : "ms-add-member-popup loan-status-popup ms-add-member-popup-adjustment"
	}).css({
		"left" : offset.left,
		"top" : offset.top+42
	});
	
	var header = $('<div>').attr({
		"class" : "popup-header"
	}).html("Loan Status");
	
	var container = $('<div>').attr({
		"class" : "popup-container"
	});
	
	var radioButtonRow = $('<div>').attr({
		"class" : "popup-row clearfix"
	});
	
	var option1 = $('<div>').attr({
		"class" : "option-radio clearfix float-left" 
	});
	
	var option1Btn = $('<div>').attr({
		"class" : "radio-btn float-left" ,
		"value" : "Pass"
	}).bind('click',function(){
		$('#loan-status-popup .radio-btn').removeClass('radio-btn-selected');
		$(this).addClass('radio-btn-selected');
	});
	
	var option1Txt = $('<div>').attr({
		"class" : "radio-btn-label float-left" 
	}).html("Pass"); 
	option1.append(option1Btn).append(option1Txt);
	
	var option2 = $('<div>').attr({
		"class" : "option-radio clearfix float-left" 
	});
	
	var option2Btn = $('<div>').attr({
		"class" : "radio-btn float-left" ,
		"value" : "Decline"
	}).bind('click',function(){
		$('#loan-status-popup .radio-btn').removeClass('radio-btn-selected');
		$(this).addClass('radio-btn-selected');
	});
	
	var option2Txt = $('<div>').attr({
		"class" : "radio-btn-label float-left" 
	}).html("Decline"); 
	option2.append(option2Btn).append(option2Txt);
	
	radioButtonRow.append(option1).append(option2);
	
	var note = $('<textarea>').attr({
		"class" : "popup-textbox",
		"placeholder" : "Add a comment"
	}).keypress(function(event){
	    if (event.keyCode == 13) {
	    	 event.preventDefault();
	 	     return false;
	    }
	       
	  });
	
	var submitBtn = $('<div>').attr({
		"class" : "popup-save-btn float-left"
	}).html("Save").bind('click',{"container":wrapper,"comment":note,"milestoneId":milestoneId},function(event){
		var comment=event.data.comment.val();
		var value=event.data.container.find(".radio-btn-selected").attr("value");
		var milestoneId=event.data.milestoneId;
		if(value){
			var url="rest/workflow/invokeaction/"+milestoneId;
			var data={};
			data["EMAIL_RECIPIENT"]=newfiObject.user.emailId;
			data["EMAIL_TEMPLATE_NAME"]="90d97262-7213-4a3a-86c6-8402a1375416";
			data["EMAIL_RECIPIENT_NAME"]=newfiObject.user.displayName;
			data["userID"]=newfiObject.user.id;
			data["loanID"]=workFlowContext.loanId;
			data["workflowItemExecId"]=milestoneId;
			data["workflowItemExecId"]=milestoneId;
			data["decision"]=value;
			data["comment"]=comment;
	 		ajaxRequest(
				url,
				"POST",
				"json",
				JSON.stringify(data),
				function(response) {
					if (response.error) {
						showErrorToastMessage(response.error.message)
					}else{
						var contxt=workFlowContext.mileStoneContextList[milestoneId];
						contxt.updateMilestoneView(COMPLETED);
						
						removeLoanStatusPopup();
					}
			},false);
		}else{
			showErrorToastMessage(selectAnOption);
		}
	});
	
	var cancelBtn = $('<div>').attr({
		"class" : "popup-save-btn float-right"
	}).html("Cancel").bind('click',function(event){
		event.stopPropagation();
		removeLoanStatusPopup();
	});
	var btnContainer = $('<div>').attr({
        "class": "milestone-qc-btn-container"
    });
    btnContainer.append(submitBtn).append(cancelBtn);

	container.append(radioButtonRow).append(note).append(btnContainer);
	
	wrapper.append(header).append(container);
	
	$('body').append(wrapper);
}

function removeLoanStatusPopup() {
	$('#loan-status-popup').remove();
}

function appendQCPopup(element,milestoneId) {
	
	var offset = $(element).offset();
	
	var wrapper = $('<div>').attr({
		"id" : "qc-popup",
		"class" : "ms-add-member-popup loan-status-popup ms-add-member-popup-adjustment"
	}).css({
		"left" : offset.left,
		"top" : offset.top+42
	});
	
	var header = $('<div>').attr({
		"class" : "popup-header"
	}).html("QC Status");
	
	var container = $('<div>').attr({
		"class" : "popup-container"
	});
	
	
	
	var note = $('<textarea>').attr({
		"class" : "popup-textbox",
		"placeholder" : "Add a comment"
	});
	
	var submitBtn = $('<div>').attr({
		"class" : "popup-save-btn float-left"
	}).html("Save").bind('click',{"container":wrapper,"comment":note,"milestoneId":milestoneId},function(event){
		event.stopPropagation();
		var comment=event.data.comment.val();
		var milestoneId=event.data.milestoneId;
		if(comment){
			var url="rest/workflow/invokeaction/"+milestoneId;
			var data={};
			data["EMAIL_RECIPIENT"]=newfiObject.user.emailId;
			data["EMAIL_TEMPLATE_NAME"]="90d97262-7213-4a3a-86c6-8402a1375416";
			data["EMAIL_RECIPIENT_NAME"]=newfiObject.user.displayName;
			data["userID"]=newfiObject.user.id;
			data["loanID"]=workFlowContext.loanId;
			data["workflowItemExecId"]=milestoneId;
			data["comment"]=comment;
			data["userID"]=newfiObject.user.id;
	 		ajaxRequest(
				url,
				"POST",
				"json",
				JSON.stringify(data),
				function(response) {
					if (response.error) {
						showErrorToastMessage(response.error.message)
					}else{
						var contxt=workFlowContext.mileStoneContextList[milestoneId]
						contxt.updateMilestoneView("3")
						removeQCPopup();
					}
			},false);
		}else{
			showErrorToastMessage(selectAnOption);
		}
	});
	

	var cancelBtn = $('<div>').attr({
		"class" : "popup-save-btn float-right"
	}).html("Cancel").bind('click',function(event){
		event.stopPropagation();
		removeQCPopup();
	});
	var btnContainer = $('<div>').attr({
        "class": "milestone-qc-btn-container"
    });
    btnContainer.append(submitBtn).append(cancelBtn);
	container.append(note).append(btnContainer);
	
	wrapper.append(header).append(container);
	wrapper.bind("click",function(e){
		e.stopPropagation();
	})
	$('body').append(wrapper);
}

function removeQCPopup() {
	$('#qc-popup').remove();
}
function appendAppFeeEditPopup(element,milestoneId) {
	removeAppFeeEditPopup();
	var offset = $(element).offset();
	
	var wrapper = $('<div>').attr({
		"id" : "appfee-edit-popup",
		"class" : "ms-add-member-popup loan-status-popup ms-add-member-popup-adjustment"
	}).css({
		"left" : offset.left,
		"top" : offset.top+42
	});
	
	var header = $('<div>').attr({
		"class" : "popup-header"
	}).html("App Fee Edit");
	
	var container = $('<div>').attr({
		"class" : "popup-container"
	});
	var newFee = $('<textarea>').attr({
		"class" : "popup-textbox",
		"placeholder" : "Change Fee here"
		
	}).keydown(function() {
    	$(this).maskMoney({
			thousands:',',
			decimal:'.',
			allowZero:true,
			prefix: '$',
		    precision:0,
		    allowNegative:false
		});		
    }).keypress(function(event) {

        if (event.keyCode == 13) {
            event.preventDefault();
        }
    });
	
	var submitBtn = $('<div>').attr({
		"class" : "popup-save-btn float-left"
	}).html("Save").bind('click',{"container":wrapper,"comment":newFee,"milestoneId":milestoneId},function(event){
		
		var newFee=event.data.comment.val();
		newFee=newFee.replace('$', '');
		var milestoneId=event.data.milestoneId;
		if(newFee){
			var url="rest/workflow/invokeaction/"+milestoneId;
			var data={};			
			data["loanID"]=workFlowContext.loanId;
			data["userID"] = newfiObject.user.id;
			data["workflowItemExecId"]=milestoneId;
			data["newFee"]=newFee;
	 		ajaxRequest(
				url,
				"POST",
				"json",
				JSON.stringify(data),
				function(response) {
					if (response.error) {
						showErrorToastMessage(response.error.message);
					}else{
						var contxt=workFlowContext.mileStoneContextList[milestoneId];							
						$("#WF"+milestoneId).find("#"+milestoneId+"fee").html("$"+newFee);						
						removeAppFeeEditPopup();
					}
			},false);
		}else{
			showErrorToastMessage(addValue);
		}
	});
	var cancelBtn = $('<div>').attr({
		"class" : "popup-save-btn float-right"
	}).html("Cancel").bind('click',function(event){
		event.stopPropagation();
		removeAppFeeEditPopup();
	});
	var btnContainer = $('<div>').attr({
        "class": "milestone-qc-btn-container"
    });
    btnContainer.append(submitBtn).append(cancelBtn);
	
	container.append(newFee).append(btnContainer);
	
	wrapper.append(header).append(container);
	wrapper.bind("click",function(e){
		e.stopPropagation();
	})
	$('body').append(wrapper);
}

function removeAppFeeEditPopup() {
	$('#appfee-edit-popup').remove();
}
function showLQBInfo (itemToAppendTo,workItem)
{
	rightLeftClass = getContainerLftRghtClass($("#WF"+workItem.id));
	if(workItem.stateInfo){
		var tempOb=JSON.parse(workItem.stateInfo);		
		if(tempOb.status){
			workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.html(tempOb.lqbKey);
			workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.attr("onclick","window.open('"+tempOb.url+"','_blank')");
			workFlowContext.mileStoneContextList[workItem.id].stateInfoContainer.addClass("cursor-pointer‏");
		}		
	}
}

function generateDownloadURL (uuID)
{
	var fileURL = newfiObject.baseUrl;
	fileURL = fileURL+ "readFileAsStream.do?uuid=";
	fileURL = fileURL+uuID;
	fileURL = fileURL + "&isThumb=0";
	return fileURL;
}
//Functions to view loan manager details in customer page


function appendLoanManagerPopup(element,loanManagerArray){
	
	removeLoanManagerPopup();
	if (loanManagerArray == undefined || loanManagerArray.length == 0)
	{
		showErrorToastMessage(noLoanAdvisorAdded);
		return;
	}
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
			"class" : "float-left"
		});
		
		if(loanManagerObj.photoImageUrl != undefined && loanManagerObj.photoImageUrl != ""){
			profilePic.attr("style","background-image:url('"+loanManagerObj.photoImageUrl+"')");
			profilePic.addClass("lp-pic");
		}
		else
		{
			profilePic.addClass("lp-initial-pic-large");
			var initialsText = getInitialsFromFullName(loanManagerObj.displayName);
			profilePic.text(initialsText);	
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

function mapNotificationToMilestone(notificationType){
	switch(notificationType){
		case "CREDIT_SCORE_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "MANAGE_CREDIT_STATUS";
			else
				return "CREDIT_SCORE";
		case "SYS_EDU_NOTIFICATION":
			return "SYSTEM_EDU";
		case "DISCLOSURE_AVAIL_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "DISCLOSURE_DISPLAY";
			else
				return "DISCLOSURE_STATUS";
		case "NEEDED_ITEMS_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "VIEW_NEEDS";
			else
				return "NEEDS_STATUS";
		case "APP_FEE_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "MANAGE_APP_FEE";
			else
				return "APP_FEE";
		case "APP_FEE_NOTIFICATION_OVERDUE":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "MANAGE_APP_FEE";
			else
				return "APP_FEE";
		case "LOCK_RATE_CUST_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "LOCK_YOUR_RATE";
			else
				return "LOCK_RATE";
		case "LOCK_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "LOCK_YOUR_RATE";
			else
				return "LOCK_RATE";
		case "APPRAISAL_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "VIEW_APPRAISAL";
			else
				return "APPRAISAL_STATUS";
		case "UW_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "VIEW_UW";
			else
				return "UW_STATUS";
		case "TEAM_ADD_NOTIFICATION":
			if(newfiObject.user.userRole.roleCd=="CUSTOMER")
				return "MANAGE_TEAM";
			else
				return "TEAM_STATUS";
		
	}
}
