package org.ndot.spring25.scop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� InjectWebScopBean.java
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
 * ����ʱ��: 2009-8-30
 * 
 */
@Service(value = "InjectWebScopBean")
public class InjectWebScopBean {
	@Autowired
	private RequestScopBean reqBean;

	public RequestScopBean getReqBean() {
		return reqBean;
	}

	public void setReqBean(RequestScopBean reqBean) {
		this.reqBean = reqBean;
	}
}
