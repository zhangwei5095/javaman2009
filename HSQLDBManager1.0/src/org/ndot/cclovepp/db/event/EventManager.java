package org.ndot.cclovepp.db.event;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.FileDialog;
import org.ndot.cclovepp.db.NDotDB;

public class EventManager {
	private NDotDB editor;

	public EventManager(NDotDB editor) {
		this.editor = editor;
	}
	//设置字体
	public void setCodeFont(FontData[] fontData) {
		Font font = editor.getSqlSourceViewer().getTextWidget().getFont();
		if (font != null)
			font.dispose();
		font = new Font(editor.getShell().getDisplay(), fontData);
		editor.getSqlSourceViewer().getTextWidget().setFont(font);
	}
	//打开文件
	public void openFile() {
		FileDialog dialog = new FileDialog(editor.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.js", "*.html", "*.htm",
				"*.*" });
		String name = dialog.open();
		if ((name == null) || (name.length() == 0))
			return;
		try {
			editor.getDocument().setFileName(name);
			editor.getDocument().open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//保存文件
	public void saveFile() {
		if (!editor.getDocument().isDirty())
			return;
		boolean b = MessageDialog.openConfirm(editor.getShell(), "确认保存",
				"您确实要保存文件吗？");
		if (b) {
			try {
				editor.getDocument().save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//查找字符
	public boolean isFind(FindReplaceDocumentAdapter adapter, String find,
			boolean forward, boolean matchCase, boolean wholeWord,
			boolean regexp) {
		boolean bFind = false;
		IRegion region = null;
		try {
			// 获得当前文本所在的位置，也就是偏移量
			int offset = editor.getSqlSourceViewer().getTextWidget().getCaretOffset();

			if (!forward) {
				Point pt = editor.getSqlSourceViewer().getSelectedRange();
				if (pt.x != pt.y) {
					offset = pt.x - 1;
				}
			}
			//确保没有超出adapter的范围
			if (offset >= adapter.length())
				offset = adapter.length() - 1;
			if (offset < 0)
				offset = 0;
			//查找字符
			region = adapter.find(offset, find, forward, matchCase, wholeWord,regexp);
			//如果找到，设置匹配的字符选中，并返回true
			if (region != null) {
				editor.getSqlSourceViewer().setSelectedRange(region.getOffset(),region.getLength());
				bFind = true;
			}

		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		} catch (org.eclipse.jface.text.BadLocationException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return bFind;
	}
	//替换字符
	public void doReplace(FindReplaceDocumentAdapter adapter, String replaceText) {
		try {
			adapter.replace(replaceText, false);
		} catch (org.eclipse.jface.text.BadLocationException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
