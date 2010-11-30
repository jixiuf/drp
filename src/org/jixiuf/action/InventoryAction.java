package org.jixiuf.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.Inventory;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.drp.service.DistribService;
import org.jixiuf.drp.service.FiscalPeriodService;
import org.jixiuf.drp.service.InventoryService;
import org.jixiuf.drp.service.MaterialService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 库存管理（台帐信息） 存货清单
 *
 * @author Administrator
 *
 */
@Component("inventoryAction")
@Scope("prototype")
public class InventoryAction extends ActionSupport implements RequestAware {
	Inventory it = new Inventory();
	Distrib distrib = new Distrib();
	Material m = new Material();
	FiscalPeriod fp;
	String inventoryIds;


	PageModel pm = new PageModel();
	InventoryService iService;
	DistribService distribService;
	FiscalPeriodService fService;
	MaterialService materialService;

	Map<String, Object> req;

	public String getInventoryIds() {
		return inventoryIds;
	}

	public void setInventoryIds(String inventoryIds) {
		this.inventoryIds = inventoryIds;
	}

	public Material getM() {
		return m;
	}

	public void setM(Material m) {
		this.m = m;
	}

	public FiscalPeriod getFp() {
		return fp;
	}

	public void setFp(FiscalPeriod fp) {
		this.fp = fp;
	}



	public Distrib getDistrib() {
		return distrib;
	}

	public void setDistrib(Distrib distrib) {
		this.distrib = distrib;
	}

	public Inventory getIt() {
		return it;
	}

	public void setIt(Inventory it) {
		this.it = it;
	}

	public PageModel getPm() {
		return pm;
	}

	public void setPm(PageModel pm) {
		this.pm = pm;
	}



	public void setReq(Map<String, Object> req) {
		this.req = req;
	}





	public void setRequest(Map<String, Object> req) {
		this.req = req;
	}


	public MaterialService getMaterialService() {
		return materialService;
	}

	@Resource(name = "materialService")
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	public InventoryService getiService() {
		return iService;
	}

	@Resource(name="inventoryService")
	public void setiService(InventoryService iService) {
		this.iService = iService;
	}

	public DistribService getDistribService() {
		return distribService;
	}

	@Resource(name="distribService")
	public void setDistribService(DistribService distribService) {
		this.distribService = distribService;
	}

	public FiscalPeriodService getfService() {
		return fService;
	}
	@Resource(name="fiscalPeriodService")
	public void setfService(FiscalPeriodService fService) {
		this.fService = fService;
	}
	// -----------------------------------------
	/***
	 * 库存清单初始化，由数据录入人员完成
	 */
	public String initInventory() {
		List<Inventory> inventorys = iService
				.findAllNoneAvailableInventorys(pm);
		req.put("inventorys", inventorys);
		return "inventory_init_jsp";

	}

	/**
	 * 显示 所有分销商
	 *
	 * @return
	 */
	public String listDistribs() {
		List<Distrib> clients = distribService. findAllDistrib(pm);
		req.put("distribs", clients);
		return "distrib_search_jsp";

	}

