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
		<h1>OS usage</h1> 
		
		<p>Here are the results of OS usage in survey that we completed.</p>
		
		<img src="reportImage" alt="Report image">
 	</body>
</html>	