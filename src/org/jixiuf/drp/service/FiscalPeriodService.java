package org.jixiuf.drp.service;

import java.util.List;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.FiscalPeriodDAO;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.stereotype.Component;

/**
 * 会计核算期间维护 service
 *
 * @author Administrator
 *
 */
@Component("fiscalPeriodService")
public class FiscalPeriodService {

	FiscalPeriodDAO dao;

	public FiscalPeriodDAO getDao() {
		return dao;
	}

	@Resource(name = "fiscalPeriodDAO")
	public void setDao(FiscalPeriodDAO dao) {
		this.dao = dao;
	}

	public FiscalPeriodService() {
	}

	// /---------------------------------------------------------

	public List<FiscalPeriod> findAll(PageModel pageModel) {
		return dao.findAll(pageModel);
	}

	public FiscalPeriod findById(String id) {
		return dao.findById(id);

	}

	public String update(FiscalPeriod fp) {
		boolean existsAlready = dao.isExistsYearAndMonthButMe(fp);
		if (!existsAlready) {
			dao.update(fp);
			return "";
		}
		return "同一核算年，核算月 的记录已经存在，你需要 更改当前数据 以保证核算期的惟一 性 ，或者删除";
	}

	public String addFiscalPeriod(FiscalPeriod fp) {
		boolean existsAlready = dao.isExistsYearAndMonth(fp);
		if (existsAlready) {
			return "相同核算年月的记录已经存在";
		} else {
			dao.save(fp);
			return "";
		}

	}

	public List<FiscalPeriod> findAllAvilable( ) {
		return dao.findAllAvilable( );
	}

	public List<FiscalPeriod> findAll() {
		return dao.findAll();
	}
}
