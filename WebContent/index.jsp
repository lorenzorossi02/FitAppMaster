<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="org.slf4j.*"%>
 <%
      if (session != null) {
         if (session.getAttribute("user") != null) {
            String name = (String) session.getAttribute("user");
            out.print("Hello, " + name + "  Welcome to ur Profile");
         } 
      }
   %>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>FitApp - Login Page</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
<link rel="stylesheet" href="assets/css/Header-Blue.css">
<link rel="stylesheet" href="assets/css/Login.css">
<link rel="stylesheet" href="assets/css/styles.css">

</head>


<body style="background-image: url(assets/img/usrPage.jpg);">

	<!-- Start: Login Form Dark -->
	<div class="login-dark"
		style="background-image: url(assets/img/gympage.jpg);">
		<form action="LoginServlet" name="myform" method="POST"
			style="width: 537px; height: 560px; color: rgb(255, 255, 255); background-color: #f7fffe; padding: 20px; opacity: 0.96; min-width: 451px;">
			<h3 style="color: rgb(100, 137, 180); margin-left: 85px;">Welcome
				to FitApp!</h3>
			<div class="illustration">
				<img src="assets/img/fitApp_LogIn.png"
					style="width: 154px; height: 118px;">
			</div>
			<div class="form-group">
				<input class="form-control" type="text" name="username"
					placeholder="Username" id="username" style="color: rgb(0, 0, 0);">
			</div>
			<div class="form-group">
				<input class="form-control" type="password" name="password"
					placeholder="Password" id="password" style="color: rgb(0, 0, 0);">
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-block" name="Login" id="Login"
					type="submit">Log In</button>
			</div>
			<a class="forgot" href="#">Forgot your email or password?</a>
			<button class="btn btn-primary btn-block" name="SignUp" id="SignUp"
				type="submit">Sign Up</button>
		</form>
	</div>
	<!-- End: Login Form Dark -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</body>


</html>