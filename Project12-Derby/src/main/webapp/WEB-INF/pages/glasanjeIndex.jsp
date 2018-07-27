<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
 		
	</head>

 	<body>
		<h1>${poll.getTitle()}</h1>
		<p>${poll.getMessage()}</p>
 		
 		<ol>
 			<c:forEach var="option" items="${options}">
				<li><a href="glasanje-glasaj?pollID=${poll.getId()}&optionID=${option.getId()}">${option.getOptionTitle()}</a></li>
			</c:forEach>
 		</ol>
 	</body>
</html>