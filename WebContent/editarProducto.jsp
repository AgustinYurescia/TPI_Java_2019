<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
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
	<div class="container">
		<h1>Editar Producto</h1>
		<form action="ControladorProducto" method="post">
  			<div class="form-row">
				<div class="form-group col-md-5">
					<label for="codigo_producto">filtrar nombre</label>
				 	<input type="text" class="form-control filter-input" id="selectSearch" placeholder="Buscar productos..." onkeyup="filtrarProductos()">
				</div>
    			<div class="form-group col-md-5">
    				<label for="codigo_producto">Seleccione el producto a editar:</label>
      				<select id="codigo_producto" name="codigo_producto" class="form-control">
        				<option selected>-</option>
        				<% 
						ProductoDAO ProdDAO = new ProductoDAO(); 
						List<Producto> lista = ProdDAO.obtenerTodos();
						Iterator<Producto>iter = lista.iterator();
						Producto prod = null;
						while(iter.hasNext()){
							prod=iter.next();
						%>
        					<option value="<%=prod.getCodigo()%>"><%=prod.getNombre()%></option>
        				<%}%>
     				</select>
    			</div>
    			<div class="form-group col-md-2" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="BuscarProductoEditar" style="width: 175px">Buscar</button>
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
  			<button type="submit" class="btn btn-primary" name="accion" value="EditarProducto" onclick="return validacion_producto();">Guardar</button>
		</form>
		<%}}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
<script>
	function filtrarProductos(){
		var input, filter, select, options, i;
		input = document.getElementById("selectSearch");
		filter = input.value.toLowerCase();
		select = document.getElementById("codigo_producto");
		options = select.getElementsByTagName("option");
		
		// Loop through all options in the select element and hide those that don't match the search query
		for (i = 0; i < options.length; i++) {
		    if (options[i].text.toLowerCase().indexOf(filter) > -1) {
		        options[i].style.display = "";
		    } else {
		        options[i].style.display = "none";
		    }
		}
	}
</script>
</html>