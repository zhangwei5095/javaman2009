package com.nasoft.security;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Des {

	/**
	 * 生成密钥3DES密钥
	 * 
	 */
	public static byte[] gen3DESKey() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		KeyGenerator keygen = KeyGenerator.getInstance("DESede");
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * 生成密钥DES密钥
	 * 
	 */
	public static byte[] genDESKey() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		KeyGenerator keygen = KeyGenerator.getInstance("DES");
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * @param keyLen
	 *            秘要长度
	 * @return 16进制格式秘要
	 */
	public static String genKeyByLen(String keyLen) throws Exception {
		int intKeyLen = Integer.parseInt(keyLen);
		byte[] keyArray = new byte[0];
		if (intKeyLen == 8) {
			keyArray = genDESKey();
		} else if (intKeyLen == 16) {
			byte[] des3Key = gen3DESKey();
			keyArray = new byte[16];
			System.arraycopy(des3Key, 0, keyArray, 0, keyArray.length);
		} else if (intKeyLen == 24) {
			keyArray = gen3DESKey();
		}
		return StringUtil.byte2hex(keyArray);
	}

	/**
	 * 单DES加密
	 * 
	 * @param input
	 *            需要加密的串
	 * @param key
	 *            密钥
	 * @param Algorithm
	 *            DES或者DESede
	 * @return
	 */
	public static byte[] encodeDES(byte[] input, byte[] key) throws Exception {

		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, "DES");
		Cipher c1 = Cipher.getInstance("DES/CBC/NoPadding");
		byte[] iv = StringUtil.hex2Byte("0000000000000000");
		javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(
				iv);
		c1.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	/**
	 * 单DES解密
	 * 
	 * @param input
	 *            需要解密的串
	 * @param key
	 *            密钥
	 * @param Algorithm
	 *            DES或者DESede
	 * @return
	 */
	public static byte[] decodeDES(byte[] input, byte[] key) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, "DES");
		byte[] iv = StringUtil.hex2Byte("0000000000000000");
		javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(
				iv);
		Cipher c1 = Cipher.getInstance("DES/CBC/NoPadding");
		c1.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

	/**
	 * 双DES解密
	 * 
	 * @param source
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] encodeDES2(byte[] input, byte[] dekey)
			throws Exception {
		byte[] bFirKey = new byte[8];
		byte[] bSecKey = new byte[8];
		System.arraycopy(dekey, 0, bFirKey, 0, 8);
		System.arraycopy(dekey, 8, bSecKey, 0, 8);
		byte[] key = new byte[input.length];
		for (int i = 0; i < input.length / 8; i++) {
			byte[] temp = new byte[8];
			System.arraycopy(input, i * 8, temp, 0, 8);
			temp = encodeDES(temp, bFirKey);
			temp = decodeDES(temp, bSecKey);
			temp = encodeDES(temp, bFirKey);
			System.arraycopy(temp, 0, key, i * 8, 8);
		}
		return key;
	}

	/**
	 * DES2加密
	 * 
	 * @param source
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] decodeDES2(byte[] input, byte[] dekey)
			throws Exception {
		byte[] bFirKey = new byte[8];
		System.arraycopy(dekey, 0, bFirKey, 0, 8);
		byte[] bSecKey = new byte[8];
		System.arraycopy(dekey, 8, bSecKey, 0, 8);
		byte[] key = new byte[input.length];
		for (int i = 0; i < input.length / 8; i++) {
			byte[] temp = new byte[8];
			System.arraycopy(input, i * 8, temp, 0, 8);
			temp = decodeDES(temp, bFirKey);
			temp = encodeDES(temp, bSecKey);
			temp = decodeDES(temp, bFirKey);
			System.arraycopy(temp, 0, key, i * 8, 8);
		}
		return key;
	}

	/**
	 * 3DES加密
	 * 
	 * @param input
	 *            需要加密的串
	 * @param key
	 *            密钥
	 * @param Algorithm
	 *            DES或者DESede
	 * @return
	 */
	public static byte[] encodeDES3(byte[] input, byte[] key) throws Exception {

		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, "DESede");
		Cipher c1 = Cipher.getInstance("DESede/CBC/NoPadding");
		byte[] iv = StringUtil.hex2Byte("0000000000000000");
		javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(
				iv);
		c1.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	/**
	 * 三DES解密
	 * 
	 * @param input
	 *            需要解密的串
	 * @param key
	 *            密钥
	 * @param Algorithm
	 *            DES或者DESede
	 * @return
	 */
	public static byte[] decodeDES3(byte[] input, byte[] key) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, "DESede");
		byte[] iv = StringUtil.hex2Byte("0000000000000000");
		javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(
				iv);
		Cipher c1 = Cipher.getInstance("DESede/CBC/NoPadding");
		c1.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

}

