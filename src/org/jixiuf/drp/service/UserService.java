package org.jixiuf.drp.service;

import java.util.List;

import javax.annotation.Resource;

import org.jixiuf.drp.dao.UserDAO;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.pojo.model.PageModel;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserService {
	UserDAO userDAO;

	public UserService() {
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	@Resource(name = "userDAO")
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<User> findAll(PageModel pageModel) {
		return userDAO.findAll(pageModel);

	}



	public User loadUser(String id) {
		return userDAO.findById(id);

	}

	public void deleteUser(User user) {
		userDAO.delete(user);

	}

	/**
	 * 检查此用户邮符是否已存在
	 *
	 * @return
	 */
	public boolean isEmailExists(User user) {
		return userDAO.isEmailExists(user);

	}

	public void addUser(User user) {

		userDAO.save(user);

	}

	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	public void deleteUsersById(String[] userIds) {
		User u = null;
		for (String id : userIds) {
			u = new User();
			u.setId(id);
			userDAO.delete(u);
		}

	}

	public User login(String email, String password) {

		List<User> users = userDAO.findByEmail(email);
		User user = null;
		if (users.size() == 0) {
			user = new User();
			user.setMsg(" 用户不存在，请重新输入");
			return user;
		}
		user = users.get(0);
		if (!user.getPassword().equals(password)) {
			user.setMsg("密码不正确");
			return null;
		}
		user.setMsg("");
		return user;
	}

}
