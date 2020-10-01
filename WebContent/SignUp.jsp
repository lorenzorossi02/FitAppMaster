<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/SignUpServlet");
    %>
<!DOCTYPE html>
<html style="height: 1080px;background-image: url(assets/img/SignUp.jpg);width: 1920px;background-repeat: space;background-size: contain;">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>FitApp - Sign Up Page</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css?h=366764a99f80ac5cbe6d2af50bbf485a">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic">
    <link rel="stylesheet" href="assets/css/styles.min.css?h=9058ef3d9a75fb28db69862b62a254b2">
</head>

<body style="background-color: Transparent;height: 240px;width: 1920;min-height: 504px;">
    <!-- Start: Newsletter Subscription Form -->
    <div class="SignUp" style="width: 787px;margin: 393px;margin-right: 381px;margin-left: 570px;height: 385px;opacity: 0.94;background-color: #FFFFFF;margin-bottom: 405px;margin-top: 377px;">
        <div class="container" style="padding: 0px;"><a href="index.jsp" style="margin-top: 63px;margin-bottom: 69px;margin-right: 24px;opacity: 0.79;font-size: 22px;background-position: left;padding: 3px;padding-top: -22px;padding-bottom: 9px;">Go Back</a>
            <!-- Start: Intro -->
            <div class="intro" style="margin-top: -21px;">
                <h2 class="text-center" style="margin: 0px;margin-top: -65px;font-size: 38px;height: 38px;margin-bottom: 8px;padding: 70px;width: 779px;min-height: 0px;max-height: 0px;opacity: 0.83;margin-left: 8px;">Sign up on FitApp!</h2>
                <p class="text-center" style="font-size: 25px;">In order to enjoy FitApp experience you have to insert your email address in the following field. We will send you all the information to login in our app.</p>
            </div>
            <!-- End: Intro -->
            <form action="SignUpServlet" name="signupform" class="form-inline" method="POST">
                <div class="form-group"><input class="form-control" type="email" name="email" id="email"  placeholder="Your Email" style="background-repeat: no-repeat;background-size: contain;margin-left: 135px;margin-top: 25px;font-size: 28px;"></div>
                <div class="form-group"><button class="btn btn-primary" name="SignUpBtn"  type="submit" style="margin-top: 16px;margin-left: 60px;font-size: 29px;background-color:#214a80;">Sign up&nbsp;</button></div>
            </form>
        </div>
    </div>
    <!-- End: Newsletter Subscription Form -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</body>

</html>