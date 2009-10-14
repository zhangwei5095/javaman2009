package com.nasoft;


public class FixedPackUnpackHelper {

	/**
	 * TODO 固定报文组包
	 * 
	 * @param nodeId
	 *            节点编号
	 * @param xml
	 *            平台统一格式xml报文
	 * @param reqRsp
	 *            请求应答标志 req请求 rsp应答
	 * @return 二进制固定报文
	 * @throws Exception
	 */
	public static byte[] pack(String nodeId, String xml, String reqRsp)
			throws Exception {
		return new Pack().pack(nodeId, xml, reqRsp);
	}

	/**
	 * TODO 固定报文拆包
	 * 
	 * @param nodeId
	 *            节点编号
	 * @param buf
	 *            二进制固定报文
	 * @param reqRsp
	 *            请求应答标志 req请求 rsp应答
	 * @return 拆包后的平台统一格式xml报文
	 * @throws Exception
	 */

	public static  String unpack(String nodeId, byte[] buf, String reqRsp)
			throws Exception {
		return new UnPack().unpack(nodeId, buf, reqRsp);
	}

}
