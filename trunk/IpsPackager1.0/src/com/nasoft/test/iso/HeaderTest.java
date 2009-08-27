package com.nasoft.test.iso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.header.CUPSHeader;

public class HeaderTest extends TestCase
{
  public static final String PREFIX = "F:/workspace/jPos/src/com/nasoft/test/iso/";
  public void setUp() throws Exception
  {
   
  }

  public HeaderTest(String name)
  {
    super(name);
  }
  
  public void testCUPSHeaderXML2Bin() throws Exception
  {
    doTestXML2Bin(new CUPSHeader(), "CUPS");
  }
  public void testCUPSHeaderBin2XML() throws Exception
  {
    doTestBin2XML(new CUPSHeader(), "CUPS");
  }
  private byte[] getImage(String name) 
    throws IOException
  {
    
    FileInputStream fin = new FileInputStream(PREFIX + name + "Header.bin");
    byte[] data = new byte[(int)fin.available()];
    fin.read(data);
    return data;
  }
  
  private String getXMLStr(String name) 
    throws IOException
  {
    
    FileInputStream fin = new FileInputStream(PREFIX + name + "Header.xml");
    byte[] data = new byte[(int)fin.available()];
    fin.read(data);
    System.out.println(ISOUtil.hexdump(data));
    return new String(data);
  }
  
  private void writeOutImage(String name, byte[] out) 
  throws IOException
  {
  
    FileOutputStream fout = new FileOutputStream(PREFIX + name + "Header.out");
    fout.write(out);
    fout.close();
  }
  
  private void doTestXML2Bin(BaseHeader header, String name)
      throws Exception
  {
    String headerXML = getXMLStr(name);
    byte[] img = getImage(name);
    
    byte[] out = header.xmlPack(headerXML);
    writeOutImage(name, out);
    
    TestUtils.assertEquals(out, img);
  }
  private void doTestBin2XML(BaseHeader header, String name)
  throws Exception
{
    byte[] img = getImage(name);
    //String headerXML = getXMLStr(name);
    
    header.unpack(img);
    System.out.print(ISOUtil.hexdump(img));
    String xml = header.xmlUnpack();
    writeOutXML(name, xml);
}

  private void writeOutXML(String name, String xml) 
    throws IOException
  {
    FileOutputStream fout = new FileOutputStream(PREFIX + name + "Header.out.xml");
    fout.write(xml.getBytes());
    fout.close();// TODO Auto-generated method stub
    
  }
}