package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
