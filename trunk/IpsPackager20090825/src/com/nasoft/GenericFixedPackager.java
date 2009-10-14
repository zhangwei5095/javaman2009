package com.nasoft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.nasoft.fixed.FixedPackager;
import com.nasoft.iso.ISOFieldPackager;

public class GenericFixedPackager extends FixedPackager {

	public void initFixedFieldArray(Element node, String nodeId)
			throws Exception {
		HashMap initMap = new HashMap();
		List nodeList = node.getChildren();
		for (int i = 0; i < nodeList.size(); i++) {

			Element e = (Element) nodeList.get(i);
			if (e.getChild("NodeID", node.getNamespace()).getValue().equals(
					nodeId)) {
				String fieldType;
				// String fieldName;
				String fieldNo;
				String preType;
				String preLen;
				String dataType;
				String dataLen;
				String padType;
				String shortCut;
				String fieldName;
				String fieldLenType;

				fieldType = Utilities.getXmlNodeValue(e.getChild("FieldType",
						node.getNamespace()), "1");
				fieldNo = Utilities.getXmlNodeValue(e.getChild("FieldNo", node
						.getNamespace()), "-1");
				// fieldName =
				// Utilities.getXmlNodeValue(e.getChild("FieldName"), "");
				preType = Utilities.getXmlNodeValue(e.getChild("PrefixType",
						node.getNamespace()), "4");
				preLen = Utilities.getXmlNodeValue(e.getChild("PrefixLen", node
						.getNamespace()), "0");
				dataType = Utilities.getXmlNodeValue(e.getChild("DataType",
						node.getNamespace()), "0");
				dataLen = Utilities.getXmlNodeValue(e.getChild("DataLen", node
						.getNamespace()), "0");
				padType = Utilities.getXmlNodeValue(e.getChild("PadType", node
						.getNamespace()), "3");
				shortCut = Utilities.getXmlNodeValue(e.getChild("ShortCut",
						node.getNamespace()), "");
				fieldName = Utilities.getXmlNodeValue(e.getChild("FieldName",
						node.getNamespace()), "");
				fieldLenType = Utilities.getXmlNodeValue(e.getChild(
						"FieldLenType", node.getNamespace()), "1");
				String detailNumLen = Utilities.getXmlNodeValue(e.getChild(
						"DetailNumLen", node.getNamespace()), "0");
				String padContent = Utilities.getXmlNodeValue(e.getChild(
						"PadContent", node.getNamespace()), "");
				String seprator = Utilities.getXmlNodeValue(e.getChild(
						"Seprator", node.getNamespace()), "");
				String detailLen = Utilities.getXmlNodeValue(e.getChild(
						"DetailLen", node.getNamespace()), "0");
				String detailPreLen = Utilities.getXmlNodeValue(e.getChild(
						"DetailPreLen", node.getNamespace()), "0");
				ISOFieldPackager fp = null;

				if (shortCut.length() > 0) {
					fp = FieldPackagerFactory.createFieldPackager(shortCut);
					// dongbg修改
					fp.setDescription(fieldName);
					fp.setLength(Integer.parseInt(dataLen));
				}

				if (fp != null) {
					initMap.put(fieldNo, fp);
				} else {
					switch (fieldType.charAt(0)) {
					case '0': // - 消息头
						// FieldPackagerFactory.createHeaderField(fieldNo,
						// fieldName,
						// dataType, dataLen);
						break;
					case '1': // - 消息体
						initMap.put(fieldNo, FieldPackagerFactory
								.createFixedFieldPackager(preType, Integer
										.parseInt(preLen), dataType, Integer
										.parseInt(dataLen), padType,
										padContent, fieldLenType, Integer
												.parseInt(detailNumLen),
										Integer.parseInt(detailLen), Integer
												.parseInt(detailPreLen),
										seprator, fieldName));
						break;
					case '2': // - BITMAP
						initMap.put(fieldNo,
								FieldPackagerFactory
										.creactBitMapPackager(Integer
												.parseInt(dataLen)));
						break;
					}
				}
			}
		}
		setFieldPackager(makeFieldArray(initMap));
	}

	private ISOFieldPackager[] makeFieldArray(HashMap tab) {
		int maxField = 0;

		// First find the largest field number in the Hashtable
		for (Iterator iterator = tab.keySet().iterator(); iterator.hasNext();) {
			int n = Integer.parseInt((String) iterator.next());
			if (n > maxField)
				maxField = n;
		}

		// Create the array
		ISOFieldPackager fld[] = new ISOFieldPackager[maxField + 1];

		// Populate it
		for (Iterator iterator = tab.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			int nKey = Integer.parseInt(key);
			// nKey = nKey==1 ? 0 : nKey;
			fld[nKey] = (ISOFieldPackager) tab.get(key);
		}
		return fld;
	}
}
