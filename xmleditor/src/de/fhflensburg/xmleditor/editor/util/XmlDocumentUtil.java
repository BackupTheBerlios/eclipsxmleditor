/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.util;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;

/**
 * @author user
 *
 */
public class XmlDocumentUtil {
	public static final String getNameOfAchestor(IDocument doc, int offset) {
		try {
			ITypedRegion part = doc.getPartition(offset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		return null;
	}
}
