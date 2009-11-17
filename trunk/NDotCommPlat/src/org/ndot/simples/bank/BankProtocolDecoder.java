package org.ndot.simples.bank;

import java.io.File;
import java.io.FileInputStream;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.nasoft.PackUnpackHelper;
import com.nasoft.PackagerFactory;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� bankProtocolDecoder.java
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
public class BankProtocolDecoder implements ProtocolDecoder {

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		int len = in.limit();
		byte[] inBytesArr = in.array();
		in.flip();
		byte[] inBytes = new byte[len];
		System.arraycopy(inBytesArr, 0, inBytes, 0, len);
		String msg = PackUnpackHelper.unpack("C009", inBytes);
		System.out.println("Receive Message : " + msg);
		out.write(msg);
	}

	public void dispose(IoSession session) throws Exception {

	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		System.out.println("FinishDecode ......");

	}

}
