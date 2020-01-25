package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
				RequestDispatcher vista = request.getRequestDispatcher(acceso);
				vista.forward(request, response);
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
				}
				else {
					request.setAttribute("registroClienteError","Las contrase�as ingresadas no coinciden");
					acceso = registroClienteError;
					RequestDispatcher vista = request.getRequestDispatcher(acceso);
					vista.forward(request, response);
				}
			}	
		}
		else if(action.equalsIgnoreCase("modificacion_cliente")){
			HttpSession sesion = request.getSession(true);
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			if (cliDAO.yaExisteUsuario(usuario)) {  //cambiar eso por usuario solamente
				Cliente cli = cliDAO.buscar_cliente(usuario);
				boolean cambios = false;
				
				if ((request.getParameter("nombre") != "") && (request.getParameter("nombre") != null)) {
					cli.setNombre(request.getParameter("nombre"));
					cambios = true;
				}
				if ((request.getParameter("apellido") != "") && (request.getParameter("apellido") != null)) {
					cli.setApellido(request.getParameter("apellido"));
					cambios = true;
				}
				if ((request.getParameter("mail") != "") && (request.getParameter("mail") != null)) {
					cli.setMail(request.getParameter("mail"));
					cambios = true;
				}
				if ((request.getParameter("direccion") != "") && (request.getParameter("direccion") != null)) {
					cli.setDireccion(request.getParameter("direccion"));
					cambios = true;
				}
				if ((request.getParameter("telefono") != "") && (request.getParameter("telefono") != null)) {
					cli.setTelefono(request.getParameter("telefono"));
					cambios = true;
				}
				if (cambios == true) {
					cliDAO.modificacion_cliente(cli);        
				}
			}
			acceso = "index.jsp";
		} 
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}

}
