<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Pedido"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Listar Pedidos</title>
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
	   <div>
	   	<form action="ControladorPedido">
	   		<div class = "form-row">
	   			<div class="form-group col-md-6">	
		   			<label for="start">Fecha desde:</label>
					<input 	type="date" class="form-control" id="fechaDesde" name="fechaDesde">
				</div>
				<div class="form-group col-md-6">
	       			<label for="start">Fecha hasta:</label>
	       			<input 	type="date" class="form-control" id="fechaHasta" name="fechaHasta">
	       		</div>
	       		<div class="form-group col-md-6">
	       			<button type="submit" class="btn btn-primary" name="accion" value="listadoPedidos">Filtrar</button>
	       		</div>
	       	</div> 				
		</form>
	   </div>
	   <form action="ControladorPedido">
			<button type="submit" class="btn btn-primary" name="accion" value="listadoPedidos">Resetear filtro</button>
		</form>
	   <div>
	   		<table class="table table-striped">
	   			<thead>
	   				<tr>
	   					<th><font face="Calibri" color="Black">Codigo</font></th>
						<th><font face="Calibri" color="Black">DNI Cliente</font></th>
						<th><font face="Calibri" color="Black">Fecha de realización</font></th>
						<th><font face="Calibri" color="Black">Monto</font></th>
						<th><font face="Calibri" color="Black"></font></th>
	   				</tr>
	   			</thead>
	   			<%
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidos");
	   				Iterator<Pedido> iter  = pedidos.iterator();
	   			  	Pedido ped = null;
	   			  	while (iter.hasNext()){
	   				  	ped = iter.next();
	   			%>	   				  		   				 	   			  
	   			<tbody><tr>
						<td><font face="Calibri" color="Black"><%=ped.getNro_pedido()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getDni_cliente()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getFecha_pedido()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getMonto()%></font></td>
						<td><font face="Calibri" color="Black"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido&nro_pedido=<%=ped.getNro_pedido()%>"><button type="submit" class="btn btn-primary">Ver</button></a></font></td>
					</tr>
				<%}%>
	   			</tbody>
	   		</table>
	   </div>
	   <%}%>
	   </body>
	   </html>