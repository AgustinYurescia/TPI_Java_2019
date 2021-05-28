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
		<h2>Preguntas Frecuentes</h2>
	<hr/>
		<button class="accordion">1 - ¿Cómo puedo ser socio/a?</button>
		<div class="panel">
 			<p>Para hacerse socio/a deberá presentarse en nuestro local y la persona encargada de la recepción le solicitará los datos necesarios.</p>
		</div>
		<button class="accordion">2 - ¿Cómo comprar?</button>
		<div class="panel">
		  	<p>Para comprar es muy simple: debe estar registrado/a y logueado/a en el sistema, luego busca uno por uno los productos que desea comprar en el listado de los mismos y le da al botón "Comprar". 
		  	En la ventana que se le abre, ingresa la cantidad deseada y agrega el producto al carrito. Una vez que desea finalizar la compra, se dirige al carrito y presiona el botón "Comprar" ubicado en la 
		  	esquina inferior izquierda, una vez hecho esto, siga los pasos para finalizar el pedido.</p>
		</div>
		<button class="accordion">3 - ¿Realizan envíos a domicilio?</button>
		<div class="panel">
		  	<p>Por el momento sólo se pueden hacer pedidos para retirar y abonar en el local.</p>
		</div>
		<button class="accordion">4 - ¿Qué sucede si no pago mi cuota de socio/a?</button>
		<div class="panel">
		  	<p>Si usted debe más de 4 (cuatro) cuotas, será dado/a de baja automáticamente. Usted podrá ser dado/a de alta nuevamente sóli si abona las cuotas que debía al momento de la baja.</p>
		</div>
		<button class="accordion">5 - ¿Puedo comprar sin estar registrado/a?</button>
		<div class="panel">
		  	<p>No, la única forma de poder realizar pedidos por este medio es estando registrado/a y haber iniciado sesión.</p>
		</div>
		<button class="accordion">6 - ¿Puedo comprar alcohol siendo menor de edad?</button>
		<div class="panel">
		  	<p>No, a la hora de retirar el pedido, en caso de que el mismo contenga bebidas alcohólicas, usted deberá presentar su DNI y en caso de ser menor de 18 años el pedido no podrá ser entregado.</p>
		</div>
	</div>
  	<jsp:include page="footer.jsp"/>
  	<script>
		var acc = document.getElementsByClassName("accordion");
		var i;
		
		for (i = 0; i < acc.length; i++) {
		  acc[i].addEventListener("click", function() {
		    this.classList.toggle("active");
		    var panel = this.nextElementSibling;
		    if (panel.style.display === "block") {
		      panel.style.display = "none";
		    } else {
		      panel.style.display = "block";
		    }
		  });
		}
	</script>
</body>
</html>