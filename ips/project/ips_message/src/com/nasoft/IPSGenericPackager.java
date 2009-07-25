package com.nasoft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.ndot.ips.db.pojo.PIsoDefine;
import org.ndot.ips.db.pojo.PIsoDefineId;

import com.nasoft.iso.ISOBasePackager;
import com.nasoft.iso.ISOFieldPackager;

public class IPSGenericPackager extends ISOBasePackager {
	// 根据数据库配置初始化Packager的Field配置信息
	public void initFieldArray(List<PIsoDefine> nodeInfoList) throws Exception {
		HashMap initMap = new HashMap();
		for (PIsoDefine isoDefine : nodeInfoList) {
			ISOFieldPackager fp = null;
			String shortCut = isoDefine.getShortcut();
			// 如果数据库中配置了Field的类型（如：com.nasoft.ips.iso.base.IF_CHAR),用反射机制创建Field类型
			if (null != shortCut && shortCut.length() > 0) {
				fp = FieldPackagerFactory.createFieldPackager(shortCut);
				fp.setDescription(isoDefine.getFieldname());
				fp.setLength(Integer.parseInt(isoDefine.getDatalen()));
			}
			PIsoDefineId id = isoDefine.getId();
//			if(id.getFieldno().equals("96")){
//				System.out.println("96");
//			}
			if (null == isoDefine.getDatatype()) {
				isoDefine.setDatatype("4");
			}
			if (null == isoDefine.getPadtype()) {
				isoDefine.setPadtype("2");
			}
			
			if (fp != null) {
				initMap.put(id.getFieldno(), fp);
			} else {
				switch (id.getFieldtype().charAt(0)) {
				case '0': // - 消息头
					// FieldPackagerFactory.createHeaderField(fieldNo,
					// fieldName,
					// dataType, dataLen);
					break;
				case '1': // - 消息体

					initMap.put(id.getFieldno(), FieldPackagerFactory
							.createFieldPackager(isoDefine.getPrefixtype(), Integer
									.parseInt(isoDefine.getPrefixlen()),
									isoDefine.getDatatype(), Integer
											.parseInt(isoDefine.getDatalen()),
									isoDefine.getPadtype()));
					break;

				case '2': // - BITMAP
					initMap.put(id.getFieldno(), FieldPackagerFactory
							.creactBitMapPackager(Integer.parseInt(isoDefine
									.getDatalen())));
					break;
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
