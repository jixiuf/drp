package org.jixiuf.drp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.DistribDAO;
import org.jixiuf.drp.dao.FiscalPeriodDAO;
import org.jixiuf.drp.dao.FlowCardDetailDAO;
import org.jixiuf.drp.dao.FlowcardDAO;
import org.jixiuf.drp.dao.MaterialDAO;
import org.jixiuf.drp.dao.TerminalDAO;
import org.jixiuf.drp.pojo.AbstractClient;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.FlowCardDetail;
import org.jixiuf.drp.pojo.Flowcard;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.stereotype.Component;

@Component("flowcardService")
public class FlowcardService {

	FlowcardDAO dao;
	FiscalPeriodDAO fpDao;
	MaterialDAO mDao;
	FlowCardDetailDAO fcdDao;
	DistribDAO distribDAO;

	public DistribDAO getDistribDAO() {
		return distribDAO;
	}

	@Resource(name = "distribDAO")
	public void setDistribDAO(DistribDAO distribDAO) {
		this.distribDAO = distribDAO;
	}

	public TerminalDAO getTerminalDAO() {
		return terminalDAO;
	}

	@Resource(name = "terminalDAO")
	public void setTerminalDAO(TerminalDAO terminalDAO) {
		this.terminalDAO = terminalDAO;
	}

	TerminalDAO terminalDAO;

	public FlowCardDetailDAO getFcdDao() {
		return fcdDao;
	}

	@Resource(name = "flowCardDetailDAO")
	public void setFcdDao(FlowCardDetailDAO fcdDao) {
		this.fcdDao = fcdDao;
	}

	public FiscalPeriodDAO getFpDao() {
		return fpDao;
	}

	@Resource(name = "fiscalPeriodDAO")
	public void setFpDao(FiscalPeriodDAO fpDao) {
		this.fpDao = fpDao;
	}

	public FlowcardDAO getDao() {
		return dao;
	}

	public MaterialDAO getmDao() {
		return mDao;
	}

	@Resource(name = "materialDAO")
	public void setmDao(MaterialDAO mDao) {
		this.mDao = mDao;
	}

	@Resource(name = "flowcardDAO")
	public void setDao(FlowcardDAO dao) {
		this.dao = dao;
	}

	public List<Flowcard> findAllWithStauts(PageModel pm, String status) {
		return dao.findAllWithStauts(pm, status);

	}

	public List<Flowcard> findAllFlowcardsWithStatusL(PageModel pm) {
		return dao.findAllFlowcardsWithStatus(pm, Flowcard.STATUS_L);
	}

	public List<Flowcard> findAllFlowcardsWithStatusS(PageModel pm) {
		return dao.findAllFlowcardsWithStatus(pm, Flowcard.STATUS_S);
	}

	public List<Flowcard> findAllFlowcardsWithStatusA(PageModel pm) {
		return dao.findAllFlowcardsWithStatus(pm, Flowcard.STATUS_A);
	}

	public List<Flowcard> findAllByFiscalPeriodIdWithStatus(PageModel pm,
			String fiscalPeriodId, String status) {
		return dao.findAllByFiscalPeriodId(pm, fiscalPeriodId, status);
	}

	public List<Flowcard> findAllByDistribIdAndStatus(PageModel pm,
			String distribId, String status) {
		return dao.findAllByDistribId(pm, distribId, status);

	}

	public List<Flowcard> findAllByDistribIdAndFiscalPeriodIdAndStatus(
			PageModel pm, String distribId, String fiscalPeriodId, String status) {

		return dao.findAllByDistribIdAndFiscalPeriodId(pm, distribId,
				fiscalPeriodId, status);
	}

	public Flowcard findById(String id) {
		return dao.findById(id);
	}

	public List<FlowCardDetail> findAllFlowcardDetails(String flowcardId) {

		return dao.findAllFlowcardDetails(flowcardId);
	}

	public String modFlowcard(Flowcard fc) {

		// 首先检查
		if (fc.getDistrib() == null || fc.getDistrib().getId() == null
				|| fc.getDistrib().getId().equals("")) {
			return "供方分销商不能为空";
		} else if (fc.getFlowcardDetails() == null
				|| fc.getFlowcardDetails().size() == 0) {
			return "请至少添一个终端客户";
		} else {

			fc.setFiscalPeriod(fpDao.findById(fc.getFiscalPeriod().getId()));
			fcdDao.deleteFlowcardDetailByFlowcardId(fc.getId());
			List<FlowCardDetail> flowCardDetails = new ArrayList<FlowCardDetail>();
			for (FlowCardDetail fcd : fc.getFlowcardDetails()) {
				if (fcd == null) {
					// do nothing ,maybe a empty flowcardDetail row
				} else {
					fcd.setMaterial(mDao.findById(fcd.getMaterial().getId()));
					AbstractClient c = distribDAO.findById(fcd.getClient()
							.getId());
					if (c == null) {
						c = terminalDAO.findById(fcd.getClient().getId());
					}
					fcd.setClient(c);
					fcd.setFlowcard(fc);
					flowCardDetails.add(fcd);
					fcdDao.save(fcd);
				}
			}

			fc.setFlowcardDetails(flowCardDetails);
			dao.merge(fc);
			return "";
		}
	}

