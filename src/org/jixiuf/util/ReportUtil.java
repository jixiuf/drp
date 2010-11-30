package org.jixiuf.util;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jixiuf.drp.dao.DistribDAO;
import org.jixiuf.drp.dao.DistribTypeDAO;
import org.jixiuf.drp.dao.RegionDAO;
import org.jixiuf.drp.pojo.DistribType;
import org.jixiuf.drp.pojo.Region;
import org.jixiuf.drp.pojo.User;
import org.springframework.stereotype.Component;

@Component("reportUtil")
public class ReportUtil {
	static Font f = null;
	DistribDAO distribDAO;
	DistribTypeDAO distribTypeDAO;
	RegionDAO regionDAO;

	public RegionDAO getRegionDAO() {
		return regionDAO;
	}

	@Resource(name = "regionDAO")
	public void setRegionDAO(RegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}

	public DistribTypeDAO getDistribTypeDAO() {
		return distribTypeDAO;
	}

	@Resource(name = "distribTypeDAO")
	public void setDistribTypeDAO(DistribTypeDAO distribTypeDAO) {
		this.distribTypeDAO = distribTypeDAO;
	}

	public DistribDAO getDistribDAO() {
		return distribDAO;
	}

	@Resource(name = "distribDAO")
	public void setDistribDAO(DistribDAO distribDAO) {
		this.distribDAO = distribDAO;
	}

	/**
	 * 获得此地区下所有分销商类型的分布情况， key=DistribType value=相应类形的分销商在此地区下的数目
	 *
	 * @param regionId
	 *            地区的id号，对-1 及null的情况作了特殊处理，指向根地区
	 * @return
	 */

	public Map<DistribType, Integer> getDistribTypeDataSet(Region region) {
		List<Region> regions = regionDAO
				.findAllDescendant(region.getId(), true);
		String ids[] = new String[regions.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = regions.get(i).getId();
		}
		regions = null;
		List<DistribType> distribTypes = distribTypeDAO.findAll();
		Map<DistribType, Integer> distribLevelData = new HashMap<DistribType, Integer>();
		for (DistribType type : distribTypes) {
			int count = distribDAO
					.findDistribsCountInRegionsWithType(ids, type);
			distribLevelData.put(type, count);
		}
		return distribLevelData;
	}

	public Font getDefaultFont() {
		return getDefaultFont(15);
	}

	public Font getDefaultFont(int size) {
		if (f == null) {
			f = new Font("文泉驿微米黑", Font.PLAIN, size);
		}
		if (f.getSize() != size) {
			f = new Font("文泉驿微米黑", Font.PLAIN, size);
		}
		return f;
	}

	/**
	 * 柱形图
	 *
	 * @param distribLevelData
	 * @param loginUser
	 * @param regionOfDesc
	 * @return
	 */
	public ByteArrayOutputStream distribLevelReportInBar(User loginUser,
			Region regionOfDesc) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Map<DistribType, Integer> distribLevelData = this
				.getDistribTypeDataSet(regionOfDesc);
		int max = 0;
		for (DistribType type : distribLevelData.keySet()) {
			int count = distribLevelData.get(type);
			if (count > max) {
				max = count;
			}
			dataset.addValue(count, type.getName(), regionOfDesc.getName());

		}
		// 如果还是0,说明 所有的数据没有起过0的,也就是说这个地区没有分销商
		if (max == 0) {
			dataset = new DefaultCategoryDataset();
		}
		JFreeChart jfreechart = ChartFactory.createBarChart(null, null, "数量",
				dataset, PlotOrientation.VERTICAL, false, true, false);
		// 设置标题及标题的字体
		jfreechart.setTitle(new TextTitle(regionOfDesc.getName()
				+ "地区 分销商级别分布图", getDefaultFont(18)));
		CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

		CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setBaseItemLabelsVisible(true);
		for (int i = 0; i < distribLevelData.size(); i++) {
			renderer.setSeriesItemLabelFont(i, getDefaultFont(13));
			renderer.setSeriesItemLabelGenerator(i,
					new StandardCategoryItemLabelGenerator("{0}{3}",
							NumberFormat.getNumberInstance(),
							new DecimalFormat("0%")));

		}

		CategoryAxis axis = plot.getDomainAxis();
		axis.setLabelFont(getDefaultFont());
		axis.setTickLabelFont(getDefaultFont());

		NumberAxis vAxis = (NumberAxis) plot.getRangeAxis();
		vAxis.setAutoRange(false);
		vAxis.setAutoRangeStickyZero(true);
		vAxis.setLabelFont(getDefaultFont());
		vAxis.setUpperBound(max == 0 ? 10 : max * 1.2);

		DecimalFormat df = new DecimalFormat("#0.0");
		vAxis.setNumberFormatOverride(df);

		// 设置当没有数据时的显示
		plot.setNoDataMessagePaint(Color.RED);
		plot.setNoDataMessageFont(getDefaultFont(20));
		plot.setNoDataMessage(regionOfDesc.getName() + "地区暂无分销商");

