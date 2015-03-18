<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nexera</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<link href="resources/css/customer-engagement.css" rel="stylesheet">
</head>

<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<div id="ce-main-container"></div>			
		</div>
	</div>
	<script src="resources/js/jquery-2.1.3.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/teaserRate.js"></script>
	<script src="resources/js/script.js"></script>
	<script src="resources/js/customer-engagement.js"></script>
	<script src="resources/js/buyHome.js"></script>
	<script>
		$(document).ready(function() {
			paintSelectLoanTypeQuestion();
			
			$(document).on('click','#progressBaarId_1',function(){
				paintRefinanceMainContainer();
			});
			$(document).on('click','#progressBaarId_2',function(){
				if(stages>1){
					progressBaar(2);
					paintRefinanceLiveNow();
				}
			});
			$(document).on('click','#progressBaarId_3',function(){
				if(stages>2){
					progressBaar(3);
					paintRefinanceStartLiving();
				}
			});
			$(document).on('click','#progressBaarId_4',function(){
				if(stages>3){
					progressBaar(4);
					paintRefinanceMyIncome();
				}
			});
			$(document).on('click','#progressBaarId_5',function(){
				if(stages>4){
					progressBaar(5);
					paintRefinanceDOB();
				}
			});
			
			//--- Buy home progress baar button 
			
			$(document).on('click','#homeProgressBaarId_1',function(){
				paintBuyHomeContainer();
			});
			$(document).on('click','#homeProgressBaarId_2',function(){
				if(active>1){
					homeProgressBaar(2);
					paintBuyHomeRenting();
				}
			});
			$(document).on('click','#homeProgressBaarId_3',function(){
				if(active>2){
					homeProgressBaar(3);
					paintBuyHomEachMonthrent();
				}
			});
			$(document).on('click','#homeProgressBaarId_4',function(){
				if(active>3){
					homeProgressBaar(4);
					paintBuyHomeMyIncome();
				}
			});
			$(document).on('click','#homeProgressBaarId_5',function(){
				if(active>4){
					homeProgressBaar(5);
					paintBuyHomeSSN();
				}
			});
			
			

		});
	</script>
</body>
</html>