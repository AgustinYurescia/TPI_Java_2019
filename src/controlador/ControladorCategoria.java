package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ExistentCategoryException;
import services.ServicioCategoria;

@WebServlet("/ControladorCategoria")
public class ControladorCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServicioCategoria _servicioCategoria;
	
    public ControladorCategoria() {
        super();
        _servicioCategoria = new ServicioCategoria(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if (action.equalsIgnoreCase("alta"))
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="altaCategoria.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if (action.equalsIgnoreCase("alta"))
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			try
			{
				_servicioCategoria.Alta(request.getParameter("categoria"));
				request.setAttribute("mensajeOk", "Alta realizada con éxito");
			}
			catch(ExistentCategoryException e)
			{
				request.setAttribute("mensajeError", e.getMessage());
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso="altaCategoria.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
