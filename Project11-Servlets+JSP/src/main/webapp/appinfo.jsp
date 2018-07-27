<%@page import="java.awt.print.Printable"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
 		<style>
			body {
				background-color: <% if (session.getAttribute("bgColor") != null) { 
										out.print(session.getAttribute("bgColor")); 
									  } else { 
										  out.print("white");
									  } %>
			}
 		</style>
	</head>

 	<body>
		<% 	long currentTime = System.currentTimeMillis();
			String time = (String)request.getServletContext().getAttribute("StartingTime");
			long startingTime = Long.parseLong(time);
			long millisElapsed = currentTime - startingTime;
			
			long days = TimeUnit.SECONDS.toDays(millisElapsed/1000);        
			long hours = TimeUnit.SECONDS.toHours(millisElapsed/1000) - (days * 24);
			long minutes = TimeUnit.SECONDS.toMinutes(millisElapsed/1000) - 
							(TimeUnit.SECONDS.toHours(millisElapsed/1000)* 60);
			long seconds = TimeUnit.SECONDS.toSeconds(millisElapsed/1000) - 
							(TimeUnit.SECONDS.toMinutes(millisElapsed/1000) *60);
			long milliseconds = millisElapsed % 1000;
			
			out.print("Time passed after server started: " + days + " days " + 
						hours + " hours " + 
						minutes + " minutes " + 
						seconds + " seconds and " +  
						milliseconds + " milliseconds."
			);
		%>
 	</body>
</html>