<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
<title>FitApp - UserPage - Booking Training Session</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/BookingForm.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/10.0.2/css/bootstrap-slider.min.css" />

</head>

<body
	style="background-image: url('assets/img/BookingForm.jpg'); height: 1080px; width: 1920px; padding: 0px;">
	<div>
		<div class="container"
			style="width: 1920px; margin-left: 0px; margin-right: 0px; padding-right: 0px; padding-left: 0px; max-width: 0px;">
			<div class="row"
				style="margin-top ': 20pxpx; margin-right: 0px; width: 1920px;">
				<div class="col-md-12"
					style="width: 1238px; max-width: 1900px; padding-right: 0px; padding-left: 0px;">
					<nav class="navbar navbar-dark navbar-expand-md" id="app-navbar"
						style="width: 1921px; opacity: 0.95; filter: brightness(100%) contrast(106%); font-size: 57px; margin-left: 0px; padding-left: 0px; padding-right: 0px; padding-top: 0px; padding-bottom: 0px; border: 10px; height: 95px;">
						<div class="container-fluid">
							<a class="navbar-brand" style="margin-right: 0px; padding: 0px;"><img
								src="assets/img/fitApp_LogIn.png" style="width: 124px;" /></a>
							<div class="collapse navbar-collapse" id="navcol-1"
								style="margin-top: 0px; width: 983px;">
								<ul class="nav navbar-nav ml-auto"
									style="width: 1682px; margin-top: 15px; margin-left: 0px; max-width: 100%;">
									<li role="presentation" class="nav-item"
										style="font-size: 30px; margin-left: 0px; padding-left: 0px; margin-top: 0px; width: 350px;">
										<p
											style="color: rgb(0, 0, 0); width: 373px; margin-left: 0px;"><%=request.getAttribute("username") %><img
												src="assets/img/user.png"
												style="width: 52px; padding-left: 0px; margin-left: -100px;" />
										</p>
									</li>
									<li role="presentation" class="nav-item">
										<p
											style="color: rgb(0, 0, 0); font-size: 30px; margin-left: 55px; width: 753px">
											<img src="assets/img/map.png" style="width: 55px;" /><%=request.getAttribute("userStreet") %></p>
									</li>
									<li role="presentation" class="nav-item"
										style="margin-left: -150px;">
										<form action="BookingFormServlet" method="GET">
										<button
											class="btn btn-primary" type="submit" name="homePageBtn"
											style="margin-top: -47px; margin-left: 213px; width: 185px; height: 64px; font-size: 23px; background-color: #ffffff; padding-top: 13px; color: rgb(0, 0, 0);">Home
											Page</button>
											</form>
											</li>
									<li role="presentation" class="nav-item">
									<form action="LogoutServlet" method="POST">
									<button
											class="btn btn-primary" type="submit"
											style="margin-top: -47px; margin-left: 10px; width: 185px; height: 64px; font-size: 23px; background-color: #ffffff; padding-top: 13px; color: rgb(0, 0, 0);">Logout</button>
											</form>
											</li>
								</ul>
							</div>
						</div>
					</nav>
				</div>
			</div>
		</div>
	</div>
	<div>
		<div class="container">
			<div class="row">
				<div class="col-md-12"
					style="height: 774px; margin-left: 0px; margin-top: 106px; background-color: #ffffff; opacity: 0.95;">
					<header style="font-size: 30px; height: 58px; margin-top: 24px;">
						<p class="d-xl-flex justify-content-xl-center"
							style="font-size: 34px;">
							<strong>Book Training Session Form</strong>
						</p>
						<form action="BookingFormServlet" method="POST"
							style="margin-top: 112px; height: 570px;">
							<div class="form-group" style="height: 90px; padding-top: 23px;">
								<p
									style="height: 79px; margin-top: 0px; width: 520px; margin-left: 294px;">
									Select date: <input class="form-control" type="date" name="dateBooking" id="dateBooking"
										style="width: 288px; margin-left: 210px; margin-top: -54px; height: 59px; font-size: 26px;" />
								</p>
							</div>
							<div class="form-group"
								style="min-height: 0px; height: 80px; padding-top: 17px;">
								<p style="margin-left: 297px; width: 530px;">
									Select time: <input class="form-control" type="time" name="timeBooking" id="timeBooking"
										style="width: 288px; margin-left: 210px; margin-top: -54px; height: 59px; font-size: 26px;" />
								</p>
							</div>
							<div class="form-group"
								style="height: 212px; padding: 0px; padding-top: 32px; margin-top: -10px;">
								<p
									style="width: 550px; margin-top: 0px; height: 58px; margin-left: 275px; padding-top: 16px;">
									Select Radious:<input type="number" class="form-control" min="1" max="20" name="radiousBooking" id="radiousBooking"
										style="width: 288px; height: 59px; margin-top: -60px; margin-left: 232px;" />
								</p>
								<p style="width: 63px; margin-top: -56px; margin-left: 812px;">km</p>
							</div>
							<button class="btn btn-primary" type="submit" name="goBooking" value="goBooking"
								style="margin-top: 19px; margin-left: 359px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">Search
								Session</button>
						</form>
					</header>
				</div>
			</div>
		</div>
	</div>
</body>

</html>