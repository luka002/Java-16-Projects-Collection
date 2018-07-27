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
		<a href="setColor?pickedBgColor=white">WHITE</a> <br>
		<a href="setColor?pickedBgColor=red">RED</a> <br>
		<a href="setColor?pickedBgColor=green">GREEN</a> <br>
		<a href="setColor?pickedBgColor=cyan">CYAN</a>
 	</body>
</html>
