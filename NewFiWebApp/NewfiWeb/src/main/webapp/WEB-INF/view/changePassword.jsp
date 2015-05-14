<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>newfi</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/styles-common.css" rel="stylesheet">
<link href="resources/css/customer-engagement.css" rel="stylesheet">
<link href="resources/css/customer-application.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">


</head>
<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="login-container container">
				<div class="container-row row clearfix">
					<div class="reg-display-title">Set new password</div>
					<div class="reg-display-title-subtxt">You have successfully verified your email. Please enter your new password below.</div>
					<div class="login-form-wrapper">
						<form id="changePwdForm" name="changePwdForm" action="#" method="POST">
						   
							<div class="change-input-reset-password login-input-pwd" id="email-container">
					        <input type="password" class="reg-input" placeholder="Password" id="password">	
					        <div class="err-msg hide"></div>
					        </div>
					        <div class="change-input-reset-password login-input-pwd" id="email-container-cp">
					        <input type="password" class="reg-input" placeholder="Confirm" id="confirmpassword">	
							<div class="err-msg hide"></div>
				            </div>
							<div class="forget-pass-btn-wrapper clearfix">
                                 <div class="cancel-btn float-left" onclick="window.location='./'">Cancel</div>
					             <div class="reset-password float-right" onclick="$('#changePwdForm').submit();">Reset Password</div>
				            </div>											
						</form>
					</div>
				</div>
			</div>
		</div>
	<script src="resources/js/jquery-2.1.3.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/script.js"></script>
	<script src="resources/js/profile.js"></script>
	<script src="resources/js/validation.js"></script>
	<script src="resources/js/historySupport.js"></script>
	<script src="resources/js/include/jquery-maskMoney.js"></script>
	
</body>
<script>
globalBinder();
$('#changePwdForm').submit(function(event){	
	event.preventDefault();
	var changePasswordData = new Object();
	changePasswordData.newPassword = $('#password').val();
	changePasswordData.userId=currentUser.userId;
	var dateVar = new Date();
	var timezone = dateVar.getTimezoneOffset();
	changePasswordData.emailID = currentUser.emailID+ ":"+timezone;
	console.log("Create user button clicked. User : "
					+ JSON.stringify(changePasswordData));
	if( $('#password').val()=="" || $('#confirmpassword').val()==""){
		$('#password').next('.err-msg').html(passwordFieldEmptyErrorMessage).show();
		$('#confirmpassword').next('.err-msg').html(passwordFieldEmptyErrorMessage).show();
		$('#email-container').addClass('ce-err-input').show();
		$('#email-container-cp').addClass('ce-err-input').show();
		
		return false;
	}else{
		$('#password').next('.err-msg').html(passwordFieldEmptyErrorMessage).hide();
		$('#confirmpassword').next('.err-msg').html(passwordFieldEmptyErrorMessage).hide();
		$('#email-container').removeClass('ce-err-input');
		$('#email-container-cp').removeClass('ce-err-input');
		
	}

	var password=$('#password').val();
	var confirmPassword=$('#confirmpassword').val();
	var firstName=currentUser.firstName;
	var lastName=currentUser.lastName;
	var isSuccess=validatePassword(password,confirmPassword,firstName,lastName,"email-container");
	showOverlay();
	if(isSuccess){
		$.ajax({
	        url: "rest/userprofile/password",
	        type: "POST",  
	        cache:false,
	        data: {
	                 "changePasswordData": JSON.stringify(changePasswordData)
	        },
	        datatype: "json",
	        success: function(data) {            
	        	hideOverlay();          
	            window.location.href = data;            
	        },
	        error: function(data) {           
	            showErrorToastMessage("error while creating user");
	            hideOverlay();
	        }
	    });
	}
	
	hideOverlay();
	});
	
function paintForgetPasswordResponse(data){
	if(data!=null){		
        $('#overlay-loader').hide();
	    window.location.href = data;
		showToastMessage(data.resultObject);
	}else{
		$("#pwd").next('.err-msg').html(data.error.message).show();
		$(".reg-input-reset-password").addClass('err-input').focus();		
	}
}
var currentUser={};
$(document).ready(function() {

	currentUser.emailID="${userVO.emailId}";
    currentUser.firstName="${userVO.firstName}";
    currentUser.lastName="${userVO.lastName}";
    currentUser.userId= "${userVO.id}";

});
</script>

</html>