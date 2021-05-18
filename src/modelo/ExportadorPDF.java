package modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import modeloDAO.ProductoDAO;
import services.CustomerService;

public class ExportadorPDF 
{
	
	private ArrayList<Cliente> socios;
	private Pedido pedido;
	private Color color_fondo;
	private Color color_letra;
	private CustomerService _servicioCliente;
	private ArrayList<Cuota> cuotas;
	
	public ExportadorPDF() 
	{
		
	}
	
	public ExportadorPDF(ArrayList<Cliente> socios, Pedido pedido, ArrayList<Cuota> cuotas)
	{
		this.socios = socios;
		this.pedido = pedido;
		this.cuotas = cuotas;
		this._servicioCliente = new CustomerService();
		color_fondo = new Color(249,234,199);
		color_letra =  new Color(88,39,45);
	}
	
	/*
	public void writeHeader(PdfPTable pdfTable) throws BadElementException, IOException
	{
		Image img = Image.getInstance("D:\\Documentos\\Eclipse\\Workspace_02\\TPI_Java_2019\\WebContent\\Images\\logo.png");
		PdfPCell cell = new PdfPCell();
		cell.setBorderColor(color_letra);
		cell.setBackgroundColor(color_fondo);
		cell.setPadding(5);
		cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		font.setColor(color_letra);
		cell.setImage(img);
		pdfTable.addCell(cell);
	}
	*/
	
	public void writeTableHeader(PdfPTable pdfTable, String type) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String string_date = sdf.format(new Date());
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		Font title_font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		Font info_font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		Font date_font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		title_font.setSize(20);
		title_font.setColor(color_letra);
		date_font.setColor(color_letra);
		info_font.setColor(color_letra);
		font.setColor(color_letra);
		System.setProperty("http.agent", "Chrome");
        String url = "https://i.imgur.com/A6ZahSa.png";
		Image img = Image.getInstance(url);
		Phrase info = new Phrase("\nVinoteca El Viejo Tonel\nRoque S. Pe�a, Pujato, Sta Fe\n3464 44-1296 ", info_font);
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setBorderColor(color_letra);
		cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
		cell.setPadding(5);
		PdfPCell span_cell_img = new PdfPCell();
		span_cell_img.setImage(img);
		span_cell_img.setBorder(0);
		PdfPCell span_cell_title = new PdfPCell();
		span_cell_title.setBorder(0);
		span_cell_title.setHorizontalAlignment(Phrase.ALIGN_CENTER);
		PdfPCell null_cell = new PdfPCell();
		null_cell.setBorder(0);
		PdfPCell span_info_cell = new PdfPCell();
		span_info_cell.setHorizontalAlignment(Phrase.ALIGN_RIGHT);
		span_info_cell.setBorder(0);
		PdfPCell date_info_cell =  new PdfPCell();
		date_info_cell.setBorder(0);
		
		if (type.equalsIgnoreCase("pedido"))
		{
			Cliente cli = _servicioCliente.Buscar(pedido.getDni_cliente());
			span_cell_img.setColspan(2);
			date_info_cell.setColspan(5);
			span_cell_title.setColspan(5);
			span_info_cell.setColspan(3);
			date_info_cell.setPaddingLeft(25);
			Phrase title = new Phrase("Pedido N�"+pedido.getNro_pedido()+"\n ", title_font);
			Phrase date_info = new Phrase("DNI: "+pedido.getDni_cliente()+"\nNombre: "+cli.getNombre()+" "+cli.getApellido()+"\nDirecci�n: "+cli.getDireccion()+"\nTel�fono : "+cli.getTelefono()+"\nEmail: "+cli.getMail()+"\n\nFecha entrega estimada: "+pedido.getFecha_entrega_est()+"\n ", date_font);
			date_info_cell.setPhrase(date_info);
			span_cell_title.setPhrase(title);
		}
		else if (type.equalsIgnoreCase("listadoSocios"))
		{
			span_cell_img.setColspan(2);
			date_info_cell.setColspan(5);
			span_cell_title.setColspan(5);
			span_info_cell.setColspan(3);
			date_info_cell.setPaddingLeft(25);
			Phrase title = new Phrase("LISTADO DE SOCIOS \n ", title_font);
			Phrase date_info = new Phrase("Fecha de informe: "+string_date, date_font);
			date_info_cell.setPhrase(date_info);
			span_cell_title.setPhrase(title);
		}
		else if (type.equalsIgnoreCase("cuotas"))
		{
			date_info_cell.setColspan(3);
			span_cell_title.setColspan(3);
			span_info_cell.setColspan(3);
			Phrase title = new Phrase("CUOTAS A�O "+cuotas.get(0).getAnio()+"\n ", title_font);
			Phrase date_info = new Phrase("Fecha de informe: " + string_date + "\n ", date_font);
			date_info_cell.setPhrase(date_info);
			span_cell_title.setPhrase(title);
		}
		
		span_info_cell.setPhrase(info);
		pdfTable.addCell(span_cell_img);
		pdfTable.addCell(span_info_cell);
		pdfTable.addCell(span_cell_title);
		pdfTable.addCell(date_info_cell);
		
