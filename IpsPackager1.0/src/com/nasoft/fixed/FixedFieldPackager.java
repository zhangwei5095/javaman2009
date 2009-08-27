package com.nasoft.fixed;

import com.nasoft.iso.ISOUtil;

public class FixedFieldPackager 
{
	private int fieldNo;
	private String fieldType;
	private String padderType;
	private int fieldLen;
	private int internalNo;
	private int offset;
	
	public FixedFieldPackager()
	{
		
	}
	public int getFieldLen() {
		return fieldLen;
	}
	public void setFieldLen(int fieldLen) {
		this.fieldLen = fieldLen;
	}
	public int getFieldNo() {
		return fieldNo;
	}
	public void setFieldNo(int fieldNo) {
		this.fieldNo = fieldNo;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getInternalNo() {
		return internalNo;
	}
	public void setInternalNo(int internalNo) {
		this.internalNo = internalNo;
	}
	
	public FixedFieldPackager(int no, String type, String padder, int len, int ino)
	{
		this.fieldNo = no;
		this.fieldType = type;
		this.fieldLen = len;
		this.internalNo = ino;
		this.padderType = padder;

	}

	public String getPadderType() {
		return padderType;
	}
	public void setPadderType(String padderType) {
		this.padderType = padderType;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void pack(byte[] out, byte[] fvalue) 
		throws Exception
	{
		/*
		 * 	0 - ASCII字符
		 *	1 - BCD码
		 *	2 - EBDC码
		 *	3 - Binary二进制
		 */
		switch(fieldType.charAt(0))
		{
		case '0':
			byte[] b = this.padASCII(fvalue);
			System.arraycopy(b, 0, out, offset, fieldLen);
			break;
		case '1':
			System.arraycopy(str2Bcd(new String(fvalue)), 0, out, offset, fieldLen);
			break;
		case '2':
			System.arraycopy(str2ebcdic(fvalue), 0, out, offset, fieldLen);
			
			break;
		case '3':
			System.arraycopy(hex2byte(fvalue), 0, out, offset, fieldLen);
			break;
		}
	}
	public byte[] unpack(byte[] in)
	{
		return null;
	}
	private byte[] padASCII(byte[] in)
	{
		
		if(in.length<this.fieldLen)
		{
			byte[] b = new byte[fieldLen];
			if(padderType.charAt(0)=='0')
			{
				System.arraycopy(in, 0, b, fieldLen-in.length, in.length);
				for(int i=0; i<(fieldLen-in.length); i++)
				{
					b[i]=0x30;
				}
			}else if(padderType.charAt(0)=='1')
			{
				System.arraycopy(in, 0, b, 0, in.length);
				for(int i=in.length; i<fieldLen; i++)
				{
					b[i] = 0x20;
				}
			}else
			{
				System.arraycopy(in, 0, b, 0, in.length);
			}
			return b;
		}else
		{
			return in;
		}
		
	}
	
	public String dumpXml(byte[] value) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<F"+internalNo);
		/*
		 * 	0 - ASCII字符
		 *	1 - BCD码
		 *	2 - EBDC码
		 *	3 - Binary二进制
		 */
		switch(fieldType.charAt(0))
		{
		case 0:
			sb.append(">");
			sb.append(new String(value));
			sb.append("</F"+internalNo+">");
			break;
		case 1:
			sb.append(" type=\"bcd\">");
			sb.append(new String(value));
			sb.append("</F"+internalNo+">");
			break;
		case 2:
			sb.append(" type=\"ebdcic\">");
			sb.append(new String(value));
			sb.append("</F"+internalNo+">");
			break;
		case 3:
			sb.append(" type=\"binary\">");
			sb.append(new String(value));
			sb.append("</F"+internalNo+">");
			break;
		}
		return sb.toString();
		
	}
	
	private byte[] str2ebcdic(byte[] ascii)
	{
		return ISOUtil.asciiToEbcdic(padASCII(ascii));
	}
	private String bcd2Str(byte[] value)
	{
		return ISOUtil.bcd2str(value, 0, value.length, padderType.equals("0")?true:false);
	}
	private byte[] hex2byte(byte[] hex) 
		throws Exception
	{
		return ISOUtil.hex2byte(hex, 0, fieldLen);
	}
	private byte[] str2Bcd(String s)
	{
		boolean padLeft = false;
		byte fill = 0;;
		switch(padderType.charAt(0))
		{
		case '0':
			padLeft = true;
			fill = 0;
			break;
		case '1':
			padLeft = false;
			fill = 0;
			break;
		case '2':
			padLeft = true;
			fill = 0xf;
			break;
		case '3':
			padLeft = false;
			fill = 0xf;
			break;
			
		}
		return ISOUtil.str2bcd(s, padLeft, fill);
	}
}
