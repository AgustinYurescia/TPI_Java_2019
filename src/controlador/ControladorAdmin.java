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
import modelo.Cliente;
import services.ServicioAdmin;

@WebServlet("/ControladorAdmin")
public class ControladorAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioAdmin _servicioAdmin;
	public ControladorAdmin() {
        super();
        _servicioAdmin = new ServicioAdmin();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		if(action.equalsIgnoreCase("cambio_contrasena")) {
			if (Cliente.isValid(request.getParameter("cont_nueva")))
			{
				if (_servicioAdmin.ValidateEqualPasswords(request.getParameter("cont_nueva"),request.getParameter("cont_nueva_rep"))) 
				{
					try 
					{
						_servicioAdmin.CambioContrasena(sesion.getAttribute("usuario_admin").toString(), request.getParameter("cont_act"), request.getParameter("cont_nueva"));
						request.setAttribute("ok_mensaje", "Contraseña modificada con éxito");
					}
					catch (NonExistentUserException e)
					{
						request.setAttribute("error_mensaje", e.getMessage());
					}
					catch (Exception e)
					{
						request.setAttribute("error_mensaje", "Error interno del servidor");
					}
				}
				else
				{
					request.setAttribute("error_mensaje", "Las contraseñas ingresadas no coinciden");
				}
			}
			else
			{
				request.setAttribute("error_mensaje", "La contraseña nueva debe tener longitud mayor o igual a 4 y menor o igual a 45");
			}
			acceso="cambiarContrasenaAdmin.jsp";
		}
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}
}


