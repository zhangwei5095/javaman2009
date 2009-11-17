package org.ndot.simples.spring;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： MinaProtocolHandler.java
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
public class MinaProtocolHandler extends IoHandlerAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// private final Set<IoSession> sessions = Collections
	// .synchronizedSet(new HashSet<IoSession>());

	public void messageReceived(IoSession session, Object message) {
		String msg = (String) message;
		System.out.println("Server Received: " + msg);
		session.write("Server Send: " + msg);
		// sessions.add(session);
	}

	public void sessionIdle(IoSession session, IdleStatus status) {
		// disconnect an idle client
		session.close(true);
	}

	// public void sessionClosed(IoSession session) throws Exception {
	// sessions.remove(session);
	// }

	public void exceptionCaught(IoSession session, Throwable cause) {
		logger.warn("Unexpected exception.", cause);
		session.close(true);
	}

}
