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
		String mes = (String)request.getAttribute("mes");
		String anio = (String)request.getAttribute("anio");
	%>
	<div class="m-2">
		<h1>Buscar Cuotas</h1>
		<form action="ControladorCuota" method="post">
	   		<div class = "form-row">
	       		<div class="form-group col-md-5">
	       			<label for="mes">Mes:</label>
	       			<select id="mes" name="mes" class="form-control">
        				<option selected>-</option>
        				<% 
						for(int i=1; i<=12; i++){
						%>
        					<option value="<%=i%>"><%=i%></option>
        				<%}%>
     				</select>
	       		</div>
	       		<div class="form-group col-md-5">
	       			<label for="anio">A�o:</label>
      				<select id="anio" name="anio" class="form-control">
        				<option selected>-</option>
        				<% 
						for(int i=2020; i<=2200; i++){
						%>
        					<option value="<%=i%>"><%=i%></option>
        				<%}%>
     				</select>
	       		</div>
	       		<div class="form-group col-md-2" style="padding-top: 31px">
	       			<button type="submit" class="btn btn-primary" name="accion" value="listadoCuotas" style="width: 175px;" onclick="return validacion_buscar_cuotas();">Buscar Cuotas</button>
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
  			sesion.setAttribute("cuotas", null);
  		%>
  		<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
	   	<form action="ControladorCuota" method="post">
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>DNI CLIENTE</th>
					<th>MES</th>
					<th>A�O</th>
					<th>FECHA PAGO</th>
					<th>VALOR</th>
					<th></th>
	   			</tr>
	   		</thead>
	   			<%
	   				if (sesion.getAttribute("cuotas") != null)
	   				{
	   				for (Cuota cuota:(ArrayList<Cuota>)sesion.getAttribute("cuotas"))
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
							<button type="submit" class="btn btn-outline-info" style="color: white;  width:200 ; height:200;" name="accion" value="registrarPagoDesdeListado">
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