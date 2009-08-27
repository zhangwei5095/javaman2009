package com.nasoft.test.iso.packager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import junit.framework.TestCase;

import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.packager.XMLPackager;
import com.nasoft.test.iso.TestUtils;

/**
 * Simple 'HelloWorld' like TestCase used to play with jUnit and to verify
 * build/junit system is running OK.
 */
public class Test extends TestCase
{
  private XMLPackager        xmlPackager;
  public static final String PREFIX = "D:/work/workspace/jPos/src/com/nasoft/test/iso/";

  private ISOMsg getMsg(String message) throws Exception
  {
    FileInputStream fis = new FileInputStream(PREFIX + message + ".xml");
    byte[] b = new byte[fis.available()];
    fis.read(b);
    ISOMsg m = new ISOMsg();
    m.setPackager(xmlPackager);
    m.unpack(b);
    fis.close();
    return m;
  }

  private byte[] getImage(String message) throws Exception
  {
    FileInputStream fis = new FileInputStream(PREFIX + message + ".bin");
    byte[] b = new byte[fis.available()];
    fis.read(b);
    fis.close();
    return b;
  }

  private void writeImage(String message, byte[] b) throws Exception
  {
    FileOutputStream fos = new FileOutputStream(PREFIX + message + ".run");
    fos.write(b);
    fos.close();
  }

  public void setUp() throws Exception
  {
    xmlPackager = new XMLPackager();
  }

  public Test(String name)
  {
    super(name);
  }

  // public void testXMLPackager() throws Exception
  // {
  // doTest(xmlPackager, "XMLPackager", "XMLPackager");
  // }

  public void testISO8583CUPS() throws Exception
  {
   // doTest(new ISO8583CUPSPackager(), "ISO8583CUPS", "ISO8583CUPS");
  }

  private void doTest(ISOPackager packager, String msg, String img)
      throws Exception
  {
    // Logger logger = new Logger();
    // logger.addListener (new SimpleLogListener (System.out));
    // packager.setLogger (logger, msg + "-m");

    ISOMsg m = getMsg(msg);
    m.setPackager(packager);
    byte[] p = m.pack();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    m.pack(out);

    assertTrue(Arrays.equals(out.toByteArray(), p));

    writeImage(img, p);

    byte[] b = getImage(img);
    TestUtils.assertEquals(b, p);

    ISOMsg m1 = new ISOMsg();
    // packager.setLogger (logger, msg + "-m1");
    m1.setPackager(packager);
    m1.unpack(b);
    TestUtils.assertEquals(b, m1.pack());

    ISOMsg m2 = new ISOMsg();
    m2.setPackager(packager);
    // packager.setLogger (logger, msg + "-m2");
    m2.unpack(new ByteArrayInputStream(out.toByteArray()));
    TestUtils.assertEquals(b, m2.pack());

    m2.setPackager(xmlPackager);
    b = m2.pack();
    FileOutputStream fout = new FileOutputStream(PREFIX + "ISO8583cups.run.xml");
    fout.write(b);
    fout.close();
  }
}
