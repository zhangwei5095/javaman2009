package org.ndot.generic;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： TestGen1.java
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
public class TestGen1 {

	public <T> T ifthenelse(boolean b, T c, T d) {
		if (b)
			return c;
		else
			return d;
	}

	/**
	 * <p>
	 * 功能:
	 * 
	 * <p>
	 * 
	 * @param args
	 *            <p>
	 *            作者:孙金城
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
