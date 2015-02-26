/*
*Contains JavaScript functions for agent dashboard pages
*/
var isAgentTypeDashboard;

function adjustAgentDashboardOnResize() {
	if(window.innerWidth <= 1200 && window.innerWidth >= 768){
		var leftPanelWidth = $('.left-panel').width();
		var centerPanelWidth = $(window).width() - (leftPanelWidth) - 15;
		$('.rp-agent-dashboard').width(centerPanelWidth);
	}
	adjustCustomerNameWidth();
}


function adjustCustomerNameWidth(){
	var cusNameColWidth = $('.leads-container-tc1').width();
	var statusIcnWidth = $('.onl-status-icn').width();
	var cusImgWidth = $('.cus-img-icn').width();
	var cusNameWidth = cusNameColWidth - (statusIcnWidth + cusImgWidth) - 5;
	$('.cus-name').outerWidth(cusNameWidth);
}

var imageBaseUrl = "resources/images/";

var custData = {
		"num_found" : "",
		"customers" : [
			{
				"name" : "Jessica Cockrell",
				"prof_image" : imageBaseUrl+"photos1.png",
				"time" : "01/09/2015 11:58AM",
				"phone_no" : "8645547862",
				"purpose" : "Purchase TBD",
				"processor" : "Johny Tester",
				"credit_score" : "731",
				"alert_count" : 3,
				"alerts" : [],
				"notes" : []
	 		},
	 		{
				"name" : "Chad Mcintosh",
				"prof_image" : imageBaseUrl+"photos2.png",
				"time" : "01/08/2015 12:48AM",
				"phone_no" : "3865618993",
				"purpose" : "Purchase",
				"processor" : "Jennifer Standifer",
				"credit_score" : "842",
				"alert_count" : 1,
				"alerts" : [],
				"notes" : []
	 		},
	 		{
				"name" : "Kimberly Burkeen",
				"prof_image" : imageBaseUrl+"photos3.png",
				"time" : "01/09/2015 11:18AM",
				"phone_no" : "9043309807",
				"purpose" : "Purchase",
				"processor" : "Johny Tester",
				"credit_score" : "731",
				"alert_count" : 0,
				"alerts" : [],
				"notes" : []
	 		},
	 		{
				"name" : "Sherry Andreu",
				"prof_image" : imageBaseUrl+"photos1.png",
				"time" : "01/09/2015 11:18AM",
				"phone_no" : "9043309807",
				"purpose" : "Purchase",
				"processor" : "Jennifer Standifer",
				"credit_score" : "699",
				"alert_count" : 3,
				"alerts" : [
				            "Salaried-W-2 forms - Pending",
				            "Payroll stubs for the past 30 days (showing YTD earings) - Pending",
				            "Payroll stub for the past 30 days," 
				            ],
				"notes" : [
				           {
				        	"name" : "Jane Doe",
				        	"time" : "Yesterday at 3:30pm",
				        	"message" : "I have attached the files requested\n\n" +
                	 		"* Salaried-W-2 for the most recent 2 years\n" +
                	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n"
				           },
				           {
				        	"name" : "Jane Doe",
				        	"time" : "Yesterday at 3:30pm",
				        	"message" : "I have attached the files requested\n\n" +
                	 		"* Salaried-W-2 for the most recent 2 years\n" +
                	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n"
				           },
				           {
				        	"name" : "Jane Doe",
				        	"time" : "Yesterday at 3:30pm",
				        	"message" : "I have attached the files requested\n\n" +
                	 		"* Salaried-W-2 for the most recent 2 years\n" +
                	 		"* Payroll stubs for the past 30 days(showing YTD earnings)\n"
				           }
				           ]
	 		},
	 		{
				"name" : "Jessica Cockrell",
				"prof_image" : imageBaseUrl+"photos1.png",
				"time" : "01/09/2015 11:18AM",
				"phone_no" : "8645547862",
				"purpose" : "Purchase TBD",
				"processor" : "Johny Tester",
				"credit_score" : "731",
				"alert_count" : 2,
				"alerts" : [],
				"notes" : []
	 		},
	 		{
				"name" : "Jessica Cockrell",
				"prof_image" : imageBaseUrl+"photos1.png",
				"time" : "01/09/2015 11:18AM",
				"phone_no" : "8645547862",
				"purpose" : "Purchase TBD",
				"processor" : "Johny Tester",
				"credit_score" : "731",
				"alert_count" : 0,
				"alerts" : [],
				"notes" : []
	 		},
	 		{
				"name" : "Jessica Cockrell",
				"prof_image" : imageBaseUrl+"photos1.png",
				"time" : "01/09/2015 11:18AM",
				"phone_no" : "8645547862",
				"purpose" : "Purchase TBD",
				"processor" : "Johny Tester",
				"credit_score" : "731",
				"alert_count" : 1,
				"alerts" : [],
				"notes" : []
	 		},
	 		{
				"name" : "Jessica Cockrell",
				"prof_image" : imageBaseUrl+"photos1.png",
				"time" : "01/09/2015 11:18AM",
				"phone_no" : "8645547862",
				"purpose" : "Purchase TBD",
				"processor" : "Johny Tester",
				"credit_score" : "731",
				"alert_count" : 2,
				"alerts" : [],
				"notes" : []
	 		}
		]
	};

