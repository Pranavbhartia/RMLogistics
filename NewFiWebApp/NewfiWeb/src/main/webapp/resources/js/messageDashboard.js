/*
 * Functions for customer message dashboard
 */

var parentId = null;
var loanTeam;
var currentPage;
var otherUsers;

$(window).scroll(
		function() {
			if (doPagination == true) {
				if ($(window).scrollTop() == $(document).height()
						- $(window).height()) {
					console.info("run our call for pagination");
					currentPage++;
					getConversationPagination();
				}
			}

		});

function getConversationPagination() {
	ajaxRequest("rest/commlog/" + newfiObject.user.id + "/"
			+ currentUserAndLoanOnj.activeLoanId + "/" + currentPage, "GET",
			"json", "", paintConversationPagination, true, "conv-container");
}

function showAgentMessageDashboard() {
	var convMainContainer = $('<div>').attr({
		"id" : "conv-main-container",
		"class" : "agent-message-dashboard"
	});
	$('#center-panel-cont').append(convMainContainer);
	getLoanTemUsingloanId();
	adjustCenterPanelWidth();
}

function paintConversationPagination(response) {

	var conversations = response.resultObject.messageVOs;
	var showSM = response.resultObject.salesManagerPresent;
	paintConversations(conversations,showSM);
}

function setCurrentUserObject() {
	if (selectedUserDetail == null) {
		currentUserAndLoanOnj.userId = newfiObject.user.id;
		currentUserAndLoanOnj.activeLoanId = newfiObject.user.defaultLoanId;
	} else {
		currentUserAndLoanOnj.userId = selectedUserDetail.userID;
		currentUserAndLoanOnj.activeLoanId = selectedUserDetail.loanID;
	}

}

function saveParentMessage() {
	/*if (otherUsers.length == 0) {
		openDialog(callBackpopupFunction);
		showDialogPopup("Select A Person",
				"You need to select a person before replying!",
				callBackpopupFunction);
		return;
	}
*/
	parentId = null;
	var messageText = $("#textareaParent").val();

	if ($.trim(messageText) == "") {
		showToastMessage(enterSomeText);
		return false;
	}
	doSavemessageAjaxCall(messageText);

}

function callBackpopupFunction() {
	return false;
}

function doSavemessageAjaxCall(messageText, callBack) {

	var message = new Object();

	var createdUser = new Object();
	createdUser.userID = newfiObject.user.id;
	createdUser.userName = newfiObject.user.displayName;
	createdUser.roleName = newfiObject.user.userRole.roleDescription;
	if (callBack) {
		message.loanId = selectedUserDetail.loanID;
	}else{
		message.loanId = currentUserAndLoanOnj.activeLoanId;	
	}
	
	message.parentId = parentId;
	message.message = messageText;
	message.createdUser = createdUser;
	message.otherUsers = otherUsers;
	message.links = new Array();
	message.messageVOs = "null";
	saveMessageCall(message, callBack);
}

function addOtherUserObject(userId) {
	var myLoanTeam = loanTeam.resultObject;
	for (i in myLoanTeam) {
		if (myLoanTeam[i].id == userId) {
			createOtherUserobject(myLoanTeam[i].id, myLoanTeam[i].firstName,
					myLoanTeam[i].userRole.roleDescription);
			break;
		}
	}
}

function removeOtherUserObject(userId) {
	for (i in otherUsers) {
		if (otherUsers[i].userID == userId) {
			otherUsers.splice(i, 1);
			break;
		}
	}
}

function getOtherUserObject(userId) {
	for (i in otherUsers) {
		if (otherUsers[i].userID == userId) {
			return otherUsers[i];

		}
	}
}

function createOtherUserobject(userID, userName, roleName) {

	var oUser = new Object();
	oUser.userID = userID;
	oUser.userName = userName;
	oUser.roleName = roleName;
	otherUsers.push(oUser);
}

function setCurrentMessageReplyId(parentMessageId) {
	parentId = parentMessageId;
}

function setReplyToMessageObject(messageText) {
	if (messageText == "") {
		showToastMessage(enterSomeText);
		return false;
	}
	doSavemessageAjaxCall(messageText);
}

function saveMessageCall(message, callBack) {
	console.info(message);
	if (callBack) {
		ajaxRequest("rest/commlog/save", "POST", "json", JSON
				.stringify(message), callBack);
	} else {
		ajaxRequest("rest/commlog/save", "POST", "json", JSON
				.stringify(message), successCallBackSaveMessage);
	}

}

