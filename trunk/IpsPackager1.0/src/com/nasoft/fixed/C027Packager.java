package com.nasoft.fixed;

import java.io.StringReader;
import java.util.HashMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.nasoft.Utilities;

public class C027Packager {
	public static void main(String args[]){
		try{
			String xmlData="<root><HF1>11111</HF1><HF2>2222</HF2><HF3>3333</HF3><HF4>4444</HF4></root>";
			//byte[] bb=C027Packager.pack(xmlData);
//			System.out.println(new String (bb,"GBK"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public  byte[] pack(String platMsg,String bitMapStr) throws Exception{
		
		
		StringBuilder xmlMsg=new StringBuilder();
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(platMsg);
		
		xmlMsg.append("<CFX>");
		xmlMsg.append("<HEAD>");
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		
		HashMap map=new HashMap<String, String>();
		map.put("BF1","发起机构");
		map.put("BF2","流水号");
		map.put("BF3","凭证号码");
		map.put("BF4","金额");
		map.put("BF5","收款机构");
		map.put("BF6","付款机构");
		map.put("BF7","开户行名");
		map.put("BF8","收款国库");
		map.put("BF9","收款库名");
		map.put("BF10","退库国库");
		map.put("BF11","退库库名");
		map.put("BF12","付款国库");
		map.put("BF13","付款库名");
		map.put("BF14","目的国库");
		map.put("BF15","目的库名");
		map.put("BF16","征收机关");
		map.put("BF17","机关名");
		map.put("BF18","备注");
		map.put("BF19","预算科目");
		map.put("BF20","科目名称");
		map.put("BF21","预算级次");
		map.put("BF22","级次描述");
		map.put("BF23","辅助标志");
		map.put("BF24","开票日期");
		map.put("BF25","批号");
		map.put("BF26","凭证种类");
		map.put("BF27","税款属期起");
		map.put("BF28","税款属期止");
		map.put("BF29","限缴日期");
		map.put("BF30","纳税人编号");
		map.put("BF31","税务登记号");
		map.put("BF32","预算单位代码");
		map.put("BF33","付款单位账号");
		map.put("BF34","付款单位名称");
		map.put("BF35","收款单位账号");
		map.put("BF36","收款单位名称");
		map.put("BF37","汇入行");
		map.put("BF38","退库原因");
		map.put("BF39","退库原因描述");
		map.put("BF40","支出原因");
		map.put("BF41","支出原因描述");
		map.put("BF42","更正原因");
		map.put("BF43","调减原因");
		map.put("BF44","密押");
		map.put("BF45","预算种类");
		map.put("BF46","明细笔数");
		map.put("BF47","附加笔数");
		map.put("BF48","查询标志");
		map.put("BF49","往来标志");
		map.put("BF50","收款属地");
		map.put("BF51","审核失败原因");
		map.put("BF52","受理日期");
		map.put("BF53","清算日期");
		map.put("BF54","借贷轧差金额");
		map.put("BF55","来账贷方金额");
		map.put("BF56","来账贷方笔数");
		map.put("BF57","来账借方金额");
		map.put("BF58","来账借方笔数");
		map.put("BF59","往账贷方金额");
		map.put("BF60","往账贷方笔数");
		map.put("BF61","往账借方金额");
		map.put("BF62","往账借方笔数");
		map.put("BF63","交易状态");
		map.put("BF64","清算状态");
		map.put("BF65","描述");
		map.put("BF66","工作日期");
		map.put("BF67","中心状态");
		map.put("BF68","原流水号");
		map.put("BF69","原交易码");
		map.put("BF70","余额");
		map.put("BF71","单位账号");
		map.put("BF72","接收机构");
		map.put("BF73","开户机构");
		map.put("BF74","税务机构");
		map.put("BF75","户名");
		map.put("BF76","账号");
		map.put("BF77","支出种类");
		map.put("BF78","原发起机构");
		map.put("BF79","响应描述");
		map.put("BF80","响应码");
		map.put("BF81","缴款日期");
		map.put("BF82","项目金额");
		map.put("BF83","税种码");
		map.put("BF84","税种名");
		map.put("BF85","品目码");
		map.put("BF86","品目名");
		map.put("BF87","课税数量");
		map.put("BF88","计税额");
		map.put("BF89","税率");
		map.put("BF90","扣除额");
		map.put("BF91","实缴额");
		map.put("BF92","隶属关系");
		map.put("BF93","注册类型");
		map.put("BF94","错误类型");
		map.put("BF95","品目额");
		map.put("BF96","清算场次");
		map.put("BF97","签约账号");
		map.put("BF98","明细包");
		
		
		
		if(!"".equals(Utilities.getXmlNodeValue(root.getChild("HF1",root.getNamespace()), ""))){
			xmlMsg.append("<SRC>");
			xmlMsg.append(Utilities.getXmlNodeValue(root.getChild("HF1",root.getNamespace()), ""));
			xmlMsg.append("</SRC>");
			
		}
		
		if(!"".equals(Utilities.getXmlNodeValue(root.getChild("HF2",root.getNamespace()), ""))){
			xmlMsg.append("<DES>");
			xmlMsg.append(Utilities.getXmlNodeValue(root.getChild("HF2",root.getNamespace()), ""));
			xmlMsg.append("</DES>");
			
		}
		
		if(!"".equals(Utilities.getXmlNodeValue(root.getChild("HF3",root.getNamespace()), ""))){
			xmlMsg.append("<APP>");
			xmlMsg.append(Utilities.getXmlNodeValue(root.getChild("HF3",root.getNamespace()), ""));
			xmlMsg.append("</APP>");
			
		}
		xmlMsg.append("</HEAD>");
		xmlMsg.append("<MSG>");
		String transId=Utilities.getXmlNodeValue(root.getChild("HF4",root.getNamespace()), "");
		if(!"".equals(transId)){
			xmlMsg.append("<交易码 val=\"");
			xmlMsg.append(transId);
			xmlMsg.append("\"/>");
			
		}

		if(bitMapStr.charAt(bitMapStr.length()-1)==','){
			bitMapStr=bitMapStr.substring(0,bitMapStr.length()-1);
		}
		String[] bitMapArray=bitMapStr.split(",");
		
		int fldLen=bitMapArray.length;
		for(int i =0;i<fldLen;i++){
			String fieldKey="BF"+bitMapArray[i];
			boolean hasFld98=false;
			if("BF98".equals(fieldKey)){
				hasFld98=true;
			}else if("BF99".equals(fieldKey)){
				
			}else{
				String fieldName=(String)map.get(fieldKey);
				xmlMsg.append("<"+fieldName+" val=\"");
				String fieldValue=Utilities.getXmlAttrValue(root.getChild(fieldKey,root.getNamespace()), "val", "");
				xmlMsg.append(fieldValue);
				xmlMsg.append("\"/>");
			}
			
			
			
		}
		
		
		xmlMsg.append("</MSG>");
		xmlMsg.append("</CFX>");
		
		byte[] msgByte=new byte[xmlMsg.toString().getBytes("GBK").length];
		System.arraycopy(xmlMsg.toString().getBytes("GBK"), 0, msgByte, 0, msgByte.length);
		return msgByte;
		
	}
	
	public  String unpack(byte[] binData)throws Exception{
		StringBuilder platMsg=new StringBuilder();
		String xmlMsg=new String(binData,"GBK");
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xmlMsg);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		
		
		platMsg.append("<root>");
		
		if(!"".equals(Utilities.getXmlAttrValue(root.getChild("HF1",root.getNamespace()), "", ""))){
			
			
		}
		
		
		return platMsg.toString();
	}

}
