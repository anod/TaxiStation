<%-- 
    Document   : report
    Created on : Aug 23, 2012, 9:34:40 AM
    Author     : srgrn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include  file="header.jsp" %>
<div class="container">
<div class="hero-unit">
  <h3>Receipts Report</h3>
    </div>
    <table class="table table-bordered table-hover">
        <tr>
            <th>ID</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Passenger count</th>
            <th>Price</th>
            <th>Cab ID</th>
        </tr>
        <c:forEach var="r" items="${receipts}">
            <tr>
                <td>${r.getId()}</td>
                <td>${r.getStartTime()}</td>
                <td>${r.getEndTime()}</td>
                <td>${r.getPassengersCount()}</td>
                <td>${r.getPrice()}</td>
                <td>${r.getCabID()}</td>
            </tr>
        </c:forEach>
    </table>
            
    </div> <!-- /container -->
<%@include  file="footer.jsp" %>