function successCallBackSaveMessage() {
	console.info("successCallBackSaveMessage saved method claeed ");
	$("#conv-main-container").empty();
	if (selectedUserDetail != undefined) {
		showAgentMessageDashboard();
	} else {
		showMessageDashboard();
	}

}

function getConversationsofUser() {
	ajaxRequest("rest/commlog/" + newfiObject.user.id + "/"
			+ currentUserAndLoanOnj.activeLoanId + "/" + currentPage, "GET",
			"json", "", paintCommunicationLog);
}

function paintCommunicationLog(response) {
	var rightArrow = $('<div>').attr({
		"class" : "lp-right-arrow"
	});
	$('#lp-talk-wrapper').append(rightArrow);
	var msgDashboardWrapper = getMessageDashboardWrapper();
	var conversationHistoryWrapper = getConversationHistoryWrapper();
	$('#conv-main-container').append(msgDashboardWrapper).append(
			conversationHistoryWrapper);
	$('#conv-container').html('');

	if (response.error != undefined && response.error.code == 500) {
		showDialogPopup(
				"Connection issue",
				"Not able to connect.Please try again after some time.Click OK to continue.",
				function() {
					return false;
				});
		return false;
	}

	var conversations = response.resultObject.messageVOs;
	var showSM = response.resultObject.salesManagerPresent;
	paintConversations(conversations,showSM);
	adjustRightPanelOnResize();
}

function getLoanTemUsingloanId() {
	$(window).scrollTop(0);
	currentPage = 0;
	setCurrentUserObject();
	otherUsers = new Array();
	ajaxRequest("rest/loan/" + currentUserAndLoanOnj.activeLoanId + "/team",
			"GET", "json", "", getCurrentLoanTeamObject);
}

function getCurrentLoanTeamObject(response) {
	loanTeam = response;
	getConversationsofUser();
}

function getMessageDashboardWrapper() {
	var myLoanTeam = loanTeam.resultObject;
	var wrapper = $('<div>').attr({
		"class" : "message-wrapper"
	});
	
	wrapper.append(header);
	var header = $('<div>').attr({
		"class" : "message-header"
	}).html("Talk to Your newfi Team");//NEXNF-641
	wrapper.append(header);

	var container = $('<div>').attr({
		"class" : "message-container"
	});

	var assignedAgentWrapper = $('<div>').attr({
		"class" : "assigned-agent-wrapper clearfix"
	});

	for (i in myLoanTeam) {
		if (myLoanTeam[i].emailId == newfiObject.user.emailId)
			continue;

		var userRole = myLoanTeam[i].userRole.roleCd;
		var userDescription = myLoanTeam[i].userRole.roleDescription;
		if (myLoanTeam[i].userRole.roleCd == "INTERNAL") {

			userRole = myLoanTeam[i].internalUserDetail.internalUserRoleMasterVO.roleName;
//			if (userRole != "LM") {
//				continue;
//			}
			userDescription = myLoanTeam[i].internalUserDetail.internalUserRoleMasterVO.roleDescription;
		}
		var agent = getAssignedAgentContainer(myLoanTeam[i].id,
				myLoanTeam[i].firstName + " " + myLoanTeam[i].lastName,
				userDescription,
				formatPhoneNumberToUsFormat(myLoanTeam[i].phoneNumber),
				myLoanTeam[i].photoImageUrl);
		assignedAgentWrapper.append(agent);
		

		createOtherUserobject(myLoanTeam[i].id, myLoanTeam[i].firstName,
				userRole);
	}

	/* addClass('assigned-agent-unselect'); */

	container.append(assignedAgentWrapper);
	

	var textContainer = $('<div>').attr({
		"class" : "message-container"
	});
	var textBox = $('<textarea>')
			.attr(
					{
						"class" : "message-cont-textinput",
						"id" : "textareaParent",
						"placeholder" : "Type your message here. Recipients are highlighted with blue circle, click to toggle. When finished click send."
					});

	textContainer.append(textBox);

	var bottomContainer = $('<div>').attr({
		"class" : "message-bottom-cont clearfix"
	});

	var messageRecipients = $('<div>').attr({
		"id" : "message-recipient-container",
		"class" : "clearfix float-left"
	});

	var messageRecipientTitle = $('<div>').attr({
		"class" : "message-recipient-title float-left semi-bold"
	}).html("Message Recipient :-");
	messageRecipients.append(messageRecipientTitle);

	var index = 0;
	container.find('.assigned-agent-container').each(
			function() {
				if (!$(this).hasClass('assigned-agent-unselect')) {
					var name = $(this).find('.assigned-agent-cont-rc').find(
							'.assigned-agent-name').html().trim();
					var agentIncluedeInMessage = $('<div>').attr({
						"class" : "message-recipient-icn float-left clearfix",
						"data-agent" : $(this).attr("data-agent"),
						"agentId" : $(this).attr("agentId")
					}).html(name);

					var removePersonIcn = $('<div>').attr({
						"class" : "message-recipient-remove-icn float-right"
					});

					agentIncluedeInMessage.append(removePersonIcn);

					if (index > 0) {
						agentIncluedeInMessage.prepend(", ");
					}
					messageRecipients.append(agentIncluedeInMessage);
					index++;
				}
			});
	bottomContainer.append(messageRecipients);

	var messageBtn = $('<div>').attr({
		"class" : "message-btn float-right"
		/*"onclick" : "saveParentMessage()"*/
	}).html("Send Message").on('click',function(){
		if (otherUsers.length == 0) {
			showDialogPopup("Select A Person",
					"You need to select a person before replying!",
					callBackpopupFunction);
			return;
		}else{
			saveParentMessage();
		}
	});

	bottomContainer.append(messageBtn);
	textContainer.append(bottomContainer);
	container.append(textContainer);
	return wrapper.append(container);
}

