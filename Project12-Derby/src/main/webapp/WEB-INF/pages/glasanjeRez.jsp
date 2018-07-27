<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
 		<style>
 			table.rez td {
 				text-align: center;
 			}
 		</style>
	</head>

 	<body> 		
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		
		<table border="1" class="rez">
 			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
 			<tbody>
 				<c:forEach var="option" items="${options}">
					<tr><td>${option.getOptionTitle()}</td><td>${option.getVotesCount()}</td></tr>
				</c:forEach>
 			</tbody>
 		</table>
		
		<h2>Grafički prikaz rezultata</h2>
 		<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="600" height="400" />
		
		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a></p>
 		
 		<h2>Razno</h2>
 		<p>Primjeri pjesama pobjedničkih bendova:</p>
 		<ul>
 			<c:forEach var="top" items="${topVoted}">
				<li><a href="${top.getOptionLink()}" target="_blank">${top.getOptionTitle()}</a></li>
			</c:forEach>	
 		</ul>
 	</body>
</html>