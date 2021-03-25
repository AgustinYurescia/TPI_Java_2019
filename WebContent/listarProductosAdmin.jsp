<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
		<% 
		HttpSession sesion = request.getSession(true);
	   	if (sesion.getAttribute("usuario_admin") != null) { %>
		<h1>Listado de productos</h1>
	   	<form action="ControladorProducto" method="POST">
  			<div class="form-row">
    			<div class="form-group col-md-11">
    				<label for="codigo_filtro">Seleccione una categoría para filtrar los productos:</label>
      				<select id="codigo_filtro" name="codigo_filtro" class="form-control"  >
        				<option value = "0">Todos</option>
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
			<% 	
  			if(request.getAttribute("mensajeError") != null){
  			%>
  			<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
			<%}%>
			<% 	
  			if(request.getAttribute("mensajeOk") != null){
  			%>
  			<div class="alert alert-primary" role="alert"><%=request.getAttribute("mensajeOk")%></div>
			<%}%>
			<table class="table">
				<thead>
					<tr>
						<th>Codigo</th>
						<th>Imagen</th>
						<th>Nombre</th>
						<th>Precio Venta</th>
						<th>Stock</th>
						<th></th>
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
						<td><img src="ControladorDeImagenes?codigo=<%=prod.getCodigo()%>" width="80" height="80"/></td>
						<td style="padding-top: 40px"><%=prod.getNombre()%></td>
						<td style="padding-top: 40px"><%=prod.getPrecioVenta()%></td>
						<td style="padding-top: 40px"><%=prod.getStock()%></td>
						<form action="ControladorProducto" method="POST">
							<input type="hidden" name="codigo_producto_baja" value="<%=prod.getCodigo()%>">
							<td style="padding-top: 30px"><button type="submit" class="btn btn-outline-danger" style="color: white; background: #c23b22 !important" name="accion" value="BajaProducto"><img src="SVG/Borrar.svg"/> Eliminar</button></td>
						</form>
					</tr>					
					<%}%>
				</tbody>
			</table>
		</div>
		<%}}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	 </div>
</body>
</html>