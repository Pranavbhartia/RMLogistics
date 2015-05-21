<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="resources/images/title-logo.png">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/styles-common.css" rel="stylesheet">
<link href="resources/css/customer-engagement.css" rel="stylesheet">
<link href="resources/css/customer-application.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">

</head>
<script>
var locationURL = window.location.href;
var resendIndex=locationURL.indexOf("?resend");
</script>
<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="login-container container">
				<div class="container-row row clearfix">
				
					<div id="reg-display-title" class="reg-display-title"></div>
					<div id="reg-display-header-text" class="reg-display-title-subtxt"></div>
				
					<div class="login-form-wrapper">
						<form id="loginForm" name="loginForm" action="#" method="POST">
						    <div class="reset-error hide" id="errorMessage"></div>
							<div class="reg-input-reset-password reg-email" id="email-container">
					        <input class="reg-input" placeholder="Email" id="emailID" >	
							<div class="err-msg hide"></div>
				            </div>
							<div class="forget-pass-btn-wrapper clearfix">
                                 <div class="cancel-btn float-left" onclick="window.location='./'">Cancel</div>
					             <div id="btnAction" class="reset-password float-right" onclick="$('#loginForm').submit();">Reset Password</div>
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
	<script src="resources/js/message.js"></script>
	<script src="resources/js/include/jquery-maskMoney.js"></script>
	<script src="resources/js/historySupport.js"></script>
	
</body>
<script>

$(document).ready(function(e){
	globalBinder();
	if("${error}"!="" && "${error}"!=undefined && "${error}"!=null){
        $("#errorMessage").text("${error}");
		$("#errorMessage").show(); 

	}
	
	
	var title = "";
	var buttonText = "";
	var headerText = "";
	if(resendIndex != -1)
	{
		 title="Resend Registration Link";	 
		 headerText="Enter your email ID. We will resend the registration link to that email.";
		 buttonText ="Submit";
		 $('#btnAction').text(buttonText);
	}
	else
	{
		title="Password Reset";
		headerText="Enter the email address that you used to create your account.  We will send you an email with instructions to reset your password.";
	}
	$('#reg-display-title').text(title);
	$('#reg-display-header-text').text(headerText);	
		
});
var emailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]\.[0-9]\.[0-9]\.[0-9]\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]+))$/;

$('#loginForm').submit(function(event){
		  event.preventDefault();
		var user = new Object();
		var ajaxURL = "rest/userprofile/forgetPassword";
		if (resendIndex!=-1)
		{
			ajaxURL = ajaxURL+"?resend=true";
		}else{
			ajaxURL = ajaxURL+"?resend=false";
		}
		user.emailId = $('#emailID').val();
		console.log("Create user button clicked. User : "
						+ JSON.stringify(user));
	if($('#emailID').val()==""||$('#emailID').val()==null){
		$("#emailID").next('.err-msg').html("Email ID cannot be empty").show();
		$(".reg-input-reset-password").addClass('err-input').focus();
			return;
		
	}else{
		$("#emailID").next('.err-msg').hide();
		$(".reg-input-reset-password").removeClass('ce-err-input');
	}
	if (!emailRegex.test(user.emailId)) {
		$("#emailID").next('.err-msg').html("Email ID is not valid").show();
		$(".reg-input-reset-password").addClass('err-input').focus();
		$('#emailID').val('');
		return;
	}else {	
		ajaxRequest(ajaxURL, "POST", "json", JSON.stringify(user),
				  paintForgetPasswordResponse);
	}
	});

function paintForgetPasswordResponse(data){
	if(data.resultObject!=null){		
		$('#emailID').val('');		
		showToastMessage(data.resultObject);
		$("#emailID").next('.err-msg').hide();
		$(".reg-input-reset-password").removeClass('err-input');
	}else{
		$("#emailID").next('.err-msg').html(data.error.message).show();
		$(".reg-input-reset-password").addClass('err-input').focus();
		$('#emailID').val('');
	}
}


</script>
</html>