function paintAgentDashboard(){
	ajaxRequest("./agentDashboard.do", "GET", "HTML", {}, paintAgentDashboardCallBack);
}

function paintAgentDashboardCallBack(data){
	$('#main-body-wrapper').html(data);
	paintAgentDashboardRightPanel();
	adjustAgentDashboardOnResize();
}

function paintAgentDashboardRightPanel(){
	var header = $('<div>').attr({
		"class" : "agent-customer-list-header clearfix"
	});
	var leftCon = $('<div>').attr({
		"class" : "agent-customer-list-header-txt float-left uppercase"
	}).html("customer list");
	
	var rightCon = $('<div>').attr({
		"class" : "agent-customer-list-header-rc float-right clearfix"
	});
	
	var searchCon = $('<div>').attr({
		"class" : "clearfix float-left"
	});
	
	var searchIcon = $('<div>').attr({
		"class" : "search-icn float-left"
	}).on('click',function(){
		$(this).hide();
		$(this).parent().find('.search-input').show().focus();
	});
	
	var searchInputBox = $('<input>').attr({
		"class" : "search-input float-left hide"
	}).on('keyup',function(e){
		if(e.which == 13){
			$(this).hide();
			$(this).parent().find('.search-icn').show();
		}
	}).on('blur',function(){
		$(this).hide();
		$(this).parent().find('.search-icn').show();
	});
	
	searchCon.append(searchIcon).append(searchInputBox);
	
	var filterText = $('<div>').attr({
		"class" : "filter-text float-left"
	}).html("View :");
	
	var filter = $('<div>').attr({
		"class" : "filer-dropdown float-left"
	}).html("New");
	
	rightCon.append(searchCon).append(filterText).append(filter);
	
	header.append(leftCon).append(rightCon);
	$('#agent-dashboard-container').append(header);
	appendAgentDashboardContainer();
	appendCustomers("leads-container",custData.customers);
}

function appendAgentDashboardContainer(){
	var container = $('<div>').attr({
		"class" : "customer-list-contianer"
	});
	
	var leadsWrapper = $('<div>').attr({
		"class" : "cutomer-leads-wrapper"
	});
	
	var leadsHeader = $('<div>').attr({
		"class" : "agent-wrapper-header agent-wrapper-header-active"
	}).html("Leads");
	
	var leadsContainer = $('<div>').attr({
		"id" : "leads-container",
		"class" : "agent-wrapper-container"
	});
	
	leadsWrapper.append(leadsHeader).append(leadsContainer);
	
	var inactiveWrapper = $('<div>').attr({
		"class" : "cutomer-inactive-wrapper"
	});
	
	var inactiveHeader = $('<div>').attr({
		"class" : "agent-wrapper-header"
	}).html("Inactive");
	
	var inactiveContainer = $('<div>').attr({
		"id" : "inactive-container",
		"class" : "agent-wrapper-container"
	});
	
	inactiveWrapper.append(inactiveHeader).append(inactiveContainer);
	
	container.append(leadsWrapper).append(inactiveWrapper);
	$('#agent-dashboard-container').append(container);
}

/**
 * 
 * @param elementId of container to which customer list is to appended
 * @param customers
 */
