<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Listar Productos</title>
	</head>
	<body>
	   <nav class="site-header sticky-top py-1">
  		<div class="container d-flex flex-column flex-md-row justify-content-between">
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">Home</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar?filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  		</div>
	   </nav>
	   <div>
			<h1>Productos</h1>
			<div class="">
    			<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Todos </font></a>
    			<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=Aperitivo"><font face="Calibri" color="Black">Aperitivos </font></a>
    			<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=Bebida Blanca"><font face="Calibri" color="Black">Bebidas Blancas </font></a>
    			<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=Cerveza"><font face="Calibri" color="Black">Cervezas</font></a>
	    		<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=Espumante"><font face="Calibri" color="Black">Espumantes</font></a>
	    		<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=Vino Blanco"><font face="Calibri" color="Black">Vinos Blancos</font></a>
    			<a class="py-2 d-none d-md-inline-block" href="Controlador?accion=listar&filtrar_por=Vino Tinto"><font face="Calibri" color="Black">Vinos Tintos</font></a>
    			<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    			<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    			<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  			</div>
			<table class="table table-striped">
				<thead>
					<tr>
						
						<th><font face="Calibri" color="Black">Codigo</font></th>
						<th><font face="Calibri" color="Black">Imagen</font></th>
						<th><font face="Calibri" color="Black">Nombre</font></th>
						<th><font face="Calibri" color="Black">Precio Venta</font></th>
						<th><font face="Calibri" color="Black">Stock</font></th>
						
					</tr>
				</thead>
						<% 
							ProductoDAO pr = new ProductoDAO(); 
							List<Producto> lista = pr.listar();
							Iterator<Producto>iter = lista.iterator();
							Producto prod = null;
							String filtro = (String)request.getAttribute("filtro");
							if (filtro.equalsIgnoreCase("TODOS")){
							while(iter.hasNext()){
								prod=iter.next();
						%>
				<tbody>
					<tr>
						<td><font face="Calibri" color="Black"><%=prod.getCodigo()%></font></td>
						<td><img src=<%=prod.getUrl_imagen()%> width="50" height="80"/></td>
						<td><font face="Calibri" color="Blue"><a class="py-2 d-none d-md-inline-block" href=""><%=prod.getNombre()%></a></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getStock()%></font></td>
					</tr>
					<% }} else {
						while(iter.hasNext()){
								prod=iter.next();
								if (prod.getCodigo_categoria()==Integer.parseInt(filtro)){
					%>
					<tr>
						<td><font face="Calibri" color="Black"><%=prod.getCodigo()%></font></td>
						<td><img src=<%=prod.getUrl_imagen()%> width="50" height="80"/></td>
						<td><font face="Calibri" color="Blue"><a class="py-2 d-none d-md-inline-block" href=""><%=prod.getNombre()%></a></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getStock()%></font></td>
					</tr>
					<%}}}%>
				</tbody>
			</table>
		</div>
	</body>
</html>