<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
		<h1>Listado de productos</h1>
	   	<form action="ControladorProducto" method="POST">
  			<div class="form-row">
    			<div class="form-group col-md-11">
    				<label for="codigo_filtro">Seleccione una categoría para filtrar los productos:</label>
      				<select id="codigo_filtro" name="codigo_filtro" class="form-control"  >
        				<option value = "0" selected>Todos</option>
        				<% 
						for (Categoria c: (ArrayList<Categoria>) request.getAttribute("categorias"))
						{
						%>
        				<option value="<%=c.getCodigo()%>"><%=c.getDescripcion()%></option>
        				<%}%>
     				</select>														
    			</div>
    			<div class="form-group col-md-1" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="listar">Listar</button>	
    			</div>
    		</div>
		</form>
	   	<div>
			<table class="table">
				<thead>
					<tr>					
						<th>Codigo</th>
						<th>Imagen</th>
						<th>Nombre</th>
						<th>Precio Venta</th>
						<th>Stock</th>						
					</tr>
				</thead>
				<tbody>
					<%
					if (request.getAttribute("listado") != null)
					{
						ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("listado");
						for (Producto prod : lista){
					%>
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
					<%}}%> 
				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>