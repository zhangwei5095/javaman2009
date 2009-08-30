package org.ndot.spring25.scop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： InjectWebScopBean.java
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
 * 创建时间: 2009-8-30
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
