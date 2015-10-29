/**
 * 
 * Contains methods for history support in Nexera. This module helps in browser
 * navigation support to give a single page app ux
 */

function globalBinder() {

	$(document).on("click", ".lp-item", function() {
		// TODO: Back button needs to be implemented
		
		console.log("Prim nav clicked : " + $.data(this, "enum").pnName);
		saveState($.data(this, "enum").pnName, null, null);
		removeToastMessage();
		return true;
	});
	$(document).on("click", ".admin-module-row", function() {
		// TODO: Back button needs to be implemented
		
		console.log("Prim nav clicked : " + $.data(this, "enum").pnName);
		saveState($.data(this, "enum").pnName, null, null);
		removeToastMessage();
		return true;
	});

}

function globalSNBinder() {
	$(document).on(
			"click",
			".lp-t2-item",
			function() {
				// TODO: Back button needs to be implemented
				console.log("Sec nav clicked : "
						+ $.data(this, "snEnum").snName);
				if (selectedUserDetail)
					saveState($.data(this, "snEnum").pnName,
							selectedUserDetail.loanID,
							$.data(this, "snEnum").snName);
				else
					saveState($.data(this, "snEnum").pnName, $.data(this,
							"snEnum").snName, null);
				removeToastMessage();
				return true;
			});
}

function bindDataToPN() {
	removeToastMessage();
	// Assign values to primary navigation-Customer view
	var divArray = $('.left-panel >div');
	$('.left-panel >div').each(function() {
		var id = $(this).attr('id');
		switch (id) {
		case "lp-customer-profile":
			$.data(this, "enum", {
				pnName : PNEnum.PROFILE

			});
			break;
		case "lp-loan-manager-profile":
			$.data(this, "enum", {
				pnName : PNEnum.PROFILE

			});
			break;
			
		case "lp-talk-wrapper":
			$.data(this, "enum", {
				pnName : PNEnum.TEAM

			});
			break;
		case "lp-loan-wrapper":
			$.data(this, "enum", {
				pnName : PNEnum.MY_LOAN

			});
			break;
		case "lp-alert-wrapper":
			$.data(this, "enum", {
				pnName : PNEnum.ALERT

			});
			break;
		default:
			break;
		}
	});

	// Agent View

	$('.lp-item').each(function() {
		var id = $(this).attr('id');
		switch (id) {
		case "lp-work-on-loan":
			$.data(this, "enum", {
				pnName : AgentViewPNEnum.WORK_ON_LOAN

			});
			break;
		case "lp-my-loans":
			$.data(this, "enum", {
				pnName : AgentViewPNEnum.LOAN

			});
			break;
		case "lp-my-archives":
			$.data(this, "enum", {
				pnName : AgentViewPNEnum.MY_ARCHIVES

			});
			break;
		case "lp-imp-alerts":
			$.data(this, "enum", {
				pnName : AgentViewPNEnum.MY_IMP_ALERTS

			});
			break;
		case "lp-my-lead":
			$.data(this, "enum", {
				pnName : AgentViewPNEnum.MY_LEADS

			});
			break;
		case "lp-quick-quote":
			$.data(this, "enum", {
				pnName : AgentViewPNEnum.QUICK_QUOTE

			});
			break;
		default:
			break;
		}
	});

	$('.admin-module-row').each(function() {
		var id = $(this).attr('id');
		switch (id) {
		case "user-management":
			$.data(this, "enum", {
				pnName : AdminModulePNEnum.USER_MANAGEMENT

			});
			break;
		case "loan-detail":
			$.data(this, "enum", {
				pnName : AdminModulePNEnum.TURN_AROUND_TEMPLATES

			});
			break;
		case "templates":
			$.data(this, "enum", {
				pnName : AdminModulePNEnum.TEMPLATES

			});
			break;
		default:
			break;
		}
	});

	
}