	/**
	 * ajax search 分销商
	 *
	 * @return
	 */
	public String searchDistribs() {
		try {
			distrib.setName(URLDecoder.decode(distrib.getName(), "utf-8").trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Distrib> distribs = null;
		if ("".equals(distrib.getName().trim())) {
			distribs = distribService.findAllDistrib(pm);
		} else {
			distribs = distribService. searchDistrib (distrib.getName(), pm);
		}

		req.put("clients", distribs);
		return "distrib_search_result_jsp";

	}

	/**
	 * 物料
	 *
	 * @return
	 */
	public String listMaterials() {
		List<Material> materials = materialService.findAllMaterials(pm);
		req.put("materials", materials);
		return "material_search_jsp";

	}

	/**
	 * ajax search 物料
	 *
	 * @return
	 */
	public String searchMaterials() {
		try {
			m.setName(URLDecoder.decode(m.getName(), "utf-8").trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Material> materials = null;
		if ("".equals(m.getName().trim())) {
			materials = materialService.findAllMaterials(pm);
		} else {
			materials = materialService.searchMaterials(m.getName(), pm);
		}

		req.put("materials", materials);
		return "material_search_result_jsp";

	}

	/**
	 *
	 *
	 * @return
	 */
	public String listFiscalPeriods() {
		List<FiscalPeriod> fps = fService.findAllAvilable( );
		req.put("fps", fps);
		return "fiscalPeriod_search_jsp";

	}

	/**
	 * 根据Inventory.client Inventory.material 查出所有 处于初始化状态的Inventory
	 *
	 * @return
	 */
	public String searchInventoryByClientOrMaterial() {
		try {
			it.getDistrib().setClientno(
					URLDecoder.decode(it.getDistrib().getClientno(), "utf-8")
							.trim());
			it.getMaterial()
					.setNo(
							URLDecoder
									.decode(it.getMaterial().getNo(), "utf-8")
									.trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("============================="+it.getMaterial().getNo());
		List<Inventory> inventorys = null;
		// 如果client material 都 为空 ，查出所有Inventory
		if ("".equals(it.getDistrib().getClientno().trim())
				&& "".equals(it.getMaterial().getNo().trim())) {
			inventorys = iService.findAllNoneAvailableInventorys(pm);
		} else if ("".equals(it.getDistrib().getClientno().trim())) {// 如果只是it.client.clientno
			// 为空
			inventorys = iService.findAllNoneAvailableInventorysByMaterialNo(it
					.getMaterial().getNo(), pm);
		} else if ("".equals(it.getMaterial().getNo().trim())) {// 如果只是it.material.no
			// 为空
			inventorys = iService.findAllNoneAvailableInventorysByClientNo(it
					.getDistrib().getClientno(), pm);
		} else {
			inventorys = iService
					.findAllNoneAvailableInventorysByClientNoAndMaterialNo(it
							.getMaterial().getNo(), it.getDistrib()
							.getClientno(), pm);
		}

		req.put("inventorys", inventorys);
		return "inventory_init_search_result_jsp";

	}

	/**
	 * 添加 库存清单，注意新添加 的库存清单的fiscalPeriod 为null, 并且，分销商与物料皆同的数据 只允许一条（
	 * 初始完毕后已经确认生效的除外）
	 */
	public String addInventory() {
		String msg = iService.addInventory(it);
		this.addActionMessage(msg);
		if ("".equals(msg)) {// 正常
			return this.initInventory();

		} else {// 出错
			return "inventory_init_add_jsp";
		}

	}

	public String preModInventory() {
		it = iService.findById(it.getId());

		return "inventory_init_mod_jsp";

	}

	public String modInventory() {
		String msg = iService.modInventory(it);
		this.addActionMessage(msg);
		if ("".equals(msg)) {// 正常
			return this.initInventory();

		} else {// 出错
			return "inventory_init_mod_jsp";
		}
	}

	public String delInventorys() {
		String msg = iService.deleteInventorys(inventoryIds);
		this.addActionMessage(msg);
		return initInventory();
	}

	/**
	 * 数据录入人员确认录入的数据无误后送去别人审查，送审的记录，数据录入人员无权更改了
	 *
	 * @return
	 */
	public String sendToCheckInventory() {
		String msg = iService.modInventoryStatus(inventoryIds.split(","),
				Inventory.SEND_TO_CHECK);
		this.addActionMessage(msg);
		return initInventory();
	}

	public String preConfirmInventorys() {
		List<Inventory> inventorys = iService.findallSendedInventorys(pm);
		req.put("inventorys", inventorys);
		return "inventory_confirm_jsp";
	}

	/**
	 * 存货清单的确认，存货清单由数据录入人员录入 ，录入 完需经确认方可生效，此法确认之，即改inventory.status=Y
	 *
	 * @return
	 */
	public String confirmInventorys() {
		String msg = iService.modInventoryStatus(inventoryIds.split(","),
				Inventory.AVAILABLE);
		this.addActionMessage(msg);
		return preConfirmInventorys();
	}
}
