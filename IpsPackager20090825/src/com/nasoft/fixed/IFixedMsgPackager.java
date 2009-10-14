package com.nasoft.fixed;

public interface IFixedMsgPackager 
{
	public byte[] pack(FixedMsg msg)throws Exception ;

	public int unpack(FixedMsg msg, byte[] b)throws Exception ;
	
	public String Msg2Xml(FixedMsg msg) throws Exception ;
	
	public FixedMsg Xml2Msg(FixedMsg msg, String xml) throws Exception;
	
	public String getTranId();
	
	public void setTranId(String tranId);
}
