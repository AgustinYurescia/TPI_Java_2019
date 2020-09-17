<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
	   	<h1>Eliminar Categoria</h1>
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
      				<label for="cantidadDias">Ingresa la cantidad de días para la entrega de pedidos:</label>
      				<input type="number" class="form-control" name="cantidadDias" id="cantidadDias" required/>														
    			</div>
    			<div class="form-group col-md-2" style="padding-top: 31px">
    				<button type="submit" class="btn btn-primary" name="accion" value="nuevoPlazoEntrega">Registrar</button>	
    			</div>
    		</div>
		</form>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>