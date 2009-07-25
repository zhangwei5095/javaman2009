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
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSChannelImp.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-20
 * 
 */
public abstract class IPSReportChannel extends IPSLogger {
	/** ����ֹͣ��־ */
	boolean stop = false;
	/** ����������� */
	String channelId = "";

	/** ���Ĵ���ͨ������ */
	String name = "";

	/** ���Ĵ���ͨ���Ķ�·������ */
	Selector selector = null;

	/** ���Ĵ���ͨ�� */
	ServerSocketChannel ipsServerSocketChannel = null;

	/** ����ͨ�������Ķ˿ں� */
	int port = 8080;

	/** ���ڶ����ű��ĳ��ȵ��ֽ�������ATM���͵ı��ĳ�����2���ֽڱ�ʾ */
	int reportLen = 0;

	/** ���Ľ����� ��� */
	IPSReportDecoder ipsReportDecoder = null;

	/** ���ı����� ��� */
	IPSReportEncoder ipsReportEncoder = null;

	/** ���Ĵ����� ҵ���� */
	// IPSReportProcesser ipsReportProcesser = null;
	/** �ͻ��� Host */
	String clientHost;
	/** �ͻ��� �˿� */
	int clientPort;
	@Autowired
	ApplicationContext ctx;

	public IPSReportChannel() {
	}

	/**
	 * ���Ĵ���ͨ��������
	 */
	abstract public void runServer() throws Exception;

	abstract public IPSReport callClient(IPSReport sendReport);

	/**
	 * ���ڽ��ض����뷽ʽ�ı��ĳ���
	 * 
	 * @param reportLen
	 *            ���յ��ö����Ʊ��ĳ���
	 * @return ���ͱ��ĵĳ���
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
