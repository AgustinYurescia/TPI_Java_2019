<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Confirmaci�n de la cancelaci�n</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
	   	<div>
			<h1>Confirmaci�n de la cancelaci�n</h1>
			<p align="center"><b><font face="calibri" color="black" size="6">MUCHAS GRACIAS</font></b></p>
			<p align="center"><b><font face="calibri" color="black" size="3">Su pedido ha sido cancelado con �xito</font></b></p>
			<p align="center"><a class="py-0 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><button type="submit" class="btn btn-primary">Volver al inicio</button></a></p>
		</div>
	</body>
</html>