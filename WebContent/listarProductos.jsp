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
	    				<label for="codigo_filtro">Seleccione una categor�a para filtrar los productos:</label>
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
				<div class="productos" style="text-align:center; width: 640px !important;">
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
				<div id="productListContainer"></div>
				<jsp:include page="footer.jsp"/>
				<!-- <script src="https://unpkg.com/react@17/umd/react.production.min.js" crossorigin></script>
	<script src="https://unpkg.com/react-dom@17/umd/react-dom.production.min.js" crossorigin></script> -->
	<script crossorigin src="https://unpkg.com/react@17/umd/react.development.js"></script>
	<script crossorigin src="https://unpkg.com/react-dom@17/umd/react-dom.development.js"></script>
  	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
	<script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
	<!-- <script type="text/babel" src="Pagination.js"></script> -->
	<script type="text/babel">
		class FilterComponent extends React.Component {
			constructor(props){
				super(props);
				this.state = {
					listHandler: this.props.listHandler,
					categories : [],
				};
			}
			fetchCategories = async () =>{
				const resp = await axios.get("http://localhost:8080/TPI_Java/ControladorApiProducto?accion=ListarPorPaginas&numero_por_pagina=5&numero_pagina=1&codigo_categoria=5");
				this.setState(this.state.categories = resp.data);
				console.log(this.state.categories);
				console.log('fetchCategories');
			}
			componentDidMount(){
				this.fetchCategories();
			}
			render(){
				return(
					<div className="form-row">
						<div className="form-group col-md-11">
							<label htmlFor="codigo_filtro">Seleccione una categoría para filtrar los productos:</label>
							<select id="codigo_filtro" name="codigo_filtro" className="form-control"  >
								<option value = "0" defaultValue>Todos</option>
								{this.state.categories.map((value, index) => {
								return <option value={value.codi} key = {index}/>
							})}
							</select>														
						</div>
						<div className="padding-top: 31px">
							<button type="submit" className="btn btn-primary" name="accion" value="listar">Listar</button>	
						</div>
					</div>
				)
			}
		}
		class ProductCardComponent extends React.Component {
			constructor(props){
				super(props);
				this.state = {
					product: props.product
				};
			}
			
			componentDidUpdate(prevProps) {
				if (this.props.product !== prevProps.product) {
					console.log("the products are");
					this.setState({ product: this.props.product });
					console.log(this.state.product);
				}
			}
			render(){
				console.log("ProductCardComponent")
				const dataImage =  "data:image/jpeg;base64," + this.props.product.imagenString;
				const routeToProduct = "ControladorProducto?accion=mostrar_producto&codigo_producto=" + this.props.product.codigo;
				return(
						
					<div className="producto" style={{float:"left !important", marginTop:"10px", height:"320px !important"}}>
						<div className="imagen-producto" style={{marginLeft:"auto", marginRight:"auto"}}>
							<img src={dataImage} width="160px" height="160px"/>
						</div>
						<div className="nombre-producto" style={{fontSize: "15px", margin:"auto", height:"45px !important"}}>
							<p>{this.props.product.nombre}</p>
						</div>
						<div className="precio-producto" style={{margin:"auto"}}>
							<p>{this.props.product.precioVenta}</p>
						</div>
						<div className="ver-producto">
							<a  href={routeToProduct}>
								<button type="submit" className="btn btn-primary" style={{marginTop:"10px", borderRadius:"315px", width:"160px"}}>Comprar</button>
							</a>
						</div>
					</div>
				)
			}
		}

		class ProductsListComponent extends React.Component {
			constructor(props){
				super(props);
				this.state = {
					products: props.products
				};
			}

			componentDidUpdate(prevProps) {
				if (this.props.products !== prevProps.products) {
					this.setState({ products: this.props.products });
				}
			}
			render(){
				console.log(this.props.products);
				return(
					<div className="productos" style={{width: "640px !important", margin:"auto !important"}}>
						{this.state.products.map((value, index) => {
							return <ProductCardComponent product = {value} key = {index}/>
						})}
					</div>
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
			fetchPage = async () =>{
				const resp = await axios.get("http://localhost:8080/TPI_Java/ControladorApiProducto?accion=ListarPorPaginas&numero_por_pagina=5&numero_pagina=1&codigo_categoria=5");
				this.setState(this.state.products = resp.data);
			}
			ListHandler(newState){
				this.setState( this.state.products = newState );
			}
			async componentDidMount() {
				await this.fetchPage();
				console.log(this.state.products);
			}
			render(){
				console.log("log in father");
				return(
					<>
					<FilterComponent listHandler = {this.ListHandler}/>
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