package org.ndot.cclovepp.db.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.ndot.cclovepp.db.tree.DbConfigTreeElement;
import org.ndot.cclovepp.db.tree.TreeElement;

public class DBConfigBuilder {
	private static DBConfigBuilder instance;

	private SAXBuilder builder;

	private Element rootElement;

	private Document dbDocument;

	private DBConfigBuilder() {
		builder = new SAXBuilder();
		try {
			File flie = new File("NDot-db-Config.xml");
			dbDocument = builder.build(flie);
			rootElement = dbDocument.getRootElement();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static DBConfigBuilder getInstance() {
		if (instance == null) {
			instance = new DBConfigBuilder();
		}
		return instance;
	}

	public List<TreeElement> getDBList() throws Exception {
		List<TreeElement> dblist = new ArrayList<TreeElement>();
		List<Element> elemList = rootElement.getChildren("db");
		for (Element elem : elemList) {
			dblist.add(elementToDatabaseConfig(elem));
		}
		return dblist;
	}

	public void addDBConfig(DatabaseConfig dbc) throws Exception, IOException {
		Element dbcElement = new Element("db");

		Attribute idAttr = new Attribute("id", String.valueOf(dbc.getId()));
		Attribute portAttr = new Attribute("port", String
				.valueOf(dbc.getPort()));
		Attribute silentAttr = new Attribute("silent", String.valueOf(dbc
				.isSilent()));
		Attribute tlsAttr = new Attribute("tls", String.valueOf(dbc.isTls()));
		Attribute tracAttr = new Attribute("trac", String.valueOf(dbc.isTrac()));

		dbcElement.setAttribute(idAttr);
		dbcElement.setAttribute(portAttr);
		dbcElement.setAttribute(silentAttr);
		dbcElement.setAttribute(tlsAttr);
		dbcElement.setAttribute(tracAttr);

		Element hostElement = new Element("host");
		hostElement.setText(dbc.getHost());
		Element dbpathElement = new Element("dbpath");
		dbpathElement.setText(dbc.getDbpath());
		Element dbnameElement = new Element("dbname");
		dbnameElement.setText(dbc.getDbname());

		dbcElement.addContent(hostElement);
		dbcElement.addContent(dbpathElement);
		dbcElement.addContent(dbnameElement);
		this.rootElement.addContent(dbcElement);

		XMLOutputter outp = new XMLOutputter();
		Format format = Format.getCompactFormat();
		format.setEncoding("gb2312"); // 设置XML文件的字符为GB2312
		format.setIndent("  "); // 设置XML文件的缩排为2个空格
		outp.setFormat(format);
		File configFile = new File("NDot-db-Config.xml");

		outp.output(this.dbDocument, new FileOutputStream(configFile)); // 输出XML文档

	}

	private DatabaseConfig elementToDatabaseConfig(Element elem)
			throws Exception {
		DatabaseConfig dbc = new DatabaseConfig();

		dbc.setId(elem.getAttributeValue("id"));
		dbc.setHost(elem.getChildText("host"));
		dbc.setDbpath(elem.getChildText("dbpath"));
		dbc.setDbname(elem.getChildText("dbname"));

		dbc.setPort(elem.getAttribute("port").getIntValue());
		dbc.setSilent(elem.getAttribute("silent").getBooleanValue());
		dbc.setTls(elem.getAttribute("tls").getBooleanValue());
		dbc.setTrac(elem.getAttribute("trac").getBooleanValue());

		// System.out.println("------------------------");
		// System.out.println(dbc.getHost()); System.out.println(dbc.getPort());
		// System.out.println(dbc.getDbname());
		// System.out.println(dbc.getPort());
		// System.out.println(dbc.isSilent());
		// System.out.println("------------------------");

		DbConfigTreeElement host = new DbConfigTreeElement("host - "
				+ dbc.getHost());
		DbConfigTreeElement dbpath = new DbConfigTreeElement("dbpath - "
				+ dbc.getDbpath());
		DbConfigTreeElement dbname = new DbConfigTreeElement("dbname - "
				+ dbc.getDbname());
		DbConfigTreeElement port = new DbConfigTreeElement("port - "
				+ dbc.getPort());
		DbConfigTreeElement silent = new DbConfigTreeElement("silent - "
				+ dbc.isSilent());
		DbConfigTreeElement tls = new DbConfigTreeElement("tls - "
				+ dbc.isTls());
		DbConfigTreeElement trac = new DbConfigTreeElement("trac - "
				+ dbc.isTrac());

		dbc.addChildren(host);
		dbc.addChildren(dbpath);
		dbc.addChildren(dbname);
		dbc.addChildren(port);
		dbc.addChildren(silent);
		dbc.addChildren(tls);
		dbc.addChildren(trac);

		return dbc;
	}

	public static void main(String[] args) {
		DBConfigBuilder d = DBConfigBuilder.getInstance();
		DatabaseConfig dbc = new DatabaseConfig();
		dbc.setId("002");
		dbc.setHost("www.baidu.com");
		dbc.setDbpath("D://dnotdb/test");
		dbc.setDbname("test");

		try {
			d.getDBList();
			d.addDBConfig(dbc);
			d.getDBList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
