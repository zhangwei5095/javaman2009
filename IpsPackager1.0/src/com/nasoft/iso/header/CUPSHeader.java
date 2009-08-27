package com.nasoft.iso.header;

import com.nasoft.iso.ISOException;

/*
 *    ��               ����                        ���ȣ���λ��Byte��     ��ʼλ��
 *  Field1  ͷ���ȣ�Header Length��                    1             0
 *  Field2  ͷ��ʶ�Ͱ汾�ţ�Header Flag and Version�� 1             1
 *  Field3  �������ĳ��ȣ�Total Message Length)      4             2
 *  Field4  Ŀ��ID��Destination ID��                  11            6
 *  Field5  ԴID��Source ID��                          11            17
 *  Field6  ����ʹ�ã�Reserved for Use��               3             28
 *  Field7  ���κţ�Batch Number��                     1             31
 *  Field8  ������Ϣ��Transaction Information��       8             32
 *  Field9  �û���Ϣ��User Information��               1             40
 *  Field10 �ܾ��루Reject Code��                       5             41
 */

public class CUPSHeader extends BaseHeader // implements ISOHeader
{
  public CUPSHeader() throws ISOException
  {
    super();
    // TODO Auto-generated constructor stub
  }
  public CUPSHeader(byte[] h) throws ISOException
  {
    super(h);
    // TODO Auto-generated constructor stub
  }
  public void initDefine()
  {
    DEFINE.put("HeaderLength",  new BaseHeader.HeaderField(1,  "int",    null, "HeaderLength", 1));
    DEFINE.put("Version",       new BaseHeader.HeaderField(1,  "hex",    null, "Version",      2));
    DEFINE.put("TotalLength",   new BaseHeader.HeaderField(4,  "number", null, "TotalLength",  3));
    DEFINE.put("DestID",        new BaseHeader.HeaderField(11, "string", null, "DestID",       4));
    DEFINE.put("SourceID",      new BaseHeader.HeaderField(11, "string", null, "SourceID",     5));
    DEFINE.put("Reserved",      new BaseHeader.HeaderField(3,  "hex", null, "Reserved",     6));
    DEFINE.put("BatchNumber",   new BaseHeader.HeaderField(1,  "int",    null, "BatchNumber",  7));
    DEFINE.put("TransInfo",     new BaseHeader.HeaderField(8,  "string",    null, "TransInfo",    8));
    DEFINE.put("UserInfo",      new BaseHeader.HeaderField(1,  "hex",    null, "UserInfo",     9));
    DEFINE.put("RejectCode",    new BaseHeader.HeaderField(5,  "string", null, "RejectCode",   10));
    setHLen(46);
    
  }
}
