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
<link href="${initParam.resourcesPath}/resources/css/font-awesome.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/perfect-scrollbar.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/datepicker.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/dropzone.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-application.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-engagement.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-admin.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
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
      
        $('#right-panel').css('min-height','100%');	
		
		$('#profilePhoneNumId').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
		$('.assigned-agent-contact').html(formatPhoneNumberToUsFormat($('#profilePhoneNumId').html()));
			initialize(newfi,baseUrl);
			if(newfiObject.user.internalUserDetail!= undefined &&!newfiObject.user.internalUserDetail.lqbUsername){
				showOverlay();
				window.location.hash="#myProfile";
				setTimeout(function(){showToastMessage("Please Provide LQB Credentials")},2000)
				hideOverlay();
			}
			if(newfiObject.user.userRole.id==2){
				window.location.hash="#loan";
			}
			//NEXNF-810
			if(newfi.user.userRole.id==2||newfiObject.user.userRole.id==4){
				$('#lp-item-header').show();
				$('#lp-item-header').html('my pipeline');
			}	else if(newfi.user.userRole.roleCd=="INTERNAL"){
				/* if(newfi.user.internalUserDetail.internalUserRoleMasterVO.id==2){
					$('#lp-item-header').html('pipeline');
				}else {
					$('#lp-item-header').html('my pipeline');
				} */
				$('#internal-usr-loan-label').show();
				$('#lp-my-loans').addClass('lp-item-body-top-row-adj');
				$('#lp-my-loans').addClass('lp-my-loan');
			}
			//NEXNF-810
			if(newfi.user.userRole.roleCd=="INTERNAL"){
				/* if(newfi.user.internalUserDetail.internalUserRoleMasterVO.id==2){
					$('#lp-item-header-archives').html('archives');
				}else {
					$('#lp-item-header-archives').html('my archives');
				} */
				$('#internal-usr-label').show();
				$('#lp-my-loans').addClass('lp-item-adj');
				$('#lp-my-archives').addClass('lp-my-archives');
			}else {
				$('#lp-item-header-archives').show();
				$('#lp-item-header-archives').html('my archives');
				
			}
			
			isAgentTypeDashboard = true;
			paintAgentDashboard('myloans');
			retrieveState();
			$(window).resize(function() {
				adjustAgentDashboardOnResize();
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
				adjustCustomerApplicationPageOnResize();
				
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
			
			$(document).on('change', '#prof-image', function(e) {
				e.stopImmediatePropagation();
				var fileName=$("#prof-image").val();
				if(fileName!=""){
					  var status=validatePhotoExtention(fileName);
						if(status!=false){				
							initiateJcrop(this);				
						}
				}
	          
				
			});
			
			$('[data-toggle="tooltip"]').tooltip();   
			 $(document).on('keypress','input[name="coBorrowerZipCode"]',function(e){
					
					if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) ) {
				        //display error message
				        //showToastMessage("Enter correct zipcode");
				          return false;
				    }				if($(this).val().length >= 5){
				    	
				    	// showToastMessage("Enter correct zipcode");
				         return false;
				    }
				}); 
			 
		/* 	 if(newfiObject.user.userRole.id==2){
				 $('.lp-item-body.lp-agent-item2').addClass('append-home-icon');
			 } */
			 //NEXNF-744
			 
			 $('.lp-item-body.lp-agent-item2').addClass('append-home-icon');

			 $("#icn-my-leads").removeClass('append-home-icon');
			 $("#icn-my-leads").addClass('append-leads-icn');

			 $('#footer-wrapper').show();
		});
		
	</script>
</body>
</html>