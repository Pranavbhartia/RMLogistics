function getNotificationContext(loanId,userId){

	var notificationContext={
		loanId:loanId,
		userId:userId,
		loanLstCntElement:{},
		userContainerId:"",
		loanContainerId:"notificationParentContainer",
		userNotificationList:[],
		loanNotificationList:[],
		existingWrapper:undefined,
		alertWrapper:undefined,
		headerText:"",
		getNotificationForUser:function(callback){
			if(userId!=0){
				var ob=this;
				var data={};
				data.userID=ob.userId;
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
						showToastMessage(response.error.message);
					}else{
						var notificationList=response.resultObject;
						ob.loanNotificationList=notificationList;
						ob.headerText="important alerts";
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
			$("#alertHeder").html(ob.headerText);
			if(callback){
				callback(ob);
			}
		},
		initContext:function(draw){
			var ob=this;
			//Code need to be changed for notification related to user (Top Notification area)
			ob.getNotificationForUser();

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
			if((notificationID+"").indexOf("LNID")>=0)
				id=notificationID.substr(4);
			if((notificationID+"").indexOf("UNID")>=0)
				id=notificationID.substr(4);
			ajaxRequest("rest/notification/"+id,"DELETE","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message);
				}else{
					showToastMessage("Notification Dismissed");
					ob.removeNotificationfromList(id);
					var existAry=$("#"+notificationID);
					if(existAry.length>0){
						$("#"+notificationID).remove();
					}
					existAry=$("#"+"LNID"+id);
					if(existAry.length>0){
						$("#"+"LNID"+id).remove();
					}
					existAry=$("#"+"UNID"+id);
					if(existAry.length>0){
						$("#"+"UNID"+id).remove();
					}
					if(callback){
						callback(ob);
					}
					removeNotificationFromAllContext(id);
					ob.updateWrapper();
				}
				
			});
			//var notificationID="LNID"+notification.id;
			
		},
		snoozeLoanNotification:function(notificationID,snoozeTimeMin,callback){
			var ob=this;
			var data={};
			var id=notificationID;
			if((notificationID+"").indexOf("LNID")>=0)
				id=notificationID.substr(4);
			data.id=id;
			snoozeTimeMin=1000*60*snoozeTimeMin;
			data.remindOn=new Date().getTime()+snoozeTimeMin;
			data.read=false;
			ajaxRequest("rest/notification","PUT","json",JSON.stringify(data),function(response){
				if(response.error){
					showToastMessage(response.error.message);
				}else{
					showToastMessage("Success");
					ob.removeNotificationfromList(id);
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
		removeNotificationFromLoanList:function(notificationID){
			if((notificationID+"").indexOf("LNID")<0)
				notificationID="LNID"+notificationID;
			var existAry=$("#"+notificationID);
			if(existAry.length>0){
				$("#"+notificationID).remove();
			}
		},
		removeNotificationfromList:function(notificationID){
			var temp=[];
			var ob=this;
			var found=false;
			for(var i=0;i<ob.loanNotificationList.length;i++){
				if(ob.loanNotificationList[i].id!=notificationID){
					temp.push(ob.loanNotificationList[i]);
				}else
					found=true;
			}
			ob.loanNotificationList=temp;
			temp=[];
			for(var i=0;i<ob.userNotificationList.length;i++){
				if(ob.userNotificationList[i].id!=notificationID){
					temp.push(ob.userNotificationList[i]);
				}else
					found=true;
			}
			ob.userNotificationList=temp;
			return found;
		},
		scheduleAEvent:function(data,callback){
			var ob=this;
			
			var url="rest/notification"
			if(data.OTHURL){
				url=data.OTHURL;
			}
			ajaxRequest(url,"POST","json",JSON.stringify(data),function(response){
				if(response.error){
					showToastMessage(response.error.message);
				}else{
					showToastMessage("Notification Scheduled");
					if(data.milestoneId){
						data=data.notificationVo;
						data.id=response.resultObject;
					}
					else
						data.id=response.resultObject.id;
					data.dismissable=true;
					
					if(new Date().getTime()>=data.remindOn)
						ob.loanNotificationList.push(data);
					updateDefaultContext(data);
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
					ob.headerText="upcoming events";
					
					if(callback){
						callback(ob);
					}
				}
			});
		},
		updateWrapper:function(callback){
			if(this.existingWrapper)
				appendRecentAlertContainer(this.loanNotificationList,this,this.existingWrapper);
		}
	};
	//notificationContext.initContext();
	return notificationContext;
}
function updateDefaultContext(notification){
	var contxt=getContext("notification");
	contxt.loanNotificationList.push(notification);
	contxt.userNotificationList.push(notification);
	contxt.paintLoanNotification(notification);
	var row = getAlertNotificationRow(notification,contxt);
	if(contxt.alertWrapper)
		contxt.alertWrapper.append(row);
}
function removeNotificationFromAllContext(notificationId){
	if(newfiObject.contextHolder){
		for(var contxtKey in newfiObject.contextHolder){
			if(contxtKey.indexOf("notification")>=0){
				var contxt=newfiObject.contextHolder[contxtKey]	
				var found=contxt.removeNotificationfromList(notificationId);
				if(found==true){
					if(contxtKey.indexOf("notification")==0){
						contxt.removeNotificationFromLoanList(notificationId);
					}else{
						contxt.updateLoanListNotificationCount();
						contxt.updateWrapper();
					}
				}
			}
		}
	}
		
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
function getTimeElapsedString(dat){
	var seconds=(new Date().getTime()-dat)/1000;
	var future=false;
	if(seconds<0){
		seconds=seconds*-1;
		future=true;
	}
	if(seconds>=60){
		var minutes=seconds/60;
		seconds=seconds%60;
		if(minutes>=60){
			var hrs=minutes/60;
			minutes=minutes%60;
			if(hrs>24){
				var days=hrs/24;
				hrs=hrs%24;
				if(days>30){
					var dat=new Date(dat);
					var amPm=dat.getHours()>12?"PM":"AM";
					var hr=dat.getHours()%12<10?("0"+dat.getHours()%12):dat.getHours()%12;
					var min=dat.getMinutes()<10?("0"+dat.getMinutes()):dat.getMinutes();
					return "On "+$.datepicker.formatDate('M-dd-yy', dat)+" "+hr+":"+min+" "+amPm;
				}else{
					if(future)
						return "After "+parseInt(days)+" Days";
					else
						return parseInt(days)+" Days ago";
				}
			}else{
				if(future)
					return "After "+parseInt(hrs)+" Hours "+parseInt(minutes)+" Minutes"
				else
					return parseInt(hrs)+" Hours "+parseInt(minutes)+" Minutes ago";
			}
		}else{
			if(future)
				return "After "+parseInt(minutes)+" Minutes "+parseInt(seconds)+" Seconds ago";
			else
				return parseInt(minutes)+" Minutes "+parseInt(seconds)+" Seconds ago";
		}
	}else{
		if(future)
			return "After "+parseInt(seconds)+" Seconds ago";
		else
			return parseInt(seconds)+" Seconds ago";
	}
}

function appendAlertNotificationPopup(){
	var alertWrapper = $('<div>').attr({
		"id" : "alert-popup-wrapper",
		"class" : "alert-popup-wrapper"
	});
	var contxt=getContext("notification");
	contxt.alertWrapper=alertWrapper
	for(var i=0;i<contxt.userNotificationList.length;i++){
		var row = getAlertNotificationRow(contxt.userNotificationList[i],contxt);
		alertWrapper.append(row);
	}
	/*var row1 = getAlertNotificationRow("Salaried-W2-forms","2hr ago",false);
	var row2 = getAlertNotificationRow("Salaried-W2-forms","2hr ago",true);
	var row3 = getAlertNotificationRow("Salaried-W2-forms","2hr ago",false);
	var row4 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum","2hr ago",false);
	var row5 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum","2hr ago",false);
	var row6 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum","2hr ago",false);
	
	alertWrapper.append(row1).append(row2).append(row3).append(row4).append(row5).append(row6);*/
	
	$('#alert-notification-btn').append(alertWrapper);
}

function getAlertNotificationRow(notification,contxt){
	if(notification.remindOn>new Date().getTime())
		return;
	var row = $('<div id="UNID'+notification.id+'">').attr({
		"class" : "alert-popup-row clearfix"
	});
	
	var container = $('<div>').attr({
		"class" : "alert-popup-container clearfix"
	}); 
	
	var alertIcn = $('<div>').attr({
		"class" : "alert-popup-icn float-left"
	});
	
	if(!notification.dismissable){
		alertIcn.addClass('alert-system-icn');
	}else{
		alertIcn.addClass('alert-user-icn');
	}
	
	var alertTxtCont = $('<div>').attr({
		"class" : "alert-popup-cont float-left"
	});
	
	var alertTxt = $('<div>').attr({
		"class" : "alert-popup-txt"
	}).html(notification.content);
	
	var alertTime = $('<div>').attr({
		"class" : "alert-popup-time"
	}).html(getTimeElapsedString(notification.remindOn));
	
	alertTxtCont.append(alertTxt).append(alertTime);
	
	container.append(alertIcn).append(alertTxtCont);
	
	if(notification.dismissable){
		var alertRemoveIcn = $('<div>').attr({
			"class" : "alert-rm-icn float-right",
			"UNID" : "UNID"+notification.id
		}).on('click',function(e){
			var notificationId=this.getAttribute("UNID");
			var contxt=getContext("notification");
			contxt.removeLoanNotification(notificationId)
			e.stopImmediatePropagation();
		});
		container.append(alertRemoveIcn);
	}
	return row.append(container);
}


function dismissAlert(element){
	$(element).closest('.alert-popup-row').remove();
}

function hideNotificationPopup(){
	$('#ms-add-notification-popup').hide();
}
function removeNotificationPopup(){
	$('#ms-add-notification-popup').remove();
}
function showNotificationPopup(){
	$('#ms-add-notification-popup').show();
}

function addNotificationPopup(loanId,element,data){
	var wrapper = $('<div>').attr({
		"id" : "ms-add-notification-popup",
		"class" : "ms-add-notification-popup ms-add-member-popup"
	}).click(function(e){
		e.stopPropagation();
	});
	var contxt=getContext(loanId+"-notification");
	var component=getSchedulerContainer(contxt,data);
	wrapper.append(component);
	$(element).append(wrapper);
	$('#sch-msg-time-picker').datetimepicker({
		pickDate : false
	});
	showNotificationPopup();
}

$(document).click(function() {
	if ($('#ms-add-notification-popup').css("display") == "block") {
		removeNotificationPopup();
	}
});
