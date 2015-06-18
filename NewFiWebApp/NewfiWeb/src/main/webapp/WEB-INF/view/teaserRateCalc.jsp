<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>

<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="${initParam.resourcesPath}/resources/images/newfiHome.ico">
<jsp:include page="include/commonStyles.jsp"></jsp:include>
 <script src="${initParam.resourcesPath}/resources/js/message.js"></script>
<script src="/NewfiWeb/${initParam.resourcesPath}/resources/js/historySupport.js"></script>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>

	<br />
	<br />
	<br />
	<br />


	<form action="test" method="get" id="formStepOne">
		<div id="dynamicQuizStepOne"></div>

		<input type="submit" value="Submit">
	</form>



	<hr>

	<hr>

	<hr>

	<form action="test" method="get" id="formStepTwo">
		<div id="dynamicQuizStepTwo"></div>

		<input type="submit" value="Submit">
	</form>

<jsp:include page="inlineFooter.jsp"></jsp:include>
	<jsp:include page="include/teaserViewJsIncludes.jsp"></jsp:include>

</body>

<script>
	$(document).ready(function() {
		globalBinder();
		console.log("Document ready.");
		$("#dynamicQuizStepOne").dynamicQuiz(quizJSONStepOne);

		$("#dynamicQuizStepTwo").dynamicQuiz(quizJSONStepTwo);
		$.validate({
			modules : 'location, date, security, file',
			onModulesLoaded : function() {

				console.log("Validator loaded");
			}
		});
		$('#footer-wrapper').show();
	});
</script>
</html>