package com.nasoft.iso.header;

import com.nasoft.iso.ISOException;


/*
 *    ��               ����                        ���ȣ���λ��Byte��     ��ʼλ��
 *   Field1      ����������                           2                0
 *   Field2      ����������                           6                2
 *   Field3      ������ʶ��                           2                8
 */
public class ATMPHeader extends BaseHeader
{
  public ATMPHeader() throws ISOException
  {
    super();
    // TODO Auto-generated constructor stub
  }
  public ATMPHeader(byte[] h) throws ISOException
  {
    super(h);
    // TODO Auto-generated constructor stub
  }
  public static final int LENGTH = 10;

  public void initDefine()
  {
//    DEFINE.put("POTYC",  new BaseHeader.HeaderField(2,  "string",    null, "POTYC", 1));
//    DEFINE.put("TXTYC",  new BaseHeader.HeaderField(6,  "string",    null, "TXTYC", 2));
//    DEFINE.put("TYTYC",  new BaseHeader.HeaderField(2,  "string", null, "TYTYC",  3));
	  
	  DEFINE.put("BODYL",  new BaseHeader.HeaderField(4,  "number",    null, "BODYL", 1));
	  DEFINE.put("MATYC",  new BaseHeader.HeaderField(2,  "string",    null, "MATYC", 2));
	  DEFINE.put("TXTYC",  new BaseHeader.HeaderField(6,  "string", null, "TXTYC",  3));
	  DEFINE.put("TYTYC",  new BaseHeader.HeaderField(2,  "string", null, "TYTYC",  4));
    
    setHLen(14);
  }
}
