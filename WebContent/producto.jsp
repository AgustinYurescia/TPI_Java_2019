<%@page import="modelo.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Producto</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
	    <% 	
	   		Producto prod = (Producto)request.getAttribute("producto"); 
	   		String mensaje = (String)request.getAttribute("mensajeError");%>
	   <h1>Información de producto</h1><br>
	   <div class="media position-relative">
	   		<img src="ControladorDeImagenes?codigo=<%=prod.getCodigo()%>" class="mr-3" width="400" height="400">
	   		<div class="media-body">
	     		<h5 class="mt-0"><%=prod.getNombre()%></h5>
	     		<p><font face="Calibri" color="Black">Stock disponible = <%=prod.getStock()%></font></p>
	     		<p><font face="Calibri" color="Black">Precio = <%=prod.getPrecioVenta()%></font></p>
	     		<form class="form-inline" action="ControladorPedido">
  					<div class="form-group mb-2">
    					<label for="cantidad" class="sr-only">Cantidad</label>
    					<input type="text" class="form-control" id="cantidad" name="cantidad" placeholder="Cantidad">
    					<input type="hidden" id="codigo_producto" name="codigo_producto" value="<%=prod.getCodigo()%>">
  					</div>
  					<% if(mensaje != null){ %>
  					<div class="alert alert-danger" role="alert" align = "center"><%=mensaje%></div>
  					<%}%>
  					<button type="submit" class="btn btn-primary mb-2" name="accion" value="agregarAlCarrito">Agregar al carrito</button>
				</form>
	 		</div>
	 	</div>
	</body>
</html>