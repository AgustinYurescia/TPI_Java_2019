<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "modelo.LineaPedido" %>
<%@ page import = "modeloDAO.ProductoDAO" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "modelo.Producto" %>


<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<link rel="stylesheet" href="CSS/vinoteca.css">
		<meta charset="ISO-8859-1">
		<title>Confirmar Pedido</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<div class="container">
	 	<% HttpSession sesion = request.getSession(true);
		   ArrayList linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); %>
	   	<div>
	   		<h1>Confirmación del pedido</h1>
			<table class="table">
				<thead>
					<tr>
						<th>Imagen</th>
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
								pro = proDAO.buscar_producto(lin.getCodigo_producto());
						%>
				<tbody>
					<tr>
						<td><img src="ControladorDeImagenes?codigo=<%=pro.getCodigo()%>" width="80" height="80"/></td>
						<td><%=pro.getNombre()%></td>
						<td><%=pro.getPrecioVenta()%></td>
						<td><%=lin.getCantidad()%></td>
						<td><%=lin.getSubtotal()%></td>
					</tr>
					<%}%>
				</tbody>
			</table>
			<% double total = (double)sesion.getAttribute("total"); %>
			<p align="right"><b><font face="calibri" color="black" size="6">Total: $<%=total%>&nbsp; &nbsp;</font></b></p>
			<p align="right"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=FinalizarPedido"><button type="submit" class="btn btn-primary">Finalizar compra</button></a>&nbsp;&nbsp;&nbsp;&nbsp;</p>
		</div>
		</div>
	</body>
</html>