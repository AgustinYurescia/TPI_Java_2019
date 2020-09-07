<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
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
		<div class="container">
		<h1>Formulario de Registro</h1>
		<form action="ControladorCliente" method="post">
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="dni">DNI</label>
      				<input type="text" class="form-control" id="dni" name="dni" placeholder="DNI" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="dni">Nombre</label>
      				<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="Apellido">Apellido</label>
      				<input type="text" class="form-control" id="apellido" name="apellido" placeholder="Apellido" required>
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="telefono">Télefono celular</label>
      				<input type="text" class="form-control" id="telefono" name="telefono" placeholder="Sin el 0 y sin el 15" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="direccion">Dirección</label>
      				<input type="text" id="direccion" name="direccion" class="form-control" placeholder="Calle-nro-piso-depto" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="mail">E-Mail</label>
      				<input type="text" id="mail" name="mail" class="form-control" placeholder="xxxxxxx@xxxxx.xxx" required> 
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="usuario">Nombre de usuario</label>
      				<input type="text" class="form-control" id="usuario" name="usuario" placeholder="Nombre de usuario" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="contrasena">Contraseña</label>
      				<input type="Password" id="contrasena" name="contrasena" class="form-control" placeholder="" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="contrasena2">Repita la contraseña</label>
      				<input type="Password" id="contrasena2" name="contrasena2" class="form-control" placeholder="" required>
      				<input type="hidden" id="es_socio" name="es_socio" value="0" >
    			</div> 
  			</div>
  			<%if(request.getAttribute("registroClienteError") != null){%>
  			<div class="alert alert-danger" role="alert"><%= request.getAttribute("registroClienteError") %></div>
  			<%}%>
  			<%if(request.getAttribute("registroClienteOk") != null){%>
  			<div class="alert alert-primary" role="alert"><%= request.getAttribute("registroClienteOk") %></div>
  			<%}%>
  			<div class="form-row">
    			<button type="submit" class="btn btn-primary" name="accion" value="alta">Registrar</button>
  			</div>
		</form>
		</div>
	</body>
</html>