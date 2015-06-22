var updateErrorMessage="Something went wrong while update . Please try again later";
var updateSuccessMessage="Succesfully updated";
var errorMessage="Something went wrong";
var message = "Invalid Entry";
var ProfileImageSuccessMessage="Image uploaded succesfully";

//Messages in agenView.js
var messageSaved="Message saved succesfully";//line 955
var cannotSelectOldMessage="Cannot select old value";//line 1393
var expiryDateExpected="Expiry Date Accepted.";//line 1422
var problemSavingExpiryDate="Problem saving Expiry Date.";//line 1424
var selectUserType="Please select a user type";//line 1525
var messageToDeleteUser="Are you sure you want to delete the user?";//line 1999
var firstNameEmptyMessage="First name cannot be empty";//line 2653
var lastNameEmptyMessage="Last name cannot be empty";
var emailEmptyMessage="Email ID cannot be empty";
var companyNameEmptyMessage="Company name cannot be empty";//line 2758
var emailAddressEmptyMessage="Email address cannot be empty";
var phoneEmptyMessage="Phone number cannot be empty";
var userRemovalErrorMessage="An error occurred, kindly contact admin.";//line 3632
var userRemoveSuccessMessage="User removed successfully";
var userExsistErrorMessage="User already exists on the loan team.";//line 3730
var userAddedToLoanTeamSuccessMessage="User added to loan team.";//line 3743
var emailAldreadyExsist="Email already exists";//line 3892
var invalidMessage="Invalid Message";
var invalidDate="Invalid Date";
var enterMessage="Please enter a message";
var errorMessageForZipcode="ZipCode cannot be empty";


//Messages in adminModule.js
var userCreationSuccessMessage="User created successfully";
var customerDeleteSuccessMessage="Customer deleted successfully";
var realtorDeleteSuccessMessage="Realtor deleted successfully";
var loanMangerDeleteSuccessMessage="Loan manger deleted successfully";
var uploadCsvErrorMessage="Problem while uploading csv.Please try again later";
var invalidEmailErrorMessage="Email ID is not valid";

//Messages in profile.js
var passwordFieldEmptyErrorMessage="should not be empty";
var passwordDonotMatchErrorMessage="Passwords do not match";
var passwordlengthErrorMessage="Password length should be at least 8 digits";
var invalidPassword="Password can not contain first name or last name";
var passwordRegexErrorMessage="Password should have at least 1 uppercase and 1 lowercase character";
var priAndSecEmailValidation="Primary and secondary email should not be same.";
var carrierSelectmessage="Please select a carrier";
var copiedToClipBoard="copied to clipboard";
var phoneFieldEmptyMessage="Phone field cannot be empty";
var invalidPhoneNumberMessage="Phone number not valid";
var invalidZipCodeMessage="Zip Code not valid";
var zipCodeEmptyMessage="Zipcode can not be empty";
var passwordEmptyMessage="Password field cannot be empty";

//Messages in customer-application.js
var yesyNoErrorMessage="Please answer all questions";
var stateErrorMessage="Please select your state";
//var gonernamentQuestionErrorMessage="Please answer all questions";
var selectQuestionErrorMessage="Please select any one that applies";
var ageErrorMessage="You must be at least 18 years of age.";
var applicationFormnotEditable="Application form not editable";
var applicationNotSubmitted="Your application could not be submitted";
var errorInCreateLoan="error inside createLoan ";
var W2EmplayeeMandatoryErrorMesssage="W2 Employment Details are Mandatory";
var maxIncomeNeeded="Maximum 2 income needed";
var selectAnyOne="Please select an income type";
var ssnLengthErrorMessage="Please enter a valid ssn of 9-digit";
var phoneNumberLegthErrorMessage="Please enter a valid phone number of 10-digit";
var selectAccountType="Please select an account type";
var selectAssestErrorMessage='If the assest information are not to be provided.Please select the above checkbox';



