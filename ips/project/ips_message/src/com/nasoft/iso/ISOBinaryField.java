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

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;

import com.nasoft.iso.packager.XMLPackager;


/**
 * implements <b>Leaf</b> for binary fields
 * 
 * See the <a href="API_users_guide.html">API User's Guide</a> for details.
 * 
 * @author apr@cs.com.uy
 * @version $Id: ISOBinaryField.java,v 1.16 2003/10/13 10:34:15 apr Exp $
 * @see ISOComponent
 */
public class ISOBinaryField extends ISOComponent implements Cloneable,
    Externalizable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 2643723327684658113L;
protected int    fieldNumber;
  protected byte[] value;

  /**
   * No args constructor <font size="-1">(required by Externalizable support on
   * ISOMsg)</font>
   */
  public ISOBinaryField()
  {
    fieldNumber = -1;
  }

  /**
   * @param n -
   *          the FieldNumber
   */
  public ISOBinaryField(int n)
  {
    fieldNumber = n;
  }

  /**
   * @param n -
   *          fieldNumber
   * @param v -
   *          fieldValue
   */
  public ISOBinaryField(int n, byte[] v)
  {
    fieldNumber = n;
    value = v;
  }

  /**
   * @param n -
   *          fieldNumber
   * @param v -
   *          fieldValue
   * @param offset -
   *          starting offset
   * @param len -
   *          field length
   */
  public ISOBinaryField(int n, byte[] v, int offset, int len)
  {
    byte[] b = new byte[len];
    System.arraycopy(v, offset, b, 0, len);
    fieldNumber = n;
    value = b;
  }

  /**
   * changes this Component field number<br>
   * Use with care, this method does not change any reference held by a
   * Composite.
   * 
   * @param fieldNumber
   *          new field number
   */
  public void setFieldNumber(int fieldNumber)
  {
    this.fieldNumber = fieldNumber;
  }

  /**
   * not available on Leaf - always throw ISOException
   * 
   * @exception ISOException
   */
  public byte[] pack() throws ISOException
  {
    throw new ISOException("Not available on Leaf");
  }

  /**
   * not available on Leaf - always throw ISOException
   * 
   * @exception ISOException
   */
  public int unpack(byte[] b) throws ISOException
  {
    throw new ISOException("Not available on Leaf");
  }

  /**
   * not available on Leaf - always throw ISOException
   * 
   * @exception ISOException
   */
  public void unpack(InputStream in) throws ISOException
  {
    throw new ISOException("Not available on Leaf");
  }

  /**
   * @return Object representing this field number
   */
  public Object getKey()
  {
    return new Integer(fieldNumber);
  }

  /**
   * @return Object representing this field value
   */
  public Object getValue()
  {
    return value;
  }

  /**
   * @param obj -
   *          Object representing this field value
   * @exception ISOException
   */
  public void setValue(Object obj) throws ISOException
  {
    if (obj instanceof String)
      value = ((String) obj).getBytes();
    else
      value = (byte[]) obj;
  }

  /**
   * @return byte[] representing this field
   */
  public byte[] getBytes()
  {
    return value;
  }

  /**
   * dump this field to PrintStream. The output is sorta XML, intended to be
   * easily parsed.
   * 
   * @param p -
   *          print stream
   * @param indent -
   *          optional indent string
   */
  public void dump(PrintStream p, String indent)
  {
    p.println(indent + "<" + XMLPackager.ISOFIELD_TAG + " "
        + XMLPackager.ID_ATTR + "=\"" + fieldNumber + "\" "
        + XMLPackager.VALUE_ATTR + "=\"" + this.toString() + "\" "
        + XMLPackager.TYPE_ATTR + "=\"" + XMLPackager.TYPE_BINARY + "\"/>");
  }

  public String toString()
  {
    return ISOUtil.hexString(value);
  }

  public void writeExternal(ObjectOutput out) throws IOException
  {
    out.writeShort(fieldNumber);
    out.writeShort(value.length);
    out.write(value);
  }

  public void readExternal(ObjectInput in) throws IOException,
      ClassNotFoundException
  {
    fieldNumber = in.readShort();
    value = new byte[in.readShort()];
    in.readFully(value);
  }
}
