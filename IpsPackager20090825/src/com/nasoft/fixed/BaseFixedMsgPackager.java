package com.nasoft.fixed;

import java.io.StringReader;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.nasoft.Utilities;
public class BaseFixedMsgPackager implements IFixedMsgPackager
{
	/*
	 * Can only used in JDK1.5.0 or above 
	 */
	private TreeMap<Integer, FixedFieldPackager> inFld = new TreeMap<Integer, FixedFieldPackager>();
	private TreeMap<Integer, FixedFieldPackager> outFld = new TreeMap<Integer, FixedFieldPackager>();

	private int maxInFields = -1;
	private int inPackLen = 0;
	private String tranId=null;
	private int maxOutFields = -1;
	private int outPackLen = 0;

	public void calcOffset() 
		throws Exception
	{
		int offset = 0;
		for(int i=0; i<inFld.size(); i++)
		{
			FixedFieldPackager packager = inFld.get(Integer.valueOf(i));
			if(packager.getFieldNo()!=i)
			{
				throw new Exception("BaseFixedMsgPackager:calcOffset() error!");
			}
			packager.setOffset(offset);
			offset += packager.getFieldLen();
		}
		maxInFields = inFld.size()-1;
		inPackLen = offset;
		
		offset = 0;
		for(int i=0; i<outFld.size(); i++)
		{
			FixedFieldPackager packager = outFld.get(Integer.valueOf(i));
			if(packager.getFieldNo()!=i)
			{
				throw new Exception("BaseFixedMsgPackager:calcOffset() error!");
			}
			packager.setOffset(offset);
			offset += packager.getFieldLen();
		}
		maxOutFields = outFld.size()-1;
		outPackLen = offset;
	}
	public void setFieldPackager(int fldno, FixedFieldPackager packager, String inOutFlag)
	{
		if(inOutFlag.equals("0"))
		{
			inFld.put(Integer.valueOf(fldno), packager);
		}else
		{
			outFld.put(Integer.valueOf(fldno), packager);
		}
	}
		
	public void initFldByXmlNode(Element node, String nodeId, String tranId) 
		throws Exception
	{
		String strXpath = "//Node[NodeID="+nodeId+"and TranCodeStr="+tranId+"]";
		setTranId(tranId);
		List nodeList = XPath.selectNodes(node, strXpath);
		for (int i = 0; i < nodeList.size(); i++) 
		{
			Element e = (Element) nodeList.get(i);
			String fieldNo;
			String fieldType;
			String padderType;
			String fieldLen;
			String internalNo;
			String inOutFlag;

			fieldType = Utilities.getXmlNodeValue(e.getChild("FieldType"), "1");
			fieldNo = Utilities.getXmlNodeValue(e.getChild("FieldNo"), "-1");
			fieldLen = Utilities.getXmlNodeValue(e.getChild("FieldLen"), "");
			internalNo = Utilities.getXmlNodeValue(e.getChild("InternalNo"), "");
			inOutFlag = Utilities.getXmlNodeValue(e.getChild("InOutFlag"), "0");
			padderType = Utilities.getXmlNodeValue(e.getChild("PadderType"), "0");
			
			FixedFieldPackager fp = new FixedFieldPackager
					(Integer.valueOf(fieldNo), fieldType, padderType,
				     Integer.valueOf(fieldLen), Integer.valueOf(internalNo));
			if(inOutFlag.equals("0"))
			{
				inFld.put(Integer.valueOf(fieldNo), fp);
			}else	
			{
				outFld.put(Integer.valueOf(fieldNo), fp);
			}
		}			
	}
	public byte[] pack(FixedMsg msg) 
		throws Exception 
	{
		byte[] b = new byte[outPackLen];
		for(int i=0; i<=maxInFields;i++)
		{
			FixedFieldPackager fp = outFld.get(Integer.valueOf(i));
			fp.pack(b, msg.getFieldBytes(i));
		}
		return b;
	}

	public int unpack(FixedMsg msg, byte[] b) throws Exception 
	{
		for(int i=0; i<=maxInFields;i++)
		{
			FixedFieldPackager fp = inFld.get(Integer.valueOf(i));
			msg.setFieldBytes(i, fp.unpack(b));
		}
		return inPackLen;
	}
	public static void main(String[] args)
	{
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		map.put(Integer.valueOf(2), "2");
		map.put(Integer.valueOf(3), "3");
		map.put(Integer.valueOf(1), "1");
		map.put(Integer.valueOf(0), "0");

	}
	public String Msg2Xml(FixedMsg msg) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<root>");
		sb.append("<tranid>");
		sb.append(tranId);
		sb.append("</tranid>");
		for(int i=0; i<=maxInFields;i++)
		{
			FixedFieldPackager fp = inFld.get(Integer.valueOf(i));
			fp.dumpXml(msg.getFieldBytes(i));
		}
		sb.append("</root>");
		return sb.toString();
	}
	
	public FixedMsg Xml2Msg(FixedMsg msg, String xml) 
		throws Exception
	{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xml);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		for(int i=0; i<maxOutFields; i++ )
		{
			FixedFieldPackager fp = outFld.get(Integer.valueOf(i));
			byte[] b = root.getChildText("F"+fp.getInternalNo()).getBytes();
			msg.setFieldBytes(i, b );
		}
		msg.setTranCode(this.tranId);
		return msg;

	}
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
}
