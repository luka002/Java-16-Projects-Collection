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
		
		<h1>${entry.title}</h1>
		<p>${entry.text}</p><br>
		<p>Date posted: ${entry.createdAt} - Last modified: ${entry.lastModifiedAt}</p>
		
		<c:if test="${sessionScope.userNick == entry.creator.nick}">
			<a href="${pageContext.request.contextPath}/servleti/author/${entry.creator.nick}/edit?id=${entry.id}">Edit blog</a>	
		</c:if>
		 	
		<h3>Comments:</h3>
		<c:choose>
			<c:when test="${entry.comments.isEmpty()}">
		 		<li>No comments</li>
		 	</c:when>
		 	<c:otherwise>
				<ul>
					<c:forEach var="comment" items="${entry.comments}">
						<li>${comment.usersEMail}: ${comment.message} - Posted on: ${comment.postedOn}</li>
					</c:forEach>
				</ul>	
			</c:otherwise>
		</c:choose>
		
		<br><br><br>
		
 		<form action="${pageContext.request.contextPath}/servleti/author/${entry.creator.nick}/${entry.id}" method="post">
	 		<span class="formLabel">Comment:</span><textarea rows="5" cols="100" name="comment">${comment.message}</textarea><br>
	 		<span class="formLabel">Email:</span><input type="text" name="email" size="30" value='<c:out value="${comment.usersEMail}"/>'>
	 		
	 		<c:if test="${error != null}">
		 		<div class="greska"><c:out value="${error}"/></div>
		 	</c:if>
				 	
	 		<div class="formControls">
				<span class="formLabel">&nbsp;</span>
	 			<input type="submit" name="method" value="Comment">
	 		</div>
 		</form>	
 		
 		<br><br><a href="${pageContext.request.contextPath}/servleti/main">HOME</a>
	</body>
</html>
