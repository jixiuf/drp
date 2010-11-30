package org.jixiuf.drp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

/**
 * A data access object (DAO) providing persistence and search support for
 * Material entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see org.jixiuf.drp.pojo.Material
 * @author MyEclipse Persistence Tools
 */

@Component("materialDAO")
public class MaterialDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(MaterialDAO.class);
	// property constants
	public static final String NO = "no";
	public static final String NAME = "name";
	public static final String GUIGE = "guige";
	public static final String XINGHAO = "xinghao";
	public static final String PICT_FILENAME = "pictFilename";

	public void save(Material transientInstance) {
		log.debug("saving Material instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Material persistentInstance) {
		log.debug("deleting Material instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Material findById(java.lang.String id) {
		log.debug("getting Material instance with id: " + id);
		try {
			Material instance = (Material) getHibernateTemplate().get(
					"org.jixiuf.drp.pojo.Material", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Material instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Material as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Material> findByNo(Object no) {
		return findByProperty(NO, no);
	}

	@SuppressWarnings("unchecked")
	public List findAll() {
		log.debug("finding all Material instances");
		try {
			String queryString = "from Material";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Material merge(Material detachedInstance) {
		log.debug("merging Material instance");
		try {
			Material result = (Material) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Material instance) {
		log.debug("attaching dirty Material instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Material instance) {
		log.debug("attaching clean Material instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Material> findAll(PageModel pm) {
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		log.debug("finding all Material instances by pageModel");
		try {
			Long rowCount = (Long) getHibernateTemplate().find(
					"select count(*) from Material   ").get(0);
			pm.setRowCount(rowCount.intValue());
			log.debug(pm);
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					// 分页查询
					return session.createQuery(" from Material")
							.setFirstResult(pageFirst).setMaxResults(pageSize)
							.list();

				}

			});
		} catch (RuntimeException re) {
			log.error("find all Material  failed", re);
			throw re;
		}

	}

	/**
	 * 物料可能有其相应的图片，根据
	 *
	 * @param ids
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<String> getPictureFiles(final String[] ids) {

		try {
			Object o = hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session
							.createQuery(
									"select m.pictFilename from Material m  where id in (:ids)")
							.setParameterList("ids", ids).list();
				}
			});

			return (List<String>) o;

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public int delMaterials(final String[] ids) {
		log.debug("delete all  Materials id in ids  ");
		try {
			Object o = hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(
							"delete from Material where id in (:ids)")
							.setParameterList("ids", ids).executeUpdate();
				}
			});
			log.debug("delete successful");
			return ((Integer) o).intValue();

		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	public void updatePictFileName(Material m) {
		try {
			getHibernateTemplate().bulkUpdate(
					"update Material m set m.pictFilename=? where m.id=? ",
					new Object[] { m.getPictFilename(), m.getId() });

		} catch (Exception e) {
			log.error("update Material.pictFilename failed");
		}

	}

	@SuppressWarnings("unchecked")
	public List<Material> searchMaterials(String materialNameOrNo, PageModel pm) {
		log.debug("searching materials ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "select m from Material m where m.no like '"
				+ materialNameOrNo + "%'  or m.name like  '%"
				+ materialNameOrNo + "%'";
		final String queryRowCountString = "select count(*) from Material m where m.no like '"
				+ materialNameOrNo
				+ "%'  or m.name like  '%"
				+ materialNameOrNo + "%'";
		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString).get(
				0)).intValue());

		try {
			List<Material> ms = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();
						}
					});
			return ms;
		} catch (RuntimeException re) {
			log.error("searching materials failed", re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Material> findAllMaterials(PageModel pm) {
		log.debug("searching materials ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "  from Material ";
		final String queryRowCountString = "select count(*)  from Material";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString).get(
				0)).intValue());
		try {
			List<Material> ms = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setFirstResult(
									pageFirst).setMaxResults(pageSize).list();

						}

					});
			return ms;

		} catch (RuntimeException re) {
			log.error("searching materials failed", re);
			throw re;
		}
	}
}