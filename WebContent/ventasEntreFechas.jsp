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
	   <h1>Listado de ventas</h1>
	   <hr/>
	   	<form action="ControladorPedido">
	   		<div class = "form-row">
	   			<div class="form-group col-md-6">	
		   			<label for="start">Fecha desde:</label>
					<input 	type="date" class="form-control" id="fechaDesde" name="fechaDesde" value="<%=request.getAttribute("fechaDesde")%>">
				</div>
				<div class="form-group col-md-6">
	       			<label for="start">Fecha hasta:</label>
	       			<input 	type="date" class="form-control" id="fechaHasta" name="fechaHasta" value="<%=request.getAttribute("fechaHasta")%>">
	       		</div>
	       	</div>
	       	<div class="form-row"> 
	       		<div class="form-group col-md-6">
	       			<button type="submit" class="btn btn-primary" name="accion" value="VentasEntreFechas">Filtrar</button>
	       		</div>
	       	</div>				
		</form>
		<hr/>
		<% 	
 			if(request.getAttribute("mensajeError") != null){
 		%>
 			<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
	   			<%
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoVentas");
	   				if (pedidos != null && pedidos.size() > 0)
	   				{
	   			%>
	   					<form action="ControladorPDF" method="POST">
							<button type="submit" class="btn btn-primary" name="accion" value="exportarVentasPdf">
									Exportar en PDF
							</button>
							<br><br>
						</form> 
	   			<%
	   				sesion.setAttribute("ventas", pedidos);
	   				double total = 0.0;
	   				for (Pedido ped: pedidos){
	   					total = total + ped.getMonto();
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
							<th>Fecha Entraga</th>
							<th>Monto</th>
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
							<td style="padding-top: 20px"><%=ped.getFecha_entrega_real()%></td>
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
						</tr>
						<tr>
							<td colspan="10" style="text-align: left;">
								<ul>
								<% for(LineaPedido lp : ped.getProductos()){ %>
									<li><%=lp.getProducto().getNombre() %> - Cantidad: <%= lp.getCantidad() %></li>
								<%} %>
								</ul>
							</td>
						</tr>
					<%}%>
					<tr>
						<td colspan="10" style="padding-top: 20px; text-align: right;"><b>Total cobrado: $<%=String.format("%.2f", total)%></b></td>
					</tr>
					</tbody>
			   		</table>
			   </div>
	   		<%}}%>
	   </div>
	</body>
</html>