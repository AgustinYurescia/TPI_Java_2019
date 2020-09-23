package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.ServicioPlazosPrecios;

@WebServlet("/ControladorPlazosPrecios")
public class ControladorPlazosPrecios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioPlazosPrecios _servicioPlazosPrecios;

    public ControladorPlazosPrecios() {
        super();
        _servicioPlazosPrecios = new ServicioPlazosPrecios();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		if(sesion.getAttribute("usuario_admin") != null)
		{
			if(action.equalsIgnoreCase("nuevoPlazoEntrega"))
			{
				try
				{
					_servicioPlazosPrecios.AltaPlazo(Integer.parseInt(request.getParameter("cantidadDias")));
					request.setAttribute("mensajeOk", "Nuevo plazo cargado correctamente");
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "No se pudo cargar el nuevo plazo, error interno del servidor");
				}
				
				acceso="cambioPlazoEntrega.jsp";
			}
			else if(action.equalsIgnoreCase("nuevoPorcentajeGanancia"))
			{
				try
				{
					_servicioPlazosPrecios.AltaPorcentajeGanancia(Double.parseDouble(request.getParameter("porcentajeGanancia")));
					request.setAttribute("mensajeOk", "Nuevo porcentaje de ganancia cargado correctamente");
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "No se pudo cargar el nuevo porcentaje de ganancia, error interno del servidor");
				}
				
				acceso="cambioPorcentajeGanancia.jsp";
			}
			
			else if(action.equalsIgnoreCase("nuevoPorcentajeDescuentoSocio"))
			{
				try
				{
					_servicioPlazosPrecios.AltaPorcentajeDescuentoSocio(Double.parseDouble(request.getParameter("porcentajeDescuento")));
					request.setAttribute("mensajeOk", "Nuevo descuento cargado correctamente");
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "No se pudo cargar el nuevo descuento, error interno del servidor");
				}
				
				acceso="cambioDescuentoSocio.jsp";
			}
			
			else if(action.equalsIgnoreCase("nuevoValorCuotas"))
			{
				try
				{
					_servicioPlazosPrecios.AltaValorCuota(Double.parseDouble(request.getParameter("valorCuota")));
					request.setAttribute("mensajeOk", "Nuevo valor de cuotas cargado correctamente");
				}
				catch(Exception e)
				{
					request.setAttribute("mensajeError", "No se pudo cargar el nuevo valor de cuotas, error interno del servidor");
				}
				
				acceso="cambioValorCuotas.jsp";
			}
		}
		else
		{
			acceso="loginAdmin.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
