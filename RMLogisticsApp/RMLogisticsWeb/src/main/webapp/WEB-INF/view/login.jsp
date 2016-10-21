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
			<title>Rare Mile Logistics</title>
			<%
			
			if(session.getId().equals(session.getAttribute("sessionID"))){
			%>
			<jsp:forward page="../../home.do"></jsp:forward>
			<%
			}
			%>
			<link href="${initParam.resourcesPath}/resources/css/bootstrap.min.css" rel="stylesheet">
			<link href="${initParam.resourcesPath}/resources/css/jquery-ui.css" rel="stylesheet">
			<link href="${initParam.resourcesPath}/resources/css/dropzone.css" rel="stylesheet">
			<%-- <link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet"> --%>
			<link href="${initParam.resourcesPath}/resources/css/jquery.Jcrop.css" rel="stylesheet">
			<%-- <link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet"> --%>
			<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
			<%-- <link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet"> --%>
			<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
			<%-- <script src="${initParam.resourcesPath}/resources/js/common.js"></script> --%>
			<script type="text/javascript">
			
			var errorMessage = "${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}"; 
			
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
					
					if(hrefValue.length == 2){
						if(hrefValue[1]=="s=autherror" && errorMessage=="First time login"){
							$("#errorMessage").html("You have not verified your registration email. Click  <a href='forgotPassword.do?resend=true'> here </a>to resend it.");
							$("#errorMessage").show();
						}
						else if(hrefValue[1]=="s=autherror" && errorMessage!=""){			
							$("#errorMessage").text(errorMessage);
							$("#errorMessage").show();
						}else if(hrefValue[1].indexOf("s=sessionerror") > -1){
							$("#errorMessage").text("Session expired.Please Login");
							$("#errorMessage").show();
						}
					}
					 $('#footer-wrapper').show();
				});
			</script>
	</head>
	<body>
		<c:if test="${not empty param['error']}"> 
		    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
		</c:if>
		<div class="header-wrapper">
			<div class="header-container">
				<div class="header-logo"></div>
				<div class="header-company-name">
					<div class="header-comp-txt">Rare Mile Logistics</div>
				</div>
				<div class="header-contact-container">
					<div class="header-contact-info">
						<div class="header-contact-txt">080-2572-4590</div>
					</div>
					<div class="hdr-contact-icon"></div>
				</div>
			</div>
		</div>
		<div class="login-container">
			<div class="row clearfix">
				<div class="display-title">Access your Rare Mile Logistics account</div>
				<div class="login-form-wrapper">
					<form id="loginForm" name="loginForm" action="j_spring_security_check" method="POST">
						<div class="login-error hide" id="errorMessage"></div>
						<div class="reg-input-row clearfix regis-input">
							<div class="login-row-rc">
								<div class="login-input-cont reg-email">
								    <input type="email" class="login-input"	placeholder="Email" id="userId">
									<input type="hidden" id="inputEmail" name="j_username" class="hide" placeholder="Email address" 
									required autofocus>
								</div>
							</div>
						</div>
						<div class="reg-input-row clearfix regis-input">
							<div class="login-row-rc">
								<div class="login-input-cont new-login-input-pwd">
								   <input type="password" class="login-input" id="password" placeholder="Password">
								   <input type="hidden" name="j_password" id="inputPassword" class="hide">
								</div>
							</div>
						</div>
						<input type="submit" class="cep-button-color login-submit-button" name="Login" value="Login">			
					</form>
				</div>
			</div>
		</div>
		<jsp:include page="login-inline-footer.jsp"></jsp:include>