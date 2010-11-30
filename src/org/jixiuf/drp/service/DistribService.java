package org.jixiuf.drp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.DistribDAO;
import org.jixiuf.drp.dao.DistribTypeDAO;
import org.jixiuf.drp.dao.RegionDAO;
import org.jixiuf.drp.pojo.AbstractClient;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.DistribType;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.Region;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.util.ReportUtil;
import org.jixiuf.util.WordBean;
import org.springframework.stereotype.Component;

@Component("distribService")
public class DistribService {
	DistribDAO dao;
	DistribTypeDAO distribTypeDAO;
	RegionDAO regionDAO;
	ReportUtil reportUtil;

	public ReportUtil getReportUtil() {
		return reportUtil;
	}

	@Resource(name = "reportUtil")
	public void setReportUtil(ReportUtil reportUtil) {
		this.reportUtil = reportUtil;
	}

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

	public DistribDAO getDao() {
		return dao;
	}

	@Resource(name = "distribDAO")
	public void setDao(DistribDAO dao) {
		this.dao = dao;
	}

	/**
	 * 返回所有分销商()
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Distrib> findAllDistrib() {

		return dao.findAllDistrib();

	}

	/*
	 * 返回所有终端用户
	 */
	public List<AbstractClient> findAllTerminal() {
		List<AbstractClient> distributions = null;
		return distributions;

	}

	public Distrib findById(String id) {
		return dao.findById(id);
	}

	/**
	 * 返回所有的分销商级别（类型）
	 *
	 * @return
	 */
	public List<DistribType> findAllDistriType() {
		return distribTypeDAO.findAll();

	}

	public String addDistrib(Distrib distrib) {
		List<Distrib> sameClientNos = dao.findByClientno(distrib.getClientno());
		/**
		 * 如果 相同 clientno 的Client数量大于1 ，或者等于1但并非client 本身
		 */
		if (sameClientNos.size() > 1
				|| (sameClientNos.size() == 1 && !sameClientNos.get(0).getId()
						.equals(distrib.getId()))) {
			return "请换一个分销商代码，此码已被使用";
		}

		Region region = regionDAO.findById(distrib.getRegion().getId());

		if (region == null) {
			return "此分销商所属的地区不存在，可能是其他人在别处将此地区删除了,出现了同步问题！！";
		}
		DistribType distribType = distribTypeDAO.findById(distrib
				.getDistribType().getId());

		if (distribType == null) {
			return "数据出现同步问题，分销商类型（级别）不存在";

		}
		distrib.setRegion(region);
		distrib.setDistribType(distribType);
		dao.save(distrib);
		return "成功添加分销商！！！";

	}

	public String modDistrib(Distrib distrib) {
		dao.merge(distrib);
		return "修改成功!!!";
		// dao.attachDirty(client);

	}

	public String delDistrib(Distrib distrib) {
		distrib = dao.findById(distrib.getId());
		if (distrib == null) {
			return "分销商不存在无从删起!!!";
		}
		try {
			dao.delete(distrib);
		} catch (Exception e) {
			return "此分销商在别处被引用，不能删除！！！";
		}
		return "分销商已删除!!!";
	}

	public List<Distrib> searchDistrib(String nameOrClientNo, PageModel pm) {
		return dao.searchAllDistribs(nameOrClientNo, pm);

	}

	/**
	 * 查询所有分销商
	 *
	 * @param pm
	 * @return
	 */
	public List<Distrib> findAllDistrib(PageModel pm) {
		return dao.findAllDistrib(pm);

	}

	public List<AbstractClient> findAllClients(PageModel pm) {
		return dao.findAllClients(pm);
	}

	public List<AbstractClient> searchAllClients(String searchString,
			PageModel pm) {

		return dao.searchAllClients(searchString, pm);
	}

	/**
	 *
	 * @param regionId
	 *            将所有属于此地区的分销商进行统计(包括子地区的分销商), key =分销商级别 ,value 为分销商的数量
	 */
	public ByteArrayOutputStream reportDistribLevelInPie(String regionId,
			User loginedUser) {
		Region region = regionDAO.findById(regionId);

		ByteArrayOutputStream out = reportUtil.distribLevelReportInPie(
				loginedUser, region);

		return out;

	}

	public ByteArrayOutputStream reportDistribLevelInBar(String regionId,
			User user) {
		Region regionToDesc = regionDAO.findById(regionId);
		ByteArrayOutputStream out = reportUtil.distribLevelReportInBar(user,
				regionToDesc);

		return out;
	}

