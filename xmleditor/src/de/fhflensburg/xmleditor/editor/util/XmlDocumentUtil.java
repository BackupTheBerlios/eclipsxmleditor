/*
 * Created on 19.10.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.fhflensburg.xmleditor.editor.util;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;

/**
 * @author user
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
