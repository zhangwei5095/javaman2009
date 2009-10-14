package com.nasoft.test.util;

import com.nasoft.FixedPackUnpackHelper;

public class MulThreadUnpack implements Runnable {

	private byte[] pack;

	public MulThreadUnpack(byte[] pack) {
		this.pack = pack;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			byte[] ddd=new FixedPackUnpackHelper().pack("C023", new String(pack), "req");
			
			 String xml=new FixedPackUnpackHelper().unpack("C023", ddd,
					"req");
			 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("11111111");
		}

	}
	
	public void start() {
    	Thread runHandle = new Thread(this);
        runHandle.start();
    }

}
