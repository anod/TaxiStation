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
		<li><a href="<c:url value="/admin/"/>" >Admin Area</a></li>
	</ul>
	<%@include file="footer.jsp" %>