package org.ndot.simples.spring;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� MinaClientHandler.java
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
 * ����ʱ��: 2009-11-17
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
