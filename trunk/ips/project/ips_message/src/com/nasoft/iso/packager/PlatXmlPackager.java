/**
 * 
 */
package com.nasoft.iso.packager;

import java.io.ByteArrayInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.nasoft.core.Configuration;
import com.nasoft.core.ConfigurationException;
import com.nasoft.core.ReConfigurable;
import com.nasoft.iso.IF_TBASE;
import com.nasoft.iso.ISOBasePackager;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOFieldPackager;
import com.nasoft.iso.ISOMsgFieldPackager;
import com.nasoft.iso.ISOUtil;

/**
 * TODO 平台packager，用xml字符串描述ISOMessage的每个域
 * @date 2007-11-20
 *
 */
public class PlatXmlPackager extends ISOBasePackager implements ReConfigurable {

	/* (non-Javadoc)
	 * @see com.nasoft.core.Configurable#setConfiguration(com.nasoft.core.Configuration)
	 */
	
	private int maxValidField=128;
    private boolean emitBitmap=true;
    private int bitmapField=1;
    
    public PlatXmlPackager() throws ISOException
    {
        super();
    }
    
    public PlatXmlPackager(String formatXml) throws ISOException
    {
        this();
        parseFormat(formatXml);
    }
    
    protected int getMaxValidField()
    {
        return maxValidField;
    }

    protected boolean emitBitMap()
    {
        return emitBitmap;
    }

    protected ISOFieldPackager getBitMapfieldPackager()
    {
        return fld[bitmapField];
    }
    
    public void parseFormat(String formatXml) throws ISOException
    {
        try {
        	InputSource src = new InputSource(new ByteArrayInputStream(formatXml.getBytes()));
            createXMLReader().parse(src);  
        } 
        catch (Exception e) {
            throw new ISOException(e);
        }
    }
    
