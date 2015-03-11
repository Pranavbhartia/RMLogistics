var newfiObject=null;
var currentHistoryUrl = '';
var historyCallback = false;
var refreshSupport = true;

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



AgentViewPNEnum = {
	WORK_ON_LOAN : "work",
	MY_LOANS : "loan",
	MY_ARCHIVES : "archive",
	MY_IMP_ALERTS:"alert"
}



AgentViewSNEnum = {
	0 : "team",
	1 : "application",
	2 : "detail",
	3:"lock-rate",
	4:"needs",
	5:"progress"
}

function initialize(newfi){
	newfiObject = newfi;
	newfiObject.user = JSON.parse(newfi.user);
	if(newfi.internalUserRoleMasters!=undefined){
		newfiObject.internalUserRoleMasters = JSON.parse(newfi.internalUserRoleMasters);	
	}
	
}

function getRandomID() {
	return (Math.floor(Math.random() * 10000) + Math
			.floor(Math.random() * 10000));
}