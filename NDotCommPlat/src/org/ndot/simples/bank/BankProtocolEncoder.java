package org.ndot.simples.bank;

import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� BankProtocolEncoder.java
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
public class BankProtocolEncoder implements ProtocolEncoder {

	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		byte[] msgBytes = (byte[]) message;
		IoBuffer buf = IoBuffer.allocate(msgBytes.length).setAutoExpand(true);
		buf.put(msgBytes);
		buf.flip();
		out.write(buf);
	}

}
