package com.nasoft.test.util;

import java.io.File;
import java.io.FileInputStream;

import com.nasoft.PackUnpackHelper;
import com.nasoft.PackagerFactory;
import com.nasoft.iso.ISOUtil;
/**
 * @author yangfeng
 * 
 */
public class TestISO8583
{
  public static void main(String[] args) throws Exception
  {
	  File file = new File("d:/xml/isoset3.xml");
	  
	  FileInputStream fin = new FileInputStream(file);
	  byte[] b = new byte[(int)file.length()];
	  fin.read(b);
	  fin.close();
////	  System.out.println(new String(b));
//	  String ss="";
	  PackagerFactory.initPackagerMap(new String(b));
	  PackagerFactory.initHeaderMap(new String(b));
	  
	  File file1 = new File("d:/data4.xml");
	  FileInputStream fin1 = new FileInputStream(file1);
	  byte[] b1 = new byte[(int)file1.length()];
	  fin1.read(b1);
	  fin1.close();
//	  
	  File file3 = new File("D:/xml/node.xml");
	  FileInputStream fin3 = new FileInputStream(file3);
	  byte[] b3 = new byte[(int)file3.length()];
	  fin3.read(b3);
	  fin3.close();
	  
	  PackagerFactory.initNodeInfo(new String (b3));
//	  System.out.println("DDDDDDDDDDDD="+new String(b1));
//	  
	  byte[] packe=PackUnpackHelper.pack("C003", new String(b1));
	  System.out.println("###"+ ISOUtil.byte2HexStr(packe,packe.length));
//	  
//	  String hexPack="02303234305847574A20202020202020323030383036313030303031313134363030303031D5D4B9FAB2C6202020202020202020202020202031202020202020202020202020202020202020202041414141414141414141414120202020202020202030202020202020373535362020202020203737313220202020203130353536202020202020323834342020202020202020203030314141414141414141414141413033D3C520202020BBDD2020202020203120202020202020202030CDCB20202020B2B92020202020203120202020202020202030BBFD202020B7D6202020202020203120202020323530343030303020200368";
//	  byte[] packe=hex2Byte(hexPack);
////	  
	  String ddd=PackUnpackHelper.unpack("C003",packe);
	  System.out.println(ddd);
	  
  }
  
  
  
  public static final byte[] hex2Byte(String b) {
		char data[] = b.toCharArray();
		int l = data.length;
		byte out[] = new byte[l >> 1];
		int i = 0;
		for (int j = 0; j < l;) {
			int f = Character.digit(data[j++], 16) << 4;
			f |= Character.digit(data[j++], 16);
			out[i] = (byte) (f & 0xff);
			i++;
		}

		return out;
	}
}
