function getNotificationContext(loanId, userId) {

	var notificationContext = {
		loanId : loanId,
		userId : userId,
		loanLstCntElement : {},
		customerName : "",
		userContainerId : "",
		loanContainerId : "notificationParentContainer",
		userNotificationList : [],
		loanNotificationList : [],
		existingWrapper : undefined,
		alertWrapper : undefined,
		headerText : "",
		pushServerUrl : "/PushNotification/pushServlet/?task=notification&taskId=",
		enablePushnotification : updateHandler.enablePushnotification,
		addToList : function(list, object) {
			var exist = false;
			for (var i = 0; i < list.length; i++) {
				if (list[i].id == object.id) {
					exist = true;
				}
			}
			if (!exist) {
				list.push(object);
			}
			return exist;
		},
		getNotificationForUser : function(callback) {
			if (userId != 0) {
				var ob = this;
				var data = {};
				data.userID = ob.userId;
				// code to call API to get Notification List for user
				ajaxRequest("rest/notification", "GET", "json", data, function(
						response) {
					if (response.error) {
						showErrorToastMessage(response.error.message)
					} else {
						var notificationList = response.resultObject;
						ob.userNotificationList = notificationList;
						if (callback) {
							callback(ob);
						}
					}

				});
			}
		},
		//removed this function since we will make use of common update handler
		/*getNotificationUpdate : function(callback) {
			var ob = this;
			if (ob.loanId != 0 && ob.enablePushnotification) {
				var data = {};
				data.userID = 0;
				data.loanID = ob.loanId;
				// code to call API to get Notification List for loan
				// http://localhost:8080/PushNotification/pushServlet/?task=notification&taskId=1
				ajaxRequest(
						ob.pushServerUrl + ob.loanId,
						"GET",
						"json",
						data,
						function(response) {

						},
						false,
						undefined,
						function(response) {
							ob.getNotificationUpdate();
							if (response.error) {
								showToastMessage(response.error.message);
							} else {
								if (response.action == "new") {
									var nwData = response.data;
									var exist = false;
									// Removed Code
									// if(nwData.createdForID==newfiObject.user.id||(!nwData.createdForID||nwData.createdForID==0))
									if (checkNotificationApplicable(nwData)) {
										exist = ob
												.addToList(
														ob.loanNotificationList,
														nwData);
										if (!exist) {
											ob.updateWrapper();
											ob
													.updateLoanListNotificationCount();
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
									if (callback) {
										callback(ob);
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

							}
						});
			}
		},*/
		updateOtherClients : function(change, callback) {
			var ob = this;
			if ((ob.loanId != 0 || change.loanId) && ob.enablePushnotification) {
				change.task="notification";
				var data = {};
				var lnId = ob.loanId != 0 ? ob.loanId : change.loanId;
				// code to call API to get Notification List for loan
				ajaxRequest(ob.pushServerUrl + lnId + "&data="
						+ JSON.stringify(change), "POST", "json", data,
						function(response) {
							if (response.error) {
								showErrorToastMessage(response.error.message);
							} else {
								if (callback) {
									callback(ob);
								}
							}
						});
			}
		},
		getNotificationForLoan : function(callback) {
			if (loanId != 0) {
				var ob = this;
				var data = {};
				data.userID = 0;
				data.loanID = ob.loanId;
				// code to call API to get Notification List for loan
				ajaxRequest("rest/notification", "GET", "json", data, function(
						response) {
					if (response.error) {
						showErrorToastMessage(response.error.message);
					} else {
						var notificationList = response.resultObject;
						ob.loanNotificationList = notificationList;
						ob.headerText = "important alerts";
						if (callback) {
							callback(ob);
						}
					}
				});
			}
		},
		updateLoanListNotificationCount : function() {
			var ob = this;
			if (ob.loanLstCntElement) {
				ob.loanLstCntElement.empty();
				if (parseInt(ob.loanNotificationList.length) > 0) {
					var alerts = $('<div>').attr({
						"class" : "alerts-count"
					}).html(ob.loanNotificationList.length);
					ob.loanLstCntElement.append(alerts);
				}
			}
		},
		populateUserNotification : function(callback) {
			var ob = this;
			ob.clearUserNotificationArea(ob);
			for (var i = 0; i < ob.userNotificationList.length; i++) {
				var notification = ob.loanNotificationList[i];
				ob.paintUserNotification(notification);
			}
			if (callback) {
				callback(ob);
			}
		},
		populateLoanNotification : function(callback) {
			var ob = this;
			ob.clearLoanNotificationArea(ob);
			var count=0;
			for (var i = 0; i < ob.loanNotificationList.length; i++) {
				count++;
				var notification = ob.loanNotificationList[i];
				ob.paintLoanNotification(notification);
				if(count==3)
					break;
			}
			$("#alertHeder").html(ob.headerText);
			if (callback) {
				callback(ob);
			}
		},
		initContext : function(draw) {
			var ob = this;
			// Code need to be changed for notification related to user (Top
			// Notification area)
			ob.getNotificationForUser();

			ob.getNotificationForLoan(function(ob) {
				if (draw) {
					ob.populateLoanNotification();
				}
			});
		},
		clearUserNotificationArea : function(ob) {
			$("#" + ob.userContainerId).empty();
		},
		clearLoanNotificationArea : function(ob) {
			$("#" + ob.loanContainerId).empty();
		},
		paintUserNotification : function(notification, callback) {
			var ob = this;
			// code to append user notification
			if (callback) {
				callback(ob);
			}
		},
		shouldNotificationToBeShown : function (notification, callback)
		{
			if (notification.loanID != 0 && selectedUserDetail
					&& notification.loanID == selectedUserDetail.loanID && newfiObject.user.userRole.roleCd != "CUSTOMER" 
						&& notification.notificationType == "WATCH_ALERT_NOTIFICATION")
			{
				return false;
			}
			else
			{
				return true;
			}
		},
		paintLoanNotification : function(notification, callback) {
			var ob = this;
			var showForLM  = ob.shouldNotificationToBeShown(notification);
			if(showForLM == false)
			{
				return ;
			}
			var closebtn = (notification.dismissable == true ? '<div class="lp-alert-close-btn float-right" onclick="removeNotification(\'LNID'
					+ notification.id + '\')"></div>'
					: '');

			
			var customerName = "";
			if (notification.loanID != 0 && selectedUserDetail
					&& notification.loanID == selectedUserDetail.loanID) {
				customerName = "";
			} else if (notification.loanID != 0
					&& newfiObject.user.userRole.roleCd != "CUSTOMER") {
				customerName = " - " + notification.customerName;
			} else {
				customerName = "";
			}
			var alertTime = '';

			if (notification.remindOn)
				alertTime = "<div class = 'notification-time'>"+getTimeElapsedString(notification.remindOn)+"</div>";
			var notificationEle=$('<div>').attr({
					"class" : "lp-alert-item-container clearfix",
					"id" : "LNID"+ notification.id
				})
		/*	var innerEle='<div class="lp-alert-item float-left">'
									+ notification.content + customerName + alertTime
									+ '</div>' + closebtn */
			var mess=notification.content;
			var innerEle='<div class="lp-alert-item float-left" title="'+mess+'">'
									+ notification.content + customerName 
									+ '</div>' + closebtn + alertTime
			notificationEle.append(innerEle);

			/*var notificationEle='<div class="lp-alert-item-container clearfix" id="LNID'
									+ notification.id
									+ '">'</div>'*/
			
			$("#" + ob.loanContainerId)
					.append(notificationEle);

			addClickHandlerToNotification(notificationEle,notification);
	
			if (callback) {
				callback(ob);
			}
		},
		removeUserNotification : function(notification, callback) {
			var ob = this;
			// code to remove notification
			if (callback) {
				callback(ob);
			}
		},
		removeLoanNotification : function(notificationID, callback) {
			var ob = this;
			var data = {};
			var id = notificationID;
			if ((notificationID + "").indexOf("LNID") >= 0)
				id = notificationID.substr(4);
			if ((notificationID + "").indexOf("UNID") >= 0)
				id = notificationID.substr(4);
			ajaxRequest(
					"rest/notification/" + id,
					"DELETE",
					"json",
					data,
					function(response) {
						if (response.error) {
							showErrorToastMessage(response.error.message);
						} else {
							showToastMessage(notificationDismisses);

							// code for updating all other clients start
							var notificationOb;
							for (var i = 0; i < ob.loanNotificationList.length; i++) {
								if (ob.loanNotificationList[i].id == id) {
									notificationOb = ob.loanNotificationList[i];
								}
							}
							var changeData = {
								"action" : "remove",
								"notificationID" : notificationID,
								"id" : id,
								"loanId" : notificationOb.loanID
							};
							ob.updateOtherClients(changeData);
							// end

							ob.removeNotificationfromList(id);
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
							if (callback) {
								callback(ob);
							}
							removeNotificationFromAllContext(id);
							ob.updateWrapper();
						}

					});
			// var notificationID="LNID"+notification.id;

		},
		snoozeLoanNotification : function(notificationID, snoozeTimeMin,
				callback) {
			var ob = this;
			var data = {};
			var id = notificationID;
			if ((notificationID + "").indexOf("LNID") >= 0)
				id = notificationID.substr(4);
			data.id = id;
			snoozeTimeMin = 1000 * 60 * snoozeTimeMin;
			data.remindOn = new Date().getTime() + snoozeTimeMin;
			data.read = false;
			ajaxRequest("rest/notification", "PUT", "json", JSON
					.stringify(data), function(response) {
				if (response.error) {
					showErrorToastMessage(response.error.message);
				} else {
					showToastMessage(notificationSuccess);

					// code for updating all other clients start
					var notificationOb;
					for (var i = 0; i < ob.loanNotificationList.length; i++) {
						if (ob.loanNotificationList[i].id == id) {
							notificationOb = ob.loanNotificationList[i];
						}
					}
					var changeData = {
						"action" : "snooze",
						"notificationID" : notificationID,
						"id" : id,
						"loanId" : notificationOb.loanID
					};
					ob.updateOtherClients(changeData);
					// end

					ob.removeNotificationfromList(id);
					var existAry = $("#" + notificationID);
					if (existAry.length > 0) {
						$("#" + notificationID).remove();
					}
					if (callback) {
						callback(ob);
					}
				}

			});
			// var notificationID="LNID"+notification.id;

		},
		removeNotificationFromLoanList : function(notificationID) {
			if ((notificationID + "").indexOf("LNID") < 0)
				notificationID = "LNID" + notificationID;
			var existAry = $("#" + notificationID);
			if (existAry.length > 0) {
				$("#" + notificationID).remove();
			}
		},
		removeNotificationfromList : function(notificationID) {
			var temp = [];
			var ob = this;
			var found = false;
			for (var i = 0; i < ob.loanNotificationList.length; i++) {
				if (ob.loanNotificationList[i].id != notificationID) {
					temp.push(ob.loanNotificationList[i]);
				} else
					found = true;
			}
			ob.loanNotificationList = temp;
			temp = [];
			for (var i = 0; i < ob.userNotificationList.length; i++) {
				if (ob.userNotificationList[i].id != notificationID) {
					temp.push(ob.userNotificationList[i]);
				} else
					found = true;
			}
			ob.userNotificationList = temp;
			return found;
		},
		scheduleAEvent : function(data, callback) {
			var ob = this;

			var url = "rest/notification"
			if (data.OTHURL) {
				url = data.OTHURL;
			}
			ajaxRequest(
					url,
					"POST",
					"json",
					JSON.stringify(data),
					function(response) {
						if (response.error) {
							showErrorToastMessage(response.error.message);
						} else {
							showToastMessage(notificationSceduled);
							var milestoneId;
							if (data.milestoneId) {
								milestoneId = data.milestoneId;
								data = data.notificationVo;
								data.id = response.resultObject;
							} else
								data.id = response.resultObject.id;
							data.dismissable = true;

							// code for updating all other clients start
							var changeData = {
								"action" : "new",
								"data" : data
							};
							ob.updateOtherClients(changeData);
							// end

							// if(new Date().getTime()>=data.remindOn)
							var exist = ob.addToList(ob.loanNotificationList,
									data);
							if (!exist)
								updateDefaultContext(data);
							if (callback) {
								callback(ob);
							}
							if (milestoneId) {
								var ctx = workFlowContext.mileStoneContextList[milestoneId];
								if(ctx.workItem.status!="3")
									ctx.updateMilestoneView("1");
							}
							updateNotificationView();
						}

					});
		},
		getLoanNotificationByType : function(callback) {
			var ob = this;
			var data = {}
			data.userID = ob.userId;
			data.type = "NOTIFICATION";
			ajaxRequest("rest/notification/bytype", "GET", "json", data,
					function(response) {
						if (response.error) {
							showErrorToastMessage(response.error.message)
						} else {
							var notificationList = response.resultObject;
							ob.loanNotificationList = notificationList;
							ob.headerText = "Notifications";

							if (callback) {
								callback(ob);
							}
						}
					});
		},
		updateWrapper : function(callback) {
			if (this.existingWrapper)
				appendRecentAlertContainer(this.loanNotificationList, this,
						this.existingWrapper);
		}
	};
	// notificationContext.initContext();
	if (notificationContext.loanId){
		updateHandler.addLoanToTaskKey(notificationContext.loanId);
		//Commented call since we will make use of common update handler
		//notificationContext.getNotificationUpdate();	
	}
	return notificationContext;
}
function checkNotificationApplicable(notification) {
	if (notification.createdForID == newfiObject.user.id) {
		return true;
	} else {
		if (!notification.createdForID) {
			if (newfiObject.user.internalUserDetail) {
				if (notification.visibleToInternalUserRoles
						.indexOf(newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleName) >= 0)
					return true;
			} else {
				if (notification.visibleToUserRoles
						.indexOf(newfiObject.user.userRole.roleCd) >= 0)
					return true;
			}
		} else
			return false;
	}
}
function getUserRoleKey() {
	if (newfiObject.user.internalUserDetail) {
		return newfiObject.user.internalUserDetail.internalUserRoleMasterVO.roleName;
	} else {
		return newfiObject.user.userRole.roleCd;
	}
}
function updateDefaultContext(notification) {
	var contxt = getContext("notification");
	contxt.loanNotificationList.push(notification);
	contxt.userNotificationList.push(notification);
	contxt.paintLoanNotification(notification);
	var row = getAlertNotificationRow(notification, contxt);
	if (contxt.alertWrapper){
		contxt.alertWrapper.append(row);
		addClickHandlerToNotification(row,notification);
	}
}
function removeNotificationFromAllContext(notificationId) {
	if (newfiObject.contextHolder) {
		for ( var contxtKey in newfiObject.contextHolder) {
			if (contxtKey.indexOf("notification") >= 0) {
				var contxt = newfiObject.contextHolder[contxtKey]
				var found = contxt.removeNotificationfromList(notificationId);
				if (found == true) {
					if (contxtKey.indexOf("notification") == 0) {
						contxt.removeNotificationFromLoanList(notificationId);
					} else {
						contxt.updateLoanListNotificationCount();
						contxt.updateWrapper();
					}
				}
			}
		}
	}

}
function getContext(key) {
	if (newfiObject) {
		if (newfiObject.contextHolder) {
			if (newfiObject.contextHolder[key]) {
				return newfiObject.contextHolder[key];
			}
		}
	}
}
function addContext(key, contxt) {
	if (newfiObject) {
		if (!newfiObject.contextHolder) {
			newfiObject.contextHolder = {};
		}
		newfiObject.contextHolder[key] = contxt;
		return true;
	}
	return false;
}
function deleteContext(key) {
	if (newfiObject) {
		if (newfiObject.contextHolder) {
			if (newfiObject.contextHolder[key]) {
				return delete (newfiObject.contextHolder[key]);
			}
		}
	}
}
function removeNotification(id) {
	if (id.indexOf("LNID") >= 0) {
		getContext("notification").removeLoanNotification(id);
	} else {

	}
}

