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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.axis.NumberAxis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.text.DecimalFormat;
import java.util.stream.Collectors;


public class GeneradorGrafico {
	
	public GeneradorGrafico() {}
	
	public String getBase64(BufferedImage image) throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(image, "png", output);
		String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
		return imageAsBase64;
	}
	
	public String graficoBarrasVentasPorProducto(Map<String,Integer> data) throws Exception {
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// Ordenar el mapa por valores en orden descendente
		Map<String, Integer> sortedData = data.entrySet()
				.stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
				.collect(Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue,
					(e1, e2) -> e1,
					LinkedHashMap::new
				));
		
		// Añadir los datos ordenados al dataset
		for (String key : sortedData.keySet()) {
			dataset.addValue(sortedData.get(key), key, key);
		}

		JFreeChart chart = ChartFactory.createBarChart("GRÁFICO DE BARRAS", "Productos", "N° Ventas", dataset);
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0);
		chart.setBackgroundPaint(trans);
		chart.getLegend().setBackgroundPaint(trans);
		plot.setBackgroundPaint(trans);
		plot.setRangeGridlinePaint(Color.black);
		
		BufferedImage image = chart.createBufferedImage(625, 400);
		return getBase64(image);
    }
	
	public String graficoBarrasVentasPorMes(Map<Integer,Float> data) throws Exception {
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
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        return getBase64(image);
    }
	
	public String graficoLinealVentasPorMes(Map<Integer,Float> data) throws Exception {
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
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        return getBase64(image);
    }
	
	public String graficoBarrasVentasPorAnio(Map<Integer,Float> data) throws Exception {
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
	    chart.setBackgroundPaint(trans);
	    chart.getLegend().setBackgroundPaint(trans);
	    plot.setBackgroundPaint(trans);
	    plot.setRangeGridlinePaint(Color.black);
        BufferedImage image = chart.createBufferedImage(625, 400);
        return getBase64(image);
    }
	
	public String graficoLinealVentasPorAnio(Map<Integer, Float> data) throws Exception {
		XYSeries dataset = new XYSeries("Ventas");
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
	
		// Agregar datos al dataset
		for (Integer key : data.keySet()) {
			dataset.add(key, data.get(key));
		}
		XYSeriesCollection xydataset = new XYSeriesCollection();
		xydataset.addSeries(dataset);
	
		JFreeChart chart = ChartFactory.createXYLineChart(
			"GRÁFICO LINEAL", "Año", "Total Ventas en $", 
			xydataset, PlotOrientation.VERTICAL, 
			true, true, false
		);
	
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		plot.setRenderer(renderer);
	
		// Configurar el eje X para mostrar números enteros sin decimales
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		xAxis.setNumberFormatOverride(new DecimalFormat("####")); // Formato sin decimales
		xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	
		// Configuraciones de estilo
		chart.setBackgroundPaint(trans);
		chart.getLegend().setBackgroundPaint(trans);
		plot.setBackgroundPaint(trans);
		plot.setRangeGridlinePaint(Color.black);
	
		BufferedImage image = chart.createBufferedImage(625, 400);
		return getBase64(image);
	}
}
