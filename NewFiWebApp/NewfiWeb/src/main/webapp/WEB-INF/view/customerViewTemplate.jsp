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
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nexera</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/dropzone.css" rel="stylesheet">
<link href="resources/css/datepicker.css" rel="stylesheet">
<link href="resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<script src="https://js.braintreegateway.com/v2/braintree.js"></script>
<link href="resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="resources/css/customer-application.css" rel="stylesheet">
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
	<div id="popup-overlay" class="popup-overlay" style="display: none;position: fixed; height: 100%; width: 100%; overflow: auto;z-index: 9999; background-color: rgba(255, 255, 255, 0.901961) !important;">
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
	<jsp:include page="footer.jsp"></jsp:include>
	<input type="hidden" value="${user.photoImageUrl}" id="photoImageUrlID">
	<script>
	var newfi = ${newfi};
		$(document).ready(function() {
			$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
			initialize(newfi);
			changeLeftPanel(2);
			adjustCenterPanelWidth();
			adjustRightPanelOnResize();
			$(window).resize(function() {
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
			});

						
			if(newfiObject.user.photoImageUrl == "" || newfiObject.user.photoImageUrl == null){
				$("#myProfilePicture").addClass("lp-pic float-left");
				
			}else{
				
				 $("#myProfilePicture").addClass("lp-pic float-left").css({"background-image": "url("+newfiObject.user.photoImageUrl+")","background-size": "cover"});
				 
			}
			
			
			bindDataToPN();
			bindDataToSN();
			
			//Bind primary navigation
			globalBinder();
			onpopstate = function(event) {
	            console.log('history modified');
	            if(location.search.trim()!=''&&location.search.indexOf("q=")!=-1){
	                historyCallback= true;
	               
	                refreshSupport=true;
	            }
	            retrieveState();
	        };
			if(location.search.trim()!=''&&location.search.indexOf("q=")!=-1 ){
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
			
		});
	</script>
</body>
</html>