		// 设置标签的字体
		plot.setBackgroundPaint(new Color(199, 237, 204));
		// jfreechart.setBackgroundPaint(new Color(199, 237, 204));
		try {
			ChartUtilities.writeChartAsJPEG(out, jfreechart, 600, 400);
			// ChartUtilities.writeChartAsJPEG(new FileOutputStream(new File(
			// "/tmp/b.jpg")), jfreechart, 600, 400);
		} catch (IOException e) {
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out = null;
			e.printStackTrace();
		}

		return out;

	}

	// 将regionOfDesc 地区的分销商分布情况以图片（柱形图）的形式存在临时文件夹内的一个文件，返回此文件的引用
	public File distribLevelReportInBarFile(User loginUser, Region regionOfDesc) {
		ByteArrayOutputStream out = this.distribLevelReportInBar(loginUser,
				regionOfDesc);
		FileOutputStream fout = null;
		String uuidFileName = UUID.randomUUID().toString();
		File jpegFile = null;
		try {
			jpegFile = File.createTempFile(uuidFileName, ".jpg", new File(
					System.getProperty("java.io.tmpdir")));
			out.writeTo(fout = new FileOutputStream(jpegFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				fout.flush();
				out.close();

				fout.close();
				out = null;
				fout = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return jpegFile;

	}

	// 将regionOfDesc 地区的分销商分布情况以图片（饼图）的形式存在临时文件夹内的一个文件，返回此文件的引用
	public File distribLevelReportInPieFile(User loginUser, Region regionOfDesc) {
		ByteArrayOutputStream out = this.distribLevelReportInPie(loginUser,
				regionOfDesc);
		String uuidFileName = UUID.randomUUID().toString();
		FileOutputStream fout=null;
		File jpegFile = null;
		try {
			jpegFile = File.createTempFile(uuidFileName, ".jpg", new File(
					System.getProperty("java.io.tmpdir")));
			out.writeTo(fout=new FileOutputStream(jpegFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				fout.flush();
				out.close();
				fout.close();
				fout=null;
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return jpegFile;

	}

	public ByteArrayOutputStream distribLevelReportInPie(User loginUser,
			Region regionOfDesc) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		Map<DistribType, Integer> distribLevelData = this
				.getDistribTypeDataSet(regionOfDesc);

		for (DistribType d : distribLevelData.keySet()) {
			defaultpiedataset.setValue(d.getName(), new Double(distribLevelData
					.get(d)));

		}
		JFreeChart jfreechart = ChartFactory.createPieChart3D(null,
				defaultpiedataset, false, true, false);
		// 设置标题及标题的字体
		jfreechart.setTitle(new TextTitle(regionOfDesc.getName()
				+ "地区 分销商级别分布图", getDefaultFont(18)));

		PiePlot plot = (PiePlot) jfreechart.getPlot();
		// 设置当没有数据时的显示
		plot.setNoDataMessage(regionOfDesc.getName() + "地区暂无分销商");
		plot.setNoDataMessageFont(getDefaultFont());
		// 设置标签的字体
		plot.setLabelFont(getDefaultFont());
		plot.setCircular(true);
		plot.setLabelGap(0.02D);
		plot.setBackgroundPaint(new Color(199, 237, 204));
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}\n 数量:{1}\n{2}", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}  数量:{1}  {2}"));

		BlockContainer blockcontainer = new BlockContainer(
				new BorderArrangement());
		blockcontainer.setFrame(new BlockBorder(1.0D, 1.0D, 1.0D, 1.0D));
		LabelBlock titleLabel = new LabelBlock(regionOfDesc.getName()
				+ "地区\n分销商级别分布表", getDefaultFont(18));
		titleLabel.setPadding(5D, 5D, 5D, 5D);
		blockcontainer.add(titleLabel, RectangleEdge.TOP);
		LabelBlock userInfo = new LabelBlock("by " + loginUser.getName() + "  "
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		userInfo.setPadding(8D, 20D, 2D, 5D);
		blockcontainer.add(userInfo, RectangleEdge.BOTTOM);
		LegendTitle legendtitle = new LegendTitle(plot);
		BlockContainer blockcontainer1 = legendtitle.getItemContainer();
		blockcontainer1.setPadding(2D, 10D, 5D, 2D);
		blockcontainer.add(blockcontainer1);
		legendtitle.setWrapper(blockcontainer);
		legendtitle.setItemFont(getDefaultFont());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		legendtitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
		jfreechart.addSubtitle(legendtitle);
		jfreechart.setBackgroundPaint(new Color(199, 237, 204));
		try {
			ChartUtilities.writeChartAsJPEG(out, jfreechart, 600, 400);
			out.flush();
		} catch (IOException e) {
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out = null;
			e.printStackTrace();
		}

		return out;

	}
}
