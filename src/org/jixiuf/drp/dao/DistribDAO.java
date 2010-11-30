package org.jixiuf.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.DistribType;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.Region;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

/**
 * A data access object (DAO) providing persistence and search support for
 * Client entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.jixiuf.drp.pojo.AbstractClient
 * @author MyEclipse Persistence Tools
 */

@Component("distribDAO")
public class DistribDAO extends AbstractClientDAO {
	private static final Log log = LogFactory.getLog(DistribDAO.class);
	// property constants
	public static final String CLIENTNO = "clientno";
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String ZIPCODE = "zipcode";
	public static final String CONTACTOR = "contactor";

	public void save(Distrib transientInstance) {
		log.debug("saving Distrib instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Distrib persistentInstance) {
		log.debug("deleting Distrib instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Distrib> findByProperty(String propertyName, Object value) {
		log.debug("finding Distrib instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Distrib as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Distrib> findByClientno(Object clientno) {
		return findByProperty(CLIENTNO, clientno);
	}

	@SuppressWarnings("unchecked")
	public List<Distrib> findAll() {
		log.debug("finding all Distrib instances");
		try {
			String queryString = "from Distrib";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Distrib merge(Distrib detachedInstance) {
		log.debug("merging Distrib instance");
		try {
			Distrib result = (Distrib) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Distrib instance) {
		log.debug("attaching dirty Distrib instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Distrib instance) {
		log.debug("attaching clean Distrib instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 返回删除的记录数, 删除regionid 为regionid 的所有分销商
	 * 
	 * @param regionId
	 * @return
	 */
	public int deleteDistriByRegionId(String regionId) {
		log.debug("delete Distributions in regionId  ");
		try {
			return getHibernateTemplate().bulkUpdate(
					"delete from Distrib c where c.region.id=?    ", regionId);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	/**
	 * 查所有分销商
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Distrib> findAllDistrib() {
		log.debug("finding all distributions   instances");
		try {
			String queryString = "  select c   from Distrib c  left outer join  c.region r     ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all 分销商  failed", re);
			throw re;
		}
	}

	/**
	 * 查所有分销商
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Distrib> findAllDistrib(PageModel pm) {

		log.debug("searching  Distrib ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "from Distrib";
		final String queryRowCountString = "select count(*)  from Distrib   ";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString).get(
				0)).intValue());
		try {
			List<Distrib> clients = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});
			return clients;

		} catch (RuntimeException re) {
			log.error("searching Distrib failed", re);
			throw re;
		}

	}

	@Override
	public Distrib findById(java.lang.String id) {
		log.debug("getting Distrib instance with id: " + id);
		try {
			Distrib instance = (Distrib) getHibernateTemplate().get(
					Distrib.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * 搜索所有分销商
	 * 
	 * @param nameOrClientNo
	 * @param pm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Distrib> searchAllDistribs(String nameOrClientNo, PageModel pm) {

		log.debug("searching Distrib ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "select c from Distrib c where c.clientno like '"
				+ nameOrClientNo
				+ "%'  or c.name like  '%"
				+ nameOrClientNo
				+ "%' ";
		final String queryRowCountString = "select count(*) from Distrib c where   c.clientno like '"
				+ nameOrClientNo
				+ "%'  or c.name like  '%"
				+ nameOrClientNo
				+ "%' ";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString).get(
				0)).intValue());
		System.out.println(pm);
		try {
			List<Distrib> clients = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});
			return clients;

		} catch (RuntimeException re) {
			log.error("searching Distrib failed", re);
			throw re;
		}

	}

	/**
	 * 
	 * @param regionIds
	 * @return返回 处于regionIds这些地区的分销商的数量
	 */
	@SuppressWarnings("unchecked")
	public int findDistribsCountInRegions(final String[] regionIds) {
		final String sql = "select count(*) from Distrib d  where d.region.id in (:regionIds) ";
		List<Long> counts = getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createQuery(sql).setParameterList(
								"regionIds", regionIds).list();

					}
				});

		return counts.get(0).intValue();
	}

	/**
	 * 返回所有处于这些地区regionIds 中,并且distribType为type 的分销商的数量
	 * 
	 * @param ids
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	public int findDistribsCountInRegionsWithType(final String[] regionIds,
			final DistribType type) {
		final String sql = "select count(*) from Distrib d  where d.distribType.id=:typeId and  d.region.id in (:regionIds) ";
		List<Long> counts = getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createQuery(sql).setString("typeId",
								type.getId()).setParameterList("regionIds",
								regionIds).list();

					}
				});

		return counts.get(0).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Distrib> findAllDistrib(List<Region> regions) {
		final String ids[] = new String[regions.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = regions.get(i).getId();
		}
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(
						"from Distrib b where b.region.id in (:ids)")
						.setParameterList("ids", ids).list();
			}
		});
	}

	public  Integer  findDistribCount( DistribType dt ) {
	 
	 
		return 	 ((Long) getHibernateTemplate().find(
					"select count(*) from Distrib d where d.distribType.id=?  ",
					dt.getId()).get(0)).intValue() ;

 
 
	}

	
	public Integer findSpottedDistribInFP( DistribType dt,FiscalPeriod fp) {
		int count = ((Long) (getHibernateTemplate()
				.find(
						" select count( distinct fc.distrib)   from   Flowcard fc  left outer join fc.distrib d    where  fc.fiscalPeriod.id=? and fc.spotter is  not null and d.distribType.id=? ",new Object[] {fp.getId(),dt.getId()})
				.get(0))).intValue();

		return count;
	}
}