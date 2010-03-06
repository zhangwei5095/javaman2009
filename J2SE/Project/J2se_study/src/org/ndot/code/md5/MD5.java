package org.ndot.code.md5;

import java.security.MessageDigest;

import org.ndot.ISOUtil;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� MD5.java
 * 
 *<P>
 * 
 * �� ��:
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-12-24
 * 
 */
public class MD5 {

	public static String encryptMD5(String data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data.getBytes());
		byte[] rsByte = md5.digest();
		return ISOUtil.byte2HexNoSpaceStr(rsByte, rsByte.length);

	}



	public static void main(String[] args) {

		try {
			String pw = "adminusername";// bcfe4a329813f197e53145f3f832a47e
			pw = "userusername";// cf016618d971159d6db71ae9d3f91b92
			pw="admin";//21232f297a57a5a743894a0e4a801fc3
			pw="user";//ee11cbb19052e40b07aac0ca060c23ee
			pw="adminadmin";//f6fdffe48c908deb0f4c3bd36c032e72
			pw="useruser";//5cc32e366c87c4cb49e4309b75f57d64
			String epw;
			epw = MD5.encryptMD5(pw);
			System.out.println(epw);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
