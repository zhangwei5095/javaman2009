package com.nasoft.iso;

import java.io.IOException;
import java.io.InputStream;

public class ISOEntireBinaryFieldPackager extends ISOBinaryFieldPackager{
	
	  
	public ISOEntireBinaryFieldPackager() {
		 
		super(LiteralBinaryInterpreter.INSTANCE,NullPrefixer.INSTANCE);
	}

	public ISOEntireBinaryFieldPackager(int len, String description) {
		super(len, description,LiteralBinaryInterpreter.INSTANCE,NullPrefixer.INSTANCE);
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

	
	 /**
	   * Convert the component into a byte[].
	   */
	  public byte[] pack(ISOComponent c) throws ISOException
	  {
	    try
	    {
	    	if(c.getValue()==null || "".equals(c.getValue())){
	    		return new byte[0];
	    	}
	      byte[] data = (byte[]) c.getValue();
	      int packedLength = prefixer.getPackedLength();
	      
	      byte[] ret = new byte[interpreter.getPackedLength(data.length)
	          + packedLength];
	      prefixer.encodeLength(data.length, ret);
	      interpreter.interpret(data, ret, packedLength);
	      return ret;
	    } catch (Exception e)
	    {
	      e.printStackTrace();
//	      System.out.println(c.getClass().toString());
	      throw new ISOException(makeExceptionMessage(c, "packing"), e);
	    }
	  }

	  /**
	   * @see com.nasoft.iso.ISOFieldPackager#unpack(com.nasoft.iso.ISOComponent, byte[],
	   *      int)
	   */
	  public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException
	  {
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
	      byte[] unpacked = interpreter.uninterpret(b, offset + lenLen, len);
	      c.setValue(unpacked);
	      return lenLen + interpreter.getPackedLength(len);
	    } catch (Exception e)
	    {
	      throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
	    }
	  }

	  /** Unpack from an input stream */
	  public void unpack(ISOComponent c, InputStream in) throws IOException,
	      ISOException
	  {
	    try
	    {
	      int lenLen = prefixer.getPackedLength();
	      int len;
	      if (lenLen == 0)
	      {
	        len = getLength();
	      } else
	      {
	        len = prefixer.decodeLength(readBytes(in, lenLen), 0);
	      }
	      int packedLen = interpreter.getPackedLength(len);
	      byte[] unpacked = interpreter.uninterpret(readBytes(in, packedLen), 0,
	          len);
	      c.setValue(unpacked);
	    } catch (ISOException e)
	    {
	      throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
	    }
	  }

	  /**
	   * component factory
	   * 
	   * @param fieldNumber -
	   *          the field number
	   * @return the newly created component
	   */
	  public ISOComponent createComponent(int fieldNumber)
	  {
	    return new ISOBinaryField(fieldNumber);
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
	    return getClass().getName() + ": Problem " + operation + " field "
	        + fieldKey;
	  }
	
}
