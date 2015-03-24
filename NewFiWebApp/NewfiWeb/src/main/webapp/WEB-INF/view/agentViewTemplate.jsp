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
<link href="resources/css/datepicker.css" rel="stylesheet">
<link href="resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="resources/css/dropzone.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<link href="resources/css/jquery.Jcrop.css" rel="stylesheet">
<!-- <link href="resources/css/new.css" rel="stylesheet"> -->
</head>

<body>
<div id="popup-overlay" class="popup-overlay" style="display: none;position: fixed; height: 100%; width: 100%; overflow: auto;z-index: 9999; background-color: rgba(255, 255, 255, 0.901961) !important;">
	</div>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<jsp:include page="agentViewLeftPanel.jsp"></jsp:include>
			<div id="right-panel"></div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script>
		var newfi = ${newfi};
		$(document).ready(function() {
		$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
			initialize(newfi);
			isAgentTypeDashboard = true;
			paintAgentDashboard();
			retrieveState();
			$(window).resize(function() {
				adjustAgentDashboardOnResize();
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
			});
			
			//TODO added for loan profile page		

             if(newfi.user.photoImageUrl == "" || newfi.user.photoImageUrl == null){
				$("#myProfilePicture").addClass("lp-pic float-left");
				
			}else{
				
				 $("#myProfilePicture").addClass("lp-pic float-left").css({"background-image": "url("+newfi.user.photoImageUrl+")","background-size": "cover"});
				 
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
					
				initiateJcrop(this);
				
				}
			});
			
    
		});
	</script>
</body>
</html>