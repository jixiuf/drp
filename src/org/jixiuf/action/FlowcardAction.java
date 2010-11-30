package org.jixiuf.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jixiuf.drp.pojo.AbstractClient;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.Flowcard;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.drp.service.DistribService;
import org.jixiuf.drp.service.FiscalPeriodService;
import org.jixiuf.drp.service.FlowcardService;
import org.jixiuf.drp.service.MaterialService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component("flowcardAction")
@Scope("prototype")
public class FlowcardAction extends ActionSupport implements RequestAware,
		SessionAware {

	PageModel pm = new PageModel();
	Flowcard fc = new Flowcard();
	Map<String, Object> req;
	Map<String, Object> session;
	FlowcardService fService;
	FiscalPeriodService fpService;
	DistribService distribService;
	MaterialService mService;
	String searchString;

	public PageModel getPm() {
		return pm;
	}

	public void setPm(PageModel pm) {
		this.pm = pm;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Flowcard getFc() {
		return fc;
	}

	public void setFc(Flowcard fc) {
		this.fc = fc;
	}

	public FlowcardService getfService() {
		return fService;
	}

	@Resource(name = "flowcardService")
	public void setfService(FlowcardService fService) {
		this.fService = fService;
	}

	public FiscalPeriodService getFpService() {
		return fpService;
	}

	@Resource(name = "fiscalPeriodService")
	public void setFpService(FiscalPeriodService fpService) {
		this.fpService = fpService;
	}

	public DistribService getDistribService() {
		return distribService;
	}

	@Resource(name = "distribService")
	public void setDistribService(DistribService distribService) {
		this.distribService = distribService;
	}

	public MaterialService getmService() {
		return mService;
	}

	@Resource(name = "materialService")
	public void setmService(MaterialService mService) {
		this.mService = mService;
	}

	public void setRequest(Map<String, Object> req) {
		this.req = req;

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	// --------------------------------------------

	public String listFlowcards() {
		// 查出所出录入状态的flowcards
		List<Flowcard> flowcards = this.getfService()
				.findAllFlowcardsWithStatusL(pm);
		req.put("flowcards", flowcards);

		List<FiscalPeriod> fps = fpService.findAllAvilable();
		// List<FiscalPeriod> fps = fpService.findAll( );

		req.put("fps", fps);
		return "flowcard_list_jsp";

	}

	// 查所有处于录入状态的
	public String searchFlowCardsByClientIdAndFiscalPeriodid() {

		List<Flowcard> flowcards = null;
		// 如果client material 都 为空 ，查出所有Inventory
		if ("".equals(fc.getDistrib().getId().trim())
				&& "".equals(fc.getFiscalPeriod().getId().trim())) {
			flowcards = fService.findAllWithStauts(pm, fc.getStatus());
		} else if ("".equals(fc.getDistrib().getId().trim())) {// 如果只是
			flowcards = fService.findAllByFiscalPeriodIdWithStatus(pm, fc
					.getFiscalPeriod().getId(), fc.getStatus());
		} else if ("".equals(fc.getFiscalPeriod().getId().trim())) {// 如果只是it.material.no
			// 为空
			flowcards = fService.findAllByDistribIdAndStatus(pm, fc
					.getDistrib().getId(), fc.getStatus());
		} else {
			flowcards = fService.findAllByDistribIdAndFiscalPeriodIdAndStatus(
					pm, fc.getDistrib().getId(), fc.getFiscalPeriod().getId(),
					fc.getStatus());
		}

		req.put("flowcards", flowcards);

		return "flowcard_search_result_jsp";
	}

	/**
	 * 根据 flowcard.id
	 *
	 * @return
	 */
	public String showFlowcasrdDetail() {
		fc = fService.findById(fc.getId());
		return "flowcard_detail_show_jsp";

	}

	/**
	 *
	 * @return 所回所有分销商及终端客户
	 */

	public String listAllClientAndTerminals() {
		List<AbstractClient> clients = distribService.findAllClients(pm);
		req.put("clients", clients);
		return "client_select_jsp";
	}

	/**
	 *
	 * @return 所回所有分销商及终端客户
	 */
	public String searchAllClientAndTerminals() {
		try {
			searchString = URLDecoder.decode(searchString, "utf-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<AbstractClient> clients = null;
		if ("".equals(searchString)) {
			clients = distribService.findAllClients(pm);
		} else {
			clients = distribService.searchAllClients(searchString, pm);
		}
		req.put("clients", clients);
		return "client_search_result_jsp";
	}

	public String proSaveFlowcard() {

		List<FiscalPeriod> fps = fpService.findAllAvilable();

		req.put("fps", fps);
		return "flowcard_add_jsp";
	}

	public String saveFlowcard() {
		User cur_user = (User) session.get("LoginedUser");
		System.out.println("cur_user" + cur_user);
		if (cur_user == null) {
			this.addActionMessage("用先登陆，再进行相应操作");
			return "login";
		}
		fc.setRecorder(cur_user);
		fc.setRecordDate(new Date());

		String msg = fService.addFlowcard(fc);
		if (msg.equals("")) {
			return listFlowcards();
		}
		this.addActionMessage(msg);
		return "flowcard_add_jsp";

	}

	public String deleteFlowcards() {
		String[] ids = searchString.split(",");
		fService.deleteFlowcards(ids);
		return listFlowcards();

	}

	public String proModFlowcard() {
		fc = fService.findById(fc.getId());
		List<FiscalPeriod> fps = fpService.findAllAvilable();
		// List<FiscalPeriod> fps = fpService.findAll( );

		req.put("fps", fps);

		return "flowcard_mod_jsp";
	}

	public String modFlowcard() {
		User cur_user = (User) session.get("LoginedUser");

		if (cur_user == null) {
			this.addActionMessage("用先登陆，再进行相应操作");
			return "login";
		}
		fc.setRecorder(cur_user);
		fc.setRecordDate(new Date());
		String msg = fService.modFlowcard(fc);
		this.addActionMessage(msg);
		if ("".equals(msg)) {
			return listFlowcards();
		} else {
			return "flowcard_mod_jsp";
		}

	}

	public String sendToCheckInventory() {
		String[] ids = searchString.split(",");
		String msg = fService.updateStatus(ids, Flowcard.STATUS_S);
		return listFlowcards();

	}

	// 所出送审状态的flowcard
	public String listFlowcardsS() {
		// 查出所出送审状态的flowcards
		List<Flowcard> flowcards = this.getfService()
				.findAllFlowcardsWithStatusS(pm);
		req.put("flowcards", flowcards);

		List<FiscalPeriod> fps = fpService.findAllAvilable();
		// List<FiscalPeriod> fps = fpService.findAll( );

		req.put("fps", fps);
		return "flowcard_adjust_jsp";

	}

	public String checkFlowcards() {
		String[] ids = searchString.split(",");
		User adjuster = (User) session.get("LoginedUser");
		String msg = fService.updateAdjusterAndStatus(ids, adjuster,
				Flowcard.STATUS_A);

		return listFlowcardsS();
	}

	public String proAdjustFlowcard() {
		fc = fService.findById(fc.getId());
		return "flowcard_adjust_detail_jsp";
	}

	public String adjustFlowcard() {
		User adjuster = (User) session.get("LoginedUser");
		fc.setAdjuster(adjuster);
		fc.setAdjusterDate(new Date());
		fService.modAdjustFlowcard(fc);

		return listFlowcardsS();
	}

	public String listFlowcardsForSpot() {
		// 查出所出已审查通过 的flowcards
		List<Flowcard> flowcards = this.getfService()
				.findAllFlowcardsWithStatusA(pm);
		req.put("flowcards", flowcards);

		List<FiscalPeriod> fps = fpService.findAllAvilable();
		req.put("fps", fps);
		return "flowcard_spot_jsp";
	}

	public String proSpotFlowcard() {
		fc = fService.findById(fc.getId());
		req.put("now", new Date());
		return "flowcard_spot_detail_jsp";
	}

	public String spotFlowcard() {
		User spotter = (User) session.get("LoginedUser");
		fc.setSpotter(spotter);
		fc.setSpotDate(new Date());
		fService.modFlowcardSpotInfo(fc);

		return listFlowcardsForSpot();
	}

	//
	public String searchFlowCardsByClientIdAndFiscalPeriodidForSpot() {

		searchFlowCardsByClientIdAndFiscalPeriodid();

		return "flowcard_search_result_spot_jsp";
	}

	// 查所有已审核过的flowcard ，以便进行复审
	public String listFlowcardsForFuShen() {

		List<Flowcard> flowcards = this.getfService()
				.findAllFlowcardsWithStatusA(pm);
		req.put("flowcards", flowcards);

		List<FiscalPeriod> fps = fpService.findAllAvilable();
		// List<FiscalPeriod> fps = fpService.findAll( );

		req.put("fps", fps);

		return "flowcard_fushen_jsp";
	}

	public String fushenFlowcards() {
		User fushener = (User) session.get("LoginedUser");
		String[] ids = searchString.split(",");

		fService.modFlowcardsForFuShen(ids, fushener);

		return listFlowcardsForFuShen();
	}

	public String rejectFuShenFlowcards() {
		User fushener = (User) session.get("LoginedUser");
		String[] ids = searchString.split(",");
		fService.modRejectFuShenFlowcards(ids, fushener);
		return listFlowcardsForFuShen();
	}
}
