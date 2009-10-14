package com.nasoft.fixed;

import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.nasoft.Utilities;
public class TranIdGetter 
{
	private HashMap<String, FixedRec[]> nodeMap = new HashMap<String, FixedRec[]>();
	public class FixedRec
	{
		int seq;
		int offset;
		int len;
		
		public FixedRec(int seq, int offset, int len)
		{
			this.seq = seq;
			this.offset = offset;
			this.len = len;
		}
		public int getLen() {
			return len;
		}
		public void setLen(int len) {
			this.len = len;
		}
		public int getOffset() {
			return offset;
		}
		public void setOffset(int offset) {
			this.offset = offset;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
	}
	public String getTranId(byte[] input, String nodeId)
	{
		FixedRec[] rec = nodeMap.get(nodeId);
		if(rec!=null)
		{
			int recLen = 0;
			for(int i=0; i<rec.length; i++)
			{
				recLen+=rec[i].getLen();;
			}
			byte[] b = new byte[recLen];
			int bOffset = 0;
			for(int i=0; i<rec.length; i++)
			{
				//int seq = rec[i].getSeq();
				int offset = rec[i].getOffset();
				int len = rec[i].getLen();
				System.arraycopy(input, offset, b, bOffset, len);
				bOffset+=len;
			}
			return new String(b);

		}else
		{
			return null;
		}
	}
	
	public String getTranId(String xml)
	{
		int start = xml.indexOf("<tranid>")+8;
		int end = xml.indexOf("</tranid>");
		String tranId = xml.substring(start, end);
		return tranId;
	}

	synchronized public void initFixedRecMap(String xml) 
		throws Exception
	{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xml);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		List nodeList = XPath.selectNodes(root, "//Node");
		
		Set uSet = new HashSet();
		
		for(int i=0; i<nodeList.size(); i++)
		{
			Element e = (Element)nodeList.get(i);
			String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeID"), "");
			if(!uSet.contains(nodeId))
			{
				uSet.add(nodeId);
			}
		}
		for(Iterator it = uSet.iterator(); it.hasNext() ;)
		{
			String nodeId = (String)it.next();
			String xpath = "//Node[NodeID="+nodeId+"]";
			FixedRec[] recs = createFixedRecArray(nodeId, XPath.selectNodes(root,xpath));
			nodeMap.put(nodeId, recs); 	
		}
	}
	private FixedRec[] createFixedRecArray(String nodeId, List list) 
	{
		FixedRec[] rec = new FixedRec[list.size()];
		for(int i=0; i<list.size(); i++)
		{
			Element e = (Element)list.get(i);
			int seq = Integer.getInteger(Utilities.getXmlNodeValue(e.getChild("SeqNo"), "-1"));
			int offset = Integer.getInteger(Utilities.getXmlNodeValue(e.getChild("OffSet"), "-1"));
			int len = Integer.getInteger(Utilities.getXmlNodeValue(e.getChild("FieldLen"), "-1"));
			if(seq>-1 && offset>-1 && len>-1)
			{
				rec[seq] = new FixedRec(seq, offset, len);
			}	
		}
		return rec;
	}
}
