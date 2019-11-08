package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloDAO.AdminDAO;


@WebServlet("/ControladorLoginAdmin")
public class ControladorLoginAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdminDAO adDAO = new AdminDAO();
    public ControladorLoginAdmin() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		Boolean rta;
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("login")) {
			String usuario=request.getParameter("usuario_admin");
			String contrasena=request.getParameter("contrasena");
			rta = adDAO.existe(usuario, contrasena);
			System.out.println(rta);
			if (rta) {
				sesion.setAttribute("usuario_admin", usuario);
				response.sendRedirect("indexAdmin.jsp");
			}else {
				response.sendRedirect("loginAdminError.jsp");
			}
		}else if(action.equalsIgnoreCase("logout")) {
			sesion.invalidate();
			response.sendRedirect("index.jsp");
		}else if(action.equalsIgnoreCase("iniciosesion")) {
			response.sendRedirect("loginAdmin.jsp");
		}
		/*doGet(request, response);*/
	}

}
