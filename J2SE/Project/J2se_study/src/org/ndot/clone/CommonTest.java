package org.ndot.clone;

import java.io.Serializable;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：IpsTester
 * 
 *<P>
 * 
 * 文件名： CommonTest.java
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
 * 创建时间: 2009-10-13
 * 
 */
public class CommonTest implements Cloneable,Serializable{

	protected byte[] header = "000000000".getBytes();

	public class HeaderField {
		public int Len;

		private String Type;

		private String Name;

		private int Index;

		private int StartPos = 0;

		private String idenFlag = null;

		public byte[] getValue() {
			byte[] value = new byte[Len];
			System.arraycopy(header, StartPos, value, 0, Len);
			return value;
		}

		public void setValue(byte[] value) {
			if (value != null) {
				System.arraycopy(value, 0, header, StartPos,
						value.length > Len ? Len : value.length);
			}
		}

		public HeaderField(int len, String type, byte[] value, String name,
				int index) {
			this.Len = len;
			this.Type = type;
			this.Name = name;
			this.Index = index;
		}

		public HeaderField(int len, String type, byte[] value, String name,
				int index, String idenFlag) {
			this.Len = len;
			this.Type = type;
			this.Name = name;
			this.Index = index;
			this.idenFlag = idenFlag;
		}
	}

	public Object clone() {
		try {
			CommonTest o = (CommonTest) super.clone();
//			System.out.println("this: "+this.header.hashCode());
//			System.out.println("o: "+o.header.hashCode());
			o.header = this.header.clone();
//			System.out.println("clone: "+o.header.hashCode());
//			System.out.println("this "+ this.hashCode() +": colne "+o.hashCode());
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public byte[] genField(String value) {
		HeaderField f1 = new HeaderField(2, "String", "aaa".getBytes(),
				"Field1", 0);
		f1.setValue(value.getBytes());
		System.out.println();
		return this.header;
	}

	public static void main(String[] args){
		t2();
	}

	public static void t2() {
		CommonTest com = new CommonTest();

		try {
			CommonTest com2 = (CommonTest) com.clone();
			CommonTest com3 = (CommonTest) com.clone();
			byte[] h = com2.genField("12");
			com3.genField("33");
			System.out.println(new String(h));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void t1() {
		byte[] a = "abcd".getBytes();
		byte[] b = a.clone();
		b[1] = "a".getBytes()[0];
		b = "123".getBytes();

		String aa = "abcd";
		String bb = aa;
		bb = "aafff";
		System.out.println();
	}
}
