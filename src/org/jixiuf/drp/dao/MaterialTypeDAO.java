package org.jixiuf.drp.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jixiuf.drp.pojo.MaterialType;
import org.springframework.stereotype.Component;



@Component("materialTypeDAO")
public class MaterialTypeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(MaterialTypeDAO.class);

	@SuppressWarnings("unchecked")
	public List<MaterialType> findAll() {
		try {
			return getHibernateTemplate().find("from MaterialType ");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}
	/**
	 *
	 * @param id
	 * @return null or MaterialType
	 */
	public MaterialType findById(String id) {
		try {
			return (MaterialType)getHibernateTemplate().get(MaterialType.class, id);
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

}