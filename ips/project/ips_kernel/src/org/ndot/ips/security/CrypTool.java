package org.ndot.ips.security;

import java.util.Arrays;

public class CrypTool {

	/**
	 * 使用MAC密文（主密钥加密的），计算MAC
	 * 
	 * @param macbuff
	 * @param mk  32位十六进制字符串  主密钥
	 * @param mackey  16位十六进制字符串  MAC密钥
	 * @param flag ASC 原串采用ASC编码 EBC 原串采用EBC编码
	 * @param keyArith 密钥加密算法：DES-DES算法
	 * @param macArith mac加密算法：DES-DES算法
	 * @return 十六进制的MAC字符串
	 * 
	 */
	public static String getMac(String macbuff, String mk, String mackey,String flag,String keyArith,String macArith)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac密钥的长度（bytes）。
		int mklen = mk.trim().length()/2;   //主密钥的长度（bytes）
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			if("DES".equals(keyArith)){
				if (mklen==16 ) { //主密钥是双倍DES密钥长度128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}
				else if (mklen==24) { //主密钥是3DES密钥长度192bit
					mackeybyte = Des.decodeDES3(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if(mklen==8){
					mackeybyte = Des.decodeDES(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}
			}
			
			if("DES".equals(macArith)){
				if (flag.equalsIgnoreCase("ASC")) {
					mac = getMac(macbuff.getBytes(), mackeybyte,macArith);
				}
				if (flag.equalsIgnoreCase("EBC")) {
					mac = getMac(Asc2Ebc.ASCIIToEBCDIC(macbuff.getBytes()), mackeybyte,macArith);
				}
				if (flag.equalsIgnoreCase("HEX")) {
					mac = getMac(StringUtil.hex2Byte(macbuff), mackeybyte,macArith);
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return mac;
	}
	
	/**
	 * 使用MAC密文（主密钥加密的），计算MAC
	 * 
	 * @param macbuff
	 * @param mk  32位十六进制字符串  主密钥
	 * @param mackey  16位十六进制字符串  MAC密钥
	 * @param flag ASC 原串采用ASC编码 EBC 原串采用EBC编码
	 * @param keyArith 密钥加密算法：DES-DES算法
	 * @param macArith mac加密算法：DES-DES算法
	 * @return 十六进制的MAC字符串
	 * 
	 */
	public static String getMacReturn8Bytes(String macbuff, String mk, String mackey,String flag,String keyArith,String macArith)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac密钥的长度（bytes）。
		int mklen = mk.trim().length()/2;   //主密钥的长度（bytes）
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			if("DES".equals(keyArith)){
				if (mklen==16 ) { //主密钥是双倍DES密钥长度128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}
				else if (mklen==24) { //主密钥是3DES密钥长度192bit
					mackeybyte = Des.decodeDES3(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if(mklen==8){
					mackeybyte = Des.decodeDES(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}
			}
			
			if("DES".equals(macArith)){
				if (flag.equalsIgnoreCase("ASC")) {
					mac = getMacReturn8Bytes(macbuff.getBytes(), mackeybyte,macArith);
				}
				if (flag.equalsIgnoreCase("EBC")) {
					mac = getMacReturn8Bytes(Asc2Ebc.ASCIIToEBCDIC(macbuff.getBytes()), mackeybyte,macArith);
				}
				if (flag.equalsIgnoreCase("HEX")) {
					mac = getMacReturn8Bytes(StringUtil.hex2Byte(macbuff), mackeybyte,macArith);
				}
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return mac;
	}
	
	/**
	 * 使用MAC密文（次主密钥加密的），计算MAC
	 * 
	 * @param macbuff  原字符串
	 * @param mk       主密钥：32位或者48位十六进制字符串  
	 * @param mmKeyArith 次主密钥加密算法，
	 * @param mmk      次主密钥：32位或者48位十六进制字符串  
	 * @param macKeyArith mak密钥加密算法，DES-DES算法
	 * @param mackey   MAC KEY密钥（密文）：16位或者32位十六进制字符串  
	 * @param macArith mac加密算法，1表示DES，2表示2DES，3表示3DES
	 * @param flag     ASC 原串采用ASC编码   EBC 原串采用EBC编码
	 * 
	 * @return         MAC：8位十六进制的字符串
	 * 
	 */
	public static String genMacReturn8Bytes(String macbuff, String mk,String mKeyArith, String mmk,String mmKeyArith, String mackey,String macArith,String flag)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac密钥的长度（bytes）。
		int mklen = mk.trim().length()/2;   //主密钥的长度（bytes）
		int mmklen = mmk.trim().length()/2;   //次主密钥的长度（bytes）
		byte[] mmkeybyte = new byte[mmklen];
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			//使用主密钥解密次主密钥
			if("DES".equals(mKeyArith)){
				if (mklen==16 ) { //主密钥是双倍DES密钥长度128bit
					mmkeybyte = Des.decodeDES2(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==24 ){ //主密钥是3DES密钥长度192bit
					mmkeybyte = Des.decodeDES3(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==8){
					mmkeybyte = Des.decodeDES(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}
			}
			
			
			//使用次主密钥解密MACKEY
			if("DES".equals(mmKeyArith)){
				if (mmklen==16 ) { //次主密钥是双倍DES密钥长度128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
				else if (mmklen==24 ) { //次主密钥是3DES密钥长度192bit
					mackeybyte = Des.decodeDES3(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}else if (mmklen==8){
					mackeybyte = Des.decodeDES(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
			}
			//使用MACKEY计算MAC
			if (flag.equalsIgnoreCase("ASC")) {
				mac = getMacReturn8Bytes(macbuff.getBytes(), mackeybyte,macArith);
			}
			if (flag.equalsIgnoreCase("EBC")) {
				mac = getMacReturn8Bytes(Asc2Ebc.ASCIIToEBCDIC(macbuff.getBytes()), mackeybyte,macArith);
			}
			if (flag.equalsIgnoreCase("HEX")) {
				mac = getMacReturn8Bytes(StringUtil.hex2Byte(macbuff), mackeybyte,macArith);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return mac;
	}
	
	/**
	 * 使用MAC密文（次主密钥加密的），计算MAC
	 * 
	 * @param macbuff  原字符串
	 * @param mk       主密钥：32位或者48位十六进制字符串  
	 * @param mmKeyArith 次主密钥加密算法，
	 * @param mmk      次主密钥：32位或者48位十六进制字符串  
	 * @param macKeyArith mak密钥加密算法，DES-DES算法
	 * @param mackey   MAC KEY密钥（密文）：16位或者32位十六进制字符串  
	 * @param macArith mac加密算法，1表示DES，2表示2DES，3表示3DES
	 * @param flag     ASC 原串采用ASC编码   EBC 原串采用EBC编码
	 * 
	 * @return         MAC：8位十六进制的字符串
	 * 
	 */
	public static String genMac(String macbuff, String mk,String mKeyArith, String mmk,String mmKeyArith, String mackey,String macArith,String flag)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac密钥的长度（bytes）。
		int mklen = mk.trim().length()/2;   //主密钥的长度（bytes）
		int mmklen = mmk.trim().length()/2;   //次主密钥的长度（bytes）
		byte[] mmkeybyte = new byte[mmklen];
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			//使用主密钥解密次主密钥
			if("DES".equals(mKeyArith)){
				if (mklen==16 ) { //主密钥是双倍DES密钥长度128bit
					mmkeybyte = Des.decodeDES2(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==24 ){ //主密钥是3DES密钥长度192bit
					mmkeybyte = Des.decodeDES3(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==8){
					mmkeybyte = Des.decodeDES(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}
			}
			
			
			//使用次主密钥解密MACKEY
			if("DES".equals(mmKeyArith)){
				if (mmklen==16 ) { //次主密钥是双倍DES密钥长度128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
				else if (mmklen==24 ) { //次主密钥是3DES密钥长度192bit
					mackeybyte = Des.decodeDES3(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}else if (mmklen==8){
					mackeybyte = Des.decodeDES(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
			}
			//使用MACKEY计算MAC
			if (flag.equalsIgnoreCase("ASC")) {
				mac = getMac(macbuff.getBytes(), mackeybyte,macArith);
			}
			if (flag.equalsIgnoreCase("EBC")) {
				mac = getMac(Asc2Ebc.ASCIIToEBCDIC(macbuff.getBytes()), mackeybyte,macArith);
			}
			if (flag.equalsIgnoreCase("HEX")) {
				mac = getMac(StringUtil.hex2Byte(macbuff), mackeybyte,macArith);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return mac;
	}

	/**
	 * 计算MAC
	 * 
	 * @param macbuff 由需要计算MAC的域组成的字符串
	 * @param key 十六进制字符串表示的MACKEY
	 * @return 16位十六进制字符串表示的MAC
	 */
	private static String getMac(byte[] macbuff, byte[] key,String macArith) throws Exception {
		// 填充数据
		int len = macbuff.length;
		if (len % 8 > 0) {
			len = len + (8 - len % 8);
		}
		byte[] mab = new byte[len];
		System.arraycopy(macbuff, 0, mab, 0, macbuff.length);
		Arrays.fill(mab, (macbuff.length), len, (byte) 0x00);

		int keyLen=key.length;
		// 按照8字节拆分，异或
		byte[] m1 = new byte[8];
		byte[] m2 = new byte[8];
		System.arraycopy(mab, 0, m1, 0, 8);
		if ("DES".equals(macArith)) {	
			if (keyLen==8) {		
	        	m1 = Des.encodeDES(m1, key);
	        } 
	        else if (keyLen==16){
	    		m1 = Des.encodeDES2(m1, key);
	        }else if(keyLen==24){
	        	m1 = Des.encodeDES3(m1, key);
	        }
		}
        
		for (int i = 1; i < mab.length / 8; i++) {
			System.arraycopy(mab, i * 8, m2, 0, 8);
			m1 = getNextMac(m1, m2);
			if ("DES".equals(macArith)) {	
				if (keyLen==8) {		
		        	m1 = Des.encodeDES(m1, key);
		        } 
		        else if (keyLen==16){
		    		m1 = Des.encodeDES2(m1, key);
		        }else if(keyLen==24){
		        	m1 = Des.encodeDES3(m1, key);
		        }
			}
	        
		}
		try {
			byte[] mactemp = new byte[4];
			System.arraycopy(m1, 0, mactemp, 0, 4);
			return StringUtil.byte2hex(mactemp);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * 计算MAC
	 * 
	 * @param macbuff 由需要计算MAC的域组成的字符串
	 * @param key 十六进制字符串表示的MACKEY
	 * @return 16位十六进制字符串表示的MAC
	 */
	private static String getMacReturn8Bytes(byte[] macbuff, byte[] key,String macArith) throws Exception {
		// 填充数据
		int len = macbuff.length;
		if (len % 8 > 0) {
			len = len + (8 - len % 8);
		}
		byte[] mab = new byte[len];
		System.arraycopy(macbuff, 0, mab, 0, macbuff.length);
		Arrays.fill(mab, (macbuff.length), len, (byte) 0x00);

		int keyLen=key.length;
		// 按照8字节拆分，异或
		byte[] m1 = new byte[8];
		byte[] m2 = new byte[8];
		System.arraycopy(mab, 0, m1, 0, 8);
		if ("DES".equals(macArith)) {	
			if (keyLen==8) {		
	        	m1 = Des.encodeDES(m1, key);
	        } 
	        else if (keyLen==16){
	    		m1 = Des.encodeDES2(m1, key);
	        }else if(keyLen==24){
	        	m1 = Des.encodeDES3(m1, key);
	        }
		}
        
		for (int i = 1; i < mab.length / 8; i++) {
			System.arraycopy(mab, i * 8, m2, 0, 8);
			m1 = getNextMac(m1, m2);
			if ("DES".equals(macArith)) {	
				if (keyLen==8) {		
		        	m1 = Des.encodeDES(m1, key);
		        } 
		        else if (keyLen==16){
		    		m1 = Des.encodeDES2(m1, key);
		        }else if(keyLen==24){
		        	m1 = Des.encodeDES3(m1, key);
		        }
			}
	        
		}
		try {
			byte[] mactemp = new byte[8];
			System.arraycopy(m1, 0, mactemp, 0, 8);
			return StringUtil.byte2hex(mactemp);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * @author 董保刚
	 * TODO Pin转换，先根据原密钥、算法解密，在根据目标密钥、算法加密
	 * @param oriPinValue 十六进制表示的原始密文pin
	 * @param accountNo 帐号
	 * @param oriMk 原明文主密钥
	 * @param oriPink 原密文pin密钥
	 * @param oriAcctFlag 原是否带主账号标志，1表示带主账号，0表示不带主帐号
	 * @param oriPinArith 原pin加密算法，1表示DES，2表示2DES，3表示3DES
	 * @param oriMArith 原pinKey加密算法，DES-DES算法
	 * @param destMkey 目标明文主密钥
	 * @param destPinKey 目标密文pin密钥
	 * @param destAcctFlag目标是否带主账号标志，1表示带主账号，0表示不带主帐号
	 * @param destPinArith 目标pin加密算法，1表示DES，2表示2DES，3表示3DES
	 * @param destMArith 目标pinKey加密算法，DES-DES算法
	 * @return 转换都得到的十六进制表示pin密文
	 * @param ip 
	 * @param port
	 * @param timeout
	 * @param oriEncFlag 原加密标志：0软加密，1硬加密
	 * @param destEncFlag 目标加密标志：0软加密，1硬加密
	 */
	public static String pinTransfer(String oriPinValue,String accountNo,
			String oriMk,String oriPink,String oriAcctFlag,String oriPinArith,
			String oriMArith,String destMkey,String destPinKey,String destAcctFlag,
			String destPinArith,String destMArith,String ip,String port,String timeout,
			String oriEncFlag,String destEncFlag)throws Exception{
		
		try{
//			用oriMk对oriPink解密
			
			int oriPikLen=oriPink.length()/2;
			String oriPinBlock="";
			int oriMkLen=oriMk.length()/2;
			int oriPinkLen=oriPink.length()/2;
			
			
			byte[] oriPik=new byte[oriPikLen];
			if("0".equals(oriEncFlag)){//软加密
				if("DES".equals(oriMArith)){
					if(oriMkLen==8){
						oriPik=Des.decodeDES(StringUtil.hex2Byte(oriPink.trim()),StringUtil.hex2Byte(oriMk.trim()));
					}else if(oriMkLen==16){
						oriPik=Des.decodeDES2(StringUtil.hex2Byte(oriPink.trim()),StringUtil.hex2Byte(oriMk.trim()));
					}else if(oriMkLen==24){
						oriPik=Des.decodeDES3(StringUtil.hex2Byte(oriPink.trim()),StringUtil.hex2Byte(oriMk.trim()));
					}
				}
				
				//用解密后的oriPink对oriPinValue解密
				if("DES".equals(oriPinArith)){
					if(oriPinkLen==8){
						 oriPinBlock=StringUtil.byte2hex(Des.decodeDES(StringUtil.hex2Byte(oriPinValue), oriPik));
					 }else if(oriPinkLen==16){
						 oriPinBlock=StringUtil.byte2hex(Des.decodeDES2(StringUtil.hex2Byte(oriPinValue), oriPik));
					 }else if(oriPinkLen==24){
						 oriPinBlock=StringUtil.byte2hex(Des.decodeDES3(StringUtil.hex2Byte(oriPinValue), oriPik));
					 }
				}
			}else if("1".equals(oriEncFlag)){//硬加密
				oriPinBlock=HardwareCryp.getPin(ip,port,timeout,oriPink,oriPinValue,"0").substring(2);
			}
			
			//得到密码明文Array
			 byte[] oriPinArray=new byte[8];
			 
			 if("1".equals(oriAcctFlag)){
				 byte[] panArray=getPanByAcct(accountNo);
				 oriPinArray=getNextMac(StringUtil.hex2Byte(oriPinBlock),panArray);
			 }else{
				 oriPinArray=StringUtil.hex2Byte(oriPinBlock);
			 }
			
			 //计算destPinBlock
			 byte[] destBlockArray=new byte[8];
			if("1".equals(destAcctFlag)){
				byte[] panArray=getPanByAcct(accountNo);
				destBlockArray=getNextMac(oriPinArray,panArray);
			}else{
				destBlockArray=oriPinArray;
			}
			
			//用destMkey对destPinKey解密
			int destPikLen=destPinKey.length()/2;
			int destMkeyLen=destMkey.length()/2;
			int destPinKeyLen=destPinKey.length()/2;
			byte[] destPik=new byte[destPikLen];
			String transferedPin="";
			if("0".equals(destEncFlag)){
				if("DES".equals(destMArith)){
					if(destMkeyLen==8){
						destPik=Des.decodeDES(StringUtil.hex2Byte(destPinKey),StringUtil.hex2Byte(destMkey.trim()));
					}else if(destMkeyLen==16){
						destPik=Des.decodeDES2(StringUtil.hex2Byte(destPinKey),StringUtil.hex2Byte(destMkey.trim()));
					}else if(destMkeyLen==24){
						destPik=Des.decodeDES3(StringUtil.hex2Byte(destPinKey),StringUtil.hex2Byte(destMkey.trim()));
					}
				}
				
				//用解密后的destPinKey对PinBlock加密
				if("DES".equals(destMArith)){
					if(destPinKeyLen==8){
						transferedPin=StringUtil.byte2hex(Des.encodeDES(destBlockArray,destPik));
					}else if (destPinKeyLen==16){
						transferedPin=StringUtil.byte2hex(Des.encodeDES2(destBlockArray,destPik));
					}
					else if (destPinKeyLen==24){
						transferedPin=StringUtil.byte2hex(Des.encodeDES3(destBlockArray,destPik));
					}
				}
			}else if("1".equals(destEncFlag)){
				transferedPin=HardwareCryp.getPin(ip, port, timeout, destPinKey, StringUtil.byte2hex(destBlockArray), "1").substring(2);
			}
			
			return transferedPin;
		}catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 * 获取密钥，根据次主密钥解密下发的密钥，并用主密钥加密
	 * 
	 * @param mk
	 * @param mmk
	 * @param keybuff
	 * @return  明文的KEY
	 */
	public static String getKey(String mk,String mkArith, String mmk,String mmkArith, String keybuff)
			throws Exception {
		int mklen = mk.trim().length()/2;   //主密钥的长度（bytes）
		int mmklen = mmk.trim().length()/2;   //次主密钥的长度（bytes）
		byte[] mmkbyte = new byte[mmklen];
		
		//使用主密钥对次主密钥进行解密
		if("DES".equals(mkArith)){
			if (mklen==16) { //主密钥是双倍DES密钥长度128bit
				mmkbyte = Des.decodeDES2(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
			}
			else if (mklen==24 ) { //主密钥是3DES密钥长度192bit
				mmkbyte = Des.decodeDES3(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
			}
			else if (mklen==8) { //主密钥是DES密钥长度
				mmkbyte = Des.decodeDES(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
			}
		}
		
		byte[] key = null;
		// 使用次主密钥解密KEY
		if("DES".equals(mmkArith)){
			if (mmklen==16 ) { //双倍DES算法
				key = Des.decodeDES2(StringUtil.hex2Byte(keybuff), mmkbyte);
			}
			else if (mmklen==24) { //3DES密钥长度算法
				key = Des.decodeDES3(StringUtil.hex2Byte(keybuff), mmkbyte);
			}else if (mmklen==8) { //DES密钥长度192
				key = Des.decodeDES(StringUtil.hex2Byte(keybuff), mmkbyte);
			}
		}
		
		
		byte[] encodedKey=new byte[0];
		
		//使用主密钥对key进行加密
		if("DES".equals(mkArith)){
			if (mklen==8 ) {
				encodedKey=Des.encodeDES(key, StringUtil.hex2Byte(mk.trim()));
			}else if (mklen==16 ) {
				encodedKey=Des.encodeDES2(key, StringUtil.hex2Byte(mk.trim()));
			}else if (mklen==24 ) {
				encodedKey=Des.encodeDES3(key, StringUtil.hex2Byte(mk.trim()));
			}
		}
		return StringUtil.byte2hex(encodedKey);
	}
	
	/**
	 * 用旧主密钥解密再用新主密钥加密，返回
	 * @param mk
	 * @param mkArith
	 * @param mmk
	 * @param mmkArith
	 * @param keybuff
	 * @return
	 * @throws Exception
	 */
	public static String getKeyByMainKey(String mk,String mkArith, String mmk,String mmkArith, String keybuff)
	throws Exception {
		int mklen = mk.trim().length() / 2; // 主密钥的长度（bytes）
		int mmklen = mmk.trim().length() / 2; // 次主密钥的长度（bytes）
		byte[] mmkbyte = new byte[mmklen];

		mmkbyte=StringUtil.hex2Byte(mmk);

		byte[] key = null;
		// 使用次主密钥解密KEY
		if ("DES".equals(mmkArith)) {
			if (mmklen == 16) { // 双倍DES算法
				key = Des.decodeDES2(StringUtil.hex2Byte(keybuff), mmkbyte);
			} else if (mmklen == 24) { // 3DES密钥长度算法
				key = Des.decodeDES3(StringUtil.hex2Byte(keybuff), mmkbyte);
			} else if (mmklen == 8) { // DES密钥长度192
				key = Des.decodeDES(StringUtil.hex2Byte(keybuff), mmkbyte);
			}
		}

		byte[] encodedKey = new byte[0];

		// 使用主密钥对key进行加密
		if ("DES".equals(mkArith)) {
			if (mklen == 8) {
				encodedKey = Des.encodeDES(key, StringUtil.hex2Byte(mk.trim()));
			} else if (mklen == 16) {
				encodedKey = Des
						.encodeDES2(key, StringUtil.hex2Byte(mk.trim()));
			} else if (mklen == 24) {
				encodedKey = Des
						.encodeDES3(key, StringUtil.hex2Byte(mk.trim()));
			}
		}
		return StringUtil.byte2hex(encodedKey);
	}
	
	/**
	 * 用主密钥加密数据
	 * @param mainKey
	 * @param value
	 * @throws Exception
	 */
	public static String encyptDataWithMainKey(String mainKey,String mkArith,String key)throws Exception{
		byte[] encodedKey = new byte[0];
		int mklen = mainKey.trim().length() / 2; // 主密钥的长度（bytes）
		if ("DES".equals(mkArith)) {
			if (mklen == 8) {
				encodedKey = Des.encodeDES(StringUtil.hex2Byte(key), StringUtil.hex2Byte(mainKey.trim()));
			} else if (mklen == 16) {
				encodedKey = Des
						.encodeDES2(StringUtil.hex2Byte(key), StringUtil.hex2Byte(mainKey.trim()));
			} else if (mklen == 24) {
				encodedKey = Des
						.encodeDES3(StringUtil.hex2Byte(key), StringUtil.hex2Byte(mainKey.trim()));
			}
		}
		
		return StringUtil.byte2hex(encodedKey);
	}
	
	
	/**
	 * TODO 根据次主密钥，生成校验码
	 * @param mk
	 * @param mkArith
	 * @param value
	 * @return
	 */
	public static String genCheckValue(String mk,String mkArith,String value)throws Exception {
		int len=value.length()/2;
		int mklen = mk.trim().length()/2; 
		byte[] mmkbyte = new byte[len];
		if("DES".equals(mkArith)){
			if (mklen==16) { //主密钥是双倍DES密钥长度128bit
				mmkbyte = Des.decodeDES2(StringUtil.hex2Byte(value.trim()),StringUtil.hex2Byte(mk.trim()));
				
			}
			else if (mklen==24 ) { //主密钥是3DES密钥长度192bit
				mmkbyte = Des.decodeDES3(StringUtil.hex2Byte(value.trim()),StringUtil.hex2Byte(mk.trim()));
			}
			else if (mklen==8) { //主密钥是DES密钥长度
				mmkbyte = Des.decodeDES(StringUtil.hex2Byte(value.trim()),StringUtil.hex2Byte(mk.trim()));
				
			}
		}
		
		byte []preValue=new byte[4];
		byte []nextVaule=new byte[4];
		System.arraycopy(mmkbyte, 0, preValue, 0, 4);
		System.arraycopy(mmkbyte, 4, nextVaule, 0, 4);
		String checked=StringUtil.byte2hex(getXor(preValue,nextVaule));
		return checked;
	}
	
	public static void main(String[] args){
//		try{
//			String oriPinValue="F5CC65BC8FC1E93A";
//			String accountNo="6221111111120011";
//			String oriMk="1234567890ABCDEF1234567890ABCDEF";
//			String oriPink="B8E929DB99FE88C0";
//			
//			String pinvalue=CrypTool.getMacReturn8Bytes(accountNo, oriMk, oriPink, "ASC", "DES", "DES");
////			String en=HardwareCryp.getPin("10.0.7.100", "6666", "5", "77492DFBBF28111A4D5EDC5594ED230D", pinvalue, "0");
//			System.out.println("pinvalue="+pinvalue);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		try {
//			String pinvalue=CrypTool.pinTransfer("091FEE56C3A33A32","6210210031700000508","1234567890ABCDEF1234567890ABCDEF","3958546B9447B00F","2","DES","DES","1234567890ABCDEF1234567890ABCDEF","","","DES","DES","10.5.1.140","10000","5","0","0");
//		} catch (Exception e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
		try {
			String hexStr="30323030B238448188C180100000000002000001313930303030303030303030303031303030303731313039343134373239323639333039343134373037313134383134303132383130383030313633393330303833393330303036363039343134373239323639333737303030373730383035393230303438313437303030303436504139393632323937363030303031303030303033333920303030303030303132464430303030303030303030233135363031343030303030303030303330303030313037303030343831343037";
//			String pinvalue=CrypTool.genCheckValue("1234567890ABCDEF1234567890ABCDEF","DES","0000000000000000");
			System.out.println(CrypTool.getMacReturn8Bytes(hexStr, "1234567890ABCDEF", "B8261D45DE497055", "HEX", "DES", "DES"));
//			System.out.println(StringUtil.byte2hex(Des.encodeDES(StringUtil.hex2Byte("01D39B5B04794026"), StringUtil.hex2Byte("1234567890ABCDEF"))));
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 对两数组进行异或计算
	 * 
	 * @param mac
	 * @param tmp
	 * @return
	 */
	public static byte[] getNextMac(byte[] mac, byte[] tmp) {
		byte[] data = new byte[8];
		for (int i = 0; i < 8; i++) {
			data[i] = (byte) (mac[i] ^ tmp[i]);
		}
		return data;
	}
	
	/**
	 * 异或
	 * @param mac
	 * @param tmp
	 * @return
	 */
	public static byte[] getXor(byte[] mac, byte[] tmp) {
		int len=mac.length;
		byte[] data = new byte[len];
		for (int i = 0; i < len; i++) {
			data[i] = (byte) (mac[i] ^ tmp[i]);
		}
		return data;
	}
	
	/**
	 * 根据帐号得到PAN
	 * @param acct
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPanByAcct(String acct)throws Exception{
		byte[]panArray=new byte[8];
		try{
			int acctLen=acct.length();
			String acctMain=acct.substring(acctLen-13>0?acctLen-13:0,acctLen-1);
			long intMain=Long.parseLong(acctMain);
			int i = 7;
			for (; i >= 0; i--) {
				long twoDigits = intMain % 100;
				intMain /= 100;
				panArray[i ] = (byte) (((twoDigits / 10) << 4) + twoDigits % 10);
				
			}
			
		}catch(Exception e){
			throw e;
		}
		return panArray;
	}
	
		
	
}
