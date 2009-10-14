package com.nasoft.fixed;

import java.util.HashMap;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;

public class FixedMsg 
{
	private IFixedMsgPackager packager;
	private HashMap<Integer, byte[]> fields = new HashMap<Integer, byte[]>();
	private int maxField = -1;
	private String tranCode;
	public void setFieldBytes(int fldno, byte[] bvalue)
	{
		fields.put(Integer.valueOf(fldno), bvalue);
		if(fldno>maxField)
		{
			maxField = fldno;
		}
	}
	public void setFieldHexStr(int fldno, String svalue)throws ISOException
	{
		fields.put(Integer.valueOf(fldno), ISOUtil.hex2byte(svalue));
		if(fldno>maxField)
		{
			maxField = fldno;
		}
	}
	public void unsetField(int fldno)
	{
		
	}
	public String getFieldHexStr(int fldno)
	{
		if(fields.containsKey(Integer.valueOf(fldno)))
		{
			return ISOUtil.hexString(fields.get(Integer.valueOf(fldno)));
		}else
		{
			return null;
		}
	}
	public byte[] getFieldBytes(int fldno)
	{
		if(fields.containsKey(Integer.valueOf(fldno)))
		{
			return fields.get(Integer.valueOf(fldno));
		}else
		{
			return null;
		}
	}
	
	public byte[] pack() throws Exception 
	{
		synchronized (this)
		{    
			return packager.pack(this);
		}
	}
	public int unpack(byte[] b) throws Exception 
	{
		synchronized (this)
		{
			return packager.unpack(this, b);
		}
	}
	public String Msg2Xml() throws Exception
	{
		return packager.Msg2Xml(this);
	}
	public void Xml2Msg(String xml) throws Exception
	{
		 packager.Xml2Msg(this, xml);
	}
	public void setPackager(IFixedMsgPackager packager) {
		this.packager = packager;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
}
