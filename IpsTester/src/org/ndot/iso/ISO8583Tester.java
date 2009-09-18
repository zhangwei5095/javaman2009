package org.ndot.iso;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;

import com.nasoft.PackUnpackHelper;
import com.nasoft.PackagerFactory;
import com.nasoft.iso.ISOUtil;

public class ISO8583Tester {
	private String isoset = "d:/xml/isoset_all.xml";
	private String node = "D:/xml/node.xml";

	@Before
	public void setUp() throws Exception {

		File file = new File(isoset);

		FileInputStream fin = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		fin.read(b);
		fin.close();
		PackagerFactory.initPackagerMap(new String(b));
		PackagerFactory.initHeaderMap(new String(b));

		File file3 = new File(node);
		FileInputStream fin3 = new FileInputStream(file3);
		byte[] b3 = new byte[(int) file3.length()];
		fin3.read(b3);
		fin3.close();

		PackagerFactory.initNodeInfo(new String(b3));

		
	}

	@Test
	public void testPack() {
		String data = "d:/xml/core_data.xml";
		try {
			File file1 = new File(data);
			FileInputStream fin1 = new FileInputStream(file1);
			byte[] b1 = new byte[(int) file1.length()];
			fin1.read(b1);
			fin1.close();
			byte[] packe = PackUnpackHelper.pack("C002", new String(b1));
			System.out
					.println("###" + ISOUtil.byte2HexStr(packe, packe.length));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testUnPack() {

		try {
//			  
			String hexPack = "02303234305847574A20202020202020323030383036313030303031313134363030303031D5D4B9FAB2C6202020202020202020202020202031202020202020202020202020202020202020202041414141414141414141414120202020202020202030202020202020373535362020202020203737313220202020203130353536202020202020323834342020202020202020203030314141414141414141414141413033D3C520202020BBDD2020202020203120202020202020202030CDCB20202020B2B92020202020203120202020202020202030BBFD202020B7D6202020202020203120202020323530343030303020200368";
			byte[] packe=ISOUtil.hex2byte(hexPack);
			
			String ddd = PackUnpackHelper.unpack("C003", packe);
			System.out.println(ddd);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
