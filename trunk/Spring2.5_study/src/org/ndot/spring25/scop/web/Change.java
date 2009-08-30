package org.ndot.spring25.scop.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： LoginAction.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-8-30
 * 
 */

public class Change extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext servletContext = req.getSession().getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);

		RequestScopBean reqInfo = (RequestScopBean) ctx
				.getBean("RequestScopBean");
		req.setAttribute("reqInfo", reqInfo);

		SessionScopBean sessionInfo = (SessionScopBean) ctx
				.getBean("SessionScopBean");
		req.setAttribute("sessionInfo", sessionInfo);

		GlobalSessionScopBean globalSessionInfo = (GlobalSessionScopBean) ctx
				.getBean("GlobalSessionScopBean");
		req.setAttribute("globalSessionInfo", globalSessionInfo);
		// 测试 普通bean 对 webScop的注入
		InjectWebScopBean ib = (InjectWebScopBean) ctx
				.getBean("InjectWebScopBean");
		System.out.println(ib.getReqBean().getLoginName() + ":"
				+ ib.getReqBean().getLoginRole());

		req.getRequestDispatcher("/pages/showInfo.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
