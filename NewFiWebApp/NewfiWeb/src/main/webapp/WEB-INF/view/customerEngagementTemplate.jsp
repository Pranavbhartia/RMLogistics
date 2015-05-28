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
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/styles-common.css" rel="stylesheet">
<link href="resources/css/customer-engagement.css" rel="stylesheet">
<link href="resources/css/customer-application.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<link href="resources/css/footer.css" rel="stylesheet">
<link href="resources/css/jquery.Jcrop.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container home-container_CEP">
		<div class="container-row row clearfix">
			<div id="ce-main-container"></div>			
		</div>
	</div>
	<footer class="footer-wrapper cust-eng" id="footer-wrapper">
    	<div class="footer-wrapper-container container">	
        	<div class="footer-text1 float-left">
            	<div class="footer-text">
                	� 2015 newfi dba of Nexera Holding LLC | All Rights Reserved | NMLS ID 1231327
    			</div>
    			<div class="footer-text-links">
         			<a class="footer-inline-txt" href="http://www.nexeraholding.com/information/#licensing" target="_blank">Licensing</a><a class="footer-inline-txt" href="http://www.nmlsconsumeraccess.org/" target="_blank">NMLS Consumer Access</a><a class="footer-inline-txt" href="http://www.nexeraholding.com/privacy-policy/" target="_blank">Privacy Policy</a><a class="footer-inline-txt" href="http://www.nexeraholding.com/information/" target="_blank">Terms of Use</a>
    			</div>
  			</div>		
   			<div class="footer-text2 float-right">
   				<a href="http://portal.hud.gov/hudportal/HUD" target="_blank">
   					<div class="footer-inline-home-image" id="footeHomeImage"></div>
   				</a>
  				<a href="http://portal.hud.gov/hudportal/HUD" target="_blank">
  					<div class="footer-inline-home-text" id="footerHomeText">Equal Housing Lender</div>
   				</a>  
  			</div>
		</div>
	</footer>
	<script src="resources/js/jquery-2.1.3.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/teaserRate.js"></script>
	<script src="resources/js/script.js"></script>
	<script src="resources/js/customer-application.js"></script>
	<script src="resources/js/customer-engagement.js"></script>
	<script src="resources/js/buyHome.js"></script>
	<script src="resources/js/validation.js"></script>
	<script src="resources/js/message.js"></script>
	<script src="resources/js/include/jquery-maskMoney.js"></script>
	<script src="resources/js/historySupport.js"></script>
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
				paintRefinanceMainContainer();
			});
			$(document).on('click','#progressBaarId_2',function(){
				if(stages>1){
					progressBaar(2);
					//paintRefinanceLiveNow();
					paintRefinanceStep2();
				}
			});
			$(document).on('click','#progressBaarId_3',function(){
				if(stages>2){
					progressBaar(3);
					//paintRefinanceStartLiving();
					paintRefinanceStep3();
				}
			});
			$(document).on('click','#progressBaarId_4',function(){
				if(stages>3){
					progressBaar(4);
					paintRefinanceHomeWorthToday();
				}
			});
			$(document).on('click','#progressBaarId_5',function(){
				if(stages>4){
					progressBaar(5);
					paintNewResidenceTypeQues();
				}
			});
			$(document).on('click','#progressBaarId_6',function(){
				if(stages>5){
					progressBaar(6);
					paintRefinanceHomeZipCode();
				}
			});
			
			//--- Buy home progress baar button 
			
			$(document).on('click','#homeProgressBaarId_1',function(){
				paintBuyHomeContainer();
			});
			$(document).on('click','#homeProgressBaarId_2',function(){
				if(active>1){
					homeProgressBaar(2);
					paintRentOfYourHouse();
				}
			});
			$(document).on('click','#homeProgressBaarId_3',function(){
				if(active>2){
					homeProgressBaar(3);
					paintNewResidenceTypeQues();
				}
			});
			 $(document).on('click','#homeProgressBaarId_4',function(){
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
			

		});
	</script>
	
	
</body>
</html>