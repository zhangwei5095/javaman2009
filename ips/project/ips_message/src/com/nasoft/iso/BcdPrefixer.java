/*
 * Copyright (c) 2000 jPOS.org. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *  3. The end-user documentation included with the redistribution, if any,
 * must include the following acknowledgment: "This product includes software
 * developed by the jPOS project (http://www.jpos.org/)". Alternately, this
 * acknowledgment may appear in the software itself, if and wherever such
 * third-party acknowledgments normally appear.
 *  4. The names "jPOS" and "jPOS.org" must not be used to endorse or promote
 * products derived from this software without prior written permission. For
 * written permission, please contact license@jpos.org.
 *  5. Products derived from this software may not be called "jPOS", nor may
 * "jPOS" appear in their name, without prior written permission of the jPOS
 * project.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE JPOS
 * PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the jPOS Project. For more information please see
 * <http://www.jpos.org/> .
 */

package com.nasoft.iso;

/**
 * BcdPrefixer constructs a prefix storing the length in BCD.
 * 
 * @author joconnor
 * @version $Revision: 1.3 $ $Date: 2003/11/05 09:00:21 $
 */
public class BcdPrefixer implements Prefixer
{
  /**
   * A length prefixer for upto 9 chars. The length is encoded with 1 ASCII char
   * representing 1 decimal digit.
   */
  public static final BcdPrefixer L     = new BcdPrefixer(1);
  /**
   * A length prefixer for upto 99 chars. The length is encoded with 2 ASCII
   * chars representing 2 decimal digits.
   */
  public static final BcdPrefixer LL    = new BcdPrefixer(2);
  /**
   * A length prefixer for upto 999 chars. The length is encoded with 3 ASCII
   * chars representing 3 decimal digits.
   */
  public static final BcdPrefixer LLL   = new BcdPrefixer(3);
  /**
   * A length prefixer for upto 9999 chars. The length is encoded with 4 ASCII
   * chars representing 4 decimal digits.
   */
  public static final BcdPrefixer LLLL  = new BcdPrefixer(4);
  /**
   * A length prefixer for upto 99999 chars. The length is encoded with 5 ASCII
   * chars representing 5 decimal digits.
   */
  public static final BcdPrefixer LLLLL = new BcdPrefixer(5);

  /** The number of digits allowed to express the length */
  protected int                     nDigits;

  public BcdPrefixer(int nDigits)
  {
    this.nDigits = nDigits;
  }

  public BcdPrefixer(){
	  
  }
  /*
   * (non-Javadoc)
   * 
   * @see org.jpos.iso.Prefixer#encodeLength(int, byte[])
   */
  public void encodeLength(int length, byte[] b)throws ISOException
  {
    for (int i = getPackedLength() - 1; i >= 0; i--)
    {
      int twoDigits = length % 100;
      length /= 100;
      b[i] = (byte) (((twoDigits / 10) << 4) + twoDigits % 10);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jpos.iso.Prefixer#decodeLength(byte[], int)
   */
  public int decodeLength(byte[] b, int offset)throws ISOException
  {
    int len = 0;
    for (int i = 0; i < (nDigits + 1) / 2; i++)
    {
      len = 100 * len + ((b[offset + i] & 0xF0) >> 4) * 10
          + ((b[offset + i] & 0x0F));
    }
    return len;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jpos.iso.Prefixer#getLengthInBytes()
   */
  public int getPackedLength()
  {
    return (nDigits + 1) / 2;
  }
}