function bindDataToSN() {
	removeToastMessage();
	// Customer view
	$('#cust-sec-nav >div').each(function() {

		var id = $(this).attr('id');
		var step = id.substr(id.length - 1, id.length - 2);
		var divLp = $("#lp-step" + step);
		switch (step) {
		case "1":
			$.data(this, "snEnum", {
				snName : SNEnum[1],
				pnName : PNEnum.MY_LOAN

			});
			break;
		case "2":
			$.data(this, "snEnum", {
				snName : SNEnum[2],
				pnName : PNEnum.MY_LOAN

			});
			break;
		case "3":
			$.data(this, "snEnum", {
				snName : SNEnum[3],
				pnName : PNEnum.MY_LOAN

			});
			break;
		case "4":
			$.data(this, "snEnum", {
				snName : SNEnum[4],
				pnName : PNEnum.MY_LOAN

			});
			break;
		case "5":
			$.data(this, "snEnum", {
				snName : SNEnum[5],
				pnName : PNEnum.MY_LOAN

			});
			break;

		default:
			break;
		}
	});

	// Agent View

	/*
	 * AgentViewSNEnum = { TALK_TO_TEAM : "team", APP_PROGRESS : "application",
	 * LOAN_DETAIL : "detail", LOCK_RATE:"lock-rate", NEEDS_LIST:"needs",
	 * LOAN_PROGRESS:"progress" }
	 */
	$('#agent-sec-nav > div').each(function() {

		var id = $(this).attr('id');
		var step = id.substr(id.length - 1, id.length - 2);
		var divLp = $("#lp-step" + step);
		switch (step) {
		case "0":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[0],
				pnName: AgentViewPNEnum.LOAN

			});
			break;
		case "1":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[1],
				pnName: AgentViewPNEnum.LOAN

			});
			break;
		case "2":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[2],
				pnName: AgentViewPNEnum.LOAN

			});
			break;
		case "3":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[3],
				pnName: AgentViewPNEnum.LOAN

			});
			break;
		case "4":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[4],
				pnName: AgentViewPNEnum.LOAN

			});
			break;
		case "5":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[5],
				pnName: AgentViewPNEnum.LOAN

			});
			break;

		default:
			break;
		}
	});

}

/**
 * This function saves the current state of the view in history.
 * 
 * @param primaryNav =
 *            name of the primaryNav
 * @param secondaryNav =
 *            name of the secondaryNav
 * @param url =
 *            any data that needs to be passed to the function ex: loanId in
 *            case of loan manager view
 */
function saveState(primaryNav, secondaryNav, url,num) {

	// console.log("Save state called : "+primaryNav+","+secondaryNav+","+url);
	var hashUrl = "";
	// if (url == null) {
	// hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav;
	// } else {
	// hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav + "&" + url;
	// }
	if(typeof(num)=="undefined"){
		hashUrl = primaryNav + getUrlHashFunction(secondaryNav, "/")
				+ getUrlHashFunction(url, "/");
		if(!historyCallback){
			history.pushState(getRandomID(), null, "#" + hashUrl);
		
		}
		historyCallback = false;
	}else{
		hashUrl="CE-"+num;
		if(window.location.hash!=hashUrl)
			history.pushState(getRandomID(), null, "#" + hashUrl);
	}
	
	
}

