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

	private TextAttribute keywords;// 关键字的文本属性

	private TextAttribute string;// 字符串的文本属性

	private TextAttribute object;// 内置对象的文本属性

	private TextAttribute comment;// 注释部分的文本属性

	public SqlScanner() {
		// 获得首选项中所设置的颜色，初始化各文本属性
		keywords = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_KEYWORD), null, SWT.BOLD);
		string = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_STRING));
		object = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_OBJECT));
		comment = new TextAttribute(ResourceManager
				.getColor(Constants.COLOR_COMMENT), null, SWT.ITALIC);
		// 设置代码的规则
		setupRules();
	}

	private void setupRules() {
		// 用一个List集合对象保存所有的规则
		List rules = new ArrayList();
		// 字符串的规则
		rules.add(new SingleLineRule("\"", "\"", new Token(string), '\\'));
		rules.add(new SingleLineRule("'", "'", new Token(string), '\\'));
		// 注释的规则
		rules.add(new SingleLineRule("/*", "*/", new Token(comment), '\\'));
		rules.add(new EndOfLineRule("//", new Token(comment), '\\'));
		// 空格的规则
		rules.add(new WhitespaceRule(new IWhitespaceDetector() {
			public boolean isWhitespace(char c) {
				return Character.isWhitespace(c);
			}
		}));
		// 关键字的规则
		WordRule keywordRule = new WordRule(new KeywordDetector());
		for (int i = 0, n = Constants.JS_SYNTAX_KEYWORD.length; i < n; i++)
			keywordRule.addWord(Constants.JS_SYNTAX_KEYWORD[i], new Token(
					keywords));
		rules.add(keywordRule);
		// 内置对象的规则
		WordRule objectRule = new WordRule(new ObjectDetector());
		for (int i = 0, n = Constants.JS_SYNTAX_BUILDIB_OBJECT.length; i < n; i++)
			objectRule.addWord(Constants.JS_SYNTAX_BUILDIB_OBJECT[i],
					new Token(object));
		rules.add(objectRule);
		// 集合类中保存的规则转化为数组
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		// 调用父类中的方法，设置规则
		// 此方法非常重要
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
