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
		</br>
	   	<form action="ControladorProducto" method="POST">
  			<div class="form-row">
    			<div class="form-group col-md-10">
      				<select id="codigo_filtro" name="codigo_filtro" class="form-control"  >
      					<option></option>
        				<% 
						for (Categoria c: (ArrayList<Categoria>) request.getAttribute("categorias"))
						{
						%>
        				<option value="<%=c.getCodigo()%>"><%=c.getDescripcion()%></option>
        				<%}%>
     				</select>														
    			</div>
    			<div class="form-group col-md-2">
    				<button type="submit" class="btn btn-primary" name="accion" value="listarParaBajaCat">Borrar Categoria</button>	
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
	   		<%
	   		if (request.getAttribute("listado") != null)
			{
	   		%>
			<h3>Al borrar la categoría se borrarán también los siguientes productos</h3>
			<table class="table">
				<thead>
					<tr>					
						<th>Codigo</th>
						<th>Nombre</th>	
						<th>Stock</th>				
					</tr>
				</thead>
				<tbody>
					<%
						ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("listado");
						for (Producto prod : lista){
					%>
					<tr>
						<td><%=prod.getCodigo()%></td>
						<td><%=prod.getNombre()%></td>
						<td><%=prod.getStock()%></td>
					</tr>
					<%}%> 
				</tbody>
			</table>
			<form action="ControladorCategoria" method="POST">
				<input type="hidden" name="codigoCategoria" value="<%=(String)request.getAttribute("categoria")%>"></input>
				<button type="submit" class="btn btn-primary" name="accion" value="baja">Confirmar</button>
			</form>	
			<%}%>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>