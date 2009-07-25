package org.ndot.ips.comm;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

import org.ndot.ips.log.IPSLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nasoft.IPSReport;
import com.nasoft.iso.ISOException;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSChannelImp.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-20
 * 
 */
public abstract class IPSReportChannel extends IPSLogger {
	/** 渠道停止标志 */
	boolean stop = false;
	/** 报文渠道编号 */
	String channelId = "";

	/** 报文传输通道名字 */
	String name = "";

	/** 报文传输通道的多路复用器 */
	Selector selector = null;

	/** 报文传输通道 */
	ServerSocketChannel ipsServerSocketChannel = null;

	/** 报文通道监听的端口号 */
	int port = 8080;

	/** 用于定义存放报文长度的字节数，如ATM上送的报文长度用2个字节表示 */
	int reportLen = 0;

	/** 报文解码器 拆包 */
	IPSReportDecoder ipsReportDecoder = null;

	/** 报文编码器 组包 */
	IPSReportEncoder ipsReportEncoder = null;

	/** 报文处理器 业务处理 */
	// IPSReportProcesser ipsReportProcesser = null;
	/** 客户端 Host */
	String clientHost;
	/** 客户端 端口 */
	int clientPort;
	@Autowired
	ApplicationContext ctx;

	public IPSReportChannel() {
	}

	/**
	 * 报文传输通道启动器
	 */
	abstract public void runServer() throws Exception;

	abstract public IPSReport callClient(IPSReport sendReport);

	/**
	 * 用于将特定编码方式的报文长度
	 * 
	 * @param reportLen
	 *            接收到得二进制报文长度
	 * @return 上送报文的长度
	 */
	public int processReportLen(byte[] reportLen) throws ISOException {
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public ServerSocketChannel getIpsServerSocketChannel() {
		return ipsServerSocketChannel;
	}

	public void setIpsServerSocketChannel(
			ServerSocketChannel ipsServerSocketChannel) {
		this.ipsServerSocketChannel = ipsServerSocketChannel;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getReportLen() {
		return reportLen;
	}

	public void setReportLen(int reportLen) {
		this.reportLen = reportLen;
	}

	public IPSReportDecoder getIpsReportDecoder() {
		return ipsReportDecoder;
	}

	public void setIpsReportDecoder(IPSReportDecoder ipsReportDecoder) {
		this.ipsReportDecoder = ipsReportDecoder;
	}

	public IPSReportEncoder getIpsReportEncoder() {
		return ipsReportEncoder;
	}

	public void setIpsReportEncoder(IPSReportEncoder ipsReportEncoder) {
		this.ipsReportEncoder = ipsReportEncoder;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getClientHost() {
		return clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public ApplicationContext getCtx() {
		return ctx;
	}

	public IPSReportProcesser getIpsReportProcesserInstance() {
		return null;
	};

	public void clear() {
		try {
			this.selector.select(3000);
			Set<SelectionKey> set = this.selector.selectedKeys();
			for (SelectionKey key : set) {
				set.remove(key);
				key.cancel();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			this.selector.close();
			this.ipsServerSocketChannel.socket().close();
			this.ipsServerSocketChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
