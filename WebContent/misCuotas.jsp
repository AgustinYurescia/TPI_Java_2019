<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Cuota"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container">
	<% 
	HttpSession sesion = request.getSession(true);
	if (sesion.getAttribute("usuario_cliente") != null) { 
	%>
	<div class="m-2">
		<h1>Mis Cuotas</h1>
		<hr/>
		<% 	
  		if(request.getAttribute("mensajeOk") != null){
  		%>
  		<div class="alert alert-success" role="alert"><%=request.getAttribute("mensajeOk")%></div>
		<%}%>
		<% 	
  		if(request.getAttribute("mensajeError") != null){
  		%>
  		<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
		<form action="ControladorPDF" method="POST">
			<button type="submit" class="btn btn-primary" name="accion" value="exportarCuotasPdf">
				Exportar en PDF
			</button>
			<br><br>
		</form>
	   	<form>
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>DNI</th>
					<th>MES</th>
					<th>AÑO</th>
					<th>FECHA PAGO</th>
					<th>VALOR</th>
					<th>ESTADO</th>
	   			</tr>
	   		</thead>
	   			<%
	   				if (request.getAttribute("misCuotas") != null)
	   				{
	   				for (Cuota cuota:(ArrayList<Cuota>)request.getAttribute("misCuotas"))
	   				{
	   				
	   			%>	   				  		   				 	   			  
	   		<tbody>
	   			<tr>
					<td style="padding-top: 20px"><%=cuota.getDniCliente()%></td>
					<td style="padding-top: 20px"><%=cuota.getMes()%></td>
					<td style="padding-top: 20px"><%=cuota.getAnio()%></td>
					<%
						if (cuota.getFechaPago() ==  null)
						{
						%>
						<td style="padding-top: 20px">-</td>
						<td style="padding-top: 20px"><%=cuota.getValor()%></td>
						<td style="padding-top: 20px">A pagar</td>
						<%	
						}
						else
						{
						%>
						<td style="padding-top: 20px"><%=cuota.getFechaPago()%></td>
						<td style="padding-top: 20px"><%=cuota.getValor()%></td>
						<td style="padding-top: 20px">Pagada</td>
						<% 
						}
						%>
				</tr>
			<%}%>
	   		</tbody>
	   	</table>
	   	</form>
	   </div>
	   <%}}%>
	</div>
</body>
</html>