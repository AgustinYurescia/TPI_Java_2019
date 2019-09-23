package controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Producto;
import modeloDAO.ProductoDAO;
import modeloDAO.CategoriaDAO;


@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String index = "index.jsp";
	String listar = "listarProductos.jsp";
	String alta = "altaProducto.jsp";
	Producto prod = new Producto();
	ProductoDAO prodDAO = new ProductoDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		if(action.equalsIgnoreCase("Agregar")) {
			String nombre = request.getParameter("nombre");
			int categoria = Integer.parseInt(request.getParameter("codigo_categoria"));
			String url_imagen = request.getParameter("url_imagen");
			int stock = Integer.parseInt(request.getParameter("stock"));
			String cuil_proveedor = request.getParameter("cuil_proveedor");
			Double precio = Double.parseDouble(request.getParameter("precio"));
			prod.setNombre(nombre);
			prod.setUrl_imagen(url_imagen);
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
		}else if(action.equalsIgnoreCase("index")) {
			acceso = index;
		}else if(action.equalsIgnoreCase("alta")) {
			acceso = alta;
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}
