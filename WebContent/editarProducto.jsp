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
		<form action="ControladorProducto">
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="codigo_producto">Nombre del producto</label>
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
      				<label for="nombre">Nuevo nombre</label>
      				<input type="text" class="form-control" id="nuevo_nombre" name="nuevo_nombre" placeholder="">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="nombre">Nueva imagen</label></br>
      				<input type="file" class="" id="nueva_imagen" name="nueva_imagen">
    			</div>
  			</div>
  			<button type="submit" class="btn btn-primary" name="accion" value="EditarProducto">Guardar</button>
		</form>
		</div>
		<%}else{
			response.sendRedirect("loginAdmin.jsp");
	  	  }
	 	%>
	</body>
</html>