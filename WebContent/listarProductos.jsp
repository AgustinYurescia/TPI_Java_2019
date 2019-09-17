<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Listar Productos</title>
	</head>
	<body>
		<div>
			<h1>Productos</h1>
			<table border="1">
				<thead>
					<tr>
						<th>Codigo</th>
						<th>Nombre</th>
						<th>Categor�a</th>
						<th>Imagen</th>
						<th>Precio Venta</th>
						<th>Stock</th>
					</tr>
				</thead>
						<% 
							ProductoDAO pr = new ProductoDAO(); 
							List<Producto> lista = pr.listar();
							Iterator<Producto>iter = lista.iterator();
							Producto prod = null;
							while(iter.hasNext()){
								prod=iter.next();
						%>
				<tbody>
					<tr>
						<td><%=prod.getCodigo()%></td>
						<td><%=prod.getNombre()%></td>
						<td><%=prod.getCodigo_categoria()%></td>
						<td><%=prod.getUrl_imagen()%></td>
						<td><%=prod.getPrecioVenta()%></td>
						<td><%=prod.getStock()%></td>
					</tr>
					<%}%>
				</tbody>
			</table>
		</div>
		<a href="Controlador?accion=index">Volver a inicio</a>
	</body>
</html>