package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modeloDAO.ProductoDAO;

@WebServlet("/ControladorDeImagenes")
public class ControladorDeImagenes extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ControladorDeImagenes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		ProductoDAO proDAO = new ProductoDAO();
		proDAO.listar_imagen(codigo, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
