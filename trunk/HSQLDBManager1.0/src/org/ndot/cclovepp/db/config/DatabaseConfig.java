package org.ndot.cclovepp.db.config;

import java.util.ArrayList;
import java.util.List;

import org.ndot.cclovepp.db.tree.TreeElement;

public class DatabaseConfig implements TreeElement {
	private String id;

	private List<TreeElement> childrens;

	private String host;// -address | name|number | any | server inet address

	private int port;// -port | number | 9001/544 | port at which server

	// listens |

	private String dbpath;// -database.i | [type]spec | 0=test | path of

	// database i |

	private String dbname;// -dbname.i | alias | -- | url alias for database i

	// |

	boolean silent;// -silent | true|false | true | false => display all

	// queries |

	boolean trac;// -trace | true|false | false | display JDBC trace messages

	// |

	boolean tls;// -tls | true|false | false | TLS/SSL (secure) sockets |

	boolean no_system_exit;// -no_system_exit| true|false | false | do not

	// issue System.exit() |
	public DatabaseConfig() {
		this.port = 9001;
		this.silent = true;
		this.trac = false;
		this.tls = false;
		this.no_system_exit = false;
		childrens = new ArrayList<TreeElement>();
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbpath() {
		return dbpath;
	}

	public void setDbpath(String dbpath) {
		this.dbpath = dbpath;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public boolean isNo_system_exit() {
		return no_system_exit;
	}

	public void setNo_system_exit(boolean no_system_exit) {
		this.no_system_exit = no_system_exit;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public boolean isTls() {
		return tls;
	}

	public void setTls(boolean tls) {
		this.tls = tls;
	}

	public boolean isTrac() {
		return trac;
	}

	public void setTrac(boolean trac) {
		this.trac = trac;
	}

	public List getChildren() {
		return childrens;
	}

	public String getName() {
		return dbname;
	}

	public boolean hasChildren() {
		if (this.childrens.size() > 0)
			return true;
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DatabaseConfig(String id, String host, int port, String dbpath,
			String dbname, boolean silent, boolean trac, boolean tls,
			boolean no_system_exit) {
		super();
		this.id = id;
		this.host = host;
		this.port = port;
		this.dbpath = dbpath;
		this.dbname = dbname;
		this.silent = silent;
		this.trac = trac;
		this.tls = tls;
		this.no_system_exit = no_system_exit;
		childrens = new ArrayList<TreeElement>();
	}

	
	public void setChildrens(List<TreeElement> childrens) {
		this.childrens = childrens;
	}

	public void addChildren(TreeElement child) {
		this.childrens.add(child);

	}

	public void removeChildren(TreeElement child) {
		this.childrens.remove(child);
	}

}
