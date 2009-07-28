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
		System.out.print("�ı���Ϣ�Ѿ��ı�...");
	}

	// ע��ExtendedModify�¼�
	public void modifyText(ExtendedModifyEvent event) {
		// ����޸��ַ�������λ��
		int end = event.start + event.length - 1;
		// ���Ϊ�����ַ�����
		if (event.start <= end) {
			// ��ò�����ַ�StyleRange����
			String text = styledText.getText(event.start, end);
			// ����һ��list���󱣴����е�
			java.util.List ranges = new java.util.ArrayList();
			// ѭ�������ÿһ���ַ���������ַ�Ϊ���֣��򽫸��ַ���λ�ñ��浽һ��StyleRange������
			for (int i = 0; i < text.length(); i++) {
				if ("0123456789".indexOf(text.charAt(i)) > -1)
					ranges.add(new StyleRange(event.start + i, 1, event.display
							.getSystemColor(SWT.COLOR_RED), null, SWT.BOLD));
			}
			// �������StyleRange�����list��Ϊ�գ���list�е�StyleRangeӦ�õ���ʽ���ı���
			if (!ranges.isEmpty())
				styledText.replaceStyleRanges(event.start, event.length,
						(StyleRange[]) ranges.toArray(new StyleRange[0]));
		}
	}

	// ע�ᱳ��ɫ�ı��¼�
	public void lineGetBackground(LineBackgroundEvent event) {

		// ��õ�ǰ�е��ı�
		String text = event.lineText;
		// ������ı����ҵ�import�ؼ��֣������ø��еı���ɫΪ��ɫ
		if (text.indexOf("import") > -1)
			event.lineBackground = styledText.getDisplay().getSystemColor(
					SWT.COLOR_GRAY);

	}
    //ע���ȡ������ַ�
	public void verifyText(VerifyEvent e) {
		System.out.println(e.text);
		if (e.end - e.start == 0) {
			if (e.text.equals("class")) {
				e.text = "class";
			}
		}
	}

}
