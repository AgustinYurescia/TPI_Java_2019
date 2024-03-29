<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "modelo.LineaPedido" %>
<%@ page import = "modeloDAO.ProductoDAO" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "modelo.Producto" %>


<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
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
								pro = proDAO.buscarProducto(lin.getCodigo_producto());
						%>
				<tbody>
					<tr>
						<td><img src="ControladorDeImagenes?codigo=<%=pro.getCodigo()%>" width="80" height="80"/></td>
						<td><%=pro.getNombre()%></td>
						<td><%=String.format("%.2f", pro.getPrecioVenta())%></td>
						<td><%=lin.getCantidad()%></td>
						<td><%=String.format("%.2f", lin.getSubtotal())%></td>
					</tr>
					<%}%>
				</tbody>
			</table>
			<%if(sesion.getAttribute("es_socio") != null){
				if(request.getAttribute("total_sin_descuento") != null){%>
					<p align="right"><b><font face="calibri" color="#58272d" size="6">Total sin aplicar descuento para socio: $<%=String.format("%.2f", request.getAttribute("total_sin_descuento"))%>&nbsp; &nbsp;</font></b></p>
			<%  }
			}%>
			<% double total = (double)sesion.getAttribute("total"); %>
			<p align="right"><b><font face="calibri" color="black" size="6">Total: $<%=String.format("%.2f", total)%>&nbsp; &nbsp;</font></b></p>
			<p align="right"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=FinalizarPedido"><button type="submit" class="btn btn-primary">Finalizar compra</button></a>&nbsp;&nbsp;&nbsp;&nbsp;</p>
		</div>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>