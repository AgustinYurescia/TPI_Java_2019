<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Listar Productos</title>
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
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=registroCliente"><font face="Calibri" color="Black">Registrarse</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=bajaCliente"><font face="Calibri" color="Black">Darse de Baja</font></a>
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
    					<a class="py-2 d-none d-md-inline-block" href="ControladorPedido?accion=listadoPedidosCliente"><font face="Calibri" color="Black">Listado de pedidos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=modificar_cliente"><font face="Calibri" color="Black">Modificar mis datos</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=carrito"><font face="Calibri" color="Black">Carrito</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=bajaCliente"><font face="Calibri" color="Black">Darse de Baja</font></a>
    					<a class="py-2 d-none d-md-inline-block" href="ControladorLogin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesi�n</font></a>
  					</div>
	   			</nav>
	   	<% } %>
	   <h5><font face="Calibri" color="Black"><label for="filtrar_por">Categor�a</label></font></h5>
	   <form action="ControladorProducto">
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<select id="filtrar_por" name="filtrar_por" class="form-control"  >
        				<option value = "TODOS" selected>Todos</option>
        				<% 
						CategoriaDAO catDAO = new CategoriaDAO(); 
						List<Categoria> listaCat = catDAO.obtener_todos();
						Iterator<Categoria>iterCat = listaCat.iterator();
						Categoria cat = null;
						while(iterCat.hasNext()){
								cat=iterCat.next();
						%>
        				<option value="<%=cat.getDescripcion()%>"><%=cat.getDescripcion()%></option>
        				<%}%>
     				</select>														
    			</div>
    			<div class="form-group col-md-6">
    			<button type="submit" class="btn btn-primary" name="accion" value="listar">Filtrar</button>	
    			</div>
    		</div>
		</form>
	   <div>
			<h1>Productos</h1>
			<table class="table table-striped">
				<thead>
					<tr>
						
						<th><font face="Calibri" color="Black">Codigo</font></th>
						<th><font face="Calibri" color="Black">Imagen</font></th>
						<th><font face="Calibri" color="Black">Nombre</font></th>
						<th><font face="Calibri" color="Black">Precio Venta</font></th>
						<th><font face="Calibri" color="Black">Stock</font></th>
						
					</tr>
				</thead>
						<% 
							ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("listado");
							for (Producto prod : lista){
						%>
				<tbody>
					<tr>
						<td><font face="Calibri" color="Black"><%=prod.getCodigo()%></font></td>
						<td><img src="ControladorDeImagenes?codigo=<%=prod.getCodigo()%>" width="80" height="80"/></td>
						<td><font face="Calibri" color="Blue"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=prod.getCodigo()%>"><%=prod.getNombre()%></a></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getPrecioVenta()%></font></td>
						<td><font face="Calibri" color="Black"><%=prod.getStock()%></font></td>
					</tr>
						<% }%> 
				</tbody>
			</table>
		</div>
	</body>
</html>