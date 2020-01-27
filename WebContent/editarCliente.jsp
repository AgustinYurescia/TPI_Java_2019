<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="modelo.Cliente"%>
<%@page import="modeloDAO.ClienteDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Sign In</title>
	</head>
	<body>
			<% 	HttpSession sesion = request.getSession(true);
	   		if (sesion.getAttribute("usuario_cliente") == null) { %>
	    		<nav class="site-header sticky-top py-1">
  					<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionCliente"><font face="Calibri" color="Black">Iniciar Sesión</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionAdmin"><font face="Calibri" color="Black">Iniciar Sesion Admin</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=registroCliente"><font face="Calibri" color="Black">Registrarse</font></a>
  					</div>
	   			</nav>
	   	<%	} else{%>
			<nav class="site-header sticky-top py-1">
  				<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=listadoPedidosCliente"><font face="Calibri" color="Black">Listado de pedidos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=modificar_cliente"><font face="Calibri" color="Black">Modificar mis datos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorLogin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
  				</div>
	   		</nav>
	   	<% ClienteDAO cliDAO = new ClienteDAO(); 
	   	   Cliente cli = cliDAO.buscar_cliente((String)sesion.getAttribute("usuario_cliente"));%>
		<h1>Formulario de Registro</h1>
		<form action="ControladorCliente" method="post">
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="dni">Nombre</label>
      				<input type="text" value=<%=cli.getNombre()%> class="form-control" id="nombre" name="nombre">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="Apellido">Apellido</label>
      				<input type="text" value=<%=cli.getApellido()%> class="form-control" id="apellido" name="apellido">
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="telefono">Télefono celular</label>
      				<input type="text" value=<%=cli.getTelefono()%> class="form-control" id="telefono" name="telefono">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="direccion">Dirección (Calle-nro-piso-depto)</label>
      				<input type="text" value="<%=cli.getDireccion()%>" id="direccion" name="direccion" class="form-control">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="mail">E-Mail</label>
      				<input type="text" value=<%=cli.getMail()%> id="mail" name="mail" class="form-control">
    			</div>
  			</div>
  			<div class="form-row">
    			<button type="submit" class="btn btn-primary" name="accion" value="modificacion_cliente">Registrar</button>
  			</div>
		</form>
		<%} %>
	</body>
</html>