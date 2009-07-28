package org.ndot.cclovepp.db.tree;

import java.util.ArrayList;
import java.util.List;

public class DbConfigTreeElement implements TreeElement {
	private String name;
	private String id;

	private List<TreeElement> childrens;

	public DbConfigTreeElement(String name, String id) {
		super();
		this.name = name;
		this.id = id;
		this.childrens = new ArrayList<TreeElement>();
	}

	public DbConfigTreeElement(String name) {
		super();
		this.name = name;
		this.id = "noid";
		this.childrens = new ArrayList<TreeElement>();
	}

	public List getChildren() {
		return childrens;
	}

	public String getName() {
		return name;
	}

	public boolean hasChildren() {
		if (this.childrens.size() > 0)
			return true;
		return false;
	}

	public void addChildren(TreeElement child) {
		this.childrens.add(child);
	}

	public List<TreeElement> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<TreeElement> childrens) {
		this.childrens = childrens;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DbConfigTreeElement(String name, String id, List<TreeElement> childrens) {
		super();
		this.name = name;
		this.id = id;
		this.childrens = childrens;
	}

	public void removeChildren(TreeElement child) {
		this.childrens.remove(child);		
	}

}
