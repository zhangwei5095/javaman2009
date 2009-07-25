package org.ndot.ips.util;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�ips_kernel
 * 
 * �ļ����� GenJNL.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-7-1
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
