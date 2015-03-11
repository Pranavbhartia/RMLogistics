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
</head>

<body>
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
			initialize(newfi);
			isAgentTypeDashboard = true;
			paintAgentDashboard();
			bindDataToPN();
			retrieveState();
			$(window).resize(function() {
				adjustAgentDashboardOnResize();
				adjustCenterPanelWidth();
			});
		});
	</script>
</body>
</html>