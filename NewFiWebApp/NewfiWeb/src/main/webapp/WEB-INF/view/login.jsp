<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/dropzone.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<script src="resources/js/jquery-2.1.3.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	$('#loginForm').submit(function() {
		var userName = $("#userId").val();
		var password = $("#password").val();
		 var dateVar = new Date();
			var timezone = dateVar.getTimezoneOffset(); 
		 $("#inputEmail").val(userName + ":"+timezone);
		 $("#inputPassword").val(password);
		 
		 return true;
		});
});

	
</script>
</head>
<body>

	<div class="login-body-wrapper">
		<!-- Login Header -->
		<div class="login-header-wrapper">
			<div class="header-container container">
				<div class="header-row row clearfix">
					<div class="header-logo float-left"></div>
					<div class="header-btns-wrapper float-right clearfix">
						<div class="float-left hdr-contact-no">1-888-415-1620</div>
						<div class="float-left btn-settings hdr-btn-item"></div>
						<div class="float-left btn-logout hdr-btn-item">
							<span class="logout-btn-txt">Logout</span>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Main Container -->
		<div class="login-container container">
			<div class="container-row row clearfix">
				<div class="display-title">A New way to Finance your home</div>
				<div class="display-title-subtxt">Lorem Ipsum is also known
					as: Greeked Text, blind text, placeholder text, dummy content,
					filter text, lipsum, and mock-content.</div>
				<div class="login-form-wrapper">
					<form id="loginForm" name="loginForm"
						action="j_spring_security_check" method="POST">
						<div class="form-logo"></div>
						<div class="login-input-cont">
							<input type="text" class="login-input login-input-username"
								placeholder="Username" id="userId"> <input type="hidden"
								id="inputEmail" name="j_username" class="hide"
								placeholder="Email address" required autofocus>
						</div>
						<div class="login-input-cont">
							<input type="password" class="login-input login-input-pwd" id="password" placeholder="Password" >
							<input type="hidden" name="j_password" id="inputPassword"
								class="hide" >
						</div>
						<input type="submit" class="login-submit-button" name="Login" value="Login to your account">
						<!--  <div class="login-submit-button" onclick="document.loginForm.submit();">Login to your account</div>-->
						<div class="forgot-pwd">
							Forgot Password?<span class="forgot-pwd-link">Click here</span>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
