package org.ndot.fixed;
/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： TestMain.java
 * 
 * 功 能:
 * 
 * 作 者: 孙金城
 * 
 * 创建时间: 2009-9-16
 * 
 */
import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;

import com.nasoft.FixedPackUnpackHelper;
import com.nasoft.PackagerFactory;
import com.nasoft.iso.ISOUtil;

public class FixedTester {
	private String fiexdNode = "D:/xml/fixedNode.xml";
	private String fixedBitMap = "d:/xml/fixedBitMap.xml";
	private String node = "D:/xml/node.xml";

	@Before
	public void setUp() throws Exception {
		try {
			File file = new File(fiexdNode);
			FileInputStream fin = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			fin.read(b);
			fin.close();

			PackagerFactory.initFixedHeaderMap(new String(b));
			PackagerFactory.initFixedPackagerMap(new String(b));

			File file1 = new File(fixedBitMap);
			FileInputStream fin1 = new FileInputStream(file1);
			byte[] b1 = new byte[(int) file1.length()];
			fin1.read(b1);
			fin1.close();
			PackagerFactory.initFixedBitMap(new String(b1));

			File file3 = new File(node);
			FileInputStream fin3 = new FileInputStream(file3);
			byte[] b3 = new byte[(int) file3.length()];
			fin3.read(b3);
			fin3.close();

			PackagerFactory.initNodeInfo(new String(b3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendReportToCBS() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 固定报文 组包
	 */
	@Test
	public void TestPack() {
		try {
			String data = "d:/xml/ndotdata.xml";
			String nodeId = "C028";
			String reqRsp = "rsp";
			File file2 = new File(data);
			FileInputStream fin2 = new FileInputStream(file2);
			byte[] b2 = new byte[(int) file2.length()];
			fin2.read(b2);
			fin2.close();
			byte[] pack = FixedPackUnpackHelper.pack(nodeId, new String(b2),
					reqRsp);
			System.out.println(ISOUtil.byte2HexStr(pack, pack.length));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 固定报文 解包
	 */
	@Test
	public void TestUnPack() {
		try {
			String nodeId = "C021";
			String reqRsp = "rsp";
			String hexPack = "4A2020202020202002303234305847574A20202020202020323030383036323130303930303030393030303031D5D4B9FAB2C6202020202020202020202020202031202020202020202020202020202020202020202041414141414141414141414120202020202020202030202020202020373535362020202020203737313220202020203130353536202020202020323834342020202020202020203030314141414141414141414141413033D3C520202020BBDD2020202020203120202020202020202030CDCB20202020B2B92020202020203120202020202020202030BBFD202020B7D6202020202020203120202020323530343030303020200369";
			byte[] pack = ISOUtil.hex2byte(hexPack);
			String xmlPackage = FixedPackUnpackHelper.unpack(nodeId, pack,
					reqRsp);
			System.out.println(xmlPackage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
