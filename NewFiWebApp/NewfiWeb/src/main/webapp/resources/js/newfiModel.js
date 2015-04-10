var newfiObject=null;
var currentHistoryUrl = '';
var historyCallback = false;
var refreshSupport = true;

PNEnum = {
	PROFILE : "myProfile",
	TEAM : "myTeam",
	MY_LOAN : "myLoan",
	ALERT:"myAlerts"
};


SNEnum = {
	1 : "myTeam",
	2 : "my-application",
	3 : "lock-my-rate",
	4 : "upload-my-needs",
	5 : "my-loan-progress"

};



AgentViewPNEnum = {
	WORK_ON_LOAN : "work",
	LOAN : "loan",
	MY_ARCHIVES : "archive",
	MY_IMP_ALERTS:"alert"
};



AgentViewSNEnum = {
	0 : "team",
	1 : "application",
	2 : "detail",
	3:"lock-rate",
	4:"needs",
	5:"progress"
};

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
function createNewfiUser(){
	var userObject = new Object();
	userObject.defaultLoanId = selectedUserDetail.loanID;
	userObject.displayName=selectedUserDetail.firstName+ " "+selectedUserDetail.lastName;
	userObject.emailId=selectedUserDetail.emailId;
	userObject.firstName=selectedUserDetail.firstName;
	userObject.id=selectedUserDetail.userID;
	userObject.lastName=selectedUserDetail.lastName;
	userObject.photoImageUrl=selectedUserDetail.photoUrl;
	return userObject;
	
}