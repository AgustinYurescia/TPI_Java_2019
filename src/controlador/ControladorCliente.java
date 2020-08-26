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
import exceptions.NonExistentUserException;
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
		HttpSession sesion = request.getSession(true);
		
		if(action.equalsIgnoreCase("alta")) {
			cliente = new Cliente(request.getParameter("dni"),request.getParameter("usuario"),request.getParameter("contrasena"), 
					request.getParameter("nombre"), request.getParameter("apellido"),request.getParameter("mail"),
					request.getParameter("telefono"),request.getParameter("direccion")
					);
			if (_customerService.ValidateEqualPasswords(request.getParameter("contrasena"), request.getParameter("contrasena2")))
			{
				try
				{
					if (request.getParameter("es_socio").equals("1"))
					{
						_customerService.RegisterCustomer(cliente,1);
						request.setAttribute("registroClienteOk", "El socio ha sido registrado en el sistema");
					}
					else
					{
						_customerService.RegisterCustomer(cliente,0);
						request.setAttribute("registroClienteOk", "El usuario ha sido registrado en el sistema");
					}
				}
				catch (ExistentUserException e)
				{
					request.setAttribute("registroClienteError", e.getMessage());
				} 
				catch (Exception e) 
				{					
					request.setAttribute("registroClienteError", "Error interno del servidor");
				}
			}
			else 
			{
				request.setAttribute("registroClienteError", "Las contraseñas no coinciden");
			}			
			acceso = "registroCliente.jsp";
		}
		
		else if(action.equalsIgnoreCase("cambio_contrasena")) {
			if (Cliente.isValid(request.getParameter("cont_nueva")))
			{
				if (_customerService.ValidateEqualPasswords(request.getParameter("cont_nueva"),request.getParameter("cont_nueva_rep"))) 
				{
					try 
					{
						_customerService.CambioContrasena(sesion.getAttribute("usuario_cliente").toString(), request.getParameter("cont_act"), request.getParameter("cont_nueva"));
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
			acceso="cambiarContrasena.jsp";
		}
		
		else if(action.equalsIgnoreCase("baja_cliente"))
		{
			try
			{
			_customerService.Baja(request.getParameter("usuario_cliente"), request.getParameter("contrasena"));
			request.setAttribute("mensajeOk", "Baja realizada con éxito");
			sesion.invalidate();
			}
			catch (NonExistentUserException e)
			{
				request.setAttribute("mensajeError", e.getMessage());
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso = "bajaCliente.jsp";
		}
		
		else if(action.equalsIgnoreCase("hacer_socio"))
		{
			try 
			{
				if (request.getParameter("dni") != null)
				{
					Cliente cli = _customerService.Buscar(request.getParameter("dni"));
					request.setAttribute("cliente", cli);
				}
				else
				{
					_customerService.RegistrarSocio(request.getParameter("dni_cli"));
					request.setAttribute("mensajeOk", "Socio registrado con éxito");					
				}
			}
			catch (NonExistentUserException e)
			{
				request.setAttribute("mensajeError", e.getMessage());
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso = "hacerSocio.jsp";
		}
		
		else if(action.equalsIgnoreCase("modificacion_cliente"))
		{
			String usuario = sesion.getAttribute("usuario_cliente").toString();
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");
			String mail = request.getParameter("mail");
			if (cliDAO.yaExisteUsuario(usuario, mail) && (Cliente.isValid(nombre, apellido, telefono, direccion, mail)) ) 
			{
				Cliente cli = cliDAO.buscar_cliente_por_usuario (usuario);
				cli.setNombre(nombre);
				cli.setApellido(apellido);
				cli.setTelefono(telefono);
				cli.setDireccion(direccion);
				cli.setMail(mail);
				cliDAO.modificacion_cliente(cli);
			}
			else 
			{
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