function getAssignedAgentContainer(id, agentName, agentRole, contactNo,
		imageUrl) {
	var wrapper = $('<div>').attr({
		"class" : "assigned-agent-cont-wrapper"
	});

	var container = $('<div>').attr({
		"class" : "assigned-agent-container clearfix float-left",
		"data-agent" : agentName,
		"agentId" : id
	});
	var leftCol = $('<div>').attr({
		"class" : "assigned-agent-cont-lc float-left"
	});
	var imgCont = $('<div>').attr({
		
	});

	if (imageUrl != undefined) {
		imgCont.addClass("assigned-agent-img");
		imgCont.css("background-image", "url('" + imageUrl + "')");
	}
	else
	{
		imgCont.addClass("assigned-agent-default-img");		
		imgCont.text(getInitialsFromFullName(agentName));
	}

	var onlineStatus = $('<div>').attr({
		"class" : "assigned-agent-online-status"
	}).html('Chat Now');
	leftCol.append(imgCont).append(onlineStatus);
	container.append(leftCol);

	var rightCol = $('<div>').attr({
		"class" : "assigned-agent-cont-rc float-left"
	});

	var name = $('<div>').attr({
		"class" : "assigned-agent-name"
	}).html(agentName);

	var role = $('<div>').attr({
		"class" : "assigned-agent-role"
	}).html(agentRole);

	var contact = $('<div>').attr({
		"class" : "assigned-agent-contact"
	}).html(contactNo);
	rightCol.append(name).append(role).append(contact);
	container.append(rightCol);

	return wrapper.append(container);
}

function getConversationHistoryWrapper() {
	var wrapper = $('<div>').attr({
		"class" : "conversation-wrapper"
	});
	var header = $('<div>').attr({
		"class" : "conversation-header uppercase"
	}).html("conversation history");
	wrapper.append(header);
	var container = $('<div>').attr({
		"id" : "conv-container",
		"class" : "conversation-parent-container"
	});
	return wrapper.append(container);
}

