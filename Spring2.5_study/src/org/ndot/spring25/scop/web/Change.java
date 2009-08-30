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
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� LoginAction.java
 * 
 *<P>
 * 
 * �� ��:
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-8-30
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
		// ���� ��ͨbean �� webScop��ע��
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
