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
import java.util.ArrayList;

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
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");
			String mail = request.getParameter("mail");
			
			if (cliDAO.yaExisteUsuario(usuario) && (Cliente.isValid(nombre, apellido, telefono, direccion, mail)) ) {
				

					Cliente cliente = cliDAO.buscar_cliente_2 (usuario);
					
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setTelefono(telefono);
					cliente.setDireccion(direccion);
					cliente.setMail(mail);

					cliDAO.modificacion_cliente(cliente);
			}else {
				RequestDispatcher vista = request.getRequestDispatcher("error.jsp");
				vista.forward(request, response);
				return;		
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
		}else if(action.equalsIgnoreCase("buscar")){
			String dni = request.getParameter("dni");
			Cliente cliente = cliDAO.buscar_cliente_por_dni(dni);
			request.setAttribute("cliente", cliente);
			acceso = "hacerSocio.jsp";
		}else if(action.equalsIgnoreCase("registrar_socio")){
			String dni = request.getParameter("dni_cli");
			cliDAO.registrar_socio(dni);
			request.setAttribute("mensaje_exito", "Socio registrado con éxito");
			acceso = "hacerSocio.jsp";
		}else if(action.equalsIgnoreCase("cambio_contrasena")) {
			HttpSession sesion = request.getSession(true);
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			System.out.println(usuario);
			String contrasena_actual = request.getParameter("cont_act");
			System.out.println(contrasena_actual);
			String contrasena_nueva = request.getParameter("cont_nueva");
			String contrasena_nueva_rep = request.getParameter("cont_nueva_rep");
			ArrayList<String>mensajes = cliDAO.cambioContrasena(usuario, contrasena_actual, contrasena_nueva, contrasena_nueva_rep);
			request.setAttribute("ok_mensaje", mensajes.get(0));
			request.setAttribute("error_mensaje", mensajes.get(1));
			acceso="cambiarContrasena.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}

}
