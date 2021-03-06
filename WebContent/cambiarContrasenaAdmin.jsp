<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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
  <% HttpSession sesion = request.getSession(true);
	   	   if (sesion.getAttribute("usuario_admin") != null) { %>
  <body class="text-center">
  	<br>
  	<div class="d-flex justify-content-center align-items-center container">
    <form class="form-signin" action="ControladorAdmin" method="post">
  		<img class="mb-4" src="Images/logo.png" alt="" width="200" height="100">
  		<h1 class="h3 mb-3 font-weight-normal">Cambio de Contraseņa</h1>
  		<div class="form-group">
  			<label for="cont_act" class="sr-only">Contraseņa actual</label>
  			<input type="password" id="cont_act" name="cont_act" class="form-control" placeholder="Contraseņa actual" required>
  		</div>
  		<div class="form-group">
  			<label for="cont_nueva" class="sr-only">Contraseņa nueva</label>
  			<input type="password" id="cont_nueva" name="cont_nueva" class="form-control" placeholder="Contraseņa nueva" required>
  		</div>
  		<div class="form-group">
  			<label for="cont_nueva_rep" class="sr-only">Repita la contraseņa</label>
  			<input type="password" id="cont_nueva_rep" name="cont_nueva_rep" class="form-control" placeholder="Repita la contraseņa" required>
  		</div>
  		<%if(request.getAttribute("error_mensaje") != null){%>
  		<div class="alert alert-danger" role="alert"><%= request.getAttribute("error_mensaje") %></div>
  		<%}%>
  		<%if(request.getAttribute("ok_mensaje") != null){%>
  		<div class="alert alert-primary" role="alert"><%= request.getAttribute("ok_mensaje") %></div>
  		<%}%>
  		<button class="btn btn-lg btn-primary btn-block" type="submit" name="accion" value="cambio_contrasena" onclick="return validacion_cambiar_contrasena();">Cambiar</button>
	</form>
	</div>
	<jsp:include page="footer.jsp"/>
  </body>
  <%}else{
	  response.sendRedirect("loginAdmin.jsp");
  }
  %>
</html>