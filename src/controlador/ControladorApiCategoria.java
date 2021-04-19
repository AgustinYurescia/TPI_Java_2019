package controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import modelo.Categoria;

import services.ServicioCategoria;


/**
 * Servlet implementation class ControladorApiCategoria
 */
@WebServlet("/ControladorApiCategoria")
public class ControladorApiCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ServicioCategoria _servicioCategoria;
	Gson _gson;
	
    public ControladorApiCategoria() {
        super();
        _servicioCategoria = new ServicioCategoria();
        _gson = new Gson();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ArrayList<Categoria> categorias = _servicioCategoria.obtenerTodas();
			
			String res = _gson.toJson(categorias);
			SendSuccessResponse(res ,response);
			return;
		}catch(Exception ex){
			int responseCode = HttpServletResponse.SC_BAD_REQUEST;
			String json = "{ \"message\": \" "+ ex.getMessage() +" \",}";
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
			String responseMessage =_gson.toJson(jsonObject);
			SendErrorResponse(responseCode,responseMessage, response);
			return;
		}
	}

	private static void SendErrorResponse(int code, String mensaje,HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(code);
		ServletOutputStream out = response.getOutputStream();
		out.print(mensaje);
		out.flush();
	}
	
	private static void SendSuccessResponse(String mensaje,HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		out.print(mensaje);
		out.flush();
	}

}
