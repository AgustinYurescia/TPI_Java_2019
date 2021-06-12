<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Cuota"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
	<% 
	HttpSession sesion = request.getSession(true);
	if (sesion.getAttribute("usuario_admin") != null) { 
		String mes = (String)request.getAttribute("mes");
		String anio = (String)request.getAttribute("anio");
	%>
	<div class="m-2">
		<h1>Buscar Cuotas</h1>
		<form action="ControladorCuota" method="post">
	   		<div class = "form-row">
	   			<div class="form-group col-md-5">
	       			<label for="anio">Año:</label>
      				<select id="anio" name="anio" class="form-control">
        				<option selected>-</option>
     				</select>
	       		</div>
	       		<div class="form-group col-md-5">
	       			<label for="mes">Mes:</label>
	       			<select id="mes" name="mes" class="form-control">
        				<option selected>Seleccione un año primero</option>
     				</select>
	       		</div>
	       		<div class="form-group col-md-2" style="padding-top: 31px">
	       			<button type="submit" class="btn btn-primary" name="accion" id="buscarcuotas" value="listadoCuotas" style="width: 175px;" onclick="return validacion_buscar_cuotas();">Buscar Cuotas</button>
	       		</div>
	       	</div>
		</form>
		<% 	
  		if(request.getAttribute("mensajeOk") != null){
  		%>
  		<div class="alert alert-success" role="alert"><%=request.getAttribute("mensajeOk")%></div>
		<%}%>
		<% 	
  		if(request.getAttribute("mensajeError") != null){
  			sesion.setAttribute("cuotas", null);
  		%>
  		<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
	   	<form action="ControladorCuota" method="post">
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>DNI CLIENTE</th>
					<th>NOMBRE</th>
					<th>APELLIDO</th>
					<th>TELEFONO</th>
					<th>MES</th>
					<th>AÑO</th>
					<th>FECHA PAGO</th>
					<th>VALOR</th>
					<th></th>
	   			</tr>
	   		</thead>
	   			<%
	   				if (sesion.getAttribute("cuotas") != null)
	   				{
	   				for (Cuota cuota:(ArrayList<Cuota>)sesion.getAttribute("cuotas"))
	   				{
	   				
	   			%>	   				  		   				 	   			  
	   		<tbody>
	   			<tr>
					<td style="padding-top: 20px"><%=cuota.getDniCliente()%></td>
					<td style="padding-top: 20px"><%=cuota.getCliente().getNombre()%></td>
					<td style="padding-top: 20px"><%=cuota.getCliente().getApellido()%></td>
					<td style="padding-top: 20px"><%=cuota.getCliente().getTelefono()%></td>
					<td style="padding-top: 20px"><%=cuota.getMes()%></td>
					<td style="padding-top: 20px"><%=cuota.getAnio()%></td>
					<%
						if (cuota.getFechaPago() ==  null)
						{
						%>
						<td style="padding-top: 20px">-</td>
						<td style="padding-top: 20px"><%=cuota.getValor()%></td>
						<td>
							<input type="hidden" name="dniCliente" value="<%=cuota.getDniCliente()%>"/>
							<input type="hidden" name="mesCuota" value="<%=cuota.getMes()%>"/>
							<input type="hidden" name="anioCuota" value="<%=cuota.getAnio()%>"/>
							<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="accion" value="registrarPagoDesdeListado">
									Registrar Pago
							</button>
						</td>
						<%	
						}
						else
						{
						%>
						<td style="padding-top: 20px"><%=cuota.getFechaPago()%></td>
						<td style="padding-top: 20px"><%=cuota.getValor()%></td>
						<td style="padding-top: 20px">Pagada</td>
						<% 
						}
						%>
				</tr>
			<%}%>
	   		</tbody>
	   	</table>
	   	</form>
	   </div>
	   <%}}%>
	</div>
</body>
<script>
	$(document).ready( function () {
		$(document.getElementById('buscarcuotas').disabled = true);
		$(document.getElementById('mes').disabled = true);
		$.ajax({
			type : 'GET',
			url : '/TPI_Java/ControladorCuota',
			data : {
				'ajax_action' : 'obtenerAnios',
			}
		}).done(
				function(anios) {
					var select = document.getElementById("anio");
					for(var i = 0; i < anios.length; i++) {
						 var opt = document.createElement('option');
						 opt.value = anios[i];
				         opt.innerHTML = anios[i];
						 select.appendChild(opt);
					}
				}).fail(function() {
					alert('Hubo un error interno')
		})
	});
	
	$("#anio").on('change', function () {
		var anio = $(document.getElementById('anio')).val();
		$(document.getElementById('mes')).find('option').remove();
		if (anio != "-")
		{
			$.ajax({
				type : 'GET',
				url : '/TPI_Java/ControladorCuota',
				data : {
					'ajax_action' : 'obtenerMeses',
					'anio': anio,
				}
			}).done(
					function(meses) {
						var select = document.getElementById("mes");
						for(var i = 0; i < meses.length; i++) {
							 var opt = document.createElement('option');
							 opt.value = meses[i];
					         opt.innerHTML = meses[i];
							 select.appendChild(opt);
						}
						$(document.getElementById('mes').disabled = false);
						$(document.getElementById('buscarcuotas').disabled = false);
					}).fail(function() {
						alert('Hubo un error interno')
			})
		}
		else
		{
			$(document.getElementById('mes').disabled = true);
			var opt = document.createElement('option');
			opt.value = "-";
	        opt.innerHTML = "Seleccione un año primero";
			$(document.getElementById('mes').appendChild(opt));
			$(document.getElementById('buscarcuotas').disabled = true);
		}
	});
</script>
</html>