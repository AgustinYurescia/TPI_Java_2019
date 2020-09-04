<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>VinotecaGatti</title>
	</head>
	<body>
	   <jsp:include page="menu.jsp"/>
	   <div class="jumbotron" align="center">
    		<div class="container">
      		<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="Images/logo.png" alt="" width="400" height="150"></a></h1>
      		<p>Somos una empresa familiar...bla bla bla...</p>
      		<p><a class="btn btn-primary btn-lg" href="ControladorDeLinks?accion=listarProductosCliente" role="button">Ver Productos &raquo;</a></p>
    		</div>
  		</div>
	</body>
</html>