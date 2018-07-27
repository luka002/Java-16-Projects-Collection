<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Dashboard</title>
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
	
		<c:choose>
			<c:when test="${sessionScope.userId == null}">
		 		<h2>Login:</h2>
		 		
		 		<form action="login" method="post">
		 			Nick: <input type="text" name="nick" value='<c:out value="${login.nick}"/>'><br>
		 			<c:if test="${login.hasError('nick')}">
		 				<div class="greska"><c:out value="${login.getError('nick')}"/></div>
		 			</c:if>
		 			
		 			Password: <input type="password" name="password"><br>
		 			<c:if test="${login.hasError('password')}">
		 				<div class="greska"><c:out value="${login.getError('password')}"/></div>
		 			</c:if>
		 			<input type="submit" value="Login">
		 		</form>
		 		
		 		<br><a href="/blog/servleti/register">Register here!</a><br><br>
		 	</c:when>
		 	<c:otherwise>
				<a href="logout">Logout here</a><br><br>
			</c:otherwise>
		</c:choose>
	 	
	 	<c:choose>
			<c:when test="${users == null}">
	      		No users available.
	    	</c:when>
			<c:otherwise>
				<ul>
					<c:forEach var="nick" items="${users}">
						<li><a href="/blog/servleti/author/${nick}">${nick}</a></li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
	</body>
</html>
