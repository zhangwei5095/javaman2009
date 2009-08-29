package org.ndot.spring25.annotation.qualifier;

import org.springframework.beans.factory.annotation.Autowired;

public class UseMyQualifier {
	@Autowired
	@MyQualifier(sex = "mbean", age = "10")
	private MyBean bean1;
	private MyBean bean2;

	@Autowired
	public void setbean2(@MyQualifier(sex = "fbean", age = "25") MyBean bean2) {
		this.bean2 = bean2;
	}

	public MyBean getBean1() {
		return bean1;
	}

	public void setBean1(MyBean bean1) {
		this.bean1 = bean1;
	}

	public MyBean getBean2() {
		return bean2;
	}

	public void setBean2(MyBean bean2) {
		this.bean2 = bean2;
	}

}