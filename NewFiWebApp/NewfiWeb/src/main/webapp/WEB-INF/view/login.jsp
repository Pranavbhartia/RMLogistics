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
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="resources/images/title-logo.png">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/dropzone.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<link href="resources/css/styles-common.css" rel="stylesheet">
<link href="resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="resources/css/footer.css" rel="stylesheet">
<script src="resources/js/jquery-2.1.3.min.js"></script>

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
			<div class="header-logo float-left"></div>
			<div class="reg-header-btns-wrapper float-right clearfix">
				<div class="float-left login-hdr hdr-signup-btn" onclick="window.location='customerEngagement.do'">Check Rates</div>
               <!--  <div class="float-left login-hdr hdr-login-btn" onclick="window.location='./'">Login</div> -->
			</div>
            <div class="soft-menu-icon float-right"></div>
            <div class="soft-menu-wrapper">
               <!--  <div class="soft-menu-hdr cursor-pointer" onclick="window.location='./'">Login</div> -->
				<div class="soft-menu-hdr cursor-pointer" onclick="window.location='customerEngagement.do'">Check Rates</div>
            </div>
             <div class="float-right login-hdr hdr-contact-no">Call Us 888-316-3934</div>
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
				                      <div class="login-row-rc float-left">
				                        <div class="login-input-cont">
								          <input type="text" class="login-input"
									placeholder="Email" id="userId"> <input
									type="hidden" id="inputEmail" name="j_username" class="hide"
									placeholder="Email address" required autofocus>
							</div>
							</div>
							</div>
							<div class="reg-input-row clearfix regis-input">	
				                       <!--  <div class="login-row-lc float-left">Password</div>  -->
				                       <div class="login-row-rc float-left">
							<div class="login-input-cont">
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
    <footer id="footer" role="contentinfo">
   <section class="section swatch-black">
      <div class="footer-container">
         <div class="row footer-element-normal-top footer-element-normal-bottom">
            <div class="footer-col-md-3 float-left">
               <div id="text-3" class="sidebar-widget  widget_text">
                  <h3 class="footer-sidebar-header footer-border-width">Links</h3>
                  <div class="textwidget">
                     <div id="footer_ourbrands" class="sidebar-widget  widget_recent_entries">
                        <ul class="footer-order-list">                          
                           <li class="footer-list clearfix"><a class="footer-anchor" onclick="window.location='customerEngagement.do'">Check Rates</a><a></a></li>
                           <li class=" footer-list clearfix"><a></a><a class="footer-anchor" onclick="window.location='./'">Login</a><a></a></li>
                           <li class=" footer-list clearfix"><a></a><a class="footer-anchor" onclick="window.location='http://www.nexeraholding.com/'">Nexsera Holding</a><a></a></li>                          
                        </ul>
                        <a>
                        </a>
                     </div>
                  </div>
                  <a>
                  </a>
               </div>
            </div>
            <a>
            </a>
