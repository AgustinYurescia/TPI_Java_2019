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
		<link rel="stylesheet" href="CSS/vinoteca.css">
		<meta charset="ISO-8859-1">
		<title>Listar Productos</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<div class="container">
		</br>
	   <form action="ControladorProducto" method="POST">
  			<div class="form-row">
    			<div class="form-group col-md-11">
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
    			<div class="form-group col-md-1">
    			<button type="submit" class="btn btn-primary" name="accion" value="listar">Listar</button>	
    			</div>
    		</div>
		</form>
	   <div>
			<h1>Productos</h1>
			<table class="table" style="background-color: #fff">
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
						if (request.getAttribute("listado") != null)
						{
							ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("listado");
							for (Producto prod : lista){
						%>
				<tbody>
					<tr>
						<td style="padding-top: 40px"><%=prod.getCodigo()%></td>
						<td>
							<a class="" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=prod.getCodigo()%>">
								<img src="ControladorDeImagenes?codigo=<%=prod.getCodigo()%>" width="80" height="80"/>
							</a>
						</td>
						<td style="padding-top: 40px">							
							<a class="" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=prod.getCodigo()%>">
								<%=prod.getNombre()%>
							</a>
						</td>
						<td style="padding-top: 40px"><%=prod.getPrecioVenta()%></td>
						<td style="padding-top: 40px"><%=prod.getStock()%></td>
					</tr>
						<% }}%> 
				</tbody>
			</table>
		</div>
		</div>
	</body>
</html>