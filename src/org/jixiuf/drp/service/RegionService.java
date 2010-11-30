package org.jixiuf.drp.service;

import java.util.List;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.DistribDAO;
import org.jixiuf.drp.dao.RegionDAO;
import org.jixiuf.drp.dao.TerminalDAO;
import org.jixiuf.drp.pojo.Region;
import org.springframework.stereotype.Component;

@Component("regionService")
public class RegionService {

	RegionDAO dao;
	DistribDAO distribDAO;
	TerminalDAO terminalDAO;

	public TerminalDAO getTerminalDAO() {
		return terminalDAO;
	}

	@Resource(name = "terminalDAO")
	public void setTerminalDAO(TerminalDAO terminalDAO) {
		this.terminalDAO = terminalDAO;
	}

	public DistribDAO getDistribDAO() {
		return distribDAO;
	}

	@Resource(name = "distribDAO")
	public void setDistribDAO(DistribDAO DistribDAO) {
		this.distribDAO = DistribDAO;
	}

	public RegionDAO getDao() {
		return dao;
	}

	@Resource(name = "regionDAO")
	public void setDao(RegionDAO dao) {
		this.dao = dao;
	}

	/**
	 * 返回所有地区
	 *
	 * @return
	 */
	public List<Region> findAll() {
		List<Region> regions = dao.findAll();
		return regions;
	}

	public Region findById(String id) {
		Region r = dao.findById(id);
		return r;
	}

	/**
	 * 尝试删除一个地区，如果此地区下有分销商，则分销商一同被删除()，若此地区下有终端客户则不能删除此地区，而只是把此地区下的所有分销商 删除
	 *
	 * @param region
	 * @return
	 */
	public String deleteDistribRegion(Region region) {
		region = dao.findById(region.getId());
		if (region == null) {
			return "数据库中无此地区名";
		}
		int count = distribDAO.deleteDistriByRegionId(region.getId());

		int terminalsCount = terminalDAO.findTerminalCount(region);
		if (terminalsCount > 0) {
			return "因为有其他终端客户位于此地区下，所以只能删除此地区下的分销商，";
		}

		try {
			// 此处利用了数据库的外键约束，如有约束存在则不能删除 此地区
			dao.deleteRegionTree(region);
		} catch (RuntimeException e) {
			return "删除地区失败，此地区不能被删除 !!!";

		}

		return "位于此地区下的分销商共有" + count + "个被 一同删除";

	}

	public void addRegion(Region region) {
		Region parent = dao.findById(region.getParent().getId());
		region.setLeaf(true);
		region.setLevel((byte) (parent.getLevel() + 1));
		parent.setLeaf(false);
		dao.attachDirty(region);
		dao.save(region);
	}

	public void modRegion(Region region) {
		Region updated = dao.findById(region.getId());
		updated.setName(region.getName());
		dao.attachDirty(updated);

	}

	public String deleteAllDistribsOfRegion(Region region) {
		region = dao.findById(region.getId());
		if (region == null) {
			return "数据库中无此地区名";
		}
		int count = distribDAO.deleteDistriByRegionId(region.getId());
		return "共有" + count + "条分销商被删除";
	}

	public List<Region> findByLevel(int level) {
		return dao.findByLevel(level);

	}

	public List<Region> findChildren(String parentId) {
		if (parentId.equals("-1")) {
			return dao.findByLevel(2);

		}
		return dao.findChildren(parentId);
	}

	public Region findRoot() {
		return dao.findRoot();
	}

	public List<Region> findAllDescendant(String regionId,
			boolean withThisRegion) {
		return	dao.findAllDescendant(regionId, withThisRegion);


	}

}
