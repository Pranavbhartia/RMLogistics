<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Nexera</title>
		<link href="resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/css/jquery-ui.css" rel="stylesheet">
		<link href="resources/css/dropzone.css" rel="stylesheet">
		<link href="resources/css/style-resp.css" rel="stylesheet">
	    <link href="resources/css/styles.css" rel="stylesheet">
	    <link href="resources/css/style-resp.css" rel="stylesheet">
	</head>
	
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<div id="main-body-wrapper">
			<!-- Include main body in this container -->
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
		<script>
				
			$(document).ready(function(){
				changeLeftPanel(2);
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
				$(window).resize(function(){
					adjustCenterPanelWidth();
					adjustRightPanelOnResize();
				});
				$(document).on('click','.small-screen-menu-icon',function(e){
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