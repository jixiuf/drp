package org.jixiuf.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.pojo.model.PageModel;
import org.jixiuf.drp.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 3160695403486857843L;
	UserService userService;
	String userIds;
	User user = new User();
	// struts.xml 文件中会据此转发
	String dynamicURL;

	PageModel pageModel = new PageModel();

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	public String getDynamicURL() {
		return dynamicURL;
	}

	public void setDynamicURL(String dynamicURL) {
		this.dynamicURL = dynamicURL;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	Map<String, Object> session;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String addUser() {
		if (userService.isEmailExists(user)) {
			this.addActionError("邮箱已存在 ，请改用其他邮箱名");
			session.put("handledUser", user);
			return "addUserFailure";
		}
		userService.addUser(user);
		session.remove("handledUser");
		return SUCCESS;
	}

	public String preModUser() {
		user = userService.loadUser(user.getId());
		return "modUser";

	}

	public String modUser() {
		userService.updateUser(user);
		System.out.println("modUser" + pageModel.getRowCount());
		this.setDynamicURL("/user/user!listUser?pageModel.pageSize="
				+ pageModel.getPageSize() + "&pageModel.pageNow="
				+ pageModel.getPageNow() + "&pageModel.rowCount="
				+ pageModel.getRowCount());
		System.out.println(this.getDynamicURL());
		return "modUserSuccess";
	}

	public String delUser() {
		String userIds[] = this.userIds.split(",");
		userService.deleteUsersById(userIds);
		this.setDynamicURL("/user/user!listUser?pageModel.pageSize="
				+ pageModel.getPageSize() + "&pageModel.pageNow="
				+ pageModel.getPageNow() + "&pageModel.rowCount="
				+ pageModel.getRowCount());
		return "modUserSuccess";
	}

	public String listUser() {
		List<User> users = userService.findAll(pageModel);
		session.put("users", users);
		return "listUser";
	}

	public String checkEmailExists() {
		System.out.println(user.getEmail());
		if (userService.isEmailExists(user)) {
			return "checkEmailExistsFailure";
		}
		return "checkEmailExistsSuccess";
	}

	public String proLogin() {
		return "login";
	}

	public String login() {

		User loginUser = userService.login(user.getEmail(), user.getPassword());
		if (loginUser.getMsg().equals("")) {// 如果没有错误信息，
			session.put("LoginedUser", loginUser);
			System.out.println(dynamicURL);
			try {
				dynamicURL = URLDecoder.decode(dynamicURL, "utf-8");
				System.out.println(dynamicURL);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (dynamicURL.equals("")) {
				dynamicURL = "/frame/right.jsp";
			}
			return "modUserSuccess";
		} else {
			this.addActionError(loginUser.getMsg());
			return "login";
		}

	}

	public String logout() {

		session.remove("LoginedUser");

		dynamicURL = "/head2.jsp";
		return "modUserSuccess";
	}

}
