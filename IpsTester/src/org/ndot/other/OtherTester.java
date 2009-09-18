package org.ndot.other;

import org.junit.Test;

import com.nasoft.iso.ISOUtil;
import com.nasoft.util.EBCaASCtransfer;

public class OtherTester {

	@Test
	public void TestEBCaASCtransfer(){
		try {
		String asc="A";
		
		byte[] ebc = ISOUtil.asciiToEbcdic(asc);
		ISOUtil.ebcdicToAscii(ebc, 0, ebc.length);
		
        ebc = EBCaASCtransfer.pub_base_ASCtoEBC(asc.getBytes(), asc.getBytes().length);
		
		System.out.println();
		System.out.println("[aa] change in [ebc] is :\n[\n"+ISOUtil.byte2HexStr(ebc, ebc.length)+"]");
	
		} catch (Exception e) {
			e.printStackTrace();
		}}
}
