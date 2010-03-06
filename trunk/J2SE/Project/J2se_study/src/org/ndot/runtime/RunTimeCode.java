package org.ndot.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import com.sun.tools.javac.Main;

public class RunTimeCode {
	static Main javac = new Main();

	public static void main(String[] args) {
		try {
			String path = System.getProperty("user.dir");
			path = "D:\\workspaces\\UTF8Workspaces\\J2se_study\\WebRoot\\WEB-INF\\classes\\";
			// File file = File.createTempFile("RunTime", ".java", new
			// File(path));
			File file = new File(path + "RunTime.java");
			// System.out.println(System.getProperty("user.dir"));
			String filename = file.getName();
			String classname = filename.substring(0, filename.indexOf('.'));
			PrintWriter out = new PrintWriter(new FileOutputStream(file));
			out.println("public class " + classname + "{");
			out.println("public static void main(String[] args){");
			out.println("System.out.println(\"Yes!\");");
			out.println("}}");
			out.flush();
			out.close();
			String[] arg = new String[] { "-d", path, path + filename };
			int status = javac.compile(arg);
			Class cls = Class.forName(classname);
			Method main = cls.getMethod("main", new Class[] { String[].class });
			main.invoke(null, new Object[] { new String[0] });
			file.delete();
			file = new File(classname + ".class");
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
