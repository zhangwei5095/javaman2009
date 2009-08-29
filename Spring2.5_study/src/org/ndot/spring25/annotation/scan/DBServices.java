package org.ndot.spring25.annotation.scan;

import javax.annotation.Resource;

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
 * 文件名： DBServices.java
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
 * 创建时间: 2009-8-29
 * 
 */
@Service(value = "dBservices")
public class DBServices {
	@Autowired
	private ATableDAO adao;
	@Resource
	private BTableDAO bdao;

	public ATableDAO getAdao() {
		return adao;
	}

	public void setAdao(ATableDAO adao) {
		this.adao = adao;
	}

	public BTableDAO getBdao() {
		return bdao;
	}

	public void setBdao(BTableDAO bdao) {
		this.bdao = bdao;
	}
}
