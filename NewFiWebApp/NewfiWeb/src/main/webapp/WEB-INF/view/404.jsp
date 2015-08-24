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
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/404-style.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">
<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
<script src="${initParam.resourcesPath}/resources/js/jquery-ui.js"></script>
<script src="${initParam.resourcesPath}/resources/js/bootstrap.min.js"></script>

</head>
<body>

	<div class="header-wrapper">
	<div class="header-container container">
		<div class="header-row row clearfix">
			<div class="header-logo float-left"></div>
			<div class="reg-header-btns-wrapper float-right clearfix">
				<div class="float-left login-hdr-error-page hdr-signup-btn" onclick="window.location='https://www.newfi.com/NewfiWeb/customerEngagement.do'">Check Rates</div>
               <!--  <div class="float-left login-hdr hdr-login-btn" onclick="window.location='./'">Login</div> -->
			</div>
            
           
		</div>
	</div>
</div>
		<!--start-wrap--->
		<div class="wrap">
			<!---start-header---->
				<!-- <div class="header">
					<div class="logo">
						<h1><a href="#">Where have I reached?</a></h1>
					</div>
				</div> -->
			<!---End-header---->
			<!--start-content------>
			<div class="content">
			<!-- 	<img src="${initParam.resourcesPath}/resources/images/404-error-img.png" title="error"/> -->
				<div class="err-text1">OOPS!!!That was not supposed to happen.</div>
				<p class="label-txt" style="">We apologize for the inconvenience</p>
				<p class="label-txt">Click the link below to be redirected to the home page.</p><br>  
				<div class="" name="Login" value="Login" onclick="window.location='https://www.newfi.com/NewfiWeb/'" style="
                 width: 150px;    height: 40px;    line-height: 40px;    font-size: 16px;    color: #fff;    background-color: #6c9f2e;    border-radius: 3px;    text-align: center;    text-transform: capitalize;    cursor: pointer;    font-family: 'opensanssemibold'; margin: 0 auto;
                 margin-top: 15px;margin-bottom: 15px;">Home</div>
				<div class="copy-right">
					<p>If problem persists, please send quick message to <a href="mailto:support@newfi.com" target="_top"><u>support@newfi.com</u></a> and tell<br> us what you did to get this error page, so we can get it fixed.</p>
				</div>
   			</div>
			<!--End-Cotent------>
		</div>
		<!--End-wrap--->
		 <jsp:include page="login-inline-footer.jsp"></jsp:include> 

</body>
<script>
$(document).ready(function(){

		var height=window.innerHeight;
		var footerHeight=$('.footer-wrapper').height();
		var headerHeight=$('.header-wrapper').height();
		height=height-headerHeight;
		$('.content').css("height",height+ "px");
		$('#footer-wrapper').show();
	    var url=
	
	
});
</script>
</html>

