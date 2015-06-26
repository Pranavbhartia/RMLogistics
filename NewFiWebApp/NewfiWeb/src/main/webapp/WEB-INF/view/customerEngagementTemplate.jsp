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
<link href="${initParam.resourcesPath}/resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-engagement.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-application.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">


</head>
<body>
	<div id="overlay-loader" class="overlay-loader hide">
				<div id="overlay-loader-text"></div>
	</div>
	<div class="header-wrapper">
	<div class="header-container container">
		<div class="header-row row clearfix">
			<!-- <div class="header-logo float-left"></div> -->
			<div class="header-logo float-left" onclick="window.location='https://www.newfi.com/'"></div>
			<div class="reg-header-btns-wrapper float-right clearfix">
				<!--  <div class="float-left login-hdr hdr-signup-btn" onclick="window.location='customerEngagement.do'">Check Rates</div>-->
                <div class="float-left login-hdr hdr-login-btn" onclick="window.location='${baseUrl}'">Login</div>
			</div>
            <div class="soft-menu-icon float-right"></div>
            <div class="soft-menu-wrapper">
                <div class="soft-menu-hdr cursor-pointer" onclick="window.location='${baseUrl}'">Login</div>
				<%-- <div class="soft-menu-hdr cursor-pointer" onclick="window.location='${baseUrl}+/customerEngagement.do'">Check Rates</div> --%>
            </div>
            <a href="tel:1-888-316-3934">
             <div class="float-right login-hdr hdr-contact-no">888-316-3934</div>
              <div class="float-right hdr-contact-icon"></div>
              </a>
		</div>
	</div>
</div>
<div id="overlay-toast" class="overlay-toast-wrapper">
	<div id="overlay-toast-txt" class="overlay-toast-txt hide"></div>
	<div id="overlay-toast-error-txt" class="overlay-toast-txt hide"></div>
</div>
	<div class="home-container container home-container_CEP">
		<div class="container-row row clearfix">
			<div id="ce-main-container"></div>			
		</div>
	</div>
	<jsp:include page="login-inline-footer.jsp"></jsp:include>
	<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery-ui.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery.mask.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/bootstrap.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/newfiModel.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/common.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/teaserRate.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/script.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/customer-application.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/customer-engagement.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/buyHome.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/validation.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/message.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/include/jquery-maskMoney.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/historySupport.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/ratePage.js"></script>
	<script>
		resizeHeaderWidth();
		$(window).resize(function() {
			resizeHeaderWidth();	
		});
	</script>
		
	<script>
	
	
		$(document).ready(function() {
			globalBinder();
			adjustCustomerEngagementPageOnResize();
			$(window).resize(function(){
				adjustCustomerEngagementPageOnResize();
				adjustInlineFooter();
			});
			adjustInlineFooter();
			/* $(document).on('keydown','input[name="currentMortgageBalance"]',function(){
				$('input[name="currentMortgageBalance"]').maskMoney({
					thousands:',',
					decimal:'.',
					allowZero:true,
					prefix: '$',
				    precision:0,
				    allowNegative:true
				});				
			}) */
			
			paintSelectLoanTypeQuestion();
			
			$(document).on('click','#progressBaarId_1',function(){
				removeToastMessage();
				paintRefinanceMainContainer();
			});
			$(document).on('click','#progressBaarId_2',function(){
				removeToastMessage();
				if(stages>1){
					progressBaar(2);
					//paintRefinanceLiveNow();
					paintRefinanceStep2();
				}
			});
			$(document).on('click','#progressBaarId_3',function(){
				removeToastMessage();
				if(stages>2){
					progressBaar(3);
					//paintRefinanceStartLiving();
					paintRefinanceStepCEP();
				}
			});
			$(document).on('click','#progressBaarId_4',function(){
				removeToastMessage();
				if(stages>3){
					progressBaar(4);
					paintRefinanceHomeWorthToday();
				}
			});
			$(document).on('click','#progressBaarId_5',function(){
				removeToastMessage();
				if(stages>4){
					progressBaar(5);
					paintNewResidenceTypeQues();
				}
			});
			$(document).on('click','#progressBaarId_6',function(){
				removeToastMessage();
				if(stages>5){
					progressBaar(6);
					paintRatesPageFromCrumb();
				}
			});
			/*$(document).on('click','#progressBaarId_7',function(){
				removeToastMessage();
				if(stages>6){
					progressBaar(7);
					paintRatesPageFromCrumb();
				}
			});*/
			
			//--- Buy home progress baar button 
			
			$(document).on('click','#homeProgressBaarId_1',function(){
				removeToastMessage();
				paintBuyHomeContainer();
			});
			$(document).on('click','#homeProgressBaarId_2',function(){
				removeToastMessage();
				if(active>1){
					homeProgressBaar(2);
					paintRentOfYourHouse();
				}
			});
			$(document).on('click','#homeProgressBaarId_3',function(){
				removeToastMessage();
				if(active>2){
					homeProgressBaar(3);
					paintNewResidenceTypeQues();
				}
			});
			 $(document).on('click','#homeProgressBaarId_4',function(){
				 removeToastMessage();
				if(active>3){
					homeProgressBaar(4);
					paintRatesPageFromCrumb();
				}
			});
			
			/*$(document).on('click','#homeProgressBaarId_5',function(){
				if(active>4){
					homeProgressBaar(5);
					paintRatesPageFromCrumb();
				}
			}); */
			
			$(document).on('keypress','input[name="yearLeftOnMortgage"]',function(e){
				
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			        //display error message
			        $("#errmsg").html("Digits Only").show().fadeOut("slow");
			          return false;
			    }
			});
		
		 $(document).on('keypress','input[name="zipCode"]',function(e){
				
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) ) {
			        //display error message
			        //showToastMessage("Enter correct zipcode");
			          return false;
			    }				if($(this).val().length >= 5){
			    	
			    	// showToastMessage("Enter correct zipcode");
			         return false;
			    }
			});  
			
		 $(document).on('keypress','input[name="propZipCode"]',function(e){
				
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) ) {
			        //display error message
			        //showToastMessage("Enter correct zipcode");
			          return false;
			    }				if($(this).val().length >= 5){
			    	
			    	// showToastMessage("Enter correct zipcode");
			         return false;
			    }
			});  
		 
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

			 $('#footer-wrapper').show();
			
		});
		function adjustInlineFooter(){
			var height=window.innerHeight;
			var footerHeight=$('.footer-wrapper').height();
			var headerHeight=$('.header-wrapper').height();
			height=height-headerHeight - footerHeight;
			$('.home-container').css("min-height",height+ "px");
		}
	</script>
	
	
</body>
</html>