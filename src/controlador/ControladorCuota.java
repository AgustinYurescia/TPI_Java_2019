package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.NonExistentFeeException;
import exceptions.NonExistentFeeValueException;
import exceptions.NonExistentPartnerException;
import modelo.Cliente;
import modelo.Cuota;
import services.CustomerService;
import services.ServicioCuota;

@WebServlet("/ControladorCuota")
public class ControladorCuota extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioCuota _servicioCuota;
	private CustomerService _servicioCliente;
	
    public ControladorCuota() {
        super();
        _servicioCuota = new ServicioCuota();
        _servicioCliente = new CustomerService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		Cliente cliente;
		HttpSession sesion = request.getSession(true);
		if(action.equalsIgnoreCase("generarCuotas"))
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try
				{
					_servicioCuota.GenerarCuotas();
					request.setAttribute("mensajeOk", "Cuotas generadas correctamente");
				}
				catch(NonExistentPartnerException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch(NonExistentFeeValueException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch(SQLException e)
				{
					if(e.getErrorCode() == 1062)
					{
						request.setAttribute("mensajeError", "Las cuotas correspondientes al mes actual ya fueron generadas anteriormente");
					}
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso="generacionDeCuotas.jsp";
			}
			else
			{
				acceso="loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("misCuotas"))
		{
			if (sesion.getAttribute("usuario_cliente") != null)
			{
				try
				{
					cliente = _servicioCliente.ObtenerPorNombreDeUsuario((String)sesion.getAttribute("usuario_cliente"));
					request.setAttribute("misCuotas", (ArrayList<Cuota>)_servicioCuota.ObtenerCuotasImpagas(cliente.getDni()));
				}
				catch(NonExistentFeeException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				acceso="misCuotas.jsp";
			}
			else
			{
				acceso = "loginClientes.jsp";
			}
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		if(action.equalsIgnoreCase("buscarCuotas"))
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try
				{
					request.setAttribute("cuotasImpagas", (ArrayList<Cuota>)_servicioCuota.ObtenerCuotasImpagas(request.getParameter("dniCliente")));
				}
				catch(NonExistentFeeException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				
				request.setAttribute("dni", request.getParameter("dniCliente"));
				acceso="pagoCuotas.jsp";
			}
			else
			{
				acceso = "loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("registrarPago") || action.equalsIgnoreCase("registrarPagoDesdeListado"))
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try
				{
					String dniCliente = request.getParameter("dniCliente");
					String mes = request.getParameter("mesCuota");
					String anio = request.getParameter("anioCuota");					
					_servicioCuota.RegistrarPago(dniCliente, Integer.parseInt(mes), Integer.parseInt(anio));
					if (!action.equalsIgnoreCase("registrarPago"))
					{
						for (Cuota c : (ArrayList<Cuota>)sesion.getAttribute("cuotas"))
						{
							if(c.getDniCliente().equalsIgnoreCase(dniCliente) && c.getMes() == Integer.parseInt(mes) && c.getAnio() == Integer.parseInt(anio)) 
							{
								LocalDate today =  LocalDate.now();
								c.setFechaPago(today);
							}
						}
					}
					request.setAttribute("mensajeOk", "Pago registrado con éxito");
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "No se pudo registrar el pago debido a un error interno del servidor");
				}
				if (action.equalsIgnoreCase("registrarPago"))
				{
					request.setAttribute("dni", request.getParameter("dniCliente"));
					acceso="pagoCuotas.jsp";
				}
				else
				{
					acceso="listadoCuotas.jsp";
				}
			}
			else
			{
				acceso = "loginAdmin.jsp";
			}
		}
		else if(action.equalsIgnoreCase("listadoCuotas"))
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try
				{
					sesion.setAttribute("cuotas", (ArrayList<Cuota>)_servicioCuota.ListadoCuotas(request.getParameter("mes"), request.getParameter("anio")));
				}
				catch(NonExistentFeeException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor");
				}
				
				request.setAttribute("mes", request.getParameter("mes"));
				request.setAttribute("anio", request.getParameter("anio"));
				acceso="listadoCuotas.jsp";
			}
			else
			{
				acceso = "loginAdmin.jsp";
			}
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
