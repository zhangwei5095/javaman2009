package org.ndot.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import com.sun.jndi.toolkit.ctx.Continuation;

public class NIOClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 8989);
			// 调用open静态方法创建连接到指定主机的SocketChannel
			SocketChannel sc = SocketChannel.open(isa);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			Selector se = Selector.open();
			buffer.put("12355555".getBytes());
			buffer.flip();
			sc.configureBlocking(false);
			sc.register(se, SelectionKey.OP_READ);
			sc.write(buffer);
			while (se.select()< 0) {
				Set<SelectionKey> set = se.selectedKeys();
				for (SelectionKey key : set) {
					int ops = key.readyOps();
					if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
						System.out.println("连接成功");
						sc.register(se, SelectionKey.OP_WRITE);
					}
					if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						System.out.println(" 收到东西");
						sc.read(buffer);
						buffer.flip();
						System.out.println("收到的是:"
										+ new String(buffer.array(), 0, buffer
												.limit()));
						sc.register(se, SelectionKey.OP_WRITE);
					}
					if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
						buffer.put("NDotaaa".getBytes());
						sc.write(buffer);
					}
					
				}
				se.selectedKeys().clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
