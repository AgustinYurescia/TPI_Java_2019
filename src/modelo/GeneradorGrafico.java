package modelo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public class GeneradorGrafico {
	
	public GeneradorGrafico() {}
	
	public void graficoBarrasVentasPorProducto(Map<String,Integer> data) throws Exception {
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		TreeMap<String,Integer> sort_data = new  TreeMap<String,Integer>(data); 
		for (String key : sort_data.keySet())
		{
			dataset.addValue(sort_data.get(key), key, key);
		}	
		JFreeChart chart = ChartFactory.createBarChart("GRÁFICO DE BARRAS", "Productos", "N° Ventas", dataset);
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0);
		File directory = new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        ImageIO.write(image, "png", new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images\\graficoVentas.png"));
    }
	
	public void graficoBarrasVentasPorMes(Map<Integer,Float> data) throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		Integer max_key = 1;
		for (Integer key : data.keySet())
		{
			dataset.addValue(data.get(key), "Mes " + key.toString(), key);
			if (key > max_key)
			{
				max_key = key;
			}
		}
		for(Integer i = max_key + 1; i <= 12; i++)
		{
			dataset.addValue(0.0, "Mes " + i.toString(), i);
		}
		
		JFreeChart chart = ChartFactory.createBarChart("GRÁFICO DE BARRAS", "Mes", "Total Ventas en $", dataset);

		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0);
		File directory = new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        ImageIO.write(image, "png", new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images\\graficoTotalVentasPorMes.png"));
    }
	
	public void graficoLinealVentasPorMes(Map<Integer,Float> data) throws Exception {
		XYSeries dataset = new XYSeries("Ventas");
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		for (Integer key : data.keySet())
		{
			dataset.add(key, data.get(key));
		}
		XYSeriesCollection xydataset = new XYSeriesCollection();
		xydataset.addSeries(dataset);

		JFreeChart chart = ChartFactory.createXYLineChart("GRÁFICO LINEAL", "Mes", "Total Ventas en $", xydataset,PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		plot.setRenderer(renderer);
		File directory = new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        ImageIO.write(image, "png", new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images\\graficoLinealVentas.png"));
    }
	
	public void graficoBarrasVentasPorAnio(Map<Integer,Float> data) throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		for (Integer key : data.keySet())
		{
			dataset.addValue(data.get(key), "Año " + key.toString(), key);
		}
		JFreeChart chart = ChartFactory.createBarChart("GRÁFICO DE BARRAS", "Año", "Total Ventas en $", dataset);
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0);
		File directory = new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        ImageIO.write(image, "png", new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images\\graficoTotalVentasAnual.png"));
    }
	
	public void graficoLinealVentasPorAnio(Map<Integer,Float> data) throws Exception {
		XYSeries dataset = new XYSeries("Ventas");
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		for (Integer key : data.keySet())
		{
			dataset.add(key, data.get(key));
		}
		XYSeriesCollection xydataset = new XYSeriesCollection();
		xydataset.addSeries(dataset);

		JFreeChart chart = ChartFactory.createXYLineChart("GRÁFICO LINEAL", "Año", "Total Ventas en $", xydataset,PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		plot.setRenderer(renderer);
		File directory = new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        ImageIO.write(image, "png", new File(System.getProperty("catalina.home") + "\\TPI_Java_2019\\WebContent\\Images\\graficoLinealVentasAnual.png"));
    }
}
