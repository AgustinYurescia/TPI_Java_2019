<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
	   	<h1>Nuevo Valor de Cuotas</h1>
	   	<% 	
  			if(request.getAttribute("mensajeError") != null){
  			%>
  				<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
			<%}%>
			<% 	
  			if(request.getAttribute("mensajeOk") != null){
  			%>
  				<div class="alert alert-primary" role="alert"><%=request.getAttribute("mensajeOk")%></div>
		<%}%>
	   	<form action="ControladorPlazosPrecios" method="POST">
  			<div class="form-row">
    			<div class="form-group col-md-10">
      				<label for="valorCuota">Ingrese el nuevo valor de cuotas:</label>
      				<input type="number" class="form-control" name="valorCuota" id="valorCuota" required/>														
    			</div>
    			<div class="form-group col-md-2" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="nuevoValorCuotas" onclick="return validacion_cambio_valor_cuotas();">Registrar</button>	
    			</div>
    		</div>
    		<div class="form-row">
    			<div class="form-group col-md-12">
      				<p id="valor_actual"></p>														
    			</div>
    		</div>
		</form>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
<script>
	const baseUrl = '<%= request.getContextPath() %>';
	const endpoint = '/ControladorPlazosPrecios';
	const fullUrl = baseUrl + endpoint;
	$(document).ready( function () {
		$.ajax({
			type : 'GET',
			url : fullUrl,
			data : {
				'ajax_action' : 'obtenerValorCuotas',
			}
		}).done(
				function(valor) {
					var valor_actual = $(document.getElementById('valor_actual'));
					valor_actual.html("Valor actual: " + valor.toFixed(2).toString());
				}).fail(function() {
					alert('Hubo un error interno')
		})
	});
</script>
</html>