<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Editar producto</title>
	</head>
	<body>
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
		<nav class="site-header sticky-top py-1">
  		<div class="container d-flex flex-column flex-md-row justify-content-between">
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><font face="Calibri" color="Black">Home</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listarAdmin&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=listadoPedidos"><font face="Calibri" color="Black">Listado de Pedidos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=altaProducto"><font face="Calibri" color="Black">Alta Producto</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=actualizarStock"><font face="Calibri" color="Black">Actualizar Stock</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=editarProducto"><font face="Calibri" color="Black">Editar Producto</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorLoginAdmin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
  	   	</div>
	   	</nav>
		<h1>Actualizar Producto</h1>
		<form action="ControladorProducto">
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="codigo_producto">Nombre del producto</label>
      				<select id="codigo_producto" name="codigo_producto" class="form-control">
        				<option selected>-</option>
        				<% 
						ProductoDAO ProdDAO = new ProductoDAO(); 
						List<Producto> lista = ProdDAO.obtener_todos();
						Iterator<Producto>iter = lista.iterator();
						Producto prod = null;
						while(iter.hasNext()){
								prod=iter.next();
						%>
        				<option value="<%=prod.getCodigo()%>"><%=prod.getNombre()%></option>
        				<%}%>
     				</select>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="nombre">Nuevo nombre</label>
      				<input type="text" class="form-control" id="nuevo_nombre" name="nuevo_nombre" placeholder="">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="nombre">Nueva imagen</label></br>
      				<input type="file" class="" id="nueva_imagen" name="nueva_imagen">
    			</div>
  			</div>
  			<button type="submit" class="btn btn-primary" name="accion" value="EditarProducto">Guardar</button>
		</form>
		<%}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	</body>
</html>