function takeToMilestone(id) {
	  if (userIsCustomer()) {
		  location.hash =  "#myLoan/my-loan-progres";
	} else {
		location.hash = "#loan/" + id + "/progress";
	}
}

function getTimeElapsedString(dat) {
	var seconds = (new Date().getTime() - dat) / 1000;
	var future = false;
	if (seconds < 0) {
		seconds = seconds * -1;
		future = true;
	}
	if (seconds >= 60) {
		var minutes = seconds / 60;
		seconds = seconds % 60;
		if (minutes >= 60) {
			var hrs = minutes / 60;
			minutes = minutes % 60;
			if (hrs > 24) {
				var days = hrs / 24;
				hrs = hrs % 24;
				if (days > 30) {
					var dat = new Date(dat);
					var amPm = dat.getHours() > 12 ? "PM" : "AM";
					var hr = dat.getHours() % 12 < 10 ? ("0" + dat.getHours() % 12)
							: dat.getHours() % 12;
					var min = dat.getMinutes() < 10 ? ("0" + dat.getMinutes())
							: dat.getMinutes();
					return "On " + $.datepicker.formatDate('M-dd-yy', dat)
							+ " " + hr + ":" + min + " " + amPm;
				} else {
					if (future)
						return "After " + parseInt(days) + " Days";
					else
						return parseInt(days) + " Days ago";
				}
			} else {
				if (future)
					return "After " + parseInt(hrs) + " Hours "
							+ parseInt(minutes) + " Minutes"
				else
					return parseInt(hrs) + " Hours " + parseInt(minutes)
							+ " Minutes ago";
			}
		} else {
			if (future)
				return "After " + parseInt(minutes) + " Minutes "
						+ parseInt(seconds) + " Seconds ago";
			else
				return parseInt(minutes) + " Minutes " + parseInt(seconds)
						+ " Seconds ago";
		}
	} else {
		if (future)
			return "After " + parseInt(seconds) + " Seconds ago";
		else
			return parseInt(seconds) + " Seconds ago";
	}
}

