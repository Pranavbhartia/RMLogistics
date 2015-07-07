<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
      
      <div class="reg-display-title">
        Get Started 
      </div>
      <!-- NEXNF-659 -->
<!-- 				<div class="reg-display-title-subtxt">Create a newfi account now to access our powerful lending tool and take control on your terms.</div> -->
      <div class="reg-display-title-subtxt referal-sub-txt">
        Create your account now to have immediate access to the powerful benefits of newfi.
      </div>
      
      <div class="clearfix user-info-outer-container">
        <div class="float-left left-user-container ">
          <div class="user-info-txt">
            Referred by
          </div>
          <div class="user-info-image">
            
          </div>
          
          <div class="user-info-row">
            <div class="user-info-name">
              ${userObject.displayName}
            </div>
          </div>
          <div class="user-info-row" id="realtorRole">
            <div>
              ${userRole}
            </div>
          </div>
          <div class="user-info-row" id="realtorPhNumber">
            <div>
              ${userObject.phoneNumber}
            </div>
          </div>
          
          <%-- 
          <div class="user-info-row">
            <div>
              ${userObject.userRole.label}
            </div>
          </div>
          --%>
        </div>
        <div class="float-right new-user-container" id="right-container-id">
                <c:set var="val" value="${userRole}"/>
                 <c:choose>                 
                  <c:when test="${val == 'Realtor'}">
                    <div class="reg-input-row  clearfix hide">
                       <!-- <div class="reg-row-lc-new reg-row-lc float-left hide">Register as</div> -->
                         <div class="reg-row-rc-new reg-row-rc float-left hide">
                           <div class="reg-select reg-input-cont hide">
                              <input class="reg-option-selected prof-form-input-select hide"  id="userTypeID" placeholder="User Type" value="Borrower"> 
                            </div>
                          </div>
                       </div> 
                                 <div class="reg-input-row realtor-row clearfix">
            <!-- <div class="reg-row-lc-new reg-row-lc float-left">
              Your Name
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left clearfix">
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
       <!--      <div class="reg-row-lc-new reg-row-lc float-left">
              Your email id
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left">
              <div class="reg-input-cont reg-lname float-left">
                <input class="reg-input" placeholder="Last Name" id="lastName">
                <div class="err-msg hide"></div>
              </div>
            </div>
          </div>
          <div class="reg-input-row clearfix">
       <!--      <div class="reg-row-lc-new reg-row-lc float-left">
              Your email id
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left">
              <div class="reg-input-cont reg-email">
                <input type="email" class="reg-input" placeholder="Email" id="emailID">
                <div class="err-msg hide"></div>
              </div>
            </div>
          </div>
         <!--  <div class="reg-input-row clearfix">
            <div class="reg-row-lc-new reg-row-lc float-left">
              Your Phone Number
            </div>
            <div class="reg-row-rc-new reg-row-rc float-left">
	          <div class="reg-input-cont reg-phone">
	          <input class="reg-input" placeholder="Phone number" name="phone" id="phoneID">
	          <div class="err-msg hide"></div>
	          </div>
           </div>
          </div> -->
            <div class="reg-btn-wrapper clearfix">
            <!-- NEXNF-659 -->
            <!-- <div class="cep-button-color reg-btn reg-chg-width float-left" id="submitID">
              Submit
            </div> -->
             <div class="cep-button-color reg-btn reg-chg-width float-left" id="submitID">
              Create Account
            </div>
          </div>
          <div class="reg-input-error hide errorMsg">
           		<span class ="reg-registration-error">
           		We are sorry, this email address already has a newfi account.To login <a href='javascript:goToLoginPage()' style="color: #2F6BF7">click here</a>
           		</span>
          </div>
            </c:when>
                  <c:otherwise>
                            <div class="reg-input-row clearfix">
          <!--   <div class="reg-row-lc-new reg-row-lc float-left">
              Register as
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left">
              <div class="reg-select reg-input-cont">
                    <input class="reg-option-selected prof-form-input-select"  id="userTypeID" placeholder="User Type" value="">
                    <div class="reg-option-dropdown hide">
                    	<!-- NEXNF-659 -->
                      	<!-- <div class="reg-select-option" id="customerID" role="cus">Customer</div> -->
                      <div class="reg-select-option" id="customerID" role="cus">Borrower</div>
                      <div class="reg-select-option" id="realtorID" role="rel">Realtor</div>
                    </div>
                    </div>
                    </div>
                    </div>
                              <div class="reg-input-row clearfix">
            <!-- <div class="reg-row-lc-new reg-row-lc float-left">
              Your Name
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left clearfix">
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
          <!--   <div class="reg-row-lc-new reg-row-lc float-left">
              Your email id
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left">
              <div class="reg-input-cont reg-lname float-left">
                <input class="reg-input" placeholder="Last Name" id="lastName">
                <div class="err-msg hide"></div>
              </div>
            </div>
          </div>
          <div class="reg-input-row clearfix">
          <!--   <div class="reg-row-lc-new reg-row-lc float-left">
              Your email id
            </div> -->
            <div class="reg-row-rc-new reg-row-rc float-left">
              <div class="reg-input-cont reg-email">
                <input class="reg-input" placeholder="Email" id="emailID">
                <div class="err-msg hide"></div>
              </div>
            </div>
          </div>
          <!-- <div class="reg-input-row clearfix">
            <div class="reg-row-lc-new reg-row-lc float-left">
              Your Phone Number
            </div>
            <div class="reg-row-rc-new reg-row-rc float-left">
	          <div class="reg-input-cont reg-phone">
	          <input class="reg-input" placeholder="Phone number" name="phone" id="phoneID" >
	          <div class="err-msg hide"></div>
	          </div>
           </div>
          </div> -->
          <div class="reg-btn-wrapper clearfix">
            <!-- NEXNF-659 -->
            <!-- <div class="cep-button-color reg-btn reg-chg-width float-left" id="submitID">
              Submit
            </div> -->
             <div class="cep-button-color reg-btn reg-chg-width float-left" id="submitID">
              Create Account
            </div>
          </div>
          <div class="reg-input-error hide errorMsg">
           		<span class ="reg-registration-error">
           		We are sorry, this email address already has a newfi account.To login <a href='javascript:goToLoginPage();' style="color: #2F6BF7">click here</a>
           		</span>
          </div>
                  </c:otherwise>
                </c:choose>
            
          

          
          
        </div>
        <div class="cus-eng-success-message hide" id="cus-eng-success-message-id"></div>
      </div>
      
      
      
      <!-- End of second div -->
      
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
	//called to dismiss the toast mess on page navigation
	globalBinder();
	var baseurl;
	console.log( "${userObject}");
	var photo = "${userObject.photoImageUrl}";
	var name = "${userObject.displayName}";
	var email = "${userObject.emailId}";
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
						
			if(window.location.href.indexOf("us") > -1){
				var url=window.location.href.split('us');
				baseurl=url[0];
				
			}else{
				var url=window.location.href.split('registerNew.do');
				baseurl=url[0];
			}
			$(document).on('click','.reg-option-selected',function(e){
				$(this).parent().find('.reg-option-dropdown').slideToggle();
			});
			$('#realtorPhNumber').html(formatPhoneNumberToUsFormat($('#realtorPhNumber').html()));
			var imgCont = $('<div>').attr({
				
			});
			if(photo==null||photo==""){
				
				imgCont.addClass("assigned-agent-default-img");		
				imgCont.text(getInitialsFromFullName(name));	
			}else{
				imgCont.addClass("assigned-agent-img");
				imgCont.css("background-image", "url('" + photo + "')");
			}
			$(".user-info-image").append(imgCont);
			

			$("#customerID").on("click",function(e){		
				var userType=$("#customerID").text();
				//alert(userType);
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
			LoanAppFormVO.loanMangerEmail=email;
			LoanAppFormVO.realtorEmail=email;
			
			//TODO form validation
			var isStatus=validateFormFeildInRealtorReferalRegistration();
			
			if(!isStatus){
				return false;
			}
			//End of validation
			validateUser(LoanAppFormVO);
				
				
			});
		});
		
		function validateUser(registration){
			showOverlay();
		    $.ajax({
		        url: baseurl+"rest/shopper/validate",
		        type: "POST",
		        cache:false,
		        data: {
		            "registrationDetails": JSON.stringify(registration)
		        },
		        datatype: "application/json",
		        success: function(data) {
		        	hideOverlay();
		            if(data.error==null){
		            	if($("#userTypeID").attr('value')=="Borrower"){//NEXNF-659 changed from customer to borrower
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
		        	
		        	hideOverlay();
		        	if(data!=""||data!=null){
		        		 showErrorToastMessage(data);
		        	}else{
		        		 showErrorToastMessage(validation_unsuccess_message);
		        	}
		            
		             
		        }
		    });
		}
   
	    function createNewCustomer(registration) {
     //alert(JSON.stringify(registration));
        showOverlay();
        $.ajax({
        url: baseurl+"rest/shopper/registration",
        type: "POST",
        cache:false,
        data: {
            "registrationDetails": JSON.stringify(registration)
        },
        datatype: "application/json",
        success: function(data) {
        	
        	hideOverlay();
            appendUserCreationSuccessMessage(data);
            $('.cus-eng-success-message').addClass('cus-eng-success-message-adjust');
            $('.cus-eng-succ-mess-row').addClass('cus-eng-succ-mess-row-adjust');
            /* window.location.href =baseurl;
            window.location.href = data; */
            // printMedianRate(data,container);
        },
        error: function(data) {
        	
        	hideOverlay();
            showErrorToastMessage(user_creation_unsuccess_message);
           
          }
         });
        }
		
		function createNewRealtor(user){
			showOverlay();
		    $.ajax({
		        url: baseurl+"rest/shopper/realtorRegistration",
		        type: "POST",
		        cache:false,
		        data: {
		            "registrationDetails": JSON.stringify(user)
		        },
		        datatype: "application/json",
		        success: function(data) {
		            // $('#overlay-loader').hide();
		            hideOverlay();
		            appendUserCreationSuccessMessage(data);
		            $('.cus-eng-success-message').addClass('cus-eng-success-message-adjust');
		            $('.cus-eng-succ-mess-row').addClass('cus-eng-succ-mess-row-adjust');
		          // alert (data);
		            /* window.location.href =baseurl;
		            window.location.href = data; */
		            // printMedianRate(data,container);
		        },
		        error: function(data) {
		         // alert(data);
		         	hideOverlay();
		            showErrorToastMessage(realtor_creation_unsuccess_message);
		            
		        }
		    });	
		}
		
		function goToLoginPage(){
			
			window.location.href="${baseurl}";
		}
	</script>
</body>
</html>