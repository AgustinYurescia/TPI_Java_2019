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
		HttpSession sesion = request.getSession();
		Boolean rta;
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("login")) {
			String usuario=request.getParameter("usuario");
			String contrasena=request.getParameter("contrasena");
			System.out.println(usuario);
			rta = adDAO.existe(usuario, contrasena);
			System.out.println(rta);
			if (rta && sesion.getAttribute("usuario") == null) {
				sesion.setAttribute("usuario", usuario);
				RequestDispatcher vista = request.getRequestDispatcher("indexAdmin.jsp");
				vista.forward(request, response);
			}else {
				RequestDispatcher vista = request.getRequestDispatcher("loginAdminError.jsp");
				vista.forward(request, response);
			}
		}else if(action.equalsIgnoreCase("logout")) {
			sesion.invalidate();
			RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
			vista.forward(request, response);
		}else if(action.equalsIgnoreCase("iniciosesion")) {
			RequestDispatcher vista = request.getRequestDispatcher("loginAdmin.jsp");
			vista.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
