package org.ndot.generic;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� TestGen1.java
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
 * ����ʱ��: 2009-12-25
 * 
 */
public class TestGen1 {

	public <T> T ifthenelse(boolean b, T c, T d) {
		if (b)
			return c;
		else
			return d;
	}

	/**
	 * <p>
	 * ����:
	 * 
	 * <p>
	 * 
	 * @param args
	 *            <p>
	 *            ����:����
	 *            <p>
	 */
	public static void main(String[] args) {
		TestGen1 t = new TestGen1();
		String a = t.ifthenelse(true, "NDot", "PanPan");
		System.out.println(a);
		int c = t.ifthenelse(false, 27, 29);
		System.out.println("" + c);

	}

}
