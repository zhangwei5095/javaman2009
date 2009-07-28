package org.ndot.cclovepp.db.document.scanner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.ndot.cclovepp.db.document.detector.KeywordDetector;
import org.ndot.cclovepp.db.document.detector.ObjectDetector;
import org.ndot.cclovepp.db.document.resource.ResourceManager;
import org.ndot.cclovepp.db.util.Constants;

public class SqlScanner extends RuleBasedScanner {

	private TextAttribute keywords;// �ؼ��ֵ��ı�����

	private TextAttribute string;// �ַ������ı�����

	private TextAttribute object;// ���ö�����ı�����

	private TextAttribute comment;// ע�Ͳ��ֵ��ı�����

	public SqlScanner() {
		// �����ѡ���������õ���ɫ����ʼ�����ı�����
		keywords = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_KEYWORD), null, SWT.BOLD);
		string = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_STRING));
		object = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_OBJECT));
		comment = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_COMMENT), null, SWT.ITALIC);
		// ���ô���Ĺ���
		setupRules();
	}

	private void setupRules() {
		// ��һ��List���϶��󱣴����еĹ���
		List rules = new ArrayList();
		// �ַ����Ĺ���
		rules.add(new SingleLineRule("\"", "\"", new Token(string), '\\'));
		rules.add(new SingleLineRule("'", "'", new Token(string), '\\'));
		// ע�͵Ĺ���
		rules.add(new SingleLineRule("/*", "*/", new Token(comment), '\\'));
		rules.add(new EndOfLineRule("//", new Token(comment), '\\'));
		// �ո�Ĺ���
		rules.add(new WhitespaceRule(new IWhitespaceDetector() {
			public boolean isWhitespace(char c) {
				return Character.isWhitespace(c);
			}
		}));
		// �ؼ��ֵĹ���
		WordRule keywordRule = new WordRule(new KeywordDetector());
		for (int i = 0, n = Constants.JS_SYNTAX_KEYWORD.length; i < n; i++)
			keywordRule.addWord(Constants.JS_SYNTAX_KEYWORD[i], new Token(
					keywords));
		rules.add(keywordRule);
		// ���ö���Ĺ���
		WordRule objectRule = new WordRule(new ObjectDetector());
		for (int i = 0, n = Constants.JS_SYNTAX_BUILDIB_OBJECT.length; i < n; i++)
			objectRule.addWord(Constants.JS_SYNTAX_BUILDIB_OBJECT[i],
					new Token(object));
		rules.add(objectRule);
		// �������б���Ĺ���ת��Ϊ����
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		// ���ø����еķ��������ù���
		// �˷����ǳ���Ҫ
		setRules(result);
	}

//	private String[] upperKeyWord(String keyword) {
//		int len = keyword.length();
//		String[] keywords = new String[len];
//		for (int i = 0; i < keywords.length; i++) {
//			int nc = keyword.charAt(i) - 32;
//			char tem = (char) nc;
//			String newkw = keyword.substring(0, i);
//			newkw += tem;
//			newkw += keyword.substring(i + 1);
//			keywords[i] = newkw;
//		}	
//		return keywords;
//	}

}
