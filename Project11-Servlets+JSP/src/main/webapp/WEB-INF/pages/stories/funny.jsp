<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
 		<style>
			body {
				background-color: 
								<c:choose>
    							  	<c:when test="${sessionScope.bgColor != null}">
        								${sessionScope.bgColor}
 									</c:when>    
    								<c:otherwise>
        								white
        							</c:otherwise>
								</c:choose>;
				color: ${textColor};
			}
 		</style>
	</head>

 	<body>
		<h1>The child and his mother</h1> 
		
		<p>A curious child asked his mother: “Mommy, why are some of your hairs turning grey?”<br><br>
		
		The mother tried to use this occasion to teach her child: “It is because of you, dear. Every 
		bad action of yours will turn one of my hairs grey!”<br><br>

		The child replied innocently: “Now I know why grandmother has only grey hairs on her head.”</p>
 	</body>
</html>