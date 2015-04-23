<!DOCTYPE html>
<html>
<head>
<title>Blustream Lending Rate Sheet</title>
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/styles.css">
<link rel="stylesheet" href="resources/css/styles-print.css">
</head>
<body>
	<div class="">
		<div id="header-wrapper" class="header-wrapper">
			<div class="header-container container">
				<div class="header-row row clearfix">
					<div id="blu-stream-link" class="header-logo float-left"></div>
					<div class="addr-blustream float-right">
						<div>Blustream Lending Website Pricing</div>
						<div>2200 Powell Street, Suite 340</div>
						<div>Emeryville, CA 94608</div>
						<div>510-844-4003</div>
					</div>
				</div>
			</div>
			<div class="container body-header-container">
				<div class="body-header-row row clearfix">
					<div class="float-left header-txt">
						NOTE: INDICATIVE PRICING ONLY <br />
						PLEASE LOG INTO BLUSTREAM PORTAL TO OBTAIN LIVE LOCK
						PRICING - <a target="_blank" href="http://www.blustream.com">www.blustream.com</a>
					</div>
					<div class="">
							<div class="clearfix ">
									<div class="float-left">
										<div> Lending rates as of : <span id="folderCurrentTimeStamp"></span></div>
									</div>
									<div class="float-right printIcon" >
										<div class="print" onclick="window.print()" title="print"></div>
									</div>
							</div>
					</div>
				</div>
			</div>
		</div>
		<div class="body-wrapper">
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

			
			$('#blu-stream-link').click(function(){
				window.open('http://www.blustream.com','_blank');
			});
			
		});
	</script>
</body>
</html>