    private XMLReader createXMLReader () throws ISOException {
        try{
        	XMLReader reader = ISOUtil.genXmlReader();

            reader.setFeature ("http://xml.org/sax/features/validation", true);
            PlatXmlContentHandler handler = new PlatXmlContentHandler();
            reader.setContentHandler(handler);
            reader.setErrorHandler(handler);
            return reader;
        }catch (Exception e) {
			throw new ISOException(e);
		}
    	
    }
    
	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
		// TODO Auto-generated method stub

	}
	
	private void setPlatXmlPackagerParams (Attributes atts)
    {
        String maxField  = atts.getValue("maxValidField");
        String emitBmap  = atts.getValue("emitBitmap");
        String bmapfield = atts.getValue("bitmapField");

        if (maxField != null)
            maxValidField = Integer.parseInt(maxField); 

        if (emitBmap != null)
            emitBitmap = Boolean.valueOf(emitBmap).booleanValue();

        if (bmapfield != null)
            bitmapField = Integer.parseInt(bmapfield);
    }
	
	public class PlatXmlContentHandler extends DefaultHandler
    {
        private Stack fieldStack;

        public void startDocument()
        {
            fieldStack = new Stack();
        }

        public void endDocument() throws SAXException
        {
            if (!fieldStack.isEmpty())
            {
                throw new SAXException ("Format error in XML Field Description File");
            }
        }

        public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
                throws SAXException
        {
            try
            {
                String id   = atts.getValue("id");
                String type = atts.getValue("class");
                String name = atts.getValue("name");
                String size = atts.getValue("length");
                String pad  = atts.getValue("pad");
                // Modified for using IF_TBASE
                String token = atts.getValue("token");

                if (localName.equals("isopackager"))
                {
                    // Stick a new Hashtable on stack to collect the fields
                    fieldStack.push(new Hashtable());

                    setPlatXmlPackagerParams (atts);
                }

                if (localName.equals("isofieldpackager"))
                {
                    /*
                    For a isofield packager node push the following fields
                    onto the stack.
                    1) an Integer indicating the field ID
                    2) an instance of the specified ISOFieldPackager class
                    3) an instance of the specified ISOBasePackager (msgPackager) class
                    4) a Hashtable to collect the subfields
                    */
                    String packager = atts.getValue("packager");

                    fieldStack.push(new Integer(id));

                    ISOFieldPackager f;
                    f = (ISOFieldPackager) Class.forName(type).newInstance();   
                    f.setDescription(name);
                    f.setLength(Integer.parseInt(size));
                    f.setPad(new Boolean(pad).booleanValue());
                    // Modified for using IF_TBASE
                    if( f instanceof IF_TBASE){
                      ((IF_TBASE)f).setToken( token );
                    }
                    fieldStack.push(f);

                    ISOBasePackager p;
                    p = (ISOBasePackager) Class.forName(packager).newInstance();
                    if (p instanceof PlatXmlPackager)
                    {
                    	PlatXmlPackager gp = (PlatXmlPackager) p;
                        gp.setPlatXmlPackagerParams (atts);
                    }
                    fieldStack.push(p);

                    fieldStack.push(new Hashtable());
                }

                if (localName.equals("isofield"))
                {
                    Class c = Class.forName(type);
                    ISOFieldPackager f;
                    f = (ISOFieldPackager) c.newInstance();     
                    f.setDescription(name);
                    f.setLength(Integer.parseInt(size));
                    f.setPad(new Boolean(pad).booleanValue());
                    // Modified for using IF_TBASE
                    if( f instanceof IF_TBASE){
                      ((IF_TBASE)f).setToken( token );
                    }
                    // Insert this new isofield into the Hashtable
                    // on the top of the stack using the fieldID as the key
                    Hashtable ht = (Hashtable) fieldStack.peek();
                    ht.put(new Integer(id), f);
                }
            }
            catch (Exception ex)
            {
                throw new SAXException(ex);
            }
        }

        /**
         * Convert the ISOFieldPackagers in the Hashtable
         * to an array of ISOFieldPackagers
         */
        private ISOFieldPackager[] makeFieldArray(Hashtable tab)
        {
            int maxField = 0;

            // First find the largest field number in the Hashtable
            for (Enumeration e=tab.keys(); e.hasMoreElements(); )
            {
                int n = ((Integer)e.nextElement()).intValue();
                if (n > maxField) maxField = n;
            }

            // Create the array
            ISOFieldPackager fld[] = new ISOFieldPackager[maxField+1];

            // Populate it
            for (Enumeration e=tab.keys(); e.hasMoreElements(); )
            {
                Integer key = (Integer) e.nextElement();
                fld[key.intValue()] = (ISOFieldPackager)tab.get(key);
            }
            return fld;
        }

        public void endElement(String namespaceURI, String localName, String qName)
        {
            if (localName.equals("isopackager"))
            {
                Hashtable tab  = (Hashtable)fieldStack.pop();

                setFieldPackager(makeFieldArray(tab));
            }

            if (localName.equals("isofieldpackager"))
            {
                // Pop the 4 entries off the stack in the correct order
                Hashtable tab = (Hashtable)fieldStack.pop();

                ISOBasePackager msgPackager = (ISOBasePackager) fieldStack.pop();
                msgPackager.setFieldPackager (makeFieldArray(tab));
                msgPackager.setLogger (getLogger(), "PlatXml Packager");

                ISOFieldPackager fieldPackager = (ISOFieldPackager) fieldStack.pop();

                Integer fno = (Integer) fieldStack.pop();

                // Create the ISOMsgField packager with the retrieved msg and field Packagers
                ISOMsgFieldPackager mfp = 
                    new ISOMsgFieldPackager(fieldPackager, msgPackager);

                // Add the newly created ISOMsgField packager to the
                // lower level field stack

                tab=(Hashtable)fieldStack.peek();
                tab.put(fno, mfp);
            }
        }

        // ErrorHandler Methods
        public void error (SAXParseException ex) throws SAXException
        {
            throw ex;
        }

        public void fatalError (SAXParseException ex) throws SAXException
        {
            throw ex;
        }
    }

}
