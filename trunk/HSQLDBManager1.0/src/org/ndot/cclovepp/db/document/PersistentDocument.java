package org.ndot.cclovepp.db.document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

public class PersistentDocument extends Document implements IDocumentListener {
	private String fileName;// �򿪵��ļ���

	private boolean dirty;// �ļ��Ƿ��޸Ĺ�

	public PersistentDocument() {
		this.addDocumentListener(this);// Ϊ���ĵ�ע���ĵ�������
	}

	// ���浽ָ�����ļ�
	public void save() throws IOException {
		if (fileName == null)
			throw new IllegalStateException("�ļ�����Ϊ�գ�");
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName));
			out.write(get());
			dirty = false;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	// ���ļ��ķ���
	public void open() throws IOException {
		if (fileName == null)
			throw new IllegalStateException("�ļ�����Ϊ�գ�");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			StringBuffer buf = new StringBuffer();
			int n;
			while ((n = in.read()) != -1) {
				buf.append((char) n);
			}
			set(buf.toString());
			dirty = false;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// �ӿ��еķ�������ʵ��
	public void documentAboutToBeChanged(DocumentEvent arg0) {

	}

	// �ӿ��еķ��������ĵ��޸ĺ��������޸�״̬
	public void documentChanged(DocumentEvent arg0) {
		dirty = true;
	}
}
