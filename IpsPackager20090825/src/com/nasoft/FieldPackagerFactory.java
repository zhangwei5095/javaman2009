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
	 * @param preType ǰ׺����
	 * 			0 - ASCIIǰ׺
	 *			1 - BCDǰ׺
	 *			2 - EBDCǰ׺
	 *			3 - Binaryǰ׺
	 *			4 - Nullǰ׺
	 *			5 - ASCII�Ҳ��ո�ǰ׺
	 * @param preLen ǰ׺λ��
	 * @param dataType��������
	 * 			0 - ASCII�ַ�
	 *			1 - BCD��
	 *			2 - EBDC��
	 *			3 - Binary������
	 * @param dataLen ���ݳ��ȣ�������󳤶ȣ�
	 * @param padType ��䷽ʽ l - �� r - �Ҳ�
	 * @param padContent �������0 - 0 1 - �ո�
	 * @param fieldLenType ����������
	 * 			1-������
	 *			2-�䳤�ָ���
	 *			3-�䳤��
	 *			4-��ϸ��
	 *			6-�����������һֱ�����Ľ���
	 *          7-��ϸ��ָ���
	 *          8-�䳤�ָ���(��ǰ���зָ�����
	 * @param detailNumLen ��ϸ��������λ��
	 * @parametailLen ��ϸ�򳤶�
	 * @param detailPreLen ��ϸ��ǰ׺����
	 * @param seprator �ָ���
	 * @param fieldName ������
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
		}else if("l".equals(padType) ){//��
			if( "0".equals(padContent)){//0
				if (dataType.equals("2"))
					padder=EBCLeftPadder.ZERO_PADDER;	
				else
				    padder=LeftPadder.ZERO_PADDER;
			}else if ( "1".equals(padContent)){//�ո�
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
		case '0': // - ASCIIǰ׺
			prefixer = new AsciiPrefixer(preLen);
			break;
		case '1': // - BCDǰ׺
			prefixer = new BcdPrefixer(preLen);
			break;
		case '2': // - EBDCǰ׺
			prefixer = new EbcdicPrefixer(preLen);
			break;
		case '3': // - Binaryǰ׺
			prefixer = new BinaryPrefixer(preLen);
			break;
		case '4': // Nullǰ׺
			prefixer = NullPrefixer.INSTANCE;
			break;
		case '5':// ASCII�Ҳ��ո�ǰ׺
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
		case '1'://������
		case '3'://�䳤��
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
			case '1':	//1 - BCD��
				
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
			case '2':	//2 - EBDC��
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
			case '3':	//3 - Binary������
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
		case '2'://�䳤�ָ���
			ISOSepFieldPackager sfp=new ISOSepFieldPackager();
			sfp.setSepStr(seprator);
			sfp.setLength(dataLen);
			fieldPackager=sfp;
			break;
		case '7'://��ϸ��ָ�����NDot
			ISODetailSepFieldPackager dsfp = new ISODetailSepFieldPackager(seprator,detailNumLen);
			fieldPackager=dsfp;
			break;
		case '8'://�䳤�ָ���(��ǰ���зָ�����NDot
			ISOStartSepFieldPackager ssfp=new ISOStartSepFieldPackager();
			ssfp.setSepStr(seprator);
			ssfp.setLength(dataLen);
			fieldPackager=ssfp;
			break;
		case '4'://��ϸ��
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
			case '1':	//1 - BCD��
				
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
			case '2':	//2 - EBDC��
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
			case '3':	//3 - Binary������
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
	 * 	0 - ASCIIǰ׺
	 *	1 - BCDǰ׺
	 *	2 - EBDCǰ׺
	 *	3 - Binaryǰ׺
	 *	4 - Nullǰ׺
	 * int preLen : ǰ׺λ��
	 * String dataType : ��������
	 * 	0 - ASCII�ַ�
	 *	1 - BCD��
	 *	2 - EBDC��
	 *	3 - Binary������
	 * int dataLen : ���ݳ��ȣ�������󳤶ȣ�
	 * String pad : ��䷽ʽ
	 * 	0 - ��0
	 *	1 - �Ҳ��ո�
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
		case '0': // - ASCIIǰ׺
			prefixer = new AsciiPrefixer(preLen);
			break;
		case '1': // - BCDǰ׺
			prefixer = new BcdPrefixer(preLen);
			break;
		case '2': // - EBDCǰ׺
			prefixer = new EbcdicPrefixer(preLen);
			break;
		case '3': // - Binaryǰ׺
			prefixer = new BinaryPrefixer(preLen);
			break;
		case '4': // Nullǰ׺
			prefixer = NullPrefixer.INSTANCE;
			break;
		default:
			prefixer = NullPrefixer.INSTANCE;
			break;
		}
		
		switch(pad.charAt(0))
		{
		case '0':	//0 - ��0
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
		case '1':	//1 - BCD��
			ISOStringFieldPackager fp1 = new ISOStringFieldPackager();		
			switch(pad.charAt(0))
			{
			case '0':	//0 - ��0
				fp1.setInterpreter(BCDInterpreter.LEFT_PADDED);	
				fp1.setPad(true);
				break;
			case '1':	//1 
				fp1.setInterpreter(BCDInterpreter.RIGHT_PADDED);	
				fp1.setPad(false);
				break;
			case '2':	//0 - ��0
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
		case '2':	//2 - EBDC��
			ISOStringFieldPackager fp2 = new ISOStringFieldPackager();		
			fp2.setInterpreter(EbcdicInterpreter.INSTANCE);
			fp2.setLength(dataLen);
			fp2.setPrefixer(prefixer);
			fp2.setPadder(padder);
			fieldPackager = fp2;
			break;
		case '3':	//3 - Binary������
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
