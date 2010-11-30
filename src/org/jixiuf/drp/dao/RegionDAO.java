package org.jixiuf.drp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.jixiuf.drp.pojo.Region;
import org.springframework.stereotype.Component;

/**
 * A data access object (DAO) providing persistence and search support for
 * Region entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see org.jixiuf.drp.pojo.Region
 * @author MyEclipse Persistence Tools
 */

@Component("regionDAO")
public class RegionDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(RegionDAO.class);
	// property constants
	public static final String PID = "pid";
	public static final String NAME = "name";
	public static final String LEVEL = "level";
	public static final String LEAF = "leaf";

	public void save(Region transientInstance) {
		log.debug("saving Region instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Region persistentInstance) {
		log.debug("deleting Region instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Region findById(java.lang.String id) {
		log.debug("getting Region instance with id: " + id);
		try {
			if (id.equals("-1") || null == id) {
				  return  findRoot();
			}
			Region instance = (Region) getHibernateTemplate().get(
					"org.jixiuf.drp.pojo.Region", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Region> findAll() {
		log.debug("finding all Region instances");
		try {
			String queryString = "from Region r order by r.level ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Region merge(Region detachedInstance) {
		log.debug("merging Region instance");
		try {
			Region result = (Region) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Region instance) {
		log.debug("attaching dirty Region instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Region instance) {
		log.debug("attaching clean Region instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 *
	 *递归删除所有此地区的子节点，注意地区是个树形结构
	 *
	 * @return
	 */
	public void deleteRegionTree(Region region) {
		if (!region.getLeaf()) {// 如果不是叶子节点 ，递归调用 本方法
			List<Region> children = findChildren(region.getId());
			for (Region r : children) {
				deleteRegionTree(r);
			}
		}

		/**
		 * 如果父节点下再无其他节点则修改其leaf 将父节点改为叶子节点
		 */
		if (region.getParent() != null) {
			int brotherCount = findChildrenCount(region.getParent());
			if (brotherCount == 0) {
				Region parent = this.findById(region.getParent().getId());
				parent.setLeaf(true);
			}
		}
		delete(region);

	}

	@SuppressWarnings("unchecked")
	public List<Region> findChildren(String parentId) {
		log.debug("find Children of region ");
		try {
			if (parentId == null || "null".equals(parentId)) {
				return getHibernateTemplate().find(
						"   from Region r where r.parent.id is null ");
			}
			List<Region> list = getHibernateTemplate().find(
					"   from Region r where r.parent.id=?", parentId);
			return list;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}

	/**
	 * 返回region 的孩子个数
	 *
	 * @param region
	 * @return
	 */
	public int findChildrenCount(Region region) {
		return findChildrenCount(region.getId());
	}

	/**
	 * 返回 以regionId 为id 的region的孩子个数
	 */

	@SuppressWarnings("unchecked")
	public int findChildrenCount(String regionId) {

		try {
			List<Long> list = getHibernateTemplate().find(
					"select count(*) from Region r where r.parent.id=?",
					regionId);
			int count = list.get(0).intValue();
			log.debug("findChildrenCount  of region :" + count);
			return count;
		} catch (RuntimeException re) {
			log.error("  findChildrenCount failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Region> findByLevel(int level) {
		return getHibernateTemplate().find(" from Region r where r.level= ?  ",
				(byte) level);

	}

	public Region findRoot() {
		return (Region) getHibernateTemplate().find(
				" from Region r where r.parent.id is null").get(0);
	}

	/**
	 * @param regionId
	 * @param b
	 *            是否包含regionId 所代表的Region
	 * @return 所有子孙
	 */
	@SuppressWarnings("unchecked")
	public List<Region> findAllDescendant(String regionId,
			boolean withThisRegion) {
		List<Region> returnRegions = new ArrayList<Region>();
		Region r = this.findById(regionId);
		if (withThisRegion) {
			returnRegions.add(r);
		}
		Stack<Region> stacks = new Stack();
		stacks.push(r);
		while (!stacks.isEmpty()) {
			r = stacks.pop();
			if (!r.getLeaf()) {
				List<Region> regions = getHibernateTemplate().find(
						" from Region r  where r.parent.id=?", r.getId());
				if (regions != null && regions.size() > 0) {
					for (Iterator it = regions.iterator(); it.hasNext();) {
						Region region = (Region) it.next();
						stacks.push(region);
						returnRegions.add(region);

					}

				}
			}

		}
		return returnRegions;
	}
}