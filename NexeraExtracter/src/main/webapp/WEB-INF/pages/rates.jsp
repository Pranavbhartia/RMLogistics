<!DOCTYPE html>
<html>
<head>
<title>Nexera</title>
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/styles.css">
<link rel="stylesheet" href="resources/css/styles-print.css">
</head>
<body>
	<div class="">
		<div class="header-wrapper">
			<div class="header-container container">
				<div class="header-row row clearfix">
					<div class="header-logo float-left"></div>
				</div>
			</div>
		</div>
		<div class="body-wrapper">
			<div class="container body-header-container">
				<div class="body-header-row row clearfix">
					<div class="float-left header-txt">
						NOTE: INDICATIVE PRICING ONLY - BASED ON $300K LOAN AMOUNT<br />
						PLEASE LOG INTO BLUSTREAM PORTAL TO PORTAL TO OBTAIN LIVE LOCK
						PRICING
					</div>
					<div class="float-right print" onclick="window.print()" title="print"></div>
				</div>
			</div>


			<div id="main-container" class="main-container container"></div>
		</div>
	</div>



	<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="resources/js/script.js"></script>
	<script type="text/javascript" src="resources/js/masonry.pkgd.min.js"></script>
	<script>
		$(document).ready(function() {
			$.ajax({
				url : "rest/rates"
			}).then(function(data) {
				paintRatesTablePage(JSON.parse(data));
			});

		});
	</script>
</body>
</html>