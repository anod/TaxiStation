<%-- 
    Document   : passengerView
    Created on : Aug 23, 2012, 12:48:35 AM
    Author     : srgrn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include  file="header.jsp" %>
        <div>Simplified ugly PassengerView</div>
        <c:if test="${empty ERROR}">
        <div>          
            <span>Passengers inline Printout</span>
            <c:forEach var="pass" items="${PassengersInLine}">   
                <a href="/TaxiStation/passenger/${pass.key}">${pass.key}</a><br/>
            </c:forEach>
        </div>
        </c:if>
                <%@include  file="footer.jsp" %>
