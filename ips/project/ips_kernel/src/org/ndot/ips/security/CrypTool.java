package org.ndot.ips.security;

import java.util.Arrays;

public class CrypTool {

	/**
	 * ʹ��MAC���ģ�����Կ���ܵģ�������MAC
	 * 
	 * @param macbuff
	 * @param mk  32λʮ�������ַ���  ����Կ
	 * @param mackey  16λʮ�������ַ���  MAC��Կ
	 * @param flag ASC ԭ������ASC���� EBC ԭ������EBC����
	 * @param keyArith ��Կ�����㷨��DES-DES�㷨
	 * @param macArith mac�����㷨��DES-DES�㷨
	 * @return ʮ�����Ƶ�MAC�ַ���
	 * 
	 */
	public static String getMac(String macbuff, String mk, String mackey,String flag,String keyArith,String macArith)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac��Կ�ĳ��ȣ�bytes����
		int mklen = mk.trim().length()/2;   //����Կ�ĳ��ȣ�bytes��
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			if("DES".equals(keyArith)){
				if (mklen==16 ) { //����Կ��˫��DES��Կ����128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}
				else if (mklen==24) { //����Կ��3DES��Կ����192bit
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
	 * ʹ��MAC���ģ�����Կ���ܵģ�������MAC
	 * 
	 * @param macbuff
	 * @param mk  32λʮ�������ַ���  ����Կ
	 * @param mackey  16λʮ�������ַ���  MAC��Կ
	 * @param flag ASC ԭ������ASC���� EBC ԭ������EBC����
	 * @param keyArith ��Կ�����㷨��DES-DES�㷨
	 * @param macArith mac�����㷨��DES-DES�㷨
	 * @return ʮ�����Ƶ�MAC�ַ���
	 * 
	 */
	public static String getMacReturn8Bytes(String macbuff, String mk, String mackey,String flag,String keyArith,String macArith)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac��Կ�ĳ��ȣ�bytes����
		int mklen = mk.trim().length()/2;   //����Կ�ĳ��ȣ�bytes��
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			if("DES".equals(keyArith)){
				if (mklen==16 ) { //����Կ��˫��DES��Կ����128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),StringUtil.hex2Byte(mk.trim()));
				}
				else if (mklen==24) { //����Կ��3DES��Կ����192bit
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
	 * ʹ��MAC���ģ�������Կ���ܵģ�������MAC
	 * 
	 * @param macbuff  ԭ�ַ���
	 * @param mk       ����Կ��32λ����48λʮ�������ַ���  
	 * @param mmKeyArith ������Կ�����㷨��
	 * @param mmk      ������Կ��32λ����48λʮ�������ַ���  
	 * @param macKeyArith mak��Կ�����㷨��DES-DES�㷨
	 * @param mackey   MAC KEY��Կ�����ģ���16λ����32λʮ�������ַ���  
	 * @param macArith mac�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
	 * @param flag     ASC ԭ������ASC����   EBC ԭ������EBC����
	 * 
	 * @return         MAC��8λʮ�����Ƶ��ַ���
	 * 
	 */
	public static String genMacReturn8Bytes(String macbuff, String mk,String mKeyArith, String mmk,String mmKeyArith, String mackey,String macArith,String flag)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac��Կ�ĳ��ȣ�bytes����
		int mklen = mk.trim().length()/2;   //����Կ�ĳ��ȣ�bytes��
		int mmklen = mmk.trim().length()/2;   //������Կ�ĳ��ȣ�bytes��
		byte[] mmkeybyte = new byte[mmklen];
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			//ʹ������Կ���ܴ�����Կ
			if("DES".equals(mKeyArith)){
				if (mklen==16 ) { //����Կ��˫��DES��Կ����128bit
					mmkeybyte = Des.decodeDES2(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==24 ){ //����Կ��3DES��Կ����192bit
					mmkeybyte = Des.decodeDES3(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==8){
					mmkeybyte = Des.decodeDES(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}
			}
			
			
			//ʹ�ô�����Կ����MACKEY
			if("DES".equals(mmKeyArith)){
				if (mmklen==16 ) { //������Կ��˫��DES��Կ����128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
				else if (mmklen==24 ) { //������Կ��3DES��Կ����192bit
					mackeybyte = Des.decodeDES3(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}else if (mmklen==8){
					mackeybyte = Des.decodeDES(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
			}
			//ʹ��MACKEY����MAC
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
	 * ʹ��MAC���ģ�������Կ���ܵģ�������MAC
	 * 
	 * @param macbuff  ԭ�ַ���
	 * @param mk       ����Կ��32λ����48λʮ�������ַ���  
	 * @param mmKeyArith ������Կ�����㷨��
	 * @param mmk      ������Կ��32λ����48λʮ�������ַ���  
	 * @param macKeyArith mak��Կ�����㷨��DES-DES�㷨
	 * @param mackey   MAC KEY��Կ�����ģ���16λ����32λʮ�������ַ���  
	 * @param macArith mac�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
	 * @param flag     ASC ԭ������ASC����   EBC ԭ������EBC����
	 * 
	 * @return         MAC��8λʮ�����Ƶ��ַ���
	 * 
	 */
	public static String genMac(String macbuff, String mk,String mKeyArith, String mmk,String mmKeyArith, String mackey,String macArith,String flag)
			throws Exception {
		int maklen = mackey.trim().length()/2;  //mac��Կ�ĳ��ȣ�bytes����
		int mklen = mk.trim().length()/2;   //����Կ�ĳ��ȣ�bytes��
		int mmklen = mmk.trim().length()/2;   //������Կ�ĳ��ȣ�bytes��
		byte[] mmkeybyte = new byte[mmklen];
		byte[] mackeybyte = new byte[maklen];
		String mac = "";
		try {
			//ʹ������Կ���ܴ�����Կ
			if("DES".equals(mKeyArith)){
				if (mklen==16 ) { //����Կ��˫��DES��Կ����128bit
					mmkeybyte = Des.decodeDES2(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==24 ){ //����Կ��3DES��Կ����192bit
					mmkeybyte = Des.decodeDES3(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}else if (mklen==8){
					mmkeybyte = Des.decodeDES(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
				}
			}
			
			
			//ʹ�ô�����Կ����MACKEY
			if("DES".equals(mmKeyArith)){
				if (mmklen==16 ) { //������Կ��˫��DES��Կ����128bit
					mackeybyte = Des.decodeDES2(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
				else if (mmklen==24 ) { //������Կ��3DES��Կ����192bit
					mackeybyte = Des.decodeDES3(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}else if (mmklen==8){
					mackeybyte = Des.decodeDES(StringUtil.hex2Byte(mackey.trim()),mmkeybyte);
				}
			}
			//ʹ��MACKEY����MAC
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
	 * ����MAC
	 * 
	 * @param macbuff ����Ҫ����MAC������ɵ��ַ���
	 * @param key ʮ�������ַ�����ʾ��MACKEY
	 * @return 16λʮ�������ַ�����ʾ��MAC
	 */
	private static String getMac(byte[] macbuff, byte[] key,String macArith) throws Exception {
		// �������
		int len = macbuff.length;
		if (len % 8 > 0) {
			len = len + (8 - len % 8);
		}
		byte[] mab = new byte[len];
		System.arraycopy(macbuff, 0, mab, 0, macbuff.length);
		Arrays.fill(mab, (macbuff.length), len, (byte) 0x00);

		int keyLen=key.length;
		// ����8�ֽڲ�֣����
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
	 * ����MAC
	 * 
	 * @param macbuff ����Ҫ����MAC������ɵ��ַ���
	 * @param key ʮ�������ַ�����ʾ��MACKEY
	 * @return 16λʮ�������ַ�����ʾ��MAC
	 */
	private static String getMacReturn8Bytes(byte[] macbuff, byte[] key,String macArith) throws Exception {
		// �������
		int len = macbuff.length;
		if (len % 8 > 0) {
			len = len + (8 - len % 8);
		}
		byte[] mab = new byte[len];
		System.arraycopy(macbuff, 0, mab, 0, macbuff.length);
		Arrays.fill(mab, (macbuff.length), len, (byte) 0x00);

		int keyLen=key.length;
		// ����8�ֽڲ�֣����
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
	 * @author ������
	 * TODO Pinת�����ȸ���ԭ��Կ���㷨���ܣ��ڸ���Ŀ����Կ���㷨����
	 * @param oriPinValue ʮ�����Ʊ�ʾ��ԭʼ����pin
	 * @param accountNo �ʺ�
	 * @param oriMk ԭ��������Կ
	 * @param oriPink ԭ����pin��Կ
	 * @param oriAcctFlag ԭ�Ƿ�����˺ű�־��1��ʾ�����˺ţ�0��ʾ�������ʺ�
	 * @param oriPinArith ԭpin�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
	 * @param oriMArith ԭpinKey�����㷨��DES-DES�㷨
	 * @param destMkey Ŀ����������Կ
	 * @param destPinKey Ŀ������pin��Կ
	 * @param destAcctFlagĿ���Ƿ�����˺ű�־��1��ʾ�����˺ţ�0��ʾ�������ʺ�
	 * @param destPinArith Ŀ��pin�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
	 * @param destMArith Ŀ��pinKey�����㷨��DES-DES�㷨
	 * @return ת�����õ���ʮ�����Ʊ�ʾpin����
	 * @param ip 
	 * @param port
	 * @param timeout
	 * @param oriEncFlag ԭ���ܱ�־��0����ܣ�1Ӳ����
	 * @param destEncFlag Ŀ����ܱ�־��0����ܣ�1Ӳ����
	 */
	public static String pinTransfer(String oriPinValue,String accountNo,
			String oriMk,String oriPink,String oriAcctFlag,String oriPinArith,
			String oriMArith,String destMkey,String destPinKey,String destAcctFlag,
			String destPinArith,String destMArith,String ip,String port,String timeout,
			String oriEncFlag,String destEncFlag)throws Exception{
		
		try{
//			��oriMk��oriPink����
			
			int oriPikLen=oriPink.length()/2;
			String oriPinBlock="";
			int oriMkLen=oriMk.length()/2;
			int oriPinkLen=oriPink.length()/2;
			
			
			byte[] oriPik=new byte[oriPikLen];
			if("0".equals(oriEncFlag)){//�����
				if("DES".equals(oriMArith)){
					if(oriMkLen==8){
						oriPik=Des.decodeDES(StringUtil.hex2Byte(oriPink.trim()),StringUtil.hex2Byte(oriMk.trim()));
					}else if(oriMkLen==16){
						oriPik=Des.decodeDES2(StringUtil.hex2Byte(oriPink.trim()),StringUtil.hex2Byte(oriMk.trim()));
					}else if(oriMkLen==24){
						oriPik=Des.decodeDES3(StringUtil.hex2Byte(oriPink.trim()),StringUtil.hex2Byte(oriMk.trim()));
					}
				}
				
				//�ý��ܺ��oriPink��oriPinValue����
				if("DES".equals(oriPinArith)){
					if(oriPinkLen==8){
						 oriPinBlock=StringUtil.byte2hex(Des.decodeDES(StringUtil.hex2Byte(oriPinValue), oriPik));
					 }else if(oriPinkLen==16){
						 oriPinBlock=StringUtil.byte2hex(Des.decodeDES2(StringUtil.hex2Byte(oriPinValue), oriPik));
					 }else if(oriPinkLen==24){
						 oriPinBlock=StringUtil.byte2hex(Des.decodeDES3(StringUtil.hex2Byte(oriPinValue), oriPik));
					 }
				}
			}else if("1".equals(oriEncFlag)){//Ӳ����
				oriPinBlock=HardwareCryp.getPin(ip,port,timeout,oriPink,oriPinValue,"0").substring(2);
			}
			
			//�õ���������Array
			 byte[] oriPinArray=new byte[8];
			 
			 if("1".equals(oriAcctFlag)){
				 byte[] panArray=getPanByAcct(accountNo);
				 oriPinArray=getNextMac(StringUtil.hex2Byte(oriPinBlock),panArray);
			 }else{
				 oriPinArray=StringUtil.hex2Byte(oriPinBlock);
			 }
			
			 //����destPinBlock
			 byte[] destBlockArray=new byte[8];
			if("1".equals(destAcctFlag)){
				byte[] panArray=getPanByAcct(accountNo);
				destBlockArray=getNextMac(oriPinArray,panArray);
			}else{
				destBlockArray=oriPinArray;
			}
			
			//��destMkey��destPinKey����
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
				
				//�ý��ܺ��destPinKey��PinBlock����
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
	 * ��ȡ��Կ�����ݴ�����Կ�����·�����Կ����������Կ����
	 * 
	 * @param mk
	 * @param mmk
	 * @param keybuff
	 * @return  ���ĵ�KEY
	 */
	public static String getKey(String mk,String mkArith, String mmk,String mmkArith, String keybuff)
			throws Exception {
		int mklen = mk.trim().length()/2;   //����Կ�ĳ��ȣ�bytes��
		int mmklen = mmk.trim().length()/2;   //������Կ�ĳ��ȣ�bytes��
		byte[] mmkbyte = new byte[mmklen];
		
		//ʹ������Կ�Դ�����Կ���н���
		if("DES".equals(mkArith)){
			if (mklen==16) { //����Կ��˫��DES��Կ����128bit
				mmkbyte = Des.decodeDES2(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
			}
			else if (mklen==24 ) { //����Կ��3DES��Կ����192bit
				mmkbyte = Des.decodeDES3(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
			}
			else if (mklen==8) { //����Կ��DES��Կ����
				mmkbyte = Des.decodeDES(StringUtil.hex2Byte(mmk.trim()),StringUtil.hex2Byte(mk.trim()));
			}
		}
		
		byte[] key = null;
		// ʹ�ô�����Կ����KEY
		if("DES".equals(mmkArith)){
			if (mmklen==16 ) { //˫��DES�㷨
				key = Des.decodeDES2(StringUtil.hex2Byte(keybuff), mmkbyte);
			}
			else if (mmklen==24) { //3DES��Կ�����㷨
				key = Des.decodeDES3(StringUtil.hex2Byte(keybuff), mmkbyte);
			}else if (mmklen==8) { //DES��Կ����192
				key = Des.decodeDES(StringUtil.hex2Byte(keybuff), mmkbyte);
			}
		}
		
		
		byte[] encodedKey=new byte[0];
		
		//ʹ������Կ��key���м���
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
	 * �þ�����Կ��������������Կ���ܣ�����
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
		int mklen = mk.trim().length() / 2; // ����Կ�ĳ��ȣ�bytes��
		int mmklen = mmk.trim().length() / 2; // ������Կ�ĳ��ȣ�bytes��
		byte[] mmkbyte = new byte[mmklen];

		mmkbyte=StringUtil.hex2Byte(mmk);

		byte[] key = null;
		// ʹ�ô�����Կ����KEY
		if ("DES".equals(mmkArith)) {
			if (mmklen == 16) { // ˫��DES�㷨
				key = Des.decodeDES2(StringUtil.hex2Byte(keybuff), mmkbyte);
			} else if (mmklen == 24) { // 3DES��Կ�����㷨
				key = Des.decodeDES3(StringUtil.hex2Byte(keybuff), mmkbyte);
			} else if (mmklen == 8) { // DES��Կ����192
				key = Des.decodeDES(StringUtil.hex2Byte(keybuff), mmkbyte);
			}
		}

		byte[] encodedKey = new byte[0];

		// ʹ������Կ��key���м���
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
	 * ������Կ��������
	 * @param mainKey
	 * @param value
	 * @throws Exception
	 */
	public static String encyptDataWithMainKey(String mainKey,String mkArith,String key)throws Exception{
		byte[] encodedKey = new byte[0];
		int mklen = mainKey.trim().length() / 2; // ����Կ�ĳ��ȣ�bytes��
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
	 * TODO ���ݴ�����Կ������У����
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
			if (mklen==16) { //����Կ��˫��DES��Կ����128bit
				mmkbyte = Des.decodeDES2(StringUtil.hex2Byte(value.trim()),StringUtil.hex2Byte(mk.trim()));
				
			}
			else if (mklen==24 ) { //����Կ��3DES��Կ����192bit
				mmkbyte = Des.decodeDES3(StringUtil.hex2Byte(value.trim()),StringUtil.hex2Byte(mk.trim()));
			}
			else if (mklen==8) { //����Կ��DES��Կ����
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
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		}
		try {
			String hexStr="30323030B238448188C180100000000002000001313930303030303030303030303031303030303731313039343134373239323639333039343134373037313134383134303132383130383030313633393330303833393330303036363039343134373239323639333737303030373730383035393230303438313437303030303436504139393632323937363030303031303030303033333920303030303030303132464430303030303030303030233135363031343030303030303030303330303030313037303030343831343037";
//			String pinvalue=CrypTool.genCheckValue("1234567890ABCDEF1234567890ABCDEF","DES","0000000000000000");
			System.out.println(CrypTool.getMacReturn8Bytes(hexStr, "1234567890ABCDEF", "B8261D45DE497055", "HEX", "DES", "DES"));
//			System.out.println(StringUtil.byte2hex(Des.encodeDES(StringUtil.hex2Byte("01D39B5B04794026"), StringUtil.hex2Byte("1234567890ABCDEF"))));
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}
	
	/**
	 * �����������������
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
	 * ���
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
	 * �����ʺŵõ�PAN
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