	public String addFlowcard(Flowcard fc) {
		// 首先检查
		if (fc.getDistrib().getId() == null
				|| fc.getDistrib().getId().equals("")) {
			return "供方分销商不能为空";
		} else if (fc.getFlowcardDetails() == null
				|| fc.getFlowcardDetails().size() == 0) {
			return "请至少添一个终端客户";
		} else {

			fc.setFiscalPeriod(fpDao.findById(fc.getFiscalPeriod().getId()));
			fc.setFlowcardNo(genFlowCardNo(new Date()));
			List<FlowCardDetail> flowCardDetails = new ArrayList<FlowCardDetail>();
			for (FlowCardDetail fcd : fc.getFlowcardDetails()) {
				if (isSameMaterialAndClientExists(flowCardDetails, fcd)) {
					return "相同物料代码和需方客户的数据存在至少两条以上，请返回进行检查，\n<br />其物料名称为"
							+ fcd.getMaterial().getName() + "\n <br />需方客户为"
							+ fcd.getClient().getName();
				} else {
					fcd.setMaterial(mDao.findById(fcd.getMaterial().getId()));
					AbstractClient c = distribDAO.findById(fcd.getClient()
							.getId());
					if (c == null) {
						c = terminalDAO.findById(fcd.getClient().getId());
					}
					fcd.setClient(c);
					fcd.setFlowcard(fc);
					flowCardDetails.add(fcd);
					fcdDao.save(fcd);

				}
			}

			fc.setFlowcardDetails(flowCardDetails);
			dao.save(fc);
			return "";
		}

	}

	// flowcard.flowcard_no 为程序自动 生成格式为yyyyMMddxxxx ,xxxx为自增的一个数字
	private String genFlowCardNo(Date d) {

		String formatDateString = new SimpleDateFormat("yyyyMMdd").format(d);
		System.out.println(formatDateString);
		String cur = dao.findMaxFlowCardNo(formatDateString);
		int num = Integer.parseInt(cur.substring(8));
		num++;
		String numString = "" + num;
		while (numString.length() < 4) {
			numString = "0" + numString;
		}
		return formatDateString + numString;

	}

	// 如果flowcardDetails 已经含有与fcd 相同material 和client 的 FlowCardDetail ,return
	// true;
	private boolean isSameMaterialAndClientExists(
			List<FlowCardDetail> flowcardDetails, FlowCardDetail fcd) {
		for (FlowCardDetail f : flowcardDetails) {
			if (f.getMaterial().getId().equals(fcd.getMaterial().getId())
					&& f.getClient().getId().equals(fcd.getClient().getId())) {
				return true;

			}

		}

		return false;
	}

	public void deleteFlowcards(String[] ids) {
		for (String id : ids) {
			Flowcard f = dao.findById(id);
			System.out.println("fc.id" + id);
			for (FlowCardDetail fd : f.getFlowcardDetails()) {
				fcdDao.delete(fd);
			}
			dao.deleteById(id);
		}
		System.out.println("end of service.deleteFlowcards");

	}

	// 批量 update flowcard.status 由录入 状态转成送审状态
	public String updateStatus(String[] flowcardIds, String flowcardStatus) {
		List<Flowcard> flowcards = dao.findByIds(flowcardIds);
		for (Flowcard fc : flowcards) {
			fc.setStatus(flowcardStatus);
			dao.attachDirty(fc);
		}

		return "";
	}

	public String updateAdjusterAndStatus(String[] flowcardIds, User adjuster,
			String flowcardStatus) {
		List<Flowcard> flowcards = dao.findByIds(flowcardIds);
		for (Flowcard fc : flowcards) {
			fc.setStatus(flowcardStatus);
			fc.setAdjuster(adjuster);
			fc.setAdjusterDate(new Date());
			dao.attachDirty(fc);
		}

		return "";
	}

	public void modAdjustFlowcard(Flowcard fc) {
		Flowcard oldFlowcard = dao.findById(fc.getId());
		Map<String, FlowCardDetail> fcdInMap = list2Map(oldFlowcard
				.getFlowcardDetails());

		FlowCardDetail fcdTmp;
		for (FlowCardDetail fcd : fc.getFlowcardDetails()) {
			fcdTmp = fcdInMap.get(fcd.getId());
			fcdTmp.setAdjustCount(fcd.getAdjustCount());
			fcdTmp.setAdjustReason(fcd.getAdjustReason());
			fcdDao.attachDirty(fcdTmp);

		}
		oldFlowcard.setAdjuster(fc.getAdjuster());
		oldFlowcard.setAdjusterDate(fc.getAdjusterDate());
		dao.attachDirty(oldFlowcard);

	}

	// 以fcd 的id 为key
	private Map<String, FlowCardDetail> list2Map(List<FlowCardDetail> fcds) {
		Map<String, FlowCardDetail> map = new HashMap<String, FlowCardDetail>();
		for (FlowCardDetail fcd : fcds) {
			map.put(fcd.getId(), fcd);
		}

		return map;

	}

	public void modFlowcardSpotInfo(Flowcard fc) {
		Flowcard fc2 = dao.findById(fc.getId());
		fc2.setSpotDate(fc.getSpotDate());
		fc2.setSpotDesc(fc.getSpotDesc());
		fc2.setSpotter(fc.getSpotter());
		dao.attachDirty(fc2);
	}

	public void modFlowcardsForFuShen(String[] ids, User fushener) {
		List<Flowcard> flowcards = dao.findByIds(ids);
		for (Flowcard f : flowcards) {
			f.setFushen(fushener);
			f.setFushenDate(new Date());
			f.setStatus(Flowcard.STATUS_F);
			dao.attachDirty(f);

		}

	}

	public void modRejectFuShenFlowcards(String[] ids, User fushener) {
		List<Flowcard> flowcards = dao.findByIds(ids);
		for (Flowcard f : flowcards) {
			f.setFushen(fushener);
			f.setFushenDate(new Date());
			f.setStatus(Flowcard.STATUS_S); //置回送审态
			dao.attachDirty(f);

		}

	}

	public int findAllFlowcardCountWithStatusA(List<Distrib> distribs) {

		return dao.findAllFlowcardCountWithStatus(distribs,Flowcard.STATUS_A);
	}
}
