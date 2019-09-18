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
						<th>Imagen</th>
						<th>Nombre</th>
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
						<td align="center"><%=prod.getCodigo()%></td>
						<td><img src=<%=prod.getUrl_imagen()%> width="50" height="80"/></td>
						<td align="center"><%=prod.getNombre()%></td>
						<td align="center"><%=prod.getPrecioVenta()%></td>
						<td align="center"><%=prod.getStock()%></td>
					</tr>
					<%}%>
				</tbody>
			</table>
		</div>
		<a href="Controlador?accion=index">Volver a inicio</a>
	</body>
</html>