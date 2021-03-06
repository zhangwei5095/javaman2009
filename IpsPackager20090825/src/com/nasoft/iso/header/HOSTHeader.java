package com.nasoft.iso.header;

import com.nasoft.iso.ISOException;


/*
 *    域               域名                        长度（单位：Byte）     起始位置
 *   Field1      报文类型码                           2                0
 *   Field2      交易类型码                           6                2
 *   Field3      渠道标识码                           2                8
 */
public class HOSTHeader extends BaseHeader
{
  public HOSTHeader() throws ISOException
  {
    super();
    // TODO Auto-generated constructor stub
  }
  public HOSTHeader(byte[] h) throws ISOException
  {
    super(h);
    // TODO Auto-generated constructor stub
  }
  public static final int LENGTH = 10;

  public void initDefine()
  {
    DEFINE.put("POTYC",  new BaseHeader.HeaderField(2,  "string",    null, "POTYC", 1));
    DEFINE.put("TXTYC",  new BaseHeader.HeaderField(6,  "string",    null, "TXTYC", 2));
    DEFINE.put("TYTYC",  new BaseHeader.HeaderField(2,  "string", null, "TYTYC",  3));
    
    setHLen(10);
  }
}
