package com.nasoft.security;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HsmApi {
	/**
	 * �� ZPK�� ZMK����תΪ LMK���ܣ�hsmFA
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param iZmkVar
	 *            ZMK��Կģʽ��ȡֵ0:ANSI X9.17 ��ʽ��1:������ʽ
	 *            ��iZmkLen����Ϊ16ʱ,ZMK��Կǰ�治�ӱ�־,��ʱiZmkVarֵ�������ã�
	 *            ��ΪiZmkLen����Ϊ32ʱ��iZmkVarΪ1ʱ��ZMK��Կǰ��ӱ�־X��iZmkVarΪ��ʱ��ZMK��Կǰ��ӱ�־U��
	 *            ��ΪiZmkLen����Ϊ48ʱ��iZmkVarΪ1ʱ��ZMK��Կǰ��ӱ�־Y��iZmkVarΪ��ʱ��ZMK��Կǰ��ӱ�־T��
	 * @param iZmkLen
	 *            ZMK��Կ�ĳ��� 16��32��48
	 * @param strZmkBuf
	 *            ZMK��Կ
	 * @param iZpkVar
	 *            ZpK��Կģʽ,ͬiZmkVar˵��
	 * @param iZpkLen
	 *            ZpK��Կ�ĳ��� 16��32��48
	 * @param strZpkBuf
	 *            ZPK��Կ
	 * @return �գ�ʧ�� �ǿգ��ɹ�
	 */
	private static String hsmFA(String ip, String port, String timeout,
			int iZmkVar, int iZmkLen, String strZmkBuf, int iZpkVar,
			int iZpkLen, String strZpkBuf) throws Exception{
		int iRet = 0;
		String strTmpSend = "";
		// String strSendData = "";
		String strRcvData = "";
		String strOther1 = "";
		strTmpSend += "01234567FA";
		String strOther = "";
		switch (iZmkLen) {
		case 16:
			break;
		case 32:
			if (1 == iZmkVar) {
				strOther = "X";
				strTmpSend += "X";
			} else {
				strOther = "U";
				strTmpSend += "U";
			}
			break;
		case 48:
			if (1 == iZmkVar) {
				strOther = "Y";
				strTmpSend += "Y";
			} else {
				strOther = "T";
				strTmpSend += "T";
			}
			break;
		default:
			break;
		}
		strTmpSend += strZmkBuf;

		switch (iZpkLen) {
		case 16:
			strOther1 += "Z";
			break;
		case 32:
			if (1 == iZpkVar) {
				strOther += "X";
				strTmpSend += "X";
			} else {
				strOther += "U";
				strTmpSend += "U";
			}
			break;
		case 48:
			if (1 == iZpkVar) {
				strOther += "Y";
				strTmpSend += "Y";
			} else {
				strOther += "T";
				strTmpSend += "T";
			}
			break;
		default:
			break;
		}
		strTmpSend += strZpkBuf;

		if ((iZmkLen > 16) || (iZpkLen > 16)) {
			strTmpSend += ";";
			strTmpSend += strOther;
			strTmpSend += strOther1;
			strTmpSend += "0";
		}

		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
		// String strTmp = new String(bLen);
		// strSendData = strTmp + strTmpSend;
		// byte[] bTmp = strSendData.getBytes();
		
		byte[] bTmp = new byte[strTmpSend.length() + 2];
		System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
		System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
		
		byte[] bTmp1 = new byte[1024];
		try {
			iRet = send(ip, port, timeout, bTmp, bTmp1);
			if (iRet > 0) {
				byte[] bTmp2 = new byte[iRet];
				System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
				strRcvData = StringUtil.byte2hex(bTmp2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strRcvData;
	}

	/**
	 * �� ZPK�� ZMK����תΪ LMK���ܣ�zpkFromZmkToLmk
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param strZmk
	 *            ZMK��Կ
	 * @param strZpk
	 *            ZPK��Կ
	 * @return "error"��ʧ�� ֵ���ɹ�
	 */
	private static String zpkFromZmkToLmk(String ip, String port,
			String timeout, String strZmk, String strZpk) throws Exception{
		String strRsp = hsmFA(ip, port, timeout, 1, strZmk.length(), strZmk, 1,
				strZpk.length(), strZpk);
		String strResult = "";
		String strTmp = "";
		String strTmp1 = "";
		String strTmp2 = "";
		// if(!strRsp.isEmpty() && (strRsp.length() > 24))
		if (strRsp.length() > 24) {
			strTmp = strRsp.substring(20);
			strTmp2 = new String(StringUtil.hex2Byte(strTmp));
			strTmp1 = strTmp2.substring(2, 4);
			if (strTmp1.equals("00")) {
				int iTmp = 4;
				if (strZpk.length() > 16)
					iTmp = 4 + 1;
				strResult = strTmp2.substring(iTmp, iTmp + strZpk.length());
			} else
				strResult = strTmp1;
		} else
			strResult = "99";
		return strResult;
	}

	/**
	 * �� ZEK/ZAK �� ZMKתΪ LMK���� hsmFK
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param flag
	 *            ZAK,ZEK��־ 0:ZEK 1:ZAK
	 * @param iZmkVar
	 *            ZMK��Կģʽ��ȡֵ0 ��1 ��iZmkLen����Ϊ16ʱ,ZMK��Կǰ�治�ӱ�־��
	 *            ��ΪiZmkLen����Ϊ32ʱ��iZmkVarΪ1ʱ��ZMK��Կǰ��ӱ�־X��iZmkVarΪ��ʱ��ZMK��Կǰ��ӱ�־U��
	 *            ��ΪiZmkLen����Ϊ48ʱ��iZmkVarΪ1ʱ��ZMK��Կǰ��ӱ�־Y��iZmkVarΪ��ʱ��ZMK��Կǰ��ӱ�־T��
	 * @param iZmkLen
	 *            ZMK��Կ�ĳ��� 16��32��48
	 * @param strZmkBuf
	 *            ZMK��Կ
	 * @param iZeakVar
	 *            ZeaK��Կģʽ��ȡֵ0 ��1 ˵��ͬiZmkVar
	 * @param iZeakLen
	 *            ZeaK��Կ�ĳ��� 16��32��48
	 * @param strZeakBuf
	 *            ZeaK��Կ
	 * @return �գ�ʧ�� �ǿգ��ɹ�
	 */
	private static String hsmFK(String ip, String port, String timeout,
			String flag, int iZmkVar, int iZmkLen, String strZmkBuf,
			int iZeakVar, int iZeakLen, String strZeakBuf) throws Exception{
		int iRet = 0;
		String strTmpSend = "";
	// String strSendData = "";
		String strRcvData = "";
		strTmpSend += "01234567FK";
		strTmpSend += flag;
		String strOther = "";
		String strOther1 = "";
		switch (iZmkLen) {
		case 16:
			break;
		case 32:
			if (1 == iZmkVar) {
				strOther = "X";
				strTmpSend += "X";
			} else {
				strOther = "U";
				strTmpSend += "U";
			}
			break;
		case 48:
			if (1 == iZmkVar) {
				strOther = "Y";
				strTmpSend += "Y";
			} else {
				strOther = "T";
				strTmpSend += "T";
			}
			break;
		default:
			break;
		}
		strTmpSend += strZmkBuf;

		switch (iZeakLen) {
		case 16:
			strOther1 += "Z";
			break;

		case 32:
			if (1 == iZeakVar) {
				strOther += "X";
				strTmpSend += "X";
			} else {
				strOther += "U";
				strTmpSend += "U";
			}
			break;
		case 48:
			if (1 == iZeakVar) {
				strOther += "Y";
				strTmpSend += "Y";
			} else {
				strOther += "T";
				strTmpSend += "T";
			}
			break;
		default:
			break;
		}
		strTmpSend += strZeakBuf;

		if ((iZmkLen > 16) || (iZeakLen > 16)) {
			strTmpSend += ";";
			strTmpSend += strOther;
			strTmpSend += strOther1;
			strTmpSend += "0";
		}

		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
	// String strTmp = new String(bLen);
	// strSendData = strTmp + strTmpSend;
	// byte[] bTmp = strSendData.getBytes();
		
		byte[] bTmp = new byte[strTmpSend.length() + 2];
		System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
		System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
		
		byte[] bTmp1 = new byte[1024];
		try {
			iRet = send(ip, port, timeout, bTmp, bTmp1);
			if (iRet > 0) {
				byte[] bTmp2 = new byte[iRet];
				System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
				strRcvData = StringUtil.byte2hex(bTmp2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strRcvData;
	}

	/**
	 * �� ZAK�� ZMK����תΪ LMK���ܣ�zakFromZmkToLmk
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param strZmk
	 *            ZMK��Կ
	 * @param strZak
	 *            ZaK��Կ
	 * @return "error"��ʧ�� ֵ���ɹ�
	 */
	private static String zakFromZmkToLmk(String ip, String port,
			String timeout, String strZmk, String strZak) throws Exception{
		String strRsp = hsmFK(ip, port, timeout, "1", 1, strZmk.length(),
				strZmk, 1, strZak.length(), strZak);
		String strResult = "";
		String strTmp = "";
		String strTmp1 = "";
		String strTmp2 = "";

		// if(!strRsp.isEmpty() && (strRsp.length() > 24))
		if (strRsp.length() > 24) {
			strTmp = strRsp.substring(20);
			strTmp2 = new String(StringUtil.hex2Byte(strTmp));
			strTmp1 = strTmp2.substring(2, 4);
			if (strTmp1.equals("00")) {
				int iTmp = 4;
				if (strZak.length() > 16)
					iTmp += 1;
				strResult = strTmp2.substring(iTmp, iTmp + strZak.length());
			} else
				strResult = strTmp1;
		} else
			strResult = "99";
		return strResult;
	}

	/**
	 * �� TAK �� ZAK �Դ���Ϣ���� MAC��MAB��hsmMS
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param msgNum
	 *            ��Ϣ��� 0����һ�飻1����һ�飻2���м�飻3�����顣
	 * @param keyType
	 *            ��Կ���� 0:TAK���ն���֤��Կ��1:ZAK��������֤��Կ��
	 * @param lenOfKey
	 *            KEY���� 16:������ 32:˫����
	 * @param msgType
	 *            ��Ϣ���� 0:��Ϣ����Ϊ������ 1:��Ϣ����Ϊ��չʮ������
	 * @param keyVar
	 *            ��Կģʽ��ȡֵ0:ANSI X9.17 ��ʽ��1:������ʽ
	 *            ��iZmkLen����Ϊ16ʱ,ZMK��Կǰ�治�ӱ�־,��ʱiZmkVarֵ�������ã�
	 *            ��ΪiZmkLen����Ϊ32ʱ��iZmkVarΪ1ʱ��ZMK��Կǰ��ӱ�־X��iZmkVarΪ��ʱ��ZMK��Կǰ��ӱ�־U��
	 *            ��ΪiZmkLen����Ϊ48ʱ��iZmkVarΪ1ʱ��ZMK��Կǰ��ӱ�־Y��iZmkVarΪ��ʱ��ZMK��Կǰ��ӱ�־T��
	 * @param key
	 *            ��ӦLMK���¼��ܵ���Կ 16��32����
	 * @param iv
	 *            ��ʼֵ��������Ϣ���msgNumΪ2 ��3ʱ����
	 * @param lenOfMsg
	 *            ��Ϣ����,��Ϣ�����16������չ��Ϊһ��ĳ��ȣ�Ϊ��������Ϊʵ�ʳ���
	 * @param strMsg
	 *            ��Ϣ��
	 * @param strCMD
	 *            ���� MS��MU
	 * @return �գ�ʧ�� �ǿգ��ɹ�
	 */
	private static String hsmMS_MU(String ip, String port, String timeout,
			String msgNum, String keyType, String lenOfKey, String msgType,
			String keyVar, String key, String iv, String strLenOfMsg,
			String strMsg, String strCMD) throws Exception{
		int iRet = 0;
		// int iLenOfMsg = Integer.parseInt(lenOfMsg);
		// String strLenOfMsg = String.format("%04d", lenOfMsg);
		// strLenOfMsg.f
		String strTmpSend = "";
	// String strSendData = "";
		String strRcvData = "";
		strTmpSend += "01234567";
		strTmpSend += strCMD;
		strTmpSend += msgNum;// ��Ϣ��� 0����һ�飻1����һ�飻2���м�飻3�����顣
		strTmpSend += keyType;// ��Կ���� 0:TAK���ն���֤��Կ��1:ZAK��������֤��Կ��
		String strTmpVar = "";
		if (lenOfKey.equals("16"))// ��Կ���� 0����������DES��Կ 1��˫������DES��Կ
		{
			strTmpSend += "0";
		} else if (lenOfKey.equals("32")) {
			strTmpSend += "1";
			if (keyVar.equals("1"))
				strTmpVar = "X";
			else
				strTmpVar = "U";
		} else {
			strTmpSend += "2";//
			if (keyVar.equals("1"))
				strTmpVar = "Y";
			else
				strTmpVar = "T";
		}
		strTmpSend += msgType;// ��Ϣ���� 0:��Ϣ����Ϊ������ 1:��Ϣ����Ϊ��չʮ������
		strTmpSend += strTmpVar;
		strTmpSend += key;
		if (!msgNum.equals("0") && !msgNum.equals("1"))
			strTmpSend += iv;

		strTmpSend += strLenOfMsg;
		strTmpSend += strMsg;

		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
		// String strTmp = new String(bLen);
		// strSendData = strTmp + strTmpSend;
		// byte[] bTmp = strSendData.getBytes();
		
		byte[] bTmp = new byte[strTmpSend.length() + 2];
		System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
		System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
		byte[] bTmp1 = new byte[1024];
		try {
			iRet = send(ip, port, timeout, bTmp, bTmp1);
			if (iRet > 0) {
				byte[] bTmp2 = new byte[iRet];
				System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
				strRcvData = StringUtil.byte2hex(bTmp2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strRcvData;
	}

	/**
	 * ��PIN���ZPK�¼��ܷ��뵽��һ��ZPK�¼���,hsmCC
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param msgNum
	 *            ��Ϣ��� 0����һ�飻1����һ�飻2���м�飻3�����顣
	 * @param iSrcLen
	 *            ԴZPK���ȣ�16 32 48
	 * @param iSrcVar
	 *            ��Կģʽ��ȡֵ0:ANSI X9.17 ��ʽ��1:������ʽ
	 *            ��iSrcLen����Ϊ16ʱ,srcZPK��Կǰ�治�ӱ�־,��ʱiSrcVarֵ�������ã�
	 *            ��ΪiSrcLen����Ϊ32ʱ��iSrcVarΪ1ʱ��srcZPK��Կǰ��ӱ�־X��iSrcVarΪ��ʱ��srcZPK��Կǰ��ӱ�־U��
	 *            ��ΪiSrcLen����Ϊ48ʱ��iSrcVarΪ1ʱ��srcZPK��Կǰ��ӱ�־Y��iSrcVarΪ��ʱ��srcZPK��Կǰ��ӱ�־T��
	 * @param srcZpk
	 *            ԴZPK
	 * @param srcPbk
	 *            ԴZPK�¼��ܵ�ԴPIN��
	 * @param srcSrcFmt
	 *            ԴPIN��ĸ�ʽ����
	 * @param iDstLen
	 *            Ŀ��ZPK���ȣ�16 32 48
	 * @param iDstVar
	 *            ͬiSrcVar
	 * @param dstZpk
	 *            Ŀ��ZPK
	 * @param dstFmt
	 *            Ŀ��PIN��ĸ�ʽ����
	 * @param panBuf
	 *            �˺���ȥ��У��λ������12 λ
	 * @return �գ�ʧ�� �ǿգ��ɹ�
	 */
	private static String hsmCC(String ip, String port, String timeout,
			int iSrcLen, int iSrcVar, String srcZpk, String srcPbk,
			String srcSrcFmt, int iDstLen, int iDstVar, String dstZpk,
			String dstFmt, String panBuf) throws Exception{
		int iRet = 0;
		// strLenOfMsg.f
		String strTmpSend = "";
	// String strSendData = "";
		String strRcvData = "";
		strTmpSend += "01234567CC";
		switch (iSrcLen) {
		case 16:
			break;
		case 32:
			if (iSrcVar == 1)
				strTmpSend += "X";
			else
				strTmpSend += "U";
			break;
		case 48:
			if (iSrcVar == 1)
				strTmpSend += "Y";
			else
				strTmpSend += "T";
			break;
		default:
			break;
		}
		strTmpSend += srcZpk;
		switch (iDstLen) {
		case 16:
			break;
		case 32:
			if (iDstVar == 1)
				strTmpSend += "X";
			else
				strTmpSend += "U";
			break;
		case 48:
			if (iDstVar == 1)
				strTmpSend += "Y";
			else
				strTmpSend += "T";
			break;
		default:
			break;
		}
		strTmpSend += dstZpk;
		strTmpSend += "12";
		strTmpSend += srcPbk;
		strTmpSend += srcSrcFmt;
		strTmpSend += dstFmt;
		strTmpSend += panBuf;
		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
	// String strTmp = new String(bLen);
	// strSendData = strTmp + strTmpSend;
		// byte[] bTmp = strSendData.getBytes();
		byte[] bTmp = new byte[strTmpSend.length() + 2];
		System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
		System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
		byte[] bTmp1 = new byte[1024];
		try {
			iRet = send(ip, port, timeout, bTmp, bTmp1);
			if (iRet > 0) {
				byte[] bTmp2 = new byte[iRet];
				System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
				strRcvData = StringUtil.byte2hex(bTmp2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return strRcvData;
	}

	/**
	 * �� PIN��� LMK�¼��ܷ��뵽 ZPK�¼���
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param desZpk
	 *            LMK�¼��ܵ�ZPKֵ
	 * @param pinBlockCode
	 *            PINBLOCK�ĸ�ʽ����
	 * @param accNo
	 *            �˺���ȥ��У��λ������12λ
	 * @param pin
	 *            LMK�ԣ�02-03���¼��ܵ�PIN
	 * @return �գ�ʧ�� ֵ���ɹ�
	 */
	private static String hsmJG(String ip, String port, String timeout,
			String desZpk, String pinBlockCode, String accNo, String pin)throws Exception
	{
		int iRet = 0;
		String strRcvData = "";
		String strTmpSend = "";
		strTmpSend += "01234567JG";
		int iSrcLen = desZpk.length();
		switch (iSrcLen) {
		case 16:
			break;
		case 32:
				strTmpSend += "X";
			break;
		case 48:
				strTmpSend += "Y";
			break;
		default:
			break;
		}
		strTmpSend += desZpk;
		strTmpSend += pinBlockCode;
		strTmpSend += accNo;
		strTmpSend += pin;
		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
		// String strTmp = new String(bLen);
		// strSendData = strTmp + strTmpSend;
			// byte[] bTmp = strSendData.getBytes();
			byte[] bTmp = new byte[strTmpSend.length() + 2];
			System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
			System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
			byte[] bTmp1 = new byte[1024];
			try {
				iRet = send(ip, port, timeout, bTmp, bTmp1);
				if (iRet > 0) {
					byte[] bTmp2 = new byte[iRet];
					System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
					strRcvData = StringUtil.byte2hex(bTmp2);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return strRcvData;
	}
	/**
	 * ��PIN��� ZPK�¼��ܷ��뵽 LMK�¼���
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param srcZpk
	 *            LMK�¼��ܵ�ZPKֵ
	 * @param pinBlockCode
	 *            PINBLOCK�ĸ�ʽ����
	 * @param accNo
	 *            �˺���ȥ��У��λ������12λ
	 * @param pinBlock
	 *            LMK�ԣ�02-03���¼��ܵ�PIN
	 * @return �գ�ʧ�� ֵ���ɹ�
	 */
	private static String hsmJE(String ip, String port, String timeout,
			String srcZpk, String pinBlock, String pinBlockCode, String accNo)throws Exception
	{
		int iRet = 0;
		String strRcvData = "";
		String strTmpSend = "";
		strTmpSend += "01234567JE";
		int iSrcLen = srcZpk.length();
		switch (iSrcLen) {
		case 16:
			break;
		case 32:
				strTmpSend += "X";
			break;
		case 48:
				strTmpSend += "Y";
			break;
		default:
			break;
		}
		strTmpSend += srcZpk;
		strTmpSend += pinBlock;
		strTmpSend += pinBlockCode;
		strTmpSend += accNo;
		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
		// String strTmp = new String(bLen);
		// strSendData = strTmp + strTmpSend;
			// byte[] bTmp = strSendData.getBytes();
			byte[] bTmp = new byte[strTmpSend.length() + 2];
			System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
			System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
			byte[] bTmp1 = new byte[1024];
			try {
				iRet = send(ip, port, timeout, bTmp, bTmp1);
				if (iRet > 0) {
					byte[] bTmp2 = new byte[iRet];
					System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
					strRcvData = StringUtil.byte2hex(bTmp2);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return strRcvData;
	}
	/**
	 * ����һ���ı� PIN������
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param pin
	 *            ���ĵ�PIN
	 * @param accNo
	 *            �˺���ȥ��У��λ������12λ
	 * @return �գ�ʧ�� ֵ���ɹ�
	 */
	private static String hsmBA(String ip, String port, String timeout,
			String pin,String accNo)throws Exception
	{
		int iRet = 0;
		String strRcvData = "";
		String strTmpSend = "";
		strTmpSend += "01234567BA";
		strTmpSend += pin;
		strTmpSend += "F";
		strTmpSend += accNo;
		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
		// String strTmp = new String(bLen);
		// strSendData = strTmp + strTmpSend;
			// byte[] bTmp = strSendData.getBytes();
			byte[] bTmp = new byte[strTmpSend.length() + 2];
			System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
			System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
			byte[] bTmp1 = new byte[1024];
			try {
				iRet = send(ip, port, timeout, bTmp, bTmp1);
				if (iRet > 0) {
					byte[] bTmp2 = new byte[iRet];
					System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
					strRcvData = StringUtil.byte2hex(bTmp2);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return strRcvData;
	}
	/**
	 * ����һ���Ѽ��ܵ� PIN
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param pin
	 *            ���ܵ�PIN
	 * @param accNo
	 *            �˺���ȥ��У��λ������12λ
	 * @return �գ�ʧ�� ֵ���ɹ�
	 */
	private static String hsmNG(String ip, String port, String timeout,
			String pin,String accNo)throws Exception
	{
		int iRet = 0;
		String strRcvData = "";
		String strTmpSend = "";
		strTmpSend += "01234567NG";
		strTmpSend += accNo;
		strTmpSend += pin;

		byte[] bLen = StringUtil.short2byte(strTmpSend.length());
		// String strTmp = new String(bLen);
		// strSendData = strTmp + strTmpSend;
			// byte[] bTmp = strSendData.getBytes();
			byte[] bTmp = new byte[strTmpSend.length() + 2];
			System.arraycopy(bLen, 0, bTmp, 0, bLen.length);
			System.arraycopy(strTmpSend.getBytes(), 0, bTmp, bLen.length, strTmpSend.length());
			byte[] bTmp1 = new byte[1024];
			try {
				iRet = send(ip, port, timeout, bTmp, bTmp1);
				if (iRet > 0) {
					byte[] bTmp2 = new byte[iRet];
					System.arraycopy(bTmp1, 0, bTmp2, 0, iRet);
					strRcvData = StringUtil.byte2hex(bTmp2);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return strRcvData;
	}
	/**
	 * ��PIN���ZPK�¼��ܷ��뵽��һ��ZPK�¼��ܻ��ߴ�һ�ָ�ʽת��Ϊ��һ�ָ�ʽ
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param srcZPK
	 *            ԴZPK
	 * @param dstZPK
	 *            Ŀ��ZPK
	 * @param panBuf
	 *            ԴZPK���ܵ�PIN
	 * @return �գ�ʧ�� ֵ���ɹ�
	 */
	public static String translatePIN(String ip, String port, String timeout,
			String srcZPK, String dstZPK, String srcPbk, String panBuf)throws Exception {
		String strRsp = hsmCC(ip, port, timeout, srcZPK.length(), 1, srcZPK,
				srcPbk, "01", dstZPK.length(), 1, dstZPK, "01", panBuf);
		String strResult = "";
		String strTmp = "";
		String strTmp1 = "";
		String strTmp2 = "";

		// strResult = new String(StringUtil.hex2Byte(strRsp));//Tmp
		// if (!strRsp.isEmpty() && (strRsp.length() > 24)) {
		if ((strRsp.length() > 24)) {
			strTmp = strRsp.substring(20);
			strTmp2 = new String(StringUtil.hex2Byte(strTmp));
			strResult = strTmp2;
			strTmp1 = strTmp2.substring(2, 4);
			if (strTmp1.equals("00"))
				strResult = strTmp2.substring(6, 22);
			else
				strResult = strTmp1;
		} else
			strResult = "99";
		return strResult;
	}

	/**
	 * ����MACУ��ֵgenerateMac
	 * 
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param key
	 *            ��Կ
	 * @param strMsg
	 *            Ҫ����MAC������
	 * @param msgType
	 *            Ҫ����MAC������.����ASCII��ʽ ������չ1��������ʽ
	 * @param strCMD
	 *            ���MS��MU ���ܻ�����ǰ��MS��MUΪ����ר��ָ��
	 */
	public static String generateMac(String ip, String port, String timeout,
			String key, String strMsg, String msgType, String strCMD) throws Exception{
		int iLen = key.length();
		int iMsgLen = 0;
		if (msgType.equals("1"))
			iMsgLen = strMsg.length() / 2;
		else
			iMsgLen = strMsg.length();
		String strMsgLenTmp = Integer.toHexString(iMsgLen);
		String strMsgLen = "";
		if (strMsgLenTmp.length() < 4)
			for (int i = strMsgLenTmp.length(); i < 4; i++)
				strMsgLen += "0";
		strMsgLen += strMsgLenTmp;
		strMsgLen = strMsgLen.toUpperCase();

		String strRsp = hsmMS_MU(ip, port, timeout, "0", "1", Integer
				.toString(iLen), msgType, "1", key, "0", strMsgLen, strMsg,
				strCMD);
		String strResult = "";
		String strTmp = "";
		String strTmp1 = "";
		String strTmp2 = "";
		strResult = new String(StringUtil.hex2Byte(strRsp));
		// if (!strRsp.isEmpty() && (strRsp.length() > 24)) {
		if (strRsp.length() > 24) {
			strTmp = strRsp.substring(20);
			strTmp2 = new String(StringUtil.hex2Byte(strTmp));
			strTmp1 = strTmp2.substring(2, 4);
			if (strTmp1.equals("00"))
				strResult = strTmp2.substring(4);
			else
				strResult = strTmp1;
		} else
			strResult = "99";
		return strResult;
	}

	/**
	 * ͨ�ź�����send
	 * 
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param data
	 *            ���͵���������
	 * @param data
	 *            ����ص�����
	 * @return -1:ʧ�� >0:�ɹ�
	 */
	private static int send(String ip, String port, String timeout,
			byte[] data, byte[] bRecvData) throws Exception {
		Socket socket = null;
		PrintStream ps = null;
		BufferedInputStream bis = null;

		// for test
		byte[] data1 = new byte[data.length];
		System.arraycopy(data, 2, data1, 0, data.length - 2);
		System.out.println("send data is :" + new String(data1));

		int iRet = 0;
		int tout = Integer.parseInt(timeout) * 1000;
		try {
			socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(ip, Integer
					.parseInt(port));
			socket.connect(address, tout);
		} catch (IOException e1) {
			iRet = -1;
			socket = null;
			e1.printStackTrace();
		}

		if (iRet != -1) {
			try {
				socket.setSoTimeout(tout);
				ps = new PrintStream(socket.getOutputStream(), true);
				bis = new BufferedInputStream(socket.getInputStream());
				ps.write(data);
				byte[] tmp = new byte[1024];
				iRet = bis.read(tmp);
				System.arraycopy(tmp, 0, bRecvData, 0, iRet);
				// byte[] result = new byte[rlen];
				// System.arraycopy(tmp, 0, result, 0, rlen);
				// returndata = StringUtil.byte2hex(result);
			} catch (IOException e) {
				iRet = -1;
				e.printStackTrace();
				throw new Exception("Input(HEX):" + StringUtil.byte2hex(data)
						+ "   Output(HEX):" + StringUtil.byte2hex(bRecvData));
			} finally {
				try {
					bis.close();
					ps.close();
					socket.close();
				} catch (IOException e) {
					socket = null;
					bis = null;
					ps = null;
				}
			}
		}
		return iRet;
	}

	/**
	 * �õ�MAC��Կ
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param strZmk
	 *            ZMK��Կ
	 * @param strZak
	 *            ZaK��Կ
	 * @return "error"��ʧ�� ֵ���ɹ�
	 */
	public static String applyMacKey(String ip, String port, String timeout,
			String strZMK, String strZAK) throws Exception{
		String strRsp = zakFromZmkToLmk(ip, port, timeout, strZMK, strZAK);
		return strRsp;
	}
	
	/**
	 * �õ�PIN��Կ
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param strZmk
	 *            ZMK��Կ
	 * @param strZpk
	 *            ZpK��Կ
	 * @return "error"��ʧ�� ֵ���ɹ�
	 */
	public static String applyPinKey(String ip, String port, String timeout,
			String strZmk, String strZpk) throws Exception{
		String strRsp = zpkFromZmkToLmk(ip, port, timeout, strZmk, strZpk);
		return strRsp;
	}
	/**
	 * ������PIN���м��ܣ���ת��Ϊ���ܻ����ؼ���
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param pin
	 *            PIN����
	 * @param accno
	 *            �˺���ȥ��У��λ������12λ
	 * @param desZpk
	 *            ZpK��Կ
	 * @return "error"��ʧ�� ֵ���ɹ�
	 */
	public static String encryptePin(String ip, String port, String timeout,
			String pin,String accno,String desZpk)throws Exception
	{
		String strRspBA = hsmBA(ip, port, timeout,pin,accno);
		String strRspJG = "";
		String strTmp = "";
		String strTmp1 = "";
		String strTmp2 = "";

		// if(!strRsp.isEmpty() && (strRsp.length() > 24))
		if (strRspBA.length() > 24) {
			strTmp = strRspBA.substring(20);
			strTmp2 = new String(StringUtil.hex2Byte(strTmp));
			strTmp1 = strTmp2.substring(2, 4);
			if (strTmp1.equals("00")) {
				int iTmp = 4;
				strRspBA = strTmp2.substring(iTmp);
			} else
				strRspBA = strTmp1;
		} else
			strRspBA = "99";
		
		if(strRspBA.length() > 3)
		{
			strRspJG = hsmJG(ip, port, timeout,desZpk,"01",accno,strRspBA);
			if (strRspJG.length() > 24) {
				strTmp = strRspJG.substring(20);
				strTmp2 = new String(StringUtil.hex2Byte(strTmp));
				strTmp1 = strTmp2.substring(2, 4);
				if (strTmp1.equals("00")) {
					int iTmp = 4;
					strRspJG = strTmp2.substring(iTmp);
				} else
					strRspJG = strTmp1;
			} else
				strRspJG = "99";
		}
		else
			return strRspBA;
		
		return strRspJG;
	}
	/**
	 * ��PIN���н��ܣ���ת��Ϊ���ܻ����ؼ���
	 * 
	 * @author qbchen
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param pinBlock
	 *            ZPK���ܺ��PINBLOCK
	 * @param accno
	 *            �˺���ȥ��У��λ������12λ
	 * @param srcZpk
	 *            ZpK��Կ
	 * @return "error"��ʧ�� ֵ���ɹ�
	 */
	public static String decodePin(String ip, String port, String timeout,
			String srcZpk,String pinBlock,String accNo)throws Exception
	{
		String strRspJE = hsmJE(ip, port, timeout,srcZpk,pinBlock,"01",accNo);
		String strRspNG = "";
		String strTmp = "";
		String strTmp1 = "";
		String strTmp2 = "";

		// if(!strRsp.isEmpty() && (strRsp.length() > 24))
		if (strRspJE.length() > 24) {
			strTmp = strRspJE.substring(20);
			strTmp2 = new String(StringUtil.hex2Byte(strTmp));
			strTmp1 = strTmp2.substring(2, 4);
			if (strTmp1.equals("00")) {
				int iTmp = 4;
				strRspJE = strTmp2.substring(iTmp);
			} else
				strRspJE = strTmp1;
		} else
			strRspJE = "99";
		
		if(strRspJE.length() > 3)
		{
			strRspNG = hsmNG(ip, port, timeout,strRspJE,accNo);
			if (strRspNG.length() > 24) {
				strTmp = strRspNG.substring(20);
				strTmp2 = new String(StringUtil.hex2Byte(strTmp));
				strTmp1 = strTmp2.substring(2, 4);
				if (strTmp1.equals("00")) {
					int iTmp = 4;
					int iTest = strTmp2.indexOf('F');
					strRspNG = strTmp2.substring(iTmp, iTest);
				} else
					strRspNG = strTmp1;
			} else
				strRspNG = "99";
		}
		else
			return strRspJE;
		
		return strRspNG;
	}
	
	/**
	 * Pinת�����ȸ���ԭ��Կ���㷨���ܣ��ڸ���Ŀ����Կ���㷨����
	 * 
	 * @author ������ alter by qbchen
	 * @param oriPinValue
	 *            ʮ�����Ʊ�ʾ��ԭʼ����pin
	 * @param accountNo
	 *            �ʺ�
	 * @param oriMk
	 *            ԭ��������Կ
	 * @param oriPink
	 *            ԭ����pin��Կ
	 * @param oriAcctFlag
	 *            ԭ�Ƿ�����˺ű�־��1��ʾ�����˺ţ�0��ʾ�������ʺ�
	 * @param oriPinArith
	 *            ԭpin�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
	 * @param oriMArith
	 *            ԭpinKey�����㷨��DES-DES�㷨
	 * @param destMkey
	 *            Ŀ����������Կ
	 * @param destPinKey
	 *            Ŀ������pin��Կ
	 * @param destAcctFlagĿ���Ƿ�����˺ű�־��1��ʾ�����˺ţ�0��ʾ�������ʺ�
	 * @param destPinArith
	 *            Ŀ��pin�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
	 * @param destMArith
	 *            Ŀ��pinKey�����㷨��DES-DES�㷨
	 * @return ת�����õ���ʮ�����Ʊ�ʾpin����
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param oriEncFlag
	 *            ԭ���ܱ�־��0����ܣ�2Ӳ����
	 * @param destEncFlag
	 *            Ŀ����ܱ�־��0����ܣ�2Ӳ����
	 */
	public static String pinTransfer(String oriPinValue, String accountNo,
			String oriMk, String oriPink, String oriAcctFlag,
			String oriPinArith, String oriMArith, String destMkey,
			String destPinKey, String destAcctFlag, String destPinArith,
			String destMArith, String ip, String port, String timeout,
			String oriEncFlag, String destEncFlag) throws Exception {

		try {
			// ��oriMk��oriPink����
			int oriPikLen = oriPink.length() / 2;
			String oriPinBlock = "";
			int oriMkLen = oriMk.length() / 2;
			int oriPinkLen = oriPink.length() / 2;

			byte[] oriPik = new byte[oriPikLen];
			if ("0".equals(oriEncFlag)) {// �����
				if ("DES".equals(oriMArith)) {
					if (oriMkLen == 8) {
						oriPik = Des.decodeDES(StringUtil.hex2Byte(oriPink
								.trim()), StringUtil.hex2Byte(oriMk.trim()));
					} else if (oriMkLen == 16) {
						oriPik = Des.decodeDES2(StringUtil.hex2Byte(oriPink
								.trim()), StringUtil.hex2Byte(oriMk.trim()));
					} else if (oriMkLen == 24) {
						oriPik = Des.decodeDES3(StringUtil.hex2Byte(oriPink
								.trim()), StringUtil.hex2Byte(oriMk.trim()));
					}
				}

				// �ý��ܺ��oriPink��oriPinValue����
				if ("DES".equals(oriPinArith)) {
					if (oriPinkLen == 8) {
						oriPinBlock = StringUtil.byte2hex(Des.decodeDES(
								StringUtil.hex2Byte(oriPinValue), oriPik));
					} else if (oriPinkLen == 16) {
						oriPinBlock = StringUtil.byte2hex(Des.decodeDES2(
								StringUtil.hex2Byte(oriPinValue), oriPik));
					} else if (oriPinkLen == 24) {
						oriPinBlock = StringUtil.byte2hex(Des.decodeDES3(
								StringUtil.hex2Byte(oriPinValue), oriPik));
					}
				}
			} else if ("2".equals(oriEncFlag)) {// Ӳ����
// just for test oriPinBlock = HardwareCryp.getPin(ip, port, timeout, oriPink,
// oriPinValue, "0").substring(2);
				oriPinBlock = decodePin(ip, port, timeout, oriPink,oriPinValue, StringUtil.getPanByAcctString(accountNo));
				oriPinBlock = StringUtil.getPinBlock(oriPinBlock, accountNo);
			}
			// �õ���������Array
			byte[] oriPinArray = new byte[8];

			if ("1".equals(oriAcctFlag)) {
				byte[] panArray = StringUtil.getPanByAcct(accountNo);
				oriPinArray = StringUtil.getNextMac(StringUtil.hex2Byte(oriPinBlock),
						panArray);
			} else {
				oriPinArray = StringUtil.hex2Byte(oriPinBlock);
			}

			// ����destPinBlock
			byte[] destBlockArray = new byte[8];
			if ("1".equals(destAcctFlag)) {
				byte[] panArray = StringUtil.getPanByAcct(accountNo);
				destBlockArray = StringUtil.getNextMac(oriPinArray, panArray);
			} else {
				destBlockArray = oriPinArray;
			}

			// ��destMkey��destPinKey����
			int destPikLen = destPinKey.length() / 2;
			int destMkeyLen = destMkey.length() / 2;
			int destPinKeyLen = destPinKey.length() / 2;
			byte[] destPik = new byte[destPikLen];
			String transferedPin = "";
			if ("0".equals(destEncFlag)) {
				if ("DES".equals(destMArith)) {
					if (destMkeyLen == 8) {
						destPik = Des.decodeDES(
								StringUtil.hex2Byte(destPinKey), StringUtil
										.hex2Byte(destMkey.trim()));
					} else if (destMkeyLen == 16) {
						destPik = Des.decodeDES2(StringUtil
								.hex2Byte(destPinKey), StringUtil
								.hex2Byte(destMkey.trim()));
					} else if (destMkeyLen == 24) {
						destPik = Des.decodeDES3(StringUtil
								.hex2Byte(destPinKey), StringUtil
								.hex2Byte(destMkey.trim()));
					}
				}

				// �ý��ܺ��destPinKey��PinBlock����
				if ("DES".equals(destMArith)) {
					if (destPinKeyLen == 8) {
						transferedPin = StringUtil.byte2hex(Des.encodeDES(
								destBlockArray, destPik));
					} else if (destPinKeyLen == 16) {
						transferedPin = StringUtil.byte2hex(Des.encodeDES2(
								destBlockArray, destPik));
					} else if (destPinKeyLen == 24) {
						transferedPin = StringUtil.byte2hex(Des.encodeDES3(
								destBlockArray, destPik));
					}
				}
			} else if ("2".equals(destEncFlag)) {
// just for test transferedPin = HardwareCryp.getPin(ip, port, timeout,
// destPinKey, StringUtil.byte2hex(destBlockArray), "1")
// .substring(2);
				//destBlockArray 06111111EFFFFFFB
				byte[] panArrayTmp = StringUtil.getPanByAcct(accountNo);
				byte[] bPinTmp = StringUtil.getNextMac(destBlockArray, panArrayTmp);
				String strPinTmp = StringUtil.byte2hex(bPinTmp);
				int iPinLen = Integer.parseInt(strPinTmp.substring(0, 2));
				String strPin = strPinTmp.substring(2, 2+iPinLen);
				transferedPin = encryptePin(ip, port, timeout,strPin,
						StringUtil.getPanByAcctString(accountNo),destPinKey);
			}

			return transferedPin;
		} catch (Exception e) {
			throw e;
		}
	}
/*	
	public static void main(String[] args) {
		String strIp = "198.198.198.250";
		String strPort = "8";
		String strTimeout = "5";
		String strRspCode = "";
		String strRet = "";
		String pan = "012345678912";
		
	// String strRet5 = applyPinKey(strIp, strPort,
	// strTimeout,"37190E58A6969B6D37190E58A6969B6D","B6862EE825DD8D1C0697F46027F89303");
// String strRet1 = encryptePin(strIp, strPort,
// strTimeout,"123456",pan,"B6862EE825DD8D1C0697F46027F89303");
		
 String strRet2 = decodePin(strIp, strPort,
 strTimeout,"35C7DEB6CAEA538A","E2B341EACD7D22D8","000010000004");
		
		try{
	// byte[] panArray = StringUtil.getPanByAcct("12345678901234");
			
		String strRet3 = pinTransfer("F6DA9C06352C8686","6229760000100000040","1234567890ABCDEF1234567890ABCDEF",
				"AFC5C81B828875BC",
				"1","DES","DES","1234567890ABCDEF1234567890ABCDEF",
				"35C7DEB6CAEA538A",
				"1","DES","DES",strIp, strPort, strTimeout,"0","2");
		}catch(Exception e)
		{
			System.out.println(e);
		}
		// String strRet1 = applyMacKey(strIp, strPort, strTimeout,
		// "48418879C5EAA68448418879C5EAA684",
		// "E9167CB9497BB90CEF2C6534A8685171");
// String strRet4 = zakFromZmkToLmk(strIp, strPort, strTimeout,
// "48418879C5EAA684", "686F6445FA2F8253");
// String strRet2 = generateMac(strIp,strPort,strTimeout, "8AAB233AF1401ABE",
// "0200 199558990000000000001 300000 1012124311","0","MS");
// String strRet3 = generateMac(strIp,strPort,strTimeout, "8AAB233AF1401ABE",
// "0200 199558990000000000001 300000 1012124311 006355 6011 02 0814033055
// 0814033055 12345678 123456789012345",
// "0","MS");
/*
 * String strRet2 = translatePIN(strIp,strPort,strTimeout, "59BBEF9B9A221099",
 * "B8E929DB99FE88C0", "8FB262E14D62D592", "9558990000000000001"); /* //
 * ����PINKEY // strRet = //
 * applyPinKey(strIp,strPort,strTimeout,"48418879C5EAA68448418879C5EAA684","0CB7AAFC68E99F134F0D29C7A31D106F"); //
 * ����PINKEY // String strRet1 = //
 * applyPinKey(strIp,strPort,strTimeout,"48418879C5EAA684","38863831A0F1FF51"); //
 * ���м��ܵ�PIN���������ܵ�PIN // String strRet2 = translatePIN(strIp,strPort,strTimeout,
 * strRet, // strRet1, "3A984F19EEB045F7", pan); // String strRet2 =
 * translatePIN(strIp,strPort,strTimeout, // "502BEE2B7D69D9B2",
 * "062700302F7A948D062700302F7A948D", // "0188754EE2C3C2F6", pan); String
 * strRet2 = translatePIN(strIp,strPort,strTimeout, // "2856FD3F765973BB",
 * "8E079C959826318C7E31B07867E40DDB", // "B9CD4FEE991937C7", pan);
 * 
 */	 
}



