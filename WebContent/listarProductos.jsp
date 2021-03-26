<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
</head>
<body>
	<div class="container" style=" padding-bottom:15%">
		<div class="productos" style="text-align:center; width: 640px !important; margin:auto !important">
			<h1>Listado de productos</h1>
		   	<form action="ControladorProducto" method="POST">
	  			<div class="form-row">
	    			<div class="form-group col-md-11">
	    				<label for="codigo_filtro">Seleccione una categoría para filtrar los productos:</label>
	      				<select id="codigo_filtro" name="codigo_filtro" class="form-control"  >
	        				<option value = "0" selected>Todos</option>
	        				<% 
							for (Categoria c: (ArrayList<Categoria>) request.getAttribute("categorias"))
							{
							%>
	        				<option value="<%=c.getCodigo()%>"><%=c.getDescripcion()%></option>
	        				<%}%>
	     				</select>														
	    			</div>
	    			<div class="form-group col-md-1" style="padding-top: 31px">
	    				<button type="submit" class="btn btn-primary" name="accion" value="listar">Listar</button>	
	    			</div>
	    		</div>
			</form>
		   	<div>
	   	</div>
	   	<%if (request.getAttribute("listado") != null){%>
	   		<div class="productos" style="text-align:center; width: 640px !important; margin:auto !important">
	   			<%
	   			ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("listado");
	   			for (Producto p: lista){%>
      			<div class="producto" style="float:left !important; margin-top:10px; height:320px !important">
      				<div class="imagen-producto" style="margin-left:auto; margin-right:auto;">
      					<img src="ControladorDeImagenes?codigo=<%=p.getCodigo()%>" width="160px" height="160px"/>
      				</div>
      				<div class="nombre-producto" style="font-size: 15px; margin:auto; height:45px !important">
      					<p><%=p.getNombre()%></p>
      				</div>
      				<div class="precio-producto" style="margin:auto;">
      					<p>$<%=p.getPrecioVenta()%></p>
      				</div>
      				<div class="ver-producto">
      					<a class="" href="ControladorProducto?accion=mostrar_producto&codigo_producto=<%=p.getCodigo()%>">
      						<button type="submit" class="btn btn-primary" style="margin-top:10px; border-radius:15px; width:160px;">Comprar</button>
      					</a>
      				</div>
      			</div>
      			<%}}%>	
	   		</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>