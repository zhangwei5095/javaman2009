package org.ndot.simples.timeserver;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� TimeServerHandler.java
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
 * ����ʱ��: 2009-11-13
 * 
 */
public class TimeServerHandler extends IoHandlerAdapter {
	@Override
	public void sessionCreated(IoSession session) {
		// ��ʾ�ͻ��˵�ip�Ͷ˿�
		System.out.println(session.getRemoteAddress().toString());
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();// �����Ự
			return;
		}
		Date date = new Date();
		session.write(date.toString());// ���ص�ǰʱ����ַ���
		System.out.println("Message written...");
	}

}
