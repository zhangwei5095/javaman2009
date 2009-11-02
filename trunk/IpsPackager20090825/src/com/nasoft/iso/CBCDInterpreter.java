package com.nasoft.iso;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：IpsPackager20090825
 * 
 *<P>
 * 
 * 文件名： CBCDInterpreter.java
 * 
 *<P>
 * 
 * 功 能: 压缩的BCD码解析器
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-10-22
 * 
 */
public class CBCDInterpreter  implements Interpreter{

	/** 左补 0 */
	public static final CBCDInterpreter LEFT_PADDED = new CBCDInterpreter(true,
			false);
	/** 右补 0 */
	public static final CBCDInterpreter RIGHT_PADDED = new CBCDInterpreter(
			false, false);
	/** 左补 F */
	public static final CBCDInterpreter RIGHT_PADDED_F = new CBCDInterpreter(
			false, true);
	/** 右补 F */
	public static final CBCDInterpreter LEFT_PADDED_F = new CBCDInterpreter(
			true, true);

	private boolean leftPadded;
	private boolean fPadded;

	/** Kept private. Only two instances are possible. */
	private CBCDInterpreter(boolean leftPadded, boolean fPadded) {
		this.leftPadded = leftPadded;
		this.fPadded = fPadded;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.nasoft.iso.Interpreter#interpret(java.lang.String)
	 */
	public void interpret(String data, byte[] b, int offset) {
		ISOUtil.str2cbcd(data,leftPadded,fPadded, b, offset);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.nasoft.iso.Interpreter#uninterpret(byte[])
	 */
	public String uninterpret(byte[] rawData, int offset, int length) {
		return ISOUtil.cbcd2str(rawData, offset, length, leftPadded,fPadded);
	}

	/**
	 * Each numeric digit is packed into a nibble, so 2 digits per byte, plus
	 * the possibility of padding.
	 * 
	 * @see com.nasoft.iso.Interpreter#getPackedLength(int)
	 */
	public int getPackedLength(int nDataUnits) {
		return nDataUnits;
	}

}
