<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
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
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
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
						<th><font face="Calibri" color="Black"></font></th>
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
						<td><font face="Calibri" color="Blue"><%=prod.getNombre()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getStock()%></font></td>
						<form action="ControladorProducto" method="POST">
							<input type="hidden" name="codigo_producto_baja" value="<%=prod.getCodigo()%>">
							<td><button type="submit" class="btn btn-outline-danger" style="color: red;" name="accion" value="BajaProducto"><img src="SVG/Borrar.svg"/> Eliminar</button></td>
						</form>
					</tr>					
					<%}%>
				</tbody>
			</table>
		</div>
		<%}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	</body>
</html>