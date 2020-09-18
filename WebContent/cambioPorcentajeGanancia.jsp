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
    				<button type="submit" class="btn btn-primary" name="accion" value="nuevoPorcentajeGanancia">Registrar</button>	
    			</div>
    		</div>
		</form>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>