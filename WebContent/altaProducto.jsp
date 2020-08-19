<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<meta charset="utf-8">
	<title>Alta de productos</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
	<% 	HttpSession sesion = request.getSession(true);
	  	if (sesion.getAttribute("usuario_admin") != null) { %>
	<div class="container">
	<h1>Agregar Producto</h1>
	<form action="ControladorProducto" enctype="multipart/form-data" method = "post">
  		<div class="form-row">
    		<div class="form-group col-md-6">
      			<label for="nombre">Nombre del producto</label>
      			<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre del producto">
    		</div>
    		<div class="form-group col-md-6">
      			<label for="codigo_categoria">Categoria</label>
      			<select id="codigo_categoria" name="codigo_categoria" class="form-control">
        			<option value="0" selected>-</option>
        			<% 
						CategoriaDAO catDAO = new CategoriaDAO(); 
						List<Categoria> lista = catDAO.obtener_todos();
						Iterator<Categoria>iter = lista.iterator();
						Categoria cat = null;
						while(iter.hasNext()){
								cat=iter.next();
					%>
        			<option value="<%=cat.getCodigo()%>"><%=cat.getDescripcion()%></option>
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
      			<input type="file" id="imagen" name="imagen" class="">
    		</div>
  		</div>
  		<% 	String mensaje = (String)request.getAttribute("mensaje_de_altaProducto");
  			if(mensaje != null){
  		%>
  				<div class="alert alert-danger" role="alert">
  					<%=mensaje%>
				</div>
		<% } %>
  		<button type="submit" class="btn btn-primary" name="accion" value="Agregar">Agregar</button>
	</form>
	</div>
	<%}else{
		response.sendRedirect("loginAdmin.jsp");
	  }
	 %>
</body>
</html>