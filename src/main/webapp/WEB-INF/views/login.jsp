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
		<h1>Login</h1>
		<form method="POST" action="j_security_check"  class="well form-inline">
			<input type="text" name="j_username" class="input-small" placeholder="Username">
			<input type="password" name="j_password" class="input-small" placeholder="Password">
			<button type="submit" class="btn">Sign in</button>
		</form>
	</div>
</div> <!-- /container -->
<%@include  file="footer.jsp" %>
