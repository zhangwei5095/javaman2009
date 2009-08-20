package org.ndot.spring25.container;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� NDotXMLApplicationContext.java
 * 
 *<P>
 * 
 * �� ��: �򵥵�IOC����,��֧�ֻ������õ� IOC ����
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-8-20
 * 
 */
public class NDotSimpleXMLApplicationContext {
	/* �������Bean �Ķ��� */
	private List<NDotBeanDefinition> ndotBeanDefinitions = new ArrayList<NDotBeanDefinition>();

	/* �������Bean ��ʵ�� */
	private Map<String, Object> ndotBeanInstances = new HashMap<String, Object>();

	/**
	 * 
	 * @param config
	 */
	public NDotSimpleXMLApplicationContext(String config) {
		this(new String[] { config });
	}

	/**
	 * 
	 * @param configs
	 */
	public NDotSimpleXMLApplicationContext(String[] configs) {
		analysisConfig(configs);
		createInstances();
		initProperty();
	}

	/**
	 * 
	 * �� �ܣ��������е������ļ�
	 * 
	 *<P>
	 * 
	 * @param configs
	 */
	public void analysisConfig(String[] configs) {
		for (int i = 0; i < configs.length; i++) {
			analysisConfig(configs[i]);
		}
	}

	private void analysisConfig(String config) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			URL filePath = this.getClass().getClassLoader().getResource(config);
			doc = reader.read(filePath);
			Map<String, String> nsMap = new HashMap<String, String>();
			nsMap.put("ns", "http://www.springframework.org/schema/beans");
			// create search path
			XPath xpath = doc.createXPath("//ns:beans/ns:bean");
			xpath.setNamespaceURIs(nsMap);
			// get all config beans
			List<Element> beans = xpath.selectNodes(doc);
			for (Element ele : beans) {
				String id = ele.attributeValue("id");
				String clazz = ele.attributeValue("class");
				NDotBeanDefinition beanDefine = new NDotBeanDefinition(id,
						clazz);
				// ��ȡ���Զ���
				XPath propertyXPath = ele.createXPath("ns:property");
				propertyXPath.setNamespaceURIs(nsMap);
				List<Element> properties = propertyXPath.selectNodes(ele);
				for (Element property : properties) {
					String name = property.attributeValue("name");
					String ref = property.attributeValue("ref");
					String value = property.attributeValue("value");
					NDotPropertyDefinetion pd = new NDotPropertyDefinetion(
							name, ref, value);
					beanDefine.getProperties().add(pd);
				}
				ndotBeanDefinitions.add(beanDefine);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * �� �ܣ�����bean�Ķ������ã�����beanʵ��
	 * 
	 *<P>
	 */
	private void createInstances() {
		try {
			for (NDotBeanDefinition bd : ndotBeanDefinitions) {
				String classType = bd.getClazz();
				if (null != classType && !"".equals(classType)) {
					Class clazz = Class.forName(classType);
					ndotBeanInstances.put(bd.getId(), clazz.newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * �� �ܣ���ʵ�����������Խ���ע��
	 * 
	 *<P>
	 */
	private void initProperty() {
		try {
			// �������е�ʵ����ע������������
			for (NDotBeanDefinition bd : ndotBeanDefinitions) {
				Object bean = ndotBeanInstances.get(bd.getId());
				if (null != bean) {
					/*
					 * Introspector ��Ϊͨ������ѧϰ�й���Ŀ�� Java Bean
					 * ֧�ֵ����ԡ��¼��ͷ�����֪ʶ�ṩ��һ����׼������
					 * 
					 * ������������Ϣ��Introspector ���ֱ���� bean
					 * ����ͳ��࣬Ѱ����ʽ����ʽ��Ϣ��ʹ����Щ��Ϣ����һ��ȫ������Ŀ�� bean �� BeanInfo ����
					 * BeanInfo.getPropertyDescriptors()��� beans
					 * PropertyDescriptor PropertyDescriptor ���� Java Bean
					 * ͨ��һ�Դ洢������������һ�����ԡ�
					 */
					PropertyDescriptor[] ps = Introspector.getBeanInfo(
							bean.getClass()).getPropertyDescriptors();
					for (NDotPropertyDefinetion pd : bd.getProperties()) {
						for (PropertyDescriptor pdes : ps) {
							if (pd.getName().equals(pdes.getName())) {
								Method setter = pdes.getWriteMethod();
								if (null != setter) {
									Object value;
									value = pd.getValue();
									if (null == value || "".equals(value)) {

										value = pd.getRefBean();
										if (null != value && !"".equals(value)) {
											value = ndotBeanInstances
													.get(value);
										}
									}
									if (null != value || !"".equals(value)) {

										setter.setAccessible(true);
										String type = setter
												.getParameterTypes()[0]
												.toString();

										value = conver(value, type);
										// ע�����õĶ���
										setter.invoke(bean, value);
									}

								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Object conver(Object value, String type) {
		System.out.println(type);
		if (type.equals("int")) {
			value = (new Integer((String) value)).intValue();
		}
		return value;
	}

	/**
	 * 
	 * �� �ܣ�����bean���õ����ƣ�name�����Bean��ʵ��
	 * 
	 *<P>
	 * 
	 * @param name
	 * @return Object
	 */
	public Object getBean(String name) {
		return this.ndotBeanInstances.get(name);
	}
}
