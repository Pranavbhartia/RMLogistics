var newfiObject=null;
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

function initialize(newfi){
	newfiObject = newfi;
	newfiObject.user = JSON.parse(newfi.user);
	if(newfi.internalUserRoleMasters!=undefined){
		newfiObject.internalUserRoleMasters = JSON.parse(newfi.internalUserRoleMasters);	
	}
	
}
function globalBinder(){
	
	$(document).on("click", ".lp-item" , function() {
		//TODO: Back button needs to be implemented
		//saveState($.data( this, "enum" ).pnName,null,null);
		return true;
    });
}

function globalSNBinder(){
	$(document).on("click", ".lp-t2-item" , function() {
		//TODO: Back button needs to be implemented
		//saveState(PNEnum.LOAN,$.data( this, "snEnum" ).snName,null);
		return true;
    });
}

function bindDataToPN(){
	//Assign values to primary navigation
	var divArray = $('.left-panel >div');
	$('.left-panel >div').each(function(){
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
}
function bindDataToSN(){
	
	
	
	$('.lp-t2-wrapper >div').each(function(){
		
		var id = $(this).attr('id');
		var step=id.substr(id.length-1,id.length-2);
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
//	if (url == null) {
//		hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav;
//	} else {
//		hashUrl = 'pn=' + primaryNav + "&sn=" + secondaryNav + "&" + url;
//	}

	hashUrl = 'pn/' + primaryNav + getUrlHashFunction(secondaryNav,"&sn/") +getUrlHashFunction(url,"&url/"); 
	
	history.pushState(getRandomID(), null, "#" + hashUrl);
}

function getUrlHashFunction(tag,key){
	return tag==null?"":key+tag;
}

function retrieveState() {
	if (!refreshSupport) {
		console.log('refresh not supported');
		return;
	}
	var decodedlocation = window.location.search
			.substring(1);
	var newLocation = decodedlocation.split("#");
	newLocation = newLocation && newLocation.length == 2 ? newLocation[1]
			: null;
	// parse the parameters out from the url key
	var params = {};
	if (newLocation) {
		var entries = newLocation.split("&");
		for (var i = 0; i < entries.length; i++) {
			var bits = entries[i].split("/");
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