var propertyValueErrorMessage="Property value can not be less than requested loan amount ";
var changePasswordErrorMessage="Error while changing your password. Please try again later.";
var messageToEnterValidEmail="Please enter valid email ID";
var feildShouldNotBeZero="The answers of the questions should not be zero.";

//Messages in buyAHome.js
var downpaymentGreaterThanPurchase="Down payment should be less than purchase price.";
var downpaymentThreePerLessThanPurchase="Down payment must be greater than 3% of purchase price.";




//Messages in script.js
var errorInCreateLoan="Error inside createLoan";
var preQualificationLetterSent="Pre-qualification letter is sent.";
var preQualificationLetterSentToEmail="Pre-qualification letter is sent to your email id.";
var preQualificationLetterAlreadySentToEmail = "Pre-qualification letter has already been sent to your email id.";
var fillApplicationPath="Please fill the application path ";
var RateLockRequested="Rate Lock Requested";

//Message in customerEngagement.js
var answerQuestionOne="Please answer the question";
var zipCodeMessage="Please enter a valid 5-digit zipcode";
//NEXNF-516
/*var invalidStateZipCode="Zip code does not belong to any of our approved states";*/
var invalidStateZipCode="Please enter a valid Zip Code in a newfi approved state: CA, OR or WA";
var errorInrefinanceRates="error inside paintRefinanceSeeRates :";
var incorrectEmailID="Incorrect Email ID";



//Messages in common.js
var tutorialStatusMessage="Error while updating tutorial status";

//Messages in customerSpouseDetails.js
var max2Needed="Maximum 2 income needed";

//Messages in jcrop.js
var uploadFailed="File upload failed. Please try again";
var photoUploadErrorMessage="You must upload an image file with one of the following extensions:";

//Messages in managerNeedList.js
var savesuccessfull="Save Successful";
var invalidDocumentTitle="Invalid document title";
var invalidDocumentDescription="Invalid document description";
var invalidDocumentType="Invalid document type";
var needAlreadyExists="Need already exists";

//Messages in messageDashBoard.js
var enterSomeText="Please enter some text!";

//Messages in mileStone.js
var masterTable="Master Tables Not Populated";
var selectAnOption="Please Select an Option";
var addValue="Add a value";
var noLoanAdvisorAdded="No Loan Advisor is added on the loan yet";

//Messages in notification.js
var notificationDismisses="Notification Dismissed";
var notificationSuccess="Success";
var notificationSceduled="Notification Scheduled";

//Message in uploadFile.js
var completeYourLoanProfile="Please complete your Loan Profile";
//var overlayMessage="Hang in there for just a quick minute,we want to give you the best options available";
var overlayMessage="newfi is working on it";
var overlayWaitMessage="This can take a minute,<br/> we are uploading your documents to our secure storage folder.";

var noSutableProductFoundMessage="Sorry, We could not find suitable products for you!";
var noProductMessage="We were unable to match you with the right program based on the information you provided.But donot worry, your newfi Loan Advisor will contact you shortly to review your options.";
//var noProductMessageInLockRatePage="<div class='contactInfoText'>We were unable to match you with the right program based on the information you provided. <br/>But donot worry, if you call us at 888-316-3934 someone from the newfi team will review your options.";
var noProductMessageInLockRatePage="<div class='contactInfoText'>We were unable to match you with the right program based on the information you provided.But donot worry, your newfi Loan Advisor will contact you shortly to review your options.";
var emailIDErrorMessageFromServer="We are sorry, this email address already has a newfi account.To login <a href='javascript:goToLogin()' style='color: #2F6BF7'>click here</a>";
function getNoProductMessageInLockRatePage(){
	var errorText="<div class='contactInfoText'>We were unable to match you with the right program based on the information you provided. <br/>But don't worry, your newfi Loan Advisor will contact you shortly to review your options.";
	if(typeof(newfiObject)==='undefined'){
	    errorText="<div class='contactInfoText'>We were unable to match you with the right program based on the information you provided. <br/>But don't worry, you can call us during normal business hours at 888-316-3934 or complete </br>the form below and someone from the newfi team will contact you to review your options.";
	}
	return errorText;
}