<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Vinoteca Gatti</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
	integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
	crossorigin="anonymous"></script>
</head>
<body>
	<footer class="footer" style="background-color: #F8F9FA">
		<hr />
		<div class="container text-center">
			<div class="row text-center text-md-left mt-3 pb-3">
				<div class="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
					<h6 align="center" class="text-uppercase mb-4 font-weight-bold"
						style="line-height: 3px">EL VIEJO TONEL &copy; 2020</h6>
					<p style="text-align: center;">Empresa familiar dedicada a la comercialización de bebidas alcoholicas</p>
				</div>
				<hr class="w-100 clearfix d-md-none">
				<div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
					<h6 align="center" class="text-uppercase mb-4 font-weight-bold"
						style="line-height: 3px">Información</h6>
					<p align="center">
						<jsp:include page="SVG/direccion.svg"/>
						Roque S. Peña 335, Pujato, Santa Fe, Argentina
					</p>
					<p align="center">
						<jsp:include page="SVG/mail.svg"/>
						vinotecagatti@gmail.com
					</p>
					<p align="center">
						<jsp:include page="SVG/telefono.svg"/>
						(+549) 3464 44-1296
					</p>
				</div>
			</div>
		</div>
		<hr>
		</hr>
	</footer>
</body>
</html>