/*
 * Functions for customer message dashboard
 */


function adjustRightPanelOnResize() {
	if(window.innerWidth <= 1200 && window.innerWidth >= 768){
		var leftPanelWidth = $('.left-panel').width();
		var centerPanelWidth = $(window).width() - (leftPanelWidth) - 15;
		$('.right-panel-messageDashboard').width(centerPanelWidth);
	}
}



function paintGettingToKnowMessageDashboard(){
	var rightArrow = $('<div>').attr({
		"class" : "lp-right-arrow"
	});
	$('#lp-talk-wrapper').append(rightArrow);
	var msgDashboardWrapper = getMessageDashboardWrapper();
	var conversationHistoryWrapper = getConversationHistoryWrapper();
	$('#conv-main-container').append(msgDashboardWrapper).append(conversationHistoryWrapper);
	$('#conv-container').html('');
	var baseUrl = "resources/images/";
	var conversations = [
	                     {
	                    	 "name" : "Jane Doe",
	                    	 "prof_image" : baseUrl+"cus-icn.png",
	                    	 "time" : "Yesterday at 3:30pm",
	                    	 "message" : "I have attached the files requested\n\n" +
	                    	 		"* Salaried-W-2 for the most recent 2 years\n" +
	                    	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n" +
	                    	 		"* Refinance - Copy of property tax bill\n" +
	                    	 		"* Refinance - Copy of homeowner's hazard insurance policy\n" +
	                    	 		"* Most recent retirement fund or stock portfolio statement\n" +
	                    	 		"* 2 months bank statements (all pages)",
	                    	 "replies_count" : 1,
	                    	 "people_included" :[],
	                    	 "conv_thread" : [
	                    	                  {
	                    	                	  "name" : "Elen Adarna",
	                 	                    	 "prof_image" : baseUrl+"profile_pic_3.png",
	                 	                    	 "time" : "7/30/2009 9:08PM",
	                 	                    	 "message" : "The following item(s) have been requested by Laura Ryan to continue processing your loan.\n" +
	                 	                    	 		"Please login to your control panel at www.myturboloan.com and upload or fax the following supporting documentation as soon as possible:\n\n" +
	                 	                    	 		"* Salaried-W-2 for the most recent 2 years\n" +
	                	                    	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n" +
	                	                    	 		"* Refinance - Copy of property tax bill\n" +
	                	                    	 		"* Refinance - Copy of homeowner's hazard insurance policy\n" +
	                	                    	 		"* Most recent retirement fund or stock portfolio statement\n" +
	                	                    	 		"* 2 months bank statements (all pages)",
	                 	                    	 "replies_count" : 0,
	                 	                    	 "people_included" :[],
	                 	                    	 "conv_thread" : []
	                    	                  }
	                    	                  ]
	                    	
 	                     },
 	                    {
	                    	 "name" : "Renov Leonga",
	                    	 "prof_image" : baseUrl+"profile_pic_2.png",
	                    	 "time" : "1/30/2009 9:08PM",
	                    	 "message" : "Hello, I have been assigned to help you through myTurboLoan progress. You will find my contact" +
	                    	 		"information below. You should expect to hear from me soon." +
	                    	 		"If you have questions about your loan, please don't hesitate to contact me directly.",
	                    	 "replies_count" : 3,
	                    	 "people_included" :[],
	                    	 "conv_thread" : [
	                    	                  {
	                    	                	  "name" : "Elen Adarna",
	                 	                    	 "prof_image" : baseUrl+"profile_pic_3.png",
	                 	                    	 "time" : "7/30/2009 9:08PM",
	                 	                    	 "message" : "I am not sure why you need to contact me for Don Gridley's phone number because it was" +
	                 	                    	 		"provided on the application and is in the contact area but his home phone is (586) 677-7781 and" +
	                 	                    	 		"his cell phone is (248) 797-9822.",
	                 	                    	 "replies_count" : 0,
	                 	                    	 "people_included" :[],
	                 	                    	 "conv_thread" : []
	                    	                  },
	                    	                  {
	                    	                	  "name" : "Elen Adarna",
	                 	                    	 "prof_image" : baseUrl+"profile_pic_3.png",
	                 	                    	 "time" : "7/30/2009 9:08PM",
	                 	                    	"message" : "I am not sure why you need to contact me for Don Gridley's phone number because it was" +
             	                    	 		"provided on the application and is in the contact area but his home phone is (586) 677-7781 and" +
             	                    	 		"his cell phone is (248) 797-9822.",
	                 	                    	 "replies_count" : 0,
	                 	                    	 "people_included" :[],
	                 	                    	 "conv_thread" : []
	                    	                  },
	                    	                  {
	                    	                	  "name" : "Elen Adarna",
	                 	                    	 "prof_image" : baseUrl+"profile_pic_3.png",
	                 	                    	 "time" : "7/30/2009 9:08PM",
	                 	                    	"message" : "I am not sure why you need to contact me for Don Gridley's phone number because it was" +
             	                    	 		"provided on the application and is in the contact area but his home phone is (586) 677-7781 and" +
             	                    	 		"his cell phone is (248) 797-9822.",
	                 	                    	 "replies_count" : 0,
	                 	                    	 "people_included" :[],
	                 	                    	 "conv_thread" : []
	                    	                  }
	                    	                  ]
	                    		 
 	                     },
 	                    {
	                    	 "name" : "Elena Adarna",
	                    	 "prof_image" : baseUrl+"profile_pic_3.png",
	                    	 "time" : "1/30/2009 9:08PM",
	                    	 "message" : "The following item(s) have been requested by Laura Ryan to continue processing your loan.\n" +
                  	 		"Please login to your control panel at www.myturboloan.com and upload or fax the following supporting documentation as soon as possible:\n\n" +
                  	 		"* Salaried-W-2 for the most recent 2 years\n" +
                 	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n" +
                 	 		"* Refinance - Copy of property tax bill\n" +
                 	 		"* Refinance - Copy of homeowner's hazard insurance policy\n" +
                 	 		"* Most recent retirement fund or stock portfolio statement\n" +
                 	 		"* 2 months bank statements (all pages)",
	                    	 "replies_count" : 1,
	                    	 "people_included" :[],
	                    	 "conv_thread" : [
	                    	                  {
	                    	                	  "name" : "Jane Doe",
	                 	                    	 "prof_image" : baseUrl+"cus-icn.png",
	                 	                    	 "time" : "7/30/2009 9:08PM",
	                 	                    	"message" : "I have attached the files requested\n\n" +
	        	                    	 		"* Salaried-W-2 for the most recent 2 years\n" +
	        	                    	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n" +
	        	                    	 		"* Refinance - Copy of property tax bill\n" +
	        	                    	 		"* Refinance - Copy of homeowner's hazard insurance policy\n" +
	        	                    	 		"* Most recent retirement fund or stock portfolio statement\n" +
	        	                    	 		"* 2 months bank statements (all pages)",
	                 	                    	 "replies_count" : 0,
	                 	                    	 "people_included" :[],
	                 	                    	 "conv_thread" : []
	                    	                  }
	                    	                  ]
	                    		 
 	                     }
	                     ];
	paintConversations(conversations);
	adjustRightPanelOnResize();
}

