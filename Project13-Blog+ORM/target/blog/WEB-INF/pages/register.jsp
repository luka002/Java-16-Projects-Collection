<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Register</title>
		
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
		<h1>Register</h1>

		<form action="saveUser" method="post">
		
		<div>
		 	<div>
		  		<span class="formLabel">First Name:</span><input type="text" name="firstName" value='<c:out value="${entry.firstName}"/>' size="20">
		 	</div>
		 	<c:if test="${entry.hasError('firstName')}">
		 		<div class="greska"><c:out value="${entry.getError('firstName')}"/></div>
		 	</c:if>
		</div>

		<div>
		 	<div>
		  		<span class="formLabel">Last Name:</span><input type="text" name="lastName" value='<c:out value="${entry.lastName}"/>' size="20">
		 	</div>
		 	<c:if test="${entry.hasError('lastName')}">
		 		<div class="greska"><c:out value="${entry.getError('lastName')}"/></div>
			</c:if>
		</div>

		<div>
		 	<div>
		  		<span class="formLabel">Nick:</span><input type="text" name="nick" value='<c:out value="${entry.nick}"/>' size="50">
		 	</div>
		 	<c:if test="${entry.hasError('nick')}">
		 		<div class="greska"><c:out value="${entry.getError('nick')}"/></div>
		 	</c:if>
		</div>

		<div>
		 	<div>
		  		<span class="formLabel">Mail:</span><input type="text" name="email" value='<c:out value="${entry.email}"/>' size="50">
		 	</div>
		 	<c:if test="${entry.hasError('email')}">
		 		<div class="greska"><c:out value="${entry.getError('email')}"/></div>
		 	</c:if>
		</div>
		
		<div>
		 	<div>
		  		<span class="formLabel">Password:</span><input type="password" name="password" size="50">
		 	</div>
		 	<c:if test="${entry.hasError('password')}">
		 		<div class="greska"><c:out value="${entry.getError('password')}"/></div>
		 	</c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Save">
		  <input type="submit" name="method" value="Back">
		</div>
		</form>
	</body>
</html>
