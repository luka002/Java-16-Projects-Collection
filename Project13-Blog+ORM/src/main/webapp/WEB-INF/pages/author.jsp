<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Blogs</title>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.userId == null}">
		 		<p>Not logged in.</p>
		 	</c:when>
		 	<c:otherwise>
				<p>${sessionScope.userFirstName} ${sessionScope.userLastName}(<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>)</p>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${sessionScope.userNick == nick}">
		 		<p>Your blogs: </p>
		 	</c:when>
		 	<c:otherwise>
				<p>Blogs from user ${nick}:</p>
			</c:otherwise>
		</c:choose>
			
		<c:choose>
			<c:when test="${entries == null}">
		 		<p>No blog entries found.</p>
		 	</c:when>
		 	<c:otherwise>
				<ul>
					<c:forEach var="entry" items="${entries}">
						<li><a href="${pageContext.request.contextPath}/servleti/author/${entry.creator.nick}/${entry.id}">${entry.title}</a></li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${sessionScope.userNick == nick}">
		 	<a href="${pageContext.request.contextPath}/servleti/author/${nick}/new">Add new blog here!</a>
		</c:if> 

		<br><br><a href="${pageContext.request.contextPath}/servleti/main">HOME</a>		
	</body>
</html>
