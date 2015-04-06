<!DOCTYPE html>
<html>
    <head>
        <title>Nexera</title>
        <link rel="stylesheet" href="resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/styles.css">
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
            	<div class="print" onclick="window.print()" title="print"></div>
            
                <div id="main-container" class="main-container container"></div>
            </div>
        </div>
        
        
        
        <script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="resources/js/script.js"></script>
        <script type="text/javascript" src="resources/js/masonry.pkgd.min.js"></script>
        <script>
            $(document).ready(function(){
            	 $.ajax({
            	        url: "rest/rates"
            	    }).then(function(data) {
            	    	paintRatesTablePage(JSON.parse(data));
            	    });
            	
                
            });
        </script>
    </body>
</html>