function appendCustomers(elementId,customers){
	
	$('#'+elementId).html("");
	appendCustomerTableHeader(elementId);
	
	for(var i=0; i<customers.length; i++){
		
		var customer = customers[i]; 
		
		var row = $('<div>').attr({
			"class" : "leads-container-tr leads-container-row clearfix"
		});
		
		if(i%2==0){
			row.addClass('leads-container-row-odd');
		}
		
		if(i == customers.length-1){
			row.addClass('leads-container-row-last');
		}
		
		var col1 = $('<div>').attr({
			"class" : "leads-container-tc1 float-left clearfix"
		});
		
		var onlineStatus = $('<div>').attr({
			"class" : "onl-status-icn float-left"
		});
		
		var profImage = $('<div>').attr({
			"class" : "cus-img-icn float-left",
			"style" :  "background-image:url("+customer.prof_image+")"
		});
		
		var cusName = $('<div>').attr({
			"class" : "cus-name float-left"
		}).html(customer.name);
		
		col1.append(onlineStatus).append(profImage).append(cusName);
		
		var phone_num = formatPhoneNumberToUsFormat(customer.phone_no);
		
		var col2 = $('<div>').attr({
			"class" : "leads-container-tc2 float-left"
		}).html(phone_num);
		
		var col3 = $('<div>').attr({
			"class" : "leads-container-tc3 float-left"
		}).html(customer.purpose);
		
		var col4 = $('<div>').attr({
			"class" : "leads-container-tc4 float-left"
		}).html(customer.processor);
		
		var col5 = $('<div>').attr({
			"class" : "leads-container-tc5 float-left"
		}).html(customer.credit_score);
		
		var col6 = $('<div>').attr({
			"class" : "leads-container-tc6 float-left"
		}).html(customer.time);
		
		var col7 = $('<div>').attr({
			"class" : "leads-container-tc7 alert-col float-left"
		}).bind('click',{"customer":customer},function(event){
			event.stopImmediatePropagation();
			appendCustomerDetailContianer($(this).parent(),event.data.customer);
		});
		
		if(parseInt(customer.alert_count) > 0){
			var alerts = $('<div>').attr({
				"class" : "alerts-count"
			}).html(customer.alert_count);
			col7.append(alerts);
		}
		
		row.append(col1).append(col2).append(col3).append(col4).append(col5).append(col6).append(col7);
		
		$('#'+elementId).append(row);
	}
	
}



//Function to append customer table header in agent dashboard 
function appendCustomerTableHeader(elementId){
	var tableHeader = $('<div>').attr({
		"class" : "leads-container-th leads-container-row clearfix"
	});
	
	var thCol1 = $('<div>').attr({
		"class" : "leads-container-tc1 float-left"
	}).html("Customer Name");
	
	var thCol2 = $('<div>').attr({
		"class" : "leads-container-tc2 float-left"
	}).html("Phone<br/>No.");
	
	var thCol3 = $('<div>').attr({
		"class" : "leads-container-tc3 float-left"
	}).html("Purpose");
	
	var thCol4 = $('<div>').attr({
		"class" : "leads-container-tc4 float-left"
	}).html("Processor");
	
	var thCol5 = $('<div>').attr({
		"class" : "leads-container-tc5 float-left"
	}).html("Credit<br/>Score");
	
	var thCol6 = $('<div>').attr({
		"class" : "leads-container-tc6 float-left"
	}).html("Date/<br/>Time");
	
	var thCol7 = $('<div>').attr({
		"class" : "leads-container-tc7 float-left"
	}).html("Alert");
	
	tableHeader.append(thCol1).append(thCol2).append(thCol3).append(thCol4).append(thCol4).append(thCol5).append(thCol6).append(thCol7);
	
	$('#'+elementId).append(tableHeader);
}

/**
 * function to append details related to customer in agent dashboard
 *  
 * @param element container to which the it is to be appended
 */
function appendCustomerDetailContianer(element,customer){
		
	if($(element).next().hasClass("cust-detail-wrapper")){
		$('#cust-detail-wrapper').remove();
		$('.leads-container-tr').removeClass('leads-container-tr-sel');
		return;
	}
	$('#cust-detail-wrapper').remove();
	$('.leads-container-tr').removeClass('leads-container-tr-sel');
	$(element).addClass('leads-container-tr-sel');
	var wrapper = $('<div>').attr({
		"id" : "cust-detail-wrapper",
		"class" : "cust-detail-wrapper clearfix"
	});
	$(element).after(wrapper);
	appendRecentAlertContainer(customer.alerts);
	appendSchedulerContainer();
	appendRecentNotesContainer(customer.notes);
	appendTakeNoteContainer();
}

function appendRecentAlertContainer(alerts){
	var wrapper = $('<div>').attr({
		"class" : "cust-detail-lw float-left"
	});
	var container = $('<div>').attr({
		"class" : "cust-detail-container"
	});
	var header = $('<div>').attr({
		"class" : "cust-detail-header"
	}).html("recent alerts");
	
	if(alerts != undefined){
		header.append(" - " + alerts.length + " NEW ALERTS");
	}else{
		header.append(" - " + 0 + " NEW ALERTS");
	}
	
	container.append(header);
	
	var recentAlertWrapper = $('<div>').attr({
		"class" : "recent-alerts-wrapper clearfix"
	});

	if(alerts != undefined){
		for(var i=0; i<alerts.length; i++){
			var alertData = alerts[i];
			var alertContainer = $('<div>').attr({
				"class" : "alert-conatiner clearfix"
			});
			var alertLeftCol = $('<div>').attr({
				"class" : "alert-container-lc float-left"
			});
			var alertTxt = $('<div>').attr({
				"class" : "alert-txt"
			}).html(alertData);
			var alertBtnRow = $('<div>').attr({
				"class" : "alert-btn-row clearfix"
			});
			
			var dismissBtn = $('<div>').attr({
				"class" : "alert-btn float-left"
			}).html("Dismiss");
			
			var snoozeBtn = $('<div>').attr({
				"class" : "alert-btn float-left"
			}).html("Snooze");
			
			alertBtnRow.append(dismissBtn).append(snoozeBtn);
			
			alertLeftCol.append(alertTxt).append(alertBtnRow);
			var editBtn = $('<div>').attr({
				"class" : "alert-edit-btn float-right"
			}).html("Edit");
			alertContainer.append(alertLeftCol).append(editBtn);
			recentAlertWrapper.append(alertContainer);
		}
	}
	
	container.append(recentAlertWrapper);
	
	wrapper.append(container);
	$('#cust-detail-wrapper').append(wrapper);
}

