package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.ExistentUserException;
import exceptions.NotValidValueCustomException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import modeloDAO.ClienteDAO;
import services.CustomerService;
import modelo.Cliente;

@WebServlet("/ControladorCliente")
public class ControladorCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService _customerService;
    public ControladorCliente() {
        super();
        _customerService = new CustomerService();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClienteDAO cliDAO = new ClienteDAO();
		Cliente cliente = null;
		String acceso = "";
		String action = request.getParameter("accion");
		
		if(action.equalsIgnoreCase("alta")) {
			
			cliente = new Cliente(request.getParameter("dni"),request.getParameter("usuario"),request.getParameter("contrasena"), 
					request.getParameter("nombre"), request.getParameter("apellido"),request.getParameter("mail"),
					request.getParameter("telefono"),request.getParameter("direccion")
					);
			
			if (_customerService.ValidateEqualPasswords(request.getParameter("contrasena"), request.getParameter("contrasena2"))){
				try
				{
					_customerService.RegisterCustomer(cliente);
					request.setAttribute("registroClienteOk", "El usuario ha sido registrado en el sistema");
				}
				catch (ExistentUserException e)
				{
					request.setAttribute("registroClienteError", e.getMessage());
				} catch (Exception e) {
					
					request.setAttribute("registroClienteError", "Error interno del servidor");
				}
			}else {
				request.setAttribute("registroClienteError", "Las contraseñas no coinciden");
			}
			
			acceso = "registroCliente.jsp";
		}
		
		else if(action.equalsIgnoreCase("cambio_contrasena")) {
			HttpSession sesion = request.getSession(true);
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			String contrasena_actual = request.getParameter("cont_act");
			String contrasena_nueva = request.getParameter("cont_nueva");
			String contrasena_nueva_rep = request.getParameter("cont_nueva_rep");
			ArrayList<String>mensajes = cliDAO.cambioContrasena(usuario, contrasena_actual, contrasena_nueva, contrasena_nueva_rep);
			request.setAttribute("ok_mensaje", mensajes.get(0));
			request.setAttribute("error_mensaje", mensajes.get(1));
			acceso="cambiarContrasena.jsp";
		}else if(action.equalsIgnoreCase("baja_cliente")){
			String usuario=request.getParameter("usuario_cliente");
			String contrasena=request.getParameter("contrasena");
			ArrayList<String> mensajes = cliDAO.baja(usuario, contrasena);
			request.setAttribute("mensajeOk", mensajes.get(0));
			request.setAttribute("mensajeError", mensajes.get(1));
			acceso = "bajaCliente.jsp";
		}else if(action.equalsIgnoreCase("buscar")){
			String dni = request.getParameter("dni");
			Cliente cli = cliDAO.buscar_cliente_por_dni(dni);
			request.setAttribute("cliente", cli);
			acceso = "hacerSocio.jsp";
		}else if(action.equalsIgnoreCase("registrar_socio")){
			String dni = request.getParameter("dni_cli");
			cliDAO.registrar_socio(dni);
			request.setAttribute("mensaje_exito", "Socio registrado con éxito");
			acceso = "hacerSocio.jsp";
		}
		else if(action.equalsIgnoreCase("modificacion_cliente")){
			HttpSession sesion = request.getSession(true);
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");
			String mail = request.getParameter("mail");
			if (cliDAO.yaExisteUsuario(usuario, mail) && (Cliente.isValid(nombre, apellido, telefono, direccion, mail)) ) {
					Cliente cli = cliDAO.buscar_cliente_por_usuario (usuario);
					cli.setNombre(nombre);
					cli.setApellido(apellido);
					cli.setTelefono(telefono);
					cli.setDireccion(direccion);
					cli.setMail(mail);
					cliDAO.modificacion_cliente(cli);
			}else {
				RequestDispatcher vista = request.getRequestDispatcher("error.jsp");
				vista.forward(request, response);
				return;		
			}
			acceso = "index.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}
}
