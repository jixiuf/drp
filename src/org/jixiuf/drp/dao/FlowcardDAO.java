package org.jixiuf.drp.dao;

// default package

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.FlowCardDetail;
import org.jixiuf.drp.pojo.Flowcard;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

/**
 * A data access object (DAO) providing persistence and search support for
 * Flowcard entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see .Flowcard
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("unchecked")
@Component("flowcardDAO")
public class FlowcardDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FlowcardDAO.class);

	public void save(Flowcard transientInstance) {
		log.debug("saving Flowcard instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Flowcard persistentInstance) {
		log.debug("deleting Flowcard instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Flowcard findById(java.lang.String id) {
		log.debug("getting Flowcard instance with id: " + id);
		try {
			Flowcard instance = (Flowcard) hibernateTemplate.get(
					Flowcard.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Flowcard instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Flowcard as model where model."
					+ propertyName + "= ?";
			return hibernateTemplate.find(queryString, value);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Flowcard> findById(Object id) {
		return findByProperty("id", id);
	}

	public List findAll() {
		log.debug("finding all Flowcard instances");
		try {
			String queryString = "from Flowcard";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	// 返回所有status=S的flowcard
	public List<Flowcard> findAllWithStauts(PageModel pm, final String status) {
		log.debug("searching Flowcard ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "  from Flowcard where  status=? ";
		final String queryRowCountString = "select count(*)  from Flowcard  where  status= ?";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				status).get(0)).intValue());
		try {
			List<Flowcard> fs = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, status).setFirstResult(pageFirst)
									.setMaxResults(pageSize).list();
						}

					});
			return fs;

		} catch (RuntimeException re) {
			log.error("searching Flowcard failed", re);
			throw re;
		}
	}

	public Flowcard merge(Flowcard detachedInstance) {
		log.debug("merging Flowcard instance");
		try {
			Flowcard result = (Flowcard) hibernateTemplate
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Flowcard instance) {
		log.debug("attaching dirty Flowcard instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Flowcard instance) {
		log.debug("attaching clean Flowcard instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Flowcard> findAllByFiscalPeriodId(PageModel pm,
			final String fiscalPeriodId, final String status) {

		log.debug("searching Flowcard ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "  from Flowcard  fc where   fc.fiscalPeriod.id=? and fc.status=?";
		final String queryRowCountString = "select count(*)  from Flowcard  fc where fc.fiscalPeriod.id=? and fc.status=?";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				new Object[] { fiscalPeriodId, status }).get(0)).intValue());
		try {
			List<Flowcard> fs = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, fiscalPeriodId).setString(1, status)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});
			return fs;

		} catch (RuntimeException re) {
			log.error("searching Flowcard failed", re);
			throw re;
		}

	}

	public List<Flowcard> findAllFlowcardsWithStatus(PageModel pm,
			final String flowcardStatus) {
		log.debug("searching Flowcard ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "  from Flowcard  fc where   fc.status=?";
		final String queryRowCountString = "select count(*)  from Flowcard  fc where fc.status=?";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				flowcardStatus).get(0)).intValue());
		try {
			List<Flowcard> fs = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, flowcardStatus)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});
			return fs;

		} catch (RuntimeException re) {
			log.error("searching Flowcard failed", re);
			throw re;
		}
	}

	public List<Flowcard> findAllByDistribId(PageModel pm,
			final String distribId, final String status) {

		log.debug("searching Flowcard ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "  from Flowcard  fc where   fc.distrib.id=? and  fc.status=?";
		final String queryRowCountString = "select count(*)  from Flowcard  fc where fc.distrib.id=? and fc.status=?";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				new Object[] { distribId, status }).get(0)).intValue());
		try {
			List<Flowcard> fs = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, distribId).setString(1, status)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});
			return fs;

		} catch (RuntimeException re) {
			log.error("searching Flowcard failed", re);
			throw re;
		}

	}

	public List<Flowcard> findAllByDistribIdAndFiscalPeriodId(PageModel pm,
			final String distribId, final String fiscalPeriodId,
			final String status) {

		log.debug("searching Flowcard ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "  from Flowcard  fc where   fc.distrib.id=? and fc.fiscalPeriod.id=? and fc.status=?";
		final String queryRowCountString = "select count(*)  from Flowcard  fc where fc.distrib.id=? and fc.fiscalPeriod.id=? and status=?";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString,
				new Object[] { distribId, fiscalPeriodId, status }).get(0))
				.intValue());
		try {
			List<Flowcard> fs = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString).setString(
									0, distribId).setString(1, fiscalPeriodId)
									.setString(2, status).setFirstResult(
											pageFirst).setMaxResults(pageSize)
									.list();

						}

					});
			return fs;

		} catch (RuntimeException re) {
			log.error("searching Flowcard failed", re);
			throw re;
		}

	}

	public List<FlowCardDetail> findAllFlowcardDetails(String flowcardId) {
		return null;
	}

	// flowcardNo 的格式 为yyyyMMddxxxx ,其中xxxx为四位的数字如0001 0002
	// 传过来的参数是格式 为yyyyMMdd 的日期格式， 此方法查询 flowcardNo 以yyyyMMdd开头，且 xxxx
	// 最的flowcardno
	public String findMaxFlowCardNo(String formatDateString) {
		String min = formatDateString + "0000";
		String max = formatDateString + "9999";
		List<String> l = getHibernateTemplate()
				.find(
						"select max(fc.flowcardNo) from  Flowcard fc  where fc.flowcardNo>? and fc.flowcardNo<?",
						new Object[] { min, max });
		String cur = l.get(0);
		if (cur == null) {
			return min;
		} else {
			return cur;
		}

	}

	public void deleteById(String id) {

		log.debug("deleting FlowCard  instance");
		try {
			getHibernateTemplate().bulkUpdate(
					"delete  from Flowcard where  id = ?", id);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	public List<Flowcard> findByIds(final String[] flowcardIds) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				return session.createQuery(
						" select f from Flowcard f where f.id in (:ids)")
						.setParameterList("ids", flowcardIds).list();
			}
		});

	}

	/**
	 * 返回分销商id 为distribIds 中任意一个，且状态为status 的所有流向单的数量 ，用于报表统计
	 * @param distribIds
	 * @param status
	 * @return
	 */
	public int findAllFlowcardCountWithStatus(final String[] distribIds,
			final String status) {
		List<Long> countList = getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session
								.createQuery(
										"select count(*) from Flowcard fc where fc.status=:status  fc.distrib.id in (:ids) ")
								.setString("status", status).setParameterList(
										"ids", distribIds).list();

					}
				});
		return countList.get(0).intValue();

	}

	public int findAllFlowcardCountWithStatus(List<Distrib> distribs,
			String status) {
		String[] distribIds = new String[distribs.size()];
		for (int i = 0; i < distribIds.length; i++) {
			distribIds[i] = distribs.get(i).getId();
		}
		return findAllFlowcardCountWithStatus(distribIds, status);
	}

}