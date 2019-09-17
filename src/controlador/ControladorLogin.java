package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modeloDAO.ClienteDAO;


@WebServlet("/ControladorLogin")
public class ControladorLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteDAO cliDAO = new ClienteDAO();
    public ControladorLogin() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean rta;
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("login")) {
			String usuario=request.getParameter("usuario");
			String contrasena=request.getParameter("contrasena");
			System.out.println(usuario);
			rta = cliDAO.existe(usuario, contrasena);
			if (rta) {
				RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
				vista.forward(request, response);
			}else {
				RequestDispatcher vista = request.getRequestDispatcher("loginClientesError.jsp");
				vista.forward(request, response);
			}
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
