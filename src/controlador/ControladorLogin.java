package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.NonExistentUserException;
import services.CustomerService;


@WebServlet("/ControladorLogin")
public class ControladorLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CustomerService _servicioCliente;
    public ControladorLogin() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("logout")) {
			sesion.invalidate();
			RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
			vista.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		HttpSession sesion = request.getSession();
		String action=request.getParameter("accion");
		_servicioCliente = new CustomerService();
		if(action.equalsIgnoreCase("login")) {
			try
			{
				_servicioCliente.IniciarSesion(request.getParameter("usuario_cliente"), request.getParameter("contrasena"));
				sesion.setAttribute("usuario_cliente", request.getParameter("usuario_cliente"));
				acceso = "index.jsp";
			}
			catch (NonExistentUserException e)
			{
				request.setAttribute("loginClienteError", e.getMessage());
				acceso = "loginClientes.jsp";
			}
			catch (Exception e)
			{
				request.setAttribute("loginClienteError", "Error interno del servidor");
				acceso = "loginClientes.jsp";
			}
		}
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}
}
