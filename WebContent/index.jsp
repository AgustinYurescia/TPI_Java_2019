<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="services.ServicioProducto"%>
<% 
	ServicioProducto servicioProducto = new ServicioProducto();
	ArrayList<Producto> productos = servicioProducto.ObtenerProductos(0);
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<br/>
	<div class="container">
		<% 	
  		if(request.getAttribute("mensajeOk") != null){
  		%>
  		<div class="alert alert-success" role="alert" style="text-align: center;"><%=request.getAttribute("mensajeOk")%></div>
		<%}%>
		<% 	
  		if(request.getAttribute("mensajeError") != null){
  		%>
  		<div class="alert alert-danger" role="alert" style="text-align: center;"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
		<div class="jumbotron" align="center" style="background: #f9eac7 !important; padding-bottom:15%">
	    	<div class="container">
	      		<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="Images/logo.png" alt="" width="330" height="180"></a></h1>
	      		<%
	      		HttpSession sesion = request.getSession(true);
	      		if (sesion.getAttribute("usuario_admin") != null) {
	      		%>
	      			<p>Bienvenido al menú de admin</p>
	      			<p><a class="btn btn-primary btn-lg" href="ControladorDeLinks?accion=listarProductosAdmin" role="button" style="margin-top:10px; background: #58272d !important; border: #58272d !important">Ver Productos &raquo;</a></p>
	      		<%
	      		}
	      		else
	      		{
	      		%>
	      			<p style="text-align: center; font-weight:bold">Somos una empresa familiar dedicada a la comercialización de bebidas alcoholicas de primera calidad y a los mejores precios</p>
	      			<div class="productos">
	      				<%for (int i=0; i < 4; i++){%>
		      			<div class="producto">
		      				<div class="imagen-producto">
		      					<img src="ControladorDeImagenes?codigo=<%=productos.get(i).getCodigo()%>" width="160px" height="160px"/>
		      				</div>
		      				<div class="nombre-producto" style="font-size: 15px;">
		      					<%=productos.get(i).getNombre()%>
		      				</div>
		      				<div class="precio-producto">
		      					$<%=productos.get(i).getPrecioVenta()%>
		      				</div>
		      				<div class="ver-producto">
		      					<a class="" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=productos.get(i).getCodigo()%>">
		      						<button type="submit" class="btn btn-primary" name="accion" value="mostrar_producto" style="margin-top:10px; border-radius:15px; width:160px;">Comprar</button>
		      					</a>
		      				</div>
		      			</div>
		      			<%} %>			      						
	      			</div>
	      			<br/>
	      			<p><a class="btn btn-primary btn-lg" href="ControladorDeLinks?accion=listarProductosCliente" role="button" style="margin-top:10px; background: #58272d !important; border: #58272d !important">Ver Todos Los Productos &raquo;</a></p>
	      		<%
	      		}
	      		%>
	    	</div>
	  	</div>
  	</div>
  	<jsp:include page="footer.jsp"/>
</body>
</html>