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
import modeloDAO.AdminDAO;
import services.ServicioAdmin;

@WebServlet("/ControladorLoginAdmin")
public class ControladorLoginAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioAdmin _servicioAdmin;
	AdminDAO adDAO = new AdminDAO();
    public ControladorLoginAdmin() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("logout")) 
		{
			sesion.invalidate();
			RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
			vista.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		HttpSession sesion = request.getSession();
		String action=request.getParameter("accion");
		_servicioAdmin = new ServicioAdmin();
		if(action.equalsIgnoreCase("login")) {
			try
			{
				_servicioAdmin.IniciarSesion(request.getParameter("usuario_admin"), request.getParameter("contrasena"));
				sesion.setAttribute("usuario_admin", request.getParameter("usuario_admin"));
				acceso = "index.jsp";
			}
			catch (NonExistentUserException e)
			{
				request.setAttribute("loginAdminError", e.getMessage());
				acceso = "loginAdmin.jsp";
			}
			catch (Exception e)
			{
				request.setAttribute("loginAdminError", "Error interno del servidor");
				acceso = "loginAdmin.jsp";
			}
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}

}
