<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<meta charset="ISO-8859-1">
		<title>VinotecaGatti</title>
	</head>
	<body>
	   <% HttpSession sesion = request.getSession(true);
	   	  if (sesion.getAttribute("usuario_admin") != null) { %>
	   <nav class="site-header sticky-top py-1">
  		<div class="container d-flex flex-column flex-md-row justify-content-between">
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><font face="Calibri" color="Black">Home</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&codigo_filtro=0"><font face="Calibri" color="Black">Listado de productos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=listadoPedidos"><font face="Calibri" color="Black">Listado de Pedidos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=altaProducto"><font face="Calibri" color="Black">Alta Producto</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=actualizarStock"><font face="Calibri" color="Black">Actualizar Stock</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=editarProducto"><font face="Calibri" color="Black">Editar Producto</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorLoginAdmin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
  		</div>
	   </nav>
	   <div class="jumbotron" align="center">
    		<div class="container">
      		<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="400" height="150"></a></h1>
      		<p>Bienvenido al menú de admin</p>
      		<p><a class="btn btn-primary btn-lg" href="ControladorProducto?accion=listar&filtrar_por=TODOS" role="button">Ver Productos &raquo;</a></p>
    		</div>
  		</div>
	</body>
	<%}else{
		response.sendRedirect("loginAdmin.jsp");
	  }
	 %>
</html>