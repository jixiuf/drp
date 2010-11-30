package org.jixiuf.drp.service;

import java.util.List;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.DistribDAO;
import org.jixiuf.drp.dao.FiscalPeriodDAO;
import org.jixiuf.drp.dao.InventoryDAO;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.Inventory;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.stereotype.Component;

@Component("inventoryService")
public class InventoryService {

	private InventoryDAO dao;
	private DistribDAO distribDAO;
	private FiscalPeriodDAO fiscalPeriodDAO;
	private MaterialService materialService;

	public InventoryDAO getDao() {
		return dao;
	}

	@Resource(name = "inventoryDAO")
	public void setDao(InventoryDAO dao) {
		this.dao = dao;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	@Resource(name = "materialService")
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public DistribDAO getDistribDAO() {
		return distribDAO;
	}

	@Resource(name = "distribDAO")
	public void setDistribDAO(DistribDAO DistribDAO) {
		this.distribDAO = DistribDAO;
	}

	public FiscalPeriodDAO getFiscalPeriodDAO() {
		return fiscalPeriodDAO;
	}

	@Resource(name = "fiscalPeriodDAO")
	public void setFiscalPeriodDAO(FiscalPeriodDAO fiscalPeriodDAO) {
		this.fiscalPeriodDAO = fiscalPeriodDAO;
	}

	// ---------------------------------------

	/**
	 * 查询所有Inventory.status='N' 的Inventory 库存信息 ,用于分销商库存数量初始化 界面 的显示
	 */
	public List<Inventory> findAllNoneAvailableInventorys(PageModel pm) {
		return dao.findAllNoneAvailable(pm);

	}

	/**
	 * 查出所有 Inventory.material.no 为参数的 处于初始化状态（即Inventory.status ='N'）的Inventory
	 *
	 * @param no
	 *            Inventory.material.no
	 * @param pm
	 * @return
	 */
	public List<Inventory> findAllNoneAvailableInventorysByMaterialNo(
			String materialNo, PageModel pm) {

		return dao.findAllNoneAvailableInventorysByMaterialNo(materialNo, pm);
	}

	/**
	 * 查出所有 Inventory.client.clientno 为参数的 处于初始化状态（即Inventory.status
	 * ='N'）的Inventory
	 *
	 * @param no
	 *            Inventory.client.clientno
	 * @param pm
	 * @return
	 */
	public List<Inventory> findAllNoneAvailableInventorysByClientNo(
			String clientNo, PageModel pm) {
		return dao.findAllNoneAvailableInventorysByClientNo(clientNo, pm);
	}

	/**
	 * 查出所有 Inventory.material.no Inventory.client.clientno 为参数的
	 * 处于初始化状态（即Inventory.status ='N'）的Inventory
	 *
	 * @param materialNo
	 *            Inventory.material.no
	 * @param clientNo
	 *            Inventory.client.clientno
	 * @param pm
	 * @return
	 */
	public List<Inventory> findAllNoneAvailableInventorysByClientNoAndMaterialNo(
			String materialNo, String clientNo, PageModel pm) {
		return dao.findAllNoneAvailableInventorysByClientNoAndMaterialNo(
				materialNo, clientNo, pm);
	}

	/**
	 * 初始化一个新存货清单
	 *
	 * @param it
	 * @return
	 */
	public String addInventory(Inventory it) {
		String clientId = it.getDistrib().getId();
		String materialId = it.getMaterial().getId();
		Distrib c = distribDAO.findById(clientId);
		Material m = materialService.findById(materialId);
		if (c == null) {
			return "您选中的分销商不存在！！";
		}
		if (m == null) {
			return "您选中的物料不存在！！";
		}
		it.setDistrib(c);
		it.setMaterial(m);
		it.setFiscalPeriod(null);
		boolean exists = dao
				.getCountOfNoneAvailableInventoryByMaterialIdAndClientId(
						materialId, clientId) > 0;
		if (exists) {
			return "此条数据已经存在，不允许保存两条相同的初始化状态的存货清单";

		}

		dao.save(it);

		return "";

	}

	public Inventory findById(String id) {
		return dao.findById(id);
	}

	public String modInventory(Inventory it) {
		if (it == null || it.getId() == null || "".equals(it.getId())) {
			return "请选择要修改的数据 ";

		}
		int count = dao
				.getCountOfNoneAvailableInventoryByMaterialIdAndClientId(it
						.getMaterial().getId(), it.getDistrib().getId());
		if (count > 1) {
			return "相同物料和分销商的库存清单已经存在,不允许修改，推荐修改操作不要修改物料代码与分销商代码，只作期初数量的修改！！ ";
		} else if (count == 1) {
			Inventory it2 = dao
					.findNoneAvailableInventoryByMaterialIdAndClientId(it
							.getMaterial().getId(), it.getDistrib().getId());
			if (!it.getId().equals(it2.getId())) {// 如果查出 的一条相同it.material.id
													// it.client.id的数据并不是参数it本身，则说明
													// 数据冲突，不允许修改
				return "相同物料和分销商的库存清单已经存在,不允许修改，推荐修改操作不要修改物料代码与分销商代码，只作期初数量的修改！！ ";
			}
			dao.merge(it); // 如果查出 的一条相同it.material.id it.client.id的数据是参数it本身
							// 进行修改
			return "";

		}

		// 如果 count==0 , 则也可以进行，相当于加入一条新数据
		dao.merge(it);
		return "";

	}

	public String deleteInventorys(String delInventoryIds) {
		String[] inventoryIds = delInventoryIds.split(",");
		int count = dao.deleteAllNoneAvailableInventorys(inventoryIds);
		return "删除了" + count + "条记录";

	}

	public String modInventoryStatus(String[] inventoryIds,
			String inventoryStatus) {
		int count = dao.changeStatusOfInventorys(inventoryIds, inventoryStatus);
		return "更新了" + count + "条记录";

	}

	/**
	 * 查出所有处于送审状态 的清单
	 *
	 * @return
	 */
	public List<Inventory> findallSendedInventorys(PageModel pm) {
		return dao.findallSendedInventorys(pm);
	}

}
