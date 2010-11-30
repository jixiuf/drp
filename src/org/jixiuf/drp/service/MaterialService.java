package org.jixiuf.drp.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.InventoryDAO;
import org.jixiuf.drp.dao.MaterialDAO;
import org.jixiuf.drp.dao.MaterialItemUnitDAO;
import org.jixiuf.drp.dao.MaterialTypeDAO;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.MaterialItemUnit;
import org.jixiuf.drp.pojo.MaterialType;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.util.UploadUtil;
import org.springframework.stereotype.Component;

@Component("materialService")
public class MaterialService {
	MaterialDAO dao;
	MaterialTypeDAO materialTypeDAO;
	MaterialItemUnitDAO materialItemUnitDAO;
	InventoryDAO inventoryDAO;

	public InventoryDAO getInventoryDAO() {
		return inventoryDAO;
	}

	@Resource(name = "inventoryDAO")
	public void setInventoryDAO(InventoryDAO inventoryDAO) {
		this.inventoryDAO = inventoryDAO;
	}

	public MaterialItemUnitDAO getMaterialItemUnitDAO() {
		return materialItemUnitDAO;
	}

	@Resource(name = "materialItemUnitDAO")
	public void setMaterialItemUnitDAO(MaterialItemUnitDAO materialItemUnitDAO) {
		this.materialItemUnitDAO = materialItemUnitDAO;
	}

	public MaterialTypeDAO getMaterialTypeDAO() {
		return materialTypeDAO;
	}

	@Resource(name = "materialTypeDAO")
	public void setMaterialTypeDAO(MaterialTypeDAO materialTypeDAO) {
		this.materialTypeDAO = materialTypeDAO;
	}

	public MaterialDAO getDao() {
		return dao;
	}

	@Resource(name = "materialDAO")
	public void setDao(MaterialDAO dao) {
		this.dao = dao;
	}

	// ----------------------------------
	public List<Material> findAll(PageModel pm) {
		List<Material> materials = dao.findAll(pm);
		return materials;

	}

	public List<MaterialType> findAllMaterialTypes() {
		return materialTypeDAO.findAll();
	}

	public List<MaterialItemUnit> findAllMaterialItemUnits() {
		return materialItemUnitDAO.findAll();
	}

	public boolean addMaterial(Material m) {
		if("".equals( m.getNo().trim())) {
			return false;
		}
		List<Material> sameClientNos = dao.findByNo(m.getNo());
		/**
		 * 如果 相同 代码 的Material数量大于1 ，或者等于1但并非m 本身
		 */

		if (sameClientNos.size() > 1
				|| (sameClientNos.size() == 1 && !sameClientNos.get(0).getId()
						.equals(m.getId()))) {
			return false;
		}

		dao.save(m);
		return true;
	}

	public Material findById(String id) {
		return dao.findById(id);
	}

	public void modMaterial(Material m) {
		dao.merge(m);

	}

	public String delMaterials(String[] ids) {

		int count = inventoryDAO.findCountOfInventory(ids);
		if (count == 0) {
			count = dao.delMaterials(ids);
			List <String> pictureFileNames=dao.getPictureFiles(ids);
			UploadUtil uploadUtil=UploadUtil.getInstance();
			for(String fileName:pictureFileNames) {
		new File(uploadUtil.getDestinationPath(),fileName).delete();

			}
			return "删了" + count + "条记录";
		} else {
			return "因为 库存信息表Inventory中引用了其中某个要删除的物料，所以不能执行删除";

		}

	}

	/**
	 * 更新Material 的pictFilename
	 * @param m
	 */
	public void modMaterialPictFilename(Material m) {
		dao.updatePictFileName(m);

	}

	/**
	 * 根据 传过来的materialNameOrNo（可能是物料的名称，可能是代码）
	 * @param materialNameOrNo
	 */
	public List<Material> searchMaterials(String  materialNameOrNo,PageModel pm) {
		if("".equals(materialNameOrNo)) {
			return dao.findAll(pm);
		}
		return dao.searchMaterials(materialNameOrNo,pm);

	}

	public List<Material> findAllMaterials(PageModel pm) {

		return dao.findAllMaterials(pm);
	}
}
