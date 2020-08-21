package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import modelo.Cliente;
import modelo.LineaPedido;
import modelo.Producto;
import modeloDAO.ProductoDAO;
import modelo.Pedido;
import modeloDAO.ClienteDAO;
import modeloDAO.PedidoDAO;

@WebServlet("/ControladorPedido")
public class ControladorPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ControladorPedido() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("accion");
		String acceso = "";
		if(action.equalsIgnoreCase("agregarAlCarrito")) {
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
						ProductoDAO prodDAO = new ProductoDAO();                                     //CAMBIOS
						Producto prod = prodDAO.buscar_producto(l.getCodigo_producto());
						l.setCantidad(l.getCantidad() + cantidad);
						l.setSubtotal(prod.getPrecioVenta() * l.getCantidad());
						ya_existe = true;
						acceso = "ControladorProducto?accion=listar&codigo_filtro=0";
						break;
						}
					} 
				}
			if (ya_existe == false) {
				ProductoDAO prodDAO = new ProductoDAO();
				Producto prod = prodDAO.buscar_producto(codigo_producto);	
				subtotal = cantidad * prod.getPrecioVenta();     																		//CAMBIOS
				linea.add(new LineaPedido(codigo_producto,cantidad,subtotal));
				acceso = "ControladorProducto?accion=listar&codigo_filtro=0";				
			}
			sesion.setAttribute("carrito", linea);
		}else if(action.equalsIgnoreCase("eliminarDelCarrito")) {
			ProductoDAO prodDAO = new ProductoDAO();
			HttpSession sesion = request.getSession(true);
			ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito");
			int codigo = Integer.parseInt(request.getParameter("codigo_prod"));
			System.out.println(codigo);
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
			boolean hayStock = true;
			ClienteDAO cliDAO = new ClienteDAO();
			ProductoDAO prodDAO = new ProductoDAO();
			Cliente cli = new Cliente();
			Pedido ped = new Pedido();
			Correo correo = new Correo();
			Producto prod = new Producto();
			PedidoDAO pedDAO = new PedidoDAO();
			HttpSession sesion = request.getSession(true);
			String usuario_cliente = (String)sesion.getAttribute("usuario_cliente");
			if(usuario_cliente != null) {
				cli = cliDAO.buscar_cliente(usuario_cliente);
				double total = (double)sesion.getAttribute("total");
				ped.setDni_cliente(cli.getDni());
				ped.setMonto(total);
				ArrayList<LineaPedido> linea = (ArrayList<LineaPedido>)sesion.getAttribute("carrito"); 
				for (LineaPedido l: linea) {
					prod = prodDAO.buscar_producto(l.getCodigo_producto());
					if(prod.getStock() - l.getCantidad() < 0) {
						hayStock = false;
						break;
					}
					else { }
				}
				if(hayStock) {
					int nro_pedido = pedDAO.alta(ped, linea);
					sesion.setAttribute("nro_pedido", nro_pedido);
					correo.enviar_mail_confirmacion(cli.getMail(), nro_pedido);
					acceso = "finalizacionDePedido.jsp";
				}else {
					request.setAttribute("errorStock", "No poseemos stock de uno o mas de los productos seleccionados. Puede ser que haya "
							+ "habido cuando usted agregó los productos al carrito y que alguien haya finalizado la compra más rápido que "
							+ "usted. O tambíen que haya cargado un stock mayor al mostrado como disponible en la pagina de selección del"
							+ "producto.");
					sesion.setAttribute("carrito", null);
					acceso = "carrito.jsp";
				}				
			}
			else {
				acceso = "loginClientes.jsp";
			}			
		}else if (action.equalsIgnoreCase("listadoPedidos")) {
			String fechaDesde = request.getParameter("fechaDesde");
			String fechaHasta = request.getParameter("fechaHasta");
			System.out.println(fechaDesde);
			System.out.println(fechaHasta);
		    PedidoDAO pedDAO = new PedidoDAO();
		    ArrayList<Pedido> pedidos = new  ArrayList<Pedido>();
			try {
				if (fechaDesde == "" && fechaHasta == "") {
					pedidos = pedDAO.listar();
					request.setAttribute("listadoPedidos", pedidos);
				}
				else if((fechaDesde != "" | fechaHasta != "") && (fechaDesde != null && fechaHasta != null)) {
					pedidos = pedDAO.listar( fechaDesde, fechaHasta);
					request.setAttribute("listadoPedidos", pedidos);
				}
				else if(fechaDesde == null && fechaHasta == null) {
					pedidos = pedDAO.listar();
					request.setAttribute("listadoPedidos", pedidos);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}  
		    acceso = "listarPedidos.jsp";			
		}else if(action.equalsIgnoreCase("mostrar_pedido")) {
			String nro_pedido;
			ArrayList<LineaPedido> lineas = new ArrayList<LineaPedido>();
			PedidoDAO pdao = new PedidoDAO();
			Pedido ped;
			nro_pedido = request.getParameter("nro_pedido");
			ped = pdao.buscar_pedido(Integer.parseInt(nro_pedido));
			lineas = pdao.buscar_productos_pedido(Integer.parseInt(nro_pedido));
			request.setAttribute("pedido", ped);
			request.setAttribute("productos_pedido", lineas);
			acceso = "mostrarPedido.jsp";
		}else if(action.equalsIgnoreCase("mostrar_pedido_cliente")) {
			String nro_pedido;
			ArrayList<LineaPedido> lineas = new ArrayList<LineaPedido>();
			PedidoDAO pdao = new PedidoDAO();
			Pedido ped;
			nro_pedido = request.getParameter("nro_pedido");
			ped = pdao.buscar_pedido(Integer.parseInt(nro_pedido));
			lineas = pdao.buscar_productos_pedido(Integer.parseInt(nro_pedido));
			request.setAttribute("pedido", ped);
			request.setAttribute("productos_pedido", lineas);
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
					prodDAO.actualizar_stock(l.getCodigo_producto(),l.getCantidad());
				}
				pedDAO.cancelar_pedido(nro_pedido);
				cli = cliDAO.buscar_cliente(usuario_cliente);
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
				pedDAO.set_fecha_entrega_real(numeroPedido);
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
