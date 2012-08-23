<%-- 
    Document   : waitingcabs
    Created on : Aug 22, 2012, 11:33:48 PM
    Author     : srgrn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
	<jsp:param name="is_admin" value="false" />
</jsp:include>
<h1>Taxi List</h1>
<c:if test="${empty ERROR}">
	<table>          
		<c:forEach var="cab" items="${WaitingCabs}">   
			<tr><td>CAB #${cab.key}</td><td> is currently ${cab.value}<td></tr>
		</c:forEach>
	</table>
</c:if>
<%@include  file="footer.jsp" %>