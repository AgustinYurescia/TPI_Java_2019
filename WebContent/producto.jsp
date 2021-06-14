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
	     		<form action="ControladorPedido">
	     			<div class="form-row">
			     		<div class="form-group col-md-12">
			     			<div class="form-inline">
			    				Precio:
			    				<input type="input" class="form-control" id="precio" name="precio" value="<%=String.format("%.2f", prod.getPrecioVenta())%>" style="border:transparent !important" disabled>
		    				</div>
		  				</div>
		  			</div>
		  			<div class="form-row">
		  				<div class="form-group col-md-12">
		  					<div class="form-inline">
			    				Stock:
			    				<input type="number" class="form-control" id="stock_disponible" name="stock_disponible" value="<%=prod.getStock()%>" style="border:transparent !important" disabled>
			    				<input type="hidden" class="form-control" id="stock" name="stock" value="<%=prod.getStock()%>" style="border:transparent !important">
		    				</div>
		  				</div>
		  			</div>
		  			<div class="form-row">
  					<div class="form-group col-md-12">
  						<div class="form-inline">
	    					Ingrese la cantidad: 
	    					<input type="number" class="form-control" id="cantidad" name="cantidad" value="1" style="margin-left:2px !important; width:75px !important;">
	    					<input type="hidden" id="codigo_producto" name="codigo_producto" value="<%=prod.getCodigo()%>">
    					</div>
  					</div>
  					</div>
  					<% if(mensaje != null){ %>
  					<div class="alert alert-danger" role="alert" align = "center"><%=mensaje%></div>
  					<%}%>
  					<div>
  						<button type="submit" class="btn btn-primary mb-2" id="add_cart_button" name="accion" value="agregarAlCarrito" onclick="return validacion_agregar_al_carrito();">Agregar al carrito</button>
  					</div>
				</form>
	 		</div>
	 	</div>
	 </div>
	 <jsp:include page="footer.jsp"/>
</body>
<script>
	$('#cantidad').on
	('input',
		function() {
			var add_cart_button = document.getElementById('add_cart_button');
			if(document.getElementById('stock_disponible')){
		        var stock = document.getElementById('stock_disponible').value;
		    }
		    if(document.getElementById('cantidad')){
		        var cantidad = document.getElementById('cantidad').value;
		    }
		    if(stock && cantidad && parseInt(cantidad,10) > parseInt(stock, 10)){
		    		add_cart_button.disabled = true
		    		add_cart_button.innerText = "Sin Stock"
		    	}
		    else{
		    	add_cart_button.disabled = false
		    	add_cart_button.innerText = "Agregar al carrito"
		    }
		}
	)
</script>
</html>