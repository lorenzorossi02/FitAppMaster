<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>LoginForm</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="/assets/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="/assets/css/styles.css">
</head>

<body style="background-image: url(&quot;/assets/img/usrPage.jpg&quot;);">
    <div class="login-dark" style="background-image: url(&quot;/assets/img/gympage.jpg&quot;);">
        <form action="LoginServlet" method="post" style="width: 537px;height: 560px;color: rgb(255,255,255);background-color: #f7fffe;padding: 20px;opacity: 0.96;min-width: 451px;">
            <h1 style="color: rgb(100,137,180);margin-left: 20px;">Welcome to FitApp!</h1>
            <div class="illustration"><img src="/assets/img/fitApp_LogIn.png" style="width: 154px;height: 118px;"></div>
            <div class="form-group"><input class="form-control" type="email" name="username" placeholder="Username"></div>
            <div class="form-group"><input class="form-control" type="password" name="password" placeholder="Password"></div>
            <div class="form-group"><button class="btn btn-primary btn-block" type="submit">Log In</button></div><a class="forgot" href="#">Forgot your email or password?</a><button class="btn btn-primary" type="button" style="width: 411px;">Sign Up</button></form>
    </div>
    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>