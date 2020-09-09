package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ControladorDeLinks")
public class ControladorDeLinks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("indexCliente")) {
			response.sendRedirect("index.jsp");
		}else if(action.equalsIgnoreCase("inicioSesionCliente")) {
			response.sendRedirect("loginClientes.jsp");
		}else if(action.equalsIgnoreCase("inicioSesionAdmin")) {
			response.sendRedirect("loginAdmin.jsp");
		}else if(action.equalsIgnoreCase("indexAdmin")) {
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect("indexAdmin.jsp");  
			}
		}else if(action.equalsIgnoreCase("actualizarStock")) {
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect("actualizarStock.jsp");
			}
		}else if(action.equalsIgnoreCase("editarProducto")) {
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect("editarProducto.jsp");
			}
		}else if(action.equalsIgnoreCase("altaProducto")) {
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect("ControladorProducto?accion=alta");
			}
		}else if(action.equalsIgnoreCase("carrito")) {
			response.sendRedirect("carrito.jsp");
		}else if(action.equalsIgnoreCase("registroCliente")) {
			response.sendRedirect("registroCliente.jsp");
		}
		else if (action.equalsIgnoreCase("modificar_cliente")) {
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_cliente") == null) {
				response.sendRedirect("loginClientes.jsp");
			}else {
				response.sendRedirect("editarCliente.jsp");
			}
		}else if (action.equalsIgnoreCase("bajaCliente")) {
			response.sendRedirect("bajaCliente.jsp");
		}else if (action.equalsIgnoreCase("listadoPedidosCliente")) {
			response.sendRedirect("listarPedidosCliente.jsp");
		}else if (action.equalsIgnoreCase("listarPedidosAdmin")) {
			response.sendRedirect("listarPedidos.jsp");
		}else if (action.equalsIgnoreCase("cambioContrasena")) {
			response.sendRedirect("cambiarContrasena.jsp");
		}else if (action.equalsIgnoreCase("cambioContrasenaAdmin")) {
			response.sendRedirect("cambiarContrasenaAdmin.jsp");
		}else if (action.equalsIgnoreCase("listarProductosAdmin")) {
			response.sendRedirect("ControladorProducto?accion=listarAdmin");
		}else if (action.equalsIgnoreCase("listarProductosCliente")) {
			response.sendRedirect("ControladorProducto?accion=listarCliente");
		}else if (action.equalsIgnoreCase("altaCategoria")) {
			response.sendRedirect("ControladorCategoria?accion=alta");
		}else if (action.equalsIgnoreCase("bajaCategoria")) {
			response.sendRedirect("ControladorCategoria?accion=baja");
		}
		
		//RequestDispatcher vista = request.getRequestDispatcher(acceso);
		//vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}