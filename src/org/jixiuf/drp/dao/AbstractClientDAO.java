package org.jixiuf.drp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jixiuf.drp.pojo.AbstractClient;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

@Component("abstractClientDAO")
public class AbstractClientDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(AbstractClientDAO.class);

	/**
	 * return all distributions and terminals
	 *
	 * @return
	 */
	public List<AbstractClient> findAllClients(PageModel pm) {
		log.debug("searching AbstractClient ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString1 = "select c from AbstractClient c  ";

		final String queryRowCountString = "select count(*) from AbstractClient ";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString).get(
				0)).intValue());
		System.out.println(pm);
		try {
			List<AbstractClient> clients = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 分页查询
							return session.createQuery(queryString1)
									.setFirstResult(pageFirst).setMaxResults(
											pageSize).list();

						}

					});
			return clients;

		} catch (RuntimeException re) {
			log.error("searching AbstractClient failed", re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public List<AbstractClient> searchAllClients(String nameOrClientNo,
			PageModel pm) {

		log.debug("searching AbstractClient ");
		final int pageFirst = pm.getFrom();
		final int pageSize = pm.getPageSize();
		final String queryString = "select c from AbstractClient c where     c.clientno like '"
				+ nameOrClientNo
				+ "%'  or c.name like  '%"
				+ nameOrClientNo
				+ "%' ";
		final String queryRowCountString = "select count(*) from AbstractClient c where   c.clientno like '"
				+ nameOrClientNo
				+ "%'  or c.name like  '%"
				+ nameOrClientNo
				+ "%'  ";

		pm.setRowCount(((Long) hibernateTemplate.find(queryRowCountString).get(
				0)).intValue());
		System.out.println(pm);
		try {
			List<AbstractClient> clients = getHibernateTemplate().executeFind(
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
			log.error("searching AbstractClient failed", re);
			throw re;
		}

	}

	public AbstractClient findById(String id) {
		return (AbstractClient) getHibernateTemplate().get(
				AbstractClient.class, id);

	}
}
