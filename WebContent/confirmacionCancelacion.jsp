<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
	   	<div class="container">
	   		<% 
			String mensajeError = (String)request.getAttribute("mensajeErrorMail");
		   	if(mensajeError != null){
		   	%>
		   		<br/>
		   		<div class="alert alert-warning" style="text-align: center;"><%=mensajeError%></div>
		   	<%}%>
			<h1>Confirmación de la cancelación</h1>
			<p align="center"><b><font face="calibri" color="black" size="6">MUCHAS GRACIAS</font></b></p>
			<p align="center"><b><font face="calibri" color="black" size="3">Su pedido ha sido cancelado con éxito</font></b></p>
			<p align="center"><a class="py-0 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><button type="submit" class="btn btn-primary">Volver al inicio</button></a></p>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>