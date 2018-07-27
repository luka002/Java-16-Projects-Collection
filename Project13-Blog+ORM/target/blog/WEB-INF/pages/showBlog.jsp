<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.userId == null}">
		 		<p>Not logged in.</p>
		 	</c:when>
		 	<c:otherwise>
				<p>${sessionScope.userFirstName} ${sessionScope.userLastName}</p>
			</c:otherwise>
		</c:choose>
		
		<h1>${entry.title}</h1>
		<p>${entry.text}</p>
		
		<c:if test="${sessionScope.userNick == entry.creator.nick}">
			<a href="/blog/servleti/author/${entry.creator.nick}/edit?id=${entry.id}">Edit blog</a>	
		</c:if>
		 	
		<h3>Comments:</h3>
		<c:choose>
			<c:when test="${entry.comments.isEmpty()}">
		 		<li>No comments</li>
		 	</c:when>
		 	<c:otherwise>
				<ul>
					<c:forEach var="comment" items="${entry.comments}">
						<li>${comment.message}</li>
					</c:forEach>
				</ul>	
			</c:otherwise>
		</c:choose>
		
		<br><br><br>
		<c:if test="${sessionScope.userId != null}">
	 		<form action="/blog/servleti/author/${entry.creator.nick}/${entry.id}" method="post">
		 			Add new comment: <textarea rows="5" cols="100" name="comment"></textarea>
		 			<input type="submit" name="method" value="Comment">
		 		</form>
	 	</c:if>
	
	</body>
</html>
