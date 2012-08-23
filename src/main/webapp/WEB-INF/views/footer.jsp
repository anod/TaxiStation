<%-- 
    Document   : footer
    Created on : Aug 23, 2012, 2:00:43 AM
    Author     : srgrn
--%>
<c:if test="${not empty ERROR}">
	<div class="alert alert-error">
		<strong>Error!</strong> Cannot connect to Socket server.
	</div>
</c:if>	
</div> <!-- /container -->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>
