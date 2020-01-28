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
	   	   if (sesion.getAttribute("usuario_cliente") != null) { %>
	   <nav class="site-header sticky-top py-1">
  		<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=listadoPedidosCliente"><font face="Calibri" color="Black">Listado de pedidos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=modificar_cliente"><font face="Calibri" color="Black">Modificar mis datos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=bajaCliente"><font face="Calibri" color="Black">Darse de Baja</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorLogin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
  		</div>
	   </nav>
	   <div>
	   	<form action="ControladorPedido">		
		</form>
	   </div>
	   <div>
	   		<table class="table table-striped">
	   			<thead>
	   				<tr>
	   					<th><font face="Calibri" color="Black">Codigo</font></th>
						<th><font face="Calibri" color="Black">Fecha de realización</font></th>
						<th><font face="Calibri" color="Black">Monto</font></th>
						<th><font face="Calibri" color="Black"></font></th>
	   				</tr>
	   			</thead>
	   			<%
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidosCliente");
	   				Iterator<Pedido> iter  = pedidos.iterator();
	   			  	Pedido ped = null;
	   			  	while (iter.hasNext()){
	   				  	ped = iter.next();
	   			%>	   				  		   				 	   			  
	   			<tbody><tr>
						<td><font face="Calibri" color="Black"><%=ped.getNro_pedido()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getFecha_pedido()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getMonto()%></font></td>
						<td><font face="Calibri" color="Black"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido_cliente&nro_pedido=<%=ped.getNro_pedido()%>"><button type="submit" class="btn btn-primary">Ver</button></a></font></td>
					</tr>
				<%}%>
	   			</tbody>
	   		</table>
	   </div>
	   <%}%>
	   </body>
	   </html>