function appendSchedulerContainer(){	
	var wrapper = $('<div>').attr({
		"class" : "cust-detail-rw float-left"
	});
	var container = $('<div>').attr({
		"class" : "cust-detail-container"
	});
	var header = $('<div>').attr({
		"class" : "cust-detail-header"
	}).html("scheduler");
	
	var dtPickerRow = $('<div>').attr({
		"class" : "dt-picker-row clearfix"
	});
	
	var dtText = $('<div>').attr({
		"class" : "dt-picker-text float-left"
	}).html("Date/Time");
	
	var datePicker = $('<div>').attr({
		"class" : "date-picker-cont float-left"
	});
	
	var datePickerBox = $('<input>').attr({
		"class" : "date-picker-input",
		"placeholder" : "MM/DD/YYYY"
	});
	
	datePicker.append(datePickerBox);
	
	var timerPicker = $('<div>').attr({
		"class" : "time-picker-cont float-left"
	}); 
	
	var timerPickerBox = $('<input>').attr({
		"class" : "time-picker-input",
		"placeholder" : "11:58AM"
	});
	timerPicker.append(timerPickerBox);
	dtPickerRow.append(dtText).append(datePicker).append(timerPicker);
	
	
	var messageBox = $('<textarea>').attr({
		"class" : "scheduled-msg-textbox",
		"placeholder" : "Type your message here. When done click submit"
	});
	
	var buttonRow = $('<div>').attr({
		"class" : "msg-btn-row clearfix"
	});

	var col1 = $('<div>').attr({
		"class" : "msg-btn-col1 float-left clearfix"
	});
	
	var col1Btn = $('<div>').attr({
		"class" : "msg-btn-submit float-right"
	}).html("Submit");
	col1.append(col1Btn);
	
	var col2 = $('<div>').attr({
		"class" : "msg-btn-col2 float-left"
	}).html("or");
	
	var col3 = $('<div>').attr({
		"class" : "msg-btn-col3 float-left clearfix"
	});
	
	var col3Btn = $('<div>').attr({
		"class" : "msg-btn-clear float-left"
	}).html("Clear");
	col3.append(col3Btn);
	
	buttonRow.append(col1).append(col2).append(col3);
	
	container.append(header).append(dtPickerRow).append(messageBox).append(buttonRow);
	
	
	wrapper.append(container);
	$('#cust-detail-wrapper').append(wrapper);
}

function appendRecentNotesContainer(notes){
	var wrapper = $('<div>').attr({
		"class" : "cust-detail-lw float-left"
	});
	var container = $('<div>').attr({
		"class" : "cust-detail-container"
	});
	var header = $('<div>').attr({
		"class" : "cust-detail-header"
	}).html("recent notes");
	
	if(notes != undefined){
		header.append(" - " + notes.length + " NEW NOTES");
	}else{
		header.append(" - " + 0 + " NEW NOTES");
	}
	container.append(header);
	
	var recentNoteWrapper = $('<div>').attr({
		"class" : "recent-notes-wrapper clearfix"
	});

	if(notes != undefined){
		for(var i=0; i<notes.length; i++){
			var noteData = notes[i];
			var noteContainer = $('<div>').attr({
				"class" : "note-conatiner"
			});
			
			var cusName = $('<div>').attr({
				"class" : "note-cus-name"
			}).html(noteData.name);
			
			var time = $('<div>').attr({
				"class" : "note-time"
			}).html(noteData.time);
			
			var message = $('<div>').attr({
				"class" : "note-message"
			}).html(noteData.message);
			
			noteContainer.append(cusName).append(time).append(message);
			
			recentNoteWrapper.append(noteContainer);
		}
	}
	container.append(recentNoteWrapper);
	wrapper.append(container);
	$('#cust-detail-wrapper').append(wrapper);
}

