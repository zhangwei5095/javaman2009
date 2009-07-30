package org.ndot.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ndot.ips.comm.ChannelContral;
import org.ndot.ips.comm.IPSReportChannel;
import org.ndot.ips.comm.channel.IPSATMReportChannel;
import org.ndot.ips.log.IPSLogLevel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

public class ChannelControl extends IPSAction {
	/*
	 * Generated Methods
	 */

	public ChannelControl() {
		super();
		log.setLog(Logger.getLogger(ChannelControl.class));
	}

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
		String message = "异常";
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
				message = "启动";
				log.writeLog(IPSLogLevel.INFO, "IPS-Action  开始启动 【"
						+ channel.getName()+"-"+channel.getChannelId() + "】" + " 服务监听......");
			} else if ("stop".equals(type)) {
				channel.setStop(true);
				message = "停止";
				log.writeLog(IPSLogLevel.INFO, "IPS-Action  开始停止 【"
						+ channel.getName()+"-"+channel.getChannelId() + "】" + " 服务监听......");
			}
			new Thread(new ChannelContral(channel)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setHeader("Cache-Control", "no-cache");// 不缓冲
		response.setHeader("Pragma", "no-cache");// 不缓冲
		response.setCharacterEncoding("GBK");// 汉字编码
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

}
