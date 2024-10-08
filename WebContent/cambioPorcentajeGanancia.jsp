<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
	   	<h1>Nuevo Porcentaje de Ganancia</h1>
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
      				<label for="porcentajeGanancia">Ingrese el nuevo porcentaje de ganancia:</label>
      				<input type="number" class="form-control" name="porcentajeGanancia" id="porcentajeGanancia" placeholder="Ejemplo: 25" required/>														
    			</div>
    			<div class="form-group col-md-2" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="nuevoPorcentajeGanancia" onclick="return validacion_cambio_porcentaje_ganancia();">Registrar</button>	
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
	$(document).ready( function () {
		$.ajax({
			type : 'GET',
			url : '/TPI_Java_2019/ControladorPlazosPrecios',
			data : {
				'ajax_action' : 'obtenerPorcGanancia',
			}
		}).done(
				function(porc) {
					var porc_actual = $(document.getElementById('valor_actual'));
					porc_actual.html("Valor actual: " + (porc * 100).toFixed(2).toString());
				}).fail(function() {
					alert('Hubo un error interno')
		})
	});
</script>
</html>