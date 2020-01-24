package controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
				response.sendRedirect("altaProducto.jsp");
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
		}
		
		//RequestDispatcher vista = request.getRequestDispatcher(acceso);
		//vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}