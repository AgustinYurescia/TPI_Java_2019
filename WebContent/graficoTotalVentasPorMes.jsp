<%@page import="modelo.Producto"%>
<%@page import="java.awt.*"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<% 
	HttpSession sesion = request.getSession(true);
	if (sesion.getAttribute("usuario_admin") != null) 
	{ 
	%>
		<div class="container">
			<% 	
			String mensaje = (String)request.getAttribute("mensajeError");
			%>
		   	<div class="jumbotron" align="center" style="background: #f9eac7 !important; padding-bottom:15%">
		   	<h2>Total de ventas</h2><hr/>
		    	<div class="container">
		    		<img src=<%="Images/graficoLinealVentas.png" %>>
		    		<br/>
		    		<br/>
		   			<img src=<%="Images/graficoTotalVentasPorMes.png" %>>
		   		</div>
		 	</div>
		 </div>
		 <jsp:include page="footer.jsp"/>
	<% 
	}
	else
	{
		response.sendRedirect("loginAdmin.jsp");
	}
	%>
</body>
</html>