package controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Cliente;
import modelo.Cuota;
import modelo.ExportadorPDF;
import modelo.Pedido;
import modeloDAO.ClienteDAO;
import modeloDAO.PedidoDAO;
import services.CustomerService;
import services.ServicioCuota;
import services.ServicioPedido;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

@WebServlet("/ControladorPDF")
public class ControladorPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	private static Logger _logger = LogManager.getLogger(ControladorPDF.class);
	CustomerService _servicioCliente;
	ServicioPedido _servicioPedido;;
	ServicioCuota _servicioCuota;

    public ControladorPDF() {
    	super();
    	this._servicioCliente = new CustomerService();
    	this._servicioPedido = new ServicioPedido();
    	this._servicioCuota = new ServicioCuota();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
		HttpSession sesion = request.getSession(true);
		if(action.equalsIgnoreCase("exportarSociosPdf")) 
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try {
					ArrayList<Cliente> socios = _servicioCliente.ObtenerSocios();
					DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
					String file_date = dateformat.format(new Date());
					String content =  "Content-Disposition";
					String filename = "attachment; filename=Socios_"+file_date+".pdf";
					response.setHeader(content, filename);
					ExportadorPDF exp = new ExportadorPDF(socios, null, null);
					exp.export(response, "listadoSocios");
				} catch (Exception e) {
					_logger.info(e.getMessage());
				}
			}
			else 
			{
				acceso = "loginAdmin.jsp";	
				RequestDispatcher vista = request.getRequestDispatcher(acceso);
				vista.forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("exportarPedidoPdf")) 
		{
			if (sesion.getAttribute("usuario_admin") != null)
			{
				try {
					String nro_pedido = request.getParameter("nro_pedido");
					Pedido pedido = _servicioPedido.BuscarPedidoConProductos(Integer.parseInt(nro_pedido));
					DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
					String file_date = dateformat.format(new Date());
					String content =  "Content-Disposition";
					String filename = "attachment; filename=PedidoN°"+pedido.getNro_pedido()+"_"+file_date+".pdf";
					response.setHeader(content, filename);
					ExportadorPDF exp = new ExportadorPDF(null, pedido, null);
					exp.export(response, "pedido");
				} catch (Exception e) {
					_logger.info(e.getMessage());
					System.out.println(e.getLocalizedMessage());
				}
			}
			else 
			{
				acceso = "loginAdmin.jsp";	
				RequestDispatcher vista = request.getRequestDispatcher(acceso);
				vista.forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("exportarCuotasPdf")) 
		{
			if (sesion.getAttribute("usuario_cliente") != null)
			{
				try {
					Cliente cliente = _servicioCliente.ObtenerPorNombreDeUsuario((String)sesion.getAttribute("usuario_cliente"));
					ArrayList<Cuota> cuotas = _servicioCuota.ObtenerCuotasImpagas(cliente.getDni());
					DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
					String file_date = dateformat.format(new Date());
					String content =  "Content-Disposition";
					String filename = "attachment; filename=MisCuotas_"+file_date+".pdf";
					response.setHeader(content, filename);
					ExportadorPDF exp = new ExportadorPDF(null, null, cuotas);
					exp.export(response, "cuotas");
				} catch (Exception e) {
					_logger.info(e.getMessage());
				}
			}
			else 
			{
				acceso = "loginClientes.jsp";	
				RequestDispatcher vista = request.getRequestDispatcher(acceso);
				vista.forward(request, response);
			}
		}
	}
}