function appendTakeNoteContainer(){
	var wrapper = $('<div>').attr({
		"class" : "cust-detail-rw float-left"
	});
	var container = $('<div>').attr({
		"class" : "cust-detail-container"
	});
	var header = $('<div>').attr({
		"class" : "cust-detail-header"
	}).html("take note");
	container.append(header);

	var messageBox = $('<textarea>').attr({
		"class" : "note-msg-textbox",
		"placeholder" : "Type your message here. When done click save."
	});
	
	container.append(messageBox);
	
	var buttonRow = $('<div>').attr({
		"class" : "msg-btn-row clearfix"
	});

	var col1 = $('<div>').attr({
		"class" : "msg-btn-col1 float-left clearfix"
	});
	
	var col1Btn = $('<div>').attr({
		"class" : "msg-btn-submit float-right"
	}).html("Save");
	col1.append(col1Btn);
	
	var col2 = $('<div>').attr({
		"class" : "msg-btn-col2 float-left"
	}).html("or");
	
	var col3 = $('<div>').attr({
		"class" : "msg-btn-col3 float-left clearfix"
	});
	
	var col3Btn = $('<div>').attr({
		"class" : "msg-btn-clear float-left"
	}).html("Clear");
	col3.append(col3Btn);
	
	buttonRow.append(col1).append(col2).append(col3);
	container.append(buttonRow);
	
	wrapper.append(container);
	$('#cust-detail-wrapper').append(wrapper);
}

/*
 * Functions for agent loan page views
 */


$(document).on('click','.lp-t2-agent-item',function(){
	changeAgentSecondaryLeftPanel(this.id);
});

function paintMyLoansView(){
	ajaxRequest("./agentLoanPage.do", "GET", "HTML", {}, paintMyLoansViewCallBack);
}

function paintMyLoansViewCallBack(data){
	$('#main-body-wrapper').html(data);
	changeAgentSecondaryLeftPanel('lp-step1');
	adjustCenterPanelWidth();
	
}

function paintAgentLoanPage(){
	appendCustomerDetailHeader();
	appendCustomerLoanDetails();
	appendAddTeamMemberWrapper();
	appendNewfiTeamWrapper();
}

//function called when secondary left panel is changed in agent view loan progress pages
function changeAgentSecondaryLeftPanel(elementId){
	$('.lp-t2-item').removeClass('t2-active');
	$('.lp-t2-item .arrow-right').remove();
	$('#'+elementId).addClass('t2-active');
	var rightArrow = $('<div>').attr({
		"class" : "arrow-right"
	});
	$('#'+elementId).append(rightArrow);
	$('#center-panel-cont').html('');
	
	//Check the id and paint the corresponding right panel
	if(elementId == "lp-step1"){
	}
	else if(elementId == "lp-step2"){
		paintAgentLoanPage();
	}
	else if(elementId == "lp-step3"){
	}
	else if(elementId == "lp-step4"){
		paintAgentNeedsListPage();
	}
	else if(elementId == "lp-step5"){
	}
	

}

//Function to append customer's detail in loan manager view
function appendCustomerDetailHeader(){
	var container = $('<div>').attr({
		"class" : "cus-prof-detail-wrapper clearfix"
	});
	
	var cusProfLeftContainer = $('<div>').attr({
		"class" : "cus-prof-detail-lc float-left clearfix"
	});
	
	var cusProfPic = $('<div>').attr({
		"class" : "cus-prof-pic float-left"
	});
	
	var cusProfText = $('<div>').attr({
		"class" : "cus-prof-pic-txt float-left"
	});
	
	var cusName = $('<div>').attr({
		"class" : "cus-prof-name-txt"
	}).html("Jane Doe");
	
	var cusRole = $('<div>').attr({
		"class" : "cus-prof-role-txt"
	}).html("Home Buyer");
	
	var cusContact = $('<div>').attr({
		"class" : "cus-prof-role-txt"
	}).html("+1 (888) 555-1875");
	
	cusProfText.append(cusName).append(cusRole).append(cusContact);
 	cusProfLeftContainer.append(cusProfPic).append(cusProfText);
 	
	var cusProfRightContainer = $('<div>').attr({
		"class" : "cus-prof-detail-rc float-right"
	});

	var rowNewfiId = $('<div>').attr({
		"class" : "cus-detail-rc-row clearfix"
	});
	var rowNewfiIdTitle = $('<div>').attr({
		"class" : "cus-detail-rc-title float-left"
	}).html("Newfi ID");
	
	var rowNewfiIdValue = $('<div>').attr({
		"class" : "cus-detail-rc-value float-left"
	}).html("654321");
	rowNewfiId.append(rowNewfiIdTitle).append(rowNewfiIdValue);
	cusProfRightContainer.append(rowNewfiId);
	
	var rowInitiatedOn = $('<div>').attr({
		"class" : "cus-detail-rc-row clearfix"
	});
	var rowInitiatedOnTitle = $('<div>').attr({
		"class" : "cus-detail-rc-title float-left"
	}).html("Inititated On");
	
	var rowInitiatedOnValue = $('<div>').attr({
		"class" : "cus-detail-rc-value float-left"
	}).html("01/09/2015 11:58AM");
	rowInitiatedOn.append(rowInitiatedOnTitle).append(rowInitiatedOnValue);
	cusProfRightContainer.append(rowInitiatedOn);
	
	var rowLastActiveOn = $('<div>').attr({
		"class" : "cus-detail-rc-row clearfix"
	});
	var rowLastActiveOnTitle = $('<div>').attr({
		"class" : "cus-detail-rc-title float-left"
	}).html("Last Active On");
	
	var rowLastActiveOnValue = $('<div>').attr({
		"class" : "cus-detail-rc-value float-left"
	}).html("01/24/2015 11:58AM");
	rowLastActiveOn.append(rowLastActiveOnTitle).append(rowLastActiveOnValue);
	cusProfRightContainer.append(rowLastActiveOn);
	
	container.append(cusProfLeftContainer).append(cusProfRightContainer);
	$('#center-panel-cont').append(container);
}


