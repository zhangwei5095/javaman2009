package org.ndot.extjs3.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonAjaxServer extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String rspmsg = "NDot test data ......";
			resp.setContentType("text/xml");
			resp.setCharacterEncoding("GBK");
			PrintWriter out = resp.getWriter();
			out.write(rspmsg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
