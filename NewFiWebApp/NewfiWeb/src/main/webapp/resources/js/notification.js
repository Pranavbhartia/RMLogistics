function getNotificationContext(loanId,userId){

	var notificationContext={
		loanId:loanId,
		userId:userId,
		loanLstCntElement:{},
		userContainerId:"",
		loanContainerId:"notificationParentContainer",
		userNotificationList:[],
		loanNotificationList:[],
		existingWrapper:{},
		getNotificationForUser:function(callback){
			if(userId!=0){
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
			}
		},
		getNotificationForLoan:function(callback){
			if(loanId!=0){
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
			}
		},
		updateLoanListNotificationCount:function(){
			var ob=this;
			if(ob.loanLstCntElement){
				ob.loanLstCntElement.empty();
				if (parseInt(ob.loanNotificationList.length) > 0) {
					var alerts = $('<div>').attr({
						"class" : "alerts-count"
					}).html(ob.loanNotificationList.length);
					ob.loanLstCntElement.append(alerts);
				}
			}
		},
		populateUserNotification:function(callback){
			var ob=this;
			ob.clearUserNotificationArea(ob);
			for(var i=0;i<ob.userNotificationList.length;i++){
				var notification=ob.loanNotificationList[i];
				ob.paintUserNotification(notification);
			}
			if(callback){
				callback(ob);
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
				callback(ob);
			}
		},
		initContext:function(draw){
			var ob=this;
			//Code need to be changed for notification related to user (Top Notification area)
			//ob.getNotificationForUser(function(ob){ob.populateUserNotification()});

			ob.getNotificationForLoan(function(ob){
				if(draw){
					ob.populateLoanNotification();
				}
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
				callback(ob);
			}
		},
		paintLoanNotification:function(notification,callback){
			var ob=this;
			var closebtn=(notification.dismissable==true?'<div class="lp-alert-close-btn float-right" onclick="removeNotification(\'LNID'+notification.id+'\')"></div>':'');
			$("#"+ob.loanContainerId).append('<div class="lp-alert-item-container clearfix" id="LNID'+notification.id+'"><div class="lp-alert-item float-left">'+notification.content+'</div>'+closebtn+'</div>');
			if(callback){
				callback(ob);
			}
		},
		removeUserNotification:function(notification,callback){
			var ob=this;
			//code to remove notification
			if(callback){
				callback(ob);
			}
		},
		removeLoanNotification:function(notificationID,callback){
			var ob=this;
			var data={};
			var id=notificationID;
			if((notificationID+"").indexOf("LNID")>0)
				id=notificationID.substr(4);
			ajaxRequest("rest/notification/"+id,"DELETE","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					ob.removeNotificationfromList(notificationID);
					var existAry=$("#"+notificationID);
					if(existAry.length>0){
						$("#"+notificationID).remove();
					}
					if(callback){
						callback(ob);
					}
				}
				
			});
			//var notificationID="LNID"+notification.id;
			
		},
		snoozeLoanNotification:function(notificationID,snoozeTimeMin,callback){
			var ob=this;
			var data={};
			var id=notificationID;
			if((notificationID+"").indexOf("LNID")>0)
				id=notificationID.substr(4);
			data.id=id;
			snoozeTimeMin=1000*60*snoozeTimeMin;
			data.remindOn=new Date().getTime()+snoozeTimeMin;
			data.read=false;
			ajaxRequest("rest/notification","PUT","json",JSON.stringify(data),function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					ob.removeNotificationfromList(notificationID);
					var existAry=$("#"+notificationID);
					if(existAry.length>0){
						$("#"+notificationID).remove();
					}
					if(callback){
						callback(ob);
					}
				}
				
			});
			//var notificationID="LNID"+notification.id;
			
		},
		removeNotificationfromList:function(notificationID){
			var temp=[];
			var ob=this;
			for(var i=0;i<ob.loanNotificationList.length;i++){
				if(ob.loanNotificationList[i].id!=notificationID){
					temp.push(ob.loanNotificationList[i]);
				}
			}
			ob.loanNotificationList=temp;
			temp=[];
			for(var i=0;i<ob.userNotificationList.length;i++){
				if(ob.userNotificationList[i].id!=notificationID){
					temp.push(ob.userNotificationList[i]);
				}
			}
			ob.userNotificationList=temp;
		},
		scheduleAEvent:function(data,callback){
			var ob=this;
			data.loanID=ob.loanId;
			data.notificationType="NOTIFICATION";
			ajaxRequest("rest/notification","POST","json",JSON.stringify(data),function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					data.dismissable=true;
					data.id=response.resultObject.id;
					if(new Date().getTime()>=data.remindOn)
						ob.loanNotificationList.push(data);

					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		getLoanNotificationByType:function(callback){
			var ob=this;
			var data={}
			data.userID=ob.userId;
			data.type="NOTIFICATION";
			ajaxRequest("rest/notification/bytype","GET","json",data,function(response){
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
		updateWrapper:function(callback){
			appendRecentAlertContainer(this.loanNotificationList,this,this.existingWrapper);
		}
	};
	//notificationContext.initContext();
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