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
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
		<div class="container">
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_cliente") != null) { 
	   	   		if(sesion.getAttribute("mensaje_error") != null){
		   	   		String mensaje = (String)sesion.getAttribute("mensaje_error");%>
		   	   		<div class="alert alert-danger" role="alert" align = "center"><%=mensaje%></div>
	   	   		<%}else{ %>
				   <div class="m-2">
				   <h5>Estado del pedido:</h5>
				   	<form action="ControladorPedido">
				   		<div class = "form-row">
							<div class="form-group col-md-11">
			      				<select id="estado" name="estado" class="form-control">
			      					<option selected>-</option>
			        				<option>Pendiente</option>
			        				<option>Entregado</option>
			        				<option>Cancelado</option>
			     				</select>
				       		</div>
				       		<div class="form-group col-md-1">
				       			<button id="button" type="submit" class="btn btn-primary" name="accion" value="listadoPedidosCliente">Filtrar</button>
				       		</div>
				       	</div>		
					</form>
				   </div>
				   <% 	ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidosCliente");
				   		if(pedidos != null){
				   %>
				   <div class="m-2">
				   		<table class="table">
				   			<thead>
				   				<tr>
				   					<th>Codigo</th>
									<th>Fecha de realización</th>
									<th>Monto</th>
									<th>Fecha Entraga Estimada</th>
									<th>Fecha Entraga Real</th>
									<th>Fecha Cancelación</th>
									<th></th>
				   				</tr>
				   			</thead>
				   			<%
				   				for (Pedido ped: pedidos){
				   			%>	   				  		   				 	   			  
				   			<tbody><tr>
									<td style="padding-top: 20px"><%=ped.getNro_pedido()%></td>
									<td style="padding-top: 20px"><%=ped.getFecha_pedido()%></td>
									<td style="padding-top: 20px"><%=ped.getMonto()%></td>
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
									<td>
										<a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido_cliente&nro_pedido=<%=ped.getNro_pedido()%>">
											<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="" value="">
												<img src="SVG/Ojo.svg"/> 
												Ver Pedido
											</button>
										</a>
									</td>
								</tr>
							<%}%>
				   			</tbody>
				   		</table>
				   </div>
				   <%}}}%>
	   </div>
	   <jsp:include page="footer.jsp"/>
	</body>
</html>