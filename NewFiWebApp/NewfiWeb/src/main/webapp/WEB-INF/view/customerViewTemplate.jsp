<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ page isELIgnored ="false" %>
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
<link href="${initParam.resourcesPath}/resources/css/perfect-scrollbar.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/font-awesome.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/dropzone.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/datepicker.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/pgwslideshow.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-admin.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-application.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-engagement.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">    
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
<script src="https://js.braintreegateway.com/v2/braintree.js"></script>


</head>

<body>
<style>
.btn-pu{
	width: 100px;
	line-height: 32px;
	text-align: center;
	border: 1px solid #dcdcdc;
	cursor: pointer;
	float: left;
	maring: 0 30px;
}
</style>
	<div id="popup-overlay" class="popup-overlay">
	</div>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<jsp:include page="customerViewLeftPanel.jsp"></jsp:include>
			
			<div id="right-panel"></div>
			<div id="imageCropContainerDiv" style="width:200px;height: 200">
			</div>
			<div id="loading"></div>
		</div>
	</div>
	<jsp:include page="inlineFooter.jsp"></jsp:include>
	<jsp:include page="footer.jsp"></jsp:include>
	<input type="hidden" value="${user.photoImageUrl}" id="photoImageUrlID">
	<script>
	var newfi = ${newfi};
	var baseUrl = "${baseUrl}";
		$(document).ready(function() {
			
			
	/* 		$('#right-panel').css('min-height',window.innerHeight - 98 + 'px'); */
			$('#right-panel').css('min-height','100%');
			
			$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
			initialize(newfi,baseUrl);
			if(window.location.hash==""){
				changeLeftPanel(2,callBackFun);
				$('#footer-wrapper').show();
			}
				
			else{
				callBackFun();
				updateNotifications(0);
			}
				
			//adjustCenterPanelWidth();
			//adjustRightPanelOnResize();

		});
		function callBackFun(){
			$(window).resize(function() {
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
                adjustCustomerApplicationPageOnResize();
                //adjustFooter();
			});
			var loanType=JSON.parse(newfi.appUserDetails).loanType.description;
			if(loanType === "Purchase")
				loanType = "Home Buyer";
			$('#loanType').text(loanType);
			var initialText = getInitials(newfiObject.user.firstName,newfiObject.user.lastName);
			if(newfiObject.user.photoImageUrl == "" || newfiObject.user.photoImageUrl == null){
				//$("#myProfilePicture").addClass("lp-pic float-left");
				$("#myProfilePicture").addClass("lp-initial-pic float-left").text(initialText);
			}else{
				 $("#myProfilePicture").addClass("lp-pic float-left").css({"background-image": "url("+newfiObject.user.photoImageUrl+")","background-size": "cover"});
			}
			
			
			bindDataToPN();
			bindDataToSN();
			
			//Bind primary navigation
			globalBinder();
			onpopstate = function(event) {
	            console.log('history modified');
	            if(location.hash.trim()!=''){
	                historyCallback= true;
	                refreshSupport=true;
	            }
	            retrieveState();
	        };
			if(location.hash.trim()!='' ){
	            historyCallback= true;
	            refreshSupport=true;
	            retrieveState();
	        }
			
			$(document).on('change', '#prof-image', function() {
				
				//alert($('#prof-image')[0].files[0].name);
				//alert("hiii");
				var fileName=$("#prof-image").val();
				//console.log("fileName"+fileName);
	            var status=validatePhotoExtention(fileName);
	            //alert("status"+status);
				if(status!=false){
				initiateJcrop(this);}
			});
			
			$(document).on('keypress','input[name="zipCode"],input[name="propZipCode"]',function(e){
				
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) ) {
			        //display error message
			        //showToastMessage("Enter correct zipcode");
			          return false;
			    }if($(this).val().length >= 5){
			    	
			    	// showToastMessage("Enter correct zipcode");
			         return false;
			    }
			});
			
			
			
			/* $(document).on('click','input[name="birthday"]',function(e){
				
				$(this).datepicker({
					orientation : "top auto",
					autoclose : true
				});
			}); */
		//	retrieveState();

			
			$('[data-toggle="tooltip"]').tooltip();  
			$('#footer-wrapper').show();
		}

	</script>
</body>
</html>