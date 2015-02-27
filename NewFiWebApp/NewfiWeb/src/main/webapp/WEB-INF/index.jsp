<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>Signin Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="resources/js/jquery-2.1.3.min.js"></script>

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<script type="text/javascript">

$(document).ready(function() {
	$('#loginForm').submit(function() {
		var userName = $("#inputEmail").val();
		 var dateVar = new Date();
			var timezone = dateVar.getTimezoneOffset(); 
		 $("#inputEmail").val(userName + ":"+timezone);
		 
		 return true;
		});
});

	
</script>

<body>

	<div class="container">


		<h3>Login with Username and Password</h3>
 
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form id="loginForm"
		  action="<c:url value="j_spring_security_check" />" method='POST'>
			<h2 class="form-signin-heading">Please sign in</h2>
			<label for="inputEmail" class="sr-only">Email address</label> <input
				type="email" id="inputEmail" name="j_username" class="form-control"
				placeholder="Email address" required autofocus> <label
				for="inputPassword"  class="sr-only">Password</label> <input
				type="password" name="j_password" id="inputPassword" class="form-control"
				placeholder="Password" required>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
				
			</div>
			<input id="offset" type="hidden" name="offset"  />	
				<input type="submit" value="Sign in " class="btn btn-lg btn-primary btn-block" />
			<a href="customerPage.do" >Redirect to Nexera</a>
		</form>

	</div>
	<!-- /container -->


	
</body>
</html>
