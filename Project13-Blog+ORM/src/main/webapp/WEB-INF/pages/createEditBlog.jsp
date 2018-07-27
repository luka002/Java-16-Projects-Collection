<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<style type="text/css">
			.formLabel {
			   display: inline-block;
			   width: 100px;
	                   font-weight: bold;
			   text-align: right;
	                   padding-right: 10px;
			}
			.greska {
			   font-family: fantasy;
			   font-weight: bold;
			   font-size: 0.9em;
			   color: #FF0000;
			   padding-left: 110px;
			}
			.formControls {
			  margin-top: 10px;
			}
		</style>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.userId == null}">
		 		<p>Not logged in.</p>
		 	</c:when>
		 	<c:otherwise>
				<p>${sessionScope.userFirstName} ${sessionScope.userLastName}(<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>)</p>
				<c:choose>
					<c:when test="${action.equals(\"Create\")}">
						<h2>Create new blog</h2>
					</c:when>
					<c:otherwise>
						<h2>Edit blog</h2>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${(sessionScope.userNick == nick) && ((action == 'Create') || (sessionScope.userNick == entry.creator.nick))}">
				<form action="${pageContext.request.contextPath}/servleti/author/submitBlog" method="post">
					<input type="hidden" name="id" value='<c:out value="${entry.id}"/>'>
					<div>
						<span class="formLabel">Title:</span><input type="text" name="title" value='<c:out value="${entry.title}"/>' size="20">
					</div>
				 	
					<div>
					  	<span class="formLabel">Text:</span><textarea rows="20" cols="100" name="text">${entry.text}</textarea>
					</div>
					<c:if test="${error != null}">
				 		<div class="greska"><c:out value="${error}"/></div>
				 	</c:if>
			
					<div class="formControls">
					  <span class="formLabel">&nbsp;</span>
					  <input type="submit" name="method" value="${action}">
					  <input type="submit" name="method" value="Cancel">
					</div>
				</form>									 		
		 	</c:when>
		 	<c:otherwise>
				<p>Can not access this page.</p>
			</c:otherwise>
		</c:choose>
		
		<br><br><a href="${pageContext.request.contextPath}/servleti/main">HOME</a>
	</body>
</html>
