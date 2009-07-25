package com.nasoft.iso;

/**
 * ISOFieldPackager ASCII variable len BINARY
 * 
 * @author Alejandro
 * @version $Id: IFA_LLLLLLBINARY.java,v 1.1 2004/07/02 13:19:50 apr Exp $
 * @see ISOComponent
 */
public class IFA_LLLLLLBINARY extends ISOBinaryFieldPackager
{
  public IFA_LLLLLLBINARY()
  {
    super(LiteralBinaryInterpreter.INSTANCE, AsciiPrefixer.LLLLLL);
  }

  /**
   * @param len -
   *          field len
   * @param description
   *          symbolic descrption
   */
  public IFA_LLLLLLBINARY(int len, String description)
  {
    super(len, description, LiteralBinaryInterpreter.INSTANCE,
        AsciiPrefixer.LLLLLL);
    checkLength(len, 999999);
  }

  public void setLength(int len)
  {
    checkLength(len, 999999);
    super.setLength(len);
  }
}
