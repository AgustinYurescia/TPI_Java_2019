package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exceptions.ExistentCategoryException;
import exceptions.NonExistentCategoryException;
import modelo.Categoria;
import services.ServicioCategoria;
import services.ServicioProducto;
import Validators.ValidatorCategoria;
import exceptions.AppException;

@WebServlet("/ControladorCategoria")
public class ControladorCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServicioCategoria _servicioCategoria;
    private ServicioProducto _servicioProducto;
    private ValidatorCategoria _validatorCategoria;
	
    public ControladorCategoria() {
        super();
        _servicioCategoria = new ServicioCategoria(); 
        _servicioProducto = new ServicioProducto(); 
        _validatorCategoria = new ValidatorCategoria();
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
		HttpSession sesion = request.getSession(true);
		if (sesion.getAttribute("usuario_admin") != null)
		{
			if (action.equalsIgnoreCase("alta"))
			{
				try
				{
					_validatorCategoria.validacion_categoria(request.getParameter("categoria"));
					_servicioCategoria.Alta(request.getParameter("categoria"));
					request.setAttribute("mensajeOk", "Alta realizada con �xito");
				}
				catch(ExistentCategoryException e)
				{
					request.setAttribute("mensajeError", e.getMessage());
				}
			
				catch (AppException e){
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
			else if (action.equalsIgnoreCase("modificacion"))
			{
				try
				{
					_validatorCategoria.validacion_categoria(request.getParameter("categoria"));
					_servicioCategoria.Modificacion(Integer.parseInt(request.getParameter("codigoCategoria")), request.getParameter("categoria"));
					request.setAttribute("mensajeOk", "Modificaci�n realizada con �xito");
				}
				catch (AppException e){
					request.setAttribute("mensajeError", e.getMessage());
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
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

}
