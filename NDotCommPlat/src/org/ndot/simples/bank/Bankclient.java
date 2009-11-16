package org.ndot.simples.bank;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.nasoft.iso.ISOUtil;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： Bankclient.java
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
public class Bankclient {

	public static void main(String[] args) {
		try {

			// 创建客户端连接器.
			NioSocketConnector connector = new NioSocketConnector();
			connector.getFilterChain().addLast("logger", new LoggingFilter());
			connector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new BankProtocolCodecFactory())); // 设置编码过滤器
			connector.setConnectTimeout(30);
			connector.setHandler(new BankClientHandler());// 设置事件处理器
			ConnectFuture cf = connector.connect(new InetSocketAddress(
					"127.0.0.1", 7528));// 建立连接
			cf.awaitUninterruptibly();// 等待连接创建完成
			String hexPack = "005B30303030303032313331303035343430303030303832302238000000802800313630343030313131333130303534343130303535323230303931303133313030303030323131323130333131303631303030303030303030303030";
			byte[] packe = ISOUtil.hex2byte(hexPack);
			cf.getSession().write(packe);
			cf.getSession().getCloseFuture().awaitUninterruptibly();// 等待连接断开
			connector.dispose();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
