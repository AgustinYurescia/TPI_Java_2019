<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Categoria"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="menu.jsp"/>
	<style>
		.page-link:hover{
			background-color: #86545a !important;
		}
	</style>
</head>
<body>
	
	<div class="container" style="padding-bottom:15%">
		<% HttpSession sesion = request.getSession(true);%>
		<%if(sesion.getAttribute("es_socio") != null){%>
			<br/>
			<div align="center" class="alert alert-info"><b>Recuerde que como beneficio de sociedad activa se le aplicara el descuento del <%=sesion.getAttribute("dcto_socio")%> % al finalizar la compra</b></div>
		<%} %>
		<div class="productos" style="text-align:center; width: 640px !important; margin:auto !important">
			<h1>Listado de productos</h1>
			<% 	
  				if(request.getAttribute("mensajeError") != null){
  			%>
  				<div class="alert alert-danger" role="alert"><%=request.getAttribute("mensajeError")%></div>
			<%
				}
  				if(request.getAttribute("mensajeOk") != null){
  			%>
  				<div class="alert alert-primary" role="alert"><%=request.getAttribute("mensajeOk")%></div>
			<%}%>
			<div id="productListContainer"></div>
		</div>
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
		class PaginatorComponent extends React.Component{
			constructor(props){
				super(props);
				this.page = this.props.page;
				this.prevPage = this.props.page - 1;
				this.nextPage = this.props.page + 1;
				this.state = {
					AmountOfPages: Math.ceil(this.props.currentAmountFetched / this.props.pageSize),
				};
			}
			// filter = memoize(
			// 	(list, filterText) => list.filter(item => item.text.includes(filterText))
			// );
			componentDidUpdate(prevProps) {
				if (this.props.page !== prevProps.page || this.props.currentAmountFetched != prevProps.currentAmountFetched || this.props.pageSize != prevProps.pageSize) {
					this.page = this.props.page;
					this.prevPage = parseInt(this.props.page) - 1;
					this.nextPage = parseInt(this.props.page) + 1;
					this.setState({AmountOfPages : Math.ceil(this.props.currentAmountFetched / this.props.pageSize),})
				}
			}
			onPageClick = (pageNumber) =>{
				this.props.handler(pageNumber);
			}
			render(){
				return(
					<nav style={{float: "right"}}>
						<ul className="pagination">
							<li onClick={(e) => {this.onPageClick(e.target.dataset.pagereference)}} className="page-link"  data-pagereference="1" style={{backgroundColor: "#58272d", border: "2px solid #f9eac7", color: "white", borderRadius: ".5em 0em 0em .5em"}}>Primera</li>
							{ this.page > 1 &&
								<li onClick={(e) => {this.onPageClick(e.target.dataset.pagereference)}} className="page-link"  data-pagereference={this.prevPage} style={{backgroundColor: "#58272d",border: "2px solid #f9eac7", color: "white"}}>{this.prevPage}</li>
							}
							<li className="page-item"className="page-link" data-pagereference={this.page} style={{backgroundColor: "green"}} style={{backgroundColor: "white",border: "2px solid #86545a", color: "#58272d"}}>{this.page}</li>
							{this.page < this.state.AmountOfPages && 
								<li onClick={(e) => {this.onPageClick(e.target.dataset.pagereference)}} className="page-link" data-pagereference={this.nextPage} style={{backgroundColor: "#58272d",border: "2px solid #f9eac7", color: "white"}}>{this.nextPage}</li>
							}
							<li onClick={(e) => {this.onPageClick(e.target.dataset.pagereference)}} className="page-link" data-pagereference={this.state.AmountOfPages} style={{backgroundColor: "#58272d",border: "2px solid #f9eac7", color: "white", borderRadius: "0em .5em .5em 0em"}}>Ultima({this.state.AmountOfPages})</li>
						</ul>
					</nav>
				)
			}
		}
		class PageSizeSelectorComponent extends React.Component{
			constructor(props){
				super(props);
				this.totalRegisters = this.props.registers;
				this.options = this.props.options;
			}
			OnSizeClick = () => {
				let selectedValue = document.getElementById("AmountOfRegitstersSelector").value;
				this.props.handler(selectedValue);
			}
			render()
			{
				return(
					<select className="form-select form-control col-12"  id="AmountOfRegitstersSelector" onChange={this.OnSizeClick} style={{backgroundColor:"#58272d !important", border:"3px"}} value={this.props.amountPerPage} >
						{this.options.map((value, index) => {
							return <option value={value} key={index}>{value} </option>
						})}
					</select>
				)
			}
		}
		class FilterComponent extends React.Component {
			constructor(props){
				super(props);
				this.state = {
					categories : [],
				};
			}
			fetchCategories = async () =>{
				const resp = await axios.get("ControladorApiCategoria");
				this.setState(this.state.categories = resp.data);
			}
			componentDidMount(){
				this.fetchCategories();
			}
			hadleFilterClick = () => {
				var selectedValue = document.getElementById("selectFiltro").value;
				var searchText =  document.getElementById("search_text").value;
				console.log(searchText);
				this.props.listHandler(selectedValue,searchText); 
			}
			
			render(){
				return(
					<>
					<h4 style={{textalign: "center"}}>Seleccione una categoria para filtrar los productos:</h4>
					<div className="form-row">
						<div className="col col-md-5">
							<select id="selectFiltro" name="selectFiltro" className="form-control"  >
								<option value = "0" defaultValue>Todos</option>
								{this.state.categories.map((value, index) => {
									return <option value={value.codigo} key = {index}> {value.descripcion}</option>
								})}
							</select>														
						</div>
						<div className="col col-md-6">
								<input type="text" id="search_text" name="search_text" className="form-control" placeholder="Nombre de producto"/>												
						</div>
						<div className="col col-md-1" style={{padding: "0px"}}>
							<button onClick={this.hadleFilterClick}  style={{float: "right"}} type="submit" className="btn btn-primary" name="accion" value="listar">Listar</button>	
						</div>
					</div>
					</>
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
					this.setState({ product: this.props.product });
				}
			}
			render(){
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
							<p>&#36;{this.props.product.precioVenta.toFixed(2)}</p>
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
				return(
					<div className="productos row" style={{width: "640px !important", margin:"auto"}}>
					{
						this.state.products != null && this.state.products != undefined ?
							this.state.products.map((value, index) => {
								return <ProductCardComponent product = {value} key = {index}/>
							}) : "" 
					}
					</div>
				)
			}
		}

		class PaginationComponent extends React.Component {
			pageSizeOptions = [6,9,21,30,60];
			constructor(props){
				super(props);
				this.state = {
					products: [],
					currentPage: 1,
					currentCategory: 0,
					currentAmountPerPage: 9,
					currentAmountFetched:0,
					currentSearchText:"",
				}
			}
			fetchPage = async () =>{
				let endpoint = "ControladorApiProducto?accion=ListarPorPaginas&numero_por_pagina="+this.state.currentAmountPerPage+"&numero_pagina="+this.state.currentPage+"&codigo_categoria="+this.state.currentCategory+"&texto_busqueda="+this.state.currentSearchText;
				const resp = await axios.get(endpoint);
				this.setState({currentAmountFetched: resp.data.CantidadRegistros, products: resp.data.Productos});

			}
			ListHandlerForFilter(categoryId, searchText){
				this.setState({currentPage: 1, currentCategory: categoryId, currentSearchText: searchText}, () => {this.fetchPage()});
			}
			ListHandlerForPageSizeSelector(pageSize){
				this.setState({currentPage: 1, currentAmountPerPage: pageSize}, () => {this.fetchPage()});
			}
			ListHandlerForPageSelector(page){
				this.setState({currentPage: page}, () => {this.fetchPage()});
			}
			async componentDidMount() {
				await this.fetchPage();
			}
			// 

			// 
			render(){
				return(
					<>
					<FilterComponent listHandler = {this.ListHandlerForFilter.bind(this)}/>
					<ProductsListComponent products={this.state.products} />
					<div className="row" style={{marginLeft: "10px", marginRight: "10px",}}>
						<div className="row col-6" style={{marginTop: "10px"}}>
							<div className="col-8">
								Registros Por Pagina
							</div>
							<div className="col-4">
								<PageSizeSelectorComponent registers={this.state.currentAmountFetched} handler = {this.ListHandlerForPageSizeSelector.bind(this)} amountPerPage = {this.state.currentAmountPerPage}  options = {this.pageSizeOptions}/>
							</div>
						</div>
						<div className="row col-6" style={{marginTop: "10px"}}>
							<PaginatorComponent key={this.state.currentAmountFetched} page ={this.state.currentPage} currentAmountFetched={this.state.currentAmountFetched} pageSize={this.state.currentAmountPerPage} handler={this.ListHandlerForPageSelector.bind(this)}/>
						</div>
					</div>
					</>
				);
			}
		}

		let domContainer = document.getElementById("productListContainer");
		ReactDOM.render(<PaginationComponent />, domContainer);
	</script>
</body>


</html>