function getMessageDashboardWrapper() {
	var wrapper = $('<div>').attr({
		"class" : "message-wrapper"
	});
	var header = $('<div>').attr({
		"class" : "message-header uppercase"
	}).html("talk to your newfi team");
	wrapper.append(header);
	
	var container = $('<div>').attr({
		"class" : "message-container"
	});
	
	var assignedAgentWrapper = $('<div>').attr({
		"class" : "assigned-agent-wrapper clearfix"
	});
	
	var agent1 = getAssignedAgentContainer("Weno Carasbong", "Real State Agent", "+1 (888) 555-1875");
	var agent2 = getAssignedAgentContainer("Renov Leonga", "Loan Officer", "+1 (888) 555-6755");
	var agent3 = getAssignedAgentContainer("Elen Adarna", "Title Agent", "+1 (888) 555-1987").addClass('assigned-agent-unselect');
	
	assignedAgentWrapper.append(agent1).append(agent2).append(agent3);
	
	container.append(assignedAgentWrapper);
	
	var textContainer = $('<div>').attr({
		"class" : "message-container"
	});
	var textBox = $('<textarea>').attr({
		"class" : "message-cont-textinput",
		"placeholder" : "Type your message here. Recipients are hightlighted with blue circle, click to toggle. When done click send"
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
	container.find('.assigned-agent-container').each(function(){
		if(!$(this).hasClass('assigned-agent-unselect')){
			var name = $(this).find('.assigned-agent-cont-rc').find('.assigned-agent-name').html().trim();
			var agentIncluedeInMessage = $('<div>').attr({
				"class" : "message-recipient-icn float-left clearfix",
				"data-agent" : $(this).attr("data-agent")
			}).html(name);
			
			var removePersonIcn = $('<div>').attr({
				"class" : "message-recipient-remove-icn float-right"
			});
			
			agentIncluedeInMessage.append(removePersonIcn);
			
			if(index > 0){
				agentIncluedeInMessage.prepend(", ");
			} 
			messageRecipients.append(agentIncluedeInMessage);
			index++;
		}
	});
	bottomContainer.append(messageRecipients);
	
	var messageBtn = $('<div>').attr({
		"class" : "message-btn float-right"
	}).html("Send Message");
	
	bottomContainer.append(messageBtn);
	textContainer.append(bottomContainer);
	container.append(textContainer);
	return wrapper.append(container);
}

function getAssignedAgentContainer(agentName, agentRole, contactNo){
	var container = $('<div>').attr({
		"class" : "assigned-agent-container clearfix float-left",
		"data-agent" : agentName
	});
	var leftCol = $('<div>').attr({
		"class" : "assigned-agent-cont-lc float-left"
	});
	var imgCont = $('<div>').attr({
		"class" : "assigned-agent-img"
	});
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
	
	return container;
}


function getConversationHistoryWrapper(){
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

function paintConversations(conversations){
	
	for(var i=0; i<conversations.length; i++){
		var data = conversations[i];
		var conContainer = $('<div>').attr({
			"class" : "conversation-container clearfix"
		});
		if(i%2==1){
			conContainer.addClass("conversation-container-even");
		}
		var topRow = $('<div>').attr({
			"class" : "conv-top-row clearfix"
		});
		
		var col1 = $('<div>').attr({
			"class" : "conv-prof-image float-left",
			"style" :  "background-image:url("+data.prof_image+")"
			
		});
		
		var col2 = $('<div>').attr({
			"class" : "float-left"
		});
		
		var profName = $('<div>').attr({
			"class" : "con-prof-name semi-bold"
		}).html(data.name);
		
		var messageTime = $('<div>').attr({
			"class" : "con-message-timestamp"
		}).html(data.time); 
		
		col2.append(profName).append(messageTime);
		
		var col3 = $('<div>').attr({
			"class" : "float-right"
		});
		
		topRow.append(col1).append(col2).append(col3);
		
		var messageContent = $('<div>').attr({
			"class" : "conv-message"
		}).html(data.message);
		var replies = $('<div>').attr({
			"class" : "reply-cont"
		}).html("Reply");
		if(data.replies_count != undefined && data.replies_count > 0){
			replies.append(" ("+data.replies_count+")");
		}
		conContainer.append(topRow).append(messageContent).append(replies);
		$('#conv-container').append(conContainer);
		if(data.conv_thread.length > 0){
			paintChildConversations(1,data.conv_thread);
		}
	}
}

function paintChildConversations(level,conversations){
	
	for(var i=0; i<conversations.length; i++){
		var data = conversations[i];
		var conContainer = $('<div>').attr({
			"class" : "clearfix conversation-container-child conversation-container-l"+level
		});
		if(i%2==1){
			conContainer.addClass("conversation-container-even-child");
		}
		var topRow = $('<div>').attr({
			"class" : "conv-top-row clearfix"
		});
		
		var col1 = $('<div>').attr({
			"class" : "conv-prof-image float-left",
			"style" :  "background-image:url("+data.prof_image+")"
			
		});
		
		var col2 = $('<div>').attr({
			"class" : "float-left"
		});
		
		var profName = $('<div>').attr({
			"class" : "con-prof-name semi-bold"
		}).html(data.name);
		
		var messageTime = $('<div>').attr({
			"class" : "con-message-timestamp"
		}).html(data.time); 
		
		col2.append(profName).append(messageTime);
		
		var col3 = $('<div>').attr({
			"class" : "float-right"
		});
		
		topRow.append(col1).append(col2).append(col3);
		
		var messageContent = $('<div>').attr({
			"class" : "conv-message"
		}).html(data.message);
		var replies = $('<div>').attr({
			"class" : "reply-cont"
		}).html("Reply");
		if(data.replies_count != undefined && data.replies_count > 0){
			replies.append(" ("+data.replies_count+")");
		}
		conContainer.append(topRow).append(messageContent).append(replies);
		$('#conv-container').append(conContainer);
	}
}


//function to paint message recipients
function paintMessageRecipients(){
	$('.message-recipient-icn').remove();
	var index = 0;
	$('.assigned-agent-wrapper').find('.assigned-agent-container').each(function(){
		if(!$(this).hasClass('assigned-agent-unselect')){
			var name = $(this).find('.assigned-agent-cont-rc').find('.assigned-agent-name').html().trim();
			var agentIncluedeInMessage = $('<div>').attr({
				"class" : "message-recipient-icn float-left clearfix",
				"data-agent" : $(this).attr("data-agent")
			}).html(name);
			
			var removePersonIcn = $('<div>').attr({
				"class" : "message-recipient-remove-icn float-right"
			});
			
			agentIncluedeInMessage.append(removePersonIcn);
			
			if(index > 0){
				agentIncluedeInMessage.prepend(", ");
			} 
			$('#message-recipient-container').append(agentIncluedeInMessage);
			index++;
		}
	});
}

//click event for adding or remove a person from coversation using the person container
$(document).on('click','.assigned-agent-container',function(){
	//var agent = $(this).attr("data-agent");
	if($(this).hasClass('assigned-agent-unselect')){
		$(this).removeClass('assigned-agent-unselect');		
	}else{
		$(this).addClass('assigned-agent-unselect');
	}
	paintMessageRecipients();
	//$('.message-recipient-icn[data-agent="'+agent+'"]').remove();
});


//Click event on remove person from conversation using remove icon
$(document).on('click','.message-recipient-remove-icn',function(){
	var agent = $(this).parent().attr("data-agent");
	//$(this).parent().remove();
	$('.assigned-agent-container[data-agent="'+agent+'"]').addClass('assigned-agent-unselect');
	paintMessageRecipients();
});