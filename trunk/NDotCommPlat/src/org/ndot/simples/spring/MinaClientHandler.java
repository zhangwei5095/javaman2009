package org.ndot.simples.spring;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： MinaClientHandler.java
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
 * 创建时间: 2009-11-17
 * 
 */
public class MinaClientHandler extends IoHandlerAdapter {

	private static final IoFilter LOGGING_FILTER = new LoggingFilter();

	private static final IoFilter CODEC_FILTER = new ProtocolCodecFilter(
			new TextLineCodecFactory());

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		session.getFilterChain().addLast("codec", CODEC_FILTER);
		session.getFilterChain().addLast("logger", LOGGING_FILTER);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = (String) message;
		System.out.println("Client Received: " + msg);
	}

}
