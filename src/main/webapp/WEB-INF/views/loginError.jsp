<%-- 
    Document   : login
    Created on : Aug 23, 2012, 4:45:40 AM
    Author     : alex
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include  file="header.jsp" %>
<div class="container">
	<div class="hero-unit">
		<h1>Login Error</h1>
		<p>Login attempt failed.</p>
		<a href="<c:url value="/admin/"/>" >Try Again</a>
	</div>
</div>
<%@include  file="footer.jsp" %>