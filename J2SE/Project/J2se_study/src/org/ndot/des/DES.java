package org.ndot.des;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

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
 * �ļ����� DES.java
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
 * ����ʱ��: 2009-10-23
 * 
 */
public class DES {
	/**  
	 
     *  
 
     * @return DES�㷨��Կ  
 
     */  
  
    public static byte[] generateKey() {   
  
        try {   
  
  
  
            // DES�㷨Ҫ����һ�������ε������Դ   
  
            SecureRandom sr = new SecureRandom();   
  
  
  
            // ����һ��DES�㷨��KeyGenerator����   
  
            KeyGenerator kg = KeyGenerator.getInstance("DES");   
  
            kg.init(sr);   
  
  
  
            // ������Կ   
  
            SecretKey secretKey = kg.generateKey();   
  
  
  
            // ��ȡ��Կ����   
  
            byte[] key = secretKey.getEncoded();   
  
  
  
            return key;   
  
        } catch (NoSuchAlgorithmException e) {   
  
            System.err.println("DES�㷨��������Կ����!");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * ���ܺ���  
 
     *  
 
     * @param data  
 
     *            ��������  
 
     * @param key  
 
     *            ��Կ  
 
     * @return ���ؼ��ܺ������  
 
     */  
  
    public static byte[] encrypt(byte[] data, byte[] key) {   
  
  
  
        try {   
  
  
  
            // DES�㷨Ҫ����һ�������ε������Դ   
  
            SecureRandom sr = new SecureRandom();   
  
  
  
            // ��ԭʼ��Կ���ݴ���DESKeySpec����   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����   
  
            // һ��SecretKey����   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // using DES in ECB mode   
  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");   
  
  
  
            // ���ܳ׳�ʼ��Cipher����   
  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);   
  
  
  
            // ִ�м��ܲ���   
  
            byte encryptedData[] = cipher.doFinal(data);   
  
  
  
            return encryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES�㷨���������ݳ���!");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * ���ܺ���  
 
     *  
 
     * @param data  
 
     *            ��������  
 
     * @param key  
 
     *            ��Կ  
 
     * @return ���ؽ��ܺ������  
 
     */  
  
    public static byte[] decrypt(byte[] data, byte[] key) {   
  
        try {   
  
            // DES�㷨Ҫ����һ�������ε������Դ   
  
            SecureRandom sr = new SecureRandom();   
  
  
  
            // byte rawKeyData[] = /* ��ĳ�ַ�����ȡԭʼ�ܳ����� */;   
  
  
  
            // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����   
  
            // һ��SecretKey����   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // using DES in ECB mode   
  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");   
  
  
  
            // ���ܳ׳�ʼ��Cipher����   
  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);   
  
  
  
            // ��ʽִ�н��ܲ���   
  
            byte decryptedData[] = cipher.doFinal(data);   
  
  
  
            return decryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES�㷨�����ܳ���");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * ���ܺ���  
 
     *  
 
     * @param data  
 
     *            ��������  
 
     * @param key  
 
     *            ��Կ  
 
     * @return ���ؼ��ܺ������  
 
     */  
  
    public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {   
  
  
  
        try {   
  
            // ��ԭʼ��Կ���ݴ���DESKeySpec����   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����   
  
            // һ��SecretKey����   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // Cipher����ʵ����ɼ��ܲ���   
  
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");   
  
            // ������NoPaddingģʽ��data���ȱ�����8�ı���   
  
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");   
  
  
  
            // ���ܳ׳�ʼ��Cipher����   
  
            IvParameterSpec param = new IvParameterSpec(iv);   
  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);   
  
  
  
            // ִ�м��ܲ���   
  
            byte encryptedData[] = cipher.doFinal(data);   
  
  
  
            return encryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES�㷨���������ݳ���!");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * ���ܺ���  
 
     *  
 
     * @param data  
 
     *            ��������  
 
     * @param key  
 
     *            ��Կ  
 
     * @return ���ؽ��ܺ������  
 
     */  
  
    public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {   
  
        try {   
  
            // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����   
  
            // һ��SecretKey����   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // using DES in CBC mode   
  
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");   
  
            // ������NoPaddingģʽ��data���ȱ�����8�ı���   
  
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");   
  
  
  
            // ���ܳ׳�ʼ��Cipher����   
  
            IvParameterSpec param = new IvParameterSpec(iv);   
  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);   
  
  
  
            // ��ʽִ�н��ܲ���   
  
            byte decryptedData[] = cipher.doFinal(data);   
  
  
  
            return decryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES�㷨�����ܳ���");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    public static void main(String[] args) {   
  
        try {   
  
            byte[] key = ISOUtil.hex2byte("1234567890ABCDEF");   
  
            byte[] iv  = "06123456".getBytes();   
  
            byte[] data = DES.encrypt(iv, key);  
            String hexStr = ISOUtil.byte2HexNoSpaceStr(data, data.length);
            System.out.println(hexStr);
  
            System.out.print("EBC mode:");   
  
            System.out.println(new String(DES.decrypt(data, key)));   
  
            System.out.print("CBC mode:");   
  
            data = DES.CBCEncrypt(iv, key, iv);   
  
            System.out.println(new String(DES.CBCDecrypt(data, key, iv)));   
  
               
  
        } catch (Exception e) {   
  
            e.printStackTrace();   
  
        }   
  
    }   


}
