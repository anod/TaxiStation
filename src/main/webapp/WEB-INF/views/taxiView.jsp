<%-- 
    Document   : waitingcabs
    Created on : Aug 22, 2012, 11:33:48 PM
    Author     : srgrn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include  file="header.jsp" %>
<div>Simplified ugly CabView</div>
        <c:if test="${empty ERROR}">
        <div>          
            <p>Waiting Cabs</p>
            <c:forEach var="cab" items="${WaitingCabs}">   
                <a href="/TaxiStation/taxi/${cab.key}">CAB-${cab.key}</a>is currently ${cab.value}<br/>
            </c:forEach>
            <br/>
            <p>Driving Cabs</p>
            <c:forEach var="cab" items="${DrivingCabs}">   
            <a href="/TaxiStation/taxi/${cab.key}">CAB-${cab.key}</a><br/>
            </c:forEach>
        </div>
</c:if>
            <%@include  file="footer.jsp" %>