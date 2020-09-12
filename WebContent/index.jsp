<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<br/>
	<div class="container">
		<% 	
  		if(request.getAttribute("mensajeOk") != null){
  		%>
  		<div class="alert alert-success" role="alert" style="text-align: center;"><%=request.getAttribute("mensajeOk")%></div>
		<%}%>
		<% 	
  		if(request.getAttribute("mensajeError") != null){
  		%>
  		<div class="alert alert-danger" role="alert" style="text-align: center;"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
		<div class="jumbotron" align="center">
	    	<div class="container">
	      		<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="Images/logo.png" alt="" width="400" height="150"></a></h1>
	      		<%
	      		HttpSession sesion = request.getSession(true);
	      		if (sesion.getAttribute("usuario_admin") != null) {
	      		%>
	      			<p>Bienvenido al menú de admin</p>
	      			<p><a class="btn btn-primary btn-lg" href="ControladorDeLinks?accion=listarProductosAdmin" role="button">Ver Productos &raquo;</a></p>
	      		<%
	      		}
	      		else
	      		{
	      		%>
	      			<p style="text-align: center;">Somos una empresa familiar dedicada a la comercialización de bebidas alcoholicas de primera calidad y a los mejores precios</p>
	      			<p><a class="btn btn-primary btn-lg" href="ControladorDeLinks?accion=listarProductosCliente" role="button">Ver Productos &raquo;</a></p>
	      		<%
	      		}
	      		%>
	    	</div>
	  	</div>
  	</div>
  	<jsp:include page="footer.jsp"/>
</body>
</html>