package org.jixiuf.drp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

/**
 * A data access object (DAO) providing persistence and search support for
 * FiscalPeriod entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see org.jixiuf.drp.pojo.FiscalPeriod
 * @author MyEclipse Persistence Tools
 */

@Component("fiscalPeriodDAO")
public class FiscalPeriodDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FiscalPeriodDAO.class);
	// property constants
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String STATUS = "status";

	public void save(FiscalPeriod transientInstance) {
		log.debug("saving FiscalPeriod instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FiscalPeriod persistentInstance) {
		log.debug("deleting FiscalPeriod instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FiscalPeriod findById(java.lang.String id) {
		log.debug("getting FiscalPeriod instance with id: " + id);
		try {
			FiscalPeriod instance = (FiscalPeriod) getHibernateTemplate().get(
					"org.jixiuf.drp.pojo.FiscalPeriod", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<FiscalPeriod> findAll() {
		log.debug("finding all FiscalPeriod instances");
		try {
			String queryString = "from FiscalPeriod";
			return getHibernateTemplate().find(queryString);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 *
	 * @param pageModel
	 *            至少要设置pageSize pageNow 两属性，而其中的rowCount 可以从此方法中得到填充
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FiscalPeriod> findAll(PageModel pageModel) {
		final int pageFirst = pageModel.getFrom();
		final int pageSize = pageModel.getPageSize();
		log.debug("finding all FiscalPeriod instances by pageModel");
		try {
			List list = getHibernateTemplate().find(
					"select count(*) from FiscalPeriod   ");
			Long rowCount = (Long) list.get(0);
			pageModel.setRowCount(rowCount.intValue());
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					// 分页查询
					return session
							.createQuery(
									" from FiscalPeriod fp order by  fp.year desc, fp.month desc ")
							.setFirstResult(pageFirst).setMaxResults(pageSize)
							.list();

				}

			});
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FiscalPeriod merge(FiscalPeriod detachedInstance) {
		log.debug("merging FiscalPeriod instance");
		try {
			FiscalPeriod result = (FiscalPeriod) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FiscalPeriod instance) {
		log.debug("attaching dirty FiscalPeriod instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FiscalPeriod instance) {
		log.debug("attaching clean FiscalPeriod instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void update(FiscalPeriod fp) {
		merge(fp);

	}

	/**
	 * 判断与fp 同year month 的记录是否存在，以便更新数据时出出同一核算年月的重复现象(保证其惟一性)
	 *
	 * @param fp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isExistsYearAndMonthButMe(FiscalPeriod fp) {
		List<FiscalPeriod> list = getHibernateTemplate().find(
				"from FiscalPeriod where year=? and month=? and id !=?",
				new Object[] { fp.getYear(), fp.getMonth(), fp.getId() });

		return list.size() > 0;
	}

	@SuppressWarnings("unchecked")
	public boolean isExistsYearAndMonth(FiscalPeriod fp) {
		List<FiscalPeriod> list = getHibernateTemplate().find(
				"from FiscalPeriod where year=? and month=? ",
				new Object[] { fp.getYear(), fp.getMonth() });

		return list.size() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<FiscalPeriod> findAllAvilable() {
		return hibernateTemplate
				.find(" from FiscalPeriod fp  where  fp.status='Y' order by  fp.year desc, fp.month desc  ");

	}

}