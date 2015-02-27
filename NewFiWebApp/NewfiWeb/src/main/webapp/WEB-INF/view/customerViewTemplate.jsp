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
<link href="resources/css/datepicker.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="home-container container">
		<div class="container-row row clearfix">
			<jsp:include page="customerViewLeftPanel.jsp"></jsp:include>
			<div id="right-panel"></div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script>
		$(document).ready(function() {
			changeLeftPanel(2);
			adjustCenterPanelWidth();
			adjustRightPanelOnResize();
			$(window).resize(function() {
				adjustCenterPanelWidth();
				adjustRightPanelOnResize();
			});

			//Assign values to primary navigation
			var divArray = $('.left-panel >div');
			for ( var div in divArray) {
				var id = $(div).attr('id');
				switch (id) {
				case "lp-customer-profile":
					$.data(div, "enum", {
						pnName : PNEnum.PROFILE

					});
					break;
				case "lp-talk-wrapper":
					$.data(div, "enum", {
						pnName : PNEnum.TEAM

					});
					break;
				case "lp-loan-wrapper":
					$.data(div, "enum", {
						pnName : PNEnum.LOAN

					});
					break;
				default:
					break;
				}
			}

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
		});
	</script>
</body>
</html>