//Function to append loan details of customer
function appendCustomerLoanDetails(){
	var wrapper = $('<div>').attr({
		"class" : "av-loan-details-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "av-loan-details-header"
	}).html("Loan Details");
	
	var container = $('<div>').attr({
		"id" : "av-loan-details-container",
		"class" : "av-loan-details-container"
	});
	
	wrapper.append(header).append(container);
	
	$('#center-panel-cont').append(wrapper);
	
	//Append loan detail rows
	appendLoanDetailsRow("File Email", "654321@loan.newfi.com");
	appendLoanDetailsRow("Single Sign On", "6872604");
	appendLoanDetailsRow("Customer", "Edit");
	appendLoanDetailsRow("Loan Amount", "$ 100,000.00");
	appendLoanDetailsRow("Lock Rate Details", "4.75 %");
	appendLoanDetailsRow("Lock Expiration Date", "02/21/2015");
	appendLoanDetailsRow("Loan Progress", "Setup");
	appendLoanDetailsRow("Credit", "TU-646/EQ-686/EX-685");
	appendLoanDetailsRow("Credit Decision", "Pass");
	appendLoanDetailsRow("Loan Purpose", "Purchase TBD");
}


//Function to append loan details row
function appendLoanDetailsRow(label,value){
	var row = $('<div>').attr({
		"class" : "av-loan-details-row clearfix"
	});
	
	var leftCol = $('<div>').attr({
		"class" : "av-loan-details-row-lc float-left"
	}).html(label);
	var rightCol = $('<div>').attr({
		"class" : "av-loan-details-row-rc float-left"
	}).html(value);
	row.append(leftCol).append(rightCol);
	
	$('#av-loan-details-container').append(row);
}


//Function to append add team member wrapper in loan managaer view
function appendAddTeamMemberWrapper(){
	var wrapper = $('<div>').attr({
		"class" : "add-team-mem-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "add-team-mem-header clearfix"
	}).html("Add Team Member");
	
	var rightHeaderIcon = $('<div>').attr({
		"class" : "header-down-icn float-right"
	});
	
	header.append(rightHeaderIcon);
	
	
	var container = $('<div>').attr({
		"class" : "add-team-mem-container clearfix"
	});

	var userTypeCont = $('<div>').attr({
		"class" : "add-member-input-cont float-left clearfix"
	}).html("User Type");
	
	var userTypeSel  =  $('<div>').attr({
		"class" : "add-member-sel float-right"
	}).html("Realtor");
	
	userTypeCont.append(userTypeSel);
	
	var userNameCont = $('<div>').attr({
		"class" : "add-member-input-cont float-left clearfix"
	}).html("User Name");
	
	var userNameSel  =  $('<div>').attr({
		"class" : "add-member-sel float-right"
	});
	
	userNameCont.append(userNameSel);
	
	container.append(userTypeCont).append(userNameCont);
	
	wrapper.append(header).append(container);
	$('#center-panel-cont').append(wrapper);
}


function appendNewfiTeamWrapper(){
	var wrapper = $('<div>').attr({
		"class" : "newfi-team-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "newfi-team-header"
	}).html("Newfi Team");
	
	var container = $('<div>').attr({
		"class" : "newfi-team-container"
	});
	
	var tableHeader = getTeamListTableHeader();
	container.append(tableHeader);
	
	wrapper.append(header).append(container);
	$('#center-panel-cont').append(wrapper);
}

function getTeamListTableHeader(){
	var tableHeaderRow = $('<div>').attr({
		"class" : "newfi-team-list-th clearfix"
	});
	
	var thCol1 = $('<div>').attr({
		"class" : "newfi-team-list-th-col1 float-left"
	}).html("User Name");
	
	var thCol2 = $('<div>').attr({
		"class" : "newfi-team-list-th-col2 float-left"
	}).html("User Type");
	
	var thCol3 = $('<div>').attr({
		"class" : "newfi-team-list-th-col3 float-left"
	}).html("Email");
	
	return tableHeaderRow.append(thCol1).append(thCol2).append(thCol3);
}


