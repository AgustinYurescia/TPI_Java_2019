<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "modelo.LineaPedido" %>
<%@ page import = "modeloDAO.ProductoDAO" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "modelo.Producto" %>
<%@ page import = "modelo.Pedido" %>
<%@ page import = "controlador.Correo" %>
<%@ page import = "modeloDAO.ClienteDAO" %>
<%@ page import = "modelo.Cliente" %>
<%@ page import = "java.io.IOException" %>


<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
		<div class="container">
	   <% 	HttpSession sesion = request.getSession(true);
	   		ArrayList linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); %>
	   	   <div>
			<h1>Resumen del pedido</h1>
			<table class="table">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Precio Venta</th>
						<th>Cantidad</th>
						<th>Subtotal</th>
					</tr>
				</thead>
						<% 
							ProductoDAO proDAO = new ProductoDAO(); 
							Iterator<LineaPedido>iter = linea.iterator();
							LineaPedido lin;
							Producto pro;
							while(iter.hasNext()){
								lin=iter.next();
								pro = proDAO.buscarProducto(lin.getCodigo_producto());
						%>
				<tbody>
					<tr>
						<td><%=pro.getNombre()%></td>
						<td><%=pro.getPrecioVenta()%></td>
						<td><%=lin.getCantidad()%></td>
						<td><%=lin.getSubtotal()%></td>
					</tr>
					<%}%>
				</tbody>
			</table>
			<% 	double total = (double)sesion.getAttribute("total"); 
				int nro_pedido = (int)sesion.getAttribute("nro_pedido");
			%>
			<p align="right"><b><font face="calibri" color="black" size="6">Total: $<%=total%>&nbsp; &nbsp;</font></b></p>
			<p align="center"><b><font face="calibri" color="black" size="6">MUCHAS GRACIAS POR SU COMPRA</font></b></p>
			<p align="center"><b><font face="calibri" color="black" size="3">Recuerde que debe pasar a retirar y abonar el pedido en tres dás hábiles por el local informando el siguiente número de pedido: <%=nro_pedido%>.</font></b></p>
			<p align="center"><a class="py-0 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><button type="submit" class="btn btn-primary">Volver al inicio</button></a></p>
		</div>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>