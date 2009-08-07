/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.api;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

/**
 * process engine configuration.
 * 
 * @author Tom Baeyens
 */
public class Configuration {

	static Map<String, String> implementationClassNames = null;

	Configuration impl;

	/** default constructor */
	public Configuration() {
		this((String) null);
	}

	/**
	 * creates a configuration of a specific implementation type. no values are
	 * supported for type yet. only <code>null</code>.
	 */
	public Configuration(String type) {
		String implementationClassName = getImplementationClassName(type);
		if (implementationClassName == null) {
			throw new JbpmException("type is null");
		}
		impl = instantiate(implementationClassName);
	}

	/**
	 * empty constructor to be used by concrete implementations of Configuration
	 */
	protected Configuration(Configuration base) {
	}

	private synchronized String getImplementationClassName(String type) {
		if (implementationClassNames == null) {
			implementationClassNames = new HashMap<String, String>();
			// null represents the default configuration (== the
			// JbpmConfiguration)
			implementationClassNames.put(null,
					"org.jbpm.pvm.internal.cfg.JbpmConfiguration");
			implementationClassNames.put("spring-test",
					"org.jbpm.pvm.internal.cfg.SpringConfiguration");
			// TODO
			// implementationClasses.put("mc",
			// "org.jbpm.pvm.internal.cfg.McConfiguration");
			// implementationClasses.put("programatic",
			// "org.jbpm.pvm.internal.cfg.ProgramaticConfiguration");
		}
		String implementationClassName = implementationClassNames.get(type);
		if (implementationClassName == null) {
			implementationClassName = type;
		}
		return implementationClassName;
	}

	protected Configuration instantiate(String className) {
		Configuration implementation;
		try {
			/*
			 * 
			 * ���ظ��̵߳������� ClassLoader�������� ClassLoader
			 * ���̴߳������ṩ���������ڸ��߳��еĴ����ڼ��������Դʱʹ�á����δ�趨����Ĭ��Ϊ���̵߳� ClassLoader
			 * �����ġ�ԭʼ�̵߳������� ClassLoader ͨ���趨Ϊ���ڼ���Ӧ�ó�������������
			 * ���ȣ�����а�ȫ�����������ҵ����ߵ������������ null��Ҳ��ͬ��������������������ڱ�������߳�������������������ȣ���ͨ��
			 * RuntimePermission("getClassLoader") Ȩ�޵��øð�ȫ�������� checkPermission
			 * �������鿴�Ƿ���Ի�ȡ������ ClassLoader��
			 */
			Class<?> implementationClass = Thread.currentThread()
					.getContextClassLoader().loadClass(className);
			implementation = (Configuration) implementationClass.newInstance();
		} catch (Exception e) {
			throw new JbpmException(
					"couldn't instantiate configuration of type " + className,
					e);
		}
		return implementation;
	}

	protected ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/** provide an xml string as the configuration resource */
	public Configuration setXmlString(String xmlString) {
		impl.setXmlString(xmlString);
		return impl;
	}

	/** provide an classpath resource as the configuration file */
	public Configuration setResource(String resource) {
		impl.setResource(resource);
		return impl;
	}

	/** provide an input stream as the configuration resource */
	public Configuration setInputStream(InputStream inputStream) {
		impl.setInputStream(inputStream);
		return impl;
	}

	/** provide a sax input source as the configuration resource */
	public Configuration setInputSource(InputSource inputSource) {
		impl.setInputSource(inputSource);
		return impl;
	}

	/** point with a url to the configuration file */
	public Configuration setUrl(URL url) {
		impl.setUrl(url);
		return impl;
	}

	/** provide a File as the configuration file */
	public Configuration setFile(File file) {
		impl.setFile(file);
		return impl;
	}

	/**
	 * after specifying the configuration resources with the other methods, a
	 * process engine can be created.
	 */
	public ProcessEngine buildProcessEngine() {
		return impl.buildProcessEngine();
	}
}
