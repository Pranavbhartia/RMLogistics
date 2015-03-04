function getNotificationContext(loanId,userId){

	var notificationContext={
		loanId:loanId,
		userId:userId,
		userContainerId:"",
		loanContainerId:"notificationParentContainer",
		userNotificationList:[],
		loanNotificationList:[],
		getNotificationForUser:function(callback){
			var ob=this;
			var data={};
			data.userID=ob.userId;
			data.loanID=0;
			//code to call API to get Notification List for user
			ajaxRequest("rest/notification","GET","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					var notificationList=response.resultObject;
					ob.userNotificationList=notificationList;
					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		getNotificationForLoan:function(callback){
			var ob=this;
			var data={};
			data.userID=0;
			data.loanID=ob.loanId;
			//code to call API to get Notification List for loan
			ajaxRequest("rest/notification","GET","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					var notificationList=response.resultObject;
					ob.loanNotificationList=notificationList;
					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		populateUserNotification:function(callback){
			var ob=this;
			ob.clearUserNotificationArea(ob);
			for(var i=0;i<ob.userNotificationList.length;i++){
				var notification=ob.loanNotificationList[i];
				ob.paintUserNotification(notification);
			}
			if(callback){
				callback();
			}
		},
		populateLoanNotification:function(callback){
			var ob=this;
			ob.clearLoanNotificationArea(ob);
			for(var i=0;i<ob.loanNotificationList.length;i++){
				var notification=ob.loanNotificationList[i];
				ob.paintLoanNotification(notification);
			}
			if(callback){
				callback();
			}
		},
		initContext:function(){
			var ob=this;
			//Code need to be changed for notification related to user (Top Notification area)
			//ob.getNotificationForUser(function(ob){ob.populateUserNotification()});

			ob.getNotificationForLoan(function(ob){
				ob.populateLoanNotification();
			});
		},
		clearUserNotificationArea:function(ob){
			$("#"+ob.userContainerId).empty();
		},
		clearLoanNotificationArea:function(ob){
			$("#"+ob.loanContainerId).empty();
		},
		paintUserNotification:function(notification,callback){
			var ob=this;
			//code to append user notification
			if(callback){
				callback();
			}
		},
		paintLoanNotification:function(notification,callback){
			var ob=this;
			var closebtn=(notification.dismissable==true?'<div class="lp-alert-close-btn float-right" onclick="removeNotification(\'LNID'+notification.id+'\')"></div>':'');
			$("#"+ob.loanContainerId).append('<div class="lp-alert-item-container clearfix" id="LNID'+notification.id+'"><div class="lp-alert-item float-left">'+notification.content+'</div>'+closebtn+'</div>');
			if(callback){
				callback();
			}
		},
		removeUserNotification:function(notification,callback){
			var ob=this;
			//code to remove notification
			if(callback){
				callback();
			}
		},
		removeLoanNotification:function(notificationID,callback){
			var ob=this;
			//var notificationID="LNID"+notification.id;
			var existAry=$("#"+notificationID);
			if(existAry.length>0){
				$("#"+notificationID).remove();
			}
			if(callback){
				callback();
			}
		}
	};
	notificationContext.initContext();
	return notificationContext;
}
function getContext(key){
	if(newfiObject){
		if(newfiObject.contextHolder){
			if(newfiObject.contextHolder[key]){
				return newfiObject.contextHolder[key];
			}
		}
	}
}
function addContext(key,contxt){
	if(newfiObject){
		if(!newfiObject.contextHolder){
			newfiObject.contextHolder={};
		}
		newfiObject.contextHolder[key]=contxt;
		return true;
	}
	return false;
}
function deleteContext(key){
	if(newfiObject){
		if(newfiObject.contextHolder){
			if(newfiObject.contextHolder[key]){
				return delete(newfiObject.contextHolder[key]);
			}
		}
	}	
}
function removeNotification(id){
	if(id.indexOf("LNID")>=0){
		getContext("notification").removeLoanNotification(id);
	}else{

	}
}