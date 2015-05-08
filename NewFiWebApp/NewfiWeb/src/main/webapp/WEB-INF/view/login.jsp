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
<link href="resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="resources/css/footer.css" rel="stylesheet">
<script src="resources/js/jquery-2.1.3.min.js"></script>

<script type="text/javascript">

var errorMessage = "${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}";
	$(document).ready(function() {
		
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
		if(errorMessage!=""){
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
 
	<div class="login-body-wrapper">
		<div class="login-body-overlay">
			<!-- Login Header -->
			<div class="login-header-wrapper">
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
							<div class="login-error hide" id="errorMessage"></div>
							<div class="login-input-cont">
								<input type="text" class="login-input login-input-username"
									placeholder="Username" id="userId"> <input
									type="hidden" id="inputEmail" name="j_username" class="hide"
									placeholder="Email address" required autofocus>
							</div>
							<div class="login-input-cont">
								<input type="password" class="login-input login-input-pwd"
									id="password" placeholder="Password"> <input
									type="hidden" name="j_password" id="inputPassword" class="hide">
							</div>
							<input type="submit" class="login-submit-button" name="Login"
								value="Login to your account">
							<!--  <div class="login-submit-button" onclick="document.loginForm.submit();">Login to your account</div>-->
							<div class="forgot-pwd">
								Forgot Password?<span class="forgot-pwd-link" onclick="window.location='forgotPassword.do'">Click here</span>
							</div>
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
                  <h3 class="footer-sidebar-header footer-border-width">Our Brands</h3>
                  <div class="textwidget">
                     <div id="footer_ourbrands" class="sidebar-widget  widget_recent_entries">
                        <ul class="footer-order-list">                          
                           <li class="footer-list clearfix"><a class="footer-anchor" href="/about-us/#newfi">Newfi</a><a></a></li>
                           <a>
                           </a>
                           <li class=" footer-list clearfix"><a></a><a class="footer-anchor" href="/about-us/#blustream">Blustream</a><a></a></li>
                           <a>
                           </a>
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
            <div class="footer-col-md-3 float-left">
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
            </div>
            <div class="footer-col-md-3 float-left">
               <div id="text-4" class="sidebar-widget  widget_text">
                  <h3 class="footer-sidebar-header footer-border-width">Legal</h3>
                  <div class="textwidget">
                     <div id="footer_legal" class="sidebar-widget  widget_recent_entries">
                        <ul class="footer-order-list">
                           <li class=" footer-list clearfix"><a class="footer-anchor" href="/information/#licensing">Licensing</a></li>
                           <li class="footer-list clearfix"><a class="footer-anchor" target="_blank" href="http://www.nmlsconsumeraccess.org/">NMLS Consumer Access</a></li>
                           <li class="footer-list clearfix"><a class="footer-anchor" href="/privacy-policy/">Privacy Policy</a></li>
                           <li class="footer-list clearfix"><a class="footer-anchor" href="/information/">Terms of Use</a></li>
                        </ul>
                     </div>
                  </div>
               </div>
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
      <div class="footer-container-text">
         © 2015 Nexera Holding LLC - DBA Newfi and Blustream | All Rights Reserved | NMLS ID 1231327
      </div>
      <br>
   </section>
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