/*
 * Functions for agent view needs list page
 */



var docData = {
		"income" : [
		            {
		            	"isChecked" : "true",
		            	"title" : "Salaried-W-2 form",
		            	"desc" : "Salaried-W-2 form"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Other income",
		            	"desc" : "Other income"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Interest/Dividend",
		            	"desc" : "Interest/Dividend"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Alimony/Child Support",
		            	"desc" : "Alimony/Child Support"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Alimony/Child Support",
		            	"desc" : "Alimony/Child Support"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Rental Income",
		            	"desc" : "Rental Income"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Social security or disability income",
		            	"desc" : "Social security or disability income"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Award letter",
		            	"desc" : "Award letter"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "YTD P/L statement",
		            	"desc" : "YTD P/L statement"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Federal Tax returns",
		            	"desc" : "Federal Tax returns"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Payroll Stubs",
		            	"desc" : "Payroll Stubs"
		            }
		            ],
        "property" : [
		            {
		            	"isChecked" : "true",
		            	"title" : "Condos and PDU's",
		            	"desc" : "Condos and PDU's"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Purchase contract from home",
		            	"desc" : "Purchase contract from home"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Home Owner's insurance",
		            	"desc" : "Home Owner's insurance"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Property tax bill",
		            	"desc" : "Property tax bill"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Home Owner's hazard insurance policy",
		            	"desc" : "Home Owner's hazard insurance policy"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Cancelled check rents",
		            	"desc" : "Cancelled check rents"
		            }
		            ],
        "asset" : [
		            {
		            	"isChecked" : "true",
		            	"title" : "Cancelled statement from close of home",
		            	"desc" : "Cancelled statement from close of home"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Purchase agreement",
		            	"desc" : "Purchase agreement"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Letter from provider of home",
		            	"desc" : "Letter from provider of home"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Retirement fund or stock portfolio",
		            	"desc" : "Retirement fund or stock portfolio"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Bank statement",
		            	"desc" : "Bank statement"
		            }
		            ],
	    "other" : [
		            {
		            	"isChecked" : "true",
		            	"title" : "Cancelled statement from close of home",
		            	"desc" : "Cancelled statement from close of home"
		            },
		            {
		            	"isChecked" : "true",
		            	"title" : "Purchase agreement",
		            	"desc" : ""
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Letter from provider of home",
		            	"desc" : "Letter from provider of home"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Retirement fund or stock portfolio",
		            	"desc" : "Retirement fund or stock portfolio"
		            },
		            {
		            	"isChecked" : "false",
		            	"title" : "Bank statement",
		            	"desc" : "Bank statement"
		            }
		            ]
};

function paintAgentNeedsListPage(){
	appendDocumentToolTip();
	appendCustomerDetailHeader();
	appendInitialNeedsListWrapper();
	paintUploadNeededItemsPage();
}

function appendInitialNeedsListWrapper(){
	var wrapper = $('<div>').attr({
		"id" : "initial-needs-wrapper",
		"class" : "initial-needs-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "initial-needs-header"
	}).html("initial need list");
	
	var container = $('<div>').attr({
		"class" : "initial-needs-container clearfix"
	});
	
	var incomeDocContainer = getNeedsListDocumentContainer("income",docData.income).addClass('float-left');
	
	var propertyDocContainer = getNeedsListDocumentContainer("property",docData.property).addClass('float-right');
	
	var assetDocContainer = getNeedsListDocumentContainer("asset",docData.asset).addClass('float-right');
	
	var otherDocContainer = getNeedsListDocumentContainer("other",docData.other).addClass('float-left');
	
	container.append(incomeDocContainer).append(propertyDocContainer).append(assetDocContainer).append(otherDocContainer);
	
	wrapper.append(header).append(container);
	$('#center-panel-cont').append(wrapper);
	
	appendAddNeedsContainer();
	
	//append save button
	var savebtnWrapper = $('<div>').attr({
		"class" : "need-list-save-btn-wrapper"
	});
	
	var savebtn = $('<div>').attr({
		"class" : "need-list-save-btn"
	}).html("Save Needs");
	
	savebtnWrapper.append(savebtn);
	
	$('#center-panel-cont').append(savebtnWrapper);
}


function getNeedsListDocumentContainer(docType,documents){
	var docWrapper = $('<div>').attr({
		"class" : "initial-list-doc-wrapper",
		"data-doc-type" : docType
	});
	
	var header = $('<div>').attr({
		"class" : "initial-list-doc-header"
	}).html(docType + " Documents"); 
	
	var container = $('<div>').attr({
		"class" : "initial-list-doc-container"
	});
	
	for(var i=0; i<documents.length; i++){
		var row = getNeededDocumentRow(documents[i]);
		container.append(row);
	}
	
	return docWrapper.append(header).append(container);
}

