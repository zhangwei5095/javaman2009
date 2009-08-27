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
 * ISOMsgFieldPackager is a packager able to pack compound ISOMsgs (one message
 * inside another one, and so on...)
 * 
 * @author apr@cs.com.uy
 * @version $Id: ISOMsgFieldPackager.java,v 1.13 2003/10/13 10:34:15 apr Exp $
 * @see com.nasoft.iso.packager.PostPackager
 */
public class ISOMsgFieldPackager extends ISOFieldPackager
{
  protected ISOPackager      msgPackager;
  protected ISOFieldPackager fieldPackager;

  /**
   * @param fieldPackager
   *          low level field packager
   * @param msgPackager
   *          ISOMsgField default packager
   */
  public ISOMsgFieldPackager(ISOFieldPackager fieldPackager,
      ISOPackager msgPackager)
  {
    super(fieldPackager.getLength(), fieldPackager.getDescription());
    this.msgPackager = msgPackager;
    this.fieldPackager = fieldPackager;
  }

  /**
   * @param c -
   *          a component
   * @return packed component
   * @exception ISOException
   */
  public byte[] pack(ISOComponent c) throws ISOException
  {
    if (c instanceof ISOMsg)
    {
      ISOMsg m = (ISOMsg) c;
      m.recalcBitMap();
      ISOBinaryField f = new ISOBinaryField(0, msgPackager.pack(m));
      return fieldPackager.pack(f);
    }
    return fieldPackager.pack(c);
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
    ISOBinaryField f = new ISOBinaryField(0);
    int consumed = fieldPackager.unpack(f, b, offset);
    if (c instanceof ISOMsg)
      msgPackager.unpack((ISOMsg) c, (byte[]) f.getValue());
    return consumed;
  }

  /**
   * @param c -
   *          the Component to unpack
   * @param in -
   *          input stream
   * @exception ISOException
   */
  public void unpack(ISOComponent c, InputStream in) throws IOException,
      ISOException
  {
    ISOBinaryField f = new ISOBinaryField(0);
    fieldPackager.unpack(f, in);
    if (c instanceof ISOMsg)
    {
      msgPackager.unpack((ISOMsg) c, (byte[]) f.getValue());
    }
  }

  public ISOComponent createComponent(int fieldNumber)
  {
    ISOMsg m = new ISOMsg(fieldNumber);
    m.setPackager(msgPackager);
    return m;
  }

  public int getMaxPackedLength()
  {
    return fieldPackager.getLength();
  }
}
