package com.nasoft.util;

import org.jdom.Element;

public class Utilities 
{
	public static String getXmlNodeValue(Element node , String defValue)
	{
		if(node==null)
		{
			return defValue;
		}
		String value = node.getValue();
		if(value!=null && value.length()>0)
		{
			return value;
		}else
		{
			return defValue;
		}	
	}
	
	public static String getXmlAttrValue(Element node , String attrName,String defValue)
	{
		if(node==null)
		{
			return defValue;
		}
		String value = node.getAttributeValue(attrName,node.getNamespace());
		if(value!=null && value.length()>0)
		{
			return value;
		}else
		{
			return defValue;
		}	
	}

}
