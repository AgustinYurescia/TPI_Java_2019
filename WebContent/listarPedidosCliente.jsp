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
		<meta charset="ISO-8859-1">
		<title>Listar Pedidos</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_cliente") != null) { %>
	   <div class="m-2">
	   <h5>Estado del pedido:</h5>
	   	<form action="ControladorPedido">
	   		<div class = "form-row">
				<div class="form-group col-md-2">
      				<select id="estado" name="estado" class="form-control">
      					<option selected>-</option>
        				<option>Pendiente</option>
        				<option>Entregado</option>
        				<option>Cancelado</option>
     				</select>
	       		</div>
	       		<div class="form-group col-md-6">
	       			<button id="button" type="submit" class="btn btn-primary" name="accion" value="listadoPedidosCliente">Filtrar</button>
	       		</div>
	       	</div>		
		</form>
	   </div>
	   <% 	ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidosCliente");
	   		if(pedidos != null){
	   %>
	   <div class="m-2">
	   		<table class="table table-striped">
	   			<thead>
	   				<tr>
	   					<th><font face="Calibri" color="Black">Codigo</font></th>
						<th><font face="Calibri" color="Black">Fecha de realización</font></th>
						<th><font face="Calibri" color="Black">Monto</font></th>
						<th><font face="Calibri" color="Black"></font></th>
	   				</tr>
	   			</thead>
	   			<%
	   				Iterator<Pedido> iter  = pedidos.iterator();
	   			  	Pedido ped = null;
	   			  	while (iter.hasNext()){
	   				  	ped = iter.next();
	   			%>	   				  		   				 	   			  
	   			<tbody><tr>
						<td><font face="Calibri" color="Black"><%=ped.getNro_pedido()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getFecha_pedido()%></font></td>
						<td><font face="Calibri" color="Black"><%=ped.getMonto()%></font></td>
						<td>
							<a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido_cliente&nro_pedido=<%=ped.getNro_pedido()%>">
								<jsp:include page="SVG/ojo.svg"/>
							</a>
						</td>
					</tr>
				<%}%>
	   			</tbody>
	   		</table>
	   </div>
	   <%}}%>
	   </body>
	   </html>