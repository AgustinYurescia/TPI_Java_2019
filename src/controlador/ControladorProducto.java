package controlador;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		if(action.equalsIgnoreCase("Agregar")) {
			String nombre = request.getParameter("nombre");
			int categoria = Integer.parseInt(request.getParameter("codigo_categoria"));
			Part imagen = request.getPart("imagen");
			InputStream imagenInputStream = imagen.getInputStream();
			int stock = Integer.parseInt(request.getParameter("stock"));
			String cuil_proveedor = request.getParameter("cuil_proveedor");
			Double precio = Double.parseDouble(request.getParameter("precio"));
			prod.setNombre(nombre);
			prod.set_imagen(imagenInputStream);
			prod.setStock(stock);
			prod.setCodigo_categoria(categoria);
			prodDAO.alta(prod, cuil_proveedor, precio);
			
		}else if(action.equalsIgnoreCase("listar")) {
			CategoriaDAO catDAO = new CategoriaDAO();
			String nombre_filtro = request.getParameter("filtrar_por");
			if (nombre_filtro.equalsIgnoreCase("TODOS")){
				request.setAttribute("filtro", "TODOS");
			}else {
				int codigo_filtro = catDAO.getCodigoCategoria(nombre_filtro);
				if (codigo_filtro != 0) {
					request.setAttribute("filtro", Integer.toString(codigo_filtro));
				}
			}
			acceso = listar;
			
		}else if(action.equalsIgnoreCase("listarAdmin")) {
			CategoriaDAO catDAO = new CategoriaDAO();
			String nombre_filtro = request.getParameter("filtrar_por");
			if (nombre_filtro.equalsIgnoreCase("TODOS")){
				request.setAttribute("filtro", "TODOS");
			}else {
				int codigo_filtro = catDAO.getCodigoCategoria(nombre_filtro);
				if (codigo_filtro != 0) {
					request.setAttribute("filtro", Integer.toString(codigo_filtro));
				}
			}
			acceso = listarAdmin;
			
		}else if(action.equalsIgnoreCase("mostrar_producto")) {
			String codigo_producto;
			ProductoDAO pdao = new ProductoDAO();
			Producto prod;
			codigo_producto = request.getParameter("codigo_producto");
			prod = pdao.buscar_producto(Integer.parseInt(codigo_producto));
			request.setAttribute("producto", prod);
			acceso = mostrar_producto;
			
		}else if(action.equalsIgnoreCase("ActualizarStock")) {
			String codigo_producto;
			String cantidad;
			ProductoDAO pdao = new ProductoDAO();
			codigo_producto = request.getParameter("codigo_producto");
			cantidad = request.getParameter("cantidad");
			pdao.actualizar_stock(Integer.parseInt(codigo_producto), Integer.parseInt(cantidad));
			acceso="indexAdmin.jsp";
		}else if(action.equalsIgnoreCase("EditarProducto")) {
			String codigo_producto;
			String nuevo_nombre;
			Part imagen = request.getPart("nueva_imagen");
			InputStream nuevaImagenInputStream = imagen.getInputStream();
			ProductoDAO pdao = new ProductoDAO();
			codigo_producto = request.getParameter("codigo_producto");
			nuevo_nombre = request.getParameter("nuevo_nombre");
			pdao.editar_producto(Integer.parseInt(codigo_producto), nuevo_nombre,nuevaImagenInputStream);
			acceso="indexAdmin.jsp";
		}else if(action.equalsIgnoreCase("BajaProducto")) {
			ProductoDAO pdao = new ProductoDAO();
			String codigo_producto_baja = request.getParameter("codigo_producto_baja");
			pdao.baja(Integer.parseInt(codigo_producto_baja));
			request.setAttribute("filtro", "TODOS");
			acceso = listarAdmin;
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}
