package de.fhflensburg.xmleditor.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import de.fhflensburg.xmleditor.editor.partitions.XMLPartitionScanner;

public class XMLDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner =
				new DefaultPartitioner(
					new XMLPartitionScanner(),
					new String[] {
						XMLPartitionScanner.XML_CDATA,
						XMLPartitionScanner.XML_COMMENT,
						XMLPartitionScanner.XML_DECLARATION,
						XMLPartitionScanner.XML_EMPTYTAG,
						XMLPartitionScanner.XML_ENDTAG,
						XMLPartitionScanner.XML_ENTITY,
						XMLPartitionScanner.XML_PROCESSOR_INSTRUCTION,
						XMLPartitionScanner.XML_STARTTAG});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}