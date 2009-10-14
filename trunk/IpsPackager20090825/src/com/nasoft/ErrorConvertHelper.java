package com.nasoft;

public class ErrorConvertHelper
{
  public static String TibcoTcpErr2SysErr(String tError)
  {
    int iErr = Integer.parseInt(tError);
    String ret = "-2";
    switch(iErr)
    {
      case 100008:
      case 100009:
      case 100015:
      case 100018:
        ret = "-1";
        break;
    }
    
    return ret;
    
  }
  
}
