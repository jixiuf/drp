package org.jixiuf.interceptor;

import java.util.Map;

import org.jixiuf.action.UserAction;
import org.jixiuf.drp.pojo.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginCheckInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = (Action) invocation.getAction();

		ActionProxy proxy = invocation.getProxy();
		String method = proxy.getMethod();

		// 如果是登陆行为不加阻止 ，让其运行
		if (action.getClass().equals(UserAction.class)
				&& ("login".equals(method) || "proLogin".equals(method) ||"logout".equals(method)    )) {
			return invocation.invoke();
		}

		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();

		User loginedUser = (User) session.get("LoginedUser");
		if (loginedUser == null) {
			// 转去登陆页面
			return "login";
		}
	return 	invocation.invoke();

	}

}
