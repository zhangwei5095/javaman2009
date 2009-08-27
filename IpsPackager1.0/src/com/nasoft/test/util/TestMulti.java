package com.nasoft.test.util;

import java.io.File;
import java.io.FileInputStream;

import com.nasoft.PackagerFactory;
import com.nasoft.iso.ISOUtil;

public class TestMulti extends Thread{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			File file = new File("D:/xml/fixedNode.xml");
			FileInputStream fin = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			fin.read(b);
			fin.close();
			PackagerFactory.initFixedHeaderMap(new String(b));
			PackagerFactory.initFixedPackagerMap(new String(b));
			
			File file1 = new File("d:/xml/fixedBitMap.xml");
			FileInputStream fin1 = new FileInputStream(file1);
			byte[] b1 = new byte[(int) file1.length()];
			fin1.read(b1);
			fin1.close();
			PackagerFactory.initFixedBitMap(new String(b1));
			
			File file3 = new File("D:/xml/node.xml");
			FileInputStream fin3 = new FileInputStream(file3);
			byte[] b3 = new byte[(int) file3.length()];
			fin3.read(b3);
			fin3.close();

			PackagerFactory.initNodeInfo(new String(b3));
			
			File file2 = new File("d:/xml/data.xml");
			FileInputStream fin2 = new FileInputStream(file2);
			byte[] b2 = new byte[(int) file2.length()];
			fin2.read(b2);
			fin2.close();
			
//			String hexPack = "d9d8f8f2f3f0f1f0f2f0f0f860f0f660f1f4f2f0f0f860f0f660f1f4f0f84bf4f94bf2f2f3f2f6f1f6f0f3f2f6f0f7f840404040f0f0f0f0f0f0f0f0f0f0f0f1f04040404040404040404097a3a26df1f2f8f64040f0f1f3f4f5c3f1c4f9f8f0f4404040f1f04bf1f6f34bf1f4f24bf7404040404040404040404040404040f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0404040c5c2c5f3c6c2c3f5404040404040404040404040404040404040f1f1f8f0f00e4af955d450c74c5b49c556500f4040404040404040404040404040404040404040404040404040f2f0f0f8f0f4f0f1f0f0f1f10e4af955d450c74c5b49c556500f4040404040404040404040404040404040404040404040404040f1f3f2f8f2f3f1f9f5f5f0f6f2f0f8f2f2f340404040f9f1f6f0f3f0f7f0f0f0f1f0f1f0f0f4f4f0f0f2f140f1f1f3f2f8f2f3f1f9f5f5f0f6f2f0f8f2f2f34040400e59465a9250bb0f4040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040400e58c05c4f0f4040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040f8f6f1f9f0f0f840404040404040404040404040f0f0f0f160f0f160f0f1f0f0f0f160f0f160f0f1404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040";
			String hexPack="33322020202020202020202020202020202020203033313720203030313631323430202020202020202020202020202020202020202020203237363236202020202020202020202020202020202020202020202020203131303130202032303131363338202020202020202020202020202020202020202020202030";
			byte[] pack = ISOUtil.hex2byte(hexPack);
			for(int i=0;i<100;i++){
				MulThreadUnpack mulThread=new MulThreadUnpack(b2);
				mulThread.start();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
