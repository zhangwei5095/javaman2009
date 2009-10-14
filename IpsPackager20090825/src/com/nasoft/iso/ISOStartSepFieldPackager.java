package com.nasoft.iso;

public class ISOStartSepFieldPackager extends ISOFieldPackager {

	private Interpreter interpreter;

	private String sepStr;//·Ö¸ô·û
	
	private byte[] sepByte;

	public ISOStartSepFieldPackager() {
		super();
		this.interpreter = LiteralInterpreter.INSTANCE;
		this.sepStr = "\t";
		this.sepByte="\t".getBytes();
	}

	public ISOStartSepFieldPackager(Interpreter interpreter,
			String sepStr) throws Exception{
		super();
		this.interpreter = interpreter;
		this.sepStr = sepStr;
		if (this.sepStr.startsWith("0x") || this.sepStr.startsWith("0X")) {
			this.sepByte = ISOUtil.hex2byte(this.sepStr.substring(2));
		} else {
			this.sepByte = this.sepStr.getBytes();
		}
	}

	public ISOStartSepFieldPackager(int maxLength, String description,
			 Interpreter interpreter, 
			String sepStr)throws Exception {
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
	 * @Override
	 * Returns the prefixer's packed length and the interpreter's packed length 
	 * 	and the sepStr's length.
	 */
	public int getMaxPackedLength() {
		// TODO Auto-generated method stub
		return interpreter.getPackedLength(getLength())
				+ sepByte.length;
	}

	
	/**
	 * @Override
	   * Convert the component into a byte[].
	   */
	public byte[] pack(ISOComponent c) throws ISOException {
		try {
			String data = (String) c.getValue();
			System.out.println( data);
			if (data.length() > getLength()) {
				throw new ISOException("Field length " + data.length()
						+ " too long. Max: " + getLength());
			}
			String paddedData = data;
			byte[] rawData = new byte[interpreter.getPackedLength(paddedData.getBytes().length)+this.sepByte.length];
			interpreter.interpret(paddedData, rawData,this.sepByte.length);
			System.arraycopy(this.sepByte, 0, rawData, 0, sepByte.length);
			System.out.println(new String(rawData));
			return rawData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISOException(makeExceptionMessage(c, "packing"), e);
		}
	}
	
	private String makeExceptionMessage(ISOComponent c, String operation)
	  {
	    Object fieldKey = "unknown";
	    if (c != null)
	    {
	      try
	      {
	        fieldKey = c.getKey();
	      } catch (Exception ignore)
	      {
	      }
	    }
	    return this.getClass().getName() + ": Problem " + operation + " field "
	        + fieldKey;
	  }

	@Override
	public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException {
		try {
			int len = getLength(b, offset+this.sepByte.length);
			String unpacked = interpreter.uninterpret(b, offset+this.sepByte.length, len);
			c.setValue(unpacked);
			return interpreter.getPackedLength(len+1);
		} catch (Exception e) {
			throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
		}
	}
	
	public int getLength(byte[] b,int offset)throws ISOException{
		int length=0;
		if(b.length<offset){
			throw new ISOException("the length of b is letter than offset");
		}
		byte[] byteSep=this.sepByte;
		for(int i=offset;i<b.length;i++){
			if(!byteEuquals(byteSep,b,i)){
				length+=1;
			}else{
				break;
			}
		}		
		return length;
	}

	private boolean byteEuquals(byte[] p1,byte[]p2,int offset){
		if(p1==null ||p2==null){
			if(p1==null && p2==null){
				return true;
			}
			return false;
		}
		
		int p1Len=p1.length;
		byte[] tempByte=new byte[p1Len];
		System.arraycopy(p2, offset, tempByte, 0, p1Len);
		int p2Len=tempByte.length;
		if(p1Len!=p2Len){
			return false;
		}else{
			for(int i=0;i<p1Len;i++){
				if(p1[i]!=tempByte[i]){
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

	public void setSepStr(String sepStr)throws ISOException {
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

