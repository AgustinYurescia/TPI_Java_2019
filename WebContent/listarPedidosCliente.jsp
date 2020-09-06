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
		<link rel="stylesheet" href="CSS/vinoteca.css">
		<meta charset="ISO-8859-1">
		<title>Listar Pedidos</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<div class="container">
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_cliente") != null) { %>
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
	   				Iterator<Pedido> iter  = pedidos.iterator();
	   			  	Pedido ped = null;
	   			  	while (iter.hasNext()){
	   				  	ped = iter.next();
	   			%>	   				  		   				 	   			  
	   			<tbody><tr>
						<td><%=ped.getNro_pedido()%></td>
						<td><%=ped.getFecha_pedido()%></td>
						<td><%=ped.getMonto()%></td>
						<td><%=ped.getFecha_entrega_est()%></td>
						<%
						if (ped.getFecha_entrega_real() !=  null)
						{
						%>
						<td><%=ped.getFecha_entrega_real()%></td>
						<%	
						}
						else
						{
						%>
						<td>-</td>
						<% 
						}
						%>
						<%
						if (ped.getFecha_cancelacion() !=  null)
						{
						%>
						<td><%=ped.getFecha_cancelacion()%></td>
						<%	
						}
						else
						{
						%>
						<td>-</td>
						<% 
						}
						%>
						<td>
							<a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido_cliente&nro_pedido=<%=ped.getNro_pedido()%>">
								<button type="submit" class="btn btn-outline-info" style="color: blue;  width:200 ; height:200;" name="" value="">
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
	   <%}}%>
	   </div>
	   </body>
	   </html>