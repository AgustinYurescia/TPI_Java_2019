package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
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
		if(action.equalsIgnoreCase("alta")) {
			String nombre_usuario = request.getParameter("usuario");
			if(cliDAO.yaExisteUsuario(nombre_usuario)) {
				request.setAttribute("registroClienteError", "El usuario ingresado ya existe");
				acceso = registroClienteError;
			}else {
				if (request.getParameter("contrasena").equals(request.getParameter("contrasena2"))) {
					Cliente cliente = new Cliente();
					String dni = request.getParameter("dni");
					String nombre = request.getParameter("nombre");
					String apellido = request.getParameter("apellido");
					String telefono = request.getParameter("telefono");
					String direccion = request.getParameter("direccion");
					String mail = request.getParameter("mail");
					String contrasena = request.getParameter("contrasena");
					cliente.setDni(dni);
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setTelefono(telefono);
					cliente.setDireccion(direccion);
					cliente.setMail(mail);
					cliente.setCliente_usuario(nombre_usuario);
					cliente.setCliente_contrasena(contrasena);
					cliDAO.alta(cliente);
					acceso = login;
				}else {
					request.setAttribute("registroClienteError","Las contraseñas ingresadas no coinciden");
					acceso = registroClienteError;
				}
			}	
		}
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}

}
