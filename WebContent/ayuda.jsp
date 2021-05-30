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
		<button class="accordion">1 - �C�mo puedo ser socio/a?</button>
		<div class="panel">
 			<p>Para hacerse socio/a deber� presentarse en nuestro local y la persona encargada de la recepci�n le solicitar� los datos necesarios.</p>
		</div>
		<button class="accordion">2 - �C�mo comprar?</button>
		<div class="panel">
		  	<p>Para comprar es muy simple: debe estar registrado/a y logueado/a en el sistema, luego busca uno por uno los productos que desea comprar en el listado de los mismos y le da al bot�n "Comprar". 
		  	En la ventana que se le abre, ingresa la cantidad deseada y agrega el producto al carrito. Una vez que desea finalizar la compra, se dirige al carrito y presiona el bot�n "Comprar" ubicado en la 
		  	esquina inferior izquierda, una vez hecho esto, siga los pasos para finalizar el pedido.</p>
		</div>
		<button class="accordion">3 - �Realizan env�os a domicilio?</button>
		<div class="panel">
		  	<p>Por el momento s�lo se pueden hacer pedidos para retirar y abonar en el local.</p>
		</div>
		<button class="accordion">4 - �Qu� sucede si no pago mi cuota de socio/a?</button>
		<div class="panel">
		  	<p>Si usted debe m�s de 4 (cuatro) cuotas, ser� dado/a de baja autom�ticamente. Usted podr� ser dado/a de alta nuevamente s�li si abona las cuotas que deb�a al momento de la baja.</p>
		</div>
		<button class="accordion">5 - �Puedo comprar sin estar registrado/a?</button>
		<div class="panel">
		  	<p>No, la �nica forma de poder realizar pedidos por este medio es estando registrado/a y haber iniciado sesi�n.</p>
		</div>
		<button class="accordion">6 - �Puedo comprar alcohol siendo menor de edad?</button>
		<div class="panel">
		  	<p>No, a la hora de retirar el pedido, en caso de que el mismo contenga bebidas alcoh�licas, usted deber� presentar su DNI y en caso de ser menor de 18 a�os el pedido no podr� ser entregado.</p>
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