function appendAlertNotificationPopup() {
	var alertContWrapper = $('<div>').attr({
		"id" : "alert-popup-cont-wrapper",
		"class" : "alert-popup-cont-wrapper"
	});
	var alertWrapper = $('<div>').attr({
		"id" : "alert-popup-wrapper",
		"class" : "alert-popup-wrapper"
	});
	var contxt = getContext("notification");
	contxt.alertWrapper = alertWrapper;
	for (var i = 0; i < contxt.userNotificationList.length; i++) {
		var row = getAlertNotificationRow(contxt.userNotificationList[i],
				contxt);
		alertWrapper.append(row);
		addClickHandlerToNotification(row,contxt.userNotificationList[i]);

	}
	/*
	 * var row1 = getAlertNotificationRow("Salaried-W2-forms","2hr ago",false);
	 * var row2 = getAlertNotificationRow("Salaried-W2-forms","2hr ago",true);
	 * var row3 = getAlertNotificationRow("Salaried-W2-forms","2hr ago",false);
	 * var row4 = getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum
	 * Lorem Ipsum","2hr ago",false); var row5 = getAlertNotificationRow("Lorem
	 * Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum","2hr ago",false); var row6 =
	 * getAlertNotificationRow("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
	 * Ipsum","2hr ago",false);
	 * 
	 * alertWrapper.append(row1).append(row2).append(row3).append(row4).append(row5).append(row6);
	 */

	$('#alert-notification-btn').append(alertContWrapper.append(alertWrapper));
    $('#alert-popup-wrapper').perfectScrollbar({
        suppressScrollX : true
    });
}

