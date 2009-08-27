package com.nasoft.iso;

import java.util.BitSet;

public class FixedISOMsg extends ISOMsg {
	
	public FixedISOMsg(){
		super();
	}
	
	public FixedISOMsg(String bitMapStr){
		super();
	}
	public FixedISOMsg(int fieldNumber){
		super(fieldNumber);
	}
	public FixedISOMsg(int fieldNumber,String bitMapStr){
		super(fieldNumber);
		this.bitMapStr=bitMapStr;
	}
	
	/**
	 * TODO 固定报文生成位图
	 */
	public void recalcBitMap() throws ISOException
	  {
	    if (!dirty)
	      return;

	    int bitLen=this.bitMapStr.length();
	    BitSet bmap=new BitSet(bitLen);
	    if(bitLen>0){
	    	for (int i = 0; i < bitLen; i++) {
				if (this.bitMapStr.charAt(i) == '1') {
					bmap.set(i);
				}
			}
	    }
	    set(new ISOBitMap(-1, bmap));

	    dirty = false;
	  }
	
	
}
