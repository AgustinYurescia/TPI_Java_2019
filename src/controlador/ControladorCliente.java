package controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Validators.ValidatorCliente;
import exceptions.AppException;
import exceptions.ExistentUserException;
import exceptions.NonExistentUserException;
import services.CustomerService;
import services.ServicioCuota;
import modelo.Cliente;

@WebServlet("/ControladorCliente")
public class ControladorCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService _customerService;
	private ServicioCuota _servicioCuota;
    public ControladorCliente() {
        super();
        _customerService = new CustomerService();
        _servicioCuota = new ServicioCuota();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if (action == null) {
			acceso = "index.jsp";
		}
		else {
			HttpSession sesion = request.getSession(true);
			if(action.equalsIgnoreCase("bajaSociosDeudores"))
			{
				if (sesion.getAttribute("usuario_admin") != null)
				{
					try
					{
						int nroBajas = _customerService.BajaSociosDeudores();
						if (nroBajas == 0)
						{
							request.setAttribute("mensajeOk", "El proceso se llevó a cabo con éxito, no se encontraron socios deudores");
						}
						else
						{
							request.setAttribute("mensajeOk", "El proceso se llevó a cabo con éxito y fueron dados de baja "+Integer.toString(nroBajas)+" socios deudores");
						}
					}
					catch(Exception e)
					{
						request.setAttribute("mensajeError", "Error interno del seervidor al intentar hacer las bajas correspondientes");
					}
					
					acceso = "index.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			
			else if (action.equalsIgnoreCase("listado_socios")) 
			{
				if(sesion.getAttribute("usuario_admin") == null)
				{
					acceso = "loginAdmin.jsp";
				}
				else
				{
					try
					{
						ArrayList<Cliente> socios = _customerService.ObtenerSocios();
						request.setAttribute("socios", socios);
						acceso = "listadoSocios.jsp";
					}
					catch(AppException e)
					{
						request.setAttribute("mensajeError", e.getMessage());
						acceso = "listadoSocios.jsp";
					}
					catch(Exception e)
					{
						request.setAttribute("mensajeError", "Error inter del servidor");
						acceso = "listadoSocios.jsp";
					}
				}
			}
		}
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cliente cliente = null;
		String acceso = "";
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		
		if(action.equalsIgnoreCase("alta")) {
			cliente = new Cliente(request.getParameter("dni"),request.getParameter("usuario"),request.getParameter("contrasena"), 
					request.getParameter("nombre"), request.getParameter("apellido"),request.getParameter("mail"),
					request.getParameter("telefono"),request.getParameter("direccion"), null, null);
			try {
				ValidatorCliente.ValidarAlta(request.getParameter("dni"),request.getParameter("usuario"),request.getParameter("contrasena"), 
						request.getParameter("nombre"), request.getParameter("apellido"),request.getParameter("mail"),
						request.getParameter("telefono"),request.getParameter("direccion"));
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
			} catch (AppException ex) {
				request.setAttribute("registroClienteError", ex.getMessage());
				request.setAttribute("data_cliente", cliente);
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
			_customerService.Baja(request.getParameter("usuario"), request.getParameter("contrasena"));
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
					Integer cantidadCuotasImpagas = _servicioCuota.getCantidadCuotasSinPago(cli.getDni());
					request.setAttribute("cliente", cli);
					request.setAttribute("cantidadCuotasImpagas", cantidadCuotasImpagas);
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
			if (Cliente.isValid(nombre, apellido, telefono, direccion, mail) ) 
			{
				try {
					_customerService.ActualizarCliente( nombre, apellido, telefono, direccion, mail, usuario);
					acceso = "index.jsp";
				} catch (Exception e) {
					acceso = "error.jsp";
				}
			}
			else 
			{
				acceso = "error.jsp";
			}
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		/*doGet(request, response);*/
	}
}
