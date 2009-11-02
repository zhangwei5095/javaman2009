package com.nasoft.xml;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Stack;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.packager.XMLPackager;

public class PlatMsg2IsoMsg extends DefaultHandler
{
//  private static String ISO_MSG_TAG = "isomsg";
//  private static String FIELD_TAG = "field";
//  private static String VALUE_TAG = "value";
//  private static String ID_TAG = "id";
  public static final String PLAT_BODY_PRIFIX = "BF"; 
  public static final String PLAT_HEADER_PRIFIX = "HF";
  private XMLReader reader = null;
  Stack             stk    = new Stack();
  StringBuffer      sbBody = null;
  StringBuffer      sbHeader=null;
  StringBuffer      sbPrefix=null;
  BaseHeader header = null;
  String rootTag = "root";
  public static void main(String[] args) throws Exception
  {
    PlatMsg2IsoMsg p = new PlatMsg2IsoMsg();
    System.out.println(p.transform("<root><BF1>abcd</BF1><BF132 type=\"binary\">abce</BF132></root>"));
  }
  public void setHeader(BaseHeader header)
  {
    this.header = header;
  }
  public void setRootTag(String tag)
  {
    rootTag = tag;
  }
  public PlatMsg2IsoMsg() throws ISOException
  {
    super();
    try
    {
      reader = ISOUtil.genXmlReader();
      reader.setFeature("http://xml.org/sax/features/validation", false);
      reader.setContentHandler(this);
      reader.setErrorHandler(this);
      
    } catch (Exception e)
    {
      throw new ISOException(e.toString());
    }
  }

  public String transform(String platXml) throws ISOException
  {
    stk.clear();
    sbBody = new StringBuffer();
    sbHeader = new StringBuffer();
    sbPrefix = new StringBuffer();
    InputSource src = new InputSource(new ByteArrayInputStream(platXml.getBytes()));
    try
    {
      if(header!=null)
      {
        sbHeader.append("<header>");
      }
      sbBody.append("<"+XMLPackager.ISOMSG_TAG+">");
      reader.parse(src);
      sbBody.append("</"+XMLPackager.ISOMSG_TAG+">");
      
      try{
		  SAXBuilder sb=new SAXBuilder();
    	  StringReader sr = new StringReader(sbBody.toString());
    	  Document doc= sb.build(sr);
    	  Element root=doc.getRootElement();
    	  List nodeList = root.getChildren();
    	  boolean flag=false;
          
    	  for (int i = 0; i < nodeList.size()-1;) {
   		  Element e = (Element) nodeList.get(i);
    		  Element e1 = (Element) nodeList.get(i+1);
    		  if(e.getAttributeValue("id", root.getNamespace()).equals(e1.getAttributeValue("id", root.getNamespace()))){
    			  String filedValue=e.getAttributeValue("value", root.getNamespace());
    			  String nextValue=e1.getAttributeValue("value", root.getNamespace());
    			  filedValue=filedValue+nextValue;
    			  e.setAttribute("value", filedValue);
    			  nodeList.remove(i+1);
    			  flag=true;    			  
    		  }else{
    			  i++;
    		  }
    		 
    		  
    	  }
    	  if(flag){
    		  sbBody=new StringBuffer();
        	  sbBody.append("<"+XMLPackager.ISOMSG_TAG+">");
        	  for (int i = 0; i < nodeList.size(); i++) {
        		  Element e = (Element) nodeList.get(i);
        		  String id=e.getAttributeValue("id", root.getNamespace());
        		  String value=e.getAttributeValue("value", root.getNamespace());
        		  String type=e.getAttributeValue("type", root.getNamespace());
        		  sbBody.append("<field id=\""+id+"\" value=\""+value + 
            	          ((type==null)?"\" />":("\" type=\""+type+"\" />")));
        		  
        	  }
        	  sbBody.append("</"+XMLPackager.ISOMSG_TAG+">");
    	  }
    	  
    	  
	  }catch(Exception e1){
		  e1.printStackTrace();
	  }
      if(header!=null)
      {
        sbHeader.append("</header>");
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ISOException(ex);
    }
    String ret =  "<"+rootTag+">"+sbPrefix.toString()+sbHeader.toString()+sbBody.toString()+"</"+rootTag+">";

    return ret;
  }
  public void characters(char[] characters, int arg1, int arg2) throws SAXException
  {
    String name = (String)stk.peek();
    String type = name.substring(name.indexOf('|')+1);
    name = name.substring(0, name.indexOf('|'));
    
    String value = new String(characters, arg1, arg2);

    if (PLAT_BODY_PRIFIX.equals(name.substring(0, PLAT_BODY_PRIFIX.length())))
    { 
      //String value = new String(characters, arg1, arg2);
      String id = name.substring(PLAT_BODY_PRIFIX.length());
      id = id.equals("1") ? "0" : id;
      
    	  sbBody.append("<field id=\""+id+"\" value=\""+value + 
    	          ((type.equals("string"))?"\" />":("\" type=\""+type+"\" />")));
      
      
    }else if (PLAT_HEADER_PRIFIX.equals(name.substring(0, PLAT_HEADER_PRIFIX.length())))
    {
      if(header!=null)
      {
       // String value = new String(characters, arg1, arg2);
        String id = name.substring(PLAT_BODY_PRIFIX.length());
        
        String hTag = header.getNameByIndex(Integer.parseInt(id));
       // System.out.println("TTTTTTTTTTTTT:"+id + "  "+hTag);
        sbHeader.append("<"+hTag+
            ((type.equals("string"))?">":(" type=\""+type+"\">"))+value+"</"+hTag+">");
          
      }
//      else if("Result".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<retcode>"+value+"</retcode>");
//      }else if("OverTime".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<timeout>"+value+"</timeout>");
//      }else if("Sequence".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<key>"+value+"</key>");
//      }else if("IsRsp".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<isrsp>"+value+"</isrsp>");
//      }else if("ReqOrRspFlag".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<isrsp>"+value+"</isrsp>");
//      }else if("SourceID".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<srcid>"+value+"</srcid>");
//      }else if("DestID".equalsIgnoreCase(name))
//      {
//        sbPrefix.append("<destid>"+value+"</destid>");
//      }
    }
  }

  public void endElement(String ns, String name, String qname)
      throws SAXException
  {
    stk.pop();
  }

  public void startElement(String ns, String name, String qName, Attributes atts)
    throws SAXException
  {
    String type = atts.getValue("type");
    
    if(type==null || type.equals(""))
    {
      type = "string";
    }
    stk.push(name+"|"+type);
  }
  
}
