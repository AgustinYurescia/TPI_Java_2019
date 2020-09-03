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
	String listar = "listarProductos.jsp";
	String listarAdmin = "listarProductosAdmin.jsp";
	String mostrar_producto = "producto.jsp";
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
		if(action.equalsIgnoreCase("listar")) 
		{
			int codigo_categoria = Integer.parseInt(request.getParameter("codigo_filtro"));
			ProductoDAO pr = new ProductoDAO();
			if (codigo_categoria == 0)
			{ 
				ArrayList<Producto> lista = (ArrayList<Producto>) pr.obtener_todos();
				request.setAttribute("listado", lista);
			}else 
			{
				request.setAttribute("listado", pr.obtener_por_codigo_categoria(codigo_categoria));
			}
			HttpSession sesion = request.getSession(true);
			String usuario_admin = (String)sesion.getAttribute("usuario_admin");
			if (usuario_admin == null) 
			{
				acceso = listar;
			}
			else 
			{
				acceso = listarAdmin;
			}			
		}
		else if(action.equalsIgnoreCase("mostrar_producto")) 
		{
			String codigo_producto;
			ProductoDAO pdao = new ProductoDAO();
			Producto prod;
			codigo_producto = request.getParameter("codigo_producto");
			prod = pdao.buscar_producto(Integer.parseInt(codigo_producto));
			request.setAttribute("producto", prod);
			acceso = mostrar_producto;
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
			prod = new Producto(request.getParameter("nombre"), imagenInputStream, Integer.parseInt(request.getParameter("stock")),Integer.parseInt(request.getParameter("codigo_categoria")));
			if(Producto.es_valido(prod.getNombre(), prod.getCodigo_categoria(), imagenInputStream, prod.getStock(), precio)) 
			{
				try 
				{					
				_servicioProducto.AltaProducto(prod, precio);
				request.setAttribute("mensajeOk","Alta realizada con éxito");
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
			ProductoDAO pdao = new ProductoDAO();
			String codigo_producto = request.getParameter("codigo_producto");
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			prod.setCodigo((Integer.parseInt(codigo_producto)));
			prod.setNombre(nombre);
			prod.set_imagen(ImagenInputStream);
			prod.setPrecioVenta(Double.parseDouble(precio));
			pdao.editar_producto(prod);
			acceso="indexAdmin.jsp";
		}
		else if(action.equalsIgnoreCase("BuscarProductoEditar")) 
		{
			String codigo_producto;
			ProductoDAO pdao = new ProductoDAO();
			codigo_producto = request.getParameter("codigo_producto");
			prod = pdao.buscar_producto(Integer.parseInt(codigo_producto));
			request.setAttribute("producto", prod);
			acceso="editarProducto.jsp";		
		}
		else if(action.equalsIgnoreCase("BajaProducto")) 
		{
			ProductoDAO pdao = new ProductoDAO();
			String codigo_producto_baja = request.getParameter("codigo_producto_baja");
			pdao.baja(Integer.parseInt(codigo_producto_baja));
			acceso = "indexAdmin.jsp";
		}
		else if(action.equalsIgnoreCase("ActualizarStock")) 
		{
			ProductoDAO pdao = new ProductoDAO();
			String codigo_producto = request.getParameter("codigo_producto");
			String cantidad = request.getParameter("cantidad");
			String precio = request.getParameter("precio");
			pdao.reponer_stock(Integer.parseInt(codigo_producto), Integer.parseInt(cantidad), Double.parseDouble(precio));
			acceso="indexAdmin.jsp";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}
}
