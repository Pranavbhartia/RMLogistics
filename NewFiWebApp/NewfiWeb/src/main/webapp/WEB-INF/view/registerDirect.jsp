<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="${initParam.resourcesPath}/resources/images/newfiHome.ico">
<link href="${initParam.resourcesPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/jquery-ui.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-application.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery-ui.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery.mask.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/bootstrap.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/common.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/customer-engagement.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/buyHome.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/historySupport.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/validation.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/message.js"></script>
</head>

<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<div id="reg-main-container" class="reg-main-container">
				
				<div class="reg-display-title">Get Started</div>
				<div class="reg-display-title-subtxt">Create a Newfi account now to access our powerful lending tool and take control on your terms.</div>
				<div class="reg-form-content">
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left"></div>
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
					<div class="reg-row-lc float-left"></div>
					<div class="reg-row-rc float-left clearfix">
						<div class="reg-input-cont reg-fname float-left">
							<input class="reg-input" placeholder="First Name" id="firstName">
							<div class="err-msg hide"></div>
						</div>
						<!-- <div class="reg-input-cont reg-lname float-left">
							<input class="reg-input" placeholder="Last Name" id="lastName">
							<div class="err-msg hide"></div>
						</div> -->
					</div>
				</div>
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left"></div>
					<div class="reg-row-rc float-left">
						<div class="reg-input-cont reg-lname float-left">
							<input class="reg-input" placeholder="Last Name" id="lastName">
							<div class="err-msg hide"></div>
						</div>
					</div>
				</div>
				<div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left"></div>
					<div class="reg-row-rc float-left">
						<div class="reg-input-cont reg-email">
							<input type="email" class="reg-input" placeholder="Email" id="emailID">
							<div class="err-msg hide"></div>
						</div>
					</div>
				</div>
				
					<!-- <div class="reg-input-row clearfix">
					<div class="reg-row-lc float-left"></div>
					<div class="reg-row-rc float-left">
						<div class="reg-input-cont reg-phone">
					          <input class="reg-input" placeholder="Phone number" name="phone" id="phoneID">
					          <div class="err-msg hide"></div>
					    </div>
					</div>
				</div> -->
				<div class="reg-btn-wrapper clearfix">
					<div class="cep-button-color reg-btn float-left" id="submitID">Submit</div>
				</div>
				 <div class="reg-input-error hide errorMsg">
           		<span class ="reg-registration-error">
           		We are sorry, this email address already has a newfi account.To login <a href='javascript:goToLoginPage()' style="color: #2F6BF7">click here</a>
           		</span>
          </div>
				
				
							
			</div>
			</div>			
		</div>
	</div>
	<script>
		resizeHeaderWidth();
		$(window).resize(function() {
			resizeHeaderWidth();	
		});
	</script>
	<script>
	$('body').on('focus',"#phoneID",function(){
	    $(this).mask("(999) 999-9999");
	});
	$("#firstName").bind('keypress', function(e) {

	    if($(this).val().length == 0){
	        var k = e.which;
	        var ok = k >= 65 && k <= 90 || // A-Z
	            k >= 97 && k <= 122 || // a-z
	            k >= 48 && k <= 57; // 0-9

	        if (!ok){
	            e.preventDefault();
	        }
	    }
	}); 
	$("#lastName").bind('keypress', function(e) {

	    if($(this).val().length == 0){
	        var k = e.which;
	        var ok = k >= 65 && k <= 90 || // A-Z
	            k >= 97 && k <= 122 || // a-z
	            k >= 48 && k <= 57; // 0-9

	        if (!ok){
	            e.preventDefault();
	        }
	    }
	});
		$(document).ready(function() {
			globalBinder();
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
				/* var phoneNumber =$("#phoneID").val();
				user.phoneNumber = phoneNumber.replace(/[^0-9]/g, ''); */
				user.userRole={
							roleDescription :$("#userTypeID").val()
						}
			    
				LoanAppFormVO.user=user;
				LoanAppFormVO.loanMangerEmail=$("#loanManagerEmailId").val();
				LoanAppFormVO.realtorEmail=$("#realtorEmailId").val();
				
				
				//TODO form validation
				if($("#userTypeID").attr('value')==""||$("#userTypeID").attr('value')==null||$("#userTypeID").attr('value')==undefined){
					showErrorToastMessage("Please Select the user");
					return false;
				}
				var firstName=validateFormFeild("#firstName",'.reg-input-cont.reg-fname',"First name cannot be empty");
				if(!firstName){
					$('.reg-input-row').css('margin-bottom','38px');
					return false;
				}
				var lastName=validateFormFeild("#lastName",'.reg-input-cont.reg-lname',"Last name cannot be empty");
				if(!lastName){
					return false;
				}
				var emailID=validateFormFeild("#emailID",'.reg-input-cont.reg-email',"Email ID cannot be empty");
				if(!emailID){
					return false;
				}
				if($("#emailID").val()!=null||$("#emailID").val()!=""){
					var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;;
	                if (!regex.test($("#emailID").val())) {
	                	$('#emailID').next('.err-msg').html("Incorrect Email").show();
	        			$('.reg-input-cont.reg-email').addClass('ce-err-input').show();
					return false;
	                }
				}
/* 				var phone=validateFormFeild("#phoneID",'.reg-input-cont.reg-phone',phoneFieldEmptyMessage);
				if(!phone){
					$('.reg-input-row').css('margin-bottom','38px');
					return false;
				} */
				//End of validation
				validateUser(LoanAppFormVO);
				
				
			});
		});
		
		function validateUser(registration){
			$('#overlay-loader').show();
		    $.ajax({
		        url: "rest/shopper/validate",
		        type: "POST",
		        cache:false,
		        data: {
		            "registrationDetails": JSON.stringify(registration)
		        },
		        datatype: "application/json",
		        success: function(data) {
		            $('#overlay-loader').hide();
		            if(data.error==null){
		            	if($("#userTypeID").attr('value')=="Customer"){
							createNewCustomer(registration);
						}else if($("#userTypeID").attr('value')=="Realtor"){
							createNewRealtor(registration);
						}
		            }else{
		            	//showErrorToastMessage(data.error.message);
		            	$('.errorMsg').show();
		            }
		           
		        },
		        error: function(data) {
		             showErrorToastMessage(data);
		             $('#overlay-loader').hide();
		        }
		    });
		}
		function createNewCustomer(registration) {
    // alert(JSON.stringify(registration));
    $('#overlay-loader').show();
    $.ajax({
        url: "/NewfiWeb/rest/shopper/registration",
        type: "POST",
        cache:false,
        data: {
            "registrationDetails": JSON.stringify(registration)
        },
        datatype: "application/json",
        success: function(data) {
            $('#overlay-loader').hide();
            window.location.href = data;
            // printMedianRate(data,container);
        },
        error: function(data) {
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
		        cache:false,
		        data: {
		            "registrationDetails": JSON.stringify(user)
		        },
		        datatype: "application/json",
		        success: function(data) {
		            $('#overlay-loader').hide();
		            window.location.href = data;
		            // printMedianRate(data,container);
		        },
		        error: function(data) {
		            showErrorToastMessage("error while creating user");
		            $('#overlay-loader').hide();
		        }
		    });	
		}
		
	 function goToLoginPage(){
			
			window.location.href="${baseUrl}";
		}
	</script>
</body>
</html>