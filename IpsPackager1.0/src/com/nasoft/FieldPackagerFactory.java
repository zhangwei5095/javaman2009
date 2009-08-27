package com.nasoft;



import org.jdom.Element;

import com.nasoft.iso.AsciiPrefixer;
import com.nasoft.iso.AsciiPrefixerRightPadSpace;
import com.nasoft.iso.BCDInterpreter;
import com.nasoft.iso.BcdPrefixer;
import com.nasoft.iso.BinaryPrefixer;
import com.nasoft.iso.EBCLeftPadder;
import com.nasoft.iso.EBCRightPadder;
import com.nasoft.iso.EbcdicInterpreter;
import com.nasoft.iso.EbcdicPrefixer;
import com.nasoft.iso.IFB_BITMAP;
import com.nasoft.iso.ISOBinaryFieldPackager;
import com.nasoft.iso.ISOBitMapPackager;
import com.nasoft.iso.ISODetailSepFieldPackager;
import com.nasoft.iso.ISOEntireBinaryFieldPackager;
import com.nasoft.iso.ISOEntireEBCDICFieldPackager;
import com.nasoft.iso.ISOEntireFieldPackager;
import com.nasoft.iso.ISOFieldPackager;
import com.nasoft.iso.ISOSepFieldPackager;
import com.nasoft.iso.ISOStartSepFieldPackager;
import com.nasoft.iso.ISOStringFieldPackager;
import com.nasoft.iso.LeftPadder;
import com.nasoft.iso.LiteralBinaryInterpreter;
import com.nasoft.iso.LiteralInterpreter;
import com.nasoft.iso.MultiAsciiPrefixer;
import com.nasoft.iso.MultiBcdPrefixer;
import com.nasoft.iso.MultilEbcdicPrefixer;
import com.nasoft.iso.NullPadder;
import com.nasoft.iso.NullPrefixer;
import com.nasoft.iso.Padder;
import com.nasoft.iso.Prefixer;
import com.nasoft.iso.RightPadder;

public class FieldPackagerFactory 
{

	/**
	 * @author dongbg
	 * TODO Create a FieldPacakger by input params
	 * @param preType 前缀类型
	 * 			0 - ASCII前缀
	 *			1 - BCD前缀
	 *			2 - EBDC前缀
	 *			3 - Binary前缀
	 *			4 - Null前缀
	 *			5 - ASCII右补空格前缀
	 * @param preLen 前缀位数
	 * @param dataType数据类型
	 * 			0 - ASCII字符
	 *			1 - BCD码
	 *			2 - EBDC码
	 *			3 - Binary二进制
	 * @param dataLen 数据长度（数据最大长度）
	 * @param padType 填充方式 l - 左补 r - 右补
	 * @param padContent 填充内容0 - 0 1 - 空格
	 * @param fieldLenType 数据域类型
	 * 			1-定长域
	 *			2-变长分割域
	 *			3-变长域
	 *			4-明细域
	 *			6-整体域，这个域一直到报文结束
	 *          7-明细域分隔符
	 *          8-变长分隔域(域前后都有分隔符）
	 * @param detailNumLen 明细域条数的位数
	 * @parametailLen 明细域长度
	 * @param detailPreLen 明细域前缀长度
	 * @param seprator 分隔符
	 * @param fieldName 域名称
	 * @return
	 * @throws Exception 
	 */
	
