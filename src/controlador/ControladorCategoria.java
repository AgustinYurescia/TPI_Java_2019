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
import modelo.Categoria;
import services.ServicioCategoria;
import services.ServicioProducto;

@WebServlet("/ControladorCategoria")
public class ControladorCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServicioCategoria _servicioCategoria;
    private ServicioProducto _servicioProducto;
	
    public ControladorCategoria() {
        super();
        _servicioCategoria = new ServicioCategoria(); 
        _servicioProducto = new ServicioProducto(); 
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
		else if (action.equalsIgnoreCase("modificacion"))
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="editarCategoria.jsp";
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
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="altaCategoria.jsp";
		}
		else if (action.equalsIgnoreCase("baja"))
		{
			try
			{
				_servicioProducto.BajaPorCategoria(Integer.parseInt(request.getParameter("codigoCategoria")));
				_servicioCategoria.Baja(Integer.parseInt(request.getParameter("codigoCategoria")));
				request.setAttribute("mensajeOk", "Baja realizada con éxito");
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
		else if (action.equalsIgnoreCase("modificacion"))
		{
			try
			{
				_servicioCategoria.Modificacion(Integer.parseInt(request.getParameter("codigoCategoria")), request.getParameter("categoria"));
				request.setAttribute("mensajeOk", "Modificación realizada con éxito");
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="editarCategoria.jsp";
		}
		
		else if (action.equalsIgnoreCase("buscarCategoriaEditar"))
		{
			try
			{
				request.setAttribute("categoria", (Categoria)_servicioCategoria.BuscarCategoria(Integer.parseInt(request.getParameter("codigoCategoria"))));
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
			acceso="editarCategoria.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
