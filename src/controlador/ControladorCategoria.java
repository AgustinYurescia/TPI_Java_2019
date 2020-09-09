package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ExistentCategoryException;
import exceptions.NonExistentCategoryException;
import services.ServicioCategoria;
import services.ServicioProducto;

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
		else if (action.equalsIgnoreCase("baja"))
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="bajaCategoria.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if (action.equalsIgnoreCase("alta"))
		{
			try
			{
				_servicioCategoria.Alta(request.getParameter("categoria"));
				request.setAttribute("mensajeOk", "Alta realizada con �xito");
			}
			catch(ExistentCategoryException e)
			{
				request.setAttribute("mensajeError", e.getMessage());
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="altaCategoria.jsp";
		}
		else if (action.equalsIgnoreCase("baja"))
		{
			try
			{
				ServicioProducto _servicioProducto = new ServicioProducto(); 
				_servicioProducto.BajaPorCategoria(Integer.parseInt(request.getParameter("codigoCategoria")));
				_servicioCategoria.Baja(Integer.parseInt(request.getParameter("codigoCategoria")));
				request.setAttribute("mensajeOk", "Baja realizada con �xito");
			}
			catch (NonExistentCategoryException e)
			{
				request.setAttribute("mensajeError", e.getMessage());
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="bajaCategoria.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
