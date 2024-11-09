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
import services.ServicioPlazosPrecios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@WebServlet("/ControladorLogin")
public class ControladorLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger _logger = LogManager.getLogger(ControladorLogin.class);
	private ServicioPlazosPrecios _servicioPlazosPrecios;

	CustomerService _servicioCliente;
    public ControladorLogin() {
        super();
        this._servicioPlazosPrecios = new ServicioPlazosPrecios();
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
				_logger.info("Iniciando sesión");
				_servicioCliente.IniciarSesion(request.getParameter("usuario"), request.getParameter("contrasena"));
				sesion.setAttribute("usuario_cliente", request.getParameter("usuario"));
				if(_servicioCliente.EsSocio(request.getParameter("usuario"))) {
					float porcentajeDescuento = _servicioPlazosPrecios.obtenerPrcentajeDescuentoSocio();
					sesion.setAttribute("es_socio","1");
					sesion.setAttribute("dcto_socio", Float.toString(porcentajeDescuento * 100));
				}
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
