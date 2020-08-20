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
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
	   <div>
	   	<form action="ControladorPedido">
	   		<div class = "form-row">
	   			<div class="form-group col-md-6">	
		   			<label for="start">Fecha desde:</label>
					<input 	type="date" class="form-control" id="fechaDesde" name="fechaDesde">
				</div>
				<div class="form-group col-md-6">
	       			<label for="start">Fecha hasta:</label>
	       			<input 	type="date" class="form-control" id="fechaHasta" name="fechaHasta">
	       		</div>
	       	</div>
	       	<div class="form-row"> 
	       		<div class="form-group col-md-6">
	       			<button type="submit" class="btn btn-primary" name="accion" value="listadoPedidos">Filtrar</button>
	       		</div>
	       	</div>				
		</form>
	   	<table class="table table-striped">
	   		<thead>
	   			<tr>
	   				<th><font face="Calibri" color="Black">Codigo</font></th>
					<th><font face="Calibri" color="Black">DNI Cliente</font></th>
					<th><font face="Calibri" color="Black">Fecha de realización</font></th>
					<th><font face="Calibri" color="Black">Monto</font></th>
					<th><font face="Calibri" color="Black"></font></th>
	   			</tr>
	   		</thead>
	   			<%
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)request.getAttribute("listadoPedidos");
	   				Iterator<Pedido> iter  = pedidos.iterator();
	   			  	Pedido ped = null;
	   			  	while (iter.hasNext()){
	   				  	ped = iter.next();
	   			%>	   				  		   				 	   			  
	   		<tbody>
	   			<tr>
					<td><font face="Calibri" color="Black"><%=ped.getNro_pedido()%></font></td>
					<td><font face="Calibri" color="Black"><%=ped.getDni_cliente()%></font></td>
					<td><font face="Calibri" color="Black"><%=ped.getFecha_pedido()%></font></td>
					<td><font face="Calibri" color="Black"><%=ped.getMonto()%></font></td>
					<td><font face="Calibri" color="Black"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido&nro_pedido=<%=ped.getNro_pedido()%>"><button type="submit" class="btn btn-primary">Ver</button></a></font></td>
				</tr>
			<%}%>
	   		</tbody>
	   	</table>
	   </div>
	   <%}%>
	   </body>
	   </html>