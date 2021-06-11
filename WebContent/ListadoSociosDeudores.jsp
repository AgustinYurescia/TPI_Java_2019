<%@page import="modelo.SocioDeudor"%>
<%@page import="modelo.Cuota"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="java.awt.*"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<% 
	HttpSession sesion = request.getSession(true);
	if (sesion.getAttribute("usuario_admin") != null) 
	{ 
	%>
		<div class="container" style=" margin-top: 1rem; margin-bottom: 1rem;">
			<% 	
			String mensaje = (String)request.getAttribute("mensajeError");
			if(mensaje != null){%>
				<div align="center" class="alert alert-warning"><p><b>Ha ocurrido un error, por favor intentelo nuevamente</b></p></div>
			<%}else{
				ArrayList<SocioDeudor> socios = (ArrayList<SocioDeudor>)request.getAttribute("sociosDeudores");%>
				<%if(socios.isEmpty()){%>
					<h1 style="text-align: center;">Todos los socios se encuentran al dia con las cuotas</h1>
				<%}else{%>
					<h1 style="text-align: center;">Listado de Socios Deudores</h1>
					<div style="display:inline">
						<div style="width:50%; float:left; text-Align: center;">
							<p>Seleccione la cantidad de cuotas minimas a mostrar</p>
						</div>
						<div style="width:50%; float:right;">
							<select id="filtro-cuotas-adeudadas" class="form-control"  >
								<option value ="todos">Todos</option>
		        				<option value = "1">1</option>
		        				<option value = "2">2</option>
		        				<option value = "3">3</option>
		        				<option value = "4">4</option>
		     				</select>
	     				</div>
     				</div>	
					<table class="table">
						<thead>
							<tr>
								<th>DNI</th>
								<th>Nombre</th>
								<th>Apellido</th>
								<th>Email</th>
								<th>Teléfono</th>
								<th>NRO Cuotas Adeudadas</th>
								<th>Ver Cuotas</th>
							</tr>
						</thead>
						<tbody>
						<% 
						double deuda_total = 0.0;
						for(SocioDeudor socio: socios){
							double total_deuda_socio = 0.0;
						%>
							<tr id="socio-<%=socio.getDni()%>" class="empleado-deudor" data-dni="<%=socio.getDni()%>" data-cantidadcuotasadeudadas="<%=socio.getCantidadCuotasAdeudadas()%>">
								<td><%=socio.getDni() %></td>
								<td><%=socio.getNombre() %></td>
								<td><%=socio.getApellido() %></td>
								<td><%=socio.getMail() %></td>
								<td><%=socio.getTelefono() %></td>
								<td><%=socio.getCantidadCuotasAdeudadas() %></td>
								<td>
									<form action="ControladorCuota" method="post">
										<input type="hidden" id="dniCliente" name="dniCliente" class="form-control" value="<%=socio.getDni()%>" required/>
										<button type="submit" class="btn btn-primary" name="accion" value="buscarCuotas" style="width: 175px;">Ir a pagar</button>
									</form>
								</td>
							</tr>
 							<tr class="empleado-deudor" data-dni="<%=socio.getDni()%>" data-cantidadcuotasadeudadas="<%=socio.getCantidadCuotasAdeudadas()%>">
								<td colspan="9" style="text-align: left;">
									<ul>
									<% for(Cuota c : socio.getCuotas()){ 
										total_deuda_socio = total_deuda_socio + c.getValor();
										deuda_total = deuda_total + c.getValor();
									%>
										<li><%=c.getMes()%>/<%=c.getAnio()%> - Valor: <%= c.getValor() %></li>
									<%}%>
										<%="Deuda total: $" + total_deuda_socio %>
									</ul>
								</td>
							</tr>
						<% }%>
						<tr>
							<td colspan="9" style="padding-top: 20px; text-align: right;"><b>Dinero total adeudado: $<%=deuda_total%></b></td>
						</tr>
						</tbody>
					</table>
					<form action="ControladorPDF" method="POST">
						<button type="submit" class="btn btn-primary" name="accion" value="exportarSociosDeudoresPdf">
							Exportar en PDF
						</button>
						<br><br>
					</form>
				<%}%>
			<% } %>
		   	
		 </div>
		 <jsp:include page="footer.jsp"/>
	<% 
	}
	else
	{
		response.sendRedirect("loginAdmin.jsp");
	}
	%>
</body>
<script>
	const selectElement = document.querySelector('#filtro-cuotas-adeudadas');
	
	selectElement.addEventListener('change', (e) => {
	    let selectElements = document.getElementsByClassName("empleado-deudor");
		let arrayElements = Array.from(selectElements);
	    arrayElements.map((elem) => {
	    	if (e.target.value == "todos"){
	    		elem.style.display = "revert";
	    	}
	    	else if(elem.dataset.cantidadcuotasadeudadas < e.target.value){
	    		elem.style.display = "none";
	    	}
	    	else{
	    		elem.style.display = "revert";
	    	}
	    });
	});
</script>
</html>