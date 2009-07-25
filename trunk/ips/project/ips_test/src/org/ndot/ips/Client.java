package org.ndot.ips;

import java.io.*;
import java.net.*;

public class Client {
	Socket socket;

	BufferedInputStream in;

	PrintStream out;

	private String ipAddr = null;

	private int port;

	private boolean isConncted = false;

	public Client(String ipAddr, int port) {
		try {
			this.ipAddr = ipAddr;
			this.port = port;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO 建立连接
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean openConn() throws IOException, Exception {
		boolean opened = false;
		try {
			socket = new Socket(this.ipAddr, this.port);
			socket.setReuseAddress(true);
			this.in = new BufferedInputStream(socket.getInputStream());
			this.out = new PrintStream(socket.getOutputStream(), false);
			socket.setSoTimeout(150000);
			opened = true;
			isConncted = true;
		} catch (Exception e) {
			opened = false;
			throw e;
		}
		return opened;

	}

	public void closeConn() throws IOException, Exception {
		try {
			out.close();
			in.close();
			socket.close();
			isConncted = false;
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * TODO 发送数据包
	 * 
	 * @param pak
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean sendOutData(byte[] data) throws IOException, Exception {
		boolean isSent = false;
		try {

			if (!this.isConncted) {
				openConn();
			}
			this.out.write(data);
			this.out.flush();
			byte[] buf = new byte[4096];
			int readLen = in.read(buf);
			while (readLen > 0) {
				byte[] recvMsg = new byte[readLen];
				System.arraycopy(buf, 0, recvMsg, 0, readLen);
				System.out.println("recvMsg(hex)="
						+ ISOUtil.byte2HexStr(recvMsg, recvMsg.length));
				readLen = in.read(buf);
			}
			isSent = true;
		} catch (Exception e) {
			isSent = false;
			throw e;
		}

		return isSent;
	}

}
