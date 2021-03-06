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

import java.util.Arrays;
import java.util.BitSet;
import java.util.StringTokenizer;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.nasoft.util.EBCaASCtransfer;

/**
 * varios functions needed to pack/unpack ISO-8583 fields
 * 
 * @author apr@cs.com.uy
 * @author Hani S. Kirollos
 * @author Alwyn Schoeman
 * @version $Id: ISOUtil.java,v 1.48 2004/08/10 11:55:12 apr Exp $
 * @see ISOComponent
 */

public class ISOUtil
{
  private static byte[] EBCDIC2ASCII = new byte[] { (byte) 0x00, (byte) 0x01,
      (byte) 0x02, (byte) 0x03, (byte) 0xDC, (byte) 0x09, (byte) 0xC3,
      (byte) 0x7F, (byte) 0xCA, (byte) 0xB2, (byte) 0xD5, (byte) 0x0B,
      (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F, (byte) 0x10,
      (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0xDB, (byte) 0xDA,
      (byte) 0x08, (byte) 0xC1, (byte) 0x18, (byte) 0x19, (byte) 0xC8,
      (byte) 0xF2, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
      (byte) 0xC4, (byte) 0xB3, (byte) 0xC0, (byte) 0xD9, (byte) 0xBF,
      (byte) 0x0A, (byte) 0x17, (byte) 0x1B, (byte) 0xB4, (byte) 0xC2,
      (byte) 0xC5, (byte) 0xB0, (byte) 0xB1, (byte) 0x05, (byte) 0x06,
      (byte) 0x07, (byte) 0xCD, (byte) 0xBA, (byte) 0x16, (byte) 0xBC,
      (byte) 0xBB, (byte) 0xC9, (byte) 0xCC, (byte) 0x04, (byte) 0xB9,
      (byte) 0xCB, (byte) 0xCE, (byte) 0xDF, (byte) 0x14, (byte) 0x15,
      (byte) 0xFE, (byte) 0x1A, (byte) 0x20, (byte) 0xA0, (byte) 0xE2,
      (byte) 0xE4, (byte) 0xE0, (byte) 0xE1, (byte) 0xE3, (byte) 0xE5,
      (byte) 0xE7, (byte) 0xF1, (byte) 0xA2, (byte) 0x2E, (byte) 0x3C,
      (byte) 0x28, (byte) 0x2B, (byte) 0x7C, (byte) 0x26, (byte) 0xE9,
      (byte) 0xEA, (byte) 0xEB, (byte) 0xE8, (byte) 0xED, (byte) 0xEE,
      (byte) 0xEF, (byte) 0xEC, (byte) 0xDF, (byte) 0x21, (byte) 0x24,
      (byte) 0x2A, (byte) 0x29, (byte) 0x3B, (byte) 0xAC, (byte) 0x2D,
      (byte) 0x2F, (byte) 0xC2, (byte) 0xC4, (byte) 0xC0, (byte) 0xC1,
      (byte) 0xC3, (byte) 0xC5, (byte) 0xC7, (byte) 0xD1, (byte) 0xA6,
      (byte) 0x2C, (byte) 0x25, (byte) 0x5F, (byte) 0x3E, (byte) 0x3F,
      (byte) 0xF8, (byte) 0xC9, (byte) 0xCA, (byte) 0xCB, (byte) 0xC8,
      (byte) 0xCD, (byte) 0xCE, (byte) 0xCF, (byte) 0xCC, (byte) 0x60,
      (byte) 0x3A, (byte) 0x23, (byte) 0x40, (byte) 0x27, (byte) 0x3D,
      (byte) 0x22, (byte) 0xD8, (byte) 0x61, (byte) 0x62, (byte) 0x63,
      (byte) 0x64, (byte) 0x65, (byte) 0x66, (byte) 0x67, (byte) 0x68,
      (byte) 0x69, (byte) 0xAB, (byte) 0xBB, (byte) 0xF0, (byte) 0xFD,
      (byte) 0xFE, (byte) 0xB1, (byte) 0xB0, (byte) 0x6A, (byte) 0x6B,
      (byte) 0x6C, (byte) 0x6D, (byte) 0x6E, (byte) 0x6F, (byte) 0x70,
      (byte) 0x71, (byte) 0x72, (byte) 0xAA, (byte) 0xBA, (byte) 0xE6,
      (byte) 0xB8, (byte) 0xC6, (byte) 0xA4, (byte) 0xB5, (byte) 0x7E,
      (byte) 0x73, (byte) 0x74, (byte) 0x75, (byte) 0x76, (byte) 0x77,
      (byte) 0x78, (byte) 0x79, (byte) 0x7A, (byte) 0xA1, (byte) 0xBF,
      (byte) 0xD0, (byte) 0xDD, (byte) 0xDE, (byte) 0xAE, (byte) 0x5E,
      (byte) 0xA3, (byte) 0xA5, (byte) 0xB7, (byte) 0xA9, (byte) 0xA7,
      (byte) 0xB6, (byte) 0xBC, (byte) 0xBD, (byte) 0xBE, (byte) 0x5B,
      (byte) 0x5D, (byte) 0xAF, (byte) 0xA8, (byte) 0xB4, (byte) 0xD7,
      (byte) 0x7B, (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x44,
      (byte) 0x45, (byte) 0x46, (byte) 0x47, (byte) 0x48, (byte) 0x49,
      (byte) 0xAD, (byte) 0xF4, (byte) 0xF6, (byte) 0xF2, (byte) 0xF3,
      (byte) 0xF5, (byte) 0x7D, (byte) 0x4A, (byte) 0x4B, (byte) 0x4C,
      (byte) 0x4D, (byte) 0x4E, (byte) 0x4F, (byte) 0x50, (byte) 0x51,
      (byte) 0x52, (byte) 0xB9, (byte) 0xFB, (byte) 0xFC, (byte) 0xF9,
      (byte) 0xFA, (byte) 0xFF, (byte) 0x5C, (byte) 0xF7, (byte) 0x53,
      (byte) 0x54, (byte) 0x55, (byte) 0x56, (byte) 0x57, (byte) 0x58,
      (byte) 0x59, (byte) 0x5A, (byte) 0xB2, (byte) 0xD4, (byte) 0xD6,
      (byte) 0xD2, (byte) 0xD3, (byte) 0xD5, (byte) 0x30, (byte) 0x31,
      (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35, (byte) 0x36,
      (byte) 0x37, (byte) 0x38, (byte) 0x39, (byte) 0xB3, (byte) 0xDB,
      (byte) 0xDC, (byte) 0xD9, (byte) 0xDA, (byte) 0x1A };
  private static byte[] ASCII2EBCDIC = new byte[] { (byte) 0x00, (byte) 0x01,
      (byte) 0x02, (byte) 0x03, (byte) 0x37, (byte) 0x2D, (byte) 0x2E,
      (byte) 0x2F, (byte) 0x16, (byte) 0x05, (byte) 0x25, (byte) 0x0B,
      (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F, (byte) 0x10,
      (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x3C, (byte) 0x3D,
      (byte) 0x32, (byte) 0x26, (byte) 0x18, (byte) 0x19, (byte) 0x3F,
      (byte) 0x27, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
      (byte) 0x40, (byte) 0x5A, (byte) 0x7F, (byte) 0x7B, (byte) 0x5B,
      (byte) 0x6C, (byte) 0x50, (byte) 0x7D, (byte) 0x4D, (byte) 0x5D,
      (byte) 0x5C, (byte) 0x4E, (byte) 0x6B, (byte) 0x60, (byte) 0x4B,
      (byte) 0x61, (byte) 0xF0, (byte) 0xF1, (byte) 0xF2, (byte) 0xF3,
      (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8,
      (byte) 0xF9, (byte) 0x7A, (byte) 0x5E, (byte) 0x4C, (byte) 0x7E,
      (byte) 0x6E, (byte) 0x6F, (byte) 0x7C, (byte) 0xC1, (byte) 0xC2,
      (byte) 0xC3, (byte) 0xC4, (byte) 0xC5, (byte) 0xC6, (byte) 0xC7,
      (byte) 0xC8, (byte) 0xC9, (byte) 0xD1, (byte) 0xD2, (byte) 0xD3,
      (byte) 0xD4, (byte) 0xD5, (byte) 0xD6, (byte) 0xD7, (byte) 0xD8,
      (byte) 0xD9, (byte) 0xE2, (byte) 0xE3, (byte) 0xE4, (byte) 0xE5,
      (byte) 0xE6, (byte) 0xE7, (byte) 0xE8, (byte) 0xE9, (byte) 0xBA,
      (byte) 0xE0, (byte) 0xBB, (byte) 0xB0, (byte) 0x6D, (byte) 0x79,
      (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85,
      (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x91,
      (byte) 0x92, (byte) 0x93, (byte) 0x94, (byte) 0x95, (byte) 0x96,
      (byte) 0x97, (byte) 0x98, (byte) 0x99, (byte) 0xA2, (byte) 0xA3,
      (byte) 0xA4, (byte) 0xA5, (byte) 0xA6, (byte) 0xA7, (byte) 0xA8,
      (byte) 0xA9, (byte) 0xC0, (byte) 0x4F, (byte) 0xD0, (byte) 0xA1,
      (byte) 0x07, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
      (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
      (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
      (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
      (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
      (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
      (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x41, (byte) 0xAA,
      (byte) 0x4A, (byte) 0xB1, (byte) 0x9F, (byte) 0xB2, (byte) 0x6A,
      (byte) 0xB5, (byte) 0xBD, (byte) 0xB4, (byte) 0x9A, (byte) 0x8A,
      (byte) 0x5F, (byte) 0xCA, (byte) 0xAF, (byte) 0xBC, (byte) 0x90,
      (byte) 0x8F, (byte) 0xEA, (byte) 0xFA, (byte) 0xBE, (byte) 0xA0,
      (byte) 0xB6, (byte) 0xB3, (byte) 0x9D, (byte) 0xDA, (byte) 0x9B,
      (byte) 0x8B, (byte) 0xB7, (byte) 0xB8, (byte) 0xB9, (byte) 0xAB,
      (byte) 0x64, (byte) 0x65, (byte) 0x62, (byte) 0x66, (byte) 0x63,
      (byte) 0x67, (byte) 0x9E, (byte) 0x68, (byte) 0x74, (byte) 0x71,
      (byte) 0x72, (byte) 0x73, (byte) 0x78, (byte) 0x75, (byte) 0x76,
      (byte) 0x77, (byte) 0xAC, (byte) 0x69, (byte) 0xED, (byte) 0xEE,
      (byte) 0xEB, (byte) 0xEF, (byte) 0xEC, (byte) 0xBF, (byte) 0x80,
      (byte) 0xFD, (byte) 0xFE, (byte) 0xFB, (byte) 0xFC, (byte) 0xAD,
      (byte) 0xAE, (byte) 0x59, (byte) 0x44, (byte) 0x45, (byte) 0x42,
      (byte) 0x46, (byte) 0x43, (byte) 0x47, (byte) 0x9C, (byte) 0x48,
      (byte) 0x54, (byte) 0x51, (byte) 0x52, (byte) 0x53, (byte) 0x58,
      (byte) 0x55, (byte) 0x56, (byte) 0x57, (byte) 0x8C, (byte) 0x49,
      (byte) 0xCD, (byte) 0xCE, (byte) 0xCB, (byte) 0xCF, (byte) 0xCC,
      (byte) 0xE1, (byte) 0x70, (byte) 0xDD, (byte) 0xDE, (byte) 0xDB,
      (byte) 0xDC, (byte) 0x8D, (byte) 0x8E, (byte) 0xDF };

  public static synchronized XMLReader genXmlReader()throws ISOException{
	  try {
		  XMLReader	reader = XMLReaderFactory.createXMLReader(System.getProperty(
						"sax.parser", "org.apache.xerces.parsers.SAXParser"));
				reader.setFeature("http://xml.org/sax/features/validation", false);
			return reader;
		} catch (Exception e) {
			throw new ISOException(e);
		}
  }
  
  
  public static String int2Str(int value, int len)throws ISOException
  {
		try{
			char[] cclen = new char[len];
		    
		    String strValue = String.valueOf(value);
		   
		    len = len>strValue.length()?strValue.length():len;
		    
		    int i;
		    for (i = 0; i < len; i++)
		    {
		      cclen[cclen.length - i - 1] = strValue.charAt(len - i - 1);
		    }

		    for(;i<cclen.length; i++)
		    {
		      cclen[cclen.length - i - 1] = '0';
		    }
		    return new String(cclen);
		}catch(Exception e){
			throw new ISOException(e);
		}
    
  }

  public static byte[] str2FixedLenBytes(String value, int n, byte pad)throws ISOException
  {
		try{
			byte[] b = new byte[n];
		    byte[] v = value.getBytes();
		    int len = n<v.length?n:v.length;
		    int i;
		    for(i=0; i<len; i++)
		    {
		      b[i]=v[i];
		    }
		    for(;i<n; i++)
		    {
		      b[i]=pad;
		    }
		    return b;
		}catch(Exception e){
			throw new ISOException(e);
		}
    
  }

  
  public static void main(String [] args){
//	  byte [] bb=ISOUtil.int2Bytes(923456789, 4);
//	  System.out.println(ISOUtil.byte2HexNoSpaceStr(bb, bb.length));
//	  int i=ISOUtil.bytes2Int(bb,0,bb.length);
//	  System.out.println(i);
	  try{
		  byte[] ee=asciiToEbcdic("汉字".getBytes("GBK"));
//		  System.out.println(ISOUtil.byte2HexStr(ee,ee.length));
		  byte[] aa=ebcdicToAsciiBytes(ee);
//		  System.out.println(new String (aa,"GBK")); 
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  
	  
  }
  
  public static byte[] int2Bytes(int value, int n)throws ISOException
  {
		try{
			 byte[] b = new byte[n];
			    int flag = 0xFF;
			    int len = n > 4 ? 4 : n;
			    for(int i=0 ; i<len; i++)
			    {
			      b[len-i-1] = (byte)(value & flag);
			      value >>= 8;
			    }
			    return b;
		}catch(Exception e){
			throw new ISOException(e);
		}
   
  }
  public static int bytes2Int(byte[] b, int offset, int num)throws ISOException
  {
		try{
			int len = 0;
		    byte bLoop; 
		    num = b.length < 4 ? b.length : 4;
		    for (int i = 0; i < num; i++)
		    {
		    	 bLoop = b[offset + i]; 
		    	 len= len |((bLoop & 0xFF) << (8*(num-i-1))); 
		    }
		    return len;
		}catch(Exception e){
			throw new ISOException(e);
		}
    
  }
  public static String bytes2IntStr(byte[] b, int offset, int num)throws ISOException
  {
		try{
			return String.valueOf(bytes2Int(b, offset, num));
		}catch(Exception e){
			throw new ISOException(e);
		}
    
  }
  public static int bytes2Int(byte[] b)throws ISOException
  {
		try{
			int len = 0;
		    byte bLoop; 
		    int offset = 0;
		    int num = b.length < 4 ? b.length : 4;
		    for (int i = 0; i < num; i++)
		    {
		    	 bLoop = b[offset + i]; 
		    	 len= len |((bLoop & 0xFF) << (8*(num-i-1))); 
		    }
		    return len;
		}catch(Exception e){
			throw new ISOException(e);
		}
    

  }
  public static String bytes2IntStr(byte[] b)throws ISOException
  {
    return String.valueOf(bytes2Int(b));
  }
  
  public static String ebcdicToAscii(byte[] e)throws ISOException
  {
    return new String(ebcdicToAsciiBytes(e, 0, e.length));
  }

  public static String ebcdicToAscii(byte[] e, int offset, int len)throws ISOException
  {
    return new String(ebcdicToAsciiBytes(e, offset, len));
  }

  public static byte[] ebcdicToAsciiBytes(byte[] e)throws ISOException
  {
    return ebcdicToAsciiBytes(e, 0, e.length);
  }

  public static byte[] ebcdicToAsciiBytes(byte[] e, int offset, int len)throws ISOException
  {
//    byte[] a = new byte[len];
//    for (int i = 0; i < len; i++)
//      a[i] = EBCDIC2ASCII[e[offset + i] & 0xFF];
	  
	  //dongbg: 修改 支持中文 2008-04-23
	  try{
		  byte[] ebcByte=new byte[len];
		  System.arraycopy(e, offset, ebcByte, 0, len);
		  byte [] a=EBCaASCtransfer.pub_base_EBCtoASC(ebcByte, len);
		  return a;
	  }catch(Exception ex){
		  throw new ISOException(ex);
	  }
  }

  public static byte[] asciiToEbcdic(String s)
  {
    return asciiToEbcdic(s.getBytes());
  }

  public static byte[] asciiToEbcdic(byte[] a)
  {
    byte[] e = new byte[a.length];
    for (int i = 0; i < a.length; i++)
      e[i] = ASCII2EBCDIC[a[i] & 0xFF];
    return e;
  }

  public static void asciiToEbcdic(String s, byte[] e, int offset)throws ISOException
  {
//    int len = s.length();
//    for (int i = 0; i < len; i++)
//      e[offset + i] = ASCII2EBCDIC[s.charAt(i) & 0xFF];
	  
	  //dongbg:修改支持中文 2008-04-23
	  try{
		  byte [] ebcByte=EBCaASCtransfer.pub_base_ASCtoEBC(s.getBytes(), s.getBytes().length);
		  System.arraycopy(ebcByte, 0, e, offset, ebcByte.length);
	  }catch(Exception ex){
		  throw new ISOException(ex);
	  }
	  
  }

  /**
   * pad to the left
   * 
   * @param s -
   *          original string
   * @param len -
   *          desired len
   * @param c -
   *          padding char
   * @return padded string
   */
  public static String padleft(String s, int len, char c) throws ISOException
  {
    s = s.trim();
    if (s.length() > len)
      throw new ISOException("invalid len " + s.length() + "/" + len);
    StringBuffer d = new StringBuffer(len);
    int fill = len - s.length();
    while (fill-- > 0)
      d.append(c);
    d.append(s);
    return d.toString();
  }

  /**
   * trim String (if not null)
   * 
   * @param s
   *          String to trim
   * @return String (may be null)
   */
  public static String trim(String s)
  {
    return s != null ? s.trim() : null;
  }

  /**
   * left pad with '0'
   * 
   * @param s -
   *          original string
   * @param len -
   *          desired len
   * @return zero padded string
   */
  public static String zeropad(String s, int len) throws ISOException
  {
    return padleft(s, len, '0');
  }

  /**
   * pads to the right
   * 
   * @param s -
   *          original string
   * @param len -
   *          desired len
   * @return space padded string
   */
  public static String strpad(String s, int len)
  {
    StringBuffer d = new StringBuffer(s);
    while (d.length() < len)
      d.append(' ');
    return d.toString();
  }

  public static String zeropadRight(String s, int len)
  {
    StringBuffer d = new StringBuffer(s);
    while (d.length() < len)
      d.append('0');
    return d.toString();
  }

  /**
   * converts to BCD
   * 
   * @param s -
   *          the number
   * @param padLeft -
   *          flag indicating left/right padding
   * @param d
   *          The byte array to copy into.
   * @param offset
   *          Where to start copying into.
   * @return BCD representation of the number
   */
  public static byte[] str2bcd(String s, boolean padLeft, byte[] d, int offset)
  {
    int len = s.length();
    int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
    for (int i = start; i < len + start; i++)
      d[offset + (i >> 1)] |= (s.charAt(i - start) - '0') << ((i & 1) == 1 ? 0
          : 4);
    return d;
  }

  /**
   * converts to BCD
   * 
   * @param s -
   *          the number
   * @param padLeft -
   *          flag indicating left/right padding
   * @return BCD representation of the number
   */
  public static byte[] str2bcd(String s, boolean padLeft)
  {
    int len = s.length();
    byte[] d = new byte[(len + 1) >> 1];
    return str2bcd(s, padLeft, d, 0);
  }

  /**
   * converts to BCD
   * 
   * @param s -
   *          the number
   * @param padLeft -
   *          flag indicating left/right padding
   * @param fill -
   *          fill value
   * @return BCD representation of the number
   */
  public static byte[] str2bcd(String s, boolean padLeft, byte fill)
  {
    int len = s.length();
    byte[] d = new byte[(len + 1) >> 1];
    Arrays.fill(d, fill);
    int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
    for (int i = start; i < len + start; i++)
      d[i >> 1] |= (s.charAt(i - start) - '0') << ((i & 1) == 1 ? 0 : 4);
    return d;
  }

  /**
   * converts a BCD representation of a number to a String
   * 
   * @param b -
   *          BCD representation
   * @param offset -
   *          starting offset
   * @param len -
   *          BCD field len
   * @param padLeft -
   *          was padLeft packed?
   * @return the String representation of the number
   */
  public static String bcd2str(byte[] b, int offset, int len, boolean padLeft)
  {
    StringBuffer d = new StringBuffer(len);
    int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
    for (int i = start; i < len + start; i++)
    {
      int shift = ((i & 1) == 1 ? 0 : 4);
      char c = Character.forDigit(((b[offset + (i >> 1)] >> shift) & 0x0F), 16);
      if (c == 'd')
        c = '=';
      d.append(Character.toUpperCase(c));
    }
    return d.toString();
  }

  /**
   * converts a byte array to hex string (suitable for dumps and ASCII packaging
   * of Binary fields
   * 
   * @param b -
   *          byte array
   * @return String representation
   */
  public static String hexString(byte[] b)
  {
    StringBuffer d = new StringBuffer(b.length * 2);
    for (int i = 0; i < b.length; i++)
    {
      char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
      char lo = Character.forDigit(b[i] & 0x0F, 16);
      d.append(Character.toUpperCase(hi));
      d.append(Character.toUpperCase(lo));
    }
    return d.toString();
  }

  /**
   * converts a byte array to printable characters
   * 
   * @param b -
   *          byte array
   * @return String representation
   */
  public static String dumpString(byte[] b)throws ISOException
  {
		try{
			StringBuffer d = new StringBuffer(b.length * 2);
		    for (int i = 0; i < b.length; i++)
		    {
		      char c = (char) b[i];
		      if (Character.isISOControl(c))
		      {
		        // TODO: complete list of control characters,
		        // use a String[] instead of this weird switch
		        switch (c)
		        {
		          case '\r':
		            d.append("{CR}");
		            break;
		          case '\n':
		            d.append("{LF}");
		            break;
		          case '\000':
		            d.append("{NULL}");
		            break;
		          case '\001':
		            d.append("{SOH}");
		            break;
		          case '\002':
		            d.append("{STX}");
		            break;
		          case '\003':
		            d.append("{ETX}");
		            break;
		          case '\004':
		            d.append("{EOT}");
		            break;
		          case '\005':
		            d.append("{ENQ}");
		            break;
		          case '\006':
		            d.append("{ACK}");
		            break;
		          case '\007':
		            d.append("{BEL}");
		            break;
		          case '\020':
		            d.append("{DLE}");
		            break;
		          case '\025':
		            d.append("{NAK}");
		            break;
		          case '\026':
		            d.append("{SYN}");
		            break;
		          case '\034':
		            d.append("{FS}");
		            break;
		          case '\036':
		            d.append("{RS}");
		            break;
		          default:
		            char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
		            char lo = Character.forDigit(b[i] & 0x0F, 16);
		            d.append('[');
		            d.append(Character.toUpperCase(hi));
		            d.append(Character.toUpperCase(lo));
		            d.append(']');
		            break;
		        }
		      } else
		        d.append(c);

		    }
		    return d.toString();
		}catch(Exception e){
			throw new ISOException(e);
		}
    
  }

  /**
   * converts a byte array to hex string (suitable for dumps and ASCII packaging
   * of Binary fields
   * 
   * @param b -
   *          byte array
   * @param offset -
   *          starting position
   * @param len
   * @return String representation
   */
  public static String hexString(byte[] b, int offset, int len)throws ISOException
  {
		try{
			 StringBuffer d = new StringBuffer(len * 2);
			    len += offset;
			    for (int i = offset; i < len; i++)
			    {
			      char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
			      char lo = Character.forDigit(b[i] & 0x0F, 16);
			      d.append(Character.toUpperCase(hi));
			      d.append(Character.toUpperCase(lo));
			    }
			    return d.toString();
		}catch(Exception e){
			throw new ISOException(e);
		}
   
  }

  /**
   * bit representation of a BitSet suitable for dumps and debugging
   * 
   * @param b -
   *          the BitSet
   * @return string representing the bits (i.e. 011010010...)
   */
  public static String bitSet2String(BitSet b)throws ISOException
  {
		try{
			int len = b.size();
		    len = (len > 128) ? 128 : len;
		    StringBuffer d = new StringBuffer(len);
		    for (int i = 0; i < len; i++)
		      d.append(b.get(i) ? '1' : '0');
		    return d.toString();
		}catch(Exception e){
			throw new ISOException(e);
		}
		
    
  }

  /**
   * converts a BitSet into a binary field used in pack routines
   * 
   * @param b -
   *          the BitSet
   * @return binary representation
   */
  public static byte[] bitSet2byte(BitSet b)throws ISOException
  {
	  try{
		  int len = (b.length() > 65) ? 128 : 64;
		    byte[] d = new byte[len >> 3];
		    for (int i = 0; i < len; i++)
		      if (b.get(i + 1))
		        d[i >> 3] |= (0x80 >> (i % 8));
		    if (len > 64)
		      d[0] |= 0x80;
		    return d;
	  }catch(Exception e){
			throw new ISOException(e);
		}
    
  }

  /**
   * Converts a binary representation of a Bitmap field into a Java BitSet
   * 
   * @param b -
   *          binary representation
   * @param offset -
   *          staring offset
   * @param bitZeroMeansExtended -
   *          true for ISO-8583
   * @return java BitSet object
   */
  public static BitSet byte2BitSet(byte[] b, int offset,
      boolean bitZeroMeansExtended)throws ISOException
  {
	  try{
		  int len = bitZeroMeansExtended ? ((b[offset] & 0x80) == 0x80 ? 128 : 64)
			        : 64;

			    BitSet bmap = new BitSet(len);
			    for (int i = 0; i < len; i++)
			      if (((b[offset + (i >> 3)]) & (0x80 >> (i % 8))) > 0)
			        bmap.set(i + 1);
			    return bmap;
	  }catch(Exception e){
			throw new ISOException(e);
		}
    
  }

  /**
   * Converts a binary representation of a Bitmap field into a Java BitSet
   * 
   * @param bmap -
   *          BitSet
   * @param b -
   *          hex representation
   * @param bitOffset -
   *          (i.e. 0 for primary bitmap, 64 for secondary)
   * @return java BitSet object
   */
  public static BitSet byte2BitSet(BitSet bmap, byte[] b, int bitOffset)throws ISOException
  {
		try{
			int len = b.length << 3;
		    for (int i = 0; i < len; i++)
		      if (((b[i >> 3]) & (0x80 >> (i % 8))) > 0)
		        bmap.set(bitOffset + i + 1);
		    return bmap;
		}catch(Exception e){
			throw new ISOException(e);
		}
		
    
  }

  /**
   * Converts an ASCII representation of a Bitmap field into a Java BitSet
   * 
   * @param b -
   *          hex representation
   * @param offset -
   *          staring offset
   * @param bitZeroMeansExtended -
   *          true for ISO-8583
   * @return java BitSet object
   */
  public static BitSet hex2BitSet(byte[] b, int offset,
      boolean bitZeroMeansExtended)throws ISOException
  {
		try{
			int len = bitZeroMeansExtended ? ((Character.digit((char) b[offset], 16) & 0x08) == 8 ? 128
			        : 64)
			        : 64;
			    BitSet bmap = new BitSet(len);
			    for (int i = 0; i < len; i++)
			    {
			      int digit = Character.digit((char) b[offset + (i >> 2)], 16);
			      if ((digit & (0x08 >> (i % 4))) > 0)
			        bmap.set(i + 1);
			    }
			    return bmap;
		}catch(Exception e){
			throw new ISOException(e);
		}
		
    
  }

  /**
   * Converts an ASCII representation of a Bitmap field into a Java BitSet
   * 
   * @param bmap -
   *          BitSet
   * @param b -
   *          hex representation
   * @param bitOffset -
   *          (i.e. 0 for primary bitmap, 64 for secondary)
   * @return java BitSet object
   */
  public static BitSet hex2BitSet(BitSet bmap, byte[] b, int bitOffset)
  {
    int len = b.length << 2;
    for (int i = 0; i < len; i++)
    {
      int digit = Character.digit((char) b[i >> 2], 16);
      if ((digit & (0x08 >> (i % 4))) > 0)
        bmap.set(bitOffset + i + 1);
    }
    return bmap;
  }

  /**
   * @param b
   *          source byte array
   * @param offset
   *          starting offset
   * @param len
   *          number of bytes in destination (processes len*2)
   * @return byte[len]
   */
  public static byte[] hex2byte(byte[] b, int offset, int len)throws ISOException
  {
	  try{
		  byte[] d = new byte[len];
		    len <<= 1;
		    len = len > (b.length << 1) ? (b.length << 1) : len;
		    for (int i = 0; i < len; i++)
		    {
		      int shift = i % 2 == 1 ? 0 : 4;
		      d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		    }
		    return d;
	  }catch(Exception e ){
		  String str = new String(b);
		  System.err.println("str="+str+"offset="+offset+"len="+len);
		  throw new ISOException(e);
	  }
   
  }

  /**
   * @param s
   *          source string (with Hex representation)
   * @return byte array
   */
  public static byte[] hex2byte(String s)throws ISOException
  {
	  try{
		  return hex2byte(s.getBytes(), 0, s.length() >> 1);
	  }catch(Exception e)
	  {
		  throw new ISOException(e);
	  }
    
  }

  /**
   * format double value
   * 
   * @param amount
   *          the amount
   * @param fieldLen
   *          the field len
   * @return a String of fieldLen characters (right justified)
   */
  public static String formatDouble(double d, int len)throws Exception
  {
    String prefix = Long.toString((long) d);
    String suffix = Integer.toString((int) ((Math.round(d * 100f)) % 100));
    try
    {
      prefix = ISOUtil.padleft(prefix, len - 3, ' ');
      suffix = ISOUtil.zeropad(suffix, 2);
    } catch (ISOException e)
    {
      e.printStackTrace();
    }
    return prefix + "." + suffix;
  }

  /**
   * prepare long value used as amount for display (implicit 2 decimals)
   * 
   * @param l
   *          value
   * @param len
   *          display len
   * @return formated field
   * @exception ISOException
   */
  public static String formatAmount(long l, int len) throws ISOException
  {
    String buf = Long.toString(l);
    if (l < 100)
      buf = zeropad(buf, 3);
    StringBuffer s = new StringBuffer(padleft(buf, len - 1, ' '));
    s.insert(len - 3, '.');
    return s.toString();
  }

  /**
   * XML normalizer
   * 
   * @param s
   *          source String
   * @param canonical
   *          true if we want to normalize \r and \n as well
   * @return normalized string suitable for XML Output
   */
  public static String normalize(String s, boolean canonical)throws ISOException
  {
		try{
			StringBuffer str = new StringBuffer();

		    int len = (s != null) ? s.length() : 0;
		    for (int i = 0; i < len; i++)
		    {
		      char ch = s.charAt(i);
		      switch (ch)
		      {
		        case '<':
		          str.append("&lt;");
		          break;
		        case '>':
		          str.append("&gt;");
		          break;
		        case '&':
		          str.append("&amp;");
		          break;
		        case '"':
		          str.append("&quot;");
		          break;
		        case '\r':
		        case '\n':
		          if (canonical)
		          {
		            str.append("&#");
		            str.append(Integer.toString(ch));
		            str.append(';');
		            break;
		          }
		          // else, default append char
		        default:
		          if (ch >= 0x20 && ch <= 0x7F)
		            str.append(ch);
		          else
		          {
		            str.append("&#");
		            str.append(Integer.toString(ch));
		            str.append(';');
//		        	  str.append(ch);
		          }
		      }
		    }
		    return (str.toString());
		}catch(Exception e){
			throw new ISOException(e);
		}
		
    
  }

  /**
   * XML normalizer (default canonical)
   * 
   * @param s
   *          source String
   * @return normalized string suitable for XML Output
   */
  public static String normalize(String s)throws ISOException
  {
    return normalize(s, true);
  }

  /**
   * Protects PAN, Track2, CVC (suitable for logs).
   * 
   * <pre>
   *  &quot;40000101010001&quot; is converted to &quot;400001____0001&quot;
   *  &quot;40000101010001=020128375&quot; is converted to &quot;400001____0001=0201_____&quot;
   *  &quot;123&quot; is converted to &quot;___&quot;
   * </pre>
   * 
   * @param s
   *          string to be protected
   * @return 'protected' String
   */
  public static String protect(String s)
  {
    StringBuffer sb = new StringBuffer();
    int len = s.length();
    int clear = len > 6 ? 6 : 0;
    int lastFourIndex = -1;
    if (clear > 0)
    {
      lastFourIndex = s.indexOf('=') - 4;
      if (lastFourIndex < 0)
      {
        lastFourIndex = s.indexOf('^') - 4;
        if (lastFourIndex < 0)
          lastFourIndex = len - 4;
      }
    }
    for (int i = 0; i < len; i++)
    {
      if (s.charAt(i) == '=')
        clear = 5;
      else if (s.charAt(i) == '^')
      {
        lastFourIndex = 0;
        clear = len - i;
      } else if (i == lastFourIndex)
        clear = 4;
      sb.append(clear-- > 0 ? s.charAt(i) : '_');
    }
    return sb.toString();
  }

  public static int[] toIntArray(String s)
  {
    StringTokenizer st = new StringTokenizer(s);
    int[] array = new int[st.countTokens()];
    for (int i = 0; st.hasMoreTokens(); i++)
      array[i] = Integer.parseInt(st.nextToken());
    return array;
  }

  /**
   * Bitwise XOR between corresponding bytes
   * 
   * @param op1
   *          byteArray1
   * @param op2
   *          byteArray2
   * @return an array of length = the smallest between op1 and op2
   */
  public static byte[] xor(byte[] op1, byte[] op2)
  {
    byte[] result = null;
    // Use the smallest array
    if (op2.length > op1.length)
    {
      result = new byte[op1.length];
    } else
    {
      result = new byte[op2.length];
    }
    for (int i = 0; i < result.length; i++)
    {
      result[i] = (byte) (op1[i] ^ op2[i]);
    }
    return result;
  }

  /**
   * Trims a byte[] to a certain length
   * 
   * @param array
   *          the byte[] to be trimmed
   * @param length
   *          the wanted length
   * @return the trimmed byte[]
   */
  public static byte[] trim(byte[] array, int length)
  {
    byte[] trimmedArray = new byte[length];
    System.arraycopy(array, 0, trimmedArray, 0, length);
    return trimmedArray;
  }

  /**
   * Concatenates two byte arrays (array1 and array2)
   * 
   * @param array1
   * @param beginIndex1
   * @param length1
   * @param array2
   * @param beginIndex2
   * @param length2
   * @return the concatenated array
   */
  public static byte[] concat(byte[] array1, int beginIndex1, int length1,
      byte[] array2, int beginIndex2, int length2)
  {
    byte[] concatArray = new byte[length1 + length2];
    System.arraycopy(array1, beginIndex1, concatArray, 0, length1);
    System.arraycopy(array2, beginIndex2, concatArray, length1, length2);
    return concatArray;
  }

  /**
   * Causes the currently executing thread to sleep (temporarily cease
   * execution) for the specified number of milliseconds. The thread does not
   * lose ownership of any monitors.
   * 
   * This is the same as Thread.sleep () without throwing InterruptedException
   * 
   * @param millis
   *          the length of time to sleep in milliseconds.
   */
  public static void sleep(long millis)
  {
    try
    {
      Thread.sleep(millis);
    } catch (InterruptedException e)
    {
    }
  }

  /**
   * Left unPad with '0'
   * 
   * @param s -
   *          original string
   * @return zero unPadded string
   */
  public static String zeroUnPad(String s)
  {
    return unPadLeft(s, '0');
  }

  /**
   * Right unPad with ' '
   * 
   * @param s -
   *          original string
   * @return blank unPadded string
   */
  public static String blankUnPad(String s)
  {
    return unPadRight(s, ' ');
  }

  /**
   * Unpad from right. In case the string to be returned is empty, the result is
   * c
   * 
   * @param s -
   *          original string
   * @param c -
   *          padding char
   * @return unPadded string.
   */
  public static String unPadRight(String s, char c)
  {
    if ((s.trim().length() == 0) && (c == ' '))
      return (new Character(c)).toString();
    else if ((s.trim().length() == 0))
      return s;
    s = s.trim();
    int end = s.length();
    while ((0 < end) && (s.charAt(end - 1) == c))
      end--;
    return (0 < end) ? s.substring(0, end) : s.substring(0, 1);
  }

  /**
   * Unpad from left. In case the string to be returned is empty, the result is
   * c
   * 
   * @param s -
   *          original string
   * @param c -
   *          padding char
   * @return unPadded string.
   */
  public static String unPadLeft(String s, char c)
  {
    if ((s.trim().length() == 0) && (c == ' '))
      return (new Character(c)).toString();
    else if ((s.trim().length() == 0))
      return s;
    s = s.trim();
    int fill = 0, end = s.length();
    while ((fill < end) && (s.charAt(fill) == c))
      fill++;
    return (fill < end) ? s.substring(fill, end) : s.substring(fill - 1, end);
  }

  /**
   * @return true if the string is zero-filled ( 0 char filled )
   */
  public static boolean isZero(String s)
  {
    int i = 0, len = s.length();
    while (i < len && (s.charAt(i) == '0'))
    {
      i++;
    }
    return (i >= len);
  }

  /**
   * @return true if the string is blank filled (space char filled)
   */
  public static boolean isBlank(String s)
  {
    return (s.trim().length() == 0);
  }

  /**
   * Return true if the string is alphanum.
   * <code>{letter digit (.) (_) (-) ( ) (?) }</code>
   * 
   */
  public static boolean isAlphaNumeric(String s)
  {
    int i = 0, len = s.length();
    while (i < len
        && (Character.isLetterOrDigit(s.charAt(i)) || s.charAt(i) == ' '
            || s.charAt(i) == '.' || s.charAt(i) == '-' || s.charAt(i) == '_')
        || s.charAt(i) == '?')
    {
      i++;
    }
    return (i >= len);
  }

  /**
   * Return true if the string represent a number in the specified radix. <br>
   * <br>
   */
  public static boolean isNumeric(String s, int radix)
  {
    int i = 0, len = s.length();
    while (i < len && Character.digit(s.charAt(i), radix) > -1)
    {
      i++;
    }
    return (i >= len);
  }

  /**
   * Converts a BitSet into an extended binary field used in pack routines. The
   * result is always in the extended format: (16 bytes of length) <br>
   * <br>
   * 
   * @param b
   *          the BitSet
   * @return binary representation
   */
  public static byte[] bitSet2extendedByte(BitSet b)
  {
    int len = 128;
    byte[] d = new byte[len >> 3];
    for (int i = 0; i < len; i++)
      if (b.get(i + 1))
        d[i >> 3] |= (0x80 >> (i % 8));
    d[0] |= 0x80;
    return d;
  }

  /**
   * Converts a String to an integer of base radix. <br>
   * <br>
   * String constraints are:
   * <li>Number must be less than 10 digits</li>
   * <li>Number must be positive</li>
   * 
   * @param s
   *          String representation of number
   * @param radix
   *          Number base to use
   * @return integer value of number
   * @throws NumberFormatException
   */
  public static int parseInt(String s, int radix) throws NumberFormatException
  {
    int length = s.length();
    if (length > 9)
      throw new NumberFormatException("Number can have maximum 9 digits");
    int result = 0;
    int index = 0;
    int digit = Character.digit(s.charAt(index++), radix);
    if (digit == -1)
      throw new NumberFormatException("String contains non-digit");
    result = digit;
    while (index < length)
    {
      result *= radix;
      digit = Character.digit(s.charAt(index++), radix);
      if (digit == -1)
        throw new NumberFormatException("String contains non-digit");
      result += digit;
    }
    return result;
  }

  /**
   * Converts a String to an integer of radix 10. <br>
   * <br>
   * String constraints are:
   * <li>Number must be less than 10 digits</li>
   * <li>Number must be positive</li>
   * 
   * @param s
   *          String representation of number
   * @return integer value of number
   * @throws NumberFormatException
   */
  public static int parseInt(String s) throws NumberFormatException
  {
    return parseInt(s, 10);
  }

  /**
   * Converts a character array to an integer of base radix. <br>
   * <br>
   * Array constraints are:
   * <li>Number must be less than 10 digits</li>
   * <li>Number must be positive</li>
   * 
   * @param cArray
   *          Character Array representation of number
   * @param radix
   *          Number base to use
   * @return integer value of number
   * @throws NumberFormatException
   */
  public static int parseInt(char[] cArray, int radix)
      throws NumberFormatException
  {
    int length = cArray.length;
    if (length > 9)
      throw new NumberFormatException("Number can have maximum 9 digits");
    int result = 0;
    int index = 0;
    int digit = Character.digit(cArray[index++], radix);
    if (digit == -1)
      throw new NumberFormatException("Char array contains non-digit");
    result = digit;
    while (index < length)
    {
      result *= radix;
      digit = Character.digit(cArray[index++], radix);
      if (digit == -1)
        throw new NumberFormatException("Char array contains non-digit");
      result += digit;
    }
    return result;
  }

  /**
   * Converts a character array to an integer of radix 10. <br>
   * <br>
   * Array constraints are:
   * <li>Number must be less than 10 digits</li>
   * <li>Number must be positive</li>
   * 
   * @param cArray
   *          Character Array representation of number
   * @return integer value of number
   * @throws NumberFormatException
   */
  public static int parseInt(char[] cArray) throws NumberFormatException
  {
    return parseInt(cArray, 10);
  }

  /**
   * Converts a byte array to an integer of base radix. <br>
   * <br>
   * Array constraints are:
   * <li>Number must be less than 10 digits</li>
   * <li>Number must be positive</li>
   * 
   * @param bArray
   *          Byte Array representation of number
   * @param radix
   *          Number base to use
   * @return integer value of number
   * @throws NumberFormatException
   */
  public static int parseInt(byte[] bArray, int radix)
      throws NumberFormatException
  {
    int length = bArray.length;
    if (length > 9)
      throw new NumberFormatException("Number can have maximum 9 digits");
    int result = 0;
    int index = 0;
    int digit = Character.digit((char) bArray[index++], radix);
    if (digit == -1)
      throw new NumberFormatException("Byte array contains non-digit");
    result = digit;
    while (index < length)
    {
      result *= radix;
      digit = Character.digit((char) bArray[index++], radix);
      if (digit == -1)
        throw new NumberFormatException("Byte array contains non-digit");
      result += digit;
    }
    return result;
  }

  /**
   * Converts a byte array to an integer of radix 10. <br>
   * <br>
   * Array constraints are:
   * <li>Number must be less than 10 digits</li>
   * <li>Number must be positive</li>
   * 
   * @param bArray
   *          Byte Array representation of number
   * @return integer value of number
   * @throws NumberFormatException
   */
  public static int parseInt(byte[] bArray) throws NumberFormatException
  {
    return parseInt(bArray, 10);
  }

  private static String hexOffset(int i)
  {
    i = (i >> 4) << 4;
    int w = i > 0xFFFF ? 8 : 4;
    try
    {
      return zeropad(Integer.toString(i, 16), w);
    } catch (ISOException e)
    {
      // should not happen
      return e.getMessage();
    }
  }

  /**
   * @param b
   *          a byte[] buffer
   * @return hexdump
   */
  public static String hexdump(byte[] b)
  {
    return hexdump(b, 0, b.length);
  }

  /**
   * @param b
   *          a byte[] buffer
   * @param offset
   *          starting offset
   * @param len
   *          the Length
   * @return hexdump
   */
  public static String hexdump(byte[] b, int offset, int len)
  {
    StringBuffer sb = new StringBuffer();
    StringBuffer hex = new StringBuffer();
    StringBuffer ascii = new StringBuffer();
    String sep = "  ";
    String lineSep = System.getProperty("line.separator");

    for (int i = offset; i < len; i++)
    {
      char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
      char lo = Character.forDigit(b[i] & 0x0F, 16);
      hex.append(Character.toUpperCase(hi));
      hex.append(Character.toUpperCase(lo));
      hex.append(' ');
      char c = (char) b[i];
      ascii.append((c >= 32 && c < 127) ? c : '.');

      int j = i % 16;
      switch (j)
      {
        case 7:
          hex.append(' ');
          break;
        case 15:
          sb.append(hexOffset(i));
          sb.append(sep);
          sb.append(hex.toString());
          sb.append(' ');
          sb.append(ascii.toString());
          sb.append(lineSep);
          hex = new StringBuffer();
          ascii = new StringBuffer();
          break;
      }
    }
    if (hex.length() > 0)
    {
      while (hex.length() < 49)
        hex.append(' ');

      sb.append(hexOffset(b.length));
      sb.append(sep);
      sb.append(hex.toString());
      sb.append(' ');
      sb.append(ascii.toString());
      sb.append(lineSep);
    }
    return sb.toString();
  }

  public static  String byte2HexStr(byte[] byteArr, int len) {
		StringBuffer hexStr = new StringBuffer(len);
		String tmp;
		int tmpInt = 0;
		for (int i = 0; i < len; i++) {
			tmpInt = byteArr[i];
			tmpInt &= 0x000000ff;
			tmp = Integer.toHexString(tmpInt);
			if (tmp.length() == 1) {
				hexStr.append(" 0").append(tmp);
			} else {
				hexStr.append(" ").append(tmp);
			}
			if (((i + 1) % 16) == 0)
				hexStr.append("\n");
		}
		hexStr.append("\n");
		return hexStr.toString();
	}
  
  public static  String byte2HexNoSpaceStr(byte[] byteArr, int len) {
		StringBuffer hexStr = new StringBuffer(len);
		String tmp;
		int tmpInt = 0;
		for (int i = 0; i < len; i++) {
			tmpInt = byteArr[i];
			tmpInt &= 0x000000ff;
			tmp = Integer.toHexString(tmpInt);
			if (tmp.length() == 1) {
				hexStr.append("0").append(tmp);
			} else {
				hexStr.append("").append(tmp);
			}
			if (((i + 1) % 16) == 0)
				hexStr.append("\n");
		}
		return hexStr.toString();
	}
  
  private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		// System.out.println(ret<<8);
		return ret;
	}

	public static  byte[] hexStr2byte(String src) {
		StringBuilder sb=new StringBuilder();
		StringBuilder sb1=new StringBuilder();
		if(src.indexOf(' ')==-1){
			
			for (int i=0;i<src.length();i++){
				sb1.append(src.charAt(i));
				if(i%2==0 && i!=0){
					sb1.append(' ');
				}
			}
		}
		src=sb1.toString();
		for(int i=0;i<src.length();i++){
			if(src.charAt(i)!= ' '){
				sb.append(src.charAt(i));
			}
		}
		String hexStr=sb.toString();
		int len=hexStr.length()/2;
		byte[] ret = new byte[len];
		byte[] tmp = hexStr.getBytes();
		for (int i = 0; i <len ; ++i) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	} 

}
