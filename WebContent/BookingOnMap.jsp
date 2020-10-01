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
<title>Fit App - User Page - Booking on map</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/BookingForm.css" />
<link rel="stylesheet" href="assets/css/BookingOnMap.css" />


<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDP-NfD5FVlNeLw52M7Ff_HPa8K3MByAa8&callback=initMap&libraries=&v=weekly"
	defer></script>
<style type="text/css">
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
	height: 100%;
}

/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>
<script>
      "use strict";

      let map;
	  var marker;
      function initMap() {
    	  <% Double[] center = new Double[2];
    			center =  (Double[]) request.getAttribute("userBaseCoords");
    			List<Session> bookableSessions = (List<Session>) request.getAttribute("allBookList");
    			%>
    	  var latCenter = <%=center[0]%>;
    	  var lngCenter = <%=center[1]%>;
			console.log('this is coords' + latCenter);
			<%System.out.println("USERBASE COORDS"+request.getAttribute("userBaseCoords"));%>
        map = new google.maps.Map(document.getElementById("map"), 
        	{zoom: 4, center: {lat:latCenter,lng:lngCenter}}
        );
        
        var contentString ='lol';
          var infowindow = new google.maps.InfoWindow({
            content: contentString,
          });
        marker = new google.maps.Marker({
            map,
            draggable: false,
      	  	icon:"assets/img/pathUser.png",
            animation: google.maps.Animation.DROP,
            position: {lat:latCenter,lng:lngCenter},
            title:"Your are Here!"
          });
        
      <%for(int bookIndex=0;bookIndex< bookableSessions.size();bookIndex++){%>
      <%center = bookableSessions.get(bookIndex).getCoordinates();%>;
      var lat= <%=center[0]%>
   	  var lng= <%=center[1]%>
      var marker = new google.maps.Marker({
    	  icon:"assets/img/pathGym.png",
          draggable: false,
          animation: google.maps.Animation.DROP,

          position: new google.maps.LatLng(lat,lng),
          map: map,
          title: '<%=bookableSessions.get(bookIndex).getCourseName().get()%> +'-'+<%=bookableSessions.get(bookIndex).getSessionId()%>',
        });

       
        // Event that closes the Info Window with a click on the map
        google.maps.event.addListener(map, 'click', (function(infowindow) {
          return function() {
            infowindow.close();
          }
        })(infowindow));
	var i =  <%=bookIndex%>
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
          return function() {
            var contentString = 
            	'<div class="card">'+
			'<header class="article-header">'+
				"<div>"+
					'<div class="category-title">Article'+'<span class="date">'+'<%=bookableSessions.get(bookIndex).getDate().get().toString()%>' 
					+"<br>"+
					"Time start:<%=bookableSessions.get(bookIndex).getTimeStart().get().toString()%>"+
							"Time end:<%=bookableSessions.get(bookIndex).getTimeEnd().get().toString()%>"+
							"</span>"+
					"</div>"+
				"</div>"+
				'<h2 class="article-title">'+
					'<%=bookableSessions.get(bookIndex).getCourseName().get()%>'+
					'<%=String.valueOf(bookableSessions.get(bookIndex).getSessionId().get())%>'+
					"-"+'<%=bookableSessions.get(bookIndex).getTrainerName().get()%>'+
					"<br>"+
					"<hr>"+"<%=bookableSessions.get(bookIndex).getDescription().getValue()%>"
					+"</h2>"+
			"</header>"+
			'<div class="author">'+
				'<div class="profile"></div>'+
				'<div class="info">'+

					'<form action="BookingOnMapServlet" method="POST">'+

				'<button class="card-btn" name="bookSession" type="submit"value="'+'<%=bookableSessions.get(bookIndex).getSessionId().get()%>'+
				'" style="height: 50px; font-size: 20px;">'+
							"BookEvent"+"<span>&rarr;</span>"+
						"</button>"+
					"</form>"+

				"</div>"+
			"</div>"+
		"</div>";
            infowindow.setContent(contentString);
            infowindow.open(map, marker);
            toogleBounce();
          }
        })(marker, <%=bookIndex%>));
		<%}%>


      }
      

      
      
      function toggleBounce() {
    	  if (marker.getAnimation() !== null) {
    	    marker.setAnimation(null);
    	  } else {
    	    marker.setAnimation(google.maps.Animation.BOUNCE);
    	  }
    	}
    </script>

