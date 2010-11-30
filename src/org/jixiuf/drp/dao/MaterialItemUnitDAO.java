package org.jixiuf.drp.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jixiuf.drp.pojo.MaterialItemUnit;
import org.springframework.stereotype.Component;



@Component("materialItemUnitDAO")
public class MaterialItemUnitDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(MaterialItemUnitDAO.class);

	@SuppressWarnings("unchecked")
	public List<MaterialItemUnit> findAll() {
		try {
			return getHibernateTemplate().find("from MaterialItemUnit ");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}
	/**
	 *
	 * @param id
	 * @return null or MaterialItemUnit
	 */
	public MaterialItemUnit findById(String id) {
		try {
			return (MaterialItemUnit)getHibernateTemplate().get(MaterialItemUnit.class, id);
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

}