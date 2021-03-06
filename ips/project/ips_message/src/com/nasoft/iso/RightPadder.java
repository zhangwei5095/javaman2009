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
 * Implements the Padder interface for padding strings and byte arrays on the
 * Right.
 * 
 * @author joconnor
 * @version $Revision: 1.2 $ $Date: 2003/11/05 09:00:22 $
 */
public class RightPadder implements Padder
{
  /**
   * A padder for padding spaces on the right. This is very common in alphabetic
   * fields.
   */
  public static final RightPadder SPACE_PADDER = new RightPadder(' ');

  private char                    pad;

  /**
   * Creates a Right Padder with a specific pad character.
   * 
   * @param pad
   *          The padding character. For binary padders, the pad character is
   *          truncated to lower order byte.
   */
  public RightPadder(char pad)
  {
    this.pad = pad;
  }

  /**
   * @see com.nasoft.iso.Padder#pad(java.lang.String, int, char)
   */
  public String pad(String data, int maxLength) throws ISOException
  {
    StringBuffer padded = new StringBuffer(maxLength);
    int len = data.getBytes().length;
    if (len > maxLength)
    {
      throw new ISOException("Data is too long. Max = " + maxLength);
    } else
    {
      padded.append(data);
      for (int i = maxLength - len; i > 0; i--)
      {
        padded.append(pad);
      }
    }
    return padded.toString();
  }

  /**
   * @see com.nasoft.iso.Padder#unpad(java.lang.String, char)
   */
  public String unpad(String paddedData)
  {
    int len = paddedData.length();
    for (int i = len; i > 0; i--)
    {
      if (paddedData.charAt(i - 1) != pad)
      {
        return paddedData.substring(0, i);
      }
    }
    return "";
  }
}