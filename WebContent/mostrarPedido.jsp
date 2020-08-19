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
		<jsp:include page="menu.jsp"/>
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
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
	   <% if(ped.getFecha_entrega_real() == null){ %>
	   <form action="ControladorPedido">
	   		<input type="hidden" class="form-control" id="numero_pedido" name="numero_pedido" value=<%=ped.getNro_pedido()%>>
	   		<button type="submit" class="btn btn-primary" name="accion" value="entregaPedido">Confirmar Entrega</button>
	   </form>
	   <%}}%>
	</body>
</html>
