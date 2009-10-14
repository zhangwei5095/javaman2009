package com.nasoft.iso;

public class ISODetailSepFieldPackager extends ISOFieldPackager {

	private Interpreter interpreter;

	private String sepStr;// 分隔符

	private byte[] sepByte;

	private int detailFileCounts;

	public ISODetailSepFieldPackager() {
	}

	public ISODetailSepFieldPackager(String sepStr, int detailFileCounts)
			throws Exception {
		super();
		this.interpreter = LiteralInterpreter.INSTANCE;
		this.sepStr = sepStr;
		if (this.sepStr.startsWith("0x") || this.sepStr.startsWith("0X")) {
			this.sepByte = ISOUtil.hex2byte(this.sepStr.substring(2));
		} else {
			this.sepByte = this.sepStr.getBytes();
		}
		this.detailFileCounts = detailFileCounts;
	}

	public ISODetailSepFieldPackager(Interpreter interpreter, String sepStr,
			int detailFileCounts) throws Exception {
		super();
		this.interpreter = interpreter;
		this.sepStr = sepStr;
		this.detailFileCounts = detailFileCounts;
		if (this.sepStr.startsWith("0x") || this.sepStr.startsWith("0X")) {
			this.sepByte = ISOUtil.hex2byte(this.sepStr.substring(2));
		} else {
			this.sepByte = this.sepStr.getBytes();
		}
	}

	public ISODetailSepFieldPackager(int maxLength, String description,
			Interpreter interpreter, String sepStr) throws Exception {
		super(maxLength, description);
		this.interpreter = interpreter;
		this.sepStr = sepStr;
		if (this.sepStr.startsWith("0x") || this.sepStr.startsWith("0X")) {
			this.sepByte = ISOUtil.hex2byte(this.sepStr.substring(2));
		} else {
			this.sepByte = this.sepStr.getBytes();
		}
	}

	/**
	 * @Override Returns the prefixer's packed length and the interpreter's
	 *           packed length and the sepStr's length.
	 */
	public int getMaxPackedLength() {
		// TODO Auto-generated method stub
		return interpreter.getPackedLength(getLength()) + sepByte.length;
	}

	/**
	 * @Override Convert the component into a byte[].
	 */
	public byte[] pack(ISOComponent c) throws ISOException {
		// TODO Auto-generated method stub
		try {
			String data = (String) c.getValue();
			System.out.println(data);
			if (data.length() > getLength()) {
				throw new ISOException("Field length " + data.length()
						+ " too long. Max: " + getLength());
			}

			String paddedData = data;

			byte[] rawData = new byte[interpreter.getPackedLength(paddedData
					.getBytes().length)
					+ this.sepByte.length];

			interpreter.interpret(paddedData, rawData, 0);

			System.arraycopy(this.sepByte, 0, rawData,
					paddedData.getBytes().length, sepByte.length);
			return rawData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISOException(makeExceptionMessage(c, "packing"), e);
		}
	}

	private String makeExceptionMessage(ISOComponent c, String operation) {
		Object fieldKey = "unknown";
		if (c != null) {
			try {
				fieldKey = c.getKey();
			} catch (Exception ignore) {
			}
		}
		return this.getClass().getName() + ": Problem " + operation + " field "
				+ fieldKey;
	}

	@Override
	public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException {
		try {
			// 获得明细域含有的纪录数对应的位置
			System.out.println(new String(b));
			int recCountsPos = getLength(b, offset, 2);
			byte[] tem = new byte[recCountsPos-this.sepByte.length];
			System.arraycopy(b, offset+this.sepByte.length, tem, 0,
					tem.length);

			int recCounts = Integer.parseInt(new String(tem));
			int len = getLength(b, offset, recCounts * this.detailFileCounts
					+ 2);
			len += 1;
			int packedLen = interpreter.getPackedLength(len);
			String unpacked = interpreter.uninterpret(b, offset, packedLen
					- this.sepByte.length);
			c.setValue(unpacked);
			return packedLen;
		} catch (Exception e) {
			throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
		}
	}

	public int getLength(byte[] b, int offset, int times) throws ISOException {
		int length = 0;
		if (b.length < offset) {
			throw new ISOException("the length of b is letter than offset");
		}
		byte[] byteSep = this.sepByte;
		for (int i = offset; i < b.length; i++) {
			if (byteEuquals(byteSep, b, i)) {
				if (--times == 0)
					break;
			}
			length += 1;
		}
		// length += this.sepByte.length * times;
		return length;
	}

	/**
	 * 
	 * @param sepBytes
	 *            采用的分隔符的字节数组，如"\t".getBytes();
	 * @param bodyBytes
	 *            解析的报文体字节
	 * @param offset
	 *            起始位置
	 * @return 功能：比较当前解析的报文的当前位置（offset）的下一个数据是否是分隔符
	 */
	private boolean byteEuquals(byte[] sepBytes, byte[] bodyBytes, int offset) {
		if (sepBytes == null || bodyBytes == null) {
			if (sepBytes == null && bodyBytes == null) {
				return true;
			}
			return false;
		}

		int p1Len = sepBytes.length;
		byte[] tempByte = new byte[p1Len];
		System.arraycopy(bodyBytes, offset, tempByte, 0, p1Len);
		int p2Len = tempByte.length;
		if (p1Len != p2Len) {
			return false;
		} else {
			for (int i = 0; i < p1Len; i++) {
				if (sepBytes[i] != tempByte[i]) {
					return false;
				}
			}
			return true;
		}
	}

	public Interpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	public String getSepStr() {
		return sepStr;
	}

	public void setSepStr(String sepStr) throws ISOException {
		this.sepStr = sepStr;
		if (this.sepStr.startsWith("0x") || this.sepStr.startsWith("0X")) {
			this.sepByte = ISOUtil.hex2byte(this.sepStr.substring(2));
		} else {
			this.sepByte = this.sepStr.getBytes();
		}
	}

	protected void checkLength(int len, int maxLength)
			throws IllegalArgumentException {
		if (len > maxLength) {
			throw new IllegalArgumentException("Length " + len
					+ " too long for " + getClass().getName());
		}
	}

}