function paintConversations(conversations,showSM) {

	for (var i = 0; i < conversations.length; i++) {
		var user = conversations[i];
		var data = user[0];
		var conContainer = $('<div>').attr({
			"class" : "conversation-container clearfix"
		});
		if (i % 2 == 1) {
			conContainer.addClass("conversation-container-even");
		}
		var topRow = $('<div>').attr({
			"class" : "conv-top-row clearfix"
		});

		var col1 = $('<div>').attr({
			
		});

		if (data.createdUser.imgUrl != undefined &&  data.createdUser.imgUrl != "") {
			
			col1.addClass("conv-prof-image float-left");
			col1.attr({
				"style" : "background-image:url('" + data.createdUser.imgUrl
						+ "')"
			});
		}
		else
		{
			col1.addClass("conv-prof-image-default float-left");
			col1.text(getInitialsFromFullName(data.createdUser.userName));
		}

		var col2 = $('<div>').attr({
			"class" : "float-left"
		});

		var profName = $('<div>').attr({
			"class" : "con-prof-name semi-bold"
		}).html(data.createdUser.userName);

		var messageTime = $('<div>').attr({
			"class" : "con-message-timestamp"
		}).html(data.createdDate);

		col2.append(profName).append(messageTime);

		var col3 = $('<div>').attr({
			"class" : "float-right",
			"id" : data.id
		});

		topRow.append(col1).append(col2);

		var otherUserBinded = data.otherUsers;
		for (k in otherUserBinded) {
			if (otherUserBinded[k].roleName == "Loan Advisor"
					|| otherUserBinded[k].roleName == "CUSTOMER" ||  otherUserBinded[k].roleName == "REALTOR" || (showSM==true &&otherUserBinded[k].roleName == "Sales Manager" )) {
				// We wil not show any other user roles in here.

				var userImage = $('<div>').attr({
					
					"id" : otherUserBinded[k].userID
				});

				if (otherUserBinded[k].imgUrl != undefined) {
					userImage.addClass("conv-prof-image float-left");
					userImage.attr({
						"style" : "background-image:url('"
								+ otherUserBinded[k].imgUrl + "')"
					});
				}
				else
				{	
					userImage.addClass("conv-prof-image-default float-left");
					var initialText =getInitialsFromFullName (otherUserBinded[k].userName); 
					userImage.text(initialText);
				}

				if (otherUserBinded[k].userID == data.createdUser.userID)
					userImage.hide();
				col3.append(userImage);
			}
		}
		topRow.append(col3);

		var messageContent = $('<div>').attr({
			"class" : "conv-message"
		}).html(data.message);
		var replies = $('<div>').attr({
			"class" : "reply-cont float-left",
			"onclick" : "setCurrentMessageReplyId('" + data.id + "')"
		}).html("Reply");

		/*
		 * if(data.replies_count != undefined && data.replies_count > 0){
		 * replies.append(" ("+data.replies_count+")"); }
		 */
		conContainer.append(topRow).append(messageContent).append(replies);
		$('#conv-container').append(conContainer);
		if (conversations[i].length > 0) {
			paintChildConversations(1, user,showSM);
		}
	}
}

$(document).on('click', '.reply-cont', function() {
	appendReplyContainer(this);
});

// Function to append reply container
function appendReplyContainer(element) {

	$('.reply-cont-wrapper').remove();

	var parentWidth = $(element).parent().width();
	var parentLeftPadding = $(element).parent().css("padding-left");
	var replyContainerWrapper = $('<div>').attr({
		"class" : "reply-cont-wrapper clearfix"
	}).css({
		"padding-left" : parentLeftPadding
	});
	var textBox = $('<textarea>').attr({
		"class" : "reply-text-box",
		"id" : "replyToParent",
		"placeholder" : "Type your message here. Press enter to send."
	}).css({
		"width" : parentWidth - 40
	})/*.on('keyup', function(e) {
		if (e.which == 13) {
			sendMessage(this);
		}
	})*/;

	
	
	var sendButton = $("<div>").attr({
			"class" : "message-btn float-right",
			"onclick" : "findTextArea(this)"
	}).html("Send Message");
	
	replyContainerWrapper.append(textBox).append(sendButton);
	$(element).parent().after(replyContainerWrapper);
	textBox.focus();
}

function findTextArea(click){
	var element = $(click).parent().find('textarea');
	 sendMessage(element);
}

// Function to be called when a user presses enter after typing a message
function sendMessage(element) {
	$(element).parent().remove();
	var message = "";
	if ($(element).val() != undefined && $(element).val() != "") {
		message = $(element).val().trim();
	}
	console.info("message : " + message);
	var newOtherUsers = new Array();
	$("#" + parentId + " div").each(function(index) {
		var userId = $(this).attr('id');
		if (userId != newfiObject.user.id) {
			newOtherUsers.push(getOtherUserObject(userId));
		}
		// setOnlySelectedUser($(this).attr('id'));
	});
	otherUsers = newOtherUsers;
	setReplyToMessageObject(message);
	// TODO : Writing logic on what happens when a user sends a message
}

