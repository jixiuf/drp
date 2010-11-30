package org.jixiuf.action;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.DistribType;
import org.jixiuf.drp.pojo.Region;
import org.jixiuf.drp.service.DistribService;
import org.jixiuf.drp.service.RegionService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 分销商与终端客户（医院）Action
 */
@Component("distribAction")
@Scope("prototype")
public class DistribAction extends ActionSupport implements RequestAware {

	Distrib  distrib = new Distrib();
	Region region = new Region();

	DistribService distribService;
	RegionService regionService;
	Map<String, Object> req;
	Map<String, Object> session;



	public Distrib getDistrib() {
		return distrib;
	}

	public void setDistrib(Distrib distrib) {
		this.distrib = distrib;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}



	public DistribService getDistribService() {
		return distribService;
	}

	@Resource(name="distribService")
	public void setDistribService(DistribService distribService) {
		this.distribService = distribService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	@Resource(name = "regionService")
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setRequest(Map<String, Object> req) {
		this.req = req;
	}

	// ------------------------------------------

	/**
	 * 列出所有分销商
	 *
	 * @return
	 */
	public String listDistrib() {

		List<Region> regions = regionService.findAll();
		List<Distrib> distributions = distribService.findAllDistrib();
		req.put("regions", regions);
		req.put("distributions", distributions);
		return "distrib_list_tree_jsp";

	}

	/**
	 * 从浏览器获得region.id 查出相应region 在region_main_jsp做显示
	 *
	 * @return
	 */
	public String region() {
		region = regionService.findById(region.getId());

		return "region_main_jsp";

	}

	/**
	 * 添加 新区域
	 *
	 * @return
	 */
	public String addRegion() {
		regionService.addRegion(region);
		this.addActionMessage("新地区添加成功,地区名为:" + region.getName());

		return SUCCESS;

	}

	public String modRegion() {
		regionService.modRegion(region);
		return SUCCESS;

	}

	/**
	 * 尝试删除一个地区，如果此地区下有分销商，则分销商一同被删除()，若此地区下有终端客户则不能删除此地区，而只是把此地区下的所有分销商 删除
	 *
	 * @return
	 */
	public String deleteDistribRegion() {
		String msg = regionService.deleteDistribRegion(region);
		this.addActionMessage(msg);
		return  SUCCESS;

	}
	public String deleteAllDistribsOfRegion() {

		String msg = regionService.deleteAllDistribsOfRegion(region);
		this.addActionMessage(msg);
		return  SUCCESS;

	}

	/**
	 * 从浏览器获得client.id 查出相应Client分销商 在distrib_main_jsp做显示
	 *
	 * @return
	 */
	public String distrib() {
		distrib = distribService.findById(distrib.getId());
		return "distrib_main_jsp";

	}

	/**
	 * 为添加分销商准备数据
	 *
	 * @return
	 */
	public String preAddDistrib() {
		region = regionService.findById(region.getId());
		List<DistribType> clientType = distribService.findAllDistriType();
		System.out.println(clientType);
		req.put("distribTypes", clientType);
		return "distrib_add_jsp";

	}

	/**
	 * 添加分销商
	 *
	 * @return
	 */
	public String addDistrib() {
		String msg=distribService.addDistrib(distrib);
		this.addActionMessage(msg);
		if (msg.equals("请换一个分销商代码，此码已被使用")) {
			return "distrib_add_jsp";
		}
		return  SUCCESS;

	}

	/**
	 * 为修改分销商准备数据，将要修改的分销商的信息查询出来
	 * @return
	 */
	public String preModDistrib() {
		List<DistribType> distribTypes = distribService.findAllDistriType();
		req.put("distribTypes", distribTypes);
		distrib=distribService.findById(distrib.getId());
		return "distrib_mod_jsp";

	}
	/**
	 * 修改分销商
	 * @return
	 */
	public String modDistrib() {
	  String msg=  distribService.modDistrib(distrib);
	  this.addActionMessage(msg);
	    return  SUCCESS;

	}
	/**
	 * 删除分销商
	 * @return
	 */
	public String delDistrib() {
		this.addActionError("无法删除此分销商，别处引用了此分销商");
		String msg=distribService.delDistrib(distrib);
		this.addActionMessage(msg);

		return  SUCCESS;

	}
	/**
	 * 显示 分销商详细信息
	 * @return
	 */
	public String showDistribDetail() {
		distrib=distribService.findById(distrib.getId());
		return "distrib_detail_jsp";


	}

}
