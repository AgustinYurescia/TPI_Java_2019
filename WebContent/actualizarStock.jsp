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
		<h1>Actualizar Stock</h1>
		<form action="ControladorProducto" method="POST">
			<div class="form-row">
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
 				<div class="form-group col-md-4">
   					<label for="nombre">Cantidad</label>
   					<input type="text" class="form-control" id="stock" name="stock" placeholder="Ej: 200" value="<%=(request.getAttribute("cantidad") != null)?request.getAttribute("cantidad"):""%>" required>
 				</div>
 				<div class="form-group col-md-4">
   					<label for="nombre">Precio Unitario</label>
   					<input type="text" class="form-control" id="precio" name="precio" placeholder="" value="<%=(request.getAttribute("precio") != null)?request.getAttribute("precio"):""%>" required>
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
			<button type="submit" class="btn btn-primary" name="accion" value="ActualizarStock" onclick="return validacion_producto();">Actualizar</button>
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
			var codigo_prod = $(document.getElementById('codigo_producto')).find('option:selected').val();
			$.ajax({
				type : 'POST',
				url : '/TPI_Java_2019/ControladorProducto',
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
		var total = parseInt(stock) + parseInt(stock_actual);
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
	
	$('#precio').on
	('input',
		function() {
		$.ajax({
			type : 'GET',
			url : '/TPI_Java_2019/ControladorPlazosPrecios',
			data : {
				'ajax_action' : 'obtenerPorcGanancia',
			}
		}).done(
				function(porc) {
					var precio = $(document.getElementById('precio')).val();
					var float_precio = parseFloat(precio)
					var precio_futuro = $(document.getElementById('precio_futuro'));
					precio_futuro.html("Nuevo precio: $" + (float_precio*(1 + porc)).toFixed(2).toString());
					if(!Number.isNaN(float_precio*(1 + porc)))
					{
						$(document.getElementById('precio_futuro').hidden = false);	
					}
					else
					{
						$(document.getElementById('precio_futuro').hidden = true);	
					}
				}).fail(function() {
					alert('Hubo un error al calcular el nuevo precio de venta del producto seleccionado')
		})
	})
</script>
</html>