</head>

<body
	style="height: 1096px; background-image: url('assets/img/BookingOnMap.jpg'); padding: 0px;">
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
						<p style="color: rgb(0, 0, 0); width: 373px; margin-left: 0px;"><%=request.getAttribute("username")%><img
								src="assets/img/user.png"
								style="width: 52px; padding-left: 0px; margin-left: -100px;" />
						</p>
					</li>
					<li role="presentation" class="nav-item">
						<p
							style="color: rgb(0, 0, 0); font-size: 30px; margin-left: 55px; width: 753px">
							<img src="assets/img/map.png" style="width: 55px;" /><%=request.getAttribute("userStreet")%></p>
					</li>
					<li role="presentation" class="nav-item"
						style="margin-left: -150px;">
						<form action="BookingOnMapServlet" method="GET">
							<button class="btn btn-primary" type="submit" name="homePageBtn"
								style="margin-top: -47px; margin-left: 213px; width: 185px; height: 64px; font-size: 23px; background-color: #ffffff; padding-top: 13px; color: rgb(0, 0, 0);">Home
								Page</button>
						</form>
					</li>
					<li role="presentation" class="nav-item">
						<form action="LogoutServlet" method="POST">
							<button class="btn btn-primary" type="submit"
								style="margin-top: -47px; margin-left: 10px; width: 185px; height: 64px; font-size: 23px; background-color: #ffffff; padding-top: 13px; color: rgb(0, 0, 0);">Logout</button>
						</form>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="row"
			style="width: 1920px; margin-right: 0px; margin-left: -405px; margin-top: 20px; height: 966px;">
			<div class="col"
				style="margin-right: 0px; margin-left: 29px; max-width: 723px; background-color: #ffffff; opacity: 0.93;">
				<div class="row"
					style="width: 715px; height: 966px; background-color: #ffffff; opacity: 0.93;">
					<div class="col">
						<p style="font-size: 30px; padding-top: 11px;">
							<strong>Booking on map</strong>
						</p>
						<hr />
						<p style="font-size: 26px;">Avaiable booking sessions:</p>
						<div class="card-container" style="margin-top: 5px;">
							<%
								List<Session> avaiableSessionList = (List<Session>) request.getAttribute("allBookList");
							System.out.println("JSP" + avaiableSessionList);
							for (int sessionIndex = 0; sessionIndex < avaiableSessionList.size(); sessionIndex++) {
								Session singleSession = avaiableSessionList.get(sessionIndex);
							%>
							<div class="card">
								<header class="article-header">
									<div>
										<div class="category-title">
											Article<span class="date"><%=singleSession.getDate().get().toString()%><br>Time
												start:<%=singleSession.getTimeStart().get().toString()%>
												Time end:<%=singleSession.getTimeEnd().get().toString()%></span>
										</div>
									</div>
									<h2 class="article-title">
										<%=singleSession.getCourseName().get()%>
										<%=String.valueOf(singleSession.getSessionId().get())%>-<%=singleSession.getTrainerName().get()%><br>
										<hr>
										<%=singleSession.getDescription().getValue()%>
									</h2>
								</header>
								<div class="author">
									<div class="profile"></div>
									<div class="info">

										<form action="BookingOnMapServlet" method="POST">

											<button class="card-btn" name="bookSession" type="submit"
												value="<%=singleSession.getSessionId().get()%>"
												style="height: 50px; font-size: 20px;">
												BookEvent<span>&rarr;</span>
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
			<div class="col"
				style="margin-left: 20px; background-color: #ffffff; max-width: 1129px;">

				<div id="map"></div>

			</div>
		</div>
	</div>

</body>

</html>