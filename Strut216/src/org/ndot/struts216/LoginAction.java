package org.ndot.struts216;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Strut216
 * 
 *<P>
 * 
 * �ļ����� LoginAction.java
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
 * ����ʱ��: 2009-8-31
 * 
 */
public class LoginAction {
	// ������Action�����ڷ�װ�û������������������

	private String username;

	private String password;

	// username���Զ�Ӧ��getter����

	public String getUsername()

	{

		return username;

	}

	// username���Զ�Ӧ��setter����

	public void setUsername(String username)

	{

		this.username = username;

	}

	// password���Զ�Ӧ��getter����

	public String getPassword()

	{

		return password;

	}

	// password���Զ�Ӧ��setter����

	public void setPassword(String password)

	{

		this.password = password;

	}

	// �����û������execute����

	public String execute() throws Exception

	{

		// ���û����������username����scott�������������Ϊtigerʱ��
		// ����success �ַ���
		// ���򷵻�error�ַ���

		if (getUsername().equals("NDot")

		&& getPassword().equals("javaman")) {

			return "success";

		} else {

			return "error";

		}

	}

}
