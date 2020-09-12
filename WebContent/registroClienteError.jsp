<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="menu.jsp"/>
	</head>
	<body>
	   <%HttpSession sesion = request.getSession(true);%>
	   <div class="jumbotron" align="center">
    		<div class="container">
      		<h1 class="display-3"><a class="py-2 d-none d-md-inline-block" href="ControladorProducto?accion=index"><img class="mb-4" src="https://proveedorespvriviera.com/wp-content/uploads/2018/10/LogoVINOTECA_negro.png" alt="" width="400" height="150"></a></h1>
      		<p><%=request.getAttribute("registroClienteError")%></p>
      		<p><a class="btn btn-primary btn-lg" href="ControladorDeLinks?accion=registroCliente" role="button">Ingresar nuevamente &raquo;</a></p>
    		</div>
  		</div>
  		<jsp:include page="footer.jsp"/>
	</body>
</html>