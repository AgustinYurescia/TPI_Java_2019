<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Editar producto</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
	   	<div class="container">
		<h1>Actualizar Producto</h1>
		<h5>Seleccionar producto</h5>
		<form action="ControladorProducto" method="post">
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<select id="codigo_producto" name="codigo_producto" class="form-control">
        				<option selected>-</option>
        				<% 
						ProductoDAO ProdDAO = new ProductoDAO(); 
						List<Producto> lista = ProdDAO.obtener_todos();
						Iterator<Producto>iter = lista.iterator();
						Producto prod = null;
						while(iter.hasNext()){
								prod=iter.next();
						%>
        				<option value="<%=prod.getCodigo()%>"><%=prod.getNombre()%></option>
        				<%}%>
     				</select>
    			</div>
    			<div class="form-group col-md-4">
    				<button type="submit" class="btn btn-primary" name="accion" value="BuscarProductoEditar">Buscar</button>
    			</div>
    		</div>
    	</form>
    	<% 	
  			if(request.getAttribute("mensajeError") != null){
  		%>
  				<div class="alert alert-danger" role="alert">
  					<%=request.getAttribute("mensajeError")%>
				</div>
		<% } %>
		<% 	
  			if(request.getAttribute("mensajeOk") != null){
  		%>
  				<div class="alert alert-primary" role="alert">
  					<%=request.getAttribute("mensajeOk")%>
				</div>
		<% } %>
    	<% 
    	Producto producto = (Producto)request.getAttribute("producto");
    	if (producto != null){
    	%>
    	<form action="ControladorProducto" enctype="multipart/form-data" method="post">
    		<input type="hidden" class="form-control" id="codigo_producto" name="codigo_producto" value="<%=producto.getCodigo()%>">
    			<div class="form-row">
    		<div class="form-group col-md-6">
      			<label for="nombre">Nombre del producto</label>
      			<input type="text" class="form-control" id="nombre" name="nombre" value="<%=producto.getNombre()%>">
    		</div>
    		<div class="form-group col-md-6">
      			<label for="precio">Precio unitario de venta</label>
      			<input type="text" id="precio" name="precio" class="form-control" placeholder="Ejemplo: 175.50" value="<%=producto.getPrecioVenta()%>">
    		</div>
  		</div>
  		<div class="form-row">
    		
  		</div>
  		<div class="form-row">
    		<div class="form-group col-md-6">
      			<label for="imagen">Imagen del producto</label> <br>
      			<input type="file" id="imagen" name="imagen" value="">
    		</div>
  		</div>
  			<button type="submit" class="btn btn-primary" name="accion" value="EditarProducto">Guardar</button>
		</form>
		<%}}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	 	</div>
	</body>
</html>