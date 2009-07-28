package org.ndot.cclovepp.db.document.config;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.ndot.cclovepp.db.NDotDB;
import org.ndot.cclovepp.db.document.scanner.SqlScanner;

public class SqlConfig extends SourceViewerConfiguration {
	private NDotDB editor ;
	public SqlConfig( NDotDB editor ){
		this.editor=editor;
	}
	//覆盖父类中的方法，主要提供代码着色功能
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
	    DefaultDamagerRepairer dr = new DefaultDamagerRepairer(new SqlScanner());
	    reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
	    reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
	    return reconciler;
	}	
}
