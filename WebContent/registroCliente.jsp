<%@page import="modelo.Cliente"%>
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
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
		<%Cliente cliente = (Cliente)request.getAttribute("data_cliente"); %>
		<div class="container">
		<h1>Formulario de Registro</h1>
		<form action="ControladorCliente" method="post">
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="dni">DNI</label>
      				<input type="text" class="form-control" id="dni" name="dni" placeholder="DNI" required value="<%=(cliente != null)? (String)cliente.getDni(): "" %>"  >
    			</div>
    			<div class="form-group col-md-4">
      				<label for="dni">Nombre</label>
      				<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required value="<%=(cliente != null)? cliente.getNombre(): "" %>">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="Apellido">Apellido</label>
      				<input type="text" class="form-control" id="apellido" name="apellido" placeholder="Apellido" required value="<%=(cliente != null)? cliente.getApellido(): "" %>">
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="telefono">T�lefono celular</label>
      				<input type="text" class="form-control" id="telefono" name="telefono" placeholder="Sin el 0 y sin el 15" required value="<%=(cliente != null)? cliente.getTelefono(): "" %>">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="direccion">Direcci�n</label>
      				<input type="text" id="direccion" name="direccion" class="form-control" placeholder="Calle-nro-piso-depto" required value="<%=(cliente != null)? cliente.getDireccion(): "" %>">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="mail">E-Mail</label>
      				<input type="text" id="mail" name="mail" class="form-control" placeholder="xxxxxxx@xxxxx.xxx" required value="<%=(cliente != null)? cliente.getMail(): "" %>"> 
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-4">
      				<label for="usuario">Nombre de usuario</label>
      				<input type="text" class="form-control" id="usuario" name="usuario" placeholder="Nombre de usuario" required value="<%=(cliente != null)? cliente.getCliente_usuario(): "" %>">
    			</div>
    			<div class="form-group col-md-4">
      				<label for="contrasena">Contrase�a</label>
      				<input type="Password" id="contrasena" name="contrasena" class="form-control" placeholder="" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="contrasena2">Repita la contrase�a</label>
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
    			<button type="submit" class="btn btn-primary" name="accion" value="alta" onclick="return validacion_cliente();">Registrar</button>
  			</div>
		</form>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>