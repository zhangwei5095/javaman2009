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
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： NDotXMLApplicationContext.java
 * 
 *<P>
 * 
 * 功 能: 简单的IOC容器,仅支持基于配置的 IOC 容器
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-8-20
 * 
 */
public class NDotSimpleXMLApplicationContext {
	/* 存放所有Bean 的定义 */
	private List<NDotBeanDefinition> ndotBeanDefinitions = new ArrayList<NDotBeanDefinition>();

	/* 存放所有Bean 的实例 */
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
	 * 功 能：解析所有的配置文件
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
				// 读取属性定义
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
	 * 功 能：根据bean的定义配置，创建bean实例
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
	 * 功 能：对实例的所有属性进行注入
	 * 
	 *<P>
	 */
	private void initProperty() {
		try {
			// 遍历所有的实例并注入其所有属性
			for (NDotBeanDefinition bd : ndotBeanDefinitions) {
				Object bean = ndotBeanInstances.get(bd.getId());
				if (null != bean) {
					/*
					 * Introspector 类为通过工具学习有关受目标 Java Bean
					 * 支持的属性、事件和方法的知识提供了一个标准方法。
					 * 
					 * 对于这三种信息，Introspector 将分别分析 bean
					 * 的类和超类，寻找显式或隐式信息，使用这些信息构建一个全面描述目标 bean 的 BeanInfo 对象。
					 * BeanInfo.getPropertyDescriptors()获得 beans
					 * PropertyDescriptor PropertyDescriptor 描述 Java Bean
					 * 通过一对存储器方法导出的一个属性。
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
										// 注入引用的对象
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
	 * 功 能：根据bean配置的名称（name）获得Bean的实例
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
