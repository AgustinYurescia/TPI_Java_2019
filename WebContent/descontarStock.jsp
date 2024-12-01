<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
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
		if (sesion.getAttribute("usuario_admin") != null){%>
		<h1>Descontar Stock</h1>
		<form action="ControladorProducto" method="POST">
			<div class="form-row">
				<div class="form-group col-md-2">
					<label for="codigo_producto">filtrar nombre</label>
				 	<input type="text" class="form-control filter-input" id="selectSearch" placeholder="Buscar productos..." onkeyup="filtrarProductos()">
				</div>
 				<div class="form-group col-md-4">
   					<label for="codigo_producto">Nombre del producto</label>
   					<select id="codigo_producto" name="codigo_producto" class="form-control">
     					<option selected>-</option>
     					<% 
						ProductoDAO ProdDAO = new ProductoDAO(); 
						List<Producto> lista = ProdDAO.obtenerTodos();
						Iterator<Producto>iter = lista.iterator();
						Producto prod = null;
						while(iter.hasNext()){
							prod=iter.next();
						%>
     					<option value="<%=prod.getCodigo()%>"><%=prod.getNombre()%></option>
     					<%}%>
  					</select>
 				</div>
 				<div class="form-group col-md-3">
   					<label for="nombre">Cantidad a Descontar</label>
   					<input type="text" class="form-control" id="stock" name="stock" placeholder="Ej: 200" value="<%=(request.getAttribute("cantidad") != null)?request.getAttribute("cantidad"):""%>" required>
 				</div>
			</div>
			<%if(request.getAttribute("mensajeOk") != null){%>
  			<div class="alert alert-primary" role="alert"><%= request.getAttribute("mensajeOk") %></div>
  			<%}%>
  			<%if(request.getAttribute("mensajeError") != null){%>
  			<div class="alert alert-danger" role="alert"><%= request.getAttribute("mensajeError") %></div>
  			<%}%>
  			<ul id="product_info">
 			</ul>
			<button type="submit" class="btn btn-primary" name="accion" value="DescontarStock" onclick="return validacion_producto();">Actualizar</button>
		</form>
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
	$('#codigo_producto').on
	('change',
		function() {
			const baseUrl = '<%= request.getContextPath() %>';
			const endpoint = '/ControladorProducto';
			const fullUrl = baseUrl + endpoint;
			var codigo_prod = $(document.getElementById('codigo_producto')).find('option:selected').val();
			$.ajax({
				type : 'POST',
				url : fullUrl,
				data : {
					'ajax_action' : 'buscar_producto',
					'codigo_producto': codigo_prod,
				}
			}).done(
					function(producto) {
						$('#product_info').html(
						"<li>Nombre del producto: " + producto.nombre + "</li>" +
						"<li id='stock_actual' value=" + producto.stock +">Stock actual: " + producto.stock + "</li>" +
						"<li>Precio de venta: $" + producto.precioVenta.toFixed(2) + "</li>" +
						"<li id='stock_futuro' hidden></li>" +
						"<li id='precio_futuro' hidden></li>"
						)
					}).fail(function() {
						alert('Hubo un error al recuperar el stock actual del producto seleccionado')
			})
		})
		
	$('#stock').on
	('input',
		function() {
		var stock = $(document.getElementById('stock')).val();
		var stock_actual = $(document.getElementById('stock_actual')).val();
		var stock_futuro = $(document.getElementById('stock_futuro'));
		var total = parseInt(stock_actual) - parseInt(stock);
		if(!Number.isNaN(total))
		{
			stock_futuro.html("Nuevo stock: " + total);
			$(document.getElementById('stock_futuro').hidden = false);
		}
		else
		{
			$(document.getElementById('stock_futuro').hidden = true);	
		}
		
	})
	
	function filtrarProductos(){
		console.log("filter product dropdown");
		var input, filter, select, options, i;
		input = document.getElementById("selectSearch");
		filter = input.value.toLowerCase();
		select = document.getElementById("codigo_producto");
		options = select.getElementsByTagName("option");
		
		// Loop through all options in the select element and hide those that don't match the search query
		for (i = 0; i < options.length; i++) {
		    if (options[i].text.toLowerCase().indexOf(filter) > -1) {
		        options[i].style.display = "";
		    } else {
		        options[i].style.display = "none";
		    }
		}
	}
	
</script>
</html>