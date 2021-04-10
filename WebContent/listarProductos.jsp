<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	   		<div class="productos" style="text-align:center; width: 1060px !important;">
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
		<div id="productListContainer"></div>
	</div>
	<jsp:include page="footer.jsp"/>
	<!-- <script src="https://unpkg.com/react@17/umd/react.production.min.js" crossorigin></script>
	<script src="https://unpkg.com/react-dom@17/umd/react-dom.production.min.js" crossorigin></script> -->
	<script crossorigin src="https://unpkg.com/react@17/umd/react.development.js"></script>
	<script crossorigin src="https://unpkg.com/react-dom@17/umd/react-dom.development.js"></script>
  	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
	<script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
	<!-- <script type="text/babel" src="Pagination.js"></script> -->
	<script type="text/babel">
		class ProductCardComponent extends React.Component {
			constructor(props){
				super(props);
			}
			render(){
				console.log("ProductCardComponent")
				return(
					<div class="productos" style="text-align:center; width: 1060px !important;">	
						<div class="producto" style="float:left !important; margin-top:10px; height:320px !important">
							<div class="imagen-producto" style="margin-left:auto; margin-right:auto;">
								<img src="https://http2.mlstatic.com/D_NQ_NP_645266-MLA43711471547_102020-O.jpg" width="160px" height="160px"/>
							</div>
							<div class="nombre-producto" style={{fontSize: 15px; margin:auto; height:45px !important}}>
								<p>nombre del producto</p>
							</div>
							<div class="precio-producto" style="margin:auto;">
								<p>precio de venta del producto</p>
							</div>
							<div class="ver-producto">
								<a class="" href="">
									<button type="submit" class="btn btn-primary" style="margin-top:10px; border-radius:15px; width:160px;">Comprar</button>
								</a>
							</div>
						</div>
					</div>
					
				)
			}
		}

		class ProductsListComponent extends React.Component {
			constructor(props){
				super(props);
			}
			render(){
				console.log(this.props.products);
				return(
					<>
					{this.props.products.map((value, index) => {
						return <ProductCardComponent product = {value} key = {index}/>
					})}
					</>
				)
			}
		}

		class PaginationComponent extends React.Component {
			constructor(props){
				super(props);
				this.state = {
					products: [],
					actualPage: 1,
				}
			}
			setprod = () => {
				let prods = [{codigo: 2, nombre: "Fernet Branca 750ml", stock: 100, precioVenta: 250, codigo_categoria: 5},
							{codigo: 3, nombre: "Fernet Branca 1L", stock: 98, precioVenta: 300, codigo_categoria: 5},
							{codigo: 4, nombre: "Gancia Clasico 1L", stock: 100, precioVenta: 200, codigo_categoria: 5},
							{codigo: 8, nombre: "Fernet 1882 1L", stock: 95, precioVenta: 260, codigo_categoria: 5},
							{codigo: 13, nombre: "Vinitoh", stock: 12, precioVenta: 156, codigo_categoria: 5}];
				this.setState(this.state.products = prods);
				
			}
			//fetchPage = async () =>{
			//	const resp = await axios.get("http://localhost:8080/TPI_Java/ControladorApiProducto?accion=ListarPorPaginas&numero_por_pagina=5&numero_pagina=1&codigo_categoria=5");
			//	this.setState(this.products = resp.data);
			//	console.log(this.products);
			//}
			componentDidMount() {
			 	//this.fetchPage();
				 this.setprod();
			}
			render(){
				//await this.fetchPage();
				console.log(this.state.products);
				console.log("log in father");
				return(
					<>
					<ProductsListComponent products={this.state.products} />
					</>
				);
			}
		}

		let domContainer = document.getElementById("productListContainer");
		ReactDOM.render(<PaginationComponent />, domContainer);
	</script>
</body>


</html>