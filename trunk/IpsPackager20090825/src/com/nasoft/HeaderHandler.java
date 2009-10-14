package com.nasoft;

import java.util.HashMap;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOHeader;

public class HeaderHandler
{
 static private HashMap packagers = new HashMap();
  
  static public byte[] pack(String data, String name) 
    throws ISOException
  {    
    ISOHeader packager = getPackagerByName(name);
    
    return packager.pack();
  }
  
 
  private static ISOHeader getPackagerByName(String name) 
    throws ISOException
  {
    if(packagers.get(name)==null)
    {
      synchronized(HeaderHandler.class)
      {
        try
        {
          packagers.put(name, createNewInstance(name));
        } catch (Exception e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
          throw new ISOException(e);
        } 
      }
    }
    return (ISOHeader)packagers.get(name);
  }


  private static Object createNewInstance(String name) 
    throws ClassNotFoundException, InstantiationException, IllegalAccessException
  {
    Class c = Class.forName("com.nasoft.iso.header"+name+"header");
    return c.newInstance();
  }

}
