<%-- 
    Document   : driving
    Created on : Aug 23, 2012, 5:11:28 PM
    Author     : alex
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
	<jsp:param name="is_admin" value="true" />
</jsp:include>
<h1>Driving List</h1>
<c:if test="${empty ERROR}">
	<table>          
		<c:forEach var="cab" items="${DrivingCabs}">   
			<tr><td>CAB #${cab.key}</td><td>${cab.value}<td></tr>
		</c:forEach>
	</table>
</c:if>
<%@include  file="footer.jsp" %>