function getUrlHashFunction(tag, key) {
	return tag == null ? "" : key + tag;
}
var hAction;
function retrieveState() {
	if (!refreshSupport) {
		console.log('refresh not supported');
		return;
	}
	var newLocation = window.location.hash.substring(1);
	;

	// parse the parameters out from the url key
	var params = {};
	if (newLocation) {
		if(newLocation.indexOf("CE-")>=0){
			var vals=newLocation.split("-")
			var num=parseInt(vals[1]);
			num=num+1;
            historyPopStage=true;
			changeToState(num);
			return;
		}
		var entries = newLocation.split("/");
		if (entries[0])
			params.pn = entries[0];
		
		if(newfiObject.user.userRole.roleCd=="CUSTOMER"){
			

			if (entries[1])
				params.sn = entries[1];
		}else{
			if (entries[1])
				params.loanID = entries[1];

			if (entries[2])
				params.sn = entries[2];
		}
		
		
	} else {

		return;
	}
	var snAction
	if(params.sn)
		snAction=params.sn.split(":")
	else
		snAction=[];
	var primary = params.pn;
	var secondary = snAction[0];
	var loanID = params.loanID;
	var secondaryId;
	if(snAction[1])
		hAction=snAction[1];
	else
		hAction=undefined;

	// If Agent/internal user is logged in

	switch (primary) {
	
	case PNEnum['MY_LOAN']:
		console.log('Work on my loans');
	    showCustomerLoanPage(newfiObject.user);
	
		break;
	
	case AgentViewPNEnum['WORK_ON_LOAN']:
		console.log('Profile view');
		break;

	case AgentViewPNEnum['LOAN']:
		console.log('Work on loans');
		if (secondary) {
			for (i = 0; i < 6; i++) {
				if (secondary == AgentViewSNEnum[i])
					secondaryId = i;
			}
			entryPointForAgentView(loanID, secondaryId);
		} else if (loanID && loanID > 0)
			entryPointForAgentView(loanID, 2);
		else{
			
			//If it is a page refresh, ignore
			if($('#agent-dashboard-container').length==0){
				paintAgentDashboard('myloans');	
			}
			
		}
			

		break;

	case AgentViewPNEnum['MY_ARCHIVES']:
		console.log('My archives');
	paintAgentDashboard('archivesloans');
		break;

	case AgentViewPNEnum['MY_IMP_ALERTS']:
		console.log('Alerts');
		break;

	case AdminModulePNEnum['USER_MANAGEMENT']:
		console.log('User Management');
	userManagement();
		break;
		
	case AdminModulePNEnum['TURN_AROUND_TEMPLATES']:
		console.log('Turn Around Templates');
	turnAroundTime();
		break;
		
	case AdminModulePNEnum['TEMPLATES']:
		console.log('Templates');
	populateTemplate();
		break;
	case AgentViewPNEnum['QUICK_QUOTE']:
		console.log('quickquote');
	loadQuickQoutePage();
		break;
	case AgentViewPNEnum['MY_LEADS']:
		console.log('my leads');
	if (secondary) {
		paintLeadsQuotePage(loanID);
	} else{
		paintAgentDashboard('myLeads');
	}
		
		break;
	default:
		break;
	}

	// If customer is logged in. Skipping this module for now.
	switch (primary) {
	case PNEnum.PROFILE:
		if(newfiObject.user.userRole.roleCd=="INTERNAL"){
			showLoanManagerProfilePage();
		}else if(userIsRealtor()){
			showLoanManagerProfilePage();
		}else{
			showCustomerProfilePage();	
		}
		
		break;

	case PNEnum.TEAM:
		console.log('TEAM view');
		changeLeftPanel(1);
		break;

	case PNEnum.MY_LOAN:
		console.log('LOAN view');
		switch (secondary) {
		case SNEnum[1]:
			 console.log('GETTINGTOKNOWNEWFI view');
			 changeSecondaryLeftPanel(1);
			 break;
		case SNEnum[2]:
			console.log('COMPLETEAPPLICATION view');
			changeSecondaryLeftPanel(2);
			break;
		case SNEnum[3]:
			console.log('LOCKRATE view');
			changeSecondaryLeftPanel(3);
			break;
		case SNEnum[4]:
			console.log('UPLOAD view');
			changeSecondaryLeftPanel(4);
			break;
		case SNEnum[5]:
			console.log('LOANPROGRESS view');
			changeSecondaryLeftPanel(5);
			break;

		default:
			break;
		}
		break;
	default:
		break;
	}

}

// call the blobal binder methods

globalBinder();
globalSNBinder();
window.addEventListener("hashchange", function() {
	// console.log("Hash changed to", window.location.hash);
	// console.log("History State is : "+window.history.state);
	//retrieveState();
});
