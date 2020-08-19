<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
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
		<jsp:include page="menu.jsp"/>
	   <h5><font face="Calibri" color="Black"><label for="codigo_filtro">Categoría</label></font></h5>
	   <form action="ControladorProducto">
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<select id="codigo_filtro" name="codigo_filtro" class="form-control"  >
        				<option value = "0" selected>Todos</option>
        				<% 
						CategoriaDAO catDAO = new CategoriaDAO(); 
						List<Categoria> listaCat = catDAO.obtener_todos();
						Iterator<Categoria>iterCat = listaCat.iterator();
						Categoria cat = null;
						while(iterCat.hasNext()){
								cat=iterCat.next();
						%>
        				<option value="<%=cat.getCodigo()%>"><%=cat.getDescripcion()%></option>
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
							ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("listado");
							for (Producto prod : lista){
						%>
				<tbody>
					<tr>
						<td><font face="Calibri" color="Black"><%=prod.getCodigo()%></font></td>
						<td><img src="ControladorDeImagenes?codigo=<%=prod.getCodigo()%>" width="80" height="80"/></td>
						<td><font face="Calibri" color="Blue"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=prod.getCodigo()%>"><%=prod.getNombre()%></a></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getStock()%></font></td>
					</tr>
						<% }%> 
				</tbody>
			</table>
		</div>
	</body>
</html>