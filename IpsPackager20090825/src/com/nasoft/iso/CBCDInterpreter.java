package com.nasoft.iso;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�IpsPackager20090825
 * 
 *<P>
 * 
 * �ļ����� CBCDInterpreter.java
 * 
 *<P>
 * 
 * �� ��: ѹ����BCD�������
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-10-22
 * 
 */
public class CBCDInterpreter  implements Interpreter{

	/** �� 0 */
	public static final CBCDInterpreter LEFT_PADDED = new CBCDInterpreter(true,
			false);
	/** �Ҳ� 0 */
	public static final CBCDInterpreter RIGHT_PADDED = new CBCDInterpreter(
			false, false);
	/** �� F */
	public static final CBCDInterpreter RIGHT_PADDED_F = new CBCDInterpreter(
			false, true);
	/** �Ҳ� F */
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
