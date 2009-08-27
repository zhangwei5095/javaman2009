package com.nasoft.xml;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.packager.XMLPackager;

public class IsoMsg2PlatMsg extends DefaultHandler
{
  public static String PLAT_BODY_PRIFIX = "BF"; 
  public static final String PLAT_HEADER_PRIFIX = "HF";
  
  private XMLReader reader = null;
  Stack             stk    = new Stack();
  StringBuffer      sbBody = null;
  StringBuffer      sbHeader=null;
  BaseHeader        header= null;
  String            rootTag = "root";
  public static void main(String[] args) throws Exception
  {
    IsoMsg2PlatMsg p = new IsoMsg2PlatMsg();
//    System.out.println(p.transform("<isomsg><field id=\"0\" value=\"abc\" /> <field id=\"2\" value=\"abc\" type=\"binary\"/><field id=\"43\" value=\"CHNTJ&#20013;&#22269;&#24314;&#35774;&#38134;&#34892;&#22825;&#27941;&#24066;&#20998;&#34892;             \"/></isomsg>"));
    
//    System.out.println(p.transform("<root><HF1>RQ</HF1><HF2>82599A</HF2><HF3>2007-09-21</HF3><HF4>2008-03-0513.47.20</HF4><HF5>12102</HF5><HF6>0</HF6><HF7>¸ß</HF7></root>"));
  }
  

  public IsoMsg2PlatMsg() throws ISOException
  {
    super();
    try{
      reader = ISOUtil.genXmlReader();
      reader.setFeature("http://xml.org/sax/features/validation", false);
      reader.setContentHandler(this);
      reader.setErrorHandler(this);
    } catch (Exception e){
      throw new ISOException(e.toString());
    }
  }
  public void setRootTag(String tag){
    rootTag = tag;
  }
  
  public void setHeader(BaseHeader header){
   // this.header = header;
    this.header =(BaseHeader)header.clone();
  }
  public String transform(String isoXml) throws ISOException, UnsupportedEncodingException{
    sbBody = new StringBuffer();
    sbHeader = new StringBuffer();
   
    InputSource src = new InputSource(new ByteArrayInputStream(isoXml.getBytes("UTF-16")));
    try {
      reader.parse(src);
    } catch (Exception ex){
      ex.printStackTrace();
      throw new ISOException(ex);
    }
    return "<"+rootTag+">"+sbHeader.toString()+sbBody.toString()+"</"+rootTag+">";
  }
  
  public void startElement(String ns, String name, String qName, Attributes atts)
    throws SAXException{
    String type = atts.getValue("type");
    
    if(type==null || type.equals("")){
      type = "string";
    }
    stk.push(name+"|"+type);
    
    if (XMLPackager.ISOFIELD_TAG.equals(name)){
      String id = atts.getValue(XMLPackager.ID_ATTR);
      String value = atts.getValue(XMLPackager.VALUE_ATTR);
      type = atts.getValue(XMLPackager.TYPE_ATTR);
      String tag = PLAT_BODY_PRIFIX + (id.equals("0") ? "1" : id);

      if (type == null || type.equals("")){
        sbBody.append("<" + tag + ">" + value + "</" + tag + ">");
      } else {
        sbBody.append("<" + tag + " type=\"" + type + "\">" + value + "</" + tag
            + ">");
      }
    }

  }
  public void characters(char[] characters, int arg1, int arg2) throws SAXException {
	  
    String name = (String)stk.peek();
    String type = name.substring(name.indexOf('|')+1);
    name = name.substring(0, name.indexOf('|'));
    System.out.println(arg1+":"+ arg2);
    
    String value = new String(characters, arg1, arg2);
    System.out.println(value);
    
    if(header!=null && header.getIndexByName(name)>0){
      int id = header.getIndexByName(name);
      
      String tag = PLAT_HEADER_PRIFIX + id;

      if (type.equals("string")){
        sbHeader.append("<" + tag + ">" + value + "</" + tag + ">");
      } else {
        sbHeader.append("<" + tag + " type=\"" + type + "\">" + value + "</" + tag
            + ">");
      }
    }else if("retcode".equalsIgnoreCase(name)){
      sbHeader.append("<Result>"+value+"</Result>");
    }else if("timeout".equalsIgnoreCase(name)){
      sbHeader.append("<OverTime>"+value+"</OverTime>");
    }else if("key".equalsIgnoreCase(name)){
      sbHeader.append("<Sequence>"+value+"</Sequence>");
    }else if("isrsp".equalsIgnoreCase(name)){
      sbHeader.append("<ReqOrRspFlag>"+value+"</ReqOrRsp>");
    }else if("srcid".equalsIgnoreCase(name)){
      sbHeader.append("<SourceID>"+value+"</SourceID>");
    }else if("destid".equalsIgnoreCase(name)){
      sbHeader.append("<DestID>"+value+"</DestID>");
    }
  }

  public void endElement(String ns, String name, String qname)
      throws SAXException
  {
    stk.pop();
  }
}
