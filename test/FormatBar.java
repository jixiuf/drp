import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.ui.TextAnchor;

public class FormatBar {  
    /** 
     * 格式化纵向柱状图使用 
     *  
     * @param chart 
     * @returnType: void 
     * @author:  
     * @data: Nov 26, 2009 
     * @time: 11:51:26 AM 
     */  
    public static void setView(JFreeChart chart){  
//      初始化字体  
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 14);  
        Font noFont = new Font("SansSerif", Font.TRUETYPE_FONT, 48);  
//      格式化 图片    
        CategoryPlot plot = chart.getCategoryPlot();  
//        没有数据是显示的消息  
        plot.setNoDataMessage("没有数据！");  
////        没有数据时显示的消息字体  
        plot.setNoDataMessageFont(noFont);   
////        没有数据时显示的消息颜色  
        plot.setNoDataMessagePaint(Color.RED);    
//     // 数据轴精度   
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();   
//     // 数据轴数据标签的显示格式   
        CategoryAxis domainAxis = plot.getDomainAxis();   
//          
//     // 设置刻度必须从0开始   
        vn.setAutoRangeIncludesZero(true);  
////        设置纵坐标数据精度  
////        DecimalFormat df = new DecimalFormat("#0.00");   
////        vn.setNumberFormatOverride(df);  
//          
//          
//     x轴设置   
        domainAxis.setLabelFont(labelFont);// 轴标题   
        domainAxis.setTickLabelFont(labelFont);// 轴数值  
//     y轴设置   
        ValueAxis rangeAxis = plot.getRangeAxis();   
        rangeAxis.setLabelFont(labelFont);   
        rangeAxis.setTickLabelFont(labelFont);   
//        三维设置  
        BarRenderer3D renderer = new BarRenderer3D();   
//        // 设置柱子宽度   
        renderer.setMaximumBarWidth(0.05);   
//        // 设置柱子高度   
//        renderer.setMinimumBarLength(0.2);   
//        // 设置柱子边框颜色   
        renderer.setBaseOutlinePaint(Color.BLACK);   
//        // 设置距离图片左端距离   
        domainAxis.setLowerMargin(0.01);   
//        // 设置距离图片右端距离   
//        domainAxis.setUpperMargin(0.2);   
//          
//      // 设置显示位置  
////        plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);  
////        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);  
//  
//        plot.setDomainAxis(domainAxis);   
//        // 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）   
//        plot.setBackgroundPaint(new Color(255, 255, 204));   
          
          
//        //设置柱子上数值的字体  
//        renderer.setItemLabelFont(new Font("宋体",Font.PLAIN,14));   
//        renderer.setItemLabelsVisible(true);        
//        //设置柱子上数据的颜色  
//        renderer.setItemLabelPaint(Color.RED);  
          
//        指定分类的数据标签的字体  
        renderer.setSeriesItemLabelFont(3,labelFont);       
//        指定分类的数据标签的字体颜色  
        renderer.setSeriesItemLabelPaint(3,Color.RED);    
    
  
  
          
//          
          
//设置柱子上比例数值的显示，如果按照默认方式显示，数值为方向正常显示  
          
        //设置柱子上显示的数据旋转90度,最后一个参数为旋转的角度值/3.14  
          ItemLabelPosition itemLabelPosition= new ItemLabelPosition(  
          ItemLabelAnchor.INSIDE12,TextAnchor.CENTER_RIGHT,  
          TextAnchor.CENTER_RIGHT,-1.57D);  
          
        //下面的设置是为了解决，当柱子的比例过小，而导致表示该柱子比例的数值无法显示的问题  
            
        //设置不能在柱子上正常显示的那些数值的显示方式，将这些数值显示在柱子外面  
          ItemLabelPosition itemLabelPositionFallback=new ItemLabelPosition(  
          ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_LEFT,  
          TextAnchor.HALF_ASCENT_LEFT,-1.57D);  
          
        //设置正常显示的柱子label的position  
        renderer.setPositiveItemLabelPosition(itemLabelPosition);  
        renderer.setNegativeItemLabelPosition(itemLabelPosition);  
          
        //设置不能正常显示的柱子label的position  
        renderer.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);  
        renderer.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);  
        // 显示每个柱的数值，并修改该数值的字体属性   
        renderer.setIncludeBaseInRange(true);  
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());   
        renderer.setBaseItemLabelsVisible(true);   
          
          
      //以下设置，将按照指定格式，制定内容显示每个柱的数值。可以显示柱名称，所占百分比  
//        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",new DecimalFormat("0.0%")));      
  
          
          
//           // 横轴上的label旋转90 度  
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);    
  
          
//     // 设置每个平行柱之间距离   
        renderer.setItemMargin(0.05);   
//  
          
  
          
        plot.setRenderer(renderer);  
    }
    }  