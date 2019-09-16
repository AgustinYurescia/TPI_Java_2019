<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Alta productos</title>
	</head>
	<body>
		<div>
			<h1>Agregar Producto</h1>
			<form action="Controlador">
				Nombre: <input type="text" name="nombre"><br>
				Url de la imagen: <input type="text" name="url_imagen"><br>
				Categoría: <input type="text" name="codigoCategoria"><br>
				Cantidad: <input type="text" name="stock"><br>
				<input type="submit" name="accion" value="Agregar"><br>
			</form>
		</div>
		<a href="Controlador?accion=index">Volver a inicio</a>
	</body>
</html>