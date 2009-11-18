package org.ndot.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Flex_J2ee
 * 
 *<P>
 * 
 * 文件名： BooksServlet.java
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
 * 创建时间: 2009-9-13
 * 
 */
public class BooksServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 设置编码，否则 中文会乱码，在 flex中默认是 UTF-8，所以这里也要设置成UTF-8
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		StringBuffer books = new StringBuffer();
		books.append("<books>");
		books
				.append("<book bookId=\"001\"><name>java基础教程</name><author>NDot</author><price>78.00</price></book>");
		books
				.append("<book bookId=\"002\"><name>Flex基础教程</name><author>SJC</author><price>68.00</price></book>");
		books
				.append("<book bookId=\"002\"><name>WS基础教程</name><author>PanPan</author><price>88.00</price></book>");
		books.append("</books>");
		out.write(books.toString());
		out.flush();
		out.close();
		return;
	}

}
