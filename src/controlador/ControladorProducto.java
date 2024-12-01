package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import modelo.Producto;
import modeloDAO.ProductoDAO;
import services.ServicioCategoria;
import services.ServicioProducto;
import Validators.ValidatorProducto;
import exceptions.AppException;

@MultipartConfig
@WebServlet("/ControladorProducto")
public class ControladorProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Producto prod = new Producto();
	ProductoDAO prodDAO = new ProductoDAO();
	ServicioProducto _servicioProducto;
	ServicioCategoria _servicioCategoria;
	ValidatorProducto _validatorProducto;
	Gson _gson;

	public ControladorProducto() {
        super();
        _servicioProducto = new ServicioProducto();
        _servicioCategoria =  new ServicioCategoria();
        _validatorProducto = new ValidatorProducto();
        _gson = new Gson();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		if (action == null) 
		{
			acceso = "index.jsp";
		}
		if(action.equalsIgnoreCase("mostrar_producto")) 
		{
			Producto prod;
			try
			{
				prod = _servicioProducto.GetProducto(Integer.parseInt(request.getParameter("codigo_producto")));
				request.setAttribute("producto", prod);
			}
			catch (Exception e)
			{
				request.setAttribute("mensajeError", "Error interno del servidor");
			}
			acceso = "producto.jsp";
		}
		else if(action.equalsIgnoreCase("listarCliente")) 
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="listarProductos.jsp";
		}
		else if(action.equalsIgnoreCase("listarAdmin")) 
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="listarProductosAdmin.jsp";
		}
		else if(action.equalsIgnoreCase("alta")) 
		{
			request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
			acceso="altaProducto.jsp";
		}
		else if(action.equalsIgnoreCase("graficoVentas")) 
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				Date date = new Date();
		        ZoneId timeZone = ZoneId.systemDefault();
		        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
		        Integer anio = getLocalDate.getYear();
				try 
				{
					request.setAttribute("imageAsBase64", _servicioProducto.obtenerVentasPorProducto(anio));
				} 
				catch (Exception e) 
				{
					request.setAttribute("mensajeError","Error interno del servidor");
				}
				acceso="graficoVentas.jsp";
			}
			else
			{
				acceso = "loginAdmin.jsp";
			}
		}
			
			RequestDispatcher vista = request.getRequestDispatcher(acceso);
			vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String ajax_action = request.getParameter("ajax_action");
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		if (action != null)
		{
			if(action.equalsIgnoreCase("Agregar")) 
			{
				if (sesion.getAttribute("usuario_admin") != null)
				{
					request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
					Part imagen = request.getPart("imagen");
					InputStream imagenInputStream = null;
					try {
						_validatorProducto.validacion_producto(request.getParameter("nombre"), request.getParameter("codigo_categoria"), request.getParameter("stock"), request.getParameter("precio"), imagen, null);
						imagenInputStream = imagen.getInputStream();
						Double precio = Double.parseDouble(request.getParameter("precio"));
						prod = new Producto(request.getParameter("nombre"), imagenInputStream, Integer.parseInt(request.getParameter("stock")), Integer.parseInt(request.getParameter("codigo_categoria")));
						_servicioProducto.AltaProducto(prod, precio);
						request.setAttribute("mensajeOk","Alta realizada con éxito");
					}
					catch(AppException e){
						request.setAttribute("mensajeError", e.getMessage());
						request.setAttribute("nombre", request.getParameter("nombre"));
						request.setAttribute("cantidad", request.getParameter("stock"));
						request.setAttribute("precio", request.getParameter("precio"));
						request.setAttribute("imagen", imagen);
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError","Error interno del servidor al realizar el alta");
					}
					acceso = "altaProducto.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			else if(action.equalsIgnoreCase("EditarProducto"))
			{
				if (sesion.getAttribute("usuario_admin") != null)
				{
					Part imagen = request.getPart("imagen");
					InputStream ImagenInputStream;
					try
					{
						_validatorProducto.validacion_producto(request.getParameter("nombre"), null, null, request.getParameter("precio"), null, null);
						ImagenInputStream = (imagen.getSize() != 0)?imagen.getInputStream():null;
						prod = new Producto(Integer.parseInt(request.getParameter("codigo_producto")), request.getParameter("nombre"), ImagenInputStream, Double.parseDouble(request.getParameter("precio")));
						_servicioProducto.EditarProducto(prod);
						request.setAttribute("mensajeOk", "Modificación realizada con éxito");
					}
					catch (AppException e) {
						request.setAttribute("mensajeError", e.getMessage());
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError", "Error interno del servidor");
					}
					acceso="editarProducto.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			
			else if(action.equalsIgnoreCase("BuscarProductoEditar"))
			{
				if (sesion.getAttribute("usuario_admin") != null)
				{
					try
					{
						prod = _servicioProducto.GetProducto(Integer.parseInt(request.getParameter("codigo_producto")));
						request.setAttribute("producto", prod);
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError", "Error interno del servidor");
					}
					acceso="editarProducto.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			
			else if(action.equalsIgnoreCase("BajaProducto")) 
			{
				if (sesion.getAttribute("usuario_admin") != null)
				{
					try
					{
						_servicioProducto.EliminarProducto(Integer.parseInt(request.getParameter("codigo_producto_baja")));
						request.setAttribute("mensajeOk", "Producto dado de baja con éxito");
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError", "Error interno del servidor");
					}
					request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
					acceso = "listarProductosAdmin.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			
			else if(action.equalsIgnoreCase("ActualizarStock")) 
			{
				if (sesion.getAttribute("usuario_admin") != null)
				{
					try
					{
						_validatorProducto.validacion_producto(null, null, request.getParameter("stock"), request.getParameter("precio"), null, request.getParameter("codigo_producto"));
						_servicioProducto.ReponerStock(Integer.parseInt(request.getParameter("codigo_producto")), Integer.parseInt(request.getParameter("stock")), Double.parseDouble(request.getParameter("precio")));
						request.setAttribute("mensajeOk", "Stock actualizado con éxito");
					}
					catch(AppException e) 
					{
						request.setAttribute("cantidad", request.getParameter("stock"));
						request.setAttribute("precio", request.getParameter("precio"));
						request.setAttribute("mensajeError", e.getMessage());
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError", "Error al actualizar el stock, error interno del servidor");
					}
					
					acceso="actualizarStock.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			else if(action.equalsIgnoreCase("DescontarStock")){
				if (sesion.getAttribute("usuario_admin") != null)
				{
					try
					{
						_validatorProducto.validacion_producto(null, null, request.getParameter("stock"), request.getParameter("precio"), null, request.getParameter("codigo_producto"));
						_servicioProducto.DescontarStock(Integer.parseInt(request.getParameter("codigo_producto")), Integer.parseInt(request.getParameter("stock")));
						request.setAttribute("mensajeOk", "Stock actualizado con éxito");
					}
					catch(AppException e) 
					{
						request.setAttribute("cantidad", request.getParameter("stock"));
						request.setAttribute("mensajeError", e.getMessage());
					}
					catch (Exception e)
					{
						request.setAttribute("mensajeError", "Error al actualizar el stock, error interno del servidor");
					}
					
					acceso="descontarStock.jsp";
				}
				else
				{
					acceso = "loginAdmin.jsp";
				}
			}
			else if(action.equalsIgnoreCase("listar") || action.equalsIgnoreCase("listarParaBajaCat")) 
			{	
				request.setAttribute("categorias", _servicioCategoria.obtenerTodas());
				try
				{
					ArrayList<Producto> lista = (ArrayList<Producto>) _servicioProducto.ObtenerProductos(Integer.parseInt(request.getParameter("codigo_filtro")));
					request.setAttribute("listado", lista);
					request.setAttribute("categoria", request.getParameter("codigo_filtro"));
				}
				catch (Exception e)
				{
					request.setAttribute("mensajeError", "Error interno del servidor al intentar obtener los productos de la categoría seleccionada");
				}
				String usuario_admin = (String)sesion.getAttribute("usuario_admin");
				if (action.equalsIgnoreCase("listar"))
				{
					if (usuario_admin == null) 
					{
						acceso = "listarProductos.jsp";
					}
					else 
					{
						acceso = "listarProductosAdmin.jsp";
					}
				}
				else
				{
					acceso = "bajaCategoria.jsp";
				}
			}
		}
		if (ajax_action != null && ajax_action.equalsIgnoreCase("buscar_producto"))
		{
			String codigo_producto = request.getParameter("codigo_producto");
			try 
			{
				Producto producto = _servicioProducto.GetProductoSinImagen(Integer.parseInt(codigo_producto));
				String res = _gson.toJson(producto);
				SendSuccessResponse(res ,response);
				return;
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			RequestDispatcher vista = request.getRequestDispatcher(acceso);
			vista.forward(request, response);
		}
	}
	
	private static void SendSuccessResponse(String mensaje,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		out.print(mensaje);
		out.flush();
	}
}