function paintChildConversations(level, conversations,showSM) {

	for (var i = 1; i < conversations.length; i++) {
		var data = conversations[i];
		var conContainer = $('<div>').attr({
			"class" : "clearfix conversation-container-child"
		});

		if (i <= 3) {
			conContainer.addClass("conversation-container-l" + i);
		} else {
			conContainer.addClass("conversation-container-l3");
		}

		if (i % 2 == 1) {
			conContainer.addClass("conversation-container-even-child");
		}
		var topRow = $('<div>').attr({
			"class" : "conv-top-row clearfix"
		});

		var col1 = $('<div>').attr({
			//"class" : "conv-prof-image float-left"
		});

		if (data.createdUser.imgUrl != undefined) {
			col1.addClass("conv-prof-image float-left");
			col1.attr({
				"style" : "background-image:url(" + data.createdUser.imgUrl
						+ ")"
			});
		}
		else
		{
			col1.addClass("conv-prof-image-default float-left");
			col1.text(getInitialsFromFullName(data.createdUser.userName));
		}

		var col2 = $('<div>').attr({
			"class" : "float-left"
		});

		var profName = $('<div>').attr({
			"class" : "con-prof-name semi-bold"
		}).html(data.createdUser.userName);

		var messageTime = $('<div>').attr({
			"class" : "con-message-timestamp"
		}).html(data.createdDate);

		col2.append(profName).append(messageTime);

		var col3 = $('<div>').attr({
			"class" : "float-right"
		});

		var otherUserBinded = data.otherUsers;
		for (k in otherUserBinded) {

			if (otherUserBinded[k].roleName == "Loan Advisor"
					|| otherUserBinded[k].roleName == "CUSTOMER" ||  otherUserBinded[k].roleName == "REALTOR"||  (showSM==true && otherUserBinded[k].roleName == "Sales Manager")) {
				// We wil not show any other user roles in here.

				var userImage = $('<div>').attr({					
					"id" : otherUserBinded[k].userID
				});

				if (otherUserBinded[k].imgUrl != undefined) {
					userImage.addClass("conv-prof-image float-left");
					userImage.attr({
						"style" : "background-image:url('"
								+ otherUserBinded[k].imgUrl + "')"
					});
				}
				else
				{
					userImage.addClass("conv-prof-image-default float-left");
					var initialText =getInitialsFromFullName (otherUserBinded[k].userName); 
					userImage.text(initialText);
				}

				if (otherUserBinded[k].userID == data.createdUser.userID)
					userImage.hide();
				col3.append(userImage);
			}

		}

		topRow.append(col1).append(col2).append(col3);

		var messageContent = $('<div>').attr({
			"class" : "conv-message"
		}).html(data.message);
		var replies = $('<div>').attr({
			"class" : "reply-cont",
			"onclick" : "setCurrentMessageReplyId('" + data.id + "')"
		}).html("Reply");

		conContainer.append(topRow).append(messageContent).append(replies);
		$('#conv-container').append(conContainer);
	}
}

// function to paint message recipients
function paintMessageRecipients() {
	$('.message-recipient-icn').remove();
	var index = 0;
	$('.assigned-agent-wrapper').find('.assigned-agent-container').each(
			function() {
				if (!$(this).hasClass('assigned-agent-unselect')) {
					var name = $(this).find('.assigned-agent-cont-rc').find(
							'.assigned-agent-name').html().trim();
					var agentIncluedeInMessage = $('<div>').attr({
						"class" : "message-recipient-icn float-left clearfix",
						"data-agent" : $(this).attr("data-agent"),
						"agentId" : $(this).attr("agentId")
					}).html(name);

					var removePersonIcn = $('<div>').attr({
						"class" : "message-recipient-remove-icn float-right"
					});

					agentIncluedeInMessage.append(removePersonIcn);

					if (index > 0) {
						agentIncluedeInMessage.prepend(", ");
					}
					$('#message-recipient-container').append(
							agentIncluedeInMessage);
					index++;

				}
			});
}

// click event for adding or remove a person from coversation using the person
// container
$(document).on('click', '.assigned-agent-container', function() {
	// var agent = $(this).attr("data-agent");
	if ($(this).hasClass('assigned-agent-unselect')) {
		$(this).removeClass('assigned-agent-unselect');
		addOtherUserObject($(this).attr("agentId"));
	} else {
		$(this).addClass('assigned-agent-unselect');
		removeOtherUserObject($(this).attr("agentId"));
	}
	paintMessageRecipients();
	// $('.message-recipient-icn[data-agent="'+agent+'"]').remove();
});

// Click event on remove person from conversation using remove icon
$(document).on(
		'click',
		'.message-recipient-remove-icn',
		function() {
			var agent = $(this).parent().attr("data-agent");
			var agentId = $(this).parent().attr("agentId");
			// $(this).parent().remove();
			$('.assigned-agent-container[agentId="' + agentId + '"]').addClass(
					'assigned-agent-unselect');
			removeOtherUserObject($(this).parent().attr("agentId"));
			paintMessageRecipients();
		});