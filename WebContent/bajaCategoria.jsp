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
	   	<h1>Eliminar Categoria</h1>
	   	<form action="ControladorProducto" method="POST">
  			<div class="form-row">
    			<div class="form-group col-md-10">
      				<label for="codigo_filtro">Seleccione la categoría a dar de baja:</label>
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
    			<div class="form-group col-md-2" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="listarParaBajaCat">Borrar Categoria</button>	
    			</div>
    		</div>
		</form>
	   	<div>
	   		<div id="confirm-popup" class="popup">
		 		<div class="popup-content">
		 			<p>¿Confirma la baja de la categoría?</p>
		 			<form action="ControladorCategoria" method="POST">
		 				<input type="hidden" id="codigo_categoria_baja" name="codigoCategoria">
						<div class="form-in-line">
							<button type="submit" class="btn btn-outline-danger" style="color: white; background: #c23b22 !important; height:37px !important" name="accion" value="baja">Confirmar</button>
							<a class="btn btn-outline-danger" style="color: white; background: #3e5f8a !important; float:right; border: 1px solid #3e5f8a !important" id="boton-cancelar">Cancelar</a>
						</div>
					</form>
		 		</div>
	 		</div>
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
			<h5>Al borrar la categoría se borrarán también los siguientes productos</h5>
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
			<input type="hidden" id="cod_cat_baja" value="<%=(String)request.getAttribute("categoria")%>"></input>
			<button type="submit" class="btn btn-primary" id="boton-eliminar" onclick="return confirmacion_eliminar_categoria(<%=(String)request.getAttribute("categoria")%>);">Confirmar</button>
			<%}%>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>