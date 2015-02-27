var currentHistoryUrl = '';
var historyCallback = false;
var refreshSupport = false;

PNEnum = {
	PROFILE : 0,
	TEAM : 1,
	LOAN : 2
}

SNEnum = {
	GETTINGTOKNOWNEWFI : 0,
	COMPLETEAPPLICATION : 1,
	LOCKRATE : 2,
	UPLOAD : 3,
	LOANPROGRESS : 4
}

function globalBinder(){
	
	$(document).on("click", ".lp-item" , function(element) {
		saveState(jQuery.data( element, "enum" ).pnName,null,null);
		return true;
    });
}

function globalSNBinder(){
	$(document).on("click", ".lp-t2-item" , function(element) {
		saveState(PNEnum.LOAN,jQuery.data( element, "enum" ).snName,null);
		return true;
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
 *            any data that needs to be passed to the function ex: loanId in case of loan manager view
 */
function saveState(primaryNav, secondaryNav, url) {

	var hashUrl = "";
	if (url != null) {
		hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav;
	} else {
		hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav + "&" + url;
	}

	history.pushState(getRandomID(), null, "?q=" + encodeURIComponent(hashUrl));
}

function retrieveState() {
	if (!refreshSupport) {
		console.log('refresh not supported');
		return;
	}
	var decodedlocation = decodeURIComponent(window.location.search
			.substring(1));
	var newLocation = decodedlocation.split("q=");
	newLocation = newLocation && newLocation.length == 2 ? newLocation[1]
			: null;
	// parse the parameters out from the url key
	var params = {};
	if (newLocation) {
		var entries = newLocation.split("&");
		for (var i = 0; i < entries.length; i++) {
			var bits = entries[i].split("=");
			if (bits.length == 2) {
				params[bits[0]] = bits[1];
			}
		}
	} else {

		return;
	}
	var primary = params.pn;
	var secondary = params.sn;

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

function getRandomID() {
	return (Math.floor(Math.random() * 10000) + Math
			.floor(Math.random() * 10000));
}