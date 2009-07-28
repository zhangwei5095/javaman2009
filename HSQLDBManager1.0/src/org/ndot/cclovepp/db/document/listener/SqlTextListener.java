package org.ndot.cclovepp.db.document.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class SqlTextListener implements ModifyListener, ExtendedModifyListener,
		LineBackgroundListener, VerifyListener {

	private StyledText styledText = null;

	public SqlTextListener(StyledText styledText) {
		this.styledText = styledText;

	}

	public void modifyText(ModifyEvent e) {
		System.out.print("文本信息已经改变...");
	}

	// 注册ExtendedModify事件
	public void modifyText(ExtendedModifyEvent event) {
		// 获得修改字符的最后的位置
		int end = event.start + event.length - 1;
		// 如果为插入字符操作
		if (event.start <= end) {
			// 获得插入的字符StyleRange对象
			String text = styledText.getText(event.start, end);
			// 创建一个list对象保存所有的
			java.util.List ranges = new java.util.ArrayList();
			// 循环输入的每一个字符，如果有字符为数字，则将该字符的位置保存到一个StyleRange对象中
			for (int i = 0; i < text.length(); i++) {
				if ("0123456789".indexOf(text.charAt(i)) > -1)
					ranges.add(new StyleRange(event.start + i, 1, event.display
							.getSystemColor(SWT.COLOR_RED), null, SWT.BOLD));
			}
			// 如果保存StyleRange对象的list不为空，则将list中的StyleRange应用到格式化文本中
			if (!ranges.isEmpty())
				styledText.replaceStyleRanges(event.start, event.length,
						(StyleRange[]) ranges.toArray(new StyleRange[0]));
		}
	}

	// 注册背景色改变事件
	public void lineGetBackground(LineBackgroundEvent event) {

		// 获得当前行的文本
		String text = event.lineText;
		// 如果在文本中找到import关键字，则设置该行的背景色为灰色
		if (text.indexOf("import") > -1)
			event.lineBackground = styledText.getDisplay().getSystemColor(
					SWT.COLOR_GRAY);

	}
    //注册获取输入的字符
	public void verifyText(VerifyEvent e) {
		System.out.println(e.text);
		if (e.end - e.start == 0) {
			if (e.text.equals("class")) {
				e.text = "class";
			}
		}
	}

}
