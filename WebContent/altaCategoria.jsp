<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="CSS/vinoteca.css">
	<meta charset="ISO-8859-1">
	<title>Alta Categor�a</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
	<div class="container">
		<% 	
		HttpSession sesion = request.getSession(true);
		if (sesion.getAttribute("usuario_admin") != null){%>
		<h1>Nueva Categor�a</h1>
		<form action="ControladorCategoria" method="POST">
			<div class="form-row">
 				<div class="form-group col-md-11">
   					<label for="codigo_producto">Ingrese el nombre de la categor�a</label>
   					<input type="text" id="categoria" name="categoria" class="form-control" placeholder="Ej: Aperitivo" required>
 				</div>
 				<div class="form-group col-md-1" style="padding-top: 31px">
   					<button type="submit" class="btn btn-primary" name="accion" value="alta">Aceptar</button>
 				</div>
			</div>
		</form>
		<%if(request.getAttribute("mensajeOk") != null){%>
		<div class="alert alert-primary" role="alert"><%= request.getAttribute("mensajeOk") %></div>
		<%}%>
		<%if(request.getAttribute("mensajeError") != null){%>
		<div class="alert alert-danger" role="alert"><%= request.getAttribute("mensajeError") %></div>
		<%}%>
		<br/>
		<%
		if (request.getAttribute("categorias") != null)
		{
		%>
		<h5>Categor�as existentes</h5>
		<table class="table">
			<thead>
				<tr>
					<th>C�digo</th>
					<th>Nombre</th>
				</tr>
			</thead>
			<tbody>
			<% 
			for (Categoria c: (ArrayList<Categoria>)request.getAttribute("categorias"))
			{
			%>
				<tr>
					<td><%=c.getCodigo()%></td>
					<td><%=c.getDescripcion()%></td>
				</tr>
			<%
			}
			%>
			</tbody>
		</table>
		<%
		}
		%>
	</div>
		<%
		}
		else
		{
			response.sendRedirect("loginAdmin.jsp");
	  	}
	 	%>
</body>
</html>