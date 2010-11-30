package org.jixiuf.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jixiuf.drp.pojo.FiscalPeriod;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.drp.service.FiscalPeriodService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component("fiscalPeriodAction")
@Scope("prototype")
public class FiscalPeriodAction extends ActionSupport implements RequestAware,
		SessionAware {

	// 会计核算期
	FiscalPeriod fp = new FiscalPeriod();
	PageModel pm = new PageModel();
	FiscalPeriodService fpService;
	Map<String, Object> req;
	Map<String, Object> session;

	public PageModel getPm() {
		return pm;
	}

	public void setPm(PageModel pm) {
		this.pm = pm;
	}

	public FiscalPeriod getFp() {
		return fp;
	}

	public void setFp(FiscalPeriod fp) {
		this.fp = fp;
	}

	public FiscalPeriodService getFpService() {
		return fpService;
	}

	@Resource(name = "fiscalPeriodService")
	public void setFpService(FiscalPeriodService fpService) {
		this.fpService = fpService;
	}

	public void setRequest(Map<String, Object> request) {
		this.req = request;

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	// ======业务逻辑==================================

	public String listFiscalPeriod() {
		// 根据 PageModel 查询出所有会计核算期
		List<FiscalPeriod> fps = fpService.findAll(pm);
		this.req.put("fps", fps);
		session.put("pm", pm);
		return "listFiscalPeriod_jsp";
	}

	public String addFiscalPeriod() {
		System.out.println(fp.getStatus());
	String msg=	fpService.addFiscalPeriod(fp);
      	req.put("msg", msg);
      	if ("".equals(msg))//无错
		return "listFiscalPeriod";

      	return  "addFiscalPeriod_jsp";
	}






	public String preModFiscalPeriod() {
		fp = fpService.findById(fp.getId());

		return "modFiscalPeriod_jsp";
	}

	public String modFiscalPeriod() {
		System.out.println(fp.getStatus());
	String msg=	fpService.update(fp);
		if("".equals(msg)) {//如果无错误消息
			return "listFiscalPeriod";
		}
		//返回错和界面
		req.put("msg", msg);
		return "modFiscalPeriod_jsp";


	}

}
