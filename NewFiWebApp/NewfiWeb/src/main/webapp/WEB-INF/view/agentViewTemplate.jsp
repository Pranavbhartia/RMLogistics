<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="resources/images/newfiHome.ico">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/perfect-scrollbar.min.css" rel="stylesheet">
<link href="resources/css/datepicker.css" rel="stylesheet">
<link href="resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="resources/css/dropzone.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="resources/css/customer-application.css" rel="stylesheet">
<link href="resources/css/customer-engagement.css" rel="stylesheet">
<link href="resources/css/style-admin.css" rel="stylesheet">
<link href="resources/css/footer.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<script src="https://js.braintreegateway.com/v2/braintree.js"></script>
</head>

<body>
<div id="popup-overlay" class="popup-overlay">
	</div>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<jsp:include page="agentViewLeftPanel.jsp"></jsp:include>
			<div id="right-panel"></div>
		</div>
	</div>
	<jsp:include page="inlineFooter.jsp"></jsp:include>
	<jsp:include page="footer.jsp"></jsp:include>
	<script>

		var newfi = ${newfi};
		var baseUrl = "${baseUrl}";
		$(document).ready(function() {
			$('#right-panel').css('min-height',window.innerHeight - 98 + 'px');
/* 			var height=$('.home-container').height();
			var footerHeight=$('.footer-wrapper').height();
			height=footerHeight-height;
			$('.footer-wrapper').css("bottom",height+ "px"); */
		$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
		$('.assigned-agent-contact').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
			initialize(newfi,baseUrl);
			if(newfiObject.user.internalUserDetail!= undefined &&!newfiObject.user.internalUserDetail.lqbUsername){
				window.location.hash="#myProfile";
			}
			isAgentTypeDashboard = true;
			paintAgentDashboard('myloans');
			retrieveState();
			$(window).resize(function() {
				adjustAgentDashboardOnResize();
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
				adjustCustomerApplicationPageOnResize();
				adjustFooter();
			});
			
			//TODO added for loan profile page		
			var initialText =  getInitials(newfi.user.firstName,newfi.user.lastName) ;
             if(newfi.user.photoImageUrl == "" || newfi.user.photoImageUrl == null){
            	 $("#myProfilePicture").addClass("lp-initial-pic float-left").text(initialText);
				
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
				var fileName=$("#prof-image").val();
	            var status=validatePhotoExtention(fileName);
				if(status!=false){				
				initiateJcrop(this);				
				}
			});
			
			$('[data-toggle="tooltip"]').tooltip();   
			
			
		});
		function adjustFooter(){
			var height=window.innerHeight;
			var footerHeight=$('.footer-wrapper').height();
			var headerHeight=$('.header-wrapper').height();
			height=height-headerHeight;
			$('.content').css("height",height+ "px");
		}
	</script>
</body>
</html>