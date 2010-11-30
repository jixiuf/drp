package org.jixiuf.drp.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jixiuf.drp.pojo.DistribType;
import org.springframework.stereotype.Component;



@Component("distribTypeDAO")
public class DistribTypeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DistribTypeDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String TYPE_ID = "typeId";
	/**
	 * 返回所有的分销商级别（类型）
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DistribType> findAll() {
		try {
			return getHibernateTemplate().find("from DistribType ");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}
	/**
	 *
	 * @param id
	 * @return null or DistribType
	 */
	public DistribType findById(String id) {
		try {
			return (DistribType)getHibernateTemplate().get(DistribType.class, id);
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

}