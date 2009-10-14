package com.nasoft;

import java.util.HashMap;

public class CupsComHelper {
	static private HashMap ResponseMsgType = new HashMap();
	  static
	  {
	    ResponseMsgType.put("0110", "0110");
	    ResponseMsgType.put("0210", "0210");
	    ResponseMsgType.put("0230", "0230");
	    ResponseMsgType.put("0130", "0130");
	    ResponseMsgType.put("0430", "0430");
	    ResponseMsgType.put("0432", "0432");
	    ResponseMsgType.put("0530", "0530");
	    ResponseMsgType.put("0532", "0532");
	    ResponseMsgType.put("0630", "0630");
	    ResponseMsgType.put("0810", "0810");
	    ResponseMsgType.put("0830", "0830");
	  }

	  static public String GetCupsRspFlag(String rejectCode, String msgType)
	  {
	    if (ResponseMsgType.containsKey(msgType) || !"0000".equals(rejectCode))
	    {
	      return "200000"; //response
	    } else
	    {
	      return "100000"; //request
	    }
	  }
	  
	  public static String ConvertRejectCode(String rejectCode)
	  {
	      if (!rejectCode.equals("00000"))
	      {
	        // Rejected by CUPS
	       return "-3";
	      }else
	      {
	    	  return "0";
	      }
	  }
}
