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
		<a href="colors.jsp">Background color chooser</a> <br>
		<a href="trigonometric?a=0&b=90">Trigonometric</a> <br>
		<a href="funnyStory">Funny story</a> <br>
		<a href="report.jsp">Report</a> <br>
		<a href="powers?a=1&b=100&n=3">Powers</a> <br>
		<a href="appinfo.jsp">App info</a> <br>
		<a href="glasanje">Glasanje</a> <br>
		<a href="glasanje-rezultati">Glasanje Rezultati</a> <br><br>
		
		<form action="trigonometric" method="GET">
 			Starting angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			Ending angle:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Make table"><input type="reset" value="Reset">
		</form>
 	</body>
</html>