<%@page import="java.util.ArrayList"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Pedido"%>
<%@page import="modelo.LineaPedido"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="menu.jsp"/>
	</head>
	<body style="weigth:800px !important">
		<div class="" style="margin:auto">
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
	   <div class="m-2">
	   <hr/>
	   		<%
		String estado = (String)request.getAttribute("estado");%>
			<h1>Listado de pedidos</h1>
		
	   	<form action="ControladorPedido">
	   		<div class = "form-row">
	   			<div class="form-group col-md-4">	
		   			<label for="start">Fecha desde:</label>
					<input 	type="date" class="form-control" id="fechaDesde" name="fechaDesde" value="<%=request.getAttribute("fechaDesde")%>">
				</div>
				<div class="form-group col-md-4">
	       			<label for="start">Fecha hasta:</label>
	       			<input 	type="date" class="form-control" id="fechaHasta" name="fechaHasta" value="<%=request.getAttribute("fechaHasta")%>">
	       		</div>
	       		<div class="form-group col-md-4">
	       			<label for="estado">Estado:</label>
      				<select id="estado" name="estado" class="form-control">
      					<option <%= "Todos".equals(estado) ? "selected": "" %>>Todos </option>
        				<option <%= "Pendiente".equals(estado) ? "selected": "" %>>Pendiente</option>
        				<option <%= "Preparado".equals(estado) ? "selected": "" %>>Preparado</option>
        				<option <%= "Entregado".equals(estado) ? "selected": "" %>>Entregado</option>
        				<option <%= "Cancelado".equals(estado) ? "selected": "" %>>Cancelado</option>
     				</select>
	       		</div>
	       	</div>
	       	<div class="form-row"> 
	       		<div class="form-group col-md-6">
	       			<button type="submit" class="btn btn-primary" name="accion" value="listadoPedidos">Filtrar</button>
	       		</div>
	       	</div>				
		</form>
		<hr/>
		<div class="alert alert-danger" role="alert" id="mensajeError" hidden="true">Ocurrió un error interno</div>
		<div class="alert alert-primary" role="alert" id="mensajeOkPreparacion" hidden="true">Preparación registrada con éxito</div>
		<div class="alert alert-primary" role="alert" id="mensajeOkEntrega" hidden="true">Entrega registrada con éxito</div>
		<% 	
 			if(request.getAttribute("mensajeError") != null){
 		%>
 			<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
		<% 	
 			if(request.getAttribute("mensajeOk") != null){
 		%>
 			<div class="alert alert-primary" role="alert"><%=request.getAttribute("mensajeOk")%></div>
		<%}%>

	   			<%
	   				if (request.getAttribute("listadoPedidos") != null)
	   				{
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidos");
	   				for (Pedido ped: pedidos){
	   			%>	  
	   			<table class="table">
	   		<thead>
	   			<tr>
	   				<th>Codigo</th>
					<th>DNI Cliente</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Telefono</th>
					<th>Fecha de realización</th>
					<th>Fecha entrega estimada</th>
					<th>Fecha Entraga Real</th>
					<th>Fecha Cancelación</th>
					<th>Monto</th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
	   			</tr>
	   		</thead> 				  		   				 	   			  
	   		<tbody>
	   			<tr>
					<td style="padding-top: 20px"><%=ped.getNro_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getDni_cliente()%></td>
					<td style="padding-top: 20px"><%=ped.getCliente().getNombre()%></td>
					<td style="padding-top: 20px"><%=ped.getCliente().getApellido()%></td>
					<td style="padding-top: 20px"><%=ped.getCliente().getTelefono()%></td>
					<td style="padding-top: 20px"><%=ped.getFecha_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getFecha_entrega_est()%></td>
					<%
						if (ped.getFecha_entrega_real() !=  null)
						{
						%>
						<td style="padding-top: 20px"><%=ped.getFecha_entrega_real()%></td>
						<%	
						}
						else
						{
						%>
						<td style="padding-top: 20px">-</td>
						<% 
						}
						%>
						<%
						if (ped.getFecha_cancelacion() !=  null)
						{
						%>
						<td style="padding-top: 20px"><%=ped.getFecha_cancelacion()%></td>
						<%	
						}
						else
						{
						%>
						<td style="padding-top: 20px">-</td>
						<% 
						}
					%>
					<td style="padding-top: 20px">$ <%=String.format("%.2f",ped.getMonto())%></td>
					<td>
						<a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido&nro_pedido=<%=ped.getNro_pedido()%>">
							<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="" value="">
									<img src="SVG/Eye.svg"/> 
									Ver
							</button>
						</a>
					</td>
					<td>
						<form action="ControladorPDF" method="POST">
							<input 	type="hidden" name="nro_pedido" class="form-control" value="<%=ped.getNro_pedido()%>"/>
							<button type="submit" class="btn btn-primary" name="accion" value="exportarPedidoPdf">
								Exportar
							</button>
						</form>
					</td>
					<td>
						<% if(ped.getEstado().equalsIgnoreCase("pendiente")){ %>
						   	<button type="submit" class="btn btn-primary" name="accion" id="botonPrepararPedido<%=ped.getNro_pedido()%>" onclick="registrarPreparacion(<%=ped.getNro_pedido()%>)">Preparado</button>
						<%}%>
					</td>
					<td>
						<% if(ped.getFecha_entrega_real() == null && ped.getFecha_cancelacion() == null){ %>
					   		<button type="submit" class="btn btn-primary" name="accion" id="botonEntregarPedido<%=ped.getNro_pedido()%>" onclick="registrarEntrega(<%=ped.getNro_pedido()%>)">Entrega</button>
						<%}%>
					</td>
				</tr>
				<tr>
					<td colspan="11" style="text-align: left;">
						<ul>
						<% for(LineaPedido lp : ped.getProductos()){ %>
							<li><%=lp.getProducto().getNombre() %> - Cantidad: <%= lp.getCantidad() %></li>
						<%} %>
						</ul>
					</td>
				</tr>
			</tbody>
	   		</table>
			<%}%>
	   </div>
	   <%}}%>
	   </div>
	</body>
	<script>
		function registrarPreparacion(nro_pedido) {
			var botonPreparar = document.getElementById("botonPrepararPedido" + nro_pedido.toString());
			$.ajax({
				type : 'GET',
				url : '/TPI_Java_2019/ControladorPedido',
				data : {
					'accion' : 'prepararPedidoAjax',
					'numero_pedido': nro_pedido,
				}
			}).done(
				function(mensajeOk) {
					var mensajeOkPrep = document.getElementById("mensajeOkPreparacion");
					var mensajeOkEnt = document.getElementById("mensajeOkEntrega");
					mensajeOkPrep.hidden = false;
					mensajeOkEnt.hidden = true;
					botonPreparar.hidden = true;
					setTimeout(mensaje.hidden = true,3000);
				}).fail(function() {
					var mensajeOkPrep = document.getElementById("mensajeOkPreparacion");
					var mensajeOkEnt = document.getElementById("mensajeOkEntrega");
					var mensaje = document.getElementById("mensajeError");
					mensajeOkPrep.hidden = true;
					mensajeOkEnt.hidden = true;
					mensaje.hidden = false;
				})
		}
		
		function registrarEntrega(nro_pedido) {
			var botonEntregar = document.getElementById("botonEntregarPedido" + nro_pedido.toString());
			$.ajax({
				type : 'GET',
				url : '/TPI_Java_2019/ControladorPedido',
				data : {
					'accion' : 'entregarPedidoAjax',
					'numero_pedido': nro_pedido,
				}
			}).done(
				function(mensajeOk) {
					var mensajeOkPrep = document.getElementById("mensajeOkPreparacion");
					var mensajeOkEnt = document.getElementById("mensajeOkEntrega");
					mensajeOkPrep.hidden = true;
					mensajeOkEnt.hidden = false;
					botonEntregar.hidden = true;
					setTimeout(mensaje.hidden = true,3000);
				}).fail(function() {
					var mensaje = document.getElementById("mensajeError");
					var mensajeOkPrep = document.getElementById("mensajeOkPreparacion");
					var mensajeOkEnt = document.getElementById("mensajeOkEntrega");
					mensajeOkPrep.hidden = true;
					mensajeOkEnt.hidden = true;
					mensaje.hidden = false;
				})
		}
	</script>
</html>