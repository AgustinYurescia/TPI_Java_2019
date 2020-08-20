<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
   	<title>Baja cliente</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/sign-in/">
	<link href="/docs/4.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
    <link href="signin.css" rel="stylesheet">
</head>
<body class="text-center">
	<jsp:include page="menu.jsp"/>
	<div class="d-flex justify-content-center align-items-center container">
	<form class="form-signin" action="ControladorCliente" method="post">
  		<img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="200" height="72">
  		<h1 class="h3 mb-3 font-weight-normal">Dar de Baja</h1>
  		<div class="form-group">
  			<label for="usuario_cliente" class="sr-only">Usuario</label>
  			<input type="text" id="usuario_cliente" name="usuario_cliente" class="form-control" placeholder="Usuario" required>
  		</div>
  		<div class="form-group">
  			<label for="contrasena" class="sr-only">Contraseņa</label>
  			<input type="password" id="contrasena" name="contrasena" class="form-control" placeholder="Contraseņa" required>
  		</div>
  		<%if(request.getAttribute("bajaClienteMensaje") != null){%>
  		<div class="alert alert-danger" role="alert"><%= request.getAttribute("bajaClienteMensaje") %></div>
  		<%}%>
  		<button class="btn btn-lg btn-primary btn-block" type="submit" name="accion" value="baja_cliente">Aceptar</button>
  		<p class="mt-5 mb-3 text-muted">&copy; 2019</p>
	</form>
	</div>
  </body>
</html>