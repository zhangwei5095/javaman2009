package org.ndot.cclovepp.db.tree;

import java.util.List;

public interface TreeElement {
	String getId();//节点id
	String getName();//节点名称
	boolean hasChildren();//是否有子孙
	List getChildren();//获得所有子孙
	void addChildren(TreeElement child);
	void removeChildren(TreeElement child);
}
