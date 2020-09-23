package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.NotEnoughStockException;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.Cliente;
import modelo.LineaPedido;
import modelo.Producto;
import modeloDAO.ProductoDAO;
import services.CustomerService;
import services.ServicioCategoria;
import services.ServicioPedido;
import services.ServicioProducto;
import modelo.Pedido;
import modeloDAO.ClienteDAO;
import modeloDAO.PedidoDAO;
import services.ServicioProducto;;

@WebServlet("/ControladorPedido")
public class ControladorPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioProducto _servicioProducto; 
	private ServicioCategoria _servicioCategoria;
	private CustomerService _servicioCliente;
	private ServicioPedido _servicioPedido;   
	
    public ControladorPedido() {
        super();
        this._servicioProducto = new ServicioProducto();
        this._servicioCategoria = new ServicioCategoria();
        this._servicioCliente = new CustomerService();
        this._servicioPedido = new ServicioPedido();
    }
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("accion");
		String acceso = "";
		if(action.equalsIgnoreCase("agregarAlCarrito")) {
			ServicioCategoria _servicioCategoria = new ServicioCategoria();
			int codigo_producto = Integer.parseInt(request.getParameter("codigo_producto"));
			int cantidad = Integer.parseInt(request.getParameter("cantidad"));
			double subtotal = 0.0 ;                                              //CAMBIO
			ArrayList<LineaPedido> linea;
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("carrito") == null) {
				linea = new ArrayList<LineaPedido>();
			}else {
				linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");	
			}
			
			boolean ya_existe = false;
			if (linea.size() > 0) {
				for (LineaPedido l : linea) {
					if (codigo_producto == l.getCodigo_producto()) {
						try
						{
							Producto prod = _servicioProducto.GetProducto(l.getCodigo_producto());
							l.setCantidad(l.getCantidad() + cantidad);
							l.setSubtotal(prod.getPrecioVenta() * l.getCantidad());
							ya_existe = true;
							request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
							acceso = "listarProductos.jsp";
							break;
						}
						catch (Exception e)
						{
							 
						}
						
					}
				} 
			}
			if (ya_existe == false) {
				try
				{
					Producto prod = _servicioProducto.GetProducto(codigo_producto);	
					subtotal = cantidad * prod.getPrecioVenta();     																		//CAMBIOS
					linea.add(new LineaPedido(codigo_producto,cantidad,subtotal));
				}
				catch (Exception e)
				{
					//manage this error showing the user the problem
				}
				request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
				acceso = "listarProductos.jsp";				
			}
			sesion.setAttribute("carrito", linea);
			
		}else if(action.equalsIgnoreCase("eliminarDelCarrito")) {
			HttpSession sesion = request.getSession(true);
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
			HttpSession sesion = request.getSession(true);
			ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); 
			Iterator<LineaPedido>iter = linea.iterator();
			LineaPedido lin;
			double total = 0.0;
			while(iter.hasNext()){
				lin=iter.next();
				total = total + lin.getSubtotal();
			sesion.setAttribute("total", total);
			acceso =  "confirmarPedido.jsp";
			}
		
		}else if(action.equalsIgnoreCase("FinalizarPedido")) {
			Cliente cli = new Cliente();
			Pedido ped = new Pedido();
			Correo correo = new Correo();
			Producto prod = new Producto();
			
			HttpSession sesion = request.getSession(true);
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
					correo.enviar_mail_confirmacion(cli.getMail(), nro_pedido);
				}
				catch(NotEnoughStockException ex) {
					request.setAttribute("errorStock", "No poseemos stock de uno o mas de los productos seleccionados. Puede ser que haya "
							+ "sucedido una compra desde que cuando usted agregó los productos al carrito. "
							+ "\n O tambíen que haya cargado un stock mayor al mostrado como disponible en la pagina de selección del"
							+ "producto.");
					sesion.setAttribute("carrito", null);
					acceso = "carrito.jsp";
				}
				catch(RuntimeException e)
				{
					//Do nothing, the email catch throws this exception
					//TODO log exception
				}
				catch (Exception e)
				{
					//TODO log exception
				}				
			}
			else {
				acceso = "loginClientes.jsp";
			}			
		}else if (action.equalsIgnoreCase("listadoPedidos")) {
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
		    acceso = "listarPedidos.jsp";
		    
		}else if(action.equalsIgnoreCase("mostrar_pedido")) {
			
			String nro_pedido = request.getParameter("nro_pedido");
			Pedido ped = _servicioPedido.BuscarPedido(Integer.parseInt(nro_pedido));
			request.setAttribute("pedido", ped);
			acceso = "mostrarPedido.jsp";
		
		}else if(action.equalsIgnoreCase("mostrar_pedido_cliente")) {
			
			Pedido pedido = _servicioPedido.BuscarPedidoConProductos(Integer.parseInt(request.getParameter("nro_pedido")));
			request.setAttribute("pedido", pedido);
			
			acceso = "mostrarPedidoCliente.jsp";
		}else if(action.equalsIgnoreCase("cancelar_Pedido")) {
			ProductoDAO prodDAO = new ProductoDAO();
			ClienteDAO cliDAO = new ClienteDAO();
			Cliente cli = new Cliente();
			Correo correo = new Correo();
			PedidoDAO pedDAO = new PedidoDAO();
			HttpSession sesion = request.getSession(true);
			String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
			int nro_pedido = Integer.parseInt(request.getParameter("nro_pedido"));
			ArrayList<LineaPedido> lineas = pedDAO.buscar_productos_pedido(nro_pedido);
			if(usuario_cliente != null) {
				for(LineaPedido l : lineas) {
					prodDAO.actualizarStock(l.getCodigo_producto(),l.getCantidad());
				}
				pedDAO.cancelar_pedido(nro_pedido);
				try {
					cli = _servicioCliente.ObtenerPorNombreDeUsuario(usuario_cliente);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				correo.enviar_mail_cancelacion(cli.getMail());
				acceso = "confirmacionCancelacion.jsp";
			}
			else {
				acceso = "loginClientes.jsp";
			}			
		}else if (action.equalsIgnoreCase("listadoPedidosCliente")) {
			ClienteDAO cliDAO = new ClienteDAO();
			Cliente cli = new Cliente();
			PedidoDAO pedDAO = new PedidoDAO();
			HttpSession sesion = request.getSession(true);
			String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
			String estado = request.getParameter("estado");
		    ArrayList<Pedido> pedidos = new  ArrayList<Pedido>();
			try{
			    cli = cliDAO.buscar_cliente(usuario_cliente);
				pedidos = pedDAO.listar_pedidos_cliente(cli.getDni(), estado);
				request.setAttribute("listadoPedidosCliente", pedidos);						
			}catch (Exception e) {
				e.printStackTrace();
			}  
		    acceso = "listarPedidosCliente.jsp";
		}else if (action.equalsIgnoreCase("entregaPedido")){
			HttpSession sesion = request.getSession(true);
			if(sesion.getAttribute("usuario_admin")!= null) {
				int numeroPedido = Integer.parseInt(request.getParameter("numero_pedido"));
				PedidoDAO pedDAO = new PedidoDAO();
				try
				{
					pedDAO.RegistrarEntrega(numeroPedido);
					request.setAttribute("mensajeOk", "Entrega registrada con éxito");
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
			}
			acceso = "ControladorPedido?accion=listadoPedidos";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		doGet(request, response);
	}

}
