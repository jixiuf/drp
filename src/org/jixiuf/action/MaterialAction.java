package org.jixiuf.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.MaterialItemUnit;
import org.jixiuf.drp.pojo.MaterialType;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.drp.service.MaterialService;
import org.jixiuf.util.UploadUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "materialAction")
@Scope("prototype")
public class MaterialAction extends ActionSupport implements RequestAware,
		ServletContextAware {
	Material m = new Material();
	PageModel pm = new PageModel();
	File pictureFile;

	/**
	 * 为上传准备的三个方法
	 *
	 * @param pictureFile
	 */
	public void setPictureFile(File pictureFile) {
		this.pictureFile = pictureFile;
	}

	public void setPictureFileFileName(String filename) {
		this.m.setPictFilename(filename);
	}

	String delMaterialsIds;
	MaterialService mService;

	Map<String, Object> req;
	HttpServletRequest httpRequest;
	private ServletContext servletCtx;

	public String getDelMaterialsIds() {
		return delMaterialsIds;
	}

	public void setDelMaterialsIds(String delMaterialsIds) {
		this.delMaterialsIds = delMaterialsIds;
	}

	public PageModel getPm() {
		return pm;
	}

	public void setPm(PageModel pm) {
		this.pm = pm;
	}

	public Material getM() {
		return m;
	}

	public void setM(Material m) {
		this.m = m;
	}

	public MaterialService getMService() {
		return mService;
	}

	@Resource(name = "materialService")
	public void setMService(MaterialService service) {
		mService = service;
	}

	public void setRequest(Map<String, Object> req) {
		this.req = req;
	}



	public void setServletContext(ServletContext ctx) {
		this.servletCtx = ctx;

	}

	// ------------------------------------------------
	public String listMaterials() {
		pm.setPageSize(5);
		List<Material> materials = mService.findAll(pm);
		req.put("materials", materials);

		return "material_main_jsp";
	}

	/**
	 * 显示某个物料的详细信息
	 *
	 * @return
	 */
	public String listSingleMaterial() {
		m = mService.findById(m.getId());

		return "material_detail_jsp";
	}

	public String preAddMaterial() {

		List<MaterialType> materialTypes = mService.findAllMaterialTypes();
		List<MaterialItemUnit> materialItemUnits = mService
				.findAllMaterialItemUnits();
		req.put("materialTypes", materialTypes);
		req.put("materialItemUnits", materialItemUnits);
		if (null == m.getId()) {// 如果传过来一个m.id 则转身 ”复制修改添加“页面，否则 转向添加 页面
			return "material_add_jsp";
		} else {
			m = mService.findById(m.getId());
			return "material_copy_jsp";
		}

	}

	public String addMaterial() {
		boolean success = mService.addMaterial(m);
		if (!success) {
			this.addActionMessage("相同代码的物料已经存在，请换一个代码");
			return preAddMaterial();
		}
		this.addActionMessage("添加成功！");
		return listMaterials();

	}

	public String preModMaterial() {
		m = mService.findById(m.getId());
		List<MaterialType> materialTypes = mService.findAllMaterialTypes();
		List<MaterialItemUnit> materialItemUnits = mService
				.findAllMaterialItemUnits();
		req.put("materialTypes", materialTypes);
		req.put("materialItemUnits", materialItemUnits);
		return "material_mod_jsp";
	}

	public String modMaterial() {
		mService.modMaterial(m);
		return listMaterials();
	}

	public String delMaterials() {

		String[] ids = this.getDelMaterialsIds().split(",");
		String msg = mService.delMaterials(ids);
		this.addActionMessage(msg);
		return listMaterials();
	}

	public String preUpload() {
		m = mService.findById(m.getId());
		return "material_upload_jsp";
	}

	/**
	 * 上传物料对应的图片
	 *
	 * @return
	 */
	public String doUpload() {
		// 如果后缀名不是gif,jqeg,jpg,bpm
		if (!UploadUtil.getInstance().setAllowFileNameSuffixs(
				"gif,jqeg,jpg,bmp")
				.isFileNameSuffixAllowed(
						UploadUtil.getInstance().getFileNameSuffix(
								m.getPictFilename()))) {
			this.addActionError("后缀名只允许gif,jqeg,jpg,bmp");
			return preUpload();

		}
		// 根据传过来的文件名后缀用UUID重命名之
		String newFileName = UploadUtil.getInstance().reName(
				m.getPictFilename());
		String uploadFilePath = servletCtx.getRealPath("/upload")
				+ File.separator + newFileName;
		this.pictureFile.renameTo(new File(uploadFilePath));
		m.setPictFilename(newFileName);
		mService.modMaterialPictFilename(m);
		m = mService.findById(m.getId());
		return "material_detail_jsp";
	}

	public String searchMaterials() {
		try {
			m.setName(URLDecoder.decode(m.getName(),"utf-8").trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Material> materials = mService.searchMaterials(m.getName(), pm);
		req.put("materials", materials);
		return "material_search_result_jsp";
	}
}
