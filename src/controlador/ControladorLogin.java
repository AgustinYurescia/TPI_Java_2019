package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloDAO.ClienteDAO;


@WebServlet("/ControladorLogin")
public class ControladorLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteDAO cliDAO = new ClienteDAO();
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
		HttpSession sesion = request.getSession();
		Boolean rta;
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("login")) {
			String usuario=request.getParameter("usuario_cliente");
			String contrasena=request.getParameter("contrasena");
			rta = cliDAO.existe(usuario, contrasena);
			if (rta && sesion.getAttribute("usuario_cliente") == null) {
				sesion.setAttribute("usuario_cliente", usuario);
				RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
				vista.forward(request, response);
			}else {
				RequestDispatcher vista = request.getRequestDispatcher("loginClientesError.jsp");
				vista.forward(request, response);
			}
			
		}else if(action.equalsIgnoreCase("logout")) {
			sesion.invalidate();
			RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
			vista.forward(request, response);
		}else if(action.equalsIgnoreCase("iniciosesion")) {
			RequestDispatcher vista = request.getRequestDispatcher("loginClientes.jsp");
			vista.forward(request, response);
		}
		/*doGet(request, response);*/
	}

}
