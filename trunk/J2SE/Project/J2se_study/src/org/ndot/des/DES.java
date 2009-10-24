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
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： DES.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-10-23
 * 
 */
public class DES {
	/**  
	 
     *  
 
     * @return DES算法密钥  
 
     */  
  
    public static byte[] generateKey() {   
  
        try {   
  
  
  
            // DES算法要求有一个可信任的随机数源   
  
            SecureRandom sr = new SecureRandom();   
  
  
  
            // 生成一个DES算法的KeyGenerator对象   
  
            KeyGenerator kg = KeyGenerator.getInstance("DES");   
  
            kg.init(sr);   
  
  
  
            // 生成密钥   
  
            SecretKey secretKey = kg.generateKey();   
  
  
  
            // 获取密钥数据   
  
            byte[] key = secretKey.getEncoded();   
  
  
  
            return key;   
  
        } catch (NoSuchAlgorithmException e) {   
  
            System.err.println("DES算法，生成密钥出错!");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * 加密函数  
 
     *  
 
     * @param data  
 
     *            加密数据  
 
     * @param key  
 
     *            密钥  
 
     * @return 返回加密后的数据  
 
     */  
  
    public static byte[] encrypt(byte[] data, byte[] key) {   
  
  
  
        try {   
  
  
  
            // DES算法要求有一个可信任的随机数源   
  
            SecureRandom sr = new SecureRandom();   
  
  
  
            // 从原始密钥数据创建DESKeySpec对象   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成   
  
            // 一个SecretKey对象   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // using DES in ECB mode   
  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");   
  
  
  
            // 用密匙初始化Cipher对象   
  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);   
  
  
  
            // 执行加密操作   
  
            byte encryptedData[] = cipher.doFinal(data);   
  
  
  
            return encryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES算法，加密数据出错!");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * 解密函数  
 
     *  
 
     * @param data  
 
     *            解密数据  
 
     * @param key  
 
     *            密钥  
 
     * @return 返回解密后的数据  
 
     */  
  
    public static byte[] decrypt(byte[] data, byte[] key) {   
  
        try {   
  
            // DES算法要求有一个可信任的随机数源   
  
            SecureRandom sr = new SecureRandom();   
  
  
  
            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;   
  
  
  
            // 从原始密匙数据创建一个DESKeySpec对象   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成   
  
            // 一个SecretKey对象   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // using DES in ECB mode   
  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");   
  
  
  
            // 用密匙初始化Cipher对象   
  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);   
  
  
  
            // 正式执行解密操作   
  
            byte decryptedData[] = cipher.doFinal(data);   
  
  
  
            return decryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES算法，解密出错。");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * 加密函数  
 
     *  
 
     * @param data  
 
     *            加密数据  
 
     * @param key  
 
     *            密钥  
 
     * @return 返回加密后的数据  
 
     */  
  
    public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {   
  
  
  
        try {   
  
            // 从原始密钥数据创建DESKeySpec对象   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成   
  
            // 一个SecretKey对象   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // Cipher对象实际完成加密操作   
  
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");   
  
            // 若采用NoPadding模式，data长度必须是8的倍数   
  
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");   
  
  
  
            // 用密匙初始化Cipher对象   
  
            IvParameterSpec param = new IvParameterSpec(iv);   
  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);   
  
  
  
            // 执行加密操作   
  
            byte encryptedData[] = cipher.doFinal(data);   
  
  
  
            return encryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES算法，加密数据出错!");   
  
            e.printStackTrace();   
  
        }   
  
  
  
        return null;   
  
    }   
  
  
  
    /**  
 
     * 解密函数  
 
     *  
 
     * @param data  
 
     *            解密数据  
 
     * @param key  
 
     *            密钥  
 
     * @return 返回解密后的数据  
 
     */  
  
    public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {   
  
        try {   
  
            // 从原始密匙数据创建一个DESKeySpec对象   
  
            DESKeySpec dks = new DESKeySpec(key);   
  
  
  
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成   
  
            // 一个SecretKey对象   
  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
  
            SecretKey secretKey = keyFactory.generateSecret(dks);   
  
  
  
            // using DES in CBC mode   
  
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");   
  
            // 若采用NoPadding模式，data长度必须是8的倍数   
  
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");   
  
  
  
            // 用密匙初始化Cipher对象   
  
            IvParameterSpec param = new IvParameterSpec(iv);   
  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);   
  
  
  
            // 正式执行解密操作   
  
            byte decryptedData[] = cipher.doFinal(data);   
  
  
  
            return decryptedData;   
  
        } catch (Exception e) {   
  
            System.err.println("DES算法，解密出错。");   
  
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
