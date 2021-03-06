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
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
		<% 	HttpSession sesion = request.getSession(true);
	   		if (sesion.getAttribute("usuario_cliente") != null) {
	    	ClienteDAO cliDAO = new ClienteDAO(); 
	   	    Cliente cli = cliDAO.buscar_cliente((String)sesion.getAttribute("usuario_cliente"));%>
	   	<div class="container"> 
			<h1>Formulario de Registro</h1>
			<form action="ControladorCliente" method="post">
	  			<div class="form-row">
	    			<div class="form-group col-md-6">
	      				<label for="dni">Nombre</label>
	      				<input type="text" value=<%=cli.getNombre()%> class="form-control" id="nombre" name="nombre" required>
	    			</div>
	    			<div class="form-group col-md-6">
	      				<label for="Apellido">Apellido</label>
	      				<input type="text" value=<%=cli.getApellido()%> class="form-control" id="apellido" name="apellido" required>
	    			</div>
	  			</div>
	  			<div class="form-row">
	    			<div class="form-group col-md-6">
	      				<label for="telefono">T�lefono celular</label>
	      				<input type="text" value=<%=cli.getTelefono()%> class="form-control" id="telefono" name="telefono" required>
	    			</div>
	    			<div class="form-group col-md-6">
	      				<label for="direccion">Direcci�n (Calle-nro-piso-depto)</label>
	      				<input type="text" value="<%=cli.getDireccion()%>" id="direccion" name="direccion" class="form-control" required>
	    			</div>
	    		</div>
	    		<div class="form-row">
	    			<div class="form-group col-md-12">
	      				<label for="mail">E-Mail</label>
	      				<input type="text" value=<%=cli.getMail()%> id="mail" name="mail" class="form-control" required>
	    			</div>
	  			</div>
	  			<div class="form-row">
	  				<div class="m-1">
	    				<button type="submit" class="btn btn-primary" name="accion" value="modificacion_cliente" onclick="return validacion_cliente();">Registrar</button>
	    			</div>
	  			</div>
			</form>
			<%}%>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>