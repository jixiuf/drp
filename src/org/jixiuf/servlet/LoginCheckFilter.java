package org.jixiuf.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jixiuf.drp.pojo.User;

public class LoginCheckFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute("LoginedUser");
		if (user == null) {
			String queryString = httpRequest.getQueryString();
			StringBuffer url = httpRequest.getRequestURL();
			//如果是登陆行为，则不进行转发，否则转发到登陆页面让其进行登陆
			if (url.indexOf("user/user!proLogin") >= 0
					|| url.indexOf("/user/user!login") >= 0) {
				chain.doFilter(req, resp);
				return;

			}

			String urlString = (queryString == null) ? url.toString() : url
					.append("?").append(queryString).toString();
			System.out.println(urlString);

			urlString = URLEncoder.encode(urlString, "utf-8");
				System.out.println(urlString);
				// String path = httpRequest.getContextPath();
				// String basePath =
				// httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+path+"/";

				httpRequest.getRequestDispatcher(
						"/user/user!proLogin?dynamicURL=" + urlString).forward(req,
						resp);


		}

		chain.doFilter(req, resp);

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
