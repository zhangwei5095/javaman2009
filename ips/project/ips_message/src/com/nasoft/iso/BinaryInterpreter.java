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
 * This interface supports the encoding and decoding of binary data. Common
 * implementations are literal or no conversion, ASCII Hex, EBCDIC Hex and BCD.
 * 
 * @author joconnor
 * @version $Revision: 1.1 $ $Date: 2003/10/30 23:37:13 $
 */
public interface BinaryInterpreter
{
  /**
   * Converts the binary data into a different interpretation or coding.
   * Standard interpretations are ASCII Hex, EBCDIC Hex, BCD and LITERAL.
   * 
   * @param data
   *          The data to be interpreted.
   * @param b
   *          The byte array to write the interpreted data to.
   * @param offset
   *          The starting position in b.
   */
  void interpret(byte[] data, byte[] b, int offset);

  /**
   * Converts the raw byte array into a uninterpreted byte array. This reverses
   * the interpret method.
   * 
   * @param rawData
   *          The interpreted data.
   * @param offset
   *          The index in rawData to start uninterpreting at.
   * @param length
   *          The number of uninterpreted bytes to uninterpret. This number may
   *          be different from the number of raw bytes that are uninterpreted.
   * @return The uninterpreted data.
   */
  byte[] uninterpret(byte[] rawData, int offset, int length);

  /**
   * Returns the number of bytes required to interpret a byte array of length
   * nBytes.
   */
  int getPackedLength(int nBytes);
}