package org.jixiuf.drp.dao;

import java.util.List;

import org.jixiuf.drp.pojo.Region;
import org.springframework.stereotype.Component;

@Component("terminalDAO")
public class TerminalDAO extends AbstractClientDAO {
	/**
	 * 查询region地区下的终端客户数量
	 *
	 * @param region
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int findTerminalCount(Region region) {
		List<Long> count = getHibernateTemplate().find(
				"select count(*) from Terminal t  where  t.region.id=? ",
				region.getId());
		return count.get(0).intValue();
	}
}
