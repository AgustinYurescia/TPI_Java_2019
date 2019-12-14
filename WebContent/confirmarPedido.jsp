<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "modelo.LineaPedido" %>
<%@ page import = "modeloDAO.ProductoDAO" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "modelo.Producto" %>


<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Confirmar Pedido</title>
	</head>
	<body>
		<% 	HttpSession sesion = request.getSession(true);
	   		if (sesion.getAttribute("usuario_cliente") == null) { %>
    			<nav class="site-header sticky-top py-1">
  					<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionCliente"><font face="Calibri" color="Black">Iniciar Sesi�n</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=inicioSesionAdmin"><font face="Calibri" color="Black">Iniciar Sesion Admin</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  					</div>
	   			</nav>	
	   	<%	}
	   		else{
		%>
				<nav class="site-header sticky-top py-1">
  					<div class="container d-flex flex-column flex-md-row justify-content-between">
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexCliente"><font face="Calibri" color="Black">Home</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listar&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorLogin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesi�n</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    					<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  					</div>
	   			</nav>
	   	<% } %>
		<% ArrayList linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); %>
	   	   <div>
			<h1>Confirmaci�n del pedido</h1>
			<table class="table table-striped">
				<thead>
					<tr>
						<th><font face="Calibri" color="Black">Imagen</font></th>
						<th><font face="Calibri" color="Black">Nombre</font></th>
						<th><font face="Calibri" color="Black">Precio Venta</font></th>
						<th><font face="Calibri" color="Black">Cantidad</font></th>
						<th><font face="Calibri" color="Black">Subtotal</font></th>
					</tr>
				</thead>
						<% 
							ProductoDAO proDAO = new ProductoDAO(); 
							Iterator<LineaPedido>iter = linea.iterator();
							LineaPedido lin;
							Producto pro;
							while(iter.hasNext()){
								lin=iter.next();
								pro = proDAO.buscar_producto(lin.getCodigo_producto());
						%>
				<tbody>
					<tr>
						<td><img src="ControladorDeImagenes?codigo=<%=pro.getCodigo()%>" width="80" height="80"/></td>
						<td><font face="Calibri" color="Blue"><%=pro.getNombre()%></font></td>
						<td><font face="Calibri" color="Black"><%=pro.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=lin.getCantidad()%></font></td>
						<td><font face="Calibri" color="Black"><%=lin.getSubtotal()%></font></td>
					</tr>
					<%}%>
				</tbody>
			</table>
			<% double total = (double)sesion.getAttribute("total"); %>
			<p align="right"><b><font face="calibri" color="black" size="6">Total: $<%=total%>&nbsp; &nbsp;</font></b></p>
			<p align="right"><a class="py-0 d-none d-md-inline-block" href="ControladorPedido?accion=FinalizarPedido"><button type="submit" class="btn btn-primary">Finalizar compra</button></a>&nbsp;&nbsp;&nbsp;&nbsp;</p>
		</div>
	</body>
</html>