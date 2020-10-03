<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.fitapp.logic.model.entity.Session"%>
<%@page import="java.util.List"%>
<%@ page import="com.fitapp.logic.bean.ManagerUserBean"%>
<%@page import="com.fitapp.logic.model.entity.Trainer"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Fit App - Gym Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<link rel="stylesheet" href="assets/css/Calendar.css">
<link rel="stylesheet" href="assets/css/Scroll.css">


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
	style="height: 1080px; background-image: url(assets/img/GymPage.png); width: 1906px;">
	<!-- Start: 1 Row 2 Columns -->
	<div style="width: 1912px; height: 1072px;">
		<div class="container"
			style="margin-left: 17px; height: 1118px; width: 1800px; max-width: 1920px; margin-top: -39px;">
			<div class="row" style="height: 1047; width: 1866px;">
				<div class="col-md-6"
					style="background-color: #ffffff; opacity: 0.93; height: 1060px; padding: 12px; width: 587px; margin-top: 15px; margin-left: -6px; padding-top: 75px; padding-bottom: 0px; padding-left: 0px; padding-right: 0pz; max-width: 547px; margin-bottom: 0px;">
					<img src="assets/img/user.png" alt="User profile"
						style="width: 165px; margin-top: 49px; margin-left: 173px;">
						<form action="LogoutServlet" method="POST">
						<button class="btn btn-primary btn-lg" type="submit"
												name="setNewSession" value="logout"
												style="height: 40px; margin-top: -500px; margin-left: 10px; background-color: #214a80; font-size: 18px;">Logout</button>
						</form>
					<hr style="background-color: #000000;">
					<div class="row"
						style="height: 101px; margin-right: 0px; margin-left: 17px; margin-top: 55px; width: 512px;">
						<div class="col">
							<img src="assets/img/user.png" alt="User profile"
								style="width: 61px; margin-top: 0px; font-size: 61px; padding-top: -16px;">
							<p id="managerUsername" 
								style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("managerUserName")%></p>
						</div>
					</div>
					<div class="row"
						style="margin-top: 15px; width: 537px; margin-left: 0px;">
						<div class="col">
							<img src="assets/img/home.png" alt="Manager name"
								style="width: 61px; margin-left: 18px;">
							<p id="managerGymName"
								style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("managerGymName")%></p>
						</div>
					</div>
					<div class="row"
						style="margin-top: 15px; margin-left: 4px; width: 518px;">
						<div class="col">
							<img src="assets/img/map.png" alt="Manager Gym"
								style="width: 61px; margin-left: 15px;">
							<p id="managerGymStreet"
								style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("managerGymStreet")%></p>
						</div>
					</div>
					<form action="GymPageServlet" method="POST">
						<button class="btn btn-primary" type="submit"
							id="manageTrainerBtn" name="manageTrainerBtn"
							style="margin-top: 80px; margin-left: 60px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">Manage
							Trainer</button>
						<button class="btn btn-primary" id="viewReviewBtn"
							name="viewReviewBtn" type="button"
							style="margin-top: 80px; margin-left: 60px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">View
							Reviews</button>
					</form>
				</div>
				<div class="col"
					style="width:1100px;margin-top: 15px; margin-bottom: 0px; background-color: #FFFFFF; opacity: 0.93; padding-left: 0px; margin-left: 5px;">
					<div class="table-responsive"
						style="width: 1150px; margin-left: 0px; height: 500px;">
						<table class="table">
							<thead>
								<tr style="width: 736px;">
									<th id="sessionTitleId"
										style="font-size: 30px; margin-top: 22px; height: 92px; width: 297px;">Insert
										New Session&nbsp;
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<form action="GymPageServlet" id="sessionForm" method="POST">

											<div class="form-group"
												style="min-height: 50px; height: 300px; margin-top: 02px;">
												<p style="font-size: 25px; width: 800px; margin-top: 8px;">
													Time start:<input type="time" name="timeStart"
														style="margin-left: 5px;">
												</p>
												<p
													style="font-size: 25px; width: 259px; margin-top: -58px; margin-left: 255px;">
													Time End:&nbsp;<input type="time" name="timeEnd"
														style="margin-left: 5px;">
												</p>

												<p
													style="font-size: 25px; width: 400px; margin-top: 17px; padding: 0px;">
													Date:<input type="date" name="date"
														style="margin-left: 5px; margin-top: -1px;">
												</p>

												<div class="form-check">
													<input class="form-check-input" type="checkbox"
														id="individualValue" name="individualValue"
														style="width: 24px; min-width: 24px; height: 24px; margin-top: 0px;">
													<label class="form-check-label" for="individualValue"
														style="font-size: 25px; margin-top: -10px; margin-left: 25px;">
														Individual Course </label>
												</div>
												<div class="form-group" style="margin-top: 100px;">
													<select class="form-control form-control-lg"
														id="selectCourse" name="selectCourse"
														style="width: 300px; height: 50px; margin-top: -80px">

														<option>1- Kick Boxing</option>
														<option>2- Pugilato</option>
														<option>3- Zumba</option>
														<option>4- Salsa</option>
														<option>5- Funzionale</option>
														<option>6- Walking</option>
														<option>7- Pump</option>
													</select>

												</div>

												<div class="form-group">
													<select class="form-control form-control-lg"
														id="selectCourse" name="selectTrainer"
														style="width: 300px; height: 50px;">
														<%
														List<Trainer> avaiableTrainerList = (List<Trainer>) request.getAttribute("managerTrainerList");
													for (int trainerIndex = 0; trainerIndex < avaiableTrainerList.size(); trainerIndex++) {
														Trainer singleTrainer = avaiableTrainerList.get(trainerIndex);
													%>
														<option>
															<%=singleTrainer.getTrainerId()%>-<%=singleTrainer.getTrainerName()%>
														</option>
														<%
														}
													%>

													</select>

												</div>
												<div class="form-group"
													style="margin-top: -130px; margin-left: 500px;">
													<textarea name="description" id="descriptionArea"
														placeholder="Insert your course description"
														style="font-size: 18px; width: 400px; height: 120px; resize: none;"></textarea>

												</div>
											</div>





											<button class="btn btn-primary btn-lg" type="submit"
												name="setNewSession" value="setNewSession"
												style="height: 40px; margin-top: 0px; margin-left: 424px; background-color: #214a80; font-size: 18px;">Set
												new Session</button>

										</form>

									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="card-container" style="margin-top:80px;width:1280px;">
						<%
													List<Session> avaiableSessions = (List<Session>) request.getAttribute("avaiableSessions");
												for (int sessionIndex = 0; sessionIndex < avaiableSessions.size(); sessionIndex++) {
													Session singleSession = avaiableSessions.get(sessionIndex);
												%>
						<div class="card"style="width:600px;">
							<header class="article-header">
								<div>
									<div class="category-title">
										Article <span class="date"><%=singleSession.getDate().get().toString()%><br>Time
											start:<%=singleSession.getTimeStart().get().toString()%> Time
											end:<%=singleSession.getTimeEnd().get().toString()%></span>
									</div>
								</div>
								<h2 class="article-title"><%=singleSession.getCourseName().getValue()%>
									<%=String.valueOf(singleSession.getSessionId().get())%><br>
									<hr>
									<%=singleSession.getDescription().getValue()%>
								</h2>
							</header>
							<div class="author">
								<div class="profile"></div>
								<div class="info">
									<form action="GymPageServlet" method="POST">
										<button class="card-btn" name="deleteSession" type="submit"
											value="deleteSession <%=sessionIndex%>" type="submit">
											Delete Session
											<%=String.valueOf(singleSession.getSessionId().get())%><span>&rarr;</span>
										</button>
									</form>
								</div>
							</div>

						</div>
						<%
													}
												%>



					</div>
				</div>
			</div>
		</div>
	</div>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>

</html>