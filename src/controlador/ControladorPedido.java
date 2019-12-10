package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.util.ArrayList;
import java.util.Iterator;

import modelo.LineaPedido;
import modelo.Producto;
import modeloDAO.ProductoDAO;

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
						l.setCantidad(l.getCantidad() + cantidad);
						ProductoDAO prodDAO = new ProductoDAO();                                     //CAMBIOS
						Producto prod = prodDAO.buscar_producto(l.getCodigo_producto());
						l.setSubtotal(prod.getPrecioVenta() * l.getCantidad());
						ya_existe = true;
						break;
					} 
				}
			}
			if (ya_existe == false) {
				ProductoDAO prodDAO = new ProductoDAO();
				Producto prod = prodDAO.buscar_producto(codigo_producto);
				subtotal = cantidad * prod.getPrecioVenta();     																		//CAMBIOS
				linea.add(new LineaPedido(codigo_producto,cantidad,subtotal));
			}
			sesion.setAttribute("carrito", linea);
			acceso = "carrito.jsp";
		}else if(action.equalsIgnoreCase("eliminarDelCarrito")) {
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
		}
		response.sendRedirect(acceso);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		doGet(request, response);
	}

}
