package org.ndot.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ndot.ips.comm.IPSReportChannel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

public class ChannelControl extends Action {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String message = "Òì³£";
		try {
			WebApplicationContext wctx = (WebApplicationContext) this
					.getServlet().getServletContext().getAttribute(
							ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
			String channelId = request.getParameter("id");
			IPSReportChannel channel = (IPSReportChannel) wctx
					.getBean(channelId);
			String type = request.getParameter("type");
			if ("start".equals(type)) {
				channel.setStop(false);
				message = "Æô¶¯";
			} else if ("stop".equals(type)) {
				channel.setStop(true);
				message = "Í£Ö¹";
			}
			new Thread(new StartChannel(channel)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setHeader("Cache-Control", "no-cache");// ²»»º³å
		response.setHeader("Pragma", "no-cache");// ²»»º³å
		response.setCharacterEncoding("GBK");// ºº×Ö±àÂë
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(message);
		out.flush();
		out.close();
		return null;

	}

	class StartChannel implements Runnable {
		IPSReportChannel channel;

		StartChannel(IPSReportChannel channel) {
			this.channel = channel;
		}

		public void run() {
			try {
				if (channel.isStop()) {
					channel.close();
				}else{
					channel.runServer();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
