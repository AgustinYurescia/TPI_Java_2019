package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import services.ServicioPlazosPrecios;
import exceptions.AppException;
import Validators.TasasPlazosValidator;

@WebServlet("/ControladorPlazosPrecios")
public class ControladorPlazosPrecios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServicioPlazosPrecios _servicioPlazosPrecios;
	private TasasPlazosValidator _tasasPlazosValidator;
	private Gson _gson;

    public ControladorPlazosPrecios() {
        super();
        _servicioPlazosPrecios = new ServicioPlazosPrecios();
        _tasasPlazosValidator = new TasasPlazosValidator();
        _gson = new Gson();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ajax_action = request.getParameter("ajax_action");
		if (ajax_action != null && ajax_action.equalsIgnoreCase("obtenerPorcGanancia"))
		{
			try 
			{
				float porc = _servicioPlazosPrecios.obtenerPorcGanancia();
				String res = _gson.toJson(porc);
				SendSuccessResponse(res ,response);
				return;
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (ajax_action != null && ajax_action.equalsIgnoreCase("obtenerPorcDescuento"))
		{
			try 
			{
				float porc = _servicioPlazosPrecios.obtenerPrcentajeDescuentoSocio();
				String res = _gson.toJson(porc);
				SendSuccessResponse(res ,response);
				return;

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (ajax_action != null && ajax_action.equalsIgnoreCase("obtenerPlazoEntrega"))
		{
			try 
			{
				Integer plazo = _servicioPlazosPrecios.obtenerPlazoEntrega();
				String res = _gson.toJson(plazo);
				SendSuccessResponse(res ,response);
				return;

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (ajax_action != null && ajax_action.equalsIgnoreCase("obtenerValorCuotas"))
		{
			try 
			{
				double valor = _servicioPlazosPrecios.obtenerValorCuotas();
				String res = _gson.toJson(valor);
				SendSuccessResponse(res ,response);
				return;

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
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
					_tasasPlazosValidator.validacion_plazo_entrega(request.getParameter("cantidadDias"));
					_servicioPlazosPrecios.AltaPlazo(Integer.parseInt(request.getParameter("cantidadDias")));
					request.setAttribute("mensajeOk", "Nuevo plazo cargado correctamente");
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
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
					_tasasPlazosValidator.validacion_porcentaje_ganancia(request.getParameter("porcentajeGanancia"));
					_servicioPlazosPrecios.AltaPorcentajeGanancia(Double.parseDouble(request.getParameter("porcentajeGanancia")));
					request.setAttribute("mensajeOk", "Nuevo porcentaje de ganancia cargado correctamente");
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
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
					_tasasPlazosValidator.validacion_descuento_socio(request.getParameter("porcentajeDescuento"));
					_servicioPlazosPrecios.AltaPorcentajeDescuentoSocio(Double.parseDouble(request.getParameter("porcentajeDescuento")));
					request.setAttribute("mensajeOk", "Nuevo descuento cargado correctamente");
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
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
					_tasasPlazosValidator.validacion_valor_cuotas(request.getParameter("valorCuota"));
					_servicioPlazosPrecios.AltaValorCuota(Double.parseDouble(request.getParameter("valorCuota")));
					request.setAttribute("mensajeOk", "Nuevo valor de cuotas cargado correctamente");
				}
				catch(AppException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
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
	
	private static void SendSuccessResponse(String mensaje,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		out.print(mensaje);
		out.flush();
	}

}
