package org.ndot.simples.bank;

import java.io.File;
import java.io.FileInputStream;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.nasoft.PackagerFactory;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： BankProtocolCodecFactory.java
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
 * 创建时间: 2009-11-16
 * 
 */
public class BankProtocolCodecFactory implements ProtocolCodecFactory {
	private BankProtocolDecoder decoder;
	private BankProtocolEncoder encoder;

	public BankProtocolCodecFactory() {
		this.decoder = new BankProtocolDecoder();
		this.encoder = new BankProtocolEncoder();
		System.out.println("start init......");
		init();
		System.out.println("finish init......");
	}

	public void init() {
		try {

			// pos
			String isoset = "d:/xml/C004/isodef.xml";
			String node = "d:/xml/C004/node.xml";

			File file = new File(isoset);

			FileInputStream fin = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			fin.read(b);
			fin.close();
			PackagerFactory.initPackagerMap(new String(b));
			PackagerFactory.initHeaderMap(new String(b));

			File file3 = new File(node);
			FileInputStream fin3 = new FileInputStream(file3);
			byte[] b3 = new byte[(int) file3.length()];
			fin3.read(b3);
			fin3.close();
			PackagerFactory.initNodeInfo(new String(b3));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
