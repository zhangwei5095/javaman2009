package org.ndot.cclovepp.db.document.detector;

import org.eclipse.jface.text.rules.IWordDetector;
import org.ndot.cclovepp.db.util.Constants;


public class ObjectDetector implements IWordDetector {public boolean isWordStart(char c) {
	for (int i = 0, n = Constants.JS_SYNTAX_BUILDIB_OBJECT.length; i < n; i++)
		if (c == ((String) Constants.JS_SYNTAX_BUILDIB_OBJECT[i]).charAt(0))
			return true;
	return false;
}

public boolean isWordPart(char c) {
	for (int i = 0, n = Constants.JS_SYNTAX_BUILDIB_OBJECT.length; i < n; i++)
		if (((String) Constants.JS_SYNTAX_BUILDIB_OBJECT[i]).indexOf(c) != -1)
			return true;
	return false;
}}
