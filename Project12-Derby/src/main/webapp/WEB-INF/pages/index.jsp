<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
 	
 	</head>
	<body>
		<b>Select poll for voting:</b><br>

		<c:choose>
			<c:when test="${polls.isEmpty()}">
				No available polls.
			</c:when>    
			<c:otherwise>
				<ul>
				<c:forEach var="poll" items="${polls}">
					<li><a href="glasanje?pollID=${poll.getId()}">${poll.getTitle()}</a> <br></li>
				</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>