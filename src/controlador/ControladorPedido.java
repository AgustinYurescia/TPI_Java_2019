package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Validators.ValidatorPedido;
import exceptions.AppException;
import exceptions.NonExistentOrderException;
import exceptions.NonExistentUserException;
import exceptions.NotEnoughStockException;
import exceptions.ValidatorsException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import modelo.Cliente;
import modelo.LineaPedido;
import modelo.Producto;
import services.CustomerService;
import services.ServicioCategoria;
import services.ServicioPedido;
import services.ServicioPlazosPrecios;
import services.ServicioProducto;
import modelo.Pedido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;


@WebServlet("/ControladorPedido")
public class ControladorPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioProducto _servicioProducto; 
	private CustomerService _servicioCliente;
	private ServicioPedido _servicioPedido; 
	private ValidatorPedido _validatorPedido;
	private ServicioPlazosPrecios _servicioPlazosPrecios;
	private static Logger _logger = LogManager.getLogger(ControladorLogin.class);
	private Gson _gson;
	
    public ControladorPedido() {
        super();
        this._servicioProducto = new ServicioProducto();
        this._servicioCliente = new CustomerService();
        this._servicioPedido = new ServicioPedido();
        this._validatorPedido = new ValidatorPedido();
        this._servicioPlazosPrecios = new ServicioPlazosPrecios();
        _gson = new Gson();
    }
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("accion");
		String acceso = "";
		HttpSession sesion = request.getSession(true);
		if(action.equalsIgnoreCase("agregarAlCarrito")) {
			try 
			{
				request.getParameter("stock");
				_validatorPedido.validacion_agregar_al_carrito(request.getParameter("stock"), request.getParameter("cantidad"));
				ServicioCategoria _servicioCategoria = new ServicioCategoria();
				int codigo_producto = Integer.parseInt(request.getParameter("codigo_producto"));
				int cantidad = Integer.parseInt(request.getParameter("cantidad"));
				double subtotal = 0.0 ;
				ArrayList<LineaPedido> linea;
				if (sesion.getAttribute("carrito") == null) {
					linea = new ArrayList<LineaPedido>();
				}else {
					linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");	
				}
				boolean ya_existe = false;
				if (linea.size() > 0) {
					for (LineaPedido l : linea) {
						if (codigo_producto == l.getCodigo_producto()) {
							Producto prod = _servicioProducto.GetProducto(l.getCodigo_producto());
							l.setCantidad(l.getCantidad() + cantidad);
							l.setSubtotal(prod.getPrecioVenta() * l.getCantidad());
							ya_existe = true;
							request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
							request.setAttribute("mensajeOk", "Agregado al carrito con éxito");
							acceso = "listarProductos.jsp";
							break;
						}
					} 
				}
				if (ya_existe == false) {
					Producto prod = _servicioProducto.GetProducto(codigo_producto);	
					subtotal = cantidad * prod.getPrecioVenta();
					linea.add(new LineaPedido(codigo_producto,cantidad,subtotal));
					request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
					request.setAttribute("mensajeOk", "Agregado al carrito con éxito");
					acceso = "listarProductos.jsp";				
				}
				sesion.setAttribute("carrito", linea);
			}
			catch(AppException e)
			{
				try 
				{
					Producto prod = _servicioProducto.GetProducto(Integer.parseInt(request.getParameter("codigo_producto")));
					request.setAttribute("producto", prod);
				}
				catch(Exception ee)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				finally
				{
					request.setAttribute("mensajeError", e.getMessage());
					acceso = "producto.jsp";
				}
			}
			catch(Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
				acceso = "producto.jsp";
			}
			
		}else if(action.equalsIgnoreCase("eliminarDelCarrito")) {
			ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");
			int codigo = Integer.parseInt(request.getParameter("codigo_prod"));
			for (LineaPedido l: linea) {
				if(l.getCodigo_producto() == codigo) {
					linea.remove(l);
					break;
				}
			}
			sesion.setAttribute("carrito", linea);
			acceso = "carrito.jsp";
			
		}else if(action.equalsIgnoreCase("ConfirmarCarrito")) {
			ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); 
			Iterator<LineaPedido>iter = linea.iterator();
			LineaPedido lin;
			double total = 0.0;
			try {
				while(iter.hasNext()){
					lin=iter.next();
					total = total + lin.getSubtotal();
				}
				if(sesion.getAttribute("es_socio") != null){
					float porcentajeDescuento = _servicioPlazosPrecios.obtenerPrcentajeDescuentoSocio();
					sesion.setAttribute("total", total - total * porcentajeDescuento);
					request.setAttribute("total_sin_descuento", total);
				}else {
					sesion.setAttribute("total", total);
				}
				acceso =  "confirmarPedido.jsp";
			} catch (Exception e) {
				acceso =  "error.jsp";
				request.setAttribute("mensajeError", "Lo sentimos, ha ocurrido un error");
				_logger.error(e.getMessage());
			}
		
		}else if(action.equalsIgnoreCase("FinalizarPedido")) {
			Cliente cli = new Cliente();
			Pedido ped = new Pedido();
			Correo correo = new Correo();
			Producto prod = new Producto();
			String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
			if(usuario_cliente != null) {
				try
				{
					cli = _servicioCliente.ObtenerPorNombreDeUsuario(usuario_cliente);
					double total = (double)sesion.getAttribute("total");
					ped.setDni_cliente(cli.getDni());
					ped.setMonto(total);
					ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); 
					for (LineaPedido l: linea) {

						prod = _servicioProducto.GetProducto(l.getCodigo_producto());
						if(prod.getStock() - l.getCantidad() < 0) {
							throw new NotEnoughStockException();
						}
					}
					int nro_pedido = _servicioPedido.Alta(ped, linea);
					sesion.setAttribute("nro_pedido", nro_pedido);
					acceso = "finalizacionDePedido.jsp";
					try {
						correo.enviar_mail_confirmacion(cli.getMail(), nro_pedido);
					}catch(Exception Ex){
						request.setAttribute("mensajeErrorMail", "La compra se realizo exitosamente pero no se ha podido enviar el mail de confirmación");
					}
				}
				catch(NotEnoughStockException ex) {
					request.setAttribute("errorStock", "No poseemos stock de uno o mas de los productos seleccionados. Puede ser que haya "
							+ "sucedido una compra desde que cuando usted agregó los productos al carrito. "
							+ "\n O también que haya cargado un stock mayor al mostrado como disponible en la pagina de selección del"
							+ "producto.");
					sesion.setAttribute("carrito", null);
					acceso = "carrito.jsp";
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "¡Error interno del servidor!");
					acceso = "error.jsp";
				}				
			}
			else 
			{
				acceso = "loginClientes.jsp";
			}			
		}else if (action.equalsIgnoreCase("listadoPedidos")) {
  			String usuario_admin = (String)sesion.getAttribute("usuario_admin");
  			if(usuario_admin != null) {
				String fechaDesde = request.getParameter("fechaDesde");
				String fechaHasta = request.getParameter("fechaHasta");
				//TODO validate dates
				
			    ArrayList<Pedido> pedidos = new  ArrayList<Pedido>();
				try {
					if ((fechaDesde == "" && fechaHasta == "") || (fechaDesde == null && fechaHasta == null)) 
					{
						pedidos = _servicioPedido.Listar(request.getParameter("estado"));
						request.setAttribute("listadoPedidos", pedidos);
					}
					
					else if((fechaDesde != "" | fechaHasta != "") && (fechaDesde != null && fechaHasta != null)) 
					{
						//TODO validate dates
						pedidos = _servicioPedido.Listar( fechaDesde, fechaHasta, request.getParameter("estado"));
						request.setAttribute("listadoPedidos", pedidos);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("fechaDesde", fechaDesde);
				request.setAttribute("fechaHasta", fechaHasta);
				request.setAttribute("estado", request.getParameter("estado"));
			    acceso = "listarPedidos.jsp";
		    }else{
		    	acceso = "loginAdmin.jsp";
		    }
		    
		}
		else if(action.equalsIgnoreCase("mostrar_pedido")) {
  			String usuario_admin = (String)sesion.getAttribute("usuario_admin");
  			if(usuario_admin != null) 
  			{
				String nro_pedido = request.getParameter("nro_pedido");
				try 
				{
					Pedido ped = _servicioPedido.BuscarPedidoConProductos(Integer.parseInt(nro_pedido));
					request.setAttribute("pedido", ped);
					acceso = "mostrarPedido.jsp";
				}
				catch(AppException ex)
				{
					request.setAttribute("mensajeError", ex.getMessage());
					acceso = "confirmarEntrega.jsp";
				}
				catch(Exception ex)
				{
					request.setAttribute("mensajeError", "Lo sentimos, ha ocurrido un error");
					acceso = "confirmarEntrega.jsp";
				}
				
  			}
  			else
  			{
  				acceso = "loginAdmin.jsp";
  			}
		}else if(action.equalsIgnoreCase("mostrar_pedido_cliente")) {
  			String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
			
  			if(usuario_cliente != null) {
  				try {
					Pedido pedido = _servicioPedido.BuscarPedidoConProductos(Integer.parseInt(request.getParameter("nro_pedido")));
					Cliente cli = _servicioCliente.ObtenerPorNombreDeUsuario(usuario_cliente);
					if(!pedido.getDni_cliente().equals(cli.getDni())) {
						throw new NonExistentUserException("No existe pedido solicitado para el cliente");
					}
					request.setAttribute("pedido", pedido);
					acceso = "mostrarPedidoCliente.jsp";
				}catch(AppException ex){
					request.setAttribute("error", ex.getMessage());
					acceso = "error.jsp";
				}catch(Exception ex){
					request.setAttribute("mensajeError", "Lo sentimos, ha ocurrido un error");
					acceso = "error.jsp";
				}
			}else {
				acceso = "loginClientes.jsp";
			}
  			
			
		}else if(action.equalsIgnoreCase("cancelar_Pedido")) {
			try {
				Correo correo = new Correo(); 
				String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
				int nro_pedido = Integer.parseInt(request.getParameter("nro_pedido"));
				
				if(usuario_cliente != null) {
					Pedido pedido = _servicioPedido.BuscarPedidoConProductos(nro_pedido);
					Cliente cli = _servicioCliente.ObtenerPorNombreDeUsuario(usuario_cliente);
					if(!pedido.getDni_cliente().equals(cli.getDni())) {
						throw new NonExistentUserException("No existe pedido solicitado para el cliente");
					}
					ArrayList<LineaPedido> lineas = pedido.getProductos(); 
					for(LineaPedido l : lineas) {
						_servicioProducto.ActualizarStock(l.getCodigo_producto(),l.getCantidad());
					}
					_servicioPedido.CancelarPedido(nro_pedido);
					try {
						correo.enviar_mail_cancelacion(cli.getMail());
					}catch(Exception Ex){
						request.setAttribute("mensajeErrorMail", "El pedido se canceló exitosamente pero no se le ha podido enviar el mail de confirmación a su correo");
						acceso = "confirmacionCancelacion.jsp";
					}
					
					acceso = "confirmacionCancelacion.jsp";
				}
				else {
					acceso = "loginClientes.jsp";
				}
			}catch(NonExistentOrderException ex) {
				request.setAttribute("mensajeError", ex.getMessage());
				acceso = "error.jsp";
			}catch(Exception ex) {
				request.setAttribute("mensajeError", "Lo sentimos, ha ocurrido un error");
				acceso = "error.jsp";
			}
		}else if (action.equalsIgnoreCase("listadoPedidosCliente")) {
  			String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
			String estado = request.getParameter("estado");
			if(usuario_cliente != null) {
				try{
					Cliente cli = _servicioCliente.ObtenerPorNombreDeUsuario(usuario_cliente);
					ArrayList<Pedido> pedidos = _servicioPedido.ListarPedidosCliente(cli, estado);
					request.setAttribute("listadoPedidosCliente", pedidos);						
				}catch (Exception e) {
					request.setAttribute("mensajeError", "Error interno del servidor");
				}  
			    acceso = "listarPedidosCliente.jsp";
			}else{
				acceso = "loginClientes.jsp";
			}
		}else if (action.equalsIgnoreCase("entregaPedido")){
			if(sesion.getAttribute("usuario_admin")!= null) {
				try
				{
					_validatorPedido.validacion_entrega(request.getParameter("numero_pedido"));
					int numeroPedido = Integer.parseInt(request.getParameter("numero_pedido"));
					_servicioPedido.RegistrarEntrega(numeroPedido);
					request.setAttribute("mensajeOk", "Entrega registrada con éxito");
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso = "ControladorPedido?accion=listadoPedidos";
			}else {
				acceso = "loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("graficoTotalVentas")) 
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				Date date = new Date();
		        ZoneId timeZone = ZoneId.systemDefault();
		        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
		        Integer anio = getLocalDate.getYear();
				try 
				{
					ArrayList<String> imagesAsBase64 = _servicioPedido.obtenerTotalVentasPorMes(anio);
					request.setAttribute("imageAsBase64_0", imagesAsBase64.get(0));
					request.setAttribute("imageAsBase64_1", imagesAsBase64.get(1));
				} 
				catch (Exception e) 
				{
					request.setAttribute("mensajeError","Error interno del servidor");
				}
				acceso="graficoTotalVentasPorMes.jsp";
			}
			else
			{
				acceso = "loginAdmin.jsp";	
			}
		}
		else if (action.equalsIgnoreCase("listarPedidosAEntregarManana"))
		{
			ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
			if(sesion.getAttribute("usuario_admin")!= null) {
				try
				{
					pedidos = _servicioPedido.PedidosAEntregarManana();
					sesion.setAttribute("pedidos", pedidos);
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso = "listadoPedidosAEntregarManana.jsp";
			}else {
				acceso = "loginAdmin.jsp";
			}
		}
		else if (action.equalsIgnoreCase("prepararPedidos"))
		{
			Correo correo = new Correo();
			Cliente cli = null;
			ArrayList<Pedido> pedidos = (ArrayList<Pedido>)sesion.getAttribute("pedidos");
			if(sesion.getAttribute("usuario_admin") != null) 
			{
				try
				{
					_servicioPedido.SetEstadoPreparado(pedidos);
					for (Pedido p: pedidos)
					{
						if (p.getEstado().equalsIgnoreCase("pendiente"))
						{
							cli = _servicioCliente.Buscar(p.getDni_cliente());
							correo.enviar_mail_confirmacion_preparacion(cli.getMail(), p.getNro_pedido());
							p.setEstado("preparado");
						}
					}
					for (int i=0; i < pedidos.size(); i++)
					{
						pedidos.get(i).setEstado("preparado");
					}
					sesion.setAttribute("pedidos", pedidos);
					request.setAttribute("mensajeOk", "Preparación registrada con éxito");
				}
				catch(MessagingException e)
				{
					for (int i=0; i < pedidos.size(); i++)
					{
						pedidos.get(i).setEstado("preparado");
					}
					sesion.setAttribute("pedidos", pedidos);
					request.setAttribute("mensajeOk", "Error interno al enviar el email al cliente, pero el estado del pedido fue cambiado satisfactoriamente");
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso = "listadoPedidosAEntregarManana.jsp";
			}else 
			{
				acceso = "loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("graficoTotalVentasAnual")) 
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try 
				{
					ArrayList<String> imagesAsBase64 = _servicioPedido.obtenerTotalVentasPorAnio();
					request.setAttribute("imageAsBase64_0", imagesAsBase64.get(0));
					request.setAttribute("imageAsBase64_1", imagesAsBase64.get(1));
				} 
				catch (Exception e) 
				{
					request.setAttribute("mensajeError","Error interno del servidor");
				}
				acceso="graficoTotalVentasPorAnio.jsp";
			}
			else
			{
				acceso = "loginAdmin.jsp";
			}
		}
		else if (action.equalsIgnoreCase("prepararPedido"))
		{
			Correo correo = new Correo();
			Cliente cli = null;
			String nro_pedido = request.getParameter("numero_pedido");
			if(sesion.getAttribute("usuario_admin") != null) 
			{
				try
				{
					Pedido ped = _servicioPedido.BuscarPedido(Integer.parseInt(nro_pedido));
					if (ped.getEstado().equalsIgnoreCase("pendiente"))
					{
						_servicioPedido.SetEstadoPreparado(nro_pedido);
						cli = _servicioCliente.Buscar(ped.getDni_cliente());
						correo.enviar_mail_confirmacion_preparacion(cli.getMail(), Integer.parseInt(nro_pedido));
						request.setAttribute("mensajeOk", "Preparación registrada con éxito");
					}
				}
				catch(MessagingException e)
				{
					request.setAttribute("mensajeError", "Error interno al enviar el email al cliente, pero el estado del pedido fue cambiado satisfactoriamente");
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso = "ControladorPedido?accion=listadoPedidos";
			}else 
			{
				acceso = "loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("prepararPedidoAjax"))
		{
			Correo correo = new Correo();
			Cliente cli = null;
			String nro_pedido = request.getParameter("numero_pedido");
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try 
				{
					_servicioPedido.SetEstadoPreparado(nro_pedido);
					Pedido ped = _servicioPedido.BuscarPedido(Integer.parseInt(nro_pedido));
					cli = _servicioCliente.Buscar(ped.getDni_cliente());
					correo.enviar_mail_confirmacion_preparacion(cli.getMail(), Integer.parseInt(nro_pedido));
					String mensajeOk = "Preparación registrada con éxito";
					String res = _gson.toJson(mensajeOk);
					SendSuccessResponse(res ,response);
					return;
				}catch(ValidatorsException e) {
					request.setAttribute("mensajeError", e.getMessage());
				}catch(Exception ex) {
					request.setAttribute("mensajeError", "Ocurrio un error, por favor vuelva a intentarlo");
				}
				acceso = "listadoCuotasPagas.jsp";
			}else
			{
				acceso="loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("entregarPedidoAjax"))
		{
			int numeroPedido = Integer.parseInt(request.getParameter("numero_pedido"));
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try 
				{
					_servicioPedido.RegistrarEntrega(numeroPedido);
					String mensajeOk = "Entrega registrada con éxito";
					String res = _gson.toJson(mensajeOk);
					SendSuccessResponse(res ,response);
					return;
				}catch(ValidatorsException e) {
					request.setAttribute("mensajeError", e.getMessage());
				}catch(Exception ex) {
					request.setAttribute("mensajeError", "Ocurrio un error, por favor vuelva a intentarlo");
				}
				acceso = "listadoCuotasPagas.jsp";
			}else
			{
				acceso="loginAdmin.jsp";
			}
		}
		else if (action.equalsIgnoreCase("ventasDelDia"))
		{
			ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
			if(sesion.getAttribute("usuario_admin")!= null) {
				try
				{
					pedidos = _servicioPedido.VentasDelDia();
					sesion.setAttribute("ventasDelDia", pedidos);
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso = "ventasDelDia.jsp";
			}else {
				acceso = "loginAdmin.jsp";
			}
		}
		else if (action.equalsIgnoreCase("VentasEntreFechas")) {
  			String usuario_admin = (String)sesion.getAttribute("usuario_admin");
  			if(usuario_admin != null) {
				String fechaDesde = request.getParameter("fechaDesde");
				String fechaHasta = request.getParameter("fechaHasta");
			    ArrayList<Pedido> pedidos = new  ArrayList<Pedido>();
				try {
					if ((fechaDesde == "" && fechaHasta == "") || (fechaDesde == null && fechaHasta == null)) 
					{
						pedidos = _servicioPedido.ListarFinalizados();
						request.setAttribute("listadoVentas", pedidos);
					}
					
					else if((fechaDesde != "" | fechaHasta != "") && (fechaDesde != null && fechaHasta != null)) 
					{
						pedidos = _servicioPedido.ListarFinalizados(fechaDesde, fechaHasta);
						request.setAttribute("listadoVentas", pedidos);
					}
					
				} catch (Exception e) 
				{
					request.setAttribute("mensajeError", "Error al obtener las ventas, vuelva a intentarlo");
				}
				request.setAttribute("fechaDesde", fechaDesde);
				request.setAttribute("fechaHasta", fechaHasta);
			    acceso = "ventasEntreFechas.jsp";
		    }else{
		    	acceso = "loginAdmin.jsp";
		    }
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		doGet(request, response);
	}
	
	private static void SendSuccessResponse(String mensaje,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		out.print(mensaje);
		out.flush();
	}

}
