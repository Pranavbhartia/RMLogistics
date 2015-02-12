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
	</head>
	
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<jsp:include page="cutomerViewMainBody.jsp"></jsp:include>
		<jsp:include page="footer.jsp"></jsp:include>
		<script>
				
			$(document).ready(function(){
				//Display customer rate page
				paintFixYourRatePage();
				adjustCenterPanelWidth();
				$(window).resize(function(){
					adjustCenterPanelWidth();
				});
				$('#rate-slider').slider({
					orientation : "horizontal",
					range : "min",
					max : 100,
					value : 40
				});
				$('#tenure-slider').slider({
					orientation : "horizontal",
					range : "min",
					max : 30,
					value : 10
				});
				function adjustCenterPanelWidth(){
					if(window.innerWidth <= 1200 && window.innerWidth >= 768){
						var leftPanelWidth = $('.left-panel').width();
						var leftPanelTab2Width = $('.lp-t2-wrapper').width();
						var centerPanelWidth = $(window).width() - (leftPanelWidth + leftPanelTab2Width) - 35;
						$('.center-panel').width(centerPanelWidth);
					}
					else if(window.innerWidth < 768){
						var leftPanelTab2Width = $('.lp-t2-wrapper').width();
						var centerPanelWidth = $(window).width() - (leftPanelTab2Width) - 35;
						$('.center-panel').width(centerPanelWidth);
					}
				}
				$('.small-screen-menu-icon').click(function(e){
					e.stopImmediatePropagation();
					$('.left-panel').toggle();
				});
				$(document).click(function(){
					if($(window).width() <= 768){
						if($('.left-panel').css("display") == "block"){
							$('.left-panel').toggle();
						}
					}
				});
			});
		</script>
	</body>
</html>