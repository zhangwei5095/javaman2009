package com.nasoft;


public class FixedPackUnpackHelper {

	/**
	 * TODO �̶��������
	 * 
	 * @param nodeId
	 *            �ڵ���
	 * @param xml
	 *            ƽ̨ͳһ��ʽxml����
	 * @param reqRsp
	 *            ����Ӧ���־ req���� rspӦ��
	 * @return �����ƹ̶�����
	 * @throws Exception
	 */
	public static byte[] pack(String nodeId, String xml, String reqRsp)
			throws Exception {
		return new Pack().pack(nodeId, xml, reqRsp);
	}

	/**
	 * TODO �̶����Ĳ��
	 * 
	 * @param nodeId
	 *            �ڵ���
	 * @param buf
	 *            �����ƹ̶�����
	 * @param reqRsp
	 *            ����Ӧ���־ req���� rspӦ��
	 * @return ������ƽ̨ͳһ��ʽxml����
	 * @throws Exception
	 */

	public static  String unpack(String nodeId, byte[] buf, String reqRsp)
			throws Exception {
		return new UnPack().unpack(nodeId, buf, reqRsp);
	}

}
