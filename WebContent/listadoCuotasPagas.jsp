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
		<h1>Listado de cuotas pagas</h1>
		<h4>Seleccione a�o y mes de pago de las cuotas</h4>
		<form action="ControladorCuota" method="get">
	   		<div class = "form-row">
	       		<div class="form-group col-md-5">
	       			<label for="anio">A�o:</label>
      				<select id="anio" name="anio" class="form-control">
        				<option selected>-</option>
     				</select>
	       		</div>
	       		<div class="form-group col-md-5">
	       			<label for="mes">Mes:</label>
	       			<select id="mes" name="mes" class="form-control">
        				<option selected>-</option>
     				</select>
	       		</div>
	       		<div class="form-group col-md-2" style="padding-top: 31px">
	       			<button type="submit" class="btn btn-primary" name="accion" id="buscarcuotas" value="listadoCuotasPagas" style="width: 175px;" onclick="return validacion_buscar_cuotas();">Buscar Cuotas</button>
	       		</div>
	       	</div>
		</form>
		<% 	
  		if(request.getAttribute("mensajeError") != null){
  		%>
  			<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
		<%if(request.getAttribute("mes") != null && request.getAttribute("anio") != null){%>
			<p>Cuotas pagadas en el mes: <%=request.getAttribute("mes") %>, del a�o  <%=request.getAttribute("anio") %></p>
		<%}%>
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>DNI CLIENTE</th>
	   				<th>NOMBRE</th>
	   				<th>APELLIDO</th>
	   				<th>TELEFONO</th>
					<th>MES</th>
					<th>A�O</th>
					<th>FECHA PAGO</th>
					<th>VALOR</th>
	   			</tr>
	   		</thead>
	   				<%
	   				if (request.getAttribute("cuotas") != null)
	   				{
	   				double total = 0.0;
	   				ArrayList<Cuota> cuotas = (ArrayList<Cuota>)request.getAttribute("cuotas");
	   				for (Cuota cuota: cuotas)
	   				{
	   					total = total + cuota.getValor();
	   				%>
				   		<tbody>
				   			<tr>
								<td style="padding-top: 20px"><%=cuota.getDniCliente()%></td>
								<td style="padding-top: 20px"><%=cuota.getCliente().getNombre()%></td>
								<td style="padding-top: 20px"><%=cuota.getCliente().getApellido()%></td>
								<td style="padding-top: 20px"><%=cuota.getCliente().getTelefono()%></td>
								<td style="padding-top: 20px"><%=cuota.getMes()%></td>
								<td style="padding-top: 20px"><%=cuota.getAnio()%></td>
								<td style="padding-top: 20px"><%=cuota.getFechaPago()%></td>
								<td style="padding-top: 20px"><%=cuota.getValor()%></td>
								<td>
									<input type="hidden" name="dniCliente" value="<%=cuota.getDniCliente()%>"/>
									<input type="hidden" name="mesCuota" value="<%=cuota.getMes()%>"/>
									<input type="hidden" name="anioCuota" value="<%=cuota.getAnio()%>"/>
								</td>
							</tr>
					<%}%>
					<tr>
						<td colspan="8" style="padding-top: 20px; text-align: right;"><b>Total cobrado: $<%=total%></b></td>
					</tr>
	   		</tbody>
	   	</table>
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
			url : '/TPI_Java_2019/ControladorCuota',
			data : {
				'ajax_action' : 'obtenerAniosPagas',
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
				url : '/TPI_Java_2019/ControladorCuota',
				data : {
					'ajax_action' : 'obtenerMesesPagas',
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
	        opt.innerHTML = "Seleccione un a�o primero";
			$(document.getElementById('mes').appendChild(opt));
			$(document.getElementById('buscarcuotas').disabled = true);
		}
	});
</script>
</html>