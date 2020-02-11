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
	   <% 	HttpSession sesion = request.getSession(true);
	   		if (sesion.getAttribute("usuario_cliente") == null) { %>
	    		<nav class="site-header sticky-top py-1">
  					<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&codigo_filtro=0"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionCliente"><font face="Calibri" color="Black">Iniciar Sesión</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionAdmin"><font face="Calibri" color="Black">Iniciar Sesion Admin</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=registroCliente"><font face="Calibri" color="Black">Registrarse</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=bajaCliente"><font face="Calibri" color="Black">Darse de Baja</font></a>
  					</div>
	   			</nav>
	   	<%	}
	   		else{
		%>
				<nav class="site-header sticky-top py-1">
  					<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&codigo_filtro=0"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=listadoPedidosCliente"><font face="Calibri" color="Black">Listado de pedidos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=modificar_cliente"><font face="Calibri" color="Black">Modificar mis datos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=bajaCliente"><font face="Calibri" color="Black">Darse de Baja</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorLogin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
  					</div>
	   			</nav>
	   	<% } %>
	   <div class="jumbotron" align="center">
    		<div class="container">
      		<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="400" height="150"></a></h1>
      		<p>Somos una empresa familiar...bla bla bla...</p>
      		<p><a class="btn btn-primary btn-lg" href="ControladorProducto?accion=listar&codigo_filtro=0" role="button">Ver Productos &raquo;</a></p>
    		</div>
  		</div>
	</body>
</html>