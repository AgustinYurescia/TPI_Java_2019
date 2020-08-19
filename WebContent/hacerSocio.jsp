<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ClienteDAO"%>
<%@page import="modelo.Cliente"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Nuevo Socio</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
	<% HttpSession sesion = request.getSession(true);
	   if (sesion.getAttribute("usuario_admin") != null) { %>
	   	<div class="container">
	   	<% 
	   	String mensaje = (String)request.getAttribute("mensaje_exito"); 
	   	if(mensaje != null){
	   	%>
	   	<div class="alert alert-success" role="alert">
	   		<%=mensaje%>
		</div>
	   	<%	
	   	}
	   	%>
	   	<h1>Formulario de Socio</h1>
	   	<form action="ControladorCliente" method="post">
	   		<div class="form-row">
    			<div class="form group col-md-6">
      				<input type="text" class="form-control" id="dni" name="dni" placeholder="Ingrese el DNI del cliente">
    			</div>
    			<div class="form group col-md-6">
      				<button type="submit" class="btn btn-primary" name="accion" value="buscar">Hacer Socio</button>
    			</div>
  			</div>
	   	</form>
	   	<% 
	   	Cliente cliente = (Cliente)request.getAttribute("cliente");
      	if (cliente != null){
      	%>
      	<br>
		<form action="ControladorCliente" method="post">
  			<div class="form-row">
      			<input type="hidden" class="form-control" id="dni_cli" name="dni_cli" value=<%=cliente.getDni()%>>
    			<div class="form-group col-md-6">
      				<label for="dni">Nombre</label>
      				<input type="text" class="form-control" id="nombre" name="nombre" value=<%=cliente.getNombre()%> disabled>
    			</div>
    			<div class="form-group col-md-6">
      				<label for="Apellido">Apellido</label>
      				<input type="text" class="form-control" id="apellido" name="apellido" value=<%=cliente.getApellido()%> disabled>
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="telefono">Télefono celular</label>
      				<input type="text" class="form-control" id="telefono" name="telefono" value=<%=cliente.getTelefono()%> disabled>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="direccion">Dirección</label>
      				<input type="text" id="direccion" name="direccion" class="form-control" value=<%=cliente.getDireccion()%> disabled>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="mail">E-Mail</label>
      				<input type="text" id="mail" name="mail" class="form-control" value=<%=cliente.getMail()%> disabled>
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="usuario">Nombre de usuario</label>
      				<input type="text" class="form-control" id="usuario" name="usuario" value=<%=cliente.getCliente_usuario()%> disabled>
    			</div>
    			<div class="form-group col-md-6">
      				<label for="contrasena">Contraseña</label>
      				<input type="Password" id="contrasena" name="contrasena" class="form-control" value=<%=cliente.getCliente_contrasena()%> disabled>
    			</div>
  			</div>
  			<div class="form-row">
    			<button type="submit" class="btn btn-primary" name="accion" value="registrar_socio">Registrar Socio</button>
  			</div>
		</form>
		<%}else{}%>
		<%}else{
			response.sendRedirect("loginAdmin.jsp");
	  		}
	 	%>
		</div>
	</body>
</html>