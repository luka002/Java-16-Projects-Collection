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
			th, td {
    			padding-right: 10px;
    			padding-left: 10px;
			}
 		</style>
	</head>

 	<body>
		
		<table border="1">
			<thead>
				<tr><th>x</th><th>sin(x)</th><th>cos(x)</th></tr>
			</thead>
			<tbody>
				<c:forEach var="value" items="${values}">
					<tr>
						<td> ${value.getX()} </td>
						<td> ${value.getSin()} </td>
						<td> ${value.getCos()} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
 	</body>
</html>