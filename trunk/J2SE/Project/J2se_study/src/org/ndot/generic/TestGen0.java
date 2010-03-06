package org.ndot.generic;

import java.util.Hashtable;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： TestGen0.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-12-25
 * 
 */
class TestGen0<K, V> {
	public Hashtable<K, V> h = new Hashtable<K, V>();

	public void put(K k, V v) {
		h.put(k, v);
	}

	public V get(K k) {
		return h.get(k);
	}

	public static void main(String args[]) {
		TestGen0<String, String> t = new TestGen0<String, String>();
		t.put("key", "value");
		String s = t.get("key");
		System.out.println(s);
	}
}
