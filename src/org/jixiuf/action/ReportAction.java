package org.jixiuf.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.DistribType;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.Region;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.service.DistribService;
import org.jixiuf.drp.service.FiscalPeriodService;
import org.jixiuf.drp.service.FlowcardService;
import org.jixiuf.drp.service.RegionService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component("reportAction")
@Scope("prototype")
public class ReportAction extends ActionSupport implements RequestAware,
		SessionAware {
	private static final long serialVersionUID = 2875563236896646388L;
	DistribService distribService;

	FiscalPeriodService fpService;
	RegionService regionService;
	FlowcardService fcService;
	Map<String, Object> req;
	Map<String, Object> session;
	String paramStr;
	Region region;
	InputStream imageStream;

	public FlowcardService getFcService() {
		return fcService;
	}

	@Resource(name = "flowcardService")
	public void setFcService(FlowcardService fcService) {
		this.fcService = fcService;
	}

	public FiscalPeriodService getFpService() {
		return fpService;
	}

	@Resource(name = "fiscalPeriodService")
	public void setFpService(FiscalPeriodService fpService) {
		this.fpService = fpService;
	}

	public InputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(InputStream imageStream) {
		this.imageStream = imageStream;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	@Resource(name = "regionService")
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public DistribService getDistribService() {
		return distribService;
	}

	@Resource(name = "distribService")
	public void setDistribService(DistribService distribService) {
		this.distribService = distribService;
	}

	public void setRequest(Map<String, Object> request) {
		this.req = request;

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	// ========================================
	public String distribLevelMain() {
		List<Region> firstLevelRegions = regionService.findByLevel(1);
		List<Region> secondLevelRegions = regionService.findByLevel(2);
		req.put("firstLevelRegions", firstLevelRegions);
		req.put("secondLevelRegions", secondLevelRegions);

		return "distribLevel_jsp";
	}

	public String listChildRegion() {
		List<Region> children = regionService.findChildren(region.getId());

		req.put("regionChildren", children);
		return "region_list_children_jsp";
	}

	public String reportDistribLevel() {
		ByteArrayOutputStream out = null;
		if ("pie".equals(paramStr)) {
			out = distribService.reportDistribLevelInPie(region.getId(),
					(User) session.get("LoginedUser"));
		} else if ("bar".equals(paramStr)) {
			out = distribService.reportDistribLevelInBar(region.getId(),
					(User) session.get("LoginedUser"));
		}

		imageStream = new ByteArrayInputStream(out.toByteArray());
		return "distribLevel_detail_jpg";

	}

	public String reportDistribLevelInWord() {

		if ("pie".equals(paramStr)) {
			imageStream = distribService
					.reportDistribLevelPieInWord((User) session
							.get("LoginedUser"));
		} else if ("bar".equals(paramStr)) {
			imageStream = distribService
					.reportDistribLevelBarInWord((User) session
							.get("LoginedUser"));

		}

		FileInputStream fis = (FileInputStream) imageStream;

		return "distribLevel_detail_doc";

	}

	// ===========================================流向单审核报告
	public String loadDateForCheckFlowcard() {
		List<FiscalPeriod> fps = fpService.findAllAvilable();
		List<Region> firstLevelRegions = regionService.findByLevel(1);
		for (Region firstLevel : firstLevelRegions) {
			List<Region> secondsLevel = regionService.findChildren(firstLevel
					.getId());
			List<Distrib> distribs = distribService
					.findAllDistrib(secondsLevel);
			int count = fcService.findAllFlowcardCountWithStatusA(distribs);

		}
		return null;
	}

	// /=============================================================
	// 分销商抽查报告
	public String spotDistrib() {
		List<DistribType> distribTypes = distribService.findAllDistriType();

		List<FiscalPeriod> fps = fpService.findAllAvilable();
		List<Integer> distribCounts = new ArrayList<Integer>();
		Map<DistribType, List<Integer>> spottedDistribMaps = new HashMap<DistribType, List<Integer>>();
		for (DistribType dt : distribTypes) {
			int allDistribCount = distribService.findDistribCount(dt);// 相应类型的分销商的数量，
			distribCounts.add(allDistribCount);
			List<Integer> spottedDistribList = new ArrayList<Integer>();
			for (FiscalPeriod fp : fps) {
				int spottedDistrib = distribService.findSpottedDistribInFP(dt,
						fp);// 不同会计期，对应的抽查过的分销商总数
				spottedDistribList.add(spottedDistrib);
				spottedDistribMaps.put(dt, spottedDistribList);
			}

		}
		req.put("distribTypes", distribTypes);
		req.put("distribCounts", distribCounts);
		req.put("fps", fps);
		req.put("spottedDistribMaps", spottedDistribMaps);
		
		return "flowcard_spot_report_jsp";
	}
}
