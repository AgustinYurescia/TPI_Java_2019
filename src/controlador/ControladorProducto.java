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
import modeloDAO.CategoriaDAO;


@MultipartConfig
@WebServlet("/ControladorProducto")
public class ControladorProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String listar = "listarProductos.jsp";
	String listarAdmin = "listarProductosAdmin.jsp";
	String mostrar_producto = "producto.jsp";
	Producto prod = new Producto();
	ProductoDAO prodDAO = new ProductoDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("listar")) {
			int codigo_categoria = Integer.parseInt(request.getParameter("codigo_filtro"));
			ProductoDAO pr = new ProductoDAO();
			if (codigo_categoria == 0){ 
				ArrayList<Producto> lista = (ArrayList<Producto>) pr.obtener_todos();
				request.setAttribute("listado", lista);
			}else {
					request.setAttribute("listado", pr.obtener_por_codigo_categoria(codigo_categoria));
			}
			HttpSession sesion = request.getSession(true);
			String usuario_admin = (String)sesion.getAttribute("usuario_admin");
			if (usuario_admin == null) {
				acceso = listar;
			}
			else {
				acceso = listarAdmin;
			}			
		}else if(action.equalsIgnoreCase("mostrar_producto")) {
			String codigo_producto;
			ProductoDAO pdao = new ProductoDAO();
			Producto prod;
			codigo_producto = request.getParameter("codigo_producto");
			prod = pdao.buscar_producto(Integer.parseInt(codigo_producto));
			request.setAttribute("producto", prod);
			acceso = mostrar_producto;
			
		}else if(action.equalsIgnoreCase("ActualizarStock")) {
			ProductoDAO pdao = new ProductoDAO();
			String codigo_producto = request.getParameter("codigo_producto");
			String cantidad = request.getParameter("cantidad");
			String precio = request.getParameter("precio");
			pdao.reponer_stock(Integer.parseInt(codigo_producto), Integer.parseInt(cantidad), Double.parseDouble(precio));
			acceso="indexAdmin.jsp";
		}else if(action.equalsIgnoreCase("BajaProducto")) {
			ProductoDAO pdao = new ProductoDAO();
			String codigo_producto_baja = request.getParameter("codigo_producto_baja");
			pdao.baja(Integer.parseInt(codigo_producto_baja));
			acceso = "ControladorProducto?accion=listar&codigo_filtro=0";
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("Agregar")) 
		{
			String nombre = request.getParameter("nombre");
			int categoria = Integer.parseInt(request.getParameter("codigo_categoria"));
			Part imagen = request.getPart("imagen");
			InputStream imagenInputStream = imagen.getInputStream();
			int stock = Integer.parseInt(request.getParameter("stock"));
			Double precio = Double.parseDouble(request.getParameter("precio"));
			if(Producto.es_valido(nombre, categoria, imagenInputStream, stock, precio)) 
			{
				prod.setNombre(nombre);
				prod.set_imagen(imagenInputStream);
				prod.setStock(stock);
				prod.setCodigo_categoria(categoria);
				prodDAO.alta(prod, precio);
				request.setAttribute("mensaje_ok_altaProducto","Alta realizada con éxito");
				acceso = "altaProducto.jsp";	
			}
			else 
			{
				request.setAttribute("mensaje_error_altaProducto","Error, revise los datos del alta e intente nuevamente");
				acceso = "altaProducto.jsp";
			}
		}else if(action.equalsIgnoreCase("EditarProducto")) {
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
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		}else if(action.equalsIgnoreCase("BuscarProductoEditar")) {
			String codigo_producto;
			ProductoDAO pdao = new ProductoDAO();
			codigo_producto = request.getParameter("codigo_producto");
			prod = pdao.buscar_producto(Integer.parseInt(codigo_producto));
			request.setAttribute("producto", prod);
			acceso="editarProducto.jsp";
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
		}
	}
}
