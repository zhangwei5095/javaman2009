package com.nasoft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.nasoft.iso.ISOBasePackager;
import com.nasoft.iso.ISOFieldPackager;


public class GenericPackager extends ISOBasePackager
{
	public void initFieldArray(Element node, String nodeId) 
		throws Exception
	{
		HashMap initMap = new HashMap();
			List nodeList = node.getChildren();
		for (int i = 0; i < nodeList.size(); i++) 
		{
			
			Element e = (Element) nodeList.get(i);
			if(e.getChild("NodeID",node.getNamespace()).getValue().equals(nodeId)){
				String fieldType;
				//String fieldName;
				String fieldNo;
				String preType;
				String preLen;
				String dataType;
				String dataLen;
				String padType;
				String shortCut;
				String FieldName;

				fieldType = Utilities.getXmlNodeValue(e.getChild("FieldType",node.getNamespace()), "1");
				fieldNo = Utilities.getXmlNodeValue(e.getChild("FieldNo",node.getNamespace()), "-1");
				//fieldName = Utilities.getXmlNodeValue(e.getChild("FieldName"), "");
				preType = Utilities.getXmlNodeValue(e.getChild("PrefixType",node.getNamespace()), "4");
				preLen = Utilities.getXmlNodeValue(e.getChild("PrefixLen",node.getNamespace()), "0");
				dataType = Utilities.getXmlNodeValue(e.getChild("DataType",node.getNamespace()), "0");
				dataLen = Utilities.getXmlNodeValue(e.getChild("DataLen",node.getNamespace()), "0");
				padType = Utilities.getXmlNodeValue(e.getChild("PadType",node.getNamespace()), "0");
				shortCut = Utilities.getXmlNodeValue(e.getChild("ShortCut",node.getNamespace()), "");
				FieldName =Utilities.getXmlNodeValue(e.getChild("FieldName",node.getNamespace()), "");
				ISOFieldPackager fp = null;
				
				
				if(shortCut.length()>0)
				{
					fp = FieldPackagerFactory.createFieldPackager(shortCut);
					//dongbg修改
					fp.setDescription(FieldName);
					fp.setLength(Integer.parseInt(dataLen));
				
				}
				
				if(fp!=null)
				{
					initMap.put(fieldNo, fp);
				}else
				{
					switch (fieldType.charAt(0)) 
					{
					case '0': // - 消息头
//						FieldPackagerFactory.createHeaderField(fieldNo, fieldName,
//								dataType, dataLen);
						break;
					case '1': // - 消息体
						initMap.put(fieldNo, FieldPackagerFactory.createFieldPackager(
								preType, Integer.parseInt(preLen), dataType, Integer
										.parseInt(dataLen), padType));
						break;
					case '2': // - BITMAP
						initMap.put(fieldNo, FieldPackagerFactory
								.creactBitMapPackager(Integer.parseInt(dataLen)));
		
						break;
					}
				}
			}
				
		}
		setFieldPackager(makeFieldArray(initMap));
	}
	
    private ISOFieldPackager[] makeFieldArray(HashMap tab)
    {
        int maxField = 0;
        
        // First find the largest field number in the Hashtable
        for (Iterator iterator =tab.keySet().iterator();iterator.hasNext();)
        {
            int n = Integer.parseInt((String)iterator.next());
            if (n > maxField) maxField = n;
        }

        // Create the array
        ISOFieldPackager fld[] = new ISOFieldPackager[maxField+1];

        // Populate it
        for (Iterator iterator =tab.keySet().iterator();iterator.hasNext();)
        {
            String key = (String)iterator.next();
            int nKey = Integer.parseInt(key);
            //nKey = nKey==1 ? 0 : nKey;
            fld[nKey] = (ISOFieldPackager)tab.get(key);
        }
        return fld;
    }

}