	public static ISOFieldPackager createFixedFieldPackager(
			String preType, int preLen, 
			String dataType, int dataLen,
			String padType,String padContent,
			String fieldLenType,int detailNumLen,
			int detailLen,int detailPreLen,String seprator,String fieldName)throws Exception
	{
		
		ISOFieldPackager fieldPackager = null;
		Prefixer prefixer = null;
		Padder padder = null;
		if("r".equals(padType) && "1".equals(padContent)){
			if (dataType.equals("2"))
				padder=EBCRightPadder.SPACE_PADDER;	
			else
			    padder=RightPadder.SPACE_PADDER;
		}else if("l".equals(padType) ){//左补
			if( "0".equals(padContent)){//0
				if (dataType.equals("2"))
					padder=EBCLeftPadder.ZERO_PADDER;	
				else
				    padder=LeftPadder.ZERO_PADDER;
			}else if ( "1".equals(padContent)){//空格
				if (dataType.equals("2"))
					padder=EBCLeftPadder.SPACE_PADDER;	
				else
				    padder=LeftPadder.SPACE_PADDER;
			}
			
		}else{
			padder = NullPadder.INSTANCE;
		}
		
		switch (preType.charAt(0)) 
		{
		case '0': // - ASCII前缀
			prefixer = new AsciiPrefixer(preLen);
			break;
		case '1': // - BCD前缀
			prefixer = new BcdPrefixer(preLen);
			break;
		case '2': // - EBDC前缀
			prefixer = new EbcdicPrefixer(preLen);
			break;
		case '3': // - Binary前缀
			prefixer = new BinaryPrefixer(preLen);
			break;
		case '4': // Null前缀
			prefixer = NullPrefixer.INSTANCE;
			break;
		case '5':// ASCII右补空格前缀
			prefixer = new AsciiPrefixerRightPadSpace(preLen);
			break;
		default:
			prefixer = NullPrefixer.INSTANCE;
			break;
		}
		if(fieldName.equalsIgnoreCase("BF72")){
			System.out.println(fieldName);
		}
		char tem = fieldLenType.charAt(0);
		switch(fieldLenType.charAt(0)){
		case '1'://定长域
		case '3'://变长域
		{/************************** 333333333 ***************************/
			switch(dataType.charAt(0)){
			case '0':	//ASCII
				ISOStringFieldPackager fp = new ISOStringFieldPackager();		
				fp.setInterpreter(LiteralInterpreter.INSTANCE);
				fp.setLength(dataLen);
				fp.setPrefixer(prefixer);
				fp.setPadder(padder);
				fieldPackager = fp;
				break;
			case '1':	//1 - BCD码
				
				ISOStringFieldPackager fp1 = new ISOStringFieldPackager();	
				if("r".equals(padType) && "1".equals(padContent)){
					fp1.setInterpreter(BCDInterpreter.RIGHT_PADDED);	
					fp1.setPad(false);
				}else if("l".equals(padType) && "0".equals(padContent)){
					fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
					fp1.setPad(true);
				}else{
					fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
					fp1.setPad(true);
				}
				fp1.setLength(dataLen);
				fp1.setPrefixer(prefixer);
				fieldPackager = fp1;
				break;
			case '2':	//2 - EBDC码
				ISOStringFieldPackager fp2 = new ISOStringFieldPackager();		
				fp2.setInterpreter(EbcdicInterpreter.INSTANCE);
				fp2.setLength(dataLen);
				fp2.setPrefixer(prefixer);
				fp2.setPadder(padder);
				fieldPackager = fp2;
//				ISOStringFieldPackager fp2 = new ISOStringFieldPackager();		
//				fp2.setInterpreter(LiteralInterpreter.INSTANCE);
//				fp2.setLength(dataLen);
//				fp2.setPrefixer(prefixer);
//				fp2.setPadder(padder);
//				fieldPackager = fp2;
				break;
			case '3':	//3 - Binary二进制
				ISOBinaryFieldPackager bfp = new ISOBinaryFieldPackager();		
				bfp.setInterpreter(LiteralBinaryInterpreter.INSTANCE);
				bfp.setPrefixer(prefixer);
				bfp.setLength(dataLen);
				fieldPackager = bfp;
				break;
			}
			break;
			/************************** 333333333 ***************************/
		}
		case '2'://变长分隔域
			ISOSepFieldPackager sfp=new ISOSepFieldPackager();
			sfp.setSepStr(seprator);
			sfp.setLength(dataLen);
			fieldPackager=sfp;
			break;
		case '7'://明细域分隔符　NDot
			ISODetailSepFieldPackager dsfp = new ISODetailSepFieldPackager(seprator,detailNumLen);
			fieldPackager=dsfp;
			break;
		case '8'://变长分隔域(域前后都有分隔符　NDot
			ISOStartSepFieldPackager ssfp=new ISOStartSepFieldPackager();
			ssfp.setSepStr(seprator);
			ssfp.setLength(dataLen);
			fieldPackager=ssfp;
			break;
		case '4'://明细域
		{/************************** 4444444 ***************************/
			switch (preType.charAt(0)) {
			case '0'://acs
				prefixer=new MultiAsciiPrefixer(preLen,detailNumLen,detailLen,detailPreLen);
				break;
			case '1'://bcd
				prefixer=new MultiBcdPrefixer(preLen,detailNumLen,detailLen,detailPreLen);
				break;
			case '2'://ebcd
				prefixer=new MultilEbcdicPrefixer(preLen,detailNumLen,detailLen,detailPreLen);
				break;
			default:
				prefixer=new MultiAsciiPrefixer(preLen,detailNumLen,detailLen,detailPreLen);
				break;
			}
			
			switch(dataType.charAt(0)){
			case '0':	//ASCII
				ISOStringFieldPackager fp = new ISOStringFieldPackager();		
				fp.setInterpreter(LiteralInterpreter.INSTANCE);
				fp.setLength(dataLen);
				fp.setPrefixer(prefixer);
				fp.setPadder(padder);
				fieldPackager = fp;
				break;
			case '1':	//1 - BCD码
				
				ISOStringFieldPackager fp1 = new ISOStringFieldPackager();	
				if("r".equals(padType) && "1".equals(padContent)){
					fp1.setInterpreter(BCDInterpreter.RIGHT_PADDED);	
					fp1.setPad(false);
				}else if("l".equals(padType) && "0".equals(padContent)){
					fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
					fp1.setPad(true);
				}else{
					fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
					fp1.setPad(true);
				}
				fp1.setLength(dataLen);
				fp1.setPrefixer(prefixer);
				fieldPackager = fp1;
				break;
			case '2':	//2 - EBDC码
				ISOStringFieldPackager fp2 = new ISOStringFieldPackager();		
				fp2.setInterpreter(EbcdicInterpreter.INSTANCE);
				fp2.setLength(dataLen);
				fp2.setPrefixer(prefixer);
				fp2.setPadder(padder);
				fieldPackager = fp2;
//				ISOStringFieldPackager fp2 = new ISOStringFieldPackager();		
//				fp2.setInterpreter(LiteralInterpreter.INSTANCE);
//				fp2.setLength(dataLen);
//				fp2.setPrefixer(prefixer);
//				fp2.setPadder(padder);
//				fieldPackager = fp2;
				break;
			case '3':	//3 - Binary二进制
				ISOBinaryFieldPackager bfp = new ISOBinaryFieldPackager();		
				bfp.setInterpreter(LiteralBinaryInterpreter.INSTANCE);
				bfp.setPrefixer(prefixer);
				bfp.setLength(dataLen);
				fieldPackager = bfp;
				break;
			}
			break;
			/************************** 4444444 ***************************/
		}
		case '6':{
			switch (dataType.charAt(0)) {
			case '0'://asc
				ISOEntireFieldPackager efp = new ISOEntireFieldPackager();
				fieldPackager = efp;
				break;
			case '2'://ebc
				ISOEntireEBCDICFieldPackager efep = new ISOEntireEBCDICFieldPackager();
				fieldPackager = efep;
				break;
			case '3'://binary
				ISOEntireBinaryFieldPackager efbp = new ISOEntireBinaryFieldPackager();
				fieldPackager = efbp;
				break;
			default: 
				ISOEntireFieldPackager efp3 = new ISOEntireFieldPackager();
				fieldPackager = efp3;
				break;
			
			}
		}			
			break;
		}
		
		if(fieldPackager!=null){
			fieldPackager.setDescription(fieldName);
		}
		return fieldPackager;
	} 
	
	
	/* Create a FieldPacakger by input params
	 * @author: yangfeng
	 * @version $Id: 1.0 2007/10/25 10:34:16
	 * @Params:
	 * String preType:
	 * 	0 - ASCII前缀
	 *	1 - BCD前缀
	 *	2 - EBDC前缀
	 *	3 - Binary前缀
	 *	4 - Null前缀
	 * int preLen : 前缀位数
	 * String dataType : 数据类型
	 * 	0 - ASCII字符
	 *	1 - BCD码
	 *	2 - EBDC码
	 *	3 - Binary二进制
	 * int dataLen : 数据长度（数据最大长度）
	 * String pad : 填充方式
	 * 	0 - 左补0
	 *	1 - 右补空格
	 */ 
	public static ISOFieldPackager createFieldPackager(
			String preType, int preLen, 
			String dataType, int dataLen,
			String pad)
	{
		ISOFieldPackager fieldPackager = null;
		Prefixer prefixer = null;
		Padder padder = null;
		switch (preType.charAt(0)) 
		{
		case '0': // - ASCII前缀
			prefixer = new AsciiPrefixer(preLen);
			break;
		case '1': // - BCD前缀
			prefixer = new BcdPrefixer(preLen);
			break;
		case '2': // - EBDC前缀
			prefixer = new EbcdicPrefixer(preLen);
			break;
		case '3': // - Binary前缀
			prefixer = new BinaryPrefixer(preLen);
			break;
		case '4': // Null前缀
			prefixer = NullPrefixer.INSTANCE;
			break;
		default:
			prefixer = NullPrefixer.INSTANCE;
			break;
		}
		
		switch(pad.charAt(0))
		{
		case '0':	//0 - 左补0
			padder = LeftPadder.ZERO_PADDER;			
			break;
		case '1':	//1
			padder = RightPadder.SPACE_PADDER;
			break;
		default:
			padder = NullPadder.INSTANCE;
		}
		
		switch(dataType.charAt(0))
		{
		case '0':	//ASCII
			ISOStringFieldPackager fp = new ISOStringFieldPackager();		
			fp.setInterpreter(LiteralInterpreter.INSTANCE);
			fp.setLength(dataLen);
			fp.setPrefixer(prefixer);
			fp.setPadder(padder);
			fieldPackager = fp;
		
			break;
		case '1':	//1 - BCD码
			ISOStringFieldPackager fp1 = new ISOStringFieldPackager();		
			switch(pad.charAt(0))
			{
			case '0':	//0 - 左补0
				fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
				fp1.setPad(true);
				break;
			case '1':	//1 
				fp1.setInterpreter(BCDInterpreter.RIGHT_PADDED);	
				fp1.setPad(false);
				break;
			case '2':	//0 - 左补0
				fp1.setInterpreter(BCDInterpreter.LEFT_PADDED_F);	
				fp1.setPad(true);
				break;
			case '3':	//1 
				fp1.setInterpreter(BCDInterpreter.RIGHT_PADDED_F);	
				fp1.setPad(false);
				break;
			default:
				fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
				fp1.setPad(true);
			}
			fp1.setLength(dataLen);
			fp1.setPrefixer(prefixer);
			fieldPackager = fp1;
			break;
		case '2':	//2 - EBDC码
			ISOStringFieldPackager fp2 = new ISOStringFieldPackager();		
			fp2.setInterpreter(EbcdicInterpreter.INSTANCE);
			fp2.setLength(dataLen);
			fp2.setPrefixer(prefixer);
			fp2.setPadder(padder);
			fieldPackager = fp2;
			break;
		case '3':	//3 - Binary二进制
			ISOBinaryFieldPackager bfp = new ISOBinaryFieldPackager();		
			bfp.setInterpreter(LiteralBinaryInterpreter.INSTANCE);
			bfp.setPrefixer(prefixer);
			bfp.setLength(dataLen);
			fieldPackager = bfp;
			break;
		}

		return fieldPackager;
	}
	
	public static ISOFieldPackager createFieldPackager(Element node)
	{
		return null;
	}

	public static ISOBitMapPackager creactBitMapPackager(int len)
	{
		return new IFB_BITMAP(len, "");
	}
	public static ISOFieldPackager createFieldPackager(String shortCut) 
		throws Exception
	{
		ISOFieldPackager f;
		
		f = (ISOFieldPackager) Class.forName(shortCut).newInstance();	
		return f;
	}

	public static void createHeaderField(String fieldNo, String fieldName, String dataType, String dataLen) {
		// TODO Auto-generated method stub
		
	}
}
