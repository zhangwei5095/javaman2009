package org.ndot.ips.util;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_kernel
 * 
 * 文件名： GenJNL.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-7-1
 * 
 */
public class GenJNL {
	private int seq = 10000000;
	private String syn = "";

	public GenJNL() {
	}

	public int next() {
		synchronized (syn) {
			return (++seq > 99999999) ? 10000000 : seq;
		}
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
