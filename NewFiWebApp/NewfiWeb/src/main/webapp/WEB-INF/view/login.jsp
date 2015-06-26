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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="${initParam.resourcesPath}/resources/images/newfiHome.ico">
<link href="${initParam.resourcesPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/jquery-ui.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/dropzone.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
<script src="${initParam.resourcesPath}/resources/js/common.js"></script>
<script type="text/javascript">

var errorMessage = "${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}"; 

	$(document).ready(function() {
	    <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
		$('#loginForm').submit(function() {
			var userName = $("#userId").val();
			var password = $("#password").val();
			var dateVar = new Date();
			var timezone = dateVar.getTimezoneOffset();
			$("#inputEmail").val(userName + ":" + timezone);
			$("#inputPassword").val(password);
			return true;
		});
		var hrefValue=window.location.href.split( '?' );
		
		if(hrefValue[1]=="s=autherror" && errorMessage=="First time login"){
			$("#errorMessage").html("You have not verified your registration email. Click  <a href='forgotPassword.do?resend=true'> here </a>to resend it.");
			$("#errorMessage").show();
		}
		else if(hrefValue[1]=="s=autherror" && errorMessage!=""){			
			$("#errorMessage").text(errorMessage);
			$("#errorMessage").show();
		}else if(hrefValue[1]=="s=sessionerror"){
			$("#errorMessage").text("Session expired.Please Login");
			$("#errorMessage").show();
		}
		 $('#footer-wrapper').show();
	});
</script>
</head>
<body>
<c:if test="${not empty param['error']}"> 
    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
 
</c:if> 
	<div class="login-body-wrapper">
		<div class="login-body-overlay">
			<!-- Login Header -->
 		<!-- 	<div class="login-header-wrapper">
				<div class="header-container container">
					<div class="header-row row clearfix">
						<div class="header-logo float-left"></div>
						<div class="header-btns-wrapper float-right clearfix">
							<div class="float-left login-hdr hdr-contact-no">1-888-415-1620</div>
							<div class="float-left login-hdr hdr-get-quote">Get Your
								Quote</div>
							<div class="float-left login-hdr hdr-login-btn">Login</div>
							<div class="float-left login-hdr hdr-signup-btn">SignUp for
								Rate Alerts</div>
						</div>
					</div>
				</div>
			</div>  -->
			<div class="header-wrapper">
	        <div class="header-container container">
		    <div class="header-row row clearfix">
				<!-- <div class="header-logo float-left"></div> -->
					<div class="header-logo float-left" onclick="window.location='https://www.newfi.com/'"></div>
				<div class="reg-header-btns-wrapper float-right clearfix">
					<!--  <div class="float-left login-hdr hdr-signup-btn" onclick="window.location='customerEngagement.do'">Check Rates</div>-->
	                <!-- <div class="float-left login-hdr hdr-login-btn" onclick="login()">Login</div> -->
				</div>
	            <div class="soft-menu-icon float-right"></div>
	            <div class="soft-menu-wrapper">
	                <!-- <div class="soft-menu-hdr cursor-pointer" onclick="window.location='./'">Login</div> -->
					<div class="soft-menu-hdr cursor-pointer" onclick="window.location='customerEngagement.do'">Check Rates</div>
	            </div>
	            <a href="tel:1-888-316-3934">
	             <div class="float-right login-hdr hdr-contact-no">888-316-3934</div>
	              <div class="float-right hdr-contact-icon"></div>
              </a>
		    </div>
	        </div>
            </div>

			<!-- Main Container -->
			<div class="login-container container">
				<div class="row clearfix">
					<div class="display-title">Access your newfi account</div>
					<!-- <div class="display-title-subtxt">Login with the email address and password used to create your account</div> -->
					<div class="login-form-wrapper">
						<form id="loginForm" name="loginForm"
							action="j_spring_security_check" method="POST">
<!-- 							<div class="form-logo"></div> -->
							<div class="login-error hide" id="errorMessage"></div>
							<div class="reg-input-row clearfix regis-input">	
				                     <!--  <div class="login-row-lc float-left">Email Address</div> -->
				                      <div class="login-row-rc">
				                        <div class="login-input-cont reg-email">
								          <input type="email" class="login-input"
									placeholder="Email" id="userId"> <input
									type="hidden" id="inputEmail" name="j_username" class="hide"
									placeholder="Email address" required autofocus>
							</div>
							</div>
							</div>
							<div class="reg-input-row clearfix regis-input">	
				                       <!--  <div class="login-row-lc float-left">Password</div>  -->
				                       <div class="login-row-rc">
							<div class="login-input-cont new-login-input-pwd">
								<input type="password" class="login-input"
									id="password" placeholder="Password"> <input
									type="hidden" name="j_password" id="inputPassword" class="hide">
							</div>
							</div>
							</div>
							
							<input type="submit" class="cep-button-color login-submit-button" name="Login"
								value="Login">
							<!--  <div class="login-submit-button" onclick="document.loginForm.submit();">Login to your account</div>-->
							<div class="forgot-pwd">
								Forgot Password?<span class="forgot-pwd-link" onclick="window.location='forgotPassword.do'">Click here</span>
							</div>
							<!-- <div class="display-title-bottom-subtxt">Newfi difference #1:we empower you with advanced tools to save time and money </div> -->
						</form>
					</div>
				</div>
			</div>
			</div>
			
			<!-- Footer -->
			 <!-- <div class="footer container">© 2015 newfi dba of Nexera Holding LLC | All Rights Reserved | NMLS ID 1231327</div> -->
	</div>
	<jsp:include page="login-inline-footer.jsp"></jsp:include>
</body>
<script>
	$(document).ready(function(){
		adjustLoginContainer();
		
		$(window).resize(function(){
			adjustLoginContainer();
			adjustInlineFooter();
		});
		adjustInlineFooter();
		function adjustLoginContainer(){
			$('.login-body-wrapper').css({
				"min-height" : $(window).height()
			});			
		}
		function adjustInlineFooter(){

			var windowHeight=window.innerHeight;
			//var footerHeight=$('.footer-wrapper').height();
			var headerHeight=$('.header-wrapper').height();
			var minHeight = windowHeight- headerHeight;
			$('.login-body-wrapper').css("min-height",minHeight+ "px");
			//$('.login-container').css("min-height",minHeight+ "px");
		}
		 $('#footer-wrapper').show();
	});
</script>
</html>