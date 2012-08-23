<%-- 
    Document   : report
    Created on : Aug 23, 2012, 9:34:40 AM
    Author     : srgrn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
	<jsp:param name="is_admin" value="true" />
</jsp:include>
<div class="container">
	<h1>Receipts Report</h1>
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
                <td><a href="<c:url value="/admin/reports/count/${r.getPassengersCount()}"/>" >${r.getPassengersCount()}</a></td>
                <td>${r.getPrice()}</td>
                <td><a href="<c:url value="/admin/reports/cab/${r.getCabID()}"/>" >${r.getCabID()}</a></td>
            </tr>
        </c:forEach>
    </table>

</div> <!-- /container -->
<%@include  file="footer.jsp" %>