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
		<div class="container">
		<h1>VentasDelDia</h1><hr/>
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
		<%
		if (sesion.getAttribute("ventasDelDia") != null)
		{
		%>
		<form action="ControladorPDF" method="POST">
			<button type="submit" class="btn btn-primary" name="accion" value="exportarVentasDelDiaPdf">
					Exportar en PDF
			</button>
			<br><br>
		</form>
		<%
		ArrayList<Pedido> pedidos = (ArrayList<Pedido>)sesion.getAttribute("ventasDelDia");
		double total = 0.0;
		for (Pedido ped: pedidos){
			total = total + ped.getMonto();
		%>
   		<table class="table">	   				  		   				 	   			  
	   		<tbody>
	   			<tr>
	   				<th>Codigo</th>
					<th>DNI Cliente</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Telefono</th>
					<th>Fecha de realización</th>
					<th>Estado</th>
					<th>Monto</th>
					<th></th>
	   			</tr>
	   			<tr>
					<td style="padding-top: 20px"><%=ped.getNro_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getDni_cliente()%></td>
					<td style="padding-top: 20px"><%=ped.getCliente().getNombre()%></td>
					<td style="padding-top: 20px"><%=ped.getCliente().getApellido()%></td>
					<td style="padding-top: 20px"><%=ped.getCliente().getTelefono()%></td>
					<td style="padding-top: 20px"><%=ped.getFecha_pedido()%></td>
					<td style="padding-top: 20px"><%=ped.getEstado().toUpperCase()%></td>
					<td style="padding-top: 20px"><%=String.format("$%.2f", ped.getMonto())%></td>
					<td>
						<a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=mostrar_pedido&nro_pedido=<%=ped.getNro_pedido()%>">
							<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="" value="">
									<img src="SVG/Eye.svg"/> 
									Ver Pedido
							</button>
						</a>
					</td>
				</tr>
				<tr>
					<td colspan="9" style="text-align: left;">
						<ul>
						<% for(LineaPedido lp : ped.getProductos()){ %>
							<li><%=lp.getProducto().getNombre() %> - Cantidad: <%= lp.getCantidad() %></li>
						<%} %>
						</ul>
					</td>
				</tr>
		<%}%>
	   			<tr>
					<td colspan="9" style="padding-top: 20px; text-align: right;"><b>Total cobrado: $<%=String.format("%.2f", total)%></b></td>
				</tr>
			</tbody>
	   	</table>
	   <%
		}}
	   	   else{
	   		response.sendRedirect("loginAdmin.jsp");
	   	   }
	   %>
	   </div>
	</body>
</html>