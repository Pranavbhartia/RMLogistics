<script src="resources/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		url : "rest/commlog/001/001/1",
		type : "GET",
		dataType : "json",
		cache : false,
		data : "",
		contentType : "application/json",
		success : function(response) {
			$('#responseFromMongo').html(response.error.code);
		},

		complete:function(response){
			$('#responseFromMongo').html(response.error.code);
		},
		error : function() {
			$('#responseFromMongo').html("500");
		}
	});
});
</script>
<div id="responseFromMongo">
</div>