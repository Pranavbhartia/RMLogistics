<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nexera</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/styles-common.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
</head>

<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<div id="reg-main-container" class="reg-main-container">
				
				<div class="reg-display-title">Lorem Ipsum Lorem Ipsum</div>
				<div class="reg-display-title-subtxt">Lorem Ipsum is also known
						as: Greeked Text, blind text, placeholder text, dummy content,
						filter text, lipsum, and mock-content.</div>
				<div class="reg-select reg-input-cont">
					<div class="reg-option-selected">User Type</div>
					<div class="reg-option-dropdown hide">
						<div class="reg-select-option" role="cus">Customer</div>
						<div class="reg-select-option" role="rel">Realtor</div>
					</div>
				</div>
				<div class="reg-input-cont reg-fname">
					<input class="reg-input" placeholder="First Name">
				</div>
				<div class="reg-input-cont reg-lname">
					<input class="reg-input" placeholder="Last Name">
				</div>
				<div class="reg-input-cont reg-email">
					<input class="reg-input" placeholder="Email">	
				</div>
				<div class="reg-input-cont reg-email">
					<input class="reg-input" placeholder="Loan Manager Email">	
				</div>
				<div class="reg-input-cont reg-email" id="realor-email">
					<input class="reg-input" placeholder="Realtor Email">	
				</div>
				
				<div class="reg-btn-wrapper clearfix">
					<div class="reg-btn float-left">Submit</div>
				</div>
				
			
			</div>			
		</div>
	</div>
	<script src="resources/js/jquery-2.1.3.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/customer-engagement.js"></script>
	<script src="resources/js/buyHome.js"></script>
	<script>
		$(document).ready(function() {
			$(document).on('click','.reg-option-selected',function(e){
				$(this).parent().find('.reg-option-dropdown').slideToggle();
			});
			$(document).on('click','.reg-select-option',function(e){
				var val = $(this).html();
				var role=$(this).attr("role");
				if(role="cus"){
					$('#realor-email').show();
				}else if(role="rel"){
					$('#realor-email').hide();
				}
				$(this).closest('.reg-select').find('.reg-option-selected').html(val).attr("role",role);
				$(this).closest('.reg-select').find('.reg-option-dropdown').slideToggle();
			});
		});
	</script>
</body>
</html>