package org.ndot.ips.comm.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.ndot.ips.comm.IPSReportChannel;
import org.ndot.ips.log.IPSLogLevel;

import com.nasoft.IPSReport;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_kernel
 * 
 * 文件名： IPSCOERReportChannel.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-28
 * 
 */
public class IPSCOREReportChannel extends IPSReportChannel {

	public IPSCOREReportChannel() {
		setLog(Logger.getLogger(IPSCOREReportChannel.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IPSReport callClient(IPSReport sendReport) {
		IPSReport rspReportContentObj = null;
		try {
			// 打开通道
			SocketChannel client = SocketChannel.open();
			// 设置非阻塞模式
			client.configureBlocking(false);
			// 打开选择器
			Selector selector = Selector.open();
			// 注册连接服务段socket运动
			client.register(selector, SelectionKey.OP_CONNECT);
			InetSocketAddress ip = new InetSocketAddress(this.getClientHost(),
					this.getClientPort());
			client.connect(ip);
			boolean isStop = false;
			_END: while (!isStop) {
				try {
					selector.select();
					Iterator iter = selector.selectedKeys().iterator();
					while (iter.hasNext()) {
						SelectionKey key = (SelectionKey) iter.next();
						iter.remove();
						if (key.isConnectable()) {
							SocketChannel channel = (SocketChannel) key
									.channel();
							if (channel.isConnectionPending())
								channel.finishConnect();
							byte[] send = getIpsReportEncoder().encoder(
									this.getChannelId(), sendReport);
							ByteBuffer sendBuf = ByteBuffer
									.allocate(send.length);
							sendBuf.put(send);
							sendBuf.flip();
							long n = channel.write(sendBuf);
							writeLog(IPSLogLevel.INFO,
									"IPS组织 至 核心-C002 请求 报文(HEX)： \n"
											+ ISOUtil.byte2HexStr(send,
													send.length));
							channel.register(selector, SelectionKey.OP_READ);
						} else if (key.isReadable()) {
							SocketChannel channel = (SocketChannel) key
									.channel();
							ByteBuffer clientBuffer = ByteBuffer.allocate(this
									.getReportLen());
							int count = channel.read(clientBuffer);
							if (count > 0) {
								// 必须调用flip
								clientBuffer.flip();
								byte[] reportLen = new byte[clientBuffer
										.limit()];
								clientBuffer.get(reportLen);
								int rlen = this.processReportLen(reportLen);
								clientBuffer = ByteBuffer.allocate(rlen);
								channel.read(clientBuffer);
								clientBuffer.flip();
								byte[] byteReportContent = new byte[clientBuffer
										.limit()
										+ this.getReportLen()];
								System.arraycopy(reportLen, 0,
										byteReportContent, 0, this
												.getReportLen());
								clientBuffer.get(byteReportContent,
										getReportLen(), clientBuffer.limit());
								writeLog(
										IPSLogLevel.INFO,
										"IPS接收 核心-C002 应答 报文(HEX)： \n"
												+ ISOUtil
														.byte2HexStr(
																byteReportContent,
																byteReportContent.length));

								rspReportContentObj = this
										.getIpsReportDecoder().decoder(
												getChannelId(),
												byteReportContent);
								client.close();
								isStop = true;
								break _END;
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					return rspReportContentObj;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return rspReportContentObj;
		}
		return rspReportContentObj;
	}

	@Override
	public int processReportLen(byte[] reportLen) throws ISOException {
		return Integer.parseInt(new String(reportLen));
	}

	@Override
	public void runServer() throws Exception {
		writeLog(IPSLogLevel.INFO, "开起 " + this.getName() + " 监听，监听端口： "
				+ this.getPort());
	}
}
