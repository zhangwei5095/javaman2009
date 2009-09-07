package org.ndot.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NIOServer {
	private ServerSocketChannel ssc;
	private Selector selector;
	private boolean stopFlag;
	private int port;

	public NIOServer(int port) {
		try {
			this.ssc = ServerSocketChannel.open();
			ssc.socket().setReuseAddress(true);
			ssc.configureBlocking(false);
			this.selector = Selector.open();
			this.stopFlag = false;
			this.port=port;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			System.out.println("已经启动服务监听......,监听端口是: "+port);
			ssc.socket().bind(new InetSocketAddress(port));
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			while (!stopFlag) {
				int num = selector.select();
				if (num < 1)
					continue;
				Set<SelectionKey> set = selector.selectedKeys();
				for (SelectionKey key : set) {
					set.remove(key);
					if (key.isAcceptable()) { // 接收请求
						System.out.println("有一个客户连接.....");
						ServerSocketChannel server = (ServerSocketChannel) key
								.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) { // 读信息
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
						int count = 0;
						if (channel.isConnected()) {
							count = channel.read(clientBuffer);
						}
						if (count > 0) {
							clientBuffer.flip();
							byte[] content = new byte[clientBuffer.limit()];
							clientBuffer.get(content);
							System.out.println(new String(content));

							channel.register(this.selector,
									SelectionKey.OP_WRITE, content);

						}
					} else if (key.isWritable()) { // 写事件
						SocketChannel channel = (SocketChannel) key.channel();
						byte[] content = (byte[]) key.attachment();
						ByteBuffer rspBuf = ByteBuffer.allocate(content.length);
						rspBuf.put(content);
						rspBuf.flip();
						channel.write(rspBuf);
						key.cancel();
						channel.close();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NIOServer server = new NIOServer(8989);
		server.run();
	}

}
