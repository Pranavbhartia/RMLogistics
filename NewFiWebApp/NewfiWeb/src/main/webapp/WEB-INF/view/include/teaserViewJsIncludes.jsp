
<%@ page isELIgnored="false"%>
<%@ page import="java.util.Date"%>

<%
	//JSP code
	long ts = (new Date()).getTime(); //Used to prevent JS/CSS caching
%>
<script src="resources/js/jquery-2.1.3.min.js"></script>
<script src="resources/js/jquery-ui.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/script.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
<script
	src="resources/js/dynamicQuiz-config.js?<%=ts %>"></script>
<script type="text/javascript"
	src="resources/js/DynamicQuizCreator.js?<%=ts %>"></script>