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
		<h1>Listado de cuotas pagas</h1>
		<h4>Seleccione año y mes de pago de las cuotas</h4>
		<form action="ControladorCuota" method="get">
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
	       			<label for="anio">Año:</label>
      				<select id="anio" name="anio" class="form-control">
        				<option selected>-</option>
        				<% 
						for(int i=2020; i<=2040; i++){
						%>
        					<option value="<%=i%>"><%=i%></option>
        				<%}%>
     				</select>
	       		</div>
	       		<div class="form-group col-md-2" style="padding-top: 31px">
	       			<button type="submit" class="btn btn-primary" name="accion" value="listadoCuotasPagas" style="width: 175px;" onclick="return validacion_buscar_cuotas();">Buscar Cuotas</button>
	       		</div>
	       	</div>
		</form>
		<% 	
  		if(request.getAttribute("mensajeError") != null){
  		%>
  			<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
		<%}%>
		<p>Cuotas pagadas en el mes: <%=request.getAttribute("mes") %>, del año  <%=request.getAttribute("anio") %></p>
	   	<table class="table">
	   		<thead>
	   			<tr>
	   				<th>DNI CLIENTE</th>
	   				<th>NOMBRE</th>
	   				<th>APELLIDO</th>
	   				<th>TELEFONO</th>
					<th>MES</th>
					<th>AÑO</th>
					<th>FECHA PAGO</th>
					<th>VALOR</th>
	   			</tr>
	   		</thead>
	   				<%
	   				if (request.getAttribute("cuotas") != null)
	   				{
	   				double total = 0.0;
	   				ArrayList<Cuota> cuotas = (ArrayList<Cuota>)request.getAttribute("cuotas");
	   				for (Cuota cuota: cuotas)
	   				{
	   					total = total + cuota.getValor();
	   				%>
				   		<tbody>
				   			<tr>
								<td style="padding-top: 20px"><%=cuota.getDniCliente()%></td>
								<td style="padding-top: 20px"><%=cuota.getCliente().getNombre()%></td>
								<td style="padding-top: 20px"><%=cuota.getCliente().getApellido()%></td>
								<td style="padding-top: 20px"><%=cuota.getCliente().getTelefono()%></td>
								<td style="padding-top: 20px"><%=cuota.getMes()%></td>
								<td style="padding-top: 20px"><%=cuota.getAnio()%></td>
								<td style="padding-top: 20px"><%=cuota.getFechaPago()%></td>
								<td style="padding-top: 20px"><%=cuota.getValor()%></td>
								<td>
									<input type="hidden" name="dniCliente" value="<%=cuota.getDniCliente()%>"/>
									<input type="hidden" name="mesCuota" value="<%=cuota.getMes()%>"/>
									<input type="hidden" name="anioCuota" value="<%=cuota.getAnio()%>"/>
								</td>
							</tr>
					<%}%>
					<tr>
						<td colspan="8" style="padding-top: 20px; text-align: right;"><b>Total cobrado: $<%=total%></b></td>
					</tr>
	   		</tbody>
	   	</table>
	   </div>
	   <%}}%>
	</div>
</body>
</html>