function getNeededDocumentRow(document){
	var row = $('<div>').attr({
		"class" : "initial-list-doc-row clearfix"
	});
	
	var checkBox = $('<div>').attr({
		"class" : "doc-checkbox float-left"
	});
	if(document.isChecked == "true"){
		checkBox.addClass('doc-checked');
	}else{
		checkBox.addClass('doc-unchecked');
	}
	
	var docTitle = $('<div>').attr({
		"class" : "doc-title float-left",
		"title" : document.desc
	}).html(document.title);
	
	/*docTitle.bind('mouseenter',{"desc" : document.desc},function(event){
		var leftOffset = $(this).offset().left;
		var topOffset = $(this).offset().top;
		var desc = event.data.desc;
		showDocumentToolTip(desc, topOffset, leftOffset);
	});
	
	docTitle.bind('mouseleave',function(event){
		hideDocumentToolTip();
	});*/
	
	return row.append(checkBox).append(docTitle);
}


//Click event for document checkbox
$(document).on('click','.doc-checkbox',function(){
	if($(this).hasClass('doc-checked')){
		$(this).removeClass('doc-checked');
		$(this).addClass('doc-unchecked');
	}else{
		$(this).removeClass('doc-unchecked');
		$(this).addClass('doc-checked');
	}
});


var docTypes = ["income","property","asset","other"];

function appendAddNeedsContainer(){
	var wrapper = $('<div>').attr({
		"class" : "add-needs-wrapper"
	});
	
	var header = $('<div>').attr({
		"class" : "initial-list-doc-header"
	}).html("Add New Needs");
	
	var container = $('<div>').attr({
		"class" : "add-needs-container"
	}); 
	
	var row1 = $('<div>').attr({
		"class" : "add-needs-input-row clearfix float-left"
	});
	var row1Label = $('<div>').attr({
		"class" : "add-needs-input-label float-left"
	}).html("Document Type"); 
	var row1Select = $('<select>').attr({
		"id" : "need_doc_type",
		"class" : "add-needs-input-edit float-left capitalize"
	});
	
	//Append options
	for(var i=0; i<docTypes.length;i++){
		var option = $('<option>').attr({
			"class" : "add-needs-input-edit"
		}).html(docTypes[i]);
		row1Select.append(option);
	}
	
	row1.append(row1Label).append(row1Select);
	
	var row2 = $('<div>').attr({
		"class" : "add-needs-input-row clearfix float-left"
	});
	var row2Label = $('<div>').attr({
		"class" : "add-needs-input-label float-left"
	}).html("Document Title");
	var row2Input = $('<input>').attr({
		"id" : "need_doc_title",
		"class" : "add-needs-input-edit float-left"
	});
	
	row2.append(row2Label).append(row2Input);
	
	var row3 = $('<div>').attr({
		"class" : "add-needs-input-row add-needs-input-last-row  clearfix"
	});
	var row3Label = $('<div>').attr({
		"class" : "add-needs-input-label float-left"
	}).html("Document Desc");
	var row3Input = $('<input>').attr({
		"id" : "need_doc_desc",
		"class" : "add-needs-input-edit float-left"
	});
	
	row3.append(row3Label).append(row3Input);
	container.append(row1).append(row2).append(row3);
	
	var addNeedsBtn = $('<div>').attr({
		"class" : "add-needs-btn"
	}).html("Add Needs"); 
	
	container.append(addNeedsBtn);
	
	wrapper.append(header).append(container);
	$('#initial-needs-wrapper').append(wrapper);
}


//Click event for add needs button
$(document).on('click','.add-needs-btn',function(){
	var docType = $('#need_doc_type :selected').text();
	var docTitle = $('#need_doc_title').val();
	var docDesc = $('#need_doc_desc').val();
	
	if(docTitle == "") return;
	if(docDesc === "") return;
	
	var document = {
		"isChecked" : "true",
		"title" : docTitle,
		"desc" : docDesc
	};
	var newNeedRow = getNeededDocumentRow(document);
	
	$('.initial-list-doc-wrapper[data-doc-type="'+docType+'"]').find('.initial-list-doc-container').append(newNeedRow);
	clearAddNeedForm();
});

function clearAddNeedForm(){
	$('#need_doc_title').val('');
	$('#need_doc_desc').val('');	
}


function appendDocumentToolTip(){
	var toolTipContainer = $('<div>').attr({
		"id" : "doc-tool-tip",
		"class" : "tool-tip-container hide"
	});
	$('#center-panel-cont').append(toolTipContainer);
}

function showDocumentToolTip(desc,topOffset,leftOffset){
	$('#doc-tool-tip').html(desc);
	$('#doc-tool-tip').css({
		"left" : leftOffset + 20,
		"top" : topOffset
	});
	$('#doc-tool-tip').show();
}

function hideDocumentToolTip(){
	$('#doc-tool-tip').hide();
}