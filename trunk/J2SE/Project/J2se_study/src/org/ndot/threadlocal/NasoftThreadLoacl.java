package org.ndot.threadlocal;

import java.util.Stack;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� NasoftThreadLoacl.java
 * 
 *<P>
 * 
 * �� ��:
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-11-9
 * 
 */
public class NasoftThreadLoacl {
	private ThreadLocal<Stack<String>> stk = new ThreadLocal<Stack<String>>() {
		@Override
		protected Stack<String> initialValue() {
			return new Stack<String>();
		}

	};

	public void doStk() {
		stk.get().push(Thread.currentThread().toString());
	}

	public void get() {
		Stack<String> s = stk.get();
		while (!s.isEmpty()) {
			System.out.println(s.pop());
		}
	}

	public static void main(String[] args) {
		NasoftThreadLoacl my = new NasoftThreadLoacl();
		Thread t1 = new Thread(new Channel(my, false));
		t1.start();
		Thread t2 = new Thread(new Channel(my, true));
		t2.start();
	}

}

class Channel implements Runnable {
	boolean isGet = false;
	NasoftThreadLoacl my;

	public Channel(NasoftThreadLoacl my, boolean isGet) {
		this.isGet = isGet;
		this.my = my;
	}

	public void run() {
		my.doStk();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (isGet) {
			my.get();
		}
	}

}
