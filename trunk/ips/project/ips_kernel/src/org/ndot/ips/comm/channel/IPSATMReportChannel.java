package org.ndot.ips.comm.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ndot.ips.comm.IPSReportChannel;
import org.ndot.ips.comm.IPSReportProcesser;
import org.ndot.ips.log.IPSLogLevel;

import com.nasoft.IPSReport;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： ATMIPSReportChannel.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-21
 * 
 */
public class IPSATMReportChannel extends IPSReportChannel {
	public IPSATMReportChannel() {
		setLog(Logger.getLogger(IPSATMReportChannel.class));
	}

	@SuppressWarnings("unused")
	@Override
	public void runServer() {
		try {
			getIpsServerSocketChannel().socket().setReuseAddress(true);
			getIpsServerSocketChannel().socket().bind(
					new InetSocketAddress(this.getPort()));
			getIpsServerSocketChannel().configureBlocking(false);
			getIpsServerSocketChannel().register(getSelector(),
					SelectionKey.OP_ACCEPT);
			writeLog(IPSLogLevel.INFO, "开起 " + this.getName() + " 监听，监听端口： "
					+ this.getPort());
			_DOWHILE: while (!this.isStop()) {
				try {
					int num = getSelector().select();
					if (num < 1)
						continue;
					Set<SelectionKey> set = getSelector().selectedKeys();
					for (SelectionKey key : set) {
						set.remove(key);
						if (key.isAcceptable()) { // 接收请求
							ServerSocketChannel server = (ServerSocketChannel) key
									.channel();
							SocketChannel channel = server.accept();
							// 设置非阻塞模式
							channel.configureBlocking(false);
							channel.register(getSelector(),
									SelectionKey.OP_READ);
						} else if (key.isReadable()) { // 读信息
							SocketChannel channel = (SocketChannel) key
									.channel();
							ByteBuffer clientBuffer = ByteBuffer.allocate(this
									.getReportLen());
							int count = channel.read(clientBuffer);
							if (count > 0) {
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
								writeLog(IPSLogLevel.INFO, "NDot --  "
										+ String.valueOf(System
												.currentTimeMillis())
										+ " start ");
								writeLog(IPSLogLevel.INFO,
										"------------开始处理交易------------");
								writeLog(
										IPSLogLevel.INFO,
										"IPS接收 ATM-C003 请求 报文(HEX)： \n"
												+ ISOUtil
														.byte2HexStr(
																byteReportContent,
																byteReportContent.length));
								IPSReport reqReportContentObj = this
										.getIpsReportDecoder().decoder(
												getChannelId(),
												byteReportContent);
								IPSReportProcesser ipsReportProcesser = getIpsReportProcesserInstance();
								String ip = channel.socket().getInetAddress()
										.getHostAddress();
								IPSReport rspReportContentObj = ipsReportProcesser
										.processer(this, reqReportContentObj,
												ip);
								if (null != rspReportContentObj) {
									channel.register(this.getSelector(),
											SelectionKey.OP_WRITE,
											rspReportContentObj);
								} else {
									writeLog(IPSLogLevel.INFO,
											"IPS组织 至 ATM-C003 应答 报文错误");
								}
							}
						} else if (key.isWritable()) { // 写事件
							SocketChannel channel = (SocketChannel) key
									.channel();
							IPSReport rspObj = (IPSReport) key.attachment();
							writeLog(IPSLogLevel.INFO,
									"IPS组织 至 ATM-C003 应答 报文(XML)： \n"
											+ rspObj.formatToXml() + " \n",
									rspObj.getBody());
							byte[] rspBytes = getIpsReportEncoder().encoder(
									getChannelId(), rspObj);
							ByteBuffer rspBuf = ByteBuffer
									.allocate(rspBytes.length);
							rspBuf.put(rspBytes);
							rspBuf.flip();
							channel.write(rspBuf);
							writeLog(IPSLogLevel.INFO,
									"IPS组织 至 ATM-C003 应答 报文(HEX)： \n"
											+ ISOUtil.byte2HexStr(rspBytes,
													rspBytes.length));
							writeLog(IPSLogLevel.INFO,
									"------------交易处理完毕------------");
							writeLog(IPSLogLevel.INFO,
									"NDot --  "
											+ String.valueOf(System
													.currentTimeMillis())
											+ " end ");
							key.cancel();
							channel.close();
						}
					}

				} catch (Exception e) {
					writeLog(IPSLogLevel.INFO,
							"ATM 事情监听错误，可能ATM强制断链，抛弃当前处理交易，继续监听......");
					e.printStackTrace();
					clear();
					continue _DOWHILE;
				}

			}
			this.close();
		} catch (Exception e) {
			writeLog(IPSLogLevel.INFO, "ATM 监听服务启动错误，系統智能从新启动......");
			this.runServer();
		}

	}

	@Override
	public int processReportLen(byte[] reportLen) throws ISOException {
		return ISOUtil.bytes2Int(reportLen);
	}

	@Override
	public IPSReport callClient(IPSReport sendReport) {
		return null;
	}

}