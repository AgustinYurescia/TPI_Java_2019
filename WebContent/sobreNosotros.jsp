<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="services.ServicioProducto"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<br/>
	<div class="container">
		<div class="jumbotron" align="center" style="background: #f9eac7 !important; padding-bottom:15%">
	    	<div class="form-row">
	      		<!--<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="Images/logo.png" alt="" width="330" height="180"></a></h1>-->
	      		<div class="form group col-md-12">
	      			<h1 class="display-5">Sobre nosotros</h1>
	      			<p style="text-align: justify; font-size: x-large;">
	      				Somos un empresa familiar fundada en la ciudad de Pujato (Santa Fe) en el año XXXX dedicada a la comercialización de bebidas. <br/>
	      				Contamos con una gran variedad de vinos, aperitivos, bebidas blancas, gaseosas y cervezas de primera calidad y a los mejores precios. <br/>
	      				Nuestro principal objetivo es satisfacer a nuestros clientes ofreciendo a los mismos una atención personalizada para que puedan contar en su mesa con
	      				la bedida ideal para cada momento y a un precio razonable. </br>
	      				No dude en contactarse con nosotros para que podamos brindarle una mejor atención y asesoramiento.
	      			</p>
	      		</div>
	      		<div class="form group col-md-12">
		      		<h2 class="display-5">¿Dónde nos encontramos?</h2>
					<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1989.2319157918594!2d-61.048329856722!3d-33.01758850069699!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x95b63357ddf4a06f%3A0xabe3514129f7a250!2sVinoteca%20El%20Viejo%20Tonel!5e0!3m2!1ses-419!2sar!4v1621978490889!5m2!1ses-419!2sar" 
							width="600" 
							height="470" 
							style="border:0;" 
							allowfullscreen="" 
							loading="lazy">
					</iframe>
				</div>
	    	</div>
	  	</div>
  	</div>
  	<jsp:include page="footer.jsp"/>
</body>
</html>