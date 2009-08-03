package org.ndot;

import org.junit.Test;

import com.nasoft.iso.ISOUtil;

public class TestMain {
	@Test
	public void testReportLen() {
		try {
//			String hexstr = "007c";//7*16+12=124
			String hexstr = "0080";//8*16+0=128
			byte[] len = ISOUtil.hex2byte(hexstr);
			int rlen = ISOUtil.bytes2Int(len);
			System.out.println(hexstr + "=" + rlen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
