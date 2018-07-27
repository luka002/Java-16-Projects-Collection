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
		<% if (request.getAttribute("a") != null && request.getAttribute("a").equals("invalid")) { %>
				Parameter "a" is invalid, allowed scope is [-100, 100]. <br>
		<% } %>
		
		<% if (request.getAttribute("b") != null && request.getAttribute("b").equals("invalid")) { %>
				Parameter "b" is invalid, allowed scope is [-100, 100]. <br>
		<% } %>
		
		<% if (request.getAttribute("n") != null && request.getAttribute("n").equals("invalid")) { %>
				Parameter "n" is invalid, allowed scope is [1, 5]. <br>
		<% } %>

		<% if (request.getAttribute("a<=b") != null && request.getAttribute("a<=b").equals("invalid")) { %>
				Parameter "a" has to be less or equal compared to "b".
		<% } %>
 	</body>
</html>