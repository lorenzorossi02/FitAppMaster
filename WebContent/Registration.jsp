<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Fit App - Registration Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Registration.css">
</head>

<body style="background-image: url(assets/img/RegisterSideLeft.jpg);min-height: 686px;max-height: 125px;height: 708px;">
    <!-- Start: Pretty Registration Form -->
    <div class="row register-form" style="margin: 0px;margin-top: 19px;margin-left: 10px;min-height: 0px;max-height: 0px;">
        <div class="col-md-8 offset-md-2" style="margin-left: 302px;padding-right: 0px;padding-left: 15px;margin-top: 69px;height: 868px;min-height: 868px;max-height: 868px;">
            <form action="RegistrationServlet" name="myform"  method="POST" class="custom-form" style="width: 1188px;height: 778px;">
                <h1 style="margin-bottom: 25px;margin-top: -59px;">FitApp registration</h1>
                <div class="form-row form-group">
                    <div class="col-sm-4 label-column"><label class="col-form-label"  for="name-input-field">Username&nbsp;</label></div>
                    <div class="col-sm-6 input-column"><input class="form-control" type="text" name="username" id="username" placeholder="Username"></div>
                </div>
                <div class="form-row form-group">
                    <div class="col-sm-4 label-column"><label class="col-form-label" for="email-input-field">Email </label></div>
                    <div class="col-sm-6 input-column"><input class="form-control" name="email" id="email" type="email" placeholder="<%=request.getSession().getAttribute("email")%>" readonly disabled></div>
                </div>
                <div class="form-row form-group">
                    <div class="col-sm-4 label-column"><label class="col-form-label" for="email-input-field">Confirm Email&nbsp;</label></div>
                    <div class="col-sm-6 input-column"><input class="form-control" name="confirmEmail" id="confirmEmail" type="email" placeholder="Confirm Email"></div>
                </div>
                <div class="form-row form-group">
                    <div class="col-sm-4 label-column"><label class="col-form-label" for="pawssword-input-field">Password </label></div>
                    <div class="col-sm-6 input-column"><input class="form-control" name="password" id="password" type="password" placeholder="Password"></div>
                </div>
                <div class="form-row form-group">
                    <div class="col-sm-4 label-column"><label class="col-form-label" for="repeat-pawssword-input-field">Confirm Password </label></div>
                    <div class="col-sm-6 input-column"><input class="form-control" name="confirmPassowrd" id="confirmPassowrd" type="password" placeholder="Confirm Password"></div>
                </div>
                <div class="form-row">
                    <div class="col" style="width: 123.703px;min-width: 123.703px;max-width: 364px;min-height: 42;max-height: 42;height: 42px;"><label class="col-form-label d-xl-flex justify-content-xl-end" style="color: rgb(95,95,95);">User Street</label></div>
                    <div class="col" style="height: 42px;width: 766px;min-width: 532;max-width: 540px;"><input class="form-control" type="text" name="userStreet" id="userStreet" placeholder="ex. Via Francesco Calò 85, Roma"></div>
                </div>
                <div class="form-check" style="width: 1077px;height: 17px;margin-left: -171px;margin-top: -7px;"><input class="form-check-input" type="checkbox" name="managerProperty"  id="managerProperty" value="off" style="width: 22px;height: 32px;margin-left: -34px;margin-top: 10px;"><label class="form-check-label" for="formCheck-1" style="font-size: 16px;color: rgb(95,95,95);margin-top: 19px;margin-left: -237px;">Are you a gym manager?</label></div>
                <div
                    class="form-row form-group" style="margin-top: 33px;">
                    <div class="col-sm-4 label-column"><label class="col-form-label" id="gymName" for="name-input-field">Insert gym name&nbsp;</label></div>
                    <div class="col-sm-6 input-column"><input class="form-control" id="gymName" name="gymName" type="text" placeholder="Gym name"></div>
        </div>
        <div class="form-row form-group" style="margin-top: 33px;">
            <div class="col-sm-4 label-column"><label class="col-form-label" for="name-input-field">Insert gym street</label></div>
            <div class="col-sm-6 input-column"><input class="form-control" type="text" id="gymStreet" name="gymStreet" placeholder="ex. Via Francesco Calò 82, Roma"></div>
        </div><button  class="btn btn-light submit-button"  type="submit">Confirm Registration</button></form>
    </div>
    </div>
    <!-- End: Pretty Registration Form -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</body>

</html>