	// 指向一个word 文件的inputStream
	public InputStream reportDistribLevelPieInWord(User user) {
		List<Region> firstLevelRegions = regionDAO.findByLevel(1);
		firstLevelRegions.add(0, regionDAO.findRoot());
		WordBean wordBean = new WordBean();
		wordBean.createNewDocument();// 创建一个新文档

		List<File>   jpgFiles = new ArrayList<File>() ;
		wordBean.insertTitleText("各大区分销商级别分布饼形图", 18);// 向文档中插入字符
		File jpgFile=null;
		for (int i =0;i <firstLevelRegions.size();i++) {
			Region r = firstLevelRegions.get(i);
			jpgFile = reportUtil.distribLevelReportInPieFile(user, r);
			jpgFiles.add(jpgFile);
			wordBean.insertJpeg(jpgFile .getAbsolutePath());

			wordBean.insertTitleText(r.getName() + "分销商级别分布饼形图", 15);// 向文档中插入字符
		}
		wordBean.insertTitleText(
				"------------------------------------------------------", 15);
		wordBean.insertTitleText("各省级分销商级别的分布饼形图", 18);
		firstLevelRegions.remove(0);// 移除根

		for (Region firstLevelRegion : firstLevelRegions) {
			wordBean.nextLine();
			wordBean.insertTitleText(firstLevelRegion.getName()
					+ "地区分销商级别的分布图----------------------------", 15);
			if (firstLevelRegion.getChildren() == null
					|| firstLevelRegion.getChildren().size() == 0) {
				wordBean.insertTitleText(firstLevelRegion.getName()
						+ "地区 暂无下辖区域，无数据可显示 ", 12);
			} else {
				for (Region secondLevelRegion : firstLevelRegion.getChildren()) {

					jpgFile = reportUtil.distribLevelReportInPieFile(user,
							secondLevelRegion);
					jpgFiles.add(jpgFile);
					wordBean.insertJpeg(jpgFile.getAbsolutePath());
					wordBean.insertTitleText(secondLevelRegion.getName()
							+ "地区分销商级别的分布图 ", 15);

				}
			}

		}

		File wordFile = new File(jpgFile.getParentFile(),	new SimpleDateFormat("yyyyMMddhhmmssSSS" ).format(new Date())+ "_各区域分销商级别分布饼形图.doc");

		wordBean.saveFileAs(wordFile.getAbsolutePath());
		wordBean.closeDocument();
		wordBean.closeWord();
		wordBean = null;
		for (File f :jpgFiles) {
			f.delete();
		}

		try {
			return new FileInputStream(wordFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public InputStream reportDistribLevelBarInWord(User user) {
		List<Region> firstLevelRegions = regionDAO.findByLevel(1);
		firstLevelRegions.add(0, regionDAO.findRoot());
		WordBean wordBean = new WordBean();
		wordBean.createNewDocument();// 创建一个新文档

		List<File>   jpgFiles = new ArrayList<File>() ;
		wordBean.insertTitleText("各大区分销商级别分布饼形图", 18);// 向文档中插入字符
		File jpgFile=null;
		for (int i =0;i <firstLevelRegions.size();i++) {
			Region r = firstLevelRegions.get(i);
			jpgFile = reportUtil.distribLevelReportInBarFile(user, r);
			jpgFiles.add(jpgFile);
			wordBean.insertJpeg(jpgFile .getAbsolutePath());

			wordBean.insertTitleText(r.getName() + "分销商级别分布饼形图", 15);// 向文档中插入字符
		}
		wordBean.insertTitleText(
				"------------------------------------------------------", 15);
		wordBean.insertTitleText("各省级分销商级别的分布饼形图", 18);
		firstLevelRegions.remove(0);// 移除根

		for (Region firstLevelRegion : firstLevelRegions) {
			wordBean.nextLine();
			wordBean.insertTitleText(firstLevelRegion.getName()
					+ "地区分销商级别的分布图----------------------------", 15);
			if (firstLevelRegion.getChildren() == null
					|| firstLevelRegion.getChildren().size() == 0) {
				wordBean.insertTitleText(firstLevelRegion.getName()
						+ "地区 暂无下辖区域，无数据可显示 ", 12);
			} else {
				for (Region secondLevelRegion : firstLevelRegion.getChildren()) {

					jpgFile = reportUtil.distribLevelReportInBarFile(user,
							secondLevelRegion);
					jpgFiles.add(jpgFile);
					wordBean.insertJpeg(jpgFile.getAbsolutePath());
					wordBean.insertTitleText(secondLevelRegion.getName()
							+ "地区分销商级别的分布图 ", 15);

				}
			}

		}


		File wordFile = new File(jpgFile.getParentFile(), 	new SimpleDateFormat("yyyyMMddhhmmssSSS" ).format(new Date())+"_各区域分销商级别分布柱形图.doc");

		wordBean.saveFileAs(wordFile.getAbsolutePath());
		wordBean.closeDocument();
		wordBean.closeWord();
		wordBean = null;
		for (File f :jpgFiles) {
			f.delete();
		}

		try {
			return new FileInputStream(wordFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}


	public List<Distrib> findAllDistrib(List<Region> secondsLevel) {
		return 	dao.findAllDistrib(  secondsLevel);


	}

	//查找相应为distribType 的分销商的总数，分别 存为int
	public  Integer  findDistribCount( DistribType  distribType ) {

		return dao.findDistribCount(distribType );
	}

	//相应会计期间被 抽查过的分销商总数
	public Integer findSpottedDistribInFP(DistribType dt, FiscalPeriod  fp) {


		return dao.findSpottedDistribInFP(dt, fp);
	}

}
