<!DOCTYPE html>
<html>
<head>
<title>Blustream Lending Rate Sheet</title>
<meta http-equiv="refresh" content="300">
<link rel="stylesheet" href="${baseURL}resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${baseURL}resources/css/styles.css">
<link rel="stylesheet" href="${baseURL}resources/css/styles-print.css">
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
										<div>Rates are subject to change without notice</div>
										<div class="rates-text-three">standard conforming >= $300k receives .25 better in ysp!</div>
									</div>
									<div class="float-right " >
										<div class="print" onclick="window.print()" title="print"></div>
									</div>
									<div class="float-right " >
										<div class="downloadExcel" onclick="downloadExcel()" title="print"></div>
									</div>
									<br /><br />
									<div class="table-rate-new float-right">
										<div class="float-left">
											<div class="rate-row">Locks Accepted:</div>
											<div class="rate-row">Fees:</div>
										</div>	
										<div class="float-right">							
											<div class="rate-row">M-F 8:30AM - 4:30PM</div>
											<div class="rate-row">Admin - $1050</div>
											<div class="rate-row">Flood - $10</div>
										</div>
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
	
	<div id="error-message-display" class="error-message" style="display: none">
		<tr>"Thank you for visiting us. We are facing some technical issue and will be back soon"</tr>
	</div>



	<script type="text/javascript" src="${baseURL}resources/js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="${baseURL}resources/js/script.js"></script>
	<script type="text/javascript" src="${baseURL}resources/js/masonry.pkgd.min.js"></script>
	<script>
	function downloadExcel(){
		location.href = "${baseURL}downloadXLS";
	}
		$(document).ready(function() {
			$.ajax({
				url : "${baseURL}rest/rates"
			}).then(function(data) {
				var jsonData = JSON.parse(data);
				if(!jsonData.fileDetailList || jQuery.isEmptyObject(jsonData.fileDetailList)){
					$('#error-message-display').show();
					return;
				}
				
				paintRatesTablePage(JSON.parse(data));
			});

			
			$('#blu-stream-link').click(function(){
				window.open('http://www.blustream.com','_blank');
			});
			
		});
	</script>
</body>
</html>