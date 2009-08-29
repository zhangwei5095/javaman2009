package org.ndot.spring25.annotation.scan;

import javax.annotation.Resource;

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
 * �ļ����� DBServices.java
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
 * ����ʱ��: 2009-8-29
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
