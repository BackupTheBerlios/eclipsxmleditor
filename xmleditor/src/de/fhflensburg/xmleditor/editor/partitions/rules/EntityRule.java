/*
 * Created on 19.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

import de.fhflensburg.xmleditor.editor.highlighting.XMLWhitespaceDetector;

/**
 * @author user
 *
 */
public class EntityRule extends MultiLineRule {

	private XMLWhitespaceDetector wsd;
	/**
	 * @param detector
	 * @param defaultToken
	 */
	public EntityRule(IToken defaultToken) {
		super("&", ";", defaultToken);
		wsd = new XMLWhitespaceDetector();
	}

	protected boolean sequenceDetected(
		ICharacterScanner scanner,
		char[] sequence,
		boolean eofAllowed) {
		if (sequence[0]=='&') {
			char c ;
			int i=0;
			do {
				c = (char) scanner.read();
				i++;
				if (wsd.isWhitespace(c)) {
					for (int j = 0; j < i; j++) {
						scanner.unread();
					}
					return false;
				}
			} while (c!=';');
			scanner.unread();
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}

}
