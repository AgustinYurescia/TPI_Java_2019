<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<head>
	<jsp:include page="menu.jsp"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }
      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body class="text-center">
	<%
		String mensajeOk = (String)request.getAttribute("mensajeOk");
		String mensajeError = (String)request.getAttribute("mensajeError");
	%>
	<div class="d-flex justify-content-center align-items-center container">
	<form class="form-signin" action="ControladorCliente" method="post">
  		<img class="mb-4" src="Images/logo.png" alt="" width="200" height="72">
  		<h1 class="h3 mb-3 font-weight-normal">Dar de Baja</h1>
  		<div class="form-group">
  			<label for="usuario_cliente" class="sr-only">Usuario</label>
  			<input type="text" id="usuario" name="usuario" class="form-control" placeholder="Usuario" required>
  		</div>
  		<div class="form-group">
  			<label for="contrasena" class="sr-only">Contraseña</label>
  			<input type="password" id="contrasena" name="contrasena" class="form-control" placeholder="Contraseña" required>
  		</div>
  		<%if(mensajeOk != null){%>
  		<div class="alert alert-primary" role="alert"><%= mensajeOk %></div>
  		<%}%>
  		<%if(mensajeError != null){%>
  		<div class="alert alert-danger" role="alert"><%= mensajeError %></div>
  		<%}%>
  		<button class="btn btn-lg btn-primary btn-block" type="submit" name="accion" value="baja_cliente" onclick="return validacion_baja_cliente();">Aceptar</button>
  		<p class="mt-5 mb-3 text-muted">&copy; 2019</p>
	</form>
	</div>
	<jsp:include page="footer.jsp"/>
  </body>
</html>