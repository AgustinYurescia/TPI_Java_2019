<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Vinoteca Gatti</title>
  	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  	<link rel="stylesheet" href="CSS/vinoteca.css"/>
  	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</head>
<body>
	<% HttpSession sesion = request.getSession(true); %>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  	<a class="navbar-brand" href="ControladorDeLinks?accion=indexCliente"><img src="Images/logo.png" alt="Logo" width="100" height="30"></img></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
   		<span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
    	<ul class="navbar-nav mr-auto">
        	<li class="nav-item active">
          		<a class="nav-link" href="ControladorDeLinks?accion=indexCliente">Home<span class="sr-only">(current)</span></a>
        	</li>
         <% 
	   	 if (sesion.getAttribute("usuario_admin") != null) { 
	   	 %>
	   	 	<li class="nav-item dropdown">
          		<a class="nav-link dropdown-toggle" href="#" id="productosDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              		Productos
            	</a>
            	<div class="dropdown-menu" aria-labelledby="productosDropdown">
              		<a class="dropdown-item" href="ControladorDeLinks?accion=actualizarStock">Actualizar stock</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=altaProducto">Alta Producto</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=editarProducto">Editar Producto</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=listarProductosAdmin">Listado de Productos</a>
           		 </div>
          	</li>
          	<li class="nav-item dropdown">
          		<a class="nav-link dropdown-toggle" href="#" id="productosDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              		Categor�as de Producto
            	</a>
            	<div class="dropdown-menu" aria-labelledby="productosDropdown">
              		<a class="dropdown-item" href="ControladorDeLinks?accion=altaCategoria">Nueva Categor�a</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=modificacionCategoria">Editar Categor�a</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=bajaCategoria">Baja Categor�a</a>
           		 </div>
          	</li>
          	<li class="nav-item dropdown">
            	<a class="nav-link dropdown-toggle" href="#" id="pedidosDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              		Pedidos
           		</a>
            	<div class="dropdown-menu" aria-labelledby="pedidosDropdown">
              		<a class="dropdown-item" href="ControladorDeLinks?accion=listarPedidosAdmin">Listado de Pedidos Pendientes</a>
              		<a class="dropdown-item" href="confirmarEntrega.jsp">Confirmar Entrega</a>
            	</div>
          	</li>
          	<li class="nav-item dropdown">
            	<a class="nav-link dropdown-toggle" href="#" id="clientesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              		Clientes
            	</a>
            	<div class="dropdown-menu" aria-labelledby="clientesDropdown">
              		<a class="dropdown-item" href="altaSocio.jsp">Nuevo Socio</a>
              		<a class="dropdown-item" href="ControladorCliente?accion=bajaSociosDeudores">Baja Socios Deudores</a>
            	</div>
          	</li>
          	<li class="nav-item dropdown">
            	<a class="nav-link dropdown-toggle" href="#" id="clientesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              		Cuotas Socio
            	</a>
            	<div class="dropdown-menu" aria-labelledby="clientesDropdown">
            		<a class="dropdown-item" href="ControladorDeLinks?accion=pagoCuotas">Buscar Para Pago</a>
              		<a class="dropdown-item" href="ControladorCuota?accion=generarCuotas">Generar Cuotas</a>
            	</div>
          	</li>
        </ul>
        
        <ul class="navbar-nav mr-right">  
        
        	<li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="cuentaAdminDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <jsp:include page="SVG/login.svg"/>
              Opciones de Cuenta
            </a>
            <div class="dropdown-menu" aria-labelledby="cuentaAdminDropdown">
            		<a class="dropdown-item" href="ControladorDeLinks?accion=cambioContrasenaAdmin">Cambiar Contrase�a</a>
            		<a class="dropdown-item" href="ControladorLoginAdmin?accion=logout">Cerrar Sesi�n</a>
            </div>  
          </li>	
        </ul>
        <%
	   	}else if (sesion.getAttribute("usuario_cliente") != null) { 
        %>
          	<li class="nav-item dropdown">
            	<a class="nav-link dropdown-toggle" href="#" id="postulacionDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              	Productos
            	</a>
            	<div class="dropdown-menu" aria-labelledby="productosCliDropdown">
              		<a class="dropdown-item" href="ControladorDeLinks?accion=listarProductosCliente">Listado de Productos</a>
            	</div>
          	</li>
          	
          	<li class="nav-item dropdown">
            	<a class="nav-link dropdown-toggle" href="#" id="postulacionDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              	Pedidos
            	</a>
            	<div class="dropdown-menu" aria-labelledby="productosCliDropdown">
              		<a class="dropdown-item" href="ControladorDeLinks?accion=listadoPedidosCliente">Mis pedidos</a>
            	</div>
          	</li>
          	
        </ul>
        
        <ul class="navbar-nav mr-right">
        
        	<li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="cuentaDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <jsp:include page="SVG/login.svg"/>
              Cuenta
            </a>
            <div class="dropdown-menu" aria-labelledby="cuentaCliDropdown">
            		<a class="dropdown-item" href="ControladorDeLinks?accion=cambioContrasena">Cambiar Contrase�a</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=modificar_cliente">Editar Datos</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=bajaCliente">Darse de Baja</a>
              		<a class="dropdown-item" href="ControladorLogin?accion=logout">Cerrar Sesi�n</a>
            </div>  
          </li>
        
          <li>
            <a href="ControladorDeLinks?accion=carrito" class="nav-link">
              	<jsp:include page="SVG/carrito.svg"/>
            	Carrito
            </a>
          </li>
        </ul>
       	<%
       	}else{
      	%>
       		<li class="nav-item">
  				<a class="nav-link" href="ControladorDeLinks?accion=listarProductosCliente">Productos</a>
			</li>
		</ul>
		<ul class="navbar-nav mr-right">
		
			<li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="loginDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <jsp:include page="SVG/login.svg"/>
              Iniciar Sesi�n
            </a>
            <div class="dropdown-menu" aria-labelledby="cuentaCliDropdown">
              		<a class="dropdown-item" href="ControladorDeLinks?accion=inicioSesionAdmin">Login Admin</a>
              		<a class="dropdown-item" href="ControladorDeLinks?accion=inicioSesionCliente">Login Clientes</a>
            </div>  
          </li>

          <li>
            <a href="ControladorDeLinks?accion=registroCliente" class="nav-link">
              <jsp:include page="SVG/login.svg"/>
              Registrarse
            </a>
          </li>
        
          <li>
            <a href="ControladorDeLinks?accion=carrito" class="nav-link">
              	<jsp:include page="SVG/carrito.svg"/>
            	Carrito
            </a>
          </li>
        </ul>
		<%
       	}
       	%>
    </div>
  </nav>
</body>
</html>