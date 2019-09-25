<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.CategoriaDAO"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>Alta de productos</title>
	</head>
	<body>
		<nav class="site-header sticky-top py-1">
  		<div class="container d-flex flex-column flex-md-row justify-content-between">
			<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="100" height="30"></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=indexAdmin"><font face="Calibri" color="Black">Home</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=listarAdmin&filtrar_por=TODOS"><font face="Calibri" color="Black">Listado de productos</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=altaProducto"><font face="Calibri" color="Black">Alta Producto</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorDeLinks?accion=actualizarStock"><font face="Calibri" color="Black">Actualizar Stock</font></a>
    		<a class="py-2 d-none d-md-inline-block" href="ControladorLoginAdmin?accion=logout"><font face="Calibri" color="Black">Cerrar Sesión</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
    		<a class="py-2 d-none d-md-inline-block" href=""><font face="Calibri" color="Black">xxxxx</font></a>
  	   	</div>
	   	</nav>
		<h1>Agregar Producto</h1>
		<form action="ControladorProducto">
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="nombre">Nombre del producto</label>
      				<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre del producto">
    			</div>
    			<div class="form-group col-md-6">
      				<label for="codigo_categoria">Categoría</label>
      				<select id="codigo_categoria" name="codigo_categoria" class="form-control">
        				<option selected>-</option>
        				<% 
						CategoriaDAO catDAO = new CategoriaDAO(); 
						List<Categoria> lista = catDAO.obtener_todos();
						Iterator<Categoria>iter = lista.iterator();
						Categoria cat = null;
						while(iter.hasNext()){
								cat=iter.next();
						%>
        				<option value="<%=cat.getCodigo()%>"><%=cat.getDescripcion()%></option>
        				<%}%>
     				</select>
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="cuil_proveedor">Cuil proveedor producto</label>
      				<input type="text" class="form-control" id="cuil_proveedor" name="cuil_proveedor" placeholder="xx-xxxxxxxx-x">
    			</div>
    			<div class="form-group col-md-6">
      				<label for="url_imagen">Url imagen</label>
      				<input type="text" id="url_imagen" name="url_imagen" class="form-control" placeholder="https://www.ejemplo.com/imagen.jpg">
    			</div>
  			</div>
  			<div class="form-row">
    			<div class="form-group col-md-6">
      				<label for="stock">Cantidad adquirida</label>
      				<input type="text" class="form-control" id="stock" name="stock" placeholder="Ejemplo: 100">
    			</div>
    			<div class="form-group col-md-6">
      				<label for="precio">Precio unitario</label>
      				<input type="text" id="precio" name="precio" class="form-control" placeholder="Ejemplo: 175.50">
    			</div>
  			</div>
  			<button type="submit" class="btn btn-primary" name="accion" value="Agregar">Agregar</button>
		</form>
	</body>
</html>