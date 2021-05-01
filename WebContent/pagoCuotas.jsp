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
	if (sesion.getAttribute("usuario_admin") != null) { 
		String dni = (String)request.getAttribute("dni");
	%>
	<div class="m-2">
		<h1>Buscar Cuotas</h1>
		<form action="ControladorCuota" method="post">
	   		<div class = "form-row">
	       		<div class="form-group col-md-10">
	       			<label for="dniCliente">Ingrese el dni del cliente:</label>
      				<input type="text" id="dniCliente" name="dniCliente" class="form-control" value="<%=(dni != null)? dni: "" %>" required/>
	       		</div>
	       		<div class="form-group col-md-2" style="padding-top: 31px">
	       			<button type="submit" class="btn btn-primary" name="accion" value="buscarCuotas" style="width: 175px;" onclick="return validacion_pago_cuotas();">Buscar Cuotas</button>
	       		</div>
	       	</div>
		</form>
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
	   	<form action="ControladorCuota" method="post">
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>DNI</th>
					<th>MES</th>
					<th>AÑO</th>
					<th>FECHA PAGO</th>
					<th>VALOR</th>
					<th></th>
	   			</tr>
	   		</thead>
	   			<%
	   				if (request.getAttribute("cuotasAnio") != null)
	   				{
	   				for (Cuota cuota:(ArrayList<Cuota>)request.getAttribute("cuotasAnio"))
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
						<td>
							<input type="hidden" name="dniCliente" value="<%=cuota.getDniCliente()%>"/>
							<input type="hidden" name="mesCuota" value="<%=cuota.getMes()%>"/>
							<input type="hidden" name="anioCuota" value="<%=cuota.getAnio()%>"/>
							<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="accion" value="registrarPago">
									Registrar Pago
							</button>
						</td>
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