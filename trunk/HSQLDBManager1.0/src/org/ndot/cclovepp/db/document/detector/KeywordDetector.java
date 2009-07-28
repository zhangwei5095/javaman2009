package org.ndot.cclovepp.db.document.detector;

import org.eclipse.jface.text.rules.IWordDetector;
import org.ndot.cclovepp.db.util.Constants;

public class KeywordDetector implements IWordDetector {

	// �ӿ��еķ���,�ַ��Ƿ��ǵ��ʵĿ�ʼ
	public boolean isWordStart(char c) {
		//ѭ�����еĹؼ���
		//����йؼ����еĵ�һ���ַ�ƥ����ַ����򷵻�true
		for (int i = 0, n = Constants.JS_SYNTAX_KEYWORD.length; i < n; i++)
			if (c == ((String) Constants.JS_SYNTAX_KEYWORD[i]).charAt(0))
				return true;
		return false;
	}

	// �ӿ��еķ���,�ַ��Ƿ��ǵ����е�һ����
	public boolean isWordPart(char c) {
		//ѭ�����еĹؼ���
		//����ؼ��ֵ��ַ����и��ַ����򷵻�true
		for (int i = 0, n = Constants.JS_SYNTAX_KEYWORD.length; i < n; i++)
			if (((String) Constants.JS_SYNTAX_KEYWORD[i]).indexOf(c) != -1)
				return true;
		return false;
	}

}
