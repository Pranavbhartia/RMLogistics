var updateHandler={
	taskKeyDictionary:{},
	taskKey:"",
	pushServerUrl : "/PushNotification/pushServlet/?taskId=",
	enablePushnotification:false,
	requestEnabled:false,
	clearUpdateHandler:function(){
		this.taskKey="";
		this.requestEnabled=false;
		taskKeyDictionary={}
	},
	addLoanToTaskKey:function(loanId,callback){
		var ob=this;
		if(!ob.taskKeyDictionary[loanId]){
			if(ob.taskKey=="")
				ob.taskKey=loanId+"";
			else
				ob.taskKey=ob.taskKey+","+loanId;
			ob.taskKeyDictionary[loanId]=true;
		}
	},
	initiateRequest:function(){
		if(!this.requestEnabled){
			this.getUpdate();
			this.requestEnabled=true;
		}
	},
	getUpdate : function(callback) {
		var ob = this;
		if (ob.taskKey != "" && ob.enablePushnotification) {
			var data = {};
			// code to call API to get Notification List for loan
			// http://localhost:8080/PushNotification/pushServlet/?task=notification&taskId=1
			ajaxRequest(
				ob.pushServerUrl + ob.taskKey,
				"GET",
				"json",
				data,
				function(response) {

				},
				false,
				undefined,
				function(response) {
					ob.getUpdate();
					if (response.error) {
						showToastMessage(response.error.message);
					} else {
						if(response.task=="notification"){
							ob.updateNotification(response);
						}else if(response.task=="milestone"){
							ob.updateMilestone(response);
						}
					}
				}
			);
		}
	},
	updateNotification:function(response){
		var ob=this.getNotificationContext(response);
		if (response.action == "new") {
			var nwData = response.data;
			var exist = false;
			// Removed Code
			// if(nwData.createdForID==newfiObject.user.id||(!nwData.createdForID||nwData.createdForID==0))
			if (checkNotificationApplicable(nwData)) {
				exist = ob.addToList(
								ob.loanNotificationList,
								nwData);
				if (!exist) {
					ob.updateWrapper();
					ob.updateLoanListNotificationCount();
					updateDefaultContext(nwData);
				}
			}
		} else if (response.action == "remove") {
			var notificationID = response.notificationID;
			var id = response.id;
			// ob.removeNotificationfromList(id);
			var existAry = $("#" + notificationID);
			if (existAry.length > 0) {
				$("#" + notificationID).remove();
			}
			existAry = $("#" + "LNID" + id);
			if (existAry.length > 0) {
				$("#" + "LNID" + id).remove();
			}
			existAry = $("#" + "UNID" + id);
			if (existAry.length > 0) {
				$("#" + "UNID" + id).remove();
			}
			removeNotificationFromAllContext(id);
			ob.updateWrapper();
		} else if (response.action == "snooze") {
			var notificationID = response.notificationID;
			var id = response.id;
			ob.removeNotificationfromList(id);
			var existAry = $("#" + notificationID);
			if (existAry.length > 0) {
				$("#" + notificationID).remove();
			}
			ob.updateLoanListNotificationCount();
		}
	},
	getNotificationContext:function(response){
		var ctx;
		if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
			ctx=newfiObject.contextHolder.notification;
		}else{
			var loanId;
			if (response.action == "new") {
				loanId=response.data.loanID;
			} else if (response.action == "remove") {
				loanId=response.loanID;	
			} else if (response.action == "snooze") {
				loanId=response.loanID;
			}
			if(loanId)
				ctx=newfiObject.contextHolder[loanId+"-notification"]
			if(!ctx)
				ctx=newfiObject.contextHolder.notification;
		}
		return ctx;
	},
	updateMilestone:function(response){
		var status=response.status
		var wfID=response.wfID;
		var WFContxt=workFlowContext.mileStoneContextList[wfID];
		if(WFContxt){
			WFContxt.updateMilestoneView(status+"");
		}
	}
}