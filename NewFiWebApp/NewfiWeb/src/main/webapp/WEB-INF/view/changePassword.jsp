<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Reset Password</title>
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
					<div class="reg-display-title">Change Password</div>
					<div class="reg-display-title-subtxt">Lorem Ipsum is also known
						as: Greeked Text, blind text, placeholder text, dummy content,
						filter text, lipsum, and mock-content.</div>
					<div class="login-form-wrapper">
						<form id="changePwdForm" name="changePwdForm" action="#" method="POST">
						   
							<div class="reg-input-reset-password" id="email-container" >
					        <input type="password" class="reg-input" placeholder="Password" id="pwd" >	
					        </div>
					        <div class="reg-input-reset-password" id="email-container">
					        <input type="password" class="reg-input" placeholder="Confirm" id="confirmPwd" >	
							<div class="err-msg hide"></div>
				            </div>
							<div class="reg-btn-wrapper clearfix">
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
	<script src="resources/js/include/jquery-maskMoney.js"></script>
	
</body>
<script>


$('#changePwdForm').submit(function(event){	
	event.preventDefault();
	var changePasswordData = new Object();
	changePasswordData.newPassword = $('#pwd').val();
	changePasswordData.userId=currentUser.userId;
	var dateVar = new Date();
	var timezone = dateVar.getTimezoneOffset();
	changePasswordData.emailID = currentUser.emailID+ ":"+timezone;
	console.log("Create user button clicked. User : "
					+ JSON.stringify(changePasswordData));
	if($('#pwd').val()==""||$('#pwd').val()==null){
		showErrorToastMessage("Password cannot be empty");
			return;		
	}
	if($('#confirmPwd').val()==""||$('#confirmPwd').val()==null){
		showErrorToastMessage("Confirm Password cannot be empty");
			return;		
	}
	if($('#pwd').val() != $('#confirmPwd').val()){
		showErrorToastMessage("Passwords don't match");
			return;		
	}	
	$.ajax({
        url: "/NewfiWeb/rest/userprofile/password",
        type: "POST",       
        data: {
                 "changePasswordData": JSON.stringify(changePasswordData)
        },
        datatype: "json",
        success: function(data) {            
            $('#overlay-loader').hide();            
            window.location.href = data;            
        },
        error: function(data) {           
            showErrorToastMessage("error while creating user");
            $('#overlay-loader').hide();
        }
    });
	
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
	currentUser.userId=${user};
	currentUser.emailID="${emailID}";	
});
</script>

</html>