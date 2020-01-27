<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Pedido"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="modelo.LineaPedido"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Mostrar Pedido</title>
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
    					<a class="py-2 d-none d-md-inline-block" href="ControladorLogin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
  		</div>
	   </nav>
	   <div>
	   <% Pedido ped = (Pedido)request.getAttribute("pedido"); %>
	   <p align="left"><b><font face="calibri" color="black" size="3">Nro pedido: <%=ped.getNro_pedido()%>&nbsp;-&nbsp;Fecha pedido: <%=ped.getFecha_pedido()%></font></b></p>
	   <p align="left"><b><font face="calibri" color="black" size="3">Fecha entrega estimada: <%=ped.getFecha_entrega_est()%></font></b></p>
	   <p align="left"><b><font face="calibri" color="black" size="3">Monto total: $<%=ped.getMonto()%></font></b></p>	  
	   <p align="left"><b><font face="calibri" color="black" size="3">Resúmen de productos:</font></b></p>	 
	   		<table class="table table-striped">
	   			<thead>
	   				<tr>
	   					<th><font face="Calibri" color="Black">Codigo Producto</font></th>
	   					<th><font face="Calibri" color="Black">Nombre</font></th>
						<th><font face="Calibri" color="Black">Cantidad</font></th>
	   				</tr>
	   			</thead>
	   			<%
	   				ArrayList<LineaPedido> pedido_productos = (ArrayList<LineaPedido>)request.getAttribute("productos_pedido");
	   				Iterator<LineaPedido> iter  = pedido_productos.iterator();
	   			  	LineaPedido lin = null;
	   			  	while (iter.hasNext()){
	   				  	lin = iter.next();
	   			%>	   				  		   				 	   			  
	   			<tbody><tr>
						<td><font face="Calibri" color="Black"><%=lin.getCodigo_producto()%></font></td>
						<td><font face="Calibri" color="Black"><%=lin.getProducto(lin.getCodigo_producto()).getNombre()%></font></td>
						<td><font face="Calibri" color="Black"><%=lin.getCantidad()%></font></td>
					</tr>
				<%}%>
	   			</tbody>
	   		</table>
	   </div>
	   <p align="center"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=cancelar_pedido&nro_pedido=<%=ped.getNro_pedido()%>"><button type="submit" class="btn btn-primary">Cancelar pedido</button></a>&nbsp;&nbsp;&nbsp;&nbsp;</p>
	   <%}%>
	</body>
</html>