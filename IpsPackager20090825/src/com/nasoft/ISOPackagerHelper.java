package com.nasoft;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.ATMPHeader;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.header.CUPSHeader;
import com.nasoft.iso.header.HOSTHeader;
import com.nasoft.iso.packager.XMLPackager;
import com.nasoft.xml.IsoMsg2PlatMsg;
import com.nasoft.xml.PlatMsg2IsoMsg;

public class ISOPackagerHelper
{
  static private HashMap packagers = new HashMap();

  static public Object[] platXml2IsoBin(String name, boolean hasHeader,
      String platXml) throws ISOException, UnsupportedEncodingException
  {
    PlatMsg2IsoMsg convert = new PlatMsg2IsoMsg();
    BaseHeader header = null;
    if (hasHeader)
    {
      header = getHeaderByName(name, null);
      convert.setHeader(header);
    }
    String xml = convert.transform(platXml);
//    System.out.println(xml);
    int start = xml.indexOf("<isomsg>");
    int end = xml.indexOf("</isomsg>") + 9;

    String bodyXML = xml.substring(start, end);
    String headerXML = "";
    if (hasHeader)
    {
      start = xml.indexOf("<header>");
      end = xml.indexOf("</header>") + 9;
      headerXML = xml.substring(start, end);
    }
    ISOMsg m = new ISOMsg();
    XMLPackager xmlPackager = new XMLPackager();
    m.setPackager(xmlPackager);
  //  bodyXML="<?xml version=\"1.0\"  encoding=\"GB2312\"?>"+bodyXML;
  //  System.out.println(ISOUtil.hexdump(bodyXML.getBytes()));
    m.unpack(bodyXML.getBytes("utf-8"));

    ISOPackager packager = getPackagerByName(name);
    m.setPackager(packager);

    byte[] body = m.pack();

    int len = body.length;

    if (hasHeader)
    {
     // System.out.println("AAAAAAAAAAAAAA:"+headerXML);
      header.xmlPack(headerXML);
      len += header.getHLen();
      ISOUtil.int2Str(len, 4);
      /*
       * Set Total-Length field of the header. It's not a good solution, should
       * be fixed later!
       */
      if (name.equalsIgnoreCase("CUPS"))
      {
        header.setBtyesValueByName("TotalLength", ISOUtil.int2Str(len, 4)
            .getBytes());
      }
    }

    byte[] data = new byte[len];

    int pos = 0;
    if (hasHeader)
    {
      System.arraycopy(header.pack(), 0, data, 0, header.getLength());
      pos += header.getLength();
    }
    System.arraycopy(body, 0, data, pos, body.length);

    String key = "";
    if (name.equalsIgnoreCase("CUPS"))
    {
      /* Get Key Fields Óò7¡¢Óò11¡¢Óò32ºÍÓò33 */
      key = m.getString(7) == null ? "" : m.getString(7);
      key += m.getString(11) == null ? "" : m.getString(11);
      key += m.getString(33) == null ? "" : m.getString(33);
    }
    Object[] o = new Object[2];
    o[0] = key;
    o[1] = data;
    return o;
  }

  static public String isoBin2PlatXml(String name, boolean hasHeader,
      byte[] data) throws ISOException, UnsupportedEncodingException
  {
    IsoMsg2PlatMsg convert = new IsoMsg2PlatMsg();
    if (hasHeader)
    {
      convert.setHeader(getHeaderByName(name, null));
    }
    if ("CUPS".equalsIgnoreCase(name))
    {
      String isoXml = cupsISO2XML(hasHeader, data);
      //System.out.println(isoXml);
      return convert.transform(isoXml);
    } else if ("HOST".equalsIgnoreCase(name))
    {
      return convert.transform(commonISO2XML(name, hasHeader, data));
    } else if ("ATMP".equalsIgnoreCase(name))
    {
      return convert.transform(commonISO2XML(name, hasHeader, data));
    }
    return null;
  }

