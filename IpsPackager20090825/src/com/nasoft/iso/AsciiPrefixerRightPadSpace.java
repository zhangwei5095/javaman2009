package com.nasoft.iso;
/**
 * TODO ASCIIÓÒ²¹¿Õ¸ñÇ°×º
 * @author dbg
 *
 */
public class AsciiPrefixerRightPadSpace implements Prefixer {

	protected int                       nDigits;
	
	public static final AsciiPrefixerRightPadSpace L      = new AsciiPrefixerRightPadSpace(1);
	public static final AsciiPrefixerRightPadSpace LL      = new AsciiPrefixerRightPadSpace(2);
	public static final AsciiPrefixerRightPadSpace LLL      = new AsciiPrefixerRightPadSpace(3);
	public static final AsciiPrefixerRightPadSpace LLLL      = new AsciiPrefixerRightPadSpace(4);
	public static final AsciiPrefixerRightPadSpace LLLLL      = new AsciiPrefixerRightPadSpace(5);
	public static final AsciiPrefixerRightPadSpace LLLLLL      = new AsciiPrefixerRightPadSpace(6);
	
	public AsciiPrefixerRightPadSpace(int nDigits) {
		this.nDigits = nDigits;
	}

	public AsciiPrefixerRightPadSpace() {
		this.nDigits = 0;
	}

	public int decodeLength(byte[] b, int offset) throws ISOException {
//		 TODO Auto-generated method stub
		int len = 0;
	    for (int i = 0; i < nDigits; i++)
	    {
	    	if(b[offset + i]!= 0x20){
	    		len = len * 10 + b[offset + i] - (byte) '0';
	    	}
	      
	    }
	    return len;
	}

	public void encodeLength(int length, byte[] b) throws ISOException {
		int n = length;
	    
	    // Write the string backwards - I don't know why I didn't see this at first.
	    int nLength=String.valueOf(length).length();
	    for (int i = nLength - 1; i >= 0; i--)
	    {
	      b[i] = (byte) (n % 10 + '0');
	      n /= 10;
	    }
	    
	    
	    if (n != 0)
	    {
	      throw new ISOException("invalid len " + length + ". Prefixing digits = "
	          + nDigits);
	    }
	    
	    int offset=nLength;
	    while(offset<nDigits){
	    	b[offset]=0x20;
	    	offset++;
	    	
	    }

	}

	public int getPackedLength() {
		// TODO Auto-generated method stub
		return nDigits;
	}
	
	

}
