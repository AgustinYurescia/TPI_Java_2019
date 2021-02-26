<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<% 
	HttpSession sesion = request.getSession(true);
	if (sesion.getAttribute("usuario_admin") != null) { %>
	<div class="container" >
		<h1>Editar Categoria</h1>
		<form action="ControladorCategoria" method="post">
  			<div class="form-row">
    			<div class="form-group col-md-10">
      				<label for="codigoCategoria">Seleccione una categoría:</label>
      				<select id="codigoCategoria" name="codigoCategoria" class="form-control">
        				<option selected>-</option>
        				<% 
        				for (Categoria c: (ArrayList<Categoria>) request.getAttribute("categorias"))
						{
						%>
        					<option value="<%=c.getCodigo()%>"><%=c.getDescripcion()%></option>
        				<%}%>
     				</select>
    			</div>
    			<div class="form-group col-md-2" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="buscarCategoriaEditar" style="width: 100px">Buscar</button>
    			</div>
    		</div>
    	</form>
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
    	Categoria categoria = (Categoria)request.getAttribute("categoria");
    	if (categoria != null){
    	%>
    	<form action="ControladorCategoria" method="post">
    		<div class="form-row">
    			<input type="hidden" class="form-control" id="codigoCategoria" name="codigoCategoria" value="<%=categoria.getCodigo()%>">
    			<div class="form-group col-md-10">
      				<label for="descCategoria">Descripción:</label>
      				<input type="text" id="descCategoria" name="descCategoria" class="form-control" value="<%=categoria.getDescripcion()%>">
    			</div>
    			<div class="form-group col-md-2" style="padding-top: 31px">
      				<button type="submit" class="btn btn-primary" name="accion" value="modificacion" style="width: 100px" onclick="return validacion_editar_categoria();">Guardar</button>
    			</div>
  			</div>
		</form>
		<%}}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>