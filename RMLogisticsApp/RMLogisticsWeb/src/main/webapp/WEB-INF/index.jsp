<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>RareMile Logistics</title>
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
			<!-- Main Container -->
			<div class="login-container container">
				<div class="container-row row clearfix">
					<div class="display-title">Access your Rare Mile Logistics account</div>
					<div class="display-title-subtxt">Login with the email address and password used to create your account</div>
					<div class="login-form-wrapper">
						<form id="loginForm"
							action="j_spring_security_check" method="POST">
							<div class="form-logo"></div>
							<div class="login-input-cont">
								<input type="text" class="login-input login-input-username" placeholder="Email" id="userId">
								<input type="hidden" id="inputEmail" name="j_username" class="hide" placeholder="Email address" required autofocus>
							</div>
							<div class="login-input-cont">
								<input type="password" class="login-input login-input-pwd" id="password" placeholder="Password" >
								<input type="hidden" name="j_password" id="inputPassword" class="hide" >
							</div>
							<div class="login-submit-button">Login to your account</div>
							<div class="forgot-pwd">Forgot Password?<span class="forgot-pwd-link">Click here</span>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