<!--             <div class="footer-col-md-3 float-left">
               <a></a>
               <div id="sticky-posts-2" class="sidebar-widget  widget_recent_entries sidebar-widget  widget_ultimate_posts">
                  <a>
                     <h3 class="footer-sidebar-header footer-border-width">Careers</h3>
                  </a>
                  <ul class="footer-order-list">
                     <a>
                     </a>
                     <li class=" footer-list clearfix">
                        <a>
                        </a>
                        <div class="upw-content">
                           <a>
                           </a>
                           <p class="post-title"><a>
                              </a><a class="footer-anchor" href="http://www.nexeraholding.com/careers/call-center-loan-officer/" title="Call Center Loan Officer">
                              Call Center Loan Officer            </a>
                           </p>
                        </div>
                     </li>
                     <li class=" footer-list clearfix">
                        <div class="upw-content">
                           <p class="post-title">
                              <a class="footer-anchor" href="http://www.nexeraholding.com/careers/senior-underwriter/" title="Senior Underwriter">
                              Senior Underwriter            </a>
                           </p>
                        </div>
                     </li>
                     <li class="footer-list clearfix">
                        <div class="upw-content">
                           <p class="post-title">
                              <a class="footer-anchor" href="http://www.nexeraholding.com/careers/fundershipper/" title="Funder/Shipper">
                              Funder/Shipper            </a>
                           </p>
                        </div>
                     </li>
                  </ul>
               </div>
            </div> -->
            <div class="footer-col-md-3 float-left">
               <div id="text-4" class="sidebar-widget  widget_text">
                  <h3 class="footer-sidebar-header footer-border-width">Legal</h3>
                  <div class="textwidget">
                     <div id="footer_legal" class="sidebar-widget  widget_recent_entries">
                        <ul class="footer-order-list">
                           <li class=" footer-list clearfix"><a class="footer-anchor" onclick="window.location='http://www.nexeraholding.com/information/#licensing'">Licensing</a></li>
                           <li class="footer-list clearfix"><a class="footer-anchor" target="_blank" onclick="window.location='http://www.nmlsconsumeraccess.org/'">NMLS Consumer Access</a></li>
                           <li class="footer-list clearfix"><a class="footer-anchor" onclick="window.location='http://www.nexeraholding.com/privacy-policy/'">Privacy Policy</a></li>
                           <li class="footer-list clearfix"><a class="footer-anchor" onclick="window.location='http://www.nexeraholding.com/information/'">Terms of Use</a></li>
                        </ul>
                     </div>
                  </div>
               </div>
            </div>
            
           <div class="footer-col-md-3 float-left">
               <div class="footer-newfi-image"></div> 
               <div class="footer-newfi-information">a new way to finance your home</div>
               <div class="footer-newfi-information-subtxt">
               <p class="footer-subtxt">2200 Powell Street, Suite 340</p>
               <p class="footer-subtxt">Emeryville, CA 94608</p>
               <p class="footer-subtxt">Call now 1-888-316-3934</p></div>

            </div>
            <div class="footer-icon-col-md-3">
               <div id="text-5" class="sidebar-widget  widget_text">
                  <div class="textwidget">
                     <div class="footer-icon-container clearfix">
                        <a href="https://www.linkedin.com/company/nexera-holding-llc" target="_blank">
                           <div class="footerlinkedin"></div>
                        </a>
                        <a href="https://twitter.com/NexeraHolding" target="_blank">
                           <div class="footertwitter"></div>
                        </a>
                        <a href="mailto:info@nexeraholding.com">
                           <div class="footeremail"></div>
                        </a>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </section>
   <section class="section swatch-black">
               <div class="container">
               <!-- <div id="footer">
	<div class="footer-bottom">
		<a href="http://portal.hud.gov/hudportal/HUD" target="_blank">
			<div class="footer-home-image float-left" id="footeHomeImage">
			<div class="footer-home-text" id="footerHomeText">Equal Housing Lender</div>
		</a></div>
			
	</div> -->
	<div class="footer-container-text float-left"> © 2015 newfi dba of Nexera Holding LLC | All Rights Reserved | NMLS ID 1231327</div>
	<div class="footer-container-text2 float-right">
   <a href="http://portal.hud.gov/hudportal/HUD" target="_blank">
   <div class="footer-login-inline-home-image" id="footeHomeImage"></div>
   </a>
  <a href="http://portal.hud.gov/hudportal/HUD" target="_blank">
  <div class="footer-login-inline-home-text" id="footerHomeText">Equal Housing Lender</div>
   </a>  
  </div>
</div>
<br>
		</div></section>

</footer>
			
		
	</div>
</body>
<script>
	$(document).ready(function(){
		adjustLoginContainer();
		
		$(window).resize(function(){
			adjustLoginContainer();
		});
		
		function adjustLoginContainer(){
			$('.login-body-wrapper').css({
				"min-height" : $(window).height()
			});			
		}
	});
</script>
</html>