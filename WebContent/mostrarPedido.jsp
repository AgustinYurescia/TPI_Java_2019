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
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
	<% HttpSession sesion = request.getSession(true);
	   if (sesion.getAttribute("usuario_admin") != null) { 
	   Pedido ped = (Pedido)request.getAttribute("pedido"); 
	   if (request.getAttribute("mensajeError") != null){
	   		String mensaje = (String)request.getAttribute("mensajeError");
	   	%>
			<div class="alert alert-danger" role="alert" align = "center"><%=mensaje%></div>
	   <%
	   }else{
	   %>
	   
			<div class="m-3">
			<h2>Datos del pedido</h2>
				<hr/>
				<form action="ControladorPDF" method="POST">
					<input 	type="hidden" name="nro_pedido" class="form-control" value="<%=ped.getNro_pedido()%>"/>
					<button type="submit" class="btn btn-primary" name="accion" value="exportarPedidoPdf">
						Exportar en PDF
					</button>
					<br><br>
				</form>
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
						<%if(ped.getFecha_cancelacion() != null){%>
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
				   			<label for="monto">Estado:</label>
							<input 	type="text" id="estado" class="form-control" value="<%=ped.getEstado().toUpperCase()%>" disabled/>
						</div>
			   			<div class="form-group col-md-4">	
				   			<label for="monto">Monto total:</label>
							<input 	type="text" id="monto" class="form-control" value="$<%=String.format("%.2f",ped.getMonto())%>" disabled/>
						</div>
					</div>
			   </form>
			   <label>Resumen de productos:</label>	 
			   <table class="table">
			   		<thead>
			   			<tr>
			   				<th>Codigo Producto</th>
			   				<th>Nombre</th>
							<th>Cantidad</th>
			   			</tr>
			   		</thead>
			   		<%
			   			ArrayList<LineaPedido> lineas = ped.getProductos();
						for(LineaPedido lin : lineas){
			   		%>	   				  		   				 	   			  
			   		<tbody>
			   			<tr>
							<td><%=lin.getCodigo_producto()%></td>
							<td><%=lin.getProducto(lin.getCodigo_producto()).getNombre()%></td>
							<td><%=lin.getCantidad()%></td>
						</tr>
					<%}%>
			   		</tbody>
			   	</table>
			</div>
			<% if(ped.getFecha_cancelacion() == null && ped.getFecha_entrega_real() == null){ %>
			<div class="m-3">
				<form action="ControladorPedido">
			   		<input type="hidden" class="form-control" id="nro_pedido" name="nro_pedido" value=<%=ped.getNro_pedido()%>>
			   		<button type="submit" class="btn btn-primary" name="accion" value="cancelar_pedido">Cancelar Pedido</button>
				</form>
			</div>
			<%}%>
			<% if(ped.getEstado().equalsIgnoreCase("pendiente")){ %>
			<div class="m-3">
				<form action="ControladorPedido">
			   		<input type="hidden" class="form-control" id="numero_pedido" name="numero_pedido" value=<%=ped.getNro_pedido()%>>
			   		<button type="submit" class="btn btn-primary" name="accion" value="prepararPedido">Confirmar Preparación</button>
				</form>
			</div>
			<%
			}
			if(ped.getFecha_entrega_real() == null && ped.getFecha_cancelacion() == null){
			%>
			<div class="m-3">
				<form action="ControladorPedido">
			   		<input type="hidden" class="form-control" id="numero_pedido" name="numero_pedido" value=<%=ped.getNro_pedido()%>>
			   		<button type="submit" class="btn btn-primary" name="accion" value="entregaPedido">Confirmar Entrega</button>
				</form>
			</div>
			<%}}%>
		<%
	   }
	   %>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>
