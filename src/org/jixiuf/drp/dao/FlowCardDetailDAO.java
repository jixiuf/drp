package org.jixiuf.drp.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.jixiuf.drp.pojo.FlowCardDetail;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component("flowCardDetailDAO")
public class FlowCardDetailDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FlowCardDetailDAO.class);
	// property constants
	public static final String MATERIAL_ID = "materialId";
	public static final String MATERIAL_COUNT = "materialCount";
	public static final String CLIENT_ID = "clientId";
	public static final String ADJUST_COUNT = "adjustCount";
	public static final String ADJUST_REASON = "adjustReason";

	public void save(FlowCardDetail transientInstance) {
		log.debug("saving FlowCardDetail instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FlowCardDetail persistentInstance) {
		log.debug("deleting FlowCardDetail instance");
		try {
			// hibernateTemplate.delete(persistentInstance);
			hibernateTemplate.bulkUpdate(
					"delete from FlowCardDetail where id =? ",
					persistentInstance.getId());
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FlowCardDetail findById(java.lang.String id) {
		log.debug("getting FlowCardDetail instance with id: " + id);
		try {
			FlowCardDetail instance = (FlowCardDetail) hibernateTemplate.get(
					"FlowCardDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FlowCardDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FlowCardDetail as model where model."
					+ propertyName + "= ?";
			return hibernateTemplate.find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FlowCardDetail> findByMaterialId(Object materialId) {
		return findByProperty(MATERIAL_ID, materialId);
	}

	public List<FlowCardDetail> findByMaterialCount(Object materialCount) {
		return findByProperty(MATERIAL_COUNT, materialCount);
	}

	public List<FlowCardDetail> findByClientId(Object clientId) {
		return findByProperty(CLIENT_ID, clientId);
	}

	public List<FlowCardDetail> findByAdjustCount(Object adjustCount) {
		return findByProperty(ADJUST_COUNT, adjustCount);
	}

	public List<FlowCardDetail> findByAdjustReason(Object adjustReason) {
		return findByProperty(ADJUST_REASON, adjustReason);
	}

	public List findAll() {
		log.debug("finding all FlowCardDetail instances");
		try {
			String queryString = "from FlowCardDetail";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FlowCardDetail merge(FlowCardDetail detachedInstance) {
		log.debug("merging FlowCardDetail instance");
		try {
			FlowCardDetail result = (FlowCardDetail) hibernateTemplate
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FlowCardDetail instance) {
		log.debug("attaching dirty FlowCardDetail instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FlowCardDetail instance) {
		log.debug("attaching clean FlowCardDetail instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void deleteFlowcardDetailByFlowcardId(String id) {
		getHibernateTemplate().bulkUpdate("delete from FlowCardDetail   fcd where  fcd.flowcard.id=? ",id);

	}
}