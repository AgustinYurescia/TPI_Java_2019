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


@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String index="index.jsp";
	String listar="listarProductos.jsp";
	String alta="altaProducto.jsp";
	String baja="bajaProducto.jsp";
	String editar="editarProducto.jsp";
	Producto prod = new Producto();
	ProductoDAO prodDAO = new ProductoDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso="";
		String action=request.getParameter("accion");
		if(action.equalsIgnoreCase("Agregar")) {
			String nombre=request.getParameter("nombre");
			String categoria=request.getParameter("categoria");
			String descripcion=request.getParameter("descripcion");
			Double precioCosto=Double.parseDouble(request.getParameter("precioCosto"));
			int stock=Integer.parseInt(request.getParameter("stock"));
			prod.setNombre(nombre);
			prod.setCategoria(categoria);
			prod.setDescripcion(descripcion);
			prod.setPrecioCosto(precioCosto);
			prod.setPrecioVenta();
			prod.setStock(stock);
			prodDAO.alta(prod);
		}else if(action.equalsIgnoreCase("Listar")) {
			acceso=listar;
		}else if(action.equalsIgnoreCase("Index")) {
			acceso=index;
		}else if(action.equalsIgnoreCase("alta")) {
			acceso=alta;
		}
		
		RequestDispatcher vista = request.getRequestDispatcher(acceso);
		vista.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
