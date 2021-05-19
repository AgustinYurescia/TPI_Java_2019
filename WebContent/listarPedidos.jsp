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
		<jsp:include page="menu.jsp"/>
	</head>
	<body style="weigth:800px !important">
		<div class="container">
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
	   <div class="m-2">
	   <hr/>
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
      					<option>-</option>
        				<option>Pendiente</option>
        				<option>Preparado</option>
        				<option>Entregado</option>
        				<option>Cancelado</option>
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
		String estado = (String)request.getAttribute("estado");
		if (estado != null && !estado.equalsIgnoreCase("-"))
		{
		%>
			<h1>Listado de pedidos <%=estado.toLowerCase()%>s</h1>
		<%
		}
		else
		{
		%>
			<h1>Listado de pedidos</h1>
		<%
		}
		%>
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>Codigo</th>
					<th>DNI Cliente</th>
					<th>Fecha de realización</th>
					<th>Fecha entrega estimada</th>
					<th>Fecha Entraga Real</th>
					<th>Fecha Cancelación</th>
					<th>Monto</th>
					<th></th>
	   			</tr>
	   		</thead>
	   			<%
	   				if (request.getAttribute("listadoPedidos") != null)
	   				{
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidos");
	   				for (Pedido ped: pedidos){
	   			%>	   				  		   				 	   			  
	   		<tbody>
	   			<tr>
					<td style="padding-top: 20px"><%=ped.getNro_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getDni_cliente()%></td>
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
									Ver Pedido
							</button>
						</a>
					</td>
				</tr>
			<%}%>
	   		</tbody>
	   	</table>
	   </div>
	   <%}}%>
	   </div>
	</body>
</html>