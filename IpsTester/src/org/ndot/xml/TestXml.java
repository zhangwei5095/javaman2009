package org.ndot.xml;




import java.io.ByteArrayInputStream;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;


public class TestXml 
    extends DefaultHandler  implements Cloneable
{
	/**
	 * 
	 */

	protected byte[] header;

	protected Stack stk ;

//	private XMLReader reader = null;

	protected int LENGTH = 46;

	protected String idenStr = "";
	
	
	public void initDefine() {
	}

	public void setField(String name, int index, String type, int len){
		
	}

	XMLReader reader;

	public int getHLen() {
		return LENGTH;
	}

	public void setHLen(int len) {
		LENGTH = len;
	}

	public TestXml() throws ISOException {
		
		try {
//			reader = ISOUtil.genXmlReader();
//			reader.setFeature("http://xml.org/sax/features/validation", false);
//			reader.setContentHandler(this);
//			reader.setErrorHandler(this);
		} catch (Exception e) {
			throw new ISOException(e.toString());
		}
	}

	

	public byte[] pack() {
		return header;
	}

	public int unpack(byte[] h) {
//		if (h.length < LENGTH)
//			LENGTH = h.length;
		if (header == null) {
			this.header = new byte[LENGTH];
		}

		if (h != null) {
			System.arraycopy(h, 0, this.header, 0, LENGTH);
		}
		return header.length;
	}

	
	public static void main(String[] agrs){
		try {
			TestXml xml=new TestXml();
			System.out.println("xml "+xml.hashCode());
//			 xml.idenStr="4444444444444";
			 TestXml xml1=(TestXml)xml.clone();
//			 
			 System.out.println("xml1 "+xml1.hashCode());
			xml1.idenStr="555555555555555";
			xml1.xmlPack("<header><HF1>111</HF1><HF2>111</HF2><HF3>111</HF3></header>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] xmlPack(String xml) throws ISOException {
		try 
		{
			XMLReader reader = ISOUtil.genXmlReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setContentHandler(this);
			reader.setErrorHandler(this);
			stk=new Stack();
			this.idenStr="2222222222";
			InputSource input = new InputSource(new ByteArrayInputStream(xml
					.getBytes()));
//			stk.clear();
//			((TestXml)reader.getContentHandler()).idenStr="";
			System.out.println("xmlPack hash="+this.hashCode());
			System.out.println("xmlPack rader "+((TestXml)reader.getContentHandler()).hashCode());
			
			reader.parse(input);
			System.out.println("xmlPack:::"+this.idenStr);
			System.out.println(((TestXml)reader.getContentHandler()).idenStr);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw new ISOException(e);
		}
//		
		return this.header;
	}

	

	

	public Object clone() {
		try{
//			return this;
			return super.clone();
		}catch(Exception e){
			return null;
		}
		
	}

	public int getLength() {
		return getHLen();
	}

	
	/***********************************************************************/
	/*
	 * SAX Handler API
	 */
	public void startElement(String ns, String name, String qName,
			Attributes atts) throws SAXException {
		stk.push(name);
	}

	public void characters(char[] p0, int p1, int p2) throws SAXException {
		try {
			System.out.println("characters:::"+this.idenStr);
			System.out.println("characters hash="+this.hashCode());
			this.idenStr="111111111";
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();//////////
			throw new SAXException(e);
		}
	}

	public void endElement(String p0, String p1, String p2) throws SAXException {
		stk.pop();
	}

}
