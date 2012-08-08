<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Taxi Station</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <link href="../css/bootstrap-theme.css" rel="stylesheet">
	<style>
		body {
			margin-top: 60px;
		}
	</style>
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="../">Taxi Station</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li><a href="../cabs.jsp">Waiting Cabs</a></li>
              <li><a href="../passengers.jsp">Waiting Passengers</a></li>
              <li><a href="driving.jsp">Driving Cabs</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

<div class="hero-unit">
  <h1>Admin Page</h1>
</div>
    </div> <!-- /container -->

  </body>
</html>
