import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class t extends ApplicationFrame {

public t(String s) {

  super(s); JPanel jpanel = createDemoPanel();

jpanel.setPreferredSize(new Dimension(500, 270)); setContentPane(jpanel);

}

  private static CategoryDataset createDataset() {

String s = "每日PV";

  String s1 = "每日IP数"; String s2 = "注册用户数";

String s3 = "A网站";

String s4 = "B网站";

  String s5 = "C网站";

String s6 = "D网站";

  String s7 = "E网站";

DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

defaultcategorydataset.addValue(1.0D, s, s3); defaultcategorydataset.addValue(4D, s, s4);

defaultcategorydataset.addValue(3D, s, s5); defaultcategorydataset.addValue(5D, s, s6);

defaultcategorydataset.addValue(5D, s, s7); defaultcategorydataset.addValue(5D, s1, s3);

defaultcategorydataset.addValue(7D, s1, s4); defaultcategorydataset.addValue(6D, s1, s5);

  defaultcategorydataset.addValue(8D, s1, s6); defaultcategorydataset.addValue(4D, s1, s7);

defaultcategorydataset.addValue(4D, s2, s3); defaultcategorydataset.addValue(3D, s2, s4);

  defaultcategorydataset.addValue(2D, s2, s5); defaultcategorydataset.addValue(3D, s2, s6);

  defaultcategorydataset.addValue(6D, s2, s7); return defaultcategorydataset;

  }

  private static JFreeChart createChart(CategoryDataset categorydataset) {

JFreeChart jfreechart = ChartFactory.createBarChart("Bar Chart Demo 1", "网站", "数值",

  categorydataset, PlotOrientation.VERTICAL, true, true, false);

CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();

  categoryplot.setDomainGridlinesVisible(true);

categoryplot.setRangeCrosshairVisible(true);

  categoryplot.setRangeCrosshairPaint(Color.blue);

NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();

numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();

  barrenderer.setBaseItemLabelFont(new Font("宋体", Font.PLAIN, 12));

barrenderer.setSeriesItemLabelFont(1, new Font("宋体", Font.PLAIN, 12));

CategoryAxis domainAxis = categoryplot.getDomainAxis();



  domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));

 

domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));



numberaxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));



numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

 

  jfreechart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

  barrenderer.setDrawBarOutline(false);

GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(  0, 0, 64));

GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F,

  new Color(0, 64, 0)); GradientPaint gradientpaint2 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(

  64, 0, 0)); barrenderer.setSeriesPaint(0, gradientpaint);

barrenderer.setSeriesPaint(1, gradientpaint1);

barrenderer.setSeriesPaint(2, gradientpaint2);

  barrenderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator( "Tooltip: {0}"));

CategoryAxis categoryaxis = categoryplot.getDomainAxis(); categoryaxis.setCategoryLabelPositions(CategoryLabelPositions  .createUpRotationLabelPositions(0.52359877559829882D)); return jfreechart;

  }

  public static JPanel createDemoPanel() {

JFreeChart jfreechart = createChart(createDataset());

  return new ChartPanel(jfreechart); }

  public static void main(String args[]) {

  t barchartdemo1 = new t("黑体JFreeChart: BarChartDemo1.java"); barchartdemo1.pack();

  RefineryUtilities.centerFrameOnScreen(barchartdemo1); barchartdemo1.setVisible(true);

   } }