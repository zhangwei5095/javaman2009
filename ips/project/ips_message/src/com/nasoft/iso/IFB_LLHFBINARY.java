/*
 * Copyright (c) 2000 jPOS.org.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *    "This product includes software developed by the jPOS project 
 *    (http://www.jpos.org/)". Alternately, this acknowledgment may 
 *    appear in the software itself, if and wherever such third-party 
 *    acknowledgments normally appear.
 *
 * 4. The names "jPOS" and "jPOS.org" must not be used to endorse 
 *    or promote products derived from this software without prior 
 *    written permission. For written permission, please contact 
 *    license@jpos.org.
 *
 * 5. Products derived from this software may not be called "jPOS",
 *    nor may "jPOS" appear in their name, without prior written
 *    permission of the jPOS project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
 * IN NO EVENT SHALL THE JPOS PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the jPOS Project.  For more
 * information please see <http://www.jpos.org/>.
 */

package com.nasoft.iso;

import java.io.IOException;
import java.io.InputStream;

/**
 * ISOFieldPackager Binary Hex Fixed LLBINARY
 * 
 * @author apr@cs.com.uy
 * @version $Id: IFB_LLHFBINARY.java,v 1.8 2003/11/18 01:13:28 ninki Exp $
 * @see ISOComponent
 */
public class IFB_LLHFBINARY extends ISOFieldPackager
{
  public IFB_LLHFBINARY()
  {
    super();
  }

  /**
   * @param len -
   *          field len
   * @param description
   *          symbolic descrption
   */
  public IFB_LLHFBINARY(int len, String description)
  {
    super(len, description);
  }

  /**
   * @param c -
   *          a component
   * @return packed component
   * @exception ISOException
   */
  public byte[] pack(ISOComponent c) throws ISOException
  {
    int len = ((byte[]) c.getValue()).length;
    if (len > getLength() || len > 255)
    {
      throw new ISOException("invalid len " + len + " packing field "
          + c.getKey());
    }
    byte[] b = new byte[getLength() + 1];
    b[0] = (byte) len;
    System.arraycopy(c.getValue(), 0, b, 1, len);
    return b;
  }

  /**
   * @param c -
   *          the Component to unpack
   * @param b -
   *          binary image
   * @param offset -
   *          starting offset within the binary image
   * @return consumed bytes
   * @exception ISOException
   */
  public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException
  {
    int len = b[offset] & 0xFF;
    byte[] value = new byte[len];
    System.arraycopy(b, ++offset, value, 0, len);
    c.setValue(value);
    return getLength() + 1;
  }

  public void unpack(ISOComponent c, InputStream in) throws IOException,
      ISOException
  {
    byte[] b = readBytes(in, 1);
    int len = b[0] & 0xFF;
    c.setValue(readBytes(in, len));
    in.skip(getLength() - len);
  }

  public ISOComponent createComponent(int fieldNumber)
  {
    return new ISOBinaryField(fieldNumber);
  }

  public int getMaxPackedLength()
  {
    return getLength() + 1;
  }
}
