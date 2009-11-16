package org.ndot.simples.timeserver;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： TimeClient.java
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
public class TimeClient {
	public static void main(String[] args) {
		try {

			// 创建客户端连接器.
			NioSocketConnector connector = new NioSocketConnector();
			connector.getFilterChain().addLast("logger", new LoggingFilter());
			connector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new TextLineCodecFactory(Charset
							.forName("UTF-8")))); // 设置编码过滤器
			connector.setConnectTimeout(30);
			connector.setHandler(new TimeClientHandler());// 设置事件处理器
			ConnectFuture cf = connector.connect(new InetSocketAddress(
					"127.0.0.1", 9123));// 建立连接
			cf.awaitUninterruptibly();// 等待连接创建完成
			cf.getSession().write("hello");// 发送消息
			cf.getSession().write("quit");// 发送消息
			cf.getSession().getCloseFuture().awaitUninterruptibly();// 等待连接断开
			connector.dispose();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
