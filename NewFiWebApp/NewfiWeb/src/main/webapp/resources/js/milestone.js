function paintCustomerLoanProgressPage(){
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

function getCustomerMilestoneLoanProgressHeaderBar(){
	var container = $('<div>').attr({
		"class" : "milestone-header-bar clearfix"
	});
	
	var step1 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE",1,"My Profile");
	var step2 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE",2,"Application Status");
	var step3 = getCustomerMilestoneLoanProgressHeaderBarStep("COMPLETE",3,"Credit Status");
	var step4 = getCustomerMilestoneLoanProgressHeaderBarStep("IN_PROGESS",4,"Team");
	var step5 = getCustomerMilestoneLoanProgressHeaderBarStep("NOT_STARTED",5,"Initial Needs List");
	
	return container.append(step1).append(step2).append(step3).append(step4).append(step5);
}

function getCustomerMilestoneLoanProgressHeaderBarStep(status,step,heading){
	var col = $('<div>').attr({
		"class" : "milestone-header-bar-step float-left"
	});
	
	if(status == "COMPLETE"){
		col.addClass('m-step-complete');
	}else if(status == "IN_PROGESS"){
		col.addClass('m-step-in-progress');
	}else if(status == "NOT_STARTED"){
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


function paintCustomerLoanProgressContainer(){
	
	var heading = $('<div>').attr({
		"class" : "loan-progress-heading"
	}).html("Know Where You Are");
	
	var loanProgressCont = $('<div>').attr({
		"id" : "loan-progress-milestone-wrapper",
		"class" : "loan-progress-milestone-wrapper"
	});
	
	$('#cust-loan-progress').append(heading).append(loanProgressCont);
	
	
	//Append milestones
	appendMilestoneMyProfile();
	appendMilestoneApplicationStatus();
	appendMilestoneCreditStatus();
	appendMilestoneTeam();
	appendMilestoneInitialNeedList();
	appendMilestoneDisclosures();
	appendMilestoneApplicationFee();
	appendMilestoneLockRates();
	appendMilestoneAppraisal();
	appendMilestoneUnderwriting();
	appendMilestoneClosingStatus();
	
	//Paint customer profile container
	paintMilestoneCustomerProfileDetails();
	
	adjustBorderMilestoneContainer();
}

function appendMilestoneMyProfile(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-in-progress"
	});
	
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	}); 
	
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("My Profile");
	
	header.append(headerTxt);
	
	var progressBarCont = $('<div>').attr({
		"class" : "clearfix"
	});
	
	var progressBarTxt = $('<div>').attr({
		"class" : "miestone-progress-bar-txt float-right"
	}).html("50 %");
	
	var progressBar = $('<div>').attr({
		"class" : "miestone-progress-bar float-right clearfix"
	});
	
	var progress = 5;
	for(var i=0; i<10; i++){
		var progressGrid = $('<div>').attr({
			"class" : "miestone-progress-grid float-left"
		});
		if(progress == 0){
			progressGrid.addClass('miestone-progress-grid-incomplete');
		}else{
			progress--;
		}
		progressBar.append(progressGrid);
	}
	
	progressBarCont.append(progressBar).append(progressBarTxt);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Account");
	
	var txtRow2 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Online Appication");
	
	var txtRow3 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Photo");
	
	var txtRow4 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("SMS Texting Preferences");
	
	wrapper.append(leftBorder).append(header).append(progressBarCont).append(txtRow1).append(txtRow2).append(txtRow3).append(txtRow4);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneApplicationStatus(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-in-progress"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Application Status");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-application-status float-left"
	}); 
	
	header.append(headerTxt).append(headerIcn);
	
	var progressBarCont = $('<div>').attr({
		"class" : "clearfix"
	});
	
	var progressBarTxt = $('<div>').attr({
		"class" : "miestone-progress-bar-txt float-left"
	}).html("40 %");
	
	var progressBar = $('<div>').attr({
		"class" : "miestone-progress-bar float-left clearfix"
	});
	var progress = 4;
	for(var i=0; i<10; i++){
		var progressGrid = $('<div>').attr({
			"class" : "miestone-progress-grid float-left"
		});
		if(progress == 0){
			progressGrid.addClass('miestone-progress-grid-incomplete');
		}else{
			progress--;
		}
		progressBar.append(progressGrid);
	}
	progressBarCont.append(progressBar).append(progressBarTxt);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Connect Your Online Application");
	
	var txtRow2 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Contact Your Loan Manager");
	
	var txtRow3 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Connect Secu Your Online Application");
	
	wrapper.append(rightBorder).append(header).append(progressBarCont).append(txtRow1).append(txtRow2).append(txtRow3);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

