package org.ndot.simples.bank;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： BankServerHandler.java
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
 * 创建时间: 2009-11-16
 * 
 */
public class BankServerHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		System.out.println("接收到数据：" + str);
		session.write(str.getBytes());// 返回当前时间的字符串
		
		session.close(true);
	}
}
