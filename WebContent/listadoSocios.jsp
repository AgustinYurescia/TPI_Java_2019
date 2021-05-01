<%@page import="java.util.ArrayList"%>
<%@page import="modeloDAO.ClienteDAO"%>
<%@page import="modelo.Cliente"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="menu.jsp" />
</head>
<body style="weigth: 800px !important">
	<div class="container">
		<%
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") != null) {
		%>
		<div class="m-2">
			<h1>Listado de socios</h1>
			<%
				if (request.getAttribute("mensajeError") != null) {
			%>
			<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
			<%
				}
			%>
			<table class="table">
				<thead>
					<tr>
						<th>DNI</th>
						<th>Nombre</th>
						<th>Apellido</th>
						<th>Mail</th>
						<th>Teléfono</th>
						<th></th>
					</tr>
				</thead>
				<%
					if (request.getAttribute("socios") != null) {
							ArrayList<Cliente> socios = (ArrayList<Cliente>) request.getAttribute("socios");
							for (Cliente s : socios) {
				%>
				<tbody>
					<tr>
						<td style="padding-top: 20px"><%=s.getDni()%></td>
						<td style="padding-top: 20px"><%=s.getNombre()%></td>
						<td style="padding-top: 20px"><%=s.getApellido()%></td>
						<td style="padding-top: 20px"><%=s.getMail()%></td>
						<td style="padding-top: 20px"><%=s.getTelefono()%></td>
						<td>
							<form action="ControladorCuota" method="post">
								<input type="hidden" id="dniCliente" name="dniCliente" class="form-control" value="<%=s.getDni()%>" required/>
								<button type="submit" class="btn btn-primary" name="accion" value="buscarCuotas" style="width: 175px;"> <img src="SVG/Eye.svg"/> Ver Cuotas</button>
							</form>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
		<%
			}
			}
		%>
	</div>
</body>
</html>