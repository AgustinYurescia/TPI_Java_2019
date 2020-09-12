<%@page import="modelo.Producto"%>
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
		Producto prod = (Producto)request.getAttribute("producto"); 
	   	String mensaje = (String)request.getAttribute("mensajeError");%>
	   	<br/>
	   	<br/>
	   	<div class="media position-relative">
	   		<img src="ControladorDeImagenes?codigo=<%=prod.getCodigo()%>" class="mr-3" width="400" height="400">
	   		<div class="media-body">
	     		<h5 class="mt-0"><%=prod.getNombre()%></h5>
	     		<p>
	     			<font face="Calibri" color="Black">Stock disponible = <%=prod.getStock()%></font><br/>
	     			<font face="Calibri" color="Black">Precio = <%=prod.getPrecioVenta()%></font>
	     		</p>
	     		<form class="form-inline" action="ControladorPedido">
  					<div class="form-group mb-2">
    					<label for="cantidad" class="sr-only">Cantidad</label>
    					<input type="number" class="form-control" id="cantidad" name="cantidad" placeholder="Cantidad" value="1">
    					<input type="hidden" id="codigo_producto" name="codigo_producto" value="<%=prod.getCodigo()%>">
  					</div>
  					<% if(mensaje != null){ %>
  					<div class="alert alert-danger" role="alert" align = "center"><%=mensaje%></div>
  					<%}%>
  					<div style="padding-left:20px">
  						<button type="submit" class="btn btn-primary mb-2" name="accion" value="agregarAlCarrito">Agregar al carrito</button>
  					</div>
				</form>
	 		</div>
	 	</div>
	 </div>
	 <jsp:include page="footer.jsp"/>
</body>
</html>