package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exceptions.NotValidValueCustomException;
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
					
					String dni = request.getParameter("dni");
					String nombre = request.getParameter("nombre");
					String apellido = request.getParameter("apellido");
					String telefono = request.getParameter("telefono");
					String direccion = request.getParameter("direccion");
					String mail = request.getParameter("mail");
					String contrasena = request.getParameter("contrasena");
					boolean valido = Cliente.isValid(dni, nombre, apellido, telefono, direccion, mail, contrasena);
					if (valido) {
						Cliente cliente = new Cliente();
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
						acceso = registroClienteError;
						request.setAttribute("registroClienteError","Los datos son erroneos o no se corresponden a las politicas de la empresa.");
					}
				}
				else {
					request.setAttribute("registroClienteError","Las contraseñas ingresadas no coinciden");
					acceso = registroClienteError;
					RequestDispatcher vista = request.getRequestDispatcher(acceso);
					vista.forward(request, response);
				}
			}	
		}
		else if(action.equalsIgnoreCase("modificacion_cliente")){
			HttpSession sesion = request.getSession(true);
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			if (cliDAO.yaExisteUsuario(usuario)) {
				
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String telefono = request.getParameter("telefono");
				String direccion = request.getParameter("direccion");
				String mail = request.getParameter("mail");
				
				if (Cliente.isValid(nombre, apellido, telefono, direccion, mail)) {
					Cliente cliente = cliDAO.buscar_cliente_2 (usuario);
					
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setTelefono(telefono);
					cliente.setDireccion(direccion);
					cliente.setMail(mail);

					cliDAO.modificacion_cliente(cliente);
						
					}
				}else {
					RequestDispatcher vista = request.getRequestDispatcher("error.jsp");
					vista.forward(request, response);
					return;		//hbsbdasmfasd,bfhvasdfbh,asbdfabdhsfkldasf		
				}
			//acceso = "index.jsp";
		}else if(action.equalsIgnoreCase("baja_cliente")){
			HttpSession sesion = request.getSession(true);
			String usuario=request.getParameter("usuario_cliente");
			String contrasena=request.getParameter("contrasena");
			Boolean rta = cliDAO.existe(usuario, contrasena);
			if (rta) {
				cliDAO.baja(usuario, contrasena);
				request.setAttribute("bajaClienteMensaje", "Baja realizada con éxito");
				sesion.invalidate();
				RequestDispatcher vista = request.getRequestDispatcher("bajaCliente.jsp");
				vista.forward(request, response);
			}else {
				request.setAttribute("bajaClienteMensaje", "Usuario y/o contraseña incorrectos");
				RequestDispatcher vista = request.getRequestDispatcher("bajaCliente.jsp");
				vista.forward(request, response);
				return; // dnfljdskhfkasdhjkahjsdljhsdfljhfdasdfldaslhfsalfhskljdf
			}
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}

}
