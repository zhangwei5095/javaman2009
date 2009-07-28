package org.ndot.cclovepp.db.tree;

import java.util.List;

public interface TreeElement {
	String getId();//�ڵ�id
	String getName();//�ڵ�����
	boolean hasChildren();//�Ƿ�������
	List getChildren();//�����������
	void addChildren(TreeElement child);
	void removeChildren(TreeElement child);
}
