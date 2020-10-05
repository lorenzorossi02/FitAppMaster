<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.fitapp.logic.model.entity.Session"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
<title>Fit App - User page</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/Calendar.css">
<link rel="stylesheet" href="assets/css/Scroll.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css">

<!-- CSS only -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>

<body
	style="background-image: url('assets/img/UserPageWeb.jpg'); width: 1905px; height: 1080px;">
	<div class="row"
		style="margin-top: -34px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px; height: 1044px; width: 1900px; padding-top: 27px;">
		<div class="col-md-6"
			style="background-color: #ffffff; opacity: 0.93; height: 1032px; padding: 12px; width: 587px; margin-top: 0px; margin-left: -6px; padding-top: 75px; padding-bottom: 0px; padding-left: 0px; padding-right: 0pz; max-width: 547px; margin-bottom: 0px;">
			<img src="assets/img/user.png"
				style="width: 165px; margin-top: 49px; margin-left: 173px;" />
				
				<form action="LogoutServlet" method="POST">
						<button class="btn btn-primary btn-lg" type="submit"
												 value="logout"
												style="height: 40px; margin-top: -500px; margin-left: 10px; background-color: #214a80; font-size: 18px;">Logout</button>
						</form>
				
			<hr style="background-color: #000000;" />
			<div class="row"
				style="height: 101px; margin-right: 0px; margin-left: 17px; margin-top: 55px; width: 512px;">
				<div class="col">
					<img src="assets/img/user.png"
						style="width: 61px; margin-top: 0px; font-size: 61px; padding-top: -16px;" />
					<p id="userUsername"
						style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("userUsername")%></p>
				</div>
			</div>
			<div class="row"
				style="margin-top: 15px; margin-left: 4px; width: 518px;">
				<div class="col">
					<img src="assets/img/map.png"
						style="width: 61px; margin-left: 15px;" />
					<p id="userStreet"
						style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("userStreet")%></p>
				</div>
			</div>
			<form action="UserPageServlet" method="GET">
			<button class="btn btn-primary" type="submit" name="bookingForm"
				style="margin-top: 80px; margin-left: 60px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">Book
				Session</button>
			</form>
			
			<button class="btn btn-primary" type="button"
				style="margin-top: 80px; margin-left: 60px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">Write
				Review</button>
		</div>
		<div class="col"
			style="margin-right: 10px; margin-top: 0px; margin-left: 10px; background-color: #ffffff; opacity: 0.93; margin-bottom: 21px; height: 1031px; width: 1315px; max-width: 1300px;">
			<p style="font-size: 30px; margin-top: 13px;">
				<strong>User Page</strong>
			</p>
			<hr />
			<p style="font-size: 28px; margin-top: 13px;">
				<strong>Calendar User</strong>
			</p>

			<div class="card-container"
				style="margin-top: 10px; width: 1280px; height: 800px;">
				<%
					List<Session> avaiableSessions = (List<Session>) request.getAttribute("avaiableSessions");
				for (int sessionIndex = 0; sessionIndex < avaiableSessions.size(); sessionIndex++) {
					Session singleSession = avaiableSessions.get(sessionIndex);
				%>
				<div class="card" style="width: 600px;height:700px;">
					<header class="article-header">
						<div>
							<div class="category-title">
								Article <span class="date"><%=singleSession.getDate().get().toString()%><br>Time
									start:<%=singleSession.getTimeStart().get().toString()%> Time
									end:<%=singleSession.getTimeEnd().get().toString()%></span>
							</div>
						</div>
						<h2 class="article-title"><%=singleSession.getCourseName().get()%>
							<%=String.valueOf(singleSession.getSessionId().get())%>-<%=singleSession.getTrainerName().get()%><br>
							<hr>
							<%=singleSession.getDescription().getValue()%>
						</h2>
					</header>
					<script>
		function showEmail(index) {
			var x = document.getElementById("EmailDiv"+index);
			var valuesel = document.getElementById("emailPopOver"+index);
			console.log(valuesel);
			console.log(valuesel.value);
			if (x.style.display === "none") {
				if(valuesel.value=="Open Email"){
					valuesel.value ="Close Email";
					valuesel.innerHTML ="Close Email";
					}
				x.style.display = "block";
				
			} else {
				x.style.display = "none";
				if(valuesel.value=="Close Email"){
					valuesel.value ="Open Email";
					valuesel.innerHTML ="Open Email";
					}
			
			}
		}
	</script>
					
					<div class="author">
						<div class="profile"></div>
						<div class="info">
							<form action="UserPageServlet" method="POST">
								<button class="card-btn" name="deleteSession" type="submit"
									value="deleteSession <%=sessionIndex%>" type="submit">
									Delete Session
									<%=String.valueOf(sessionIndex)%><span>&rarr;</span>
								</button>
							</form>
							<button type="button" id="emailPopOver<%=sessionIndex %>" name="emailPopOver" value="Open Email"
								class="card-btn" onClick="showEmail(<%=sessionIndex %>)" style="margin-top: 6px;">Open
								Email</button>
						
					</div>
								
						</div>
								<div id="EmailDiv<%=sessionIndex %>"
						style="margin-top: 0px; width: 1000px;margin-top:0px; display: none">
						<form action="UserPageServlet" method="POST">
							<p style="font-size: 16px;">
								Subject
							</p>
							:<input type="text" name="subject"
									style=" width: 300px;font-size:14px;" />
							<p style="font-size: 16px;">Object</p>
							
							<textarea name="object" style="width: 300px; height: 189px;font-size:14px;"></textarea>
							<button class="btn btn-primary" type="submit"
								onCLick="showEmail(<%=sessionIndex %>)" name="emailToGym"
								value="<%=sessionIndex%>"
								style="margin-top: 80px; margin-left: -300px; width: 136px; height: 43px; font-size: 20px; background-color: #214a80; padding-top: 4px;">Send
								Email</button>
						</form>
					</div>

				</div>
				<%
					}
				%>



			</div>

		</div>

	</div>


	
</body>

</html>