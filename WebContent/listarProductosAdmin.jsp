<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
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
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><font face="Calibri" color="Black">Home</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listarAdmin&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=altaProducto"><font face="Calibri" color="Black">Alta Producto</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=actualizarStock"><font face="Calibri" color="Black">Actualizar Stock</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorLoginAdmin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  		</div>
	   </nav>
	   	   <h5><font face="Calibri" color="Black"><label for="filtrar_por">Categoría</label></font></h5>
	   <form action="ControladorProducto">
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<select id="filtrar_por" name="filtrar_por" class="form-control"  >
        				<option value = "TODOS" selected>Todos</option>
        				<% 
						CategoriaDAO catDAO = new CategoriaDAO(); 
						List<Categoria> listaCat = catDAO.obtener_todos();
						Iterator<Categoria>iterCat = listaCat.iterator();
						Categoria cat = null;
						while(iterCat.hasNext()){
								cat=iterCat.next();
						%>
        				<option value="<%=cat.getDescripcion()%>"><%=cat.getDescripcion()%></option>
        				<%}%>
     				</select>														
    			</div>
    			<div class="form-group col-md-6">
    			<button type="submit" class="btn btn-primary" name="accion" value="listar">Filtrar</button>	
    			</div>
    		</div>
		</form>
	   <div>
			<h1>Productos</h1>
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
							List<Producto> lista = pr.obtener_todos();
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
						<td><font face="Calibri" color="Blue"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=prod.getCodigo()%>"><%=prod.getNombre()%></a></font></td>
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
						<td><font face="Calibri" color="Blue"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=prod.getCodigo()%>"><%=prod.getNombre()%></a></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getStock()%></font></td>
					</tr>
					<%}}}%>
				</tbody>
			</table>
		</div>
	</body>
</html>