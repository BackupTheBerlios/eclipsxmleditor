package de.fhflensburg.xmleditor.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import de.fhflensburg.xmleditor.contentassist.XmlContentAssistProcessor;
import de.fhflensburg.xmleditor.editor.highlighting.ColorManager;
import de.fhflensburg.xmleditor.editor.highlighting.IXMLColorConstants;
import de.fhflensburg.xmleditor.editor.partitions.CDATAScanner;
import de.fhflensburg.xmleditor.editor.partitions.XMLPartitionScanner;
import de.fhflensburg.xmleditor.editor.partitions.XMLScanner;
import de.fhflensburg.xmleditor.editor.partitions.XMLTagScanner;

public class XMLConfiguration extends SourceViewerConfiguration {
	private ColorManager colorManager;
	private XMLDoubleClickStrategy doubleClickStrategy;
	private XMLScanner scanner;
	private XMLTagScanner tagScanner;
	private CDATAScanner cdataScanner;

	public XMLConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	/**
	 * @return
	 */
	private CDATAScanner getCDATAScanner() {
		if (cdataScanner == null) {
			cdataScanner = new CDATAScanner(colorManager);
		}
		return cdataScanner;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			XMLPartitionScanner.XML_CDATA,
			XMLPartitionScanner.XML_COMMENT,
			XMLPartitionScanner.XML_DECLARATION,
			XMLPartitionScanner.XML_EMPTYTAG,
			XMLPartitionScanner.XML_ENDTAG,
			XMLPartitionScanner.XML_ENTITY,
			XMLPartitionScanner.XML_PROCESSOR_INSTRUCTION,
			XMLPartitionScanner.XML_STARTTAG };
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getContentAssistant(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant ca = new ContentAssistant();
		ca.install(sourceViewer);
		ca.enableAutoActivation(true);
		ca.enableAutoInsert(true);
		ca.setAutoActivationDelay(3);
		ca.setContentAssistProcessor(
			new XmlContentAssistProcessor(),
			IDocument.DEFAULT_CONTENT_TYPE);
		return super.getContentAssistant(sourceViewer);
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new XMLDoubleClickStrategy();
		return doubleClickStrategy;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_STARTTAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_STARTTAG);

		dr = new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_ENDTAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_ENDTAG);

		dr = new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_EMPTYTAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_EMPTYTAG);

		dr = new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(
			dr,
			XMLPartitionScanner.XML_PROCESSOR_INSTRUCTION);
		reconciler.setRepairer(
			dr,
			XMLPartitionScanner.XML_PROCESSOR_INSTRUCTION);

		dr = new DefaultDamagerRepairer(getCDATAScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_CDATA);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_CDATA);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IXMLColorConstants.XML_COMMENT)));
		reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

		ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IXMLColorConstants.XML_PROCESSOR_INSTRUCTION)));
		reconciler.setDamager(ndr, XMLPartitionScanner.XML_ENTITY);
		reconciler.setRepairer(ndr, XMLPartitionScanner.XML_ENTITY);
		
		ndr =
					new NonRuleBasedDamagerRepairer(
						new TextAttribute(
							colorManager.getColor(IXMLColorConstants.XML_DECLARATION)));
				reconciler.setDamager(ndr, XMLPartitionScanner.XML_DECLARATION);
				reconciler.setRepairer(ndr, XMLPartitionScanner.XML_DECLARATION);

		ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IXMLColorConstants.DEFAULT)));
		reconciler.setDamager(ndr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(ndr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

	protected XMLScanner getXMLScanner() {
		if (scanner == null) {
			scanner = new XMLScanner(colorManager);
		}
		return scanner;
	}
	protected XMLTagScanner getXMLTagScanner() {
		if (tagScanner == null) {
			tagScanner = new XMLTagScanner(colorManager);
		}
		return tagScanner;
	}

}