  /*
   * Convert ISO8583 to XML String. <p> @author yangfeng
   * 
   * @version 1.0
   */
  static public String ISO8583ToXML(String packagerName, boolean hasHeader,
      byte[] data) throws ISOException
  {
    if ("CUPS".equalsIgnoreCase(packagerName))
    {
      return cupsISO2XML(hasHeader, data);
    }
    return "";
  }
  public static String commonISO2XML(String name, boolean hasHeader, byte[] data)
      throws ISOException
  {
    String packagerName = name;
    XMLPackager xmlPackager = new XMLPackager();
    ISOMsg m = new ISOMsg();

    ISOPackager packager = getPackagerByName(packagerName);
    int hlen = 0;
    String headerXML = "";
    if (hasHeader)
    {
      BaseHeader header = getHeaderByName(packagerName, data);
      headerXML = header.xmlUnpack();
      hlen = header.getHLen();
    }

    m.setPackager(packager);
    byte[] nhData = new byte[data.length - hlen];
    System.arraycopy(data, hlen, nhData, 0, nhData.length);
    m.unpack(nhData);
    m.setPackager(xmlPackager);

    byte[] xmlBytes = m.pack();
    String key = "";
    if("HOST".equalsIgnoreCase(name))
    {
      /* Get Host Key Fileds BF5+BF12+BF13+BF33 */
      key = m.getString(5) == null ? "" : m.getString(11).trim();
      key += m.getString(12) == null ? "" : m.getString(12).trim();
      key += m.getString(13) == null ? "" : m.getString(13).trim();
      key += m.getString(33) == null ? "" : m.getString(33).trim();
    }else if("ATMP".equalsIgnoreCase(name))
    {
      /* Get Host Key Fileds 11, 12 , 13, 33 */
      /* Get Key Fields Óò7¡¢Óò11¡¢Óò32ºÍÓò33 */
      key = m.getString(11) == null ? "" : m.getString(11);
      key += m.getString(12) == null ? "" : m.getString(12);
      key += m.getString(13) == null ? "" : m.getString(13);
      key += m.getString(33) == null ? "" : m.getString(33);
    }
    StringBuffer sb = new StringBuffer();
    sb.append("<root>");
    sb.append("<key>");
    sb.append(key);
    sb.append("</key>");
    sb.append("<retcode>");
    sb.append("0");
    sb.append("</retcode>");
    sb.append(headerXML);
    sb.append(new String(xmlBytes));
    sb.append("</root>");

    return sb.toString();
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

    String retcode = "0";
    if (hasHeader)
    {
      BaseHeader header = getHeaderByName(packagerName, data);
      headerXML = header.xmlUnpack();
      hlen = header.getHLen();
      String rejectCode = header.getStrByName("RejectCode");
      if (!rejectCode.equals("00000"))
      {
        // Rejected by CUPS
        retcode = "-3";
        /* Jump off the original header if rejected */
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
    key += m.getString(32) == null ? "" : m.getString(32);

    // //deleted the <?xml version="1.0" encoding="UTF-8"?> in the headerXML
    // headerXML = headerXML.replaceAll("<\\?.*\\?>", "");
    //  
    StringBuffer sb = new StringBuffer();
    sb.append("<root>");
    sb.append("<isrsp>");
    sb.append(isCupsResponse(retcode, m.getString(0)));
    sb.append("</isrsp>");
    sb.append("<key>");
    sb.append(key);
    sb.append("</key>");
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

  static private String isCupsResponse(String rejectCode, String msgType)
  {
    if (ResponseMsgType.containsKey(msgType) || !"0".equals(rejectCode))
    {
      return "1";
    } else
    {
      return "0";
    }
  }


  private static BaseHeader getHeaderByName(String name, byte[] data)
      throws ISOException
  {
    if ("CUPS".equalsIgnoreCase(name))
    {
      return new CUPSHeader(data);
    }else if("HOST".equalsIgnoreCase(name))
    {
      return new HOSTHeader(data);
    }else if("ATMP".equalsIgnoreCase(name))
    {
      return new ATMPHeader(data);
    }
    return null;
  }

  private static ISOPackager getPackagerByName(String packagerName)
      throws ISOException
  {
    if (packagers.get(packagerName) == null)
    {
      synchronized (ISO8583PackageHandler.class)
      {
        try
        {

          packagers.put(packagerName,
              createNewInstance("com.nasoft.iso.packager.ISO8583"
                  + packagerName + "Packager"));
        } catch (Exception e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
          throw new ISOException(e);
        }
      }
    }
    return (ISOPackager) packagers.get(packagerName);
  }

  private static Object createNewInstance(String name)
      throws ClassNotFoundException, InstantiationException,
      IllegalAccessException
  {
    Class c = Class.forName(name);
    return c.newInstance();
  }

  
  public static void main(String[] args) 
    throws IOException, ISOException
  {
  //  cc fin = new FileInputStream("c:\\output.bin");//"F:/workspace/jPos/src/com/nasoft/test/iso/ISO8583CUPSWithHeader.bin");
//    c = new byte[(int)fin.available()];
//    fin.read(data);

//    System.out.println(isoBin2PlatXml("CUPS", true, data));
    
	 FileInputStream fin = new FileInputStream("e:/TransLog_OTB_20070927.log");//("F:/workspace/jPos/src/com/nasoft/test/iso/CupsPlatXmlWithHeader.xml");
	 byte[] data = new byte[(int)fin.available()];
     fin.read(data);
//     System.out.println(platXml2IsoBin("CUPS", true, new String(data)));
   // System.out.println(ISOUtil.hexdump((byte[])platXml2IsoBin("HOST", true, new String(data))[1]));
  }
}
