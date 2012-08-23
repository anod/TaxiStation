<%-- 
    Document   : admin
    Created on : Aug 23, 2012, 4:56:53 AM
    Author     : alex
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp">
	<jsp:param name="is_admin" value="true" />
</jsp:include>
<div class="container">
<div class="hero-unit">
  <h1>Admin Page</h1>
  <p><a href="<c:url value="/admin/driving"/>" >Driving List</a> <a href="<c:url value="/admin/reports"/>" >Reports</a></p>
</div>
    </div> <!-- /container -->
<%@include  file="footer.jsp" %>

