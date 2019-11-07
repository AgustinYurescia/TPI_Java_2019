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

import modelo.LineaPedido;
import modelo.Producto;

@WebServlet("/ControladorPedido")
public class ControladorPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ControladorPedido() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		System.out.println(action);
		if(action.equalsIgnoreCase("agregarAlCarrito")) {
			int codigo_producto = Integer.parseInt(request.getParameter("codigo_producto"));
			int cantidad = Integer.parseInt(request.getParameter("cantidad"));
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
						ya_existe = true;
						break;
					} 
				}
			}
			if (ya_existe == false) {
				linea.add(new LineaPedido(codigo_producto,cantidad));
			}
			sesion.setAttribute("carrito", linea);
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
		}
		response.sendRedirect("carrito.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
