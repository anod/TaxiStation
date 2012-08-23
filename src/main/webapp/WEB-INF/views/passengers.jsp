<%-- 
    Document   : passengerView
    Created on : Aug 23, 2012, 12:48:35 AM
    Author     : srgrn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
	<jsp:param name="is_admin" value="false" />
</jsp:include>
<h1>Passengers List</h1>
<c:if test="${empty ERROR}">
	<table>          
		<c:forEach var="pass" items="${PassengersInLine}">   
			<tr><td><a href="/TaxiStation/passenger/${pass.key}">${pass.key}</td></tr>
		</c:forEach>
	</table>
</c:if>
<%@include  file="footer.jsp" %>
