package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("indexCliente")) {
			acceso = indexCliente;
		}else if(action.equalsIgnoreCase("inicioSesionCliente")) {
			acceso = loginCliente;
		}else if(action.equalsIgnoreCase("inicioSesionAdmin")) {
			acceso = loginAdmin;
		}else if(action.equalsIgnoreCase("indexAdmin")) {
			acceso = indexAdmin;
		}else if(action.equalsIgnoreCase("actualizarStock")) {
			acceso = "actualizarStock.jsp";
		}else if(action.equalsIgnoreCase("altaProducto")) {
			acceso = altaProducto;
		}else if(action.equalsIgnoreCase("-------")) {
			acceso = indexCliente;
		}else if(action.equalsIgnoreCase("--------")) {
			acceso = actualizarStock;
		}else if(action.equalsIgnoreCase("----")) {
			acceso = "";
		}
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}