/*
 * Created on 21.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

import de.fhflensburg.xmleditor.editor.util.*;

public class EndTagRule extends MultiLineRule {

	public EndTagRule(IToken token) {
		super("<", ">", token);
	}
	protected boolean sequenceDetected(
		ICharacterScanner scanner,
		char[] sequence,
		boolean eofAllowed) {
		int c = scanner.read();
		if (sequence[0] == '<') {
			CharacterScannerDeco decoScanner = new CharacterScannerDeco(scanner); 
			if (decoScanner.firstMatch(new int[] {'<','>'})=='>') {
				if (c != '/') {
					// no Endtag - abort
					scanner.unread();
					return false;
				}
			}
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
