<%-- 
    Document   : waitingcabs
    Created on : Aug 22, 2012, 11:33:48 PM
    Author     : srgrn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cabs Ugly View</title>
    </head>
    <body>
        <div>Simplified ugly CabView</div>
        <div>
            <span>Waiting Cabs</span>
            <c:forEach var="type" items="${WaitingCabs}">   
                <a href="/TaxiStation/taxi/${type.key}">CAB-${type.key}</a><br/>
            </c:forEach>
            <br/>
            <span>Driving Cabs</span>
            <c:forEach var="type" items="${DrivingCabs}">   
            <a href="/TaxiStation/taxi/${type.key}">CAB-${type.key}</a><br/>
            </c:forEach>
        </div>
    </body>
</html>
