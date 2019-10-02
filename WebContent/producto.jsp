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
	    <nav class="site-header sticky-top py-1">
  		<div class="container d-flex flex-column flex-md-row justify-content-between">
  			<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionCliente"><font face="Calibri" color="Black">Iniciar Sesión</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionAdmin"><font face="Calibri" color="Black">Iniciar Sesion Admin</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  		</div>
	   
	   </nav>
	   <% Producto prod = (Producto)request.getAttribute("producto"); %>
	   <h1>Información de producto</h1><br>
	   <div class="media position-relative">
	   		<img src="<%=prod.getUrl_imagen()%>" class="mr-3" width="200" height="400">
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
  					<button type="submit" class="btn btn-primary mb-2" name="accion" value="agregarAlCarrito">Agregar al carrito</button>
				</form>
	 		</div>
	 	</div>
	</body>
</html>