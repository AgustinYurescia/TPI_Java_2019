<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@page import="modelo.Cliente"%>
<%@page import="modeloDAO.ClienteDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Sign In</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<% 	HttpSession sesion = request.getSession(true);
	   		if (sesion.getAttribute("usuario_cliente") != null) {
	    	ClienteDAO cliDAO = new ClienteDAO(); 
	   	    Cliente cli = cliDAO.buscar_cliente((String)sesion.getAttribute("usuario_cliente"));%>
	   	<div class="container"> 
			<h1>Formulario de Registro</h1>
			<form action="ControladorCliente" method="post">
	  			<div class="form-row">
	    			<div class="form-group col-md-4">
	      				<label for="dni">Nombre</label>
	      				<input type="text" value=<%=cli.getNombre()%> class="form-control" id="nombre" name="nombre">
	    			</div>
	    			<div class="form-group col-md-4">
	      				<label for="Apellido">Apellido</label>
	      				<input type="text" value=<%=cli.getApellido()%> class="form-control" id="apellido" name="apellido">
	    			</div>
	  			</div>
	  			<div class="form-row">
	    			<div class="form-group col-md-4">
	      				<label for="telefono">Télefono celular</label>
	      				<input type="text" value=<%=cli.getTelefono()%> class="form-control" id="telefono" name="telefono">
	    			</div>
	    			<div class="form-group col-md-4">
	      				<label for="direccion">Dirección (Calle-nro-piso-depto)</label>
	      				<input type="text" value="<%=cli.getDireccion()%>" id="direccion" name="direccion" class="form-control">
	    			</div>
	    			<div class="form-group col-md-4">
	      				<label for="mail">E-Mail</label>
	      				<input type="text" value=<%=cli.getMail()%> id="mail" name="mail" class="form-control">
	    			</div>
	  			</div>
	  			<div class="form-row">
	  				<div class="m-3">
	    				<button type="submit" class="btn btn-dark" name="accion" value="modificacion_cliente">Registrar</button>
	    			</div>
	  			</div>
			</form>
			<%}%>
		</div>
		
	</body>
</html>