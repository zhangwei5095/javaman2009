package com.nasoft.fixed;

import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.nasoft.Utilities;

public class C028Packager {
	public static void main(String args[]){
		try{
			String xml="<body><HF1>11</HF1><HF2>0022</HF2><HF3>3 </HF3><HF4>4</HF4><HF5>R       </HF5></body>";
//			byte [] bb=packHeader(xml,"req");
//			System.out.println(ISOUtil.byte2HexNoSpaceStr(bb, bb.length));
//			String head=unpackHeader(bb,"req");
//			System.out.println(head);
		}catch(Exception e){
			e.printStackTrace();
		}
		String buf ="1234567890875r44r444t5greg";
		try {
//			unpackHeader(buf.getBytes());
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} 
		
	}
	public  C028Head unpackHeader(byte[] buf)throws Exception{
		C028Head head=new C028Head();
		String idenStr="";
		int offset=0;
		String[] hf = new String[11]; 
		int[] offsetArr = {2,6,2,1,1,1,1,8,1,26}; 
		 hf[1]="";
		 hf[2]="";
		 hf[3]="";
		 hf[4]="";
		 hf[5]="";
		 hf[6]="";
		 hf[7]="";
		 hf[8]="";
		 hf[9]="";
		 hf[10]="";
		 
		
			for (int i =1; i<=10 ; i++){
				if(offset+offsetArr[i-1]>=buf.length){
					hf[i]=new String(buf,offset,buf.length-offset);
					break;
				}
				
				hf[i]=new String(buf,offset,offsetArr[i-1]);
				offset+=offsetArr[i-1];
				
			}
			
			
			
		
		
		head.setHeadLen(offset);
		head.setIdenStr(idenStr);
		
		
		StringBuffer resultXml=new StringBuffer();

		resultXml.append("<HF1>");
		resultXml.append(hf[1]);
		resultXml.append("</HF1>");
		
		resultXml.append("<HF2>");
		resultXml.append(hf[2]);
		resultXml.append("</HF2>");
		
		resultXml.append("<HF3>");
		resultXml.append(hf[3]);
		resultXml.append("</HF3>");
		
		
			resultXml.append("<HF4>");
			resultXml.append(hf[4]);
			resultXml.append("</HF4>");
			
			resultXml.append("<HF5>");
			resultXml.append(hf[5]);
			resultXml.append("</HF5>");
		
			resultXml.append("<HF6>");
			resultXml.append(hf[6]);
			resultXml.append("</HF6>");
			
			resultXml.append("<HF7>");
			resultXml.append(hf[7]);
			resultXml.append("</HF7>");
			
			resultXml.append("<HF8>");
			resultXml.append(hf[8]);
			resultXml.append("</HF8>");
			
			
				resultXml.append("<HF9>");
				resultXml.append(hf[9]);
				resultXml.append("</HF9>");
				
				resultXml.append("<HF10>");
				resultXml.append(hf[10]);
				resultXml.append("</HF10>");
		head.setXmlHead(resultXml.toString());
//		System.out.println("aaa="+resultXml.toString());
		return head;
	}
	
	public  byte[] packHeader(String xmlHeader,String reqRsp)throws Exception{
		
//		System.out.println("xmlHeader="+xmlHeader);
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xmlHeader);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		String hf1=Utilities.getXmlNodeValue(root.getChild("HF1",root.getNamespace()), "");
		String hf2=Utilities.getXmlNodeValue(root.getChild("HF2",root.getNamespace()), "");
		String hf3=Utilities.getXmlNodeValue(root.getChild("HF3",root.getNamespace()), "");
		String hf4=Utilities.getXmlNodeValue(root.getChild("HF4",root.getNamespace()), "");
		String hf5=Utilities.getXmlNodeValue(root.getChild("HF5",root.getNamespace()), "");
		String hf6=Utilities.getXmlNodeValue(root.getChild("HF6",root.getNamespace()), "");
		String hf7=Utilities.getXmlNodeValue(root.getChild("HF7",root.getNamespace()), "");
		String hf8=Utilities.getXmlNodeValue(root.getChild("HF8",root.getNamespace()), "");
		String hf9=Utilities.getXmlNodeValue(root.getChild("HF9",root.getNamespace()), "");
		String hf10=Utilities.getXmlNodeValue(root.getChild("HF10",root.getNamespace()), "");
		
		return (hf1+hf2+hf3+hf4+hf5+hf6+hf7+hf8+hf9+hf10).getBytes();
	}
	
	public  byte xOrArray(byte [] array){
		byte result=0x00;
		if(array.length>0){
			for(int i=0;i<array.length;i++){
				result =(byte) (result ^ array[i]);
			}
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 
	 * @param str
	 * @param padType l左补，r右补
	 * @param padContent
	 * @return
	 */
	private  String padStr(String str,int padLen,String padType,String padContent){
		int strLen=str.length();
		if(strLen<padLen){
			if("l".equals(padType)){
				while(strLen<padLen){
					str=padContent+str;
					strLen++;
				}
			}else if("r".equals(padType)){
				while(strLen<padLen){
					str=str+padContent;
					strLen++;
				}
			}
		}
		return str;
	}

}
