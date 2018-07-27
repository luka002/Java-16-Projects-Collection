<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<style type="text/css">
			.greska {
			   font-family: fantasy;
			   font-weight: bold;
			   font-size: 0.9em;
			   color: #FF0000;
			   padding-left: 110px;
			}
			.formLabel {
			   display: inline-block;
			   width: 100px;
	                   font-weight: bold;
			   text-align: right;
	                   padding-right: 10px;
			}
			.formControls {
			  margin-top: 10px;
			}
		</style>
		<title>Dashboard</title>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.userId == null}">
		 		<p>Not logged in.</p>
		 		<h2>Login:</h2>
		 		
		 		<form action="${pageContext.request.contextPath}/servleti/login" method="post">
		 			<span class="formLabel">Nick:</span><input type="text" name="nick" value='<c:out value="${login.nick}"/>'><br>
		 			<c:if test="${login.hasError('nick')}">
		 				<div class="greska">${login.getError('nick')}</div>
		 			</c:if>
		 			
		 			<span class="formLabel">Password:</span><input type="password" name="password"><br>
		 			<c:if test="${login.hasError('password')}">
		 				<div class="greska">${login.getError('password')}</div>
		 			</c:if>
		 			
		 			<div class="formControls">
				  		<span class="formLabel">&nbsp;</span>
		 				<input type="submit" value="Login">
		 			</div>
		 		</form>
		 		
		 		<br><a href="${pageContext.request.contextPath}/servleti/register">Register here!</a><br><br>
		 	</c:when>
		 	<c:otherwise>
				<p>${sessionScope.userFirstName} ${sessionScope.userLastName}(<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>)</p>
			</c:otherwise>
		</c:choose>
	 	
	 	<c:choose>
			<c:when test="${users == null}">
	      		No users available.
	    	</c:when>
			<c:otherwise>
				<ul>
					<c:forEach var="nick" items="${users}">
						<li><a href="${pageContext.request.contextPath}/servleti/author/${nick}">${nick}</a></li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
	</body>
</html>
