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
		<h1>Listado de pedidos a entregar mañana</h1><hr/>
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
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
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>Codigo</th>
					<th>DNI Cliente</th>
					<th>Fecha de realización</th>
					<th>Estado</th>
					<th>Monto</th>
					<th></th>
	   			</tr>
	   		</thead>
	   			<%
	   				if (sesion.getAttribute("pedidos") != null)
	   				{
	   				ArrayList<Pedido> pedidos = (ArrayList<Pedido>)sesion.getAttribute("pedidos");
	   				for (Pedido ped: pedidos){
	   			%>	   				  		   				 	   			  
	   		<tbody>
	   			<tr>
					<td style="padding-top: 20px"><%=ped.getNro_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getDni_cliente()%></td>
					<td style="padding-top: 20px"><%=ped.getFecha_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getEstado().toUpperCase()%></td>
					<td style="padding-top: 20px">$ <%=ped.getMonto()%></td>
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
	   	<form action="ControladorPedido" method="get">
	   		<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="accion" value="prepararPedidos">
				¡Pedidos ya preparados!
			</button>
	   	</form>
	   </div>
	   <%}}
	   	   else{
	   		response.sendRedirect("loginAdmin.jsp");
	   	   }
	   %>
	   </div>
	</body>
</html>