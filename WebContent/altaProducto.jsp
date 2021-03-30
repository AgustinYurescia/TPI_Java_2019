<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<% 	
	HttpSession sesion = request.getSession(true);
	if (sesion.getAttribute("usuario_admin") != null) 
	{
	%>
	<div class="container">
		<h1>Agregar Producto</h1>
		<form action="ControladorProducto" enctype="multipart/form-data" method = "post">
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="nombre">Nombre del producto</label>
      				<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre del producto" required>
    			</div>
    			<div class="form-group col-md-6">
      				<label for="codigo_categoria">Categoria</label>
      				<select id="codigo_categoria" name="codigo_categoria" class="form-control" required>
        				<option selected></option>
        				<% 
						for (Categoria c: (ArrayList<Categoria>) request.getAttribute("categorias"))
						{
						%>
        				<option value="<%=c.getCodigo()%>"><%=c.getDescripcion()%></option>
        				<%}%>
     				</select>
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="stock">Cantidad adquirida</label>
      				<input type="text" class="form-control" id="stock" name="stock" placeholder="Ejemplo: 100" value="0">
    			</div>
    			<div class="form-group col-md-6">
      				<label for="precio">Precio unitario</label>
      				<input type="text" id="precio" name="precio" class="form-control" placeholder="Ejemplo: 175.50" value="0">
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="imagen">Imagen del producto</label> </br>
      				<input type="file" id="imagen" name="imagen" class="" required>
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
  			<button type="submit" class="btn btn-primary" name="accion" value="Agregar" onclick="return validacion_producto();">Agregar</button>
		</form>
	</div>
	<jsp:include page="footer.jsp"/>
	<%
	}
	else
	{
		response.sendRedirect("loginAdmin.jsp");
	}
	%>
</body>
</html>