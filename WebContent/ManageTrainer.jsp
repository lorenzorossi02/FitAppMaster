<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@ page import="com.fitapp.logic.bean.ManagerUserBean"%>
<%@page import="com.fitapp.logic.model.entity.Trainer"%>
<%@page import="javafx.beans.property.StringProperty"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
<title>Fit App - Gym Page - ManageTrainer</title>
<link rel="stylesheet" href="ManagerTrainer.css" />
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">


</head>

<body
	style="background-image: url(assets/img/GymPage.png); width: 968px;">
	<div class="row"
		style="margin-top: -34px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px; height: 1044px; width: 1900px; padding-top: 27px;">
		<div class="col-md-6"
			style="background-color: #ffffff; opacity: 0.93; height: 1032px; padding: 12px; width: 587px; margin-top: 0px; margin-left: -6px; padding-top: 75px; padding-bottom: 0px; padding-left: 0px; padding-right: 0pz; max-width: 547px; margin-bottom: 0px;">
			<img src="assets/img/user.png"
				style="width: 165px; margin-top: 49px; margin-left: 173px;" />
				
				<form action="LogoutServlet" method="POST">
						<button class="btn btn-primary btn-lg" type="submit"
												name="setNewSession" value="logout"
												style="height: 40px; margin-top: -500px; margin-left: 10px; background-color: #214a80; font-size: 18px;">Logout</button>
						</form>
				
			<hr style="background-color: #000000;" />
			<div class="row"
				style="height: 101px; margin-right: 0px; margin-left: 17px; margin-top: 55px; width: 512px;">
				<div class="col">
					<img src="assets/img/user.png"
						style="width: 61px; margin-top: 0px; font-size: 61px; padding-top: -16px;" />
					<p id="managerUsername"
						style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("managerUserName")%></p>
				</div>
			</div>
			<div class="row"
				style="margin-top: 15px; width: 537px; margin-left: 0px;">
				<div class="col">
					<img src="assets/img/home.png"
						style="width: 61px; margin-left: 18px;" />
					<p id="managerGymName"
						style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("managerGymName")%></p>
				</div>
			</div>
			<div class="row"
				style="margin-top: 15px; margin-left: 4px; width: 518px;">
				<div class="col">
					<img src="assets/img/map.png"
						style="width: 61px; margin-left: 15px;" />
					<p id="managerGymStreet"
						style="font-size: 30px; margin-top: -70px; padding-top: 17px; margin-left: 82px;"><%=request.getAttribute("managerGymStreet")%></p>
				</div>
			</div>
			<form action="ManageTrainerServlet" method="POST">
				<button class="btn btn-primary" type="submit" name="homePage" id="homePage"
					style="margin-top: 80px; margin-left: 60px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">Home
					page</button>
			</form>

			<button class="btn btn-primary" type="button"
				style="margin-top: 80px; margin-left: 60px; width: 397px; height: 64px; font-size: 27px; background-color: #214a80;">View
				Reviews</button>
		</div>
		<div class="col"
			style="margin-right: 10px; margin-top: 0px; margin-left: 10px; background-color: #ffffff; opacity: 0.93; margin-bottom: 21px; height: 1031px; width: 1315px;3max-width: 1300px;">
			<p style="font-size: 30px; margin-top: 13px;">
				<strong>Manage Trainer</strong>
			</p>
			<hr />
			<div>
				
				<span class="counter pull-right"></span>
				<div style="width: 1280px; height: 500px; overflow: auto;">
					<table class="table table-hover table-bordered results">
						<thead>
							<tr>
								<th style="width: 31px;">ID</th>
								<th class="col-md-5 clo-xs-5" style="width: 31px;">Trainer
									Name</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Kick
									Boxing</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Pugilato</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Zumba</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Salsa</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Funzionale</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Walking</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Pump</th>
								<th class="col-md-4 col-xs-4" style="width: 31px;">Action</th>
							</tr>
							
						</thead>
						<tbody>
							<%
							List<Trainer> avaiableTrainerList = (List<Trainer>) request.getAttribute("managerTrainerList");
							for (int trainerIndex = 0; trainerIndex < avaiableTrainerList.size(); trainerIndex++) {
									Trainer singleTrainer = avaiableTrainerList.get(trainerIndex);
							%>
							<tr>
									<%StringProperty trainerName = (StringProperty) singleTrainer.getName(); %>	
								<th scope="row"><%=singleTrainer.getTrainerId() %></th>
								<td><%=trainerName.get() %></td>
								<td><%=singleTrainer.getKick() %></td>
								<td><%=singleTrainer.getBoxe() %></td>
								<td><%=singleTrainer.getZumba() %></td>
								<td><%=singleTrainer.getSalsa() %></td>
								<td><%=singleTrainer.getFunct() %></td>
								<td><%=singleTrainer.getWalk() %></td>
								<td><%=singleTrainer.getPump() %></td>
								<td>
									<form action="ManageTrainerServlet" method="POST">
										<button class="btn btn-primary" type="submit" id="deleteTrainer" name="deleteTrainer" value="<%=singleTrainer.getTrainerId() %>"
											style="background-color: #214a80;">Delete Trainer <%=singleTrainer.getTrainerId() %></button>
									</form>
								</td>
							</tr>
							<%
							};
							%>
							
							

						</tbody>
					</table>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<p style="font-size: 20px;">
						<strong>Insert new Session</strong>
					</p>
					<form action="ManageTrainerServlet" method="POST">
						<p>
						Name trainer:<input class="form-control" id="gymName" name="trainerName" type="text" placeholder="Trainer name" style="width: 249px;">
							
						</p>
						<p>Courses:</p>
						<div class="checkbox" style="width: 150px;margin-top:0px;">
							<label><input type="checkbox" name="kickBoxing"
								style="width: 20px; height: 20px;"  /><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -26px; margin-left: 22px;">
									Kick Boxing</p></label>
						</div>
						<div class="checkbox"
							style="width: 150px; margin-top: -48px; margin-left: 150px;">
							<label><input type="checkbox" name="pugilato"
								style="width: 20px; height: 20px;" /><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -26px; margin-left: 22px;">
									Pugilato</p></label>
						</div>
						<div class="checkbox" style="width: 150px; margin-top:-5px">
							<label><input type="checkbox" name="zumba"
								style="width: 20px; height: 20px;" /><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -25px; margin-left: 22px;">
									Zumba</p></label>
						</div>
						<div class="checkbox"
							style="width: 150px; margin-left: 150px; margin-top: -47px;">
							<label><input type="checkbox" name="pump"
								style="width: 20px; height: 20px;" /><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -26px; margin-left: 22px;">
									Pump</p></label>
						</div>
						<div class="checkbox"
							style="width: 150px; margin-left: 300px; margin-top: -95px;">
							<label><input type="checkbox" name="salsa"
								style="width: 20px; height: 20px;"/><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -26px; margin-left: 22px;">
									Salsa</p></label>
						</div>
						<div class="checkbox"
							style="width: 150px; margin-left: 300px; margin-top: 0px;">
							<label><input type="checkbox" name="funzionale"
								style="width: 20px; height: 20px;" /><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -25px; margin-left: 22px;">
									Funzionale</p></label>
						</div>
						<div class="checkbox"
							style="width: 150px;margin-top:-10px;">
							<label><input type="checkbox" name="walking"
								style="width: 20px; height: 20px;" /><span
								class="checkbox-material"><span class="check"></span></span>
								<p
									style="font-size: 16px; margin-top: -26px; margin-left: 22px;">
									Walking</p></label>
						</div>
						<button class="btn btn-primary"  name="addNewTrainer" type="submit"
							style="margin-top: 5px; margin-left: 209px; width: 182px; height: 45px; font-size: 17px; background-color: #214a80; padding-top: 12px;">Add
							new Trainer</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>

</html>