package com.nasoft.security;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HsmApi {
	/**
	 * 将 ZPK由 ZMK加密转为 LMK加密，hsmFA
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param iZmkVar
	 *            ZMK密钥模式，取值0:ANSI X9.17 方式；1:变量方式
	 *            当iZmkLen长度为16时,ZMK密钥前面不加标志,这时iZmkVar值不起作用；
	 *            当为iZmkLen长度为32时：iZmkVar为1时，ZMK密钥前面加标志X；iZmkVar为０时，ZMK密钥前面加标志U；
	 *            当为iZmkLen长度为48时：iZmkVar为1时，ZMK密钥前面加标志Y；iZmkVar为０时，ZMK密钥前面加标志T；
	 * @param iZmkLen
	 *            ZMK密钥的长度 16、32、48
	 * @param strZmkBuf
	 *            ZMK密钥
	 * @param iZpkVar
	 *            ZpK密钥模式,同iZmkVar说明
	 * @param iZpkLen
	 *            ZpK密钥的长度 16、32、48
	 * @param strZpkBuf
	 *            ZPK密钥
	 * @return 空：失败 非空：成功
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
	 * 将 ZPK由 ZMK加密转为 LMK加密，zpkFromZmkToLmk
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param strZmk
	 *            ZMK密钥
	 * @param strZpk
	 *            ZPK密钥
	 * @return "error"：失败 值：成功
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
	 * 将 ZEK/ZAK 从 ZMK转为 LMK加密 hsmFK
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param flag
	 *            ZAK,ZEK标志 0:ZEK 1:ZAK
	 * @param iZmkVar
	 *            ZMK密钥模式，取值0 或1 当iZmkLen长度为16时,ZMK密钥前面不加标志；
	 *            当为iZmkLen长度为32时：iZmkVar为1时，ZMK密钥前面加标志X；iZmkVar为０时，ZMK密钥前面加标志U；
	 *            当为iZmkLen长度为48时：iZmkVar为1时，ZMK密钥前面加标志Y；iZmkVar为０时，ZMK密钥前面加标志T；
	 * @param iZmkLen
	 *            ZMK密钥的长度 16、32、48
	 * @param strZmkBuf
	 *            ZMK密钥
	 * @param iZeakVar
	 *            ZeaK密钥模式，取值0 或1 说明同iZmkVar
	 * @param iZeakLen
	 *            ZeaK密钥的长度 16、32、48
	 * @param strZeakBuf
	 *            ZeaK密钥
	 * @return 空：失败 非空：成功
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
	 * 将 ZAK由 ZMK加密转为 LMK加密，zakFromZmkToLmk
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param strZmk
	 *            ZMK密钥
	 * @param strZak
	 *            ZaK密钥
	 * @return "error"：失败 值：成功
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
	 * 用 TAK 或 ZAK 对大消息生成 MAC（MAB）hsmMS
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param msgNum
	 *            消息块号 0：仅一块；1：第一块；2：中间块；3：最后块。
	 * @param keyType
	 *            密钥类型 0:TAK（终端认证密钥）1:ZAK（区域认证密钥）
	 * @param lenOfKey
	 *            KEY长度 16:单倍长 32:双倍长
	 * @param msgType
	 *            消息类型 0:消息数据为二进制 1:消息数据为扩展十六进制
	 * @param keyVar
	 *            密钥模式，取值0:ANSI X9.17 方式；1:变量方式
	 *            当iZmkLen长度为16时,ZMK密钥前面不加标志,这时iZmkVar值不起作用；
	 *            当为iZmkLen长度为32时：iZmkVar为1时，ZMK密钥前面加标志X；iZmkVar为０时，ZMK密钥前面加标志U；
	 *            当为iZmkLen长度为48时：iZmkVar为1时，ZMK密钥前面加标志Y；iZmkVar为０时，ZMK密钥前面加标志T；
	 * @param key
	 *            对应LMK对下加密的密钥 16或32长度
	 * @param iv
	 *            初始值，仅当消息块号msgNum为2 或3时有用
	 * @param lenOfMsg
	 *            消息长度,消息如果是16进制扩展，为一半的长度，为二进制则为实际长度
	 * @param strMsg
	 *            消息块
	 * @param strCMD
	 *            命令 MS、MU
	 * @return 空：失败 非空：成功
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
		strTmpSend += msgNum;// 消息块号 0：仅一块；1：第一块；2：中间块；3：最后块。
		strTmpSend += keyType;// 密钥类型 0:TAK（终端认证密钥）1:ZAK（区域认证密钥）
		String strTmpVar = "";
		if (lenOfKey.equals("16"))// 密钥长度 0：单倍长度DES密钥 1：双倍长度DES密钥
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
		strTmpSend += msgType;// 消息类型 0:消息数据为二进制 1:消息数据为扩展十六进制
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
	 * 将PIN块从ZPK下加密翻译到另一个ZPK下加密,hsmCC
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param msgNum
	 *            消息块号 0：仅一块；1：第一块；2：中间块；3：最后块。
	 * @param iSrcLen
	 *            源ZPK长度，16 32 48
	 * @param iSrcVar
	 *            密钥模式，取值0:ANSI X9.17 方式；1:变量方式
	 *            当iSrcLen长度为16时,srcZPK密钥前面不加标志,这时iSrcVar值不起作用；
	 *            当为iSrcLen长度为32时：iSrcVar为1时，srcZPK密钥前面加标志X；iSrcVar为０时，srcZPK密钥前面加标志U；
	 *            当为iSrcLen长度为48时：iSrcVar为1时，srcZPK密钥前面加标志Y；iSrcVar为０时，srcZPK密钥前面加标志T；
	 * @param srcZpk
	 *            源ZPK
	 * @param srcPbk
	 *            源ZPK下加密的源PIN块
	 * @param srcSrcFmt
	 *            源PIN块的格式代码
	 * @param iDstLen
	 *            目的ZPK长度，16 32 48
	 * @param iDstVar
	 *            同iSrcVar
	 * @param dstZpk
	 *            目的ZPK
	 * @param dstFmt
	 *            目的PIN块的格式代码
	 * @param panBuf
	 *            账号中去除校验位的最右12 位
	 * @return 空：失败 非空：成功
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
	 * 将 PIN块从 LMK下加密翻译到 ZPK下加密
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param desZpk
	 *            LMK下加密的ZPK值
	 * @param pinBlockCode
	 *            PINBLOCK的格式代码
	 * @param accNo
	 *            账号中去除校验位的最右12位
	 * @param pin
	 *            LMK对（02-03）下加密的PIN
	 * @return 空：失败 值：成功
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
	 * 将PIN块从 ZPK下加密翻译到 LMK下加密
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param srcZpk
	 *            LMK下加密的ZPK值
	 * @param pinBlockCode
	 *            PINBLOCK的格式代码
	 * @param accNo
	 *            账号中去除校验位的最右12位
	 * @param pinBlock
	 *            LMK对（02-03）下加密的PIN
	 * @return 空：失败 值：成功
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
	 * 加密一个文本 PIN的明文
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param pin
	 *            明文的PIN
	 * @param accNo
	 *            账号中去除校验位的最右12位
	 * @return 空：失败 值：成功
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
	 * 解密一个已加密的 PIN
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param pin
	 *            加密的PIN
	 * @param accNo
	 *            账号中去除校验位的最右12位
	 * @return 空：失败 值：成功
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
	 * 将PIN块从ZPK下加密翻译到另一个ZPK下加密或者从一种格式转换为另一种格式
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param srcZPK
	 *            源ZPK
	 * @param dstZPK
	 *            目的ZPK
	 * @param panBuf
	 *            源ZPK加密的PIN
	 * @return 空：失败 值：成功
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
	 * 生成MAC校验值generateMac
	 * 
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param key
	 *            密钥
	 * @param strMsg
	 *            要生成MAC的数据
	 * @param msgType
	 *            要生成MAC的数据.０：ASCII形式 ０：扩展1６进制形式
	 * @param strCMD
	 *            命令。MS或MU 加密机升级前用MS，MU为银联专用指令
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
	 * 通信函数，send
	 * 
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param data
	 *            发送的命令数据
	 * @param data
	 *            命令返回的数据
	 * @return -1:失败 >0:成功
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
	 * 得到MAC密钥
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param strZmk
	 *            ZMK密钥
	 * @param strZak
	 *            ZaK密钥
	 * @return "error"：失败 值：成功
	 */
	public static String applyMacKey(String ip, String port, String timeout,
			String strZMK, String strZAK) throws Exception{
		String strRsp = zakFromZmkToLmk(ip, port, timeout, strZMK, strZAK);
		return strRsp;
	}
	
	/**
	 * 得到PIN密钥
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param strZmk
	 *            ZMK密钥
	 * @param strZpk
	 *            ZpK密钥
	 * @return "error"：失败 值：成功
	 */
	public static String applyPinKey(String ip, String port, String timeout,
			String strZmk, String strZpk) throws Exception{
		String strRsp = zpkFromZmkToLmk(ip, port, timeout, strZmk, strZpk);
		return strRsp;
	}
	/**
	 * 对明文PIN进行加密，并转化为加密机本地加密
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param pin
	 *            PIN明文
	 * @param accno
	 *            账号中去除校验位的最右12位
	 * @param desZpk
	 *            ZpK密钥
	 * @return "error"：失败 值：成功
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
	 * 对PIN进行解密，并转化为加密机本地加密
	 * 
	 * @author qbchen
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param pinBlock
	 *            ZPK加密后的PINBLOCK
	 * @param accno
	 *            账号中去除校验位的最右12位
	 * @param srcZpk
	 *            ZpK密钥
	 * @return "error"：失败 值：成功
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
	 * Pin转换，先根据原密钥、算法解密，在根据目标密钥、算法加密
	 * 
	 * @author 董保刚 alter by qbchen
	 * @param oriPinValue
	 *            十六进制表示的原始密文pin
	 * @param accountNo
	 *            帐号
	 * @param oriMk
	 *            原明文主密钥
	 * @param oriPink
	 *            原密文pin密钥
	 * @param oriAcctFlag
	 *            原是否带主账号标志，1表示带主账号，0表示不带主帐号
	 * @param oriPinArith
	 *            原pin加密算法，1表示DES，2表示2DES，3表示3DES
	 * @param oriMArith
	 *            原pinKey加密算法，DES-DES算法
	 * @param destMkey
	 *            目标明文主密钥
	 * @param destPinKey
	 *            目标密文pin密钥
	 * @param destAcctFlag目标是否带主账号标志，1表示带主账号，0表示不带主帐号
	 * @param destPinArith
	 *            目标pin加密算法，1表示DES，2表示2DES，3表示3DES
	 * @param destMArith
	 *            目标pinKey加密算法，DES-DES算法
	 * @return 转换都得到的十六进制表示pin密文
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param oriEncFlag
	 *            原加密标志：0软加密，2硬加密
	 * @param destEncFlag
	 *            目标加密标志：0软加密，2硬加密
	 */
	public static String pinTransfer(String oriPinValue, String accountNo,
			String oriMk, String oriPink, String oriAcctFlag,
			String oriPinArith, String oriMArith, String destMkey,
			String destPinKey, String destAcctFlag, String destPinArith,
			String destMArith, String ip, String port, String timeout,
			String oriEncFlag, String destEncFlag) throws Exception {

		try {
			// 用oriMk对oriPink解密
			int oriPikLen = oriPink.length() / 2;
			String oriPinBlock = "";
			int oriMkLen = oriMk.length() / 2;
			int oriPinkLen = oriPink.length() / 2;

			byte[] oriPik = new byte[oriPikLen];
			if ("0".equals(oriEncFlag)) {// 软加密
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

				// 用解密后的oriPink对oriPinValue解密
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
			} else if ("2".equals(oriEncFlag)) {// 硬加密
// just for test oriPinBlock = HardwareCryp.getPin(ip, port, timeout, oriPink,
// oriPinValue, "0").substring(2);
				oriPinBlock = decodePin(ip, port, timeout, oriPink,oriPinValue, StringUtil.getPanByAcctString(accountNo));
				oriPinBlock = StringUtil.getPinBlock(oriPinBlock, accountNo);
			}
			// 得到密码明文Array
			byte[] oriPinArray = new byte[8];

			if ("1".equals(oriAcctFlag)) {
				byte[] panArray = StringUtil.getPanByAcct(accountNo);
				oriPinArray = StringUtil.getNextMac(StringUtil.hex2Byte(oriPinBlock),
						panArray);
			} else {
				oriPinArray = StringUtil.hex2Byte(oriPinBlock);
			}

			// 计算destPinBlock
			byte[] destBlockArray = new byte[8];
			if ("1".equals(destAcctFlag)) {
				byte[] panArray = StringUtil.getPanByAcct(accountNo);
				destBlockArray = StringUtil.getNextMac(oriPinArray, panArray);
			} else {
				destBlockArray = oriPinArray;
			}

			// 用destMkey对destPinKey解密
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

				// 用解密后的destPinKey对PinBlock加密
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
 * 银联PINKEY // strRet = //
 * applyPinKey(strIp,strPort,strTimeout,"48418879C5EAA68448418879C5EAA684","0CB7AAFC68E99F134F0D29C7A31D106F"); //
 * 银行PINKEY // String strRet1 = //
 * applyPinKey(strIp,strPort,strTimeout,"48418879C5EAA684","38863831A0F1FF51"); //
 * 银行加密的PIN到银联加密的PIN // String strRet2 = translatePIN(strIp,strPort,strTimeout,
 * strRet, // strRet1, "3A984F19EEB045F7", pan); // String strRet2 =
 * translatePIN(strIp,strPort,strTimeout, // "502BEE2B7D69D9B2",
 * "062700302F7A948D062700302F7A948D", // "0188754EE2C3C2F6", pan); String
 * strRet2 = translatePIN(strIp,strPort,strTimeout, // "2856FD3F765973BB",
 * "8E079C959826318C7E31B07867E40DDB", // "B9CD4FEE991937C7", pan);
 * 
 */	 
}



