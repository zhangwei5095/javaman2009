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

			SocketChannel sc = SocketChannel.open();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			Selector se = Selector.open();
			buffer.put("12355555".getBytes());
			buffer.flip();
			sc.configureBlocking(false);
			sc.register(se, SelectionKey.OP_CONNECT | SelectionKey.OP_READ
					| SelectionKey.OP_WRITE);
			sc.connect(new InetSocketAddress("127.0.0.1", 8989));
			while (!sc.finishConnect())
				sc.write(buffer);
		    	Thread.sleep(10000);
			int sum = 0;
			while (sum < 1) {
				sum = se.select();
				Set<SelectionKey> set = se.selectedKeys();
				System.out.println("��С��:" + set.size());
				for (SelectionKey key : set) {
					int ops = key.readyOps();
					if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
						sc.write(buffer);
						System.out.println("���ӳɹ�");
					}
					if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						System.out.println(" �յ�����");
						sc.read(buffer);
						buffer.flip();
						System.out
								.println("�յ�����:"
										+ new String(buffer.array(), 0, buffer
												.limit()));
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
