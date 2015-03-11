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
				saveState('loan', selectedUserDetail.loanID, $.data(this,
						"snEnum").snName);
				return true;
			});
}

function bindDataToPN() {
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
		case "lp-talk-wrapper":
			$.data(this, "enum", {
				pnName : PNEnum.TEAM

			});
			break;
		case "lp-loan-wrapper":
			$.data(this, "enum", {
				pnName : PNEnum.LOAN

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
				pnName : AgentViewPNEnum.MY_LOANS

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
		default:
			break;
		}
	});

}
function bindDataToSN() {

	// Customer view
	$('.lp-t2-wrapper >div').each(function() {

		var id = $(this).attr('id');
		var step = id.substr(id.length - 1, id.length - 2);
		var divLp = $("#lp-step" + step);
		switch (step) {
		case "1":
			$.data(this, "snEnum", {
				snName : SNEnum.GETTINGTOKNOWNEWFI

			});
			break;
		case "2":
			$.data(this, "snEnum", {
				snName : SNEnum.COMPLETEAPPLICATION

			});
			break;
		case "3":
			$.data(this, "snEnum", {
				snName : SNEnum.LOCKRATE

			});
			break;
		case "4":
			$.data(this, "snEnum", {
				snName : SNEnum.UPLOAD

			});
			break;
		case "5":
			$.data(this, "snEnum", {
				snName : SNEnum.LOANPROGRESS

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
	$('.lp-t2-wrapper > div').each(function() {

		var id = $(this).attr('id');
		var step = id.substr(id.length - 1, id.length - 2);
		var divLp = $("#lp-step" + step);
		switch (step) {
		case "0":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[0]

			});
			break;
		case "1":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[1]

			});
			break;
		case "2":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[2]

			});
			break;
		case "3":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[3]

			});
			break;
		case "4":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[4]

			});
			break;
		case "5":
			$.data(this, "snEnum", {
				snName : AgentViewSNEnum[5]

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
function saveState(primaryNav, secondaryNav, url) {

	// console.log("Save state called : "+primaryNav+","+secondaryNav+","+url);
	var hashUrl = "";
	// if (url == null) {
	// hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav;
	// } else {
	// hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav + "&" + url;
	// }

	hashUrl = primaryNav + getUrlHashFunction(secondaryNav, "/")
			+ getUrlHashFunction(url, "/");

	history.pushState(getRandomID(), null, "#" + hashUrl);
}

function getUrlHashFunction(tag, key) {
	return tag == null ? "" : key + tag;
}

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
		var entries = newLocation.split("/");
		if (entries[0])
			params.pn = entries[0];

		if (entries[1])
			params.loanID = entries[1];

		if (entries[2])
			params.sn = entries[2];
	} else {

		return;
	}
	var primary = params.pn;
	var secondary = params.sn;
	var loanID = params.loanID;
	var secondaryId;

	// If Agent/internal user is logged in

	switch (primary) {
	case AgentViewPNEnum['WORK_ON_LOAN']:
		console.log('Profile view');
		break;

	case AgentViewPNEnum['MY_LOANS']:
		console.log('Work on my loans');
		if (secondary) {
			for (i = 0; i < 6; i++) {
				if (secondary == AgentViewSNEnum[i])
					secondaryId = i;
			}
			entryPointForAgentView(loanID, secondaryId);
		} else if (loanID && loanID > 0)
			entryPointForAgentView(loanID, 2);
		else
			paintAgentDashboard();

		break;

	case AgentViewPNEnum['MY_ARCHIVES']:
		console.log('My archives');
		break;

	case AgentViewPNEnum['MY_IMP_ALERTS']:
		console.log('Alerts');
		break;

	default:
		break;
	}

	// If customer is logged in. Skipping this module for now.
	switch (primary) {
	case PNEnum.PROFILE:
		console.log('Profile view');
		break;

	case PNEnum.TEAM:
		console.log('TEAM view');
		break;

	case PNEnum.LOAN:
		console.log('LOAN view');
		switch (secondary) {
		case SNEnum.GETTINGTOKNOWNEWFI:
			console.log('GETTINGTOKNOWNEWFI view');
			break;
		case SNEnum.COMPLETEAPPLICATION:
			console.log('COMPLETEAPPLICATION view');

			break;
		case SNEnum.LOCKRATE:
			console.log('LOCKRATE view');
			break;
		case SNEnum.UPLOAD:
			console.log('UPLOAD view');
			break;
		case SNEnum.LOANPROGRESS:
			console.log('LOANPROGRESS view');
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
	retrieveState();
});