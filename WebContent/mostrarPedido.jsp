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
	   if (sesion.getAttribute("usuario_admin") != null) { 
	   Pedido ped = (Pedido)request.getAttribute("pedido"); %>
	<div class="m-3">
	   <form>
	   		<div class="form-row">
	   			<div class="form-group col-md-4">	
		   			<label for="nro_pedido">Nro de pedido:</label>
					<input 	type="text" id="nro_pedido" class="form-control" value=<%=ped.getNro_pedido()%> disabled/>
				</div>
				<div class="form-group col-md-4">	
		   			<label for="dni">Dni cliente:</label>
					<input 	type="text" id="dni" class="form-control" value=<%=ped.getDni_cliente()%> disabled/>
				</div>
				<div class="form-group col-md-4">	
		   			<label for="fecha_ped">Fecha:</label>
					<input 	type="date" id="fecha_ped" class="form-control" value=<%=ped.getFecha_pedido()%> disabled/>
				</div>
			</div>
			<div class="form-row">
	   			<div class="form-group col-md-4">	
		   			<label for="fecha_ent_est">Fecha estimada de entrega:</label>
					<input 	type="date" id="fecha_ent_est" class="form-control" value=<%=ped.getFecha_entrega_est()%> disabled/>
				</div>
				<%if(ped.getFecha_entrega_real() != null){%>
				<div class="form-group col-md-4">	
		   			<label for="fecha_ent">Fecha de entrega:</label>
					<input 	type="date" id="fecha_ent" class="form-control" value=<%=ped.getFecha_entrega_real()%> disabled/>
				</div>
				<%}else{%>
				<div class="form-group col-md-4">	
		   			<label for="fecha_ent">Fecha de entrega:</label>
					<input 	type="text" id="fecha_ent" class="form-control" value="-" disabled/>
				</div>
				<%}%>
				<%if(ped.getFecha_entrega_real() != null){%>
				<div class="form-group col-md-4">	
		   			<label for="fecha_canc">Fecha de cancelación:</label>
					<input 	type="date" id="fecha_canc" class="form-control" value=<%=ped.getFecha_cancelacion()%> disabled/>
				</div>
				<%}else{%>
				<div class="form-group col-md-4">	
		   			<label for="fecha_canc">Fecha de cancelación:</label>
					<input 	type="text" id="fecha_canc" class="form-control" value="-" disabled/>
				</div>
				<%}%>
			</div>
			<div class="form-row">
	   			<div class="form-group col-md-4">	
		   			<label for="monto">Monto total:</label>
					<input 	type="text" id="monto" class="form-control" value="$<%=ped.getMonto()%>" disabled/>
				</div>
			</div>
	   </form>
	   <label>Resumen de productos:</label>	 
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
	   		<tbody>
	   			<tr>
					<td><font face="Calibri" color="Black"><%=lin.getCodigo_producto()%></font></td>
					<td><font face="Calibri" color="Black"><%=lin.getProducto(lin.getCodigo_producto()).getNombre()%></font></td>
					<td><font face="Calibri" color="Black"><%=lin.getCantidad()%></font></td>
				</tr>
			<%}%>
	   		</tbody>
	   	</table>
	</div>
	<div class="m-3">
	<% if(ped.getFecha_entrega_real() == null){ %>
		<form action="ControladorPedido">
	   		<input type="hidden" class="form-control" id="numero_pedido" name="numero_pedido" value=<%=ped.getNro_pedido()%>>
	   		<button type="submit" class="btn btn-primary" name="accion" value="entregaPedido">Confirmar Entrega</button>
		</form>
	<%}}%>
	</div>
</body>
</html>
