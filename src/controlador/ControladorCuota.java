package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import modelo.Cuota;
import services.ServicioCuota;

@WebServlet("/ControladorCuota")
public class ControladorCuota extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioCuota _servicioCuota;
	
    public ControladorCuota() {
        super();
        _servicioCuota = new ServicioCuota();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
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
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("buscarCuotas"))
		{
			try
			{
				request.setAttribute("cuotasAnio", (ArrayList<Cuota>)_servicioCuota.ObtenerCuotasAnioActual(request.getParameter("dniCliente")));
			}
			catch(NonExistentFeeException e)
			{
				request.setAttribute("mensajeError", e.getMessage());
			}
			catch(Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			
			acceso="pagoCuotas.jsp";
		}
		else if(action.equalsIgnoreCase("registrarPago"))
		{
			try
			{
				_servicioCuota.RegistrarPago(request.getParameter("dniCliente"), Integer.parseInt(request.getParameter("mesCuota")), Integer.parseInt(request.getParameter("anioCuota")));
				request.setAttribute("mensajeOk", "Pago registrado con éxito, puede volver a buscar las cuotas del cliente para comprobarlo");
			}
			catch(Exception e)
			{
				request.setAttribute("mensajeError", "No se pudo registrar el pago debido a un error interno del servidor");
			}
			
			acceso="pagoCuotas.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
