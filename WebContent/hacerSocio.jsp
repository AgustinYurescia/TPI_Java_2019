<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ClienteDAO"%>
<%@page import="modelo.Cliente"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="menu.jsp" />
</head>
<body>
	<% HttpSession sesion = request.getSession(true);
	   if (sesion.getAttribute("usuario_admin") != null) { 
		   Cliente cliente = (Cliente)request.getAttribute("cliente");	   
	%>
	<div class="container">
		<h1>Formulario de Socio</h1>
		<% 
		if (cliente == null)
		{
		%>
		<form action="ControladorCliente" method="post">
			<div class="form-row">
				<div class="form group col-md-10">
					<input type="text" class="form-control" id="dni" name="dni"
						placeholder="Ingrese el DNI del cliente a hacer socio" required>
				</div>
				<div class="form group col-md-2">
					<button type="submit" class="btn btn-primary" name="accion"
						value="hacer_socio" style="width: 175px"
						onclick="return validacion_cliente();">Buscar</button>
				</div>
			</div>
		</form>
		<% 
		}
		if((String)request.getAttribute("mensajeOk") != null){%>
		<br>
		<div class="alert alert-success" role="alert">
			<%=(String)request.getAttribute("mensajeOk")%>
		</div>
		<%}%>
		<% if((String)request.getAttribute("mensajeError") != null){%>
		<br>
		<div class="alert alert-danger" role="alert">
			<%=(String)request.getAttribute("mensajeError")%>
		</div>
		<%}%>
		<% 
      	if (cliente != null){
      	%>
		<br>
		<form action="ControladorCliente" method="post">
			<div class="form-row">
				<input type="hidden" class="form-control" id="dni_cli"
					name="dni_cli" value="<%=cliente.getDni()%>" /> <input
					type="hidden" class="form-control" id="es_socio" name="es_socio"
					value=1 />
				<div class="form-group col-md-6">
					<label for="dni">Nombre</label> <input type="text"
						class="form-control" id="nombre" name="nombre"
						value="<%=cliente.getNombre()%>" disabled>
				</div>
				<div class="form-group col-md-6">
					<label for="Apellido">Apellido</label> <input type="text"
						class="form-control" id="apellido" name="apellido"
						value="<%=cliente.getApellido()%>" disabled>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-4">
					<label for="telefono">Télefono celular</label> <input type="text"
						class="form-control" id="telefono" name="telefono"
						value=<%=cliente.getTelefono()%> disabled>
				</div>
				<div class="form-group col-md-4">
					<label for="direccion">Dirección</label> <input type="text"
						id="direccion" name="direccion" class="form-control"
						value="<%=cliente.getDireccion()%>" disabled>
				</div>
				<div class="form-group col-md-4">
					<label for="mail">E-Mail</label> <input type="text" id="mail"
						name="mail" class="form-control" value=<%=cliente.getMail()%>
						disabled>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<!-- <label for="usuario">Nombre de usuario</label> -->
					<input type="hidden" class="form-control" id="usuario"
						name="usuario" value=<%=cliente.getCliente_usuario()%> disabled>
				</div>
				<div class="form-group col-md-6">
					<!-- <label for="contrasena">Contraseña</label> -->
					<input type="hidden" id="contrasena" name="contrasena"
						class="form-control" value=<%=cliente.getCliente_contrasena()%>
						disabled>
				</div>
			</div>
			<% 
  			if (cliente.getFecha_baja_socio() == null)
  			{
  			%>
			<div class="form-row" style="margin-left: 1px">¡Ya es socio!</div>
			<%
  			}
  			else
  			{
  			%>
			<div class="form-row" style="margin-left: 1px">
				<button type="submit" class="btn btn-primary" name="accion"
					value="hacer_socio">Registrar Socio</button>
			</div>
			<%
			}
  		}}
      	else
      	{
			response.sendRedirect("loginAdmin.jsp");	
      	}
	 	%>
		</form>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>