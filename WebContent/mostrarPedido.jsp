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
	   <% Pedido ped = (Pedido)request.getAttribute("pedido"); %>
	   <p align="left"><b><font face="calibri" color="black" size="3">Nro pedido: <%=ped.getNro_pedido()%>&nbsp;-&nbsp;Dni cliente: <%=ped.getDni_cliente()%>&nbsp;-&nbsp;Fecha pedido: <%=ped.getFecha_pedido()%></font></b></p>
	   <p align="left"><b><font face="calibri" color="black" size="3">Fecha entrega estimada: <%=ped.getFecha_entrega_est()%>&nbsp;-&nbsp;Fecha entrega real: <%=ped.getFecha_entrega_real()%>&nbsp;-&nbsp;Fecha cancelación: <%=ped.getFecha_cancelacion()%></font></b></p>
	   <p align="left"><b><font face="calibri" color="black" size="3">Monto total: $<%=ped.getMonto()%></font></b></p>	  
	   <p align="left"><b><font face="calibri" color="black" size="3">Resúmen de productos:</font></b></p>	 
	   		<table class="table table-striped">
	   			<thead>
	   				<tr>
	   					<th><font face="Calibri" color="Black">Codigo Producto</font></th>
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
						<td><font face="Calibri" color="Black"><%=lin.getCantidad()%></font></td>
					</tr>
				<%}%>
	   			</tbody>
	   		</table>
	   </div>
	   <%}%>
	</body>
</html>
