/*
 * Created on 11.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import de.fhflensburg.xmleditor.editor.highlighting.XMLWhitespaceDetector;

/**
 * @author user
 *
 */
public class AttributeRule implements IRule	{

	private IToken token;
	private XMLWhitespaceDetector wsd;

	/**
	 * @param attributename
	 */
	public AttributeRule(IToken attributename) {
		this.token = attributename;
		this.wsd = new XMLWhitespaceDetector();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner) {
		scanner.unread();
		int c = scanner.read();
		if (wsd.isWhitespace((char) c)) {
			int i = 0;
			do {
				c = scanner.read();
				i++;
			} while (c!='=' && !wsd.isWhitespace((char) c) && c!='>' && c!=ICharacterScanner.EOF);
			if (c=='=') {
				return this.token;
			} 
			for (;i>0;i--) {
				scanner.unread();
			}
		}
		return Token.UNDEFINED;
	}
	
}