//Function to append milestone credit status
function appendMilestoneCreditStatus(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-complete"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	}); 
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Credit Status");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-credit-status float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("EQ - 686 ~ TU - 694 ~ Ex - 714");
	
	wrapper.append(leftBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneTeam(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Team");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-team float-left"
	}); 
	
	header.append(headerTxt).append(headerIcn);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Add members to Team");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneInitialNeedList(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-in-progress"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	}); 
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Initial Needs List");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-initial-need-list float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("16 of 18 Complete");
	
	wrapper.append(leftBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneDisclosures(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Disclosures");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-disclosure float-left"
	}); 
	
	header.append(rightBorder).append(headerTxt).append(headerIcn);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Click here to Sign");
	
	wrapper.append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneApplicationFee(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	}); 
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Application Fee");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-application-fee float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Click here to Pay Application Fee");
	
	wrapper.append(leftBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneLockRates(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Lock Rate");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-lock-rate float-left"
	}); 
	
	header.append(headerTxt).append(headerIcn);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Click here to Request Rate Lock");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}


function appendMilestoneAppraisal(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var leftBorder = $('<div>').attr({
		"class" : "milestone-lc-border"
	}); 
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Appraisal");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-appraisal float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Not Ordered");
	
	wrapper.append(leftBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}


function appendMilestoneUnderwriting(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-rc m-not-started"
	});
	var rightBorder = $('<div>').attr({
		"class" : "milestone-rc-border"
	});
	var header = $('<div>').attr({
		"class" : "milestone-rc-header clearfix"
	});

	var headerTxt = $('<div>').attr({
		"class" : "milestone-rc-header-txt float-left"
	}).html("Underwriting");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-rc-header-icn ms-icn-underwriting float-left"
	}); 
	
	header.append(headerTxt).append(headerIcn);
	
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-rc-text"
	}).html("Pending");
	
	wrapper.append(rightBorder).append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function appendMilestoneClosingStatus(){
	var wrapper = $('<div>').attr({
		"class" : "milestone-lc m-not-started"
	});
	var header = $('<div>').attr({
		"class" : "milestone-lc-header clearfix"
	});
	
	var headerTxt = $('<div>').attr({
		"class" : "milestone-lc-header-txt float-right"
	}).html("Closing Status");
	
	var headerIcn = $('<div>').attr({
		"class" : "milestone-lc-header-icn ms-icn-closing-status float-right"
	});
	header.append(headerTxt).append(headerIcn);
	var txtRow1 = $('<div>').attr({
		"class" : "milestone-lc-text"
	}).html("Download the First Payment Coupon");
	
	wrapper.append(header).append(txtRow1);
	
	$('#loan-progress-milestone-wrapper').append(wrapper);
}

function adjustBorderMilestoneContainer(){
	$('.milestone-lc:first-child').find('.milestone-lc-border').css({
		"top" : 0,
		"height" : $(this).height() + 32
	});
}


function paintMilestoneCustomerProfileDetails(){
	var container = $('<div>').attr({
		"class" : "ms-cust-prof-container clearfix"
	});
	
	var custImg = $('<div>').attr({
		"class" : "ms-cust-prof-img float-left"
	});
	
	var custTxtContainer = $('<div>').attr({
		"class" : "ms-cust-prof-txt-cont float-left"
	});
	
	var name = $('<div>').attr({
		"class" : "ms-cust-prof-txt-name"
	}).html("jane doe");
	
	var role = $('<div>').attr({
		"class" : "ms-cust-prof-txt-role"
	}).html("Home Buyer");
	
	var contact = $('<div>').attr({
		"class" : "ms-cust-prof-txt-contact"
	}).html("+1 (888) 555-1875");
	
	custTxtContainer.append(name).append(role).append(contact);
	container.append(custImg).append(custTxtContainer);
	$('#loan-progress-milestone-wrapper').append(container);
}