		if (type.equalsIgnoreCase("listadoSocios"))
		{
			cell.setPhrase(new Phrase("DNI", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("NOMBRE", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("APELLIDO", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("MAIL", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("TELEFONO", font));
			pdfTable.addCell(cell);
		}
		if (type.equalsIgnoreCase("pedido"))
		{
			cell.setPhrase(new Phrase("CODIGO PRODUCTO", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("NOMBRE", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("CANTIDAD", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("PRECIO", font));
			pdfTable.addCell(cell);
			cell.setPhrase(new Phrase("SUBTOTAL", font));
			pdfTable.addCell(cell);
		}
	}
	
	public void writeTableData(PdfPTable pdfTable, String type)
	{
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
		Font font = FontFactory.getFont(FontFactory.COURIER);
		font.setColor(color_letra);
		if (type.equalsIgnoreCase("listadoSocios"))
		{
			for (Cliente s : socios)
			{
				cell.setPhrase(new Phrase(s.getDni(), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(s.getNombre(), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(s.getApellido(), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(s.getMail(), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(s.getTelefono(), font));
				pdfTable.addCell(cell);
			}
		}
		else if(type.equalsIgnoreCase("pedido"))
		{
			for (LineaPedido l: pedido.getProductos())
			{
				Producto producto = l.getProducto(l.getCodigo_producto());
				cell.setPhrase(new Phrase(String.valueOf(l.getCodigo_producto()), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(producto.getNombre(), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(l.getCantidad()), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(producto.getPrecioVenta()), font));
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase(String.valueOf(l.getCantidad() * producto.getPrecioVenta()), font));
				pdfTable.addCell(cell);
			}
			
			Font total_font = FontFactory.getFont(FontFactory.COURIER_BOLD);
			total_font.setSize(15);
			total_font.setColor(color_letra);
			PdfPCell total_cell = new PdfPCell();
			total_cell.setBorder(0);
			total_cell.setHorizontalAlignment(Phrase.ALIGN_RIGHT);
			total_cell.setColspan(5);
			total_cell.setPhrase(new Phrase("Total: $"+String.valueOf(pedido.getMonto()), total_font));
			total_cell.setPaddingRight(25);
			pdfTable.addCell(total_cell);
		}
		else if(type.equalsIgnoreCase("cuotas"))
		{
			cell.setColspan(3);
			cell.setPaddingLeft(5);
			cell.setPaddingRight(5);
			cell.setPaddingBottom(5);
			cell.setBorderColor(color_letra);
			for (Cuota c: cuotas)
			{
				cell.setPhrase(new Phrase("Cuota: " + String.valueOf(c.getMes()) + "/" + String.valueOf(c.getAnio()), font));
				cell.setHorizontalAlignment(Phrase.ALIGN_LEFT);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase("Fecha de vencimiento: 10/" + String.valueOf(c.getMes()) + "/" + String.valueOf(c.getAnio()), font));
				cell.setHorizontalAlignment(Phrase.ALIGN_LEFT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				pdfTable.addCell(cell);
				if (c.getFechaPago() != null)
				{
					cell.setPhrase(new Phrase("Fecha de pago: " + String.valueOf(c.getFechaPago()), font));
				}
				else
				{
					cell.setPhrase(new Phrase("Fecha de pago: -", font));
				}
				cell.setHorizontalAlignment(Phrase.ALIGN_LEFT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				pdfTable.addCell(cell);
				cell.setPhrase(new Phrase("Total: $" + String.valueOf(c.getValor()), font));
				cell.setHorizontalAlignment(Phrase.ALIGN_RIGHT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(1);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthRight(1);
				pdfTable.addCell(cell);
				cell.setBorder(0);
				cell.setPhrase(new Phrase(" ", font));
				pdfTable.addCell(cell);
			}
		}
	}
	
	public void export(HttpServletResponse response, String type) throws Exception
	{
		try (Document doc = new Document(PageSize.A4))
		{
			PdfWriter.getInstance(doc, response.getOutputStream());
			doc.open();
			if (type.equalsIgnoreCase("listadoSocios"))
			{
				PdfPTable pdfPTable = new PdfPTable(5);
				pdfPTable.setWidthPercentage(100f);
				pdfPTable.setWidths(new float []{10.0f, 10.0f, 10.0f, 10.0f, 10.0f});
				pdfPTable.setSpacingBefore(10);
				writeTableHeader(pdfPTable, "listadoSocios");
				writeTableData(pdfPTable, "listadoSocios");
				doc.add(pdfPTable);
			}
			else if (type.equalsIgnoreCase("pedido"))
			{
				PdfPTable pdfPTable = new PdfPTable(5);
				pdfPTable.setWidthPercentage(100f);
				pdfPTable.setWidths(new float []{10.0f, 10.0f, 10.0f, 10.0f, 10.0f});
				pdfPTable.setSpacingBefore(10);
				writeTableHeader(pdfPTable, "pedido");
				writeTableData(pdfPTable, "pedido");
				doc.add(pdfPTable);
			}
			else if (type.equalsIgnoreCase("cuotas"))
			{
				PdfPTable pdfPTable = new PdfPTable(3);
				pdfPTable.setWidthPercentage(100f);
				pdfPTable.setWidths(new float []{10.0f, 10.0f, 10.0f});
				pdfPTable.setSpacingBefore(10);
				writeTableHeader(pdfPTable, "cuotas");
				writeTableData(pdfPTable, "cuotas");
				doc.add(pdfPTable);
			}
			doc.close();
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
}
