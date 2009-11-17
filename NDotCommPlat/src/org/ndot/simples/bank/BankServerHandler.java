package org.ndot.simples.bank;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� BankServerHandler.java
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
 * ����ʱ��: 2009-11-16
 * 
 */
public class BankServerHandler implements IoHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BankServerHandler.class);

	public void sessionCreated(IoSession session) throws Exception {
		// Empty handler
		System.out.println("sessionCreated......");
	}

	public void sessionOpened(IoSession session) throws Exception {
		// Empty handler
		System.out.println("sessionOpened......");
	}

	public void sessionClosed(IoSession session) throws Exception {
		// Empty handler
		System.out.println("sessionClosed......");
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// Empty handler
		System.out.println("sessionIdle......");
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn("EXCEPTION, please implement " + getClass().getName()
					+ ".exceptionCaught() for proper handling:", cause);
		}
		System.out.println("exceptionCaught......");
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		// Empty handler
		System.out.println("messageSent......");
		session.close(true);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		System.out.println("���յ����ݣ�" + str);
		session.write(str);
	}
}
