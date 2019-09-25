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

import modelo.LineaPedido;



@WebServlet("/ControladorDeLinks")
public class ControladorDeLinks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String loginCliente = "loginClientes.jsp";
	String indexCliente = "index.jsp";
	String loginAdmin = "loginAdmin.jsp";
	String indexAdmin = "indexAdmin.jsp";
	String actualizarStock = "actualizarStock.jsp";
	String listar = "listarProductos.jsp";
	String altaProducto = "altaProducto.jsp";
	String mostrar_producto = "producto.jsp";
	String carrito = "carrito.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("indexCliente")) {
			acceso = indexCliente;
		}else if(action.equalsIgnoreCase("inicioSesionCliente")) {
			acceso = loginCliente;
			response.sendRedirect(acceso);
		}else if(action.equalsIgnoreCase("inicioSesionAdmin")) {
			acceso = loginAdmin;
			response.sendRedirect(acceso);
		}else if(action.equalsIgnoreCase("indexAdmin")) {
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect("indexAdmin.jsp");  
			}
		}else if(action.equalsIgnoreCase("actualizarStock")) {
			acceso = "actualizarStock.jsp";
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect(acceso);
			}
		}else if(action.equalsIgnoreCase("altaProducto")) {
			acceso = altaProducto;
			HttpSession sesion = request.getSession(true);
			if (sesion.getAttribute("usuario_admin") == null) {
				response.sendRedirect("loginAdmin.jsp");   
			}else {
				response.sendRedirect(acceso);
			}
		}else if(action.equalsIgnoreCase("carrito")) {
			acceso = carrito;
			response.sendRedirect(acceso);
		}
		//RequestDispatcher vista = request.getRequestDispatcher(acceso);
		//vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}