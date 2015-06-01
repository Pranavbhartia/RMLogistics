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
<link href="${initParam.resourcesPath}/resources/css/jquery.Jcrop.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles-common.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-engagement.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/customer-application.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">


</head>
<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container home-container_CEP">
		<div class="container-row row clearfix">
			<div id="ce-main-container"></div>			
		</div>
	</div>
	<jsp:include page="customer-engagement-footer.jsp"></jsp:include>
	<script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery-ui.js"></script>
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
					paintRefinanceStep3();
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
					paintRefinanceHomeZipCode();
				}
			});
			
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
					paintHomeZipCode();
				}
			});
			/*
			$(document).on('click','#homeProgressBaarId_5',function(){
				if(active>4){
					homeProgressBaar(5);
					paintBuyHomeSSN();
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
			    }if($(this).val().length >= 5){
			    	
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

		});
		function adjustInlineFooter(){

			var height=window.innerHeight;
			var footerHeight=$('.footer-wrapper').height();
			var headerHeight=$('.header-wrapper').height();
			height=height-headerHeight;
			$('.home-container').css("min-height",height+ "px");
		}
	</script>
	
	
</body>
</html>