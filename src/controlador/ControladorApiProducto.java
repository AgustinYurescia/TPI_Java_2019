package controlador;

import java.io.IOException;
import java.io.PrintWriter;
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

import Validators.ValidatorProducto;
import modelo.Producto;
import modeloDAO.ProductoDAO;
import services.ServicioCategoria;
import services.ServicioProducto;

@WebServlet("/ControladorApiProducto")
public class ControladorApiProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Producto prod = new Producto();
	ProductoDAO prodDAO = new ProductoDAO();
	ServicioProducto _servicioProducto;
	ServicioCategoria _servicioCategoria;
	Gson _gson;
   

    public ControladorApiProducto() {
        super();
        _servicioProducto = new ServicioProducto();
        _servicioCategoria =  new ServicioCategoria();
        _gson = new Gson();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acceso = "";
		String action = request.getParameter("accion");
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		if(action.equalsIgnoreCase("ListarPorPaginas")) {
			if(ValidatorProducto.ValidarListarPorPaginas(request.getParameter("numero_por_pagina"),request.getParameter("numero_pagina"),request.getParameter("codigo_categoria"))) {
				
				int numeroPorPagina = Integer.parseInt(request.getParameter("numero_por_pagina"));
				int numeroPagina = Integer.parseInt(request.getParameter("numero_pagina"));
				int codigoCategoria = Integer.parseInt(request.getParameter("codigo_categoria"));
				try {
					ArrayList<Producto> productos = _servicioProducto.ObtenerPorPagina(numeroPorPagina, numeroPagina, codigoCategoria);
					String res = _gson.toJson(productos);
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
			}else {
				int responseCode = HttpServletResponse.SC_BAD_REQUEST;
				String json = "{ \"message\": \"alguno de los parametros tiene un valor no valido\"}";
				JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
				String responseMessage =_gson.toJson(jsonObject);
				SendErrorResponse(responseCode,responseMessage, response);
				return;
			}
		}
		else {
			int responseCode = HttpServletResponse.SC_BAD_REQUEST;
			String json = "{ \"message\": \"Request no valida\"}";
			SendErrorResponse(responseCode, json, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private static void SendErrorResponse(int code, String mensaje,HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(code);
		ServletOutputStream out = response.getOutputStream();
//		PrintWriter out = response.getWriter();
		out.print(mensaje);
		out.flush();
	}
	
	private static void SendSuccessResponse(String mensaje,HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json;charset=UTF-8");
//		PrintWriter out = response.getWriter();
		ServletOutputStream out = response.getOutputStream();
		out.print(mensaje);
		out.flush();
	}

}
