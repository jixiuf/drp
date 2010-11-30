package org.jixiuf.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.Inventory;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

/**
 * A data access object (DAO) providing persistence and search support for
 * Inventory entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see org.jixiuf.drp.pojo.Inventory
 * @author MyEclipse Persistence Tools
 */

@Component("inventoryDAO")
public class InventoryDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(InventoryDAO.class);
	// property constants
	public static final String INITCOUNT = "initcount";
	public static final String INCOUNT = "incount";
	public static final String OUTCOUNT = "outcount";

	public void save(Inventory transientInstance) {
		log.debug("saving Inventory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Inventory persistentInstance) {
		log.debug("deleting Inventory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Inventory findById(String id) {
		log.debug("getting Inventory instance with id: " + id);
		try {
			Inventory instance = (Inventory) getHibernateTemplate().get(
					"org.jixiuf.drp.pojo.Inventory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Inventory merge(Inventory detachedInstance) {
		log.debug("merging Inventory instance");
		try {
			Inventory result = (Inventory) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/**
	 * 查出有关这些物料相关的库存记录 决定是否可以删除某个物料 ids 是一个Material.id 的数组 ，
	 *
	 * @param ids
	 * @return 返回以Inventory.material.id 在ids 中 的个数
	 */
	public int findCountOfInventory(final String[] materialIds) {
		try {
			Object o = hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session
							.createQuery(
									"select count(*) from Inventory i where i.material.id in (:ids )")
							.setParameterList("ids", materialIds)
							.uniqueResult();
				}
			});
			return ((Long) o).intValue();

		} catch (RuntimeException re) {
			log.error(
					"find count of inventory where i.materials in ids failed",
					re);
			throw re;
		}

	}

	/**
	 *
	 * 查询所有Inventory.status='N' 的Inventory 库存信息 ,用于分销商库存数量初始化 界面 的显示
	 *
	 * @param pm
	 * @return null or Inventorys by pageModel Inventorys.fiscalPeriod 并未查出
	 */
	@SuppressWarnings("unchecked")
	public List<Inventory> findAllNoneAvailable(PageModel pm) {
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		log.debug("finding all Inventory instances by pageModel");
		List<Inventory> inventorys = new ArrayList<Inventory>();
		try {
			pm
					.setRowCount(((Long) hibernateTemplate
							.find(
									" select count(*) from Inventory it left outer join it.material   left outer join it. material.materialItemUnit left outer join it.distrib where it.status=?",
									Inventory.NONE_AVAILABLE).get(0))
							.intValue());
			List<Object[]> items = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session
									.createQuery(
											"from Inventory it left outer join it.material   left outer join it. material.materialItemUnit left outer join it.distrib where it.status=:statusPos")
									.setString("statusPos",
											Inventory.NONE_AVAILABLE)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});

			Inventory it = null;

			for (Object[] o : items) {
				it = (Inventory) o[0];
				inventorys.add(it);

			}

			return inventorys;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 查出所有 Inventory.material.no 为参数的 处于初始化状态（即Inventory.status ='N'）的Inventory
	 *
	 * @param no
	 *            Inventory.material.no
	 * @param pm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Inventory> findAllNoneAvailableInventorysByMaterialNo(
			final String materialNo, PageModel pm) {

		log.debug("searching  Inventory ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "   from Inventory it  left outer join  it.material left outer join it.distrib where it.status=?  and it.material.no=?";
		final String queryRowCountString = "select count(*)  from Inventory it  where it.status=?  and it.material.no=?";
		List<Inventory> inventorys = new ArrayList<Inventory>();

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				new Object[] { Inventory.NONE_AVAILABLE, materialNo }).get(0))
				.intValue());
		try {
			List<Object[]> items = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, Inventory.NONE_AVAILABLE).setString(1,
									materialNo).setFirstResult(pageFirst)
									.setMaxResults(pageSize).list();

						}

					});
			Inventory i;
			Distrib c;
			Material m;

			for (Object[] o : items) {
				i = ((Inventory) o[0]);
				c = ((Distrib) o[2]);
				m = ((Material) o[1]);
				i.setMaterial(m);
				i.setDistrib(c);
				inventorys.add(i);

			}

			return inventorys;

		} catch (RuntimeException re) {
			log.error("searching Inventory failed", re);
			throw re;
		}
	}

	/**
	 * 查出所有 Inventory.distrib.clientno 为参数的 处于初始化状态（即Inventory.status
	 * ='N'）的Inventory
	 *
	 * @param no
	 *            Inventory.distrib.clientno
	 * @param pm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Inventory> findAllNoneAvailableInventorysByClientNo(
			final String clientNo, PageModel pm) {

		log.debug("searching  Inventory ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "   from Inventory it  left outer join it.distrib left outer join  it.material  where it.status=?  and it.distrib.clientno=?";
		final String queryRowCountString = "select count(*)  from Inventory it  where it.status=?  and it.distrib.clientno=?";
		List<Inventory> inventorys = new ArrayList<Inventory>();
		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				new Object[] { Inventory.NONE_AVAILABLE, clientNo }).get(0))
				.intValue());
		try {
			List<Object[]> items = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, Inventory.NONE_AVAILABLE).setString(1,
									clientNo).setFirstResult(pageFirst)
									.setMaxResults(pageSize).list();

						}

					});
			Inventory i;
			Distrib c;
			Material m;

			for (Object[] o : items) {
				i = ((Inventory) o[0]);
				c = ((Distrib) o[1]);
				m = ((Material) o[2]);
				i.setMaterial(m);
				i.setDistrib(c);
				inventorys.add(i);

			}

			return inventorys;

		} catch (RuntimeException re) {
			log.error("searching Inventory failed", re);
			throw re;
		}
	}

	/**
	 * 查出所有 Inventory.material.no Inventory.distrib.clientno 为参数的
	 * 处于初始化状态（即Inventory.status ='N'）的Inventory
	 *
	 * @param materialNo
	 *            Inventory.material.no
	 * @param clientNo
	 *            Inventory.distrib.clientno
	 * @param pm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Inventory> findAllNoneAvailableInventorysByClientNoAndMaterialNo(
			final String materialNo, final String clientNo, PageModel pm) {
		log.debug("searching  Inventory ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "   from Inventory it  left outer join it.distrib left outer join  it.material  where it.status=?  and it.distrib.clientno=? and it.material.no=?";
		final String queryRowCountString = "select count(*)  from Inventory it  where it.status=?  and it.distrib.clientno=? and it.material.no=?";
		List<Inventory> inventorys = new ArrayList<Inventory>();

		pm.setRowCount(((Long) hibernateTemplate
				.find(
						queryRowCountString,
						new Object[] { Inventory.NONE_AVAILABLE, clientNo,
								materialNo }).get(0)).intValue());
		try {
			List<Object[]> items = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, Inventory.NONE_AVAILABLE).setString(1,
									clientNo).setString(2, materialNo)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});

			Inventory i;
			Distrib c;
			Material m;
			for (Object[] o : items) {
				i = ((Inventory) o[0]);
				c = ((Distrib) o[1]);
				m = ((Material) o[2]);
				i.setMaterial(m);
				i.setDistrib(c);
				inventorys.add(i);

			}

			return inventorys;

		} catch (RuntimeException re) {
			log.error("searching Inventory failed", re);
			throw re;
		}
	}

	/**
	 * 查询处于初始化状态（Inventory.status=N） 的，且it.material.id=materialId
	 * ,it.distrib.id=clientId 的清单是否存在
	 *
	 * @param materialId
	 * @param clientId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getCountOfNoneAvailableInventoryByMaterialIdAndClientId(
			String materialId, String clientId) {

		try {
			List<Long> countList = getHibernateTemplate()
					.find(
							"select count(*) from  Inventory it where it.status=? and it.material.id=? and it.distrib.id=?",
							new Object[] { Inventory.NONE_AVAILABLE,
									materialId, clientId });
			Long count = countList.get(0);
			return count.intValue();
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}

	}

	/**
	 * 此方法旨在查出所有处于初始化状态的存货清单，前提是已经确定 materialId clientId 两参数只对应一条相应数据 ，
	 * 建议使用此法前保证getCountOfNoneAvailableInventoryByMaterialIdAndClientId（）==1
	 *
	 * @param materialId
	 * @param clientId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Inventory findNoneAvailableInventoryByMaterialIdAndClientId(
			String materialId, String clientId) {
		try {
			List<Inventory> list = getHibernateTemplate()
					.find(
							"select it from  Inventory it where it.status=? and it.material.id=? and it.distrib.id=?",
							new Object[] { Inventory.NONE_AVAILABLE,
									materialId, clientId });

			return list.get(0);
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/**
	 * 删除 处于初始化状态 的库存清单
	 *
	 * @param inventoryIds
	 * @return 返回删除的记录数
	 */
	public int deleteAllNoneAvailableInventorys(final String[] inventoryIds) {
		log.debug("delete all  Inventory id in ids  ");
		try {
			Object o = hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(
							"delete from Inventory where id in (:ids)")
							.setParameterList("ids", inventoryIds)
							.executeUpdate();
				}
			});
			log.debug("delete successful");
			return ((Integer) o).intValue();

		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Inventory> findBYIds(final String[] inventoryIds) {
		log.debug("find   Inventory   ");
		try {
			Object o = hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(
							"  from Inventory where id in (:ids)")
							.setParameterList("ids", inventoryIds).list();
				}
			});
			log.debug("find successful");

			return (List<Inventory>) o;

		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}

	public int changeStatusOfInventorys(final String[] inventoryIds,
			final String inventoryStatus) {
		log.debug("update   Inventory   ");
		try {
			Object o = hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session
							.createQuery(
									"   update  Inventory it set it.status=:statusPos where id in (:ids)")
							.setString("statusPos", inventoryStatus)
							.setParameterList("ids", inventoryIds)
							.executeUpdate();
				}
			});
			log.debug("update successful");
			return ((Integer) o).intValue();

		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Inventory> findallSendedInventorys(PageModel pm) {
		log.debug("searching  Inventory ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "   from Inventory it  left outer join it.distrib left outer join  it.material  where it.status=?   ";
		final String queryRowCountString = "select count(*)  from Inventory it  where it.status=?  ";
		List<Inventory> inventorys = new ArrayList<Inventory>();
		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				Inventory.SEND_TO_CHECK).get(0)).intValue());
		try {
			List<Object[]> items = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, Inventory.SEND_TO_CHECK).setFirstResult(
									pageFirst).setMaxResults(pageSize).list();

						}

					});
			Inventory i;
			Distrib c;
			Material m;

			for (Object[] o : items) {
				i = ((Inventory) o[0]);
				c = ((Distrib) o[1]);
				m = ((Material) o[2]);
				i.setMaterial(m);
				i.setDistrib(c);
				inventorys.add(i);

			}

			return inventorys;

		} catch (RuntimeException re) {
			log.error("searching Inventory failed", re);
			throw re;
		}
	}

}