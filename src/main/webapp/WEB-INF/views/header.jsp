<%-- 
    Document   : header
    Created on : Aug 23, 2012, 1:58:45 AM
    Author     : srgrn
--%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8">
    <title>Taxi Station - Web</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="<c:url value="/resources/css/bootstrap-responsive.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="<c:url value="/resources/ico/favicon.ico" />">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<c:url value="/resources/ico/apple-touch-icon-144-precomposed.png" />">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<c:url value="/resources/ico/apple-touch-icon-114-precomposed.png" />">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<c:url value="/resources/ico/apple-touch-icon-72-precomposed.png" />">
    <link rel="apple-touch-icon-precomposed" href="<c:url value="/resources/ico/apple-touch-icon-57-precomposed.png" />">
  </head>

  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">TaxiStation</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li><a href="/TaxiStation/">Home</a></li>
              <li><a href="<c:url value="/taxi/"/>">Taxi List</a></li>
              <li><a href="<c:url value="/passengers/"/>" >Passengers List</a></li>
				<c:choose>
				   <c:when test="${param.is_admin}">
					 <li><a href="<c:url value="/admin/driving"/>" >Driving List</a></li>			  
					 <li><a href="<c:url value="/admin/reports"/>" >Reports</a></li>
				   </c:when>
				   <c:otherwise>
					 <li><a href="<c:url value="/admin/"/>" >Admin Area</a></li>
				   </c:otherwise>
				</c:choose>
            </ul>
          </div><!--/.nav-collapse class="active"-->
        </div>
      </div>
    </div>

    <div class="container">