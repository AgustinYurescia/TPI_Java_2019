package controlador;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.NonExistentFeeValueException;
import exceptions.NonExistentPartnerException;
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
		if(action.equalsIgnoreCase("generarCuotas"))
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
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
