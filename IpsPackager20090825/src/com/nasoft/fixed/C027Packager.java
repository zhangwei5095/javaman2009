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
		map.put("BF1","�������");
		map.put("BF2","��ˮ��");
		map.put("BF3","ƾ֤����");
		map.put("BF4","���");
		map.put("BF5","�տ����");
		map.put("BF6","�������");
		map.put("BF7","��������");
		map.put("BF8","�տ����");
		map.put("BF9","�տ����");
		map.put("BF10","�˿����");
		map.put("BF11","�˿����");
		map.put("BF12","�������");
		map.put("BF13","�������");
		map.put("BF14","Ŀ�Ĺ���");
		map.put("BF15","Ŀ�Ŀ���");
		map.put("BF16","���ջ���");
		map.put("BF17","������");
		map.put("BF18","��ע");
		map.put("BF19","Ԥ���Ŀ");
		map.put("BF20","��Ŀ����");
		map.put("BF21","Ԥ�㼶��");
		map.put("BF22","��������");
		map.put("BF23","������־");
		map.put("BF24","��Ʊ����");
		map.put("BF25","����");
		map.put("BF26","ƾ֤����");
		map.put("BF27","˰��������");
		map.put("BF28","˰������ֹ");
		map.put("BF29","�޽�����");
		map.put("BF30","��˰�˱��");
		map.put("BF31","˰��ǼǺ�");
		map.put("BF32","Ԥ�㵥λ����");
		map.put("BF33","���λ�˺�");
		map.put("BF34","���λ����");
		map.put("BF35","�տλ�˺�");
		map.put("BF36","�տλ����");
		map.put("BF37","������");
		map.put("BF38","�˿�ԭ��");
		map.put("BF39","�˿�ԭ������");
		map.put("BF40","֧��ԭ��");
		map.put("BF41","֧��ԭ������");
		map.put("BF42","����ԭ��");
		map.put("BF43","����ԭ��");
		map.put("BF44","��Ѻ");
		map.put("BF45","Ԥ������");
		map.put("BF46","��ϸ����");
		map.put("BF47","���ӱ���");
		map.put("BF48","��ѯ��־");
		map.put("BF49","������־");
		map.put("BF50","�տ�����");
		map.put("BF51","���ʧ��ԭ��");
		map.put("BF52","��������");
		map.put("BF53","��������");
		map.put("BF54","���������");
		map.put("BF55","���˴������");
		map.put("BF56","���˴�������");
		map.put("BF57","���˽跽���");
		map.put("BF58","���˽跽����");
		map.put("BF59","���˴������");
		map.put("BF60","���˴�������");
		map.put("BF61","���˽跽���");
		map.put("BF62","���˽跽����");
		map.put("BF63","����״̬");
		map.put("BF64","����״̬");
		map.put("BF65","����");
		map.put("BF66","��������");
		map.put("BF67","����״̬");
		map.put("BF68","ԭ��ˮ��");
		map.put("BF69","ԭ������");
		map.put("BF70","���");
		map.put("BF71","��λ�˺�");
		map.put("BF72","���ջ���");
		map.put("BF73","��������");
		map.put("BF74","˰�����");
		map.put("BF75","����");
		map.put("BF76","�˺�");
		map.put("BF77","֧������");
		map.put("BF78","ԭ�������");
		map.put("BF79","��Ӧ����");
		map.put("BF80","��Ӧ��");
		map.put("BF81","�ɿ�����");
		map.put("BF82","��Ŀ���");
		map.put("BF83","˰����");
		map.put("BF84","˰����");
		map.put("BF85","ƷĿ��");
		map.put("BF86","ƷĿ��");
		map.put("BF87","��˰����");
		map.put("BF88","��˰��");
		map.put("BF89","˰��");
		map.put("BF90","�۳���");
		map.put("BF91","ʵ�ɶ�");
		map.put("BF92","������ϵ");
		map.put("BF93","ע������");
		map.put("BF94","��������");
		map.put("BF95","ƷĿ��");
		map.put("BF96","���㳡��");
		map.put("BF97","ǩԼ�˺�");
		map.put("BF98","��ϸ��");
		
		
		
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
			xmlMsg.append("<������ val=\"");
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
