<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nexera</title>
<link href="/NewfiWeb/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/NewfiWeb/resources/css/jquery-ui.css" rel="stylesheet">
<link href="/NewfiWeb/resources/css/styles.css" rel="stylesheet">
<link href="/NewfiWeb/resources/css/styles-common.css" rel="stylesheet">
<link href="/NewfiWeb/resources/css/style-resp.css" rel="stylesheet">
<script src="/NewfiWeb/resources/js/jquery-2.1.3.min.js"></script>
	<script src="/NewfiWeb/resources/js/jquery-ui.js"></script>
	<script src="/NewfiWeb/resources/js/bootstrap.min.js"></script>
	<script src="/NewfiWeb/resources/js/common.js"></script>
	<script src="/NewfiWeb/resources/js/customer-engagement.js"></script>
	<script src="/NewfiWeb/resources/js/buyHome.js"></script>
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
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left">I am here to register as a</div>
					<div class="reg-row-rc float-left">
						<div class="reg-select reg-input-cont">
							<input class="reg-option-selected prof-form-input-select"  id="userTypeID" placeholder="User Type" value="">
							<div class="reg-option-dropdown hide">
								<div class="reg-select-option" id="customerID" role="cus">Customer</div>
								<div class="reg-select-option" id="realtorID" role="rel">Realtor</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left">Name</div>
					<div class="reg-row-rc float-left clearfix">
						<div class="reg-input-cont reg-fname float-left">
							<input class="reg-input" placeholder="First Name" id="firstName">
						</div>
						<div class="reg-input-cont reg-lname float-left">
							<input class="reg-input" placeholder="Last Name" id="lastName">
						</div>
					</div>
				</div>
				
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left">Your email id</div>
					<div class="reg-row-rc float-left">
						<div class="reg-input-cont reg-email">
							<input class="reg-input" placeholder="Email" id="emailID">
						</div>
					</div>
				</div>
				
				<div id="realor-email-cont" class="reg-input-row clearfix">
					<div class="reg-row-lc float-left">Referral Realtor email</div>
					<div class="reg-row-rc float-left">
						<div class="reg-input-cont reg-email" id="realor-email">
							<input class="reg-input" placeholder="Loan Manager Email" id="loanManagerEmailId" value="${loanManagerEmail}">
						</div>
					</div>
				</div>
				
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left">Referral Loan Manager email</div>
					<div class="reg-row-rc float-left">
						<div class="reg-input-cont reg-email" id="loanManager-email">
							<input class="reg-input" placeholder="Realtor Email" id="realtorEmailId" value="${realtorEmail}">	
						</div>
					</div>
				</div>

				<div class="reg-btn-wrapper clearfix">
					<div class="reg-btn float-left" id="submitID">Submit</div>
				</div>
							
			</div>			
		</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$(document).on('click','.reg-option-selected',function(e){
				$(this).parent().find('.reg-option-dropdown').slideToggle();
			});

			$("#customerID").on("click",function(e){		
				var userType=$("#customerID").text();
				$("#userTypeID").attr('value',userType);							
				$(this).closest('.reg-select').find('.reg-option-dropdown').slideToggle();
				$("#realor-email").show();
			});
			$("#realtorID").on("click",function(e){		
				var userType=$("#realtorID").text();
				$("#userTypeID").attr('value',userType);							
				$(this).closest('.reg-select').find('.reg-option-dropdown').slideToggle();
				$("#realor-email").hide();
			});
			
				$("#submitID").click(function(e){
				var LoanAppFormVO=new Object();
				var user = new Object();
				var loan=new Object();
				user.firstName=$("#firstName").val();
				var dateVar = new Date();
				var timezone = dateVar.getTimezoneOffset();
				user.emailId = $("#emailID").val() + ":" + timezone;
				user.lastName = $("#lastName").val();
				user.userRole={
							roleDescription :$("#userTypeID").val()
						}

			
				LoanAppFormVO.user=user;
				LoanAppFormVO.loanMangerEmail=$("#loanManagerEmailId").val();
				LoanAppFormVO.realtorEmail=$("#realtorEmailId").val();
				
				
				
				if($("#firstName").val()==""){
						showErrorToastMessage("Firstname cannot be empty");
						return;
				}else if($("#lastName").val()==""){
						showErrorToastMessage("LastName cannot be empty");
						return;
				}else if($("#emailID").val()==""){
					showErrorToastMessage("Email cannot be empty");
					return;
				}else if($("#emailID").val()!=null||$("#emailID").val()!=""){
					var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	                if (!regex.test($("#emailID").val())) {
		            showErrorToastMessage("Incorrect Email");
					return;
	                }
				}else if($("#userTypeID").attr('value')==""||$("#userTypeID").attr('value')==null||$("#userTypeID").attr('value')==undefined){
					showErrorToastMessage("Please Select the user");
					return;
				}
				if($("#userTypeID").attr('value')=="Customer"){
					createNewCustomer(LoanAppFormVO);
				}else if($("#userTypeID").attr('value')=="Realtor"){
					createNewRealtor(LoanAppFormVO);
				}
				
			});
		});
		function createNewCustomer(registration) {
    // alert(JSON.stringify(registration));
    $('#overlay-loader').show();
    $.ajax({
        url: "/NewfiWeb/rest/shopper/registration",
        type: "POST",
        data: {
            "registrationDetails": JSON.stringify(registration)
        },
        datatype: "application/json",
        success: function(data) {
            // $('#overlay-loader').hide();
            $('#overlay-loader').hide();
            // alert (data);
            window.location.href = data;
            // printMedianRate(data,container);
        },
        error: function(data) {
           // alert(data);
            showErrorToastMessage("error while creating user");
            $('#overlay-loader').hide();
        }
    });
}
		
		function createNewRealtor(user){
		    $('#overlay-loader').show();
		    $.ajax({
		        url: "/NewfiWeb/rest/shopper/realtorRegistration",
		        type: "POST",
		        data: {
		            "registrationDetails": JSON.stringify(user)
		        },
		        datatype: "application/json",
		        success: function(data) {
		            // $('#overlay-loader').hide();
		            $('#overlay-loader').hide();
		            // alert (data);
		            window.location.href = data;
		            // printMedianRate(data,container);
		        },
		        error: function(data) {
		           // alert(data);
		            showErrorToastMessage("error while creating user");
		            $('#overlay-loader').hide();
		        }
		    });	
		}

	</script>
</body>
</html>