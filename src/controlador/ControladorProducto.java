package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import modelo.Producto;
import modeloDAO.ProductoDAO;
import services.ServicioProducto;;

@MultipartConfig
@WebServlet("/ControladorProducto")
public class ControladorProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Producto prod = new Producto();
	ProductoDAO prodDAO = new ProductoDAO();
	ServicioProducto _servicioProducto;

	public ControladorProducto() {
        super();
        _servicioProducto = new ServicioProducto();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
	if(action.equalsIgnoreCase("mostrar_producto")) 
		{
			Producto prod;
			try
			{
				prod = _servicioProducto.MostrarProducto(Integer.parseInt(request.getParameter("codigo_producto")));
				request.setAttribute("producto", prod);
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso = "producto.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		
		if(action.equalsIgnoreCase("Agregar")) 
		{
			Part imagen = request.getPart("imagen");
			InputStream imagenInputStream = imagen.getInputStream();
			Double precio = Double.parseDouble(request.getParameter("precio"));
			prod = new Producto(request.getParameter("nombre"), imagenInputStream, Integer.parseInt(request.getParameter("stock")), Integer.parseInt(request.getParameter("codigo_categoria")));
			if(Producto.es_valido(prod.getNombre(), prod.getCodigo_categoria(), imagenInputStream, prod.getStock(), precio)) 
			{
				try 
				{					
					_servicioProducto.AltaProducto(prod, precio);
					request.setAttribute("mensajeOk","Alta realizada con �xito");
					acceso = "altaProducto.jsp";
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError","Error interno del servidor al realizar el alta");
				}
			}
			else 
			{
				request.setAttribute("mensajeError","Error, revise los datos del alta e intente nuevamente");
				acceso = "altaProducto.jsp";
			}
		}
		
		else if(action.equalsIgnoreCase("EditarProducto")) 
		{
			Part imagen = request.getPart("imagen");
			InputStream ImagenInputStream = null;
			if (imagen.getSize() != 0) 
			{
				ImagenInputStream = imagen.getInputStream();
			}
			prod = new Producto(Integer.parseInt(request.getParameter("codigo_producto")), request.getParameter("nombre"), ImagenInputStream, Double.parseDouble(request.getParameter("precio")));
			try
			{
				_servicioProducto.EditarProducto(prod);
				request.setAttribute("mensajeOk", "Modificaci�n realizada con �xito");
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso="editarProducto.jsp";
		}
		
		else if(action.equalsIgnoreCase("BuscarProductoEditar")) 
		{
			try
			{
				prod = _servicioProducto.BuscarProductoEditar(Integer.parseInt(request.getParameter("codigo_producto")));
				request.setAttribute("producto", prod);
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso="editarProducto.jsp";		
		}
		
		else if(action.equalsIgnoreCase("BajaProducto")) 
		{
			try
			{
				_servicioProducto.EliminarProducto(Integer.parseInt(request.getParameter("codigo_producto_baja")));
				request.setAttribute("mensajeOk", "Producto dado de baja con �xito");
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso = "listarProductosAdmin.jsp";
		}
		
		else if(action.equalsIgnoreCase("ActualizarStock")) 
		{
			if (Integer.parseInt(request.getParameter("cantidad")) > 0)
			{
				if (Integer.parseInt(request.getParameter("precio")) > 0)
				{
					try
					{
						_servicioProducto.ReponerStock(Integer.parseInt(request.getParameter("codigo_producto")), Integer.parseInt(request.getParameter("cantidad")), Double.parseDouble(request.getParameter("precio")));
						request.setAttribute("mensajeOk", "Stock actualizado con �xito");
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError", "Error al actualizar el stock, error interno del servidor");
					}
				}
				else
				{
					request.setAttribute("mensajeError", "Error, el precio unitario debe ser mayor que cero");
				}
			}
			else
			{
				request.setAttribute("mensajeError", "Error, la cantidad debe ser mayor que cero");
			}
			acceso="actualizarStock.jsp";
		}
		else if(action.equalsIgnoreCase("listar")) 
		{	
			try
			{
				ArrayList<Producto> lista = (ArrayList<Producto>) _servicioProducto.ObtenerProductos(Integer.parseInt(request.getParameter("codigo_filtro")));
				request.setAttribute("listado", lista);
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor al intentar obtener los productos de la categor�a seleccionada");
			}
			HttpSession sesion = request.getSession(true);
			String usuario_admin = (String)sesion.getAttribute("usuario_admin");
			if (usuario_admin == null) 
			{
				acceso = "listarProductos.jsp";
			}
			else 
			{
				acceso = "listarProductosAdmin.jsp";
			}			
		}
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}
}
