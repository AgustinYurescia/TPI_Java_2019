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
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
	<% HttpSession sesion = request.getSession(true);
	   if (sesion.getAttribute("usuario_admin") != null) { %>
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
      				<label for="telefono">T�lefono celular</label>
      				<input type="text" class="form-control" id="telefono" name="telefono" placeholder="Sin el 0 y sin el 15" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="direccion">Direcci�n</label>
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
      				<label for="contrasena">Contrase�a</label>
      				<input type="Password" id="contrasena" name="contrasena" class="form-control" placeholder="" required>
    			</div>
    			<div class="form-group col-md-4">
      				<label for="contrasena2">Repita la contrase�a</label>
      				<input type="Password" id="contrasena2" name="contrasena2" class="form-control" placeholder="" required>
      				<input type="hidden" id="es_socio" name="es_socio" value="1" >
    			</div> 
  			</div>
  			<div>
  				<a class="py-2 d-none d-md-inline-block" href="hacerSocio.jsp"><font face="Calibri" color="#58272d">�El cliente ya se encuentra registrado? - Click aqu�</font></a>
  			</div>
  			<div>
    			<button type="submit" class="btn btn-primary" name="accion" value="alta" onclick="return validacion_cliente();">Registrar</button>
  			</div>  			
		</form>
		<%}else{
			response.sendRedirect("loginAdmin.jsp");
	  		}
	 	%>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>