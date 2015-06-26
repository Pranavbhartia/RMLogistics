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
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">

</head>

<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<div id="reg-main-container" class="reg-main-container">
				
				<div class="reg-display-title">Get Started Now</div>
				<div class="reg-display-title-subtxt">Create a Newfi account now to access our powerful lending tool and take control on your terms.</div>
				<div class="reg-input-cont reg-fname">
					<input class="reg-input" placeholder="First Name">
				</div>
				<div class="reg-input-cont reg-lname">
					<input class="reg-input" placeholder="Last Name">
				</div>
				<div class="reg-input-cont reg-email">
					<input type="email" class="reg-input" placeholder="Email">	
				</div>
				<div class="reg-input-cont reg-phone">
					<input class="reg-input" placeholder="Phone number" name="phone" autocomplete="off">
				</div>
				<div class="reg-btn-wrapper clearfix">
					<div class="reg-btn float-left">Get Rate Alerts</div>
					<div class="reg-btn float-right">Get Started</div>
				</div>
				
			
			</div>			
		</div>
	</div>
	<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery-ui.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/bootstrap.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/common.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/customer-engagement.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/buyHome.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/message.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/historySupport.js"></script>
    <script src="${initParam.resourcesPath}/resources/js/validation.js"></script>
    <script>
		resizeHeaderWidth();
		$(window).resize(function() {
			resizeHeaderWidth();	
		});
	</script>
	<script>
		$(document).ready(function() {
			globalBinder();
			
		});
	</script>
</body>
</html>