function getAlertNotificationRow(notification, contxt) {
	if (notification.remindOn > new Date().getTime())
		return;
	var row = $('<div id="UNID' + notification.id + '">').attr({
		"class" : "alert-popup-row clearfix"
	});

	var container = $('<div>').attr({
		"class" : "alert-popup-container clearfix"
	});

	var alertIcn = $('<div>').attr({
		"class" : "alert-popup-icn float-left"
	});

	if (!notification.dismissable) {
		alertIcn.addClass('alert-system-icn');
	} else {
		alertIcn.addClass('alert-user-icn');
	}

	var alertTxtCont = $('<div>').attr({
		"class" : "alert-popup-cont float-left"
	});

	var alertTxt = $('<div>').attr({
		"class" : "alert-popup-txt",
		"title" : notification.content
	}).html(notification.content);

	var customerName = $('<div>').attr({
		"class" : "alert-popup-txt"
	}).html(" For : " + notification.customerName);

	var alertTime = $('<div>').attr({
		"class" : "alert-popup-time"
	}).html(getTimeElapsedString(notification.remindOn));

	alertTxtCont.append(alertTxt);
	if (notification.customerName)
		alertTxtCont.append(customerName);
	if (notification.remindOn)
		alertTxtCont.append(alertTime);

	container.append(alertIcn).append(alertTxtCont);
	//removed this click handler binding
	/*container.bind("click", function(e) {
		if (notification.loanID!=0)
		{
			hideAlertNotificationPopup();
			takeToMilestone(notification.loanID);
		}
	});*/
	if (notification.dismissable) {
		var alertRemoveIcn = $('<div>').attr({
			"class" : "alert-rm-icn float-right",
			"UNID" : "UNID" + notification.id
		}).on('click', function(e) {
			var notificationId = this.getAttribute("UNID");
			var contxt = getContext("notification");
			contxt.removeLoanNotification(notificationId)
			e.stopImmediatePropagation();
		});
		container.append(alertRemoveIcn);
	}
	
			
	return row.append(container);
}

