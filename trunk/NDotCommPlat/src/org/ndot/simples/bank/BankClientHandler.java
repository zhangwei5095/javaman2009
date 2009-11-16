package org.ndot.simples.bank;

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
 * 文件名： TimeClientHandler.java
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
 * 创建时间: 2009-11-13
 * 
 */
public class BankClientHandler extends IoHandlerAdapter {
	public BankClientHandler() {
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(message);// 显示接收到的消息
	}

}
