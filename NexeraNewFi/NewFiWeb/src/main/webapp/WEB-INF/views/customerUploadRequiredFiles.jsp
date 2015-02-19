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
		<div class="home-container container">
		<div class="container-row row clearfix">
		<jsp:include page="customerViewLeftPanel.jsp"></jsp:include>
		<div class="right-panel float-left">
			<jsp:include page="customerViewSecondaryLeftPanel.jsp"></jsp:include>
			<div id="center-panel-cont" class="center-panel float-left">
					<div id="fileUpload" class="dropFileUpload">
						<form action="newfi/file-upload"
    						  class="dropzone" method="post" class="my-awesome-dropzone" enctype="multipart/form-data"
     								 id="dropzoneForm">
     					 </form>
					</div>
			</div>
		</div>
	</div>
</div>
		<jsp:include page="footer.jsp"></jsp:include>
		<script type="text/javascript">
		
		 Dropzone.options.dropzoneForm = {
		            init: function () {
		                this.on("complete", function (data) {
		                    //var res = eval('(' + data.xhr.responseText + ')');
		                     var res = JSON.parse(data.xhr.responseText);
		                     alert(""+res);
		                });
		            }
		        };
				
		</script>
	</body>
</html>