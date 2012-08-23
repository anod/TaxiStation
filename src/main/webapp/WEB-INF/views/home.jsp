<%-- 
    Author     : srgrn
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">

      <%@include file="header.jsp" %>
      <ul>
         <li><a href="<c:url value="/taxi/"/>">Taxi List</a></li>
         <li><a href="<c:url value="/passenger/"/>" >Passengers List</a></li>
         <li><a href="<c:url value="/admin/"/>" >Admin Page</a></li>
         <li><a href="<c:url value="/reports/"/>" >Reports Page</a></li>
            </ul>
      due to unfurtunate events i had no time to finish prettying this out,
      in order to get to reports about a cab go to <c:url value="/reports/cab/"/>cabno
      future upgrades could include real management forms (add passenger and cab using socket server requests)
      better forms for reports (and print-style.css for printing them)
      and better design of the page.
      <%@include file="footer.jsp" %>