package com.nasoft.iso;

import com.nasoft.util.EBCaASCtransfer;

public class ISOEntireEBCDICFieldPackager extends ISOFieldPackager{


	private Interpreter interpreter;
	private Padder      padder;
	private Prefixer    prefixer;
	
	  
	public ISOEntireEBCDICFieldPackager() {
		this.interpreter=EbcdicInterpreter.INSTANCE;
		this.padder=NullPadder.INSTANCE;
		 this.prefixer = NullPrefixer.INSTANCE;
		// TODO Auto-generated constructor stub
	}

	public ISOEntireEBCDICFieldPackager(int len, String description) {
		super(len, description);
		this.interpreter=EbcdicInterpreter.INSTANCE;
		this.padder=NullPadder.INSTANCE;
		this.prefixer = NullPrefixer.INSTANCE;
	}
	
	public int getLength(byte[] b,int offset)throws ISOException{
		int len=b.length-offset;
		return len;
	}

	@Override
	public int getMaxPackedLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] pack(ISOComponent c) throws ISOException {
		 try
		    {
		      String data = (String) c.getValue();
//		      System.out.println(data);
		      
		      String paddedData = padder.pad(data, getLength());
		      byte[] rawData = new byte[prefixer.getPackedLength()
		          + interpreter.getPackedLength(EBCaASCtransfer.getEBDLen(paddedData))];
		      
		      prefixer.encodeLength(EBCaASCtransfer.getEBDLen(paddedData), rawData);
		      interpreter.interpret(paddedData, rawData, prefixer.getPackedLength());
		      return rawData;
		    } catch (Exception e)
		    {
		    	e.printStackTrace();
		      throw new ISOException(makeExceptionMessage(c, "packing"), e);
		    }
	}
	
	
	
	/** Create a nice readable message for errors */
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
	    try
	    {
	      int len = prefixer.decodeLength(b, offset);
	      if (len == -1)
	      {
	        // The prefixer doesn't know how long the field is, so use
	        // maxLength instead
	        len = getLength(b,offset);
	      }
	      
	      int lenLen = prefixer.getPackedLength();
	      String unpacked=interpreter.uninterpret(b, offset + lenLen, len);
	      
	      c.setValue(unpacked);
	      return lenLen + interpreter.getPackedLength(len);
	    } catch (Exception e)
	    {
	      throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
	    }
	}

	/**
	 * @return the interpreter
	 */
	public Interpreter getInterpreter() {
		return interpreter;
	}

	/**
	 * @param interpreter the interpreter to set
	 */
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * @return the padder
	 */
	public Padder getPadder() {
		return padder;
	}

	/**
	 * @param padder the padder to set
	 */
	public void setPadder(Padder padder) {
		this.padder = padder;
	}

	/**
	 * @return the prefixer
	 */
	public Prefixer getPrefixer() {
		return prefixer;
	}

	/**
	 * @param prefixer the prefixer to set
	 */
	public void setPrefixer(Prefixer prefixer) {
		this.prefixer = prefixer;
	}



}