function dismissAlert(element) {
	$(element).closest('.alert-popup-row').remove();
}

function hideNotificationPopup() {
	$('#ms-add-notification-popup').hide();
}
function removeNotificationPopup() {
	$('#ms-add-notification-popup').remove();
}
function showNotificationPopup() {
	$('#ms-add-notification-popup').show();
}

function addNotificationPopup(loanId, element, data) {
	var wrapper = $('<div>').attr({
		"id" : "ms-add-notification-popup",
		"class" : "ms-add-notification-popup ms-add-member-popup"
	}).click(function(e) {
		e.stopPropagation();
	});
	var contxt = getContext(loanId + "-notification");
	var component = getSchedulerContainer(contxt, data);
	wrapper.append(component);
	$(element).append(wrapper);
	$('#sch-msg-time-picker').datetimepicker({
		pickDate : false
	});
	showNotificationPopup();
}
$(document).click(function() {
	if ($('#ms-add-notification-popup').css("display") == "block") {
		if ($(event.target).attr("class") != "day")
			removeNotificationPopup();
	}
});

function getHashLocationForNotification(type,loanId){
	if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
		switch(type){
			case "NEEDED_ITEMS_NOTIFICATION":
				return "#myLoan/upload-my-needs";
			case "LOCK_RATE_CUST_NOTIFICATION":
				return "#myLoan/lock-my-rate";
			case "LOCK_NOTIFICATION":
				return "#myLoan/lock-my-rate";
			case "PURCHASE_DOC_NOTIFICATION":
				return "#myLoan/upload-my-needs";
			case "WATCH_ALERT_NOTIFICATION":
				if(newfiObject.applicationKnowNewfi)
					return "#myLoan/myTeam";
				break;
			case "COMPLETE_APPLICATION_NOTIFICATION":
				if(newfiObject.applicationNavTab)
					return "#myLoan/my-application";
				break;
			case "NEEDS_LIST_SET_TYPE":
				return "#myLoan/upload-my-needs";
			case "VERIFY_EMAIL":				
				return resendEmail;
		}
		return "#myLoan/my-loan-progress:"+type;
	}else{
		var baseHash="#loan/"+loanId+"/"
		switch(type){
			case "NOTIFICATION":
				return "";
			case "NEEDED_ITEMS_NOTIFICATION":
				return baseHash+"needs";
			case "LOCK_RATE_CUST_NOTIFICATION":
				return baseHash+"lock-rate";
			case "LOCK_NOTIFICATION":
				return baseHash+"lock-rate";
			case "PURCHASE_DOC_NOTIFICATION":
				return baseHash+"needs";
			case "COMPLETE_APPLICATION_NOTIFICATION":
				if(newfiObject.applicationNavTab)
					return baseHash+"application";
				break;
			case "NEEDS_LIST_SET_TYPE":
				return baseHash+"needs";
			case "VERIFY_EMAIL":				
				return baseHash+"detail";
		}
		return baseHash+"progress:"+type;
	}
}
function resendEmail()
{
	var user=new Object();
	user.emailId=newfiObject.user.emailId;
	ajaxRequest("rest/userprofile/forgetPassword"+"?resend=true", "POST", "json", JSON.stringify(user));
}
function addClickHandlerToNotification(element,notification){
	var hashLocation=getHashLocationForNotification(notification.notificationType,notification.loanID);
	if (typeof(hashLocation) == "function")
	{
		$(element).bind("click",function(e){hashLocation()});
		if(!$(element).hasClass("cursor-pointer"))
			$(element).addClass("cursor-pointer");
	}
	else if(hashLocation!=""){
		$(element).bind("click",{"target":hashLocation},navigateToHash);
		if(!$(element).hasClass("cursor-pointer"))
			$(element).addClass("cursor-pointer");
	}
}
function navigateToHash(e){
	var hashLocation=e.data.target;
	e.stopPropagation();
	window.location.hash=hashLocation;
	hideAlertNotificationPopup();
}

function updateNotificationView(){
	if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
		var ctx=newfiObject.contextHolder["notification"];
		ctx.initContext(true);
	}else{
		if(typeof(selectedUserDetail)==="undefined"){
			for(var key in newfiObject.contextHolder){
				var ctx=newfiObject.contextHolder[key];
				if(key=="notification"){
					ctx.getLoanNotificationByType(function(ob) {
						ob.populateLoanNotification();
					});
					ctx.getNotificationForUser();
				}else{
					ctx.getNotificationForLoan(function(ob){
						ob.updateLoanListNotificationCount();
					});
				}
			}
		}else{
			var ctx=newfiObject.contextHolder[selectedUserDetail.loanID+"-notification"];
			ctx.getNotificationForLoan(function(ob) {
				ob.populateLoanNotification();
			});
		}	
	}
}

$(document).on('click', '.left-panel,.lp-t2-wrapper', function(e) {
	try{
		updateNotificationView();
	}catch(exception){

	}
});