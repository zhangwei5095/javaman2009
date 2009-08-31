package org.ndot.struts216;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Strut216
 * 
 *<P>
 * 
 * 文件名： LoginAction.java
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
 * 创建时间: 2009-8-31
 * 
 */
public class LoginAction {
	// 下面是Action内用于封装用户请求参数的两个属性

	private String username;

	private String password;

	// username属性对应的getter方法

	public String getUsername()

	{

		return username;

	}

	// username属性对应的setter方法

	public void setUsername(String username)

	{

		this.username = username;

	}

	// password属性对应的getter方法

	public String getPassword()

	{

		return password;

	}

	// password属性对应的setter方法

	public void setPassword(String password)

	{

		this.password = password;

	}

	// 处理用户请求的execute方法

	public String execute() throws Exception

	{

		// 当用户请求参数的username等于scott，密码请求参数为tiger时，
		// 返回success 字符串
		// 否则返回error字符串

		if (getUsername().equals("NDot")

		&& getPassword().equals("javaman")) {

			return "success";

		} else {

			return "error";

		}

	}

}
