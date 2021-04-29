<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<%HttpSession sesion = request.getSession(true);
		String errorStock = (String)request.getAttribute("errorStock");
		ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");
	   	if(errorStock != null){%>
	   		<br>
	   		<div class="alert alert-info"><%=errorStock%></div>
			<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=listarProductosCliente"><button type="submit" class="btn btn-primary">Ir a comprar</button></a>	
	   	<%}else{
			if (linea == null || linea.isEmpty()) {
		%>
			<br>
			<div class="alert alert-info">Su carrito se encuentra vacío</div>
			<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=listarProductosCliente"><button type="submit" class="btn btn-primary">Ir a comprar</button></a>
		<% 
			}else {
				linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");	
		%>
	   	   <div>
			<h1>Productos</h1>
			<table class="table">
				<thead>
					<tr>
						<th>Imagen</th>
						<th>Nombre</th>
						<th>Precio Venta</th>
						<th>Cantidad</th>
						<th></th>
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
						<td style="padding-top: 40px"><%=pro.getNombre()%></td>
						<td style="padding-top: 40px"><%=pro.getPrecioVenta()%></td>
						<td style="padding-top: 40px"><%=lin.getCantidad()%></td>
						<td style="padding-top: 30px">					
							<a href="ControladorPedido?accion=eliminarDelCarrito&codigo_prod=<%=lin.getCodigo_producto()%>">
								<button type="submit" class="btn btn-outline-danger" style="color: white;" name="" value="">
									<img src="SVG/Borrar.svg"/> 
									Eliminar
								</button>
							</a>			
						</td>
					</tr>
					<% } %>				
				</tbody>
			</table>
			<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=ConfirmarCarrito"><button type="submit" class="btn btn-primary">Comprar</button></a>
			<% }} %>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
	</body>
</html>