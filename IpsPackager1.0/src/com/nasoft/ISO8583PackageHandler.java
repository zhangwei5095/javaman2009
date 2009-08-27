package com.nasoft;

import java.util.HashMap;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.header.CUPSHeader;
import com.nasoft.iso.packager.XMLPackager;

public class ISO8583PackageHandler
{
  static private HashMap packagers = new HashMap();
//  static private HashMap headers = new HashMap();
  static public Object[] XMLToISO8583(String packagerName, boolean hasHeader, String headerXML, String bodyXML) 
    throws ISOException
  {    
    ISOMsg m = new ISOMsg();
    XMLPackager xmlPackager = new XMLPackager();
    m.setPackager(xmlPackager);
    m.unpack(bodyXML.getBytes());
    
    ISOPackager packager = getPackagerByName(packagerName);
    m.setPackager(packager);
    
    byte[] body  = m.pack();
  
    int len = body.length;
    BaseHeader header = getHeaderByName(packagerName, null);
    if(hasHeader)
    {    
      header.xmlPack(headerXML);
      len += header.getHLen();
      ISOUtil.int2Str(len, 4);
      /*
       * Set Total-Length field of the header. 
       * It's not a good solution, should be fixed later!
       */
      if(packagerName.equalsIgnoreCase("CUPS"))
      {
        header.setBtyesValueByName("TotalLength", ISOUtil.int2Str(len, 4).getBytes());
      }
    }

    byte[] data = new byte[len];
 
    int pos = 0;
    if(hasHeader)
    {
      System.arraycopy(header.pack(), 0, data, 0, header.getLength());
      pos += header.getLength();
    }
    System.arraycopy(body, 0, data, pos, body.length);
    
    String key = "";
    if(packagerName.equalsIgnoreCase("CUPS"))
    {
      /* Get Key Fields Óò7¡¢Óò11¡¢Óò32ºÍÓò33 */
      key = m.getString(7) == null ? "" : m.getString(7);
      key += m.getString(11) == null ? "" : m.getString(11);
      key += m.getString(33) == null ? "" : m.getString(33);
    }
    Object[] o = new Object[2];
    o[0]= key;
    o[1]= data;
    return o ;
  }
  
   /*
   * Convert ISO8583 to XML String.
   * <p>
   * @author yangfeng
   * @version 1.0
   */
  static public String ISO8583ToXML(String packagerName, boolean hasHeader, byte[] data) 
    throws ISOException
  {
    if("CUPS".equalsIgnoreCase(packagerName))
    {
      return cupsISO2XML(hasHeader, data);
    }
    return "";
  }
  
  public static String cupsISO2XML(boolean hasHeader, byte[] data)
    throws ISOException
  {
    String packagerName = "CUPS";
    XMLPackager xmlPackager = new XMLPackager();
    ISOMsg m = new ISOMsg();
    
    ISOPackager packager = getPackagerByName(packagerName);
    int hlen = 0;
    String headerXML = "";
  
    String retcode = "00000";
    if(hasHeader)
    {
      BaseHeader header = getHeaderByName(packagerName, data);
      headerXML = header.xmlUnpack();
      hlen = header.getHLen();
      String rejectCode = header.getStrByName("RejectCode");
      if(!rejectCode.equals("00000"))
      {
        retcode = rejectCode;
        /*Jump off the original header if rejected */
        hlen = hlen << 1;
      }
    }
    
    m.setPackager(packager);
    byte[] nhData = new byte[data.length - hlen];
    System.arraycopy(data, hlen, nhData, 0, nhData.length);
    m.unpack(nhData); 
    m.setPackager(xmlPackager);
    
    byte[] xmlBytes = m.pack(); 
    /* Get Key Fields Óò7¡¢Óò11¡¢Óò32ºÍÓò33 */
    String key = m.getString(7) == null ? "" : m.getString(7);
    key += m.getString(11) == null ? "" : m.getString(11);
    key += m.getString(33) == null ? "" : m.getString(33);
    
//    //deleted the <?xml version="1.0" encoding="UTF-8"?> in the headerXML
//    headerXML = headerXML.replaceAll("<\\?.*\\?>", "");
//    
    StringBuffer sb = new StringBuffer();
    sb.append("<root>");
    sb.append("<key>");
    sb.append(key);
    sb.append("</key>");
    sb.append("<isresp>");
    sb.append(isCupsResponse(m.getString(0)));
    sb.append("</isresp>");
    sb.append("<retcode>");
    sb.append(retcode);
    sb.append("</retcode>");
    sb.append(headerXML);
    sb.append(new String(xmlBytes));
    sb.append("</root>");

    return sb.toString();
  }
  
  static private HashMap ResponseMsgType = new HashMap();
  static  
  {
    ResponseMsgType.put("0110", "0110");
    ResponseMsgType.put("0210", "0210");
    ResponseMsgType.put("0230", "0230");
    ResponseMsgType.put("0130", "0130");
    ResponseMsgType.put("0430", "0430");
    ResponseMsgType.put("0432", "0432");
    ResponseMsgType.put("0530", "0530");
    ResponseMsgType.put("0532", "0532");
    ResponseMsgType.put("0630", "0630");
    ResponseMsgType.put("0810", "0810");
    ResponseMsgType.put("0830", "0830");
  }
  static private String isCupsResponse(String msgType)
  {
    if(ResponseMsgType.containsKey(msgType) )
    {
      return "true";
    }else
    {
      return "false";
    }
  }
  //  private static ISOHeader getHeaderByName(String name) 
//    throws ISOException
//  {
//
//    if(headers.get(name)==null)
//    {
//      synchronized(ISO8583PackageHandler.class)
//      {
//        try
//        {
//          
//          headers.put(name, createNewInstance("com.nasoft.iso.header."+name+"Header"));
//        } catch (Exception e)
//        {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//          throw new ISOException(e);
//        } 
//      }
//    }
//    return (ISOHeader)headers.get(name);
//  }

  private static BaseHeader getHeaderByName(String name, byte[] data) 
    throws ISOException
  {
    if("CUPS".equalsIgnoreCase(name))
    {
      return new CUPSHeader(data);
    }
    return null;
  }

  private static ISOPackager getPackagerByName(String packagerName) 
    throws ISOException
  {
    if(packagers.get(packagerName)==null)
    {
      synchronized(ISO8583PackageHandler.class)
      {
        try
        {
          
          packagers.put(packagerName, createNewInstance("com.nasoft.iso.packager.ISO8583"+packagerName+"Packager"));
        } catch (Exception e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
          throw new ISOException(e);
        } 
      }
    }
    return (ISOPackager)packagers.get(packagerName);
  }


  private static Object createNewInstance(String name) 
    throws ClassNotFoundException, InstantiationException, IllegalAccessException
  {
    Class c = Class.forName(name);
    return c.newInstance();
  }

}
