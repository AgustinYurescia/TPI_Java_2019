package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exceptions.NotValidValueCustomException;
import java.sql.Date;
import java.util.ArrayList;

import modeloDAO.ClienteDAO;
import modelo.Cliente;

@WebServlet("/ControladorCliente")
public class ControladorCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String registroClienteError = "registroClienteError.jsp";
	String login = "loginClientes.jsp";
    public ControladorCliente() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClienteDAO cliDAO = new ClienteDAO();
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("cambio_contrasena")) {
			HttpSession sesion = request.getSession(true);
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			String contrasena_actual = request.getParameter("cont_act");
			String contrasena_nueva = request.getParameter("cont_nueva");
			String contrasena_nueva_rep = request.getParameter("cont_nueva_rep");
			ArrayList<String>mensajes = cliDAO.cambioContrasena(usuario, contrasena_actual, contrasena_nueva, contrasena_nueva_rep);
			request.setAttribute("ok_mensaje", mensajes.get(0));
			request.setAttribute("error_mensaje", mensajes.get(1));
			acceso="cambiarContrasena.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}
}
