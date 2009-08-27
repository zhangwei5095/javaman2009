package com.nasoft.test.util;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;

import com.nasoft.FixedPackUnpackHelper;
import com.nasoft.PackagerFactory;
import com.nasoft.iso.ISOUtil;

public class TestFixedMsg extends TestCase {

	// public void testBitMap(){
	// BitSet bmap = new BitSet(128);
	// String
	// bitMap="01110011001100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	// for (int i = 0; i < 128; i++) {
	// if (bitMap.charAt(i) == '1') {
	// bmap.set(i);
	// }
	// }
	// for(int i=0;i<128;i++){
	// if(bmap.get(i)){
	// System.out.print("1");
	// }else{
	// System.out.print("0");
	// }
	// }
	// System.out.println();
	// }

//	public void testMultiAsciiPrifixer() {
//		 MultiAsciiPrefixer prifixer=new MultiAsciiPrefixer(0,4,50,2);
//		 byte []b=new byte[10];
//		 try{
//		 prifixer.encodeLength(600,b);
//		 System.out.println(ISOUtil.byte2HexStr(b, b.length));
//		 byte[]b1=new byte[4];
//		 b1[0]=0x20;
//		 b1[1]=0x20;
//		 b1[2]=0x31;
//		 b1[3]=0x32;
//		 int length=prifixer.decodeLength(b1, 0);
//		 System.out.println(length);
//		 }catch(Exception e){
//		 e.printStackTrace();
//		 }
//
//	}

	// public void testISOSepFieldPackager(){
	// try{
	// ISOSepFieldPackager packager=new
	// ISOSepFieldPackager(26,"11",LiteralInterpreter.INSTANCE,"0x0A0B0C0D");
	// ISOComponent c=new ISOField();
	// c.setValue("ºº×Ö");
	//			
	// ISOComponent c2=new ISOField();
	// c2.setValue("ËûÂèµÄºº×Ö");
	//			
	// byte [] b1=packager.pack(c);
	// byte [] b2=packager.pack(c2);
	//			
	// byte[] b=new byte[b1.length+b2.length];
	// System.arraycopy(b1, 0, b, 0, b1.length);
	// System.arraycopy(b2, 0, b, b1.length, b2.length);
	//			
	// System.out.println(ISOUtil.byte2HexStr(b, b.length));
	// ISOComponent c3=new ISOField();
	// int off=packager.unpack(c3, b, 0);
	// System.out.println("off=="+off);
	// ISOComponent c4=new ISOField();
	// packager.unpack(c4, b, off);
	//			
	//			
	// System.out.println(c3.getValue());
	// System.out.println(c4.getValue());
	//			
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	//		
	// }

	/**
	 * ²âÊÔ²ð°ü×é°ü
	 */
	public void testFixedpackager() {
		try {
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

			File file2 = new File("d:/xml/data.xml");
			FileInputStream fin2 = new FileInputStream(file2);
			byte[] b2 = new byte[(int) file2.length()];
			fin2.read(b2);
			fin2.close();
			System.out.println("b2="+b2);
			//			  
			File file3 = new File("D:/xml/node.xml");
			FileInputStream fin3 = new FileInputStream(file3);
			byte[] b3 = new byte[(int) file3.length()];
			fin3.read(b3);
			fin3.close();

			PackagerFactory.initNodeInfo(new String(b3));
			//			  
			 //System.out.println(new String (b2));
			String nodeId = "C023";
			String reqRsp = "rsp";
//			byte[] pack1=FixedPackUnpackHelper.pack(nodeId, new String(b2),
//					 reqRsp);
//			System.out.println(ISOUtil.byte2HexStr(pack1,pack1.length));
						  

//			String hexPack = "d9d8f8f2f3f0f1f0f2f0f0f860f0f660f1f4f2f0f0f860f0f660f1f4f0f84bf4f94bf2f2f3f2f6f1f6f0f3f2f6f0f7f840404040f0f0f0f0f0f0f0f0f0f0f0f1f04040404040404040404097a3a26df1f2f8f64040f0f1f3f4f5c3f1c4f9f8f0f4404040f1f04bf1f6f34bf1f4f24bf7404040404040404040404040404040f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0404040c5c2c5f3c6c2c3f5404040404040404040404040404040404040f1f1f8f0f00e4af955d450c74c5b49c556500f4040404040404040404040404040404040404040404040404040f2f0f0f8f0f4f0f1f0f0f1f10e4af955d450c74c5b49c556500f4040404040404040404040404040404040404040404040404040f1f3f2f8f2f3f1f9f5f5f0f6f2f0f8f2f2f340404040f9f1f6f0f3f0f7f0f0f0f1f0f1f0f0f4f4f0f0f2f140f1f1f3f2f8f2f3f1f9f5f5f0f6f2f0f8f2f2f34040400e59465a9250bb0f4040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040400e58c05c4f0f4040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040f8f6f1f9f0f0f840404040404040404040404040f0f0f0f160f0f160f0f1f0f0f0f160f0f160f0f1404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040";
			String hexPack="333220202020202020203139322020202020202030333137202030303136313236362020202020202020202020202020202020202020202032373632362020202020202020202020202020202020202020202020202031313030303031333039313033363832323020202020202032303030303230393235303731323030313032313039303331BCD6CFFEB6AB20202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202031302E303020202020202020302E30302020202020202020302E30302020202020202020302E30302020202020202020302E30302020202020202020302E30302020202020202020323030383036313731363235353023";
			byte[] pack = ISOUtil.hex2byte(hexPack);
			String xmlPackage = new FixedPackUnpackHelper().unpack(nodeId, pack,reqRsp);
//			System.out.println(xmlPackage);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	// public void testAsciiPrefixerRightPadSpace(){
	// AsciiPrefixerRightPadSpace aa=new AsciiPrefixerRightPadSpace(3);
	// int length=3;
	// byte[] b=new byte[3];
	// try{
	// String ssss="\n";
	// System.out.println(
	// ISOUtil.byte2HexStr(ssss.getBytes(),ssss.getBytes().length));
	//			
	//			
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }

	// public void testISOEntireFieldPackager(){
	// try{
	// ISOEntireFieldPackager packager=new ISOEntireFieldPackager();
	// ISOComponent c=new ISOField(2,"dddddd");
	// c.setValue("12345678");
	// byte [] b1=packager.pack(c);
	// System.out.println(ISOUtil.byte2HexStr(b1, b1.length));
	//			
	// ISOComponent c3=new ISOField();
	// int off=packager.unpack(c3, b1, 0);
	// System.out.println("off=="+off);
	// System.out.println(c3.getValue());
	//			
	// }catch(Exception e){
	// e.printStackTrace();
	// }

	// }

	// public void testISOStringFieldPackager(){
	// ISOStringFieldPackager ii=new ISOStringFieldPackager();
	// MultiAsciiPrefixer prefixer=new MultiAsciiPrefixer(2,4,10,0);
	// try{
	// ii.setPrefixer(prefixer);
	// ISOComponent c=new ISOField(2,"dddddd");
	// c.setValue("123456789012345678901234567890");
	// byte[] b1=ii.pack(c);
	// System.out.println(ISOUtil.byte2HexStr(b1, b1.length));
	// ISOComponent c3=new ISOField(2,"ddddd");
	// ii.unpack(c3, b1, 0);
	// System.out.println("unpacked:"+c3.getValue());
	//			
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	//		
	// }
}
