package org.ndot.simples.bank;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.nasoft.iso.ISOUtil;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� Bankclient.java
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
public class Bankclient {

	public static void main(String[] args) {
		try {

			// �����ͻ���������.
			NioSocketConnector connector = new NioSocketConnector();
			connector.getFilterChain().addLast("logger", new LoggingFilter());
			connector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new BankProtocolCodecFactory())); // ���ñ��������
			connector.setConnectTimeout(30);
			connector.setHandler(new BankClientHandler());// �����¼�������
			ConnectFuture cf = connector.connect(new InetSocketAddress(
					"127.0.0.1", 7528));// ��������
			cf.awaitUninterruptibly();// �ȴ����Ӵ������
			String hexPack = "005B30303030303032313331303035343430303030303832302238000000802800313630343030313131333130303534343130303535323230303931303133313030303030323131323130333131303631303030303030303030303030";
			byte[] packe = ISOUtil.hex2byte(hexPack);
			cf.getSession().write(packe);
			cf.getSession().getCloseFuture().awaitUninterruptibly();// �ȴ����ӶϿ�
			connector.dispose();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
