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
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>VinotecaGatti</title>
	</head>
	<body>
	<jsp:include page="menu.jsp"/>
		<%HttpSession sesion = request.getSession(true);
		String errorStock = (String)request.getAttribute("errorStock");
		ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");
	   	if(errorStock != null){%>
	   		<div class="alert alert-info"><%=errorStock%></div>
			<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&codigo_filtro=0"><button type="submit" class="btn btn-primary">Ir a comprar</button></a>	
	   	<%}else{
			if (linea == null || linea.isEmpty()) {
		%>
			<div class="alert alert-info">Su carrito se encuentra vacío</div>
			<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&codigo_filtro=0"><button type="submit" class="btn btn-primary">Ir a comprar</button></a>
		<% 
			}else {
				linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");	
		%>
	   	   <div>
			<h1>Productos</h1>
			<table class="table table-striped">
				<thead>
					<tr>
						<th><font face="Calibri" color="Black">Imagen</font></th>
						<th><font face="Calibri" color="Black">Nombre</font></th>
						<th><font face="Calibri" color="Black">Precio Venta</font></th>
						<th><font face="Calibri" color="Black">Cantidad</font></th>
						<th><font face="Calibri" color="Black"></font></th>
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
						<td><font face="Calibri" color="Blue"><%=pro.getNombre()%></font></td>
						<td><font face="Calibri" color="Black"><%=pro.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=lin.getCantidad()%></font></td>
						<td><font face="Calibri" color="Black"><a href="ControladorPedido?accion=eliminarDelCarrito&codigo_prod=<%=lin.getCodigo_producto()%>">Eliminar</a></font></td>
					</tr>
					<% } %>				
				</tbody>
			</table>
			<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=ConfirmarCarrito"><button type="submit" class="btn btn-primary">Comprar</button></a>
			<% }} %>
		</div>
	</body>
</html>